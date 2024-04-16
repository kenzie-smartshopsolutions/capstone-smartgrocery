// user.js

document.addEventListener('DOMContentLoaded', (event) => {
    const settingsForm = document.getElementById('settings-form');

    settingsForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const password = document.getElementById('password').value;
        const username = document.getElementById('username').value;
        const email = document.getElementById('email').value;
        const householdName = document.getElementById('householdName').value;

        // Placeholder for API endpoint
        const userId = localStorage.getItem('userId');
        const apiEndpoint = `/User/register/userId/${userId}`;

        fetch(apiEndpoint, {
            method: 'PUT', // Use PUT for updates
            headers: {
                'Content-Type': 'application/json',
                // Add your authorization header if needed
                'Authorization': 'Bearer ' + localStorage.getItem('jwt')
            },
            body: JSON.stringify({ password, username, email, householdName }),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log('Update successful', data);
                // Handle the UI update or redirection as needed
            })
            .catch(error => {
                console.error('Update failed', error);
                // Handle the UI error display
            });
    });

    // Deleting a user
    document.addEventListener('DOMContentLoaded', (event) => {
        const settingsForm = document.getElementById('settings-form');

        settingsForm.addEventListener('submit', function (e) {
            e.preventDefault();

            const password = document.getElementById('password').value;
            const username = document.getElementById('username').value;
            const email = document.getElementById('email').value;
            const householdName = document.getElementById('householdName').value;

            // Placeholder for API endpoint
            const userId = localStorage.getItem('userId');;
            const apiEndpoint = `/User/register/userId/${userId}`;

            fetch(apiEndpoint, {
                method: 'PUT', // Use PUT for updates
                headers: {
                    'Content-Type': 'application/json',
                    // Add your authorization header if needed
                    'Authorization': 'Bearer ' + localStorage.getItem('jwt')
                },
                body: JSON.stringify({ password, username, email, householdName }),
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Update successful', data);
                    // Handle the UI update or redirection as needed
                })
                .catch(error => {
                    console.error('Update failed', error);
                    // Handle the UI error display
                });
        });
    });
});
