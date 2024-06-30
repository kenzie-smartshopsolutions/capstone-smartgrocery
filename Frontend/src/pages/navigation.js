document.addEventListener('DOMContentLoaded', () => {
    // Load the default home page content
    loadPage('home.html');
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
