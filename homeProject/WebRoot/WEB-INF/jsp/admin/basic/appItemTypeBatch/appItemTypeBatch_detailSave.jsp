<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;height: 207px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<li class="form-validate">
							<label><span class="required">*</span>施工材料分类</label>
							<house:dict id="confItemType" dictCode="" 
								sql=" select distinct Code,Code+' '+Descr ConfItemType from tConfItemType where Expired = 'F' " 
								sqlValueKey="Code" sqlLableKey="ConfItemType" unShowValue="${keys}" value=""/>          
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>显示顺序</label>
							<input type="text" id="dispSeq" name="dispSeq" style="width:160px;" 
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'');"
								value="0"/>
						</li>
						<!-- <li hidden="true">
							<label>过期</label>
							<input type="checkbox" id="expired" name="expired" value="" 
								onclick="checkExpired(this)" ${appItemTypeBatch.expired=="T"?"checked":""}/>
						</li> -->
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
<script type="text/javascript"> 
$(function() {
	switch ("${appItemTypeBatch.m_umState}") {
	case "M"://编辑中的编辑
		$("#confItemType").val("${appItemTypeBatch.confItemType}");
		$("#dispSeq").val("${appItemTypeBatch.dispSeq}");
		//$("#expired").parent().prop("hidden",false);
		//$("#expired").val("${appItemTypeBatch.expired}");
		break;
	case "V":
		//不显示保存按钮
		$("#saveBtn").hide();
		//$("#expired").parent().prop("hidden",false);
		
		$("#confItemType").val("${appItemTypeBatch.confItemType}");
		$("#dispSeq").val("${appItemTypeBatch.dispSeq}");
		//$("#expired").val("${appItemTypeBatch.expired}");
		
		$("#confItemType").prop("disabled",true);
		$("#dispSeq").prop("readonly",true);
		//$("#expired").prop("disabled",true);
		break;
	default:
		break;
	}
	
	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			confItemType:{  
				validators: {  
					notEmpty: {  
						message: "施工材料分类不能为空"  
					}
				}  
			},
			dispSeq:{  
				validators: {  
					notEmpty: {  
						message: "显示顺序不能为空"  
					}
				}  
			}
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});

	var originalData = $("#page_form").serialize();

	$("#saveBtn").on("click",function(){
		//如果数据没有改变，就关闭窗口
		var newData = $("#page_form").serialize();
		if(newData == originalData) {
			art.dialog({content: "数据没有改变，无法新增",width: 220});
			return;
		}
	
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()){ 
			art.dialog({content: "无法保存，请输入完整的信息",width: 220});
			return;
		}
		
		/* 获得选择值，并将descr提取出来 */
		var confItemTypeText=$("#confItemType").find("option:selected").text();
	 	var arr = confItemTypeText.split(" ");
	 	var confItemTypeDescr=arr[arr.length-1];
		/* 将page_form json化后赋给selectRows */
 		var selectRows = [];
		var datas=$("#page_form").jsonForm();
		datas.confItemTypeDescr=(confItemTypeDescr=="请选择..."?"":confItemTypeDescr);
	 	selectRows.push(datas);
	 	//console.log(selectRows);
		Global.Dialog.returnData = selectRows;
		
		closeWin();
	});
	
});
</script>
</html>
