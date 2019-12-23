$(function () {
    //生成商品类别下拉框
    categoryOption();
    //初始化要操作的商品信息
    getProduct();
});

//获取商品信息的url
var getProductUrl = '/product';
//获取商品类别的url
var getProductCategory = '/productCategory/list';
//修改店铺的url
var modifyProductUrl = '/product';
//获取token
var token = getToken();
//从url中获取商品id
var productId = getQueryString("productId");

//生成商品类别下拉框
function categoryOption() {
    $.getJSON(getProductCategory, function (data) {
        if (data.code === 0) {
            //后台获取到的商品类别列表
            var productCategoryList = data.data;
            var productCategoryHtml = '';
            productCategoryList.forEach(function (item) {
                productCategoryHtml += '<option value = "' + item.productCategoryId + '">' + item.productCategoryName + '</option>';
            });
            $("#category").html(productCategoryHtml);
        }
    })
}

//获取商品信息
function getProduct() {
    var url = getProductUrl + '/' + productId;
    $.getJSON(url, function (data) {
        if (data.code == 0) {
            //获取商品信息
            var product = data.data;
            //商品id
            $('#productId').val(product.productId);
            //商品名
            $('#product-name').val(product.productName);
            //设置商品类别选项
            $("#category option[value = '" + product.productCategoryId + "']").attr("selected", true);
            //优先级
            $('#priority').val(product.priority);
            //积分
            // $('#point').val(product.point);
            //原价
            $('#normal-price').val(product.normalPrice);
            //折扣价
            $('#discountPrice').val(product.discountPrice);
            //商品描述
            $('#product-desc').val(product.productDesc);
        }
    })
}

//提交
$('#submit').click(function () {
    if ($('#captcha').val() === ""){
        $.toast("请输入验证码");
        return;
    }
    var form = document.getElementById("form");
    var formData = new FormData(form);
    //检验上传的文件是否合法
    var imgThum = document.getElementById("small-img").files[0];
    if (!fileCheck(imgThum)){
        return;
    }
    var detailImgList = document.getElementById("detail-img").files;
    for(var i=0;i<detailImgList.length;i++){
        if (!fileCheck(detailImgList[i])){
            return;
        }
    }
    $.ajax({
        url: modifyProductUrl,
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

                $.toast("修改成功");
            }
        }
    });
});

//校验文件
function fileCheck(file) {
    if (file !== undefined && !isImg(file.name)){
        $.toast("只能上传图片！");
        return false;
    }
    if (file !== undefined && file.length > 30*1024*1024){
        $.toast("最大上传30M的图片");
        return false;
    }
    return true;
}