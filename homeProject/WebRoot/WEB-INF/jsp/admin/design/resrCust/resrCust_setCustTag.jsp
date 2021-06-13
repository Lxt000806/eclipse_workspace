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
	<title>设置客户标签</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<style type="text/css">
		span.tag {
		    -moz-border-radius: 3px;
		    -webkit-border-radius: 3px;
		    display: block;
		    float: left;
		    padding: 5px;
		    text-decoration: none;
		    margin-right: 10px;
		    margin-bottom: 5px;
		    font-family: helvetica;
		    font-size: 13px;
		    text-align: center;
		    cursor:not-allowed;
		}
		.columnBox{
			display: -webkit-flex; /* Safari */
  			flex-direction: column ;
  			flex-wrap: wrap;
  			justify-content: flex-row;
		}
		.rowBox{
			display: -webkit-flex; /* Safari */
  			flex-direction: row ;
  			flex-wrap: wrap;
  			justify-content: space-between;
  			margin-bottom: 10px;
		}
		.tagGroup{
			padding-top:10px;
		}
	</style>
<script type="text/javascript"> 
$(function(){
 	$("span[name=tagSpan]").hover(function(){
 		var oldColor=$(this).css("background-color");
 		//背景为白色，即批量方案为3替换时触发
 		if(oldColor=="rgb(255, 255, 255)"){
			var groupInfo=$(this).parent().prev().val().split(",");
			var color=groupInfo[0];
			$(this).css({"border-color":color,"background-color":"white","color":color});
 		}
	},function(){
		var oldColor=$(this).css("background-color");
		if(oldColor=="rgb(255, 255, 255)"){
			$(this).css({"border-color":"#e6e5e5","background-color":"white","color":"black"});
		}
 	}); 
});

function changeBatchPlan(obj){
	var batchPlan=$(obj).val();
	var color="#aba5a5";
	var backgroundColor="#f5f5f5";
	var cursor="not-allowed";
	if(batchPlan=="3"){
		backgroundColor="white";
		cursor="pointer";
		color="black";
	}
	$(obj).parent().parent().siblings().css({"background-color":backgroundColor,"cursor":cursor,"color":color});
}

function selTag(obj){
	var groupInfo=$(obj).parent().prev().val().split(",");
	var color=groupInfo[0];
	var isMulti=groupInfo[1];
	var oldColor=$(obj).css("background-color");
	//不支持多选的，且方案为3替换，点击之前清空其他标签颜色
	if(isMulti!="1" && oldColor=="rgb(255, 255, 255)"){
		$(obj).siblings("span").css({"background-color":"white","color":"black","border-color":"#e6e5e5"});
	} 
	if(oldColor=="rgb(255, 255, 255)"){//背景为白色，即批量方案为3替换时，设置为标签组颜色
		$(obj).css({"background-color":color,"color":"white","border-color":"#e6e5e5"});
	}else if(oldColor!="rgb(245, 245, 245)"){//已经选择过颜色，再次点击，则变成白色
		$(obj).css({"background-color":"white","color":"black","border-color":"#e6e5e5"});
	}
}


function doSave(){
	var delTagPks="";
	var addTagPks="";
	$("select[name=batchPlan]").each(function(){
		var batchPlan=$(this).val();
		$(this).parent().parent().siblings("span").each(function(index,element){
			if(batchPlan=="3" && $(this).css("background-color")!="rgb(255, 255, 255)" 
				&& $(this).css("background-color")!="rgb(245, 245, 245)"){
				addTagPks+=$(this).children("input").val()+",";
			}
			if(batchPlan!="1" ){
				delTagPks+=$(this).children("input").val()+",";
			}
		});
	});
	$.ajax({
		url:'${ctx}/admin/ResrCust/doSetCustTag',
		type: 'post',
		data: {delTagPks:delTagPks,addTagPks:addTagPks,codes:"${resrCust.codes}"},
		dataType: 'json',
		cache: false,
		async:false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin(false);
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
</head>
	<body>
		<div class="body-box-form">
				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " onclick="doSave()">
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
					<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
								<input type="hidden" name="jsonString" value="" />
								<input type="hidden" id="czybh" name="czybh" style="width:160px;" value="${resrCust.czybh }"/>
								<input type="hidden" id="department2" name="department2" style="width:160px;" value="${resrCust.department2 }"/>
								<input type="hidden" id="codes" name="codes" style="width:160px;" value="${resrCust.codes }"/>
								<ul class="ul-form">
									<p style="color:gray">您已选择${custNum}位客户</p>
									<div class="columnBox" >
									${htmlStr}
									</div>
								</ul>
						  </form>
				   </div>
			   </div>
		</div>
	</body>	
</html>
