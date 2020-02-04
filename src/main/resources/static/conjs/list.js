// 配置
layui.config({
	base : 'modules/' // 扩展模块目录
}).extend({ // 模块别名
	hpWindow : 'hpComponent/hpWindow',
	hpConfig : 'hpComponent/hpConfig',
});

layui.use([ 'table', 'hpWindow', 'laydate' ], function() {
	var table = layui.table;
	var $ = layui.$;
	var hpWindow = layui.hpWindow;
	var form = layui.form;
	var laydate = layui.laydate;
	// 方法事件
	var event = {
		reload : function(id, data) {
			// 执行重载
			table.reload(id, {
				page : {
					curr : 1
				// 重新从第 1 页开始
				},
				where : data
			// 参数
			});

		}
	}
	
	function statusFmt(d) {
		var selectMap = {};
		selectMap[0] = '未发送';
		selectMap[1] = '已发送';
		return selectMap[d.status];
	}
	 function timeFmt(d){
		//如果记得时间戳是毫秒级的就需要*1000 不然就错了记得转换成整型
		var now = new Date(d.timestamp);
		 var year=now.getFullYear(); 
	     var month=now.getMonth()+1; 
	     var date=now.getDate(); 
	     var hour=now.getHours(); 
	     var minute=now.getMinutes(); 
	     var second=now.getSeconds(); 
		 return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
	} 
	// 初始化参数
	laydate.render({
		elem : '#startTime',
		type : 'datetime'
	});
	laydate.render({
		elem : '#endTime',
		type : 'datetime'
	});
	laydate.render({
		elem : '#groupstartTime',
		type : 'datetime'
	});
	laydate.render({
		elem : '#groupendTime',
		type : 'datetime'
	});
	$.ajax({
		url : webPath + '/empController/queryAllList',
		data : {},
		type : 'post',
		dataType : 'json',
		success : function(json) {
			if (json && json.code == 200) {
				var selectUsername = '';
				var selectId = '';
				layui.each(json.data, function(index, item) {
					selectUsername += '<option value="' + item.name + '">'
							+ item.name + '</option>';
					selectId += '<option value="' + item.id + '">' + item.name
							+ '</option>';
				});
				$('select[name = "username"][id = "list"]').append(
						selectUsername);
				$('select[name = "username"][id = "groupUsername"]').append(
						selectUsername);
				$('select[name = "toId"][id = "list"]').append(selectId);
				$('select[name = "toId"][id = "grouptoId"]').append(selectId);
				form.render('select'); // 更新form
			} else {
				layer.msg('数据有误');
			}
		},
		error : function() {
			layer.msg('系统出错,请联系管理员');
		}
	});
	$.ajax({
		url : webPath + '/deptController/queryAllList',
		data : {},
		type : 'post',
		dataType : 'json',
		success : function(json) {
			if (json && json.code == 200) {
				var selectId = '';
				layui.each(json.data, function(index, item) {
					selectId += '<option value="' + item.id + '">'
							+ item.groupname + '</option>';
				});
				$('select[name = "id"][id = "groupid"]').append(selectId);
				form.render('select'); // 更新form
			} else {
				layer.msg('数据有误');
			}
		},
		error : function() {
			layer.msg('系统出错,请联系管理员');
		}
	});

	table.render({
		elem : '#demo',
		url : webPath + '/layimController/ajaxListFriend',
		page : true,
		height : 'full-185' // 高度最大化减去差值
		,
		size : 'sm' // 小尺寸的表格
		,
		cols : [ [ {
			type : 'checkbox',
			fixed : 'left'
		}, {
			field : 'cid',
			title : '编号',
			sort : true
		}, {
			field : 'username',
			title : '发送消息人员',
			sort : false
		}, {
			field : 'acceptName',
			title : '接受消息人员',
			sort : false
		}, {
			field : 'type',
			title : '消息类型',
			sort : false
		}, {
			field : 'content',
			title : '消息内容',
			sort : false
		}, {
			field : 'status',
			title : '消息情况',
			sort : false,
			templet : statusFmt
		}, {
			field : 'timestamp',
			title : '消息时间',
			sort : false,
			templet : timeFmt
		}, {
			fixed : 'right',
			title : '操作',
			width : 178,
			align : 'center',
			toolbar : '#barTool'
		} ] ]
	});

	// 批量删除
	$('#deletesBtn').click(function() {
		groupdeletesBtn
		layer.confirm('批量删除吗', function(index) {
			var checkStatus = table.checkStatus('demo');
			if (checkStatus) {
				var ids = "";
				var arr = [];
				$.each(checkStatus.data, function(index, item) {
					arr.push(item.cid);
				});
				ids = arr.join(',');
				$.ajax({
					url : webPath + '/layimController/deletes',
					data : {
						"ids" : ids
					},
					type : 'post',
					dataType : 'json',
					success : function(json) {
						if (json && json.code == 200) {
							// 执行重载
							event.reload('demo');
							layer.msg(json.msg);
							layer.close(index);
						} else {
							layer.msg('数据有误');
						}
					},
					error : function() {
						layer.msg('系统出错,请联系管理员');
					}
				})
			}
		})
	});

	// 监听表格复选框选择
	table.on('checkbox(demo)', function(obj) {
		console.log(obj)
	});

	// 监听工具条
	table.on('tool(demo)', function(obj) {
		var data = obj.data;
		if (obj.event === 'del') {
			layer.confirm('真的删除行么', function(index) {

				$.ajax({
					url : webPath + '/layimController/delete',
					data : {
						"cid" : data.cid
					},
					type : 'post',
					dataType : 'json',
					success : function(json) {
						if (json && json.code == 200) {
							// 执行重载
							event.reload('demo');
							obj.del();
							layer.msg(json.msg);
							layer.close(index);
						} else {
							layer.msg('数据有误');
						}
					},
					error : function() {
						layer.msg('系统出错,请联系管理员');
					}

				})

			});
		} else if (obj.event === 'detail') {
			layer.open({
				type : 1,
				area : [ '30%', '30%' ],
				title : '内容详情',
				closeBtn : 0,
				shadeClose : true,
				skin : 'yourclass',
				content : '<div class="layui-text">' + data.content + '</div>'
			});
		}
	});

	// 监听排序
	table.on('sort(demo)', function(obj) {
		console.log(this, obj.field, obj.type)

		return;
		table.reload('idTest', {
			initSort : obj,
			where : { // 重新请求服务端
				key : obj.field // 排序字段
				,
				order : obj.type
			// 排序方式
			}
		// ,height: 300
		});
	});

	// 监听提交
	form.on('submit(formDemo)', function(data) {
		var res = data.field;
		res.startTime = res.startTime.trim() != "" ? new Date(res.startTime)
				.valueOf() : null;
		res.endTime = res.endTime.trim() != "" ? new Date(res.endTime)
				.valueOf() : null;
		res.content = res.content.trim() != "" ? res.content : null;
		res.toId = res.toId.trim() != "" ? res.toId : null;
		res.status = res.status == "" ? null : res.status;
		res.username = res.username.trim() != "" ? res.username.trim() : null;
		table.reload('demo', {
			url : webPath + '/layimController/ajaxListFriend',
			where : res,
			page : {
				curr : 1
			// 重新从第 1 页开始
			}
		});
		return false;
	});

	// 表格刷新
	$("#refrsh").click(function() {
		// 执行重载
		event.reload('demo');
	});

	table.render({
		elem : '#groupdemo',
		url : webPath + '/layimController/ajaxListGroup',
		height : 'full-185' // 高度最大化减去差值
		,
		page : true,
		size : 'sm' // 小尺寸的表格
		,
		cols : [ [ {
			type : 'checkbox',
			fixed : 'left'
		}, {
			field : 'cid',
			title : '编号',
			sort : true
		}, {
			field : 'username',
			title : '发送消息人员',
			sort : false
		}, {
			field : 'acceptName',
			title : '接受消息人员',
			sort : false
		}, {
			field : 'acceptGroupName',
			title : '接受消息群',
			sort : false
		}, {
			field : 'type',
			title : '消息类型',
			sort : false
		}, {
			field : 'content',
			title : '消息内容',
			sort : false
		}, {
			field : 'status',
			title : '消息情况',
			sort : false,
			templet : statusFmt
		}, {
			field : 'timestamp',
			title : '消息时间',
			sort : false,
			templet : timeFmt
		}, {
			fixed : 'right',
			title : '操作',
			width : 178,
			align : 'center',
			toolbar : '#barTool'
		} ] ]
	});

	$("#groupRefrsh").click(function() {
		// 执行重载
		event.reload('groupdemo');
	});

	// 监听工具条
	table.on('tool(groupdemo)', function(obj) {
		var data = obj.data;
		if (obj.event === 'del') {
			layer.confirm('真的删除行么', function(index) {
				$.ajax({
					url : webPath + '/layimController/delete',
					data : {
						"cid" : data.cid
					},
					type : 'post',
					dataType : 'json',
					success : function(json) {
						if (json && json.code == 200) {
							// 执行重载
							event.reload('demo');
							obj.del();
							layer.msg(json.msg);
							layer.close(index);
						} else {
							layer.msg('数据有误');
						}
					},
					error : function() {
						layer.msg('系统出错,请联系管理员');
					}
				});

			});
		} else if (obj.event === 'detail') {
			layer.open({
				type : 1,
				area : [ '30%', '30%' ],
				title : '内容详情',
				closeBtn : 0,
				shadeClose : true,
				skin : 'yourclass',
				content : '<div class="layui-text">' + data.content + '</div>'
			});
		}
	});

	// 批量删除
	$('#groupdeletesBtn').click(function() {
		layer.confirm('批量删除吗', function(index) {
			var checkStatus = table.checkStatus('groupdemo');
			if (checkStatus) {
				var ids = "";
				var arr = [];
				$.each(checkStatus.data, function(index, item) {
					arr.push(item.cid);
				});
				ids = arr.join(',');
				$.ajax({
					url : webPath + '/layimController/deletes',
					data : {
						"ids" : ids
					},
					type : 'post',
					dataType : 'json',
					success : function(json) {
						if (json && json.code == 200) {
							// 执行重载
							event.reload('groupdemo');
							layer.msg(json.msg);
							layer.close(index);
						} else {
							layer.msg('数据有误');
						}
					},
					error : function() {
						layer.msg('系统出错,请联系管理员');
					}
				})
			}
		})
	});

	form.on('submit(groupformDemo)', function(data) {
		console.log(data)
		var res = data.field;
		res.startTime = res.startTime.trim() != "" ? new Date(res.startTime)
				.valueOf() : null;
		res.endTime = res.endTime.trim() != "" ? new Date(res.endTime)
				.valueOf() : null;
		res.content = res.content.trim() != "" ? res.content : null;
		res.toId = res.toId.trim() != "" ? res.toId : null;
		res.status = res.status == "" ? null : res.status;
		res.username = res.username.trim() != "" ? res.username.trim() : null;
		table.reload('groupdemo', {
			url : webPath + '/layimController/ajaxListGroup',
			where : res,
			page : {
				curr : 1
			// 重新从第 1 页开始
			}
		});
		return false;
	});
})