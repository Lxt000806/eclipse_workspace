<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>测量后下单 查看</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script type="text/javascript">
		/**初始化表格*/
		$(function(){
		    if($("#m_umState").val()=="M"){
		    	$(".clhxdButtons > #saveBut").removeAttr("disabled");
		    }
		});
		function doXdqx_clhxd(){
			var datas = $("#dataForm").jsonForm();
			var remarks = $("#remarks").val();
			var cancelRemark = $("#cancelRemark").val();
			$.extend(datas,{
				remarks:remarks,
				cancelRemark:cancelRemark
			});
			$.ajax({
				url:"${ctx}/admin/itemPreApp/doXdqx_clhxd",
				type: "post",
				data:datas,
				dataType:"json",
				cache: false,
				error: function(obj){			    		
					art.dialog({
						content:"下单取消出错,请重试",
						time: 3000,
						beforeunload: function (){
							closeWin();
						}
					});
				},
				success: function(obj){
					if(obj.rs){
						art.dialog({
							content: obj.msg,
							time: 3000,
							beforeunload: function () {
								closeWin();
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
			    	<div class="btn-group-xs clhxdButtons" >
		   				<button id="saveBut" type="button" class="btn btn-system" onclick="doXdqx_clhxd()" disabled>保存</button>
		   				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="page_form" class="form-search">
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" value="${map.Address}" readonly="readonly" />
							</li>
							<li>
								<label>项目经理</label>
								<input type="text" id="prjManName" name="prjManName" value="${map.PrjManName}" readonly="readonly"/>
							</li>
							<li>
								<label>电话</label>
								<input type="text" id="prjManPhone" name="prjManPhone" value="${map.PrjManPhone}" readonly="readonly"/>
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div class="container-fluid" > 
				<input type="hidden" id="m_umState" value="${itemPreMeasure.m_umState }" /> 
				<ul class="nav nav-tabs" >
					<li class="active"><a href="#itemPreApp_tabView_measureDetail" data-toggle="tab">测量单</a></li>  
					<li class=""><a href="#itemPreApp_tabView_applyList" data-toggle="tab">预申请明细</a></li> 
				</ul>  
				<div class="tab-content">  
					<div id="itemPreApp_tabView_measureDetail" class="tab-pane fade in active"> 
						<jsp:include page="itemPreAppManage_tabView_measureDetail.jsp">
							<jsp:param value="${itemPreMeasure.m_umState }" name="fromFlag"/>
							<jsp:param value="${ itemPreMeasure.pk}" name="pk"/>
						</jsp:include>
					</div>  
					<div id="itemPreApp_tabView_applyList" class="tab-pane fade "> 
						<jsp:include page="itemPreAppManage_tabView_applyList.jsp">
							<jsp:param value="clhxd" name="fromFlag"/>
							<jsp:param value="${ itemPreMeasure.preAppNo}" name="no"/>
							<jsp:param value="${ itemPreMeasure.supplCode}" name="supplCode"/>
						</jsp:include>
					</div>
				</div>	
			</div>	
		</div>
	</body>
</html>
