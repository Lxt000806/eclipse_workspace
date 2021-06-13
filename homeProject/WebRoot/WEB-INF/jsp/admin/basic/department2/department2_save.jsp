<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加二级部门</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">

	$(function(){

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
				department1:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}
					}  
				}
			}
		});
		if ("V" == "${department2.m_umState}") {
			$("#saveBtn").hide();
			disabledForm("dataForm");
		}else if("A"== "${department2.m_umState}"||"C"== "${department2.m_umState}"){
			$("#chekbox_show").hide();
		}else if("U"== "${department2.m_umState}"){
			$("#code").attr("readonly","readonly");
		}
		
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator("validate");/* 提交验证 */
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return; /* 验证失败return */
			var datas = $("#dataForm").serialize();
			$.ajax({
				url:"U" == "${department2.m_umState}"?"${ctx}/admin/department2/doUpdate":"${ctx}/admin/department2/doSave",
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
//校验函数


</script>

</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
		<div class="panel-body" >
			<div class="btn-group-xs" >
		      	<button type="button" id="saveBtn" class="btn btn-system " onclick="save()" >保存</button>
				<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" id="expired" name="expired" value="${department2.expired }"/>
				<input type="hidden" id="department" name="department" value="${department2.department }"/>
				<ul class="ul-form">
					<div class="validate-group row" >
						<li>
							<label>部门编号</label>
							<input type="text" id="code" name="code" value="${department2.code}" placeholder="保存时生成" readonly="readonly" />	
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>名称</label>
							<input type="text" id="desc2" name="desc2" value="${department2.desc2}" />	
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>一级部门</label> 
							<house:dict id="department1" dictCode=""  sql=" select Code,Desc2 from tDepartment1 where expired = 'F' order by Code " 
							sqlValueKey="Code" sqlLableKey="Desc2"  value="${department2.department1}"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>部门类型</label> 
							<house:xtdm id="DepType" dictCode="DEPTYPE" style="width:160px;" value="${department2.depType}"/>
						</li>
						<li>
							<label>编制数</label>
							<input type="text" id="planNum" name="planNum" value="${department2.planNum}" placeholder="0"/>	
						</li>
						<li>
							<label>显示顺序</label>
							<input type="text" id="dispSeq" name="dispSeq" value="${department2.dispSeq}" placeholder="0"/>	
						</li>
						<li>
							<label>业务类型</label>
							<house:xtdm id="BusiType" dictCode="BUSITYPE" style="width:160px;" value="1"/>
						</li>
						<li>
                            <label>外部渠道</label>
                            <house:xtdm id="isOutChannel" dictCode="YESNO" value="${department2.isOutChannel}"/>
                        </li>
						<li id="chekbox_show">
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${department2.expired }",
								onclick="checkExpired(this)" ${department2.expired=="T"?"checked":"" }/>
						</li>
					</div>
				</ul>
			</form>
		</div>
	</div>
</div>	
</body>
</html>
