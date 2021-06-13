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
	<title>活动门票管理查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_activity.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 

$(function(){
	var hasAuthByCzybh=$.trim($("#hasAuthByCzybh").val());
	var provideCZY=$.trim($("#provideCZY").val());
	var czybh=$.trim($("#czybh").val());
	if(czybh!=provideCZY&&hasAuthByCzybh!='true'){
		document.getElementById('phone').setAttribute('type','password') ;
	}
	
	if('${hdmpgl.provideType}'=='2'){
		$("#provideType option[value='2']").attr("selected","selected");
		$("#businessMan").openComponent_employee({readonly:true});	
		$("#designMan").openComponent_employee({readonly:true});
	}else{
		$("#busiManName").attr("readonly","readonly");
		$("#busiManPhone").attr("readonly","readonly");
		$("#businessMan").openComponent_employee({showValue:'${hdmpgl.businessMan }',showLabel:'${hdmpgl.businessDescr }'} );
		$("#designMan").openComponent_employee( {showValue:'${hdmpgl.designMan }',showLabel:'${hdmpgl.designDescr }'});
	}

	$("#provideCZY").openComponent_employee({showValue:'${hdmpgl.provideCZY }',showLabel:'${hdmpgl.provideDescr }',readonly:true});
	$("#actNo").openComponent_activity({showValue:'${hdmpgl.actNo }',showLabel:'${hdmpgl.actDescr }',readonly:true});
	
});
function changeType(){
	var provideType=$.trim($("#provideType").val());
	if(provideType=='2'){
		$("#businessMan").openComponent_employee({readonly:true});	
		$("#designMan").openComponent_employee({readonly:true});
		$("#busiManName").removeAttr("readonly","readonly");
		$("#busiManPhone").removeAttr("readonly","readonly");
		$("#businessMan").val('');
		$("#openComponent_employee_businessMan").val('');
		$("#designMan").val('');
		$("#openComponent_employee_designMan").val('');
	}else{
		$("#businessMan").openComponent_employee( );	
		$("#designMan").openComponent_employee( );
		$("#openComponent_employee_businessMan").removeAttr("readonly",true);
		$("#openComponent_employee_designMan").removeAttr("readonly",true);
		$("#busiManName").attr("readonly","readonly");
		$("#busiManPhone").attr("readonly","readonly");
		$("#busiManName").val("");
		$("#busiManPhone").val("");
	}
	
}

</script>
</head>
<body >
<div class="body-box-form" >
	<div class="content-form">
		<!--panelBar-->
		<div class="panel panel-system" >
		    <div class="panel-body" >
		     	 <div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
	</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_hdmpgl_view" data-toggle="tab">门票信息</a></li>
		        <li class=""><a href="#tab_order_detail" data-toggle="tab">下定明细</a></li>
		        <li class=""><a href="#tab_gift_detail" data-toggle="tab">礼品明细</a></li>
		    </ul> 
		    <div class="tab-content">  
		        <div id="tab_hdmpgl_view" class="tab-pane fade in active"> 
		         	<jsp:include page="tab_hdmpgl_view.jsp"></jsp:include>
		        </div> 
		        <div id="tab_order_detail" class="tab-pane fade "> 
		         	<jsp:include page="tab_order_detail.jsp"></jsp:include>
		        </div> 
		       
		        <div id="tab_gift_detail" class="tab-pane fade "> 
		         	<jsp:include page="tab_gift_detail.jsp"></jsp:include>
		        </div> 
		    </div>  
		</div>
</div>
</body>

</html>
