<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>基础结算项目管理--增加</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){  
	Global.LinkSelect.initSelect("${ctx}/admin/baseItemType1/baseItemType","baseItemType1","baseItemType2");
});
$(function(){
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			descr : {
				validators : {
					notEmpty : {
						message : '项目名称不能为空'
					}
				}
			},
			baseItemType1 : {
				validators : {
					notEmpty : {
						message : '基装类型1不能为空'
					}
				}
			},
			baseItemType2 : {
				validators : {
					notEmpty : {
						message : '基装类型2长度不能为空'
					}
				}
			},
			offerPri : {
				validators : {
					notEmpty : {
						message : '人工成本单价不能为空'
					},
					numeric: {
	                            message:'只能输入数字'
	                        },
				}
			 },
			 material : {
				validators : {
					notEmpty : {
						message : '材料成本单价不能为空'
					},
					numeric: {
	                            message:'只能输入数字'
	                        },
				}
			}, 
			prjOfferPri : {
				validators : {
					notEmpty : {
						message : '项目经理人工单价不能为空'
					},
					numeric: {
	                            message:'只能输入数字'
	                        },
				}
			 },
			 prjMaterial : {
				validators : {
					notEmpty : {
						message : '项目经理材料单价不能为空'
					},
					numeric: {
	                            message:'只能输入数字'
	                        },
				}
			 },
			 uom : {
				validators : {
					notEmpty : {
						message : '单位不能为空'
					}
				}
			 },
			 type : {
				validators : {
					notEmpty : {
						message : '类型 不能为空'
					}
				}
			 },
			 workerClassify_NAME: {
				 validators : {
					notEmpty : {
						message : '工人分类不能为空'
					}
				}
			 },
			 isSubsidyItem: {
				 validators : {
					notEmpty : {
						message : '是否发包补贴项目不能为空'
					}
				}
			 },
		},
		submitButtons : 'input[type="submit"]'
		}); 
		$("#dispSeq").on("blur", function () {
			if ("" == $(this).val()) $(this).val(1000);
		})
	});
	function save(){
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var datas = $("#dataForm").serialize();
		$.ajax({
			url : "${ctx}/admin/baseCheckItem/doSave",
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
	function chgWorkerClassify(){
		$("#dataForm").data("bootstrapValidator")
        .updateStatus("workerClassify_NAME", "NOT_VALIDATED",null)  
        .validateField("workerClassify_NAME");
	}
</script>
</head>
<body scroll=no>
 	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
			    	 <button type="submit" class="btn btn-system" id="saveBut" onclick="save()">保存</button> 
	     			 <button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button> 
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">					  
						<li>
							<label>编号</label>
							<input type="text" placeholder="自动生成" readonly="readonly" />
						</li>
						<li class="form-validate">	
							<label>项目名称</label>
							<input type="text" id="descr" name="descr"/>
						</li>
						<li class="form-validate">
							<label>基装类型1</label>
							<select id="baseItemType1" name="baseItemType1"></select>
						</li>
						<li class="form-validate">
							<label>基装类型2</label>
							<select id="baseItemType2" name="baseItemType2"></select>
						</li>
						<li>
							<label>工种分类12</label>
							<house:dict id="workType12" dictCode="" sql="select code,code+' '+ descr descr from tWorkType12"
							sqlLableKey="descr" sqlValueKey="code"></house:dict>
						</li>
						<li class="form-validate">
							<label>人工成本单价</label>
							<input type="text" id="offerPri" name="offerPri" value="0" />
						</li>
						<li class="form-validate">
							<label>材料成本单价</label>
							<input type="text" id="material" name="material" value="0" />
						</li>
						<li  class="form-validate">
							<label>项目经理人工单价</label>
							<input type="text" id="prjOfferPri" name="prjOfferPri" value="0" />
						</li>
						<li class="form-validate">
							<label>项目经理材料单价</label>
							<input type="text" id="prjMaterial" name="prjMaterial" value="0" />
						</li>
						<li  class="form-validate">
							<label>单位</label>
							<house:dict id="uom" dictCode="" 
							sql="select code,code+' '+descr descr from tuom where expired='F' order by rtrim(Code) " sqlLableKey="descr" sqlValueKey="code"></house:dict>
						</li>		
						<li  class="form-validate">
							<label>类型</label>
							<house:xtdm id="type" dictCode="BASECKITEMTYPE"></house:xtdm>
						</li>
						<li  class="form-validate">
							<label>是否定责项目</label>
							<house:xtdm id="isFixItem" dictCode="YESNO"></house:xtdm>
						</li>
						<li  class="form-validate">
							<label>显示顺序</label>
							<input type="text" id="dispSeq" name="dispSeq" style="width:160px;"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
								value="1000"/>
						</li>
						<li class="form-validate">
							<label>工人分类</label>
							<house:xtdmMulit id="workerClassify" dictCode="WORKERCLASSIFY" selectedValue="" onCheck="chgWorkerClassify()"></house:xtdmMulit>                     
						</li>
						<li  class="form-validate">
							<label>是否发包补贴项目</label>
							<house:xtdm id="isSubsidyItem" dictCode="YESNO" value="0"></house:xtdm>
						</li>
						<li class="form-validate">
							<label class="control-textarea">描述</label>
							<textarea id="remark" name="remark" rows="3">${baseCheckItem.remark}</textarea>
						</li>						
					</ul>		
				</form>
			</div>
	  	</div>
	  </div>
	</body>
</html>


