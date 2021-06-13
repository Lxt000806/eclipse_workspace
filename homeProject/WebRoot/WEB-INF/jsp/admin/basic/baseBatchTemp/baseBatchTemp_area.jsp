<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>基础批量报价模板--区域配置</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		if('${baseBatchTemp.m_umState}'=="V"){
			disabledForm("dataForm");
			$("#saveBtn").val();
		}
		
		$("#saveBtn").on("click", function() {
			var descr=$("#descr").val();
			if(descr==""){
				art.dialog({
					content : "请填写完整信息！",
					width : 200
				});
				return;
			}
			var records = $("#detailDataTable").jqGrid('getGridParam', 'records'); //获取数据总条数
			if(records==0){
				art.dialog({
					content : "请输入基础算量明细信息！",
					width : 200
				});
				return;
			}
			$.ajax({
				url : '${ctx}/admin/baseBatchTemp/checkExist',
				type : 'post',
				data : {
					'descr' :$("#descr").val(),'custType':$("#custType").val(),'no':"${baseBatchTemp.no}",
				},
				async:false,
				dataType : 'json',
				cache : false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : '保存数据出错~'
					});
					
				},
				success : function(obj) {
					if(obj.length>0){
						art.dialog({
							content : "名称和客户类型都已经存在，无法保存！",
							width : 250
						});
					}else{
						var baseBatchTempDetailJson = Global.JqGrid.allToJson("detailDataTable");
						var param = {"baseBatchTempDetailJson": JSON.stringify(baseBatchTempDetailJson)};
							Global.Form.submit("dataForm","${ctx}/admin/baseBatchTemp/doSave",param,function(ret){
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
				}
			});
	
		});
	});
	function addClose(){
		var dataChanged=$("#dataChanged").val();
		if(dataChanged=="1"){
			art.dialog({
				 content:"数据发生变动，是否确认退出？",
				 lock: true,
				 width: 250,
				 height: 100,
				 ok: function () {
					closeWin(false);
				},
				cancel: function () {
						return true;
				}
			}); 
		}else{
			closeWin(false);
		}
	}
</script>
</head>
<body>
	<form action="" method="post" id="dataForm" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="addClose()">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<input type="hidden" id="m_umState" name="m_umState"
						style="width:160px;" value="${baseBatchTemp.m_umState}" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate"><label>模板编号</label> <input
								type="text" id="no" name="no" style="width:160px;"
								value="${baseBatchTemp.no}" readonly />
							</li>
							<li class="form-validate"><label>模板名称</label> <input
								type="text" id="descr" name="descr" style="width:160px;"
								value="${baseBatchTemp.descr}" readonly/>
							</li>
							<li class="form-validate"><label>客户类型</label> <house:dict
									id="custType" dictCode=""
									sql="select code ,code +' ' + desc1 descr from tcustType where expired='F'"
									sqlValueKey="code" sqlLableKey="descr"
									value="${baseBatchTemp.custType}" disabled="true">
								</house:dict>
							</li>
						</div>
					</ul>
				</div>
			</div>
	</form>
	<div class="container-fluid" id="id_detail">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#tab_area" data-toggle="tab">模板区域管理</a>
			</li>
		</ul>
	 	<div class="tab-content">
				<div id="tab_area" class="tab-pane fade in active">
					<div id="content-list">
						<jsp:include page="baseBatchTemp_area_tab.jsp"></jsp:include>
					</div>
				</div>
			</div> 
	</div>
	</div>
</body>
</html>
