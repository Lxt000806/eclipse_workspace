<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>提成汇总查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/commiCustStakeholder/goHisJqGrid",
			postData:{mon:"${commiCustStakeholder.mon}",custCode:"${commiCustStakeholder.custCode}"},
			height:470,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "commimon", index: "commimon", width: 70, label: "提成月份", sortable: true, align: "left",frozen:true},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left",frozen:true},
				{name: "empname", index: "empname", width: 65, label: "员工", sortable: true, align: "left",frozen:true,},
				{name: "roledescr", index: "roledescr", width: 65, label: "角色", sortable: true, align: "left",frozen:true},
			    {name: "typedescr", index: "typedescr", width: 80, label: "提成类型", sortable: true, align: "left",frozen:true},
				{name: "perfamount", index: "perfamount", width: 75, label: "业绩金额", sortable: true, align: "right"},
				{name: "weightper", index: "weightper", width: 50, label: "权重", sortable: true, align: "right"},
				{name: "commiper", index: "commiper", width: 60, label: "提成点", sortable: true, align: "right"},
				{name: "subsidyper", index: "subsidyper", width: 60, label: "补贴点", sortable: true, align: "right"},
				{name: "multiple", index: "multiple", width: 50, label: "倍数", sortable: true, align: "right"},
				{name: "commiamount", index: "commiamount", width: 70, label: "提成金额", sortable: true, align: "right"},
				{name: "commiprovideper", index: "commiprovideper", width: 70, label: "提成比例", sortable: true, align: "right"},
				{name: "subsidyprovideper", index: "subsidyprovideper", width: 70, label: "补贴比例", sortable: true, align: "right"},
				{name: "multipleprovideper", index: "multipleprovideper", width: 70, label: "倍数比例", sortable: true, align: "right"},
				{name: "shouldprovideamount", index: "shouldprovideamount", width: 70, label: "本次应发", sortable: true, align: "right"},
				{name: "adjustamount", index: "adjustamount", width: 70, label: "调整金额", sortable: true, align: "right"},
				{name: "realprovideamount", index: "realprovideamount", width: 70, label: "本次实发", sortable: true, align: "right"},
				{name: "totalrealprovideamount", index: "totalrealprovideamount", width: 70, label: "累计已发", sortable: true, align: "right"},
				{name: "commiexprremarks", index: "commiexprremarks", width: 200, label: "提成公式", sortable: true, align: "left"},
			    {name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
			    {name: "signcommimon", index: "signcommimon", width: 90, label: "签单提成月份", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 61, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 71, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"},
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left",},
			],
		});
	});
	
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
