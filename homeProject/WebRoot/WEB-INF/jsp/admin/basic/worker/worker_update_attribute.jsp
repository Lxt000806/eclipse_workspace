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
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		      	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="beforeEmpCode" name="beforeEmpCode" value="${worker.empCode}"/>
					<input type="hidden" id="expired" name="expired" value="${worker.expired}"/>
					<ul class="ul-form">
						<div class="row">
							<div class="col-sm-4">
								<li>
									<label><span class="required">*</span>编码</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${worker.code}"
										placeholder="保存自动生成" readonly="readonly"/>
								</li>
								<li>
									<label><span class="required">*</span>姓名</label>
									<input type="text" id="nameChi" name="nameChi" style="width:160px;" value="${worker.nameChi}" readonly="readonly"/>
								</li>
								<li>
									<label><span class="required">*</span>卡号1</label>
									<input type="text" id="cardId" name="cardId" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 ');"
										value="${worker.cardId}" readonly="readonly"/>
								</li>
								<li class="form-validate">
                                    <label><span class="required">*</span>开户行及支行1</label>
                                    <input type="text" id="bank" name="bank" value="${worker.bank}" readonly/>
                                </li>
								<li>
									<label>卡号2</label>
									<input type="text" id="cardId2" name="cardId2" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 ');" 
										value="${worker.cardId2}" readonly="readonly"/>
								</li>
								<li>
                                    <label>开户行及支行2</label>
                                    <input type="text" id="bank2" name="bank2" value="${worker.bank2}" readonly/>
                                </li>
								<li>	
									<label><span class="required">*</span>工种分类</label>
									<select id="workType12" name="workType12" disabled="disabled"></select>
								</li>
								<li>
									<label>介绍人</label>
									<input type="text" id="introduceEmp" name="introduceEmp" style="width:160px;"/>
								</li>
								<li>
									<label><span class="required">*</span>签约类型</label>
									<house:xtdm id="isSign" dictCode="WSIGNTYPE" style="width:160px;" value="${worker.isSign}"/>
								</li>
								<li class="form-validate">
									<label>签约时间</label>
									<input type="text" id="signDate" name="signDate"
										class="i-date" style="width:160px;" onFocus="WdatePicker({onpicked:validateRefresh_signDate(), skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${worker.signDate}' pattern='yyyy-MM-dd'/>" readonly="true"/>
								</li>
								<li>
									<label>状态</label>
									<house:xtdm id="isLeave" dictCode="WORKERSTS" style="width:160px;" onchange="changeStatus()" value="${worker.isLeave}"/>
								</li>
								
								<li class="form-validate">
									<label>项目经理类型</label>
									<house:xtdm id="isSupvr" dictCode="PRJMANTYPE" style="width:160px;" value="${worker.isSupvr}"/>
								</li>
							</div>
							<div class="col-sm-4">
								<li class="form-validate">
									<label>监理星级</label>
									<house:dict id="prjLevel" dictCode="" sql="select Code,Code+' '+Descr PrjLevel from tPrjlevel" 
										sqlValueKey="Code" sqlLableKey="PrjLevel" value="${worker.prjLevel}"/>
								</li>
								<li>
									<label>所属分组</label>
									<select id="workType12Dept" name="workType12Dept" disabled="disabled"></select>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>工人级别</label>
									<house:xtdm id="level" dictCode="WORKERLEVEL" style="width:160px;" value="${worker.level}"/>
								</li>
								<li>
									<label>班组人数</label>
									<input type="text" id="num" name="num" style="width:160px;"
									    onkeyup="value=value.replace(/[^\.\d]/g,'')" value="${worker.num}" readonly/>
								</li>
								<li>
									<label>工作效率比例</label>
									<input type="text" id="efficiency" name="efficiency" style="width:160px;" onkeyup="value=value.replace(/[^\.\d]/g,'')" value="${worker.efficiency}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>是否自动安排</label>
									<house:xtdm id="isAutoArrange" dictCode="YESNO" style="width:160px;" value="${worker.isAutoArrange}"/>
								</li>
								<li>
									<label>归属事业部</label>
									<house:dict id="department1" dictCode="" sql="select Code,Code+' '+Desc1 Department1 from tdepartment1 where DepType='3'" 
										sqlValueKey="Code" sqlLableKey="Department1" value="${worker.department1}"/>
								</li>
								<li>
									<label>所属工程部</label>
									<house:department2 id="department2" dictCode="" depType="3" value="${worker.department2}"/>
								</li>
								<li>	
									<label>一级居住区域</label>
									<select id="liveRegion" name="liveRegion"></select>
								</li>
								<li>
									<label>二级居住区域</label>
									<select id="liveRegion2" name="liveRegion2"></select>
								</li>
								<li>
									<label>归属专盘</label>
									<house:dict id="spcBuilder" dictCode="" sql="select Code,Code+' '+Descr SpcBuilder from tSpcBuilder" 
										sqlValueKey="Code" sqlLableKey="SpcBuilder" value="${worker.spcBuilder}"/>
								</li>
								<li>
									<label>归属区域</label>
									<house:DictMulitSelect id="belongRegion" dictCode="" sql="select Code,Descr Descr1 from tRegion where code <> ''"
										sqlValueKey="Code"  sqlLableKey="Descr1" selectedValue="${worker.belongRegion}"  ></house:DictMulitSelect>
								</li>
							</div>
							<div class="col-sm-4">
								<li class="form-validate">
									<label id="empCodeLabel">员工编号</label>
									<input type="text" id="empCode" name="empCode" style="width:160px;"/>
								</li>
								<li>
									<label>工程大区</label>
									<house:dict id="prjRegionCode" dictCode="" sql="select Code,Code+' '+Descr PrjRegionCode from tPrjRegion" 
										sqlValueKey="Code" sqlLableKey="PrjRegionCode" value="${worker.prjRegionCode}"/>
								</li>
								<li>
									<label>承接工地类型</label>
									<house:xtdmMulit id="rcvPrjType" dictCode="" sql="select NOTE,CBM from tXTDM where ID='RCVPRJTYPE'" 
										sqlValueKey="CBM" sqlLableKey="NOTE" selectedValue="${worker.rcvPrjType}">
									</house:xtdmMulit>
								</li>
								<li>
									<label>交通工具</label>
									<house:xtdm id="vehicle" dictCode="VEHICLE" style="width:160px;" value="${worker.vehicle}"/>
								</li>
								<li>
									<label>住址</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${worker.address}"/>
								</li>
								<li>
									<label>允许材料下单</label>
									<house:xtdm id="allowItemApp" dictCode="YESNO" style="width:160px;" value="${worker.allowItemApp}"/>
								</li>
								<li>
									<label>工人分类</label>
									<house:xtdm id="workerClassify" dictCode="WORKERCLASSIFY" style="width:160px;" value="${worker.workerClassify}"/>
								</li>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2" style="height: 50px;">${worker.remarks}</textarea>
								</li>
								<%-- <li>
									<label>过期</label>
									<input type="checkbox" id="expired_show" name="expired_show" value="${worker.expired}" 
										onclick="checkExpired(this)" ${worker.expired=="T"?"checked":""}/>
								</li> --%>
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
	$("#isSign").attr("disabled",true);
	$("#signDate").attr("disabled",true);
	$("#isLeave").attr("disabled",true);
	
	/* 联动传入数据-开始 */
	Global.LinkSelect.initSelect("${ctx}/admin/worker/workType12","workType12","workType12Dept");
	var	dataSet = {
		firstSelect:"workType12",
		firstValue:"${worker.workType12}",
		secondSelect:"workType12Dept",
		secondValue:"${worker.workType12Dept}"	
	};
	Global.LinkSelect.setSelect(dataSet);
	/* 结束 */							
	Global.LinkSelect.initSelect("${ctx}/admin/worker/region","liveRegion","liveRegion2");
	Global.LinkSelect.setSelect({firstSelect:"liveRegion",
								firstValue:"${worker.liveRegion}",
								secondSelect:"liveRegion2",
								secondValue:"${worker.liveRegion2}"});
	
	/* 显示员工编号、姓名 */
	$("#introduceEmp").openComponent_employee({showValue:"${worker.introduceEmp}",showLabel: "${worker.introduceEmpDescr}",readonly:true});
	$("#empCode").openComponent_employee({showValue:"${worker.empCode}",showLabel: "${worker.empCodeDescr}"});
	
	$("#isSign option[value='']").remove(); /* 删除Select中索引值为""的Option(请选择...) */
	$("#isLeave option[value='']").remove();

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			level: {
				validators: {
					notEmpty: {
						message: "工人级别未选择"
					}
				}
			},
			isAutoArrange: {
				validators: {
					notEmpty: {
						message: "请选择是否自动安排"
					}
				}
			},
		}
	});
	
	/* 开始需要执行的方法 */
	changeSignDate();
	changeType();
	
	var originalData = $("#page_form").serialize();
	
	/* 是否签约改变事件 */
	$("#isSign").change(function(){
		changeSignDate();
	});
	
	$("#closeBut").on("click",function(){
		var changeData = $("#page_form").serialize();
		if (originalData != changeData) {
			art.dialog({
				content: "数据已变动,是否保存？",
				width: 200,
				okValue: "确定",
				ok: function () {
					doSave();
				},
				cancelValue: "取消",
				cancel: function () {
					closeWin();
				}
			});
		} else {
			closeWin();
		}
		
	});
	
	/* 保存 */
	$("#saveBtn").on("click",function(){
		doSave();
	});
	
	
});

// 签约时间校验刷新
function validateRefresh_signDate(){
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("signDate", "NOT_VALIDATED", null)
		.validateField("signDate");
}

function validateRefresh(){
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_employee_empCode", "NOT_VALIDATED", null)/* 重置某一单一验证字段验证规则  */
		.validateField("openComponent_employee_empCode");/* 触发指定字段的验证 */
}

/* 是否签约触发事件 */
function changeSignDate(){
	var isSign =$.trim($("#isSign").val());
	$("#page_form")
	.bootstrapValidator("addField", "signDate", {  /* 动态添加校验 */
       	validators: {  
        	notEmpty: {  
        		message: "工人签约时间未选择",
        	}
        }  
    });
	$("#page_form").data("bootstrapValidator").updateStatus("signDate", "NOT_VALIDATED", null);
	//$("#signDate").prop("disabled",true);
	if(isSign==0){
		$("#signDate").val("");
		$("#signDate").prev().find("span").remove();/* 找到label中的span，并将它移除 */
		$("#page_form").bootstrapValidator("removeField","signDate");/* 删除指定字段校验 */
	}else{
		$("#signDate").prev().prepend("<span class='required'>*</span>");
		//$("#signDate").prop("disabled",false);
	}
}

/* 工种分类是监理后触发事件 */
function changeType(){
	var workType =$.trim("${worker.workType12}");
	$("#page_form")
	.bootstrapValidator("addField", "openComponent_employee_empCode", {  /* 动态添加校验 */
       	validators: {  
        	notEmpty: {  
        		message: "当工种分类为项目经理时,员工编号必填",
        	}
        }  
    })
    .bootstrapValidator("addField", "isSupvr", {  /* 动态添加校验 */
       	validators: {  
        	notEmpty: {  
        		message: "当工种分类为项目经理时,项目经理类型必填",
        	}
        }  
    })
    .bootstrapValidator("addField", "prjLevel", {  /* 动态添加校验 */
       	validators: {  
        	notEmpty: {  
        		message: "当工种分类为项目经理时,监理星级必填",
        	}
        }  
    });
    $("#page_form").data("bootstrapValidator")
		.updateStatus("openComponent_employee_empCode", "NOT_VALIDATED", null)
		.updateStatus("isSupvr", "NOT_VALIDATED", null)
		.updateStatus("prjLevel", "NOT_VALIDATED", null);
	$("#empCodeLabel").find("span").remove();
	$("#isSupvr").prev().find("span").remove();
	$("#prjLevel").prev().find("span").remove();
	if(workType==06){
		$("#empCode").openComponent_employee({callBack:validateRefresh});/* 回调validateRefresh */
		$("#empCodeLabel").prepend("<span class='required'>*</span>");
		$("#isSupvr").prev().prepend("<span class='required'>*</span>");
		$("#prjLevel").prev().prepend("<span class='required'>*</span>");
		$("#isSupvr").attr("disabled",false);
		$("#prjLevel").attr("disabled",false);
	} else {
		$("#empCode").openComponent_employee();
		$("#isSupvr").val("");
		$("#prjLevel").val("");
		$("#isSupvr").attr("disabled",true);
		$("#prjLevel").attr("disabled",true);
		$("#page_form")
			.bootstrapValidator("removeField","openComponent_employee_empCode")
			.bootstrapValidator("removeField","isSupvr")
			.bootstrapValidator("removeField","prjLevel");/* 删除指定字段校验 */
	}
}

function doSave(){
	$("#page_form").bootstrapValidator("validate");
	if(!$("#page_form").data("bootstrapValidator").isValid()) return; 
	
	$("#isSign").removeAttr("disabled",true);
	$("#signDate").removeAttr("disabled",true);
	$("#isLeave").removeAttr("disabled",true);
	
	/* ajax之前先将disabled取消以便获取数据 */
	$("#workType12").prop("disabled",false);
	$("#workType12Dept").prop("disabled",false);
	
	var datas = $("#page_form").serialize();
	datas += "&phone=${worker.phone}&idnum=${worker.idnum}&m_umState=Z";/* 添加phone，idnum，操作类型 */
	$.ajax({
		url:"${ctx}/admin/worker/doUpdate",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		$("#workType12").prop("disabled",true);
	    		$("#workType12Dept").prop("disabled",true);
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    	}else{
		    	$("#isSign").attr("disabled",true);
				$("#signDate").attr("disabled",true);
				$("#isLeave").attr("disabled",true);
			    $("#workType12").prop("disabled",true);
	    		$("#workType12Dept").prop("disabled",true);
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
    	}
	});
}

</script>
</html>
