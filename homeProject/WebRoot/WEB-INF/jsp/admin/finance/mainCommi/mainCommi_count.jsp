<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>提成计算</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		$(function(){
		 	if("${m_umState}"=="V"){
				$(".viewFlag").attr("disabled",true);
			}
		});
		//查询
		function doQuery(){
			goto_query("dlDataTable","goDlJqGrid");
			goto_query("fdlDataTable","goFdlJqGrid");
		}
		//重新加载表格 
		function goto_query(tableId,type){
			$("#"+tableId).jqGrid(
			 "setGridParam",{
				datatype:"json",
				postData:$("#page_form").jsonForm(),
				page:1,
				url:"${ctx}/admin/mainCommi/"+type
			 }
			).trigger("reloadGrid"); 
		}
		//生成提成数据
		function createData(){
			art.dialog({
				 content:"是否确认开始计算提成",
				 lock: true,
				 ok: function () {
					$.ajax({
						url:"${ctx}/admin/mainCommi/doCount",
						type: "post",
						data: {no:"${no}"},
						dataType: "json",
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
					    },
					    success: function(obj){
							doQuery();
							art.dialog({
								content	:obj.msg,
								time:1000			
							});
					    }
					 });
				},
				cancel: function () {
						return true;
				}
			}); 
		}
		//打开的tab
		function activeTab(){
			var fdlActive=$("#tabFdl").attr("class");
			if(fdlActive=="active"){
				return "fdlDataTable";
			}
			return "dlDataTable";
		}
		//导出excel
		function doExcel(){
			var selectedTab=activeTab();
			if(selectedTab=="fdlDataTable"){
				doExcelNow("非独立销售明细","fdlDataTable");
			}else{
				doExcelNow("独立销售明细","dlDataTable");
			}
		}
		//清空
		function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#custType").val("");
			$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
		} 
		//查看
		function view(m_umState){
			var selectedTab=activeTab();
			ret=selectDataTableRow(selectedTab);
			ret.m_umState=m_umState;
			if(selectedTab=="fdlDataTable"){
				Global.Dialog.showDialog("viewFdl",{
					title:"提成计算——查看非独立销售明细",
					postData:{map:JSON.stringify(ret)},
					url:"${ctx}/admin/mainCommi/goViewFdl",
					height: 600,
				 	width:700,
					returnFun: function(){
						doQuery();
					} 
				});
			}else{
				Global.Dialog.showDialog("viewDl",{
					title:"提成计算——查看独立销售明细",
					postData:{map:JSON.stringify(ret)},
					url:"${ctx}/admin/mainCommi/goViewDl",
					height: 500,
				 	width:700,
					returnFun: function(){
						doQuery();
					} 
				});
			}
		}
		//回车键搜索
		function keyQuery(){
		 	if (event.keyCode==13)  //回车键的键值为13
		  	$("#qr").click(); //调用按钮
		}
	</script>
	</head>

<body onkeydown="keyQuery();">
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<button id="createData" type="button" class="btn btn-system viewFlag"
						onclick="createData()">提成数据生成</button>
					<button id="update" type="button" class="btn btn-system viewFlag"
						onclick="view('M')">编辑</button>
					<button id="view" type="button" class="btn btn-system"
						onclick="view('V')">查看</button>
					<button id="doExcel" type="button" class="btn btn-system"
						onclick="doExcel()">导出excel</button>
					<button id="closeBut" type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="m_umState" id="m_umState"  />
					<input type="hidden" name="no" id="no"  value="${no}"/>
				<ul class="ul-form">
					<div class="validate-group row">
						<li><label>楼盘地址</label> <input type="text" id="address" name="address" 
							 />
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType" ></house:custTypeMulit>
						</li>
					</div>
					<div class="validate-group row">
						<li class="search-group-shrink">
								<button type="button" id="qr" class="btn btn-sm btn-system" onclick="doQuery()">查询</button>
								<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					</div>
		</div>
		</ul>
		</form>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabFdl" class="active"><a
				href="#tab_Fdl" data-toggle="tab">非独立销售明细</a>
			</li>
			 <li id="tabDl" class=""><a
				href="#tab_Dl" data-toggle="tab">独立销售明细</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_Fdl" class="tab-pane fade in active">
				<jsp:include page="mainCommi_tab_fdl.jsp"></jsp:include>
			</div>
			<div id="tab_Dl" class="tab-pane fade ">
				<jsp:include page="mainCommi_tab_dl.jsp"></jsp:include>
			</div>
		</div>
	</div> 
</body>
</html>
