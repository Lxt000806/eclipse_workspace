<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>录入套内面积</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	    <script type="text/javascript">
	    $(function(){
	    	$("#csInnerArea").val(myRound("${csInnerArea }",2));
	    });
	    	function update(){
				$("#dataForm").bootstrapValidator("validate");
				if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
				var datas = $("#dataForm").serialize();
				var innerArea=parseFloat($("#innerArea").val());
				var area=parseFloat($("#area").val());
				var innerAreaPer=parseFloat("${custType.innerAreaPer}");
				if((innerArea/area>innerAreaPer && "${hasManageAreaRight}"=="0")||area==0){
					art.dialog({
						content : "录入的套内面积太大，需要管理权限才能保存！"
					});
					return;
				}
				$.ajax({
					url : "${ctx}/admin/custDoc/doUpdateInnerArea",
					type : "post",
					data : datas,
					dataType : "json",
					cache : false,
					error : function(obj) {
						showAjaxHtml({
							"hidden" : false,
							"msg" : "保存数据出错~"
						});
					},
					success : function(obj) {
						if (obj.rs) {
							art.dialog({
								content : obj.msg,
								time : 1000,
								beforeunload : function() {
									closeWin();
								}
							});
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
		</script>
	</head>
	<body>
	 	<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
			    		<button type="submit" class="btn btn-system" id="saveBut" onclick="update()">保存</button>
	     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<ul class="ul-form">
							<input type="text" id="expired" name="expired"
								value="${customer.expired}" hidden="true" />
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy"
								value="${customer.lastUpdatedBy}" hidden="true" />
							<li><label>客户编号</label> <input type="text" id="code"
								name="code" value="${customer.code}" readonly="readonly" />
							</li>
							<li><label>楼盘</label> <input type="text" id="address"
								name="address" value="${customer.address}" readonly="readonly" />
							</li>
							<li><label>客户状态</label> <house:xtdm id="custStatus"
									dictCode="CUSTOMERSTATUS" value="${customer.status}"
									disabled="true"></house:xtdm>
							</li>
							<li><label>户型</label> <house:xtdm id="layout"
									dictCode="LAYOUT" value="${customer.layout}" disabled="true"></house:xtdm>
							</li>
							<li><label>面积</label> <input type="text" id="area"
								name="area" value="${customer.area}" readonly="readonly" />
							</li>
							<li><label style="color:blue">套内面积</label> <input type="text"
								id="innerArea" name="innerArea" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" 
								value="${customer.innerArea}" /></li>
							<li><label style="color:blue">测算套内面积</label> <input type="text"
								id="csInnerArea" name="csInnerArea"  
								value="" readonly="true"/></li>
						</ul>
					</form>
				</div>
	  		</div>
	  	</div>
	 </body>
</html>


