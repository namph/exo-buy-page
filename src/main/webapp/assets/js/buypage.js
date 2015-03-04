(function($){
  var _planContainerDOM;
  var _addonsContainerDOM;
  var _billContainerDOM;
  var _baseRestUrl = "/BuyPage/rest";
  var _planSelected;
  var _listAddonsSelected = new Array();
  var _listServicesSelected = new Array();
  var _discountProvided;

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
      console.info(data);
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

  var _addEventClick2PlanTypeItem = function(){
    $(document).on('click.planTypeItem.select','div.planTypeItem',function(){
      var me = $(this);
      var id = me.attr("data-id");
      var name = me.attr("data-name");
      var price = me.attr("data-price");
      _planSelected = {id:id,name:name,price:price};
      _loadBillFromClient();
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
      _loadBillFromClient();
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
      _loadBillFromClient();
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

    var data = {"plan":_planSelected,"addons":_listAddonsSelected,"services":_listServicesSelected,"discountId":"toto"};
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
      _loadBillFromClient();
    });
  };
  var _addEvent2LinkRemoveService = function(){
    $(document).on('click.service.remove','a.remove-serviceItem', function () {
      var me = $(this);
      var id = me.attr("data-id");
      $("#"+id).removeClass("selected");
      _removeServiceFromListSelected(id);

      _loadBillFromClient();
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
      var addonIds = new Array();
      for(var i=0;i<_listAddonsSelected.length;i++){
        addonIds.push(_listAddonsSelected[i].id);
      }
      for(var i=0;i<_listServicesSelected.length;i++){
        addonIds.push(_listServicesSelected[i].id);
      }

      var data = {"firstName":firstName,"lastName":lastName,"organization":organization,"phone":phone,"email":email,"cardNumber":cardNumber,"cardHolder":cardHolder,"expireMonth":expireMonth,"expireYear":expireYear,"cardCVV":cardCVV,"planId":planId,"addonIds":addonIds};
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

  BuyPage.setPlanDefaultSelected = function (id,name,price,users) {
    _planSelected = {"id":id,"name":name,"price":price,"users":users};
  };

  BuyPage.init = function () {
    _planContainerDOM = $(".buypage-plans");
    _addonsContainerDOM = $(".buypage-addons");
    _billContainerDOM = $(".buypage-bill");
    _loadActivePlans();
    _loadActiveAddons(null);
    _addEvent2BtnSubmitDiscount();
    _addEventClick2PlanTypeItem();
    _addEventClick2AddonItem();
    _addEventClick2ServiceItem();
    _addEvent2LinkRemoveAddon();
    _addEvent2LinkRemoveService();
    _addEvent2BtnSubscribe();
  };

  return window.BuyPage = BuyPage;

})($);