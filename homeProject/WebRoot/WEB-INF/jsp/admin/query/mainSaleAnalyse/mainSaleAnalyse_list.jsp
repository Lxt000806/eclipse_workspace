<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>主材独立销售分析</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp"%>
		<script type="text/javascript">
			$(function(){
				Global.JqGrid.initJqGrid("dataTable",{
					//url:"${ctx}/admin/mainSaleAnalyse/goJqGrid",
					height:$(document).height()-$("#content-list").offset().top-100,
					styleUI: 'Bootstrap',
					postData:$("#page_form").jsonForm(),
					colModel : [
						{name: "empcode", index: "empcode", width: 94, label: "管家", sortable: true, align: "left",hidden:true},
						{name: "empname", index: "empname", width: 80, label: "管家", sortable: true, align: "left"},
						{name: "supplcode", index: "supplcode", width: 94, label: "品牌", sortable: true, align: "left",hidden:true},
						{name: "suppldescr", index: "suppldescr", width: 200, label: "品牌", sortable: true, align: "left"},
						{name: "dept1code", index: "dept1code", width: 94, label: "一级部门", sortable: true, align: "left",hidden:true},
						{name: "dept1descr", index: "dept1descr", width: 120, label: "一级部门", sortable: true, align: "left",hidden:true},
						{name: "saleamount", index: "saleamount", width: 80, label: "销售额", sortable: true, align: "right",sum:true},
					],
					loadonce:true
				});
			});
			function view(){
				var ret = selectDataTableRow();
				if(ret){
					Global.Dialog.showDialog("mainSaleAnalyseView",{
						title:"主材独立销售分析-查看",
						url:"${ctx}/admin/mainSaleAnalyse/goView",
						postData:{
							empCode:ret.empcode,supplierCode:ret.supplcode,department1:ret.dept1code,
							dateFrom:$("#dateFrom").val(),dateTo:$("#dateTo").val(),
							role:$("#role").val(),itemType12:$("#itemType12").val(),
							custType:$("#custType").val()
						},
						height:650,
						width:1300,
						returnFun:goto_query
					});
				}else{
				    art.dialog({
						content: "请选择一条记录"
					});
				}
			}
			function doExcel(){
				if(!$("#dateFrom").val()){
					art.dialog({
						content:"请选择一个开始时间"
					});
					return ;
				}
				if(!$("#dateTo").val()){
					art.dialog({
						content:"请选择一个结束时间"
					});
					return ;
				}
				doExcelNow("主材独立销售分析");
			}
			function goto_query(){
				var statistcsMethod = $("#statistcsMethod").val();
				if(!$("#dateFrom").val()){
					art.dialog({
						content:"请选择一个开始时间"
					});
					return ;
				}
				if(!$("#dateTo").val()){
					art.dialog({
						content:"请选择一个结束时间"
					});
					return ;
				}
				
				if(statistcsMethod=="1"){
					$("#dataTable").jqGrid('showCol', "empname");
					$("#dataTable").jqGrid('showCol', "suppldescr");
					$("#dataTable").jqGrid('hideCol', "dept1descr");
				}else if(statistcsMethod=="2"){
					$("#dataTable").jqGrid('showCol', "dept1descr");
					$("#dataTable").jqGrid('hideCol', "empname");
					$("#dataTable").jqGrid('hideCol', "suppldescr");
				}else{
					$("#dataTable").jqGrid('showCol', "empname");
					$("#dataTable").jqGrid('hideCol', "suppldescr");
					$("#dataTable").jqGrid('hideCol', "dept1descr");
				}
				
				$("#dataTable").jqGrid("setGridParam",{
						datatype:"json",
						postData:$("#page_form").jsonForm(),
						page:1,
						url:"${ctx}/admin/mainSaleAnalyse/goJqGrid"
					}
				).trigger("reloadGrid"); 
			}
			function clearForm(){
			    $("#page_form input[type='text']").val('');
		        $("#custType").val('');
		        $.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
		        $("#itemType12").val('');
		        $.fn.zTree.getZTreeObj("tree_itemType12").checkAllNodes(false);
			}
		</script>
	</head>
	<body>
		<div class="body-box-list">
			<div class="query-form" >
				<form action="" method="post" id="page_form" class="form-search" >
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>统计时间从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${customer.dateFrom }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${customer.dateTo }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>客户类型</label>
							<house:DictMulitSelect id="custType" dictCode="" sql="select Code,Desc1 from tCustType where IsPartDecorate = '3'  " 
							sqlLableKey="desc1" sqlValueKey="code"   ></house:DictMulitSelect>
						</li>
						<li>
							<label>材料分类</label>
							<house:DictMulitSelect id="itemType12" dictCode="" sql="select Code,Descr from tItemType12 where ItemType1 = 'ZC'  " 
							sqlLableKey="descr" sqlValueKey="code"   ></house:DictMulitSelect>
						</li>
						<li>		
							<label>统计方式</label>
								<select id="statistcsMethod" name="statistcsMethod" >
									<option value="1">按个人+品牌</option>
									<option value="2">按一级部门</option>
									<option value="3">按个人</option>
								</select>
							</label>	
						</li>
						<li>		
							<label>角色</label>
								<select id="role" name="role" >
									<option value="01">业务员</option>
									<option value="00">设计师</option>
								</select>
							</label>	
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
					</ul>	
				</form>
			</div>
			
			<div class="btn-panel" >
				<div class="btn-group-sm" >
					<house:authorize authCode="PRJDEPTPERF_VIEW">
						<button type="button" class="btn btn-system " id="viewBtn" onclick="view()">查看</button>
					</house:authorize>
					<house:authorize authCode="PRJDEPTPERF_EXCEL">
						<button type="button" class="btn btn-system"  id="excelBtn" onclick="doExcel()">导出excel</button>
					</house:authorize>
				</div>
			</div>
					
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>
			</div> 
		</div>
	</body>
</html>


