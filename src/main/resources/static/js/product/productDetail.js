$(function() {
	//初始化
	$.init();
	//获取商品信息
	getProduct();
});
// 从地址栏的URL里获取productId
var productId = getQueryString('productId');
// 获取商品信息的URL
var productDetailUrl = '/product/' + productId;
// 访问后台获取该商品的信息并渲染
function getProduct(){
	$.getJSON(productDetailUrl,function(data) {
		if (data.code == 0){
			//获取商品信息
			var product = data.data;
			var imgPath = '/image/2019122200372840204.png';
			// 商品缩略图
			$('#product-img').attr('src','/image' + product.productThum);
			// 商品更新时间
			$('#last-edit-time').text(new Date(product.lastEditTime).format("yyyy-MM-dd"));
			if (product.point !== undefined) {
				$('#product-point').text('购买可得' + product.point + '积分');
			}
			// 商品名称
			$('#product-name').text(product.productName);
			// 商品简介
			$('#product-desc').text(product.productDesc);
			// 商品价格展示逻辑，主要判断原价现价是否为空 ，所有都为空则不显示价格栏目
			if (product.normalPrice !== undefined && product.discountPrice !== undefined) {
				// 如果现价和原价都不为空则都展示，并且给原价加个删除符号
				$('#price').show();
				$('#normalPrice').html('<del>' + '￥' + product.normalPrice + '</del>');
				$('#discountPrice').text('￥' + product.discountPrice);
			} else if (product.normalPrice !== undefined && product.discountPrice === undefined) {
				// 如果原价不为空而现价为空则只展示原价
				$('#price').show();
				$('#discountPrice').text('￥' + product.normalPrice);
			} else if (product.normalPrice === undefined && product.discountPrice !== undefined) {
				// 如果现价不为空而原价为空则只展示现价
				$('#discountPrice').text('￥' + product.discountPrice);
			}
				var imgListHtml = '';
			// 遍历商品详情图列表，并生成批量img标签
			product.productImgList.map(function(item, index) {
				imgListHtml += '<div> <img src="/image'+ item.productImgAddr + '" width="100%" /></div>';
			});
			$('#imgList').html(imgListHtml);
				// // 2.0新增
				// if (data.needQRCode) {
				// 	// 若顾客已登录，则生成购买商品的二维码供商家扫描
				// 	imgListHtml += '<div> <img src="/o2o/frontend/generateqrcode4product?productId='
				// 		+ product.productId
				// 		+ '" width="100%"/></div>';
				// }
		}
	});
}
// 点击后打开右侧栏
$('#me').click(function() {
	$.openPanel('#panel-right-demo');
});
