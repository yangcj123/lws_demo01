layui.use(['layim','flow'], function(){
    var layer = layui.layer
        ,laytpl = layui.laytpl
        ,$ = layui.jquery
        ,layim = layui.layim
        ,flow = layui.flow;

    //聊天记录的分页此处不做演示，你可以采用laypage，不了解的同学见文档：http://www.layui.com/doc/modules/laypage.html
    var fromid = $("#user_id").val();

    //每一页的数据条数
    var limit = 5;

    var totalPage = 0;  //总的页数

    loadFlow();

    //开始请求聊天记录
    var params =  location.search.split('&'); //获得URL参数。该窗口url会携带会话id和type，他们是你请求聊天记录的重要凭据
    console.log("location.search="+location.search);
    console.log("params"+params);
    var index0 = params[0].lastIndexOf("=");
    var index1 = params[1].lastIndexOf("=");
    var jsonMessage = {};
    jsonMessage['toId'] = params[0].substring(index0+1,params[0].length);
    jsonMessage['fromid'] = fromid;
    jsonMessage['type'] = params[1].substring(index1+1,params[1].length);
    jsonMessage['delFlag'] = 1;
    //实际使用时，下述的res一般是通过Ajax获得，而此处仅仅只是演示数据格式
    function loadChatLog(page){
        $.ajax({
            url: 'message/listByPramas?page='+page+'&limit='+limit
            ,type: 'post'
            ,data: jsonMessage
            ,async: false
            ,dataType:'json'
            ,success: function(res){
                console.log(res);
                if(res.code == 0){
                    var html = laytpl(LAY_tpl.value).render({
                        data: res.data
                    });
                    $('#LAY_view').append(html);
                    if(res.count%limit==0){
                        totalPage = res.count/limit;
                    }else{
                        totalPage = parseInt(res.count/limit)+1;
                    }
                }
            },error: function(err, msg){
                layer.alert("服务器保错，请联系管理员");
            }
        });
    }

//@问题：新切换初始化的的flow下拉加载（自动加载）的时候还会保留上一次的flow的page，等于每次还是会加载之前的flow和新的flow。（待解决，layUI官网还没有解决）
    function loadFlow(){
        flow.load({
            elem: '#chatLogDiv' //流加载容器
            //我这里将下拉加载改成了自动加载
            ,isAuto: true
            ,done: function(page, next){ //执行下一页的回调 ，首次进来page自动为1
                //模拟数据插入
                setTimeout(function(){
                    var lis = [];
                    //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                    //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                    loadChatLog(page);
                    next(lis.join(''), page < totalPage); //总页数
                }, 500);
            }
        });
    }
});