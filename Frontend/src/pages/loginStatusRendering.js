
function loggedIn() {

}

function loggedOut() {

}

function updateRendering() {

    if(isLoggedOut) {
        const element = document.getElementById("logged-out1");
        element.remove();

        const element = document.getElementById("logged-out2");
        element.remove();

        const element = document.getElementById("logged-out3");
        element.remove();

        const element = document.getElementById("logged-out4");
        element.remove();

        const element = document.getElementById("logged-out5");
        element.remove();
    }

    if(isLoggedIn) {
        let element = document.getElementById("id01").innerHTML;
        document.getElementById("id01").innerHTML = element.replace("
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
        ");
    }
}