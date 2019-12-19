$(function () {
    // 初始化页面
    $.init();

    //加载可选店铺类别
    getShopCategory(shopCategoryId);
    //加载区域选项
    getAreaList();
    //预加载一页的店铺列表
    showItems(pageIndex,pageSize);
});

//当前页面是否在加载中
var loading = false;
//页码
var pageIndex = 1;
//一页最多显示条数
var pageSize = 5;
//获取店铺列表的url
var shopListUrl = '/shop/list';
//获取店铺类别的url
var shopCategoryListUrl = '/shopCategory/list';
//获取区域列表的url
var areaListUrl = '/area/list';
//店铺类别查询条件(请求地址中获取类别id)
var shopCategoryId = getQueryString("shopCategoryId");
//列表最多加载多少店铺信息
var maxItems = 500;
//店铺名模糊查询条件
var shopName;
//区域查询条件
var areaId;

// 加载可选店铺类别
function getShopCategory(id) {
    var url = shopCategoryListUrl;
    if (id != null){
        url += '?parentId=' + id;
    }
    $.getJSON(url, function (data) {
        if (data.code == 0){
            //后台获取到的店铺类别列表
            var shopCategoryList = data.data;
            var shopCategoryHtml = '<a href="#" class="col-33 button" data-category-id="">全部类别</a>';
            shopCategoryList.forEach(function (item) {
                shopCategoryHtml += '<a href="#" class="col-33 button" data-category-id=' + item.shopCategoryId + '>' + item.shopCategoryName + '</a>';
            });
            $("#shoplist-search-div").html(shopCategoryHtml);
        }
    })
}

// 获取区域列表
function getAreaList() {
    $.getJSON(areaListUrl,function (data) {
        if (data.code == 0){
            //后台获取到的区域列表
            var areaList = data.data;
            var areaHtml = '<option value = "">全部区域</option>';
            areaList.forEach(function (item) {
                areaHtml += '<option value = "' + item.areaId + '">' + item.areaName + '</option>';
            });
            $("#area-search").html(areaHtml);
        }
    })
}

// 获取分页展示的店铺信息
function showItems(index, size) {
    //拼接分页查询店铺列表的url
    var shopPageUrl = shopListUrl + '?pageIndex=' + index + '&pageSize=' + size;
    if (shopCategoryId != null){
        shopPageUrl += '&shopCategoryId=' + shopCategoryId;
    }
    if (shopName != null){
        shopPageUrl += '&shopName=' + shopName;
    }
    if (areaId != null){
        shopPageUrl +='&areaId=' + areaId;
    }
    //设置加载符，若正在加载中就不能再次访问后台
    loading = true;
    //访问后台
    $.getJSON(shopPageUrl,function (data) {
        if (data.code == 0){
            //取出分页查询到的店铺列表信息
            var shopList = data.data.records;
            if (shopList.length == 0){
                $.toast("未查询到结果");
            }else {
                var shopListHtml = '';
                shopList.forEach(function (item) {
                    shopListHtml +=
                        '<div class="card" data-shop-id="' + item.shopId + '">' +
                            '<div class="card-header">' + item.shopName + '</div>' +
                            '<div class="card-content">' +
                                '<div class="list-block media-list">' +
                                    '<ul>' +
                                        '<li class="item-content">' +
                                            '<div class="item-media">' +
                                                '<img src="' + item.shopImg + '" width="44">' +
                                            '</div>' +
                                            '<div class="item-inner">' +
                                                '<div class="item-subtitle">'+ item.shopDesc + '</div>' +
                                            '</div>' +
                                        '</li>' +
                                    '</ul>' +
                                '</div>' +
                            '</div>' +
                            '<div class="card-footer">' +
                                '<p class="color-gray">' + new Date(item.lastEditTime).format("yyyy-MM-dd") + '更新</p>' +
                                '<span>点击查看</span>' +
                            '</div>' +
                        '</div>'
                });
                //将生成的html拼接到页面的店铺列表末尾
                $(".shop-list").append(shopListHtml);
            }
            //获取已加载的长度，最多只允许加载500条
            var total = $(".shop-list>.card").length;
            var dbTotal = data.data.total;
            if (shopList.length == 0 || total>=dbTotal){
                //加载完成则注销无限加载事件，防止不必要的加载
                $.detachInfiniteScroll($(".infinite-scroll"));
                //隐藏加载提示符
                $(".infinite-scroll-preloader").hide();
            }else {
                $(".infinite-scroll-preloader").show();
                //页面还可加载，则页码+1
                pageIndex += 1;
            }
            //设置加载符为false
            loading = false;
            //刷新页面，显示新加载的店铺
            $.refreshScroller();
        }
    })
}

//下滑屏幕自动进行分页搜索
$(document).on('infinite','.infinite-scroll-bottom',function () {
    if (loading){
        return;
    }
    showItems(pageIndex,pageSize);
});

//点击店铺类别清空原先列表，重置页面，按照新的条件去查询
$(".shoplist-button-div").on("click",".button",function (obj) {
    //使无极滚动生效
    $.attachInfiniteScroll($(".infinite-scroll"));
    shopCategoryId = obj.currentTarget.dataset.categoryId;
    //重新加载可选店铺类别列表
    getShopCategory(shopCategoryId);
    $('.list-div').empty();
    pageIndex = 1;
    showItems(pageIndex,pageSize);
});

//选择区域清空原列表，重置页面
$('#area-search').on("change",function () {
    //使无极滚动生效
    $.attachInfiniteScroll($(".infinite-scroll"));
    areaId = $('#area-search').val();
    $('.list-div').empty();
    pageIndex = 1;
    showItems(pageIndex,pageSize);
});

//输入关键字模糊查询店铺名
$('#search').on('change',function () {
    //使无极滚动生效
    $.attachInfiniteScroll($(".infinite-scroll"));
    shopName = $('#search').val();
    $('.list-div').empty();
    pageIndex = 1;
    showItems(pageIndex,pageSize);
});

//点击卡片进入对应店铺的详情页
$('.list-div ').on('click','.card',function (obj) {
    var shopId = obj.currentTarget.dataset.shopId;
    window.location.href = '/shop/detail?shopId=' + shopId;
});

// 点击后打开右侧栏
$('#me').click(function() {
    $.openPanel('#panel-right-demo');
});