// 注册url
var registerUrl = '/user/register';

//点击提交
$('#submit').click(function () {
    // 获取输入的帐号
    var username = $('#username').val();
    // 获取输入的密码
    var password = $('#psw').val();
    // 获取验证码信息
    var verifyCodeActual = $('#j_captcha').val();

    // 访问后台进行登录验证
    $.ajax({
        url: registerUrl,
        async: false,
        cache: false,
        type: "post",
        dataType: 'json',
        data: {
            username: username,
            password: password,
            captcha: verifyCodeActual,
        },
        success: function (data) {
            if (data.code == 0) {
                $.toast("注册成功！");
                //将token存到本地
                localStorage.setItem('token', data.data.token);
                localStorage.setItem('role', data.data.role);
            } else {
                $.toast("注册失败！");
            }
            //1秒后跳转到首页
            setTimeout(function () {
                window.location.href = '/index';
            },1000)
        }
    });
})







// 登录次数，累积登录三次失败之后自动弹出验证码要求输入
var loginCount = 0;

//点击提交
$('#submit').click(function () {
    // 获取输入的帐号
    var username = $('#username').val();
    // 获取输入的密码
    var password = $('#psw').val();
    // 获取验证码信息
    var verifyCodeActual = $('#j_captcha').val();
    // 是否需要验证码验证，默认为false,即不需要
    var needVerify = false;
    // 如果登录三次都失败
    if (loginCount >= 3) {
        // 那么就需要验证码校验了
        if (!verifyCodeActual) {
            $.toast('请输入验证码！');
            return;
        } else {
            needVerify = true;
        }
    }
    // 访问后台进行登录验证
    $.ajax({
        url: loginUrl,
        async: false,
        cache: false,
        type: "post",
        dataType: 'json',
        data: {
            username: username,
            password: password,
            verifyCodeActual: verifyCodeActual,
            //是否需要做验证码校验
            needVerify: needVerify
        },
        success: function (data) {
            if (data.code == 0) {
                $.toast('登录成功！');
                //将token存到本地
                localStorage.setItem('token', data.data.token);
                localStorage.setItem('role', data.data.role);
                //若登陆账户是店长或店员，跳转到店铺管理界面，否则条转到首页
                if (data.data.role == "shopManage" || data.data.role == "shopAssistant"){
                    window.location.href = '/shop/manage';
                } else {
                    window.location.href = '/index';
                }
            } else {
                $.toast('登录失败！' + data.massage);
                loginCount++;
                if (loginCount >= 3) {
                    // 登录失败三次，需要做验证码校验
                    $('#verifyPart').show();
                    //更换验证码
                    $('captcha_img').click();
                }
            }
        }
    });
});
