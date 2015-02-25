<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${plans}" var="plan">
    id = ${plan.id}
</c:forEach>
${msg}
<!-- Nav tabs -->
<div class="row intro-container">
    <div class="col-sm-4 intro-nav">
        <ul class="nav nav-pills nav-stacked" role="tablist">
            <li role="presentation" class="active">
                <div data-target="#home" aria-controls="home" role="tab" data-toggle="tab">
                    <h5>1 Year plan<br>eXo Platfom Enterprise</h5>
                </div>
            </li>
            <li role="presentation">
                <div data-target="#profile" aria-controls="profile" role="tab" data-toggle="tab">
                    <h5>2 Year plan<br>eXo Platfom Enterprise</h5>
                </div>
            </li>
            <li role="presentation">
                <div data-target="#messages" aria-controls="messages" role="tab" data-toggle="tab">
                    <h5>3 Year plan<br>eXo Platfom Enterprise</h5>
                </div>
            </li>
        </ul>
    </div>
    <div class="col-sm-8 col-sm-offset-4 intro-content">
        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="home">
                <h3 class="heading-border">Your subcription Plan includes:</h3>
                <div class="checkbox"><label><input type="checkbox"> license for 100 user</label></div>
                <div class="checkbox"><label><input type="checkbox"> 5 support tickets included</label></div>
                <div class="checkbox"><label><input type="checkbox"> Software Maintenace</label></div>
            </div>
            <div role="tabpanel" class="tab-pane" id="profile">
                <h3 class="heading-border">Your subcription Plan includes:</h3>
                <div class="checkbox"><label><input type="checkbox"> license for 200 user</label></div>
                <div class="checkbox"><label><input type="checkbox"> 10 support tickets included</label></div>
                <div class="checkbox"><label><input type="checkbox"> Software Maintenace</label></div>
            </div>
            <div role="tabpanel" class="tab-pane" id="messages">
                <h3 class="heading-border">Your subcription Plan includes:</h3>
                <div class="checkbox"><label><input type="checkbox"> license for 300 user</label></div>
                <div class="checkbox"><label><input type="checkbox"> 15 support tickets included</label></div>
                <div class="checkbox"><label><input type="checkbox"> Software Maintenace</label></div>
            </div>
        </div>
    </div>
</div>