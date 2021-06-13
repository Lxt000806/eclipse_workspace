<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>工程完工客户结算</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator("validate");
	if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
	var prjDepartment1=$("#prjDepartment1").val();
	var prjDepartment2=$("#prjDepartment2").val();

	var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/gcwg/doKhjs",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    	}else{
				art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	});
	
}
//校验函数
$(function() {
	$("#dataForm").bootstrapValidator({
        message : "This value is not valid",
        feedbackIcons : {/*input状态样式图片*/
            validating : "glyphicon glyphicon-refresh"
        },
        fields: {
        	realDesignFee: {
        		code: { 
	            notEmpty: { 
	            	message: "客户编号不能为空"  
	            }
	        }
      	}
      	},
        submitButtons : 'input[type="submit"]'
    });
	//$("#prjDeptLeader").openComponent_employee({dep1Type:"3"});
	$("#prjDeptLeader").openComponent_employee({
		showValue:"${leaderNumber}",
		showLabel:"${leaderDescr}",
		callBack:function(data){
			if(data.number!="${leaderNumber}"){//有修改才触发，防止点击input框更新部门
				$("#prjDepartment1").val(data.department1).trigger("onchange");
				$("#prjDepartment2").val(data.department2);
			}
		}
	});
});
</script>
</head>
<body>
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="save()">保存</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
        	<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<input type="hidden" id="code" name="code" value="${customer.code }" />
            	<input type="hidden" id="status" name="status" value="${customer.status }" />
            	<ul class="ul-form">
            		<div class="col-sm-12">
            		<li class="form-validate">
            			<label>凭证号</label>
            			<input type="text" id="checkDocumentNo" name="checkDocumentNo" style="width:160px;" maxlength="20"/>
            		</li>
            		</div>
            		<div class="col-sm-12">
            		<li class="form-validate">
            			<label>工程部经理</label>
            			<input type="text" id="prjDeptLeader" name="prjDeptLeader" style="width:160px;"/>
            		</li>
            		</div>
            		<div class="col-sm-12">
            		<li><label>工程事业部</label> <house:department1 id="prjDepartment1"
							onchange="changeDepartment1(this,'prjDepartment2','${ctx }')"
							value="${dept1}" ></house:department1>
					</li>
					</div>
					<div class="col-sm-12">
					<li><label>工程部</label> <house:department2 id="prjDepartment2"
							dictCode="${dept1}"
							value="${dept2}"
							></house:department2>
					</li>
					</div>
            		<div class="col-sm-12">
            		<li class="form-validate">
            			<label>结算日期</label>
            			<input type="text" style="width:160px;" id="custCheckDate" name="custCheckDate" class="i-date" 
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
            			value="<fmt:formatDate value='${customer.custCheckDate }' pattern='yyyy-MM-dd'/>"/>
            		</li>
            		</div>
            	</ul>
            </form>
         </div>
     </div>
</div>
</body>
</html>
