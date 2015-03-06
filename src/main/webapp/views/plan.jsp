<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<div class="intro-plan-box" role="tabpanel">
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
                        <div data-target=".${id}" aria-controls="profile" role="tab" data-toggle="tab" class="planTypeItem" data-name="${name}" data-price="${price}" data-id="${id}" data-user="${user}" data-planCycle="${planCycle}">
                            <h5>${name}<br>${description}</h5>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-sm-8 col-sm-offset-4 intro-content">
            <!-- Tab panes -->
            <div class="tab-content">
                <c:forEach items="${planTypes}" var="entry">
                    <c:set var="id" value="${entry.value.getId()}"></c:set>
                    <c:set var="name" value="${entry.value.getName()}"></c:set>
                    <c:set var="description" value="${entry.value.getDescription()}"></c:set>
                    <c:set var="price" value="${entry.value.getPrice()}"></c:set>
                    <div role="tabpanel" class="tab-pane ${entry.value.getActive()} ${id}">
                        <h3 class="heading-border">Your subcription Plan includes:</h3>
                        <div class="checkbox"><label><input type="checkbox"> ${name}</label></div>
                        <div class="checkbox"><label><input type="checkbox"> ${description}</label></div>
                        <div class="checkbox"><label><input type="checkbox"> Software Maintenace</label></div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<!-- Tab panes -->
<div class="tab-content">

    <c:forEach items="${planTypes}" var="entry">
        <c:set var="id" value="${entry.value.getId()}"></c:set>
        <c:set var="tick" value="${entry.value.generateUserSliderTick()}"></c:set>
        <c:set var="label" value="${entry.value.generateUserSliderLabel()}"></c:set>
        <c:set var="user" value="${entry.value.getDefaultNbUser()}"></c:set>
        <c:set var="active" value="${entry.value.getActive()}"></c:set>
        <c:set var="invisible" value="block"></c:set>
        <c:if test="${active==\"active\"}">
            <c:set var="invisible" value="none"></c:set>
        </c:if>
        <div class="slider-plan-type" id="slider-number-users-${id}" type="text" data-slider-ticks="${tick}" data-slider-ticks-snap-bounds="1" data-slider-ticks-labels="${label}" data-slider-value="${user}" style=" display:${invisible};"></div>

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
                            <div class="ui-slider-handle ui-state-default ui-corner-all planTypeItem" style="left: ${position}%;" data-name="${name}" data-price="${price}" data-id="${id}"  data-user="${user}" data-planCycle="${planCycle}">
                                <span class="countValue" >${nbUser} users</span>
                            </div>
                        </c:forEach>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
