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
	<title>工地问题查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function(){
		$("#custCode").openComponent_customer({showValue:"${prjProblem.custCode}",showLabel:"${prjProblem.custDescr}",readonly:true});	
		$("#appCZY").openComponent_employee({showValue:"${prjProblem.appCZY}",showLabel:"${appCzy.nameChi}",readonly:true});	
		$("#confirmCZY").openComponent_employee({showValue:"${prjProblem.confirmCZY}",showLabel:"${confirmCzy.nameChi}",readonly:true});	
		$("#feedbackCZY").openComponent_employee({showValue:"${prjProblem.feedbackCZY}",showLabel:"${feedbackCzy.nameChi}",readonly:true} );		
		$("#dealCZY").openComponent_employee({showValue:"${prjProblem.dealCZY}",showLabel:"${dealCzy.nameChi}",} );	
		$("#cancelCZY").openComponent_employee({showValue:"${prjProblem.cancelCZY}",showLabel:"${cancelCzy.nameChi}",readonly:true} );	
		$("#fixDutyMan").openComponent_employee({showValue:"${prjProblem.fixDutyMan}",showLabel:"${fixDutyMan.nameChi}",readonly:true} );	
		
		$("#picNumBtn").on("click",function(){
			if($("#picNum").val()=="0"){
				art.dialog({
					content:"项目经理未上传图片",
				});
				return;
			}
			Global.Dialog.showDialog("viewPic", {
				title : "查看图片",
				url : "${ctx}/admin/prjProblem/goViewPic",
				postData : {
					'no' : $("#no").val()
				},
				height : 650,
				width : 750,
			});
		});
	});
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="page_form" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>问题单号</label>
									<input type="text" id="no" name="no" style="width:160px;" value="${prjProblem.no }" readonly="readonly"/>
								</li>
								<li>
									<label>图片数</label>
									<div class="input-group">
										<input type="text" class="form-control" id="picNum" style="width: 121px;" value="${picNum}" readonly="readonly">
										<button type="button" class="btn btn-system" id="picNumBtn" style="font-size: 12px;width: 40px;" >查看</button>
									</div>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${prjProblem.custCode }" readonly="readonly"/>
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${prjProblem.address }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>问题性质</label>
									<house:xtdm id="promPropCode" dictCode="PRJPROMPROP"  value="${prjProblem.promPropCode }" disabled="true"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label>状态</label>
									<house:xtdm id="status" dictCode="PRJPROMSTATUS" value="${prjProblem.status }" disabled="true"></house:xtdm>                     
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>责任部门</label>
									<house:dict id="promDeptCode" dictCode="" 
										sql=" select code,code+' '+descr descr from tPrjPromDept where expired='F' " 
										sqlValueKey="Code" sqlLableKey="Descr" value="${prjProblem.promDeptCode }" disabled="true" >
									</house:dict>
								</li>
								<li>
									<label>提报人</label>
									<input type="text" id="appCZY" name="appCZY" style="width:160px;" value="${prjProblem.appCZY }" readonly="readonly"/>
								</li>
							</div>
							<div class="validate-group row">
								<div id="promTypeCode_div">
									
								</div>
								<li>
									<label> 责任分类</label>
									<house:dict id="promTypeCode" dictCode="" 
										sql=" select code,code+' '+descr descr from tPrjPromType where expired='F' " 
										sqlValueKey="Code" sqlLableKey="Descr"  value="${prjProblem.promTypeCode }" disabled="true" >
									</house:dict>
								</li> 
								<li class="form-validate">
									<label>提报时间</label>
									<input type="text" id="appDate" name="appDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.appDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>确认人</label>
									<input type="text" id="confirmCZY" name="confirmCZY" value="${prjProblem.confirmCZY }"/>
								</li>
								
								<li class="form-validate">
									<label>反馈人</label>
									<input type="text" id="feedbackCZY" name="feedbackCZY" style="width:160px;" value="${prjProblem.feedbackCZY}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>确认时间</label>
									<input type="text" id="confirmDate" name="confirmDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.confirmDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
								
								<li class="form-validate">
									<label>反馈时间</label>
									<input type="text" id="feedbackDate" name="feedbackDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.feedbackDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label> 处理人</label>
									<input type="text" id="dealCZY" name="dealCZY" value="${prjProblem.dealCZY }"/>
								</li>
								<li class="form-validate">
									<label> 撤销人</label>
									<input type="text" id="cancelCZY" name="cancelCZY" style="width:160px;" value="${prjProblem.cancelCZY}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>处理时间</label>
									<input type="text" id="dealDate" name="dealDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.dealDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li class="form-validate">
									<label>撤销时间</label>
									<input type="text" id="cancelDate" name="cancelDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.cancelDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>定责人</label>
									<input type="text" id="fixDutyMan" name="fixDutyMan" style="width:160px;" value="${prjProblem.fixDutyMan}"/>
								</li>
								<li>
									<label>定责时间</label>
									<input type="text" id="fixDutyDate" name="fixDutyDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.fixDutyDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label> 是否解决</label>
									<house:xtdm id="isDeal" dictCode="YESNO" value="${prjProblem.isDeal }"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label> 预计处理时间</label>
									<input type="text" id="planDealDate" name="planDealDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProblem.planDealDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>	
							<div class="validate-group row">
								<li class="form-validate">
									<label>导致停工</label>
									<house:xtdm id="isBringStop" dictCode="YESNO" value="${prjProblem.isBringStop }"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label>停工天数</label>
									<input type="number" id="stopDays" name="stopDays" style="width:160px;" value="${prjProblem.stopDays }" />
								</li>
							</div>	
							<div class="validate-group row">
								<li>
									<label class="control-textarea">问题描述</label>
									<textarea id="remarks" name="remarks" rows="3" readonly="true">${prjProblem.remarks }</textarea>
		 						</li>
	 						</div>
	 						<div class="validate-group row">
								<li>
									<label class="control-textarea">解决方案</label>
									<textarea id="dealRemarks" name="dealRemarks" rows="3" readonly="true">${prjProblem.dealRemarks }</textarea>
		 						</li>
	 						</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
