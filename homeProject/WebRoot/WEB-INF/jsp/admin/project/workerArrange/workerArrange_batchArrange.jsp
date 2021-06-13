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
		<div class="panel-body">
			<ul class="nav nav-tabs" role="tablist">
			    <li role="presentation" class="active"><a href="#first" aria-controls="first" role="tab" data-toggle="tab">选择工人</a></li>
			    <li role="presentation"><a href="#second" aria-controls="second" role="tab" data-toggle="tab">排班规则</a></li>
			</ul>
			<div class="tab-content">
			    <div role="tabpanel" class="tab-pane active" id="first">
			    	<div class="query-form">
						<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
							<input type="hidden" id="expired" name="expired" value="${rcvAct.expired }"/>
							<input type="hidden" name="jsonString" value=""/>
							<ul class="ul-form">
								<li>
									<label>姓名</label>
									<input type="text" id="name_chi" name="nameChi" style="width: 160px;" value="${worker.nameChi}"/>
								</li>
								<li>
									<label>电话</label>
									<input type="text" id="phone" name="Phone" style="width:160px;" value="${worker.phone}"/>
								</li>
								<li>
									<label>归属区域</label>
									<house:dict id="belongRegion" dictCode="" sql="select Code,(Code+' '+Descr) Descr1 from tRegion" sqlValueKey="Code" 
										sqlLableKey="Descr1" value="${worker.belongRegion}"/>
								</li>
								<li>
									<label>归属事业部</label>
									<house:dict id="department1" dictCode="" sql="select Code,Code+' '+Desc1 Department1 from tdepartment1 where DepType='3'" 
										sqlValueKey="Code" sqlLableKey="Department1" value="${worker.department1}"/>
								</li>
								<li>
									<label>所属工程部</label>
									<house:department2 id="department2" dictCode="" depType="3"></house:department2>
								</li>
								<li>	
									<label>工种分类</label>
									<select id="work_type12" name="workType12" value="${worker.workType12}"></select>
								</li>
								<li>
									<label>所属分组</label>
									<select id="work_type12_dept" name="workType12Dept"></select>
								</li>
								<li>
									<label>归属专盘</label>
									<house:dict id="spcBuilder" dictCode="" sql="select Code,Code+' '+Descr SpcBuilder from tSpcBuilder" 
										sqlValueKey="Code" sqlLableKey="SpcBuilder" value="${worker.spcBuilder}"/>
								</li>
								<li>
									<label>状态</label>
									<house:xtdm id="isLeave" dictCode="WORKERSTS"></house:xtdm>
								</li>
								<li>
									<label>工程大区</label>
									<house:DictMulitSelect id="prjRegionCode" dictCode="" 
										sql="select Code,Descr from tPrjRegion where Expired='F' order by Code " 
										sqlLableKey="Descr" sqlValueKey="Code">
									</house:DictMulitSelect>
								</li>
								<li>
									<label>工人分类</label>
									<house:xtdmMulit id="workerClassify" dictCode="WORKERCLASSIFY"/>
								</li>
								<li id="load_more">
									<input type="checkbox" id="expired_show" name="expired_show" value="${worker.expired}" 
										onclick="hideExpired(this)" ${worker.expired != 'T' ? 'checked' : ''}/>
									<label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label>
									<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
									<button type="button" class="btn btn-system btn-sm" id="clear" onclick="clearForm();">清空</button>
								</li>
							</ul>
						</form>
					</div>
			    	<div id="content-list">
						<table id="dataTable"></table>
						<div id="dataTablePager"></div>
					</div>
			    </div>
			    <div role="tabpanel" class="tab-pane" id="second">
			    	<form action="" method="post" id="arrangeform" class="form-search">
						<house:token></house:token>
						<ul class="ul-form">
							<div class="row">
								<div class="col-sm-4">
									<input id="codes" type="hidden" name="codes">
									<li class="form-validate">
										<label><span class="required">*</span>工种分类</label>
										<select id="workType12" name="workType12"></select>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>日期从</label>
										<input type="text" id="fromDate" name="fromDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd', minDate: '${workerArrange.fromDate}'})"
											value="<fmt:formatDate value='${workerArrange.fromDate}' pattern='yyyy-MM-dd'/>" />
									</li>
									<li class="form-validate">						
										<label><span class="required">*</span>至</label>
										<input type="text" id="toDate" name="toDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd', minDate: '${workerArrange.fromDate}'})"/>
									</li>
									<li class="form-validate">	
										<label><span class="required">*</span>工作日</label>
										<house:xtdmMulit id="weekdays" dictCode="WEEKDAY" selectedValue="0,1,2,3,4,5,6" onCheck="updateWeekDaysStatus()"></house:xtdmMulit>
									</li>
									<li class="form-validate">	
										<label><span class="required">*</span>班次</label>
										<house:xtdmMulit id="dayTypes" dictCode="DAYTYPE" selectedValue="0,1" onCheck="updateDayTypesStatus()"></house:xtdmMulit>
									</li>
									<li class="form-validate">	
										<label><span class="required">*</span>号数</label>
										<select name="number" id="number">
											<option value ="1">1</option>
											<option value ="2">2</option>
										</select>
									</li>
									<li>
										<button id="batchArrange" type="button" class="btn btn-system btn-sm" onclick="doBatchArrange()">提交排班</button>
									</li>
								</div>
							</div>
						</ul>
					</form>
			    </div>
			</div>
		</div>
	</div>
</body>	
<script>
var postData = $("#page_form").jsonForm();

$(function() {
	Global.LinkSelect.initSelect("${ctx}/admin/worker/workType12","work_type12","work_type12_dept");
	Global.LinkSelect.initSelect("${ctx}/admin/worker/workType12","workType12","work_type12_dept");
	
	Global.JqGrid.initJqGrid("dataTable", {
		sortable: true,
		url: "${ctx}/admin/worker/goJqGridList",
		postData: postData,
		height: 280,
		styleUI: "Bootstrap",
		multiselect: true,
		colModel: [
			{name: "code", index: "code", width: 50, label: "编号", sortable: true, align: "left"},
			{name: "namechi", index: "namechi", width: 70, label: "姓名", sortable: true, align: "left"},
			{name: "leveldescr", index: "leveldescr", width: 70, label: "工人级别", sortable: true, align: "left"},
			{name: "worktype12descr", index: "worktype12descr", width: 70, label: "工种分类", sortable: true, align: "left"},
			{name: "worktype12deptdescr", index: "worktype12deptdescr", width: 70, label: "所属分组", sortable: true, align: "left"},
			{name: "empcode", index: "empcode", width: 70, label: "员工编号", sortable: true, align: "left"},
			{name: "liveregiondescr", index: "liveregiondescr", width: 100, label: "一级居住区域", sortable: true, align: "left"},
			{name: "liveregion2descr", index: "liveregion2descr", width: 100, label: "二级居住区域", sortable: true, align: "left"},
			{name: "rcvprjtypedescr", index: "rcvprjtypedescr", width: 100, label: "承接工地类型", sortable: true, align: "left"},
			{name: "vehicledescr", index: "vehicledescr", width: 65, label: "交通工具", sortable: true, align: "left"},
			{name: "num", index: "num", width: 80, label: "班组人数", sortable: true, align: "right"},
			{name: "department1descr", index: "department1descr", width: 70, label: "事业部", sortable: true, align: "left"},
			{name: "department2descr", index: "department2descr", width: 80, label: "所属工程部", sortable: true, align: "left"},
			{name: "spcbuilderdescr", index: "spcbuilderdescr", width: 120, label: "归属专盘", sortable: true, align: "left"},
			{name: "isautoarrangedescr", index: "isautoarrangedescr", width: 100, label: "是否自动安排", sortable: true, align: "left"},
			{name: "belongregiondescr", index: "belongregiondescr", width: 70, label: "归属区域", sortable: true, align: "left"},
			{name: "isleavedescr", index: "isleavedescr", width: 70, label: "是否离职", sortable: true, align: "left", hidden: true},
			{name: "efficiency", index: "efficiency", width: 100, label: "工作效率比例", sortable: true, align: "right"},
			{name: "issupvrdescr", index: "issupvrdescr", width: 70, label: "是否监理", sortable: true, align: "left"},
			{name: "prjleveldescr", index: "prjleveldescr", width: 70, label: "监理级别", sortable: true, align: "left"},
		]
	});
	
	$("#arrangeform").bootstrapValidator({
		excluded: ':disabled',
		fields: {  
			workType12: {
				validators: {
					notEmpty: {
	                    message:'请选择一个工种'
	                }
				}
			},
			fromDate: {
				validators: {
					date: {
                        format: 'YYYY-MM-DD',
                        message: '请选择一个正确的起始日期'
                    }
				}
			},
			toDate: {
				validators: {
				    notEmpty: {
				        message: '请选择排班的截止日期'
				    },
					date: {
                        format: 'YYYY-MM-DD',
                        message: '请选择一个正确的截止日期'
                    }
				}
			},
			weekday: {
				validators: {
					notEmpty: {
	                    message:'请至少选择一个工作日'
	                }
				}
			},
			dayTypes: {
				validators: {
					notEmpty: {
	                    message:'请至少选择一个班次'
	                }
				}
			},
			number: {
				validators: {
					notEmpty: {
	                    message:'请选择当前班次要预约的号数'
	                }
				}
			} 
 		},
	});
		
});

function updateDayTypesStatus() {
	$("#arrangeform").data("bootstrapValidator")
		.updateStatus("dayTypes", "NOT_VALIDATED", null).validateField("dayTypes")
}

function updateWeekDaysStatus() {
	$("#arrangeform").data("bootstrapValidator")
		.updateStatus("weekdays", "NOT_VALIDATED", null).validateField("weekdays")
}

function doBatchArrange() {
	var rows = selectDataTableRows();
	if (rows.length < 1) {
		art.dialog({content: "至少选择一位工人！"});
		return;
	}

	var codes = [];
	for (i = 0; i < rows.length; i++) codes[i] = rows[i].code;
 	$("#codes").val(codes);

	$("#arrangeform").bootstrapValidator("validate");
	if(!$("#arrangeform").data("bootstrapValidator").isValid()) return;
	
	var data = $("#arrangeform").jsonForm();
	$.ajax({
		url:"${ctx}/admin/workerArrange/doBatchArrange",
		type: "post",
		data: data,
		dataType: "json",
		cache: false,
		error: function(obj) {
			showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function() {
	    				closeWin();
				    }
				});
	    	}else{
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
				});
	    	}
    	}
	});
}

</script>
</html>
