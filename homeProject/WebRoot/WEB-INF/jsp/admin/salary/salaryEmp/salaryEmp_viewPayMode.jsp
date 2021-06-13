<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看发放方案</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
//校验函数	
$(function() {
	parent.$("#iframe_${frameName}").attr("height","98%"); //消灭掉无用的滑动条
	changePayMode();
	disabledForm("dataForm");
	Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
	Global.LinkSelect.setSelect({firstSelect:"department1",
							firstValue:"${salaryEmp.department1}",
							secondSelect:"department2",
							secondValue:"${salaryEmp.department2}",
							});
	
});

//修改发放方式触发
function changePayMode(){
	var payMode="${salaryEmp.payMode}";
	if(payMode=="1"){
		var insurLimit=parseFloat("${salaryEmp.insurLimit}");
		if(insurLimit<=0){
			$("#insurLimit").parent().addClass("hidden");
		}else{
			$("#insurLimit").parent().removeClass("hidden");
		}
		$("#salarySettleCmp,#cmpUsageType,#payCmp1,#payCmp2,#payCmp3,#payCmp4").parent().addClass("hidden");
		$("#isTaxable").val("1");
	}else if(payMode=="2"){
		$("#salarySettleCmp,#cmpUsageType,#insurLimit,#payCmp1,#payCmp2,#payCmp3,#payCmp4").parent().addClass("hidden");
		$("#isTaxable").val("0");
	}else if(payMode=="3"){
		$("#salarySettleCmp,#cmpUsageType,#payCmp1,#payCmp2,#payCmp3,#payCmp4").parent().removeClass("hidden");
		$("#insurLimit").parent().addClass("hidden");
		$("#insurLimit").val("").attr("disabled","true");
		$("#isTaxable").val("0");
	}else{
		$("#insurLimit,#salarySettleCmp,#cmpUsageType,#payCmp1,#payCmp2,#payCmp3,#payCmp4").parent().addClass("hidden");
		$("#isTaxable").val("0");
	}
}
</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<input type="hidden" id="bankTypeDescr" name="bankTypeDescr">
					<house:token></house:token>
					<ul class="ul-form">
						<div class="validate-group row ">
							<li >
								<label >姓名</label>
								<input type="text" id="empName" name="empName" value="${salaryEmp.empName}"/>
							</li>
							<li >
								<label >身份证号</label>
								<input type="text" id="idnum" name="idnum" value="${salaryEmp.idnum}"/>
							</li>
						</div>
						<div class="validate-group row ">
							<li>
								<label>一级部门</label>
								<select id="department1" name="department1" value="${salaryEmp.department1}"></select>
							</li>
							<li>
								<label>二级部门</label>
								<select id="department2" name="department2" value="${salaryEmp.department2}"></select>
							</li>
						</div>
						<div class="validate-group row ">
							<li>
								<label>工资</label>
								<input type="text" id="salary" name="salary" value="${salaryEmp.salary}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
							<li>
								<label>基本工资</label>
								<input type="text" id="basicSalary" name="basicSalary" value="${salaryEmp.basicSalary}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
						</div>
						<div class="validate-group row">	
							<li >
								<label>发放方式</label>	
								<house:xtdm id="payMode" dictCode="SALPAYMODE" value="${salaryEmp.payMode }" onchange="changePayMode()"></house:xtdm>
							</li>
							<li >
								<label>是否计税</label>	
								<house:xtdm id="isTaxable" dictCode="YESNO" value="${salaryEmp.isTaxable }" ></house:xtdm>
							</li>	
						</div>
						<div class="validate-group row ">
							<li class=" hidden" >
								<label>保险费用额度</label>
								<input type="text" id="insurLimit" name="insurLimit" value="${salaryEmp.insurLimit}"  onblur="insurLimitBlur()"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
							<li class=" hidden">
								<label >薪酬结算企业</label>
								<house:dict id="salarySettleCmp" dictCode="" sql="select pk,cast(pk as nvarchar(20))+' '+descr descr from tSalarySettleCmp a where a.Expired='F' " 
								 sqlValueKey="pk" sqlLableKey="descr" value="${salaryEmp.salarySettleCmp}" ></house:dict>							
							</li>
							<li class=" hidden">
								<label>用途备注</label>	
								<house:xtdm id="cmpUsageType" dictCode="CMPUSAGETYPE" value="${salaryEmp.cmpUsageType }" ></house:xtdm>
							</li>
						</div>	
						<div class="validate-group row">
							<li class=" hidden">
								<label>出款公司1</label>
								<house:dict id="payCmp1" style="width:115px" dictCode=""
									sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
									sqlLableKey="descr" sqlValueKey="code" value="${salaryEmp.payCmp1}"  />
									<input style="width:40px" type="text" id="weight1" name="weight1" value="${salaryEmp.weight1}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
									<span style="position: absolute;left:285px;width: 30px;top:3px;">%</span>
							</li>
							<li class=" hidden">
								<label>出款公司2</label>
								<house:dict id="payCmp2" style="width:115px" dictCode=""
									sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
									sqlLableKey="descr" sqlValueKey="code" value="${salaryEmp.payCmp2}"  />
									<input style="width:40px" type="text" id="weight2" name="weight2" value="${salaryEmp.weight2}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
									<span style="position: absolute;left:285px;width: 30px;top:3px;">%</span>
							</li>
						</div>
						<div class="validate-group row">
							<li class=" hidden">
								<label>出款公司3</label>
								<house:dict id="payCmp3" style="width:115px" dictCode=""
									sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
									sqlLableKey="descr" sqlValueKey="code" value="${salaryEmp.payCmp3}"  />
									<input style="width:40px" type="text" id="weight3" name="weight3" value="${salaryEmp.weight3}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
									<span style="position: absolute;left:285px;width: 30px;top:3px;">%</span>
							</li>
							<li class=" hidden">
								<label>出款公司4</label>
								<house:dict id="payCmp4" style="width:115px" dictCode=""
									sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
									sqlLableKey="descr" sqlValueKey="code" value="${salaryEmp.payCmp4}"  />
									<input style="width:40px" type="text" id="weight4" name="weight4" value="${salaryEmp.weight4}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
									<span style="position: absolute;left:285px;width: 30px;top:3px;">%</span>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
