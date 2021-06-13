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
<title>退货搬运费规则管理--新增</title>  
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script>
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
});
$(function(){
	var gridOption = {
		height:280,
		rowNum:10000000,
		url:"${ctx}/admin/ReturnCarryRule/goItemJqGrid",
		postData: {no:$.trim($("#no").val())},
		styleUI: "Bootstrap", 
		colModel : [
			{name: "no", index: "no", width: 84, label: "编号", sortable: true, align: "left",hidden:true},
			{name: "itemtype2desc", index: "itemtype2desc", width: 79, label: "材料类型2", sortable: true, align: "left"},
			{name: "itemtype3desc", index: "itemtype3desc", width: 79, label: "材料类型3", sortable: true, align: "left"},
			{name: "itemtype1", index: "itemtype1", width: 79, label: "材料类型1", sortable: true, align: "left",hidden:true},
			{name: "itemtype2", index: "itemtype2", width: 79, label: "材料类型2", sortable: true, align: "left",hidden:true},
			{name: "itemtype3", index: "itemtype3", width: 172, label: "材料类型3", sortable: true, align: "left",hidden:true},
			{name: "lastupdatedby", index: "lastupdatedby", width: 96, label: "最后修改人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 76, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 124, label: "最后修改时间", sortable: true, align: "left", formatter: formatDate}
			],
			loadonce: true,
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	//匹配材料新增
	 $("#add").on("click",function(){		
			Global.Dialog.showDialog("add",{
			title:"匹配材料——新增",
			url:"${ctx}/admin/ReturnCarryRule/goItemSave",
			//postData:{},
			height: 480,
			width:750,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							itemtype1:v.itemtype1,
							itemtype2:v.itemtype2,
							itemtype3:v.itemtype3,
							itemtype2desc:v.itemtype2desc,
							itemtype3desc:v.itemtype3desc,
							expired:v.expired,
							actionlog:v.actionlog,
							lastupdate:v.lastupdate,
							lastupdatedby:v.lastupdatedby
						};
						Global.JqGrid.addRowData("dataTable",json);
					});
				}
			} 
		});
	});
	//匹配材料修改
	$("#update").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
		var param =$("#dataTable").jqGrid("getRowData",rowId);
		Global.Dialog.showDialog("update",{
			title:"匹配材料——编辑",
		  	url:"${ctx}/admin/ReturnCarryRule/goItemUpdate",
		  	postData:param,
		  	height: 480,
			width:750,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							itemtype1:v.itemtype1,
							itemtype2:v.itemtype2,
							itemtype3:v.itemtype3,
							itemtype2desc:v.itemtype2desc,
							itemtype3desc:v.itemtype3desc,
							expired:v.expired,
							actionlog:v.actionlog,
							lastupdate:v.lastupdate,
							lastupdatedby:v.lastupdatedby
						  };
			   			$("#dataTable").setRowData(rowId,json);
					});
				}
			} 
		});
	});
	//删除
	$("#delete").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
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
 	//查看
	$("#view").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
		var param =$("#dataTable").jqGrid("getRowData",rowId);
		Global.Dialog.showDialog("ItemUpdate",{
			title:"匹配材料——查看",
		  	url:"${ctx}/admin/ReturnCarryRule/goItemView",
		  	postData:param,
		  	height: 480,
			width:750,
		});
	}); 
	//保存 
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var returnCarryRuleItemDetail= $('#dataTable').jqGrid("getRowData");	
		var param = {"returnCarryRuleDetailJson": JSON.stringify(returnCarryRuleItemDetail)};
		Global.Form.submit("dataForm","${ctx}/admin/ReturnCarryRule/doreturnCarryRuleUpdate",param,function(ret){				
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
});
//校验函数
$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
		},
		fields: {  
			price:{  
				validators: {  
					notEmpty: {  
						message: '价格不能为空'  
					}
				}  
			},
			beginValue:{  
				validators: {  
					notEmpty: {  
						message: '起始重量不能为空'  
					}  
				}  
			},
			endValue:{  
				validators: {  
					notEmpty: {  
						message: '截止重量不能为空'  
					}  
				}  
			},
			priceType:{  
				validators: {  
					notEmpty: {  
						message: '价格类型不能为空'  
					}  
				}  
			},
			cardAmount:{  
				validators: {  
					notEmpty: {  
						message: '起始值不能为空'  
					},
					numeric: {
	                    message:'只能输入数字'
	                }  
				}  
			},
			incValue:{  
				validators: {  
					notEmpty: {  
						message: '递增值不能为空'  
					},
					numeric: {
	                    message:'只能输入数字'
	                }  
				}  
			},
			calType:{  
				validators: {  
					notEmpty: {  
						message: '计算方式不能为空'  
					}
				}  
			},
			remarks:{
				validators:{
					stringLength: {
                           max: 200,
                           message: '长度不超过200个字符'
                       } 
				}
			}
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
});
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="saveBtn">保存</button>
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
								<ul class="ul-form">	
					<div class="validate-group">
					<li>
						<label><span class="required">*</span>编号</label>
						<input type="text" id="no" name="no"  value="${returnCarryRule.no }" readonly="true"  placeholder="保存时生成"/>							
					</li>
					<li class="form-validate">
						<label ><span class="required">*</span>价格</label>
						<input type="text" id="price" name="price" onkeyup="this.value=this.value.replace(/\D/g,'')" 
							onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="${returnCarryRule.price }"/>							
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>起始重量</label>
						<input type="text" id="beginValue" name="beginValue" onkeyup="this.value=this.value.replace(/\D/g,'')" 
							onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="${returnCarryRule.beginValue }" />
					</li>
					</div>
					<div class="validate-group">
					<li class="form-validate">
						<label><span class="required">*</span>截止重量</label>
						<input type="text" id="endValue" name="endValue" onkeyup="this.value=this.value.replace(/\D/g,'')" 
							onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="${returnCarryRule.endValue }" />
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>价格类型</label>
						<house:xtdm id="priceType" dictCode="PriceType" value="${returnCarryRule.priceType}"></house:xtdm>													
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>起始值</label>
						<input type="text" id="cardAmount" name="cardAmount"  value="${returnCarryRule.cardAmount }" />											
					</li>
					</div>
					<div class="validate-group">
					<li class="form-validate">
						<label><span class="required">*</span>递增值</label>
						<input type="text" id="incValue" name="incValue"  value="${returnCarryRule.incValue }" />													
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>计算方式</label>
						<house:xtdm id="calType" dictCode="ReCarryCalType" value="${returnCarryRule.calType}"></house:xtdm>													
					</li>
					</div>
					<div class="validate-group">
					<li class="form-validate">
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${returnCarryRule.remarks }</textarea>
					</li>
					</div>
				</ul>			
			</form>
		</div>
	</div>
	<div  class="container-fluid" >  
	     <ul class="nav nav-tabs">
	        <li class="active"><a href="#tab_prjProvide" data-toggle="tab">匹配材料</a></li>  
	    </ul>  
	    <div class="tab-content">  
	    	<div id="tab_prjProvide" class="tab-pane fade in active">
	    		<div class="body-box-list"  style="margin-top: 0px;">
					<div class="panel panel-system" >
					    <div class="panel-body">
					      	<div class="btn-group-xs" >
								<button type="submit" class="btn btn-system " id="add">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system "  id="update">
									<span>编辑</span>
								</button>
								<button type="submit" class="btn btn-system " id="delete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="view">
									<span>查看</span>
								</button>
							</div>
						</div>
					</div>
					<table id= "dataTable" style="overflow: auto;"></table>					
				</div>
			</div>
		</div>
	</div>
</div>		
</body>
</html>



