document.addEventListener('DOMContentLoaded', (event) => {
    const loginForm = document.getElementById('login-form');

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        // Placeholder for API endpoint
        const apiEndpoint = 'User/login';

        fetch(apiEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log('Login successful', data);
                // Redirect to a different page or something
            })
            .catch(error => {
                console.error('Login failed', error);
            });
    });
});
