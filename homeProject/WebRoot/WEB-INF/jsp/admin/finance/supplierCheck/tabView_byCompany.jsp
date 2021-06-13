<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initEditJqGrid("dataTableByCompany", {
		height:200,
		url:"${ctx}/admin/supplierCheck/goJqGridMainItemByCompany?checkOutNo="+$("#no").val(),
		rowNum: 10000,
		styleUI:"Bootstrap",
		colModel:[
			{name: "cmpname", index: "cmpname", width: 150, label: "签约公司", sortable: true, align: "left", },
			{name: "amount", index: "amount", width: 80, label: "总金额", sortable: true, align: "left",sum:true},
		],
		loadonce: true,
	}); 
});	
</script>
<input type="hidden" id="firstFlag" name="firstFlag" value="1"/>
<input type="hidden" id="maxCheckSeq" name="maxCheckSeq" value="0"/>
<table id="dataTableByCompany"></table>
