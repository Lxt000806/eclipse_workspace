<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加一级部门</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_company.js?v=${v}" type="text/javascript"></script>
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
				<input type="hidden" id="expired" name="expired" value="${department1.expired }"/>
				<input type="hidden" id="department" name="department" value="${department1.department }"/>
				<ul class="ul-form">
					<div class="validate-group row" >
						<li>
							<label>部门编号</label>
							<input type="text" id="code" name="code" value="${department1.code}" placeholder="保存时生成" readonly="readonly" />	
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>名称</label>
							<input type="text" id="desc2" name="desc2" value="${department1.desc2}" />	
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>公司编号</label>
							<input type="text" id="cmpCode" name="cmpCode" value="${department1.cmpCode}" />	
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>部门类型</label> 
							<house:xtdm id="DepType" dictCode="DEPTYPE" style="width:160px;" value="${department1.depType}"/>
						</li>
						<li class="form-validate">
							<label>编制数</label>
							<input type="text" id="planNum" name="planNum" value="${department1.m_umState=='A'?0:department1.planNum}" />	
						</li>
						<li>
							<label>显示顺序</label>
							<input type="text" id="dispSeq" name="dispSeq" value="${department1.m_umState=='A'?0:department1.dispSeq}" />	
						</li>
						<li>
							<label>业务类型</label>
							<house:xtdm id="BusiType" dictCode="BUSITYPE" style="width:160px;" value="${department1.busiType}"/>
						</li>
						<li>
                            <label>外部渠道</label>
                            <house:xtdm id="isOutChannel" dictCode="YESNO" value="${department1.isOutChannel}"/>
                        </li>
						<li id="chekbox_show">
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${department1.expired }",
								onclick="checkExpired(this)" ${department1.expired=="T"?"checked":"" }/>
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
			showLabel:"${department1.compName}",
			showValue:"${department1.cmpCode}",
			callBack:validateRefresh_company
		});
		
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: { 
				desc2:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}
					}  
				},
				DepType:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						} 
					}  
				},
				openComponent_company_cmpCode:{  
						validators: {  
							notEmpty: {  
								message: "请输入完整的信息"  
							}              
						}  
					},
			}
		});

		
		if ("V" == "${department1.m_umState}") {
			$("#saveBtn").hide();
			disabledForm("dataForm");
		}else if("A"== "${department1.m_umState}"||"C"== "${department1.m_umState}"){
			$("#chekbox_show").hide();
		}else if("U"== "${department1.m_umState}"){
			$("#code").attr("readonly","readonly");
		}
		
		

	
			
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator("validate");/* 提交验证 */
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return; /* 验证失败return */
			
			//验证
			var datas = $("#dataForm").serialize();
			$.ajax({
				url:"U" == "${department1.m_umState}"?"${ctx}/admin/department1/doUpdate":"${ctx}/admin/department1/doSave",
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
	
	function validateRefresh_company(){
			 $("#dataForm").data("bootstrapValidator")
		                   .updateStatus("openComponent_company_cmpCode", "NOT_VALIDATED",null)   
		                   .validateField("openComponent_company_cmpCode");                    
		}
	//校验函数
	
	

</script>
</html>
