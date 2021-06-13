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
	<title>客户投诉管理添加查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/custProblem/promRsn","promType1","promRsn");
		Global.LinkSelect.initSelect("${ctx}/admin/custProblem/promType","promType1","promType2");		Global.LinkSelect.setSelect({firstSelect:'promType1',
									firstValue:"${map.promtype1[0]}",
									secondSelect:'promType2',
									secondValue:"",
									});
		$("#promType2").val("${map.promtype2[0]}");
		$("#promRsn").val("${map.promrsn[0]}");
		$("#rcvDate").val("${map.rcvdate[0]}");
		$("#planDealDate").val("${map.plandealdate[0]}");
		$("#dealDate").val("${map.dealdate[0]}");
		$("#infoDate").val("${map.infodate[0]}");
		$("#crtDate").val("${map.crtdate[0]}");
		
		$("#dealCZY").openComponent_employee({showValue:"${map.dealczycode[0]}",showLabel:"${map.dealczydescr[0]}"});	
		$("#supplCode").openComponent_supplier({showValue:"${map.supplcode[0]}",showLabel:"${map.suppldescr[0]}"});	
		
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator('validate');
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			
			var selectRows = [];
			var datas=$("#dataForm").jsonForm();
			if(""!=$.trim($("#promType1").val())){
				datas.promType1Descr=$("#promType1 option:selected").text().split(" ")[1];
			}
			if(""!=$.trim($("#promType2").val())){
				datas.promType2Descr=$("#promType2 option:selected").text().split(" ")[1];
			} 
			if(""!=$.trim($("#promRsn").val())){
				datas.promRsnDescr=$("#promRsn option:selected").text();
			} 
			if(""!=$.trim($("#status").val())){
				datas.statusDescr=$("#status option:selected").text();
			} 
			datas.supplDescr=$("#openComponent_supplier_supplCode").val().split("|")[1];

			datas.dealCZYDescr=$("#openComponent_employee_dealCZY").val().split("|")[1];
		 	selectRows.push(datas);
			Global.Dialog.returnData = selectRows;
			closeWin();
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
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>问题分类	</label>
									<select type="text" id="promType1" name="promType1" style="width:160px;" value="${map.promtype1[0]}"></select>
								</li>
								<li class="form-validate">
									<label>接收人</label>
									<input type="text" id="dealCZY" name="dealCZY" style="width:160px;"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>供应商</label>
									<input type="text" id="supplCode" name="supplCode"/>
								</li>
								<li class="form-validate">
									<label>接收时间</label>
									<input type="text" id="rcvDate" name="rcvDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>材料类型</label>
									<select type="text" id="promType2" name="promType2" style="width:160px;"></select>
								</li>
								<li class="form-validate">
									<label>计划处理时间</label>
									<input type="text" id="planDealDate" name="planDealDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>问题原因</label>
									<select type="text" id="promRsn" name="promRsn" style="width:160px;"></select>				
								</li>
								<li class="form-validate">
									<label>实际处理时间</label>
									<input type="text" id="dealDate" name="dealDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>创建时间</label>
									<input type="text" id="crtDate"name="crtDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true"/>
								</li>
								<li>
									<label>是否处理</label>
									<house:xtdm id="status" dictCode="PROMSTATUS"  style="width:160px;" value="${map.status[0]}"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>通知时间</label>
									<input type="text" id="infoDate" name="infoDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
							</div>
							<div class="validate-group row" >	
								<li>
									<label class="control-textarea">处理结果</label>
									<textarea id="dealRemarks" name="dealRemarks" rows="2">${map.dealremarks[0]}</textarea>
	  							</li>
	  						</div>
	  						<div class="validate-group row" >	
								<li>
									<label class="control-textarea">售后反馈内容</label>
									<textarea id="feedBackRemark" name="feedBackRemark" rows="2">${map.feedbackremark[0]}</textarea>
	  							</li>
	  						</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
