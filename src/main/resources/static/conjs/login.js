layui.use(['jquery','layer','form',],function () {
    //创建出内置模块的对象
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;

    if($("#loginMsg").val()!=""){
        layer.msg("你还未登陆！！",{icon:2,time:2000,anim:6});
    }

    //表单验证
    form.verify({
        userName: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value.length>12||value.length<3) {
                return '用户名长度在3-12之间';
            }
        },
        pwd: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value.length>12||value.length<3) {
                return '密码长度在3-12之间';
            }
        }
    });

    //监听登陆表单提交
    form.on('submit(login)', function(data){
        var loginJsonUser = data.field;
        login(loginJsonUser);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    function login(loginJsonUser) {
        $.ajax({
            type:'POST',
            url:'/user/login',
            data:loginJsonUser,
            success:function (data) {
                if(data=='success'){
                    layer.msg("恭喜你，登陆成功。。",{icon:1,time:2000,anim:4});
                    setTimeout('window.location="model/toLayout"',2000);
                }else {
                    layer.msg("很遗憾，登陆失败！！",{icon:2,time:2000,anim:4});
                }
            },
            error:function () {
                layer.msg("服务器异常！！",{icon:3,time:2000,anim:3});
            }
        });
    }

});