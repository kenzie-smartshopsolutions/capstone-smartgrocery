document.addEventListener('DOMContentLoaded', (event) => {
    const loginForm = document.getElementById('login-form');

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        const userId = document.getElementById('userId').value;
        const email = document.getElementById('email').value;
        const householdName = document.getElementById('householdName').value;
        const accountNonLocked = document.getElementById('accountNonLocked').value;
        const failedLoginAttempts = document.getElementById('failedLoginAttempts').value;

        // Placeholder for API endpoint
        const apiEndpoint = 'User/login';

        fetch(apiEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ userId, username, password, householdName, email, accountNonLocked, failedLoginAttempts}),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log('Login successful', data);
                localStorage.setItem('jwt', data.token);
                location.reload();
            })
            .catch(error => {
                console.error('Login failed', error);
            });
    });
});
