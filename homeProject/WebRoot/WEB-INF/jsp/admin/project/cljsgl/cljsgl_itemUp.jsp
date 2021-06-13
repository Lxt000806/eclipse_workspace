<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料结算管理--材料升级</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
//校验函数
/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	var dataSet = {
			firstSelect:"itemType1",
			firstValue:"${itemCheck.itemType1}",
			disabled:"true"
		};
	Global.LinkSelect.setSelect(dataSet);
	function getData(data){
		if (!data) return;
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:"${itemCheck.itemType1}",
		};
		Global.LinkSelect.setSelect(dataSet);
		$("#address").val(data.address);
	}	
	$("#custCode").openComponent_customer({callBack: getData,condition:{status:"4",disabled:"disabled",},valueOnly:"1"});
	$("#custCode").setComponent_customer({showValue:"${itemCheck.custCode}"});
});
</script>
</head>
    
<body>
<input type="hidden" name="jsonString" value="" />
<div class="body-box-form" >
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
      	<button type="button" id="saveBtn" class="btn btn-system" >保存</button>
		<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
      </div>
   	</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="exportData" id="exportData">
				<ul class="ul-form">
					<li>
						<label>材料免费升级</label>
						<house:xtdm id="isItemUp" dictCode="YESNO" value="${customer.isItemUp }" ></house:xtdm>
					</li>				
					<li hidden="true">
						<input id="isItemUpRemark" value="${customer.isItemUp }" />
					</li>	
					<li hidden="true">
						<label>结算单号</label>
						<input type="text" id="no" name="no" readonly="true" value="${itemCheck.no}"/>
					</li>
				</ul>
			</form>
		</div>
		</div>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script>
$("#saveBtn").on("click",function(){		
	var isItemUp = $("#isItemUp").val();
	var isItemUpRemark = $("#isItemUpRemark").val();
	var custCode = $.trim($("#custCode").val());	
	if (isItemUp==isItemUpRemark){
		art.dialog({content: "您未做任何修改，无需保存！",width: 200});
		return false;
	}	
	var datas=$("#page_form").jsonForm();
	console.log(datas);
	$.ajax({
		url:"${ctx}/admin/cljsgl/doCljsglItemUp",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
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
				art.dialog({
					content: obj.msg,
					width: 200
				});
		   	}
		}
	});		

});

</script>	
</body>
</html>


