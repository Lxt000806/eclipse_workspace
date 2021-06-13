<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjManCheckAnaly/goDetailJqGrid",
		postData:{
			custCheckDateFrom:$("#custCheckDateFrom").val(),custCheckDateTo:$("#custCheckDateTo").val(),
			custType:$("#custType").val(),projectMan:$("#projectMan").val(),
			department2:$("#department2").val(),containOilPaint:$("#containOilPaint").val(),
			installElev:"${customer.installElev}"
		},
        rowNum:10000000,
        height:400,
		styleUI: 'Bootstrap',
		colModel : [
			{name: "楼盘", index: "楼盘", width: 120, label: "楼盘", sortable: true, align: "left"},
			{name: "客户类型", index: "客户类型", width: 105, label: "客户类型", sortable: true, align: "left"},
			{name: "套内面积", index: "套内面积", width:105, label: "套内面积", sortable: true, align: "right",sum: true},
			{name: "结算时间", index: "结算时间", width: 120, label: "结算时间", sortable: true, align: "left", formatter: formatTime},
			{name: "总提成", index: "总提成", width: 105, label: "总提成", sortable: true, align: "right", sum: true},
			{name: "单方提成", index: "单方提成", width: 105, label: "单方提成", sortable: true, align: "right",sortorder:'asc'},
			{name: "水电单方成本", index: "水电单方成本", width: 115, label: "水电单方成本", sortable: true, align: "right"},
			{name: "油漆单方成本", index: "油漆单方成本", width: 115, label: "油漆单方成本", sortable: true, align: "right"},	
			{name: "土建单方成本", index: "土建单方成本", width: 145, label: "土建单方成本(扣砌墙)", sortable: true, align: "right"},	
			{name: "套内单方成本", index: "套内单方成本", width: 115, label: "套内单方成本", sortable: true, align: "right",hidden:true},
			{name: "成本", index: "成本", width: 115, label: "成本", sortable: true, align: "right",hidden:true},
			{name: "水电成本", index: "水电成本", width: 115, label: "水电成本", sortable: true, align: "right",hidden:true},
			{name: "油漆成本", index: "油漆成本", width: 115, label: "油漆成本", sortable: true, align: "right",hidden:true},	
			{name: "土建成本", index: "土建成本", width: 145, label: "土建成本(扣砌墙)", sortable: true, align: "right",hidden:true},	
		],
		gridComplete:function(){
		    var sumTnmj=myRound($("#dataTable").getCol('套内面积',false,'sum'));
		    var sumZtc=myRound($("#dataTable").getCol('总提成',false,'sum'));
		    var sumCommitAvg=0,sumSdAvg=0,sumYqAvg=0,sumTjAvg=0,sumTnAvg=0;
            if(sumTnmj!=0){
               sumCommitAvg=myRound($("#dataTable").getCol('总提成',false,'sum')/sumTnmj); //单方提成
               sumSdAvg=myRound($("#dataTable").getCol('水电成本',false,'sum')/sumTnmj); //水电单方成本
               sumYqAvg=myRound($("#dataTable").getCol('油漆成本',false,'sum')/sumTnmj); //油漆单方成本
               sumTjAvg=myRound($("#dataTable").getCol('土建成本',false,'sum')/sumTnmj); //土建单方成本
               sumTnAvg=myRound($("#dataTable").getCol('成本',false,'sum')/sumTnmj); //套内单方成本
            }
            $("#dataTable").footerData('set', {'单方提成': sumCommitAvg});	
            $("#dataTable").footerData('set', {'套内单方成本': sumTnAvg});	
            $("#dataTable").footerData('set', {'水电单方成本': sumSdAvg});
            $("#dataTable").footerData('set', {'土建单方成本': sumTjAvg});
            $("#dataTable").footerData('set', {'油漆单方成本': sumYqAvg});
            $("#dataTable").footerData('set', {'套内面积': myRound(sumTnmj)});
            $("#dataTable").footerData('set', {'总提成': myRound(sumZtc)});
	     },                   
		loadonce:true
	});
});
</script>
<table id="dataTable"></table>
