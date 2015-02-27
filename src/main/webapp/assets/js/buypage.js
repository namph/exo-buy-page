(function($){
  var _planContainerDOM;
  var _addonsContainerDOM;
  var _baseRestUrl = "/BuyPage/rest";
  var _planSelected;
  var _addonsSelected = new Array();
  var _discountProvided;

  var BuyPage = {};

  var _loadActivePlans = function(){
    $.ajax({
      url: _baseRestUrl+"/Plan/getActives",
      dataType: "text"
    })
      .done(function(data) {
        _planContainerDOM.html(data);
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
      _planSelected = {'id':id,'name':name,'price':price};
      console.info(_planSelected);
    });
  };
  var _addEventClick2AddonItem = function(){
    $(document).on('click.addonItem.select','div.addonItem',function(){
      var me = $(this);
      if(me.hasClass("selected")){

      }
      var id = me.attr("data-id");
      var name = me.attr("data-name");
      var price = me.attr("data-price");
      _planSelected = {'id':id,'name':name,'price':price};
      console.info(_planSelected);
    });
  };

  BuyPage.init = function () {
    _planContainerDOM = $(".buypage-plans");
    _addonsContainerDOM = $(".buypage-addons");
    _loadActivePlans();
    _loadActiveAddons(null);
    _addEvent2BtnSubmitDiscount();
    _addEventClick2PlanTypeItem();
    _addEventClick2AddonItem();
  };

  return window.BuyPage = BuyPage;

})($);