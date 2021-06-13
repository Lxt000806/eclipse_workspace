<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>工人工资审批</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">


/**材料类型1*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/preWorkCostDetail/workTypeByAuthority","workType1","workType2");
	var dataSet = {
		firstSelect:"workType1",
		secondSelect:"workType2",
		firstValue:'${pWorkCostDetail.workType1}',
		secondValue:'${pWorkCostDetail.workType2}',
		disabled:"true"				
	};
	Global.LinkSelect.setSelect(dataSet);	

	$("#quotaSalaryBtn").on("click",function() {
		var data = $("#dataForm").jsonForm();
		Global.Dialog.showDialog("quotaSalaryDetail",{
			title:"定额工资—明细查看",
			url:"${ctx}/admin/preWorkCostDetail/goQuotaSalaryDetail",
			postData:data,		
			height:540,
			width:870,
		});
	});
	
	$("#workCostViewBtn").on("click",function() {
		Global.Dialog.showDialog("workCostView",{
			title:"已审核工资—明细查看",
			url:"${ctx}/admin/preWorkCostDetail/goWorkCostView",
			postData:{
				hasBaseCheckItemPlan: "${hasBaseCheckItemPlan}",
				workType2: "${pWorkCostDetail.workType2}",
				workType1: "${pWorkCostDetail.workType1}",
				custCode: "${customer.code}"
			},		
			height:500,
			width:700,
		});
	});
});
</script>
</head>
    
<body>
	<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" class="btn btn-system "  >一级反审核</button>
		      <button type="button" id="Cancel" class="btn btn-system "  >审核取消</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<li>
							<label >客户编号</label>
								<input type="text" id="custCode" name="custCode"   value="${pWorkCostDetail.custCode }"  readonly="true" />
							</label>
					</li>
					<li>
							<label >楼盘</label>
								<input type="text" id="address" name="address"   value="${customer.address }"  readonly="true" />
							</label>												
					</li>
					<li>
							<label >档案编号</label>
								<input type="text" id="documentNo" name="documentNo"   value="${customer.documentNo }"  readonly="true" />
							</label>
					</li>
					<li>
							<label >申请人</label>
								<input type="text" id="applyMan" name="applyMan" style="width:79px;"  value="${pWorkCostDetail.applyMan }"  readonly="true" />
								<input type="text" id="applyMan" name="applyMan" style="width:79px;"  value="${pWorkCostDetail.applyManDescr }"  readonly="true" />
							</label>												
					</li>
					<li>
							<label >工资类型</label>
								<house:dict id="salaryType" dictCode="" sql="select Code,rtrim(Code)+''+Descr Descr from tSalaryType order by Code " 
								sqlValueKey="Code" sqlLableKey="Descr"  value="${pWorkCostDetail.salaryType }"  disabled="true"></house:dict>
							</label>
					</li>
					<li>
							<label >工种分类1</label>
								<select id="workType1" name="workType1"   disabled="true"></select>
							</label>	
					</li>
					<li>
							<label >工种分类2</label>
								<select id="workType2" name="workType2"   disabled="true"></select>
							</label>
					</li>
					<li>
							<label >申请金额</label>
								<input type="text" id="appAmount" name="appAmount"   value="${pWorkCostDetail.appAmount }"  readonly="true" />
							</label>																			
					</li>
					<li>
							<label for="workCost" style="font-weight:bold">已审核工资</label>
							<div class="input-group">
								<input type="text" class="form-control" id="workCost" name="workCost"   value="${pWorkCostDetail.workCost }"  readonly="true" style="width: 121px;"/>
								<button type="button" class="btn btn-system" id="workCostViewBtn" style="font-size: 12px;width: 40px;" >查看</button>
							</div>
					</li>
					<li>
							<label >审核金额</label>
								<input type="text" id="cfmAmount" name="cfmAmount"   value="${pWorkCostDetail.cfmAmount }" readonly="true"  />
							</label>												
					</li>
					<li>	
							<label >是否工人申请</label>
								<house:xtdm  id="isWorkApp" dictCode="YESNO"    value="${pWorkCostDetail.isWorkApp}" disabled="true"></house:xtdm>
							</label>
					</li>
					<li>
							<label >工人申请金额</label>
								<input type="text" id="workAppAmount" name="workAppAmount"   value="${pWorkCostDetail.workAppAmount }"  readonly="true" />
							</label>												
					</li>
					<li>	
							<label >合同总价</label>
								<input type="text" id="workCon" name="workCon"   value="${pWorkCostDetail.workCon }"  readonly="true" />
							</label>
					</li>
					<li>	
							<label >卡号</label>
								<input type="text" id="cardId" name="cardId"   value="${pWorkCostDetail.cardId }"  readonly="true" />
							</label>												
					</li>
					<li>
						<label for="">定额工资</label>
						<div class="input-group">
							<input type="text" class="form-control" id="quotaSalary" style="width: 121px;" value="${pWorkCostDetail.quotaSalary}" readonly="readonly">
							<button type="button" class="btn btn-system" id="quotaSalaryBtn" style="font-size: 12px;width: 40px;" >查看</button>
						</div>
					</li>
					<li>	
							<label >户名</label>
								<input type="text" id="actName" name="actName"   value="${pWorkCostDetail.actName }"  readonly="true" />
							</label>
					</li>
					<li>	
						<label>质检安排工人</label>
						<house:xtdm  id="hasCustWork" dictCode="YESNO" value="${pWorkCostDetail.hasCustWork}"></house:xtdm >
					</li>
					<li>	
							<label >状态</label>
								<house:xtdm  id="status" dictCode="PRECOSTSTATUS" value="${pWorkCostDetail.status}"   disabled="true"></house:xtdm >
							</label>												
					</li>
					<li>	
							<label >面积</label>
								<input type="text" id="area" name="area"   value="${customer.area }"  readonly="true" />
							</label>
					</li>
					<li>	
							<label >开工日期</label>
								<input type="text" id="signDate" name="signDate" class="i-date"  
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${customer.signDate}' pattern='yyyy-MM-dd'/>"  readonly="true"/>								
							</label>												
					</li>
					<li>
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${pWorkCostDetail.remarks }</textarea>
					</li>
					<li>	
							<label >是否预扣</label>
								<house:xtdm  id="isWorkApp" dictCode="YESNO"  value="${pWorkCostDetail.isWithHold}" disabled="true"></house:xtdm>								
							</label>
					</li>
					<li>	
							<label >预扣单号</label>
								<input type="text" id="withHoldNo" name="withHoldNo"   value="${pWorkCostDetail.withHoldNo }"  readonly="true" />
							</label>												
					</li>
					<li>	
							<label >预扣金额</label>
								<input type="text" id="yukou" name="yukou"   value="${pWorkCostDetail.yukou }"  readonly="true" />
							</label>
					</li>
					<li>
							<label >领取金额</label>
								<input type="text" id="ret1" name="ret1"   value="${pWorkCostDetail.ret1 }"  readonly="true" />
							</label>												
					</li>
					<li>	
							<label >总发包</label>
								<input type="text" id="allCustCtrl" name="allCustCtrl"   value="${pWorkCostDetail.allCustCtrl }"  readonly="true" />
							</label>
					</li>
					<li>
							<label >工种发包</label>
								<input type="text" id="ctrlCost" name="ctrlCost"   value="${pWorkCostDetail.ctrlCost }"  readonly="true" />
							</label>												
					</li>
					<li>
							<label >总支出</label>
								<input type="text" id="allCustCost" name="allCustCost"   value="${pWorkCostDetail.allCustCost }"  readonly="true" />
							</label>
					</li>
					<li>
							<label >工种支出</label>
								<input type="text" id="custCost" name="custCost"   value="${pWorkCostDetail.custCost }"  readonly="true" />
							</label>												
					</li>
					<li>
							<label >总发包余额</label>
								<input type="text" id="allLeaveCustCost" name="allLeaveCustCost"   value="${pWorkCostDetail.allLeaveCustCost }"  readonly="true" />
							</label>
					</li>
					<li>
							<label >工种余额</label>
							
								<input type="text" id="leaveCustCost" name="leaveCustCost"   value="${pWorkCostDetail.leaveCustCost }"  readonly="true" />
							</label>												
					</li>
					<li>	
							<label >施工项目</label>
								<house:xtdm id="prjItem" dictCode="PRJITEM"  value="${pWorkCostDetail.prjItem }"  disabled="true" ></house:xtdm>
							</label>
					</li>
					<li>	
							<label >人工控制项</label>
								<input type="text" id="ctrlCost1" name="ctrlCost1"   value="${pWorkCostDetail.ctrlCost1 }"  readonly="true" />
							</label>												
					</li>
					<li>	
							<label >验收日期</label>
							<input type="text" id="confirmDate" name="confirmDate" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${pWorkCostDetail.confirmDate}' pattern='yyyy-MM-dd'/>" />							
							</label>
					</li>
					<li>	
							<label >完成时间</label>
							
								<input type="text" id="endDate" name="endDate" class="i-date"  
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${pWorkCostDetail.endDate}' pattern='yyyy-MM-dd'/>" readonly="true"/>								
							</label>												
					</li>
					<li>	
							<label >审批金额</label>
								<input type="text" id="confirmAmount" name="confirmAmount"   value="${pWorkCostDetail.confirmAmount }"  readonly="true" />
							</label>
					</li>
					<li>	
							<label >验收人</label>
								<input type="text" id="confirmCZY" name="confirmCZY" style="width:79px;"  value="${pWorkCostDetail.confirmCZY }"  readonly="true" />
								<input type="text" id="confirmCZY" name="confirmCZY" style="width:79px;"  value="${pWorkCostDetail.confirmCZYDescr }"  readonly="true" />
							</label>												
					</li>
					<li>
						<label class="control-textarea" >备注：</label>
						<textarea id="confirmRemark" name="confirmRemark">${pWorkCostDetail.confirmRemark }</textarea>
					</li>
					<li>	
						<li hidden="true">
							<label >pk</label>
							<td class="td-value" >
								<input type="text" id="pk" name="pk"    value="${pWorkCostDetail.pk }"  readonly="true" />
							</label>
							<label >msg</label>
								<input type="text" id="msg" name="msg"    value="${pWorkCostDetail.msg }"  readonly="true" />
							</label>
							<label >m_s</label>
							
								<input type="text" id="m_s" name="m_s"   value="ZF"  readonly="true" />
							</label>
							
							<label >工人编号</label>
							<input type="text" id="workerCode" name="workerCode"   value="${pWorkCostDetail.workerCode }"  readonly="true" />

							<label for="isConfirmTwo">工资是否二次审核</label>
							<input type="text" id="isConfirmTwo" name="isConfirmTwo" value="${workType2.isConfirmTwo}" readonly="readonly">
						</li>

			</form>
		</div>				
	</div>
<script>
$("#hasCustWork").prop("disabled",true);
$("#saveBtn").on("click",function(){	
	var PrjItem = $.trim($("#prjItem").val());
	var confirmCZY = $.trim($("#confirmCZY").val());
	var isWithHold = $.trim($("#isWithHold").val());
	var msg = $.trim($("#msg").val());
	if ((PrjItem != "") && (confirmCZY =="") && (isWithHold =="0")) {
	art.dialog({content: "该工地需先进行验收操作，才允许审批工资。",width: 200});
		return false;
	}
	if (msg==0) {
	art.dialog({content: "未上传水电定位图到售后，无法审批通过！",width: 200});
		return false;
	}
	if(!$("#dataForm").valid()) {return false;}//表单校验				
	art.dialog({
		content:"是否确认要审核通过该条工人工资申请信息？",
		lock: true,
		width: 200,
		height: 100,
		ok: function () {
	    var param= Global.JqGrid.allToJson("dataTable");
			Global.Form.submit("dataForm","${ctx}/admin/preWorkCostDetail/doZhuLiSave",param,function(ret){				
			if(ret.rs==true){
				art.dialog({
					content:ret.msg,
					time:1000,
					beforeunload:function(){
						closeWin();
					}
				});				
			}else{				
				$("#_form_token_uniq_id").val(ret.token.token);
				art.dialog({
					content:ret.msg,
					width:200
				});
			}
		});	
	    	return true;
		},
		cancel: function () {
			return true;
		}
	});
});

$("#Cancel").on("click",function(){	
	if(!$("#dataForm").valid()) {return false;}//表单校验	
	art.dialog({
		content:"是否确认要审核取消该条工人工资申请信息？",
		lock: true,
		width: 200,
		height: 100,
		ok: function () {
			var param= Global.JqGrid.allToJson("dataTable");	
			Global.Form.submit("dataForm","${ctx}/admin/preWorkCostDetail/doCancel",param,function(ret){				
				if(ret.rs==true){
					art.dialog({
						content:ret.msg,
						time:1000,
						beforeunload:function(){
							closeWin();
						}
					});				
				}else{				
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content:ret.msg,
						width:200
					});
				}
			});
		    return true;
		},
		cancel: function () {
			return true;
		}
	});
});
</script>		
</body>
</html>


