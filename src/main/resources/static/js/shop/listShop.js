$(function () {
    //加载可选店铺类别
    getShopCategory();
    //加载区域选项
    getAreaList();
})

//当前页面是否在加载中
var loading = false;
//页码
var pageNum = 1;
//一页最多显示条数
var pageSize = 10;
//获取店铺列表的url
var shopListUrl = '/shop/list';
//获取店铺类别的url
var shopCategoryListUrl = '/shopCategory/list';
//获取区域列表的url
var areaListUrl = '/area/list';
//从请求地址中获取类别id
var shopCategoryId = getQueryString("shopCategoryId");

/**
 * 加载可选店铺类别
 */
function getShopCategory() {
    if (shopCategoryId != null){
        shopCategoryListUrl = shopCategoryListUrl + '?shopCategoryId=' + shopCategoryId;
    }
    $.getJSON(shopCategoryListUrl, function (data) {
        if (data.code == 0){
            //后台获取到的店铺类别列表
            var shopCategoryList = data.data;
            var shopCategoryHtml = '<a href="#" class="col-33 button" data-category-id="">全部类别</a>';
            shopCategoryList.forEach(function (item) {
                shopCategoryHtml += '<a href="#" class="col-33 button" data-category-id=' + item.shopCategoryId + '>' + item.shopCategoryName + '</a>';
            })
            $("#shoplist-search-div").html(shopCategoryHtml);
        }
    })
}

/**
 * 获取区域列表
 */
function getAreaList() {
    $.getJSON(areaListUrl,function (data) {
        if (data.code == 0){
            //后台获取到的区域列表
            var areaList = data.data;
            var areaHtml = '<option value = "">全部区域</option>';
            areaList.forEach(function (item) {
                areaHtml += '<option value = "' + item.areaId + '">' + item.areaName + '</option>';
            })
            $("#area-search").html(areaHtml);
        }
    })
}

/**
 * 获取分页展示的店铺信息
 */
function showItems() {

}