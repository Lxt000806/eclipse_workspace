<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>发货时限管理--新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		//初始化材料类型1，2，3三级联动
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2");
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:'${sendTime.itemType1}',
		};
		Global.LinkSelect.setSelect(dataSet);
		if('${sendTime.m_umState}'=="V"){
			$(".view").attr("disabled",true);
		}
	});
	function addClose(){
		var dataChanged=$("#dataChanged").val();
		if(dataChanged=="1"){
			art.dialog({
				 content:"数据发生变动，是否保存更改的数据？",
				 lock: true,
				 ok: function () {
				 	save();
				},
				cancel: function () {
					closeWin(false);
				}
			}); 
		}else{
			closeWin(false);
		}
	}
	function save(){
		var itemType1=$("#itemType1").val();
		var isSetItem=$("#isSetItem").val();
		var rowNum = $("#detailDataTable").jqGrid("getGridParam","records");
		var isDisabled=$("#itemType1").prop("disabled");
		if(itemType1==""){
			art.dialog({
				content : "请选择材料类型1！"
			});
			return;
		}
		if(isSetItem=="1" && rowNum==0){
			art.dialog({
				content : "请输入发货时限明细信息！"
			});
			return;
		}
		var rets = $("#detailDataTable").jqGrid("getRowData");
		var params = {"sendTimeDetailJson": JSON.stringify(rets)};
		Global.Form.submit("dataForm","${ctx}/admin/sendTime/doSave",params,function(ret){
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
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system view" id="saveBtn" onclick="save()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="excel"
						onclick="doExcelNow('发货时限明细','detailDataTable','form')">
						<span>导出excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="addClose()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<form action="" method="post" id="dataForm" class="form-search">
			<div class="panel panel-info">
				<div class="panel-body">
					<input type="hidden" id="m_umState" name="m_umState"
						style="width:160px;" value="${sendTime.m_umState}" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li><label style="color:blue">发货时限编号</label> <input type="text" id="no"
								name="no" style="width:160px;" value="${sendTime.no}" readonly
								placeholder="保存后生成" /></li>
							<li><label style="color:blue">材料类型1</label> <select id="itemType1"
								name="itemType1"></select></li>
						</div>
						<div class="validate-group row">
							<li><label>产品类型</label> <house:xtdm id="productType"
									dictCode="APPPRODUCTTYPE" value="${sendTime.productType}"></house:xtdm>
							</li>
							<li><label style="color:blue">是否限制材料</label> <house:xtdm id="isSetItem"
									dictCode="YESNO" value="${sendTime.isSetItem}"></house:xtdm></li>
						</div>
						<div class="validate-group row">
							<li><label>送货天数</label> <input type="text" id="sendDay"
								name="sendDay" style="width:160px;"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								value="${sendTime.sendDay}" /></li>
							<li><label>优先级</label> <input type="text" id="prior"
								name="prior" style="width:160px;"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								value="${sendTime.prior}" /></li>
						</div>
						<div class="validate-group row">
							<li><label class="control-textarea">备注</label> <textarea
									id="remarks" name="remarks"
									style="overflow-y:scroll; overflow-x:hidden; height:75px; " />${sendTime.remarks
								}</textarea>
							</li>
						</div>
						<div class="validate-group row">
							<c:if test="${sendTime.m_umState!='A'}">
								<li class="form-validate"><label>过期</label> <input
									type="checkbox" id="expired" name="expired"
									value="${sendTime.expired }" ${sendTime.expired!='F'
									?'checked':'' } 
								onclick="checkExpired(this)">
								</li>
							</c:if>
						</div>
					</ul>
				</div>
			</div>
		</form>
		<div class="container-fluid" id="id_detail">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_mainDetail" data-toggle="tab">详情</a>
				</li>
			</ul>
			<div class="tab-content">
				<div id="tab_mainDetail" class="tab-pane fade in active">
					<div id="content-list">
						<jsp:include page="sendTime_detail.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
