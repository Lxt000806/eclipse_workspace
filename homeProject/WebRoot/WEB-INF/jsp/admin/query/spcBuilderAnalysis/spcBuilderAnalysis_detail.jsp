<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <title>专盘分析明细</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
function doExcel(){
	var spcBuilderAnalysisType=" ";
    if ($(".nav-tabs li a[href='#tab1']").parent().hasClass("active")){
   		spcBuilderAnalysisType="1";
		var url = "${ctx}/admin/spcBuilderAnalysis/doSpcBuilderAnalysisDetailExcel?spcBuilderAnalysisType="+spcBuilderAnalysisType;
	    doExcelAll(url,"custSetdataTable");
	}else if  ($(".nav-tabs li a[href='#tab2']").parent().hasClass("active")){
		spcBuilderAnalysisType="2";
		var url = "${ctx}/admin/spcBuilderAnalysis/doSpcBuilderAnalysisDetailExcel?spcBuilderAnalysisType="+spcBuilderAnalysisType;
	    doExcelAll(url,"custUnsubdataTable");
	}else if  ($(".nav-tabs li a[href='#tab3']").parent().hasClass("active")){
		spcBuilderAnalysisType="3";
		var url = "${ctx}/admin/spcBuilderAnalysis/doSpcBuilderAnalysisDetailExcel?spcBuilderAnalysisType="+spcBuilderAnalysisType;
	    doExcelAll(url,"custSigndataTable");
	}else if  ($(".nav-tabs li a[href='#tab4']").parent().hasClass("active")){
		spcBuilderAnalysisType="4";
		var url = "${ctx}/admin/spcBuilderAnalysis/doSpcBuilderAnalysisDetailExcel?spcBuilderAnalysisType="+spcBuilderAnalysisType;
	    doExcelAll(url,"custYjDecorCmpBegindataTable");
   	}else if  ($(".nav-tabs li a[href='#tab5']").parent().hasClass("active")){
		spcBuilderAnalysisType="5";
		var url = "${ctx}/admin/spcBuilderAnalysis/doSpcBuilderAnalysisDetailExcel?spcBuilderAnalysisType="+spcBuilderAnalysisType;
	    doExcelAll(url,"custPrjMandataTable");   
	}else if  ($(".nav-tabs li a[href='#tab6']").parent().hasClass("active")){
		spcBuilderAnalysisType="6";
		var url = "${ctx}/admin/spcBuilderAnalysis/doSpcBuilderAnalysisDetailExcel?spcBuilderAnalysisType="+spcBuilderAnalysisType;
	    doExcelAll(url,"custComedataTable");   
	}	
}
$(function(){
	//初始化表格
	var params=$("#page_form").jsonForm();
	params.spcBuilderAnalysisType="1";
    var  height=$(document).height()-$("#content-list").offset().top-70;
	Global.JqGrid.initJqGrid("custSetdataTable",{
		url:"${ctx}/admin/spcBuilderAnalysis/goSpcBuilderAnalysisDetailJqGrid",
		postData:params, 
		height:height,  
        styleUI: 'Bootstrap',
        autowidth: false,
		width:970,  
		colModel : [
			{name : "address",index : 'no',width : 140,label:"楼盘",sortable : true,align : "left"},
			{name : "designmandescr",index : 'designmandescr',width : 120,label:"设计师",sortable : true,align : "left"},
			{name : "businessmandescr",index : "businessmandescr",width : 120,label:"业务员",sortable : true,align : "left"},
			{name : "setdate",index : "setdate",width : 150,label:"下定时间",sortable : true,align : "left",formatter: formatTime},
			{name : "crtdate",index : "crtdate",width : 150,label:"创建时间",sortable : true,align : "left",formatter: formatTime}
    	]
	});
	params.spcBuilderAnalysisType="2",
	Global.JqGrid.initJqGrid("custUnsubdataTable",{
		url:"${ctx}/admin/spcBuilderAnalysis/goSpcBuilderAnalysisDetailJqGrid",
		postData:params, 
		height:height, 
        styleUI: 'Bootstrap',
        autowidth: false,
		width:970, 
		colModel : [
		 	{name : "address",index : 'no',width : 140,label:"楼盘",sortable : true,align : "left"},
			{name : "designmandescr",index : 'designmandescr',width : 120,label:"设计师",sortable : true,align : "left"},
			{name : "businessmandescr",index : "businessmandescr",width : 120,label:"业务员",sortable : true,align : "left"},
			{name : "enddate",index : "enddate",width : 150,label:"退订时间",sortable : true,align : "left",formatter: formatTime},
			{name : "setdate",index : "setdate",width : 150,label:"下定时间",sortable : true,align : "left",formatter: formatTime},
			{name : "crtdate",index : "crtdate",width : 150,label:"创建时间",sortable : true,align : "left",formatter: formatTime}
        ]
	});
	params.spcBuilderAnalysisType="3",
	Global.JqGrid.initJqGrid("custSigndataTable",{
		url:"${ctx}/admin/spcBuilderAnalysis/goSpcBuilderAnalysisDetailJqGrid",
		postData:params, 
		height:height, 
        styleUI: 'Bootstrap',
        autowidth: false,
		width:970, 
		colModel : [
	        {name : "address",index : 'no',width : 140,label:"楼盘",sortable : true,align : "left"},
			{name : "designmandescr",index : 'designmandescr',width : 120,label:"设计师",sortable : true,align : "left"},
			{name : "businessmandescr",index : "businessmandescr",width : 120,label:"业务员",sortable : true,align : "left"},
			{name : "signdate",index : "signdate",width : 150,label:"签订时间",sortable : true,align : "left",formatter: formatTime},
			{name : "setdate",index : "setdate",width : 150,label:"下定时间",sortable : true,align : "left",formatter: formatTime},
			{name : "crtdate",index : "crtdate",width : 150,label:"创建时间",sortable : true,align : "left",formatter: formatTime}
        ]
	});	
	params.spcBuilderAnalysisType="4",			
	Global.JqGrid.initJqGrid("custYjDecorCmpBegindataTable",{
		url:"${ctx}/admin/spcBuilderAnalysis/goSpcBuilderAnalysisDetailJqGrid",
		postData:params, 
		height:height,   
        styleUI: 'Bootstrap',
        autowidth: false,
		width:970, 
		colModel : [
	        {name : "address",index : 'no',width : 140,label:"楼盘",sortable : true,align : "left"},
			{name : "designmandescr",index : 'designmandescr',width : 120,label:"设计师",sortable : true,align : "left"},
			{name : "businessmandescr",index : "businessmandescr",width : 120,label:"业务员",sortable : true,align : "left"},
			{name : "confirmbegin",index : "confirmbegin",width : 150,label:"实际开工时间",sortable : true,align : "left",formatter: formatTime},
			{name : "signdate",index : "signdate",width : 150,label:"签订时间",sortable : true,align : "left",formatter: formatTime},
			{name : "setdate",index : "setdate",width : 150,label:"下定时间",sortable : true,align : "left",formatter: formatTime},
			{name : "crtdate",index : "crtdate",width : 150,label:"创建时间",sortable : true,align : "left",formatter: formatTime}
	    ]
	});
	params.spcBuilderAnalysisType="5",
	Global.JqGrid.initJqGrid("custPrjMandataTable",{
		url:"${ctx}/admin/spcBuilderAnalysis/goSpcBuilderAnalysisDetailJqGrid",
		postData:params, 
		height:height,   
        styleUI: 'Bootstrap',
        autowidth: false,
		width:970, 
		colModel : [
			{name :"address",index : 'no',width : 140,label:"楼盘",sortable : true,align : "left"},
			{name : "designmandescr",index : 'designmandescr',width : 60,label:"设计师",sortable : true,align : "left"},
			{name : "businessmandescr",index : "businessmandescr",width : 60,label:"业务员",sortable : true,align : "left"},
			{name : "againdept2descr",index : "againdept2descr",width : 120,label:"工程部",sortable : true,align : "left"},
			{name : "statusdescr",index : "statusdescr",width : 120,label:"客户状态",sortable : true,align : "left"},
			{name : "signdate",index : "signdate",width : 150,label:"签订时间",sortable : true,align : "left",formatter: formatTime},
			{name : "setdate",index : "setdate",width : 150,label:"下定时间",sortable : true,align : "left",formatter: formatTime},
			{name : "crtdate",index : "crtdate",width : 150,label:"创建时间",sortable : true,align : "left",formatter: formatTime}
	   ]
	});
	params.spcBuilderAnalysisType="6",
	Global.JqGrid.initJqGrid("custComedataTable",{
		url:"${ctx}/admin/spcBuilderAnalysis/goSpcBuilderAnalysisDetailJqGrid",
		postData:params, 
		height:height,   
        styleUI: 'Bootstrap',
        autowidth: false,
		width:970, 
		colModel : [
			{name :"address",index : 'no',width : 140,label:"楼盘",sortable : true,align : "left"},
			{name : "designmandescr",index : 'designmandescr',width : 60,label:"设计师",sortable : true,align : "left"},
			{name : "businessmandescr",index : "businessmandescr",width : 60,label:"业务员",sortable : true,align : "left"},
			{name : "crtdate",index : "crtdate",width : 150,label:"创建时间",sortable : true,align : "left",formatter: formatTime},
			{name : "visitdate",index : "visitdate",width : 150,label:"到店时间",sortable : true,align : "left",formatter: formatTime},
			{name : "setdate",index : "setdate",width : 150,label:"下定时间",sortable : true,align : "left",formatter: formatTime},
			{name : "signdate",index : "signdate",width : 150,label:"签订时间",sortable : true,align : "left",formatter: formatTime}
	   ]
	});
});
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="query-form" hidden>
			<form action="" method="post" id="page_form" >
				<input type="hidden" name="jsonString" value="" />
				<input type="text" id="dateTo" name="dateTo" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${spcBuilder.dateTo}" pattern='yyyy-MM-dd'/>" />
				<input  type="hidden" id="code" name="code" value="${spcBuilder.code}" />
			</form>
		</div>
		<div class="clear_float"> </div>
		
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
               		<house:authorize authCode="DEPT2FUNDUSE_EXCEL">
     					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					</house:authorize>
						<button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div  class="container-fluid" >  
		     <ul class="nav nav-tabs" >
		     	<li id="tab6_li" class ="active"><a href="#tab6" data-toggle="tab">来客信息</a></li>
		        <li id="tab1_li" class=""><a href="#tab1" data-toggle="tab">下定信息</a></li>  
		        <li id="tab2_li"  class=""><a href="#tab2" data-toggle="tab">退订信息</a></li>
		        <li id="tab3_li" class=""><a href="#tab3" data-toggle="tab">有效合同信息</a></li>  
		        <li id="tab4_li" class=""><a href="#tab4" data-toggle="tab">有家开工信息</a></li> 
		        <li id="tab5_li" class=""><a href="#tab5" data-toggle="tab">项目经理翻单信息</a></li> 
		    </ul>  
		    <div class="tab-content">
				<div id="tab1"  class="tab-pane fade "> 
					<div id="content-list">
						<table id= "custSetdataTable"></table>
						<div id="custSetdataTablePager"></div>
					</div> 
				</div>  
				<div id="tab2"  class="tab-pane fade "> 
					<div id="content-list2">
					  <table id= "custUnsubdataTable"></table>
					   <div id="custUnsubdataTablePager"></div>
				   </div> 
				</div>
				<div id="tab3"  class="tab-pane fade "> 
					<div id="content-list3">
						<table id= "custSigndataTable"></table>
						<div id="custSigndataTablePager"></div>
					</div> 
				</div>
				<div id="tab4"  class="tab-pane fade "> 
					<div id="content-list4">
						<table id= "custYjDecorCmpBegindataTable"></table>
						<div id="custYjDecorCmpBegindataTablePager"></div>
					</div> 
				</div>
				<div id="tab5"  class="tab-pane fade "> 
					<div id="content-list5">
						<table id= "custPrjMandataTable"></table>
						<div id="custPrjMandataTablePager"></div>
					</div> 
				</div>
				<div id="tab6"  class="tab-pane fade in active"> 
					<div id="content-list6">
						<table id= "custComedataTable"></table>
						<div id="custComedataTablePager"></div>
					</div> 
				</div>
			</div>	
		</div>
	</div>
</body>
</html>

