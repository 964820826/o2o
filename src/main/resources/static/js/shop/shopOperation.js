$(function () {
    //获取店铺初始信息
    var initUrl = '/shopAdmin/shopInitInfo';
    //注册店铺
    var registerShopUrl = '/shopAdmin/shop';

    alert(initUrl);
    //页面加载时自动调用方法
    getShopInitInfo();

    //获取店铺初始信息（下拉框、原参数）
    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {//访问initUrl,返回的数据为data
            if (data.success){
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function (item, index){//遍历shopCategoryList，生成下拉框
                    tempHtml += '<option data-id="' + item.shopCagetoryId + '">' + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
                });
                //将js生成的下拉列表填充到前端
                $('#shopCategory').html(tempHtml);
                $('#areaCategory').html(tempAreaHtml);
            }
        });

        //点击提交时执行
        $('#submit').click(function () {
            var shop = {};
            shop.shopName = $('#shopName').val();
            shop.shopAddr = $('#shopAddr').val();
            shop.shopPhone = $('#shopPhone').val();
            shop.shopDesc = $('#shopDesc').val();
            //从下拉列表中获取所选项
            shop.shopCategory = {
                shopCategoryId : $('#shopCagetory').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            shop.area = {
                area : $('#area').find('option').not(function () {
                return !this.selected;
                }).data('id')
            };
            //获取上传的图片
            var shopImg = $('#shopImg')[0].files[0];
            //将图片添加到表单里
            var formData = new formData();
            formData.append('shopImg',shopImg);
            formData.append('shopStr',JSON.stringify(shop));

            //通过ajax与后台交互
            $.ajax({
                url: registerShopUrl,
                type: 'POST',
                data: formData,
                contentType: false,//要传参数要传文件，所以用false
                proceesData: false,
                cache: false,
                success: function (data) {
                    if (data.success){
                        $.toast('提交成功！');
                    }else{
                        $.toast('提交失败！' + data.errMsg);
                    }
                }
            })
        })
    }
})