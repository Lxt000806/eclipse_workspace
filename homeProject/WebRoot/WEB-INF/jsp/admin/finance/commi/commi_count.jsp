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
			$("#custCode").openComponent_customer();
			$("#designMan").openComponent_employee();
			$("#sceneDesignMan").openComponent_employee();
			$("#itemCode").openComponent_item();
		 	if("${m_umState}"=="V"){
				$(".viewFlag").attr("disabled",true);
			}
		});
		//查询
		function doQuery(){
			goto_query("custDataTable");
			goto_query("itemDataTable");
		} 
		//生成提成数据
		function createData(){
			var sContent= '<input type="checkbox" id="isRegenChecked"/>重新生成提成比例<br/>注：勾选此项，在计算提成时，材料提成比例将重新计算生成！<br/>是否确认开始计算提成？ ';
			art.dialog({
				 content:sContent,
				 lock: true,
				 width: 280,
		 		 height: 120,
				 ok: function () {
				 	var isRegenCommiPerc='0'
				 	if ($("#isRegenChecked").is(':checked')){
				 		isRegenCommiPerc='1'
				 	}
					$.ajax({
						url:"${ctx}/admin/commi/doCount",
						type: "post",
						data: {no:"${no}",isRegenCommiPerc:isRegenCommiPerc},
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
			var custActive=$("#tabCust").attr("class");
			if(custActive=="active"){
				return "custDataTable";
			}
			return "itemDataTable";
		}
		//导出excel
		function doExcel(){
			var selectedTab=activeTab();
			if(selectedTab=="custDataTable"){
				var url = "${ctx}/admin/commi/doCustExcel";
				doExcelAll(url,"custDataTable");
			}else{
				var url = "${ctx}/admin/commi/doItemExcel";
				doExcelAll(url,"itemDataTable");
			}
		}
		//清空
		function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#commiType").val("");
			$.fn.zTree.getZTreeObj("tree_commiType").checkAllNodes(false);
		} 
		//查看
		function view(){
			var selectedTab=activeTab();
			ret=selectDataTableRow(selectedTab);
			if(selectedTab=="custDataTable"){
				Global.Dialog.showDialog("viewCust",{
					title:"提成计算——查看楼盘明细",
					postData:{pk:ret.pk},
					url:"${ctx}/admin/commi/goViewCust",
					height: 380,
				 	width:750,
					returnFun: goto_query 
				});
			}else{
				Global.Dialog.showDialog("viewItem",{
					title:"提成计算——查看材料明细",
					postData:{pk:ret.pk},
					url:"${ctx}/admin/commi/goViewItem",
					height: 380,
				 	width:1350,
					returnFun: goto_query 
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
					<button id="view" type="button" class="btn btn-system"
						onclick="view()">查看</button>
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
						<label>提成类型</label>
							<house:xtdmMulit id="commiType" dictCode="COMMICUSTTYPE"/>
						</li>
						<li><label>设计师</label> <input type="text" id="designMan" name="designMan"
							 />
						</li>
						<li><label>现场设计师</label> <input type="text" id="sceneDesignMan" name="sceneDesignMan"
							 />
						</li>
					</div>
					<div class="validate-group row">
						<li><label>客户编号</label> <input type="text" id="custCode" name="custCode"
							 />
						</li>
						<li><label>材料编号</label> <input type="text" id="itemCode" name="itemCode"
							 />
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
			<li id="tabCust" class="active"><a
				href="#tab_Cust" data-toggle="tab">楼盘明细</a>
			</li>
			 <li id="tabItem" class=""><a
				href="#tab_Item" data-toggle="tab">材料明细</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_Cust" class="tab-pane fade in active">
				<jsp:include page="commi_tab_cust.jsp"></jsp:include>
			</div>
			<div id="tab_Item" class="tab-pane fade ">
				<jsp:include page="commi_tab_item.jsp"></jsp:include>
			</div>
		</div>
	</div> 
</body>
</html>
