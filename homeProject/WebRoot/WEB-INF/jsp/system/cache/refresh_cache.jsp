<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>刷新缓存</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/cache/goJqGrid",
		multiselect: true,
		height:$(document).height()-$("#content-list").offset().top-100,
		colModel : [
		  {name : 'code',index : 'code',width : 100,label:'编号',sortable : true,align : "left",hidden: true},
	      {name : 'descr',index : 'descr',width : 200,label:'描述',sortable : true,align : "left"}
        ]
	});
});

function refresh(){
	var ids = [];
	var ret = selectDataTableRows();
	if (ret && ret.length>0){
		for (var i=0;i<ret.length;i++){
			ids.push(ret[i]['code']);
		}
	}
	if(ids.length <1){
		 art.dialog({
			content: '请选择需要刷新缓存的记录',
			lock: true
		 });
		return ;
	}
	
	art.dialog({
		 content:'您确定要刷新缓存吗？',
		 lock: true,
		 width: 260,
		 height: 100,
		 ok: function () {
	        $.ajax({
				url : "${ctx}/admin/cache/doRefreshAll",
				data : "itemKeys="+ids.join(','),
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: 'json',
				cache: false,
				error: function(){
			        art.dialog({
						content: '刷新缓存出现异常'
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
						       $("#dataTable").jqGrid('resetSelection');
						    }
						});
			    	}else{
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});
	    	return true;
		},
		cancel: function () {
			return true;
		}
	});
}
</script>
</head>
<body>
<div class="body-box-form" style="margin-top: 5px;">
	<div class="btn-panel" >
      <div class="btn-group-sm">
		<button type="button" class="btn btn-system" onclick="refresh()">刷新缓存</button>
      </div>
    </div>
    <div id="content-list">
		<table id="dataTable"></table>
	</div>
</div>
</body>
</html>
