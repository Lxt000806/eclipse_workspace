<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>集成业绩明细--编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	$(function() {
		//绑定实时修改input
		$('#perfAmount').bind('input propertychange', function() {  
			var perfAmountSet = $('#perfAmountSet').val();
		    var perfAmount=$('#perfAmount').val();
		    //总业绩=业绩+套餐内业绩(签订日期2018年10月3日后（ID='TaxLevyDate'）的楼盘，总业绩要乘以0.94（ID='SoftPerfPer'），预发、结算业绩都要by zjf2010720)     
		    $('#totalPerfAmount').val((parseFloat(perfAmount)+parseFloat(perfAmountSet))*parseFloat(${map.perfPer}));
		});
		$('#perfAmountSet').bind('input propertychange', function() {  
			var perfAmountSet = $('#perfAmountSet').val();
		    var perfAmount=$('#perfAmount').val();
		    //总业绩=业绩+套餐内业绩(签订日期2018年10月3日后（ID='TaxLevyDate'）的楼盘，总业绩要乘以0.94（ID='SoftPerfPer'），预发、结算业绩都要by zjf2010720)     
		    $('#totalPerfAmount').val((parseFloat(perfAmount)+parseFloat(perfAmountSet))*parseFloat(${map.perfPer}));//总业绩=业绩+套餐内业绩     
		});
		$("#designMan").openComponent_employee({
				showValue:"${map.designMan}",
				showLabel:"${map.designManDescr}",
				callBack : function(data) {
					
				},
		});
		$("#openComponent_employee_designMan").attr("readonly",true);
		if("${map.m_umState}"=="V"){
			$("font").css("color","black");
			$("input").attr("readonly",true);
			$("#designMan").openComponent_employee({readonly:true});
			$("#saveBtn").attr("disabled",true);
		}
		
		$("#saveBtn").on("click",function(){
			var datas = $("#dataForm").serialize();
			$.ajax({
				url:"${ctx}/admin/intPerfDetail/doUpdate",
				type: "post",
				data: datas,
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
		});
		
	});
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
   				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
        		<div class="panel-body">		
					<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
						<ul class="ul-form">
						<div class="validate-group row">
						<input type="hidden" id="pk" name="pk" value="${map.pk}"/>
							<li><label>档案号</label> <input type="text" id="documentNo"
								name="documentNo" style="width:160px;" value="${map.documentNo}"
								readonly="true" />
							</li>
							<li><label>楼盘</label> <input type="text" id="address"
								name="address" style="width:160px;" value="${map.address }"
								readonly="true" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>客户类型</label> <input type="text"
								id="custTypeDescr" name="custTypeDescr" style="width:160px;"
								value="${map.custTypeDescr }" readonly="true" />
							</li>
							<li><label><font color="blue">设计师</font> </label> <input
								type="text" id="designMan" name="designMan" style="width:160px;"
								value="${map.designMan }" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>发放类型</label> <input type="text" id="typeDescr"
								name="typeDescr" style="width:160px;" value="${map.typeDescr }"
								readonly="true" />
							</li>
							<li><label>是否橱柜</label> <input type="text"
								id="isCupBoardDescr" name="isCupboardDescr" style="width:160px;"
								value="${map.isCupboardDescr }" readonly="true" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label><font color="blue">业绩</font></label> <input type="number" id="perfAmount"
								name="perfAmount" style="width:160px;"
								value="${map.perfAmount }"/>
							</li>
							<li><label><font color="blue">套餐内业绩</font> </label> <input
								type="number" id="perfAmountSet" name="perfAmountSet"
								style="width:160px;" value="${map.perfAmount_Set }" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>总业绩</label> <input type="number"
								id="totalPerfAmount" name="totalPerfAmount" style="width:160px;"
								value="${map.totalPerfAmount }" readonly="true" />
							</li>
						</div>
					</ul>
					</form>	
				</div>
			</div>
		</div>
	</body>	
</html>
