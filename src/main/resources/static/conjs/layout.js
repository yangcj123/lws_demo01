layui.use('layim', function(layim){

    var socket = layui.socket;
    var $ = layui.$;

    //用户id
    var user_id = "";

    //判断系统是否支持WebSocket
    if('WebSocket' in window){
        // websocket链接地址  window.location.host localhost:9099
        //ws://localhost:9099/websocket
        socket = new WebSocket('ws://' + window.location.host + '/websocket');
    }
    else{
        alert('Not support websocket')
    }

    //连接到服务器端成功时触发
    socket.onopen = function(){
        //链接成功，取到user_id
        user_id = $("#user_id").val();
        var jsonUpdUser = {};
        jsonUpdUser['id'] = user_id;
        jsonUpdUser['status'] = "online";
        //通过服务器端修改用户当前状态
        updUser(jsonUpdUser);
        console.log("webScoket连接成功..")
       // socket.send('webScoket连接成功..');
    };

    //连接关闭时触发
    socket.onclose = function(){
        var jsonUpdUser = {};
        jsonUpdUser['id'] = user_id;
        jsonUpdUser['status'] = "offline";
        //通过服务器端修改用户当前状态
        updUser(jsonUpdUser);
        user_id = "";
        console.log("webScoket连接关闭..")
    };

    //连接断开时触发
    socket.onerror = function(){
        var jsonUpdUser = {};
        jsonUpdUser['id'] = user_id;
        jsonUpdUser['status'] = "offline";
        //通过服务器端修改用户当前状态
        updUser(jsonUpdUser);
        user_id = "";
        console.log("webScoket连接断开..")
    };

  //基础配置
  layim.config({
    init: {
    	url: '/user/init' //接口地址（返回的数据格式见下文）
        ,type: 'get' //默认get，一般可不填
        ,data: {} //额外参数
    } //获取主面板列表信息，下文会做进一步介绍
 
    //获取群员接口（返回的数据格式见下文）
    ,members: {
      url: '/biGroup/getMembers' //接口地址（返回的数据格式见下文）
      ,type: 'get' //默认get，一般可不填
      ,data: {} //额外参数
    }
     
    //上传图片接口（返回的数据格式见下文），若不开启图片上传，剔除该项即可
    ,uploadImage: {
      url: '/message/uploadImage' //服务器接口地址
      ,type: 'post' //默认post
    } 
    
    //上传文件接口（返回的数据格式见下文），若不开启文件上传，剔除该项即可
    ,uploadFile: {
      url: '/message/uploadFile' //服务器接口地址
      ,type: 'post' //默认post
    }
    
    //扩展工具栏，下文会做进一步介绍（如果无需扩展，剔除该项即可）
    ,tool: [{
      alias: 'code' //工具别名
      ,title: '代码' //工具名称
      ,icon: '&#xe64e;' //工具图标，参考图标文档
    },{
      alias: 'talk' //工具别名
      ,title: '聊天' //工具名称
      ,icon: '&#xe63a;' //工具图标，参考图标文档
    },{
      alias: 'share' //工具别名
      ,title: '分享' //工具名称
      ,icon: '&#xe641;' //工具图标，参考图标文档
    }]
    
    
    //,title: 'WebIM' //自定义主面板最小化时的标题
    //,right: '300px' //主面板相对浏览器右侧距离
    //,minRight: '90px' //聊天面板最小化时相对浏览器右侧距离
    ,initSkin: '1.jpg' //1-5 设置初始背景
    ,skin: ['http://q30eoesus.bkt.clouddn.com/g100601.jpg'] //新增皮肤
    //,isfriend: false //是否开启好友
    //,isgroup: false //是否开启群组
    //,min: true //是否始终最小化主面板，默认false
    ,notice: true //是否开启桌面消息提醒，默认false
    ,voice: false //声音提醒，默认开启，声音文件为：default.mp3
    
    ,isAudio: true //开启聊天工具栏音频
    ,isVideo: true //开启聊天工具栏视频
    
    ,msgbox: '/static/lib/layim/html/list.html' //消息盒子页面地址，若不开启，剔除该项即可
  //  ,find: layui.cache.dir + 'lws_demo01//static/lib/layim/html/add.html'//发现页面地址，若不开启，剔除该项即可
    ,chatLog: 'model/toChatlog'//聊天记录页面地址，若不开启，剔除该项即可
  });
  
  //给具体的工具栏绑定事件
  //监听自定义工具栏点击，以添加代码为例
  layim.on('tool(code)', function(insert, send, obj){ //事件中的tool为固定字符，而code则为过滤器，对应的是工具别名（alias）
	  layer.prompt({
	    title: '插入代码'
	    ,formType: 2
	    ,shade: 0
	  }, function(text, index){
	    layer.close(index);
	    insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器，主要由insert完成
	    //send(); //自动发送
	  });
	  console.log(this); //获取当前工具的DOM对象
	  console.log(obj); //获得当前会话窗口的DOM对象、基础信息
  });  
  
  layim.on('tool(talk)', function(insert, send, obj){ 
  	layer.msg("进行了聊天。。");
  	
  });
  
  layim.on('tool(share)', function(insert, send, obj){ 
  	layer.msg("进行了分享。。");
  	
  });

  layim.msgbox(17); //数字即为你通过websocket或者Ajax实时获取到的最新消息数量
  //它将在主面板的消息盒子icon上不断显隐提示，直到点击后自动消失

    //监听在线状态
    layim.on('online', function(status){
        var jsonUpdUser = {};
        jsonUpdUser['id'] = user_id;
        jsonUpdUser['status'] = status;
        //通过服务器端修改用户当前状态
        updUser(jsonUpdUser);
        //此时，你就可以通过Ajax将这个状态值记录到数据库中了。
        //服务端接口需自写。
    });

    //监听个性化签名
    layim.on('sign', function(value){
        console.log(value); //获得新的签名
        var jsonUpdUser = {};
        jsonUpdUser['id'] = user_id;
        jsonUpdUser['sign'] = value;
        //通过服务器端修改用户当前状态
        updUser(jsonUpdUser);
        //此时，你就可以通过Ajax将新的签名同步到数据库中了。
    });

    //layim监听对话框消息发送
    layim.on('sendMessage', function(res) {
        var mine = res.mine; //包含我发送的消息及我的信息
        console.log(res);
        //监听到上述消息后，就可以轻松地发送socket了，如：
        socket.send(JSON.stringify({
            emit: 'chatMessage' //随便定义，用于在服务端区分消息类型
            ,data: res
        }));
    });

    //监听收到的聊天消息，假设你服务端emit的事件名为：chatMessage
    socket.onmessage = function(res){  //res表示推送的消息数据
        console.log(res);

        var data = JSON.parse(res.data);
        if(data.emit === 'chatMessage'){
            //将服务器推送过来的消息数据在对话框中显示
            layim.getMessage(data.data); //res.data即你发送消息传递的数据（阅读：监听发送的消息）
        }else if(data.emit === 'offLineMessage'){
            layer.msg("你有"+data.data.length+"条离线消息需要读取");
            //通过循环将消息显示在对话框中
            $.each(data.data,function (i,message) {
                //将服务器推送过来的消息数据在对话框中显示
                layim.getMessage(message);
            });
        }
    };

    //修改用户的基本信息
    function updUser(jsonUpdUser) {
        $.ajax({
           type:'POST',
           url:"/user/updT",
           data:jsonUpdUser,
           success:function (data) {
               console.log(data);
           },
           error:function () {
               layer.msg("服务器异常！！",{icon:3,time:2000,anim:3});
           }
        });
    }

});      