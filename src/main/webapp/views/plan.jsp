<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="intro-plan-box" role="tabpanel">
    <!-- Nav tabs -->
    <div class="row intro-container">
        <div class="col-sm-4 intro-nav">
            <ul class="nav nav-pills nav-stacked" role="tablist">
                <c:forEach items="${planTypes}" var="entry">
                    <li role="presentation"  class="${entry.value.getActive()}">
                        <div data-target=".${entry.value.getId()}" aria-controls="profile" role="tab" data-toggle="tab">
                            <h5>${entry.value.getName()}<br>${entry.value.getDescription()}</h5>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-sm-8 col-sm-offset-4 intro-content">
            <!-- Tab panes -->
            <div class="tab-content">
                <c:forEach items="${planTypes}" var="entry">
                    <div role="tabpanel" class="tab-pane ${entry.value.getActive()} ${entry.value.getId()}">
                        <h3 class="heading-border">Your subcription Plan includes:</h3>
                        <div class="checkbox"><label><input type="checkbox"> ${entry.value.getName()}</label></div>
                        <div class="checkbox"><label><input type="checkbox"> 5 support tickets included</label></div>
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
        <div role="tabpanel" class="tab-pane ${entry.value.getActive()} ${entry.value.getId()}">
            <div class="boxSlider">
                <div id="slider" class="ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all" aria-disabled="false">
                    <div class="ui-slider-range ui-widget-header ui-corner-all" style="left: 0%; width: ${entry.value.numberUserPercent()}%;"></div>
                        <c:forEach items="${entry.value.getPlanDTOs()}" var="planDTO">
                            <c:set var="position" value="${planDTO.getOptionUser()/entry.value.getTotalUsers()*100}"></c:set>
                            <a href="#" class="ui-slider-handle ui-state-default ui-corner-all" style="left: ${position}%;" data-price="${planDTO.getPrice()}">
                                <span class="countValue" >${planDTO.getOptionUser()} users</span>
                            </a>
                        </c:forEach>
                </div>
                <input type="hidden" value="161" name="slider-value" id="slider-value">
            </div>
        </div>
    </c:forEach>
</div>
