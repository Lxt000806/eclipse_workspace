<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>供应商结算审核</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript" defer></script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${supplCheckConfirm.expired}"/>
				<input type="hidden" name="jsonString"/>
				<ul class="ul-form">
					<li>
						<label>材料类型1</label>
						<house:DictMulitSelect id="itemType1" dictCode="" 
							sql="select rtrim(Code) Code,Descr from tItemType1 where Expired<>'T' and Code in (${itemRight}) order by DispSeq" 
							sqlLableKey="Descr" sqlValueKey="Code">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>供应商编号</label> 
						<input type="text" id="supplier" name="supplier" style="width:160px;"/>
					</li>
					<li>
						<label>供应商状态</label> 
						<house:xtdmMulit id="splStatus" dictCode="PuSplStatus" selectedValue="1,4"></house:xtdmMulit>
					</li>
					<li>
						<label>申请结算时间从</label>
						<input type="text" id="appCheckDateFrom" name="appCheckDateFrom" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="appCheckDateTo" name="appCheckDateTo" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>发货时间从</label>
						<input type="text" id="sendDateFrom" name="sendDateFrom" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="sendDateTo" name="sendDateTo" class="i-date" style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>楼盘</label> 
						<input type="text" id="address" name="address" style="width:160px;"/>
					</li>
					<li>
						<label>包含结算完成</label>
						<house:xtdm id="isIncludeSetComp" dictCode="YESNO" style="width:160px;" value="0"/>
					</li>
					<li>
						<label>结算单状态</label>
						<house:xtdmMulit id="checkOutStatus" dictCode="SPLCKOTSTATUS"></house:xtdmMulit>
					</li>
					<li>
					    <label>领料单号</label>
					    <input type="text" name="IANo"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " id="clear" 
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SUPPLCHECKCONFIRM_CHECK">
					<button type="button" class="btn btn-system" id="check">
						<span>审核</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SUPPLCHECKCONFIRM_RETCHECK">
					<button type="button" class="btn btn-system" id="retCheck">
						<span>反审核</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SUPPLCHECKCONFIRM_VIEW">
					<button type="button" class="btn btn-system" id="view">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SUPPLCHECKCONFIRM_EXCEL">
					<button type="button" class="btn btn-system" id="excel">
						<span>导出Excel</span>
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
	<script type="text/javascript">
		var postData = $("#page_form").jsonForm();
		postData.splStatus = "1";
		$(function(){
			$("#supplier").openComponent_supplier();
			Global.JqGrid.initJqGrid("dataTable", {
				url: "${ctx}/admin/supplCheckConfirm/goJqGrid",
				postData:postData ,
				sortable: true,
				height: $(document).height() - $("#content-list").offset().top - 68,
				styleUI: "Bootstrap", 
				colModel: [
					{name:"no", index:"no", width:80, label:"采购单号", sortable:true, align:"left"},
					{name:"iano", index:"iano", width:80, label:"领料单号", sortable:true, align:"left"},
					{name:"address", index:"address", width:180, label:"楼盘", sortable:true, align:"left"},
					{name:"custtype", index:"custtype", width:80, label:"客户类型", sortable:true, align:"left", hidden:true},
					{name:"custtypedescr", index:"custtypedescr", width:80, label:"客户类型", sortable:true, align:"left"},
					{name:"layoutdescr", index:"layoutdescr", width:60, label:"户型", sortable:true, align:"left"},
					{name:"region", index:"region", width:60, label:"片区", sortable:true, align:"left", hidden:true},
					{name:"regiondescr", index:"regiondescr", width:60, label:"片区", sortable:true, align:"left"},
					{name:"itemtype1descr", index:"itemtype1descr", width:80, label:"材料类型1", sortable:true, align:"left"},
					{name:"iscupboarddescr", index:"iscupboarddescr", width:80, label:"是否橱柜", sortable:true, align:"left"},
					{name:"supplierdescr", index:"supplierdescr", width:170, label:"供应商", sortable:true, align:"left"},
					{name:"splstatus", index:"splstatus", width:80, label:"供应商状态", sortable:true, align:"left" ,hidden:true},
					{name:"splstatusdescr", index:"splstatusdescr", width:80, label:"供应商状态", sortable:true, align:"left"},
					{name:"appcheckdate", index:"appcheckdate", width:120, label:"申请结算时间", sortable:true, align:"left", formatter:formatTime},
					{name:"amount", index:"amount", width:80, label:"材料金额", sortable:true, align:"right"},
					{name:"splamount", index:"splamount", width:80, label:"对账金额", sortable:true, align:"right"},
					{name:"diffamount", index:"diffamount", width:80, label:"差额", sortable:true, align:"right"},
					{name:"projectamount", index:"projectamount", width:110, label:"项目经理结算价", sortable:true, align:"right"},
					{name:"projectothercost", index:"projectothercost", width:100, label:"项目经理调整", sortable:true, align:"right"},
					{name:"othercost", index:"othercost", width:80, label:"其它费用", sortable:true, align:"right"},
					{name:"erpothercost", index:"erpothercost", width:120, label:"系统计算其它费用", sortable:true, align:"right"},
					{name:"diffcost", index:"diffcost", width:80, label:"差额", sortable:true, align:"right"},
					{name:"othercostadj", index:"othercostadj", width:100, label:"其它费用调整", sortable:true, align:"right"},
					{name:"remainamount", index:"remainamount", width:100, label:"应付金额", sortable:true, align:"right"},
					{name:"profitper", index:"profitper", width:100, label:"毛利率", sortable:true, align:"right"},
					{name:"checkconfirmdate", index:"checkconfirmdate", width:120, label:"结算审核时间", sortable:true, align:"left", formatter:formatTime},
					{name:"checkconfirmczydescr", index:"checkconfirmczydescr", width:70, label:"审核人", sortable:true, align:"left"},
					{name:"checkconfirmremarks", index:"checkconfirmremarks", width:200, label:"结算审核说明", sortable:true, align:"left"},
					{name:"checkoutstatus", index:"checkoutstatus", width:110, label:"供应商结算单状态", sortable:true, align:"left", hidden:true},
					{name:"checkoutstatusdescr", index:"checkoutstatusdescr", width:110, label:"结算单状态", sortable:true, align:"left"},
					{name:"itemapptype", index:"itemapptype", width:110, label:"领料申请状态", sortable:true, align:"left", hidden:true},
				],
				ondblClickRow: function(){
					view();
				}
			});
			$("#clear").on("click",function(){
				$("#itemType1").val("");
				$("#splStatus").val("");
				$.fn.zTree.getZTreeObj("tree_itemType1").checkAllNodes(false);
				$.fn.zTree.getZTreeObj("tree_splStatus").checkAllNodes(false);
				$.fn.zTree.getZTreeObj("tree_checkOutStatus").checkAllNodes(false);
			});
			$("#excel").on("click", function() {
				doExcel();
			});
			$("#check").on("click",function(){
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				if ("0" == ret.splstatus || "2" == ret.splstatus) {
					art.dialog({
						content: "待结算或结算已审核状态不能进行审核操作"
					});
					return;
				}
				
				if ("2" == $.trim(ret.checkoutstatus)) {
					art.dialog({
						content: "该单供应商结算已审核"
					});
					return;
				} 
				Global.Dialog.showDialog("check",{
					title:"供应商结算审核——审核",
					url:"${ctx}/admin/supplCheckConfirm/goCheck",
					postData:{
						no:ret.no,
						isIncludeSetComp: $("#isIncludeSetComp").val()
					},
					height:725,
					width:1048,
					returnFun:goto_query
				});
			});
			/*
			$("#retCheck").on("click",function(){
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				if ("2" != ret.splstatus) {
					art.dialog({
						content: "只有已审核状态才可以反审核"
					});
					return;
				} 
				Global.Dialog.showDialog("retCheck",{
					title:"供应商结算审核——反审核",
					url:"${ctx}/admin/supplCheckConfirm/goRetCheck",
					postData:{
						no:ret.no,
						isIncludeSetComp: $("#isIncludeSetComp").val()
					},
					height:711,
					width:1048,
					returnFun:goto_query
				});
			});
			*/
	        $("#retCheck").on("click",function(){
	        	var ret= selectDataTableRow();
	        	
	        	if(ret){
		        	if ("2" != ret.splstatus) {
						art.dialog({
							content: "只有已审核状态才可以反审核"
						});
						return;
					}
					$.ajax({
						url : "${ctx}/admin/supplCheckConfirm/getGoRetCheckBefore",
						data: {
							no:ret.no.trim()
						},
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						dataType: "json",
						type: "post",
						cache: false,
						error: function(){
					        art.dialog({
								content: "反审核操作出现异常"
							});
					    },
					    success:function(obj){
					    	if(obj.rs){
					        	Global.Dialog.showDialog("retCheck",{
									title:"供应商结算审核——反审核",
									url:"${ctx}/admin/supplCheckConfirm/goRetCheck",
									postData:{
										no:ret.no,
										isIncludeSetComp: $("#isIncludeSetComp").val()
									},
									height:711,
									width:1048,
									returnFun:goto_query
								});
					    	}else{
					    		art.dialog({
					    			content:obj.msg
					    		});
					    	}
					    }
	    			});
		        }else{
		        	art.dialog({
		        		content:"请选择一条记录"
		        	});
		        }	     	
	        });
	        
			$("#view").on("click", function () {
				view();
			});
		});
		function view(){
			var ret=selectDataTableRow();
			if (!ret) {
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			Global.Dialog.showDialog("view",{
				title:"供应商结算审核——查看",
				url:"${ctx}/admin/supplCheckConfirm/goView",
				postData:{
					m_umState:"V",
					no:ret.no,
					isIncludeSetComp: $("#isIncludeSetComp").val(),
				},
				height:711,
				width:1048,
				returnFun:goto_query
			});
		}
		function doExcel(){
			var url = "${ctx}/admin/supplCheckConfirm/doExcel";
			doExcelAll(url);
		}
	</script>
</body>
</html>
