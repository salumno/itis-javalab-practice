var userUpdatesWs;

function connect(login) {
    var wsUrl = "ws://localhost:8080/userUpdates/" + login;
    userUpdatesWs = new WebSocket(wsUrl);
    userUpdatesWs.onmessage = function(event) {
        console.log(event);
        console.log(login);
    };
}