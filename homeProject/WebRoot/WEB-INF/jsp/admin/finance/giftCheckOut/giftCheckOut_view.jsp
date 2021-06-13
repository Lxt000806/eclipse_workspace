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
	
  </head>
  <body>
  	 <div class="body-box-form">
  	
  		<div class="content-form">
  			<!-- panelBar -->
  			<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>	
			</div>
  			<div class="infoBox" id="infoBoxDiv"></div>
  			<!-- edit-form -->
  			<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>记账单号</label>
									<input type="text" id="No" name="No" style="width:160px;"  value="${giftCheckOut.no }" readonly="readonly"/>                                             
							</li>	
							<li>
								<label>申请人</label>
									<input type="text" id="appCzy" name="appCzy" style="width:160px;" value="${giftCheckOut.appCzy }" readonly="readonly"/>                                             
							</li>	
							<li>
								<label>状态</label>
									<house:xtdm id="status" dictCode="WHChkOutStatus"  value="${giftCheckOut.status }" disabled="true"></house:xtdm>
							</li>	
							<li>
								<label>申请日期</label>
									<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftCheckOut.date}' pattern='yyyy-MM-dd'/>"  disabled="true"/>
							</li>	
							<li>
								<label>仓库编号</label>
									<input type="text" id="whCode" name="whCode" style="width:160px;" value="${giftCheckOut.whCode }" readonly="readonly"/>                                             
							</li>	
							<li>
								<label>审核人</label>
									<input type="text" id="confirmCzy" name="confirmCzy" style="width:160px;" value="${giftCheckOut.confirmCzy }" readonly="readonly"/>                                             
							</li>	
							<li>
								<label>记账日期</label>
									<input type="text" id="checkDate" name="checkDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftCheckOut.checkDate}' pattern='yyyy-MM-dd'/>"  disabled="true" disabled="true"/>
							</li>	
							<li>
								<label>审核日期</label>
									<input type="text" id="confirmDate" name="confirmDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftCheckOut.confirmDate}' pattern='yyyy-MM-dd'/>" disabled="true" disabled="true"/>
							</li>	
							<li>
								<label>凭证号</label>
									<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${giftCheckOut.documentNo }" readonly="readonly"/>                                             
							</li>	
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${giftCheckOut.remarks }</textarea>
  							</li>
						</ul>
  				</form>
  				</div>
  				</div>
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
								<button type="button" class="btn btn-system " onclick="doExcelNow(tableName,ExcTableId,'page_form')" title="导出当前excel数据" >
									<span>导出excel</span>
								</button>
								<button type="button" class="btn btn-system " id="view" onclick="view()" >
									<span>查看</span></button>
				</div>
			</div>	
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_giftCheckout_view" onclick="changeToView()" data-toggle="tab">礼品领用表</a></li>
		        <li class=""><a href="#tab_giftCheckOut_depaView" onclick="changeToDepaView()" data-toggle="tab">按部门汇总</a></li>
		        <li class=""><a href="#tab_giftCheckOut_custDepaView" onclick="changeToCustDepaView()" data-toggle="tab">按楼盘部门汇总</a></li>
		    </ul> 
		    <div class="tab-content">  
		        <div id="tab_giftCheckout_view"  class="tab-pane fade in active"> 
		         	<jsp:include page="tab_giftCheckout_view.jsp"></jsp:include>
		        </div> 
		        <div id="tab_giftCheckOut_depaView"  class="tab-pane fade "> 
		         	<jsp:include page="tab_giftCheckOut_depaView.jsp"></jsp:include>
		        </div> 
		        <div id="tab_giftCheckOut_custDepaView"  class="tab-pane fade "> 
		         	<jsp:include page="tab_giftCheckOut_custDepaView.jsp"></jsp:include>
		        </div> 
		    </div>
		</div>
  	</div> 
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var ExcTableId='dataTable';
var tableName='礼品领用单';
$(function(){
	$("#appCzy").openComponent_employee({showValue:'${giftCheckOut.appCzy}',showLabel:'${giftCheckOut.appCzyDescr}' ,readonly:true});
	$("#whCode").openComponent_wareHouse({showValue:'${giftCheckOut.whCode}',showLabel:'${giftCheckOut.whCodeDescr}' ,readonly:true});
	$("#confirmCzy").openComponent_employee({showValue:'${giftCheckOut.confirmCzy}',showLabel:'${giftCheckOut.confirmCzyDescr}' ,readonly:true});
	
	var lastCellRowId;
	
});
	function view(){
		var ret = selectDataTableRow();
	    if (ret) {    	
	      Global.Dialog.showDialog("giftAppview",{
			  title:"礼品管理--查看",
			  url:"${ctx}/admin/giftApp/goview?id="+ret.No+"&viewStatus="+"C",
			  height: 700,
			  width:1000,
			  returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
    	}
	}	
function changeToView(){
	ExcTableId='dataTable';
	tableName='礼品领用单';
}
function changeToDepaView(){
	ExcTableId='dataTable_depa';
	tableName='礼品领用单按部门汇总'
}
function changeToCustDepaView(){
	ExcTableId='dataTable_custDepa';
	tableName='礼品领用单按楼盘部门汇总'
}	
</script>
  </body>
</html>

















