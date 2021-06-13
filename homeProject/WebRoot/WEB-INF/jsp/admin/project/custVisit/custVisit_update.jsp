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
	
	<!-- 修改bootstrap中各个row的边距 -->
	<style>
		.row{
			margin: 0px;
		}
		.col-sm-3{
			padding: 0px;
		}
		.col-sm-6{
			padding: 0px;
		}
		.col-sm-12{
			padding: 0px;
		}
	</style>
	
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="submit" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="excelBtn" onclick="doExcelNow('客户回访问题_')">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<div class="row">
							<div class="col-sm-3">
								<li>
									<label>回访编号</label>
									<input type="text" id="no" name="no" style="width:160px;" value="${custVisit.no}"
										placeholder="保存自动生成" readonly="readonly"/>
								</li>
								<li>
									<label>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${custVisit.custcode}"
										readonly="readonly"/>
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${custVisit.address}"
										readonly="readonly"/>
								</li>
								<li>
									<label>客户类型</label>
									<house:DictMulitSelect id="custType" dictCode="" sql="select code ,desc1 descr from tcustType where expired='F'" 
										sqlValueKey="Code" sqlLableKey="Descr" selectedValue="${custVisit.custtype}"></house:DictMulitSelect>
								</li>
								<li>
									<label>业主姓名</label>
									<input type="text" id="custDescr" name="custDescr" style="width:160px;" value="${custVisit.custdescr}"
										readonly="readonly"/>
								</li>
								<li>
									<label>业主电话</label>
									<input type="text" id="custPhone" name="custPhone" style="width:160px;" value="${custVisit.custphone}"
										readonly="readonly"/>
								</li>
							</div>
							<div class="col-sm-3">
								<li>
									<label>项目经理</label>
									<input type="text" id="projectManDescr" name="projectManDescr" style="width:160px;" value="${custVisit.projectmandescr}"
										readonly="readonly"/>
								</li>
								<li>
									<label>项目经理电话</label>
									<input type="text" id="projectManPhone" name="projectManPhone" style="width:160px;" value="${custVisit.projectmanphone}" readonly="readonly"/>
								</li>
								<li>
									<label>工程部</label>
									<house:DictMulitSelect id="gcDept" dictCode="" 
										sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' and DepType='3' order by DispSeq " 
										sqlLableKey="fd2" sqlValueKey="fd1" selectedValue="${custVisit.gcdept}">
									</house:DictMulitSelect>
								</li>
								<li>
									<label>设计师</label>
									<input type="text" style="width:160px;" value="${custVisit.designmandescr}" readonly="readonly"/>
								</li>
								<li>
									<label>设计师电话</label>
									<input type="text" style="width:160px;" value="${custVisit.designmanphone}" readonly="readonly"/>
								</li>
								<li>
									<label>设计所</label>
									<house:DictMulitSelect id="designDept" dictCode="" 
										sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' order by DispSeq " 
										sqlLableKey="fd2" sqlValueKey="fd1" selectedValue="${custVisit.designdept}">
									</house:DictMulitSelect>
								</li>
							</div>
							<div class="col-sm-6">
								<div class="col-sm-6">
									<li>
										<label>开工时间</label>
										<input type="text" id="beginDate" name="beginDate"
											class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
											value="<fmt:formatDate value='${custVisit.begindate}' pattern='yyyy-MM-dd'/>" disabled/>
									</li>
									<li>
										<label>当前进度</label>
										<input type="text" id="prjItem" name="prjItem" style="width:160px;" value="${customer.prjitemdescr}"
											readonly="readonly"/>
									</li>
									<li>
										<label>结算时间</label>
										<input type="text" id="checkOutDate" name="checkOutDate"
											class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
											value="<fmt:formatDate value='${custVisit.checkoutdate}' pattern='yyyy-MM-dd'/>" disabled/>
									</li>
									<li>
										<label>总拖延天数</label>
										<input type="text" style="width:160px;" value="${customer.delaynum}" readonly="readonly"/>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>满意度</label>
										<house:xtdm id="satisfaction" dictCode="VISITSATIS" style="width:160px;" value="${custVisit.satisfaction}"/>
									</li>
								</div>
								<div class="col-sm-6">
									<li>
										<label>回访类型</label>
										<house:xtdm id="visitType" dictCode="VISITTYPE" style="width:160px;" value="${custVisit.visittype}"/>
									</li>
									<li>
										<label>回访状态</label>
										<house:xtdm id="status" dictCode="VISITSTATUS" style="width:160px;" value="${custVisit.status}"/>
									</li>
									<li>
										<label>回访人</label>
										<input type="text" id="visitCZYDescr" name="visitCZYDescr" style="width:160px;" value="${custVisit.visitczydescr}"
											readonly="readonly"/>
									</li>
									<li>
										<label>回访时间</label>
										<input type="text" class="i-date" id="visitDate" name="visitDate" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
											value="<fmt:formatDate value='${custVisit.visitdate}' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled/>
									</li>
									<li>
										<label>结算完工</label>
										<house:xtdm id="isComplete" dictCode="YESNO" style="width:160px;" value="${custVisit.iscomplete}"/>
									</li>
								</div>
								<div class="col-sm-12">
									<li>
										<label class="control-textarea">回访问题详情</label>
										<textarea id="remarks" name="remarks" rows="2" style="height: 50px;">${custVisit.remarks}</textarea>
									</li>
								</div>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#custVisit_tabView" data-toggle="tab">客户问题</a></li>  
			</ul>
		    <div class="tab-content">  
				<div id="custVisit_tabView"  class="tab-pane fade in active"> 
					<div class="pageContent">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="custProSave">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="custProUpdate">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="custProDelete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="custProView" onclick="view()">
									<span>查看</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
						</div>
					</div>
				</div> 
			</div>	
		</div>
	</div>
</body>	
<script>
//表格中的最一开始的数据(全局) 
var originalDataTable;
$(function() {
	if("V" == "${m_umState}"){
		$("#saveBtn").hide();
		$("#custProSave").hide();
		$("#custProUpdate").hide();
		$("#custProDelete").hide();
		
		$("#satisfaction").prev().children().remove();//移除span
		
		$("#satisfaction").attr("disabled",true);
		$("#visitType").attr("disabled",true);
		$("#status").attr("disabled",true);
		$("#isComplete").attr("disabled",true);
		$("#remarks").attr("readonly",true);
	}

	/* 锁定多选下拉树 */
	$("#custType_NAME").attr("disabled",true);
	$("#gcDept_NAME").attr("disabled",true);
	$("#designDept_NAME").attr("disabled",true);
	
	/* 原始form数据(放在锁定组件后面，否则后面比较数据会出错) */
	var originalData = $("#page_form").jsonForm();
	
	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			satisfaction:{  
				validators: {  
					notEmpty: {  
						message: "请选择满意度"  
					}
				}  
			}
		}
	});
	
	var gridOption = {
		url:"${ctx}/admin/custVisit/goCustProblemJqGrid",
		postData:{no:"${custVisit.no}"},
		sortable: true,
		height : $(document).height()-$("#content-list").offset().top - 40,
		rowNum : 10000000,
		colModel : [
			{name: "no", index: "no", width: 70, label: "NO", sortable: true, align: "left", hidden: true},
			{name: "promsource", index: "promsource", width: 70, label: "promSource", sortable: true, align: "left", hidden: true},
			{name: "promsourcedescr", index: "promsourcedescr", width: 70, label: "问题类型", sortable: true, align: "left"},
			{name: "status", index: "status", width: 70, label: "status", sortable: true, align: "left", hidden: true},
			{name: "statusdescr", index: "statusdescr", width: 70, label: "问题状态", sortable: true, align: "left"},
			{name: "promtype1", index: "promtype1", width: 70, label: "promType1", sortable: true, align: "left", hidden: true},
			{name: "promtype1descr", index: "promtype1descr", width: 70, label: "问题分类", sortable: true, align: "left"},
			{name: "promtype2", index: "promtype2", width: 70, label: "promType2", sortable: true, align: "left", hidden: true},
			{name: "promtype2descr", index: "promtype2descr", width: 70, label: "材料分类", sortable: true, align: "left"},
			{name: "promrsn", index: "promrsn", width: 70, label: "promRsn", sortable: true, align: "left", hidden: true},
			{name: "promrsndescr", index: "promrsndescr", width: 70, label: "问题原因", sortable: true, align: "left"},
			{name: "supplcode", index: "supplcode", width: 70, label: "supplCode", sortable: true, align: "left", hidden: true},
			{name: "suppldescr", index: "suppldescr", width: 70, label: "供应商", sortable: true, align: "left"},
			{name: "crtdate", index: "crtdate", width: 130, label: "crtDate", sortable: true, align: "left", formatter: formatTime, hidden: true},
			{name: "infodate", index: "infodate", width: 80, label: "通知时间", sortable: true, align: "left", formatter: formatDate},
			{name: "dealczy", index: "dealczy", width: 70, label: "dealCZY", sortable: true, align: "left", hidden: true},
			{name: "dealczydescr", index: "dealczydescr", width: 70, label: "处理人", sortable: true, align: "left"},
			{name: "plandealdate", index: "plandealdate", width: 90, label: "计划处理时间", sortable: true, align: "left", formatter: formatDate},
			{name: "dealremarks", index: "dealremarks", width: 70, label: "处理结果", sortable: true, align: "left"},
			{name: "dealdate", index: "dealdate", width: 80, label: "处理时间", sortable: true, align: "left", formatter: formatDate},
			{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后更新人员", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 130, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "过期标志", sortable: true, align: "left"}
		],
		loadonce: true,
		ondblClickRow: function(){
			view();
		},
		loadComplete: function(){
			originalDataTable = Global.JqGrid.allToJson("dataTable").detailJson;
		}
	};
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	/* setGridParam：有些参数的修改必须要重新加载grid才可以生效，这个方法可以覆盖事件  */
	window.goto_query = function(){
		$("#dataTable").jqGrid(
				"setGridParam",{
					datatype:"json",
					postData:{no : "${custVisit.no}"},
					page:1,
					url:"${ctx}/admin/custVisit/goCustProblemJqGrid"
				}
			).trigger("reloadGrid");
	};

	/* 回访客户新增 */
	$("#custProSave").on("click",function(){
		Global.Dialog.showDialog("custProSave", {
			title : "客户问题--新增",
			url : "${ctx}/admin/custVisit/custProblem",
			postData: {m_umState:"A",no: $("#no").val()},
			height : 400,
			width : 750,
			returnFun : function(data){
				if(data){
					// console.log(data)
					$.each(data,function(k,v){
						var json = {
							no : v.no,
							promsource : v.promSource,
							promsourcedescr : v.promSourceDescr,
							status : v.status,
							statusdescr : v.statusDescr,
							promtype1 : v.promType1,
							promtype1descr : v.promType1Descr,
							promtype2 : v.promType2,
							promtype2descr : v.promType2Descr,
							promrsn : v.promRsn,
							promrsndescr : v.promRsnDescr,
							supplcode : v.supplCode,
							suppldescr : v.supplDescr,
							infodate : v.infodate,
							dealczy : v.dealCZY,
							dealczydescr : v.dealCZYDescr,
							crtdate : new Date(),
							plandealdate : v.plandealdate,
							dealremarks : v.dealremarks,
							dealdate : v.dealdate,
							lastupdatedby : v.lastUpdatedBy,
							lastupdate : new Date(),
							actionlog : "ADD",
							expired : "F"
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
				  	});
				}
			}
		});
	});

	/* 客户回访编辑 */
	$("#custProUpdate").on("click",function(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
			return false;
		}
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		
		Global.Dialog.showDialog("custProUpdate", {
			title : "客户问题--编辑",
			url : "${ctx}/admin/custVisit/custProblem",
			postData: {
				m_umState:"M",
				no: $("#no").val(),
				promSource:ret.promsource,
				status:ret.status,
				promType1:ret.promtype1,
				promType2:ret.promtype2,
				promRsn:ret.promrsn,
				supplCode:ret.supplcode,
				SupplDescr:ret.suppldescr,
				crtDate:ret.crtdate,
				infoDate:ret.infodate,
				dealCZY:ret.dealczy,
				DealCZYDescr:ret.dealczydescr,
				planDealDate:ret.plandealdate,
				dealRemarks:ret.dealremarks,
				dealDate:ret.dealdate,
				lastUpdate:ret.lastupdate,
				lastUpdatedBy:ret.lastupdatedby,
				expired:ret.expired,
				actionLog:ret.actionlog
			},
			height : 400,
			width : 750,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							no : v.no,
							promsource : v.promSource,
							promsourcedescr : v.promSourceDescr,
							status : v.status,
							statusdescr : v.statusDescr,
							promtype1 : v.promType1,
							promtype1descr : v.promType1Descr,
							promtype2 : v.promType2,
							promtype2descr : v.promType2Descr,
							promrsn : v.promRsn,
							promrsndescr : v.promRsnDescr,
							supplcode : v.supplCode,
							suppldescr : v.supplDescr,
							infodate : v.infodate,
							dealczy : v.dealCZY,
							dealczydescr : v.dealCZYDescr,
							crtdate : new Date(),
							plandealdate : v.plandealdate,
							dealremarks : v.dealremarks,
							dealdate : v.dealdate,
							lastupdatedby : v.lastUpdatedBy,
							lastupdate : new Date(),
							actionlog : "Edit",
							expired : "F"
					  	};
					  	$("#dataTable").setRowData(rowId,json);
				  	});
				}
			}
		});
	});

	/* 回访客户删除 */
	$("#custProDelete").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作",width: 220});
			return false;
		}
		art.dialog({
			content:"是否删除该记录？",
			lock: true,
			width: 200,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
			},
			cancel: function () {
				return true;
			}
		});
		
	});

	/* 关闭按钮时检测数据是否更改校验 */
	$("#closeBut").on("click",function(){
		/*获取表单json数据，将_form_token_uniq_id设为空来解决使用导出excel后token变化的问题，
		再将json转为String进行前后数据比较。*/
		var newData = $("#page_form").jsonForm();
		newData._form_token_uniq_id = "";
		originalData._form_token_uniq_id = "";
		newData = JSON.stringify(newData);
		originalData = JSON.stringify(originalData);

		var param= Global.JqGrid.allToJson("dataTable");
		
		if (param.detailJson != originalDataTable||newData != originalData) {
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
	
	function doSave(){
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()) return;
		
		var param= Global.JqGrid.allToJson("dataTable");
		param.visitCZY = "${custVisit.visitczy}";//增加回访人编号
		
		Global.Form.submit(
			"page_form",
			"${ctx}/admin/custVisit/doUpdate",
			param,
			function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
		    		art.dialog({
						content: ret.msg,
						width: 200
					});
				}
			});
	}
	
});

/* 客户回访查看 */
function view(){
	var ret=selectDataTableRow();
	if(!ret){
		art.dialog({content: "请选择一条记录进行查看操作",width: 220});
		return false;
	}
	var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
	
	Global.Dialog.showDialog("custProView", {
		title : "客户问题--查看",
		url : "${ctx}/admin/custVisit/custProblem",
		postData: {
			m_umState:"V",
			no: $("#no").val(),
			promSource:ret.promsource,
			status:ret.status,
			promType1:ret.promtype1,
			promType2:ret.promtype2,
			promRsn:ret.promrsn,
			supplCode:ret.supplcode,
			SupplDescr:ret.suppldescr,
			crtDate:ret.crtdate,
			infoDate:ret.infodate,
			dealCZY:ret.dealczy,
			DealCZYDescr:ret.dealczydescr,
			planDealDate:ret.plandealdate,
			dealRemarks:ret.dealremarks,
			dealDate:ret.dealdate,
			lastUpdate:ret.lastupdate,
			lastUpdatedBy:ret.lastupdatedby,
			expired:ret.expired,
			actionLog:ret.actionlog
		},
		height : 400,
		width : 750,
		returnFun : goto_query
	});
}
</script>
</html>
