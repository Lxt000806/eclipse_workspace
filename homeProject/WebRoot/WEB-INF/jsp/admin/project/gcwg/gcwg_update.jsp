<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>工程完工信息录入</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
	<style>
	#page_form .ul-form li {
		width: 320px;
	}
	#dataForm .ul-form li label {
	    width: 130px;
	}
	</style>
	<script type="text/javascript">
	function save(){
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var datas = $("#dataForm").jsonForm();
		$.ajax({
			url:"${ctx}/admin/gcwg/doUpdate",
			type: "post",
			data:datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
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
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
		    }
	 	});
	}
	</script>
</head>
<body>
<div class="body-box-form">
	<div class="panel panel-system">
    	<div class="panel-body">
      		<div class="btn-group-xs">
      			<c:if test="${operType!='V' }">
      			<button type="button" id="saveBtn" class="btn btn-system" onclick="save()">保存</button>
      			</c:if>
      			<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
      		</div>
   		</div>
	</div>
	<div class="panel panel-info" style="margin-bottom: 5px;">  
   		<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
			<ul class="ul-form">
				<div class="col-sm-3">
				<li>
				<label>客户编号</label>
				<input type="text" id="code" name="code" style="width:160px;" value="${customer.code }" readonly="readonly"/>
				</li>
				</div>
				
				<div class="col-sm-3">
				<li>
				<label>客户状态</label>
				<input type="text" id="statusDescr" name="statusDescr" style="width:160px;" value="${customer.statusDescr }" readonly="readonly"/>
				</li>
				</div>
				<c:if test="${operType=='V' }">
				<div class="col-sm-3">
				<li>
				<label>最后更新时间</label>
				<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;" readonly="readonly" 
					value="<fmt:formatDate value='${customer.lastUpdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
				</li>
				</div>
				
				<div class="col-sm-3">
				<li>
				<label>最后更新人员</label>
				<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;" value="${customer.lastUpdatedBy }" readonly="readonly"/>
				</li>
				</div>
				</c:if>
			</ul>
		</form>
	</div>
    <!--标签页内容 -->
	<div class="container-fluid">
	    <ul class="nav nav-tabs">
	      	<li class="active"><a href="#tab_mainDetail" data-toggle="tab">主项目</a></li>
	    </ul>
		<div class="tab-content">
	       	<div id="tab_mainDetail" class="tab-pane fade in active"> 
				<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<house:token></house:token>
            		<input type="hidden" id="code" name="code" value="${customer.code }" />
					<ul class="ul-form">
						<div class="col-sm-4">
						<li>
						<label>客户名称</label>
						<input type="text" id="descr" name="descr" style="width:160px;" value="${customer.descr }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>基础费</label>
						<input type="text" id="baseFee" name="baseFee" style="width:160px;" value="${customer.baseFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>软装费</label>
						<input type="text" id="softFee" name="softFee" style="width:160px;" value="${customer.softFee}" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>基础直接费</label>
						<input type="text" id="baseFeeDirct" name="baseFeeDirct" style="width:160px;" value="${customer.baseFeeDirct }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>软装优惠</label>
						<input type="text" id="softDisc" name="softDisc" style="width:160px;" value="${customer.softDisc }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>设计师</label>
						<input type="text" id="designManDescr" name="designManDescr" style="width:160px;" value="${customer.designManDescr }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>基础综合费</label>
						<input type="text" id="baseFeeComp" name="baseFeeComp" style="width:160px;" value="${customer.baseFeeComp }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>软装费变更</label>
						<input type="text" id="chgSoftFee" name="chgSoftFee" style="width:160px;" value="${customer.chgSoftFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>业务员</label>
						<input type="text" id="businessManDescr" name="businessManDescr" style="width:160px;" value="${customer.businessManDescr }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>基础协议优惠</label>
						<input type="text" id="baseDisc" name="baseDisc" style="width:160px;" value="${customer.baseDisc }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>集成费</label>
						<input type="text" id="integrateFee" name="integrateFee" style="width:160px;" value="${customer.integrateFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>工程造价</label>
						<input type="text" id="contractFee" name="contractFee" style="width:160px;" value="${customer.contractFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>基础费变更</label>
						<input type="text" id="chgBaseFee" name="chgBaseFee" style="width:160px;" value="${customer.chgBaseFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>集成优惠</label>
						<input type="text" id="integrateDisc" name="integrateDisc" style="width:160px;" value="${customer.integrateDisc }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li class="form-validate">
						<label style="color: blue;">物料人工耗用</label>
						<input type="text" id="materialFee" name="materialFee" style="width:160px;" value="${customer.materialFee }"/>
						</li>
						</div>
					
						<div class="col-sm-4">
						<li>
						<label>主材费</label>
						<input type="text" id="mainFee" name="mainFee" style="width:160px;" value="${customer.mainFee }" readonly="readonly"/>
						</li>
						</div>
					
						<div class="col-sm-4">
						<li>
						<label>集成费变更</label>
						<input type="text" id="chgIntFee" name="chgIntFee" style="width:160px;" value="${customer.chgIntFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>设计费</label>
						<input type="text" id="designFee" name="designFee" style="width:160px;" value="${customer.designFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>主材优惠</label>
						<input type="text" id="mainDisc" name="mainDisc" style="width:160px;" value="${customer.mainDisc }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>橱柜费</label>
						<input type="text" id="cupBoardFee" name="cupBoardFee" style="width:160px;" value="${customer.cupBoardFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>管理费</label>
						<input type="text" id="manageFee" name="manageFee" style="width:160px;" value="${customer.manageFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>主材费变更</label>
						<input type="text" id="chgMainFee" name="chgMainFee" style="width:160px;" value="${customer.chgMainFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>橱柜优惠</label>
						<input type="text" id="cupBoardDisc" name="cupBoardDisc" style="width:160px;" value="${customer.cupBoardDisc }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>量房费</label>
						<input type="text" id="measureFee" name="measureFee" style="width:160px;" value="${customer.measureFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>主材费(按比例提成)</label>
						<input type="text" id="mainFeePer" name="mainFeePer" style="width:160px;" value="${customer.mainFeePer }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>橱柜费变更</label>
						<input type="text" id="chgCupFee" name="chgCupFee" style="width:160px;" value="${customer.chgCupFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>制图费</label>
						<input type="text" id="drawFee" name="drawFee" style="width:160px;" value="${customer.drawFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>主材变更(按比例提成)</label>
						<input type="text" id="chgMainFeePer" name="chgMainFeePer" style="width:160px;" value="${customer.chgMainFeePer }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>服务产品费</label>
						<input type="text" id="mainServFee" name="mainServFee" style="width:160px;" value="${customer.mainServFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li>
						<label>效果图费</label>
						<input type="text" id="colorDrawFee" name="colorDrawFee" style="width:160px;" value="${customer.colorDrawFee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-8">
						<li class="form-validate" style="width: 100%;position: relative;">
						<label style="float: left;">完工备注</label>
						<textarea id="endRemark" name="endRemark" style="float: left;width: 500px;margin-left: 5px;">${customer.endRemark }</textarea>
						</li>
						</div>
						
						<div class="col-sm-4">
						<li class="form-validate">
						<label style="color: blue;">特殊优惠</label>
						<input type="text" id="specialDisc" name="specialDisc" style="width:160px;" value="${customer.specialDisc }"/>
						</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$("#dataForm").bootstrapValidator({
        message : "This value is not valid",
        feedbackIcons : {/*input状态样式图片*/
            validating : "glyphicon glyphicon-refresh"
        },
        fields: {
        	materialFee: {
		        validators: { 
		            numeric: {
		            	message: "物料人工耗用只能是数字" 
		            },
		            notEmpty: { 
		            	message: "物料人工耗用不能为空"  
		            }
		        }
	      	},
	      	specialDisc: {
		        validators: { 
		            numeric: {
		            	message: "特殊优惠只能是数字" 
		            },
		            notEmpty: { 
		            	message: "特殊优惠不能为空"  
		            }
		        }
	      	},
	      	endRemark: {
	      		validators: { 
		        	stringLength:{
	               	 	min: 0,
	          			max: 100,
	               		message:"备注长度必须在0-100之间" 
	               	}
		        }
	      	}
      	},
        submitButtons : 'input[type="submit"]'
    });
	$("#dataForm input[type='text']").each(function(){
		if ($(this).val() && !isNaN($(this).val())){
			$(this).val(parseFloat($(this).val()));
		}
	});
});
</script>
</body>
</html>


