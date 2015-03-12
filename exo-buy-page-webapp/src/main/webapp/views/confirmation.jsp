<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Buy Page</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/assets/css/jquery-ui.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/assets/js/html5shiv.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/respond.min.js"></script>
    <![endif]-->

    <!-- Favicon -->
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-precomposed.png">
    <link rel="icon" href="${pageContext.request.contextPath}/assets/ico/favicon.ico">
</head>

<body>
<div class="container buy-page-container">
    <h2 class="TitleForm"><img class="logo" src="${pageContext.request.contextPath}/assets/img/logo-exo.png" alt="Logo Exo">Buy EXO Platform</h2>
    <div class="row">
        <c:choose>
            <c:when test="${plan != null}">
                <div class="col-md-8 col-sm-offset-2">
                    <br>
                    <p>We are happy to confirm that your transaction <b>${transactionId}</b> has been validated. You will reveive shortly your subscription information an your product key at <b>${customer_email}</b>.</p>
                    <br>
                    <table class="table table-striped">
                        <tr>
                            <td>TransactionID</td>
                            <td>${transactionId}</td>
                        </tr>
                        <tr>
                            <td>Plan</td>
                            <td>${plan.getName()}</td>
                        </tr>
                        <tr>
                            <td>Organisation</td>
                            <td>${customer_org}</td>
                        </tr>
                        <tr>
                            <td>Users</td>
                            <td>${plan.getOptionUser()}</td>
                        </tr>
                        <tr>
                            <td>Period</td>
                            <td>${plan.getPeriod()}</td>
                        </tr>
                        <c:forEach items="${plan.getAddons()}" var="addon">
                            <tr>
                                <td>
                                    addon
                                </td>
                                <td>
                                    ${addon.getName()}
                                </td>
                            </tr>
                        </c:forEach>
                        <c:forEach items="${plan.getServices()}" var="service">
                            <tr>
                                <td>
                                    Services
                                </td>
                                <td>
                                        ${service.getName()}
                                </td>
                            </tr>
                        </c:forEach>

                        <tr>
                            <td>
                                Discount
                            </td>
                            <td>${discount.getAmount()}</td>
                        </tr>
                        <tr>
                            <td>Total Amount</td>
                            <td>$ ${total}</td>
                        </tr>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                Sorry, there is no transaction
            </c:otherwise>
        </c:choose>    

    </div>
</div>
<!-- Javascript -->
<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
</body>

</html>