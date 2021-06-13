<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加IntProd</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">

	function save() {
		$("#dataForm").bootstrapValidator('validate');
		if (!$("#dataForm").data('bootstrapValidator').isValid())
			return;
		
		//验证
		var datas = $("#dataForm").serialize();
		$.ajax({
			url : '${ctx}/admin/intProd/doUpdate',
			type : 'post',
			data : datas,
			dataType : 'json',
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : '保存数据出错~'
				});
			},
			success : function(obj) {
				if (obj.rs) {
					art.dialog({
						content : obj.msg
					});
					closeWin();
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

	//校验函数
	$(function() {
		$("#descr").keydown(function(e) {
			if (e.keyCode == 13) {
				save();
			}
		});

		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			fields : {
				fixAreaPk : {
					validators : {
						notEmpty : {
							message : '装修区域不能为空'
						}
					}
				},
				isCupboard: {
				    validators: {
				        notEmpty: {message: "请选择是否橱柜"}
				    }
				}
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});

		$("#descr").focus();

	});
	
    function onFixAreaChange(obj) {
        // "8333197 厨房"
        var fixAreaText = obj.options[obj.selectedIndex].text;
        var isCupboard = $("#isCupboard");
        
        if (fixAreaText.indexOf("厨房") >= 0)
            isCupboard.val("1");
        else
            isCupboard.val("0");
    }
	
</script>
</head>
<body>
<div class="body-box-form" >
	 <div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
      <button type="button" id="saveBtn" class="btn btn-system "   onclick="save()">保存</button>
      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
      </div>
   </div>
	</div>
	<div class="panel panel-info" >  
                <div class="panel-body">
                <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<input  type="hidden" name="pk" id="pk" value="${intProd.pk}" />
				 <ul class="ul-form">
					<div class="validate-group row" >
						<div class="col-sm-4" >
								<li  class="form-validate "  >
								<label>装修区域 </label>
								<house:DataSelect id="fixAreaPk" className="FixArea"
									              keyColumn="pk" valueColumn="descr"
									              filterValue="Expired='F' and custCode='${intProd.custCode}' and itemType1='${intProd.itemType1}' and isService='${intProd.isService}' order by DispSeq"
									              value="${intProd.fixAreaPk}"
									              onchange="onFixAreaChange(this)"></house:DataSelect>
								</li>
						</div>
						<div class="col-sm-4" >
							 <li class="form-validate "  >
								<label>集成成品名称 </label>
									<input data-bv-notempty data-bv-notempty-message="集成成品名称不能为空" type="text" id="descr" name="descr" style="width:160px;"  value="${intProd.descr}" />
								          
							</li>
						</div>
						<div class="col-sm-4" >
							<li class="form-validate">
							    <label><span class="required">*</span>橱柜</label>
							    <house:xtdm id="isCupboard" dictCode="YESNO" value="${intProd.isCupboard}"></house:xtdm>
							</li>
						</div>
					</div>		
				</ul>
			</form>
		</div>
	</div>
</div>
</body>
</html>
