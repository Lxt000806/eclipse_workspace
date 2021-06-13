<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
    //初始化表格
 	Global.JqGrid.initJqGrid("dataTable_sjfl", {
         url: "${ctx}/admin/commiCycle/goSupplierRebateJqGrid",
         postData: $("#page_form").jsonForm(),
         height: 450,
         rowNum: 100000000,
         colModel: [
             {name: "pk", index: "pk", width: 50, label: "PK", sortable: true, align: "left", hidden: true},
             {name: "date", index: "date", width: 80, label: "销售日期", sortable: true, align: "left", formatter: formatDate},
             {name: "itemtype1", index: "itemtype1", width: 80, label: "材料类型1", sortable: true, align: "left", hidden: true},
             {name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
             {name: "supplcode", index: "supplcode", width: 80, label: "供应商名称", sortable: true, align: "left", hidden: true},
             {name: "suppldescr", index: "suppldescr", width: 120, label: "供应商名称", sortable: true, align: "left"},
             {name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
             {name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
             {name: "itemdescr", index: "itemdescr", width: 120, label: "材料名称", sortable: true, align: "left"},
             {name: "amount", index: "amount", width: 100, label: "返利总金额", sortable: true, align: "right",sum:true},
             {name: "empcode", index: "empcode", width: 80, label: "干系人", sortable: true, align: "left", hidden: true},
             {name: "empname", index: "empname", width: 80, label: "干系人", sortable: true, align: "left"},
             {name: "commiamount", index: "commiamount", width: 100, label: "业务员提成", sortable: true, align: "right"},
             {name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
             {name: "lastupdate", index: "lastupdate", width: 130, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
             {name: "lastupdatedby", index: "lastupdatedby", width: 110, label: "最后更新人员", sortable: true, align: "left"},
             {name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
             {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
         ],
     });
});


</script>
<div class="body-box-list">
	<div class="pageContent" >
		<table id="dataTable_sjfl"></table>
	</div>
</div>
