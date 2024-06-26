branch=main

source ./setupEnvironment.sh

if [ -z "$CAPSTONE_REPO_NAME" ] ; then
  echo "Your environment variables are not properly configured.  Make sure that you have filled out setupEnvironment.sh and that script is set to run as part of your PATH"
  exit 1
fi

if [ -z "$GITHUB_TOKEN" ] ; then
  echo "Your environment variable GITHUB_TOKEN is not properly configured.  Make sure that you have added it to your .bash_profile"
  exit 1
fi

if [ -z "$GITHUB_GROUP_NAME" ] || [ "$GITHUB_GROUP_NAME" == "replacewithyourgroupname" ] ; then
  echo "Your environment variable GITHUB_GROUP_NAME is not properly configured.  Make sure that you have set it properly in setupEnvironment.sh"
  exit 1
fi

# Local path to the CloudFormation template
TEMPLATE_FILE_PATH="./buildScripts/CICDPipeline-Capstone.yml"

echo "Outputting parameters for the pipeline..."
echo "Project name: $CAPSTONE_PROJECT_NAME"
echo "Github Group Name: $GITHUB_GROUP_NAME"
echo "Repo path: $CAPSTONE_REPO_NAME"
echo "Branch: $branch"
echo "Template file path: $TEMPLATE_FILE_PATH"
#curl -H "Authorization: token $GITHUB_TOKEN" https://api.github.com/user

# Check if the stack exists
STACK_EXISTS=$(aws cloudformation describe-stacks --stack-name $GITHUB_GROUP_NAME-$CAPSTONE_PROJECT_NAME 2>&1)

if [[ $STACK_EXISTS == *"ValidationError"* ]]; then
  ACTION="create-stack"
else
  ACTION="update-stack"
fi

# Execute the appropriate CloudFormation command
aws cloudformation $ACTION --stack-name $GITHUB_GROUP_NAME-$CAPSTONE_PROJECT_NAME \
--template-body file://$TEMPLATE_FILE_PATH --parameters \
ParameterKey=ProjectName,ParameterValue=$CAPSTONE_PROJECT_NAME \
ParameterKey=GithubUserName,ParameterValue=$GITHUB_USERNAME \
ParameterKey=GithubGroupName,ParameterValue=$GITHUB_GROUP_NAME \
ParameterKey=Repo,ParameterValue=$CAPSTONE_REPO_NAME \
ParameterKey=Branch,ParameterValue=$branch \
ParameterKey=GithubToken,ParameterValue=$GITHUB_TOKEN \
--capabilities CAPABILITY_IAM CAPABILITY_AUTO_EXPAND

# Check the exit status of the command
if [ $? -ne 0 ]; then
  echo "AWS CLI command failed. Check the error message above for more details."
  exit 1
fi

# Added to wait if the stack creation is completed.
aws cloudformation wait stack-create-complete --stack-name $GITHUB_GROUP_NAME-$CAPSTONE_PROJECT_NAME

# Check if the stack operation succeeded
STACK_STATUS=$(aws cloudformation describe-stacks --stack-name $GITHUB_GROUP_NAME-$CAPSTONE_PROJECT_NAME --query "Stacks[0].StackStatus" --output text)
if [ "$STACK_STATUS" != "CREATE_COMPLETE" ] && [ "$STACK_STATUS" != "UPDATE_COMPLETE" ]; then
  echo "Stack $ACTION failed with status: $STACK_STATUS"
  echo "Fetching stack events for more details..."
  aws cloudformation describe-stack-events --stack-name $GITHUB_GROUP_NAME-$CAPSTONE_PROJECT_NAME --query "StackEvents[?ResourceStatus=='ROLLBACK_COMPLETE' || ResourceStatus=='CREATE_FAILED' || ResourceStatus=='UPDATE_FAILED']" --output table

  exit
fi

echo "Stack $ACTION completed."
