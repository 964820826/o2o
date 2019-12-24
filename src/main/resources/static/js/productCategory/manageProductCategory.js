$(function () {
    //获取商品类别列表
    getProductCategoryList();
});


//获取店铺id
var shopId = getQueryString("shopId");
//获取商品类别列表url
var productCategoryListUrl = '/productCategory/list';

//获取商品类别列表
function getProductCategoryList() {
    var url = productCategoryListUrl + '?shopId=' + shopId;
    $.getJSON(url, function (data) {
        if (data.code == 0) {
            var productCategoryList = data.data;
            var productCategoryListHtml = '';
            productCategoryList.forEach(function (item) {
                productCategoryListHtml +=
                    '<div class="row row-product-category">' +
                        '<div class="col-40">' + item.productCategoryName + '</div>' +
                        '<div class="col-20">' + item.priority + '</div>' +
                        '<div class="col-40">' +
                        '<a href="/productCategory/modify?productCategoryId=' + item.productCategoryId + '">编辑</a>' +
                        '<a href="javascript:deleteProductCategory(' + item.productCategoryId + ')">删除</a>' +
                        '</div>' +
                    '</div>'
            })
            $('.category-wrap').html(productCategoryListHtml);
        } else {
            $.toast("获取商品信息失败");
        }
    })
}