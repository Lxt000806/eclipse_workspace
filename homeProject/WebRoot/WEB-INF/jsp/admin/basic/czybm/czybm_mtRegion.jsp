<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>麦田区域</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/czybm/goJqGridMtRegion?czybh=${czybm.czybh}",
		height:$(document).height()-$("#content-list").offset().top-65,
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "pk",index : "pk",width : 100,label:"pk",align : "left",hidden:true},
			{name: "mtregioncode",index : "mtregioncode",width : 80,label:"麦田区域编号",align : "left"},
			{name: "mtregiondescr",index : "mtregiondescr",width : 150,label:"麦田区域名称",align : "left"},
			{name: "belongmtregiondescr",index : "belongmtregiondescr",width : 150,label:"麦田大区",align : "left"},
			{name: "namechi",index : "namechi",width : 100,label:"对接人",align : "left"},
        ],
	});
	
});
function loadMtregion(){
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/czybm/goJqGridMtRegion",datatype:'json',postData:{czybh:"${czybm.czybh}"},page:1}).trigger("reloadGrid");
}

function add(){
	Global.Dialog.showDialog("Save", {
		title : "操作员管理--麦田区域新增",
		url : "${ctx}/admin/czybm/goCzyMtRegionAdd",
		postData:{czybh:"${czybm.czybh}",m_umState:"A"},
		height: 330,
		width:700,
		returnFun:function(){
			loadMtregion();	
		}
	});
}
function del(){
	var ret = selectDataTableRow("dataTable");
		if(!ret){
			art.dialog({
				content:"请选择一条数据 进行删除",
			});
			return;
		}
		art.dialog({
       		content: '是删除该记录？',
	        lock: true,
	        width: 160,
	        height: 50,
	        ok: function () {
	       		$.ajax({
					url:"${ctx}/admin/czyMtRegion/doDelete?id="+ret.pk,
					type: "post",
					dataType: "json",
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
				    },
				    success: function(obj){
				    	if(obj.rs){
				    		art.dialog({
								content: "删除成功",
								time: 1000,
							});
							loadMtregion();	
				    	}else{
				    		$("#_form_token_uniq_id").val(obj.token.token);
				    		art.dialog({
								content: obj.msg,
								width: 200
							});
				    	}
				    }
				});
	       		
	        },
		    cancel: function () {
	      		return;
		    }
	   	});
}
</script>

</head>
    
<body onunload="closeWin()">
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<house:token></house:token>
					<ul class="ul-form">
						<div class="validate-group row">
							<div class="col-sm-6">
								<li><label>操作员编号</label> <input type="text" id="czybh"
									name="czybh" value="${czybm.czybh}" readonly="readonly" />
								</li>
							</div>
							<div class="col-sm-6">
								<li><label>姓名 </label> <input type="text" id="zwxm"
									name="zwxm" value="${czybm.zwxm}" readonly="readonly" />
								</li>
							</div>
							
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_detail" data-toggle="tab">麦田区域明细</a>
				</li>
			</ul>
			<div class="tab-content">
				<div id="tab_detail" class="tab-pane fade in active">
					<div class="pageContent">
						<div class="panel panel-system">
							<div class="panel-body">
								<div class="btn-group-xs">
									<button style="align:left" type="button"
										class="btn btn-system onlyView" onclick="add()">
										<span>新增 </span>
									</button>
									<button style="align:left" type="button"
										class="btn btn-system onlyView" onclick="del()">
										<span>删除 </span>
									</button>
								</div>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
