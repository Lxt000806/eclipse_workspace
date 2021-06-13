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
	<title>新主材提成规则管理新增</title>
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
		message :"This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating :"glyphicon glyphicon-refresh"
		},
		fields: {  
			commiType:{  
				validators: {  
					notEmpty: {  
						message:"提成类型不能为空"  
					}
				}  
			},
			fromProfit:{  
				validators: {  
					notEmpty: {  
						message:"毛利率从不能为空"  
					}
				}  
			},
			toProfit:{  
				validators: {  
					notEmpty: {  
						message:"毛利率至不能为空"  
					}
				}  
			},
			isUpgItem:{  
				validators: {  
					notEmpty: {  
						message:"是否审计材料不能为空"  
					}
				}  
			},
			commiPerc:{  
				validators: {  
					notEmpty: {  
						message:"提成比例不能为空"  
					}
				}  
			},
		},
		submitButtons :"input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});	
$(function() {
	var lastCellRowId;
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-80,
		rowNum:10000000,
		colModel : [
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true ,align:"left",hidden:true },
			{name:"no", index:"no", width:80, label:"规则编号", sortable:true ,align:"left",hidden:true},
			{name:"itemtype2", index:"itemtype2", width:80, label:"材料类型2", sortable:true ,align:"left",hidden:true},
			{name:"itemtype2descr", index:"itemtype2descr", width:80, label:"材料类型2", sortable:true ,align:"left"},
			{name:"itemtype3", index:"itemtype3", width:80, label:"材料类型3", sortable:true ,align:"left",hidden:true},
			{name:"itemtype3descr", index:"itemtype3descr", width:80, label:"材料类型3", sortable:true ,align:"left"},
			{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:100, align:"left",formatter:formatDate}, 
			{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
			{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
			{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"}, 
		
		],
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	$("#add").on("click",function(){
		Global.Dialog.showDialog("add",{
			title:"新主材提成规则匹配材料——新增",
			url:"${ctx}/admin/mainCommiRuleNew/goAdd",
			postData:{},
			height: 285,
			width:760,
			returnFun:function(data){
				if(data){
					var json = {
						itemtype2: data.itemType2,
						itemtype2descr: data.itemType2Descr,
						itemtype3: data.itemType3,
						itemtype3descr: data.itemType3Descr,
						lastupdate: new Date(),
						lastupdatedby:"${czybh }",
						expired:"F",
						actionlog:"ADD",
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
			title:"新主材提成规则匹配材料——编辑",
			url:"${ctx}/admin/mainCommiRuleNew/goAddUpdate?map="+encodeURIComponent(JSON.stringify(ret)),
			height: 285,
			width:760,
			returnFun:function(data){
				if(data){
					var json = {
						itemtype2: data.itemType2,
						itemtype2descr: data.itemType2Descr,
						itemtype3: data.itemType3,
						itemtype3descr: data.itemType3Descr,
						lastupdate: new Date(),
						lastupdatedby:"${czybh }",
						expired:"F",
						actionlog:"ADD",
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
		/**
		if(param["detailJson"] =="[]"){
			art.dialog({
				content:"明细数据,无法保存",
			});
			return;
		} */
		Global.Form.submit("dataForm","${ctx}/admin/mainCommiRuleNew/doSave",param,function(ret){
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
			art.dialog({content:"请选择一条记录进行删除操作！",width: 200});
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
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
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
							<li class="form-validate">
								<label><span class="required">*</span>提成类型</label>
								<house:xtdm id="commiType" dictCode="COMMITYPE"></house:xtdm>                     
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>毛利率从</label>
								<input type="text" id="fromProfit" name="fromProfit" style="width:160px;" onkeyup="value=value.replace(/[^(\-|\+)?\d+(\.\d+)?$]/g,'')" value="0.0"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>毛利率至</label>
								<input type="text" id="toProfit" name="toProfit" style="width:160px;" onkeyup="value=value.replace(/[^(\-|\+)?\d+(\.\d+)?$]/g,'')" value="0.0"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>升级材料</label>
								<house:xtdm id="isUpgItem" dictCode="YESNO" ></house:xtdm>                     
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>提成比例</label>
								<input type="text" id="commiPerc" name="commiPerc" style="width:160px;" onkeyup="value=value.replace(/[^(\-|\+)?\d+(\.\d+)?$]/g,'')" value="0.0"/>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2"></textarea>
							</li>
						</ul>	
					</form>
				</div>
			</div>
			<div class="btn-panel" >
    			<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system" id="add" >
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system" id="update" >
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system" id="delDetail">
						<span>删除</span>
					</button>
				</div>
			</div>	
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active"><a href="#tab_detail" data-toggle="tab">分配材料</a></li>
			    </ul> 
				<div id="content-list">
					<table id="dataTable"></table>
				</div>	
			</div>
		</div>
	</body>	
</html>
