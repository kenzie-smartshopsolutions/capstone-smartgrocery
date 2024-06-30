function isLoggedIn() {
    return Boolean(localStorage.getItem('jwt'));
}
function loggedIn() {
    localStorage.setItem('jwt', token);
    updateRendering();
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
        updateRendering();
        window.location.href = 'index.html';
        alert('Logged out successfully.');

        return { success: true };

    } catch (error) {
        console.error('Logout failed', error);
        alert('Logout failed. Please try again.');
        return { success: false };
    }
}

function updateRendering() {
    const logoutElements = [1, 2, 3, 4, 5].map(
        (element) => document.getElementById(`logged-out${element}`)
    );
    if (!isLoggedIn) {
        logoutElements.forEach((element) => {
            if (element) element.style.display = "none";
        });
    } else {
        logoutElements.forEach((element) => {
            if (element) element.style.display = "block";
        });
    }

    if(isLoggedIn) {
        let element = document.getElementById("id01");
        if (element) element.innerHTML = `
        <div id="id01" class="modal">
            <form class="modal-content animate" action="/action_page.php" method="post">
                <div class="imgcontainer">
                    <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
                    <img src="img/user.png" alt="Avatar" class="avatar">
                </div>
                <div class="container">
                    <button class="login-popup-button" type="submit">Logoff</button>
                </div>
                <div class="container" style="background-color:#f1f1f1">
                    <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn login-popup-button">Cancel</button>
                </div>
            </form>
        </div>
        `;
    }
}

document.addEventListener('DOMContentLoaded', updateRendering);
