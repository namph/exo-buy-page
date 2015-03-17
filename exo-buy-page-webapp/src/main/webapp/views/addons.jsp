<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${addons.size() > 0}">
    <div class="dropdown-info-addon" style="display: none;">
        <div class="col-sm-12 wrap-dropdown">
            <div class="dropdown-box show">
                <p><b>This Add-on includes:</b></p>
            </div>
        </div>
    </div>

    <div class="bp-heading">
        <h3 class="title">Add-ons</h3>
        <span class="title-icon"><i class="uiIcon28BPAddons"></i></span>
    </div>
    <div class="bp-content addonsContainer">

        <p>Add powerful add-on to your Social Intranet</p>
        <br>
        <!-- User .uiCloudCardSelect with Grid System. You can not use with Grid System like Services section below  -->
        <c:forEach items="${addons}" var="addon">
            <c:set var="count_addon" value="${count_addon + 1}"/>
            <c:if test="${count_addon%3==1}"><div class="row"></c:if>
            <c:set var="id" value="${addon.getId()}"></c:set>
            <c:set var="name" value="${addon.getName()}"></c:set>
            <c:set var="listDescription" value="${addon.getListDescription()}"></c:set>
            <c:set var="price" value="${addon.getPrice()}"></c:set>
            <c:set var="user" value="${addon.getOptionUser()}"></c:set>
            <c:set var="icon" value="uiIcon28BPCamera"></c:set>
            <c:set var="isAttched2Plan" value="${addon.isAttached2Plan()}"></c:set>
            <c:set var="type" value="${addon.getAddonType()}"></c:set>
            <div class="col-sm-4 addon-bloc addon-${id}-${user}" style="display: none;" data-attached-plan="${isAttched2Plan}" data-type="${type}">
                <div class="uiCloudCardSelect" id="${id}">
                    <div class="inner addonItem" data-toggle="${count_addon%3}" data-name="${name}" data-price="${price}" data-id="${id}">
                        <div class="item-list-description" style="display: none;">
                            <c:if test="${listDescription.get(\"title\") != \"\"}">
                                <p><b>${listDescription.get("title")}</b></p>
                            </c:if>
                            <c:if test="${listDescription != null}">
                                <ul class="ui-list-check">
                                    <c:forEach items="${listDescription.get(\"description\")}" var="description">
                                        <li> ${description} </li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                        </div>
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
        <div class="col-sm-12 wrap-dropdown">
            <div class="dropdown-box show">
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
            <c:set var="listDescription" value="${service.getListDescription()}"></c:set>
            <c:set var="price" value="${service.getPrice()}"></c:set>
            <c:set var="icon" value="uiIcon28BPCamera"></c:set>
            <div class="col-sm-4">
                <div class="uiCloudCardSelect mini" id="${id}">
                    <div class="inner serviceItem" data-toggle="${count_service%3}" data-name="${name}" data-price="${price}" data-id="${id}">
                        <div class="item-list-description" style="display: none;">
                            <c:if test="${listDescription != null}">
                                <c:if test="${listDescription.get(\"title\") != \"\"}">
                                    <p><b>${listDescription.get("title")}</b></p>
                                </c:if>
                                <ul class="ui-list-check">
                                    <c:forEach items="${listDescription.get(\"description\")}" var="description">
                                        <li> ${description} </li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                        </div>
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