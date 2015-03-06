(function($){
  var _planContainerDOM;
  var _addonsContainerDOM;
  var _billContainerDOM;
  var _baseRestUrl = "/BuyPage/rest";
  var _planSelected;
  var _listAddonsSelected = new Array();
  var _listServicesSelected = new Array();
  var _discountProvided;
  var _addonUserDefault = 0;
  var BuyPage = {};
  BuyPage.tata = "";
  var _loadActivePlans = function(){
    $.ajax({
      url: _baseRestUrl+"/Plan/getActives",
      dataType: "text"
    })
      .done(function(data) {
        _planContainerDOM.html(data);
        _loadBillFromClient();
        _loadActiveAddons(null);

        $("#slider-number-users").slider({
          ticks: [1, 2, 3, 4],
          ticks_labels: ['25 users', '50 users', '100 users', '250 users'],
          ticks_snap_bounds: 1,
          min: 0,
          max: 4,
          tooltip: 'always',
          formatter: function(value) {
            var numberUsers = 0;
            if(value == "1") {
              numberUsers = "25 users";
            } else if(value == "2") {
              numberUsers = "50 users";
            } else if(value == "4") {
              numberUsers = "250 users";
            } else {
              numberUsers = "100 users";                
            }
            return numberUsers;
          }
        });

      })
      .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ', ' + error;
        console.log("Transaction Failed: " + err);
      });
  };
  var _loadActiveAddons = function(planId){
    $.ajax({
      url: _baseRestUrl+"/Addons/getActives/"+planId,
      dataType: "text"
    })
      .done(function(data) {
        _addonsContainerDOM.html(data);
        _hideAllAddonsButOne();
      })
      .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ', ' + error;
        console.log("Transaction Failed: " + err);
      });
  };
  var _getDiscount = function(discountId){
    $.ajax({
      url: _baseRestUrl+"/Discount/get/"+discountId,
      dataType: "text"
    })
    .done(function(data) {
      var obj = jQuery.parseJSON(data);
       if(typeof data !== undefined){
         _discountProvided = {"id":obj.id,"name":obj.name,"description":obj.description,"amount":obj.amount};
         _loadBillFromClient();
       }
    })
    .fail(function (jqxhr, textStatus, error) {
      var err = textStatus + ', ' + error;
      console.log("Transaction Failed: " + err);
    });
  };
  var _addEvent2BtnSubmitDiscount = function(){
    $(document).on('click.discount.submit','button#btnSubmitDiscount',function(){
      _discountProvided = null;
      var discountId = $("#discountId").val();
      if (typeof discountId !== undefined && discountId.length > 0 ){
        _getDiscount(discountId);
      }
    })
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
        _loadBillFromClient();
        _hideAllAddonsButOne();
      }
    });
  };
  var _addEventClick2AddonItem = function(){
    $(document).on('click.addonItem.select','div.addonItem',function(){
      var me = $(this);
      var parent = me.parent();
      var id = me.attr("data-id");
      var name = me.attr("data-name");
      var price = me.attr("data-price");
      var obj = {id:id,name:name,price:price};
      if(parent.hasClass("selected")){
        parent.removeClass("selected");
        _removeAddonFromListSelected(id);
      }else{
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
        _removeServiceFromListSelected(id);
      }else{
        parent.addClass("selected");
        _addService2ListSelected(obj);
      }
      if(typeof _discountProvided !== undefined && null != _discountProvided){
        _getDiscount(_discountProvided.id);
      }else{
        _loadBillFromClient();
      }
    });
  };
  var _addAddon2ListSelected = function(obj){
    if (_checkItemExistsInList(obj.id,_listAddonsSelected) == null)
      _listAddonsSelected.push(obj);
  };
  var _removeAddonFromListSelected = function (id) {
    var pos = _checkItemExistsInList(id,_listAddonsSelected);
    if ( pos != null)
      _listAddonsSelected.splice(pos,1);
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
      })
      .fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ', ' + error;
        console.log("Transaction Failed: " + err);
      });
  };
  var _addEvent2BtnSubmitDiscount = function(){
    $(document).on('click.discount.submit','button#btnSubmitDiscount',function(){
      var discountId = $("#discountId").val();
      if (typeof discountId !== undefined && discountId.length > 0 ){
        _getDiscount(discountId);
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
  var _addEvent2BtnSubscribe = function(){
    $(document).on('click.subscribe.submit','button.subscribe', function () {
      var subscriptionCustomer = _validateBillingForm();
      if (subscriptionCustomer == null){
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
      var planId = _planSelected.id;

      var discountCode = null;
      if(typeof _discountProvided !== undefined && null != _discountProvided)
        discountCode = _discountProvided.id;
      var addonIds = new Array();
      for(var i=0;i<_listAddonsSelected.length;i++){
        addonIds.push(_listAddonsSelected[i].id);
      }
      for(var i=0;i<_listServicesSelected.length;i++){
        addonIds.push(_listServicesSelected[i].id);
      }

      var data = {"firstName":firstName,"lastName":lastName,"organization":organization,"phone":phone,"email":email,"cardNumber":cardNumber,"cardHolder":cardHolder,"expireMonth":expireMonth,"expireYear":expireYear,"cardCVV":cardCVV,"planId":planId,"addonIds":addonIds,"discountCode":discountCode};
      data = JSON.stringify(data);
      $.ajax({
        url: _baseRestUrl+"/Subscribe/submit",
        dataType: "text",
        type:"POST",
        contentType:"application/json",
        data:data
      })
        .done(function(data) {
          console.info(data);
        })
        .fail(function (jqxhr, textStatus, error) {
          var err = textStatus + ', ' + error;
          console.log("Transaction Failed: " + err);
        });

    })
  };
  var _hideAllAddonsButOne = function(){
    $(".addon-bloc").hide();
    $(".addon-"+_addonUserDefault).show();
  };
  BuyPage.setPlanDefaultSelected = function (id,name,price,user,planCycle) {
    _planSelected = {"id":id,"name":name,"price":price,"planCycle":planCycle};
    _addonUserDefault = user;
  };

  BuyPage.init = function () {
    _planContainerDOM = $(".buypage-plans");
    _addonsContainerDOM = $(".buypage-addons");
    _billContainerDOM = $(".buypage-bill");
    _loadActivePlans();
    _addEvent2BtnSubmitDiscount();
    _addEventClick2PlanTypeItem();
    _addEventClick2AddonItem();
    _addEventClick2ServiceItem();
    _addEvent2LinkRemoveAddon();
    _addEvent2LinkRemoveService();
    _addEvent2BtnSubscribe();
    _addEvent2LinkRemoveDiscount();
  };

  return window.BuyPage = BuyPage;

})($);