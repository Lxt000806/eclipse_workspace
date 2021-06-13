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
<title>领料审核规则管理--修改</title>  
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script>
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
	Global.LinkSelect.setSelect({
		firstSelect:"itemType1",
		firstValue:"${itemAppConfRule.itemType1}",
	});
});
$(function(){
	$("#payPer").val(myRound("${itemAppConfRule.payPer}",2));
	$("#prior").val(myRound("${itemAppConfRule.prior}",2));
	$("#diffAmount").val(myRound("${itemAppConfRule.diffAmount}",2));
	$("#itemCost").val(myRound("${itemAppConfRule.itemCost}",2));
});
$(function(){
	var gridOption = {
		height:280,
		rowNum:10000000,
		url:"${ctx}/admin/itemAppConfRule/goDetailJqGrid",
		postData: {no: "${itemAppConfRule.no}"},
		styleUI: "Bootstrap", 
		colModel : [
			{name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left",hidden:true},
			{name: "itemtype2desc", index: "itemtype2desc", width: 84, label: "材料类型2", sortable: true, align: "left"},
			{name: "itemtype2", index: "itemtype2", width: 84, label: "材料类型2", sortable: true, align: "left",hidden:true},
			{name: "itemtype3", index: "itemtype3", width: 98, label: "材料类型3", sortable: true, align: "left",hidden:true},
			{name: "itemtype3desc", index: "itemtype3desc", width: 98, label: "材料类型3", sortable: true, align: "left"},
			{name: "itemdesc", index: "itemdesc", width: 359, label: "材料名称", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 147, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 120, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 76, label: "过期标志", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 94, label: "操作标志", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 90, label: "是否过期", sortable: true, align: "left", hidden: true}
		],
		loadonce: true,
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	//领料审核规则明细信息新增
		$("#add").on("click",function(){		
		var itemType1 = $("#itemType1").val();
		if($.trim($("#itemType1").val())==""){
			art.dialog({
				content:"材料类型1不能为空"
			});
			return;
		}
		var itemType2Com = Global.JqGrid.allToJson("dataTable","itemtype2");
		var itemType3Com = Global.JqGrid.allToJson("dataTable","itemtype3");
		var itemDescCom = Global.JqGrid.allToJson("dataTable","itemdesc");
		Global.Dialog.showDialog("add",{
			title:"领料审核规则明细信息--添加",
			url:"${ctx}/admin/itemAppConfRule/goDetailAdd",
			postData:{itemType1:itemType1,itemType2Com: itemType2Com["fieldJson"],itemType3Com: itemType3Com["fieldJson"],itemDescCom: itemDescCom["fieldJson"]},
			width:380,
			height:300,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							itemtype2:v.itemtype2,
							itemtype2desc:v.itemtype2desc,
							itemtype3:v.itemtype3,
							itemtype3desc:v.itemtype3desc,
							itemdesc:v.itemdesc,
							expired:v.expired,
							actionlog:v.actionlog,
							lastupdate:v.lastupdate,
							lastupdatedby:v.lastupdatedby
						};
						Global.JqGrid.addRowData("dataTable",json);
					});
					$("#itemType1").attr("disabled","disabled");			
				}
			} 
		});
	});
	//领料审核规则明细信息修改
	$("#update").on("click",function(){
		var itemType1 = $("#itemType1").val();
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
		var ret = selectDataTableRow();
		var itemType2Com = Global.JqGrid.allToJson("dataTable","itemtype2");
		var itemType3Com = Global.JqGrid.allToJson("dataTable","itemtype3");
		var itemDescCom = Global.JqGrid.allToJson("dataTable","itemdesc");
		var itemType2 =ret.itemtype2;
		var itemType3 =ret.itemtype3;
		var itemDesc = ret.itemdesc;
		Global.Dialog.showDialog("update",{
			title:"领料审核规则明细信息--编辑",
			url:"${ctx}/admin/itemAppConfRule/goDetailUpdate",
			postData:{itemType1:itemType1,itemType2:itemType2,itemType3:itemType3,itemDesc:itemDesc,
			itemType2Com: itemType2Com["fieldJson"],itemType3Com: itemType3Com["fieldJson"],itemDescCom: itemDescCom["fieldJson"],},
			width:380,
			height:300,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							itemtype2:v.itemtype2,
							itemtype2desc:v.itemtype2desc,
							itemtype3:v.itemtype3,
							itemtype3desc:v.itemtype3desc,
							itemdesc:v.itemdesc,
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
		var rt = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!rt){
			art.dialog({content:"请选择一条记录",width:200});
		    return false;	
		}
		art.dialog({
			content:"是否删除该记录",
			lock:true,
			width:200,
			height:100,
			ok:function(){
				$("#dataTable").delRowData(rt);
				var col =$("#dataTable"). getDataIDs();
				if(col==""){
					$("#itemType1").prop("disabled",false);
				}
        	},
		cancel:function(){
			return true;
        }
		});
	});
 	//查看
	//领料审核规则明细信息修改
	$("#view").on("click",function(){
		var itemType1 = $("#itemType1").val();
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
		var param =$("#dataTable").jqGrid("getRowData",rowId);
		param.itemType1 = itemType1;
		Global.Dialog.showDialog("update",{
			title:"领料审核规则明细信息--查看",
			url:"${ctx}/admin/itemAppConfRule/goDetailView",
			postData:param,
			width:380,
			height:300,
		});
	});
	//保存 
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;//表单校验
		var ids = $("#dataTable").getDataIDs();
		if((!ids || ids.length == 0)){
			art.dialog({content: "明细表中无数据！",width: 200});
			return false;
		}
		var itemAppConfRuleDetail=$('#dataTable').jqGrid("getRowData");
		var param = {"itemAppConfRuleJson": JSON.stringify(itemAppConfRuleDetail)};
		Global.Form.submit("dataForm","${ctx}/admin/itemAppConfRule/doSave",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content: ret.msg,
					/* time: 1000, */
					beforeunload: function (){
	    				closeWin();
				    }
				});
			}else{
				$("#_form_token_uniq_id").val(ret.token.token);
	    		art.dialog({
					content: ret.msg,
					width: 200
				});
			}
			
		});
	});
});
//校验函数
$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			payPer : {
				validators : {
					notEmpty : {
						message : '付款比例不能为空',
					},
					numeric: {
	                    message:'只能输入数字'
	                }	
				}
			},
			prior : {
				validators : {
					notEmpty : {
						message : '优先级不能为空',
					},
					numeric: {
	                    message:'只能输入数字'
	                }
				}
			},
			itemType1 : {
				validators : {
					notEmpty : {
						message : '材料类型1不能为空',
					},
				}
			},
			diffAmount : {
				validators : {
					notEmpty : {
						message : '允许差额不能为空',
					},
					numeric: {
	                    message:'只能输入数字'
	                }
				}
			},
			payNum : {
				validators : {
					notEmpty : {
						message : '付款期数不能为空',
					},
				}
			},
			itemCost : {
				validators : {
					notEmpty : {
						message : '允许差额不能为空',
					},
					numeric: {
	                    message:'只能输入数字'
	                }
				}
			},
		},
		submitButtons : 'input[type="submit"]'
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
				<input type="hidden" name="expired" id="expired" value="${itemAppConfRule.expired}">
				<ul class="ul-form">	
					<div class="validate-group">		  
						<li>
							<label>批次号</label>
							<input type="text" id="no" name="no" placeholder="保存自动生成" readonly="readonly"/>
						</li>
						<li class="form-validate">	
							<label>付款比例</label>
							<input type="text" id=payPer name="payPer" value="${itemAppConfRule.payPer}"/>
						</li>
						<li>
							<label>付款类型</label>
							<house:xtdm id="payType" dictCode="TIMEPAYTYPE" value="${itemAppConfRule.payType}"></house:xtdm>
						</li>
					</div>
					<div class="validate-group">	
						<li>
							<label>客户类型</label>
							<house:dict id="custType" dictCode="" sql="select convert(int,code) code,code+' '+desc1 fd from tCusttype a  order by a.code" 
											 sqlValueKey="code" sqlLableKey="fd" value="${itemAppConfRule.custType}"></house:dict>	
						</li>
						<li class="form-validate">
							<label>优先级</label>
							<input type="text" id="prior" name="prior" value="${itemAppConfRule.prior}"/>
						</li>			
						<li class="form-validate">
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1" disabled="disabled"></select>
						</li>
					</div>	
					<div class="validate-group">
						<li class="form-validate">	
							<label>允许差额</label>
							<input type="text" id="diffAmount" name="diffAmount" value="${itemAppConfRule.diffAmount}"/>
						</li>
						<li class="form-validate">
							<label>付款期数</label>
							<house:xtdm id="payNum" dictCode="PAYNUM" value="${itemAppConfRule.payNum}"></house:xtdm>
						</li>
						<li class="form-validate">	
							<label>材料成本</label>
							<input type="text" id="itemCost" name="itemCost" value="${itemAppConfRule.itemCost}"/>
						</li>
					</div>
					<div class="validate-group">	
						<li>
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" >${itemAppConfRule.remarks}</textarea>
						</li>
						<li>
							<input type="checkbox" id="expired_show" name="expired_show"
						 					   onclick="checkExpired(this)" ${itemAppConfRule.expired=="T"?"checked":""} style="float: left;margin-left: 70px"/>
			 				<p style="float: right;margin-top: 5px;">过期</p>
						</li>
					</div>	
				</ul>				
			</form>
		</div>
	</div>
	<div  class="container-fluid" >  
	     <ul class="nav nav-tabs">
	        <li class="active"><a href="#tab_prjProvide" data-toggle="tab">领料审核规则明细信息</a></li>  
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
