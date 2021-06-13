<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>集成进度明细--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">

	//根据id查descr
	function findDescr(cbm,id,taget) {
		$.ajax({
			url : '${ctx}/admin/intProgDetail/findDescr',
			type : 'post',
			data : {
				'cbm' : $("#"+cbm).val(),
				'id' : id
			},
			async:false,
			dataType : 'json',
			cache : false,
			error : function(obj) {
				console.log(obj);
				showAjaxHtml({
					"hidden" : false,
					"msg" : '保存数据出错~'
				});
			},
			success : function(obj) {
				$("#" + taget).val(obj.note);
			}
		});
	}

	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				resPart : {
					validators : {
						notEmpty : {
							message : '请选择责任方'
						}
					}
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<input type="hidden" id="isCup" name="isCup" style="width:160px;"
							 />
						<input type="hidden" id="typedescr" name="typedescr"
							style="width:160px;"  />
						<input type="hidden" id="resPartDescr" name="resPartDescr"
							style="width:160px;"/>
						<input type="hidden" id="cancelResonDescr" name="cancelResonDescr"
							style="width:160px;"/>
						<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy"
							style="width:160px;" value="${intProgDetail.lastUpdatedBy }" />
						<div class="validate-group row">
							<li class="form-validate"><label>客户编号</label> <input
								type="text" id="custCode" name="custCode" style="width:160px;"
								value="${intProgDetail.custCode }" readonly />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>楼盘</label> <input
								type="text" id="address" name="address" style="width:160px;"
								value="${intProgDetail.address}" readonly />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>类型</label> <house:xtdm
									id="type" dictCode="INTDTTYPE" value="${intProgDetail.type }"
									disabled="true"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>日期</label> <input
								type="text" id="date" name="date" class="i-date"
								style="width:160px;" disabled="true"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${intProgDetail.date}' pattern='yyyy-MM-dd'/>" />
							</li>
						</div>
						<c:if test="${showZrf=='yes' }">
							<div class="validate-group row">
								<li class="form-validate"><label>责任方</label> <house:xtdm
										id="resPart" dictCode="RESPART" disabled="true"
										value="${intProgDetail.resPart }"></house:xtdm>
								</li>
							</div>
						</c:if>
						<c:if test="${cantInstall=='yes'}">
						<div class="validate-group row" >
							<li class="form-validate"><label>是否橱柜</label> <house:xtdm id="isCupboard"
									dictCode="YESNO"  value="${intProgDetail.isCupboard }" disabled="true"></house:xtdm>
							</li>
						</div>
						</c:if>
						<c:if test="${isReturn=='yes'}">
						<div class="validate-group row" >
						 <li class="form-validate"><label>退单原因</label> <house:xtdm id="resPart"
									dictCode="CANCELRESON"  value="${intProgDetail.cancelReson } " disabled="true"></house:xtdm>
						</li>
						</div>
						</c:if>
						<div class="validate-group row">
							<li class="form-validate"><label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" disabled="true">${intProgDetail.remarks }</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
