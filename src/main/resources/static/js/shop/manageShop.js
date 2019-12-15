$(function () {
    var token = localStorage.getItem("token");
    //获取当前用户的店铺
    var currentUserShop = "/shop/currentUser";
    $.getJSON(currentUserShop,function (data) {
        if (data.code == 0){
            $("#shopInfo").href("/shop/"+ data.data.shopId);
        }else {
            //无可操作店铺，跳转到首页
        }
    })
});