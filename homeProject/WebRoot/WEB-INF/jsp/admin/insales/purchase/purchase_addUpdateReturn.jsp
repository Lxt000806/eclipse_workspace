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
	<title>采购明细增加</title>
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
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="expired"  name="expired" value="${purchaseDetail.expired }"/>
					<input type="hidden" id="projectCost"  name="projectCost" value="${purchaseDetail.projectCost }"/>
					<input type="hidden" id="befLinePrice"  name="befLinePrice" value="${purchaseDetail.befLinePrice}"/>
						<ul class="ul-form">
							<li>
								<label>材料编号</label>
								<input type="text" id="itcode" name="itcode" style="width:160px;" value="${purchaseDetail.itcode}" readonly="true"/>
							</li>
							<li>
								<label>材料名称</label>
								<input type="text" id="itdescr" name="itdescr" style="width:160px;" value="${purchaseDetail.itdescr}" readonly="true"/>
							</li>
							<li>
								<label ><span class="required">*</span>单价</label>
								<input type="text" id="unitPrice" name="unitPrice" style="width:160px;"
									onblur="changeAmount()" readonly="true" value="${purchaseDetail.unitPrice}"/>
							</li>
							<li>
								<label>折扣</label>
								<input type="text" id="markup" name="markup" style="width:160px;"
									  readonly="true" value="${purchaseDetail.markup}" />
							</li>
							<li>
								<label>折扣前总价</label>
								<input type="text" id="befLineAmount" name="befLineAmount" style="width:160px;"
									 readonly="true" value="${purchaseDetail.amount / purchaseDetail.markup}" />
							</li>
							<li>
								<label>库存数量</label>
								<input type="text" id="allqty" name="allqty"   style="width:160px;" value="${purchaseDetail.allqty }" readonly="true"/>
							</li>
							<li>
								<label><span class="required">*</span>退回数量</label>
								<input type="text" id="qtyCal" name="qtyCal" style="width:160px;" 
								onblur="changeAmount()"  value="${purchaseDetail.qtyCal}"/>
							</li>
							<li>
								<label>可用数量</label>
								<input type="text" id="useqty" name="useqty" style="width:160px;" value="${purchaseDetail.allqty+purchaseDetail.purqty-purchaseDetail.appqty-purchaseDetail.saleqty-purchaseDetail.applyqty}" readonly="true"/>
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
								<textarea id="remarks" name="remarks" rows="2" value=" ${purchaseDetail.remarks }"></textarea>
						
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
							<li hidden="true"  >
								<label>PUNo</label>
								<input type="text" id="puno" name="puno"    style="width:160px;" value="${purchaseDetail.puno}"/>
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
				<table id="dataTable">
				</table>
			</div>
		</div>			
	</div>
		<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_purchase.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$("#tabs").tabs();
$(function(){
	if('${costRight}'=='0'){
		document.getElementById('amount').setAttribute('type','password') ;
		document.getElementById('unitPrice').setAttribute('type','password') ;
	}												
	$("#itcode").openComponent_item({callBack: getData,condition: {itemType1:'${purchaseDetail.itemtype1}',puno:'${purchaseDetail.puno}',disabled:true,title:'产品选择',readonly:"1"}});
	$('input').unbind('blur');
	$("#itcode").setComponent_item({showValue:'${purchaseDetail.itcode}'});
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemWHBal/goPurchJqGrid?itCode="+'${purchaseDetail.itcode}',
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		colModel : [
			{name:'whcode', index:'whcode', width:80, label:'仓库编号', sortable:true ,align:"left"},
			{name:'desc1', 	index:'descr', 	width:200,label:'仓库名称', sortable:true ,align:"left" ,count:true},
			{name:'qtycal', index:'allqty', width:60, label:'现有数量', sortable:true ,align:"left" ,sum:true},
		]
	});	
	function getData(data){
		if (!data) return;
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:'${purchaseDetail.itemtype1}',
		};
		Global.LinkSelect.setSelect(dataSet);
		$('#itcode').val(data.code);
		$('#itdescr').val(data.descr);
		$('#unitPrice').val(data.price);
		$('#allqty').val(data.allqty);
		$('#remarks').val(data.remark);
		$('#color').val(data.color);
		$('#qtyCal').val('');
		$('#amount').val('');
		$('#uniDescr').val(data.uomdescr);
		$('#sqlCodeDescr').val(data.sqldescr);
	  
	  Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemWHBal/goPurchJqGrid?itCode="+$.trim($("#itcode").val()),
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		colModel : [
			{name:'whcode', index:'whcode', width:80, label:'仓库编号', sortable:true ,align:"left"},
			{name:'desc1', 	index:'descr', 	width:200,label:'仓库名称', sortable:true ,align:"left" ,count:true},
			{name:'qtycal', index:'allqty', width:60, label:'现有数量', sortable:true ,align:"left" ,sum:true},
		]
	});	
	    $("#dataTable").jqGrid('setGridParam',{url : "${ctx}/admin/itemWHBal/goPurchJqGrid?itCode="+$.trim($("#itcode").val())});
       	$("#dataTable").jqGrid().trigger('reloadGrid'); 
		
		$.ajax({
			url:"${ctx}/admin/purchase/getAjaxDetail?itcode="+$('#itcode').val(),
			type:'post',
			data:{},
			dataType:'json',
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				if (obj){
					$('#applyqty').val(obj.applyqty);
					$('#appqty').val(obj.appqty);
					$('#purqty').val(obj.purqty);
					$('#saleqty').val(obj.saleqty);	
					$('#useqty').val(obj.allqty+obj.purqty-obj.applyqty-obj.appqty-obj.saleqty);	
				}
			}
		});
	}
	$("#itcode").openComponent_item({callBack: getData,condition: {itemType1:'${purchaseDetail.itemtype1}',puno:'${purchaseDetail.puno}',disabled:true,title:'产品选择',readonly:"1"}});
	$("#saveBtn").on("click",function(){
	if(!$("#page_form").valid()) {return false;}//表单校验
	 	var qtycal = $.trim($("#qtyCal").val());
		var unitprice = $.trim($("#unitPrice").val());
		if(qtycal==''){
				art.dialog({content: "请填写采购数量",width: 200});
				return false;
			}else if(qtycal<=0){
				art.dialog({content: "采购数量必须为正数，请重新输入",width: 200});
				return false;
			}else if(unitprice=='') {
				art.dialog({content: "请填写单价",width: 200});
				return false;
			}else if(unitprice<=0){
				art.dialog({content: "单价必须为正数，请重新输入",width: 200});
				return false;
			}
		 var selectRows = [];
		 var datas=$("#page_form").jsonForm();
		 	selectRows.push(datas);
			 Global.Dialog.returnData = selectRows;
			closeWin();
		}); 	
 });
		
function changeAmount(){
  	var unitPrice= $("#unitPrice").val();
   var  qtyCal=$("#qtyCal").val();
   var  markup=$("#markup").val();
   var  befLinePrice=$("#befLinePrice").val();
  /*  var  allqty=$("#allqty").val();
   var  purqty=$("#purqty").val();
   var  appqty=$("#appqty").val();
   var  saleqty=$("#saleqty").val();
   var  applyqty=$("#applyqty").val(); */
   var 	amount=unitPrice*qtyCal;
   var 	befLineAmount = befLinePrice*qtyCal;
   if(amount) {
   		 $("#amount").val(amount.toFixed(2));
   		 $("#befLineAmount").val(befLineAmount.toFixed(2));
   }else{
     	 $("#amount").val(0);
     	 $("#befLineAmount").val(0);
   }
   // if(1) {
    //$("#useqty").val(allqty+purqty-appqty-saleqty-applyqty);
  // } 
  
}	
	
</script>
	</body>
</html>
