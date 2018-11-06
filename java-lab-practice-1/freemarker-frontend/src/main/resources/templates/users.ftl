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
    <div>
        <form method="post" action="/login">
            <div class="form-group">
                <input id="login" type="text" name="login" placeholder="login" class="form-control">
            </div>
            <div class="form-group">
                <div class="row">
                    <div class="col-md-12">
                        <button id="bun-btn" onclick="banUser()" class="btn btn-success form-control" type="button">Ban</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div id="users">
    </div>

    <script>
        getAllUsers();

        function getAllUsers() {
            $.ajax({
                url: 'http://localhost:8080/users',
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    showUsers(data);
                },
                error: function () {
                    console.log('getAllUsers method produced the error!');
                }
            });
        }

        function banUser() {
            var login = $('#login').val();
            console.log(login);
            $.ajax({
                url: 'http://localhost:8080/users/ban/' + login,
                type: 'GET',
                dataType: 'json',
                success: function () {
                    console.log("SUCCESS!11!!!!!");
                },
                error: function () {
                    console.log('banUser method produced the error!');
                }
            });
        }

        function showUsers(data) {
            console.log(data);
            var users = data;
            $('#users').html('');
            for (var i = 0; i < users.length; i++) {
                var user = users[i];
                $('#users').append(
                        '<div class="container">' +
                            '<h2>' +
                                user.firstName + ' ' + user.lastName +
                            '</h2>' +
                            '<img src="' + user.photoUrl + '">' +
                        '</div>'
                );
            }
        }
    </script>
</body>
</html>