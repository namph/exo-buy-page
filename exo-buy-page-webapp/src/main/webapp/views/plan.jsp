<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${error != \"\"}">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <strong>Warning!</strong> ${error}
        </div>
    </c:when>
    <c:otherwise>
        <c:forEach items="${planTypes}" var="entry">
            <c:set var="id" value="${entry.value.getId()}"></c:set>
            <c:set var="name" value="${entry.value.getName()}"></c:set>
            <c:set var="price" value="${entry.value.getPrice()}"></c:set>
            <c:set var="active" value="${entry.value.getActive()}"></c:set>
            <c:set var="user" value="${entry.value.getDefaultNbUser()}"></c:set>
            <c:set var="planCycle" value="${entry.value.getPlanCycle()}"></c:set>
            <c:if test="${active == 'active'}">
                <script language="javascript">
                    BuyPage.setPlanDefaultSelected('${id}','${name}','${price}','${user}','${planCycle}');
                </script>
            </c:if>
        </c:forEach>
        <div class="intro-plan-box has-${planTypes.size()}-plan" role="tabpanel">
        <!-- Nav tabs -->
        <div class="row intro-container">
            <div class="col-sm-4 intro-nav">
                <ul class="nav nav-pills nav-stacked" role="tablist">
                    <c:forEach items="${planTypes}" var="entry">
                        <c:set var="id" value="${entry.value.getId()}"></c:set>
                        <c:set var="name" value="${entry.value.getName()}"></c:set>
                        <c:set var="description" value="${entry.value.getDescription()}"></c:set>
                        <c:set var="price" value="${entry.value.getPrice()}"></c:set>
                        <c:set var="user" value="${entry.value.getDefaultNbUser()}"></c:set>
                        <c:set var="planCycle" value="${entry.value.getPlanCycle()}"></c:set>
                        <li role="presentation"  class="${entry.value.getActive()}">
                            <div data-target=".${id}" aria-controls="${id}" role="tab" data-toggle="tab" class="planTypeItem" data-name="${name}" data-price="${price}" data-id="${id}" data-user="${user}" data-planCycle="${planCycle}">
                                <h5>${name}</h5>
                                <span>eXo Platfom Enterprise</span>
                                <span class="iconSelect"><i class="fa fa-check"></i></span>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="col-sm-8 col-sm-offset-4 intro-content">
                <!-- Tab panes -->
                <div class="tab-content">
                    <c:forEach items="${planTypes}" var="entry">
                    <c:set var="planTypeId" value="${entry.value.getId()}"></c:set>
                    <c:set var="active" value="${entry.value.getActive()}"></c:set>
                    <c:forEach items="${entry.value.getPlanDTOs()}" var="planDTO">
                    <c:set var="id" value="${planDTO.getId()}"></c:set>
                    <c:set var="listDescription" value="${planDTO.getListDescription()}"></c:set>
                    <div data-target=".${id}" class="hide-tab-item-${id}" aria-controls="${id}" role="tab" data-toggle="tab" style="display: none;">
                    </div>
                    <c:choose>
                    <c:when test="${planTypeId == id}">
                    <div role="tabpanel" class="tab-pane ${active} ${id}">
                        </c:when>
                        <c:otherwise>
                        <div role="tabpanel" class="tab-pane ${id}">
                            </c:otherwise>
                            </c:choose>
                            <h3 class="heading-border">Your subcription Plan includes:</h3>
                            <c:if test="${listDescription != null}">
                                <ul class="ui-list-check">
                                    <c:forEach items="${listDescription.get(\"description\")}" var="description">
                                        <li>${description}</li>
                                    </c:forEach>
                                </ul>

                            </c:if>
                        </div>

                        </c:forEach>

                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <p>Select the number of users for your subscription:</p>
        <!-- Tab panes -->
        <div class="tab-content text-center">
            <c:forEach items="${planTypes}" var="entry">
                <c:set var="planTypeId" value="${entry.value.getId()}"></c:set>
                <c:set var="tick" value="${entry.value.generateUserSliderTick()}"></c:set>
                <c:set var="label" value="${entry.value.generateUserSliderLabel()}"></c:set>
                <c:set var="userPosition" value="${entry.value.getCurrentUserPositionInSlider()}"></c:set>
                <c:set var="active" value="${entry.value.getActive()}"></c:set>
                <c:set var="invisible" value="block"></c:set>
                <c:if test="${active==\"active\"}">
                    <c:set var="invisible" value="none"></c:set>
                </c:if>
                <div class="slider-plan-type" id="slider-number-users-${planTypeId}" type="text" data-slider-ticks="${tick}" data-slider-ticks-snap-bounds="1" data-slider-ticks-labels="${label}" data-slider-value="${userPosition}" style=" display:${invisible};"></div>

                <div role="tabpanel" class="tab-pane ${entry.value.getActive()} ${entry.value.getId()}" style="display: none;">
                    <div class="boxSlider">
                        <div id="slider" class="ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all" aria-disabled="false">
                            <div class="ui-slider-range ui-widget-header ui-corner-all" style="left: 0%; width: ${entry.value.numberUserPercent()}%;"></div>
                            <c:forEach items="${entry.value.getPlanDTOs()}" var="planDTO">
                                <c:set var="id" value="${planDTO.getId()}"></c:set>
                                <c:set var="name" value="${planDTO.getName()}"></c:set>
                                <c:set var="description" value="${planDTO.getDescription()}"></c:set>
                                <c:set var="price" value="${planDTO.getPrice()}"></c:set>
                                <c:set var="nbUser" value="${planDTO.getOptionUser()}"></c:set>
                                <c:set var="position" value="${nbUser/entry.value.getMaxNbUser()*100}"></c:set>
                                <c:set var="user" value="${planDTO.getOptionUser()}"></c:set>
                                <c:set var="planCycle" value="${planDTO.getPlanCycle()}"></c:set>
                                <div class="ui-slider-handle ui-state-default ui-corner-all subPlanItem" id="${planTypeId}-${nbUser}" style="left: ${position}%;" data-name="${name}" data-price="${price}" data-id="${id}"  data-user="${user}" data-planCycle="${planCycle}">
                                    <span class="countValue" >${nbUser} users</span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <p class="text-right">Need more users? Purchase an <a href="http://www.exoplatform.com/company/en/company/contact-us" target="_blank">unlimited users subscription</a></p>
    </c:otherwise>
</c:choose>