<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>配送明细管理</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_driver.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
		var excelName="send";
		var queryType="s_qr";
		/**初始化表格*/
		$(function(){
			 $("#cancel").attr("disabled",true);
			 $("#returnInfo").attr("disabled",true);
			 $("#receivePhoto").attr("disabled",true);
			 $("#returnPhoto").attr("disabled",true);
		});
		
		function sendDetail(){
			excelName="send";
			queryType="s_qr";
			$("#cancel").attr("disabled",true);
			$("#returnInfo").attr("disabled",true);
			$("#receivePhoto").attr("disabled",true);
			$("#returnPhoto").attr("disabled",true);
			$("#arrive").removeAttr("disabled");
			$("#sendInfo").removeAttr("disabled");
			$("#sendPhoto").removeAttr("disabled");
		}
		function returnDetail(){
			excelName="return";
			queryType="r_qr";
			$("#arrive").attr("disabled",true);
			$("#sendInfo").attr("disabled",true);
			$("#sendPhoto").attr("disabled",true);		
			$("#cancel").removeAttr("disabled",true);
			$("#returnInfo").removeAttr("disabled");
			$("#receivePhoto").removeAttr("disabled");
			$("#returnPhoto").removeAttr("disabled");
		}
		function doExcel(){
			if(excelName=="send"){
				doExcelAll("${ctx}/admin/itemAppSend/doSendDetailMngExcel","sendDetailDataTable","send_form");
			}else if (excelName="return"){
				doExcelAll("${ctx}/admin/itemReturn/doReturnDetailMngExcel","returnDetailDataTable","return_form");
			}
		}
		function arrive(){
			var id = $("#sendDetailDataTable").jqGrid("getGridParam","selrow");
		 	var ret = $("#sendDetailDataTable").jqGrid('getRowData',id);
			if(!id){
				art.dialog({content: "请选择一条记录！",width: 200});
				return false;
			}
			console.log(ret);
			if($.trim(ret.sendstatus)!="0"){
				art.dialog({content: "只有未指定司机的可以到货操作！",width:300});
				return false;
			}
			Global.Dialog.showDialog("arrive",{
						title:"录入司机反馈信息",
						url:"${ctx}/admin/itemAppSend/goArrive",
					    postData:{no:ret.no,driverRemark:ret.driverremark},
					    height:300,
					    width:700,
						returnFun:function (){
							goto_query("sendDetailDataTable");
						}
					});
		}
		function cancel(){
			var id = $("#returnDetailDataTable").jqGrid("getGridParam","selrow");
		 	var ret = $("#returnDetailDataTable").jqGrid('getRowData',id);
			if($.trim(ret.status)!="2"){
				art.dialog({content: "只有申请状态可以取消操作！",width:300});
				return false;
			}
			$.ajax({
				url:'${ctx}/admin/itemReturn/doCancel',
				type: 'post',
				data:{no:ret.no},
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
						    	goto_query('returnDetailDataTable');
						    }
						});
			    	}else{
			    		$("#_form_token_uniq_id").val(obj.token.token);
			    		art.dialog({
							content: obj.msg,
							width: 200
						});
			    	}
			    }
		 });
		}
	//发货明细
	function sendInfo() {
		var rowId = $("#sendDetailDataTable").jqGrid("getGridParam","selrow");
		var ret = $("#sendDetailDataTable").jqGrid('getRowData',rowId);
		if (rowId) {
		Global.Dialog.showDialog("view", {
			title : "发货明细查看",
			url : "${ctx}/admin/itemAppSendDetail/goSendInfo",
			postData : {
				'no':ret.no
			},
			height : 500,
			width : 1000,
		});
	  }else{
	  	art.dialog({    	
			content: "请选择一条记录！"
		});
	  }
	}
	//退货明细
	function returnInfo() {
		var rowId = $("#returnDetailDataTable").jqGrid("getGridParam","selrow");
		var ret = $("#returnDetailDataTable").jqGrid('getRowData',rowId);
		if (rowId) {
		Global.Dialog.showDialog("view", {
			title : "退货明细查看",
			url : "${ctx}/admin/itemReturnDetail/goReturnInfo",
			postData : {
				'no':ret.no
			},
			height : 500,
			width : 1000,
		});
	  }else{
	  	art.dialog({    	
			content: "请选择一条记录！"
		});
	  }
	}
	//发货，收货图片
	function sendPhoto(name){
		var rowId = $("#sendDetailDataTable").jqGrid("getGridParam","selrow");
		var ret = $("#sendDetailDataTable").jqGrid('getRowData',rowId);
		if (rowId) {
		Global.Dialog.showDialog("sendView", {
			title : name+"--查看",
			url : "${ctx}/admin/itemAppSend/goViewSendPhoto",
			postData : {
				'no':ret.no
			},
		  	height: 650,
           	width:750,
		});
	  }else{
	  	art.dialog({    	
			content: "请选择一条记录"
		});
	  }
	}
	//退货图片
	function returnPhoto(){
		var rowId = $("#returnDetailDataTable").jqGrid("getGridParam", "selrow");
		var ret = $("#returnDetailDataTable").jqGrid('getRowData', rowId);
		if (rowId) {
			Global.Dialog.showDialog("returnView", {
				title : "退货图片信息--查看",
				url : "${ctx}/admin/itemReturn/goViewReturnPhoto",
				postData : {
					'no' : ret.no
				},
				height : 600,
				width : 1000,
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}
	//回车键搜索
	function keyQuery(queryType){
	 	if (event.keyCode==13)  //回车键的键值为13
	  	$("#"+queryType).click(); //调用登录按钮的登录事件
	}
	</script>
	</head>
<body onkeydown="keyQuery(queryType);">
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<button type="button" id="arrive" class="btn btn-system" onclick="arrive()">到货</button>
					<button type="button" id="cancel" class="btn btn-system" onclick="cancel()">取消</button>
					<button type="button" id="doExcel" class="btn btn-system" onclick="doExcel()">导出Excel</button>
					<button type="button" id="sendInfo" class="btn btn-system" onclick="sendInfo()">发货明细</button>
					<button type="button" id="returnInfo" class="btn btn-system" onclick="returnInfo()">退货明细</button>
					<button type="button" id="sendPhoto" class="btn btn-system" onclick="sendPhoto('发货图片信息')">发货图片</button>
					<button type="button" id="receivePhoto" class="btn btn-system" onclick="sendPhoto('收货图片信息')">收货图片</button>
					<button type="button" id="returnPhoto" class="btn btn-system" onclick="returnPhoto()">退货图片</button>
					<button type="button" id="closeWin" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabSendDetail" class="active" onclick="sendDetail()"><a
				href="#tab_SendDetail" data-toggle="tab">送货明细</a>
			</li>
			<li id="tabReturnDetail" class="" onclick="returnDetail()"><a
				href="#tab_ReturnDetail" data-toggle="tab">退货明细</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_SendDetail" class="tab-pane fade in active">
				<jsp:include page="itemSendBatch_detailManage_sendDetail.jsp"></jsp:include>
			</div>
			<div id="tab_ReturnDetail" class="tab-pane fade ">
				<jsp:include page="itemSendBatch_detailManage_returnDetail.jsp"></jsp:include>
			</div>
		</div>
	</div>
	</div>
</body>
</html>
