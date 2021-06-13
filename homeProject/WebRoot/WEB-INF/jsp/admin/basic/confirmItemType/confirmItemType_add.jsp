<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>确认材料类型分类增加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 

$(function() {
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${ar }',
								secondSelect:'itemType2',
								secondValue:'${item.itemType2 }',
								thridSelect:'itemType3',
								thirdValue:'${item.itemType3 }',});	
								
	$("#saveBtn").on("click",function(){
		var itemtype2=$.trim($("#itemType2").val());
		var itemtype3=$.trim($("#itemType3").val());
		var arrString=$.trim($("#arr").val());
	 	if(itemtype2==''||itemtype2==null){
	 		art.dialog({
	 			content:'请选择材料编号2'
	 		});
	 		return false;
	 	}
	 	
	 	var itemarr2=$.trim($("#arr").val()).split(',');
	 	var itemarr3=$.trim($("#arry").val()).split(',');
	 	for(var i=0;i<itemarr2.length;i++ ){
	 		if(arrString!=''&&itemarr2[i]==itemtype2&&itemarr3[i]==itemtype3){
	 			art.dialog({
	 			content:'明细表存在相同数据'
	 		});
	 		return false;
	 		}
	 	}
	 	
	 	var item2text=$("#itemType2").find("option:selected").text();
	 	var arr = item2text.split(' ');
	 	var item2Descr=arr[arr.length-1];
	 	var item3text=$("#itemType3").find("option:selected").text();
	 	var arry = item3text.split(' ');
	 	var item3Descr=arry[arry.length-1];
	 	
		var selectRows = [];
		var datas=$("#page_form").jsonForm();
		datas.item2Descr=(item2Descr=='请选择...'?"":item2Descr);
		datas.item3Descr=(item3Descr=='请选择...'?"":item3Descr);
	 	selectRows.push(datas);
		Global.Dialog.returnData = selectRows;
		closeWin();
	}); 							
								
});

</script>
</head>
	<body>
<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value="" />  
					<input type="hidden" name="arr" id="arr" value="${arr}" /><!--材料类型2  -->  
					<input type="hidden" name="arry" id="arry" value="${arry }" /><!--材料类型3  -->  
						<ul class="ul-form">
							<li hidden="true">
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1" style="width: 160px;" value="${ar}" ></select>
							</li>
							<li>
								<label>材料类型2</label>
								<select id="itemType2" name="itemType2" style="width: 160px;" ></select>
							</li>
							<li hidden="true">
								<label>材料类型2</label>
								<input id="item2Descr" name="item2Descr" style="width: 160px;" />
							</li>
							<li>
								<label>材料类型3</label>
								<select id="itemType3" name="itemType3" style="width: 160px;"  ></select>
							</li>
							<li hidden="true">
								<label>材料类型3</label>
								<input id="item3Descr" name="item3Descr" style="width: 160px;"  />
							</li>
						</ul>
				</form>
				</div>
			</div>
		</div>
	</body>	
</html>
