<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>业绩计算</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		var clickCount=0;
		var clickTabIndependPerfCount=0;
		var gridOption,independPerf_gridOption;
		$(function(){
			$("#custCode").openComponent_customer();
			$("#businessMan").openComponent_employee();
			$("#designMan").openComponent_employee();
			$("#againMan").openComponent_employee();
		 	if("${m_umState}"=="V"){
				$(".viewFlag").attr("disabled",true);
				if("${status}"=="2"){
					$("#completeTip").removeClass("hidden");
				}
			}
		});
		
		//勾选框赋值
		function changeBox(id){
			if($("#"+id).val() == "1"){
				$("#"+id).val("0");
			}else{
				$("#"+id).val("1");
			}
		}
		//解决两个存在固定列的tab同时加载，第二个错乱的问题，点击时才延时加载第二个（未计算业绩表格）。
		function goWjsyj(){
			clickCount+=1;
			if(clickCount==1){//第一次点击时候加载
				setTimeout(function(){
				    	Global.JqGrid.initJqGrid("wjsyjDataTable",gridOption);
				    	$("#wjsyjDataTable").jqGrid("setFrozenColumns");
				},400);
			}
		}
		function goIndependPerf(){
			clickTabIndependPerfCount+=1;
			if(clickTabIndependPerfCount==1){//第一次点击时候加载
				console.log(independPerfDataTable);
				setTimeout(function(){            
				    	Global.JqGrid.initJqGrid("independPerfDataTable",independPerf_gridOption);
				    	$("#independPerfDataTable").jqGrid("setFrozenColumns");
				    	    
				},400);
			}
		}
		//打开的tab
		function activeTab(){
			var yjsyjActive=$("#tabYjsyj").attr("class");	
			if(yjsyjActive=="active"){
				return "yjsyjDataTable";
			}else {
			    var independPerActivef=$("#tabIndependPerf").attr("class")
				if(independPerActivef=="active"){
					return "independPerfDataTable";
				}
			}
			return "wjsyjDataTable";
		}
		//只查询打开的那个tab
		function doQuery(){
			var selectedTab=activeTab();
			console.log(selectedTab);
			if(selectedTab=="yjsyjDataTable"){
				goto_query("yjsyjDataTable");
			}else if(selectedTab=="wjsyjDataTable"){
				 $("#wjsyjDataTable").jqGrid(
				"setGridParam",{
					datatype:"json",
					postData:$("#page_form").jsonForm(),
					page:1,
					url:"${ctx}/admin/perfCycle/goWjsyjJqGrid"
				}
				).trigger("reloadGrid"); 
			}else{
				 $("#independPerfDataTable").jqGrid(
					"setGridParam",{
						datatype:"json",
						postData:$("#page_form").jsonForm(),
						page:1,
						url:"${ctx}/admin/perfCycle/goIndependPerfJqGrid"
					}
				).trigger("reloadGrid");	
			}
		}
		//生成业绩数据
		function createData(){
			art.dialog({
				 content:"是否确认开始计算业绩",
				 lock: true,
				 ok: function () {
				    var waitDialog=art.dialog({
						content: "业绩计算中，请稍候...", 
						lock: true,
						esc: false,
						unShowOkButton: true
					});
					$.ajax({
						url:"${ctx}/admin/perfCycle/doCount",
						type: "post",
						data: {no:"${no}",calChgPerf:$("#calChgPerf").val()},
						dataType: "json",
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
							 waitDialog.close();
			              	 art.dialog({
								content: "业绩计算失败！",
								width: 200
							});
					    },
					    success: function(obj){
					    	waitDialog.close();
							$("#yjsyjDataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
							art.dialog({
								content	:obj.msg,			
							});
					    }
					 });
				},
				cancel: function () {
						return true;
				}
			}); 
		}
		//扣减业绩设置
		function pefChgSet(){
			var selectedTab=activeTab();
			var ret=selectDataTableRow(selectedTab);
			var id = $("#"+selectedTab).jqGrid('getGridParam', 'selrow');
			var custCode;
			if (ret) {
				if(selectedTab=="yjsyjDataTable"){
					custCode=ret.custcode;
				}else{
					custCode=ret.CustCode;
				}
			    Global.Dialog.showDialog("pefChgSet",{
					 title:"业绩扣减设置",
					 url:"${ctx}/admin/perfCycle/goPefChgSet",
					 postData:{custCode:custCode},
					 height:300,
					 width:500,	
					 returnFun:function(){
					 	doQuery();
					 } 
				});
			} else {
			    art.dialog({
					content: "请选择一条记录"
				});
		    }
		}
		//导出excel
		function doExcel(){
			var selectedTab=activeTab();
			if(selectedTab=="yjsyjDataTable"){
				var url = "${ctx}/admin/perfCycle/doYjsyjExcel";
				doExcelAll(url,"yjsyjDataTable");
			}else if(selectedTab=="wjsyjDataTable"){
				doExcelNow("未计算业绩","wjsyjDataTable");
			}else{
				doExcelNow("独立销售业绩","independPerfDataTable");
			}
		}
		//指定客户
		function pointCust(){
			var btn="${m_umState}"=="V"?"查看":"编辑";
			Global.Dialog.showDialog("pefChgSet",{
			title:"指定客户--"+btn,
				url:"${ctx}/admin/perfCycle/goPointCust?no=${no}&m_umState=${m_umState}",
				height:700,
				width:1080
			});
		}
		//清空
		function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#checkStatus").val("");
			$.fn.zTree.getZTreeObj("tree_checkStatus").checkAllNodes(false);
		} 
	</script>
	</head>

<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<button id="createData" type="button" class="btn btn-system viewFlag"
						onclick="createData()">业绩数据生成</button>
					<button id="pointCust" type="button" class="btn btn-system"
						onclick="pointCust()">指定客户</button>
					<button id="pefChgSet" type="button" class="btn btn-system viewFlag" 
						onclick="pefChgSet()">业绩扣减设置</button>
					<button id="doExcel" type="button" class="btn btn-system"
						onclick="doExcel()">导出excel</button>
					<button id="closeBut" type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
				<input type="checkbox" style="position:absolute;left:500px;top:8px;"
					id="calChgPerf" name="calChgPerf" onclick="changeBox('calChgPerf')"
					value="0" />
					<p style=" position:absolute;left:515px;top:13px;">计算增减业绩</p>
					<p id="completeTip" style=" position:absolute;left:650px;top:13px;color:red" class="hidden">该周期业绩已计算完成！</p>
			</div>
		</div>
		<div class="panel panel-info">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="m_umState" id="m_umState"  />
					<input type="hidden" id="no" name="no" value="${no}"/>
				<ul class="ul-form">
					<div class="validate-group row">
						<li><label>客户编号</label> <input type="text" id="custCode" name="custCode"
							 />
						</li>
						<li><label>楼盘地址</label> <input type="text" id="address" name="address" style="width:452px;" 
							 />
						</li>
						<li><label>是否计算个人业绩</label> <house:xtdm id="isCalcPersonPerf"
							dictCode="YESNO"></house:xtdm>
						</li>
					</div>
					<div class="validate-group row">
					 <li><label>业绩类型</label> <house:xtdm id="perfType"
							dictCode="PERFTYPE"></house:xtdm>
					</li>
					 <li><label>人工修改</label> <house:xtdm id="isModified"
							dictCode="YESNO"></house:xtdm>
					</li>
					<li><label>数据类型</label> <house:xtdm id="dataType"
							dictCode="PERFDATATYPE"></house:xtdm>
					</li>
					<li><label>是否计算部门业绩</label> <house:xtdm id="isCalcDeptPerf"
							dictCode="YESNO"></house:xtdm>
					</li>
					</div>
					<div class="validate-group row">
					<li><label>达标时间从</label> <input type="text"
						id="achieveDateFrom" name="achieveDateFrom" class="i-date"
						style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="achieveDateTo"
						name="achieveDateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
						<li><label>复核</label> <house:xtdm id="isChecked"
							dictCode="YESNO"></house:xtdm>
					</li>
						<li><label>是否计算PK业绩</label> <house:xtdm id="isCalPkPerf"
							dictCode="YESNO"></house:xtdm>
					</li>
					</div>
					<div class="validate-group row">
						<li><label>业务员</label> <input type="text" id="businessMan" name="businessMan"
							 />
						</li>
						<li><label>设计师</label> <input type="text" id="designMan" name="designMan"
							 />
						</li>
						<li><label>翻单员</label> <input type="text" id="againMan" name="againMan"
							 />
						</li>
						<li><label>档案号</label> <input type="text" id="documentNo" name="documentNo"
							 />
						</li>
						<li>
						<label for="checkStatus">客户结算状态</label>
							<house:xtdmMulit id="checkStatus" dictCode="CheckStatus"/>
						</li>
						<li>
							<label>客户来源</label>
							<house:xtdm id="source" dictCode="CUSTOMERSOURCE" ></house:xtdm>
						</li>
						<li><label>常规变更</label>
							 <house:xtdm id="isAddAllInfo" dictCode="YESNO" ></house:xtdm>
						</li>
						 <li><label>新旧业绩</label>
							<select id="perfDateType" name="perfDateType" >
								<option value="">请选择</option>
								<option value="1">旧业绩</option>
								<option value="2">新业绩</option>
							</select>
						 </li>
						<li class="search-group-shrink">
								<input  style="position:absolute;left:30px;" type="checkbox" id="chkNoLeader" name="chkNoLeader" onclick="changeBox('chkNoLeader')" value="0" />
									<p>&nbsp&nbsp&nbsp干系人存在没有部门领导</p>
								<button type="button" class="btn btn-sm btn-system" onclick="doQuery()">查询</button>
								<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					</div>
		</div>
		</ul>
		</form>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabYjsyj" class="active"><a
				href="#tab_Yjsyj" data-toggle="tab">已计算业绩</a>
			</li>
			 <li id="tabWjsyj" class=""><a
				href="#tab_Wjsyj" data-toggle="tab" onclick="goWjsyj()">未计算业绩</a>
			</li>
			<li id="tabIndependPerf" class=""><a
				href="#tab_independPerf" data-toggle="tab" onclick="goIndependPerf()">独立客户销售业绩</a>	
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_Yjsyj" class="tab-pane fade in active">
				<jsp:include page="perfCycle_tab_yjsyj.jsp"></jsp:include>
			</div>
			<div id="tab_Wjsyj" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_wjsyj.jsp"></jsp:include>
			</div>
			<div id="tab_independPerf" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_independ.jsp"></jsp:include>
			</div>
			
		</div>
	</div> 
</body>
</html>
