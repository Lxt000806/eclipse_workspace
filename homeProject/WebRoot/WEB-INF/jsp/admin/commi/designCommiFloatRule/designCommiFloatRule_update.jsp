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
	<title>业务提成浮动编辑</title>
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
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			descr:{  
				validators: {  
					notEmpty: {  
						message: "规则名称不能为空"  
					}
				}  
			},
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});	
$(function() {
	var gridOption = {
		url:"${ctx}/admin/designCommiFloatRuleDetail/goJqGrid",
		postData:{floatRulePK :"${designCommiFloatRule.pk }"},
		height:$(document).height()-$("#content-list").offset().top-80,
		rowNum:10000000,
		colModel : [
			{name:"floatrulepk", index:"floatrulepk", width:80, label:"pk", sortable:true ,align:"left",hidden:true},
			{name:"cardinalfrom", index:"cardinalfrom", width:130, label:"提成基数从(大等于)", sortable:true ,align:"right"},
			{name:"cardinalto", index:"cardinalto", width:120, label:"提成基数至(小于)", sortable:true ,align:"right"},
			{name:"commiper", index:"commiper", width:80, label:"提成点", sortable:true ,align:"right"},
			{name:"remarks", index:"remarks", width:220, label:"说明", sortable:true ,align:"left"},
			{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:100, align:"left",formatter:formatDate}, 
			{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
			{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
			{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"}, 
		
		],
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	$("#add").on("click",function(){
		Global.Dialog.showDialog("add",{
			title:"浮动规则明细--新增",
			url:"${ctx}/admin/designCommiFloatRule/goAdd",
			postData:{},
			height: 285,
			width:760,
			returnFun:function(data){
				if(data){
					var json = {
						remarks: data.remarks,
						cardinalfrom: data.cardinalFrom,
						cardinalto: data.cardinalTo,
						commiper: data.commiPer,
						lastupdate: new Date(),
						lastupdatedby: "${czybh }",
						expired: "F",
						actionlog: "ADD",
					}
					Global.JqGrid.addRowData("dataTable",json);
				}
			} 
		});
	});
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		
		if(!ret){
			art.dialog({
				content:"请选择一条明细进行编辑",
			});
			return
		}
		Global.Dialog.showDialog("addUpdate",{
			title:"浮动规则明细--编辑",
			url:"${ctx}/admin/designCommiFloatRule/goAddUpdate?map="+encodeURIComponent(JSON.stringify(ret)),
			height: 285,
			width:760,
			returnFun:function(data){
				if(data){
					var json = {
						floatrulepk: data.floatrulepk,
						remarks: data.remarks,
						cardinalfrom: data.cardinalFrom,
						cardinalto: data.cardinalTo,
						commiper: data.commiPer,
						lastupdate: new Date(),
						lastupdatedby: "${czybh }",
						expired: "F",
						actionlog: "ADD",
					}
			   		$("#dataTable").setRowData(rowId,json);
				}
			} 
		});
	});
	
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
		if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
    
		var param= Global.JqGrid.allToJson("dataTable",null,null,true);
		if(param["detailJson"] == "[]"){
			art.dialog({
				content:"明细数据,无法保存",
			});
			return;
		}
		
		Global.Form.submit("dataForm","${ctx}/admin/designCommiFloatRule/doUpdate",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content:ret.msg,
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
	
	$("#delDetail").on("click",function(){
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
});
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="pk" id="pk" value="${designCommiFloatRule.pk }" />
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li class="form-validate">
								<label><span class="required">*</span>规则名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${designCommiFloatRule.descr }"/>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${designCommiFloatRule.remarks}</textarea>
							</li>
						</ul>	
					</form>
				</div>
			</div>
			<div class="btn-panel">
    			<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system" id="add">
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system" id="delDetail">
						<span>删除</span>
					</button>
				</div>
			</div>	
			<div class="container-fluid">  
				<ul class="nav nav-tabs"> 
			        <li class="active"><a href="#tab_detail" data-toggle="tab">分段明细</a></li>
			    </ul> 
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>	
			</div>
		</div>
	</body>	
</html>
