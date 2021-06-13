<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_workType2.js?v=${v}" type="text/javascript"></script>
	<style>
	    .form-search .ul-form li label {
	        width: 120px;
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
	                <button type="button" class="btn btn-system " id="closeBtn">
	                    <span>关闭</span>
	                </button>
	            </div>
	        </div>
	    </div>
	    <div class="panel panel-info" style="margin: 0px;">
	        <div class="panel-body">
	            <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
	                <house:token></house:token>
	                <input type="hidden" id="expired" name="expired" value="${prjItem1.expired}"/>
	                <div class="row">
	                    <div class="col-sm-6">
	                        <ul class="ul-form">
	                            <li class="form-validate">
	                                <label><span class="required">*</span>编号</label>
	                                <input type="text" id="code" name="code" style="width:160px;" value="${prjItem1.code}"/>
	                            </li>
	                            <li class="form-validate">
	                                <label><span class="required">*</span>名称</label>
	                                <input type="text" id="descr" name="descr" style="width:160px;"
	                                       value="${prjItem1.descr}"/>
	                            </li>
	                            <li class="form-validate">
	                                <label><span class="required">*</span>节点顺序</label>
	                                <input type="text" id="seq" name="seq" style="width:160px;"
	                                       onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
	                                       value="${prjItem1.seq}"/>
	                            </li>
	                            <li class="form-validate">
	                                <label>是否验收</label>
	                                <house:xtdm id="isConfirm" dictCode="YESNO" style="width:160px;"
	                                            value="${prjItem1.isConfirm}" onchange="onIsConfirmChange()"/>
	                            </li>
	                            <li class="form-validate">
	                                <label>进度相片数</label>
	                                <input type="text" id="prjphotoNum" name="prjphotoNum" style="width:160px;"
	                                       onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
	                                       value="${prjItem1.prjphotoNum}"/>
	                            </li>
	                            <li class="form-validate">
	                                <label>工种分类12</label>
	                                <house:dict id="worktype12" dictCode=""
	                                            sql="select Code,Code+' '+Descr Descr from tWorkType12 where Expired='F' order by Code"
	                                            sqlValueKey="Code" sqlLableKey="Descr"
	                                            value="${prjItem1.worktype12.trim()}"/>
	                            </li>
	                            <li class="form-validate">
	                                <label>是否必须通过初检</label>
	                                <house:xtdm id="isFirstPass" dictCode="YESNO" style="width:160px;"
	                                            value="${prjItem1.isFirstPass}"/>
	                            </li>
	                            <li class="form-validate">
	                                <label>项目经理应签到次数</label>
	                                <input type="text" id="signTimes" name="signTimes" style="width:160px;"
	                                       onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
	                                       value="${prjItem1.signTimes}"/>
	                            </li>
	                            <li class="form-validate">
	                                <label><span class="required">*</span>设计师应签到次数</label>
	                                <input type="text" id="designSignTimes" name="designSignTimes" style="width:160px;"
	                                       value="${prjItem1.designSignTimes}"/>
	                            </li>
	                            <li class="form-validate">
	                                <label><span class="required">*</span>是否初检标记完工</label>
	                                <house:xtdm id="isFirstComplete" dictCode="YESNO" style="width:160px;"
	                                            value="${prjItem1.isFirstComplete}"/>
	                            </li>
	                        </ul>
	                    </div>
	                    <div class="col-sm-6">
	                        <ul class="ul-form">
	                            <li class="form-validate">
	                                <label>初检触发验收</label>
	                                <house:xtdm id="firstAddConfirm" dictCode="FIRSTADDCONF" value="${prjItem1.firstAddConfirm}"></house:xtdm>
	                            </li>
		                        <c:if test="${prjItem1.m_umState != 'A'}">
		                            <li>
		                                <label>过期</label>
		                                <input type="checkbox" id="expired_show" name="expired_show" value="${prjItem1.expired}"
		                                       onclick="checkExpired(this)" ${prjItem1.expired=="T"?"checked":""}/>
		                            </li>
		                        </c:if>
	                        </ul>
	                    </div>
	                </div>
	            </form>
	        </div>
	    </div>
	</div>
	<script type="text/javascript" defer>
		var url = "", oldCode="";
		$(function() {
			switch ("${prjItem1.m_umState}") {
				case "M":
					url = "${ctx}/admin/prjItem1/doUpdate";
					break;
				case "V":
					$("#saveBtn").remove();
					disabledForm();
					break;
				default:
					url = "${ctx}/admin/prjItem1/doSave";
					break;
			}
			
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					code:{ 
						validators: {  
							notEmpty: {  
								message: "编号不允许为空"  
							},
							remote: {
					            message: "编号重复",
					            url: "${ctx}/admin/prjItem1/checkCode",
					            data: {oldCode: "${prjItem1.code}"},
					            delay:1000, 
					            type:"post", 
					        }
						}  
					},
					descr:{ 
						validators: {  
							notEmpty: {  
								message: "名称不允许为空"  
							},
						}  
					},
					seq:{
						validators: {  
							notEmpty: {  
								message: "节点顺序不允许为空"  
							},
						}
					},
					isFirstComplete:{
						validators: {  
							notEmpty: {  
								message: "是否初检标记完工不允许为空"  
							},
						}
					},
				}
			});
			
			onIsConfirmChange();
			
			$("#saveBtn").on("click", function () {
				if ("V" == "${prjItem1.m_umState}") return;
				$("#page_form").bootstrapValidator("validate");/* 提交验证 */
				if(!$("#page_form").data("bootstrapValidator").isValid()) return; /* 验证失败return */	
				var datas = $("#page_form").jsonForm();
				
				if (datas.isConfirm === "1" && !datas.firstAddConfirm) {
				    art.dialog({content: "验收节点请选择初检触发验收类型！"});
				    return;
				}
				
				saveAjax(datas);
			});
			
			$("#closeBtn").on("click", function () {
				closeWin(false);
			});
			
		});
		
        function onIsConfirmChange() {
            var isConfirm = $("#isConfirm");
            var firstAddConfirm = $("#firstAddConfirm");
            
            if (!isConfirm.val() || isConfirm.val() === "0") {
                firstAddConfirm.val("0");
                firstAddConfirm.attr("disabled", true);
            } else {
                firstAddConfirm.val("1");
                firstAddConfirm.removeAttr("disabled");
            }
        }
		
		function saveAjax(datas) {
			$.ajax({
				url:url,
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
				},
				success: function(obj){
					if(obj.rs){
						art.dialog({
							content: obj.msg,
							time: 700,
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
</body>
</html>
