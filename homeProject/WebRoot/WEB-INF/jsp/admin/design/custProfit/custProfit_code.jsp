<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>CustProfit查询code</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custProfit/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
		      {name : 'CustCode',index : 'CustCode',width : 100,label:'custCode',sortable : true,align : "left"}
			  ,
		      {name : 'BaseDiscPer',index : 'BaseDiscPer',width : 100,label:'baseDiscPer',sortable : true,align : "left"}
			  ,
		      {name : 'BaseDisc1',index : 'BaseDisc1',width : 100,label:'baseDisc1',sortable : true,align : "left"}
			  ,
		      {name : 'BaseDisc2',index : 'BaseDisc2',width : 100,label:'baseDisc2',sortable : true,align : "left"}
			  ,
		      {name : 'DesignFee',index : 'DesignFee',width : 100,label:'designFee',sortable : true,align : "left"}
			  ,
		      {name : 'Gift',index : 'Gift',width : 100,label:'gift',sortable : true,align : "left"}
			  ,
		      {name : 'ContainBase',index : 'ContainBase',width : 100,label:'containBase',sortable : true,align : "left"}
			  ,
		      {name : 'ContainMain',index : 'ContainMain',width : 100,label:'containMain',sortable : true,align : "left"}
			  ,
		      {name : 'ContainSoft',index : 'ContainSoft',width : 100,label:'containSoft',sortable : true,align : "left"}
			  ,
		      {name : 'ContainInt',index : 'ContainInt',width : 100,label:'containInt',sortable : true,align : "left"}
			  ,
		      {name : 'ContainCup',index : 'ContainCup',width : 100,label:'containCup',sortable : true,align : "left"}
			  ,
		      {name : 'ContainMainServ',index : 'ContainMainServ',width : 100,label:'containMainServ',sortable : true,align : "left"}
			  ,
		      {name : 'ColorDrawFee',index : 'ColorDrawFee',width : 100,label:'colorDrawFee',sortable : true,align : "left"}
			  ,
		      {name : 'RemoteFee',index : 'RemoteFee',width : 100,label:'remoteFee',sortable : true,align : "left"}
			  ,
		      {name : 'BaseDisc',index : 'BaseDisc',width : 100,label:'baseDisc',sortable : true,align : "left"}
			  ,
		      {name : 'MainCost',index : 'MainCost',width : 100,label:'mainCost',sortable : true,align : "left"}
			  ,
		      {name : 'JobPer',index : 'JobPer',width : 100,label:'jobPer',sortable : true,align : "left"}
			  ,
		      {name : 'BasePro',index : 'BasePro',width : 100,label:'basePro',sortable : true,align : "left"}
			  ,
		      {name : 'MainPro',index : 'MainPro',width : 100,label:'mainPro',sortable : true,align : "left"}
			  ,
		      {name : 'ServPro',index : 'ServPro',width : 100,label:'servPro',sortable : true,align : "left"}
			  ,
		      {name : 'IntPro',index : 'IntPro',width : 100,label:'intPro',sortable : true,align : "left"}
			  ,
		      {name : 'CupPro',index : 'CupPro',width : 100,label:'cupPro',sortable : true,align : "left"}
			  ,
		      {name : 'SoftPro',index : 'SoftPro',width : 100,label:'softPro',sortable : true,align : "left"}
			  ,
		      {name : 'ManagePro',index : 'ManagePro',width : 100,label:'managePro',sortable : true,align : "left"}
			  ,
		      {name : 'DesignPro',index : 'DesignPro',width : 100,label:'designPro',sortable : true,align : "left"}
			  ,
		      {name : 'AllPro',index : 'AllPro',width : 100,label:'allPro',sortable : true,align : "left"}
			  ,
		      {name : 'DesignCalPer',index : 'DesignCalPer',width : 100,label:'designCalPer',sortable : true,align : "left"}
			  ,
		      {name : 'CostPer',index : 'CostPer',width : 100,label:'costPer',sortable : true,align : "left"}
			  ,
		      {name : 'BaseCalPer',index : 'BaseCalPer',width : 100,label:'baseCalPer',sortable : true,align : "left"}
			  ,
		      {name : 'MainCalPer',index : 'MainCalPer',width : 100,label:'mainCalPer',sortable : true,align : "left"}
			  ,
		      {name : 'ServProPer',index : 'ServProPer',width : 100,label:'servProPer',sortable : true,align : "left"}
			  ,
		      {name : 'ServCalPer',index : 'ServCalPer',width : 100,label:'servCalPer',sortable : true,align : "left"}
			  ,
		      {name : 'JobCtrl',index : 'JobCtrl',width : 100,label:'jobCtrl',sortable : true,align : "left"}
			  ,
		      {name : 'JobLowPer',index : 'JobLowPer',width : 100,label:'jobLowPer',sortable : true,align : "left"}
			  ,
		      {name : 'JobHighPer',index : 'JobHighPer',width : 100,label:'jobHighPer',sortable : true,align : "left"}
			  ,
		      {name : 'IntProPer',index : 'IntProPer',width : 100,label:'intProPer',sortable : true,align : "left"}
			  ,
		      {name : 'IntCalPer',index : 'IntCalPer',width : 100,label:'intCalPer',sortable : true,align : "left"}
			  ,
		      {name : 'CupProPer',index : 'CupProPer',width : 100,label:'cupProPer',sortable : true,align : "left"}
			  ,
		      {name : 'CupCalPer',index : 'CupCalPer',width : 100,label:'cupCalPer',sortable : true,align : "left"}
			  ,
		      {name : 'SoftProPer',index : 'SoftProPer',width : 100,label:'softProPer',sortable : true,align : "left"}
			  ,
		      {name : 'SoftCalPer',index : 'SoftCalPer',width : 100,label:'softCalPer',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdate',index : 'LastUpdate',width : 100,label:'lastUpdate',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdatedBy',index : 'LastUpdatedBy',width : 100,label:'lastUpdatedBy',sortable : true,align : "left"}
			  ,
		      {name : 'Expired',index : 'Expired',width : 100,label:'expired',sortable : true,align : "left"}
			  ,
		      {name : 'ActionLog',index : 'ActionLog',width : 100,label:'actionLog',sortable : true,align : "left"}
			  ,
		      {name : 'Prepay',index : 'Prepay',width : 100,label:'prepay',sortable : true,align : "left"}
			  ,
		      {name : 'PayType',index : 'PayType',width : 100,label:'payType',sortable : true,align : "left"}
			  ,
		      {name : 'position',index : 'position',width : 100,label:'position',sortable : true,align : "left"}
			  
            ],
			ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<table cellpadding="0" cellspacing="0" width="100%">
						<col  width="82" />
						<col  width="25.4%"/>
						<col  width="82"/>
						<col  width="25.3%"/>
						<col  width="82" />
						<col  width="25.4%"/>
					<tbody>
						<tr class="td-btn">	
							<td class="td-label">custCode</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${custProfit.custCode}" />
							</td>
							<td class="td-label">baseDiscPer</td>
							<td class="td-value">
							<input type="text" id="baseDiscPer" name="baseDiscPer" style="width:160px;"  value="${custProfit.baseDiscPer}" />
							</td>
							<td class="td-label">baseDisc1</td>
							<td class="td-value">
							<input type="text" id="baseDisc1" name="baseDisc1" style="width:160px;"  value="${custProfit.baseDisc1}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">baseDisc2</td>
							<td class="td-value">
							<input type="text" id="baseDisc2" name="baseDisc2" style="width:160px;"  value="${custProfit.baseDisc2}" />
							</td>
							<td class="td-label">designFee</td>
							<td class="td-value">
							<input type="text" id="designFee" name="designFee" style="width:160px;"  value="${custProfit.designFee}" />
							</td>
							<td class="td-label">gift</td>
							<td class="td-value">
							<input type="text" id="gift" name="gift" style="width:160px;"  value="${custProfit.gift}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">containBase</td>
							<td class="td-value">
							<input type="text" id="containBase" name="containBase" style="width:160px;"  value="${custProfit.containBase}" />
							</td>
							<td class="td-label">containMain</td>
							<td class="td-value">
							<input type="text" id="containMain" name="containMain" style="width:160px;"  value="${custProfit.containMain}" />
							</td>
							<td class="td-label">containSoft</td>
							<td class="td-value">
							<input type="text" id="containSoft" name="containSoft" style="width:160px;"  value="${custProfit.containSoft}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">containInt</td>
							<td class="td-value">
							<input type="text" id="containInt" name="containInt" style="width:160px;"  value="${custProfit.containInt}" />
							</td>
							<td class="td-label">containCup</td>
							<td class="td-value">
							<input type="text" id="containCup" name="containCup" style="width:160px;"  value="${custProfit.containCup}" />
							</td>
							<td class="td-label">containMainServ</td>
							<td class="td-value">
							<input type="text" id="containMainServ" name="containMainServ" style="width:160px;"  value="${custProfit.containMainServ}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">colorDrawFee</td>
							<td class="td-value">
							<input type="text" id="colorDrawFee" name="colorDrawFee" style="width:160px;"  value="${custProfit.colorDrawFee}" />
							</td>
							<td class="td-label">remoteFee</td>
							<td class="td-value">
							<input type="text" id="remoteFee" name="remoteFee" style="width:160px;"  value="${custProfit.remoteFee}" />
							</td>
							<td class="td-label">baseDisc</td>
							<td class="td-value">
							<input type="text" id="baseDisc" name="baseDisc" style="width:160px;"  value="${custProfit.baseDisc}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">mainCost</td>
							<td class="td-value">
							<input type="text" id="mainCost" name="mainCost" style="width:160px;"  value="${custProfit.mainCost}" />
							</td>
							<td class="td-label">jobPer</td>
							<td class="td-value">
							<input type="text" id="jobPer" name="jobPer" style="width:160px;"  value="${custProfit.jobPer}" />
							</td>
							<td class="td-label">basePro</td>
							<td class="td-value">
							<input type="text" id="basePro" name="basePro" style="width:160px;"  value="${custProfit.basePro}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">mainPro</td>
							<td class="td-value">
							<input type="text" id="mainPro" name="mainPro" style="width:160px;"  value="${custProfit.mainPro}" />
							</td>
							<td class="td-label">servPro</td>
							<td class="td-value">
							<input type="text" id="servPro" name="servPro" style="width:160px;"  value="${custProfit.servPro}" />
							</td>
							<td class="td-label">intPro</td>
							<td class="td-value">
							<input type="text" id="intPro" name="intPro" style="width:160px;"  value="${custProfit.intPro}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">cupPro</td>
							<td class="td-value">
							<input type="text" id="cupPro" name="cupPro" style="width:160px;"  value="${custProfit.cupPro}" />
							</td>
							<td class="td-label">softPro</td>
							<td class="td-value">
							<input type="text" id="softPro" name="softPro" style="width:160px;"  value="${custProfit.softPro}" />
							</td>
							<td class="td-label">managePro</td>
							<td class="td-value">
							<input type="text" id="managePro" name="managePro" style="width:160px;"  value="${custProfit.managePro}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">designPro</td>
							<td class="td-value">
							<input type="text" id="designPro" name="designPro" style="width:160px;"  value="${custProfit.designPro}" />
							</td>
							<td class="td-label">allPro</td>
							<td class="td-value">
							<input type="text" id="allPro" name="allPro" style="width:160px;"  value="${custProfit.allPro}" />
							</td>
							<td class="td-label">designCalPer</td>
							<td class="td-value">
							<input type="text" id="designCalPer" name="designCalPer" style="width:160px;"  value="${custProfit.designCalPer}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">costPer</td>
							<td class="td-value">
							<input type="text" id="costPer" name="costPer" style="width:160px;"  value="${custProfit.costPer}" />
							</td>
							<td class="td-label">baseCalPer</td>
							<td class="td-value">
							<input type="text" id="baseCalPer" name="baseCalPer" style="width:160px;"  value="${custProfit.baseCalPer}" />
							</td>
							<td class="td-label">mainCalPer</td>
							<td class="td-value">
							<input type="text" id="mainCalPer" name="mainCalPer" style="width:160px;"  value="${custProfit.mainCalPer}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">servProPer</td>
							<td class="td-value">
							<input type="text" id="servProPer" name="servProPer" style="width:160px;"  value="${custProfit.servProPer}" />
							</td>
							<td class="td-label">servCalPer</td>
							<td class="td-value">
							<input type="text" id="servCalPer" name="servCalPer" style="width:160px;"  value="${custProfit.servCalPer}" />
							</td>
							<td class="td-label">jobCtrl</td>
							<td class="td-value">
							<input type="text" id="jobCtrl" name="jobCtrl" style="width:160px;"  value="${custProfit.jobCtrl}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">jobLowPer</td>
							<td class="td-value">
							<input type="text" id="jobLowPer" name="jobLowPer" style="width:160px;"  value="${custProfit.jobLowPer}" />
							</td>
							<td class="td-label">jobHighPer</td>
							<td class="td-value">
							<input type="text" id="jobHighPer" name="jobHighPer" style="width:160px;"  value="${custProfit.jobHighPer}" />
							</td>
							<td class="td-label">intProPer</td>
							<td class="td-value">
							<input type="text" id="intProPer" name="intProPer" style="width:160px;"  value="${custProfit.intProPer}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">intCalPer</td>
							<td class="td-value">
							<input type="text" id="intCalPer" name="intCalPer" style="width:160px;"  value="${custProfit.intCalPer}" />
							</td>
							<td class="td-label">cupProPer</td>
							<td class="td-value">
							<input type="text" id="cupProPer" name="cupProPer" style="width:160px;"  value="${custProfit.cupProPer}" />
							</td>
							<td class="td-label">cupCalPer</td>
							<td class="td-value">
							<input type="text" id="cupCalPer" name="cupCalPer" style="width:160px;"  value="${custProfit.cupCalPer}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">softProPer</td>
							<td class="td-value">
							<input type="text" id="softProPer" name="softProPer" style="width:160px;"  value="${custProfit.softProPer}" />
							</td>
							<td class="td-label">softCalPer</td>
							<td class="td-value">
							<input type="text" id="softCalPer" name="softCalPer" style="width:160px;"  value="${custProfit.softCalPer}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${custProfit.lastUpdate}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${custProfit.lastUpdatedBy}" />
							</td>
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${custProfit.expired}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${custProfit.actionLog}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">prepay</td>
							<td class="td-value">
							<input type="text" id="prepay" name="prepay" style="width:160px;"  value="${custProfit.prepay}" />
							</td>
							<td class="td-label">payType</td>
							<td class="td-value">
							<input type="text" id="payType" name="payType" style="width:160px;"  value="${custProfit.payType}" />
							</td>
							<td class="td-label">position</td>
							<td class="td-value">
							<input type="text" id="position" name="position" style="width:160px;"  value="${custProfit.position}" />
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input onclick="goto_query();"  class="i-btn-s" type="button"  value="检索"/>
									<input onclick="clearForm();"  class="i-btn-s" type="button"  value="清空"/>
								</div>
						  	</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


