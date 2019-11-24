$(function () {
    //页面加载时自动调用方法
    var shopId = getQueryString("shopId");
    if (shopId != null){
        //修改店铺，获取店铺原信息
        getShopInitInfo(shopId);
    } else {
        //新增店铺
        getShopInitInfo();
    }
})

//获取店铺初始信息
var initUrl = '/shopAdmin/shopInitInfo';
//注册店铺
var registerShopUrl = '/shopAdmin/shop';

//获取店铺初始信息（下拉框、原参数）
function getShopInitInfo() {
    $.getJSON(initUrl, function (data) {//访问initUrl,返回的数据为data
        if (data.code == 0){
            var shopCategoryOption = '';
            var areaOption = '';
            var shopCategoryList = data.data.shopCategoryDOList;
            for (var i = 0; i < shopCategoryList.length; i++) {
                shopCategoryOption += '<option value="' + shopCategoryList[i].shopCategoryId + '">' + shopCategoryList[i].shopCategoryName + '</option>';
            }
            // data.shopCategoryDOList.forEach(function (item, index){//遍历shopCategoryList，生成下拉框
            //     tempHtml += '<option data-id="' + item.shopCagetoryId + '">' + item.shopCategoryName + '</option>';
            // });
            var areaList = data.data.areaDOList;
            for (var i = 0; i < areaList.length; i++) {
                areaOption += '<option value="' + areaList[i].areaId + '">' + areaList[i].areaName + '</option>';
            }
            // data.areaDOList.map(function (item, index) {
            //     tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
            // });
            //将js生成的下拉列表填充到前端
            $('#shopCategory').html(shopCategoryOption);
            $('#area').html(areaOption);
        }else{
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
})

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