document.addEventListener('DOMContentLoaded', () => {
    // Load the default home page content
    loadPage('home.html');
    updateNavForAuth();
});

function loadPage(page) {
    fetch(page)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.text();
        })
        .then(html => {
            document.getElementById('main-content').innerHTML = html;
        })
        .catch(error => {
            console.error('Error loading page:', error);
            document.getElementById('main-content').innerHTML = '<p>Sorry, the page could not be loaded.</p>';
        });
}
function updateNavForAuth() {
    const isLoggedIn = Boolean(localStorage.getItem('jwt'));
    document.querySelectorAll('.nav-menu ul li[id^="logged-out"]').forEach(li => {
        li.style.display = isLoggedIn ? 'block' : 'none';
    });
    const loginButton = document.querySelector('.login-button');
    loginButton.textContent = isLoggedIn ? 'Logout' : 'Login';
    loginButton.onclick = isLoggedIn ? logout : login;
}

function login() {
    document.getElementById('id01').style.display = 'block';
}

async function logout() {
    // Fetch API to send a POST request to the server
    try {
        const response = await fetch('/User/logout', {
            method: 'POST',
            // Include the JWT in the Authorization header
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwt')
            }
        });
        if (!response.ok) throw new Error('Logout failed');

        // Successful logged out -> removes token
        localStorage.removeItem('jwt');

        // update & redirects to homepage w/ message
        updateNavForAuth();
        loadPage('home.html');  // Redirect to home.html
        alert('Logged out successfully.');

        return { success: true };

    } catch (error) {
        console.error('Logout failed', error);
        alert('Logout failed. Please try again.');
        return { success: false };
    }
}

