document.addEventListener('DOMContentLoaded', (event) => {
    const registrationForm = document.getElementById('registration-form');

    registrationForm.addEventListener('submit', async function (e) {
        e.preventDefault();

        const email = document.getElementById('email').value;
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const householdName = document.getElementById('householdName').value;

        if (password !== confirmPassword) {
            alert("Passwords do not match.");
            return;
        }

        if (!email || !username || !password) {
            alert("Email, username, and password are required.");
            return;
        }

        const userData = {
            email: email,
            username: username,
            password: password,
            householdName: householdName
        };

        // Placeholder for API endpoint
        const apiEndpoint = '/User/register';

        try {
            const response = await fetch(apiEndpoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userData)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            console.log('Registration successful', data);
            window.location.href = '/index.html';
            alert('Registration successful. Please login.');
        } catch (error) {
            console.error('Registration failed', error);
        }
    });
});
