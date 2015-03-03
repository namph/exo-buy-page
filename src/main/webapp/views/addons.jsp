<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${addons.size() > 0}">
<div class="bp-heading">
    <h3 class="title">Add-ons</h3>
    <span class="title-icon"><i class="fa fa-plus-square-o"></i></span>
</div>

<div class="bp-content">

    <p>Add powerful add-on to your Social Intranet</p>
    <br>
    <!-- User .uiCloudCardSelect with Grid System. You can not use with Grid System like Services section below  -->
    <div class="row">

        <c:forEach items="${addons}" var="addon">
            <c:set var="id" value="${addon.getId()}"></c:set>
            <c:set var="name" value="${addon.getName()}"></c:set>
            <c:set var="description" value="${addon.getDescription()}"></c:set>
            <c:set var="price" value="${addon.getPrice()}"></c:set>
            <div class="col-md-4">
                <div class="uiCloudCardSelect" id="${id}">
                    <span class="iconSelect"><i class="fa fa-check"></i></span>
                    <div class="inner addonItem" data-toggle="dropdown" data-name="${name}" data-price="${price}" data-id="${id}">
                        <h4><i class="fa fa-video-camera"></i>${name}</h4>
                    </div>
                    <div class="dropdown-box">
                        <p>This Add-on includes: </p>
                        <div class="checkbox"><label><input type="checkbox">${description}</label></div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</c:if>
<c:if test="${services.size() > 0}">
<div class="bp-heading">
    <h3 class="title">Services</h3>
    <span class="title-icon"><i class="fa fa-cog"></i></span>
</div>
<div class="bp-content">
    <p>Add services to your Social Intranet</p>
    <br>
    <div class="row">
        <c:forEach items="${services}" var="service">

            <c:set var="id" value="${service.getId()}"></c:set>
            <c:set var="name" value="${service.getName()}"></c:set>
            <c:set var="description" value="${service.getDescription()}"></c:set>
            <c:set var="price" value="${service.getPrice()}"></c:set>
            <div class="col-md-4">
                <div class="uiCloudCardSelect" id="${id}">
                    <span class="iconSelect"><i class="fa fa-check"></i></span>
                    <div class="inner serviceItem" data-toggle="dropdown" data-name="${name}" data-price="${price}" data-id="${id}">
                        <h4><i class="fa fa-video-camera"></i>${name}</h4>
                    </div>
                    <div class="dropdown-box">
                        <p>This Add-on includes: </p>
                        <div class="checkbox"><label><input type="checkbox">${description}</label></div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</c:if>