(function($){
  var _planContainerDOM;
  var _baseRestUrl = "/BuyPage/rest";
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
  $(document).ready(function () {
    _planContainerDOM = $(".buypage-plans");
    _loadActivePlans();
    console.info("init js buy page")
  });
})($)