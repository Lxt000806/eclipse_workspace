<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<title>网络客服派单分析查看</title>
		<%@ include file="/commons/jsp/common.jsp" %>
		
<script type="text/javascript">
	$(function(){
		var ret = selectDataTableRow();
		var postData = $("#page_form").jsonForm();
		var statistcsMethod = ${customer.statistcsMethod};
		if(statistcsMethod=='3'){
			Global.JqGrid.initJqGrid("dataTable",{
				title:"派单明细",
				url:"${ctx}/admin/netCustAnaly/goManAndNetChanelView",
				postData:postData,
				height:500,
				colModel : [		
					{name : 'custcode',index : 'custcode',width : 95,label:'资源客户编号',sortable : true,align : "left"},	
					{name : 'address',index : 'address',width : 150,label:'资源客户楼盘',sortable : true,align : "left"},
					{name : 'custresstatdescr',index : 'custresstatdescr',width : 95,label:'资源客户状态',sortable : true,align : "left"},
					{name : 'businessmandescr',index : 'businessmandescr',width : 60,label:'跟踪人',sortable : true,align : "left"},
	  				{name : 'crtczydescr',index : 'crtczydescr',width : 60,label:'创建人',sortable : true,align : "left"},
	  				{name : 'dispatchdate',index : 'dispatchdate',width : 80,label:'派单时间',sortable : true,align : "left",formatter: formatDate},
	  				{name : 'custaddress',index : 'custaddress',width : 150,label:'意向客户楼盘',sortable : true,align : "left"},
	  				{name : 'custstatusdescr',index : 'custstatusdescr',width : 70,label:'客户状态',sortable : true,align : "left"},
	  				{name : 'visitdate',index : 'visitdate',width : 80,label:'到店时间',sortable : true,align : "left",formatter: formatDate},
	  				{name : 'setdate',index : 'setdate',width : 80,label:'下定时间',sortable : true,align : "left",formatter: formatDate},
	  				{name : 'signdate',index : 'signdate',width : 80,label:'签单时间',sortable : true,align : "left",formatter: formatDate},
	  				{name : 'contractfee',index : 'contractfee',width : 60,label:'合同额',sortable : true,align : "left"}
	            ]
			});
		}
	});

</script>
	</head>
	<body>
	
		<form action="" method="post" id="page_form" class="form-search" hidden>
			<input type="hidden" name="jsonString" value="" />
			<input type="hidden" id="businessMan" name="businessMan" value="${customer.businessMan}" />
			<input type="hidden" id="netChanel" name="netChanel" value="${customer.netChanel}" />
			<input type="hidden" id="statistcsMethod" name="statistcsMethod" value="${customer.statistcsMethod}" />
			<input type="hidden" id="crtCzy" name="crtCzy" value="${customer.crtCzy}" />
			<input type="hidden" id="custKind" name="custKind" value="${customer.custKind}" />
			<input type="hidden" id="custType" name="custType" value="${customer.custType}" />
			<input type="hidden" id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${customer.dateFrom }'  pattern='yyyy-MM-dd'/>"/>
			<input type="hidden" id="dateTo" name="dateTo" value="<fmt:formatDate value='${customer.dateTo }'  pattern='yyyy-MM-dd'/>"/>
		</form>
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
	     			 <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBtn" onclick="closeWin()">关闭</button>
					</div>
				</div> 
			</div>
			<table id= "dataTable"></table>  
			<div id= "dataTablePager"></div>  
		</div>
		<c:if test="${customer.statistcsMethod == 1}">
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active"><a href="#tab_Month_Man" data-toggle="tab">当月转化明细</a></li>
	 			    <li class=""><a href="#tab_History_Man" data-toggle="tab">历史转换明细</a></li>
			    </ul> 
			    <div class="tab-content">  
			        <div id="tab_Month_Man" class="tab-pane fade in active"> 
			       		<jsp:include page="tab_netCustAnalyMonth_man.jsp"></jsp:include>
			        </div> 
			        <div id="tab_History_Man" class="tab-pane fade "> 
			       		<jsp:include page="tab_netCustAnalyHistory_man.jsp"></jsp:include>
			        </div> 
			    </div>  
			</div>
		</c:if>
		<c:if test="${customer.statistcsMethod == 2}">
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active"><a href="#tab_Month_Man" data-toggle="tab">当月转化明细</a></li>
	 			    <li class=""><a href="#tab_History_Man" data-toggle="tab">历史转换明细</a></li>
			    </ul> 
			    <div class="tab-content">  
			        <div id="tab_Month_Man" class="tab-pane fade in active"> 
			       		<jsp:include page="tab_netCustAnalyMonth_netChanel.jsp"></jsp:include>
			        </div> 
			        <div id="tab_History_Man" class="tab-pane fade "> 
			       		<jsp:include page="tab_netCustAnalyHistory_netChanel.jsp"></jsp:include>
			        </div> 
			    </div>  
			</div>
		</c:if>
	</body>
</html>


