<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>模板区间管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		if('${baseBatchTemp.m_umState}'=="V"){
			$(".view").attr("disabled",true);
			$("#areaNo,#areaType,#areaDescr").attr("disabled",true);
		}
		
		$("#saveBtn").on("click", function() {
			var areaType=$("#areaType").val();
			var areaDescr=$("#areaDescr").val();
			if(areaType==""){
				art.dialog({
					content : "请选择区域类型！",
					width : 200
				});
				return;
			}
			if(areaDescr==""){
				art.dialog({
					content : "请输入区域名称！",
					width : 200
				});
				return;
			}
			var records = $("#detailDataTable").jqGrid('getGridParam', 'records'); //获取数据总条数
			if(records==0){
				art.dialog({
					content : "明细不能为空！",
					width : 200
				});
				return;
			}
			var typeResult=checkExistType();
			if(areaType!="1" && typeResult){
				getDescrByCode('areaType','areaTypeDescr');
				art.dialog({
					content : "已存在【"+$("#areaTypeDescr").val()+"】类型，不允许保存",
				});
				return;
			}
			var descrResult=checkExistDescr();
			if(descrResult){
				art.dialog({
					content : "区域名称【"+areaDescr+"】已存在，不允许保存",
				});
				return;
			}
			doSave();
		});
	});
	function doSave(){
		var rets = $("#detailDataTable").jqGrid("getRowData");
		var params = {"baseBatchTempItemJson": JSON.stringify(rets)};
		Global.Form.submit("dataForm","${ctx}/admin/baseBatchTemp/doSaveItem",params,function(ret){
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
	function addClose(){
		var dataChanged=$("#dataChanged").val();
		if(dataChanged=="1"){
			art.dialog({
				 content:"数据发生变动，是否保存被更改的数据？",
				 lock: true,
				 cancelValue:"否",
				 ok: function () {
					$("#saveBtn").trigger("click");
				},
				cancel: function () {
						closeWin(false);
				}
			}); 
		}else{
			closeWin(false);
		}
	}
	function getDescrByCode(codeId,descrId){
		var selectText=$("#"+codeId).find("option:selected").text();
		var arr = selectText.split(" ");
		var descr=arr[1];
		if(descr=="请选择..."){
			descr="";
		}
		if(descr!="固定一项"){
			$("#"+descrId).val(descr);
		}else{
			$("#"+descrId).val("");
		}
	}
	function checkExistType(){
		var result=false;
		var areaType=$("#areaType").val();
		$.ajax({
			url : '${ctx}/admin/baseBatchTemp/checkExistType',
			type : 'post',
			data : {
				'no' :"${baseBatchTemp.no}",'areaType':areaType,
				'm_umState':"${baseBatchTemp.m_umState}",'areaNo':"${baseBatchTemp.areaNo}"
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
					result=true;
				}
			}
		});
		return result;
	}
	function checkExistDescr(){
		var result=false;
		var areaDescr=$("#areaDescr").val();
		$.ajax({
			url : '${ctx}/admin/baseBatchTemp/checkExistDescr',
			type : 'post',
			data : {
				'no' :"${baseBatchTemp.no}",'areaDescr':$("#areaDescr").val(),
				'areaNo':"${baseBatchTemp.areaNo}",'m_umState':"${baseBatchTemp.m_umState}"
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
					result=true;
				}
			}
		});
		return result;
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
					<input type="hidden" id="m_umState" name="m_umState"
						style="width:160px;" value="${baseBatchTemp.m_umState}" />
					<input type="hidden" id="areaTypeDescr" name="areaTypeDescr"
						style="width:160px;" value="${baseBatchTemp.areaTypeDescr}" />
						<input type="hidden" id="no" name="no"
						style="width:160px;" value="${baseBatchTemp.no}" />
						<input type="hidden" id="descr" name="descr"
						style="width:160px;" value="${baseBatchTemp.descr}" />
						<input type="hidden" id="dataChanged" name="dataChanged"
						style="width:160px;" value="" />
					<ul class="ul-form">
						<div class="row">
							<li><label>区域编号</label> <input
								type="text" id="areaNo" name="areaNo" style="width:160px;"
								value="${baseBatchTemp.areaNo}" placeholder="保存时自动生成" readonly />
							</li>
							<li><label>区域类型</label> <house:xtdm id="areaType"
                                     dictCode="AREATYPE" value="${baseBatchTemp.areaType}" onchange="getDescrByCode('areaType','areaDescr')"></house:xtdm>
                            </li>
							<li><label>区域名称</label> <input
								type="text" id="areaDescr" name="areaDescr" style="width:160px;"
								value="${baseBatchTemp.areaDescr}" />
							</li>
						</div>
					</ul>
				</div>
			</div>
	</form>
	<div class="container-fluid" id="id_detail">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#tab_item" data-toggle="tab">材料</a>
			</li>
		</ul>
	 	<div class="tab-content">
			<div id="tab_item" class="tab-pane fade in active">
				<div id="content-list">
					<jsp:include page="baseBatchTemp_item_tab.jsp"></jsp:include>
				</div>
			</div>
		</div> 
	</div>
	</div>
</body>
</html>
