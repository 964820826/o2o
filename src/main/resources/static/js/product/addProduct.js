$(function () {
    categoryOption();
});

//添加商品的url
var addProductUrl = '/product';
//获取token
var token = getToken();
//获取商品类别的url
var getProductCategory = '/productCategory/list';

//生成商品类别下拉框
function categoryOption() {
    $.getJSON(getProductCategory, function (data) {
        if (data.code === 0) {
            //后台获取到的商品类别列表
            var productCategoryList = data.data;
            var productCategoryHtml = '<option value = "">选择商品类别</option>';
            productCategoryList.forEach(function (item) {
                productCategoryHtml += '<option value = "' + item.productCategoryId + '">' + item.productCategoryName + '</option>';
            });
            $("#category").html(productCategoryHtml);
        }
    })
}

//提交事件
$('#submit').click(function () {
   paramCheck();
   var form = $('#form');
   var formData = new FormData(form);
   return;
   $.ajax({
       url: addProductUrl,
       type: 'POST',
       data: formData,
       headers: {
           Authorization: 'Bearer ' + token
       },
       contentType: false,//要传参数要传文件，所以用false
       processData: false,
       cache: false,
       success: function (data) {
           if (data.code == 0){
               $.toast('添加成功');
               setTimeout(function () {
                   history.back(-1);
               },1000)
           }
       }
   })
});

function paramCheck() {
    if ($('#category').val() == ""){
        $.toast("请选择商品类别");
    }
}