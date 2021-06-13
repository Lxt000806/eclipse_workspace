<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_workType2.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		.form-search .ul-form li label {
			width: 140px;
		}
	</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search" target="targetFrame">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>工种分类2编号</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${workType2.code}" autofocus/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>工种分类2名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${workType2.descr}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>工种分类1</label>
								<house:dict id="workType1" dictCode="" sql="select rtrim(Code)+' '+Descr fd,Code from tWorkType1 order by DispSeq " 
									sqlValueKey="Code" sqlLableKey="fd" value="${workType2.workType1}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>显示顺序</label>
								<input type="text" id="dispSeq" name="dispSeq" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="0"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>统计类型</label>
								<house:dict id="calType" dictCode="" sql=" select rtrim(IBM)+' '+NOTE tj,IBM from tXTDM where ID='CalType' " 
									sqlValueKey="IBM" sqlLableKey="tj" value="${workType2.calType}"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>对应施工阶段</label>
								<house:dict id="prjItem" dictCode="" sql=" select rtrim(IBM)+' '+NOTE fd,IBM from tXTDM where ID='PrjItem' order by IBM " 
									sqlValueKey="IBM" sqlLableKey="fd" value="${workType2.prjItem}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>是否计算结算成本</label>
								<house:xtdm id="isCalProjectCost" dictCode="YESNO" style="width:160px;" value="0"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>工资</label>
								<input type="text" id="salary" name="salary" style="width:160px;" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" value="0"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>工种分类12</label>
								<house:dict id="workType12" dictCode="" sql="select rtrim(Code)+' '+Descr fd,Code from tWorkType12 order by Code " 
									sqlValueKey="Code" sqlLableKey="fd" value="${workType2.workType12}"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>工资是否二次审核</label>
								<house:xtdm id="isConfirmTwo" dictCode="YESNO" style="width:160px;" value="1"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>是否项目经理申请</label>
								<house:xtdm id="isPrjApp" dictCode="YESNO" style="width:160px;" value="1"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>工资申请后不允许领料</label>
								<input type="text" id="denyOfferWorkType2" name="denyOfferWorkType2"/>
								<%-- <house:xtdm id="denyOfferWorkType2" dictCode="YESNO" style="width:160px;" value="1"/> --%>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>工资控制类型</label>
								<house:xtdm id="salaryCtrlType" dictCode="SALACTRLTYPE" style="width:160px;" value="0"/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function() {

	$("#denyOfferWorkType2").openComponent_workType2({
		showValue: "${workType2.denyOfferWorkType2}",
		showLabel: "${workType2.denyOfferWorkType2Descr}"
	});

	if ("C" == "${workType2.m_umState}") {
		$("#dispSeq").val("${workType2.dispSeq}");
		$("#isCalProjectCost").val("${workType2.isCalProjectCost}");
		$("#salary").val("${workType2.salary}");
		$("#isConfirmTwo").val("${workType2.isConfirmTwo}");
		$("#isPrjApp").val("${workType2.isPrjApp}");
		$("#denyOfferWorkType2").val("${workType2.denyOfferWorkType2}");
	}
	
	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			code:{ 
				validators: {  
					notEmpty: {  
						message: "工种类型2编号不允许为空"  
					},
					remote: {
			            message: "该工种类型2编号已存在",
			            url: "${ctx}/admin/workType2/checkCode",
			            delay:1000, 	//每输入一个字符，就发ajax请求，服务器压力还是太大，
			            type:"post",	// 设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
			            cache: false
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
			workType1:{ 
				validators: {  
					notEmpty: {  
						message: "工种类型1未选择"  
					}
				}  
			},
			dispSeq:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}
				}  
			},
			calType:{ 
				validators: {  
					notEmpty: {  
						message: "统计类型未选择"  
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
			salary:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}
				}  
			},
			salaryCtrlType:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}
				}  
			},
		}
	});
	
	// 获得原始数据
	var originalData = $("#page_form").serialize();

	$("#closeBut").on("click",function(){
		var changeData = $("#page_form").serialize();
		if (originalData != changeData) {
			art.dialog({
				content: "数据已变动,是否保存？",
				width: 200,
				okValue: "确定",
				ok: function () {
					doSave();
				},
				cancelValue: "取消",
				cancel: function () {
					closeWin();
				}
			});
		} else {
			closeWin();
		}
		
	});

	//保存
	$("#saveBtn").on("click",function(){
		doSave();
	});
});

function doSave(){
	$("#page_form").bootstrapValidator("validate");/* 提交验证 */
	if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
		
	var datas = $("#page_form").serialize();

	$.ajax({
		url:"${ctx}/admin/workType2/doSave",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			alert('error');
			showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
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
}

</script>
</html>
