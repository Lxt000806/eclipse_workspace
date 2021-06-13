<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>仓库调整-查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	$("#whCode").openComponent_wareHouse();
	$("#whCode").openComponent_wareHouse({showValue:'${itemBalAdjHeader.whCode}',showLabel:'${itemBalAdjHeader.whDescr}',readonly:true });
	$("#appEmp").openComponent_employee({showValue:'${itemBalAdjHeader.appEmp}',showLabel:'${itemBalAdjHeader.appEmpDescr}',readonly:true });
	$("#status_NAME").attr("disabled","disabled");
});

//查看
function view(){
		var whCode = $.trim($("#whCode").val());

		var ret= selectDataTableRow('dataTable');
	 	if(ret){
			Global.Dialog.showDialog("DetailView",{
				title:"仓库调整明细——查看",
				url:"${ctx}/admin/itemBalAdjHeader/goDetailView",
				postData:{itCode:ret.itcode, uom:ret.uomdescr, cost:ret.cost, adjQty:ret.adjqty, qty:ret.qty ,remarks:ret.remarks, itDescr:ret.itdescr, allQty:ret.allqty, adjCost:ret.adjcost,
				 aftCost:ret.aftcost, lastUpdate:ret.lastupdate, lastUpdatedBy:ret.lastupdatedby,
				 whCode:whCode},
				height:480,
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
<div class="body-box-form" >
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
		<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
							<ul class="ul-form">
							<li>
								<label>仓库调整编号</label>
								<input type="text" id="No" name="No" style="width:160px;" placeholder="保存自动生成" value="${itemBalAdjHeader.no }" readonly="readonly"/>                                             
							</li>
							<li>
								<label><span class="required">*</span>仓库编号</label>
									<input type="text" id="whCode" name="whCode" style="width:160px;" value="${itemBalAdjHeader.whCode }"/>
							</li>
							<li>
								<label>状态</label>
								<house:xtdmMulit id="status" dictCode="BALADJSTATUS" selectedValue="${itemBalAdjHeader.status}"></house:xtdmMulit>
							</li>
							<li>
								<label><span class="required">*</span>调整日期</label>
								<input type="text" style="width:160px;" id="date" name="date" class="i-date" value="<fmt:formatDate value='${itemBalAdjHeader.date}' pattern='yyyy-MM-dd'/>"  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
							</li>
							<li>
								<label>申请日期</label>
								<input type="text" style="width:160px;" id="appDate" name="appDate" class="i-date" value="<fmt:formatDate value='${itemBalAdjHeader.appDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
							</li>
							<li>
								<label><span class="required">*</span>调整类型</label>
								<house:xtdm id="adjType" dictCode="ADJTYP"  value="${itemBalAdjHeader.adjType }"></house:xtdm>
							</li>
							<li>
								<label>申请人</label>
									<input type="text" id="appEmp" name="appEmp" style="width:160px;" value="${itemBalAdjHeader.appEmp }"/>
							</li>
							<li>
								<label><span class="required">*</span>调整原因</label>
								<house:xtdm id="adjReason" dictCode="AdjReason"  value="${itemBalAdjHeader.adjReason }"></house:xtdm>
							</li>
							<li>
								<label>审核日期</label>
								<input type="text" style="width:160px;" id="confirmDate" name="confirmDate" class="i-date" value="<fmt:formatDate value='${itemBalAdjHeader.confirmDate}'  pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
							</li>
							<li>
								<label>凭证号</label>
								<input type="text" id="documentNo" name="documentNo" style="width:160px;"  value="${itemBalAdjHeader.documentNo}" disabled='true'/>
							</li>
							<li>
								<label>审核人</label>
								<input type="text" id="confirmEmp" name="confirmEmp" style="width:160px;"  value="${itemBalAdjHeader.confirmEmp}" disabled='true'/>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea style="width:160px" id="remarks" name="remarks" rows="2">${itemBalAdjHeader.remarks}</textarea>
							</li>
						</ul>	
			</form>
			</div>
		</div> <!-- edit-form end -->
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
				<li class="active"><a href="#itemBalAdjHeader_tabView" data-toggle="tab">详情</a></li>
				<li class="" id="reference" ><a href="#tab_viewSupplier" data-toggle="tab">按供应商汇总</a></li>
			</ul>
		    <div class="tab-content">  
		        <div id="itemBalAdjHeader_tabView" class="tab-pane fade in active"> 
			         	<jsp:include page="itemBalAdjHeader_tabView.jsp"></jsp:include>
		        </div> 
		        <div id="tab_viewSupplier" class="tab-pane fade "> 
			         	<jsp:include page="tab_viewSupplier.jsp"></jsp:include>
		        </div> 
		    </div>  
		</div>
	</div>
</div>
</body>
</html>
