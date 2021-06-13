<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>施工材料分类编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 

$(function() {
		$("#dataForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  
					descr:{  
						validators: {  
							notEmpty: {  
								message: '名称不能为空'  
							}
						}  
					},
					dispatchOrder:{  
						validators: {  
							notEmpty: {  
								message: '调度下单不能为空'  
							}
						}  
					},
					itemType1:{  
						validators: {  
							notEmpty: {  
								message: '材料类型1不能为空'  
							}
						}  
					},
					
				},
				submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	function getData(data){
		if (!data) return;
		$('#address').val(data.Descr);
	  	validateRefresh_choice();
	  }
});

$(function() {
	var lastCellRowId;
	var gridOption = {
		url:"${ctx}/admin/itemConfirm/goDetailJqGrid",
		postData:{code:'${itemTypeConfirm.code}'},
		height:$(document).height()-$("#content-list").offset().top-80,
		rowNum:10000000,
		colModel : [
			{name:'dtpk', index:'dtpk', width:80, label:'明细pk', sortable:true ,align:"left",hidden:true},
			{name:'itemtype2', index:'itemtype2', width:80, label:'材料类型2', sortable:true ,align:"left",hidden:true},
			{name:'itemtype2descr', index:'itemtype2descr', width:100, label:'材料类型2', sortable:true ,align:"left"},
			{name:'itemtype3', index:'itemtype3', width:80, label:'材料类型3', sortable:true ,align:"left",hidden:true},
			{name:'itemtype3descr', index:'itemtype3descr', width:100, label:'材料类型3', sortable:true ,align:"left"},
		
		],
	};
		var detailJson = Global.JqGrid.allToJson("dataTable","itcode");
		Global.JqGrid.initJqGrid("dataTable",gridOption);
		
	$("#add").on("click",function(){
		var itemType1=$.trim($("#itemType1").val());
		if(itemType1==null||itemType1==''){
			art.dialog({
				content:'请先选择材料类型1',
			});
			return false;
		}
		var itemtype2 = Global.JqGrid.allToJson("dataTable","itemtype2");
		var itemtype3 = Global.JqGrid.allToJson("dataTable","itemtype3");
		var arr = new Array();//2
		var arry = new Array();//3
			arr = itemtype2.fieldJson.split(",");
			arry = itemtype3.fieldJson.split(",");
	
		Global.Dialog.showDialog("save",{
			  title:"施工材料分类——增加",
			  url:"${ctx}/admin/itemConfirm/goAdd",
			  postData:{ar:itemType1,arr:arr,arry:arry},
			  height:400,
		      width:800,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
							itemtype2:v.itemType2,
							itemtype2descr:v.item2Descr,
							itemtype3:v.itemType3,
							itemtype3descr:v.item3Descr,
						  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
				  }
			  } 
		 });
	});
	
	$("#update").on("click",function(){
		var itemType1=$.trim($("#itemType1").val());
		if(itemType1==null||itemType1==''){
			art.dialog({
				content:'请先选择材料类型1',
			});
			return false;
		}
		var itemtype2 = Global.JqGrid.allToJson("dataTable","itemtype2");
		var itemtype3 = Global.JqGrid.allToJson("dataTable","itemtype3");
		var arr = new Array();//2
		var arry = new Array();//3
			arr = itemtype2.fieldJson.split(",");
			arry = itemtype3.fieldJson.split(",");
		var ret=selectDataTableRow();
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		Global.Dialog.showDialog("save",{
			  title:"施工材料分类——增加",
			  url:"${ctx}/admin/itemConfirm/goSaveUpdate",
			  postData:{itemType2:ret.itemtype2,itemType3:ret.itemtype3,arr:arr,arry:arry,ar:itemType1},
			  height:400,
		      width:800,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
							itemtype2:v.itemType2,
							itemtype2descr:v.item2Descr,
							itemtype3:v.itemType3,
							itemtype3descr:v.itemType3== "" ? "":v.item3Descr,
						  };
			   				$('#dataTable').setRowData(rowId,json);
					  });
				  }
			  } 
		 });
	});	
	
	$("#avgSendDay").on("blur",function() {
		var avgSendDay = $(this).val();
		if (isNaN(avgSendDay) || parseInt(avgSendDay) < 0) {
			art.dialog({
				content: "有效需求施工节点数据有错！应为正整数！请重新输入",
				width: 220,
				okValue: "确定",
				ok: function () {
					$("#avgSendDay").focus();
				},
			});
		}
		if ("" == avgSendDay) {
			$(this).val(0);
		}
	});
	
	$("#del").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		art.dialog({
				 content:"是删除该记录？",
				 lock: true,
				 width: 100,
				 height: 80,
				 ok: function () {
					Global.JqGrid.delRowData("dataTable",id);
				},
				cancel: function () {
					return true;
				}
			});
	});
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType", "itemType1");
	Global.LinkSelect.setSelect({firstSelect:"itemType1",firstValue:"${itemTypeConfirm.itemType1}"});
	//保存
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var descr=$.trim($("#descr").val());
		var code=$.trim($("#code").val());
		if(descr==null||descr==""){
			art.dialog({
				content:"名称不能为空",
			});
			return false;
		}
		if(code==null||code==""){
			art.dialog({
				content:"编号不能为空",
			});
			return false;
		}
		var Ids =$("#dataTable").getDataIDs();
		if(Ids==null||Ids==''){
			art.dialog({
				content:'明细表为空，不能保存',
			});
			return false;
		}
		if(itemType1==null||itemType1==''){
			art.dialog({
				content:'材料类型1为空，不能保存',
			});
			return false;
		}
		var param= Global.JqGrid.allToJson("dataTable");
		Global.Form.submit("dataForm","${ctx}/admin/itemConfirm/doUpdate",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content:'保存成功',
					time:1000,
					beforeunload:function(){
						closeWin();
					}
				});				
			}else{
				$("#_form_token_uniq_id").val(ret.token.token);
				art.dialog({
					content:ret.msg,
					width:200
				});
			}
		});
	});
});

function formatNum(e){
	$("#avgSendDay").val(e.value.replace(/[^\-?\d]/g,''));
}

function itemType1Changed(){
	$("#dataTable").clearGridData();//清空明细表
}
</script>
</head>
	<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		      <div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li >
								<label>编号</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${itemTypeConfirm.code }" readonly="true" />
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" readonly="true" value="${itemTypeConfirm.descr }"/>
							</li>
							<li class="form-validate">
								<label>确认节点编号</label>
								<house:dict id="itemTimeCode" dictCode="" sql="select Code,Descr from tConfItemTime " 
								sqlValueKey="Code" sqlLableKey="Descr" value="${itemTypeConfirm.itemTimeCode}" ></house:dict>
							</li>
							<li class="form-validate">
								<label>有效需求施工节点</label>
								<house:dict id="prjItem" dictCode="" sql="select Code,Descr from tPrjItem1 " 
								sqlValueKey="Code" sqlLableKey="Descr"  value="${itemTypeConfirm.prjItem }"></house:dict>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>平均发货天数</label>
								<input type="text" id="avgSendDay" name="avgSendDay" style="width:160px;" 
								value="${itemTypeConfirm.avgSendDay }" onkeyup = "formatNum(this)" />
							</li>
							<li class="form-validate">
                                <label><span class="required">*</span>材料类型1</label>
                                <select id="itemType1" name="itemType1" onchange="itemType1Changed()"></select>
                            </li>
                            <li class="form-validate">
								<label><span class="required">*</span>调度下单</label>
								<house:dict id="dispatchOrder" dictCode="" sql="select CBM Code,NOTE Descr from tXtdm where id = 'YESNO' " 
								sqlValueKey="Code" sqlLableKey="Descr"  value="${itemTypeConfirm.dispatchOrder }"></house:dict>
							</li>
						</ul>	
					</form>
				</div>
			</div>
			<div class="btn-panel" >
    			<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="add" >
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system " id="update" >
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system " id="del">
						<span>删除</span>
					</button>
				</div>
			</div>	
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active"><a href="#tab_detail" data-toggle="tab">材料分类明细表</a></li>
			    </ul> 
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>	
			</div>
		</div>
	</body>	
</html>
