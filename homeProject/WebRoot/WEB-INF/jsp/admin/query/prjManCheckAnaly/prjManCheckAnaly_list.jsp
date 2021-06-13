<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript" src="${ctx}/commons/echarts/dist/echarts.min.js"></script>
<title>项目经理结算分析</title>
<script type="text/javascript">
 var loadJqGridNum=0;//表格加载次数
 var sTabName='单方提成';//页签名
 $(function(){
       //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:260,
		rowNum:100000,  
		pager :'1',
		loadonce: true,
		colModel : [
			{name: "项目经理编号", index: "项目经理编号", width: 105, label: "项目经理编号", sortable: true, align: "left",count:true,hidden:true},
			{name: "项目经理", index: "项目经理", width: 105, label: "项目经理", sortable: true, align: "left",count:true},
			{name: "工程部", index: "工程部", width: 105, label: "工程部", sortable: true, align: "left"},
			{name: "结算套数", index: "结算套数", width: 105, label: "结算套数", sortable: true, align: "right",sum: true},
			{name: "总套内面积", index: "总套内面积", width:105, label: "总套内面积", sortable: true, align: "right",sum: true},
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
		    var sumTnmj=myRound($("#dataTable").getCol('总套内面积',false,'sum'));
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
            if(loadJqGridNum<1){
	     	 	loadChart(sTabName); 
	     	 	loadJqGridNum++;
	     	}  
	     },                   
	 });	 
});
function goto_query(){
	if($.trim($("#custCheckDateFrom").val())==''){
			art.dialog({content: "结算开始日期不能为空",width: 200});
			return false;
	} 
	if($.trim($("#custCheckDateTo").val())==''){
			art.dialog({content: "结算结束日期不能为空",width: 200});
			return false;
	}
     var dateStart = Date.parse($.trim($("#custCheckDateFrom").val()));
     var dateEnd = Date.parse($.trim($("#custCheckDateTo").val()));
     if(dateStart>dateEnd){
    	 art.dialog({content: "结算开始日期不能大于结束日期",width: 200});
			return false;
     } 
     loadJqGridNum=0;
	 $("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/prjManCheckAnaly/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}  
function loadChart(tabName){
	sTabName=tabName;
	var arrPrjMan =[];
	var data =[];	
	var name=[];
	var rowData = $("#dataTable").jqGrid('getRowData');
	var avgNum=0;
	if(rowData){
		avgNum=getAvgNum(tabName);
		var datas = JSON.parse(JSON.stringify(rowData));
		datas.sort(function(a, b) {
	 	 	if (tabName == '单方提成') {
               	if (parseFloat(a.单方提成) > parseFloat(b.单方提成))
              		return -1; //降序
          		else
                    return 1
            } 
            if (tabName == '总提成') {
            	if (parseFloat(a.总提成) > parseFloat(b.总提成))
              		return -1; //降序
          		else
                    return 1
              
            }
            if (tabName == '水电单方成本') {
                if (parseFloat(a.水电单方成本) > parseFloat(b.水电单方成本))
              		return -1; //降序
          		else
                    return 1
            }
            if (tabName == '油漆单方成本') {
                if (parseFloat(a.油漆单方成本) > parseFloat(b.油漆单方成本))
              		return -1; //降序
          		else
                    return 1  
            } 
            if (tabName == '土建单方成本') {
            	if (parseFloat(a.土建单方成本) > parseFloat(b.土建单方成本))
              		return -1; //降序
          		else
                    return 1     
            }
            if (tabName == '套内单方成本') {
                if (parseFloat(a.套内单方成本) > parseFloat(b.套内单方成本))
              		return -1; //降序
          		else 
          			return 1     
            }	
		});
        datas.forEach(function(item,index) {
	     	if(index<=29){
			  	 arrPrjMan.push(item.项目经理);
			     if (tabName == '单方提成') {
			         data.push(item.单方提成);	
			     } 
			     if (tabName == '总提成') {
			      	data.push(item.总提成); 
			     }
			     if (tabName == '水电单方成本') {
			         data.push(item.水电单方成本);
			     }
			     if (tabName == '油漆单方成本') {
			         data.push(item.油漆单方成本);
			     }
			     if (tabName == '土建单方成本') {
			         data.push(item.土建单方成本);
			     }
			     if (tabName == '套内单方成本') {
			         data.push(item.套内单方成本);
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
              data: arrPrjMan,
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
					  	   { name:'平均值', yAxis: avgNum },
				      ],

			   	  }
	          },               
          ],
      };
      var prjManCheckChart = echarts.init(document.getElementById('prjManCheckChart'));
      prjManCheckChart.setOption(option); 
}
function getAvgNum(tabName){
	if (tabName =='单方提成') {
        return $("#dataTable").footerData("get","单方提成").单方提成;
    }else if (tabName =='总提成') {
     	return myRound(parseFloat($("#dataTable").footerData("get","总提成").总提成)/parseFloat($('#dataTable').jqGrid('getGridParam','records')));
    }else if (tabName =='水电单方成本') {
    	return $("#dataTable").footerData("get","水电单方成本").水电单方成本;
    }else if (tabName =='油漆单方成本') {
    	return $("#dataTable").footerData("get","油漆单方成本").油漆单方成本;
    }else if (tabName =='土建单方成本') {
        return $("#dataTable").footerData("get","土建单方成本").土建单方成本;   
    }else if (tabName =='套内单方成本') {
        return $("#dataTable").footerData("get","套内单方成本").套内单方成本;
    }else{
    	return 0;
    }
}
function changeContainOilPaint(obj){
	if ($(obj).is(":checked")){
		$("#containOilPaint").val("1");
	}else{
		$("#containOilPaint").val("0");
	}
}
function view(){
	var ret = selectDataTableRow();
	if(ret){
		Global.Dialog.showDialog("view",{
			title:"项目经理结算分析-查看",
			url:"${ctx}/admin/prjManCheckAnaly/goView",
			postData:{
				custCheckDateFrom:$("#custCheckDateFrom").val(),custCheckDateTo:$("#custCheckDateTo").val(),
				custType:$("#custType").val(),projectMan:ret.项目经理编号,
				department2:$("#department2").val(),containOilPaint:$("#containOilPaint").val(),
				installElev:$("#installElev").val()
			},
			height:650,
			width:1300,
			returnFun:goto_query
		});
	}else{
	    art.dialog({
			content: "请选择一条记录"
		});
	}
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form" style="margin-bottom:5px;">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" /> <input type="hidden" id="expired" name="expired" />
				<ul class="ul-form">
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" selectedValue="${customer.custType}" ></house:custTypeMulit>
					</li>
					<li>
						<label>工程部</label>
						<house:DictMulitSelect id="department2" dictCode="" 
							sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' and DepType='3' order by DispSeq " 
							sqlLableKey="fd2" sqlValueKey="fd1">
						</house:DictMulitSelect>
					</li>
					<li><label>结算日期从</label> <input type="text" id="custCheckDateFrom" name="custCheckDateFrom"
						class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${customer.custCheckDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>至</label> <input type="text" id="custCheckDateTo" name="custCheckDateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${customer.custCheckDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label for="installElev">安装电梯</label><!-- 增加有无电梯 add by zb on 20191107 -->
						<house:xtdm id="installElev" dictCode="YESNO"  style="width:160px;"></house:xtdm>
					</li>
					<li><label></label>
						<input type="checkbox" id="containOilPaint" name="containOilPaint" checked="checked" value="1" onclick="changeContainOilPaint(this)"/>包含油漆未做&nbsp;
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
					
				</ul>
			</form>
		</div>
		<div class="panel panel-system " tyle="padding-top:0px;">
			<ul class="nav nav-tabs" style="background:#FFFFFF;" >
					<li class="active"><a data-toggle="tab" onclick="loadChart('单方提成')">单方提成</a>
					</li>
					<li class=""><a  data-toggle="tab" onclick="loadChart('总提成')">总提成</a>
					</li>
					<li class=""><a  data-toggle="tab" onclick="loadChart('水电单方成本')">水电单方成本</a>
					</li>
					<li class=""><a  data-toggle="tab" onclick="loadChart('油漆单方成本')">油漆单方成本</a>
					</li>
					<li class=""><a  data-toggle="tab" onclick="loadChart('土建单方成本')">土建单方成本</a>
					
			</ul> 
			<div id="prjManCheckChart" style="width: 100%;height:300px;padding-top:0px;border:1px solid #dfdfdf;border-top: 0px;background:#FFFFFF;" ></div>
		</div>
		<div class="pageContent" style="margin-top: 0px;">
			<div class="btn-group-xs">
				<button type="button" class="btn btn-system" onclick="view()">查看</button>
				<button type="button" class="btn btn-system" onclick="doExcelNow('项目经理结算分析表','dataTable','page_form')">导出excel</button>
			</div>
			<table id="dataTable"></table>
		</div>
	</div>
</body>

</html>


