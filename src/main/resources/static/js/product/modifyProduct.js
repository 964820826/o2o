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
//获取token
var token = getToken();
//从url中获取商品id
var productId = getQueryString("productId");

//生成商品类别下拉框
function categoryOption() {
    $.getJSON(getProductCategory, function (data) {
        if (data.code === 0){
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
        if (data.code == 0){
            //获取商品信息
            var product = data.data;
            //商品名
            $('#product-name').val(product.productName);
            //设置商品类别选项
            $("#category option[value = '"+product.productCategoryId+"']").attr("selected",true);
            //优先级
            $('#priority').val(product.priority);
            //积分
            $('#point').val(product.point);
            //原价
            $('#normal-price').val(product.normalPrice);
            //折扣价
            $('#discountPrice').val(product.discountPrice);

            //商品描述
            $('#product-desc').val(product.productDesc);


            $.toast("修改成功");
        }
    })
}