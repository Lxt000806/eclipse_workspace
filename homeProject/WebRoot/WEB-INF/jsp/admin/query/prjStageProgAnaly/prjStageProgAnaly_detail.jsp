<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>工程部阶段分析明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
		.frozen-div .jqg-third-row-header {
			height: 53px;
		}
		.table > thead > tr > th {
		    vertical-align: middle;
		}
	</style>
<script type="text/javascript"> 
$(function(){

	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjStageProgAnaly/goJqGrid",
		postData: $.extend({}, $("#page_form").jsonForm(), {status: "2"}),
		height: 550,
		rowNum:10000,
		colModel : [
			{name:'客户编号',	index:'客户编号',	width:70,	label:'客户编号',	sortable:true,align:"left",hidden:true,frozen:true},
			{name:'楼盘',	index:'楼盘',	width:120,	label:'楼盘',	sortable:true,align:"left",frozen:true},
			{name:'项目经理',	index:'项目经理',	width:70,	label:'项目经理',	sortable:true,align:"left",frozen:true},
			{name:'工程部',	index:'工程部',	width:70,	label:'工程部',	sortable:true,align:"left",frozen:true},
			{name:'面积',	index:'面积',	width:50,	label:'面积',	sortable:true,align:"right",frozen:true},
			{name:'户型', 	index:'户型',		width:50,	label:'户型',sortable:true,align:"left",frozen:true},
			{name:'客户类型', 	index:'客户类型',		width:70,	label:'客户类型',sortable:true,align:"left",frozen:true},
			{name:'总工期', 	index:'总工期'  ,  excelLabel: '总标准工期',		width:70,	label:'总标准</br>工期',sortable:true,align:"right",frozen:true,sum:true},
			{name:'当前标准工期', 	index:'当前标准工期',  excelLabel: '当前标准工期'	,	width:70,	label:'当前标准</br>工期',sortable:true,align:"right",sum:true,frozen:true,hidden:"${customer.progStage}"=="99"?true:false},
			{name:'计划工期', 	index:'计划工期',		width:70,	label:'计划工期',sortable:true,align:"right",sum:true,frozen:true,hidden:"${customer.progStage}"=="99"?false:true},
			{name:'已施工天数', 	index:'已施工天数',		width:70,	label:"${customer.progStage}"=="99"?'总施工</br>天数':'已施工</br>天数',	excelLabel:"${customer.progStage}"=="99"?'总施工天数':'已施工天数',sortable:true,align:"right",frozen:true,sum:true},
			{name:'当前超期天数',		index:'当前超期天数', width:70,	label:"${customer.progStage}"=="99"?'总超期</br>天数':'当前超期</br>天数',	excelLabel:"${customer.progStage}"=="99"?'总超期天数':'当前超期天数',	sortable:true,align:"right",frozen:true,sum:true},
			{name:'一阶段开始时间',		index:'一阶段开始时间',	excelLabel: '一阶段开始时间',	width:70,	label:"${customer.progStage}"=="01"?"<font color='red'>开始时间</font>":"开始时间",	sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true},
			{name:'一阶段结束时间',		index:'一阶段结束时间',	excelLabel: '一阶段结束时间',	width:70,	label:"${customer.progStage}"=="01"?"<font color='red'>结束时间</font>":"结束时间",	sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
			{name:'一阶段实际工天',		index:'一阶段实际工天',	excelLabel: '一阶段实际工天',	width:70,	label:"${customer.progStage}"=="01"?"<font color='red'>实际工天</font>":"实际工天",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
			{name:'一阶段标准工天',		index:'一阶段标准工天',	excelLabel: '一阶段标准工天',	width:70,	label:"${customer.progStage}"=="01"?"<font color='red'>标准工天</font>":"标准工天",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
			{name:'一阶段超期天数',		index:'一阶段超期天数',	excelLabel: '一阶段超期天数',	width:70,	label:"${customer.progStage}"=="01"?"<font color='red'>超期天数</font>":"超期天数",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
			{name:'一阶段提报问题',		index:'一阶段提报问题',	excelLabel: '一阶段提报问题',	width:70,	label:"${customer.progStage}"=="01"?"<font color='red'>提报问题</font>":"提报问题",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
			{name:'一阶段罚款金额',		index:'一阶段罚款金额',	excelLabel: '一阶段罚款金额',	width:70,	label:"${customer.progStage}"=="01"?"<font color='red'>罚款金额</font>":"罚款金额",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
			{name:'二阶段开始时间',		index:'二阶段开始时间',	excelLabel: '二阶段开始时间',	width:70,	label:"${customer.progStage}"=="02"?"<font color='red'>开始时间</font>":"开始时间",	sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="01" || "${customer.progStage}"=="99"?false:true},
			{name:'二阶段结束时间',		index:'二阶段结束时间',	excelLabel: '二阶段结束时间',	width:70,	label:"${customer.progStage}"=="02"?"<font color='red'>结束时间</font>":"结束时间",	sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="01" || "${customer.progStage}"=="99"?false:true}, 
			{name:'二阶段实际工天',		index:'二阶段实际工天',	excelLabel: '二阶段实际工天',	width:70,	label:"${customer.progStage}"=="02"?"<font color='red'>实际工天</font>":"实际工天",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="01" || "${customer.progStage}"=="99"?false:true}, 
			{name:'二阶段标准工天',		index:'二阶段标准工天',	excelLabel: '二阶段标准工天',	width:70,	label:"${customer.progStage}"=="02"?"<font color='red'>标准工天</font>":"标准工天",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="01" || "${customer.progStage}"=="99"?false:true}, 
			{name:'二阶段超期天数',		index:'二阶段超期天数',	excelLabel: '二阶段超期天数',	width:70,	label:"${customer.progStage}"=="02"?"<font color='red'>超期天数</font>":"超期天数",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="01" || "${customer.progStage}"=="99"?false:true}, 
			{name:'二阶段提报问题',		index:'二阶段提报问题',	excelLabel: '二阶段提报问题',	width:70,	label:"${customer.progStage}"=="02"?"<font color='red'>提报问题</font>":"提报问题",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
			{name:'二阶段罚款金额',		index:'二阶段罚款金额',	excelLabel: '二阶段罚款金额',	width:70,	label:"${customer.progStage}"=="02"?"<font color='red'>罚款金额</font>":"罚款金额",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
			{name:'三阶段开始时间',		index:'三阶段开始时间',	excelLabel: '三阶段开始时间',	width:70,	label:"${customer.progStage}"=="03"?"<font color='red'>开始时间</font>":"开始时间",	sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="02" || "${customer.progStage}"=="99"?false:true},
			{name:'三阶段结束时间',		index:'三阶段结束时间',	excelLabel: '三阶段结束时间',	width:70,	label:"${customer.progStage}"=="03"?"<font color='red'>结束时间</font>":"结束时间",	sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="02" || "${customer.progStage}"=="99"?false:true}, 
			{name:'三阶段实际工天',		index:'三阶段实际工天',	excelLabel: '三阶段实际工天',	width:70,	label:"${customer.progStage}"=="03"?"<font color='red'>实际工天</font>":"实际工天",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="02" || "${customer.progStage}"=="99"?false:true}, 
			{name:'三阶段标准工天',		index:'三阶段标准工天',	excelLabel: '三阶段标准工天',	width:70,	label:"${customer.progStage}"=="03"?"<font color='red'>标准工天</font>":"标准工天",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="02" || "${customer.progStage}"=="99"?false:true}, 
			{name:'三阶段超期天数',		index:'三阶段超期天数',	excelLabel: '三阶段超期天数',	width:70,	label:"${customer.progStage}"=="03"?"<font color='red'>超期天数</font>":"超期天数",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="02" || "${customer.progStage}"=="99"?false:true},
			{name:'三阶段提报问题',		index:'三阶段提报问题',	excelLabel: '三阶段提报问题',	width:70,	label:"${customer.progStage}"=="03"?"<font color='red'>提报问题</font>":"提报问题",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
			{name:'三阶段罚款金额',		index:'三阶段罚款金额',	excelLabel: '三阶段罚款金额',	width:70,	label:"${customer.progStage}"=="03"?"<font color='red'>罚款金额</font>":"罚款金额",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
			{name:'四阶段开始时间',		index:'四阶段开始时间',	excelLabel: '四阶段开始时间',	width:70,	label:"${customer.progStage}"=="04"?"<font color='red'>开始时间</font>":"开始时间",	sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true},
			{name:'四阶段结束时间',		index:'四阶段结束时间',	excelLabel: '四阶段结束时间',	width:70,	label:"${customer.progStage}"=="04"?"<font color='red'>结束时间</font>":"结束时间",	sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
			{name:'四阶段实际工天',		index:'四阶段实际工天',	excelLabel: '四阶段实际工天',	width:70,	label:"${customer.progStage}"=="04"?"<font color='red'>实际工天</font>":"实际工天",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
			{name:'四阶段标准工天',		index:'四阶段标准工天',	excelLabel: '四阶段标准工天',	width:70,	label:"${customer.progStage}"=="04"?"<font color='red'>标准工天</font>":"标准工天",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
			{name:'四阶段超期天数',		index:'四阶段超期天数',	excelLabel: '四阶段超期天数',	width:70,	label:"${customer.progStage}"=="04"?"<font color='red'>超期天数</font>":"超期天数",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true},
			{name:'四阶段提报问题',		index:'四阶段提报问题',	excelLabel: '四阶段提报问题',	width:70,	label:"${customer.progStage}"=="04"?"<font color='red'>提报问题</font>":"提报问题",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true},
			{name:'四阶段罚款金额',		index:'四阶段罚款金额',	excelLabel: '四阶段罚款金额',	width:70,	label:"${customer.progStage}"=="04"?"<font color='red'>罚款金额</font>":"罚款金额",	sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true},
		],
		gridComplete:function(){
           	$(".ui-jqgrid-bdiv").scrollTop(0);
        	frozenHeightReset("dataTable");
        	//固定列合计栏
        	$("tbody tr[class~=footrow] td[aria-describedby=dataTable_当前超期天数]").html(myRound($("#dataTable").getCol('当前超期天数', false, 'avg')));
        	$("tbody tr[class~=footrow] td[aria-describedby=dataTable_总工期]").html(myRound($("#dataTable").getCol('总工期', false, 'avg')));
        	$("tbody tr[class~=footrow] td[aria-describedby=dataTable_已施工天数]").html(myRound($("#dataTable").getCol('已施工天数', false, 'avg')));
        	$("tbody tr[class~=footrow] td[aria-describedby=dataTable_当前标准工期]").html(myRound($("#dataTable").getCol('当前标准工期', false, 'avg')));
        	$("#dataTable").footerData('set', {"一阶段实际工天": myRound($("#dataTable").getCol('一阶段实际工天', false, 'avg'))});
        	$("#dataTable").footerData('set', {"一阶段标准工天": myRound($("#dataTable").getCol('一阶段标准工天', false, 'avg'))});
        	$("#dataTable").footerData('set', {"一阶段超期天数": myRound($("#dataTable").getCol('一阶段超期天数', false, 'avg'))});
        	$("#dataTable").footerData('set', {"二阶段实际工天": myRound($("#dataTable").getCol('二阶段实际工天', false, 'avg'))});
        	$("#dataTable").footerData('set', {"二阶段标准工天": myRound($("#dataTable").getCol('二阶段标准工天', false, 'avg'))});
        	$("#dataTable").footerData('set', {"二阶段超期天数": myRound($("#dataTable").getCol('二阶段超期天数', false, 'avg'))});
        	$("#dataTable").footerData('set', {"三阶段实际工天": myRound($("#dataTable").getCol('三阶段实际工天', false, 'avg'))});
        	$("#dataTable").footerData('set', {"三阶段标准工天": myRound($("#dataTable").getCol('三阶段标准工天', false, 'avg'))});
        	$("#dataTable").footerData('set', {"三阶段超期天数": myRound($("#dataTable").getCol('三阶段超期天数', false, 'avg'))});
        	$("#dataTable").footerData('set', {"四阶段实际工天": myRound($("#dataTable").getCol('四阶段实际工天', false, 'avg'))});
        	$("#dataTable").footerData('set', {"四阶段标准工天": myRound($("#dataTable").getCol('四阶段标准工天', false, 'avg'))});
        	$("#dataTable").footerData('set', {"四阶段超期天数": myRound($("#dataTable").getCol('四阶段超期天数', false, 'avg'))});
        	
        },
        loadonce:true
	});
	$("#dataTable").jqGrid('setGroupHeaders', {
	  	useColSpanStyle: true, 
		groupHeaders:[
				{startColumnName: '一阶段开始时间', numberOfColumns: 7, titleText: "${customer.progStage}"=="01"?"<font color='red'>第一阶段<font/>":"第一阶段"},
				{startColumnName: '二阶段开始时间', numberOfColumns: 7, titleText: "${customer.progStage}"=="02"?"<font color='red'>第二阶段<font/>":"第二阶段"},
				{startColumnName: '三阶段开始时间', numberOfColumns: 7, titleText: "${customer.progStage}"=="03"?"<font color='red'>第三阶段<font/>":"第三阶段"},
				{startColumnName: '四阶段开始时间', numberOfColumns: 7, titleText: "${customer.progStage}"=="04"?"<font color='red'>第四阶段<font/>":"第四阶段"},
		],
	}); 
	$("#dataTable").setFrozenColumns(53);
	
	/* 在建工地 */
    Global.JqGrid.initJqGrid("dataTableInProgress",{
        url:"${ctx}/admin/prjStageProgAnaly/goJqGrid",
        postData: $.extend({}, $("#page_form").jsonForm(), {status: "3"}),
        height: 550,
        rowNum:10000,
        colModel : [
            {name:'客户编号',   index:'客户编号',   width:70,   label:'客户编号',   sortable:true,align:"left",hidden:true,frozen:true},
            {name:'楼盘', index:'楼盘', width:120,  label:'楼盘', sortable:true,align:"left",frozen:true},
            {name:'项目经理',   index:'项目经理',   width:70,   label:'项目经理',   sortable:true,align:"left",frozen:true},
            {name:'工程部',    index:'工程部',    width:70,   label:'工程部',    sortable:true,align:"left",frozen:true},
            {name:'面积', index:'面积', width:50,   label:'面积', sortable:true,align:"right",frozen:true},
            {name:'户型',     index:'户型',     width:50,   label:'户型',sortable:true,align:"left",frozen:true},
            {name:'客户类型',   index:'客户类型',       width:70,   label:'客户类型',sortable:true,align:"left",frozen:true},
            {name:'总工期',    index:'总工期'  ,  excelLabel: '总标准工期',        width:70,   label:'总标准</br>工期',sortable:true,align:"right",frozen:true,sum:true},
            {name:'当前标准工期',     index:'当前标准工期',  excelLabel: '当前标准工期'   ,   width:70,   label:'当前标准</br>工期',sortable:true,align:"right",sum:true,frozen:true,hidden:"${customer.progStage}"=="99"?true:false},
            {name:'计划工期',   index:'计划工期',       width:70,   label:'计划工期',sortable:true,align:"right",sum:true,frozen:true,hidden:"${customer.progStage}"=="99"?false:true},
            {name:'已施工天数', index:'已施工天数', width:70, label: '总施工</br>天数', excelLabel: '总施工天数', sortable:true, align:"right", frozen:true, sum:true},
            {name:'当前超期天数', index:'当前超期天数', width:70, label: '总超期</br>天数', excelLabel: '总超期天数', sortable:true, align:"right", frozen:true, sum:true},
            {name:'当前阶段是否超期', index:'当前阶段是否超期', width:70, label: '当前阶段</br>是否超期', excelLabel: '当前阶段是否超期', sortable:true, align:"right", frozen:true, sum:true, hidden: "${customer.progStage}" == "99" ? true : false},
            {name:'一阶段开始时间', index:'一阶段开始时间', excelLabel: '一阶段开始时间',  width:70,   label:"${customer.progStage}"=="01"?"<font color='red'>开始时间</font>":"开始时间", sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true},
            {name:'一阶段结束时间', index:'一阶段结束时间', excelLabel: '一阶段结束时间',  width:70,   label:"${customer.progStage}"=="01"?"<font color='red'>结束时间</font>":"结束时间", sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
            {name:'一阶段实际工天', index:'一阶段实际工天', excelLabel: '一阶段实际工天',  width:70,   label:"${customer.progStage}"=="01"?"<font color='red'>实际工天</font>":"实际工天", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
            {name:'一阶段标准工天', index:'一阶段标准工天', excelLabel: '一阶段标准工天',  width:70,   label:"${customer.progStage}"=="01"?"<font color='red'>标准工天</font>":"标准工天", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
            {name:'一阶段超期天数', index:'一阶段超期天数', excelLabel: '一阶段超期天数',  width:70,   label:"${customer.progStage}"=="01"?"<font color='red'>超期天数</font>":"超期天数", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
            {name:'一阶段提报问题', index:'一阶段提报问题', excelLabel: '一阶段提报问题',  width:70,   label:"${customer.progStage}"=="01"?"<font color='red'>提报问题</font>":"提报问题", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
            {name:'一阶段罚款金额', index:'一阶段罚款金额', excelLabel: '一阶段罚款金额',  width:70,   label:"${customer.progStage}"=="01"?"<font color='red'>罚款金额</font>":"罚款金额", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="01" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
            {name:'二阶段开始时间', index:'二阶段开始时间', excelLabel: '二阶段开始时间',  width:70,   label:"${customer.progStage}"=="02"?"<font color='red'>开始时间</font>":"开始时间", sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="01" || "${customer.progStage}"=="99"?false:true},
            {name:'二阶段结束时间', index:'二阶段结束时间', excelLabel: '二阶段结束时间',  width:70,   label:"${customer.progStage}"=="02"?"<font color='red'>结束时间</font>":"结束时间", sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="01" || "${customer.progStage}"=="99"?false:true}, 
            {name:'二阶段实际工天', index:'二阶段实际工天', excelLabel: '二阶段实际工天',  width:70,   label:"${customer.progStage}"=="02"?"<font color='red'>实际工天</font>":"实际工天", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="01" || "${customer.progStage}"=="99"?false:true}, 
            {name:'二阶段标准工天', index:'二阶段标准工天', excelLabel: '二阶段标准工天',  width:70,   label:"${customer.progStage}"=="02"?"<font color='red'>标准工天</font>":"标准工天", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="01" || "${customer.progStage}"=="99"?false:true}, 
            {name:'二阶段超期天数', index:'二阶段超期天数', excelLabel: '二阶段超期天数',  width:70,   label:"${customer.progStage}"=="02"?"<font color='red'>超期天数</font>":"超期天数", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="01" || "${customer.progStage}"=="99"?false:true}, 
            {name:'二阶段提报问题', index:'二阶段提报问题', excelLabel: '二阶段提报问题',  width:70,   label:"${customer.progStage}"=="02"?"<font color='red'>提报问题</font>":"提报问题", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
            {name:'二阶段罚款金额', index:'二阶段罚款金额', excelLabel: '二阶段罚款金额',  width:70,   label:"${customer.progStage}"=="02"?"<font color='red'>罚款金额</font>":"罚款金额", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="02" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
            {name:'三阶段开始时间', index:'三阶段开始时间', excelLabel: '三阶段开始时间',  width:70,   label:"${customer.progStage}"=="03"?"<font color='red'>开始时间</font>":"开始时间", sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="02" || "${customer.progStage}"=="99"?false:true},
            {name:'三阶段结束时间', index:'三阶段结束时间', excelLabel: '三阶段结束时间',  width:70,   label:"${customer.progStage}"=="03"?"<font color='red'>结束时间</font>":"结束时间", sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="02" || "${customer.progStage}"=="99"?false:true}, 
            {name:'三阶段实际工天', index:'三阶段实际工天', excelLabel: '三阶段实际工天',  width:70,   label:"${customer.progStage}"=="03"?"<font color='red'>实际工天</font>":"实际工天", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="02" || "${customer.progStage}"=="99"?false:true}, 
            {name:'三阶段标准工天', index:'三阶段标准工天', excelLabel: '三阶段标准工天',  width:70,   label:"${customer.progStage}"=="03"?"<font color='red'>标准工天</font>":"标准工天", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="02" || "${customer.progStage}"=="99"?false:true}, 
            {name:'三阶段超期天数', index:'三阶段超期天数', excelLabel: '三阶段超期天数',  width:70,   label:"${customer.progStage}"=="03"?"<font color='red'>超期天数</font>":"超期天数", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="02" || "${customer.progStage}"=="99"?false:true},
            {name:'三阶段提报问题', index:'三阶段提报问题', excelLabel: '三阶段提报问题',  width:70,   label:"${customer.progStage}"=="03"?"<font color='red'>提报问题</font>":"提报问题", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
            {name:'三阶段罚款金额', index:'三阶段罚款金额', excelLabel: '三阶段罚款金额',  width:70,   label:"${customer.progStage}"=="03"?"<font color='red'>罚款金额</font>":"罚款金额", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
            {name:'四阶段开始时间', index:'四阶段开始时间', excelLabel: '四阶段开始时间',  width:70,   label:"${customer.progStage}"=="04"?"<font color='red'>开始时间</font>":"开始时间", sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true},
            {name:'四阶段结束时间', index:'四阶段结束时间', excelLabel: '四阶段结束时间',  width:70,   label:"${customer.progStage}"=="04"?"<font color='red'>结束时间</font>":"结束时间", sortable:true,align:"left",formatter: formatDate,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
            {name:'四阶段实际工天', index:'四阶段实际工天', excelLabel: '四阶段实际工天',  width:70,   label:"${customer.progStage}"=="04"?"<font color='red'>实际工天</font>":"实际工天", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
            {name:'四阶段标准工天', index:'四阶段标准工天', excelLabel: '四阶段标准工天',  width:70,   label:"${customer.progStage}"=="04"?"<font color='red'>标准工天</font>":"标准工天", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true}, 
            {name:'四阶段超期天数', index:'四阶段超期天数', excelLabel: '四阶段超期天数',  width:70,   label:"${customer.progStage}"=="04"?"<font color='red'>超期天数</font>":"超期天数", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true},
            {name:'四阶段提报问题', index:'四阶段提报问题', excelLabel: '四阶段提报问题',  width:70,   label:"${customer.progStage}"=="04"?"<font color='red'>提报问题</font>":"提报问题", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true},
            {name:'四阶段罚款金额', index:'四阶段罚款金额', excelLabel: '四阶段罚款金额',  width:70,   label:"${customer.progStage}"=="04"?"<font color='red'>罚款金额</font>":"罚款金额", sortable:true,align:"right",sum:true,hidden:"${customer.progStage}"=="03" || "${customer.progStage}"=="04" || "${customer.progStage}"=="99"?false:true},
        ],
        gridComplete:function(){
            $(".ui-jqgrid-bdiv").scrollTop(0);
            frozenHeightReset("dataTableInProgress");
            //固定列合计栏
            $("tbody tr[class~=footrow] td[aria-describedby=dataTableInProgress_当前超期天数]").html(myRound($("#dataTableInProgress").getCol('当前超期天数', false, 'avg')));
            $("tbody tr[class~=footrow] td[aria-describedby=dataTableInProgress_总工期]").html(myRound($("#dataTableInProgress").getCol('总工期', false, 'avg')));
            $("tbody tr[class~=footrow] td[aria-describedby=dataTableInProgress_已施工天数]").html(myRound($("#dataTableInProgress").getCol('已施工天数', false, 'avg')));
            $("tbody tr[class~=footrow] td[aria-describedby=dataTableInProgress_当前标准工期]").html(myRound($("#dataTableInProgress").getCol('当前标准工期', false, 'avg')));
            $("#dataTableInProgress").footerData('set', {"一阶段实际工天": myRound($("#dataTableInProgress").getCol('一阶段实际工天', false, 'avg'))});
            $("#dataTableInProgress").footerData('set', {"一阶段标准工天": myRound($("#dataTableInProgress").getCol('一阶段标准工天', false, 'avg'))});
            $("#dataTableInProgress").footerData('set', {"一阶段超期天数": myRound($("#dataTableInProgress").getCol('一阶段超期天数', false, 'avg'))});
            $("#dataTableInProgress").footerData('set', {"二阶段实际工天": myRound($("#dataTableInProgress").getCol('二阶段实际工天', false, 'avg'))});
            $("#dataTableInProgress").footerData('set', {"二阶段标准工天": myRound($("#dataTableInProgress").getCol('二阶段标准工天', false, 'avg'))});
            $("#dataTableInProgress").footerData('set', {"二阶段超期天数": myRound($("#dataTableInProgress").getCol('二阶段超期天数', false, 'avg'))});
            $("#dataTableInProgress").footerData('set', {"三阶段实际工天": myRound($("#dataTableInProgress").getCol('三阶段实际工天', false, 'avg'))});
            $("#dataTableInProgress").footerData('set', {"三阶段标准工天": myRound($("#dataTableInProgress").getCol('三阶段标准工天', false, 'avg'))});
            $("#dataTableInProgress").footerData('set', {"三阶段超期天数": myRound($("#dataTableInProgress").getCol('三阶段超期天数', false, 'avg'))});
            $("#dataTableInProgress").footerData('set', {"四阶段实际工天": myRound($("#dataTableInProgress").getCol('四阶段实际工天', false, 'avg'))});
            $("#dataTableInProgress").footerData('set', {"四阶段标准工天": myRound($("#dataTableInProgress").getCol('四阶段标准工天', false, 'avg'))});
            $("#dataTableInProgress").footerData('set', {"四阶段超期天数": myRound($("#dataTableInProgress").getCol('四阶段超期天数', false, 'avg'))});
            
        },
        loadonce:true
    });
    $("#dataTableInProgress").jqGrid('setGroupHeaders', {
        useColSpanStyle: true, 
        groupHeaders:[
                {startColumnName: '一阶段开始时间', numberOfColumns: 7, titleText: "${customer.progStage}"=="01"?"<font color='red'>第一阶段<font/>":"第一阶段"},
                {startColumnName: '二阶段开始时间', numberOfColumns: 7, titleText: "${customer.progStage}"=="02"?"<font color='red'>第二阶段<font/>":"第二阶段"},
                {startColumnName: '三阶段开始时间', numberOfColumns: 7, titleText: "${customer.progStage}"=="03"?"<font color='red'>第三阶段<font/>":"第三阶段"},
                {startColumnName: '四阶段开始时间', numberOfColumns: 7, titleText: "${customer.progStage}"=="04"?"<font color='red'>第四阶段<font/>":"第四阶段"},
        ],
    }); 
    $("#dataTableInProgress").setFrozenColumns(53);
	
});

function activeGrid() {
    var activePanel = $(".tab-pane.active").attr("id");
    
    switch (activePanel) {
        case "tab_completion": return "dataTable";
        case "tab_inProgress": return "dataTableInProgress";
    }
}

function view() {
	var ret = selectDataTableRow(activeGrid());
	if (ret) {	
    	Global.Dialog.showDialog("Update",{
			title:"查看客户工程进度",
			url:"${ctx}/admin/prjDelayAnaly/goView",
			postData:{code:ret.客户编号},
			width:1100,
			height:715,
		});
  	} else {
  		art.dialog({
			content: "请选择一条记录"
		});
  	}
}

function exportExcel() {
    var tableId = activeGrid();
    var status = $("#status");
    
    switch (tableId) {
        case "dataTable": status.val("2");
            break;
        case "dataTableInProgress": status.val("3");
            break;
    }
    
    doExcelAll("${ctx}/admin/prjStageProgAnaly/doExcel", tableId);
}

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " onclick="view()">查看进度信息</button>
					<button type="button" class="btn btn-system " onclick="exportExcel()">导出Excel</button>
					<button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
		    <input type="hidden" name="jsonString" value=""/>
		    <input type="hidden" id="status" name="status" value=""/>
		    <input type="hidden" name="department2" value="${customer.department2}"/>
		    <input type="hidden" name="progStage" value="${customer.progStage}"/>
		    <input type="hidden" name="layout" value="${customer.layout}"/>
		    <input type="hidden" name="isPartDecorate" value="${customer.isPartDecorate}"/>
		    <input type="hidden" name="dateFrom" value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>"/>
		    <input type="hidden" name="dateTo" value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>"/>
		    <input type="hidden" name="beginDateFrom" value="<fmt:formatDate value='${customer.beginDateFrom}' pattern='yyyy-MM-dd'/>"/>
		    <input type="hidden" name="beginDateTo" value="<fmt:formatDate value='${customer.beginDateTo}' pattern='yyyy-MM-dd'/>"/>
		    <input type="hidden" name="confirmBeginFrom" value="<fmt:formatDate value='${customer.confirmBeginFrom}' pattern='yyyy-MM-dd'/>"/>
		    <input type="hidden" name="confirmBeginTo" value="<fmt:formatDate value='${customer.confirmBeginTo}' pattern='yyyy-MM-dd'/>"/>
		</form>
	</div>
	<div class="clear_float"></div>
    <div class="container-fluid">
	    <ul class="nav nav-tabs" role="tablist">
	        <li class="active">
	            <a href="#tab_completion" data-toggle="tab">阶段完工工地</a>
	        </li>
	        <li class="">
	            <a href="#tab_inProgress" data-toggle="tab">阶段在建工地</a>
	        </li>
	    </ul>
	    <div class="tab-content">
	        <div id="tab_completion" class="tab-pane fade in active">
		        <div id="content-list-completion">
		            <table id="dataTable"></table>
		        </div>
	        </div>
	        <div id="tab_inProgress" class="tab-pane fade">
	            <div id="content-list-inProgress">
	                <table id="dataTableInProgress"></table>
	            </div>
	        </div>
	    </div>
    </div>
</body>
</html>
