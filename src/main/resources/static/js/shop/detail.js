$(function () {
// 初始化页面
    $.init();

    //加载可选商品类别
    getProductCategory(shopId);
    //预加载一页的店铺列表
    showItems(pageIndex,pageSize);
})

//当前页面是否在加载中
var loading = false;
//页码
var pageIndex = 1;
//一页最多显示条数
var pageSize = 5;
//获取商品列表的url
var productListUrl = '/product/list';
//获取商品类别的url
var productCategoryListUrl = '/productCategory/list';
//商品类别查询条件(请求地址中获取类别id)
var shopId = getQueryString("shopId");
//列表最多加载多少商品信息
var maxItems = 500;
//商品名模糊查询条件
var productName = '';
//商品类别查询条件
var productCategoryId = '';

// 加载可选店铺类别
function getProductCategory(id) {
    var url = productCategoryListUrl+ '?shopId=' + id;
    $.getJSON(url, function (data) {
        if (data.code == 0){
            //后台获取到的店铺类别列表
            var productCategoryList = data.data;
            var productCategoryHtml = '<a href="#" class="col-33 button" data-category-id="">全部类别</a>';
            productCategoryList.forEach(function (item) {
                productCategoryHtml += '<a href="#" class="col-33 button" data-category-id=' + item.productCategoryId + '>' + item.productCategoryName + '</a>';
            })
            $("#product-search-div").html(productCategoryHtml);
        }
    })
}

// 获取分页展示的商品信息
function showItems(index, size) {
    //拼接分页查询商品列表的url
    var productPageUrl = productListUrl + '?pageIndex=' + index + '&pageSize=' + size;
    if (productCategoryId != null & productCategoryId != ''){
        productPageUrl += '&productCategoryId=' + productCategoryId;
    }
    if (productName != null & productName != ''){
        productPageUrl += '&productName=' + productName;
    }
    //设置加载符，若正在加载中就不能再次访问后台
    loading = true;
    //访问后台
    $.getJSON(productPageUrl,function (data) {
        if (data.code == 0){
            //取出分页查询到的店铺列表信息
            var productList = data.data;
            var productListHtml = '';
            productList.forEach(function (item) {
                productListHtml +=
                     '<div class="card" data-product-id=' + item.productId + '>'
                        + '<div class="card-header">' + item.productName + '</div>'
                        + '<div class="card-content">'
                            + '<div class="list-block media-list">'
                                + '<ul>'
                                    + '<li class="item-content">'
                                        + '<div class="item-media">'
                                            + '<img src="' + item.imgAddr + '" width="44">'
                                        + '</div>'
                                        + '<div class="item-inner">'
                                            + '<div class="item-subtitle">' + item.productDesc + '</div>'
                                        + '</div>'
                                    + '</li>'
                                + '</ul>'
                            + '</div>'
                        + '</div>'
                        + '<div class="card-footer">'
                            + '<p class="color-gray">' + new Date(item.lastEditTime).format("yyyy-MM-dd") + '更新</p>'
                                + '<span>点击查看</span>'
                        + '</div>'
                    + '</div>';
            })
            //将生成的html拼接到页面的店铺列表末尾
            $(".product-list").append(productListHtml);
            //获取已加载的长度，最多只允许加载500条
            var total = $(".card").length;
            if (total >= maxItems || productList.length != size){
                //加载完成则注销无限加载事件，防止不必要的加载
                $.detachInfiniteScroll($(".infinite-scroll"));
                //删除加载提示符
                $(".infinite-scroll-preloader").remove();
            }else {
                //页面还可加载，则页码+1
                pageIndex += 1;
                //设置加载符为false
                loading = false;
                //刷新页面，显示新加载的店铺
                $.refreshScroller();
            }
        }
    })
}

//下滑屏幕自动进行分页搜索
$(document).on('infinite','.infinite-scroll-bottom',function () {
    if (loading){
        return;
    }
    showItems(pageIndex,pageSize);
})

//点击店铺类别清空原先列表，重置页面，按照新的条件去查询
$(".productlist-button-div").on("click",".button",function (obj) {
    productCategoryId = obj.currentTarget.dataset.categoryId;
    //重新加载可选店铺类别列表
    getShopCategory(productCategoryId);
    $('.list-div').empty();
    pageIndex = 1;
    showItems(pageIndex,pageSize);
})

//选择区域清空原列表，重置页面
$('#area-search').on("change",function () {
    areaId = $('#area-search').val();
    $('.list-div').empty();
    pageIndex = 1;
    showItems(pageIndex,pageSize);
})

//输入关键字模糊查询店铺名
$('#search').on('change',function () {
    productName = $('#search').val();
    $('.list-div').empty();
    pageIndex = 1;
    showItems(pageIndex,pageSize);
})

//点击卡片进入对应商品的详情页
$('.list-div ').on('click','.card',function (obj) {
    var productId = obj.currentTarget.dataset.productId;
    window.location.href = '/product/detail?productId=' + productId;
})

// 点击后打开右侧栏
$('#me').click(function() {
    $.openPanel('#panel-right-demo');
});