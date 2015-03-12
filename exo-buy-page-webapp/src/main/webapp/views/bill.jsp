<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${error == null}">
    <c:set var="planId" value="${plan.getId()}"></c:set>
    <c:set var="planName" value="${plan.getName()}"></c:set>
    <c:set var="planPrice" value="${plan.getPrice()}"></c:set>
    <c:set var="planType" value="${plan.getPlanType()}"></c:set>
    <c:set var="planUser" value="${plan.getOptionUser()}"></c:set>
    <c:set var="period" value="${plan.getPeriod()}"></c:set>

    <div class="panel panel-primary order-box" data-spy="affix" data-offset-top="110" data-offset-bottom="200">
        <div class="panel-heading">
            <h4>Your Order<br><small><a class="exo-icon-restore" href="#"></a>${period}</small></h4>
        </div>
        <div class="panel-body">
            <h5 class="price-head">Exo Platform Entrerprise</h5>
            <div class="price-row">
                <div class="row">
                    <div class="col-xs-7 price-label">
                            ${planName}<br><strong>${planUser}</strong> users
                    </div>
                    <div class="col-xs-5 text-right">
                        <span class="price">$ ${planPrice}</span>
                    </div>
                </div>
            </div>
            <c:if test="${addons.size() > 0}">
                <h5 class="price-head">Add-ons</h5>
                <c:forEach items="${addons}" var="item">
                    <c:set var="id" value="${item.getId()}"></c:set>
                    <c:set var="name" value="${item.getName()}"></c:set>
                    <c:set var="price" value="${item.getPrice()}"></c:set>
                    <c:set var="user" value="${item.getOptionUser()}"></c:set>
                    <div class="price-row">
                        <div class="row">
                            <div class="col-xs-7 price-label">
                                    ${name}<br>Up to ${user} users
                            </div>
                            <div class="col-xs-5 text-right">
                                <span class="price link-color">$ ${price}</span>
                            </div>
                        </div>
                        <a href="javascript:void(0);" class="fa fa-trash remove-addonItem" data-id="${id}"></a>
                    </div>

                </c:forEach>
            </c:if>
            <c:if test="${services.size() > 0}">
                <h5 class="price-head">Service</h5>
                <c:forEach items="${services}" var="item">
                    <c:set var="id" value="${item.getId()}"></c:set>
                    <c:set var="name" value="${item.getName()}"></c:set>
                    <c:set var="price" value="${item.getPrice()}"></c:set>
                    <div class="price-row">
                        <div class="row">
                            <div class="col-xs-7 price-label">
                                    ${name}
                            </div>
                            <div class="col-xs-5 text-right">
                                <span class="price link-color">$ ${price}</span>
                            </div>
                        </div>
                        <a href="javascript:void(0);" class="fa fa-trash  remove-serviceItem" data-id="${id}"></a>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${not empty discount}">
                <c:set var="id" value="${discount.getId()}"></c:set>
                <c:set var="name" value="${discount.getName()}"></c:set>
                <c:set var="amount" value="${discount.getStrAmount()}"></c:set>
                <h5 class="price-head">Discount</h5>
                <div class="price-row">
                    <div class="row">
                        <div class="col-xs-7 price-label">
                                ${name}
                        </div>
                        <div class="col-xs-5 text-right">
                            <span class="price link-color">$ ${amount}</span>
                        </div>
                    </div>
                    <a href="javascript:void(0);" class="fa fa-trash  remove-discountItem" data-id="${id}"></a>
                </div>
            </c:if>
        </div>
        <div class="panel-footer">
            <div class="row">
                <div class="col-xs-6 total-text">Total</div>
                <div class="col-xs-6 total-price"><span class="price">$ ${total}</span></div>
            </div>
        </div>
    </div>
</c:if>
