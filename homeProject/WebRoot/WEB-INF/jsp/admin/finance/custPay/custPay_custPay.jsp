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
		<title>客户付款</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
		$(function(){
			if("${customerPayMap.ispubreturn}"=="1"){
				$("#payee").text("收款单位：${customerPayMap.payeedescr}");
			}
			if("${customer.m_umState}"=="V"){
				$("#custpaybtn button").attr("disabled",true);
			}
			// 不可编辑‘对公退款’
			$("#isPubReturn").prop("disabled",true);
			if("V"=="${customer.m_umState}"){
				$("#isPubReturnBut").remove();
			}
			
			var sumpay=parseFloat("${customerPayMap.sumpay }");
			var designFeeType="${customerPayMap.designFeeType}";
			var tax=parseFloat("${customerPayMap.tax}");
			var contractfee=parseFloat($("#contractfee").val());
			var sumPay=parseFloat("${customerPayMap.sumpay}");
			var returndesignfee=parseFloat("${customerPayMap.returndesignfee }");
			var stddesignfee=parseFloat("${customerPayMap.stddesignfee }");
			var designfee=parseFloat($("#designfee").val());
			var lblerror="";
			if(designFeeType=="2"){
				if(contractfee+tax+designfee!=sumPay){
					lblerror="工程造价【"+contractfee+"】+设计费【"+designfee+"】+税金【"+tax+"】与付款计划总额【"+sumPay+"】不相等！";
				}
			}else if(designFeeType=="1") { 
				if(contractfee+tax+designfee!=sumPay){
					lblerror="工程造价【"+contractfee+"】+设计费【"+designfee+"】+税金【"+tax+"】与付款计划总额【"+sumPay+"】不相等！";
				}
			}else if (designFeeType=="3") { 
				if(contractfee+tax+designfee!=sumPay){
					lblerror="工程造价【"+contractfee+"】+设计费【"+designfee+"】+税金【"+tax+"】与付款计划总额【"+sumPay+"】不相等！";
				}
			}
			
			$("#tips").text(lblerror);
			// 修改对公退款
			$("#isPubReturnBut").on("click", function () {
				if ("V" == "${customer.m_umState}") return;
				var isPubReturn = $("#isPubReturn").val();
				Global.Dialog.showDialog("goIsPubReturn",{
					title:"修改对公退款",
					url:"${ctx}/admin/custPay/goIsPubReturn",
					postData:{isPubReturn:isPubReturn, custCode:"${customerPayMap.code}"},
					width:500,
					height:300,
					returnFun:function (data) {
						$("#isPubReturn").prop("disabled",false);
						$("#isPubReturn").val(data);
						$("#isPubReturn").prop("disabled",true);
					}
				});
			});

		});
		function custInfo(){
		      Global.Dialog.showDialog("customerXxView",{
				  title:"查看客户【"+"${customerPayMap.address}"+"】",
				  url:"${ctx}/admin/customerXx/goDetail?id=${customerPayMap.code }&isCustPay=1",
				  height:750,
				  width:1430
			  });
		}
		//打印
		function print(){
			var records = $("#detailDataTable").jqGrid('getGridParam', 'records');                
			var ret = selectDataTableRow();
		   	var reportName = "custPay_khfk.jasper";
		   	Global.Print.showPrint(reportName, {
		   		custCode:"${customerPayMap.code }",
		   		//records:records,
				LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
				SUBREPORT_DIR: "<%=jasperPath%>" 
			});
		}
	</script>
	</head>

<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
							<button id="print" type="button" class="btn btn-system"
								onclick="print()">打印</button>
							<button id="custInfo" type="button" class="btn btn-system"
								onclick="custInfo()" >客户信息查询</button>
							<button id="isPubReturnBut" type="button" class="btn btn-system">对公退款</button>
							<button id="closeBut" type="button" class="btn btn-system"
								onclick="closeWin(false)">关闭</button>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<font color="red" ><span id="tips"></span></font>
							<font color="red" size="4" style="position:absolute;left:1000px;top:8px"><b><span id="payee"></span></b></font>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" /><input
					type="hidden" name="isExitTip" id="isExitTip" value="0" /> <input
					type="hidden" name="m_umState" id="m_umState" />
				<ul class="ul-form">
					<li><label>客户编号</label> <input type="text" id="code"
						name="code" style="width:160px;" value="${customerPayMap.code }"
						readonly="readonly" />
					</li>
					<li><label>客户名称</label> <input type="text" id="descr"
						name="descr" style="width:160px;" value="${customerPayMap.descr }"
						readonly="readonly" />
					</li>
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" style="width:450px;"
						value="${customerPayMap.address }" readonly="readonly" />
					</li>
					<li><label>客户状态</label> <input type="text" id="statusdescr"
						name="statusdescr" style="width:160px;"
						value="${customerPayMap.statusdescr }" readonly="readonly" />
					</li>
					<li><label>面积</label> <input type="text" id="area" name="area"
						style="width:160px;" value="${customerPayMap.area }"
						readonly="readonly" />
					</li>
					<li><label>户型</label> <input type="text" id="layoutdescr"
						name="layoutdescr" style="width:160px;"
						value="${customerPayMap.layoutdescr }" readonly="readonly" />
					</li>
					<li><label>客户类型</label> <input type="text" id="custTypeDescr"
						name="custTypeDescr" style="width:160px;"
						value="${customerPayMap.custtypedescr}" readonly="readonly" />
					</li>
					<li><label>工程造价</label> <input type="text" id="contractfee"
						name="contractfee" style="width:160px;"
						value="${customerPayMap.contractfee }" readonly="readonly" />
					</li>
					<li><label>设计费</label> <input type="text" id="designfee"
						name="designfee" style="width:160px;"
						value="${customerPayMap.designfee }" readonly="readonly" />
					</li>
					<li><label>工程造价+设计费</label> <input type="text"
						id="id_contractfee" name="id_contractfee" style="width:160px;"
						value="${customerPayMap.contractfeedesignfee}"
						readonly="readonly" />
					</li>
					<li><label>税金</label> <input type="text" style="width:160px;"
						id="tax" name="tax" value="${customerPayMap.tax }"
						readonly="readonly" />
					</li>
					<li><label>首批付款</label> <input type="text" id="firstpay"
						name="firstpay" style="width:160px;"
						value="${customerPayMap.firstpay }" readonly="readonly" />
					</li>
					<li><label>二批付款</label> <input type="text" id="secondpay"
						name="secondpay" style="width:160px;"
						value="${customerPayMap.secondpay }" readonly="readonly" />
					</li>
					<li><label>三批付款</label> <input type="text" id="thirdpay"
						name="thirdpay" style="width:160px;"
						value="${customerPayMap.thirdpay }" readonly="readonly" />
					</li>
					<li><label>尾批付款</label> <input type="text" id="fourpay"
						name="fourpay" style="width:160px;"
						value="${customerPayMap.fourpay }" readonly="readonly" />
					</li>
					<li><label>工程分期合计</label> <input type="text" id="sumpay"
						name="sumpay" style="width:160px;"
						value="${customerPayMap.sumpay }" readonly="readonly" />
					</li>
					<li style="padding-left: 300px;"><label>增减总金额</label> <input type="text" id="zjzje"
						name="zjzje" style="width:160px;" value="${customerPayMap.zjzje }"
						readonly="readonly" />
					</li>
					<li>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</li>
					<li ><label>已付款</label> <input type="text" id="haspay"
						name="haspay" style="width:160px;"
						value="${customerPayMap.haspay }" readonly="readonly" />
					</li>
					<li><label>本期付款期数</label> <input type="text" id="nowNum"
						name="nowNum" style="width:160px;" value="${balanceMap.nowNum}"
						readonly="readonly" />
					</li>
					<li><label>本期应付余额</label> <input type="text" id="nowShouldPay"
						name="nowShouldPay" style="width:160px;"
						value="${balanceMap.nowShouldPay }" readonly="readonly" />
					</li>
					<li><label>下期应付(含本期)</label> <input type="text"
						id="nextShouldPay" name="nextShouldPay" style="width:160px;"
						value="${balanceMap.nextShouldPay}" readonly="readonly" />
					</li>
					<li><label>标准设计费</label> <input type="text" id="stddesignfee"
						name="stddesignfee" style="width:160px;"
						value="${customerPayMap.stddesignfee }" readonly="readonly" />
					</li>
					<li><label>设计费返还</label> <input type="text"
						id="returndesignfee" name="returndesignfee" style="width:160px;"
						value="${customerPayMap.returndesignfee }" readonly="readonly" />
					</li>
					<li>
						<label>对公退款</label> 
						<house:xtdm id="isPubReturn" dictCode="YESNO" value="${customerPayMap.ispubreturn}"></house:xtdm>
					</li>
					<li><label>退款金额</label> <input type="text" id="returnAmount"
						name="returnAmount" style="width:160px;"
						onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
						value="${customerPayMap.returnamount }" readonly="readonly" />
					</li>
					<li><label>未达账</label> <input type="text" id="wdz"
						name="wdz" style="width:160px;" value="${customerPayMap.wdz}"
						readonly="readonly" />
					</li>
					<li><label class="control-textarea">优惠说明</label> <textarea
							id="discremark" name="discremark" readonly="readonly">${customerPayMap.discremark }</textarea>
					</li>
					<li><label class="control-textarea">付款说明</label> <textarea
							id="payremark" name="payremark" readonly="readonly">${customerPayMap.payremark }</textarea>
					</li>
		</div>
	</div>
	</ul>
		</form>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabPayInfo" class="active"><a
				href="#tab_PayInfo" data-toggle="tab">付款信息</a>
			</li>
			<li id="tabChgInfo" class=""><a
				href="#tab_ChgInfo" data-toggle="tab">增减信息</a>
			</li>
			<li id="tabChgApp" class=""><a
				href="#tab_ChgApp" data-toggle="tab">增减申请（<span id="appCount"></span>）</a>
			</li> 
		</ul>
		 <div class="tab-content">
			<div id="tab_PayInfo" class="tab-pane fade in active">
				<jsp:include page="tab_payinfo.jsp"></jsp:include>
			</div>
			 <div id="tab_ChgInfo" class="tab-pane fade ">
				<jsp:include page="tab_chginfo.jsp"></jsp:include>
			</div>
			<div id="tab_ChgApp" class="tab-pane fade ">
				<jsp:include page="tab_chgapp.jsp"></jsp:include>
			</div>
		</div> 
	</div>
	</div>
</body>
</html>
