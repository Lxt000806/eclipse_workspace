<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加工资类型</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
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
             	<house:token></house:token>
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" id="expired" name="expired" value="${salaryType.Expired }"/>
				<ul class="ul-form">
					<div class="validate-group" >
						<li class="form-validate">
							<label><span class="required">*</span>工资类型编号</label>
							<input type="text" id="code" name="code" value="${salaryType.code}"  />	
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>工资类型名称</label>
							<input type="text" id="descr" name="descr" value="${salaryType.Descr}" />	
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>是否计成本</label>
							<house:xtdm id="isCalCost" dictCode="YESNO" value="${salaryType.IsCalCost}"></house:xtdm>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>是否需出纳签字</label>
							<house:xtdm id="isSign" dictCode="YESNO" value="${salaryType.IsSign}"></house:xtdm>
						</li>
						<li>
							<label>工种类型1</label> 
							<select id="workType1" name="workType1" value="${salaryType.WorkType1 }"></select>
						</li>
						<li>
							<label>工种类型2</label> 
							<house:xtdm id="workType2" dictCode="WORKTYPE2" style="width:160px;" value="${salaryType.WorkType2}"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>计项目经理成本</label>
							<house:xtdm id="isCalProjectCost" dictCode="YESNO" value="${salaryType.IsCalProjectCost}"></house:xtdm>
						</li>
						<li id="chekbox_show">
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${salaryType.Expired }",
								onclick="checkExpired(this)" ${salaryType.Expired=="T"?"checked":"" }/>
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
		Global.LinkSelect.initSelect("${ctx}/admin/workType1/workTypeForWorker","workType1","workType2","salaryType");
		Global.LinkSelect.setSelect({firstSelect:"workType1",
								firstValue:"${salaryType.WorkType1}",
								secondSelect:"workType2",
								secondValue:"${salaryType.WorkType2}"});	
		
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {
				code:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}
					}  
				},
				descr:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}
					}  
				},
				isCalCost:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						} 
					}  
				},
				isSign:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						} 
					}  
				},
				isCalProjectCost:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						} 
					}  
				},
			}
		});

		
		if ("V" == "${salaryType.m_umState}") {
			$("#saveBtn").hide();
			$("#chekbox_show").hide();
			disabledForm("dataForm");
		}else if("A"== "${salaryType.m_umState}"||"C"== "${salaryType.m_umState}"){
			$("#chekbox_show").hide();
			$("#isCalCost option[value='']").remove();
			$("#isSign option[value='']").remove();
			$("#isCalProjectCost option[value='']").remove();
		}else if("M"== "${salaryType.m_umState}"){
			$("#code").prop("readonly","readonly");
			$("#isCalCost option[value='']").remove();
			$("#isSign option[value='']").remove();
			$("#isCalProjectCost option[value='']").remove();
		}
		
		

	
			
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator("validate");/* 提交验证 */
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return; /* 验证失败return */
			
			//验证
			var datas = $("#dataForm").serialize();
			$.ajax({
				url:"M" == "${salaryType.m_umState}"?"${ctx}/admin/salaryType/doUpdate":"${ctx}/admin/salaryType/doSave",
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
	

	

</script>
</html>
