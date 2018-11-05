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