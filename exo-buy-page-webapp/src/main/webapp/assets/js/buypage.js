(function($){
    var _planContainerDOM;
    var _addonsContainerDOM;
    var _billContainerDOM;
    var _baseRestUrl = "/buy";
    var _planSelected;
    var _listAddonsSelected = new Array();
    var _listServicesSelected = new Array();
    var _discountProvided;
    var _addonUserDefault = 0;
    var _currentSlider;
    var _totalBill= 0;
    var BuyPage = {};

    var _showPartLoading = function(parent){
        var loading = $("#BuyPageLoadingContainer").html();
        try{
            parent.find("div.panel-body").html(loading);
        }catch (e){}

    };

    var _eXoStyleMessageConfirmCBController = function (type,message) {
        var alertDOM =  $('#buypage-alert-billing');
        if(type != null && type != "") {
            var icon = type.charAt(0).toUpperCase() + type.slice(1);
            var strIcon =""; //"<i class='uiIcon" + icon + "'></i>";
            alertDOM.removeClass();
            alertDOM.addClass('alert');
            alertDOM.addClass('alert-' + type);
            alertDOM.html(strIcon + message);
        }
    };
    var _disPlayInfoeXoStyleMsgCB = function(msg){
        _eXoStyleMessageConfirmCBController('info',msg);
    };
    var _disPlayWarningeXoStyleMsgCB = function(msg){
        _eXoStyleMessageConfirmCBController('warning',msg);
    };
    var _disPlayErroreXoStyleMsgCB = function(msg){
        _eXoStyleMessageConfirmCBController('danger',msg);
    };


    var _messageConfirmCBController = function (typeParent, type,message,display) {

        var buypageAlertGeneral = $("#buypage-alert-general");
        var buypageAlertCoupon = $("#buypage-alert-coupon");
        var buypageAlertBilling = $("#buypage-alert-billing");
        var buypageAlertCredit = $("#buypage-alert-credit");
        var alertDOM = buypageAlertGeneral;

        if(typeParent == "coupon"){
            alertDOM = buypageAlertCoupon;
        } else if(typeParent == "credit"){
            alertDOM = buypageAlertCredit;
        }
        if(alertDOM != null){

            if(type != null && type != "") {
                alertDOM.removeClass();
                alertDOM.addClass('alert');
                alertDOM.addClass('alert-' + type);
                alertDOM.children('div.msg').html(message);
            }
            if(display)
                alertDOM.show();
            else
                alertDOM.hide();
        }
    };
    var _disPlayInfoMsgCB = function(typeParent,msg,display){
        _messageConfirmCBController(typeParent,'info',msg,display);
    };
    var _disPlayWarningMsgCB = function(typeParent,msg,display){
        _messageConfirmCBController(typeParent,'warning',msg,display);
    };
    var _disPlayErrorMsgCB = function(typeParent,msg,display){
        _messageConfirmCBController(typeParent,'danger',msg,display);
    };
    var _disPlaySuccessMsgCB = function(typeParent,msg,display){
        _messageConfirmCBController(typeParent,'success',msg,display);
    };

    var _showSliderAssociated2PlanSelected = function(){
        $(".slider-plan-type").each(function(){
            var sliderDOM = $(this);
            if(sliderDOM.attr("id") == "slider-number-users-"+_planSelected.id){
                _loadSlider(_planSelected.id);
            }
        });
    };
    var _convertSliderData2Array = function(strData){
        return strData.split(",");
    };
    var _loadSlider = function(sliderID){
        if( _currentSlider != null)
            _currentSlider.slider("destroy");
        var sliderDOM = $("#slider-number-users-"+sliderID);
        var ticks = sliderDOM.attr("data-slider-ticks");
        var labels = sliderDOM.attr("data-slider-ticks-labels");
        ticks = _convertSliderData2Array(ticks);
        labels = _convertSliderData2Array(labels);
        _currentSlider = $("#slider-number-users-"+sliderID).slider({
            ticks: ticks,
            ticks_labels: labels,
            ticks_snap_bounds: 1,
            tooltip: 'always',
            formatter: function(value) {
                var i = value - 1;
                if (i < 0)
                    i = 0;
                return labels[i];
            }
        }).on('change',function(objValue){
            var newVal=objValue.value.newValue;
            var lbl = labels[newVal-1];
            var nbUser = lbl.match(/\d+/);
            _selectSubPlanItem(sliderID+"-"+nbUser);
        });
    };
    var _loadActivePlans = function(){
        $.ajax({
            url: _baseRestUrl+"/Plan/getActives",
            dataType: "text"
        })
            .done(function(data) {
                _planContainerDOM.html(data);
                _showSliderAssociated2PlanSelected();
                _loadBillFromClient();
                _loadActiveAddons(null);

            })
            .error(function (xhr, ajaxOptions, thrownError){
                if(xhr.status==404) {
                    _planContainerDOM("Something went wrong, please try again");
                }
            })
            .fail(function (jqxhr, textStatus, error) {
                var err = textStatus + ', ' + error;
                console.log("Transaction Failed: " + err);
            });
    };
    var _loadActiveAddons = function(planId){
        $.ajax({
            mimeType:"text/html; charset=UTF-8",
            url: _baseRestUrl+"/Addons/getActives/"+planId,
            dataType: "text"
        })
            .done(function(data) {
                _addonsContainerDOM.html(data);
                _hideAllAddonsButOne();
            })
            .error(function (xhr, ajaxOptions, thrownError){
                if(xhr.status==404) {
                    _addonsContainerDOM("Something went wrong, please try again");
                }
            })
            .fail(function (jqxhr, textStatus, error) {
                var err = textStatus + ', ' + error;
                console.log("Transaction Failed: " + err);
            });
    };
    var _getDiscount = function(discountId){
        var url = encodeURI(_baseRestUrl+"/Discount/get/"+discountId);
        $(".coupon-loading").show();
        $.ajax({
            url: url
        })
            .done(function(data) {
                $(".coupon-loading").hide();
                if(typeof data !== undefined && data!= ""){
                    var obj = data;
                    _discountProvided = {"id":obj.id,"name":obj.name,"description":obj.description,"amount":obj.amount};
                    _loadBillFromClient();
                    _disPlaySuccessMsgCB("coupon","The coupon is valid",true);
                }else
                    _disPlayErrorMsgCB("coupon","The coupon is unknown",true)
            })
            .error(function (xhr, ajaxOptions, thrownError){
                if(xhr.status==404) {
                  _disPlayErrorMsgCB("coupon","The coupon is unknown",true)
                }
            })
            .fail(function (jqxhr, textStatus, error) {
                $(".coupon-loading").hide();
                var err = textStatus + ', ' + error;
              _disPlayErrorMsgCB("coupon","The coupon is unknown",true)
            });
    };

    var _addEventClick2PlanTypeItem = function(){
        $(document).on('click.planTypeItem.select','div.planTypeItem',function(){
            var me = $(this);
            var id = me.attr("data-id");
            var name = me.attr("data-name");
            var price = me.attr("data-price");
            var user = me.attr("data-user");
            var planCycle = me.attr("data-planCycle");
            _addonUserDefault = user;
            _planSelected = {"id":id,"name":name,"price":price,"planCycle":planCycle};
            if(typeof _discountProvided !== undefined && null != _discountProvided){
                _getDiscount(_discountProvided.id);
            }else{
                _showSliderAssociated2PlanSelected();
                _hideAllAddonsButOne();
                _loadBillFromClient();
            }
        });
    };
    var _selectSubPlanItem = function(elementId){
        var me = $("#"+elementId);
        if(me.length > 0){
            var id = me.attr("data-id");
            var name = me.attr("data-name");
            var price = me.attr("data-price");
            var user = me.attr("data-user");
            var planCycle = me.attr("data-planCycle");
            _addonUserDefault = user;
            _planSelected = {"id":id,"name":name,"price":price,"planCycle":planCycle};
            $(".hide-tab-item-"+id).trigger('click');
            if(typeof _discountProvided !== undefined && null != _discountProvided){
                _getDiscount(_discountProvided.id);
            }else{
                _hideAllAddonsButOne();
                _loadBillFromClient();
            }
        }
    };

    var _addEventClick2AddonItem = function(){
        $(document).on('click.addonItem.select','div.addonItem',function(){
            var me = $(this);
            var parent = me.parent();
            var id = me.attr("data-id");
            var name = me.attr("data-name");
            var price = me.attr("data-price");
            var description = "";//me.attr("data-description");
            var obj = {id:id, name:name, price:price, description:description};
            _toggleDropdownAddonDesc(me);
            if(parent.hasClass("selected")){
                parent.removeClass("selected");
                _removeAddonFromListSelected(id);
            } else {
                parent.addClass("selected");
                _addAddon2ListSelected(obj);
            }
            if(typeof _discountProvided !== undefined && null != _discountProvided){
                _getDiscount(_discountProvided.id);
            }else{
                _loadBillFromClient();
            }
        });
    };

    var _toggleDropdownAddonDesc = function(addon_item) {
        var parent = addon_item.parent();
        var parent_row = addon_item.closest(".row");
        var descriptionBloc = addon_item.children("div.item-list-description");
        var description = descriptionBloc.html();//addon_item.attr("data-description");
        var data_toggle = addon_item.attr("data-toggle");
        var is_shown_desc = false;

        var listDisplayedAddon = new Array();
        parent_row.parent().find(".uiCloudCardSelect").each(function () {
            listDisplayedAddon.push(this.id);
        });

        var dropdown_position = ($.inArray(addon_item.attr("data-id"), listDisplayedAddon) + 1) % 3;

        if (parent_row.find(".dropdown-info-addon").hasClass(data_toggle)) is_shown_desc = true;

        parent_row.parent().find(".dropdown-info-addon").remove();

        //if (is_shown_desc) return;

        $(".dropdown-info-addon").clone().addClass(data_toggle).appendTo(parent_row);

        switch (data_toggle) {
            case 1:
                dropdown_position = "left";
                break;
            case 2:
                dropdown_position = "middle";
                break;
            case 0:
                dropdown_position = "right";
                break;
        }

        parent_row.find(".wrap-dropdown").addClass(dropdown_position);

        dropdown_box = parent_row.find(".dropdown-box");
        dropdown_box.append(description);

        var appended_dropdown_info_service = parent_row.find(".dropdown-info-addon");

        appended_dropdown_info_service.toggle()
    };

    var _addEventClick2ServiceItem = function(){
        $(document).on('click.serviceItem.select','div.serviceItem',function(){
            var me = $(this);
            var parent = me.parent();
            var id = me.attr("data-id");
            var name = me.attr("data-name");
            var price = me.attr("data-price");
            var obj = {id:id,name:name,price:price};

            if(parent.hasClass("selected")){
                parent.removeClass("selected");
                _toggleDropdownServiceDesc(me,false);
                _removeServiceFromListSelected(id);
            }else{
                parent.addClass("selected");
                _addService2ListSelected(obj);
                _toggleDropdownServiceDesc(me,true);
            }
            if(typeof _discountProvided !== undefined && null != _discountProvided){
                _getDiscount(_discountProvided.id);
            }else{
                _loadBillFromClient();
            }
        });
    };

    var _toggleDropdownServiceDesc = function(service_item,display) {

        var parent = service_item.parent();
        var parent_row = service_item.closest(".row");
        var descriptionBloc = service_item.children("div.item-list-description");
        var description = descriptionBloc.html();//service_item.attr("data-description");
        var data_toggle = service_item.attr("data-toggle");
        var is_shown_desc = false;
        if(!display){
            parent_row.parent().find(".dropdown-info-service").remove();
        }
        if (parent_row.find(".dropdown-info-service").hasClass(data_toggle)) is_shown_desc = true;

        parent_row.parent().find(".dropdown-info-service").remove();

        //if (is_shown_desc) return;

        $(".dropdown-info-service").clone().addClass(data_toggle).appendTo(parent_row);

        var dropdown_position;

        switch (data_toggle) {
            case "1":
                dropdown_position = "left";
                break;
            case "2":
                dropdown_position = "middle";
                break;
            case "0":
                dropdown_position = "right";
                break;
        }

        parent_row.find(".wrap-dropdown").addClass(dropdown_position);

        dropdown_box = parent_row.find(".dropdown-box");

        dropdown_box.append(description);

        var appended_dropdown_info_service = parent_row.find(".dropdown-info-service");

        appended_dropdown_info_service.toggle();
    };

    var _addAddon2ListSelected = function(obj){
        if (_checkItemExistsInList(obj.id,_listAddonsSelected) == null)
            _listAddonsSelected.push(obj);
    };
    var _removeAddonFromListSelected = function (id) {
        var pos = _checkItemExistsInList(id,_listAddonsSelected);
        if ( pos != null){
            _listAddonsSelected.splice(pos,1);
            return true;
        }
        return false;
    };
    var _addService2ListSelected = function(obj){
        if (_checkItemExistsInList(obj.id,_listServicesSelected) == null)
            _listServicesSelected.push(obj);
    };
    var _removeServiceFromListSelected = function (id) {
        var pos = _checkItemExistsInList(id,_listServicesSelected);
        if ( pos != null)
            _listServicesSelected.splice(pos,1);
    };
    var _removeDiscount = function(id){
        _discountProvided = null;
        _loadBillFromClient();
    };
    var _checkItemExistsInList = function(id,list){
        var index = null;
        $.each(list, function (i,v) {
            if(id == v.id){
                index =  i;
            }
        });
        return index;
    };
    var _loadBillFromClient = function(){
        _showPartLoading(_billContainerDOM);
        var data = {"plan":_planSelected,"addons":_listAddonsSelected,"services":_listServicesSelected,"discount":_discountProvided};
        data = JSON.stringify(data);
        $.ajax({
            url: _baseRestUrl+"/Bill/showFromClient",
            dataType: "text",
            type:"POST",
            contentType:"application/json",
            data:data
        })
            .done(function(data) {
                _billContainerDOM.html(data);
                _resetAffixOrderBox();
            })
            .error(function (xhr, ajaxOptions, thrownError){
                if(xhr.status==404) {
                    _billContainerDOM.html("Something went wrong, please try again");
                }
            })
            .fail(function (jqxhr, textStatus, error) {
                var err = textStatus + ', ' + error;
                _billContainerDOM.html("Something went wrong, please try again");
            });
    };
    var _addEvent2BtnSubmitDiscount = function(){
        $(document).on('click.discount.submit','button#btnSubmitDiscount',function(){
            _disPlayWarningMsgCB("coupon","",false);
            var discountId = $("#discountId").val();
            if (typeof discountId !== undefined && discountId.length > 0 ){
                _getDiscount(discountId);
            }else{
                _disPlayWarningMsgCB("coupon","Please provide your coupon code",true);
            }
        })
    };
    var _addEvent2LinkRemoveAddon = function(){
        $(document).on('click.addon.remove','a.remove-addonItem', function () {
            var me = $(this);
            var id = me.attr("data-id");
            $("#"+id).removeClass("selected");
            _removeAddonFromListSelected(id);
            if(typeof _discountProvided !== undefined && null != _discountProvided){
                _getDiscount(_discountProvided.id);
            }else{
                _loadBillFromClient();
            }
        });
    };
    var _addEvent2LinkRemoveService = function(){
        $(document).on('click.service.remove','a.remove-serviceItem', function () {
            var me = $(this);
            var id = me.attr("data-id");
            $("#"+id).removeClass("selected");
            _removeServiceFromListSelected(id);
            if(typeof _discountProvided !== undefined && null != _discountProvided){
                _getDiscount(_discountProvided.id);
            }else{
                _loadBillFromClient();
            }
        });
    };

    var _addEvent2LinkRemoveDiscount = function(){
        $(document).on('click.discount.remove','a.remove-discountItem',function(){
            var id = $(this).attr("data-id");
            _removeDiscount(id);
        });
    };
    var _validateBillingForm = function(){
        var info = new Array();
        var i = 0;
        var listInputDOM = $("#billingForm :text");
        listInputDOM.each(function(){
            var val = $(this).val();
            var name = $(this).attr("name");
            if(val.length > 0 && val != null ){
                info[name] = val;
                i++;
            }
        });
        if (i < listInputDOM.length-1)
            return null;

        return info;
    };
    var _dislayLoadingAll = function(display){
        var loadingDOM = $(".loading-all");
        if(display){
            loadingDOM.addClass("show");
        }
        else
            loadingDOM.removeClass("show");
    };
    var _addEvent2BtnSubscribe = function(){
        $(document).on('click.subscribe.submit','button.subscribe', function () {
            var me = $(this);
            var subscriptionCustomer = _validateBillingForm();
            var isValidBillingForm = _isValidBillingForm();
            if (subscriptionCustomer == null || !isValidBillingForm){
                //_disPlayInfoMsgCB("billing","Please fill all mandatory fields");
                return;
            }
            var firstName = subscriptionCustomer['first_name'];
            var lastName = subscriptionCustomer['last_name'];
            var organization = subscriptionCustomer['organisation'];
            var phone = subscriptionCustomer['phone'];
            var email = subscriptionCustomer['billing_email'];
            var cardNumber = $("#cardNumber").val();;
            var cardHolder = $("#cardHolder").val();
            var expireMonth = $("#expireMonth").val();
            var expireYear = $("#expireYear").val();
            var cardCVV = $("#cardCVV").val();
            var productCode = $("#product_code").val();
            var isOk2Submit = false;
            if ( cardNumber.replace(/ /g,'').length > 0 && cardCVV.replace(/ /g,'').length > 0 && cardHolder.replace(/ /g,'').length > 0 && expireMonth != null && expireYear != null ) {
              isOk2Submit = true;
            }
            if (isOk2Submit){
              _disPlayWarningMsgCB("credit","",false);
            }else{
              _disPlayWarningMsgCB("credit","Please input all required fields",true);
              return;
            }
            var termandcondition = $("#termandcondition");
            if (!termandcondition.prop('checked')){
                _disPlayWarningMsgCB("credit","To complete your order, you must read and agree to the Terms and Conditions",true);
                return;
            }
            me.prop('disabled',true);
            var discount = null;
            if(typeof _discountProvided !== undefined && null != _discountProvided)
                discount = _discountProvided;
            var addons = new Array();
            for(var i=0;i<_listAddonsSelected.length;i++){
                addons.push(_listAddonsSelected[i]);
            }
            for(var i=0;i<_listServicesSelected.length;i++){
                addons.push(_listServicesSelected[i]);
            }
            _dislayLoadingAll(true);
            var data = {"firstName":firstName,"lastName":lastName,"organization":organization,"phone":phone,"email":email,"productCode":productCode,"cardNumber":cardNumber,"cardHolder":cardHolder,"expireMonth":expireMonth,"expireYear":expireYear,"cardCVV":cardCVV,"plan":_planSelected,"addons":addons,"discount":discount,"totalBill":_totalBill};
            data = JSON.stringify(data);
            $.ajax({
                url: _baseRestUrl+"/Subscribe/submit",
                dataType: "text",
                type:"POST",
                contentType:"application/json",
                data:data
            })
                .done(function(data) {
                    var obj = $.parseJSON(data);
                    if(obj.msg == "ok"){
                        _disPlaySuccessMsgCB("credit","Transaction successful",true);
                        window.location.href = "confirmation/success";
                    }else{
                      _dislayLoadingAll(false);
                      me.prop('disabled',false);
                      _disPlayErrorMsgCB("credit",obj.msg,true);
                    }
                })
                .error(function (xhr, ajaxOptions, thrownError){
                    if(xhr.status==404) {
                        _disPlayErrorMsgCB("credit","Something went wrong, please try again",true);;
                    }
                })
                .fail(function (jqxhr, textStatus, error) {
                    var err = textStatus + ', ' + error;
                    _dislayLoadingAll(false);
                    me.prop('disabled',false);
                    _disPlayErrorMsgCB("credit",err,true);
                });
        })
    };


    var error = "has-error";
    var valid = "has-success";
    var _initValidFormFields = function(){
        $("#first_name").blur(function(){
            var lenFirstName = $(this).val().length;
            if(lenFirstName < 1 ) {
                $(this).parent().addClass(error);
            } else {
                $(this).parent().removeClass(error);
                $(this).parent().addClass(valid);
            }
        });

        $("#last_name").blur(function(){
            var lenLastName = $(this).val().length;
            if(lenLastName < 1 ) {
                $(this).parent().addClass(error);
            } else {
                $(this).parent().removeClass(error);
                $(this).parent().addClass(valid);
            }
        });

        $("#organisation").blur(function(){
            var lenLastName = $(this).val().length;
            if(lenLastName < 1 ) {
                $(this).parent().addClass(error);
            } else {
                $(this).parent().removeClass(error);
                $(this).parent().addClass(valid);
            }
        });

        $("#billing_email").blur(function(){
            var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
            var lenEmail = $(this).val().length;
            if(!$(this).val().match(mailformat)) {
                $(this).parent().addClass(error);
            } else {
                $(this).parent().removeClass(error);
                $(this).parent().addClass(valid);
            }
        });

        $("#phone").blur(function(){
            var phoneformat = /^[0-9+\(\)#\.\s\/ext-]+$/;
            var lenPhone = $(this).val().length;
            if(lenPhone < 9 || !$(this).val().match(phoneformat)) {
                $(this).parent().addClass(error);
            } else {
                $(this).parent().removeClass(error);
                $(this).parent().addClass(valid);
            }
        });
    };

    var _isValidBillingForm = function() {
        $("#buypage-alert-billing").hide();
        var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        var phoneformat = /^[0-9+\(\)#\.\s\/ext-]+$/;
        var msg = "";

        var isValid = true;
        if($("#first_name").val().length < 1) {
            msg +="<p>Please fill in the First Name.</p>";
            $("#first_name").parent().addClass(error);
            isValid = false;
        } else {
            $(this).parent().removeClass(error);
            $(this).parent().addClass(valid);
        }

        if($("#last_name").val().length < 1) {
            msg +="<p>Please fill in the Last Name.</p>";
            $("#last_name").parent().addClass(error);
            isValid = false;
        } else {
            $(this).parent().removeClass(error);
            $(this).parent().addClass(valid);
        }

        if($("#organisation").val().length < 1) {
            msg +="<p>Please fill in the Organisation.</p>";
            $("#organisation").parent().addClass(error);
            isValid = false;
        } else {
            $(this).parent().removeClass(error);
            $(this).parent().addClass(valid);
        }

        if(!$("#phone").val().match(phoneformat)) {
            msg +="<p>Please enter a valid phone number.</p>";
            $("#phone").parent().addClass(error);
            isValid = false;
        } else {
            $(this).parent().removeClass(error);
            $(this).parent().addClass(valid);
        }

        if(!$("#billing_email").val().match(mailformat)) {
            msg +="<p>Please enter an valid email address.</p>";
            $("#billing_email").parent().addClass(error);
            isValid = false;
        } else {
            $(this).parent().removeClass(error);
            $(this).parent().addClass(valid);
        }
        if(msg != ""){
            _disPlayErroreXoStyleMsgCB(msg);
            $("#buypage-alert-billing").show();
        }
        return isValid;

    };

    var _hideAllAddonsButOne = function() {

        $(".addon-bloc").hide();
        var addonsSelected2BeRemoved = new Array();
        var addonsAttached2Plan = new Array();
        _listAddonsSelected = new Array();
        $(".addonsContainer").find(".addon-bloc").each(function (i, v) {
            var addonDOM = $(this);
            var type = $(this).attr("data-type");
            var childL1 = $(this).children();
            var id = childL1.attr("id");
            var isAttached2Plan = addonDOM.attr("data-attached-plan");
            if (childL1.hasClass('selected')) {
                if (isAttached2Plan)
                    addonsSelected2BeRemoved.push(type);
                else
                    addonsAttached2Plan.push({"id": id, "type": type, "selected": true});
                childL1.removeClass('selected');
            }

        });
        $(".addonsContainer").find(".addon-bloc").each(function (i, v) {
            var addonDOM = $(this);
            var type = $(this).attr("data-type");
            var childL1 = $(this).children();
            var id = childL1.attr("id");
            if ($(".addon-" + id + "-" + _addonUserDefault).length > 0) {
                addonsAttached2Plan.push({"id": id, "type": type, "selected": false});
            }
        });
        var addon2BeDisplayed = new Array();
        var addons2BeSelected = new Array();
        if (addonsAttached2Plan.length > 0) {
            for (var i = 0; i < addonsAttached2Plan.length; i++) {
                if (jQuery.inArray(addonsAttached2Plan[i].type, addonsSelected2BeRemoved) != -1) {
                    addons2BeSelected.push(addonsAttached2Plan[i].id);
                }
                addon2BeDisplayed.push(addonsAttached2Plan[i].id);
            }
            $(".addonsContainer").find(".addon-bloc").each(function (i, v) {
                var addonDOM = $(this);
                var type = $(this).attr("data-type");
                var childL1 = $(this).children();
                var me = childL1.children();
                var parent_row = addonDOM.parent();
                parent_row.parent().find(".dropdown-info-addon").remove();
                var id = me.attr("data-id");
                var name = me.attr("data-name");
                var price = me.attr("data-price");
                var description = "";//me.attr("data-description");
                var obj = {id: id, name: name, price: price, description: description};
                if (jQuery.inArray(id, addon2BeDisplayed)  != -1) {
                    addonDOM.show();
                }
                if (jQuery.inArray(id, addons2BeSelected)  != -1) {
                    _addAddon2ListSelected(obj);
                    childL1.addClass("selected");
                }
            });
        }
    };
    var _addEvent2CheckTandC = function () {
        $(document).on('click.termandcondition.check',"#termandcondition",function(){
            var me = $(this);
            if(me.prop("checked")){
                _disPlayWarningMsgCB("credit","",false);
            }
        });
    };
    var _resetAffixOrderBox = function () {
        var windowHeight = $(window).height();
        var orderboxHeight = $(".order-box").height();
        if(orderboxHeight > windowHeight) {
            $(".order-box").addClass('full-height');
        } else {
            $(".order-box").removeClass('full-height');
        }
        $('.order-box').affix({
            offset: {
                top: 100,
                bottom: function () {
                    return (this.bottom = $('.footer').outerHeight(true))
                }
            }
        })
    };
    var _addEventClickOutSide = function(){
      document.onclick = function(e){

        var target = (e && e.target) || (event && event.srcElement);
        var clickedOnItem = false;
        var clickOnCard = false;
        while (target.parentNode) {
          if ($(target.parentNode).hasClass("uiCloudCardSelect") || $(target.parentNode).hasClass("wrap-dropdown")) {
            if ($(target).hasClass("serviceItem")) {
              $(".dropdown-info-addon").hide();
            } else if ($(target).hasClass("mini") || $(target.parentNode).hasClass("mini")) {
              $(".dropdown-info-service").hide();
            }
            clickedOnItem = true;
            break;
          }

          if ($(target.parentNode).hasClass("ccjs-csc-diagram-wrapper") || $(target.parentNode).hasClass("input-group-btn")) {
            clickOnCard = true;
            break;
          }

            target = target.parentNode;
        }
        if (!clickedOnItem) {
          $(".dropdown-info-service").hide();
          $(".dropdown-info-addon").hide();
        }
        if (!clickOnCard) {
          $(".ccjs-csc-diagram-wrapper").removeClass("ccjs-active");
          $(".ccjs-csc-help").removeClass("ccjs-active");
        }
      }
    };
    BuyPage.setTotalBill = function(total){
        _totalBill = total;
    };
    BuyPage.setPlanDefaultSelected = function (id,name,price,user,planCycle) {
        _planSelected = {"id":id,"name":name,"price":price,"planCycle":planCycle};
        _addonUserDefault = user;
    };


    BuyPage.init = function () {
        _planContainerDOM = $(".buypage-plans");
        _addonsContainerDOM = $(".buypage-addons");
        _billContainerDOM = $(".buypage-bill");
        _addEventClickOutSide();
        _loadActivePlans();
        _addEvent2BtnSubmitDiscount();
        _addEventClick2PlanTypeItem();
        _addEventClick2AddonItem();
        _addEventClick2ServiceItem();
        _addEvent2LinkRemoveAddon();
        _addEvent2LinkRemoveService();
        _addEvent2BtnSubscribe();
        _addEvent2LinkRemoveDiscount();
        _initValidFormFields();
        _addEvent2CheckTandC();

    };

    return window.BuyPage = BuyPage;

})($);