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
        <!-- list plan here -->
        <div class="bp-content buypage-plans">

        </div>
        <!-- list addons here -->
        <div class="buypage-addons">

        </div>
        <div class="bp-heading">
            <h3 class="title">Discount</h3>
            <span class="title-icon"><i class="fa fa-cut"></i></span>
        </div>
        <div class="bp-content">
            <p>Do you have any coupon code ? user it here.</p>
            <div class="input-group col-md-8">
                <input type="text" class="form-control" placeholder="Coupon" id="discountId">
                    <span class="input-group-btn">
                        <button class="btn btn-default" id="btnSubmitDiscount" type="button">Add Coupon</button>
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
        <div class="panel panel-primary order-box" data-spy="affix" data-offset-top="110" data-offset-bottom="200">
            <div class="panel-heading">
                <h4>Your Order<br><small><a class="exo-icon-restore" href="#"></a> 02/20/2015 - 02/20/2016</small></h4>
            </div>
            <div class="panel-body">
                <h5 class="price-head">Exo Platform Entrerprise</h5>
                <div class="price-row">
                    <div class="row">
                        <div class="col-xs-7 price-label">
                            1 Year Plan<br><strong>10</strong> users
                        </div>
                        <div class="col-xs-5 text-right">
                            <span class="price">$4,000.00</span>
                        </div>
                    </div>
                    <a href="#" class="fa fa-trash"></a>
                </div>
                <h5 class="price-head">Add-ons</h5>
                <div class="price-row">
                    <div class="row">
                        <div class="col-xs-7 price-label">
                            Video Call<br>Up to 100 users
                        </div>
                        <div class="col-xs-5 text-right">
                            <span class="price link-color">$1,600.00</span>
                        </div>
                    </div>
                    <a href="#" class="fa fa-trash"></a>
                </div>
                <h5 class="price-head">Service</h5>
                <div class="price-row">
                    <div class="row">
                        <div class="col-xs-7 price-label">
                            10 Support Tickets
                        </div>
                        <div class="col-xs-5 text-right">
                            <span class="price link-color">$6,200.00</span>
                        </div>
                    </div>
                    <a href="#" class="fa fa-trash"></a>
                </div>
            </div>
            <div class="panel-footer">
                <div class="row">
                    <div class="col-xs-6 total-text">Total</div>
                    <div class="col-xs-6 total-price"><span class="price">$10,800.00</span></div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<!-- Javascript -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/bootstrap.custom.js"></script>
<script src="assets/js/buypage.js"></script>
<script language="javascript">
    $(document).ready(function () {
        BuyPage.init();
    });
</script>
</body>

</html>