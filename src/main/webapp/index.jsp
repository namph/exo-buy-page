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
            holder<input type="text" name="card_holder" id="cardHolder" value="Tuan Anh VU"  />
            number <input type="text" name="card_number" id="cardNumber" value="4111 1111 1111 1111" />
            <select  name="month" id="expireMonth">
                <% for(int i=1;i<13;i++){%>
                <option value="<%=i%>"><%=i%></option>
                <% } %>
            </select>
            <select  name="yeah" id="expireYear">
                <% for(int i=2015;i<2030;i++){%>
                <option value="<%=i%>"><%=i%></option>
                <% } %>
            </select>
            CVV <input type="text" name="card_cvv" id = "cardCVV" value="123"/>
            <br/>
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
<script language="javascript">
    $(document).ready(function () {
        BuyPage.init();
    });
</script>
</body>

</html>