<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>快速预报价模板--新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		if('${prePlanTemp.m_umState}'=="V"){
			$(".view").attr("disabled",true);
			$("#descr").attr("readonly",true);
			$("#expired").attr("disabled",true);
			$(".copy").hide();
		}else if('${prePlanTemp.m_umState}'=="M"){
			$(".copy").hide();
		}
		
		$("#saveBtn").on("click", function() {
			var descr=$("#descr").val();
			var custType=$("#custType").val();
			if(descr==""||custType==""){
				art.dialog({
					content : "请填写完整信息！",
					width : 200
				});
				return;
			}
			var records = $("#detailDataTable").jqGrid('getGridParam', 'records'); //获取数据总条数
			if(records==0){
				art.dialog({
					content : "请输入快速预报价明细信息！",
					width : 220
				});
				return;
			}
			$("#custType").removeAttr("disabled");
			var prePlanTempDetailJson = Global.JqGrid.allToJson("detailDataTable");
			var param = {"prePlanTempDetailJson": JSON.stringify(prePlanTempDetailJson)};
				Global.Form.submit("dataForm","${ctx}/admin/prePlanTemp/doSave",param,function(ret){
					if(ret.rs==true){
						art.dialog({
							content: ret.msg,
							time: 1000,
							beforeunload: function () {
			    				$("#custType").attr("disabled",true);
			    				closeWin();
						    }
						});
					}else{
						$("#custType").attr("disabled",true);
						$("#_form_token_uniq_id").val(ret.token.token);
			    		art.dialog({
							content: ret.msg,
							width: 200
						});
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
						<button type="button" class="btn btn-system view" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="addClose()">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<input type="hidden" id="m_umState" name="m_umState" style="width:160px;" value="${prePlanTemp.m_umState}"  />
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate"><label>编号</label> <input type="text" id="no"
								 name="no" style="width:160px;"
								value="${prePlanTemp.no}" readonly />
							</li>
							<li class="form-validate"><label>名称</label> <input type="text" id="descr"
								 name="descr" style="width:160px;"
								value="${prePlanTemp.descr}"/>
							</li>
							<li class="form-validate"><label>客户类型</label> <house:dict
									id="custType" dictCode=""
									sql="select code ,code +' ' + desc1 descr from tcustType where expired='F'"
									sqlValueKey="code" sqlLableKey="descr"
									value="${prePlanTemp.custType}">
								</house:dict>
							</li>
							<c:if test="${prePlanTemp.m_umState!='A'}">
							<li class="form-validate"><label>过期</label> <input type="checkbox" id="expired" name="expired" value="${prePlanTemp.expired }" 
								${prePlanTemp.expired!='F' ?'checked':'' } 
								onclick="checkExpired(this)">
							</li>
							</c:if>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab_mainDetail" data-toggle="tab">快速预报价模板明细</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tab_mainDetail" class="tab-pane fade in active">
						<div id="content-list">
							<jsp:include page="prePlanTemp_detail.jsp"></jsp:include>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
