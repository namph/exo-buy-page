<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${addons.size() > 0}">
    <div class="dropdown-info-addon" style="display: none;">
        <div class="col-sm-12 wrap-dropdown">
            <div class="dropdown-box show">
                <p><b>This Add-on includes:</b></p>
                <p><i class="fa fa-check fa-primary-color"></i> Unlimited video calls for up to 25 users</p>
                <p><i class="fa fa-check fa-primary-color"></i> Screen sharing</p>
                <p><i class="fa fa-check fa-primary-color"></i> Group video conference with Chat integration</p>
                <p><i class="fa fa-check fa-primary-color"></i> 1-year license</p>
            </div>
        </div>
    </div>

    <div class="bp-heading">
        <h3 class="title">Add-ons</h3>
        <span class="title-icon"><i class="uiIcon28BPAddons"></i></span>
    </div>
    <div class="bp-content">

        <p>Add powerful add-on to your Social Intranet</p>
        <br>
        <!-- User .uiCloudCardSelect with Grid System. You can not use with Grid System like Services section below  -->
        <c:forEach items="${addons}" var="addon">
            <c:set var="count_addon" value="${count_addon + 1}"/>
            <c:if test="${count_addon%3==1}"><div class="row"></c:if>

            <c:set var="id" value="${addon.getId()}"></c:set>
            <c:set var="name" value="${addon.getName()}"></c:set>
            <c:set var="description" value="${addon.getDescription()}"></c:set>
            <c:set var="price" value="${addon.getPrice()}"></c:set>
            <c:set var="user" value="${addon.getOptionUser()}"></c:set>
            <c:set var="icon" value="uiIcon28BPCamera"></c:set>
            <div class="col-sm-4 addon-bloc addon-${user}" style="display: none;">
                <div class="uiCloudCardSelect" id="${id}">
                    <div class="inner addonItem" data-toggle="dropdown" data-name="${name}" data-desciption="${description}" data-price="${price}" data-id="${id}">
                        <div class="circle-icon">
                            <i class="${icon}"></i>
                        </div>
                        <h5 class="title">${name}</h5>
                    </div>
                </div>
            </div>
            <c:if test="${count_addon%3==0 || count_addon==addons.size()}"></div></c:if>
        </c:forEach>
    </div>
</c:if>
<c:if test="${services.size() > 0}">
    <div class="dropdown-info-service" style="display: none;">
        <div class="col-sm-12 middle wrap-dropdown">
            <div class="dropdown-box show">
                <p><b>5 days of ddedicated expertise</b></p>
                <p><i class="fa fa-check fa-primary-color"></i> Installation, users and groups provisioning</p>
                <p><i class="fa fa-check fa-primary-color"></i> 1 Administrator training</p>
                <p><i class="fa fa-check fa-primary-color"></i> 1 Hands on customization workshop</p>
                <p><i class="fa fa-check fa-primary-color"></i> 1 Change management workshop</p>
                <p><i class="fa fa-check fa-primary-color"></i> 1-year license</p>
            </div>
        </div>
    </div>
    <div class="bp-heading">
        <h3 class="title">Services</h3>
        <span class="title-icon"><i class="uiIcon28BPServices"></i></span>
    </div>
    <div class="bp-content">
        <p>Add services to your Social Intranet</p>
        <br>
        <c:forEach items="${services}" var="service">
            <c:set var="count_service" value="${count_service + 1}"/>
            <c:if test="${count_service%3==1}"><div class="row"></c:if>

            <c:set var="id" value="${service.getId()}"></c:set>
            <c:set var="name" value="${service.getName()}"></c:set>
            <c:set var="description" value="${service.getDescription()}"></c:set>
            <c:set var="price" value="${service.getPrice()}"></c:set>
            <c:set var="icon" value="uiIcon28BPCamera"></c:set>
            <div class="col-sm-4">
                <div class="uiCloudCardSelect" id="${id}">
                    <div class="inner serviceItem" data-toggle="dropdown" data-name="${name}" data-description="${description}" data-price="${price}" data-id="${id}">
                        <c:if test="${icon != \"\"}">
                            <div class="circle-icon">
                                <i class="${icon}"></i>
                            </div>
                        </c:if>
                        <h5 class="title">${name}</h5>
                    </div>
                </div>
            </div>
            <c:if test="${count_service%3==0 || count_service==services.size()}"></div></c:if>
        </c:forEach>
    </div>
</c:if>