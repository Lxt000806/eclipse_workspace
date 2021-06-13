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
	<title>考勤信息管理</title>
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
						<house:dict id="salaryMon" dictCode="" sql="select salaryMon from tSalaryMon a where a.Expired='F' order by salaryMon desc" 
						 sqlValueKey="salaryMon" sqlLableKey="salaryMon"  ></house:dict>							
					</li>	
					<li>
						<label>签约公司</label>
						<house:dict id="conSignCmp" dictCode=""
							sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
							sqlLableKey="descr" sqlValueKey="code" />
					</li>
					<li>
                        <label>一级部门</label>
                        <house:DictMulitSelect id="department1" dictCode="" sql="select code, desc1 from tDepartment1 where Expired='F'" 
                            sqlLableKey="desc1" sqlValueKey="code" onCheck="checkDepartment1()"></house:DictMulitSelect>
                    </li>
				
					<li hidden="true" >
						<label >岗位类别</label>
						<house:dict id="posiClass" dictCode="" sql="select pk,cast(pk as nvarchar(20))+' '+descr descr from tSalaryPosiClass a where a.Expired='F' " 
						 sqlValueKey="pk" sqlLableKey="descr" ></house:dict>							
					</li>
					<li>
						<label>查询条件</label>
						<input type="text" id="queryCondition" name="queryCondition" placeholder="姓名/工号/身份证"/>
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
				<house:authorize authCode="SALARYEMPATTENDANCE_SAVE">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYEMPATTENDANCE_UPDATE">
					<button type="button" class="btn btn-system showCtrl" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYEMPATTENDANCE_DELETE">
					<button type="button" class="btn btn-system showCtrl" id="delete">
						<span>删除</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYEMPATTENDANCE_BATCHDELETE">
					<button type="button" class="btn btn-system" id="deleteBatch">
						<span>批量删除</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYEMPATTENDANCE_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYEMPATTENDANCE_IMPORT">
					<button type="button" class="btn btn-system" id="import" >
						<span>批量导入</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYEMPATTENDANCE_EXCEL">
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
var postData = $("#page_form").jsonForm();
$(function(){
	initMon();
	postData.salaryMon=$("#salaryMon").val();
	Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/salaryEmpAttendance/goJqGrid",
		postData:postData ,
		sortable: true,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
		 		{name: "pk", index: "pk", width: 50, label: "pk", sortable: true, align: "left",hidden:true},
                {name: "empname", index: "empname", width: 80, label: "姓名", sortable: true, align: "left",  },
                {name: "salaryemp", index: "salaryemp", width: 70, label: "工号", sortable: true, align: "left"},
                {name: "salarymon", index: "salarymon", width: 70, label: "薪酬月份", sortable: true, align: "left",  },
                {name: "latetimes", index: "latetimes", width: 70, label: "迟到次数", sortable: true, align: "right",  },
                {name: "seriouslatetimes", index: "seriouslatetimes", width: 115, label: "迟到（半小时以上）次数", sortable: true, align: "right",  },
               	{name: "lateoverhourtimes", index: "lateoverhourtimes", width: 115, label: "迟到（1小时以上）次数", sortable: true, align: "right",  },
                {name: "absenttimes", index: "absenttimes", width: 70, label: "缺卡次数", sortable: true, align: "right",  },
                {name: "leaveearlytimes", index: "leaveearlytimes", width: 70, label: "早退次数", sortable: true, align: "right",  },
                {name: "leavedays", index: "leavedays", width: 70, label: "请假天数", sortable: true, align: "right",  },
                {name: "absentdays", index: "absentdays", width: 70, label: "旷工天数", sortable: true, align: "right",  },
               	{name: "importdate", index: "importdate", width: 120, label: "导入时间", sortable: true, align: "left", formatter: formatTime},
               	{name: "importczy", index: "importczy", width: 80, label: "导入操作员", sortable: true, align: "left",},
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
			title : "考勤信息管理——新增",
			url : "${ctx}/admin/salaryEmpAttendance/goSave",
			height : 400,
			width : 800,
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
			title:"考勤信息管理——编辑",
			url:"${ctx}/admin/salaryEmpAttendance/goUpdate",
			postData:{
				id:ret.pk
			},
			height : 400,
			width : 800,
			returnFun:goto_query
		});
	});
	
	$("#import").on("click",function(){
		Global.Dialog.showDialog("Import",{
			title:"考勤信息管理——批量导入",
			url:"${ctx}/admin/salaryEmpAttendance/goImport",
			height:600,
			width:1000,
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
					url : "${ctx}/admin/salaryEmpAttendance/doDelete",
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
	
	$("#deleteBatch").on("click",function(){
		var contents = "<div><div class='row'><label style='width:80px;font-weight:normal'>薪酬月份</label>" +
		               $("#salaryMon").prop("outerHTML")
		               .replace('salaryMon', 'salaryMon_del')
		               .replace('onmousedown=\"javascript:return false;\"','')+
                       "</div></div>";
		art.dialog({
			content:contents,
			lock: true,
			padding:0,
			width: 300,
			height: 80,
			title:"选择要删除的薪酬月份",
			ok: function () {
			    var salaryMon_del = $("#salaryMon_del").val();
	            if (salaryMon_del=="") {
	                art.dialog({
	                	content:"请选择要删除的薪酬月份！"
	                });
	                return false;
	            }
	            var status=getSalaryMonStatus(salaryMon_del);
	            if(status=="3"){
	            	art.dialog({
	                	content:"该薪酬月份已结算，不允许删除！"
	                });
	                return false;
	            }
		        art.dialog({
					content:"您确定要删除薪酬月份【"+salaryMon_del+"】的所有记录吗？",
					lock: true,
					ok: function () {
						$.ajax({
							url:'${ctx}/admin/salaryEmpAttendance/doDeleteBatch',
							type: 'post',
							data: {
								salaryMon: salaryMon_del
							},
							dataType: 'json',
							cache: false,
							error: function(obj){
								showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
						    },
						    success: function(obj){
						    	art.dialog({
									content:obj.msg,
									time: 1000,
									beforeunload: function () {
										goto_query();
									}
								});
							}	
							});
						},
						cancel: function () {
							return true;
						}
				});
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
		title:"考勤信息管理——查看",
		url:"${ctx}/admin/salaryEmpAttendance/goView",
		postData:{
			id:ret.pk
		},
		height : 400,
		width : 800,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/salaryEmpAttendance/doExcel";
	doExcelAll(url);
}

function clearForm(){
	$("#page_form input[type='text'] ").val('');
	$("#page_form select").val('');
	$.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
	$("#department1").val('');
} 

function getSalaryMonStatus(salaryMon_del){
	var status="";
	$.ajax({
		url:'${ctx}/admin/salaryMon/getSalaryMon',
		type: 'post',
		data: {
			sm:salaryMon_del  
		},
		dataType: 'json',
		cache: false,
		async:false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	status=obj;
		}	
	});
	return status;
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
