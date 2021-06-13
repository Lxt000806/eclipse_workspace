<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料类型2--增加</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
    <script type="text/javascript">
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/itemType1/byItemType1","itemType1","itemType12");
	});
	$(function(){  
		Global.LinkSelect.initSelect("${ctx}/admin/workType1/workType","workType","materWorkType2");
	});
 	$(function(){  
	    Global.LinkSelect.initSelect("${ctx}/admin/itemType2/prjType","appPrjItem");
    }); 
	function save() {
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var datas = $("#dataForm").serialize();
		$.ajax({
			url : "${ctx}/admin/itemType2/doSave",
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
	$(function() {
			$("#dataForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
				descr : {
					validators : {
						notEmpty : {
							message : '名称不能为空'
						}
					}
				},
				itemType1 : {
					validators : {
						notEmpty : {
							message : '材料类型1不能为空'
						}
					}
				},
				itemType12 : {
					validators : {
						notEmpty : {
							message : '材料分类不能为空'
						}
					}
				},
			},
		});
	});
</script>
</head>
<body>
  	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
		    	<button type="submit" class="btn btn-system" id="saveBut"
							onclick="save()">保存</button>
     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">					  
						<li>
							<label style="width:120px">编号</label>
							<input type="text" id="code" name="code" value="${null}" readonly="readonly" placeholder="主键自动生成"/>
						</li>
						<li class="form-validate"">	
							<label style="width:120px">名称</label>
							<input type="text" id="descr" name="descr"/>
						</li>
						<li>
							<label style="width:120px">其他成本占销售额比例</label>
							<input type="text" id="otherCostPer_Sale" name="otherCostPer_Sale"  value="0" />
						</li>
						<li class="form-validate">
							<label style="width:120px">材料类型1</label>
							<select id="itemType1" name="itemType1"></select>
						</li>
						<li>
							<label style="width:120px">其他成本占成本比例</label>
							<input type="text" id="otherCostPer_Cost" name="otherCostPer_Cost" value="0"/>
						</li>
						<li class="form-validate">
							<label style="width:120px">材料分类</label>
							<select id="itemType12" name="itemType12"></select>
						</li>
						<li>
							<label style="width:120px">到货天数</label>
							<input type="text" id="arriveDay" name="arriveDay"  value="0" />
						</li>
						<li>
							<label style="width:120px">材料工种分类1</label>
							<select id="workType" name="workType"></select>
						</li>
						<li>
							<label style="width:120px">显示顺序</label>
							<input type="text" id="dispSeq" name="dispSeq" value="0"/>
						</li>
						<li>
							<label style="width:120px">材料工种分类2</label>
							<select id="materWorkType2" name="materWorkType2"></select>
						</li>
						<li>
							<label style="width:120px">订货验收结点</label>
							<select id="appPrjItem" name="appPrjItem"></select>
						</li> 
					    </li>
					</ul>		
				</form>
			</div>
  		</div>
  	</div>
  </body>
</html>


