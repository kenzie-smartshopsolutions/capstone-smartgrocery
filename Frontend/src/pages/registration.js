document.addEventListener('DOMContentLoaded', (event) => {
    const registrationForm = document.getElementById('registration-form');

    registrationForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const email = document.getElementById('email').value;
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        // Placeholder for API endpoint
        const apiEndpoint = '/register';

        fetch(apiEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, username, password }),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                // Handle registration success
                console.log('Registration successful', data);
                // Redirect to login page or other action
            })
            .catch(error => {
                // Handle errors, such as displaying a registration failed message
                console.error('Registration failed', error);
            });
    });
});
