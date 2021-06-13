<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
		$(function(){
			$("input").attr("disabled",true);
			$("textarea").attr("disabled",true);
		});
		function finCpl(){
			var mainCplDate = $("#mainCplDate").val();
			var intCplDate = $("#intCplDate").val();
			var softCplDate = $("#softCplDate").val();
			var confirmDate = $("#confirmDate").val();
			var tips = "";
			if(mainCplDate == ""){
				tips += "主材结算未完成<br/>";
			}
			if(intCplDate == ""){
				tips += "集成结算未完成<br/>";
			}
			if(softCplDate == ""){
				tips += "软装结算未完成<br/>";
			}
			if(confirmDate == ""){
				tips += "竣工验收未完成<br/>";
			}
			tips += "确定完成?";
			art.dialog({
				content: tips,
				width: 200,
				ok:function(){
					doFinCpl();
				},
				cancel:function(){
				
				}
			});
		}
		function doFinCpl(){
			$.ajax({
				url:'${ctx}/admin/CustCheck/doUpdateFinCpl?custCode='+$("#custCode").val(),
				type: 'post',
				dataType: 'json',
				cache: false,
				error: function(obj){			    		
					art.dialog({
						content: '保存出错,请重试',
						time: 1000,
						beforeunload: function () {
							closeWin();
						}
					});
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
    				<button type="button" class="btn btn-system " id="finBut" onclick="finCpl()">财务完成</button>
    				<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">关闭</button>
		    	</div>
		    </div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" enctype="multipart/form-data" class="form-search">
					<input type="hidden" id="custCode" name="custCode" value="${custCheck.custCode }"/>
					<ul class="ul-form">
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" value="${custCheck.address}" />
						</li>
						<li>
							<label>申请日期</label>
							<input type="text" id="appDate" name="appDate" value="<fmt:formatDate value='${custCheck.appDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>
						
						<li>
							<label>主材结算状态</label>
							<input type="text" id="mainStatusDescr" name="mainStatusDescr" value="${custCheck.mainStatusDescr}" />
						</li>
						<li>
							<label>主材接收日期</label>
							<input type="text" id="mainRcvDate" name="mainRcvDate" value="<fmt:formatDate value='${custCheck.mainRcvDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>

						<li>
							<label>主材接收人</label>
							<input type="text" id="mainCZY" name="mainCZY" value="${custCheck.mainCZY}" />
						</li>
						<li>
							<label>主材完成日期</label>
							<input type="text" id="mainCplDate" name="mainCplDate" value="<fmt:formatDate value='${custCheck.mainCplDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>
					
						<li class="form-validate">
							<label class="control-textarea">主材备注</label>
							<textarea id="mainRemark" name="mainRemark" />${custCheck.mainRemark }</textarea>
						</li>
						
						<li>	
							<label>集成结算状态</label>
							<input type="text" id="intStatusDescr" name="intStatusDescr" value="${custCheck.intStatusDescr}" />
						</li>
						<li>
							<label>集成接收日期</label>
							<input type="text" id="intRcvDate" name="intRcvDate" value="<fmt:formatDate value='${custCheck.intRcvDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>
						
						<li>		
							<label>集成接收人</label>
							<input type="text" id="intCZY" name="intCZY" value="${custCheck.intCZY}" />
						</li>
						<li>
							<label>集成完成日期</label>
							<input type="text" id="intCplDate" name="intCplDate" value="<fmt:formatDate value='${custCheck.intCplDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>

						<li class="form-validate">	
							<label class="control-textarea">集成备注</label>
							<textarea id="intRemark" name="intRemark"/>${custCheck.intRemark }</textarea>
						</li>

						<li>	
							<label>软装结算状态</label>
							<input type="text" id="softStatusDescr" name="softStatusDescr" value="${custCheck.softStatusDescr}" />
						</li>
						<li>
							<label>软装接收日期</label>
							<input type="text" id="softRcvDate" name="softRcvDate" value="<fmt:formatDate value='${custCheck.softRcvDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>
			
						<li>	
							<label>软装接收人</label>
							<input type="text" id="softCZY" name="softCZY" value="${custCheck.softCZY}" />
						</li>
						<li>
							<label>软装完成日期</label>
							<input type="text" id="softCplDate" name="softCplDate" value="<fmt:formatDate value='${custCheck.softCplDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>
			
						<li class="form-validate">	
							<label class="control-textarea">软装备注</label>
							<textarea id="softRemark" name="softRemark"/>${custCheck.softRemark }</textarea>
						</li>
		
						<li>	
							<label>竣工验收人</label>
							<input type="text" id="confirmCZY" name="confirmCZY" value="${custCheck.confirmCZY}" />
						</li>
						<li>
							<label>竣工验收日期</label>
							<input type="text" id="confirmDate" name="confirmDate" value="<fmt:formatDate value='${custCheck.confirmDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>
	
						<li>	
							<label>财务结算状态</label>
							<input type="text" id="finStatusDescr" name="finStatusDescr" value="${custCheck.finStatusDescr}" />
						</li>
						<li>
							<label>财务接收人</label>
							<input type="text" id="finCZY" name="finCZY" value="${custCheck.finCZY}" />
						</li>
				
						<li>
							<label>财务接收日期</label>
							<input type="text" id="finRcvDate" name="finRcvDate" value="<fmt:formatDate value='${custCheck.finRcvDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>
						<li>
							<label>财务完成日期</label>
							<input type="text" id="finCplDate" name="finCplDate" value="<fmt:formatDate value='${custCheck.finCplDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						</li>
			
						<li class="form-validate">
							<label class="control-textarea">财务备注</label>
							<textarea id="finRemark" name="finRemark" />${custCheck.finRemark }</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
