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
	<script type="text/javascript">
		var clickCount=0;
		var gridOption;
		var excelTable="dataTable_yjtc";
		var excelTitle="业绩提成";
		var excelResource="commiCustStakeholder/doExcel";
		$(function(){
			$("#custCode").openComponent_customer();
			$("#empCode").openComponent_employee();
		 	if("${commiCycle.m_umState}"=="V"){
				$(".viewFlag").attr("disabled",true);
			}
		});
		
		function clickTcgz(){
			clickCount++ ;
			if(clickCount == 1){
				excelTable="dataTable_tcgz_tcgxr";
				excelTitle="提成干系人";
				excelResource = null;
			}
		}
		
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
		
		//触发打开的tab的click事件
		function triggerSelTab(id){
			$("#"+id +" .active").eq(0).children(":first").trigger("click");
		}
		
		//查询所有
		function doQuery(){
			goto_query("dataTable_yjtc");
			goto_query("dataTable_tcgz_tcgxr");
			goto_query("dataTable_tcgz_lpgxrtcgz");
			goto_query("dataTable_tcgz_ffbl");
			goto_query("dataTable_tcgz_tcjd");
			goto_query("dataTable_zctc");
			goto_query("dataTable_jctc");
			goto_query("dataTable_rztc");
			goto_query("dataTable_sjftc");
			goto_query("dataTable_jcgxhtc");
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
						url:"${ctx}/admin/commiCycle/doCount",
						type: "post",
						data: {no:$("#no").val()},
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
		
		//停发楼盘
		function notProvideCust(){
			Global.Dialog.showDialog("notProvideCust",{
			title:"停发楼盘",
				url:"${ctx}/admin/commiNotProvideCustStakeholder/goList?no=${no}",
				height:700,
				width:1180,
				postData:{
					m_umState:"${commiCycle.m_umState}"
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
					<button id="pointCust" type="button" class="btn btn-system"
						onclick="notProvideCust()">停发楼盘</button>
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
					<input type="hidden" id="no" name="no" value="${commiCycle.no}"/>
					<input type="hidden" id="mon" name="mon" value="${commiCycle.mon}"/>
					<input type="hidden" id="onlyHitAgainMan" name="onlyHitAgainMan" value="0" />
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
			<li id="tcgz" class="" onclick="triggerSelTab('id_tcgz')" ><a
				href="#tab_tcgz" data-toggle="tab" >提成规则</a>
			</li>
			<li id="yjtc" class="active" onclick="setExcelTable('dataTable_yjtc','业绩提成','commiCustStakeholder/doExcel')"><a
				href="#tab_yjtc" data-toggle="tab" onclick="">业绩提成</a>
			</li>
			<li id="zctc" class="" onclick="setExcelTable('dataTable_zctc','主材提成','mainBusiCommi/doExcel')"><a
				href="#tab_zctc" data-toggle="tab" onclick="">主材提成</a>
			</li>
			<li id="jctc" class="" onclick="setExcelTable('dataTable_jctc','集成提成','intBusiCommi/doExcel')"><a
				href="#tab_jctc" data-toggle="tab" onclick="">集成提成</a>
			</li>
			<li id="rztc" class="" onclick="setExcelTable('dataTable_rztc','软装提成','softBusiCommi/doExcel')"><a
				href="#tab_rztc" data-toggle="tab" onclick="">软装提成</a>
			</li>
			<li id="sjftc" class="" onclick="setExcelTable('dataTable_sjftc','设计费提成','commiCustStakeholder/doDesignFeeExcel')"><a
				href="#tab_sjftc" data-toggle="tab" onclick="">设计费提成</a>
			</li>
			<li id="jcgxhtc" class="" onclick="setExcelTable('dataTable_jcgxhtc','基础个性化提成','commiCustStakeholder/doBasePersonalExcel')"><a
				href="#tab_jcgxhtc" data-toggle="tab" onclick="">基础个性化提成</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_tcgz" class="tab-pane fade " >
				<jsp:include page="tab_tcgz.jsp"></jsp:include>
			</div>
			<div id="tab_yjtc" class="tab-pane fade in active" >
				<jsp:include page="tab_yjtc.jsp"></jsp:include>
			</div>
			<div id="tab_zctc" class="tab-pane fade " >
				<jsp:include page="tab_zctc.jsp"></jsp:include>
			</div>
			<div id="tab_jctc" class="tab-pane fade " >
				<jsp:include page="tab_jctc.jsp"></jsp:include>
			</div>
			<div id="tab_rztc" class="tab-pane fade " >
				<jsp:include page="tab_rztc.jsp"></jsp:include>
			</div>
			<div id="tab_sjftc" class="tab-pane fade " >
				<jsp:include page="tab_sjftc.jsp"></jsp:include>
			</div>
			<div id="tab_jcgxhtc" class="tab-pane fade " >
				<jsp:include page="tab_jcgxhtc.jsp"></jsp:include>
			</div>
		</div>
	</div> 
</body>
</html>
