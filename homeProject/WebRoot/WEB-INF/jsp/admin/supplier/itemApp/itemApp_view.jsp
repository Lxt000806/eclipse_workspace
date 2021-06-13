<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看ItemApp</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
var parentWin=window.opener;
$(function(){
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplierItemApp/goJqGridDetail?id=${itemApp.no}",
		height:$(document).height()-$("#content-list").offset().top-65,
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
		  {name : 'itemcode',index : 'itemcode',width : 70,label:'材料编号',align : "left",count:true},
		  {name : 'itemdescr',index : 'itemdescr',width : 220,label:'材料名称',align : "left"},
		  {name : "fixareadescr",index : "fixareadescr",width : 90,label:"区域",align : "left"},
		  {name : "intproddescr",index : "intproddescr",width : 90,label:"集成产品",align : "left",hidden:true},
		  {name : 'qty',index : 'qty',width : 60,label:'数量',align : "right",sum:true},
		  {name : 'uomdescr',index : 'uomdescr',width : 60,label:'单位',align : "left"},
		  {name : 'processcost',index : 'processcost',width : 70,label:'其他费用',align : "right",sum:true},
		  {name : "cost",index : "cost",width : 60,label:"单价",align : "right",sum:true, hidden : true},
		  {name : "sumcost",index : "sumcost",width : 60,label:"总价",align : "right",sum:true, hidden : true},
		  {name : "projectcost",index : "projectcost",width : 90,label:"结算价",align : "right",sum:true, hidden : true},
		  {name : "projectallcost",index : "projectallcost",width : 90,label:"结算总价",align : "right",sum:true, hidden : true},
		  {name : 'remarks',index : 'a.remarks',width : 350,label:'备注',align : "left"}
          ],
          gridComplete:function(){
			 if ($.trim("${itemApp.itemType1}") == "JC") {
				$("#dataTable").jqGrid("showCol", "intproddescr");
				/* $("#dataTable").jqGrid("showCol", "cost");
				$("#dataTable").jqGrid("showCol", "sumcost"); */
			} 
			if ($.trim("${projectCostRight}") == "1") {
				$("#dataTable").jqGrid("showCol", "projectcost");
				$("#dataTable").jqGrid("showCol", "projectallcost");
			}
			if ($.trim("${costRight}") == "1") {
				$("#dataTable").jqGrid("showCol", "cost");
				$("#dataTable").jqGrid("showCol", "sumcost");
			}
          }
	});
	
	if ($.trim("${itemApp.itemType1}") == "JC") {
		$("#lblOtherCost").text("安装费");
		$("#lblOtherCostAdj").text("售后费");
	}
	if("${splstatus}"=="0" && $.trim("${itemApp.itemType1}") == "JC"){
		$("#dataTable").setGridParam().hideCol("qty");
	}
});

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/supplierItemApp/doExcel";
	doExcelAll(url);
} 
	
</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
	      	<button type="button" class="btn btn-system " onclick="doExcel()">导出Excel</button>
	      	<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      	</div>
	   </div>
	</div>
	
	<div class="panel panel-info"> 
		<div class="panel-body">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="m_umState" id="m_umState" value="V"/>
				
				<ul class="ul-form">
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>领料单号 </label>
								<input type="text" id="no" name="no"  value="${itemApp.no}" readonly="readonly"/>
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>单据状态 </label>
								<house:xtdm id="status" dictCode="ITEMAPPSTATUS" value="${itemApp.status}" disabled="true"></house:xtdm>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>项目经理 </label>
								<input type="text" id="projectMan" name="projectMan"  value="${itemApp.projectMan}" readonly="readonly"/>
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>电话号码</label>
								<input type="text" id="phone" name="phone" value="${itemApp.phone}" readonly="readonly"/>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>部门 </label>
								<input type="text" id="projectDept2Descr" name="projectDept2Descr"  value="${itemApp.projectDept2Descr}" readonly="readonly" />
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>编号</label>
								<input type="text" id="custDocumentNo" name="custDocumentNo"  value="${itemApp.custDocumentNo}" readonly="readonly" />
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>楼盘地址 </label>
								<input type="text" id="custAddress" name="custAddress"  value="${itemApp.custAddress}" readonly="readonly" />
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>供应商状态</label>
								<house:xtdm id="splStatus" dictCode="APPSPLSTATUS" value="${itemApp.splStatus}" disabled="true"></house:xtdm>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>产品类型 </label>
								<house:xtdm id="productType" dictCode="APPPRODUCTTYPE" value="${itemApp.productType}" disabled="true"></house:xtdm>
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>预计到货日期</label>
								<input type="text" id="arriveDate" name="arriveDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.arriveDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label id="lblOtherCost">加工费 </label>
								<input type="text" id="otherCost" name="otherCost"  value="${itemApp.otherCost}" readonly="readonly" />
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label id="lblOtherCostAdj">物流配送费</label>
								<input type="text" id="otherCostAdj" name="otherCostAdj"  value="${itemApp.otherCostAdj}" readonly="readonly" />
							</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-12" >
							<li>
								<label>到厂日期 </label>
								<input type="text" id="arriveSplDate" name="arriveSplDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.arriveSplDate }' pattern='yyyy-MM-dd'/>" disabled="true" />
							</li>	
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-12" >
							<li>
								<label class="control-textarea">备注 </label>
								<textarea id="splRemark" name="splRemark" style="width:645px;" rows="3">${itemApp.splRemark}</textarea>
							</li>	
						</div>
					</div>
				</ul>
			</form>
		</div>
	</div>

	<div id="content-list">
		<table id= "dataTable"></table> 
	</div> 
</div>
</body>
</html>
