<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>订单转化分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
//冻结列的时候会将页脚不显示，此函数是将页脚添加上去
function getTdValue(sum,count,crtcount,setcount,signcount,sumcontractfee,convrate){
	$(".ui-jqgrid-ftable").each(function(){  
	 $(this).find('td').eq(0).html(sum);
	$(this).find('td').eq(1).html(count);
	if (($("#StatistcsMethod").val() == '1')){
		$(this).find('td').eq(2).html(crtcount);
		$(this).find('td').eq(3).html(setcount);
		$(this).find('td').eq(4).html(signcount);
		$(this).find('td').eq(5).html(sumcontractfee);
		$(this).find('td').eq(6).html(convrate);
	}else if (($("#StatistcsMethod").val() == '2')){
		$(this).find('td').eq(3).html(crtcount);
		$(this).find('td').eq(4).html(setcount);
		$(this).find('td').eq(5).html(signcount);
		$(this).find('td').eq(6).html(sumcontractfee);
		$(this).find('td').eq(7).html(convrate);
	}else if (($("#StatistcsMethod").val() == '3')){
		$(this).find('td').eq(4).html(crtcount);
		$(this).find('td').eq(5).html(setcount);
		$(this).find('td').eq(6).html(signcount);
		$(this).find('td').eq(7).html(sumcontractfee);
		$(this).find('td').eq(8).html(convrate);
	}else if (($("#StatistcsMethod").val() == '4')){
		$(this).find('td').eq(5).html(crtcount);
		$(this).find('td').eq(6).html(setcount);
		$(this).find('td').eq(7).html(signcount);
		$(this).find('td').eq(8).html(sumcontractfee);
		$(this).find('td').eq(9).html(convrate);
	}else if (($("#StatistcsMethod").val() == '5')){
		$(this).find('td').eq(2).html(crtcount);
		$(this).find('td').eq(3).html(setcount);
	}else if (($("#StatistcsMethod").val() == '6')){
		$(this).find('td').eq(3).html(crtcount);
		$(this).find('td').eq(4).html(setcount); 
	}
	});
}

//检索一次之后会残留div，导致下一次做changecss()的时候会改变在上一个class中。
//移除掉上一次检索的css
function removecss(){
	$("div.frozen-div").remove();
}

//frozen并且分组的时候,有冻结列和未冻结列的高度不同。
function changecss(){  
	$(".frozen-div table thead tr:eq(1) th:eq(0) div").css({"height":"46.5px"});	
	//$(".frozen-sdiv ").remove();
	
}

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/ddzhfx/doExcelcheckDdzhfx";
	var tableId ;
	if ($("#StatistcsMethod").val() == '1') {		
		tableId='dataTable';
	} else if ($("#StatistcsMethod").val() == '2'){
		tableId='dataTableGroupByDpt1Month';
	} else if ($("#StatistcsMethod").val() == '3'){
		tableId='dataTableGroupByDpt2Month';
	}else if ($("#StatistcsMethod").val() == '4'){
		tableId='dataTableGroupByPersonMonth';
	} else if ($("#StatistcsMethod").val() == '5'){
		tableId='dataTableGroupByDpt1';
	}else if ($("#StatistcsMethod").val() == '6'){
		tableId='dataTableGroupByDpt2';
	}	
	doExcelAll(url, tableId);
}   
 
//将小数转化为百分数 例:0.83→83%
function DiyFmatter (cellvalue, options, rowObject){ 
	return Math.round(cellvalue*100)+"%";
}

function colMonth(sum,beginNum,col){
//传入的StatistcsMethod值为1,2,3,4
//但是存储过程传回来的值  1与2,3,4的列名称不同，所以要分开push到col中 
//sum值为下定开始的月份加上分析月数的值
	 var m=1;
	 if (sum>12){
		sum=sum-12;	
		if ($("#StatistcsMethod").val()=='1'){
			for(var i = beginNum ; i<13 ;i++){
				col.push(
					{name: "signcount"+i, index: "signcount"+i, width: 76, label: "合同数", sortable: true,  align: "right",sum: true},
					{name: "sumcontractfee"+i, index: "sumcontractfee"+i, width: 76, label: "合同额", sortable: true,  align: "right",sum: true},
					{name: "convrate"+i, index: "convrate"+i, width: 76, label: "转化率", sortable: true,  align: "right",formatter : DiyFmatter}
				);
			}		
			for(var i = 1 ; i<sum ;i++){
				col.push(
					{name: "signcount"+i, index: "signcount"+i, width: 76, label: "合同数", sortable: true,  align: "right",sum: true},
					{name: "sumcontractfee"+i, index: "sumcontractfee"+i, width: 76, label: "合同额", sortable: true,  align: "right",sum: true},
					{name: "convrate"+i, index: "convrate"+i, width: 76, label: "转化率", sortable: true,  align: "right",formatter : DiyFmatter}
				);
			}	
		}else{
			for(var i = beginNum ; i<13 ;i++){
				col.push(
					{name: "signcount"+m, index: "signcount"+m, width: 76, label: "合同数", sortable: true,  align: "right",sum: true},
					{name: "sumcontractfee"+m, index: "sumcontractfee"+m, width: 76, label: "合同额", sortable: true,  align: "right",sum: true},
					{name: "convrate"+m, index: "convrate"+m, width: 76, label: "转化率", sortable: true,  align: "right",formatter : DiyFmatter}
				);
				m++;
			}		
			for(var i = 1 ; i<sum ;i++){
				col.push(
					{name: "signcount"+m, index: "signcount"+m, width: 76, label: "合同数", sortable: true,  align: "right",sum: true},
					{name: "sumcontractfee"+m, index: "sumcontractfee"+m, width: 76, label: "合同额", sortable: true,  align: "right",sum: true},
					{name: "convrate"+m, index: "convrate"+m, width: 76, label: "转化率", sortable: true,  align: "right",formatter : DiyFmatter}
				);
				m++;
			}	
		}
	}else{
		if($("#StatistcsMethod").val()=='1'){
			for(var i = beginNum ; i<sum ;i++){
			col.push(
					{name: "signcount"+i, index: "signcount"+i, width: 76, label: "合同数", sortable: true,  align: "right",sum: true},
					{name: "sumcontractfee"+i, index: "sumcontractfee"+i, width: 76, label: "合同额", sortable: true,  align: "right",sum: true},
					{name: "convrate"+i, index: "convrate"+i, width: 76, label: "转化率", sortable: true,  align: "right",formatter : DiyFmatter}
				);
			}
		}else{
			for(var i = beginNum ; i<sum ;i++){
				col.push(		
					{name: "signcount"+m, index: "signcount"+m, width: 76, label: "合同数", sortable: true,  align: "right",sum: true},
					{name: "sumcontractfee"+m, index: "sumcontractfee"+m, width: 76, label: "合同额", sortable: true,  align: "right",sum: true},
					{name: "convrate"+m, index: "convrate"+m, width: 76, label: "转化率", sortable: true,  align: "right",formatter : DiyFmatter}
				);
				m++;
			}
		}
	}
	return col;
}	

//StatistcsMethod = 4、5的时候添加列
function colDpt(sum,beginNum,monthNum,col){
	for(var i=1;i<=monthNum;i++){
		col.push(		
				{name: "signcount"+i, index: "signcount"+i, width: 76, label: "合同", sortable: true,  align: "right",sum: true},
				{name: "signper"+i, index: "signper"+i, width: 76, label: "合同比例", sortable: true,  align: "right",formatter : DiyFmatter},
				{name: "designcount"+i, index: "designcount"+i, width: 76, label: "纯设计", sortable: true,  align: "right",sum: true},
				{name: "designper"+i, index: "designper"+i, width: 76, label: "纯设计比例", sortable: true,  align: "right",formatter : DiyFmatter},
				{name: "returncount"+i, index: "returncount"+i, width: 76, label: "退订", sortable: true,  align: "right",sum: true},
				{name: "returnper"+i, index: "returnper"+i, width: 76, label: "退订比例", sortable: true,  align: "right",formatter : DiyFmatter}
			);
		if(i>1){
		var j;//累计延迟到下一个月开始计算 例如从8月开始计算，累计则从9月开始
		j=i-1;
		col.push(
			{name: "totalsigncount"+j, index: "totalsigncount"+i, width: 76, label: "合同", sortable: true,  align: "right",sum: true},
			{name: "totalsignper"+j, index: "totalsignper"+i, width: 76, label: "合同比例", sortable: true,  align: "right",formatter : DiyFmatter},
			{name: "totaldesigncount"+j, index: "totaldesigncount"+i, width: 76, label: "纯设计", sortable: true,  align: "right",sum: true},
			{name: "totaldesignper"+j, index: "totaldesignper"+i, width: 76, label: "纯设计比例", sortable: true,  align: "right",formatter : DiyFmatter},
			{name: "totalreturncount"+j, index: "totalreturncount"+i, width: 76, label: "退订", sortable: true,  align: "right",sum: true},
			{name: "totalreturnper"+j, index: "totalreturnper"+i, width: 76, label: "退订比例", sortable: true,  align: "right",formatter : DiyFmatter}
			);
		}			
	}
	return col;
}	

//获取从beginNum至num的数组
function Array(beginNum,sum,arr){
	for (i=beginNum;i<sum;i++){
			if(i>12){
				i=i-12;
				for(var j=1;j<sum-12;j++){
					arr.push(j);
				}
				break;
			}
			arr.push(i);
		};
		return arr;
}

/**初始化表格*/
$(function () {
	var  oldcol =[];		//按月份
	var  Dpt1Month =[];		//一级部门月份
	var  Dpt2Month =[];		//二级部门月份
	var  PersonMonth =[];	//个人月份
	var  Dpt1 =[];			//一级部门
	var  Dpt2 =[];			//二级部门
	oldcol.push (
		{name: "mdescr", index: "mdescr", width: 70, label: "月份", sortable: true, align:"right",count:true,frozen: true},
		{name: "crtcount", index: "crtcount", width: 85, label: "来客数", sortable: true, align: "right",sum: true, frozen: true},
		{name: "setcount", index: "setcount", width: 85, label: "订单数", sortable: true, align: "right",sum: true, frozen: true},
		{name: "signcount", index: "signcount", width: 85, label: "合同数", sortable: true, align: "right",sum: true, frozen: true},
		{name: "sumcontractfee", index: "sumcontractfee", width: 85, label: "合同额", sortable: true, align: "right",sum: true, frozen: true},
		{name: "convrate", index: "convrate", width: 85, label: "转化率", sortable: true, align: "right", frozen: true,formatter : DiyFmatter},
		{name: "avgconvday", index: "avgconvday", width: 120, label: "平均转化时间", sortable: true, align: "right"},
		{name: "unsubcount", index: "unsubcount", width: 80, label: "退订", sortable: true, align: "right",sum: true},
		{name: "transcount", index: "transcount", width: 100, label: "暂时转移", sortable: true, align: "right",sum: true},
		{name: "remain", index: "remain", width: 76, label: "余单", sortable: true, align: "right", sum: true}
	);
	Dpt1Month.push (
		{name: "dept1descr", index: "dept1descr", width: 85, label: "部门", sortable: true,  align: "left",count:true,frozen: true,cellattr: function(rowId, value, rowObject, colModel, arrData) {return 'id=\'dept1descr' + rowId + "\'";}},
		{name: "mdescr", index: "mdescr", width: 70, label: "月份", sortable: true,  align: "left", frozen: true},
		{name: "crtcount", index: "crtcount", width: 85, label: "来客数", sortable: true, align: "right", sum: true, frozen: true},
		{name: "setcount", index: "setcount", width: 85, label: "订单数", sortable: true, align: "right", sum: true, frozen: true},
		{name: "signcount", index: "signcount", width: 85, label: "合同数", sortable: true, align: "right", sum: true, frozen: true},
		{name: "sumcontractfee", index: "sumcontractfee", width: 85, label: "合同额", sortable: true, align: "right", sum: true, frozen: true},
		{name: "convrate", index: "convrate", width: 85, label: "转化率", sortable: true, align: "right", frozen: true,formatter : DiyFmatter},
		{name: "avgconvday", index: "avgconvday", width: 120, label: "平均转化时间", sortable: true, align: "right"},
		{name: "unsubcount", index: "unsubcount", width: 80, label: "退订", sortable: true, align: "right", sum: true},
		{name: "transcount", index: "transcount", width: 100, label: "暂时转移", sortable: true, align: "right", sum: true},
		{name: "remain", index: "remain", width: 80, label: "余单", sortable: true, align: "right", sum: true}
	);
	Dpt2Month.push (
		{name: "dept1descr", index: "dept1descr", width: 110, label: "一级部门", sortable: true,  align: "left", frozen: true,count:true,cellattr: function(rowId, value, rowObject, colModel, arrData) {return 'id=\'dept1descr' + rowId + "\'";}},
		{name: "dept2descr", index: "dept2descr", width: 110, label: "二级部门", sortable: true,  align: "left", frozen: true,cellattr: function(rowId, value, rowObject, colModel, arrData) {return 'id=\'dept2descr' + rowId + "\'";}},
		{name: "mdescr", index: "mdescr", width: 80, label: "月份", sortable: true,  align: "left", frozen: true},
		{name: "crtcount", index: "crtcount", width: 80, label: "来客数", sortable: true, align: "right", sum: true, frozen: true},
		{name: "setcount", index: "setcount", width: 80, label: "订单数", sortable: true, align: "right", sum: true, frozen: true},
		{name: "signcount", index: "signcount", width: 80, label: "合同数", sortable: true, align: "right", sum: true, frozen: true},
		{name: "sumcontractfee", index: "sumcontractfee", width: 80, label: "合同额", sortable: true, align: "right", sum: true, frozen: true},
		{name: "convrate", index: "convrate", width: 80, label: "转化率", sortable: true, align: "right", frozen: true,formatter : DiyFmatter},
		{name: "avgconvday", index: "avgconvday", width: 120, label: "平均转化时间", sortable: true, align: "right"},
		{name: "unsubcount", index: "unsubcount", width: 80, label: "退订", sortable: true, align: "right", sum: true},
		{name: "transcount", index: "transcount", width: 100, label: "暂时转移", sortable: true, align: "right", sum: true},
		{name: "remain", index: "remain", width: 80, label: "余单", sortable: true, align: "right", sum: true}
	);
	PersonMonth.push (
		{name: "dept1descr", index: "dept1descr", width: 100, label: "一级部门", sortable: false,  align: "left", frozen: true,count:true,cellattr: function(rowId, value, rowObject, colModel, arrData) {return 'id=\'dept1descr' + rowId + "\'";}},
		{name: "dept2descr", index: "dept2descr", width: 100, label: "二级部门", sortable: false,  align: "left", frozen: true,cellattr: function(rowId, value, rowObject, colModel, arrData) {return 'id=\'dept2descr' + rowId + "\'";}},
		{name: "empdescr", index: "empdescr", width: 70, label: "姓名", sortable: false,  align: "left", frozen: true,cellattr: function(rowId, value, rowObject, colModel, arrData) {return 'id=\'empdescr' + rowId + "\'";}},
		{name: "mdescr", index: "mdescr", width: 80, label: "月份", sortable: true,  align: "left", frozen: true},
		{name: "crtcount", index: "crtcount", width: 85, label: "来客数", sortable: true, align: "right", sum: true, frozen: true},
		{name: "setcount", index: "setcount", width: 85, label: "订单数", sortable: true, align: "right", sum: true, frozen: true},
		{name: "signcount", index: "signcount", width: 85, label: "合同数", sortable: true, align: "right", sum: true, frozen: true},
		{name: "sumcontractfee", index: "sumcontractfee", width: 85, label: "合同额", sortable: true,  align: "right", sum: true, frozen: true},
		{name: "convrate", index: "convrate", width: 85, label: "转化率", sortable: true, align: "right", frozen: true,formatter : DiyFmatter},
		{name: "avgconvday", index: "avgconvday", width: 120, label: "平均转化时间", sortable: true, align: "right"},
		{name: "unsubcount", index: "unsubcount", width: 85, label: "退订", sortable: true, align: "right", sum: true},
		{name: "transcount", index: "transcount", width: 100, label: "暂时转移", sortable: true, align: "right", sum: true},
		{name: "remain", index: "remain", width: 85, label: "余单", sortable: true, align: "right", sum: true}
	);
	Dpt1.push (
		{name: "dept1descr", index: "dept1descr", width: 120, label: "部门", sortable: true,  align: "left", frozen: true,count:true},
		{name: "crtcount", index: "crtcount", width: 85, label: "来客数", sortable: true, align: "right", sum: true, frozen: true},
		{name: "setcount", index: "setcount", width: 85, label: "订单数", sortable: true, align: "right", sum: true, frozen: true}
	);
	Dpt2.push (
		{name: "dept1descr", index: "dept1descr", width: 120, label: "一级部门", sortable: true,  align: "left", frozen: true,count:true},
		{name: "dept2descr", index: "dept2descr", width: 120, label: "二级部门", sortable: true,  align: "left", frozen: true},
		{name: "crtcount", index: "crtcount", width: 85, label: "来客数", sortable: true, align: "right", sum: true, frozen: true},
		{name: "setcount", index: "setcount", width: 85, label: "订单数", sortable: true, align: "right", sum: true, frozen: true}
	);
	
	var gridOption = {
        		height:380,
    			colModel: oldcol,
    			gridComplete:function(){
    			}
    		};
    Global.JqGrid.initJqGrid("dataTable", gridOption);
    
    //查询 begin
    window.goto_query = function() {
    var monthNum=parseInt($("#monthNum").val());
	var begindate=$("#beginDate").val();
	var endDate=$("#endDate").val();
	if (begindate>endDate){
		art.dialog({content: "下定开始日期不能大于下定结束日期！",width: 200});
		return false;
	}

	begindate = begindate.split('-');
	beginNum = parseInt(begindate[1]);
	endDate = endDate.split('-');
	endNum = parseInt(endDate[1]);
    var sum= parseInt(monthNum)+parseInt(beginNum);
    if ($.trim($("#beginDate").val())==''){
    	art.dialog({content: "下定开始日期不能为空！",width: 200});
		return false;
    }
    if ($.trim($("#endDate").val())==''){
    	art.dialog({content: "下定结束日期不能为空！",width: 200});
		return false;
    }
    if (monthNum>12){
    	art.dialog({content: "分析月数必须在1~12之间！",width: 200});
		return false;
    }
    change();//先改变表格的初始页面，再去修改表里面的列和和并列
    removecss();//移除掉上一次的css样式，否则只有对上有效
    var copyoldcol=[];
    var col=[];
    var tableId;
    var custRight=$("#custRight").val();//客户权限 3:所有权限
    if (($("#StatistcsMethod").val() == '1')&& (custRight == '3'))  {		
		tableId='dataTable';
		for (i=0;i<oldcol.length;i++){
	    	copyoldcol.push(oldcol[i]);
	    }
	} else if ($("#StatistcsMethod").val() == '2'){ //<!-- 按一级部门月份 -->
		tableId='dataTableGroupByDpt1Month';
		for (i=0;i<Dpt1Month.length;i++){
	    	copyoldcol.push(Dpt1Month[i]);
	    }
	} else if ($("#StatistcsMethod").val() == '3'){ // <!-- 按二级部门月份 -->
		tableId='dataTableGroupByDpt2Month';
		for (i=0;i<Dpt2Month.length;i++){
	    	copyoldcol.push(Dpt2Month[i]);
	    }
	}else if ($("#StatistcsMethod").val() == '4'){  // <!-- 按个人月份 -->
		tableId='dataTableGroupByPersonMonth';
		for (i=0;i<PersonMonth.length;i++){
	    	copyoldcol.push(PersonMonth[i]);
	    }
	} else if (($("#StatistcsMethod").val() == '5')&& (custRight == '3')){ // <!-- 按一级部门 -->
		tableId='dataTableGroupByDpt1';
		for (i=0;i<Dpt1.length;i++){
	    	copyoldcol.push(Dpt1[i]);
	    }
	}else if (($("#StatistcsMethod").val() == '6')&& (custRight == '3')) {  //<!-- 按二级部门 -->
		tableId='dataTableGroupByDpt2';
		for (i=0;i<Dpt2.length;i++){
	    	copyoldcol.push(Dpt2[i]);
	    }
	}else{
		art.dialog({content :"您没有查看所有客户权限，无法按此统计方式进行查询！",width:200});
	}
  	if (($("#StatistcsMethod").val() == '5')||($("#StatistcsMethod").val() == '6')){
  		colDpt(sum,beginNum,monthNum,col);
  		copyoldcol=copyoldcol.concat(col);
		$.jgrid.gridUnload(tableId);
		Global.JqGrid.initJqGrid(tableId, $.extend(gridOption,{colModel: copyoldcol,sortable: false,
			    			url: "${ctx}/admin/ddzhfx/DdzhfxSelect",postData:$("#page_form").jsonForm(),page:1,loadonce: true,
				    			loadComplete: function(){
					              	frozenHeightReset(tableId);
					              },
				    			gridComplete:function(){
					    			var sumsetcount=0;
					    			var setcount = Global.JqGrid.allToJson(tableId,"setcount");
					    			arry = setcount.fieldJson.split(",");
					    			for(var i = 0;i < arry.length; i++){	//订单数的总值
										sumsetcount=sumsetcount+parseFloat(arry[i]);
									}
									//循环加入页脚：月份的合同比例、纯设计比例、退订比例统计 begin
									for(var i=0;i<sum-beginNum;i++){
										var arry=[];
										var arrdesign=[];
										var arrreturn=[]; 
										var sumsigncount=0;
										var sumdesign=0;
										var sumreturn=0;
										var avgsign=0;
										var avgdesign=0;
										var avgreturn=0;
										var sign="signcount"+parseInt(i+1);//合同数
									    var signcount = Global.JqGrid.allToJson(tableId,sign);
									    arry = signcount.fieldJson.split(",");
									    var design="designcount"+parseInt(i+1);//纯设计数
									    var designcount = Global.JqGrid.allToJson(tableId,design);
									    arrdesign = designcount.fieldJson.split(",");
									    var sReturn="returncount"+parseInt(i+1);//退订数
									    var returncount = Global.JqGrid.allToJson(tableId,sReturn);
									    arrreturn = returncount.fieldJson.split(",");
										for(var j = 0;j < arry.length; j++){	//取总值
											sumsigncount=sumsigncount+parseFloat(arry[j]);//合同总值
											sumdesign=sumdesign+parseFloat(arrdesign[j]);//纯设计总值
											sumreturn=sumreturn+parseFloat(arrreturn[j]);//退订总值
										}
										avgsign=(sumsigncount/sumsetcount)*100;//合同平均值
										avgdesign=(sumdesign/sumsetcount)*100;//纯设计平均值	
										avgreturn=(sumreturn/sumsetcount)*100;//退订平均值	
										//判断是否为NaN，是的话则平均值为0
										if(isNaN(avgsign)){
									   		avgsign=0;	
									    }
									    if(isNaN(avgdesign)){
									   		avgdesign=0;	
									    }
									    if(isNaN(avgreturn)){
									   		avgreturn=0;	
									    }		
									    var footerData = {};								           	
						   	    		footerData["signper"+parseInt(i+1)] = avgsign.toFixed(0)+"%";
						   	    		$("#"+tableId).footerData("set",footerData,false);
						   	    		var footerData = {};								           	
						   	    		footerData["designper"+parseInt(i+1)] = avgdesign.toFixed(0)+"%";
						   	    		$("#"+tableId).footerData("set",footerData,false);
						   	    		var footerData = {};								           	
						   	    		footerData["returnper"+parseInt(i+1)] = avgreturn.toFixed(0)+"%";
						   	    		$("#"+tableId).footerData("set",footerData,false);
									}
									//循环加入页脚：月份的合同比例、纯设计比例、退订比例统计 end
									//循环加入页脚：累计的合同比例、纯设计比例、退订比例统计 begin
									for(var i=0;i<sum-beginNum-1;i++){
										var arry=[];
										var arrtotaldesign=[];
										var arrtotalreturn=[]; 
										var sumtotalsigncount=0;
										var sumtotaldesign=0;
										var sumtotalreturn=0;
										var avgtotalsign=0;
										var avgtotaldesign=0;
										var avgtotalreturn=0;
										var totalsign="totalsigncount"+parseInt(i+1);//合同数
									    var totalsigncount = Global.JqGrid.allToJson(tableId,totalsign);
									    arry = totalsigncount.fieldJson.split(",");
									    var totaldesign="totaldesigncount"+parseInt(i+1);//纯设计数
									    var totaldesigncount = Global.JqGrid.allToJson(tableId,totaldesign);
									    arrtotaldesign = totaldesigncount.fieldJson.split(",");
									    var totalreturn="totalreturncount"+parseInt(i+1);//退订数
									    var totalreturncount = Global.JqGrid.allToJson(tableId,totalreturn);
									    arrtotalreturn = totalreturncount.fieldJson.split(",");
										for(var j = 0;j < arry.length; j++){	//取总值
											sumtotalsigncount=sumtotalsigncount+parseFloat(arry[j]);//合同总值
											sumtotaldesign=sumtotaldesign+parseFloat(arrtotaldesign[j]);//纯设计总值
											sumtotalreturn=sumtotalreturn+parseFloat(arrtotalreturn[j]);//退订总值
										}
										avgtotalsign=(sumtotalsigncount/sumsetcount)*100;//合同平均值
										avgtotaldesign=(sumtotaldesign/sumsetcount)*100;//纯设计平均值	
										avgtotalreturn=(sumtotalreturn/sumsetcount)*100;//退订平均值	
										//判断是否为NaN，是的话则平均值为0
										if(isNaN(avgtotalsign)){
									   		avgtotalsign=0;	
									    }
									    if(isNaN(avgtotaldesign)){
									   		avgtotaldesign=0;	
									    }
									    if(isNaN(avgtotalreturn)){
									   		avgtotalreturn=0;	
									    }		
									    var footerData = {};								           	
						   	    		footerData["totalsignper"+parseInt(i+1)] = avgtotalsign.toFixed(0)+"%";
						   	    		$("#"+tableId).footerData("set",footerData,false);
						   	    		var footerData = {};								           	
						   	    		footerData["totaldesignper"+parseInt(i+1)] = avgtotaldesign.toFixed(0)+"%";
						   	    		$("#"+tableId).footerData("set",footerData,false);
						   	    		var footerData = {};								           	
						   	    		footerData["totalreturnper"+parseInt(i+1)] = avgtotalreturn.toFixed(0)+"%";
						   	    		$("#"+tableId).footerData("set",footerData,false);
									}
									//循环加入页脚：累计的合同比例、纯设计比例、退订比例统计 end
								   var sumcrtcount=0;
							   	   var sumsetcount=0;
							   	   var crtcount = Global.JqGrid.allToJson(tableId,"crtcount"); 
							   	   var setcount = Global.JqGrid.allToJson(tableId,"setcount"); 
								   var arr = crtcount.fieldJson.split(",");
								   var arry = setcount.fieldJson.split(",");
								   for(var i = 0;i < arr.length; i++){		
								     	sumcrtcount=sumcrtcount+parseFloat(arr[i]);
								     	sumsetcount=sumsetcount+parseFloat(arry[i]);
								   }	
								   if(isNaN(sumcrtcount)){
								   		sumcrtcount=0;
								   }
								   if(isNaN(sumsetcount)){
								   		sumsetcount=0;
								   }
								   getTdValue("合计",arr.length,sumcrtcount,sumsetcount);	
			},
		}));
		var arr=[];
		var count;
		Array(beginNum,sum,arr);
		$("#"+tableId).jqGrid('setGroupHeaders', {
		  	useColSpanStyle: true, 
			groupHeaders:[  															  	
						{startColumnName: 'signcount1', numberOfColumns: 6, titleText: arr[0]+'月份'},
						{startColumnName: 'signcount2', numberOfColumns: 6, titleText: arr[1]+'月份'},
						{startColumnName: 'signcount3', numberOfColumns: 6, titleText: arr[2]+'月份'},
						{startColumnName: 'signcount4', numberOfColumns: 6, titleText: arr[3]+'月份'},
						{startColumnName: 'signcount5', numberOfColumns: 6, titleText: arr[4]+'月份'},
						{startColumnName: 'signcount6', numberOfColumns: 6, titleText: arr[5]+'月份'},
						{startColumnName: 'signcount7', numberOfColumns: 6, titleText: arr[6]+'月份'},
						{startColumnName: 'signcount8', numberOfColumns: 6, titleText: arr[7]+'月份'},
						{startColumnName: 'signcount9', numberOfColumns: 6, titleText: arr[8]+'月份'},
						{startColumnName: 'signcount10', numberOfColumns: 6, titleText: arr[9]+'月份'},
						{startColumnName: 'signcount11', numberOfColumns: 6, titleText: arr[10]+'月份'},
						{startColumnName: 'signcount12', numberOfColumns: 6, titleText: arr[11]+'月份'},
						{startColumnName: 'totalsigncount1', numberOfColumns: 6, titleText: '累计'},
						{startColumnName: 'totalsigncount2', numberOfColumns: 6, titleText: '累计'},
						{startColumnName: 'totalsigncount3', numberOfColumns: 6, titleText: '累计'},
						{startColumnName: 'totalsigncount4', numberOfColumns: 6, titleText: '累计'},
						{startColumnName: 'totalsigncount5', numberOfColumns: 6, titleText: '累计'},
						{startColumnName: 'totalsigncount6', numberOfColumns: 6, titleText: '累计'},
						{startColumnName: 'totalsigncount7', numberOfColumns: 6, titleText: '累计'},
						{startColumnName: 'totalsigncount8', numberOfColumns: 6, titleText: '累计'},
						{startColumnName: 'totalsigncount9', numberOfColumns: 6, titleText: '累计'},
						{startColumnName: 'totalsigncount10', numberOfColumns: 6, titleText: '累计'},
						{startColumnName: 'totalsigncount11', numberOfColumns: 6, titleText: '累计'}
						],
				});
  	}else{
	  	colMonth(sum,beginNum,col);
		copyoldcol=copyoldcol.concat(col);
		$.jgrid.gridUnload(tableId);
		Global.JqGrid.initJqGrid(tableId, $.extend(gridOption,{colModel: copyoldcol,rowNum:100000,pager:1,
        	sortable: false,url: "${ctx}/admin/ddzhfx/DdzhfxSelect",postData:$("#page_form").jsonForm(),page:1,loadonce: true,
	       		loadComplete: function(){
	              	frozenHeightReset(tableId);
	              },
	            //页脚附加百分比  begin
	       		gridComplete:function(){
	       			//通过模拟合并单元格后,设置默认选中行的样式 begin
		       		if ($("#StatistcsMethod").val()=='2'||$("#StatistcsMethod").val()=='3'||$("#StatistcsMethod").val()=='4'){
		       			var dept1descrs = $("#"+tableId).jqGrid("getCol","dept1descr",true);
		       			if(dept1descrs.length > 0){
			        	for(var i=0;i<dept1descrs.length;i++){
							$("#"+tableId).jqGrid("setCell",dept1descrs[i].id,"dept1descr_hidden",dept1descrs[i].value);
			        	}
			        		Merger(tableId,"dept1descr");
							var id = $("#"+tableId).jqGrid("getGridParam", "selrow");
							if(id > 0){
		        				$("#"+tableId+" tbody tr #dept1descr"+id).css({"background":"#198EDE","color":"white"});
							}
			        	}
		       		}
		       		if ($("#StatistcsMethod").val()=='3'||$("#StatistcsMethod").val()=='4'){		       		
			       		var dept2descrs = $("#"+tableId).jqGrid("getCol","dept2descr",true);
			        	if(dept2descrs.length > 0){
			        	for(var i=0;i<dept2descrs.length;i++){
							$("#"+tableId).jqGrid("setCell",dept2descrs[i].id,"dept2descr_hidden",dept2descrs[i].value);
			        	}
			        		Merger(tableId,"dept2descr");
							var id = $("#"+tableId).jqGrid("getGridParam", "selrow");
							if(id > 0){
		        				$("#"+tableId+" tbody tr #dept2descr"+id).css({"background":"#198EDE","color":"white"});
							}
			        	}
		       		}
		       		if ($("#StatistcsMethod").val()=='3'||$("#StatistcsMethod").val()=='4'){
		       		var empdescrs = $("#"+tableId).jqGrid("getCol","empdescr",true);
		       		if(empdescrs.length > 0){
			        	for(var i=0;i<empdescrs.length;i++){
							$("#"+tableId).jqGrid("setCell",empdescrs[i].id,"empdescr_hidden",empdescrs[i].value);
			        	}
			        		Merger(tableId,"empdescr");
							var id = $("#"+tableId).jqGrid("getGridParam", "selrow");
							if(id > 0){
		        				$("#"+tableId+" tbody tr #empdescr"+id).css({"background":"#198EDE","color":"white"});
							}
			        		//通过模拟合并单元格后,设置默认选中行的样式 end
			        	}
		       		}
					var avgconvrate=0;
					var sumcountsign=0;
					var sumcountset=0;
					var countsign = Global.JqGrid.allToJson(tableId,"signcount"); // 合同数
					var countset = Global.JqGrid.allToJson(tableId,"setcount"); // 订单数
					arry = countset.fieldJson.split(",");
					for(var i = 0;i < arry.length; i++){	//订单数的总值
						sumcountset=sumcountset+parseFloat(arry[i]);
					}
					//动态添加转化率页脚 begin 	StatistcsMethod='1'
					if ($("#StatistcsMethod").val()=='1'){
						var m=1;
						for(var i=beginNum;i<sum;i++){
							var arry=[];
							var sumsigncount=0;
							var sign;
							if(i>12){
								sign="signcount"+parseInt(m);
							}else{
								sign="signcount"+parseInt(i);
							}
						    var signcount = Global.JqGrid.allToJson(tableId,sign);
						    arry = signcount.fieldJson.split(",");
							for(var j = 0;j < arry.length; j++){	//订单数的总值
								sumsigncount=sumsigncount+parseFloat(arry[j]);
							}
							avgcountsign=(sumsigncount/sumcountset)*100;	
							if(isNaN(avgcountsign)){
						   		avgcountsign=0;	
						    }		
						    var footerData = {};
						    
						    if(i>12){//作用：判断是否月份加上月数是否到了下一年。
						    	j=m;
						    	m++;
						    }else{
						   	 	j=i;
						    }				
			   	    		footerData["convrate"+parseInt(j)] = avgcountsign.toFixed(0)+"%";
			   	    		$("#"+tableId).footerData("set",footerData,false);
			   	    		j++;
						}
					}else{	
						//动态添加转化率页脚 begin 	StatistcsMethod != '1'  
						//区别：StatistcsMethod=1的时候，signcount列明是从begindate开始的
						//StatistcsMethod != '1' 的时候，signcount列明是从1开始的   
						for(var i=0;i<sum-beginNum;i++){
							var arry=[];
							var sumsigncount=0;
							var signcount="signcount"+parseInt(i+1);
						    var signcount = Global.JqGrid.allToJson(tableId,signcount);
						    arry = signcount.fieldJson.split(",");
							for(var j = 0;j < arry.length; j++){	//订单数的总值
								sumsigncount=sumsigncount+parseFloat(arry[j]);
							}
							avgcountsign=(sumsigncount/sumcountset)*100;	
							if(isNaN(avgcountsign)){
						   		avgcountsign=0;	
						    }		
						    var footerData = {};								           	
			   	    		footerData["convrate"+parseInt(i+1)] = avgcountsign.toFixed(0)+"%";
			   	    		$("#"+tableId).footerData("set",footerData,false);
						}
					}
					//动态添加转化率页脚 end
				   var sumcountsign=0;
				   arr = countsign.fieldJson.split(",");
				   for(var i = 0;i < arr.length; i++){		
				     	sumcountsign=sumcountsign+parseFloat(arr[i]);
				   }	
				   avgconvrate=(sumcountsign/sumcountset)*100;				   			
				   if(isNaN(avgconvrate)){
				   		avgconvrate=0;
				   }		
			   	   $("#"+tableId).footerData("set",{"convrate":(avgconvrate).toFixed(0)+"%"},false); 
			   	   var sumcontractfee=0;
			   	   var sumcrtcount=0;
			   	   var contractfee = Global.JqGrid.allToJson(tableId,"sumcontractfee"); 
			   	   var crtcount = Global.JqGrid.allToJson(tableId,"crtcount"); 
				   var arr = contractfee.fieldJson.split(",");
				   var arry = crtcount.fieldJson.split(",");
				   
				   for(var i = 0;i < arry.length; i++){		
				     	sumcontractfee=sumcontractfee+parseFloat(arr[i]);
				     	sumcrtcount=sumcrtcount+parseFloat(arry[i]);
				   }
				  	
				   if(isNaN(sumcontractfee)){
				   		sumcontractfee=0;
				   }
				   if(isNaN(sumcrtcount)){
				   		sumcrtcount=0;
				   }
				   getTdValue("合计",arr.length,sumcrtcount,sumcountset,sumcountsign,sumcontractfee,(avgconvrate).toFixed(0)+"%");				 
			  },	
			  //页脚附加百分比  end 
			//dataTableGroupByDpt1Month_frozen 选择之后改变背景样式
            beforeSelectRow:function(rowid, e){
		        $("#"+tableId+"_frozen"+" tbody tr td[id^=dept1descr]").css({"background":"white","color":"#333"});
		       	$("#"+tableId+"_frozen"+" tbody tr #dept1descr"+rowid).css({"background":"#198EDE","color":"white"}); 
		       	$("#"+tableId+"_frozen"+" tbody tr td[id^=dept2descr]").css({"background":"white","color":"#333"});
		       	$("#"+tableId+"_frozen"+" tbody tr #dept2descr"+rowid).css({"background":"#198EDE","color":"white"}); 
		       	$("#"+tableId+"_frozen"+" tbody tr td[id^=empdescr]").css({"background":"white","color":"#333"});
		       	$("#"+tableId+"_frozen"+" tbody tr #empdescr"+rowid).css({"background":"#198EDE","color":"white"});        	        	
		       }
        	}) 
		);
		//表头分组  begin	将StatistcsMethod=1和其他分开的原因是：1的时候，月份是不变的，从1-12.其他的时候，是从begin的月份开始计算的。所以要分开写
		if ($("#StatistcsMethod").val()=='1'){
			$("#"+tableId).jqGrid('setGroupHeaders', {
			  	useColSpanStyle: true, 
				groupHeaders:[  
					{startColumnName: 'signcount1', numberOfColumns: 3, titleText: '1月份'},
					{startColumnName: 'signcount2', numberOfColumns: 3, titleText: '2月份'},
					{startColumnName: 'signcount3', numberOfColumns: 3, titleText: '3月份'},
					{startColumnName: 'signcount4', numberOfColumns: 3, titleText: '4月份'},
					{startColumnName: 'signcount5', numberOfColumns: 3, titleText: '5月份'},
					{startColumnName: 'signcount6', numberOfColumns: 3, titleText: '6月份'},
					{startColumnName: 'signcount7', numberOfColumns: 3, titleText: '7月份'},
					{startColumnName: 'signcount8', numberOfColumns: 3, titleText: '8月份'},
					{startColumnName: 'signcount9', numberOfColumns: 3, titleText: '9月份'},
					{startColumnName: 'signcount10', numberOfColumns: 3, titleText: '10月份'},
					{startColumnName: 'signcount11', numberOfColumns: 3, titleText: '11月份'},
					{startColumnName: 'signcount12', numberOfColumns: 3, titleText: '12月份'}
				],
			});
		}else{
			var arr=[];
			Array(beginNum,sum,arr);
			$("#"+tableId).jqGrid('setGroupHeaders', {
			  	useColSpanStyle: true, 
				groupHeaders:[  
					{startColumnName: 'signcount1', numberOfColumns: 3, titleText: arr[0]+'月份'},
					{startColumnName: 'signcount2', numberOfColumns: 3, titleText: arr[1]+'月份'},
					{startColumnName: 'signcount3', numberOfColumns: 3, titleText: arr[2]+'月份'},
					{startColumnName: 'signcount4', numberOfColumns: 3, titleText: arr[3]+'月份'},
					{startColumnName: 'signcount5', numberOfColumns: 3, titleText: arr[4]+'月份'},
					{startColumnName: 'signcount6', numberOfColumns: 3, titleText: arr[5]+'月份'},
					{startColumnName: 'signcount7', numberOfColumns: 3, titleText: arr[6]+'月份'},
					{startColumnName: 'signcount8', numberOfColumns: 3, titleText: arr[7]+'月份'},
					{startColumnName: 'signcount9', numberOfColumns: 3, titleText: arr[8]+'月份'},
					{startColumnName: 'signcount10', numberOfColumns: 3, titleText: arr[9]+'月份'},
					{startColumnName: 'signcount11', numberOfColumns: 3, titleText: arr[10]+'月份'},
					{startColumnName: 'signcount12', numberOfColumns: 3, titleText: arr[11]+'月份'}
				],
			});
		}
		//表头分组  end
	  }
	  $("#"+tableId).jqGrid('setFrozenColumns');
	  changecss();
	};	//查询 	
});

function change(){ 
	var tableId;										//  content-list  					<!-- 按月份 -->
	if ($("#StatistcsMethod").val() == '1') {			//  content-list-groupByDpt1Month 	<!-- 按一级部门月份 -->
		tableId = 'dataTable';							//  content-list-groupByDpt2Month  	<!-- 按二级部门月份 -->
		$("#content-list").show();						//  content-list-groupByPersonMonth <!-- 按个人月份 -->
		$("#content-list-groupByDpt1Month").hide();  	//  content-list-groupByDpt1   		<!-- 按一级部门 -->
		$("#content-list-groupByDpt2Month").hide();     //  content-list-groupByDpt2  		<!-- 按二级部门 -->
		$("#content-list-groupByPersonMonth").hide();
		$("#content-list-groupByDpt1").hide();   
		$("#content-list-groupByDpt2").hide();
	} else if ($("#StatistcsMethod").val() == '2'){
		tableId = 'groupBycheckman';
		$("#content-list").hide();
		$("#content-list-groupByDpt1Month").show();
		$("#content-list-groupByDpt2Month").hide();
		$("#content-list-groupByPersonMonth").hide();
		$("#content-list-groupByDpt1").hide();   
		$("#content-list-groupByDpt2").hide();
	} else if ($("#StatistcsMethod").val() == '3'){
		tableId = 'groupByPrjman';
		$("#content-list").hide();
		$("#content-list-groupByDpt1Month").hide();
		$("#content-list-groupByDpt2Month").show();
		$("#content-list-groupByPersonMonth").hide();
		$("#content-list-groupByDpt1").hide();   
		$("#content-list-groupByDpt2").hide();
	} else if ($("#StatistcsMethod").val() == '4'){
		tableId = 'groupByDpt2';
		$("#content-list").hide();
		$("#content-list-groupByDpt1Month").hide();
		$("#content-list-groupByDpt2Month").hide();
		$("#content-list-groupByPersonMonth").show();
		$("#content-list-groupByDpt1").hide();   
		$("#content-list-groupByDpt2").hide();
	}	else if ($("#StatistcsMethod").val() == '5'){
		tableId = 'groupByPrjman';
		$("#content-list").hide();
		$("#content-list-groupByDpt1Month").hide();
		$("#content-list-groupByDpt2Month").hide();
		$("#content-list-groupByPersonMonth").hide();
		$("#content-list-groupByDpt1").show();   
		$("#content-list-groupByDpt2").hide();
	} else if ($("#StatistcsMethod").val() == '6'){
		tableId = 'groupByDpt2';
		$("#content-list").hide();
		$("#content-list-groupByDpt1Month").hide();
		$("#content-list-groupByDpt2Month").hide();
		$("#content-list-groupByPersonMonth").hide();
		$("#content-list-groupByDpt1").hide();   
		$("#content-list-groupByDpt2").show();
	}	
}

//月份改变时候，月数改变
function MonthNum(){
	var begindate=$("#beginDate").val();
	var enddate=$("#endDate").val();	
	if (begindate!='' && enddate!=''){
		begindate = begindate.split('-');
		begindate = parseInt(begindate[0]) * 12 + parseInt(begindate[1]);
		enddate = enddate.split('-');
		enddate = parseInt(enddate[0]) * 12 + parseInt(enddate[1]);
		var m = Math.abs(begindate - enddate)+1;
		document.getElementById("monthNum").value=m;
	}else{
		document.getElementById("monthNum").value=0;
	}
}

</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="expired" name="expired" value="F" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">						
						<li>
							<label>一级部门</label>
							<house:DictMulitSelect id="department1" dictCode="" sql="select code,desc1 from tDepartment1 where Expired='F'" 
							sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department1 }" onCheck="checkDepartment1()"></house:DictMulitSelect>
						</li>
						<li>
							<label>二级部门</label>
							<house:DictMulitSelect id="department2" dictCode="" sql="select code,desc1 from tDepartment2 where 1=2" 
							sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department2 }" onCheck="checkDepartment2()"></house:DictMulitSelect>
						</li>
						<li>
							<label>三级部门</label>
							<house:DictMulitSelect id="department3" dictCode="" sql="select code,desc1 from tDepartment3 where 1=2" sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department3 }"></house:DictMulitSelect>
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType"  selectedValue="${customer.custType}"></house:custTypeMulit>
						</li>
						<li>
							<label >团队</label>
								<house:dict id="team" dictCode="" sql="select rtrim(Code) Code,rtrim(Code)+' '+Desc1 Descr
								from tTeam where Expired='F' order by Code" 
								sqlValueKey="Code" sqlLableKey="Descr"  value="${customer.team}" ></house:dict>
							</label>
						</li>	
						<li>
							<label >下定开始日期</label>
							<input type="text" id="beginDate" name="beginDate" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${customer.beginDate}' pattern='yyyy-MM-dd'/>"  onblur="MonthNum()"/>
						</li>
						<li>
							<label >下定结束日期</label>
							<input type="text" id="endDate" name="endDate" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${customer.endDate}' pattern='yyyy-MM-dd'/>"  onblur="MonthNum()"/>
						</li>
						<li >
							<label>分析月份</label>
							<input type="text" id="monthNum" name="monthNum"  value="6"/>
						</li>		
						<li> 
						    <label>统计角色</label>
							<select id="role" name="role" >								
								<option value="1">按设计师统计</option>
								<option value="2">按业务员统计</option>
							</select>
						</li>
											
						<li> 
						    <label>统计方式</label>
							<select id="StatistcsMethod" name="StatistcsMethod" >								
								<option value="1">按月份</option>
								<option value="2">按一级部门月份</option>
								<option value="3">按二级部门月份</option>
								<option value="4">按个人月份</option>
								<option value="5">按一级部门</option>
								<option value="6">按二级部门</option>
							</select>
						</li>	
						<li hidden="true">
							<label>客户权限</label>
							<input type="text" id="custRight" name="custRight"  value='${customer.custRight}'/>
						</li>
						
						
						
					</ul>		
					<ul class="ul-form">
					<li id="loadMore" >
						<button type="button"  class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
					</li>					
				</ul>
			</form>
		</div>
		<div class="btn-panel" >
			<div class="btn-group-sm"  >
				<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
			</div>
			<div id="content-list">               <!-- 按月份 -->
				<table id= "dataTable"></table>  
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list-groupByDpt1Month"> <!-- 按一级部门月份 -->
				<table id= "dataTableGroupByDpt1Month"></table> 
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list-groupByDpt2Month">    <!-- 按二级部门月份 -->
				<table id= "dataTableGroupByDpt2Month"></table> 
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list-groupByPersonMonth"> <!-- 按个人月份 -->
				<table id= "dataTableGroupByPersonMonth"></table> 
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list-groupByDpt1">    <!-- 按一级部门 -->
				<table id= "dataTableGroupByDpt1"></table> 
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list-groupByDpt2">    <!-- 按二级部门 -->
				<table id= "dataTableGroupByDpt2"></table> 
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div> 
</body>
</html>


