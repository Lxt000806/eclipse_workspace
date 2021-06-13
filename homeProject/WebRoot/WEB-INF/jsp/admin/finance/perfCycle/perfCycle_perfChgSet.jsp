<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>业绩扣减设置</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#custCode").openComponent_customer({
			showValue:"${perfCycle.custCode}",
			showLabel:"${perfCycle.custDescr}",
			callBack:function(data){
				$("#address").html(data.address);
				$("#custStatus").val(data.status);
			},
			condition:{status:"4",laborFeeCustStatus:"1,2,3,5",mobileHidden:"1"}
		});
		$("#saveBtn").on("click", function() {
			var custStatus=$("#custStatus").val();
			if(custStatus!="4"){
				art.dialog({
					content : "只有施工状态的客户才能进行业绩扣减设置！",
				});
				return;
			}
			var datas = $("#dataForm").serialize();
			$.ajax({
				url : "${ctx}/admin/perfCycle/doPerfChgSet",
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
		});
	});
</script>
</head>
<body>
	<form action="" method="post" id="dataForm" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<input type="hidden" name="jsonString" value="" /> 
					<input type="hidden" id="custStatus"
								name="custStatus" value="${perfCycle.custStatus }"/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li><label>客户编号</label> <input type="text" id="custCode"
								name="custCode" value="${perfCycle.custCode }"/>
							</li><span id="address" style="position:absolute;top:54px;left:310px">${perfCycle.address }</span>
						</div>
						<div class="validate-group row">
							<li><label>业绩比例</label> <house:dict id="perfPerc"
									dictCode=""
									sql="select Code,rtrim(Code)+' '+Desc1 Descr from tPerfPerc ORDER BY Code ASC"
									sqlValueKey="Code" sqlLableKey="Descr"
									value="${perfCycle.perfPerc}">
								</house:dict>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>瓷砖状态</label> <house:xtdm id="tileStatus"
									dictCode="MaterialStatus" value="${perfCycle.tileStatus}"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>卫浴状态</label> <house:xtdm id="bathStatus"
									dictCode="MaterialStatus" value="${perfCycle.bathStatus}"></house:xtdm>
							</li>
						</div>
					</ul>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
