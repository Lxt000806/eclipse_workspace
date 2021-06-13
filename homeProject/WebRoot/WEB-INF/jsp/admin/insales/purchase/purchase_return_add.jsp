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
					<input type="hidden" id="befLinePrice"  name="befLinePrice" value=""/>
							<ul class="ul-form">
							<li>
								<label><span class="required">*</span>材料编号</label>
								<input type="text" id="itcode" name="itcode" style="width:160px;" value="${purchaseDetail.itcode}" readonly="true"/>
							</li>
							<li>
								<label>材料名称</label>
								<input type="text" id="itdescr" name="itdescr" style="width:160px;" value="${purchaseDetail.itdescr}" readonly="true"/>
							</li>
							<li>
								<label>可用数量</label>
								<input type="text" id="useqty" name="useqty" style="width:160px;" value="${purchaseDetail.allqty+purchaseDetail.purqty-purchaseDetail.appqty-purchaseDetail.saleqty-purchaseDetail.applyqty}" readonly="true"/>
							</li>
							<li>
								<label><span class="required">*</span>退回数量</label>
								<input type="text" id="qtyCal" name="qtyCal" style="width:160px;" 
								onblur="changeAmount()"  value="${purchaseDetail.qtyCal}"/>
							</li>
							<li>
								<label><span class="required">*</span>单价</label>
								<input type="text" id="unitPrice" name="unitPrice" style="width:160px;"
									onblur="changeAmount()"  readonly="true" value="${purchaseDetail.unitPrice}"/>
							</li>
							<li>
								<label><span class="required">*</span>折扣</label>
								<input type="text" id="markup" name="markup" style="width:160px;"
									readonly="true" value="1"/>
							</li>
							<li>
								<label><span class="required">*</span>折扣前总价</label>
								<input type="text" id="befLineAmount" name="befLineAmount" style="width:160px;"
									readonly="true" value="0"/>
							</li>
							<li>
								<label>库存数量</label>
								<input type="text" id="allqty" name="allqty"   style="width:160px;" value="${purchaseDetail.allqty }" readonly="true"/>
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
							<li  hidden="true">
								<label>puno</label>
								<input type="text" id="puno" name="puno"    style="width:160px;" value="${purchaseDetail.puno}"/>
							</li>
							<li hidden="true" >
								<label >oldQtyCal</label>
								<input type="text" id="oldQtyCal" name="oldQtyCal"    style="width:160px;" value="${purchaseDetail.oldQtyCal}"/>
							</li>
							<li hidden="true">
								<label>itemType2</label>
								<input type="text" id="itemType2" name="itemType2"    style="width:160px;" value="${purchaseDetail.itemType2}"/>
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
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemWHBal/goJqGrid?itCode="+'000000',
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
		$('#itemType2').val(data.itemtype2);
		$('#markup').val(data.pumarkup);
		$('#befLinePrice').val(data.pubeflineprice);
	  
	  Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemWHBal/goJqGrid?itCode="+$.trim($("#itcode").val()),
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		colModel : [
			{name:'whcode', index:'whcode', width:80, label:'仓库编号', sortable:true ,align:"left"},
			{name:'desc1', 	index:'descr', 	width:200,label:'仓库名称', sortable:true ,align:"left" ,count:true},
			{name:'qtycal', index:'allqty', width:60, label:'现有数量', sortable:true ,align:"left" ,sum:true},
		]
	});	
	    $("#dataTable").jqGrid('setGridParam',{url : "${ctx}/admin/itemWHBal/goJqGrid?itCode="+$.trim($("#itcode").val())});
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
					$.ajax({
						url:"${ctx}/admin/purchase/getAjaxQtyCalDetail",
						type:'post',
						data:{puno:$('#puno').val(),itcode:$('#itcode').val()},
						dataType:'json',
						cache:false,
						error:function(obj){
							showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
						},
						success:function(obj){
							if (obj){
								$('#oldQtyCal').val(obj.qtyCal);
							}
						}
					});
				}
			}
		});
	}
	$("#itcode").openComponent_item({callBack: getData,condition: {itemType1:'${purchaseDetail.itemtype1}',puno:'${purchaseDetail.puno}',disabled:true,title:'产品选择',readonly:"1"}});

	$("#saveBtn").on("click",function(){
	if(!$("#page_form").valid()) {return false;}//表单校验
	 	var qtycal = $.trim($("#qtyCal").val());
		var unitprice = $.trim($("#unitPrice").val());
		var oldQtyCal = $.trim($("#oldQtyCal").val());
		
		if(parseFloat(oldQtyCal)<parseFloat(qtycal)){
			art.dialog({content: "退回数量不能大于原采购数量",width: 200});
				return false;
		}
		
		if(qtycal==''){
				art.dialog({content: "请填写退回数量",width: 200});
				return false;
			}else if(qtycal<=0){
				art.dialog({content: "退回数量必须为正数，请重新输入",width: 200});
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
  	var unitPrice= parseFloat($("#unitPrice").val());
   var  qtyCal=parseFloat($("#qtyCal").val());
   var  allqty=parseFloat($("#allqty").val());
   var  purqty=parseFloat($("#purqty").val());
   var  appqty=parseFloat($("#appqty").val());
   var  saleqty=parseFloat($("#saleqty").val());
   var  applyqty=parseFloat($("#applyqty").val());
   var  markup=parseFloat($("#markup").val());
   var  befLinePrice=parseFloat($("#befLinePrice").val());
   var 	amount=myRound(unitPrice*qtyCal,2);
   var befLineAmount = befLinePrice * qtyCal;
   if(amount) {
   		 $("#amount").val(amount);
   		 $("#befLineAmount").val(befLineAmount);
   }else{
     	 $("#amount").val(0);
     	 $("#befLineAmount").val(0);
   }
    if(1) {
    $("#useqty").val(allqty+purqty-appqty-saleqty-applyqty);
   } 
  
}	
	
</script>
	</body>
</html>
