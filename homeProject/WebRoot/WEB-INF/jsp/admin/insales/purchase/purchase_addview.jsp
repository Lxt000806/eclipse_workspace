<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>采购明细——查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

</head>	
	<body>
		<div class="body-box-list">
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
					<input type="hidden" id="expired"  name="expired" value="${purchase.expired }"/>
						<ul class="ul-form">
							<li>
								<label>材料编号</label>
								<input type="text" id="itCode" name="itCode" style="width:160px;" value="${purchaseDetail.itcode}"/>
							</li>
							<li>
								<label>材料名称</label>
								<input type="text" id="itdescr" name="itdescr" style="width:160px;" value="${purchaseDetail.itdescr}" readonly="true"/>
							</li>
							<li>
								<label>单价</label>
								<input type="text" id="unitPrice" name="unitPrice" style="width:160px;"
									onblur="changeAmount()"  value="${purchaseDetail.unitPrice}" />
							</li>
							<li>
								<label>折扣</label>
								<input type="text" id="markup" name="markup" style="width:160px;"
									  value="${purchaseDetail.markup}" />
							</li>
							<li>
								<label>折扣前总价</label>
								<input type="text" id="befLineAmount" name="befLineAmount" style="width:160px;"
									  value="${purchaseDetail.amount * purchaseDetail.markup}" />
							</li>
							<li>
								<label>所有库存数量</label>
								<input type="text" id="allqty" name="allqty"   style="width:160px;" value="${purchaseDetail.allqty }" readonly="true"/>
							</li>
							<li>
								<label>采购数量</label>
								<input type="text" id="qtyCal" name="qtyCal" style="width:160px;" 
								onblur="changeAmount()"  value="${purchaseDetail.qtyCal}"/>
							</li>
							<li>
								<label>可用数量</label>
								<input type="text" id="useqty" name="useqty" style="width:160px;" value="${purchaseDetail.allqty+purchaseDetail.purqty-purchaseDetail.appqty-purchaseDetail.saleqty-purchaseDetail.applyqty-purchaseDetail.ypqty}" readonly="true"/>
							</li>
							<li>
								<label>总价</label>
								<input type="text" id="amount" name="amount" style="width:160px;" value="${purchaseDetail.amount}" readonly="true"/>
							</li>
							<li>
								<label>在途采购数量</label>
								<input type="text" id="purqty"  name="purqty" style="width:160px;" value="${purchaseDetail.purqty }" readonly="true"/>
							</li>
							<li>
								<label>领料审核数</label>
								<input type="text" id="appqty" name="appqty"   style="width:160px;" value="${purchaseDetail.appqty}" readonly="true"/>
							</li>
							<li>
								<label>领料申请数</label>
								<input type="text" id="applyqty" name="applyqty"   style="width:160px;" value="${purchaseDetail.applyqty }" readonly="true"/>
							</li>
							<li>
								<label>销售申请数</label>
								<input type="text" id="saleqty" name="saleqty"    style="width:160px;" value="${purchaseDetail.saleqty}" readonly="true"/>
							</li>
							<li>
								<label>样品库数量</label>
								<input type="text" id="ypqty" name="ypqty"    style="width:160px;" value="${purchaseDetail.ypqty}" readonly="true"/>
							</li>
							<li>
								<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2"  readonly="true">${purchaseDetail.remarks }</textarea>
							</li>

							<li hidden="true">
								<label>材料类型</label>
								<input type="text" id="itemtype1" name="itemtype1"    style="width:160px;" value="${purchaseDetail.itemtype1}"/>
							</li>
							<li hidden="true">
								<label>颜色</label>
								<input type="text" id="color" name="color"    style="width:160px;" value="${purchaseDetail.color}"/>
							</li>
							<li hidden="true">
								<label>单位</label>
									<input type="text" id="uniDescr" name="uniDescr"    style="width:160px;" value="${purchaseDetail.uniDescr}"/>
							</li>
							<li hidden="true">
								<label>品牌</label>
								<input type="text" id="sqlCodeDescr" name="sqlCodeDescr"    style="width:160px;" value="${purchaseDetail.sqlCodeDescr}"/>
							</li>
						</ul>	
				</form>
				</div>
			</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_detail" data-toggle="tab">仓库结存</a></li>
		    </ul> 
				<div id="content-list">
					<table id="dataTable"></table>
				</div>
		</div>			
			</div>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$("#tabs").tabs();
$(function(){
	$("#itCode").openComponent_item({showValue:'${purchaseDetail.itcode}',readonly:true});
	if('${costRight}'=='0'){
		document.getElementById('amount').setAttribute('type','password') ;
		document.getElementById('unitPrice').setAttribute('type','password') ;
	}
		var lastCellRowId;
		var gridOption = {	
			url:"${ctx}/admin/itemWHBal/goPurchJqGrid",
			postData:{itCode:'${purchaseDetail.itcode}'},
			height:$(document).height()-$("#content-list").offset().top-70,
			rowNum:10000000,
			colModel : [
			{name:'whcode', index:'whcode', width:80, label:'仓库编号', sortable:true ,align:"left"},
			{name:'desc1', index:'descr', width:200, label:'仓库名称', sortable:true ,align:"left" ,count:true},
			{name:'qtycal', index:'allqty', width:60, label:'现有数量', sortable:true ,align:"right" ,sum:true},
		],  
		beforeSelectRow:function(rowId, e){
			setTimeout(function(){
				relocate(rowId,"dataTable");
			},10);
		}
	};	

			Global.JqGrid.initEditJqGrid("dataTable",gridOption);
	if("${purchaseDetail.fromPage}" == "supplierCheck" && "${purchaseDetail.type}" == "R"){
		$("#qtyCal").parent().children("label").html("退回数量");
	}
	
});
function changeAmount(){
  	var unitPrice= parseFloat($("#unitPrice").val());
   var  qtyCal=parseFloat($("#qtyCal").val());
   var 	amount=unitPrice*qtyCal
   if(amount) {
   		 $("#amount").val(amount);
   }else{
     	 $("#amount").val(0);
   }
  
}	
	
	
</script>
	</body>
</html>
