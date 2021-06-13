<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>定责人员--增加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}"type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}"type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}"type="text/javascript"></script>
<script type="text/javascript">
function save(){
	var faultType=$("#faultType").val();
	var faultTypeDescr=$("#faultTypeDescr").val();
	var name="";
	var empCode=$("#empCode").val();
	var workerCode=$("#workerCode").val();
	var supplCode=$("#supplCode").val();
	var amount=$("#amount").val();
	var pk=$("#pk").val();
	var isRepeated=false;
	if(faultType==""){
		art.dialog({
			content:"责任人类型不能为空！",
		});
		return;
	}else if(faultType=="1" && empCode==""){
		art.dialog({
			content:"请选择员工！",
		});
		return;
	}else if(faultType=="2" && workerCode==""){
		art.dialog({
			content:"请选择工人！",
		});
		return;
	}else if(faultType=="4" && supplCode==""){
		art.dialog({
			content:"请选择供应商！",
		});
		return;
	}
	if(amount==""){
		art.dialog({
			content:"金额不能为空！",
		});
		return;
	}
	
	var rows = $(top.$("#iframe_view")[0].contentWindow.document.getElementById("dataTable_man")).jqGrid("getRowData");
	if(rows.length>0){
		for(var i=0;i<rows.length;i++){
			if(rows[i].pk!=pk){
				if(rows[i].faulttype=="1"&&rows[i].empcode==empCode&&rows[i].empcode!=""){
					name=rows[i].empnamechi;
					isRepeated=true;
				}else if(rows[i].faulttype=="2"&&rows[i].workercode==workerCode&&rows[i].workercode!=""){
					name=rows[i].workernamechi;
					isRepeated=true;
				}else if(rows[i].faulttype=="4"&&rows[i].supplcode==supplCode&&rows[i].supplcode!=""){
					name=rows[i].suppldescr;
					isRepeated=true;
				}
			}
		}
	}
	if(isRepeated){
		art.dialog({
			content: "已经存在名为["+name+"]的"+faultTypeDescr+"！",
		});
		return;
	}
	if($("#faultType").val()=="1" && $("#empCode").val()=="${map.designMan}"){
		if($("#leftDesignRiskFund").val()<0 && $("#riskFund").val()>0){
		
		// 不再限制前端风险金是否超支 20200815
// 			art.dialog({
// 				content: "累计已使用设计师风控基金超出可使用风控基金总额，保存失败！",
// 			});
// 			return;
		}
	}else{
		if($("#leftPrjManRiskFund").val()<0 && $("#riskFund").val()>0){
			art.dialog({
				content: "累计已使用项目经理风控基金超出可使用风控基金总额，保存失败！",
			});
			return;
		}
	}
	$("#isSalary").removeAttr("disabled");
	var datas = $("#dataForm").jsonForm();
	Global.Dialog.returnData = datas;
	closeWin();
}
//校验函数
$(function() {
    if ("${map.fromPage}" === "confirmDuty") {
        $("#paidAmount").parent().show()
        $("#designDutyCount").parent().show()
    }
    
	var m_umState='${m_umState}';
	if(m_umState=="V"){
		$("input").attr("readonly",true);
		$("select").attr("disabled",true);
		$("#saveBtn").attr("disabled",true);
	}
	$("#empCode").openComponent_employee({
		showValue:"${map.empcode}",
		showLabel:"${map.empnamechi}",
		callBack:function(data){
			$("#empNamechi").val(data.namechi);
			if(data.number=="${map.designMan}"){
				$("#leftDesignRiskFund").parent().show();
				$("#leftPrjManRiskFund").parent().hide();
				$("#discBalance").parent().show();
                $("#totalDiscBalance").parent().show();
			}else{
				$("#leftDesignRiskFund").parent().hide();
				$("#leftPrjManRiskFund").parent().show();
				$("#discBalance").parent().hide();
                $("#totalDiscBalance").parent().hide();
			}
			var riskFund= $("#riskFund").val();
			if($("#faultType").val()=="1" && $("#empCode").val()=="${map.designMan}"){
				var sumDesignFund=parseFloat("${map.riskfund}"==""?0:"${map.riskfund}")+parseFloat("${map.leftDesignRiskFund}");
				$("#leftDesignRiskFund").val(sumDesignFund-riskFund);
				$("#totalDiscBalance").val(parseFloat("${map.totalDiscBalance}") + parseFloat("${map.riskfund}" || 0) - (parseFloat(riskFund) || 0))
			}else{
				var sumPrjManFund=parseFloat("${map.riskfund}"==""?0:"${map.riskfund}")+parseFloat("${map.leftPrjManRiskFund}");
				$("#leftPrjManRiskFund").val(sumPrjManFund-riskFund);
				$("#totalDiscBalance").val(parseFloat("${map.totalDiscBalance}") + parseFloat("${map.riskfund}" || 0))
			}
		}
	});
	$("#workerCode").openComponent_worker({
		showValue:"${map.workercode}",
		showLabel:"${map.workernamechi}",
		callBack:function(data){
			$("#workerNamechi").val(data.NameChi);
			$.ajax({					  
				url:"${ctx}/admin/fixDuty/getConfirmAmount",
				type:'post',
				data:{custCode:"${custCode}",workerCode:data.Code},
				dataType:'json',
				cache:false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": '出错~'});
				},
				success:function(obj){
					if (obj){ 
						$("#confirmAmount").val(obj);
					}
				}
			});
		}
	});
	$("#supplCode").openComponent_supplier({
		showValue:"${map.supplcode}",
		showLabel:"${map.suppldescr}",
		callBack:function(data){
			$("#supplDescr").val(data.Descr);
		}
	});
	if("${m_umState}" == "A"){
		setComponentData($("#empCode"), {
			preStr: "openComponent_employee_",
			showValue:"${map.projectMan}",
			showLabel:"${map.projectManDescr}"
		});
		$("#empNamechi").val("${map.projectManDescr}");
	}
	if("${map.isAddDesign}"=="1"){
		$("#isSalary").val("0").attr("disabled",true);
		$("#isSalaryDescr").val("否");
	}
	changeType();
	//修改使用风控基金实时更新风控余额
	// 同时更新总剩余优惠额度
	$('#riskFund').bind('input propertychange', function() {  
		var riskFund= $("#riskFund").val();
		if($("#faultType").val()=="1" && $("#empCode").val()=="${map.designMan}"){
			var sumDesignFund=parseFloat("${map.riskfund}"==""?0:"${map.riskfund}")+parseFloat("${map.leftDesignRiskFund}");
			$("#leftDesignRiskFund").val(sumDesignFund-riskFund);
			$("#totalDiscBalance").val(parseFloat("${map.totalDiscBalance}") + parseFloat("${map.riskfund}" || 0) - (parseFloat(riskFund) || 0))
		}else{
			var sumPrjManFund=parseFloat("${map.riskfund}"==""?0:"${map.riskfund}")+parseFloat("${map.leftPrjManRiskFund}");
			$("#leftPrjManRiskFund").val(sumPrjManFund-riskFund);
		}
	});
});
function changeType(){
	var faultType=$("#faultType").val();
	if(faultType=="1"){
		$("#empCode").parent().show();
		$("#isSalary").parent().show();
		$("#riskFund").val("${m_umState}"=="A"?0:"${map.riskfund}");
		$("#riskFund").parent().show();
		$("#supplCode").parent().hide();
		$("#workerCode").parent().hide();
		$("#openComponent_supplier_supplCode").val("");
		$("#openComponent_worker_workerCode").val("");
		$("#supplCode").val("");
		$("#workerCode").val("");
		$("#workerNamechi").val("");
		$("#supplDescr").val("");
		if("${m_umState}" == "A"){
		setComponentData($("#empCode"), {
				preStr: "openComponent_employee_",
				showValue:"${map.projectMan}",
				showLabel:"${map.projectManDescr}"
			});
			$("#empNamechi").val("${map.projectManDescr}");
		}
		if("${map.isAddDesign}"=="1"){
			$("#isSalary").val("0").attr("disabled",true);
			$("#isSalaryDescr").val("否");
		}
	}else if(faultType=="2"){
		$("#empCode").parent().hide();
		$("#isSalary").parent().hide();
		$("#isSalary").val("");
		$("#isSalaryDescr").val("");
		$("#supplCode").parent().hide();
		$("#workerCode").parent().show();
		$("#openComponent_supplier_supplCode").val("");
		$("#openComponent_employee_empCode").val("");
		$("#supplCode").val("");
		$("#empCode").val("");
		$("#empNamechi").val("");
		$("#supplDescr").val("");
		$("#riskFund").parent().hide();
		$("#riskFund").val(0);
		if("${map.appManType}" == "1"){
			setComponentData($("#workerCode"), {
				preStr: "openComponent_worker_",
				showValue:"${map.appWorkerCode}",
				showLabel:"${map.appManDescr}"
			});
			if("${map.workercode}" == ""){
				$.ajax({					  
					url:"${ctx}/admin/fixDuty/getConfirmAmount",
					type:'post',
					data:{custCode:"${custCode}",workerCode:"${map.appWorkerCode}"},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '出错~'});
					},
					success:function(obj){
						if (obj){ 
							$("#confirmAmount").val(obj);
						}
					}
				});
				
			}
			$("#workerNamechi").val("${map.appManDescr}");
		}
	}else if(faultType=="4"){
		$("#empCode").parent().hide();
		$("#isSalary").parent().hide();
		$("#isSalary").val("");
		$("#isSalaryDescr").val("");
		$("#supplCode").parent().show();
		$("#workerCode").parent().hide();
		$("#openComponent_worker_workerCode").val("");
		$("#openComponent_employee_empCode").val("");
		$("#workerCode").val("");
		$("#empCode").val("");
		$("#empNamechi").val("");
		$("#workerNamechi").val("");
		$("#riskFund").parent().hide();
		$("#riskFund").val(0);
	} else if(faultType=="3"){
		$("#empCode").parent().hide();
		$("#isSalary").parent().hide();
		$("#isSalary").val("");
		$("#isSalaryDescr").val("");
		$("#workerCode").parent().hide();
		$("#openComponent_worker_workerCode").val("");
		$("#openComponent_employee_empCode").val("");
		$("#workerCode").val("");
		$("#empCode").val("");
		$("#supplCode").parent().hide();
		$("#openComponent_supplier_supplCode").val("");
		$("#empNamechi").val("");
		$("#workerNamechi").val("");
		$("#supplDescr").val("");
		$("#riskFund").parent().show();
		$("#riskFund").val("${m_umState}"=="A"?0:"${map.riskfund}");
	}else{
		$("#empCode").parent().hide();
		$("#isSalary").parent().hide();
		$("#isSalary").val("");
		$("#isSalaryDescr").val("");
		$("#workerCode").parent().hide();
		$("#openComponent_worker_workerCode").val("");
		$("#openComponent_employee_empCode").val("");
		$("#workerCode").val("");
		$("#empCode").val("");
		$("#supplCode").parent().hide();
		$("#openComponent_supplier_supplCode").val("");
		$("#empNamechi").val("");
		$("#workerNamechi").val("");
		$("#supplDescr").val("");
		$("#riskFund").parent().hide();
		$("#riskFund").val(0);
	}
	if(faultType=="1" && $("#empCode").val()=="${map.designMan}"){
		$("#leftDesignRiskFund").parent().show();
		$("#leftPrjManRiskFund").parent().hide();
		$("#discBalance").parent().show();
		$("#totalDiscBalance").parent().show();
	}else{
		$("#leftDesignRiskFund").parent().hide();
		$("#leftPrjManRiskFund").parent().show();
		$("#discBalance").parent().hide();
		$("#totalDiscBalance").parent().hide();
	}
}

function viewDesignDuties() {
    Global.Dialog.showDialog("viewDesignDuties",{
        title:"工人定责管理--查看历史设计定责单",
        postData: {custCode: "${custCode}"},
        url:"${ctx}/admin/fixDuty/goDutyHistory",
        height: 650,
        width:1300
    })
}

</script>
<style type="text/css">

</style>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system"
						onclick="save()">保存</button>
					<button type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" id="confirmAmount" name="confirmAmount" value="${confirmAmount}">
					<input type="hidden" id="faultTypeDescr" name="faultTypeDescr"
						value="${m_umState=='A'?'员工（项目经理）':map.faulttypedescr }">
					<input type="hidden" id="pk" name="pk"
						value="${map.pk }">
					<input type="hidden" id="isSalaryDescr" name="isSalaryDescr"
						value="${map.issalarydescr }">
						<input type="hidden" id="supplDescr" name="supplDescr"
						value="${map.suppldescr }">
						<input type="hidden" id="empNamechi" name="empNamechi"
						value="${map.empnamechi }">
						<input type="hidden" id="workerNamechi" name="workerNamechi"
						value="${map.workernamechi }">
						<input type="hidden" id="designMan" name="designMan"
						value="${map.designMan }">
					<ul class="ul-form">
						<li><label>定责申请编号</label> <input type="text" id="no"
							name="no" style="width:160px;" value="${no==''?'保存时生成':no}" readonly/>
						</li>
						<li><label>金额</label> <input type="number" id="amount"
							name="amount" value="${m_umState=='A'?0:map.amount }"/>
						</li>
						<li><label>责任人类型</label> <house:xtdm id="faultType" 
								dictCode="FAULTTYPE" unShowValue="${map.isAddDesign=='1'?'2,4':''}" onchange="changeType();getDescrByCode('faultType','faultTypeDescr')" value="${m_umState=='A'?'1':map.faulttype }"></house:xtdm>
						</li>
						<li><label>员工</label> <input type="text" id="empCode"
							name="empCode" />
						</li>
						<li><label>正常工资</label> <house:xtdm id="isSalary"
								dictCode="YESNO" value="${map.issalary }" onchange="getDescrByCode('isSalary','isSalaryDescr')"></house:xtdm>
						</li>
						<li hidden><label>工人</label> <input type="text"
							id="workerCode" name="workerCode" />
						</li>
						<li hidden><label>供应商</label> <input type="text"
							id="supplCode" name="supplCode" />
						</li>
						<li hidden><label>使用风控基金</label> <input type="number" id="riskFund"
							name="riskFund" value="${m_umState=='A'?0:map.riskfund }"/>
						</li>
						<li hidden><label>前端风险金余额</label> <input type="number" id="leftDesignRiskFund"
							name="leftDesignRiskFund" value="${map.leftDesignRiskFund }" readonly/>
						</li>
						<li hidden><label>工程风险金余额</label> <input type="number" id="leftPrjManRiskFund"
							name="leftPrjManRiskFund" value="${map.leftPrjManRiskFund }" readonly/>
						</li>
						<li hidden>
						    <label>剩余优惠额度</label>
						    <input type="number" id="discBalance" name="discBalance" value="${map.discBalance}" readonly/>
						</li>
						<li hidden>
						    <label>总优惠余额</label>
						    <input type="number" id="totalDiscBalance" name="totalDiscBalance" value="${map.totalDiscBalance}" readonly/>
						</li>
						<li hidden>
						    <label>已支付金额</label>
						    <input type="number" id="paidAmount" name="paidAmount" value="${map.paidamount}"/>
                        </li>
						<li hidden>
						    <label>设计定责单数</label>
						    <input type="number" style="width:110px;" id="designDutyCount" name="designDutyCount" value="${map.designDutyCount}" readonly/>
						    <button type="button" class="btn btn-system" style="width:40px; height: 22px;
                                        font-size: 5px;text-align: center; float:right;" onclick="viewDesignDuties()" >
                                <span>查看</span>
                            </button>
                        </li>
						<li>
							<label class="control-textarea">备注</label>
							<textarea id="remark" name="remark" style="height:75px;">${map.remark }</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
