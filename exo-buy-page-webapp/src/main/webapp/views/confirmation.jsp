<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
            <h2 class="TitleForm"><img class="logo" src="${pageContext.request.contextPath}/assets/img/logo-exo.png" alt="Logo Exo">Congratulations!</h2>
            <div class="row">
                <c:choose>
                    <c:when test="${plan != null}">
                        <div class="col-md-8 col-sm-offset-2">
                            <br>
                            <p>We are happy to confirm that your transaction <b>${transactionId}</b> has been validated.</p><br/>
                            <p>You will reveive shortly your subscription information an your product key at <b>${customer_email}</b>.</p>
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
                                <c:if test="${plan.getAddons().size() > 0}">
                                    <tr>
                                        <td>
                                            Add-ons
                                        </td>
                                        <td>
                                            <c:forEach items="${plan.getAddons()}" var="addon">
                                                <c:set var="i" value="${i+1}"></c:set>
                                                ${addon.getName()}<c:if test="${i < plan.getAddons().size()}">,</c:if>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${plan.getServices().size() > 0}">
                                    <tr>
                                        <td>
                                            Services
                                        </td>
                                        <td>
                                            <c:forEach items="${plan.getServices()}" var="service">
                                                <c:set var="i" value="${i+1}"></c:set>
                                                ${service.getName()}
                                                <c:if test="${i < plan.getServices().size()}">,</c:if>
                                            </c:forEach>
                                        </td>
                                    </tr>

                                </c:if>
                                <c:if test="${discount != null}">
                                    <tr>
                                        <td>
                                            Discount
                                        </td>
                                        <td>${discount.getName()} (<small>${discount.getDescription()}</small>)</td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td>Total Amount</td>
                                    <td>$ ${total}</td>
                                </tr>
                            </table>
                            <a class="btn btn-default" href="/buy">Close</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        Sorry, there is no transaction
                        <br>
                        <a class="btn btn-default" href="/buy">Close</a>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </div><!-- .wrapper -->
    </br>
    <footer id="footer">
        Copyright &copy;2000-2015. All rights reserved. <span><a href="http://www.exoplatform.com/" target="_blank">eXo Platform SAS</a><a href="http://www.exoplatform.com/terms-conditions" target="_blank">Terms and Condition</a><a href="http://www.exoplatform.com/company/en/privacy" target="_blank">Privacy Policy</a></span>
    </footer>

<!-- Javascript -->
<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
</body>

</html>