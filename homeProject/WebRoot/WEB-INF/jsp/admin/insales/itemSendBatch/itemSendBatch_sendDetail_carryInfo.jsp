<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>配送明细--录入搬运信息</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_sendRegion.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				region : {
					validators : {
						notEmpty : {
							message : '请选择区域'
						},
					}
				},
				carryType : {
					validators : {
						notEmpty : {
							message : '请选择搬运类型'
						},
					}
				},
				floor : {
					validators : {
						notEmpty : {
							message : '请填写楼层'
						},
						numeric: {
							message: '楼层只能输入数字'
						}
					}
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});

	$(function() {
		//onchange1();
		$("#region").openComponent_sendRegion({
			showValue:'${itemAppSend.region}',
			showLabel:'${itemAppSend.regionDescr}',
			callBack : function(data) {
				$("#regionDescr").val(data.descr);
			}
		});
		$("#openComponent_sendRegion_region").attr("readonly",true);
		$("#saveBtn").on("click", function() {
			$("#dataForm").bootstrapValidator('validate');
			if (!$("#dataForm").data('bootstrapValidator').isValid())
				return;
			var region=$("#region").val();
			if(region==""){
				art.dialog({    	
					content: "请选择区域！"
				});
				return;
			}
			onchange1();
			var datas = $("#dataForm").jsonForm();
			Global.Dialog.returnData = datas;
			closeWin();
		});
	});
	
 function onchange1(){		
	var str=$("#carryType").text();	
	var Type=$("#carryType").val();
	str = str.split(' ');//先按照空格分割成数组
	str=str[Type];
	if(Type==str.length){
		var str1=str.substring(0,str.length);
	}
	else{
		var str1=str.substring(0,str.length-1);
	}
	document.getElementById("carryTypeDescr").value=str1; 
} 
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
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
					<input type="hidden" id="carryTypeDescr" name="carryTypeDescr"  value="${itemAppSend.carryTypeDescr}"/>
					<input type="hidden" id="regionDescr" name="regionDescr" value="${itemAppSend.regionDescr }"/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate"><label>单号</label> <input
								type="text" id="no" name="no" style="width:160px;"
								value="${itemAppSend.no }" readonly />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>区域</label> <input
								type="text" id="region" name="region" style="width:160px;"
								value="${itemAppSend.region}" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>搬运类型</label> <house:xtdm id="carryType"
									dictCode="CarType" value="${itemAppSend.carryType}" ></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>楼层</label> <input
								type="text" id="floor" name="floor" style="width:160px;"
								value="${itemAppSend.floor}" />
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
