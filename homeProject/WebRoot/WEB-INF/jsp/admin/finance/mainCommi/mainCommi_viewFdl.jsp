<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>主材提成非独立销售明细查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
		function saveFdl(){
			$.ajax({
				url : "${ctx}/admin/mainCommi/doUpdateFdl",
				type : "post",
				data : {
					pk:$("#pk").val(),
					managercommi:$("#managercommi").val(),
					mainbusimancommi:$("#mainbusimancommi").val(),
					declaremancommi:$("#declaremancommi").val(),
					checkmancommi:$("#checkmancommi").val(),
					deptfundcommi:$("#deptfundcommi").val(),
					totalcommi:$("#totalcommi").val()
				},
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
		$(function(){
			if("${map.m_umState}"=="M"){
				$(".setColor").css("color","blue");
			}else{
				$(".setColor").each(function(){
					$(this).next().attr("readonly",true);
				});
			}
			$('#managercommi,#mainbusimancommi,#declaremancommi,#checkmancommi,#deptfundcommi').bind('input propertychange', function() {  
				doSum();
			});
		});
		//加法计算  解决js小数加法精度问题，防止出现一大串小数
		function accAdd(arg1,arg2){  
			var r1,r2,m;  
			try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}
			try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}
			m=Math.pow(10,Math.max(r1,r2));  
			return (arg1*m+arg2*m)/m;  
		}
		//合计
		function doSum(){
			var managercommi=parseFloat($("#managercommi").val());
			var mainbusimancommi=parseFloat($("#mainbusimancommi").val());
			var declaremancommi=parseFloat($("#declaremancommi").val());
			var checkmancommi=parseFloat($("#checkmancommi").val());
			var deptfundcommi=parseFloat($("#deptfundcommi").val());
			var sum0=accAdd(managercommi,mainbusimancommi);
			var sum1=accAdd(sum0,declaremancommi);
			var sum2=accAdd(sum1,checkmancommi);
			var sum3=accAdd(sum2,deptfundcommi);
			$('#totalcommi').val(sum3);  
		}
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
   				<div class="panel-body">
					<div class="btn-group-xs">
   					<c:if test="${map.m_umState=='M'}">
							<button type="button" class="btn btn-system" id="saveFdlBut"
							onclick="saveFdl()">
							<span>保存</span>
							</button>
					</c:if>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
        		<div class="panel-body">		
					<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
						<ul class="ul-form">
						<div class="validate-group row">
						<input type="hidden" id="pk" name="pk" value="${map.pk}"/>
							<li><label>档案号</label> <input type="text" id="documentNo"
								name="documentNo" style="width:160px;" value="${map.documentno}"
								readonly />
							</li>
							<li><label>客户编号</label> <input type="text" id="custCode"
								name="custCode" style="width:160px;" value="${map.custcode }"
								readonly />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>楼盘</label> <input type="text" id="address"
								name="address" style="width:160px;" value="${map.address }"
								readonly />
							</li>
							<li><label>面积</label> <input type="text"
								id="area" name="area" style="width:160px;"
								value="${map.area }" readonly />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>签订日期 </label> <input
								type="text" id="signdate" name="signdate" style="width:160px;"
								value="${map.signdate }" readonly/>
							</li>
						<li>
							<label>客户类型</label>
							<input type="text" id="custtypedescr"
								name="custtypedescr" style="width:160px;" value="${map.custtypedescr }"
								readonly />
						</li>
						</div>
						<div class="validate-group row">
							<li><label>套内结算</label> <input
								type="text" id="checkamount_inset" name="checkamount_inset" style="width:160px;"
								value="${map.checkamount_inset }" readonly/>
							</li>
						<li>
							<label>主材套外预算</label>
							 <input type="text" id="mainplanamount_outset"
								name="mainplanamount_outset" style="width:160px;" value="${map.mainplanamount_outset }"
								readonly />
						</li>
						</div>
						<div class="validate-group row">
							<li><label>主材套外增减</label> <input type="text" id="mainchgamount_outset"
								name="mainchgamount_outset" style="width:160px;"
								value="${map.mainchgamount_outset }" readonly/>
							</li>
							<li><label>主材套外结算</label> <input
								type="text" id="maincheckamount_outset" name="maincheckamount_outset"
								style="width:160px;" value="${map.maincheckamount_outset }" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>服务性套外预算</label> <input type="text" id="servplanamount_outset"
								name="servplanamount_outset" style="width:160px;"
								value="${map.servplanamount_outset }" readonly/>
							</li>
							<li><label>服务性套外增减</label> <input
								type="text" id="servchgamount_outset" name="servchgamount_outset"
								style="width:160px;" value="${map.servchgamount_outset }" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>服务性套外结算</label> <input type="text" id="servcheckamount_outset"
								name="servcheckamount_outset" style="width:160px;"
								value="${map.servcheckamount_outset }" readonly/>
							</li>
							<li><label>结算总额</label> <input
								type="text" id="checkamount" name="checkamount"
								style="width:160px;" value="${map.checkamount }" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>集采材料结算</label> <input type="text" id="checkamount_centralpurch"
								name="checkamount_centralpurch" style="width:160px;"
								value="${map.checkamount_centralpurch }" readonly/>
							</li>
							<li><label  class="setColor">经理提成</label> <input
								type="text" id="managercommi" name="managercommi"
								style="width:160px;" value="${map.managercommi }" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>主材管家</label> <input type="text" id="mainbusimandescr"
								name="mainbusimandescr" style="width:160px;"
								value="${map.mainbusimandescr }" readonly/>
							</li>
							<li><label  class="setColor">管家提成</label> <input
								type="text" id="mainbusimancommi" name="mainbusimancommi"
								style="width:160px;" value="${map.mainbusimancommi }" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label  class="setColor">报单提成</label> <input type="text" id="declaremancommi"
								name="declaremancommi" style="width:160px;"
								value="${map.declaremancommi }" />
							</li>
							<li><label  class="setColor">结算员提成</label> <input
								type="text" id="checkmancommi" name="checkmancommi"
								style="width:160px;" value="${map.checkmancommi }" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label  class="setColor">部门基金</label> <input type="text" id="deptfundcommi"
								name="deptfundcommi" style="width:160px;"
								value="${map.deptfundcommi }" />
							</li>
							<li><label>合计提成</label> <input
								type="text" id="totalcommi" name="totalcommi"
								style="width:160px;" value="${map.totalcommi }" readonly/>
							</li>
						</div>
							<div class="validate-group row">
							<li><label>人工修改</label> <input type="text" id="ismodifieddescr"
								name="ismodifieddescr" style="width:160px;"
								value="${map.ismodifieddescr }" readonly/>
							</li>
						</div>
					</ul>
					</form>	
				</div>
			</div>
		</div>
	</body>	
</html>
