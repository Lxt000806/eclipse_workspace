<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>custItemConfirm列表</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp"%>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<style type="text/css">
			.SelectBG{
				background-color:#FF7575!important;
			}    
		</style>
		<script type="text/javascript">
		function clearForm(){
			$("#page_form input[type='text'][id!='openComponent_employee_empCode'][id!='empCode']").val('');
			$("#page_form input[type='text'][id!='openComponent_employee_mainBusinessMan'][id!='mainBusinessMan']").val('');
			$("#custType").val('');
			$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
		}
		
		function loadJqGrid(firstFlag){
			if(firstFlag != true){
				$("#gbox_dataTable").parent().append("<table id= \"dataTable\"></table><div id=\"dataTablePager\"></div>");
				$("#gbox_dataTable").remove();
			}
			var colModel = [
					{name : 'code',index : 'code',width : 70,label:'客户编号',sortable : true,align : "left",hidden:true},
					{name : 'descr',index : 'descr',width : 60,label:'客户名称',sortable : true,align : "left",hidden:true},
					{name : 'address',index : 'address',width : 200,label:'楼盘',sortable : true,align : "left"},
					{name : 'custtype',index : 'custtype',width : 100,label:'客户类型',sortable : true,align : "left",hidden:true},
					{name : 'custtypedescr',index : 'custtypedescr',width : 70,label:'客户类型',sortable : true,align : "left"},
					{name : 'status',index : 'status',width : 100,label:'客户状态',sortable : true,align : "left",hidden:true},
					{name : 'statusdescr',index : 'statusdescr',width : 70,label:'客户状态',sortable : true,align : "left"},
					{name : 'area',index : 'area',width : 70,label:'面积',sortable : true,align : "right"},
					{name : 'mainprefee',index : 'mainprefee',width : 70,label:'预算金额',sortable : true,align : "right"},
					{name : 'designmandescr',index : 'designmandescr',width : 55,label:'设计师',sortable : true,align : "left"},
					{name : 'designmanphone',index : 'designmanphone',width : 100,label:'设计师电话',sortable : true,align : "left"},
					{name : 'designmanpart',index : 'designmanpart',width : 55,label:'设计所',sortable : true,align : "left"},
					
					{name : 'custscencedescr',index : 'custscencedescr',width : 80,label:'现场设计师',sortable : true,align : "left"},
					
					{name : 'zcmanagerdescr',index : 'zcmanagerdescr',width : 80,label:'主材管家',sortable : true,align : "left"},
					{name : 'confirmbegin',index : 'confirmbegin',width : 90,label:'实际开工时间',sortable : true,align : "left",formatter:formatDate},
						   
					{name : 'zcdesignmandescr',index : 'zcdesignmandescr',width : 80,label:'主材设计师',sortable : true,align : "left",hidden:true},
					{name : 'prjitemdescr',index : 'prjitemdescr',width : 80,label:'当前进度',sortable : true,align : "left"},
					{name : 'confitem',index : 'confitem',width : 75,label:'当前确认节点',sortable : true,align : "left",hidden:true},
					{name : 'confitemdescr',index : 'confitemdescr',width : 93,label:'当前确认节点',sortable : true,align : "left"},
					{name : 'confirmstartdate',index : 'confirmstartdate',width : 93,label:'确认开始时间',sortable : true,align : "left", formatter: formatTime},
					{name : 'checkconfirmdate',index : 'checkconfirmdate',width : 93,label:'确认结束时间',sortable : true,align : "left", formatter: formatTime},
					{name : 'overdate',index : 'overdate',width : 80,label:'超期天数',sortable : true,align : "left", },
					{name : 'informdate',index : 'informdate',width : 90,label:'最近预约时间',sortable : true,align : "right", formatter: formatTime},
					{name : 'informremark',index : 'informremark',width : 75,label:'预约说明',sortable : true,align : "left"},
					{name : 'runday',index : 'runday',width : 75,label:'runday',sortable : true,align : "left",hidden:true},
					{name : 'confirmdate',index : 'confirmdate',width : 93,label:'最近确认时间',sortable : true,align : "left", formatter: formatTime},
					{name : 'confirmremarks',index : 'confirmremarks',width : 75,label:'确认说明',sortable : true,align : "left"},
					{name : 'projectman',index : 'projectman',width : 70,label:'项目经理',sortable : true,align : "left",hidden:true},
					{name : 'projectmandescr',index : 'projectmandescr',width : 70,label:'项目经理',sortable : true,align : "left"},
					{name : 'projectmanphone',index : 'projectmanphone',width : 93,label:'项目经理电话',sortable : true,align : "left"},
					{name : 'projectmanpart',index : 'projectmanpart',width : 60,label:'工程部',sortable : true,align : "left"}
				];
			if($("#showType").val() == "02"){
				//瓷砖
				colModel.push({name : 'itemconfstatus1descr',index : 'itemconfstatus1descr',width : 50,label:'确认状态',sortable : true,align : "left"});
				colModel.push({name : 'czysamount',index : 'czysamount',width : 65,label:'预算额',sortable : true,align : "right"});
				colModel.push({name : 'czqramount',index : 'czqramount',width : 65,label:'确认额',sortable : true,align : "right"});
				colModel.push({name : 'czxdamount',index : 'czxdamount',width : 65,label:'下单额',sortable : true,align : "right"});
				//地漏
				colModel.push({name : 'itemconfstatus2descr',index : 'itemconfstatus2descr',width : 50,label:'确认状态',sortable : true,align : "left"});
				colModel.push({name : 'dlysamount',index : 'dlysamount',width : 65,label:'预算额',sortable : true,align : "right"});
				colModel.push({name : 'dlqramount',index : 'dlqramount',width : 65,label:'确认额',sortable : true,align : "right"});
				colModel.push({name : 'dlxdamount',index : 'dlxdamount',width : 65,label:'下单额',sortable : true,align : "right"});
				//挡水石
				colModel.push({name : 'itemconfstatus3descr',index : 'itemconfstatus3descr',width : 50,label:'确认状态',sortable : true,align : "left"});
				colModel.push({name : 'dssysamount',index : 'dssysamount',width : 65,label:'预算额',sortable : true,align : "right"});
				colModel.push({name : 'dssqramount',index : 'dssqramount',width : 65,label:'确认额',sortable : true,align : "right"});
				colModel.push({name : 'dssxdamount',index : 'dssxdamount',width : 65,label:'下单额',sortable : true,align : "right"});
				//淋浴房
				colModel.push({name : 'itemconfstatus4descr',index : 'itemconfstatus4descr',width : 50,label:'确认状态',sortable : true,align : "left"});
				colModel.push({name : 'lyfysamount',index : 'lyfysamount',width : 65,label:'预算额',sortable : true,align : "right"});
				colModel.push({name : 'lyfqramount',index : 'lyfqramount',width : 65,label:'确认额',sortable : true,align : "right"});
				colModel.push({name : 'lyfxdamount',index : 'lyfxdamount',width : 65,label:'下单额',sortable : true,align : "right"});
				//水槽
				colModel.push({name : 'itemconfstatus11descr',index : 'itemconfstatus11descr',width : 50,label:'确认状态',sortable : true,align : "left"});
				colModel.push({name : 'scaoysamount',index : 'scaoysamount',width : 65,label:'预算额',sortable : true,align : "right"});
				colModel.push({name : 'scaoqramount',index : 'scaoqramount',width : 65,label:'确认额',sortable : true,align : "right"});
				colModel.push({name : 'scaoxdamount',index : 'scaoxdamount',width : 65,label:'下单额',sortable : true,align : "right"});
				//石材
				colModel.push({name : 'itemconfstatus5descr',index : 'itemconfstatus5descr',width : 50,label:'确认状态',sortable : true,align : "left"});
				colModel.push({name : 'scaiysamount',index : 'scaiysamount',width : 65,label:'预算额',sortable : true,align : "right"});
				colModel.push({name : 'scaiqramount',index : 'scaiqramount',width : 65,label:'确认额',sortable : true,align : "right"});
				colModel.push({name : 'scaixdamount',index : 'scaixdamount',width : 65,label:'下单额',sortable : true,align : "right"});
				//门及锁
				colModel.push({name : 'itemconfstatus6descr',index : 'itemconfstatus6descr',width : 50,label:'确认状态',sortable : true,align : "left"});
				colModel.push({name : 'msysamount',index : 'msysamount',width : 65,label:'预算额',sortable : true,align : "right"});
				colModel.push({name : 'msqramount',index : 'msqramount',width : 65,label:'确认额',sortable : true,align : "right"});
				colModel.push({name : 'msxdamount',index : 'msxdamount',width : 65,label:'下单额',sortable : true,align : "right"});
				//吊顶
				colModel.push({name : 'itemconfstatus7descr',index : 'itemconfstatus7descr',width : 50,label:'确认状态',sortable : true,align : "left"});
				colModel.push({name : 'ddysamount',index : 'ddysamount',width : 65,label:'预算额',sortable : true,align : "right"});
				colModel.push({name : 'ddqramount',index : 'ddqramount',width : 65,label:'确认额',sortable : true,align : "right"});
				colModel.push({name : 'ddxdamount',index : 'ddxdamount',width : 65,label:'下单额',sortable : true,align : "right"});
				//地板
				colModel.push({name : 'itemconfstatus8descr',index : 'itemconfstatus8descr',width : 50,label:'确认状态',sortable : true,align : "left"});
				colModel.push({name : 'dbysamount',index : 'dbysamount',width : 65,label:'预算额',sortable : true,align : "right"});
				colModel.push({name : 'dbqramount',index : 'dbqramount',width : 65,label:'确认额',sortable : true,align : "right"});
				colModel.push({name : 'dbxdamount',index : 'dbxdamount',width : 65,label:'下单额',sortable : true,align : "right"});
				//卫浴
				colModel.push({name : 'itemconfstatus9descr',index : 'itemconfstatus9descr',width : 50,label:'确认状态',sortable : true,align : "left"});
				colModel.push({name : 'wyysamount',index : 'wyysamount',width : 65,label:'预算额',sortable : true,align : "right"});
				colModel.push({name : 'wyqramount',index : 'wyqramount',width : 65,label:'确认额',sortable : true,align : "right"});
				colModel.push({name : 'wyxdamount',index : 'wyxdamount',width : 65,label:'下单额',sortable : true,align : "right"});
				//开关
				colModel.push({name : 'itemconfstatus10descr',index : 'itemconfstatus10descr',width : 50,label:'确认状态',sortable : true,align : "left"});
				colModel.push({name : 'kgysamount',index : 'kgysamount',width : 65,label:'预算额',sortable : true,align : "right"});
				colModel.push({name : 'kgqramount',index : 'kgqramount',width : 65,label:'确认额',sortable : true,align : "right"});
				colModel.push({name : 'kgxdamount',index : 'kgxdamount',width : 65,label:'下单额',sortable : true,align : "right"});
				
			}else{
				colModel.push({name : 'itemconfstatus1descr',index : 'itemconfstatus1descr',width : 95,label:'瓷砖确认状态',sortable : true,align : "left"});
				colModel.push({name : 'itemconfstatus2descr',index : 'itemconfstatus2descr',width : 95,label:'地漏确认状态',sortable : true,align : "left"});
				colModel.push({name : 'itemconfstatus3descr',index : 'itemconfstatus3descr',width : 103,label:'挡水石确认状态',sortable : true,align : "left"});
				colModel.push({name : 'itemconfstatus4descr',index : 'itemconfstatus4descr',width : 103,label:'淋浴房确认状态',sortable : true,align : "left"});
				colModel.push({name : 'itemconfstatus11descr',index : 'itemconfstatus11descr',width : 95,label:'水槽确认状态',sortable : true,align : "left"});
				colModel.push({name : 'itemconfstatus5descr',index : 'itemconfstatus5descr',width : 95,label:'石材确认状态',sortable : true,align : "left"});
				colModel.push({name : 'itemconfstatus6descr',index : 'itemconfstatus6descr',width : 103,label:'门及锁确认状态',sortable : true,align : "left"});
				colModel.push({name : 'itemconfstatus7descr',index : 'itemconfstatus7descr',width : 95,label:'吊顶确认状态',sortable : true,align : "left"});
				colModel.push({name : 'itemconfstatus8descr',index : 'itemconfstatus8descr',width : 95,label:'地板确认状态',sortable : true,align : "left"});
				colModel.push({name : 'itemconfstatus9descr',index : 'itemconfstatus9descr',width : 95,label:'卫浴确认状态',sortable : true,align : "left"});
				colModel.push({name : 'itemconfstatus10descr',index : 'itemconfstatus10descr',width : 95,label:'开关确认状态',sortable : true,align : "left"});
			}
			colModel.push({name : 'statusdescr',index : 'statusdescr',width : 70,label:'客户状态',sortable : true,align : "left",hidden:true});
			var postData = $("#page_form").jsonForm();
			if(firstFlag){
				postData.constructStatus="4";
			}
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/custItemConfirm/goJqGrid",
				height:$(document).height()-$("#content-list").offset().top-100,
 				postData:postData, 
				styleUI: 'Bootstrap',
				colModel : colModel,
				gridComplete:function(){//confitemdescr
					var ids = $("#dataTable").getDataIDs();
					for(var i=0;i<ids.length;i++){
						var rowData = $("#dataTable").getRowData(ids[i]);
						if(rowData.runday>0){
							$('#'+ids[i]).find("td").addClass("SelectBG");
						}                   
					}
				}
			});	
			if($("#showType").val() == "02"){
				$("#dataTable").jqGrid("setGroupHeaders", {
				  	useColSpanStyle: true, 
					groupHeaders:[  															  	
						{startColumnName: 'itemconfstatus1descr', numberOfColumns: 4, titleText: '瓷砖'},
						{startColumnName: 'itemconfstatus2descr', numberOfColumns: 4, titleText: '地漏'},
						{startColumnName: 'itemconfstatus3descr', numberOfColumns: 4, titleText: '挡水石'},
						{startColumnName: 'itemconfstatus4descr', numberOfColumns: 4, titleText: '淋浴房'},
						{startColumnName: 'itemconfstatus5descr', numberOfColumns: 4, titleText: '石材'},
						{startColumnName: 'itemconfstatus6descr', numberOfColumns: 4, titleText: '门及锁'},
						{startColumnName: 'itemconfstatus7descr', numberOfColumns: 4, titleText: '吊顶'},
						{startColumnName: 'itemconfstatus8descr', numberOfColumns: 4, titleText: '地板'},
						{startColumnName: 'itemconfstatus9descr', numberOfColumns: 4, titleText: '卫浴'},
						{startColumnName: 'itemconfstatus10descr', numberOfColumns: 4, titleText: '开关'},
						{startColumnName: 'itemconfstatus11descr', numberOfColumns: 4, titleText: '水槽'},
					
					],
				}); 
			}		
		}
		    
		/**初始化表格*/
		$(function(){

			$("#empCode").openComponent_employee(({showLabel:'${uc.zwxm}',showValue:'${uc.czybh}'}));
			$("#mainBusinessMan").openComponent_employee(({showLabel:'${uc.zwxm}',showValue:'${uc.czybh}'}));
			
			loadJqGrid(true);
			onCollapse(68);
				
		});
		function changeOrder(obj){
			var id=$(obj).attr('id');
			if ($(obj).is(':checked')){
				$('#'+id).val('1');
			}else{
				$('#'+id).val('0');
			}
		}
		function order(){
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("custItemConfirmOrder",{
					title: "主材选定--预约",
					url: "${ctx}/admin/custItemConfirm/goOrder?custCode="+ret.code+"&itemTimeCode="+ret.confitem,
					height: 600,
					width: 1000,
					resizable:false, 
					returnFun: goto_query
				});
			} else {
				art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function itemConfrim(){
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("custItemConfirm",{
					title: "主材选定--材料确认",
					url: "${ctx}/admin/custItemConfirm/goItemConfrim?custCode="+ret.code+"&itemTimeCode="+ret.confitem,
					height: 600,
					width: 1000,
					returnFun: goto_query
				});
			} else {
				art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function itemCancel(){
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("custItemCancel",{
					title: "主材选定--取消确认",
					url: "${ctx}/admin/custItemConfirm/goItemCancel?custCode="+ret.code+"&itemTimeCode="+ret.confitem,
					height: 600,
					width: 1000,
					returnFun: goto_query
				});
			} else {
				art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function view(){
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("custItemConfirmDetail",{
					title:"主材选定—查看",
					url: "${ctx}/admin/custItemConfirm/goView?custCode="+ret.code+"&itemTimeCode="+ret.confitem+"&address="+ret.address,
					height:600,
					width:1000
				});
			   	
			} else {
			   	art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		
		function hidden(){
			var xtcs =$("#xtcs").val();
			var Designer = $("#Designer");
			var Manager = $("#Manager");
			if (xtcs=='02'){
				Designer.attr("hidden","true");
			}else{
				Manager.attr("hidden","true");
			}
		}
		//导出EXCEL
		function doExcel(){
			var url = "${ctx}/admin/custItemConfirm/doExcel";
			doExcelAll(url);
		}
		function goto_query(){
			loadJqGrid();
		}
		</script>
	</head>
	<body>
		<div class="body-box-list">
			<div class="query-form" >
				<form action="" method="post" id="page_form" class="form-search" >
					<input type="hidden" id="expired" name="expired" value="${customer.expired}" />
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="empCode" name="empCode" value="${uc.czybh }"/>
					<input type="hidden" name="xtcs" name="xtcs" value="${customer.xtcs }"/>
					<ul class="ul-form">
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address"  />
						</li>
						<li>
							<label>确认节点</label>
							<select id="confItem" name="confItem" >
								<option value="">全部</option>
								<option value="01">一次确认</option>
								<option value="02">二次确认</option>
								<option value="03">三次确认</option>
							</select>
						</li>
						<li>
							<label>确认完成</label>
							<select id="confirmFinish" name="confirmFinish" >
								<option value="">请选择...</option>
								<option value="1">是</option>
								<option value="0" selected="selected">否</option>
							</select>
						</li>
					<%-- 
						<li hidden="true">
							<label>系统参数</label> 
							<!-- 01是福州参数，02是厦门参数 参数=‘02’显示主材管家，其他显示主材设计师-->
							<input type="text" id="xtcs" name="xtcs" value='${customer.xtcs}'/>
						</li> 
					--%>
						<li id="Manager">
							<label>主材管家</label>
							<input type="text" id="mainBusinessMan" name="mainBusinessMan" />
						</li>
						<li>
							<label>开工日期</label>
							<input type="text" id="confirmBeginFrom"
								   name="confirmBeginFrom" class="i-date" 
							   	   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								   value="<fmt:formatDate value='${customer.confirmBeginFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="confirmBeginTo"
								   name="confirmBeginTo" class="i-date" 
								   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								   value="<fmt:formatDate value='${customer.confirmBeginTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>确认开始日期</label>
							<input type="text" id="confirmStartDateFrom"
								   name="confirmStartDateFrom" class="i-date" 
								   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								   value="<fmt:formatDate value='${customer.confirmStartDateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="confirmStartDateTo"
								   name="confirmStartDateTo" class="i-date" 
								   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								   value="<fmt:formatDate value='${customer.confirmStartDateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>显示类型</label>
							<select id="showType" name="showType" >
								<option value="01">简要信息</option>
								<option value="02">完整信息</option>
							</select>
						</li>
						<li id="loadMore" >
							<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">更多</button>
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
						<div  class="collapse " id="collapse" >
							<ul>
								<li>
									<label>最近预约日期</label>
									<input type="text" id="informDateFrom"
										   name="informDateFrom" class="i-date" 
										   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										   value="<fmt:formatDate value='${customer.informDateFrom}' pattern='yyyy-MM-dd'/>" />
								</li>
								<li>
									<label>至</label>
									<input type="text" id="informDateTo"
										   name="informDateTo" class="i-date" 
										   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										   value="<fmt:formatDate value='${customer.informDateTo}' pattern='yyyy-MM-dd'/>" />
								</li>
								<li>
									<label>客户类型</label>
									<house:custTypeMulit id="custType"  selectedValue="${customer.custType }" ></house:custTypeMulit>
								</li>
								<li>
									<label>施工状态</label>
									<house:xtdm id="constructStatus" dictCode="CONSTRUCTSTATUS" value="1"></house:xtdm>
								</li>
								<li>
									<label>最近确认日期</label>
									<input type="text" id="confirmDateFrom"
										   name="confirmDateFrom" class="i-date" 
										   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										   value="<fmt:formatDate value='${customer.confirmDateFrom}' pattern='yyyy-MM-dd'/>" />
								</li>
								<li>
									<label>至</label>
									<input type="text" id="confirmDateTo"
										   name="confirmDateTo" class="i-date" 
										   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										   value="<fmt:formatDate value='${customer.confirmDateTo}' pattern='yyyy-MM-dd'/>" />
								</li>
								<li>
									<label>是否有预算</label>
									<select id="isContainOutPlan" name="isContainOutPlan" >
										<option value="1" >是</option>
										<option value="0" >否</option>
										<option value="2" selected="selected">全部</option>
									</select>
								</li>
								<li>
									<label>只显示待预约</label>
									<input id="isOrder" name="isOrder" type="checkbox" onclick="changeOrder(this)"/>
								</li>

								<!-- <li>
									<label></label>
									<input id="isContainOutPlan" name="isContainOutPlan" type="checkbox" onclick="changeOrder(this)"/>包含无预算客户&nbsp
								</li> -->
				
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
					<house:authorize authCode="CUSTITEMCONFIRM_ORDER">
						<button type="button" class="btn btn-system " onclick="order()">预约</button>
					</house:authorize>
					<house:authorize authCode="CUSTITEMCONFIRM_CONFIRM">
						<button type="button" class="btn btn-system " onclick="itemConfrim()">材料确认</button>
					</house:authorize>
					<house:authorize authCode="CUSTITEMCONFIRM_CANCEL">
						<button type="button" class="btn btn-system "  onclick="itemCancel()">取消确认</button>
					</house:authorize>
					<house:authorize authCode="CUSTITEMCONFIRM_VIEW">
						<button type="button" class="btn btn-system"  onclick="view()">查看</button>
					</house:authorize>
					<house:authorize authCode="CUSTITEMCONFIRM_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcel()">输出至excel</button>
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


