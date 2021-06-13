<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>业绩计算--干系人新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var department2Text;//二级部门的下拉框text
var department3Text;//三级部门的下拉框text
//校验函数
$(function() {
	$("#empCode").openComponent_employee({
		showValue:"${perfCycle.empCode}",
		showLabel:"${perfCycle.empName}",
		condition:{"isEmpForPerf":"1"},
		callBack:function(data){
			$("#empName").val(data.namechi);
			initDept(data.department1,data.department2,data.department3);
			initLeaderComponent(data.leadercode,data.leaderdescr);
			$("#leaderName").val(data.leaderdescr);
    		initbusiDrcComponent(data.busidrc,data.busidrcdescr);
    		$("#busiDrcDescr").val(data.busidrcdescr);
			$("#perfPer").val(1);	
		}
	});
	//初始化部门领导，业务主任
    initLeaderComponent("${perfCycle.leaderCode}","${perfCycle.leaderName}")
    initbusiDrcComponent("${perfCycle.busiDrc}","${perfCycle.busiDrcDescr}")
	//初始化部门
	initDept("${perfCycle.department1}","${perfCycle.department2}","${perfCycle.department3}");
	if("${perfCycle.m_umState}"=="V"){
		$("#saveBtn").attr("disabled",true);
	}
	if($("#department1").find("option[value="+$.trim("${perfCycle.department1}")+"]").length == 0){
    	appendOption("department1","${perfCycle.department1}","${perfCycle.dept1Descr}");
    	$("#department1").val("${perfCycle.department1}");
    	$("#dept1Descr").val("${perfCycle.dept1Descr}");
	}	
	if($("#department2").find("option[value="+$.trim("${perfCycle.department2}")+"]").length == 0){
    	appendOption("department2","${perfCycle.department2}","${perfCycle.dept2Descr}");
    	$("#department2").val("${perfCycle.department2}");
    	$("#dept2Descr").val("${perfCycle.dept2Descr}");
	}
	setTimeout(function(){
		if($("#department3").find("option[value="+$.trim("${perfCycle.department3}")+"]").length == 0){
			appendOption("department3","${perfCycle.department3}","${perfCycle.dept3Descr}")
		}
		$("#department3").val("${perfCycle.department3}");
		$("#dept3Descr").val("${perfCycle.dept3Descr}");
	},200)
	
});
//重写修改一级部门方法，可手动添加不属于该一级部门的二级部门
function changeDepartment1(){
	var department1=$("#department1").val();
	$.ajax({
		url:"${ctx}/admin/department1/changeDepartment1?code="+department1,
		type:'post',
		data:{},
		dataType:'json',
		cache:false,
		async:false,
		error:function(obj){
			
		},
		success:function(obj){
			if (obj){
				department2Text=obj;
				$("#department2").html(obj.strSelect).trigger("onchange");
				$("#department2").searchableSelect();
			}
		}
	}); 
}
//重写修改二级部门方法
function changeDepartment2(){
	var department2=$("#department2").val();
	$.ajax({
		url:"${ctx}/admin/department2/changeDepartment2?code="+department2,
		type:'post',
		data:{},
		dataType:'json',
		cache:false,
		async:false,
		error:function(obj){
			
		},
		success:function(obj){
			if (obj){
				department3Text=obj;
				$("#department3").html(obj.strSelect).trigger("onchange");
				$("#department3").searchableSelect();
			}
		}
	});
}
//添加二级部门
function addDepartment2(){
	Global.Dialog.showDialog("Department2Add",{
		title:"搜寻--二级部门信息",
		url:"${ctx}/admin/department2/goCode",
		height:600,
		width:1000,
		returnFun:function(data){
			setDepartment2(data.code,data.code+' '+data.desc1);
		}
	});
}

function addDepartment1(){
	Global.Dialog.showDialog("Department1Add",{
		title:"搜寻--一级级部门信息",
		url:"${ctx}/admin/department1/goCode",
		height:600,
		width:1000,
		returnFun:function(data){
			if($("#department1").find("option[value="+data.code+"]").length == 0){
				appendOption("department1",data.code,data.desc1);

			}			
			$("#department1").val(data.code);
			changeDepartment1();
			getDescrByCode('department1','dept1Descr');
			setDepartment2(data.code,data.code+' '+data.desc1);
		}
	});
}

//添加三级部门
function addDepartment3(){
	Global.Dialog.showDialog("Department3Add",{
		title:"搜寻--三级部门信息",
		url:"${ctx}/admin/department3/goCode",
		height:600,
		width:1000,
		returnFun:function(data){	
			setDepartment3(data.code,data.code+' '+data.desc1);
		}
	});
}

function appendOption(id, code, desc1){
	$("#"+id).append("<option value='"+code+"'>"+code+" "+desc1+"</option>");
	$("#"+id).searchableSelect();
}

//重置二级部门下拉框
function setDepartment2(department2,department2Descr){
	if(department2!="" && department2Text.strSelect.indexOf(department2Descr)==-1){
		department2Text.strSelect+="<option value="+department2+">"+department2Descr+"</option>";
	}
	$("#department2").html(department2Text.strSelect).trigger("onchange");
	if(department2!=""){
		$("#department2").val(department2).trigger("onchange");
	}
}

//重置三级部门下拉框
function setDepartment3(department3,department3Descr){
	if(department3!="" && department3Text.strSelect.indexOf(department3Descr)==-1){
		department3Text.strSelect+="<option value="+department3+">"+department3Descr+"</option>";
	}
	$("#department3").html(department3Text.strSelect).trigger("onchange");
	if(department3!=""){
		$("#department3").val(department3).trigger("onchange");
	}
}

//保存
function save(){
	var role=$("#role").val();
	var roleDescr=$("#roleDescr").val();
	var empCode=$("#empCode").val();
	var empName=$("#empName").val();
	var department1=$("#role").val();
	var perfPer=$("#perfPer").val();
	var isCalcDeptPerf=$("#isCalcDeptPerf").val();
	var isCalcPersonPerf=$("#isCalcPersonPerf").val();
	var isRepeated=false;
	var pk=$("#pk").val();
	var rows = $(top.$("#iframe_addcount")[0].contentWindow.document.getElementById("gxrDataTable")).jqGrid("getRowData");
	if(rows.length>0){
		for(var i=0;i<rows.length;i++){
			if(rows[i].role==role && rows[i].empcode==empCode && rows[i].pk!=pk){
				isRepeated=true;
			};
		}
	}
	if(role==""){
		art.dialog({
			content: "请选择角色！",
		});
		return;
	}
	if(empCode==""){
		art.dialog({
			content: "请输入员工编号！",
		});
		return;
	}
	if(perfPer==""){
		art.dialog({
			content: "请输入业绩占比！",
		});
		return;
	}
	if(department1==""){
		art.dialog({
			content: "请选择一级部门！",
		});
		return;
	}
	if(isCalcDeptPerf==""){
		art.dialog({
			content: "请选择是否统计部门业绩！",
		});
		return;
	}
	if(isCalcPersonPerf==""){
		art.dialog({
			content: "请选择是否统计个人业绩！",
		});
		return;
	}
	if(isNaN(perfPer)){
		art.dialog({
			content: "输入的业绩占比不是有效的数值，请重新输入！",
		});
		return;
	}
	if(isRepeated){
		art.dialog({
			content: "该业绩明细中的角色["+roleDescr+"]已经存在员工["+empName+"]！",
		});
		return;
	}
	var datas = $("#dataForm").jsonForm();
	Global.Dialog.returnData = datas;
	closeWin();
}
//初始化部门领导，component,用于实时更新condition中的department1
function initLeaderComponent(leaderCode,leaderName){
	$("#leaderCode").openComponent_employee({
		showValue:leaderCode,
		showLabel:leaderName,
		callBack:function(data){
			$("#leaderName").val(data.namechi);
		},
		condition:{department1:$("#department1").val()}
	});
	if(leaderCode==""){
    	$("#openComponent_employee_leaderCode").val("");
    }
}
//初始化业务主任component,用于实时更新condition中的department1
function initbusiDrcComponent(busiDrc,busiDrcDescr){
	$("#busiDrc").openComponent_employee({
		showValue:busiDrc,
		showLabel:busiDrcDescr,
		callBack:function(data){
			$("#busiDrcDescr").val(data.namechi);
		},
		condition:{department1:$("#department1").val()}
	});
	if(busiDrc==""){
    	$("#openComponent_employee_busiDrc").val("");
    }
}
//初始化部门
function initDept(dept1,dept2,dept3){
	$("#department1").val(dept1).trigger("onchange");
	$("#department2").val(dept2).trigger("onchange");
	$("#department3").val(dept3).trigger("onchange");
}
</script>
<style type="text/css">

</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system"
						onclick="save()">保存</button>
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					 <input type="hidden"  id="roleDescr" name="roleDescr" value="${perfCycle.roleDescr }"/>
					 <input type="hidden"  id="dept1Descr" name="dept1Descr" value="${perfCycle.dept1Descr }"/>
					 <input type="hidden"  id="dept2Descr" name="dept2Descr" value="${perfCycle.dept2Descr }"/>
					 <input type="hidden"  id="dept3Descr" name="dept3Descr" value="${perfCycle.dept3Descr }"/>
					 <input type="hidden"  id="empName" name="empName" value="${perfCycle.empName }"/>
					 <input type="hidden" id="leaderName" name="leaderName" value="${perfCycle.leaderName }"/>
					 <input type="hidden" id="isCalcDeptPerfDescr" name="isCalcDeptPerfDescr" value="${perfCycle.isCalcDeptPerfDescr }"/>
					 <input type="hidden" id="isCalcPersonPerfDescr" name="isCalcPersonPerfDescr" value="${perfCycle.isCalcPersonPerfDescr }"/>
					 <input type="hidden" id="busiDrcDescr" name="busiDrcDescr" value="${perfCycle.busiDrcDescr }" />
					 <input type="hidden" id="pk" name="pk" value="${perfCycle.pk }"/>
					<house:token></house:token>
					<ul class="ul-form">
						<div class="row">
							<li><label>角色</label> <house:dict id="role" dictCode=""
									sql="select Code,rtrim(Code)+' '+Descr Desc1 from tRoll where Code in ('00','01','24')"
									sqlValueKey="Code" sqlLableKey="Desc1" onchange="getDescrByCode('role','roleDescr')"
									value="${perfCycle.role }"></house:dict>
							</li>
							<li>
								<label>一级部门</label> 
								<div class="input-group">
									<house:department1 id="department1" style="position:relative;top:1px;left:1px;" 
										onchange="changeDepartment1();getDescrByCode('department1','dept1Descr');"
										value="${perfCycle.department1 }">
									</house:department1>
									<button type="button" class="btn btn-system" 
											onclick="addDepartment1()">
											<span class="glyphicon glyphicon-search"></span>
									</button>
								</div>
							</li>
						</div>
						<div class="row">
							<li><label>员工编号</label> <input type="text"
								style="width:160px;" id="empCode" name="empCode" />
							</li>
							<li><label>二级部门</label>  
								<div class="input-group">
									<house:department2 id="department2"
											dictCode="${perfCycle.department1 }" value="${perfCycle.department2 }"
											style="position:relative;top:1px;left:1px;" 
											onchange="changeDepartment2();getDescrByCode('department2','dept2Descr')">
									</house:department2>
									<button type="button" class="btn btn-system" 
											onclick="addDepartment2()">
											<span class="glyphicon glyphicon-search"></span>
									</button>
								</div>
							</li>
						</div>
						<div class="row">
							<li><label>业绩占比</label> <input type="text"
								style="width:160px;" id="perfPer" name="perfPer" value="${perfCycle.perfPer }" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"/>
							</li>
							<li> <label>三级部门</label>
								<div class="input-group">
									<house:department3 id="department3"
											dictCode="${perfCycle.department2 }" value="${perfCycle.department3 }"
											style="position:relative;top:1px;left:1px;" 
											onchange="getDescrByCode('department3','dept3Descr')">
									</house:department3>
									<button type="button" class="btn btn-system" 
											onclick="addDepartment3()">
											<span class="glyphicon glyphicon-search"></span>
									</button>
								</div>
							</li>
						</div>
						<div class="row">
							<li><label>统计部门业绩</label> <house:xtdm id="isCalcDeptPerf"
									dictCode="YESNO" value="${perfCycle.isCalcDeptPerf!=''?perfCycle.isCalcDeptPerf:0 }" onchange="getDescrByCode('isCalcDeptPerf','isCalcDeptPerfDescr')"></house:xtdm>
							</li>
							<li><label>部门领导编号</label> <input type="text"
								style="width:160px;" id="leaderCode" name="leaderCode" /></li>
						</div>
						<div class="row">
							<li><label>统计个人业绩</label> <house:xtdm id="isCalcPersonPerf"
									dictCode="YESNO" value="${perfCycle.isCalcPersonPerf!=''?perfCycle.isCalcPersonPerf:0 }" onchange="getDescrByCode('isCalcPersonPerf','isCalcPersonPerfDescr')"></house:xtdm>
							</li>
							<li><label>业务主任</label> <input type="text"
								style="width:160px;" id="busiDrc" name="busiDrc"/>
							</li>
						</div>
						<div class="row">
							<li><label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks"
									style="overflow-y:scroll; overflow-x:hidden; height:75px; "
									/>${perfCycle.remarks }</textarea></li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
