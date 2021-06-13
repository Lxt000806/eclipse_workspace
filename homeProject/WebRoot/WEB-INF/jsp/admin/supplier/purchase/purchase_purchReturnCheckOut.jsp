<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE html>
<html>
<head>
	<title>采购退回确认</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>


</head>
<body>
	<div class="body-box-list">
		<div class="content-form">
			<!-- panelBar -->
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
								<button type="button" class="btn btn-system " id="saveBtn">
									<span>保存</span>
								</button>
								<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
									<span>关闭</span>
								</button>
					</div>
				</div>
			</div>
         <div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
			  		<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="P"/>
						<ul class="ul-form">
							<li>
								<label> 采购订单号</label>
								<input type="text" id="no" name="no" style="width:160px;" value="${purchase.no }" readonly="readonly"/>
							</li>
							<li>
								<label>采购类型</label>
								<house:xtdm id="type" dictCode="PURCHTYPE"  value="${purchase.type }" disabled="true"></house:xtdm>
							</li>
							<li>
								<label>采购单状态</label>
								<house:xtdm id="status" dictCode="PURCHSTATUS"  value="CONFIRMED" disabled="true" ></house:xtdm>
							</li>
							<li>
								<label>下单员</label>
								<input type="text" id="applyMan" name="applyMan" style="width:160px;" value="${purchase.applyMan }"/> 
							</li>
							<li>
								<label>采购日期</label>
								<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${purchase.date}' pattern='yyyy-MM-dd'/>" disabled="true"/>
							</li>
							<li>
								<label>到货日期</label>
								<input type="text" id="arriveDate" name="arriveDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${purchase.arriveDate}' pattern='yyyy-MM-dd'/>"  disabled="true"/>
							</li>
							<li>
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1" style="width: 160px;" disabled="disabled" value="${purchase.itemType1 }"></select>
							</li>
							<li>
								<label>供应商编号</label>
								<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier } " readonly="readonly" />
							</li>
							<li>
								<label>仓库编号</label>
								<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchase.whcode }">
							</li>
							<li>
								<label>原采购订单号</label>
								<input type="text" id="oldPUNo" name="oldPUNo" style="width:160px;" value="${purchase.oldPUNo } " readonly="readonly" />
							</li>
							<li hidden="true">
								<label>配送地点</label>
									<input type="text" id="delivType" name="delivType" style="width:160px;" value="${purchase.delivType }">
							</li>
							<li>
								<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2" >${purchase.remarks }</textarea>
							</li>
						</ul>
				</form>
				</div>
				</div>
			<div>
			
			</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_detail" data-toggle="tab">采购单明细</a></li>
		    </ul> 
		    <div class="tab-content">  
		        <div id="tab_detail" class="tab-pane fade in active"> 
		         	<jsp:include page="tab_detail.jsp"></jsp:include>
		        </div> 
		    </div>  
		</div>
	</div>
</div>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$("#tabs").tabs();
	$(function(){
		$("#whcode").openComponent_wareHouse();
		$("#whcode").setComponent_wareHouse({showValue:'${purchase.whcode}',showLabel:'${purchase.WHCodeDescr}',readonly: true});
		$("#applyMan").openComponent_employee();
		$("#applyMan").setComponent_employee({showValue:'${purchase.applyMan}',showLabel:'${purchase.applyManDescr}',readonly: true});
		$("#supplier").openComponent_supplier();
		$("#supplier").setComponent_supplier({showValue:'${purchase.supplier}',showLabel:'${purchase.supDescr}',readonly: true});
	
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
		//查看
	$("#view1").on("click",function(){
		var ret = selectDataTableRow();
	 	if(ret){
		Global.Dialog.showDialog("PurchaseView",{
			title:"查看采购信息",
			url:"${ctx}/admin/supplierPurchase/goPurchView?id=" + ret.pk,
			height:700,
			width:1000,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}
	});  
	//输出到Excel
	$("#Excel").on("click",function(){
		Global.JqGrid.exportExcel("dataTable","${ctx}/admin/supplierPurchase/export","采购订单明细","targetForm");
	});
});


	</script>
</body>
</html>




