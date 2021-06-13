<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>业绩计算--指定客户</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			//回车搜索
			$('input','#page_form1').keydown(function(e){
				if(e.keyCode==13){
					doQuery('page_form1');
				}
			});
			$('input','#page_form2').keydown(function(e){
				if(e.keyCode==13){
					doQuery('page_form2');
				}
			});
			if("${perfCycle.m_umState}"=="V"){
				$(".viewFlag").attr("disabled",true);
			}
		});
		//打开的tab
		function activeTab(){
			var cyyjjsActive=$("#tabCyyjjs").attr("class");
			if(cyyjjsActive=="active"){
				return "cyyjjsDataTable";
			}
			return "bcyyjjsDataTable";
		}
		function add() {
			var selectedTab=activeTab();
			Global.Dialog.showDialog("Add", {
				title : "指定客户--增加",
				url : "${ctx}/admin/perfCycle/goPointCustAdd",
				postData:{selectedTab:selectedTab,m_umState:"A",no:"${perfCycle.no}",isCalcPerf:selectedTab=="cyyjjsDataTable"?"1":"0"},
				height : 400,
				width : 800,
				returnFun : function(){
					goto_query(selectedTab);
				}
			});
		}
		function update() {
			var selectedTab=activeTab();
			var ret = selectDataTableRow(selectedTab);
			if (ret) {
				Global.Dialog.showDialog("Update", {
					title : "指定客户--修改",
					url : "${ctx}/admin/perfCycle/goPointCustAdd",
					postData:{selectedTab:selectedTab,custCode:ret.custcode,custDescr:ret.custdescr,
					address:ret.address,isCalcPerf:ret.iscalcperf,achieveDate:ret.achievedate,isCalcPerf:selectedTab=="cyyjjsDataTable"?"1":"0",
					signDate:ret.signdate,remarks:ret.remarks,m_umState:"M",manualPerfCustPk:ret.pk,no:"${perfCycle.no}"},
					height : 400,
					width : 800,
					returnFun : function(){
						goto_query(selectedTab);
					}
				});
			} else {
				art.dialog({
					content : "请选择一条记录"
				});
			}
		}

		function view() {
			var selectedTab=activeTab();
			var ret = selectDataTableRow(selectedTab);
			if (ret) {
				Global.Dialog.showDialog("profitPerfView", {
					title : "指定客户--查看",
					url : "${ctx}/admin/perfCycle/goPointCustAdd",
					postData:{selectedTab:selectedTab,custCode:ret.custcode,custDescr:ret.custdescr,
					address:ret.address,isCalcPerf:ret.iscalcperf,achieveDate:ret.achievedate,
					signDate:ret.signdate,remarks:ret.remarks,m_umState:"V",manualPerfCustPk:ret.pk,no:"${perfCycle.no}"},
					height : 400,
					width : 800,
				});
			} else {
				art.dialog({
					content : "请选择一条记录"
				});
			}
		}	
		function del(){
			var selectedTab=activeTab();
			var ret = selectDataTableRow(selectedTab);
			if (ret) {
				art.dialog({
					content : "是否确认要删除该条记录？",
					ok : function() {
						$.ajax({
							url : "${ctx}/admin/manualPerfCust/doDelete?deleteIds=" + ret.pk,
							type : "post",
							dataType : "json",
							cache : false,
							error : function(obj) {
								art.dialog({
									content : "删除出错,请重试",
									time : 1000,
									beforeunload : function() {
										goto_query(selectedTab);
									}
								});
							},
							success : function(obj) {
								if (obj.rs) {
									goto_query(selectedTab);
								} else {
									$("#_form_token_uniq_id").val(obj.token.token);
									art.dialog({
										content : obj.msg,
										width : 200
									});
								}
							}
						});
					},
					cancel : function() {
						goto_query();
					}
				});
			} else {
				art.dialog({
					content : "请选择一条记录"
				});
			}
		}
		function doQuery(formId){
			var selectedTab=activeTab();
			$("#"+selectedTab).jqGrid("setGridParam",{postData:$("#"+formId).jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
		}
		function clearForm(formId){
			$("#"+formId+" input[type='text']").val('');
		}
	</script>
	</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<button id="closeBut" type="button" class="btn btn-system"
						onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" /><house:token></house:token><input
					type="hidden" name="isExitTip" id="isExitTip" value="0" /> <input
					type="hidden" name="m_umState" id="m_umState" />
				<ul class="ul-form">
					<div class="validate-group row">
						<li><label>周期编号</label> <input type="text" id="no" name="no"
							value="${perfCycle.no}" readonly="readonly" />
						</li>
						<li><label>归属年份</label> <input type="text" id="y" name="y"
							value="${perfCycle.y}" readonly="readonly" />
						</li>
						<li><label>归属月份</label> <input type="text" id="m" name="m"
							value="${perfCycle.m}" readonly="readonly" />
						</li>
					</div>
					<div class="validate-group row">
						<li><label>归属季度</label> <input type="text" id="season" name="season"
							value="${perfCycle.season}" readonly="readonly" />
						</li>
						<li><label>开始时间</label> <input type="text" id="beginDate"
							name="beginDate" class="i-date"
							value="<fmt:formatDate value='${perfCycle.beginDate }' pattern='yyyy-MM-dd '/>"
							readonly="readonly" />
						</li>
						<li><label>结束时间</label> <input type="text" id="endDate"
							name="endDate" class="i-date"
							value="<fmt:formatDate value='${perfCycle.endDate }' pattern='yyyy-MM-dd '/>"
							readonly="readonly" />
						</li>
					</div>
		</div>
		</ul>
		</form>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabCyyjjs" class="active" onclick="sendDetail()"><a
				href="#tab_cyyjjs" data-toggle="tab">参与业绩计算</a>
			</li>
			<li id="tabBcyyjjs" class="" onclick="returnDetail()"><a
				href="#tab_bcyyjjs" data-toggle="tab">不参与业绩计算</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_cyyjjs" class="tab-pane fade in active">
				 <jsp:include page="perfCycle_tab_cyyjjs.jsp"></jsp:include> 
			</div>
			<div id="tab_bcyyjjs" class="tab-pane fade ">
				 <jsp:include page="perfCycle_tab_bcyyjjs.jsp"></jsp:include> 
			</div>
		</div>
	</div>
	</div>
</body>
</html>
