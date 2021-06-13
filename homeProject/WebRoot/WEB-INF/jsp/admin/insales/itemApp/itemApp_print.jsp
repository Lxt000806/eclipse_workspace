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
		<title>领料管理打印页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	
		<script type="text/javascript">
		$(function(){
			$("#whcode").openComponent_wareHouse({
				showValue:"${data.whcode}",
				showLabel:"${data.whcodeDescr}",
				callBack:function(ret){
					$("#whcodeDescr").val(ret.desc1);
				}
			});
			$("#openComponent_wareHouse_whcode").attr("readonly", true);
			$("#printTypeGroup li").find("input[type=radio]").css("margin-top", "0px").on("click",function(){
				var lis = $("#printTypeGroup li").find("input[type=radio]");
				for(var i=0;i<lis.length;i++){
					lis[i].checked = false;
					$(lis[i]).removeAttr("checked");
				}
				var ele = $(this).parent().find("input[type=radio]");
				ele.attr("checked",true);
				ele.get(0).checked = true;
				if(ele.get(0).value == "fhwl"){
					$("#itemAppSend").parent().css("display", "none");
					$("#whcode").parent().parent().css("display", "block");
				}else if(ele.get(0).value == "fpfh"){
					$("#itemAppSend").val("");
					$("#whcode").parent().parent().css("display", "none");
					$("#itemAppSend").parent().css("display", "block");
				}else{
					$("#itemAppSend").parent().css("display", "none");
					$("#whcode").parent().parent().css("display", "none");
				}
			});
			$("#printTypeGroup li").find("input[type=text]").css({
				"width":"60%",
				"border":"0px",
				"font-size":"14px"
			}).attr("readonly", true).on("click",function(){
				var lis = $("#printTypeGroup li").find("input[type=radio]");
				for(var i=0;i<lis.length;i++){
					lis[i].checked = false;
					$(lis[i]).removeAttr("checked");
				}
				var ele = $(this).parent().find("input[type=radio]");
				ele.attr("checked",true);
				ele.get(0).checked = true;
				if(ele.get(0).value == "fhwl"){
					$("#itemAppSend").parent().css("display", "none");
					$("#whcode").parent().parent().css("display", "block");
				}else if(ele.get(0).value == "fpfh"){
					$("#itemAppSend").val("");
					$("#whcode").parent().parent().css("display", "none");
					$("#itemAppSend").parent().css("display", "block");
				}else{
					$("#itemAppSend").parent().css("display", "none");
					$("#whcode").parent().parent().css("display", "none");
				}
			});
			$("#printTypeGroup li").css({
				"margin-top":"6%",
				"margin-left":"10%"
			});
			$("#dh").get(0).checked = true;
		});
		function print(){
			var printSelected = $("#printTypeGroup input[type=radio][name=printType]:checked").val();

			if(printSelected == "fpfh" && $("#itemAppSend").val() == ""){
				art.dialog({content:"请选择分批发货单"});
				return;
			}
			$.ajax({
				url:"${ctx}/admin/itemApp/getBeforeDoPrint",
				type: "post",
		    	data: {
		    		from:printSelected,
		    		nos:"${data.no}",
					checkFH:$("#checkSend").val(),
					whcode:$("#whcode").val()
		    	},
				dataType: "json",
				cache: false,
				async:false,
				error: function(obj){			    		
					art.dialog({
						content: "访问出错,请重试",
						time: 3000,
						beforeunload: function () {}
					});
				},
				success: function(obj){
					if(obj.datas.rs){
						if(printSelected == "dh"){
							Global.Print.showPrint("llglDH.jasper", 
								{
									appNo:"${data.no}",
									LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
									SUBREPORT_DIR: "<%=jasperPath%>" 
								}, null, 
								function(){
									art.dialog({
										content:"是否已打印报表?",
										ok:function(){
											updatePrint("${data.no}");
										},
										cancel:function(){},
										okValue:"是",
										cancelValue:"否"
									});
								}
							);
						}else if(printSelected == "fhwl"){
							Global.Print.showPrint("llglFHWL.jasper", 
								{
									appNo:"${data.no}",
									checkFH:$("#checkSend").val(),
									whcode:$("#whcode").val(),
									LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
									SUBREPORT_DIR: "<%=jasperPath%>" 
								}, null, 
								function(){
									art.dialog({
										content:"是否已打印报表?",
										ok:function(){
											updatePrint("${data.no}");
										},
										cancel:function(){},
										okValue:"是",
										cancelValue:"否"
									});
								}
							);
						}else{
							Global.Print.showPrint("llglOther.jasper", 
								{
									appNo:"${data.no}",
									from:printSelected,
									itemSendNo:$("#itemAppSend").val(),
									LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
									SUBREPORT_DIR: "<%=jasperPath%>" 
								}, null, 
								function(){
									art.dialog({
										content:"是否已打印报表?",
										ok:function(){
											updatePrint("${data.no}");
										},
										cancel:function(){},
										okValue:"是",
										cancelValue:"否"
									});
								}
							);
						} 
					}else{
						art.dialog({
							content:obj.datas.errorInfo
						});
					}
				}
			});
		}
		function checkSendChange(){
			if($("#checkSend").val() == "F"){
				$("#checkSend").val("T");
			}else{
				$("#checkSend").val("F");
			}	
		}
		function updatePrint(no){
			$.ajax({
				url:"${ctx}/admin/itemApp/doUpdatePrint",
				type: "post",
		    	data: {
		    		nos:no
		    	},
				dataType: "json",
				cache: false,
				error: function(obj){			    		
					art.dialog({
						content: "访问出错,请重试",
						time: 3000,
						beforeunload: function () {}
					});
				},
				success: function(obj){
					if(!obj.rs){
						art.dialog({
							content:obj.msg
						});
					}
				}
			});
		}
		</script>
	
	</head>
	    
	<body>
		<div class="body-box-list" >
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	    				<button id="saveBut" type="button" class="btn btn-system "   onclick="print()">打印</button>
	    				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<input type="hidden" id="isManagePosi" name="isManagePosi" value="${data.isManagePosi }" />
			<input type="hidden" id="custCode" name="custCode" value="${data.custCode }" />
			<div style="padding-top:10px">
				<ul id="printTypeGroup" style="float:left;width:40%;height:250px;">
					<li>
						<input type="radio" id="dh" name="printType" value="dh" /><input type="text" value="打印订货单" />
					</li>
					<li>
						<input type="radio" id="fh" name="printType" value="fh" /><input type="text" value="打印发货单" />
					</li>
					<li>
						<input type="radio" id="wfh" name="printType" value="wfh" /><input type="text" value="打印未发货单" />
					</li>
					<li>
						<input type="radio" id="fpfh" name="printType" value="fpfh" /><input type="text" value="打印分批发货单" />
					</li>
					<li>
						<input type="radio" id="fhwl" name="printType" value="fhwl" /><input type="text" value="打印发货单(物流)" />
					</li>
					<li>
						<input type="radio" id="zx" name="printType" value="zx" /><input type="text" value="打印增项单" />
					</li>
				</ul>
				<from class="form-search" style="float:left;width:60%;height:250px;">
					<ul class="ul-form">
						<li>
							<label>打印次数</label>
							<input type="text" id="printTimes" name="printTimes" value="${data.printTimes }" />
						</li>
						<li>
							<label>打印人</label>
							<input type="text" id="printCZYDescr" name="printCZYDescr" value="${data.printCZYDescr }" />
							<input type="hidden" id="printCZY" name="printCZY" value="${data.printCZY }" />
						</li>
						<li>
							<label>打印日期</label>
							<input type="text" id="printDate" name="printDate" class="i-date" 
									value="<fmt:formatDate value='${data.printDate }' pattern='yyyy-MM-dd hh:mm:ss'/>" />
						</li>
						<li style="display:none">
							<label></label>
							<house:dict id="itemAppSend" dictCode="" 
										sql="select No,rtrim(No)+' '+convert(varchar(20),date,120) fd from tItemAppSend where IANo='${data.no }'"  
							            sqlValueKey="No" sqlLableKey="fd">
							</house:dict>
						</li>
						<div style="display:none">
							<li>
								<label>仓库编号</label>
								<input type="text" id="whcode" name="whcode" />
								<input type="checkbox" id="checkSend" name="checkSend" onchange="checkSendChange()" value="F"/>包含发货
							</li>
							<li>
								<label></label>
								<input type="text" id="whcodeDescr" name="whcodeDescr" value="${data.whcodeDescr }" style="border:0px;background:white"/>
							</li>
						</div>
					</ul>
				</from>
			</div>
		</div>
	</body>
</html>
