<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>领料审核规则明细信息--添加</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
//验证信息是否重复
function validateCom(itemType2,itemType3,itemDesc){
	var isPass=0;
	var str1 =$("#itemType2Com").val();
	var str2 =$("#itemType3Com").val();
	var str3 =$("#itemDescCom").val();
	var strs1 = new Array();
	var strs2 = new Array();
	var strs3 = new Array();
	strs1 = str1.split(',');
	strs2 = str2.split(',');
	strs3 = str3.split(',');;
	for(i=0;i<strs1.length;i++){
		if(itemType2==strs1[i].replace(/(^\s*)|(\s*$)/g, "") && itemType3==strs2[i].replace(/(^\s*)|(\s*$)/g, "") && itemDesc==strs3[i].replace(/(^\s*)|(\s*$)/g,"")){
			isPass=1;
			return isPass;
		};
	}
} 
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({
			firstSelect:"itemType1",
			firstValue:$.trim("${itemAppConfRule.itemType1}"),
	});
});
function saveBtn(){
	var itemType2=$.trim($("#itemType2").val());	
	var itemType3=$.trim($("#itemType3").val());
	var itemDesc=$.trim($("#itemDesc").val());
	if( itemType2=="" && itemType3 =="" && itemDesc==""){
		art.dialog({
			content:"保存时数据不能全部为空"
		});
		return;
	}
	if(validateCom(itemType2,itemType3,itemDesc)==1){
		art.dialog({
			content:"该记录以存在"
		});
		return;
	} 
	var selectRows = [];
	var options = $("#itemType2 option:selected");
	var datas=$("#dataForm").jsonForm();
	if(""!=$.trim($("#itemType2").val())){
		datas.itemtype2=$("#itemType2").val();
	}
	if(""!=$.trim($("#itemType2").val())){
		//获取当前选项
		var options = $("#itemType2 option:selected");
		//获取选中项目的文本
		var arr = options.text().split(" ");
		datas.itemtype2desc=arr[1];
	} 
	if(""!=$.trim($("#itemType3").val())){
		datas.itemtype3=$("#itemType3").val();
	} 
	if(""!=$.trim($("#itemType3").val())){
		var options = $("#itemType3 option:selected");
		var arr = options.text().split(" ");
		datas.itemtype3desc=arr[1];
	}
	if(""!=$.trim($("#itemDesc").val())){
		datas.itemdesc=$("#itemDesc").val();
	} 
	datas.expired="F";
	datas.actionlog="ADD";
	datas.lastupdate= new Date();
	datas.lastupdatedby="${itemAppConfRule.lastUpdatedBy}";
 	selectRows.push(datas);
	Global.Dialog.returnData = selectRows;
	closeWin();
};
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
		    	<div class="panel-body" >
		    		<div class="btn-group-xs" >
						<button type="button" class="btn btn-system" onclick="saveBtn()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<%-- <input type="text" name="itemType1" id="itemType1" value="${itemAppConfRule.itemType1}" hidden="true"/> --%>
						<input type="text" name="itemType2Com" id="itemType2Com" value="${itemAppConfRule.itemType2Com}" hidden="true"/>
						<input type="text" name="itemType3Com" id="itemType3Com" value="${itemAppConfRule.itemType3Com}" hidden="true"/>
						<input type="text" name="itemDescCom" id="itemDescCom" value="${itemAppConfRule.itemDescCom}" hidden="true"/>
						<ul class="ul-form">
							<li hidden="true">
								<label>材料类型1</label>
								<select id="itemType1"  name="itemType1" value="${itemAppConfRule.itemType1}"></select>
							</li>
							<li>
								<label>材料类型2</label>
								<select id="itemType2"  name="itemType2"></select>
								</li>
							<li>
								<label>材料类型3</label>
								<select id="itemType3" name="itemType3"></select>
							</li>
							<li class="form-validate">
								<label>材料名称</label>
								<input type="text" id="itemDesc" name="itemDesc"/>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
