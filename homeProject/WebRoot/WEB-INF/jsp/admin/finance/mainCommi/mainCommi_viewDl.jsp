<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>主材提成独立销售明细查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
		function saveDl(){
			$.ajax({
				url : "${ctx}/admin/mainCommi/doUpdateDl",
				type : "post",
				data : {pk:$("#pk").val(),businessmancommi:$("#businessmancommi").val()},
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
			}
		});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<c:if test="${map.m_umState=='M'}">
						<button type="button" class="btn btn-system" id="saveDlBut"
						onclick="saveDl()">
						<span>保存</span>
						</button>
					</c:if>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<ul class="ul-form">
						<div class="validate-group row">
							<input type="hidden" id="pk" name="pk" value="${map.pk}" />
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
							<li><label>签订日期 </label> <input type="text"
								id="signdate" name="signdate" style="width:160px;"
								value="${map.signdate }" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>客户类型</label> <input type="text" id="custtypedescr"
								name="custtypedescr" style="width:160px;" value="${map.custtypedescr }" readonly/>
							</li>
							<li><label> 销售类型</label> <input type="text" id="saletypedescr"
								name="saletypedescr" style="width:160px;" value="${map.saletypedescr }" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>主材结算</label> <input type="text" id="maincheckamount"
								name="maincheckamount" style="width:160px;" value="${map.maincheckamount }" readonly/>
							</li>
							<li><label>服务性结算</label> <input type="text" id="servcheckamount"
								name="servcheckamount" style="width:160px;" value="${map.servcheckamount }" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>结算总额</label> <input type="text" id="checkamount"
								name="checkamount" style="width:160px;" value="${map.checkamount }" readonly/>
							</li>
							<li><label>电器销售额</label> <input type="text" id="elecsaleamount"
								name="elecsaleamount" style="width:160px;"
								value="${map.elecsaleamount }" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>电器成本</label> <input type="text"
								id="eleccost" name="eleccost" style="width:160px;"
								value="${map.eleccost }" readonly/>
							</li>
							<li><label>业务员</label> <input type="text" id="businessmandescr"
								name="businessmandescr" style="width:160px;" value="${map.businessmandescr }" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label class="setColor">业务员提成</label> <input type="text" id="businessmancommi"
								name="businessmancommi" style="width:160px;"
								value="${map.businessmancommi }" />
							</li>
							<li><label>主材管家</label> <input type="text" id="mainbusimandescr"
								name="mainbusimandescr" style="width:160px;"
								value="${map.mainbusimandescr }" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label  class="setColor">管家提成</label> <input
								type="text" id="mainbusimancommi" name="mainbusimancommi"
								style="width:160px;" value="${map.mainbusimancommi }" />
							</li>
							<li><label  class="setColor">报单提成</label> <input type="text" id="declaremancommi"
								name="declaremancommi" style="width:160px;"
								value="${map.declaremancommi }" />
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
