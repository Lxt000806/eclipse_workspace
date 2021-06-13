<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料结算管理--审核</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
//校验函数
/**初始化表格*/
$(function(){
	$("#appEmp").openComponent_employee({showValue:"${itemCheck.appEmp}",showLabel:"${itemCheck.appEmpDescr}"});
	$("#confirmEmp").openComponent_employee({readonly:true,showValue:"${itemCheck.confirmEmp}",showLabel:"${itemCheck.confirmEmpDescr}"});

	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	var dataSet = {
			firstSelect:"itemType1",
			firstValue:'${itemCheck.itemType1}',
			disabled:"true"
		};
	Global.LinkSelect.setSelect(dataSet);
	function getData(data){
		if (!data) return;
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:'${itemCheck.itemType1}',
		};
		Global.LinkSelect.setSelect(dataSet);
		$('#address').val(data.address);
	}	
	$("#custCode").openComponent_customer({readonly:true,callBack: getData,condition:{status:"4",disabled:"disabled",}});
	$("#custCode").setComponent_customer({showValue:'${itemCheck.custCode}',showLabel:"${custDescr}"});
	getCustomerPay("${itemCheck.custCode}");
});
function getCustomerPay(custCode){
		$.ajax({
			url:'${ctx}/admin/cljsgl/getCustomerPay',
			type: 'post',
			data: {'custCode':custCode},
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if (parseFloat(obj.wdz)!=0){
		    		$("#tips").text("未达账金额:"+obj.wdz);
		    	}else{
		    		$("#tips").text("");
		    	}
		    }
		});
	}
</script>
</head>
    
<body  >
<input type="hidden" name="jsonString" value="" />
<div class="body-box-form" >
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
      	<button type="button" id="saveBtn" class="btn btn-system ">审核</button>
      	<button type="button" id="saveBtnReturn" class="btn btn-system ">审核退回</button>
      	<button type="button" id="customerInfoBtn" class="btn btn-system ">客户信息</button>
		<button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<font color="red" ><span id="tips"></span></font>
      </div>
   	</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<input type="hidden" name="exportData" id="exportData">
				<ul class="ul-form">
					<div class="validate-group row" >
					<li>
						<label>结算单号</label>
						<input type="text" id="no" name="no"  readonly="true"  value="${itemCheck.no}"/>
					</li>
					</div>
					<div class="validate-group row" >
					<li >
						<label >客户编号</label>
						<input type="text" id="custCode" name="custCode" style="width:160px;" value="${itemCheck.custCode}" />
					</li>
					<li >
						<label >楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${itemCheck.address}"  readonly="readonly"/>
					</li>
					</div>
					<div class="validate-group row" >			
					<li>		
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1"  value="${itemCheck.itemType1}"  disabled="true"></select>
					</li>
					<li>
						<label>结算状态</label>
						<house:xtdm id="status" dictCode="ITEMCHECKSTATUS"  value="${itemCheck.status }" disabled="true"></house:xtdm>
					</li>
					</div>
					<div class="validate-group row" >					
					<li>
						<label>申请日期</label>
						<input type="text" id="appdate" name="appdate" value="${itemCheck.date}" disabled="true"/>	
					</li>
					<li>
						<label>审核日期</label>
						<input type="text" id="confirmDate" name="confirmDate" value="${itemCheck.confirmDate}" disabled="true"/>	
					</li>
					</div>
					<div class="validate-group row" >
					<li>	
						<label>申请人</label>
						<input type="text" id="appEmp" name="appEmp" style="width:160px;"  value="${itemCheck.appEmp}"  disabled="true"/> 
					</li>
					<li>	
						<label>审核人</label>
						<input type="text" id="confirmEmp" name="confirmEmp" style="width:160px;"  value="${itemCheck.confirmEmp}"  disabled="true"/> 
					</li>
					</div>
					<div class="validate-group row" >					
					<li>
						<label class="control-textarea" >申请说明</label>
						<textarea id="appRemark" name="appRemark" readonly="readonly" style="width:160px" >${itemCheck.appRemark}</textarea>
					</li>
					<li>
						<label class="control-textarea" >审核说明</label>
						<textarea id="confirmRemark" name="confirmRemark" style="width:160px">${itemCheck.confirmRemark}</textarea>
					</li>	
					</div>
				</ul>
			</form>
		</div>
		</div>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script>
$(function(){
	$("#saveBtn").on("click",function(){		
		art.dialog({
			content:"确定要进行审核通过操作？",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				save();
			},
			cancel: function () {
					return true;
			}
		});
	});
	
	$("#saveBtnReturn").on("click",function(){		
		art.dialog({
			content:"确定要进行审核退回操作？",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				saveReturn();
			},
			cancel: function () {
					return true;
			}
		});		
	});
	
	$("#customerInfoBtn").on("click", function() {		
		Global.Dialog.showDialog("customerInfoView", {
		    title: "查看客户【" + "${itemCheck.address}" + "】",
		    url: "${ctx}/admin/customerXx/goDetail?id=" + "${itemCheck.custCode}",
		    height: 750,
		    width: 1430
		});
	}); 
	
});
	
	function save(){
		var datas=$("#page_form").jsonForm();
		//当需求数量不等于发货数量时，是否继续进行材料结算操作
		$.ajax({
			url:"${ctx}/admin/cljsgl/getHasNotConfirmedItemChg",
			type: "post",
			data: datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(true==obj){
		    		art.dialog({
						content:"存在未审核的变更单,无法审核通过！",
					});
					return false
		    	}else{
					 $.ajax({
						url:"${ctx}/admin/cljsgl/ReqQtyEqualSendQty",
						type: "post",
						data: datas,
						dataType: "json",
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
					    },
					    success: function(obj){
					    	if(obj==0){
						    	$.ajax({
									url:"${ctx}/admin/cljsgl/doCljsglCheck",
									type: 'post',
									data: datas,
									dataType: 'json',
									cache: false,
									error: function(obj){
										showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
								    },
								    success: function(obj){
								    	if(obj.rs){
								    		art.dialog({
												content: obj.msg,
												time: 1000,
												beforeunload: function () {
								    				closeWin();
											    }
											});
								    	}else{
											art.dialog({
												content: obj.msg,
												width: 200
											});
								    	}
								    }
								 });		
					    	}else{
				    			Global.Dialog.showDialog("cljsglqr",{			
									title:"材料结算确认",
									url:"${ctx}/admin/cljsgl/goCheckCljsqr",
									postData:datas,
									height: 400,
									width:800,
									returnFun:  function(data){					     	
								    	if(data=="1"){  
								    		art.dialog({
												content: "保存成功",
												time: 1000,
												beforeunload: function () {
								    				closeWin();
											    }
											});         
							        	}
							    	}
								}); 
					    	}
					 	}
					}); 
				}
			}
		});
	}		
	function saveReturn(){
		var datas=$("#page_form").jsonForm();
		$.ajax({
			url:"${ctx}/admin/cljsgl/doCljsglCheckReturn",
			type: 'post',
			data: datas,
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
		    	}else{
					art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
		    }
		});
	}
</script>	
</body>
</html>


