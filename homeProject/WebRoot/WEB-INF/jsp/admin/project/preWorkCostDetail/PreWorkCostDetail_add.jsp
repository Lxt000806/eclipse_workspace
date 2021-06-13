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
	<style type="text/css">
		.row{
			margin: 0px;
		}
		.col-xs-4{
			padding: 0px;
		}
		.col-xs-6{
			padding: 0px;
		}
		.col-xs-8{
			padding: 0px;
		}
		.col-xs-12{
			padding: 0px;
		}
		.form-search .ul-form li .control-textarea +textarea{
			width: 475px;
		}
	</style>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**材料类型1*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/preWorkCostDetail/getWorkTypeByAuthorityForPrj","workType1","workType2");
	var dataSet = {
		firstSelect:"workType1",
		secondSelect:"workType2",
	};
	Global.LinkSelect.setSelect(dataSet);	
	
	$("#workerCode").openComponent_worker({
		callBack : function(data) {
			if(data.cardId){
				$("#cardId").val(data["cardId"]);
				$("#cardId2").val(data["cardId2"]);
				$("#actName").val(data["nameChi"]);
			} else {
				$("#cardId").val(data["CardID"]);
				$("#cardId2").val(data["CardId2"]);
				$("#actName").val(data["NameChi"]);
			}
		},
		condition:{
			appType:"2",
			wortType1:$("#workType1").val()
		}
	});
	$("#custCode").openComponent_customer({
		callBack:function(data){
			resetQuotaSalary();
			getAppAmount();
		}
	});
	$("#quotaSalaryBtn").on("click",function () {
		Global.Dialog.showDialog("quotaSalaryDetail",{
			title:"定额工资—明细查看",
			url:"${ctx}/admin/workCostDetail/goDeDetail",
			postData:$("#dataForm").jsonForm(),		
			height: 540,
			width:870,
		});
	});
});

function save(){
	var workerCode=$("#workerCode").val();
	var custCode=$("#custCode").val();
	var workType2=$("#workType2").val();
	var appAmount=$("#appAmount").val();
	var appedAmount=$("#appedAmount").val();
	var quotaSalary=$("#quotaSalary").val();
	
	var result=isExistsWt2();
	if(custCode==""){
		art.dialog({
			content: "客户编号不能为空"
		});
		return;
	}
	if(workerCode==""){
		art.dialog({
			content: "工人编号不能为空"
		});
		return;
	}
	if(workType2==""){
		art.dialog({
			content: "工种分类2不能为空"
		});
		return;
	}
	if(appAmount==""){
		art.dialog({
			content: "申请金额不能为空"
		});
		return;
	}
	
	if(parseFloat(appAmount)+parseFloat(appedAmount)>parseFloat(quotaSalary)){
		art.dialog({
			content: "申请金额(含已申请)不能大于定额工资"
		});
		return;
	}
	if(result=="1"){
		art.dialog({
			 content:"该客户存在该工种分类2的申请，是否确认再次申请？",
			 lock: true,
			 ok: function () {
			 	 doSave();
			 },
			 cancel: function () {
			 	return true;
			 }
		});	
	}else{
		doSave();
	}
}

function doSave(){
	var datas = $("#dataForm").jsonForm();
	$.ajax({
		url:"${ctx}/admin/preWorkCostDetail/doSave",
		type: "post",
		data:datas,
		dataType: "json",
		async:false,
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
			if(obj.rs){
				art.dialog({
					content:obj.msg,
					time:1000,
					beforeunload:function(){
						closeWin();
					}
				});
			}else{
				art.dialog({
					content:obj.msg,
				});
			}
	    }
	 });  
}

function isExistsWt2(){
	var result="0";
	$.ajax({
		url:"${ctx}/admin/preWorkCostDetail/isExistsWt2",
		type:"post",
		data:{custCode:$("#custCode").val(),workType2:$("#workType2").val()},
		dataType:"json",
		cache:false,
		async:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
		},
		success:function(obj){
			result=obj;
		}
	});
	return result;
}

function resetQuotaSalary(){
	$.ajax({
		url:"${ctx}/admin/preWorkCostDetail/getQuotaSalary",
		type:"post",
		data:{custCode:$("#custCode").val(),workType2:$("#workType2").val()},
		dataType:"json",
		cache:false,
		async:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
		},
		success:function(obj){
			$("#quotaSalary").val(obj);
		}
	});
}

function getAppAmount(){
	$.ajax({
		url:"${ctx}/admin/preWorkCostDetail/getAppAmount",
		type:"post",
		data:{custCode:$("#custCode").val(),workType2:$("#workType2").val()},
		dataType:"json",
		cache:false,
		async:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
		},
		success:function(obj){
			$("#appedAmount").val(obj);
		}
	});
}

function changeWt1(){
	$("#workerCode").openComponent_worker({
		callBack : function(data) {
			$("#cardId").val(data["CardID"]);
			$("#cardId2").val(data["CardId2"]);
			$("#actName").val(data["NameChi"]);
		},
		condition:{
			appType:"2",
			workType1:$("#workType1").val(),
			custCode:$("#custCode").val()
		}
	});
}
</script>
</head>
    
<body>
	<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      	  <button type="button" class="btn btn-system "   onclick="save()">保存</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="status" id="status" value="2"/>
					<input type="hidden" name="actName" id="actName" />
					<input type="hidden" name="appedAmount" id="appedAmount" />
				<ul class="ul-form">
					<div class="row">
						<li>	
							<label >是否预扣</label>
								<house:xtdm  id="isWithHold" dictCode="YESNO" value="0" disabled="true"></house:xtdm>								
							</label>
						</li>
						<li>	
							<label >是否工人申请</label>
								<house:xtdm  id="isWorkApp" dictCode="YESNO" value="0" disabled="true"></house:xtdm>
							</label>
						</li>
					</div>
					<div class="row">
						<li>
							<label >工资类型</label>
								<house:dict id="salaryType" dictCode="" sql="select Code,rtrim(Code)+''+Descr Descr from tSalaryType order by Code " 
								sqlValueKey="Code" sqlLableKey="Descr"  value="01"  disabled="true"></house:dict>
							</label>
						</li>
						<li>	
							<label >是否自动确认</label>
								<house:xtdm  id="isAutoConfirm" dictCode="YESNO"  value="1" disabled="true"></house:xtdm>								
							</label>
						</li>
					</div>
					<div class="row">
						<li>
							<label >客户编号</label>
								<input type="text" id="custCode" name="custCode"  />
							</label>
						</li>
						<li>
							<label>工种分类1</label> 
							<select id="workType1" name="workType1" onchange="changeWt1()"></select>
						</li>
					</div>
					<div class="row">
						<li>
							<label >工人编号</label>
								<input type="text" id="workerCode" name="workerCode"  />
							</label>
						</li>
						<li>
							<label>工种分类2</label> 
							<select id="workType2" name="workType2" onchange="resetQuotaSalary();getAppAmount()"></select>
						</li>
					</div>
					<div class="row">
						<li>	
							<label >卡号</label>
								<input type="text" id="cardId" name="cardId" readonly="true" />
							</label>												
						</li>
						<li>	
							<label >卡号2</label>
								<input type="text" id="cardId2" name="cardId2" readonly="true" />
							</label>												
						</li>
					</div>
					<div class="row">
						<li>
							<label >申请金额</label>
								<input type="number" id="appAmount" name="appAmount" />
							</label>																			
						</li>
						<li>
							<label>定额工资</label>
							<div class="input-group">
								<input type="text" class="form-control" id="quotaSalary" name="quotaSalary" style="width: 121px;" readonly="readonly">
								<button type="button" class="btn btn-system" id="quotaSalaryBtn" style="font-size: 12px;width: 40px;" >查看</button>
							</div>
						</li>
					</div>
					<div class="row">
						<li class="form-validate"><label class="control-textarea">请款说明</label>
							<textarea id="remarks" name="remarks" style="overflow-y:scroll; overflow-x:hidden; height:75px; " /></textarea>
						</li>
					</div>
					</div>
				</div>
			</form>
		</div>				
	</div>
</body>
</html>


