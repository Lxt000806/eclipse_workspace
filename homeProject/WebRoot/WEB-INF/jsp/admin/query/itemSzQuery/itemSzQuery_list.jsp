<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>材料收支信息查询</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp"%>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">
			function clearForm(){
				$("#page_form input[type='text']").val('');
				$("#page_form select").val('');
				$("#status").val('');
				$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
				$("#checkStatus").val('');
				$.fn.zTree.getZTreeObj("tree_checkStatus").checkAllNodes(false);
				$("#endCode").val('');
				$.fn.zTree.getZTreeObj("tree_endCode").checkAllNodes(false);
			}
			/**初始化表格*/
			$(function(){
				//var postData = $("#page_form").jsonForm();
				//postData.status="4,5";
					Global.JqGrid.initJqGrid("dataTable",{
						//url:"${ctx}/admin/itemSzQuery/goJqGrid",
						height:$(document).height()-$("#content-list").offset().top-70,
						//postData:postData,
						styleUI: 'Bootstrap',
						colModel : [
					    {name: "documentno", index: "documentno", width: 94, label: "档案编号", sortable: true, align: "left", frozen: true},
						{name: "code", index: "code", width: 90, label: "客户编号", sortable: true, align: "left", frozen: true},
						{name: "descr", index: "descr", width: 90, label: "客户名称", sortable: true, align: "left", frozen: true},
						{name: "genderdescr", index: "genderdescr", width: 50, label: "性别", sortable: true, align: "left", frozen: true},
						{name: "sourcedescr", index: "sourcedescr", width: 80, label: "客户来源", sortable: true, align: "left", frozen: true},
						{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left", frozen: true},
						{name: "layoutdescr", index: "layoutdescr", width: 110, label: "户型", sortable: true, align: "left"},
						{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "left"},
						{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
						{name: "designstyledescr", index: "designstyledescr", width: 80, label: "设计风格", sortable: true, align: "left"},
						{name: "statusdescr", index: "statusdescr", width: 90, label: "客户状态", sortable: true, align: "left"},
						{name: "endcodedescr", index: "endcodedescr", width: 80, label: "结束代码", sortable: true, align: "left"},
						{name: "enddate", index: "enddate", width: 90, label: "结束时间", sortable: true, align: "left", formatter: formatTime},
						{name: "custcheckdate", index: "custcheckdate", width: 100, label: "客户结算日期", sortable: true, align: "left", formatter: formatTime},
						{name: "setdate", index: "setdate", width: 90, label: "下定时间", sortable: true, align: "left", formatter: formatTime},
						{name: "signdate", index: "signdate", width: 90, label: "签订时间", sortable: true, align: "left", formatter: formatTime},
						{name: "endremark", index: "endremark", width: 90, label: "结束原因", sortable: true, align: "left"},
						{name: "contractfee", index: "contractfee", width: 90, label: "工程造价", sortable: true, align: "left"},
						{name: "designfee", index: "designfee", width: 90, label: "设计费", sortable: true, align: "left"},
						{name: "measurefee", index: "measurefee", width: 90, label: "量房费", sortable: true, align: "left"},
						{name: "drawfee", index: "drawfee", width: 90, label: "制图费", sortable: true, align: "left"},
						{name: "colordrawfee", index: "colordrawfee", width: 90, label: "效果图费", sortable: true, align: "left"},
						{name: "managefee", index: "managefee", width: 90, label: "管理费", sortable: true, align: "left"},
						{name: "basefee", index: "basefee", width: 90, label: "基础费", sortable: true, align: "left"},
						{name: "basedisc", index: "basedisc", width: 90, label: "基础优惠", sortable: true, align: "left"},
						{name: "basefee_dirct", index: "basefee_dirct", width: 90, label: "基础直接费", sortable: true, align: "left"},
						{name: "basefee_comp", index: "basefee_comp", width: 90, label: "基础综合费", sortable: true, align: "left"},
						{name: "mainfee", index: "mainfee", width: 90, label: "主材费", sortable: true, align: "left"},
						{name: "maindisc", index: "maindisc", width: 90, label: "主材优惠", sortable: true, align: "left"},
						{name: "mainservfee", index: "mainservfee", width: 98, label: "服务性产品费", sortable: true, align: "left"},
						{name: "softfee", index: "softfee", width: 90, label: "软装费", sortable: true, align: "left"},
						{name: "softdisc", index: "softdisc", width: 90, label: "软装优惠", sortable: true, align: "left"},
						{name: "softother", index: "softother", width: 108, label: "软装其它费用", sortable: true, align: "left"},
						{name: "integratefee", index: "integratefee", width: 90, label: "集成费", sortable: true, align: "left"},
						{name: "integratedisc", index: "integratedisc", width: 90, label: "集成优惠", sortable: true, align: "left"},
						{name: "cupboardfee", index: "cupboardfee", width: 90, label: "橱柜费", sortable: true, align: "left"},
						{name: "cupboarddisc", index: "cupboarddisc", width: 90, label: "橱柜优惠", sortable: true, align: "left"},
						{name: "mobile1", index: "mobile1", width: 110, label: "手机1", sortable: true, align: "left", hidden: true},
						{name: "mobile2", index: "mobile2", width: 110, label: "手机2", sortable: true, align: "left", hidden: true},
						{name: "qq", index: "qq", width: 110, label: "qq", sortable: true, align: "left", hidden: true},
						{name: "email2", index: "email2", width: 130, label: "email2", sortable: true, align: "left", hidden: true},
						{name: "crtdate", index: "crtdate", width: 130, label: "创建时间", sortable: true, align: "left", formatter: formatTime},
						{name: "desidpt1", index: "desidpt1", width: 100, label: "设计师部门一", sortable: true, align: "left"},
						{name: "desidpt2", index: "desidpt2", width: 100, label: "设计师部门二", sortable: true, align: "left"},
						{name: "desidpt3", index: "desidpt3", width: 100, label: "设计师部门三", sortable: true, align: "left"},
						{name: "designmandescr", index: "designmandescr", width: 90, label: "设计师", sortable: true, align: "left"},
						{name: "businessmandescr", index: "businessmandescr", width: 90, label: "业务员", sortable: true, align: "left"},
						{name: "softdesigndescr", index: "softdesigndescr", width: 90, label: "软装设计师", sortable: true, align: "left"},
						{name: "remarks", index: "remarks", width: 170, label: "备注", sortable: true, align: "left"},
						{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
						{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "修改人", sortable: true, align: "left"},
						{name: "expired", index: "expired", width: 120, label: "是否过期", sortable: true, align: "left"},
						{name: "actionlog", index: "actionlog", width: 120, label: "操作", sortable: true, align: "left"}
			            ],
						loadComplete: function(){
							frozenHeightReset("dataTable");
						}
					});
					$("#dataTable").jqGrid('setFrozenColumns');
					$("#role").openComponent_roll();
					$("#empCode").openComponent_employee();
					$("#code").openComponent_customer();
					onCollapse(136);
					
			});
			
			function jcszxx(){
				var ret = selectDataTableRow();
				
				if(ret){
					Global.Dialog.showDialog("jcszxx",{
						title:"基础收支信息【"+ret.address+"】",
						url:"${ctx}/admin/itemSzQuery/goJcszxx?code="+ret.code,
						height:730,
						width:1350
					});
				}else{
					art.dialog({
						content:"请选择一条信息"
					});
				}
			}
			
			function zcrzszxx(){
				var ret = selectDataTableRow();
				
				if(ret){
					Global.Dialog.showDialog("zcrzszxx",{
						title:"主材软装收支信息【"+ret.address+"】",
						url:"${ctx}/admin/itemSzQuery/goZcrzszxx?code="+ret.code,
						height:730,
						width:1350
					});
				}else{
					art.dialog({
						content:"请选择一条信息"
					});
				}
			}
			
			function doPrint(){
				var ret = selectDataTableRow();
				
				if(ret){
					console.log("doPrint");
				}else{
					art.dialog({
						content:"请选择一条信息"
					});
				}
			}
			
			function doExcel(){
				var url = "${ctx}/admin/itemSzQuery/doExcel";
				doExcelAll(url);
			}
			
			function goto_query(){
				var postData = $("#page_form").jsonForm();
				//postData.status="4,5";
				$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goJqGrid",postData:postData,page:1,sortname:''}).trigger("reloadGrid");
			}
		</script>
	</head>
	<body>
		<div class="body-box-list">
			<div class="query-form" >
				<form action="" method="post" id="page_form" class="form-search" >
					<input type="hidden" id="expired" name="expired" value="${customer.expired}" />
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>客户名称</label>
							<input type="text" id="descr" name="descr"  />
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address"  />
						</li>
						<li>
							<label>客户状态</label>
							<house:xtdmMulit id="status" dictCode="" sql=" select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM WHERE ID='CUSTOMERSTATUS' AND CBM IN (4,5)" selectedValue="4,5"></house:xtdmMulit>
						</li>
						<li>
							<label>角色</label>
							<input type="text" id="role" name="role"   />
						</li>
						<li>
							<label>干系人编号</label>
							<input type="text" id="empCode" name="empCode"  />
						</li>
						<li>
							<label>客户来源</label>
							<house:xtdm id="source" dictCode="CUSTOMERSOURCE" value="${customer.source }"></house:xtdm>
						</li>
						<li>
							<label>客户编号</label>
							<input type="text" id="code" name="code"  />
						</li>
						<li id="loadMore" >
							<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">更多</button>
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
						<div  class="collapse " id="collapse" >
							<ul>
								<li>
									<!-- <label>客户结算状态</label> -->
									<label>结算状态</label>
									<house:xtdmMulit id="checkStatus" dictCode="CheckStatus" selectedValue="${customer.checkStatus }"></house:xtdmMulit>
								</li>
								<li>
									<label>一级部门</label>
									<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${customer.department1 }"></house:department1>
								</li>
								<li>
									<label>二级部门</label>
									<house:department2 id="department2" dictCode="${customer.department1 }" value="${customer.department2 }" onchange="changeDepartment2(this,'department3','${ctx }')"></house:department2>
								</li>
								<li>
									<label>三级部门</label>
									<house:department3 id="department3" dictCode="${customer.department2 }" value="${customer.department3 }"></house:department3>
								</li>
				<%-- 				<li>
									<label>结束代码</label>
									<house:xtdmMulit id="endCode" dictCode="CUSTOMERENDCODE" selectedValue="${customer.endCode }"></house:xtdmMulit>
								</li> --%>
								<li>
									<label>下定时间从</label>
									<input type="text"  id="setDateFrom" name="setDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.setDateFrom }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>到</label>
									<input type="text"  id="setDateTo" name="setDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.setDateTo }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>签订时间从</label>
									<input type="text"  id="signDateFrom" name="signDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.signDateFrom }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>到</label>
									<input type="text"  id="signDateTo" name="signDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.signDateTo }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>创建时间从</label>
									<input type="text"  id="crtDateFrom" name="crtDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.crtDateFrom }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>到</label>
									<input type="text" id="crtDateTo" name="crtDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.crtDateTo }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>结束时间从</label>
									<input type="text"  id="endDateFrom" name="endDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.endDateFrom }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>到</label>
									<input type="text"  id="endDateTo" name="endDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.endDateTo }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li class="search-group-shrink" >
									<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">收起</button>
									<input type="checkbox" id="expired_show" name="expired_show"
														value="${customer.expired}" onclick="hideExpired(this)"
									${customer.expired!='T' ?'checked':'' }/><p>隐藏过期</p>
									<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
									<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
								</li>
							</ul>
						</div> 
					</ul>	
				</form>
			</div>
			
			<div class="btn-panel" >
				<div class="btn-group-sm" >
					<house:authorize authCode="ITEMSZQUERY_JCSZXX">
						<button type="button" class="btn btn-system " id="jcszxxBtn" onclick="jcszxx()">基础收支信息</button>
					</house:authorize>
					<house:authorize authCode="ITEMSZQUERY_ZCRZSZXX">
						<button type="button" class="btn btn-system "  id="zcrzszxxBtn" onclick="zcrzszxx()">主材软装收支信息</button>
					</house:authorize>
	<%-- 				<house:authorize authCode="ITEMSZQUERY_PRINT">
						<button type="button" class="btn btn-system "  id="printBtn" onclick="doPrint()">打印</button>
					</house:authorize> --%>
					<house:authorize authCode="ITEMSZQUERY_EXCEL">
						<button type="button" class="btn btn-system"  id="excelBtn" onclick="doExcel()">导出excel</button>
					</house:authorize>
				</div>
			</div>
			
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div>
			</div> 
		</div>
	</body>
</html>


