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
<body>
<div>
    <div>
        <form method="post" action="/login">
            <div class="form-group">
                <input id="login" type="text" name="login" placeholder="login" class="form-control">
            </div>
            <div class="form-group">
                <input id="password" type="password" name="password" placeholder="Password" class="form-control">
            </div>
            <div class="form-group">
                <div class="row">
                    <div class="col-md-12">
                        <button id="submit-btn" class="btn btn-success form-control" type="submit">Sign In</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>