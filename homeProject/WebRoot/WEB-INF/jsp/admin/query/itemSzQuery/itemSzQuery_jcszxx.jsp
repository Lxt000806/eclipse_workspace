<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>基础收支信息</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp"%>
		<script type="text/javascript">
			function doExcel(tableName){
				var firstTab = $("#jcszxxTabsUl li[class='active']").children().attr("id");
				var secondTab = "";
				
				var dataTableName = "";
				var dataFormName = "page_form_jcszxx";
				var postDataUrl = "";
				if(tableName != undefined){
					dataTableName = tableName;
					var higherTableName = "";
					if(tableName == "dataTable_llmx_clcbmx"){
						higherTableName="dataTable_clcbmx";
					}else if(tableName == "dataTable_llmx_shclmx"){
						higherTableName="dataTable_shclmx";
					}else if(tableName == "dataTable_llmx_shclmx_tc"){
						higherTableName="dataTable_shclmx_tc";
					}else if(tableName == "dataTable_llmx_jcclcbmx"){
						higherTableName="dataTable_jcclcbmx";
					}else if(tableName == "dataTable_llmx_tcnzccbmx"){
						higherTableName="dataTable_tcnzccbmx";
					}else {
						higherTableName="dataTable_tcnjccbmx";
					}
		        	var id = $("#"+higherTableName).jqGrid("getGridParam", "selrow");
				    if (id) {
				      var ret = $("#"+higherTableName).jqGrid("getRowData",id);
				      postDataUrl +="&no="+ret.no.trim();
				    }
				}else{
					if(firstTab == "qd"){
						secondTab = $("#qdUl li[class='active']").children().attr("id");
						if(secondTab == "a_qd_jcszxx"){
							dataTableName = "dataTable_jcszxx";
						}else if(secondTab == "a_qd_yshfbdemx"){
							dataTableName = "dataTable_yshfbdemx";
						}else if(secondTab == "a_qd_zjhfbdemx"){
							dataTableName = "dataTable_zjhfbdemx";
						}else if(secondTab == "a_qd_zfbdemx"){
							dataTableName = "dataTable_zfbdemx";
						}else if(secondTab == "a_qd_clcbmx"){
							dataTableName = "dataTable_clcbmx";
						}else if(secondTab == "a_qd_rgcbmx"){
							dataTableName = "dataTable_rgcbmx_qd";
						}else if(secondTab == "a_qd_rgfymx"){
							dataTableName = "dataTable_rgfymx_qd";
						}else if(secondTab == "a_qd_khfkmx"){
							dataTableName = "dataTable_khfkmx_qd";
						}else if(secondTab == "a_qd_yxxmjljl"){
							dataTableName = "dataTable_yxxmjljl";
						}else if(secondTab == "a_qd_ykmx"){
							dataTableName = "dataTable_ykmx_qd";
						}else if(secondTab == "a_qd_waterContractQuota"){
							dataTableName = "dataTable_waterContractQuota_qd";
						}else if(secondTab == "a_qd_shclmx"){
							dataTableName = "dataTable_shclmx";
						}else if(secondTab == "a_qd_shrgmx"){
							dataTableName = "dataTable_shrgmx_qd";
						}else if(secondTab == "a_qd_sgbt"){
							dataTableName = "dataTable_qd_sgbt";
						}
					}else {
						secondTab = $("#tcUl li[class='active']").children().attr("id");
						if(secondTab == "a_tc_jccbzcxx"){
							dataTableName = "dataTable_jccbzcxx";
						}else if(secondTab == "a_tc_jcbg"){
							dataTableName = "dataTable_jcbg";
						}else if(secondTab == "a_tc_jcclcbmx"){
							dataTableName = "dataTable_jcclcbmx";
						}else if(secondTab == "a_tc_tcnzccbmx"){
							dataTableName = "dataTable_tcnzccbmx";
						}else if(secondTab == "a_tc_tcnjccbmx"){
							dataTableName = "dataTable_tcnjccbmx";
						}else if(secondTab == "a_tc_rgcbmx"){
							dataTableName = "dataTable_rgcbmx_tc";
						}else if(secondTab == "a_tc_rgfymx"){
							dataTableName = "dataTable_rgfymx_tc";
						}else if(secondTab == "a_tc_khfkmx"){
							dataTableName = "dataTable_khfkmx_tc";
						}else if(secondTab == "a_tc_ykmx"){
							dataTableName = "dataTable_ykmx_tc";
						}else if (secondTab == "a_tc_jcxq"){
							dataTableName = "dataTable_jcxq";
							dataFormName = "page_form_tc_jcxq";
						}else if(secondTab == "a_tc_waterContractQuota"){
							dataTableName = "dataTable_waterContractQuota_tc";
						}else if(secondTab == "a_tc_tcnzcjc"){
							dataTableName = "dataTable_tc_tcnzcjc";
						}else if(secondTab == "a_tc_sgbt"){
							dataTableName = "dataTable_tc_sgbt";
						}else if(secondTab == "a_tc_shclmx"){
							dataTableName = "dataTable_shclmx_tc";
						}else if(secondTab == "a_tc_shrgmx"){
							dataTableName = "dataTable_shrgmx_tc";
						}
					}
				}
			
				var url = "${ctx}/admin/itemSzQuery/doExcel_jcszxx?custCode="+$("#code").val()+"&dataTableName="+dataTableName+postDataUrl;
				doExcelAll(url,dataTableName,dataFormName); 
			}
			$(function(){
				if("${customer.prjCtrlType}" == "1"){
					$("#qd").parent().css("display","block");
					$("#qd").tab("show");
					$("#a_qd_jcszxx").tab("show");
				}else if("${customer.prjCtrlType}" == "2"){
					$("#tc").parent().css("display","block");
					$("#tc").tab("show");
					$("#a_tc_jccbzcxx").tab("show");
				}
			});
			window.onload = function (){
				
				if("${customer.costRight}" == "1"){	
					$("#dataTable_jcszxx").jqGrid( "showCol", "materialcost");
					$("#dataTable_jcszxx").jqGrid( "showCol", "allamount");
					$("#dataTable_jcszxx").jqGrid( "showCol", "remainamount");
					$("#dataTable_clcbmx").jqGrid( "showCol", "materialcost");
					$("#dataTable_jcclcbmx").jqGrid( "showCol", "amount");
					$("#dataTable_jccbzcxx").jqGrid( "showCol", "materialcost");
					$("#dataTable_jccbzcxx").jqGrid( "showCol", "allamount");
					$("#dataTable_tcnzccbmx").jqGrid( "showCol", "amount");
				}else{
					$("#dataTable_jcszxx").jqGrid( "hideCol", "materialcost");
					$("#dataTable_jcszxx").jqGrid( "hideCol", "allamount");
					$("#dataTable_jcszxx").jqGrid( "hideCol", "remainamount");
					$("#dataTable_clcbmx").jqGrid( "hideCol", "materialcost");
					$("#dataTable_jcclcbmx").jqGrid( "hideCol", "amount");
					$("#dataTable_jccbzcxx").jqGrid( "hideCol", "materialcost");
					$("#dataTable_jccbzcxx").jqGrid( "hideCol", "allamount");
					$("#dataTable_tcnzccbmx").jqGrid( "hideCol", "amount");	
				}
/* 				if("${isRefCustCode}">0){
					$("#a_qd_shclmx,#a_tc_shclmx").css("display","block");
				}else{
					$("#a_qd_shclmx,#a_tc_shclmx").css("display","none");
				}
				if("${isRefCustCode_wc}">0){
					$("#a_qd_shrgmx,#a_tc_shrgmx").css("display","block");
				}else{
					$("#a_qd_shrgmx,#a_tc_shrgmx").css("display","none");
				} */
			}
		</script>
	</head>
<body>
	<form action="" method="post" id="page_form_jcszxx">
	    <input type="hidden" name="jsonString" value=""/>
	    <input type="hidden" name="calType" value=""/>
	    <input type="hidden" name="workType1Name" value=""/>
	    <input type="hidden" name="workType2" value=""/>
	    <input type="hidden" name="costType" value=""/>
	</form>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button id="doExcelBut" type="button" class="btn btn-system "
						onclick="doExcel()">输出到Excel</button>
					<button id="closeBut" type="button" class="btn btn-system "
						onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<input type="hidden" id="code" name="code" value="${customer.code }" />
		<input type="hidden" name="jsonString" value="" />
		<c:if test="${isWorkcost=='1'}">
			<div class="panel panel-info">
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<div class="validate-group">
								<li><label>客户编号</label> <input type="text" id="code"
									name="code" value="${customer.code }" readonly="readonly"/></li>
								<li><label>客户名称</label> <input type="text" id="descr"
									name="descr" value="${customer.descr }" readonly="readonly"/></li>
								<li><label>楼盘</label> <input type="text" id="address"
									name="address" value="${customer.address }" readonly="readonly"/></li>
								<li><label>客户状态</label> <input type="text"
									id="custStatDescr" name="custStatDescr"
									value="${customer.custStatDescr }" readonly="readonly"/></li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</c:if>

		<div class="container-fluid">
			<c:if test="${isWorkcost=='1'}">
				<ul id="jcszxxTabsUl" class="nav nav-tabs">
					<li style="display:none"><a id="qd" href="#tabView_qd"
						data-toggle="tab">项目发包（套餐客户发包公式）</a>
					</li>
					<li style="display:none"><a id="tc" href="#tabView_tc"
						data-toggle="tab">公式发包（原家装客户发包公式）</a>
					</li>
				</ul>
			</c:if>
			<c:if test="${isWorkcost!='1'}" >
				<ul id="jcszxxTabsUl" class="nav nav-tabs" hidden>
					<li style="display:none"><a id="qd" href="#tabView_qd"
						data-toggle="tab">项目发包（套餐客户发包公式）</a>
					</li>
					<li style="display:none"><a id="tc" href="#tabView_tc"
						data-toggle="tab">公式发包（原家装客户发包公式）</a>
					</li>
				</ul>
			</c:if>
			<div class="tab-content">
				<div id="tabView_qd" class="tab-pane fade"
					style="border:0px;height:630px">
					<jsp:include page="tabView_qd.jsp">
						<jsp:param value="" name="" />
					</jsp:include>
				</div>
				<div id="tabView_tc" class="tab-pane fade"
					style="border:1px;height:630px">
					<jsp:include page="tabView_tc.jsp">
						<jsp:param value="" name="" />
					</jsp:include>
				</div>
			</div>
		</div>
	</div>
</body>
</html>


