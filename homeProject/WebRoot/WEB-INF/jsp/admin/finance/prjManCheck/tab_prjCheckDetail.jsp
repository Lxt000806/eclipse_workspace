<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
function colAdd(col){
	var arrayTitle = JSON.parse('${arryWorkType1Descr}');
	var arrayColumnName =JSON.parse('${arryTypeDescr}');
	for(var j=0;j<arrayTitle.length;j++){
		for(var i=0;i<arrayColumnName.length;i++){
			if (arrayColumnName[i].split("|")[0]==arrayTitle[j] )  
		  		col.push(
		  			{name: arrayColumnName[i], index: arrayColumnName[i], width: 95, label: arrayColumnName[i].split("|")[1], sortable: true, align: "right"}
				);		
	    };  	
	};
	col.push(
	    	{name: "sumamount", index: "sumamount", width: 70, label:"合计", sortable: true, align: "right"}
	);
	return col;
}
function groupHeadAdd(groupHead){
	var arrayTitle = JSON.parse('${arryWorkType1Descr}');
	var arrayColumnName =JSON.parse('${arryTypeDescr}');
	var arr_flag=-1,arr_len=0;
	for(var j=0;j<arrayTitle.length;j++){
		for(var i=0;i<arrayColumnName.length;i++){
			if (arrayColumnName[i].split("|")[0]==arrayTitle[j] ){
				if (arr_flag==-1){
					arr_flag=i;
				}
				arr_len++; 
			}   			
	    };
	    groupHead.push(
		  	   {startColumnName: arrayColumnName[arr_flag], numberOfColumns: arr_len, titleText: arrayTitle[j]}
		); 
		arr_flag=-1; arr_len=0;	
	};
	return groupHead;
}
	
/**初始化表格*/
$(function(){
    //初始化表格
    var fheight=220;
	if("${prjCheck.custTypeType}"=="2"){
		fheight=140	;
	}
	var  col =[],groupHead=[];	
	col.push (
		{name: "typedescr", index: "typedescr", width: 70, label: "项目", sortable: true, align:"left",count:true,frozen: true}
	);
    colAdd(col); 
    var gridOption ={
		height:fheight,
		url:"${ctx}/admin/prjManCheck/goJqGridPrjCheckDetail",
	    postData:{custCode:"${prjCheck.custCode}"},
		colModel :col,
		rowNum:100000,  
   		pager :'1'
	};	
    Global.JqGrid.initJqGrid("dataTable_prjCheckDetail", gridOption);
    //表头分组  begin	
    groupHeadAdd(groupHead);
	$("#dataTable_prjCheckDetail").jqGrid('setGroupHeaders', {
	  	useColSpanStyle: true, 
		groupHeaders:groupHead
	});
});      
function loadPrjDetailTab(){
	$("#dataTable_prjCheckDetail").jqGrid("setGridParam",{url:"${ctx}/admin/prjManCheck/goJqGridPrjCheckDetail",datatype:'json',postData:{custCode:"${prjCheck.custCode}"},page:1}).trigger("reloadGrid");
}

</script>
<div class="body-box-list" style="margin-top: 0px;">
	<table id="dataTable_prjCheckDetail" style="overflow: auto;"></table>
</div>	
		




