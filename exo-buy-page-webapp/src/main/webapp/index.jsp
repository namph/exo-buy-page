<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Buy eXo Platform</title>

    <!-- Bootstrap -->
    <link href="assets/css/jquery-ui.css" rel="stylesheet">
    <link href="assets/css/bootstrap-slider.min.css" rel="stylesheet">
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

<!-- Google Tag Manager -->
<noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-KFFJLW"
                  height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
        new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
        j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
        '//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
})(window,document,'script','dataLayer','GTM-KFFJLW');</script>
<!-- End Google Tag Manager -->

    <div class="wrapper">
        <div class="container buy-page-container">
            <h2 class="TitleForm"><img class="logo" src="assets/img/logo-exo.png" alt="eXo Logo">Buy eXo Platform</h2>
            <!-- message callback -->
            <div class="alert alert-dismissible" id="buypage-alert-general" role="alert" style="display: none">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong>Warning!</strong>Something went wrong, please try again.
            </div>
            <div class="row">
                <div class="col-md-8">
                    <div class="bp-heading">
                        <h3 class="title">Enterprise plans</h3>
                        <span class="title-icon"><i class="uiIcon28BPEnterprisePlans"></i></span>
                    </div>
                    <!-- list plan here -->
                    <div class="bp-content buypage-plans">
                        <div class="text-center"><i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom"></i></div>
                    </div>
                    <!-- list addons here -->
                    <div class="buypage-addons">

                    </div>
                    <div class="bp-heading">
                        <h3 class="title">Discount</h3>
                        <span class="title-icon"><i class="uiIcon28BPDiscount"></i></span>
                    </div>
                    <div class="bp-content">
                        <div class="text-center coupon-loading" style="display: none;"><i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom"></i></div>
                        <!-- message callback -->
                        <div class="alert alert-dismissible" id="buypage-alert-coupon" role="alert" style="display: none">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <div></div>
                        </div>
                        <p>Do you have any coupon code ? user it here.</p>
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Coupon" id="discountId">
                                <span class="input-group-btn">
                                    <button class="btn btn-primary" id="btnSubmitDiscount" type="button">Add Coupon</button>
                                </span>
                        </div>
                    </div>
                    <div class="bp-heading">
                        <h3 class="title">Billing</h3>
                        <span class="title-icon"><i class="uiIcon28BPBilling"></i></span>
                    </div>

                    <div class="bp-content" id="billingForm">
                        <!-- message callback -->
                        <div class="alert alert-warning" id="buypage-alert-billing" style="display: none;">
                        </div>
                        <div class="form-group required ">
                            <input type="text" id="first_name" name="first_name" class="form-control" placeholder="First Name" value="">
                        </div>
                        <div class="form-group required ">
                            <input type="text" id="last_name" name="last_name" class="form-control" placeholder="Last Name" value="">
                        </div>
                        <div class="form-group required ">
                            <input type="text" id="organisation" name="organisation" class="form-control" placeholder="Organisation" value="">
                        </div>
                        <div class="form-group required ">
                            <input type="text" id="phone" name="phone" class="form-control" placeholder="Phone" value="">
                        </div>
                        <div class="form-group required ">
                            <input type="text" id="billing_email" name="billing_email" class="form-control" placeholder="Billing Email" value="">
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <input type="text" id="product_code" class="form-control" placeholder="Product Code (not mandatory)" aria-describedby="sizing-addon2">
                                        <span class="input-group-btn popover-manual-toggle" data-toggle="dropdown" id="sizing-addon2">
                                            <div role="tooltip" class="popover fade in">
                                                <div class="arrow"></div>
                                                <div class="popover-content">
                                                    <h5 class="bold">Product Code</h5 class="bold">
                                                    <p>The product code is a unique code that identifies one eXo Platform Enterprise instance. For instance:</p>
                                                    <p><strong>B33@SF20D4KF@20S</strong></p>
                                                    <p>If you have installed eXo Platform already, find your product code by clicking on the banner "buy a subscription".</p>
                                                    <p>If not, No Big Deal!</p>
                                                    <p>We will send it to you directly with your unlock key.</p>
                                                </div>
                                            </div>
                                            <button class="btn btn-default" type="button"><i class="fa fa-question-circle"></i></button>
                                        </span>
                            </div>
                        </div>
                    </div>
                    <div class="bp-heading">
                        <h3 class="title">Payment</h3>
                                <span class="title-icon">
                                    <i class="uiIcon28BPPayment"></i>
                                </span>
                    </div>
                    <div class="bp-content">
                        <!-- message callback -->
                        <div class="alert alert-dismissible" id="buypage-alert-credit" role="alert" style="display: none">
                            <div></div>
                        </div>
                        <!-- credit js -->

                        <div class="ccjs-card">
                            <div class="row">
                                <div class="ccjs-number form-group col-sm-8">
                                    <input name="card-number" id="cardNumber" class="ccjs-number form-control" placeholder="Card Number">
                                </div>
                                <div class="ccjs-csc form-group col-sm-4">
                                    <div class="input-group">
                                        <input name="csc" id="cardCVV" class="ccjs-csc form-control" placeholder="Security Code">
                                                <span class="input-group-btn">
                                                    <button type="button" class="btn btn-default ccjs-csc-help"><i class="fa fa-question-circle"></i></button>
                                                </span>
                                    </div><!-- /input-group -->
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group ccjs-name">
                                        <input name="name" id="cardHolder" class="ccjs-name form-control" placeholder="Name on Card">
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <fieldset class="ccjs-expiration row">
                                        <div class="col-sm-4"><p class="form-control-static text-right md">Expiration:</p></div>
                                        <div class="col-sm-4 col-xs-6 form-group">
                                            <select name="month" id="expireMonth" class="ccjs-month form-control">
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
                                        <div class="col-sm-4  col-xs-6 form-group">
                                            <select name="year" id="expireYear" class="ccjs-year form-control">
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
                                        <input type="checkbox" id="termandcondition"> I have read and agreed to the <a href="http://www.exoplatform.com/terms-conditions" target="_blank">terms and conditions</a>.
                                    </label>
                                </div>
                            </div>
                            <br>
                            <button class="btn btn-primary btn-lg subscribe">Confirm purchase</button>
                            <!-- credit js -->
                        </div>
                    </div>
                </div>
                <div class="col-md-4 buypage-bill">
                    <div class="text-center"><i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom"></i></div>
                </div>
            </div>
        </div>
        </div>
        <div id="BuyPageLoadingContainer" style="display: none">
            <div class="text-center"><i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom"></i></div>
        </div>
        <div class="loading-all">
            <i class="fa fa-spinner fa-spin"></i>
        </div>
    </div><!-- .wrapper -->
    </br>
    <footer id="footer">
        Copyright &copy;2000-2015. All rights reserved. <span><a href="http://www.exoplatform.com/" target="_blank">eXo Platform SAS</a><a href="http://www.exoplatform.com/terms-conditions" target="_blank">Terms and Condition</a><a href="http://www.exoplatform.com/company/en/privacy" target="_blank">Privacy Policy</a></span>
    </footer>

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