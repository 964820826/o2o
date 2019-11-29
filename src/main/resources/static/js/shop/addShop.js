$(function () {
    // //页面加载时自动调用方法
    // var shopId = getQueryString("shopId");
    // if (shopId != null){
    //     //修改店铺，获取店铺原信息
    //     getShopInitInfo(shopId);
    // } else {
        //新增店铺
        getShopInitInfo();
    // }
});

//获取店铺初始信息
var initUrl = '/shopAdmin/shopInitInfo';
//获取店铺分类
var shopCategoryUrl = '/shopCategory';
//获取区域分类
var areaUrl = '/area';
//注册店铺
var registerShopUrl = '/shop';

//获取店铺初始信息（下拉框、原参数）
function getShopInitInfo() {
    $.getJSON(shopCategoryUrl, function (data) {
        if (data.code == 0){
            var shopCategoryOption = '';
            var shopCategoryArr = data.data;
            for (var i=0; i<shopCategoryArr.length; i++){
                shopCategoryOption += '<option value = "' + shopCategoryArr[i].shopCategoryId + '">' + shopCategoryArr[i].shopCategoryName + '</option>';
            }
            $('#shopCategory').html(shopCategoryOption);
        }else {
            $.toast('获取初始信息失败!' + data.massage);
        }
    })
    $.getJSON(areaUrl, function (data) {
        if (data.code == 0){
            var areaOption = '';
            var areaArr = data.data;
            for (var i=0; i<areaArr.length; i++){
                areaOption += '<option value = "' + areaArr[i].areaId + '">' + areaArr[i].areaName + '</option>';
            }
            $('#area').html(areaOption);
        }else {
            $.toast('获取初始信息失败!' + data.massage);
        }
    });
}

//点击提交时执行
$('#submit').click(function () {
    var form = document.getElementById("form");
    var formData = new FormData(form);
    var shopName = formData.get("shopName");
    $("#captcha").click();
    //通过ajax与后台交互
    $.ajax({
        url: registerShopUrl,
        type: 'POST',
        data: formData,
        contentType: false,//要传参数要传文件，所以用false
        processData: false,
        cache: false,
        success: function (data) {
            if (data.code == 0){
                $.toast('提交成功！');
                $("#form input").each(function () {
                    $(this).val("");
                })
            }else{
                $.toast('提交失败！' + data.massage);
                $("#captcha").val("");
            }
        }
    });
    //更换验证码
    $("#captchaImg").click();
});

function getQueryString(name) {
    //匹配字符串开头或者以&开头的，中间为任意长度除&号的部分，结尾以&或者字符串结尾结束;如&shopId=3
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    //查询字符串参数？之后的部分匹配正则表达式
    var r = window.location.search.substr(1).match(reg);
    if (typeof (r) != "number"){
        return null;
    }else {
        return r;
    }
}