<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>仓库转移-审核</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {
				validating : "glyphicon glyphicon-refresh"
			},
			fields: { 
				documentNo:{  
					validators: {  
						notEmpty: {  
							message: "凭证号不能为空" 
						},
					}  
				}, 
			},
			submitButtons : "input[type='submit']" 
		});
	});
	$(function(){
		$("#fromWHCode").openComponent_wareHouse({condition: {czybh:"${czybh}"},showValue:"${itemTransferHeader.fromWHCode}",showLabel:"${fromWHouse.desc1}",readonly:true});
		$("#toWHCode").openComponent_wareHouse({condition: {czybh:"${czybh}"},showValue:"${itemTransferHeader.toWHCode}",showLabel:"${toWHouse.desc1}",readonly:true});
		$("#appEmp").openComponent_employee({showValue:"${itemTransferHeader.appEmp}",showLabel:"${employee.nameChi}",readonly:true });
		$("#confirmEmp").openComponent_employee({showValue:"${itemTransferHeader.confirmEmp}",showLabel:"${itemTransferHeader.confirmCZYDescr}",readonly:true });
		$("#status_NAME").attr("disabled","disabled");
	});

	//新增是否允许重复
	function noRepeat(obj){
		if ($(obj).is(":checked")){
			$("#noRepeat").val("1");
		}else{
			$("#noRepeat").val("");
		}
	}
//校验函数
	$(function(){
		var gridOption = {
			postData:{no:$.trim("${itemTransferHeader.no}")
					},
			url:"${ctx}/admin/itemTransferHeader/getDetailBySql",
			height:$(document).height()-$("#content-list").offset().top-70,
			rowNum:10000000,
			styleUI: "Bootstrap", 
			colModel : [
				{name:"pk", index:"pk", width:105, label:"pk", sortable:true ,align:"left",hidden:true},
				{name:"ithno", index:"ithno", width:105, label:"仓库转移号码", sortable:true ,align:"left",hidden:true},
				{name:"seq", index:"seq", width:105, label:"排序", sortable:true ,align:"left",hidden:true},
				{name:"itcode", index:"itcode", width:105, label:"材料编号", sortable:true ,align:"left"},
				{name:"itdescr", index:"itdescr", width:200, label:"材料名称", sortable:true ,align:"left"},
				{name:"itemtype2descr", index:"itemtype2descr", width:76, label:"材料类型2", sortable : true,align : "left" ,},
				{name:"trfqty", index:"trfqty", width:76, label:"转移数量", sortable : true,align : "right" ,},
				{name:"avgcost",index:"avgcost",width:60, label:"成本单价", sortable:true,align:"right" ,},
				{name:"amountcost",	index:"amountcost",width:80, label:"成本总价", sortable : true,align : "right",sum:true},
				{name:"fromafterqty", index:"fromafterqty", width:80, label:"源变动后数量", sortable:true ,align:"right",},
				{name:"toafterqty",index:"toafterqty",width:85, label:"目的变动后数量", sortable:true,align:"right"},	
				{name:"detailremarks",index:"detailremarks",width:150, label:"备注", sortable:true,align:"left", },	
			],
		};
		var detailJson = Global.JqGrid.allToJson("dataTable","itcode");
		Global.JqGrid.initJqGrid("dataTable",gridOption);
		if('${costRight}'=='0'){
			jQuery("#dataTable").setGridParam().hideCol("avgcost").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("amountcost").trigger("reloadGrid");
		}
		$("#check").on("click",function(){
			$("#dataForm").bootstrapValidator("validate");
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
			var whCode = $.trim($("#whCode").val());
			var date = $.trim($("#date").val());
			var adjType = $.trim($("#adjType").val());
			var adjReason = $.trim($("#adjReason").val());
			var param= Global.JqGrid.allToJson("dataTable");
			var documentNo = $.trim($("#documentNo").val());
			if(documentNo==null||documentNo==""){
				art.dialog({
					content:"请输入凭证号",
				});
				return;
			}
			art.dialog({
				content:"是否确认要审核通过该转移单!",
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
					Global.Form.submit("dataForm","${ctx}/admin/itemTransferHeader/doSaveCheck",param,function(ret){
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
				},	
				cancel: function () {
					return true;
				}
			});	
		});
			
		$("#cancelCheck").on("click",function(){
			var whCode = $.trim($("#whCode").val());
			var date = $.trim($("#date").val());
			var adjType = $.trim($("#adjType").val());
			var adjReason = $.trim($("#adjReason").val());
			var param= Global.JqGrid.allToJson("dataTable");
			art.dialog({
				content:"是否确认要审核取消该转移单!",
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
					Global.Form.submit("dataForm","${ctx}/admin/itemTransferHeader/doSaveCancelCheck",param,function(ret){
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
				},	
				cancel: function () {
					return true;
				}
			});
		});
	});
	//查看
	function view(){
		var ret= selectDataTableRow("dataTable");
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		var fromWHCode = $.trim($("#fromWHCode").val());
		var toWHCode = $.trim($("#toWHCode").val());
	 	if(ret){
			Global.Dialog.showDialog("SaveUpdate",{
				title:"仓库转移——查看",
				url:"${ctx}/admin/itemTransferHeader/goAddView",
				postData:{fromWHCode:fromWHCode,
							toWHCode:toWHCode,
							itCode:ret.itcode,
							trfQty:ret.trfqty,
							detailRemarks:ret.detailremarks
				},
				height:550,          
				width:800,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}	 
	};
	</script>
</head>
  
<body>
	<div class="body-box-form">
		<div class="content-form">
  			<div class="panel panel-system">
    			<div class="panel-body">
   					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" style="color:#777777; cursor:default">
							<span>保存</span>
						</button>
 						<button type="button" class="btn btn-system" id="check">
 							<span>审核通过</span>
 						</button>	
 						<button type="button" class="btn btn-system" id="cancelCheck">
 							<span>审核取消</span>
 						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="edit-form">
				<div class="panel panel-info">  
	         		<div class="panel-body">
	         			<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
							<house:token></house:token>
							<input type="hidden" name="jsonString" value=""/>
							<ul class="ul-form">
								<li>
									<label>仓库转移号码</label>
									<input type="text" id="No" name="No" style="width:160px;" value="${itemTransferHeader.no }" readonly="readonly"/>                                             
								</li>
								<li>
									<label>状态</label>
									<house:xtdmMulit id="status" dictCode="BALADJSTATUS" selectedValue="${itemTransferHeader.status}"></house:xtdmMulit>
								</li>
								<li class="form-validate">
									<label>转移日期</label>
									<input type="text" style="width:160px;" onchange="validateRefresh()" id="date" name="date" class="i-date" value="<fmt:formatDate value='${itemTransferHeader.date}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>源仓库编号</label>
									<input type="text" id="fromWHCode" name="fromWHCode" style="width:160px;" value="${fromWHouse.code }"/>
								</li>
								<li>
									<label>申请日期</label>
									<input type="text" style="width:160px;" id="appDate" name="appDate" class="i-date" value="<fmt:formatDate value='${itemTransferHeader.appDate}' pattern='yyyy-MM-dd hh:mm:ss'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true"/>
								</li>
								<li>
									<label>申请人</label>
									<input type="text" id="appEmp" name="appEmp" style="width:160px;" value="${itemTransferHeader.appEmp }"/>
								</li>
								<li>
									<label>目的仓库编号</label>
									<input type="text" id="toWHCode" name="toWHCode" style="width:160px;" value="${toWHouse.code }"/>
								</li>
								<li>
									<label>审核日期</label>
									<input type="text" style="width:160px;" id="confirmDate" name="confirmDate" class="i-date" value="<fmt:formatDate value='${itemTransferHeader.confirmDate}' pattern='yyyy-MM-dd hh:mm:ss'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true"/>
								</li>
								<li>
									<label>审核人</label>
									<input type="text" id="confirmEmp" name="confirmEmp" style="width:160px;" value="${itemTransferHeader.confirmEmp}" disabled="true"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>凭证号</label>
									<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${itemTransferHeader.documentNo}"/>
								</li>
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2" style="width:160px">${itemTransferHeader.remarks }</textarea>
								</li>
							</ul>	
						</form>
					</div>
				</div>
			</div> 
			<div class="btn-panel">
   			  	<div class="btn-group-sm">
					<button type="button" class="btn btn-system" id="add" style="color:#777777; cursor:default">
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system" id="quickAdd" style="color:#777777; cursor:default">
						<span>快速新增</span>
					</button>
					<button type="button" class="btn btn-system" id="update" style="color:#777777; cursor:default">
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system" id="delete" style="color:#777777; cursor:default">
						<span>删除</span>
					</button>
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system" onclick="doExcelNow('仓库转移明细','dataTable','dataForm')" title="导出检索条件数据">
						<span>输出到excel</span>
					</button>
					<span style="vertical-align:middle;margin-left:30px;color:blue">
						成本及变动后数量，以审核后数据为准。
					</span>
				</div>
			</div>		
			<div id="content-list">
				<table id="dataTable"></table>
			</div>	
		</div>
	</div>
</body>
</html>
