<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Buy Page</title>

    <!-- Bootstrap -->
    <link href="assets/css/jquery-ui.css" rel="stylesheet">
    <link href="assets/css/bootstrap-slider.min.css" rel="stylesheet">
    <link href="assets/css/style.css" rel="stylesheet">
    <link href="assets/css/creditcardjs-v0.10.12.min.css" rel="stylesheet">

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
        <div class="bp-content" id="billingForm">
            <div class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong>Warning!</strong> Better check yourself, you're not looking too good.
            </div>
            <div class="form-group">
                <input type="text" name="first_name" class="form-control" placeholder="First Name" value="Tuan Anh">
            </div>
            <div class="form-group">
                <input type="text" name="last_name" class="form-control" placeholder="Last Name" value="VU">
            </div>
            <div class="form-group">
                <input type="text" name="organisation" class="form-control" placeholder="Organisation" value="exo">
            </div>
            <div class="form-group">
                <input type="text" name="phone" class="form-control" placeholder="Phone" value="0123344555">
            </div>
            <div class="form-group">
                <input type="text" name="billing_email" class="form-control" placeholder="Billing Email" value="anhvt@exoplatform.com">
            </div>
            <div class="form-group">
                <div class="input-group">
                    <input type="text" name="product_code" class="form-control" placeholder="Product Code (not mandatory)" aria-describedby="sizing-addon2">
                    <span class="input-group-addon" id="sizing-addon2"><i class="fa fa-question-circle"></i></span>
                </div>
            </div>
        </div>
        <div class="bp-heading">
            <h3 class="title">Payment</h3>
            <span class="title-icon"><i class="fa fa-shopping-cart"></i></span>
        </div>
        <div class="bp-content">

            <!-- credit js -->
            <div class="ccjs-card">
                <div class="row">
                    <div class="ccjs-number form-group col-sm-8">
                        <input name="card-number" class="ccjs-number form-control" placeholder="Card Number">
                    </div>
                    <div class="ccjs-csc form-group col-sm-4">
                        <input name="csc" class="ccjs-csc form-control" placeholder="Security Code">
                        <button type="button" class="ccjs-csc-help"><i class="fa fa-question-circle"></i></button>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-7">
                        <div class="form-group ccjs-name">
                            <input name="name" class="ccjs-name form-control" placeholder="Name on Card">
                        </div>
                    </div>
                    <div class="col-sm-5">
                        <fieldset class="ccjs-expiration row">
                            <div class="col-md-4"><p class="form-control-static">Expiration</p></div>
                            <div class="col-sm-4 form-group">
                                <select name="month" class="ccjs-month form-control">
                                    <option selected disabled>MM</option>
                                    <option value="01">01</option>
                                    <option value="02">02</option>
                                    <option value="03">03</option>
                                    <option value="04">04</option>
                                    <option value="05">05</option>
                                    <option value="06">06</option>
                                    <option value="07">07</option>
                                    <option value="08">08</option>
                                    <option value="09">09</option>
                                    <option value="10">10</option>
                                    <option value="11">11</option>
                                    <option value="12">12</option>
                                </select>
                            </div>
                            <div class="col-sm-4 form-group">
                                <select name="year" class="ccjs-year form-control">
                                    <option selected disabled>YY</option>
                                    <option value="14">14</option>
                                    <option value="15">15</option>
                                    <option value="16">16</option>
                                    <option value="17">17</option>
                                    <option value="18">18</option>
                                    <option value="19">19</option>
                                    <option value="20">20</option>
                                    <option value="21">21</option>
                                    <option value="22">22</option>
                                    <option value="23">23</option>
                                    <option value="24">24</option>
                                </select>
                            </div>
                        </fieldset>
                    </div>
                </div>

                <div class="form-group">
                    <ul class="list-card-supported clearfix">
                        <li class="mastercard"><img src="assets/img/logoMasterCard.png" alt="logoMasterCard"></li>
                        <li class="visa"><img src="assets/img/logoVisa.png" alt="logoVisa"></li>
                        <li class="discover"><img src="assets/img/logoDiscover.png" alt="logoDiscover"></li>
                        <li class="american"><img src="assets/img/LogoAmericanExpress.png" alt="LogoAmericanExpress"></li>
                        <li class="dinersclub"><img src="assets/img/logoDinersClub.png" alt="logoDinersClub"></li>
                        <li class="jcb"><img src="assets/img/logoJCB.png" alt="logoJCB"></li>
                    </ul>
                </div>

                <div class="text-right hide">
                    <select name="card-type" class="ccjs-hidden-card-type form-control">
                        <option value="amex" class="ccjs-amex">American Express</option>
                        <option value="discover" class="ccjs-discover">Discover</option>
                        <option value="mastercard" class="ccjs-mastercard">MasterCard</option>
                        <option value="visa" class="ccjs-visa">Visa</option>
                        <option value="diners-club" class="ccjs-diners-club">Diners Club</option>
                        <option value="jcb" class="ccjs-jcb">JCB</option>
                        <!--<option value="laser" class="laser">Laser</option>-->
                        <!--<option value="maestro" class="maestro">Maestro</option>-->
                        <!--<option value="unionpay" class="unionpay">UnionPay</option>-->
                        <!--<option value="visa-electron" class="visa-electron">Visa Electron</option>-->
                        <!--<option value="dankort" class="dankort">Dankort</option>-->
                    </select>
                </div>

                <div class="form-group">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox"> I have read and agreed to the <a href="#">terms and conditions</a>.
                        </label>
                    </div>
                </div>
                <br>
                <br>
                <button class="btn btn-primary btn-lg">Confirm purchase</button>

            </div>
            <!-- credit js -->
            <button class="btn subscribe">Subscribe</button>
        </div>
    </div>
    <div class="col-md-4 buypage-bill">

    </div>
</div>
</div>

<!-- Javascript -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/bootstrap.custom.js"></script>
<script src="assets/js/bootstrap-slider.min.js"></script>
<script src="assets/js/buypage.js"></script>
<script src="assets/js/creditcardjs-v0.10.12.min.js"></script>
<script language="javascript">
    $(document).ready(function () {
        BuyPage.init();
    });
</script>
</body>

</html>