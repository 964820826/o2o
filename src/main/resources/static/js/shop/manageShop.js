$(function () {
    //获取当前用户的店铺的id
    getCurrentShopId();
});

//获取当前用户的token
var token = getToken();
//获取当前登陆用户的店铺信息的url
var getShopInfoUrl = '/shop';

//获取当前用户的店铺的id
function getCurrentShopId() {
    $.ajax({
        url:getShopInfoUrl,
        type:'GET',
        headers:{
            Authorization: 'Bearer ' + token
        },
        cache: false,
        success:function (data) {
            if (data.code == 0){
                $('#shopId').val(data.data.shopId);
                $('#manageProduct').attr("href","/product/listPage?shopId=" + data.data.shopId)
            } else if (data.code == 5002 || data.code ==5004) {
                //未登陆或无操作权限，返回上一页
                window.history.go(-1);
            } else {
                $.toast(data.massage);
            }
        }
    })
}