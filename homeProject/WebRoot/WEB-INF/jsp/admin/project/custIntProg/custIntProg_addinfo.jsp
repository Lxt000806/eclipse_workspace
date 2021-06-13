<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>集成进度信息管理--信息录入</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	function save(){
	var intProgDetailJson = Global.JqGrid.allToJson("dataTable");
		console.log(intProgDetailJson);
		var param = {"intProgDetailJson": JSON.stringify(intProgDetailJson)};
		
			Global.Form.submit("dataForm","${ctx}/admin/custIntProg/doSave",param,function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
		    		art.dialog({
						content: ret.msg,
						width: 200
					});
				}
				
			});
	}
</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	     <div class="panel-body" >
	     	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="save" onclick="save()">保存</button>
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	
	<div  class="container-fluid" >
			<ul class="nav nav-tabs">
				<li id="tabCustIntProgInfo" class="active"><a
					href="#tab_CustIntProgInfo" data-toggle="tab">集成进度信息</a>
				</li>
				<li id="tabCustIntProgDetail" class=""><a
					href="#tab_CustIntProgDetail" data-toggle="tab">集成进度明细</a>
				</li>
				<li id="tabCustIntProgSale" class=""><a
					href="#tab_CustIntProgSale" data-toggle="tab">售中/售后</a>
				</li>
				<li id="tabCustIntProgComp" class=""><a
					href="#tab_CustIntProgComp" data-toggle="tab">投诉明细</a>
				</li>
			</ul>
			<div class="tab-content">  
			<div id="tab_CustIntProgInfo"  class="tab-pane fade in active"> 
		    	<jsp:include page="custIntProg_info.jsp"></jsp:include>
			</div>  
			<div id="tab_CustIntProgDetail"  class="tab-pane fade "> 
	         	<jsp:include page="custIntProg_detail.jsp"></jsp:include>
			</div>
				<div id="tab_CustIntProgSale"  class="tab-pane fade"> 
		    	<jsp:include page="custIntProg_sale.jsp"></jsp:include>
			</div>  
			<div id="tab_CustIntProgComp"  class="tab-pane fade "> 
	         	<jsp:include page="custIntProg_problem.jsp"></jsp:include>
			</div>
		</div>	
	</div>	
<script type="text/javascript">
</script>
</body>
</html>
