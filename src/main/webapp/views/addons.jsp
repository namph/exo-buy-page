<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            <c:forEach items="${addons}" var="addon">

                <div class="uiCloudCardSelect">
                    <span class="iconSelect"><i class="fa fa-check"></i></span>
                    <div class="inner" data-toggle="dropdown">
                        <h4><i class="fa fa-video-camera"></i>${addon.getName()}</h4>
                    </div>
                    <div class="dropdown-box">
                        <p>This Add-on includes: </p>
                        <div class="checkbox"><label><input type="checkbox">${addon.getDescription()}</label></div>
                    </div>
                </div>

            </c:forEach>

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

    <c:forEach items="${services}" var="service">

        <div class="uiCloudCardSelect">
            <span class="iconSelect"><i class="fa fa-check"></i></span>
            <div class="inner" data-toggle="dropdown">
                <h4><i class="fa fa-video-camera"></i>${service.getName()}</h4>
            </div>
            <div class="dropdown-box">
                <p>This Add-on includes: </p>
                <div class="checkbox"><label><input type="checkbox">${service.getDescription()}</label></div>
            </div>
        </div>

    </c:forEach>

</div>