document.addEventListener('DOMContentLoaded', (event) => {
    const loginForm = document.getElementById('login-form');

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        if (!username) {
            alert("Username is required!");
            return;
        }

        if (!password) {
            alert("Password is required.");
            return;
        }

        const loginData = {
            username: username,
            password: password
        };

        // Placeholder for API endpoint
        const apiEndpoint = 'User/login';

        fetch(apiEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({loginData}),
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
                window.location.href = '/pantry.html';
            })
            .catch(error => {
                console.error('Login failed', error);
                alert('Login failed. Please try again.');
            });
    });
});
