$(function () {
    //分页获取商品列表
    getProductList();
});

//获取url中的店铺id
var shopId = getQueryString("shopId");
//获取token
var token = getToken();
//分页参数（一次全部取出，最多500条）
var pageIndex = 1;
var pageSize = 500;

//获取商品列表的url
var getProductListUrl = "/product/list";
//删除商品的url
var deleteProductUrl = "/product";

//获取商品列表
function getProductList() {
    var url = getProductListUrl + '?shopId=' + shopId + '&pageIndex=' + pageIndex + '&pageSize=' + pageSize;
    $.ajax({
        url:url,
        type:'GET',
        headers:{
            Authorization:"Bearer " + token
        },
        cache: false,
        success:function (data) {
            if (data.code == 0){
                var productList = data.data.records;
                var productListHtml = '';
                productList.forEach(function (item) {
                    productListHtml +=
                    '<div class="row row-product">'+
                        '<div class="col-40">' + item.productName + '</div>'+
                        '<div class="col-20">' + item.priority + '</div>'+
                        '<div class="col-40">'+
                            '<a href="/product/modify?productId='+item.productId + '">编辑</a>'+
                            '<a href="javascript:deleteProduct(' + item.productId + ')">删除</a>'+
                            '<a href="/product/detail?productId=' + item.productId + '">预览</a>'+
                        '</div>'+
                    '</div>'
                })
                $('.product-wrap').html(productListHtml);
            }else {
                $.toast("获取商品信息失败");
            }
        }
    })
}

//删除商品
function deleteProduct(productId) {
    $.ajax({
        url: deleteProductUrl,
        type: 'POST',
        data: {
            _method: "DELETE",
            productId: productId
        },
        headers: {
            Authorization: "Bearer " + token
        },
        cache: false,
        success: function (data) {
            if (data.code == 0){
                getProductList();
                $.toast("删除成功");
            }else {
                $.toast("删除失败 " + data.massage);
            }
        }
    });
}