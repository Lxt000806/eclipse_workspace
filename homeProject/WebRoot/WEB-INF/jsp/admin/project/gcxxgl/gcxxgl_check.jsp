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
//校验函数
$(function() {
	$("#dataForm").bootstrapValidator({
        message : 'This value is not valid',
        feedbackIcons : {/*input状态样式图片*/
            validating : 'glyphicon glyphicon-refresh'
        },
        fields: {
        	/* checkOutDate: { 
        		validators:{
                    notEmpty: { 
	            		message: "实际结算时间不能为空"  
	            	}
                }
        	}, */
        	/* prjDepartment1: { 
        		validators:{
                   notEmpty: { 
	            	   message: "工程事业部不能为空"  
	                }
                }
	            
        	}, */
        	/* prjDepartment2: { 
        		validators:{
                   notEmpty: { 
	            	   message: "工程部不能为空"  
	               }
                } 
        	} */
      	},
        submitButtons : 'input[type="submit"]'
    });
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
function save(){
	/* if ($.trim($("#prjDeptLeader").val())==''){
		art.dialog({
			content: "请输入完整的信息,工程部经理未选！",
			width: 200
		});
		return false;
	}; */
	/* if ($.trim($("#checkOutDate").val())==''){
		art.dialog({
			content: "实际结算时间不能为空",
			width: 200
		});
		return false;
	}; */
	$("#dataForm").bootstrapValidator("validate");
	if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
	var prjDepartment1=$("#prjDepartment1").val();
	var prjDepartment2=$("#prjDepartment2").val();

	var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/gcxxgl/dogcjs",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
	    console.log(obj);
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
            	<input type="hidden" id="status" name="status" value="${customer.status }" />
            	<ul class="ul-form">
            		<li class="form-validate">
            			<label>客户编号</label>
            			<input type="text" id="code" name="code" value="${customer.code }" readonly />
            		</li>
            		<li class="form-validate">
            			<label>楼盘</label>
            			<input type="text" id="address" name="address" value="${customer.address }" readonly />
            		</li>	
            		<li class="form-validate">
            			<label>实际结算日期</label>
            			<input type="text" style="width:160px;" id="checkOutDate" name="checkOutDate" class="i-date" 
            			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
            			value="<fmt:formatDate value='${customer.checkOutDate}' pattern='yyyy-MM-dd'/>"/>
            		</li>
            		<li class="form-validate">
            			<label>工程部经理</label>
            			<input type="text" id="prjDeptLeader" name="prjDeptLeader" style="width:160px;" value="${customer.prjDeptLeader}"/>
            		</li>
            		<li class="form-validate">
            			<label>工程事业部</label> 
            			<house:department1 id="prjDepartment1"
							onchange="changeDepartment1(this,'prjDepartment2','${ctx }')"
							value="${dept1}" >
						</house:department1>
					</li>
					<li class="form-validate">
						<label>工程部</label> 
						<house:department2 id="prjDepartment2"
							dictCode="${dept1}"
							value="${dept2}"
							>
						</house:department2>
					</li>
            	</ul>
            </form>
         </div>
     </div>
</div>
</body>
</html>
