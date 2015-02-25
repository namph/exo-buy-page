<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Buy Page</title>

    <!-- Bootstrap -->
    <link href="assets/css/jquery-ui.css" rel="stylesheet">
    <link href="assets/css/style.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="assets/js/html5shiv.min.js"></script>
      <script src="assets/js/respond.min.js"></script>
    <![endif]-->

    <!-- Favicon -->
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-precomposed.png">
    <link rel="icon" href="assets/ico/favicon.ico">
</head>

<body>
<div class="container buy-page-container">
<h2 class="TitleForm"><img class="logo" src="assets/img/logo-exo.png" alt="Logo Exo">Buy EXO Platform</h2>
<div class="row">
    <div class="col-md-8">
        <div class="bp-heading">
            <h3 class="title">Enterprise plans</h3>
            <span class="title-icon"><i class="fa fa-th-large"></i></span>
        </div>
        <div class="bp-content">

            <div class="intro-plan-box" role="tabpanel">
                <!-- list plans here -->
            </div>

            <div class="boxSlider">
                <!-- <div class="percentage" id="progress"><strong>5 users</strong></div> -->
                <div id="slider" class="ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all" aria-disabled="false">
                    <div class="ui-slider-range ui-widget-header ui-corner-all" style="left: 0%; width: 0%;"></div>
                    <a href="#" class="ui-slider-handle ui-state-default ui-corner-all" style="left: 0%;">
                        <span class="countValue" >15 users</span><!-- margin-left = - (width / 2)  -->
                    </a>
                    <input type="hidden" value="215" name="slider-value" id="slider-value">
                </div>
            </div>
            <div class="boxSlider">
                <!-- <div class="percentage" id="progress">
                    <strong>161 users</strong>
                </div> -->
                <div id="slider" class="ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all" aria-disabled="false">
                    <div class="ui-slider-range ui-widget-header ui-corner-all" style="left: 0%; width: 31.5152%;"></div>
                    <a href="#" class="ui-slider-handle ui-state-default ui-corner-all" style="left: 0%;">
                        <span class="countValue" >500 users</span><!-- margin-left = - (width / 2)  -->
                    </a>
                    <a href="#" class="ui-slider-handle ui-state-default ui-corner-all" style="left: 31.5152%;">
                        <span class="countValue" >15 users</span><!-- margin-left = - (width / 2)  -->
                    </a>
                </div>
                <input type="hidden" value="161" name="slider-value" id="slider-value">
            </div>
        </div>

        <div class="bp-heading">
            <h3 class="title">Add-ons</h3>
            <span class="title-icon"><i class="fa fa-plus-square-o"></i></span>
        </div>
        <div class="bp-content">
            <p>Add powerful add-on to your Social Intranet</p>
            <br>
            <!-- User .uiCloudCardSelect with Grid System. You can not use with Grid System like Services section below  -->
            <div class="row">
                <div class="col-md-4">
                    <div class="uiCloudCardSelect">
                        <span class="iconSelect"><i class="fa fa-check"></i></span>
                        <div class="inner" data-toggle="dropdown">
                            <h4><i class="fa fa-video-camera"></i> Video Call</h4>
                        </div>
                        <div class="dropdown-box">
                            <p>This Add-on includes: </p>
                            <div class="checkbox"><label><input type="checkbox"> license for 300 user</label></div>
                            <div class="checkbox"><label><input type="checkbox"> 15 support tickets included</label></div>
                            <div class="checkbox"><label><input type="checkbox"> Software Maintenace</label></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="bp-heading">
            <h3 class="title">Services</h3>
            <span class="title-icon"><i class="fa fa-cog"></i></span>
        </div>
        <div class="bp-content">
            <p>Add powerful add-on to your Social Intranet</p>
            <br>
            <div class="uiCloudCardSelect" >
                <span class="iconSelect"><i class="fa fa-check"></i></span>
                <div class="inner" data-toggle="dropdown">
                    <h4><i class="fa fa-home"></i> Starting Park</h4>
                </div>
            </div>
            <div class="uiCloudCardSelect selected">
                <span class="iconSelect"><i class="fa fa-check"></i></span>
                <div class="inner">
                    <h4><i class="fa fa-smile-o"></i> 10 Support tickets</h4>
                </div>
                <div class="dropdown-box" data-toggle="dropdown">
                    <p>This Add-on includes: </p>
                    <div class="checkbox"><label><input type="checkbox"> license for 300 user</label></div>
                    <div class="checkbox"><label><input type="checkbox"> 15 support tickets included</label></div>
                    <div class="checkbox"><label><input type="checkbox"> Software Maintenace</label></div>
                </div>
            </div>
        </div>
        <div class="bp-heading">
            <h3 class="title">Discount</h3>
            <span class="title-icon"><i class="fa fa-cut"></i></span>
        </div>
        <div class="bp-content">
            <p>Do you have any coupon code ? user it here.</p>
            <div class="input-group col-md-8">
                <input type="text" class="form-control" placeholder="Coupon">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button">Add Coupon</button>
                        </span>
            </div>
        </div>
        <div class="bp-heading">
            <h3 class="title">Billing</h3>
            <span class="title-icon"><i class="fa fa-file-text-o"></i></span>
        </div>
        <div class="bp-content">
            <div class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong>Warning!</strong> Better check yourself, you're not looking too good.
            </div>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="First Name">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Last Name">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Organisation">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Phone">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Billing Email">
            </div>
            <div class="form-group">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Product Code (not mandatory)" aria-describedby="sizing-addon2">
                    <span class="input-group-addon" id="sizing-addon2"><i class="fa fa-question-circle"></i></span>
                </div>
            </div>
        </div>
        <div class="bp-heading">
            <h3 class="title">Payment</h3>
            <span class="title-icon"><i class="fa fa-shopping-cart"></i></span>
        </div>
        <div class="bp-content">
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
                proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
        </div>
    </div>
    <div class="col-md-4">
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
            tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
            quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
            consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
            cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
            proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
    </div>
</div>
</div>

<!-- Javascript -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/bootstrap.custom.js"></script>
<script src="assets/js/buypage.js"></script>
</body>

</html>