layui.use('layim', function(layim){
  //先来个客服模式压压精
  layim.config({  //对话框的配置
    brief: true //是否简约模式（如果true则不显示主面板）
  }).chat({   //对话框对方的具体配置，第1个对话框
    name: '客服姐姐2'  //对话框对方的名字
    ,type: 'friend'   //你与对话框对方的关系  好友
    //对话框中对方的头像
    ,avatar: 'http://tp1.sinaimg.cn/5619439268/180/40030060651/1'
    //每一个对话框对方的id必须是唯一的
    ,id: -2
  }).chat({   //对话框的具体配置，第2个对话框
    name: '客服姐姐1'   
    ,type: 'friend'
    ,avatar: 'http://tp1.sinaimg.cn/5619439268/180/40030060651/1'
    ,id: -1
  });
});