
//添加商品的url
var addProductUrl = '/product';
//获取token
var token = getToken();

//提交事件
$('#submit').click(function () {
   var form = $('#form');
   var formData = new FormData(form);
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