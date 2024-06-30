document.addEventListener('DOMContentLoaded', (event) => {
    const loginForm = document.getElementById('login-form');

    loginForm.addEventListener('submit', async function (e) {
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

        try {
            const response = await fetch(apiEndpoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(loginData)
            });

            if (!response.ok) {
                console.error(`HTTP error! status: ${response.status}`);
                alert('Login failed. Please try again.');
                return;            }

            const data = await response.text();
            console.log('Login successful', data);

            // Extract the token from the response -> store & redirect
            const tokenMatch = data.match(/Token:(.*)$/);
            if (tokenMatch) {
                const token = tokenMatch[1].trim();
                localStorage.setItem('jwt', token);
                window.location.href = '/index.html';
            } else {
                throw new Error('Token not found in response');
            }
        } catch (error) {
            console.error('Login failed', error);
            alert('Login failed. Please try again.');
        }
    });
});
