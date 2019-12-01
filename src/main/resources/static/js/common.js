function getQueryString(name) {
    //匹配字符串开头或者以&开头的，中间为任意长度除&号的部分，结尾以&或者字符串结尾结束;如&shopId=3
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    //查询字符串参数？之后的部分匹配正则表达式
    var r = window.location.search.substr(1).match(reg);
    if (typeof (r) != "number"){
        return null;
    }else {
        return r;
    }
}