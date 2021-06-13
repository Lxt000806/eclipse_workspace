<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>礼品出库记账查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	var ExcTableId="";
	$(function(){
		if("${itemChg.countType}"=="0"){
			$("#tableName").val("材料增减");
			ExcTableId="dataTable_chgDetail";
		}else{
			$("#tableName").val("材料需求");
			ExcTableId="dataTable_itemReq";
		}
	});		  
	function changeToChgView(){
		ExcTableId="dataTable_chgDetail";
		$("#tableName").val("材料增减");
	}
	
	function changeToSaleView(){
		ExcTableId="dataTable_saleDetail";
		$("#tableName").val("独立销售明细");
	}
	function changeToPlanView(){
		ExcTableId="dataTable_itemPlan";
		$("#tableName").val("材料预算");
	}
	function changeToReqView(){
		ExcTableId="dataTable_itemReq";
		$("#tableName").val("材料需求");
	}
	function doExcel(){
		var url = "${ctx}/admin/rzxscx/doDetailExcel";
		doExcelAll(url,ExcTableId);
	}
	</script>
</head>
	<body>
  	 	<div class="body-box-form">
  			<div class="content-form">
  				<div class="panel panel-system">
			   	 	<div class="panel-body">
			    		<div class="btn-group-xs">
							<button type="button" class="btn btn-system" onclick="doExcel()">
								<span>导出excel</span>
							</button>
							<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
								<span>关闭</span>
							</button>
						</div>
					</div>	
				</div>
				<div style="height:8px">
			
				</div>
				<div class="query-form" hidden="true">
					<form role="form" class="form-search" id="page_form" action=""
							method="post" target="targetFrame">
						<input type="hidden" id="expired" name="expired" value="${itemChg.expired }"/> 
						<input type="text" id="containCmpCust" name="containCmpCust" value="${itemChg.containCmpCust }"/> 
						<input type="hidden" id="isOutSet" name="isOutSet" value="${itemChg.isOutSet}"/>
						<input type="hidden" id="sendType" name="sendType" value="${itemChg.sendType}"/>
						<input type="hidden" id="role" name="role" value="${itemChg.role}"/>
						<input type="hidden" id="empCode" name="empCode" value="${itemChg.empCode}"/>
						<input type="hidden" id="notContainPlan" name="notContainPlan" value="${itemChg.notContainPlan}"/>
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<li class="form-validate">
								<label>表格名称</label> 
								<input type="text" id="tableName" name="tableName" style="width:160px;"/> 
							</li>
							<li class="form-validate">
								<label>材料类型1</label> 
								<input type="text" id="itemType1" name="itemType1" style="width:160px;" value="${itemChg.itemType1 }"/>
							</li>
							<li class="form-validate">
								<label>材料类型2</label> 
								<input type="text" id="itemType2" name="itemType2" style="width:160px;" value="${itemChg.itemType2 }"/>
							</li>
							<li>
								<label>客户类型</label> 
								<input type="text" id="custType" name="custType" style="width:160px;" value="${itemChg.custType }"/>						
							</li>
							<li>
								<label>统计时间从</label> 
								<input type="text" id="dateFrom"
								name="dateFrom" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${itemChg.dateFrom}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li>
								<label>至</label> 
								<input type="text" id="dateTo"
								name="dateTo" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${itemChg.dateTo}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li>
								<label>供应商</label> 
								<input type="text" id="supplCode" name="supplCode" style="width:160px;" value="${itemChg.supplCode }"/>
							</li>
							<li>
								<label>品牌</label> 
								<input type="text" id="brandCode" name="brandCode" style="width:160px;" value="${itemChg.brandCode }"/>
							</li>
							<li>
								<label>归属买手</label> 
								<input type="text" id="buyer" name="buyer" style="width:160px;" value="${itemChg.buyer }"/>
							</li>
							<li>
								<label>归属部门</label> 
								<input type="text" id="department2" name="department2" style="width:160px;" value="${itemChg.department2 }"/>
							</li>
							<li>
								<label>客户编号</label> 
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${itemChg.custCode }"/>
							</li>
							<li>
								<label>统计类型</label> 
								<input type="text" id="countType" name="countType" style="width:160px;" value="${itemChg.countType }"/>
							</li>
							<li>
								<label>销售类型</label> 
								<input type="text" id="saleType" name="saleType" style="width:160px;" value="${itemChg.saleType }"/>
							</li>
							<li class="search-group"><input type="checkbox" id="expired_show" name="expired_show"
								value="${itemChg.expired }" onclick="hideExpired(this)"
								${purchase.expired!='T' ?'checked':'' }/><p>隐藏过期</p>
								<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">
										查询
								</button>
								<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">
									清空</button>
							</li>
						</ul>
					</form>
				</div>
				<div class="container-fluid" >  
					<ul class="nav nav-tabs" > 
				      	<c:if test="${itemChg.countType=='0' &&itemChg.saleType!='独立销售' }">
					        <li class="active"><a href="#tab_chgDetail" onclick="changeToChgView()" data-toggle="tab">材料增减</a></li>
				      	</c:if>
				      	<c:if test="${itemChg.countType=='0' &&itemChg.saleType!='独立销售' }">
					        <li class=""><a href="#tab_itemPlan" onclick="changeToPlanView()" data-toggle="tab">材料预算</a></li>
				      	</c:if>
				      	<c:if test="${itemChg.saleType=='独立销售' }">
					        <li class=""><a href="#tab_saleDetail" onclick="changeToSaleView()" data-toggle="tab">独立销售明细</a></li>
					    </c:if>
				      	<c:if test="${itemChg.countType=='1' &&itemChg.saleType!='独立销售' }"> 
					        <li class="active"><a href="#tab_itemReq" onclick="changeToReqView()" data-toggle="tab">材料需求</a></li>
					   	</c:if>
				    </ul> 
				    <div class="tab-content">  
				      	<c:if test="${itemChg.countType=='0' &&itemChg.saleType!='独立销售' }">
					        <div id="tab_chgDetail"  class="tab-pane fade in active">
					         	<jsp:include page="tab_chgDetail.jsp"></jsp:include>
					        </div> 
				        </c:if>
				      	<c:if test="${itemChg.countType=='0' &&itemChg.saleType!='独立销售' }">
					        <div id="tab_itemPlan" class="tab-pane fade"> 
					         	<jsp:include page="tab_itemPlan.jsp"></jsp:include>
					        </div> 
				        </c:if>
				      	<c:if test="${itemChg.saleType=='独立销售' }">
					        <div id="tab_saleDetail" class="tab-pane fade"> 
					         	<jsp:include page="tab_saleDetail.jsp"></jsp:include>
					        </div> 
				        </c:if>
				      	<c:if test="${itemChg.countType=='1' &&itemChg.saleType!='独立销售' }"> 
					        <div id="tab_itemReq" class="tab-pane fade in active"> 
					         	<jsp:include page="tab_itemReq.jsp"></jsp:include>
					        </div> 
				        </c:if>
				    </div>
				</div>
  			</div> 
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
	</body>
</html>

















