$(function () {
    //获取头条列表
    getHeadLineList();

    //获取一级店铺类别
    getShopCategory();

});

//获取头条的url
var headLineUrl = '/headLine';
//获取一级店铺类别的url
var topShopCategory = '/shopCategory/list';

//获取头条列表
function getHeadLineList() {
    $.getJSON(headLineUrl, function (data) {
        if (data.code == 0) {//成功
            var headLineList = data.data;
            var swiperHtml = '';
            headLineList.forEach(function (item) {
                swiperHtml +=
                    '<div class="swiper-slide img-wrap">' +
                    '<a href="' + item.headLineLink + '" external>' +
                    '<img class="banner-img" src="' + item.headLineImg + '" alt="' + item.headLineName + '">' +
                    '</a>' +
                    '</div>';
            })
            //将拼接好的html语句设置到页面中
            $(".swiper-wrapper").html(swiperHtml);
            //设置轮播
            $(".swiper-container").swiper({
                autoplay: 3000,
                // 用户对轮播图进行操作时，是否自动停止autoplay
                autoplayDisableOnInteraction: false
            });
        }
    })
}

//获取店铺类别列表
function getShopCategory() {
    $.getJSON(topShopCategory,function(data){
        if (data.code == 0){
            var shopCategoryList = data.data;
            var categoryHtml = '';
            shopCategoryList.forEach(function (item) {
                categoryHtml +=
                    '<div class="col-50 shop-classify" data-category=' + item.shopCategoryId + '>' +
                        '<div class="word">' +
                            '<p class="shop-title">' + item.shopCategoryName + '</p>' +
                            '<p class="shop-desc">' + item.shopCategoryDesc + '</p>' +
                        '</div>' +
                        '<div class="shop-classify-img-warp">' +
                            '<img class="shop-img" src="' + item.shopCategoryImg + '">' +
                        '</div>' +
                    '</div>'
            })
            $(".row").html(categoryHtml);
        }
    })
}

//点击店铺类别调整到店铺列表
$('.row').on('click', '.shop-classify', function (e) {
    var shopCategoryId = e.currentTarget.dataset.category;
    //将选取的店铺类别id设置到session中
    // sessionStorage.setItem("shopCategoryId",shopCategoryId);
    // var newUrl = '/shoplist';
    var newUrl = '/shop/listShop?shopCategoryId=' + shopCategoryId;
    window.location.href = newUrl;
});

// 若点击"我的"，则显示侧栏
$('#me').click(function () {
    $.openPanel('#panel-right-demo');
});