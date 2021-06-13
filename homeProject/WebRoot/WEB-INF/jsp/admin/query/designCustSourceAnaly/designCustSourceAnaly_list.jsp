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
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<title>设计客户来源分析</title>
<script type="text/javascript">
 var loadJqGridNum=0;//表格加载次数
 var sTabName='来客数';//页签名
 $(function(){
 	$("#designMan").openComponent_employee({
 		condition: {
 			positionType: "4",//设计师
 		},
 	});
     //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:260,
		rowNum:100000,  
		pager :'1',
		loadonce: true,
		colModel : [
			{name: "number", index: "number", width: 80, label: "员工编号", sortable: true, align: "left", hidden: true},
			{name: "namechi", index: "namechi", width: 80, label: "员工姓名", sortable: true, align: "left",count:true},
			{name: "department1", index: "department1", width: 100, label: "一级部门", sortable: true, align: "left", hidden: true},
			{name: "depart1descr", index: "depart1descr", width: 100, label: "一级部门", sortable: true, align: "left",count:true},
			{name: "department2", index: "department2", width: 100, label: "二级部门", sortable: true, align: "left", hidden: true},
			{name: "depart2descr", index: "depart2descr", width: 100, label: "二级部门", sortable: true, align: "left"},
			{name: "crtcount", index: "crtcount", width: 70, label: "来客数", sortable: true, align: "right",sum: true},
			{name: "crtper", index: "crtper", width:70, label: "来客占比", sortable: true, align: "right",formatter:"number",formatoptions:{decimalPlaces: 1,suffix: "%"}},
			{name: "setcount", index: "setcount", width: 70, label: "下定数", sortable: true, align: "right", sum: true},
			{name: "setper", index: "setper", width: 70, label: "下定占比", sortable: true, align: "right",formatter:"number",formatoptions:{decimalPlaces: 1,suffix: "%"}},
			{name: "signcount", index: "signcount", width: 70, label: "签单数", sortable: true, align: "right",sum: true},
			{name: "signper", index: "signper", width: 70, label: "签单占比", sortable: true, align: "right",formatter:"number",formatoptions:{decimalPlaces: 1,suffix: "%"}},
			{name: "contractfee", index: "contractfee", width: 70, label: "签单金额", sortable: true, align: "right",sum: true},	
			{name: "contractfeeper", index: "contractfeeper", width: 100, label: "签单金额占比", sortable: true, align: "right",formatter:"number",formatoptions:{decimalPlaces: 1,suffix: "%"}},	
			{name: "cursetcount", index: "cursetcount", width: 100, label: "当月来客下定数", sortable: true, align: "right",sum: true},
			{name: "setcrtpercent", index: "setcrtpercent", width: 100, label: "当期下定率", sortable: true, align: "right",formatter:"number",formatoptions:{decimalPlaces: 0,suffix: "%"}},
			{name: "cursetsigncount", index: "cursetsigncount", width: 100, label: "当月下定签单数", sortable: true, align: "right",sum: true},	
			{name: "cursignper", index: "cursignper", width: 100, label: "当期签单率", sortable: true, align: "right",formatter:"number",formatoptions:{decimalPlaces: 0,suffix: "%"}},	
        ],    
	    gridComplete:function(){
	    	if ($("#statistcsMethod").val() == "1") {
	     	 	$("#dataTable").jqGrid("showCol", "depart2descr");
       	 		$("#dataTable").jqGrid("hideCol", "namechi");
       	 		$("#dataTable").jqGrid("hideCol", "depart1descr");
	     	}else if ("2" == $("#statistcsMethod").val()) {
	     		$("#dataTable").jqGrid("showCol", "depart1descr");
	     		$("#dataTable").jqGrid("hideCol", "namechi");
       	 		$("#dataTable").jqGrid("hideCol", "depart2descr");
	     	}else if ("3" == $("#statistcsMethod").val()) {
	     		$("#dataTable").jqGrid("showCol", "namechi");
				$("#dataTable").jqGrid("hideCol", "depart1descr");
       	 		$("#dataTable").jqGrid("hideCol", "depart2descr");
	     	}
            if(loadJqGridNum<1){
	     	 	loadChart(sTabName); 
	     	 	loadJqGridNum++;
	     	};	
	     },                   
	 });
	$("#view").on("click",function () {
		view();
	});
});
function view() {
	var ret = selectDataTableRow();
	if (ret) {
		var data = $("#page_form").jsonForm();
		data.department1Code = ret.department1;
		data.department2Code = ret.department2;
		data.number = ret.number;
		Global.Dialog.showDialog("view",{
			title:"设计客户来源分析--查看明细",
			url:"${ctx}/admin/designCustSourceAnaly/goView",
			postData:data,
			width:1050,
			height:650,
		});
	} else {
		art.dialog({
			content : "请选择一条记录"
		});
	}
}
function goto_query(){
	if($.trim($("#department1").val())==''||$.trim($("#department1_NAME").val())==''){
		art.dialog({content: "请选择一级部门",width: 200});
		return false;
	} 
	if($.trim($("#dateFrom").val())==''){
		art.dialog({content: "结算开始日期不能为空",width: 200});
		return false;
	} 
	if($.trim($("#dateTo").val())==''){
		art.dialog({content: "结算结束日期不能为空",width: 200});
		return false;
	}
    var dateStart = Date.parse($.trim($("#dateFrom").val()));
    var dateEnd = Date.parse($.trim($("#dateTo").val()));
    if(dateStart>dateEnd){
    	art.dialog({content: "统计开始日期不能大于结束日期",width: 200});
		return false;
    } 
    loadJqGridNum=0;
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/designCustSourceAnaly/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}  
function loadChart(tabName){
	sTabName=tabName;	
	var arrDept=[]; //部门数据
	var arrData=[];//展示数据
	var rowData = $("#dataTable").jqGrid('getRowData');
	var statistcsMethod=$("#statistcsMethod").val();
	var rowData = $("#dataTable").jqGrid('getRowData');
	var avgNum=0;
	if(rowData){
		var datas = JSON.parse(JSON.stringify(rowData));
		datas.sort(function(a, b) {
	 	 	if (tabName == '来客数') {
               	if (parseFloat(a.crtcount) > parseFloat(b.crtcount))
              		return -1; //降序
          		else
                    return 1;
            } 
            if (tabName == '下定数') {
            	if (parseFloat(a.setcount) > parseFloat(b.setcount))
              		return -1; 
          		else
                    return 1;
            }
            if (tabName == '签单数') {
                if (parseFloat(a.signcount) > parseFloat(b.signcount))
              		return -1; 
          		else
                    return 1;
            }
            if (tabName =='签单金额'){
                if (parseFloat(a.contractfee) > parseFloat(b.contractfee))
              		return -1; 
          		else
                    return 1;  
            } 
		});
        datas.forEach(function(item,index) {
		     if (tabName=="来客数") {
		        if(item.crtcount>0){
				    if (statistcsMethod=="1") {
				    	arrDept.push(item.depart2descr);
				    	arrData.push({name:item.depart2descr,value:item.crtcount});
				    } else if ("2" == statistcsMethod) {
				     	arrDept.push(item.depart1descr);
				     	arrData.push({name:item.depart1descr,value:item.crtcount});
				    } else if ("3" == statistcsMethod) {
				    	arrDept.push(item.namechi);
				     	arrData.push({name:item.namechi,value:item.crtcount});
				    }
				}
		     }else if(tabName=="下定数"){
				if(item.setcount>0){
					if (statistcsMethod=="1") {
				    	arrDept.push(item.depart2descr);
						arrData.push({name:item.depart2descr,value:item.setcount});
				    }else if ("2" == statistcsMethod) {
				     	arrDept.push(item.depart1descr);
						arrData.push({name:item.depart1descr,value:item.setcount});
				    } else if ("3" == statistcsMethod) {
				    	arrDept.push(item.namechi);
				     	arrData.push({name:item.namechi,value:item.setcount});
				    }
				} 
			}else if(tabName=="签单数"){
				if(item.signcount>0){
				    if (statistcsMethod=="1"){
				    	arrDept.push(item.depart2descr);
						arrData.push({name:item.depart2descr,value:item.signcount});
				    }else if ("2" == statistcsMethod) {
				     	arrDept.push(item.depart1descr);
						arrData.push({name:item.depart1descr,value:item.signcount});
				    } else if ("3" == statistcsMethod) {
				    	arrDept.push(item.namechi);
				     	arrData.push({name:item.namechi,value:item.signcount});
				    }
				}
			}else{
				if(item.contractfee>0){
					if (statistcsMethod=="1"){
				    	arrDept.push(item.depart2descr);
						arrData.push({name:item.depart2descr,value:item.contractfee});
				    }else if ("2" == statistcsMethod) {
				     	arrDept.push(item.depart1descr);
						arrData.push({name:item.depart1descr,value:item.contractfee});
				    } else if ("3" == statistcsMethod) {
				    	arrDept.push(item.namechi);
				     	arrData.push({name:item.namechi,value:item.contractfee});
				    }	
				}	
			}    	   
	
    	});	
	} 
 	var designCustSourceChart = echarts.init(document.getElementById('designCustSourceChart'));
    // 指定图表的配置项和数据
    option = {
        backgroundColor: '#FFFFFF',
        color:['#006633','#CC6600','#CC9999','#336699','#339999','#CC99CC','#9999FF','#CCCCCC','#999999'],
        legend: {
        	type: 'scroll',
            x: 'center',
            y:'bottom',
            data:arrDept,
        },
        series: [
            {
                name:'设计客户来源分析',
                type:'pie',
                radius: '75%',
                itemStyle : {
                    normal : {
                        label : {
                            show : true,
                            formatter:function(data){
                             	return data.name+'：'+data.percent.toFixed(1)+"%";} 
                        	},
                        labelLine : {
                            show : true,//显示标签线
                        },
                        borderColor:'#fff',
                    },
                },
                data:arrData,
            }
        ]
    };
    designCustSourceChart.setOption(option); 
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form" style="margin-bottom:5px;">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" /> <input type="hidden" id="expired" name="expired" />
				<ul class="ul-form">
					<li><label>统计日期</label> <input type="text" id="dateFrom" name="dateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>至</label> <input type="text" id="dateTo" name="dateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>一级部门</label> <house:DictMulitSelect id="department1" dictCode=""
							sql="select code,desc1 from tDepartment1 a where a.Expired='F' and a.DepType in('0','2')
								 and ('${user.custRight}'='3' or ('${user.custRight}'='2' and exists(select 1 from tCZYDept in_a where in_a.Department1=a.Code and in_a.CZYBH='${user.czybh}'))) "
						    sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department1}" onCheck="checkDepartment1('','','','2','${user.custRight=='2'?user.czybh:''}')"></house:DictMulitSelect>
					</li>
					<li><label>设计部</label> <house:DictMulitSelect id="department2" dictCode=""
							sql="select code,desc1 from tDepartment2 a where a.Expired='F' and a.DepType='2' and a.Department1='${customer.department1}' 
								and ('${user.custRight}'='3' or ('${user.custRight}'='2' and (exists(select 1 from tCZYDept in_a where in_a.Department2=a.Code and in_a.CZYBH='${user.czybh}') or exists (
								select 1 from tCZYDept in_b where in_b.Department1=a.Department1 and in_b.CZYBH='${user.czybh}')))) " sqlLableKey="desc1" sqlValueKey="code"
							 selectedValue="${customer.department2 }" ></house:DictMulitSelect>
					</li>
					<li hidden="true"><label>三级部门</label> <house:DictMulitSelect id="department3" dictCode=""
							sql="select code,desc1 from tDepartment3 where 1=2" sqlLableKey="desc1" sqlValueKey="code"></house:DictMulitSelect>
					</li>
					<li>
						<label for="designMan">设计师</label>
						<input type="text" name="designMan" id="designMan">
					</li>
					<li><label>统计方式</label> <select id="statistcsMethod" name="statistcsMethod">
							<option value="1">按二级部门</option>
							<option value="2">按一级部门</option>
							<option value="3">按业务员</option>
					</select></li>
					<li><label></label>
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="panel panel-system " tyle="padding-top:0px;">
			<ul class="nav nav-tabs" style="background:#FFFFFF;" >
					<li class="active"><a data-toggle="tab" onclick="loadChart('来客数')">来客数（占比）</a>
					</li>
					<li class=""><a data-toggle="tab" onclick="loadChart('下定数')">下定数（占比）</a>
					</li>
					<li class=""><a data-toggle="tab" onclick="loadChart('签单数')">签单数（占比）</a>
					</li>
					<li class=""><a data-toggle="tab" onclick="loadChart('签单金额')">签单金额（占比）</a>
					</li>
					
			</ul> 
			<div id="designCustSourceChart" style="width: 100%;height:400px;padding-top:0px;border:1px solid #dfdfdf;border-top: 0px;background:#FFFFFF;" ></div>
		</div>
		<div class="pageContent" style="margin-top: 0px;" >
			<div class="btn-group-xs">
				<button type="button" class="btn btn-system " id="view">查看</button>
				<button type="button" class="btn btn-system" onclick="doExcelNow('设计客户来源分析','dataTable','page_form')">导出excel</button>
			</div>
			<table id="dataTable"></table>
		</div>
	</div>
</body>

</html>


