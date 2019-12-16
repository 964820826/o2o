//从请求中获取参数
function getQueryString(name) {
    //匹配字符串开头或者以&开头的，中间为任意长度除&号的部分，结尾以&或者字符串结尾结束;如&shopId=3
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    //查询字符串参数？之后的部分匹配正则表达式
    var r = window.location.search.substr(1).match(reg);
    if (r == null){
        return null;
    }else {
        return r[2];
    }
}

//日期格式化
Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
};

//从本地获取token
function getToken() {
    var token = localStorage.getItem("token");
    if (token){
        return token;
    }else {
        return null;
    }
}

//判断是否需要重新登陆
function testTokenFailed(code) {
    //若未登陆或token过期，则清除本地存放的token，并提示重新登陆
    if (code == 5004 || code == 5006){
        localStorage.clear();
        $.toast("登陆信息已过期，请重新登陆");
        setTimeout(function () {
            window.location.href = '/login';
        },1000)
    }
}

//检测是否登陆，根据登陆信息调整侧边栏显示内容
function checkLogin() {
    if (localStorage.getItem('token')){
        $(".login").show();
        if (localStorage.getItem('role') == 'consumer'){
            $(".customer").show();
        }else {
            $(".myShop").show();
        }
    }else {
        $(".unLogin").show();
    }
}

//登出，清除登陆信息
function logout() {
    localStorage.clear();
    location.reload();
}