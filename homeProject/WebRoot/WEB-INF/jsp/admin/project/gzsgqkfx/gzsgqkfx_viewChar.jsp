<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>固定资产查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript" src="${ctx}/commons/echarts/dist/echarts.min.js"></script>
	
<script type="text/javascript"> 
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'WorkType12',	index:'WorkType12',	width:90,	label:'WorkType12',	sortable:true,align:"left" ,hidden:true},
			{name:'workerType12Descr',	index:'workerType12Descr',	width:80,	label:'工种类型',	sortable:true,align:"left" ,count:true},
			{name:'ArrNum',	index:'ArrNum',	width:90,	label:'安排工人套数',	sortable:true,align:"right" ,sum:true},
			{name:'arrworkload',index:'arrworkload',	width:90,	label:'安排工作量',	sortable:true,align:"right" ,sum:true},
			{name:'inTimeRate',	index:'inTimeRate',	width:60,	label:'及时率',	sortable:true,align:"right" ,},
			{name:'AvgDelayDay',	index:'AvgDelayDay',	width:90,	label:'平均拖延天数',	sortable:true,align:"right" ,},
			{name:'FirstSignInTimeNum',	index:'FirstSignInTimeNum',	width:90,	label:'及时签到套数',	sortable:true,align:"right",sum:true},
			{name:'FirstSignInTimeRate',	index:'FirstSignInTimeRate',	width:90,	label:'及时签到率',	sortable:true,align:"right" ,},
			{name:'SignRate',	index:'SignRate',	width:90,	label:'签到率',	sortable:true,align:"right" ,},
			{name:'UsedRate',	index:'UsedRate',	width:90,	label:'使用率',	sortable:true,align:"right" ,},
			{name:'ConfirmNum',	index:'ConfirmNum',	width:70,	label:' 验收套数',	sortable:true,align:"right" ,sum:true},
			{name:'ConInTimeRate',	index:'ConInTimeRate',	width:75,	label:'验收及时率',	sortable:true,align:"right" ,},
			{name:'ConPass',	index:'ConPass',	width:90,	label:'验收通过套数',	sortable:true,align:"right" ,sum:true},
			{name:'LevelOneAvg',	index:'LevelOneAvg',	width:90,	label:'一级工地占比',	sortable:true,align:"right" ,},
			{name:'CompleteNum',	index:'CompleteNum',	width:90,	label:'完工套数',	sortable:true,align:"right" ,sum:true},
			{name:'AvgConsDay',	index:'AvgConsDay',	width:90,	label:'平均施工天数',	sortable:true,align:"right" ,},
			{name:'InTimeConfirm',	index:'InTimeConfirm',	width:90,	label:'按时完工套数',	sortable:true,align:"right" ,sum:true},
			{name:'AvgInTimeDays',	index:'AvgInTimeDays',	width:90,	label:'按时平均天数',	sortable:true,align:"right" },
			{name:'NotSignNum',	index:'NotSignNum',	width:90,	label:'未签到套数',	sortable:true,align:"right" ,sum:true},
			{name:'InTimeConfirmRate',	index:'InTimeConfirmRate',	width:90,	label:'按时完工率',	sortable:true,align:"right" ,},
			{name:'BuilderRepDay',	index:'BuilderRepDay',	width:90,	label:'停工报备天数',	sortable:true,align:"right" ,sum:true},
			{name:'WorkerCost',	index:'WorkerCost',	width:78,	label:'工资发放额',	sortable:true,align:"right" ,sum:true},
			{name:'IsSignNum',	index:'IsSignNum',	width:75,	label:'班组数',	sortable:true,align:"right" ,sum:true},
			{name:'WithOutWorkerRate',	index:'WithOutWorkerRate',	width:75,	label:'无工地占比',	sortable:true,align:"right" ,},
			{name:'SignNum',	index:'SignNum',	width:75,	label:'本日签到人数',	sortable:true,align:"right" ,},
			{name:'OnConsNum',	index:'OnConsNum',	width:75,	label:'在建工地数',	sortable:true,align:"right" ,sum:true},
			{name:'OverPlanEndCons',	index:'OverPlanEndCons',	width:75,	label:'超期工地数',	sortable:true,align:"right" ,sum:true},
			{name:'MaxOnCons',	index:'MaxOnCons',	width:100,	label:'工人最大在建数',	sortable:true,align:"right" ,},
			{name:'WaitArrNum',	index:'WaitArrNum',	width:70,	label:'待安排数',	sortable:true,align:"right" ,sum:true},
			{name:'WaitConNum',	index:'WaitConNum',	width:80,	label:'待验收套数',	sortable:true,align:"right" ,sum:true},

		],
	});
	var tableData = ${tableData};
	if(tableData){
		$.each(tableData,function(k,v){
			Global.JqGrid.addRowData("dataTable",v);
		});
		loadChart("ArrNum");
	}
}); 

function loadChart(tabName){
	var rowNum = 15;
	
	if(!tabName || tabName == ""){
		tabName = $("#chartName").val();
	} else {
		$("#chartName").val(tabName);
	}
	
	rowNum = $('input:radio:checked').val();
	var workType12Descr =[];
	var data =[];	
	var name=[];
	var rowData = $("#dataTable").jqGrid('getRowData');
	var avgNum=0;
	if(rowData){
		var datas = JSON.parse(JSON.stringify(rowData));
		//设置排序
		datas.sort(function(a, b) {
			if(tabName == "ArrNum"){// 安排数
           		if (parseFloat(a.ArrNum) > parseFloat(b.ArrNum))
	          		return -1; 
	      		else
	                return 1
	        } 
	        if (tabName == 'inTimeRate') {// 及时率
	        	if (parseFloat(a.inTimeRate.split("%")[0]) > parseFloat(b.inTimeRate.split("%")[0]))
	          		return -1;
	      		else
	                return 1;
	        }
	        if (tabName == 'FirstSignInTimeRate') {// 及时签到率、
	            if (parseFloat(a.FirstSignInTimeRate.split("%")[0]) > parseFloat(b.FirstSignInTimeRate.split("%")[0]))
	          		return -1; 
	      		else
	                return 1;
	        }
	        
	        if (tabName == 'SignRate') {// 签到率
	            if (parseFloat(a.SignRate.split("%")[0]) > parseFloat(b.SignRate.split("%")[0]))
	          		return -1; 
	      		else
	                return 1;  
	        } 
	        
	        if (tabName == 'CompleteNum') {// 完工套数
	        	if (parseFloat(a.CompleteNum) > parseFloat(b.CompleteNum))
	          		return -1; 
	      		else
	                return 1;     
	        }
	        if (tabName == 'InTimeConfirmRate') {// 按时完工率
	            if (parseFloat(a.InTimeConfirmRate.split("%")[0]) > parseFloat(b.InTimeConfirmRate.split("%")[0]))
	          		return -1; 
	      		else 
	      			return 1;     
	        }	
	        if (tabName == 'OnConsNum') {// 按时完工率
	            if (parseFloat(a.OnConsNum) > parseFloat(b.OnConsNum))
	          		return -1; 
	      		else 
	      			return 1;     
	        }	
	        if (tabName == 'IsSignNum') {// 签约班组数
	            if (parseFloat(a.IsSignNum) > parseFloat(b.IsSignNum))
	          		return -1; 
	      		else 
	      			return 1;     
	        }	
	        if (tabName == 'OverPlanEndCons') {// 超期工地数
	            if (parseFloat(a.OverPlanEndCons) > parseFloat(b.OverPlanEndCons))
	          		return -1; 
	      		else 
	      			return 1;     
	        }	
		});
		
		// 设置数据
		datas.forEach(function(item,index) {
	     	if(index < rowNum){
	     		workType12Descr.push(item.workerType12Descr);
			     if (tabName == 'ArrNum') {
			         data.push(item.ArrNum);	
			     } 
			     if (tabName == 'inTimeRate') {
			      	data.push(item.inTimeRate.split("%")[0]); 
			     }
			     if (tabName == 'FirstSignInTimeRate') {
			         data.push(item.FirstSignInTimeRate.split("%")[0]);
			     }
			     if (tabName == 'SignRate') {
			         data.push(item.SignRate.split("%")[0]);
			     }
			     if (tabName == 'CompleteNum') {
			         data.push(item.CompleteNum);
			     }
			     if (tabName == 'InTimeConfirmRate') {
			         data.push(item.InTimeConfirmRate.split("%")[0]);
			     }
			     if (tabName == 'OnConsNum') {
			         data.push(item.OnConsNum);
			     }
			     if (tabName == 'IsSignNum') {
			         data.push(item.IsSignNum);
			     }
			     if (tabName == 'OverPlanEndCons') {
			         data.push(item.OverPlanEndCons);
			     }
	      	}   	
    	});	
	}
    // 指定图表的配置项和数据
	var option = {
    	backgroundColor: '#FFFFFF',
        tooltip: {
        	formatter:"{b0}: {c0}<br/>",
        },     
        grid: { 
  			x:55,
			y:20,
			x2:45,
			y2:50,
			borderWidth:1
		},
		color:'#198EDE', 
		xAxis: {
            data: workType12Descr,
			axisLabel: {
				interval:0,
            	rotate:-30//角度顺时针计算的
			}
		},
		yAxis: {
			type: 'value'
        },
		series: [
			{    
	           	name: tabName,
	            type: 'bar',    
	            barWidth : 20,//柱图宽度  
			    data : data,
			    markLine : {  
				    data : [  
					],
				}
			},               
		],
    };
    var gzsgqkfxChart = echarts.init(document.getElementById('gzsgqkfxChart'));
    gzsgqkfxChart.setOption(option); 
}

</script>
</head>
<body >
<div class="body-box-form" >
	<div class="body-box-list">
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
					<label style="padding-left:50px">显示：<input type="radio" name="rowNums" style="margin-top:-2px" onchange="loadChart()" value="15" checked/>前15条</label>
					<label style="padding-left:20px"><input type="radio" name="rowNums" style="margin-top:-2px" onchange="loadChart()" value="30"/>前30条</label>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid" style="margin-top:5px" >  
		<ul class="nav nav-tabs" >  
	        <li class="active"><a  data-toggle="tab" onclick="loadChart('ArrNum')">安排工人</a></li>
	        <li class=""><a data-toggle="tab" onclick="loadChart('inTimeRate')">安排及时率(%)</a></li>
	        <li class=""><a data-toggle="tab" onclick="loadChart('FirstSignInTimeRate')">及时签到率(%)</a></li>
	        <li class=""><a data-toggle="tab" onclick="loadChart('SignRate')">签到率(%)</a></li>
	        <li class=""><a data-toggle="tab" onclick="loadChart('CompleteNum')">完工套数</a></li>
	        <li class=""><a data-toggle="tab" onclick="loadChart('InTimeConfirmRate')">按时完工率(%)</a></li>
	        <li class=""><a data-toggle="tab" onclick="loadChart('OnConsNum')">在建工地数</a></li>
	        <li class=""><a data-toggle="tab" onclick="loadChart('IsSignNum')">班组数</a></li>
	        <li class=""><a data-toggle="tab" onclick="loadChart('OverPlanEndCons')">超期工地数</a></li>
	    </ul> 
	    <input tye="text" name="chartName" id="chartName" value="ArrNum" hidden="true"/>
		<div id="gzsgqkfxChart" style="width: 100%;height:400px;padding-top:0px;border:1px solid #dfdfdf;border-top: 0px;background:#FFFFFF;" ></div>
	</div>
	<div id="content-list" hidden="true">
		<table id= "dataTable"></table>
	</div> 
</div>
</body>
</html>
