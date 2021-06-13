<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
 function setAnalysisData(data){
	$("#ARealPaySalary").val(data.ARealPaySalary);
	$("#BRealPaySalary").val(data.BRealPaySalary);
	$("#AbaseSalary").val(data.AbaseSalary);
	$("#BbaseSalary").val(data.BbaseSalary);
	$("#ChgBaseSalary").val(data.ChgBaseSalary);
	$("#RealPaySalaryPer").val(myRound(data.RealPaySalaryPer*100,1)+"%");
	$("#chgRealPaySalary").val(data.chgRealPaySalary);
	$("#baseSalaryPer").val(myRound(data.baseSalaryPer*100,1)+"%");
	$("#AUnPaidSalary").val(data.AUnPaidSalary);
	$("#BUnPaidSalary").val(data.BUnPaidSalary);
	$("#BEmpNum").val(data.BEmpNum);
	$("#AEmpNum").val(data.AEmpNum);
	$("#chgempnum").val(data.chgempnum);
	$("#AJoinNum").val(data.AJoinNum);
	$("#AJoinBaseSalary").val(data.AJoinBaseSalary);
	$("#AJoinRealPaySalary").val(data.AJoinRealPaySalary);
	$("#ALeaveNum").val(data.ALeaveNum);
	$("#ALeaveBaseSalary").val(data.ALeaveBaseSalary);
	$("#ALeaveRealPaySalary").val(data.ALeaveRealPaySalary);
};
 </script>
<div class="body-box-list" style="margin-top: 0px;border:1px solid #ddd;">
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<div class="row">
						<li>
							<label>本月实发</label>
							<input type="text" id="ARealPaySalary" name="ARealPaySalary" 
								style="width:160px;" readonly/>
						</li>
						<li>
							<label>对比月实发</label>
							<input type="text" id="BRealPaySalary" name="BRealPaySalary" 
							style="width:160px;" readonly/>
						</li>
					</div>
					<div class="row">
						<li>
							<label>实发变动</label>
							<input type="text" id="chgRealPaySalary" name="chgRealPaySalary" 
							style="width:160px;" readonly/>
						</li>
						<li>
							<label>实发变动百分比</label>
							<input type="text" id="RealPaySalaryPer" name="RealPaySalaryPer" 
							style="width:160px;" readonly/>
						</li>
					</div>
					<div class="row">
						<li>
							<label>本月应发</label>
							<input type="text" id="AbaseSalary" name="AbaseSalary" 
							style="width:160px;" readonly/>
						</li>
						<li>
							<label>对比月应发</label>
							<input type="text" id="BbaseSalary" name="BbaseSalary" 
							style="width:160px;" readonly/>
						</li>
					</div>
					<div class="row">
						<li>
							<label>应发变动</label>
							<input type="text" id="ChgBaseSalary" name="ChgBaseSalary" 
							style="width:160px;" readonly/>
						</li>
						<li>
							<label>应发变动比例</label>
							<input type="text" id="baseSalaryPer" name="baseSalaryPer" 
							style="width:160px;" readonly/>
						</li>
					</div>
					<div class="row">
						<li>
							<label>本月未付</label>
							<input type="text" id="AUnPaidSalary" name="AUnPaidSalary" 
							style="width:160px;" readonly/>
						</li>
						<li>
							<label style="">对比月未付</label>
							<input type="text" id="BUnPaidSalary" name="BUnPaidSalary" 
							style="width:160px;" readonly/>
						</li>
					</div>
					<div class="row">
						<li>
							<label>本月人数</label>
							<input type="text" id="AEmpNum" name="AEmpNum" 
							style="width:160px;" readonly/>
						</li>
						<li>
							<label>对比月人数</label>
							<input type="text" id="BEmpNum" name="BEmpNum" 
							style="width:160px;" readonly/>
						</li>
					</div>
					<div class="row">
						<li>
							<label>人员变动</label>
							<input type="text" id="chgempnum" name="chgempnum" 
							style="width:160px;" readonly/>
						</li>
					</div>
					<div class="row">
						<li>
							<label>本月入职人数</label>
							<input type="text" id="AJoinNum" name="AJoinNum" 
							style="width:160px;" readonly/>
						</li>
						<li>
							<label>本月入职应发</label>
							<input type="text" id="AJoinBaseSalary" name="AJoinBaseSalary" 
							style="width:160px;" readonly/>
						</li>
					</div>
					<div class="row">
						<li>
							<label>本月入职实发</label>
							<input type="text" id="AJoinRealPaySalary" name="AJoinRealPaySalary" 
							style="width:160px;" readonly/>
						</li>
					</div>
					<div class="row">
						<li>
							<label>本月离职人数</label>
							<input type="text" id="ALeaveNum" name="ALeaveNum" 
							style="width:160px;" readonly/>
						</li>
						<li>
							<label>本月离职应发</label>
							<input type="text" id="ALeaveBaseSalary" name="ALeaveBaseSalary" 
							style="width:160px;" readonly/>
						</li>
					</div>
					<div class="row">
						<li>
							<label>本月离职实发</label>
							<input type="text" id="ALeaveRealPaySalary" name="ALeaveRealPaySalary" 
							style="width:160px;" readonly/>
						</li>
					</div>
				</ul>
			</form>
		</div>
</div>
