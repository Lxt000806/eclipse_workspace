<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE HTML>
<html>
<head>
<title>礼品领用干系人-新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">

$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
		
			role: {
				validators: {  
		            notEmpty: {  
		            message: '角色不能为空'  
		            }  
	            }  
			},
			
		},
		submitButtons : 'input[type="submit"]'
	});	
});
$(function(){
	$("#role").openComponent_roll({showLabel:"${giftStakeholder.roleDescr}",showValue:"${giftStakeholder.role}",condition:{code:'00'},callBack: function(data){$("#roleDescr").val(data["descr"]) }});
	$("#empCode").openComponent_employee({showLabel:"${giftStakeholder.empDescr}",showValue:"${giftStakeholder.empCode}" ,
		callBack: function(data){ $("#empDescr").val(data.namechi) ,$("#department1").val(data.department1).change(), 
		 setTimeout(function(){
	          $("#department2").val(data.department2);
	          },100)
		}});
/* 	$("#openComponent_employee_empCode").attr("readonly",true); */
	$("#openComponent_roll_role").attr("readonly",true);
	
$("#saveBtn").on("click",function(){
	if ($("#role").val()!='00' && $.trim($("#role").val())!='01'){
		art.dialog({
			content: "角色请填业务员或设计师！",
			width: 200
		});
		return false;
	}	
	if ($("#sharePer").val()==''){
		$.trim($("#sharePer").val(0.00));
		
	}	
	var sdepartment1=$("#department1").find("option:selected").text();	
	sdepartment1 = sdepartment1.split(' ');//先按照空格分割成数组
	sdepartment1=sdepartment1[1];
	document.getElementById("department1Descr").value=sdepartment1;
	if ($.trim($("#department2").val())!=''){	
	 	var sdepartment2=$("#department2").find("option:selected").text();
		sdepartment2 = sdepartment2.split(' ');//先按照空格分割成数组
		sdepartment2=sdepartment2[1];
		document.getElementById("department2Descr").value=sdepartment2;
	}
	var empCode =$.trim($("#empCode").val());
	var department1 =$.trim($("#department1").val());
	var department2 =$.trim($("#department2").val());
	if (empCode==""){
		art.dialog({content: "员工编号不能为空",width: 200});
		return false;
	}
	if (department1==""){
		art.dialog({content: "一级部门不能为空",width: 200});
		return false;
	}
		
	var selectRows = [];
	var datas=$("#page_form").jsonForm();
 	selectRows.push(datas);
	Global.Dialog.returnData = selectRows;
	closeWin();
	}); 

});	
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "   id="saveBtn">保存</button>
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="page_form" class="form-search" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="${giftApp.m_umState}"/>
				<ul class="ul-form">
						<li class="form-validate">
							<label ><span class="required">*</span>角色</label>
							<input type="text" id="role" name="role" style="width:160px;" value="${giftStakeholder.role}"/>     
						</li>
						<li class="form-validate">
							<label ><span class="required">*</span>员工</label>
							<input type="text" id="empCode" name="empCode" style="width:160px;"  value="${giftStakeholder.empCode}"/>    
						</li>
						<li class="form-validate">
							<label ><span class="required">*</span>一级部门</label> 
							<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${giftStakeholder.department1 }" ></house:department1>   
						</li>
						<li>
							<label>二级部门</label>
							<house:department2 id="department2" dictCode="${giftStakeholder.department1 }" value="${giftStakeholder.department2 }"  ></house:department2>
						</li>
						<li class="form-validate">
							<label >分摊比例</label>
						    <input type="text" id="sharePer" name="sharePer" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${giftStakeholder.sharePer}"  />
						</li>
						<li hidden="true">
							<label>一级部门名称</label>
							<input type="text" id="department1Descr" name="department1Descr" style="width:160px;" value="${giftStakeholder.department1Descr}"/>
						</li>
						<li hidden="true">
							<label>二级部门名称</label>
							<input type="text" id="department2Descr" name="department2Descr" style="width:160px;" value="${giftStakeholder.department2Descr}"/>
						</li>
						<li hidden="true">
							<label>角色名称</label>
							<input type="text" id="roleDescr" name="roleDescr" style="width:160px;" value="${giftStakeholder.roleDescr}"/>
						</li>
					    <li hidden="true">
							<label>员工姓名</label>
							<input type="text" id="empDescr" name="empDescr" style="width:160px;" value="${giftStakeholder.empDescr}"/>
						</li>
						<li hidden="true">
							<label>最后修改时间</label>
							<input type="text" id="lastUpdate" name="lastUpdate"  value="<fmt:formatDate value='${giftStakeholder.lastUpdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
					   </li>	
						<li hidden="true">
							<label>最后修改人</label>
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy"  value="${giftStakeholder.lastUpdatedBy}"/>
					   </li>
					</ul>
			</form>
		</div>
	</div>			
</div>
</body>
</html>
