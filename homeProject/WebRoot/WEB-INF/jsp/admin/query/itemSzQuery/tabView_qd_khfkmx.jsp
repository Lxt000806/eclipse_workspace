<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_khfkmx_qd",{
		url:"${ctx}/admin/itemSzQuery/goKhfkJqGrid",
		postData:{custCode:$("#code").val()},
        autowidth: false,
        height:410,
		width:1259, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "date", index: "date", width: 120, label: "付款日期", sortable: true, align: "left", formatter: formatTime},
			{name: "amount", index: "amount", width: 70, label: "付款金额", sortable: true, align: "right", sum: true},
			{name: "documentno", index: "documentno", width: 140, label: "凭证号", sortable: true, align: "left"},
			{name: "checkdate", index: "checkdate", width: 120, label: "记账日期", sortable: true, align: "left", formatter: formatTime},
			{name: "remarkscheck", index: "remarkscheck", width: 170, label: "记账说明", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 140, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "操作人员", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 270, label: "备注", sortable: true, align: "left"}
        ]
	});
	
	$.ajax({
		url:"${ctx}/admin/itemSzQuery/getCustPayPlan",
		type: 'post',
	   	data: {custCode:$("#code").val()},
		dataType: 'json',
		cache: false,
		error: function(obj){			    		
			art.dialog({
				content: '访问出错,请重试',
				time: 3000,
				beforeunload: function () {
				}
			});
		},
		success: function(data){
			var inputs = $("#khfkmxUl_qd").children().children("input");
			for(var i=0;i<inputs.length;i++){
				if(data[inputs[i].id.substring(0,inputs[i].id.lastIndexOf("_"))] != undefined){
					if(inputs[i].id == "SignDate_qd"){
						var date = new Date(data[inputs[i].id.substring(0,inputs[i].id.lastIndexOf("_"))].time);
						$("#"+inputs[i].id).val(date.format("yyyy-MM-dd"));
					}else{
						$("#"+inputs[i].id).val(data[inputs[i].id.substring(0,inputs[i].id.lastIndexOf("_"))]);
					}
				}
			}
			//var contractFee = isNaN( parseFloat($("#ContractFee_qd").val()) ) ? 0.0:parseFloat($("#ContractFee_qd").val());
			var firstPay = isNaN(parseFloat(data.FirstPay)) ? 0.0:parseFloat(data.FirstPay);
			var secondPay = isNaN(parseFloat(data.SecondPay)) ? 0.0:parseFloat(data.SecondPay);
			var thirdPay = isNaN(parseFloat(data.ThirdPay)) ? 0.0:parseFloat(data.ThirdPay);
			var fourPay = isNaN(parseFloat(data.FourPay)) ? 0.0:parseFloat(data.FourPay);
			var designFee = isNaN( parseFloat($("#DesignFee_qd").val()) ) ? 0.0:parseFloat($("#DesignFee_qd").val());
			var zjzje = isNaN( parseFloat($("#zjzje_qd").val()) ) ? 0.0:parseFloat($("#zjzje_qd").val());
			var haspay = isNaN( parseFloat($("#Haspay_qd").val()) ) ? 0.0:parseFloat($("#Haspay_qd").val());
			var isRcvDesignFee = isNaN(parseFloat(data.isRcvDesignFee)) ? 0.0:parseFloat(data.isRcvDesignFee);
			
			$("#Wdz_qd").val(parseFloat(firstPay + secondPay + thirdPay + fourPay + designFee*isRcvDesignFee + zjzje - haspay).toFixed(1));
		}
	});	
});
</script>

<div style="width:100%;height:20px;background:#EEEEEE">客户付款计划</div>

<div class="panel-info">  
	<div class="panel-body">
		<form action="" method="post" id="dataForm"  class="form-search">
			<input type="hidden" name="jsonString" value="" />
			<ul id="khfkmxUl_qd" class="ul-form">
			<c:if test="${isWorkcost=='1'}">
					<li><label>项目经理</label> <input type="text" id="ProjectManName"
						name="ProjectManName" style="width:160px;"
						value="${customerPayMap.ProjectManName }" readonly="readonly" />
					</li>
					<li><label>设计师</label> <input type="text" id="DesignManName"
						name="DesignManName" style="width:160px;"
						value="${customerPayMap.DesignManName }" readonly="readonly" />
					</li>
					<li><label>业务员</label> <input type="text"
						id="BusinessManName" name="BusinessManName" style="width:160px;"
						value="${customerPayMap.BusinessManName }" readonly="readonly" />
					</li>
					<li><label>签订时间</label> <input type="text" id="SignDate_qd"
						name="SignDate_qd" />
					</li>
					<li><label>面积</label> <input type="text" id="area" name="area"
						style="width:160px;" value="${customerPayMap.Area }"
						readonly="readonly" />
					</li>
					<li><label>户型</label> <input type="text" id="layoutdescr"
						name="layoutdescr" style="width:160px;"
						value="${customerPayMap.LayOutDescr }" readonly="readonly" />
					</li>
					<li><label>工程造价</label> <input type="text"
						id="ContractFee_qd" name="ContractFee_qd" />
					</li>
					<li><label>设计费</label> <input type="text" id="DesignFee_qd"
						name="DesignFee_qd" />
					</li>
					<li><label>工程造价+设计费</label> <input type="text"
						id="id_contractfee" name="id_contractfee" style="width:160px;"
						value="${customerPayMap.ContractFee+customerPayMap.DesignFee }"
						readonly="readonly" />
					</li>
					<li><label>首批付款</label> <input type="text" id="firstpay"
						name="firstpay" style="width:160px;"
						value="${customerPayMap.FirstPay }" readonly="readonly" />
					</li>
					<li><label>二批付款</label> <input type="text" id="secondpay"
						name="secondpay" style="width:160px;"
						value="${customerPayMap.SecondPay }" readonly="readonly" />
					</li>
					<li><label>三批付款</label> <input type="text" id="thirdpay"
						name="thirdpay" style="width:160px;"
						value="${customerPayMap.ThirdPay }" readonly="readonly" />
					</li>
					<li><label>尾批付款</label> <input type="text" id="fourpay"
						name="fourpay" style="width:160px;"
						value="${customerPayMap.FourPay }" readonly="readonly" />
					</li>
					<li><label>工程分期合计</label> <input type="text" id="sumpay"
						name="sumpay" style="width:160px;"
						value="${customerPayMap.SumPay }" readonly="readonly" />
					</li>
					<li><label>基础直接费</label> <input type="text"
						id="basefee_dirctcontainbase" name="basefee_dirctcontainbase"
						style="width:160px;"
						value="${customerPayMap.BaseFee_DirctContainBase }"
						readonly="readonly" />
					</li>
					<li><label>基础综合费</label> <input type="text"
						id="basefee_compcontainbase" name="basefee_compcontainbase"
						style="width:160px;"
						value="${customerPayMap.BaseFee_CompContainBase }"
						readonly="readonly" />
					</li>
					<li><label>管理费</label> <input type="text"
						id="managefeecontainbase" name="managefeecontainbase"
						style="width:160px;"
						value="${customerPayMap.ManageFeeContainBase }"
						readonly="readonly" />
					</li>
					<li><label>主材费</label> <input type="text"
						id="mainfeecontainmain" name="mainfeecontainmain"
						style="width:160px;" value="${customerPayMap.MainFeeContainMain }"
						readonly="readonly" />
					</li>
					<li><label>软装费</label> <input type="text"
						id="softfeecontainsoft" name="softfeecontainsoft"
						style="width:160px;" value="${customerPayMap.SoftFeeContainSoft }"
						readonly="readonly" />
					</li>
					<li><label>集成费</label> <input type="text"
						id="integratefeecontainint" name="integratefeecontainint"
						style="width:160px;"
						value="${customerPayMap.IntegrateFeeContainInt }"
						readonly="readonly" />
					</li>
					<li><label>橱柜费</label> <input type="text"
						id="cupboardfeecontaincup" name="cupboardfeecontaincup"
						style="width:160px;"
						value="${customerPayMap.CupboardFeeContainCup }"
						readonly="readonly" />
					</li>
					<li><label>服务性产品</label> <input type="text"
						id="mainservfeecontainmainserv" name="mainservfeecontainmainserv"
						style="width:160px;"
						value="${customerPayMap.MainServFeeContainMainServ }"
						readonly="readonly" />
					</li>
					<li><label>优惠合计</label> <input type="text" id="sumdisc"
						name="sumdisc" style="width:160px;"
						value="${customerPayMap.SumDisc }" readonly="readonly" />
					</li>
					<li><label>增减总金额</label> <input type="text" id="zjzje_qd"
						name="zjzje_qd" />
					</li>
					<li><label>已付款</label> <input type="text" id="Haspay_qd"
						name="Haspay_qd" />
					</li>
					<li><label>未达账</label> <input type="text" id="Wdz_qd"
						name="Wdz_qd" />
					</li>
				</c:if>
				<c:if test="${isWorkcost!='1'}">
					<li><label>签订时间</label> <input type="text" id="SignDate_qd"
						name="SignDate_qd" />
					</li>
					<li hidden><label>工程造价</label> <input type="text"
						id="ContractFee_qd" name="ContractFee_qd" />
					</li>
					<li hidden><label>设计费</label> <input type="text"
						id="DesignFee_qd" name="DesignFee_qd" />
					</li>
					<li hidden><label>增减总金额</label> <input type="text"
						id="zjzje_qd" name="zjzje_qd" />
					</li>
					<li><label>已付款</label> <input type="text" id="Haspay_qd"
						name="Haspay_qd" />
					</li>
					<li><label>未达账</label> <input type="text" id="Wdz_qd"
						name="Wdz_qd" />
					</li>
				</c:if>
				<li><label>税金</label> <input type="text" id="tax"
					name="tax" value="${customerPayMap.Tax }"/>
				</li>
			</ul>
		</form>
	</div>
</div>

<div style="width:100%;height:20px;background:#EEEEEE">付款记录明细</div>

<table id="dataTable_khfkmx_qd"></table>
