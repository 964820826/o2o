$(function () {
    //获取店铺类别选项
    getShopCategory();
    //获取区域选项
    getAreaList();
    //获取店铺信息
    getShopInfo();
});

//获取token
var token = localStorage.getItem('token');

//获取店铺信息url(get)
var getShopInfoUrl = '/shop';
//获取二级店铺类别url
var childShopCategoryUrl = '/shopCategory/child';
//获取区域列表url
var areaListUrl = '/area/list';
//修改店铺信息url(put)
var modifyShopUrl = '/shop';

// 加载可选店铺类别
function getShopCategory() {
    $.getJSON(childShopCategoryUrl, function (data) {
        if (data.code === 0){
            //后台获取到的店铺类别列表
            var shopCategoryList = data.data;
            var shopCategoryHtml = '';
            shopCategoryList.forEach(function (item) {
                shopCategoryHtml += '<option value = "' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>';
            });
            $("#shopCategory").html(shopCategoryHtml);
        }
    })
}

// 获取区域列表
function getAreaList() {
    $.getJSON(areaListUrl, function (data) {
        if (data.code == 0) {
            //后台获取到的区域列表
            var areaList = data.data;
            var areaHtml = '';
            areaList.forEach(function (item) {
                areaHtml += '<option value = "' + item.areaId + '">' + item.areaName + '</option>';
            });
            $("#area").html(areaHtml);
        }
    })
}

//获取店铺信息
function getShopInfo() {
    //通过ajax与后台交互
    $.ajax({
        url: getShopInfoUrl,
        type: 'GET',
        headers:{
            Authorization: 'Bearer ' + token
        },
        contentType: false,//要传参数要传文件，所以用false
        processData: false,
        cache: false,
        success: function (data) {
            if (data.code == 0){
                var shop = data.data;
                $('#shopId').val(shop.shopId);
                $('#shopName').val(shop.shopName);
                // $('#shopCategory').val(shop.shopCategory);
                $('#area').val(shop.area);
                $('#shopAddr').val(shop.shopAddr);
                $('#shopPhone').val(shop.shopPhone);
                $('#shopDesc').val(shop.shopDesc);
                $("#shopCategory option[value = '"+shop.shopCategoryId+"']").attr("selected",true);
                $("#area option[value = '"+shop.areaId+"']").attr("selected",true);
            } else if (data.code == 5002 || data.code ==5004) {
                //未登陆或无操作权限，返回上一页
                window.history.go(-1);
            } else {
                $.toast(data.massage);
            }

        }
    })
}

//点击提交时执行
$('#submit').click(function () {
    if ($("#captcha").val() == null || $("#captcha").val() == ''){
        $.toast("请输入验证码");
        return;
    }
    var file = document.getElementById("shopImg").files[0];
    var form = document.getElementById("form");
    var formData = new FormData(form);
    formData.append("newImg",file);
    $("#captcha").click();
    //todo 校验上传的图片格式是否正确
    //通过ajax与后台交互
    $.ajax({
        url: modifyShopUrl,
        type: 'POST',
        data: formData,
        headers:{
            Authorization: 'Bearer ' + token
        },
        contentType: false,//要传参数要传文件，所以用false
        processData: false,
        cache: false,
        success: function (data) {
            if (data.code == 0){
                $.toast('提交成功！');
            }else{
                $.toast('提交失败！' + data.massage);
            }
            $("#captcha").val("");
        }
    });
});