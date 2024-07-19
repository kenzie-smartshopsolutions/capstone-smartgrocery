function isLoggedIn() {
    return Boolean(localStorage.getItem('jwt'));
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
