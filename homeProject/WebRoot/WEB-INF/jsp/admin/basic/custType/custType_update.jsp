<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>客户类型明细--新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		if ("${custType.m_umState}"=="C"){
			$("#code").removeProp("readonly");
		}

		$("#saveBtn").on("click", function() {
			$("#prjCtrlType").removeProp("disabled");
			var datas = $("#dataForm").serialize();
			var code=$("#code").val();
			var desc1=$("#desc1").val();
			var basePerfPer=$("#basePerfPer").val();
			var prjCtrlType=$("#prjCtrlType").val();
			var signQuoteType=$("#signQuoteType").val();
			if(code==""){
				art.dialog({
					content : "请填写编号！",
					width : 200
				});
				return;
			}
			if(desc1==""){
				art.dialog({
					content : "请填写说明！",
					width : 200
				});
				return;
			}
			if(basePerfPer==""){
				art.dialog({
					content : "基础业绩比例不能为空！",
				});
				return;
			}
			if(basePerfPer>1 || basePerfPer<0){
				art.dialog({
					content : "基础业绩比例只能在0-1之间！",
				});
				return;
			}
			if(prjCtrlType==""){
				art.dialog({
					content : "发包方式不能为空！",
				});
				return;
			}
			if(signQuoteType==""){
				art.dialog({
					content : "签约报价流程不能为空！",
				});
				return;
			}
			
			if (!$("#genTaxInfo").val()) {
                art.dialog({
                    content : "请选择是否生成税务信息！",
                });
                return;
			}
			
			var url="${ctx}/admin/custType/doUpdate";
			if ("${custType.m_umState}"=="C"){
				url="${ctx}/admin/custType/doSave";
			}
			$.ajax({
				url : url,
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
					<input type="hidden" name="m_umState" id="m_umState" value="${custType.m_umState}"/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate"><label>编号</label> <input type="text" id="code"
								maxlength="10" name="code" style="width:160px;"
								value="${custType.code}"  readonly="readonly"/>
							</li>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab_info" data-toggle="tab">主项目</a>
					</li>
					<li><a href="#tab_manage" data-toggle="tab">管理费系数</a>
					</li>
					<li><a href="#tab_perf" data-toggle="tab">发包/业绩</a>
					</li>
				</ul>
				<ul class="ul-form">
					<div class="tab-content">
						<div id="tab_info" class="tab-pane fade in active">
							<jsp:include page="custType_updateTab.jsp"></jsp:include>
						</div>
						<div id="tab_manage" class="tab-pane fade>
							<jsp:include page="custType_updateManageTab.jsp"></jsp:include>
						</div>
						<div id="tab_perf" class="tab-pane fade>
							<jsp:include page="custType_updatePerfTab.jsp"></jsp:include>
						</div> 
					</div>
				</ul>
			</div>
		</div>
	</form>
</body>
</html>
