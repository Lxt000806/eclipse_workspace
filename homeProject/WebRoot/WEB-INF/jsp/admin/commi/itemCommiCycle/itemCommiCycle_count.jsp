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
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		var clickCount=0;
		var gridOption;
		var excelTable="dataTable_yjtc";
		var excelTitle="主材提成";
		var excelResource="commiCustStakeholder/doExcel";
		$(function(){
			$("#empCode").openComponent_employee();
		 	if("${itemCommiCycle.m_umState}"=="V"){
				$(".viewFlag").attr("disabled",true);
			}
		});
		
		function setExcelTable(table,title,resource){
			 excelTable = table;
			 excelTitle = title;
			 excelResource = resource;
		}
		
		function doExcelByUrl(){
			var url = "${ctx}/admin/"+excelResource;
			doExcelAll(url,excelTable);
		}
		
		//导出excel
		function doExcel(){
			if(excelResource){
				doExcelByUrl();
			}else{
				doExcelNow(excelTitle,excelTable);
			}
		}
		
		//查询所有
		function doQuery(){
			goto_query("dataTable_zcdlxstc");
			goto_query("dataTable_jcdlxstc");
			goto_query("dataTable_rzdlxstc");
		}
		
		//生成提成数据
		function createData(){
			art.dialog({
				 content:"是否确认开始计算提成",
				 lock: true,
				 ok: function () {
				 	var waitDialog=art.dialog({
						content: "提成数据生成中，请稍候...", 
						lock: true,
						esc: false,
						unShowOkButton: true
					}); 
					$.ajax({
						url:"${ctx}/admin/itemCommiCycle/doCount",
						type: "post",
						data: {no:$("#commiNo").val()},
						dataType: "json",
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
					    },
					    success: function(obj){
					    	waitDialog.close();
					    	doQuery();
					    	if(obj.datas.ret == 1){
								art.dialog({
									content: obj.datas.errmsg,
									time: 1000,
								});
					    	}else{
					    		art.dialog({
									content: obj.datas.errmsg
								});
					    	}
					    }
					 });
				},
				cancel: function () {
						return true;
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
					<button id="createData" type="button" class="btn btn-system viewFlag"
						onclick="createData()">提成数据生成</button>
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
					<input type="hidden" id="commiNo" name="commiNo" value="${itemCommiCycle.no}"/>
				<ul class="ul-form">
				<div class="validate-group row">
					<li>
						<label>楼盘地址</label> 
						<input type="text" id="address" name="address" />
					</li>
					<li>
						<label>员工</label> 
						<input type="text" id="empCode" name="empCode"/>
					</li>
				</div>
				<div class="validate-group row">
					<li class="search-group-shrink">
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
			<li id="zcdlxstc" class="active" onclick="setExcelTable('dataTable_zcdlxstc','主材独立销售提成','mainBusiCommi/doIndExcel')"><a
				href="#tab_zcdlxstc" data-toggle="tab" onclick="">主材独立销售提成</a>
			</li>
			<li id="jcdlxstc" class="" onclick="setExcelTable('dataTable_jcdlxstc','集成独立销售提成','intBusiCommi/doIndExcel')"><a
				href="#tab_jcdlxstc" data-toggle="tab" onclick="">集成独立销售提成</a>
			</li>
			<li id="rzdlxstc" class="" onclick="setExcelTable('dataTable_rzdlxstc','软装独立销售提成','softBusiCommi/doIndExcel')"><a
				href="#tab_rzdlxstc" data-toggle="tab" onclick="">软装独立销售提成</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_zcdlxstc" class="tab-pane fade in active" >
				<jsp:include page="tab_zctc_dlxstc.jsp"></jsp:include>
			</div>
			<div id="tab_jcdlxstc" class="tab-pane fade " >
				<jsp:include page="tab_jctc_dlxstc.jsp"></jsp:include>
			</div>
			<div id="tab_rzdlxstc" class="tab-pane fade " >
				<jsp:include page="tab_rztc_dlxstc.jsp"></jsp:include>
			</div>
		</div>
	</div> 
</body>
</html>
