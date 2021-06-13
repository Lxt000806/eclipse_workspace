<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>毛利率业绩配置--增加</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
	.ul-form div.row li label {
		width: 120px;
	}
</style>
<script type="text/javascript">
	$(function() {
		if ("${softPerfPrePer.m_umState}"=="V"){
			$("#saveBtn").remove();
		}
		Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1");
		Global.LinkSelect.setSelect({firstSelect:"department1",
			firstValue:"${softPerfPrePer.department1}",
		});
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				department1 : {
					validators : {
						notEmpty : {
							message : '一级部门不能为空'
						}
					}
				},
				isOutSideEmp : {
					validators : {
						notEmpty : {
							message : '是否外部人员不能为空'
						}
					}
				},
				per : {
					validators : {
						notEmpty : {
							message : '发放比例不能为空'
						},
						numeric: {
	                           message: '只能输入数字', 
	                    }
					}
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});
	
	function save() {
		$("#dataForm").bootstrapValidator('validate');
		if (!$("#dataForm").data('bootstrapValidator').isValid())
			return;
		
		var url="${ctx}/admin/softPerfPrePer/doSave"
		if ("${softPerfPrePer.m_umState}"=="M"){
			url = "${ctx}/admin/softPerfPrePer/doUpdate";
		}
		var datas = $("#dataForm").serialize();
		$.ajax({
			url : url,
			type : "post",
			data : datas,
			dataType : "json",
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : "保存数据出错~"
				});
			},
			success : function(obj) {
				if (obj.rs) {
					art.dialog({
						content : obj.msg,
						time : 1000,
						beforeunload : function() {
							closeWin();
						}
					});
				} else {
					$("#_form_token_uniq_id").val(obj.token.token);
					art.dialog({
						content : obj.msg,
						width : 200
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
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn" onclick="save()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="pk" name="pk" value="${softPerfPrePer.pk}"/>
					<input type="hidden" id="expired" name="expired" value="${softPerfPrePer.expired}"/>
					<input type="hidden" id="m_umState" name="m_umState" value="${softPerfPrePer.m_umState}"/>
					<ul class="ul-form">
						<li class="form-validate">
							<label >一级部门</label>
							<select id="department1" name="department1"></select>
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>是否外部人员</label> <house:xtdm id="isOutSideEmp"
								dictCode="YESNO" value="${softPerfPrePer.isOutSideEmp }"></house:xtdm>
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>提成发放比例</label> <input type="text"
							id="per" name="per" 
							value="${softPerfPrePer.per}" />
						</li>
						<li hidden="true" >
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${softPerfPrePer.expired}" 
								onclick="checkExpired(this)" ${softPerfPrePer.expired=="T"?"checked":""}/>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
