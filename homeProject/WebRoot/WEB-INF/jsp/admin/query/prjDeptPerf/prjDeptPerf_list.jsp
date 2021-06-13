<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>工程部业绩分析</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp"%>
		<script type="text/javascript">
			$(function(){
				Global.JqGrid.initJqGrid("dataTable",{
					url:"${ctx}/admin/PrjDeptPerf/goJqGrid",
					height:$(document).height()-$("#content-list").offset().top-100,
					styleUI: 'Bootstrap',
					postData:$("#page_form").jsonForm(),
					colModel : [
						{name: "Dept2Code", index: "Dept2Code", width: 94, label: "Dept2Code", sortable: true, align: "left",hidden:true},
						{name: "PrjDeptLeaderCode", index: "PrjDeptLeaderCode", width: 94, label: "PrjDeptLeaderCode", sortable: true, align: "left",hidden:true},
						{name: "Dept1Descr", index: "Dept1Descr", width: 94, label: "工程事业部", sortable: true, align: "left"},
						{name: "Dept2Descr", index: "Dept2Descr", width: 94, label: "工程部", sortable: true, align: "left"},
						{name: "PrjDeptLeaderDescr", index: "PrjDeptLeaderDescr", width: 90, label: "经理", sortable: true, align: "left"},
						{name: "CheckBuilds", index: "CheckBuilds", width: 90, label: "结算套数", sortable: true, align: "right",sum:true},
						{name: "CheckAmount", index: "CheckAmount", width: 90, label: "结算金额", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
						{name: "CheckContractFee", index: "CheckContractFee", width: 120, label: "结算工地签单额", sortable: true, align: "right",sum:true},
						{name: "BaseChg", index: "BaseChg", width: 120, label: "结算工地基础增减", sortable: true, align: "right",sum:true},
						{name: "ItemChg", index: "ItemChg", width: 120, label: "结算工地材料增减", sortable: true, align: "right",sum:true},
						{name: "CheckPerf", index: "CheckPerf", width: 90, label: "结算业绩", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
						{name: "OntimeCheckBuilds", index: "OntimeCheckBuilds", width: 100, label: "按时完工套数", sortable: true, align: "right",sum:true},
						{name: "AvgConstructDay", index: "AvgConstructDay", width: 100, label: "平均施工天数", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
						{name: "OntimeCheckRate", index: "OntimeCheckRate", width: 100, label: "按时完工率", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return (myRound(cellvalue*100,1))+"%";}},
						{name: "OvertimeCheckRate", index: "OvertimeCheckRate", width: 100, label: "拖期率", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return (myRound(cellvalue*100,1))+"%";}},
						{name: "ReorderBuilds", index: "ReorderBuilds", width: 90, label: "签单套数", sortable: true, align: "right",sum:true},
						{name: "ReorderFee", index: "ReorderFee", width: 90, label: "签单业绩金额", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
						{name: "ChangedPerfAmount", index: "ChangedPerfAmount", width: 90, label: "增减业绩金额", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
					    {name: "ContractAmount", index: "ContractAmount", width: 90, label: "到账合同额", sortable: true, align: "right", sum: true},
                        {name: "ChangedContractAmount", index: "ChangedContractAmount", width: 90, label: "增减合同额", sortable: true, align: "right", sum: true},
                        {name: "TotalContractAmount", index: "TotalContractAmount", width: 90, label: "合计合同额", sortable: true, align: "right", sum: true},

						/* {name: "confirmbuilds_in", index: "confirmbuilds_in", width: 90, label: "开工套数", sortable: true, align: "right",sum:true},
						{name: "contractfee_in", index: "contractfee_in", width: 90, label: "合同金额", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
						{name: "CheckBuilds_in", index: "CheckBuilds_in", width: 90, label: "结算套数", sortable: true, align: "right",sum:true},
						{name: "CheckAmount_in", index: "CheckAmount_in", width: 90, label: "结算金额", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
						{name: "CheckPerf_in", index: "CheckPerf_in", width: 90, label: "结算业绩", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
						{name: "OntimeCheckBuilds_in", index: "OntimeCheckBuilds_in", width: 100, label: "按时结算套数", sortable: true, align: "right",sum:true},
						{name: "AvgConstructDay_in", index: "AvgConstructDay_in", width: 100, label: "平均施工天数", sortable: true, align: "right",sum:true},
						{name: "OntimeCheckRate_in", index: "OntimeCheckRate_in", width: 100, label: "按时结算率", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return (myRound(cellvalue*100,1))+"%";}},
						{name: "confirmbuilds_out", index: "confirmbuilds_out", width: 90, label: "开工套数", sortable: true, align: "right",sum:true},
						{name: "contractfee_out", index: "contractfee_out", width: 90, label: "合同金额", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
						{name: "CheckBuilds_out", index: "CheckBuilds_out", width: 90, label: "结算套数", sortable: true, align: "right",sum:true},
						{name: "CheckAmount_out", index: "CheckAmount_out", width: 90, label: "结算金额", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
						{name: "CheckPerf_out", index: "CheckPerf_out", width: 90, label: "结算业绩", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
						{name: "OntimeCheckBuilds_out", index: "OntimeCheckBuilds_out", width: 100, label: "按时结算套数", sortable: true, align: "right",sum:true},
						{name: "AvgConstructDay_out", index: "AvgConstructDay_out", width: 100, label: "平均施工天数", sortable: true, align: "right",sum:true},
						{name: "OntimeCheckRate_out", index: "OntimeCheckRate_out", width: 100, label: "按时结算率", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return (myRound(cellvalue*100,1))+"%";}},
 */						
					],
					gridComplete:function(){
						var CheckBuilds = $("#dataTable").footerData("get","CheckBuilds").CheckBuilds;
						var OntimeCheckBuilds = $("#dataTable").footerData("get","OntimeCheckBuilds").OntimeCheckBuilds;
						$("#dataTable").footerData("set",{OntimeCheckRate:parseFloat(CheckBuilds)<=0?0:parseFloat(OntimeCheckBuilds)/parseFloat(CheckBuilds)});
						$("#dataTable").footerData("set",{OvertimeCheckRate:parseFloat(CheckBuilds)<=0?0:parseFloat(CheckBuilds-OntimeCheckBuilds)/parseFloat(CheckBuilds)});
						var checkBuild = $("#dataTable").jqGrid("getCol", "CheckBuilds");
						var avgConstructDay = $("#dataTable").jqGrid("getCol", "AvgConstructDay");
						var sum = 0;
						for(var i=0;i<checkBuild.length;i++){
							sum += parseFloat(checkBuild[i])*parseFloat(avgConstructDay[i]);
						}
						$("#dataTable").footerData("set",{AvgConstructDay:myRound(parseFloat(CheckBuilds)<=0?0:sum/parseFloat(CheckBuilds), 0)});
					
	/* 					var CheckBuilds_in = $("#dataTable").footerData("get","CheckBuilds_in").CheckBuilds_in;
						var OntimeCheckBuilds_in = $("#dataTable").footerData("get","OntimeCheckBuilds_in").OntimeCheckBuilds_in;
						$("#dataTable").footerData("set",{OntimeCheckRate_in:parseFloat(CheckBuilds_in)<=0?0:parseFloat(OntimeCheckBuilds_in)/parseFloat(CheckBuilds_in)});
						var checkBuild_in = $("#dataTable").jqGrid("getCol", "CheckBuilds_in");
						var AvgConstructDay_in = $("#dataTable").jqGrid("getCol", "AvgConstructDay_in");
						var sum1 = 0;
						for(var i=0;i<checkBuild_in.length;i++){
							sum1 += parseFloat(checkBuild_in[i])*parseFloat(AvgConstructDay_in[i]);
						}
						$("#dataTable").footerData("set",{AvgConstructDay_in:myRound(parseFloat(CheckBuilds_in)<=0?0:sum1/parseFloat(CheckBuilds_in), 0)});
					
						var CheckBuilds_out = $("#dataTable").footerData("get","CheckBuilds_out").CheckBuilds_out;
						var OntimeCheckBuilds_out = $("#dataTable").footerData("get","OntimeCheckBuilds_out").OntimeCheckBuilds_out;
						$("#dataTable").footerData("set",{OntimeCheckRate_out:parseFloat(CheckBuilds_out)<=0?0:parseFloat(OntimeCheckBuilds_out)/parseFloat(CheckBuilds_out)});
						var checkBuild_out = $("#dataTable").jqGrid("getCol", "CheckBuilds_out");
						var AvgConstructDay_out = $("#dataTable").jqGrid("getCol", "AvgConstructDay_out");
						var sum2 = 0;
						for(var i=0;i<checkBuild_out.length;i++){
							sum2 += parseFloat(checkBuild_out[i])*parseFloat(AvgConstructDay_out[i]);
						}
						$("#dataTable").footerData("set",{AvgConstructDay_out:myRound(parseFloat(CheckBuilds_out)<=0?0:sum2/parseFloat(CheckBuilds_out), 0)});
						 */
					}
				});
				/*  $("#dataTable").jqGrid('setGroupHeaders', {
				  	useColSpanStyle: true, 
					groupHeaders:[{startColumnName: 'confirmbuilds_in', numberOfColumns: 8, titleText: '借入专盘'},
								{startColumnName: 'confirmbuilds_out', numberOfColumns: 8, titleText: '借出专盘'}
					],
				}); */
				Global.JqGrid.initJqGrid("custTypeDataTable",{
					url:"${ctx}/admin/PrjDeptPerf/goJqGrid",
					height:$(document).height()-$("#content-list").offset().top-70,
					styleUI: 'Bootstrap',
					postData:$("#page_form").jsonForm(),
					colModel : [
						{name: "CustTypeDescr", index: "CustTypeDescr", width: 94, label: "客户类型", sortable: true, align: "left"},
						{name: "CheckBuilds", index: "CheckBuilds", width: 90, label: "结算套数", sortable: true, align: "right",sum:true},
						{name: "CheckAmount", index: "CheckAmount", width: 90, label: "结算金额", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
						{name: "CheckContractFee", index: "CheckContractFee", width: 120, label: "结算工地签单额", sortable: true, align: "right",sum:true},
						{name: "BaseChg", index: "BaseChg", width: 120, label: "结算工地基础增减", sortable: true, align: "right",sum:true},
						{name: "ItemChg", index: "ItemChg", width: 120, label: "结算工地材料增减", sortable: true, align: "right",sum:true},
						{name: "CheckPerf", index: "CheckPerf", width: 90, label: "结算业绩", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
						{name: "OntimeCheckBuilds", index: "OntimeCheckBuilds", width: 100, label: "按时完工套数", sortable: true, align: "right",sum:true},
						{name: "OvertimeCheckRate", index: "OvertimeCheckRate", width: 100, label: "拖期率", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return (myRound(cellvalue*100,1))+"%";}},
						{name: "AvgConstructDay", index: "AvgConstructDay", width: 100, label: "平均施工天数", sortable: true, align: "right",sum:true},
						{name: "OntimeCheckRate", index: "OntimeCheckRate", width: 100, label: "按时结算率", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return (myRound(cellvalue*100,1))+"%";}},
						{name: "OvertimeCheckRate", index: "OvertimeCheckRate", width: 100, label: "拖期率", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return (myRound(cellvalue*100,1))+"%";}},
						{name: "ReorderBuilds", index: "ReorderBuilds", width: 90, label: "签单套数", sortable: true, align: "right",sum:true},
						{name: "ReorderFee", index: "ReorderFee", width: 90, label: "签单业绩金额", sortable: true, align: "right",sum:true,formatter:function(cellvalue, options, rowObject){return myRound(cellvalue);}},
						{name: "ContractAmount", index: "ContractAmount", width: 90, label: "到账合同额", sortable: true, align: "right", sum: true},

					],
					gridComplete:function(){
						var CheckBuilds = $("#custTypeDataTable").footerData("get","CheckBuilds").CheckBuilds;
						var OntimeCheckBuilds = $("#custTypeDataTable").footerData("get","OntimeCheckBuilds").OntimeCheckBuilds;
						$("#custTypeDataTable").footerData("set",{OntimeCheckRate:parseFloat(CheckBuilds)<=0?0:parseFloat(OntimeCheckBuilds)/parseFloat(CheckBuilds)});
						$("#custTypeDataTable").footerData("set",{OvertimeCheckRate:parseFloat(CheckBuilds)<=0?0:parseFloat(CheckBuilds-OntimeCheckBuilds)/parseFloat(CheckBuilds)});
						
						var checkBuild = $("#custTypeDataTable").jqGrid("getCol", "CheckBuilds");
						var avgConstructDay = $("#custTypeDataTable").jqGrid("getCol", "AvgConstructDay");
						
						var sum = 0;
						for(var i=0;i<checkBuild.length;i++){
							sum += parseFloat(checkBuild[i])*parseFloat(avgConstructDay[i]);
						}
						$("#custTypeDataTable").footerData("set",{AvgConstructDay:myRound(parseFloat(CheckBuilds)<=0?0:sum/parseFloat(CheckBuilds), 0)});
					}
				});
				$("#content-list-custType").hide();
			});
			function view(){
				if($("#statistcsMethod").val() == "1"){
					var ret = selectDataTableRow();
					if(ret){
						Global.Dialog.showDialog("prjDeptPerfView",{
							title:"工程部业绩-查看",
							url:"${ctx}/admin/PrjDeptPerf/goView",
							postData:{prjDeptLeaderCode:ret.PrjDeptLeaderCode,dateFrom:$("#dateFrom").val(),dateTo:$("#dateTo").val(),
							custType:$("#custType").val(),empCode:ret.PrjDeptLeaderCode,checkDateFrom:$("#checkDateFrom").val(),checkDateTo:$("#checkDateTo").val(),
							department2:ret.Dept2Code},
							height:650,
							width:1300,
							returnFun:goto_query
						});
					}else{
					    art.dialog({
							content: "请选择一条记录"
						});
					}
				}else{
					art.dialog({
						content:"只有按工程部查看可以点击查看按钮"
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
				doExcelAll("${ctx}/admin/PrjDeptPerf/doExcel");
			}
			function goto_query(){
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
				if($("#statistcsMethod").val() == "1"){
					$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
					$("#content-list-custType").hide();
					$("#content-list").show();
				}else{
					$("#content-list").hide();
					$("#content-list-custType").show();
					$("#custTypeDataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
				}
			}
			function clearForm(){
				 $("#page_form input[type='text']").val('');
			      $("#page_form select").val('');
			      $("#code").val('');
			      $.fn.zTree.getZTreeObj("tree_code").checkAllNodes(false);
			      $("#custType").val('');
			      $.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
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
							<label>工程部</label>
							<house:DictMulitSelect id="code" dictCode="" sql="select Code,desc1 desc1  from dbo.tDepartment2 where deptype in ('3','9','10') and Expired='F' order By dispSeq  " 
							sqlLableKey="desc1" sqlValueKey="code"   ></house:DictMulitSelect>
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType" ></house:custTypeMulit>
						</li>
						<li>
							<label>结算时间从</label>
							<input type="text" id="checkDateFrom" name="checkDateFrom" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${dateInfo.checkDateFrom }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="checkDateTo" name="checkDateTo" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${dateInfo.checkDateTo }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>业绩时间从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${dateInfo.dateFrom }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${dateInfo.dateTo }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>		
							<label>统计方式</label>
								<select id="statistcsMethod" name="statistcsMethod" >
									<option value="1">按工程部</option>
									<option value="2">按客户类型</option>
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
				<div id="content-list-custType">
					<table id= "custTypeDataTable"></table>
				</div>
			</div> 
		</div>
	</body>
</html>


