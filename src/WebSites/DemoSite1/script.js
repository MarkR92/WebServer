// BELOW IS USING FETCH API
const formelement = document.getElementById('loginForm');

formelement.addEventListener('submit', event => {
    event.preventDefault();

    const formData = new FormData(formelement);
    const data = Object.fromEntries(formData);

    fetch('http://localhost:8080', { //'https://reqres.in/api/users' http://localhost:8080
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => res.json())
        .then(data => console.log(data)) 
        .catch(error => console.log(error));
});


function showLogin() {
    document.getElementById("registrationSection").style.display = "none";
    document.getElementById("loginSection").style.display = "block";
}

function showRegistration() {
    document.getElementById("registrationSection").style.display = "block";
    document.getElementById("loginSection").style.display = "none";
}