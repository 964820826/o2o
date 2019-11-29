$(function () {
    getHeadLine();
})
//获取头条列表
var headLineUrl = '/headLine';
function getHeadLine() {
    $.getJSON(headLineUrl,function (data) {
        //todo
    })
}
