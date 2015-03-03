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

  var _addEvent2BtnSubscribe = function(){
    $(document).on('click.subscribe.submit','button.subscribe', function () {
      var firstName = 'fname';
      var lastName = "lname"
      var organization = "exo";
      var phone = "12345566";
      var email = "tuan@anh.com";
      var cardNumber = "$bt4|javascript_1_3_6$Q71IedNIO1dOEBnqIGrh576A7Dl6TQbRr8SpacJ34zNQrtGGNN+J3v4wE0ENp5cKYBug2b0oAiQzWKFMAftsCXI8BgUF0WRY1un5rlr5lgqVDQo1e7hsj2sGELj37KFsolpKLVODs04wf6wxgcKFraUcSVXshMOA2RvcOHX3Fo/XykhlK1OwuOCyW7nba5EDIAgZMoNFjiksp3Ek/6/1nMzFKIM/t8BQ0+0mIIuqdcNo58HSnq/6sEKs6ZPzv1zS8vJzlFs1raJqKd134ID0GsetFsFQeRWsVH9/nX8DcVmhBMnLJ6wNKtF4eZkMYQ9WPeKdcM6NLjv8ru3Liz/uZw==$zGEpX+9Jj1AU9HdGhk6FeTZ5FDor3TIGSRyvfPTHhiJbmAxQqcOAAcCZCj5fAHMA$yi4sIzV19lInJCkaqyjgyafESzu3EOvWe/CTlK/+i2I=";
      var cardHolder = "$bt4|javascript_1_3_6$F+y72SZFl8fsC6MLCp1tvIfdzHM86WMw4bdbH7pUe5fTTV0rDfRj6s+PImmURZLPq0kr+oNEP9lks1usui6ECmSnJu+3/w6ZMa54jgMhtFrfxpqgdKosEVPyVzwSdeMMPSigRYw1EV/gHbEdW/no8BqwJJe9N71FVpE0uUlEquQ1o2w+Ra+k9gS7HaBeHV+W+9bo4q3HwYNVQU0ODSy+BiXGOrCpDiwniJomuM7DJkPF6CIoUpommHHPGRwWs3RJfGfkOBVpIV5UcXoo1dfbzOFFuTpDovGVPD7KEM4lGppoDzNyzafaZQDBiZyDvY42D1e/94gtMwFYmCen0S9IIA==$77Mw9eAfcB5qMYTwGnGueoSWqAPVonMb5zXHUuM7aM0=$HratFPCBpjTs50zacMCJm7Md+K6wGupApYlDj2l38UM=";
      var expireMonth = 1;
      var expireYear = 2015;
      var cardCVV = "$bt4|javascript_1_3_6$m4Cvd8zuyyuyeabCqpEgTyQtAQ+WC1WJ0g6Is3un/Bv0RkgwUmMppgIBrWszNTtU/cPDKsepWyf6T15XyAXgKquqx5LfyX5dUfwZFalDiFh3rQVUAMxP+xyMMvandZjXOcqLALHwP2T4S6MNEpyzTcH2NEViBCSrX0HEFo6zYdLmn7kIvCa6i0YByHRMYlW5l8OP1YBp3cqKV3yy7KF67eLvI/+GSCeXnr0hI5glTjjO1iuAkaKq25G3JVReKfpyvH/ys8IeZzUbK3zzOhDqrz6tfeZaVX024AkpPtncweEKg/Z3WL+LDdaeRmF8Ht9lGwpDWdt25TAQ2dKASzjw0w==$/2E5MezfSRmWe0DOP82GY6UdeaqGQueYHLtl602bbvo=$MU5X+QMINpapI8Yqhrn5o9tIK3Y9AIRuSH/zjMkirqc=";
      var planId = _planSelected.id;

      var addons = new Array();
      for(var i=0;i<_listAddonsSelected.length;i++){
        addons.push(_listAddonsSelected[i].id);
      }
      for(var i=0;i<_listServicesSelected.length;i++){
        addons.push(_listServicesSelected[i].id);
      }

      var data = {"firstName":firstName,"lastName":lastName,"organization":organization,"phone":phone,"email":email,"cardNumber":cardNumber,"cardHolder":cardHolder,"expireMonth":expireMonth,"expireYear":expireYear,"cardCVV":cardCVV,"planId":planId,"addons":addons};
      data = JSON.stringify(data);
      $.ajax({
        url: _baseRestUrl+"/Bill/showFromClient",
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