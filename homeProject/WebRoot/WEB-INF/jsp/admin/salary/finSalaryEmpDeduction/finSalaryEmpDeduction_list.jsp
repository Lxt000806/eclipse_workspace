<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>财务扣款管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>

</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label >薪酬月份</label>
						<house:dict id="salaryMon" dictCode="" sql="select salaryMon from tSalaryMon a where a.Expired='F' order by salaryMon Desc" 
						 sqlValueKey="salaryMon" sqlLableKey="salaryMon"  ></house:dict>							
					</li>	
					<li>
						<label>薪酬方案类型</label>
						 <house:dict id="salarySchemeType" dictCode="" sql="select * from tSalarySchemeType where expired = 'F'"
							sqlValueKey="Code" sqlLableKey="Descr">
						 </house:dict>
					</li>
					<li>
						<label>扣款科目</label>	
						<house:dict id="deductType2" dictCode=""
							sql="select code, code+' '+descr descr from tSalaryDeductType2 where Expired='F' and DeductType1='1' order by code  "
							sqlLableKey="descr" sqlValueKey="code"  />
					</li>
					<li>
						<label>签约公司</label> <house:dict id="conSignCmp"
							dictCode=""
							sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
							sqlLableKey="descr" sqlValueKey="code" /></li>
					<li>
                        <label>一级部门</label>
                        <house:DictMulitSelect id="department1" dictCode="" sql="select code, desc1 from tDepartment1 where Expired='F'" 
                            sqlLableKey="desc1" sqlValueKey="code" onCheck="checkDepartment1()"></house:DictMulitSelect>
                    </li>
                    
					
					<li>
						<label>查询条件</label>
						<input type="text" id="queryCondition" name="queryCondition" placeholder="姓名/工号/身份证/财务编码"/>
					</li>
					<li>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="FINSALARYEMPDEDUCTION_SAVE">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="FINSALARYEMPDEDUCTION_UPDATE">
					<button type="button" class="btn btn-system showCtrl" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="FINSALARYEMPDEDUCTION_DELETE">
					<button type="button" class="btn btn-system showCtrl" id="delete">
						<span>删除</span>
					</button>
				</house:authorize>
				<house:authorize authCode="FINSALARYEMPDEDUCTION_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="FINSALARYEMPDEDUCTION_IMPORT">
					<button type="button" class="btn btn-system" id="import" >
						<span>批量导入</span>
					</button>
				</house:authorize>
				<house:authorize authCode="FINSALARYEMPDEDUCTION_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>输出到Excel</span>
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
<script type="text/javascript">
$(function(){
	initMon();
	
	Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
	
	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/finSalaryEmpDeduction/goJqGrid",
		postData:$("#page_form").jsonForm() ,
		sortable: true,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
		 		{name: "pk", index: "pk", width: 50, label: "pk", sortable: true, align: "left",hidden:true},
		 		{name: "salarymon", index: "salarymon", width: 80, label: "薪酬月份", sortable: true, align: "left",  },
		 		{name: "salaryschemetypedescr", index: "salaryschemetypedescr", width: 85, label: "薪酬方案类型", sortable: true, align: "left",  },
                {name: "empname", index: "empname", width: 80, label: "姓名", sortable: true, align: "left",  },
                {name: "salaryemp", index: "salaryemp", width: 80, label: "工号", sortable: true, align: "left"},
               	{name: "department1descr", index: "department1descr", width: 80, label: "一级部门", sortable: true, align: "left" },
                {name: "deducttype2descr", index: "deducttype2descr", width: 80, label: "扣款科目", sortable: true, align: "left"},
                {name: "remarks", index: "remarks", width: 150, label: "扣款内容", sortable: true, align: "left"},
                {name: "amount", index: "amount", width: 70, label: "扣款金额", sortable: true, align: "right",  },
                {name: "financialcode", index: "financialcode", width: 80, label: "财务编码", sortable: true, align: "left",  },
               	{name: "deductdate", index: "deductdate", width: 120, label: "登记时间", sortable: true, align: "left", formatter: formatTime},
                {name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "最后修改人员", sortable: true, align: "left"},
                {name: "expired", index: "expired", width: 50, label: "过期", sortable: true, align: "left"},
                {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"},
		],
		ondblClickRow: function(){
			view();
		},
		onSelectRow: function(id){ 
		    var rowData = $("#dataTable").jqGrid('getRowData',id);
		    getSalaryStatusCtrl(rowData.salarymon, rowData.salaryschemetype)
		}, 
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("save", {
			title : "财务扣款管理——新增",
			url : "${ctx}/admin/finSalaryEmpDeduction/goSave",
			height : 460,
			width : 530,
			returnFun : goto_query
		});
	});
	
	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"财务扣款管理——编辑",
			url:"${ctx}/admin/finSalaryEmpDeduction/goUpdate",
			postData:{
				id:ret.pk
			},
			height : 460,
			width : 530,
			returnFun:goto_query
		});
	});
	
	$("#import").on("click",function(){
		Global.Dialog.showDialog("Import",{
			title:"财务扣款管理——批量导入",
			url:"${ctx}/admin/finSalaryEmpDeduction/goImport",
			height:600,
			width:1070,
			returnFun:function(data){
				goto_query();
			}
		});
	});
	
	$("#delete").on("click",function(){
		var ret = selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		art.dialog({
			content:"您确定要删除该信息吗？",
			lock: true,
			ok: function () {
				$.ajax({
					url : "${ctx}/admin/finSalaryEmpDeduction/doDelete",
					data : {deleteIds:ret.pk},
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: "json",
					type: "post",
					cache: false,
					error: function(){
						art.dialog({
							content: "删除该信息出现异常"
						});
					},
					success: function(obj){
						if(obj.rs){
							art.dialog({
								content: obj.msg,
								time: 1000,
								beforeunload: function () {
									goto_query();
								}
							});
						}else{
							art.dialog({
								content: obj.msg
							});
						}
					}
				});
				return true;
			},
			cancel: function () {
				return true;
			}
		});
	});

});

function view(){
	var ret=selectDataTableRow();
	if (!ret) {
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}
	Global.Dialog.showDialog("view",{
		title:"财务扣款管理——查看",
		url:"${ctx}/admin/finSalaryEmpDeduction/goView",
		postData:{
			id:ret.pk
		},
		height : 460,
		width : 530,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/finSalaryEmpDeduction/doExcel";
	doExcelAll(url);
}

function clearForm(){
	$("#page_form input[type='text'] ").val('');
	$("#page_form select").val('');
	$.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
	$("#department1").val('');
} 

function initMon(){
	var today=new Date();
	var year=today.getFullYear();  //月份
	var month=parseFloat(today.getMonth()+1);
	var day=parseFloat(today.getDate());
	var lastMon="";
	var thisMon="";
	//月份小于10，前面加0
	if(month<10){
		lastMon="0"+(month-1);
		thisMon="0"+(month);
	}else{
		lastMon=""+(month-1);
		thisMon=""+(month);
	}
	//16号之前的，取上个月，否则取这个月
	if(day<16){
		$("#salaryMon").val(year+lastMon);
	}else{
		$("#salaryMon").val(year+thisMon);
	}
}

function getSalaryStatusCtrl(salaryMon, salarySchemeType){
	$.ajax({
		url:"${ctx}/admin/salaryCalc/getSalaryStatusCtrl",
		type: "post",
		data: {salaryMon:salaryMon,salarySchemeType:salarySchemeType, status:'3'},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "数据出错~"});
	    },
	    success: function(obj){
	    	//薪酬状态为已结算，只能查询信息
	    	if(obj.schemeInfo.indexOf("已结算") != -1){
				$(".showCtrl").attr("disabled",true);
			}else{
				$(".showCtrl").removeAttr("disabled");
			}
	    }
	});
}

</script>
</body>
</html>
