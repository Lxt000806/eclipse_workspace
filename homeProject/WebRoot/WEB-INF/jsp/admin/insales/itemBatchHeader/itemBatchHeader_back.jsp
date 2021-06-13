<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>材料批次--撤回</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			$("input").attr("readonly",true);
			$("select").attr("disabled",true);
			    //初始化材料类型1，2，3三级联动
		    Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");	
		    Global.LinkSelect.setSelect({firstSelect:'itemType1',
				firstValue:'${itemBatchHeader.itemType1}',
			});	
			$("#crtCzy").openComponent_employee({
				showValue:"${itemBatchHeader.crtCzy}",
				showLabel:"${itemBatchHeader.crtCzyDescr}",
				readonly:true
			});
		});
		//保存
		function save(){
			$.ajax({
				url : "${ctx}/admin/itemBatchHeader/doBack",
				type : "post",
				data : {no:"${itemBatchHeader.no}"},
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
	
	</script>
	</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<button id="saveBut" type="button" class="btn btn-system"
						onclick="save()">撤回</button>
					<button id="closeBut" type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" /><input
					type="hidden" name="isExitTip" id="isExitTip" value="0" /> <input
					type="hidden" name="m_umState" id="m_umState" value="R"/>
				<ul class="ul-form">
					<div class="validate-group row">
						<li><label>批次编号</label> <input type="text" id="no" name="no"
							value="${itemBatchHeader.no}" readonly="readonly" />
						</li>
						<li><label>创建人员</label> <input type="text" id="crtCzy"
							name="crtCzy" style="width:160px;"
							value="${itemBatchHeader.crtCzy }" />
						</li>
						<li><label>显示顺序</label> <input type="number" id="dispSeq"
							name="dispSeq" style="width:160px;"
							value="${itemBatchHeader.dispSeq}" />
						</li>
						<li >
						<label >客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;" value="${itemBatchHeader.custCode}" readonly="readonly" />
						</li>
					</div>
					<div class="validate-group row">
						<li><label>批次名称</label> <input type="text" id="remarks"
							name="remarks" value="${itemBatchHeader.remarks}" />
						</li>
						<li><label>材料类型1</label> <select id="itemType1"
							name="itemType1" value="${itemBatchHeader.itemType1}"></select>
						</li>
						<li class="form-validate"><label>适用工种</label> <house:dict
								id="workType12" dictCode="" sql="select a.Code,a.code+' '+a.descr Descr from tWorkType12 a where expired='F' "
								sqlValueKey="Code" sqlLableKey="Descr"
								value="${itemBatchHeader.workType12}">
							</house:dict>
						</li>
						<li><label>楼盘</label> <input type="text" id="address"
							name="address" style="width:160px;" value="${itemBatchHeader.address}" readonly="readonly" />
						</li>
					</div>
					<div class="validate-group row">
						<li><label>批次类型</label> <house:xtdm id="batchType"
								dictCode="BatchType" value="${itemBatchHeader.batchType}"></house:xtdm>
						</li>
						<li class="form-validate"><label>客户类型</label> <house:dict
								id="custType" dictCode="" sql="select a.ibm,cast(a.ibm as nvarchar(10))+' '+a.note descr  
								from tXTDM a   where a.ID='CUSTTYPE' and ibm !='0'  order By ibm "
								sqlValueKey="ibm" sqlLableKey="descr"
								value="${itemBatchHeader.custType}">
							</house:dict>
						</li>
						<li><label>状态</label> <house:xtdm id="status"
								dictCode="ITEMBATCHSTATUS" value="${itemBatchHeader.status}"></house:xtdm>
						</li>
						<li><label>过期</label> <input type="checkbox" id="expired"
							name="expired" value="${itemBatchHeader.expired }"
							${itemBatchHeader.expired!='F'
							?'checked':'' } 
								onclick="checkExpired(this)">
						</li>
					</div>
					<div class="validate-group row">
						<li>
							<label class="control-textarea" >备注</label>
							<textarea id="otherRemarks" name="otherRemarks" readonly="readonly" style="width:160px" >${itemBatchHeader.otherRemarks}</textarea>
						</li>
					</div>
		</div>
		</ul>
		</form>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabMainPrj" class="active"><a href="#tab_MainPrj"
				data-toggle="tab">主项目</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_MainPrj" class="tab-pane fade in active">
				<jsp:include page="itemBatchHeader_mainPrj.jsp"></jsp:include>
			</div>
		</div>
	</div>
	</div>
</body>
</html>
