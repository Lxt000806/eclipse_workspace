<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		      	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<ul class="ul-form">
						<div class="row">
							<div class="col-sm-6">
								<li>
									<label>编号</label>
									<input type="text" id="pk" name="pk" style="width:160px;" value="${workerArrange.pk}" readonly="readonly"/>
								</li>
								<li>
									<label>姓名</label>
									<input type="text" id="workerName" name="workerName" style="width:160px;"
										value="${workerArrange.workerName}" readonly="readonly"/>
								</li>
								<li>
									<label>日期</label>
									<input type="text" id="date" name="date" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${workerArrange.date}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
								<li>	
									<label>班次</label>
									<house:dict id="dayType" dictCode="" sql="select CBM, (CBM + ' ' + NOTE) Descr from tXTDM where ID = 'DAYTYPE'"
									 	sqlValueKey="CBM" sqlLableKey="Descr" value="${workerArrange.dayType}" disabled="true"/>
								</li>
								<li>
									<label>预约编号</label>
									<input type="text" id="no" name="no" style="width:160px;" value="${workerArrange.no}" readonly="readonly"/>
								</li>
								<li>
									<label>已预约</label>
									<house:dict id="booked" dictCode="" sql="select CBM, (CBM + ' ' + NOTE) Descr from tXTDM where ID = 'YESNO'"
									 	sqlValueKey="CBM" sqlLableKey="Descr" value="${workerArrange.booked}" disabled="true"/>
								</li>
							</div>
                            <div class="col-sm-6">
								<li>
									<label>工地工人信息主键</label>
									<input type="text" id="custWorkPk" name="custWorkPk" style="width:160px;" value="${workerArrange.custWorkPk}" readonly="readonly"/>
								</li>
                                <li>    
                                    <label>工种分类</label>
                                    <select id="work_type12" name="workType12" disabled="disabled"></select>
                                </li>
								<li>
									<label>预约楼盘</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;" readonly="readonly"/>
								</li>
								<li>
									<label>预约操作员</label>
									<input type="text" id="czybh" name="czybh" style="width:160px;" readonly="readonly"/>
								</li>
								<li>
									<label>预约时间</label>
									<input type="text" id="orderDate" name="orderDate" class="i-date"
										value="<fmt:formatDate value='${workerArrange.orderDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled="true"/>
								</li>
								<li>
									<label>过期</label>
									<input type="checkbox" id="expired_show" name="expired_show" value="${worker.expired}" 
										${workerArrange.expired == "T" ? "checked" : ""} disabled="disabled"/>
								</li>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
<script>
	$(function() {
		Global.LinkSelect.initSelect("${ctx}/admin/worker/workType12","work_type12","work_type12_dept");
		$("#work_type12").val("${workerArrange.workType12}");
		
		$("#czybh").openComponent_employee({showLabel:"${workerArrange.czybh}",showValue:"${workerArrange.operator}"});
		$("#custCode").openComponent_customer({showLabel:"${workerArrange.custCode}",showValue:"${workerArrange.address}"});
	});
</script>
</html>
