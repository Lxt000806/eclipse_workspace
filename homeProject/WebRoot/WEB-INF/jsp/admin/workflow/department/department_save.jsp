<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加部门</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_company.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
		<div class="panel-body" >
			<div class="btn-group-xs" >
		      	<button type="button" id="saveBtn" class="btn btn-system "  >保存</button>
				<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
             <input type="hidden" id="m_umState" name="m_umState" value="${map.m_umState}"  />
             <input type="hidden" id="expired" name="expired" value="${map.expired }"/>
             	<house:token></house:token>
				<ul class="ul-form">
					<div class="validate-group row" >
						<li>
							<label>部门编号</label>
							<input type="text" id="code" name="code" value="${map.code}" placeholder="保存时生成" readonly="readonly" />	
						</li>
						<li >
							<label>名称</label>
							<input type="text" id="desc2" name="desc2" value="${map.desc2}" />	
						</li>
						<li >
							<label>公司编号</label>
							<input type="text" id="cmpCode" name="cmpCode" value="${map.cmpcode}" />	
						</li>
						<li><label>上级部门</label> <input type="text"
								style="width:160px;" id="higherDep" name="higherDep" />
						</li>
						<li >
							<label>部门类型</label> 
							<house:xtdm id="depType" dictCode="DEPTYPE" style="width:160px;" value="${map.deptype}"/>
						</li>
						<li >
							<label>编制数</label>
							<input type="text" id="planNum" name="planNum" value="${map.m_umState=='A'?0:map.plannum}" />	
						</li>
						<li>
							<label>业务类型</label>
							<house:xtdm id="busiType" dictCode="BUSITYPE" style="width:160px;" value="${map.busitype}"/>
						</li>
						<li><label>部门领导</label> <input type="text"
								style="width:160px;" id="leaderCode" name="leaderCode" />
						</li>
						<li>
							<label>显示顺序</label>
							<input type="text" id="dispSeq" name="dispSeq" value="${map.m_umState=='A'?0:map.dispseq}" />	
						</li>
						<li>
							<label>是否实际部门</label>
							<house:xtdm id="isActual" dictCode="YESNO" style="width:160px;" value="${map.isactual}"/>
						</li>
						<li>
							<label>是否流程部门</label>
							<house:xtdm id="isProcDept" dictCode="YESNO" style="width:160px;" value="${map.m_umState=='A'?'1':map.isprocdept}"/>
						</li>
						<li>
                            <label>外部渠道</label>
                            <house:xtdm id="isOutChannel" dictCode="YESNO" value="${map.m_umState=='A'?'0':map.isoutchannel}"/>
                        </li>
					</div>
				</ul>
			</form>
		</div>
	</div>
</div>	
</body>
<script type="text/javascript">
	$(function(){
		$("#cmpCode").openComponent_company({
			showLabel:"${map.cmpdescr}",
			showValue:"${map.cmpcode}",
		});
		$("#higherDep").openComponent_department({
			showLabel:"${map.higherdepdescr}",
			showValue:"${map.higherdep}",
			condition:{code:$("#code").val()}
		});
		$("#leaderCode").openComponent_employee({
			showLabel:"${map.leadername}",
			showValue:"${map.leadercode}",
		}); 
		//changeIsActual();

		if ("V" == "${map.m_umState}") {
			$("#saveBtn").attr("disabled","true");
			disabledForm("dataForm");
		}else if("A"== "${map.m_umState}"){
			$("#expired_show").parent().hide();
		}else if("M"== "${map.m_umState}"){
			$("#code").prop("readonly","readonly");
			$("#openComponent_department_higherDep").attr("readonly",true).next("button").attr("disabled",true);
			//if("${map.hasEmp}">0){
			$("#isActual").attr("disabled",true);
			//}
		}
		
		$("#saveBtn").on("click",function(){
			var cmpCode=$("#cmpCode").val();
			var desc2=$("#desc2").val();
			var depType=$("#depType").val();
			var isProcDept=$("#isProcDept").val();
			if(cmpCode==""){
				art.dialog({
					content: "请选择公司编号！",
				});
				return;
			}
			if(desc2==""){
				art.dialog({
					content: "请填写名称！",
				});
				return;
			}
			if(depType==""){
				art.dialog({
					content: "请选择部门类型！",
				});
				return;
			}
			if(isProcDept==""){
				art.dialog({
					content: "请选择是否流程部门！",
				});
				return;
			}
			
			$("#isActual").removeAttr("disabled");
			var datas = $("#dataForm").serialize();
			$.ajax({
				url:"${ctx}/admin/department/doSave",
				type: 'post',
				data: datas,
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
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
			    		$("#_form_token_uniq_id").val(obj.token.token);
			    		art.dialog({
							content: obj.msg,
							width: 200
						});
			    	}
			    }
			 });
		});
	});
/* 	function changeIsActual(){//部门领导都允许修改，虚拟部门修改事件取消掉20200311 by cjg
		var isActual=$("#isActual").val();
		if(isActual=="1"){
			$("#leaderCode").setComponent_employee({
				showLabel:"${map.leadername}",
				showValue:"${map.leadercode}",
			}); 
			if($("#leaderCode").val().trim()!=""){
				$("#openComponent_employee_leaderCode").attr("readonly",true).next("button").attr("disabled",true);
			}
		}else{
			$("#openComponent_employee_leaderCode").removeAttr("readonly").next("button").removeAttr("disabled");
		}
	} */
</script>
</html>
