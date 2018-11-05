<html>
<head>
    <!-- Bootstrap core CSS -->
    <link href="/mdbootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Material Design Bootstrap -->
    <link href="/mdbootstrap/css/mdb.min.css" rel="stylesheet">

    <!-- SCRIPTS -->
    <!-- JQuery -->
    <script type="text/javascript" src="/mdbootstrap/js/jquery-3.2.1.min.js"></script>
    <!-- Bootstrap tooltips -->
    <script type="text/javascript" src="/mdbootstrap/js/popper.min.js"></script>
    <!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="/mdbootstrap/js/bootstrap.min.js"></script>
    <!-- MDB core JavaScript -->
    <script type="text/javascript" src="/mdbootstrap/js/mdb.min.js"></script>
    <#--<script type="text/javascript" src="/js/user-updates-websocket.js"></script>-->
</head>
<body class="container-fluid">
<div id="notification"></div>
<div>
    <div>
        <form id="form">
            <div class="form-group">
                <input id="firstName" type="text" name="firstName" placeholder="First Name" class="form-control">
            </div>
            <div class="form-group">
                <input id="lastName" type="text" name="lastName" placeholder="Last Name" class="form-control">
            </div>
            <div class="form-group">
                <input id="login" type="text" name="login" placeholder="Login" class="form-control">
            </div>
            <div class="form-group">
                <input id="password" type="password" name="password" placeholder="Password" class="form-control">
            </div>
            <div id="image" class="form-group">
            </div>
            <div class="form-group">
                <div class="row">
                    <div class="col-md-12">
                        <button id="submit-btn" onclick="registerUser()" class="btn btn-success form-control" type="button">Sign Up</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div id="users">
    </div>
</div>
</body>
<script>
    function registerUser() {
        var login = $('#login').val();
        var data = JSON.stringify({
            "firstName": $('#firstName').val(),
            "lastName": $('#lastName').val(),
            "login": login,
            "password": $('#password').val()
        });
        connect(login);
        $.ajax({
            url: 'http://localhost:8080/sign-up',
            type: 'POST',
            data: data,
            dataType: 'json',
            contentType: "application/json"
        });
    }

    function showSignUpNotification(login, photoUrl) {
        var notificationElement  = $('#notification');
        notificationElement.html('');
        notificationElement.append(
            '<div>' +
                '<label for="userPhoto">Registration completed successfully! Hello, ' + login + '</label>' +
                '<img id="userPhoto" src="' + photoUrl + '" width="300" height="300">' +
            '</div>'
        );
    }

    function connect(login) {
        var userUpdatesWs;
        var wsUrl = "ws://localhost:8080/userUpdates/" + login;
        userUpdatesWs = new WebSocket(wsUrl);
        userUpdatesWs.onmessage = function(event) {
            showSignUpNotification(login, event.data)
        };
    }

</script>
</html>