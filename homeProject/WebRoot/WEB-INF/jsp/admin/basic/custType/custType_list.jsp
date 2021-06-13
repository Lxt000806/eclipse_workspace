<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>客户类型管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#type").val("");
		}	
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/custType/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "code", index: "code", width: 65, label: "编号", sortable: true, align: "left"},
			{name: "desc1", index: "desc1", width: 102, label: "说明", sortable: true, align: "left"},
			{name: "type", index: "type", width: 79, label: "类型", sortable: true, align: "left", hidden: true},
			{name: "typedescr", index: "typedescr", width: 79, label: "类型", sortable: true, align: "left"},
			{name: "dispseq", index: "dispseq", width: 65, label: "显示顺序", sortable: true, align: "left"},
			{name: "areaper", index: "areaper", width: 78, label: "面积系数", sortable: true, align: "right"},
			{name: "basefee_compper", index: "basefee_compper", width: 110, label: "基础综合费系数", sortable: true, align: "right"},
			{name: "basefee_dirctper", index: "basefee_dirctper", width: 108, label: "基础直接费系数", sortable: true, align: "right"},
			{name: "mainsetfeeper", index: "mainsetfeeper", width: 98, label: "主套餐费系数", sortable: true, align: "right"},
			{name: "setminusper", index: "setminusper", width: 115, label: "套餐内减项系数", sortable: true, align: "right"},
			{name: "setaddper", index: "setaddper", width: 134, label: "套餐外基础增项系数", sortable: true, align: "right"},
			{name: "longfeeper", index: "longfeeper", width: 89, label: "远程费系数", sortable: true, align: "right"},
			{name: "managefee_baseper", index: "managefee_baseper", width: 110, label: "基础管理费系数", sortable: true, align: "right"},
			{name: "mainfeeper", index: "mainfeeper", width: 87, label: "主材费系数", sortable: true, align: "right"},
			{name: "maindiscper", index: "maindiscper", width: 107, label: "主材优惠系数", sortable: true, align: "right"},
			{name: "managefee_mainper", index: "managefee_mainper", width: 109, label: "主材管理费系数", sortable: true, align: "right"},
			{name: "mainservfeeper", index: "mainservfeeper", width: 170, label: "服务性产品费（主材）系数", sortable: true, align: "right"},
			{name: "managefee_servper", index: "managefee_servper", width: 149, label: "服务性产品管理费系数", sortable: true, align: "right"},
			{name: "softfeeper", index: "softfeeper", width: 89, label: "软装费系数", sortable: true, align: "right"},
			{name: "softdiscper", index: "softdiscper", width: 96, label: "软装优惠系数", sortable: true, align: "right"},
			{name: "managefee_softper", index: "managefee_softper", width: 110, label: "软装管理费系数", sortable: true, align: "right"},
			{name: "integratefeeper", index: "integratefeeper", width: 85, label: "集成费系数", sortable: true, align: "right"},
			{name: "integratediscper", index: "integratediscper", width: 98, label: "集成优惠系数", sortable: true, align: "right"},
			{name: "managefee_intper", index: "managefee_intper", width: 110, label: "集成管理费系数", sortable: true, align: "right"},
			{name: "cupboardfeeper", index: "cupboardfeeper", width: 87, label: "橱柜费系数", sortable: true, align: "right"},
			{name: "cupboarddiscper", index: "cupboarddiscper", width: 104, label: "橱柜优惠系数", sortable: true, align: "right"},
			{name: "managefee_cupper", index: "managefee_cupper", width: 109, label: "橱柜管理费系数", sortable: true, align: "right"},
			{name: "isdefaultstaticdescr", index: "isdefaultstaticdescr", width: 96, label: "是否默认统计", sortable: true, align: "right"},
			{name: "chgmanagefeeper", index: "chgmanagefeeper", width: 109, label: "增减管理费系数", sortable: true, align: "right"},
			{name: "expired", index: "expired", width: 74, label: "过期标志", sortable: true, align: "left"},
			{name: "confirmitemdescr", index: "confirmitemdescr", width: 97, label: "是否确认材料", sortable: true, align: "left"},
			{name: "isaddallinfodescr", index: "isaddallinfodescr", width: 143, label: "是否添加完整客户材料", sortable: true, align: "left"},
			{name: "ctrlexpr", index: "ctrlexpr", width: 242, label: "发包价公式", sortable: true, align: "left"},
		    {name: "ctrlexprremarks", index: "ctrlexprremarks", width: 170, label: "发包公式说明", sortable: true, align: "left"},
			{name: "iscalcperfdescr", index: "iscalcperfdescr", width: 122, label: "是否参与业绩计算", sortable: true, align: "left"},
			{name: "firstpayper", index: "firstpayper", width: 100, label: "达标首付比例", sortable: true, align: "right"},
			{name: "setcontainintdescr", index: "setcontainintdescr", width: 115, label: "套餐内包含集成", sortable: true, align: "left"},
			{name: "setctrlexpr", index: "setctrlexpr", width: 220, label: "指定发包价计算公式", sortable: true, align: "left"},
			{name: "materialexpr", index: "materialexpr", width: 286, label: "材料金额计算公式", sortable: true, align: "left"},
			{name: "issetmainctrldescr", index: "issetmainctrldescr", width: 140, label: "套餐主材是否发包", sortable: true, align: "left"},
			{name: "mustimporttempdescr", index: "mustimporttempdescr", width: 140, label: "基础预算必须导入模板", sortable: true, align: "left"},
			{name: "cmpnyname", index: "cmpnyname", width: 90, label: "公司名称", sortable: true, align: "left"},
			{name: "logofile", index: "logofile", width: 89, label: "Logo文件名", sortable: true, align: "left"},
			{name: "cmpnyfullname", index: "cmpnyfullname", width: 92, label: "公司全称", sortable: true, align: "left"},
			{name: "cmpnyaddress", index: "cmpnyaddress", width: 124, label: "公司地址", sortable: true, align: "left"},
			{name: "baseperfper", index: "baseperfper", width: 90, label: "基础业绩比例", sortable: true, align: "right"},
			{name: "iscalcbasediscdescr", index: "iscalcbasediscdescr", width: 170, label: "业绩是否扣基础协议优惠", sortable: true, align: "left"},
			{name: "perfexpr", index: "perfexpr", width: 242, label: "业绩公式", sortable: true, align: "left"},
			{name: "perfexprremarks", index: "perfexprremarks", width: 150, label: "业绩公式说明", sortable: true, align: "left"},
			{name: "waterctrlpri", index: "waterctrlpri", width: 80, label: "防水补贴", sortable: true, align: "right"},
			{name: "intsaleamount_set", index: "intsaleamount_set", width: 170, label: "集成套餐内销售额", sortable: true, align: "left"},
		    {name: "cupsaleamount_set", index: "cupsaleamount_set", width: 170, label: "橱柜套餐内销售额", sortable: true, align: "left"},
		    {name: "taxexpr", index: "taxexpr", width: 170, label: "税金公式", sortable: true, align: "left"},
		    {name: "kitchenstdarea", index: "kitchenstdarea", width: 150, label: "厨房（发包）标准面积", sortable: true, align: "right"},
		    {name: "toiletstdarea", index: "toiletstdarea", width: 150, label: "卫生间（发包）标准面积", sortable: true, align: "right"},
		    {name: "overareasubsidyper", index: "overareasubsidyper", width: 150, label: "厨卫超面积每平米补贴", sortable: true, align: "right"},
		    {name: "itemremark", index: "itemremark", width: 140, label: "预算打印基础物料说明", sortable: true, align: "left"},
		    {name: "pricremark", index: "pricremark", width: 140, label: "预算打印基础报价说明", sortable: true, align: "left"},
		    {name: "basespecdescr", index: "basespecdescr", width: 80, label: "基础解单", sortable: true, align: "left"},
		],
	});
		//新增
		$("#add").on("click",function(){
				Global.Dialog.showDialog("goSave",{
					title:"客户类型明细--增加",
					url:"${ctx}/admin/custType/goSave",
				    height:720,
				    width:1400,
					returnFun:goto_query
				});
		});
		//编辑
		$("#update").on("click",function(){
				var ret = selectDataTableRow();
				if(!ret){
					art.dialog({
								content:"请选择一条记录！",
								width: 200
							});
					return;
				}
				Global.Dialog.showDialog("goUpdate",{
					title:"客户类型明细--编辑",
					url:"${ctx}/admin/custType/goUpdate?id="+ret.code+"&m_umState=M",			
				    height:720,
				    width:1400,
					returnFun:goto_query
				});
		});
		//复制
		$("#copy").on("click",function(){
				var ret = selectDataTableRow();
				if(!ret){
					art.dialog({
						content:"请选择一条记录！",
						width: 200
					});
					return;
				}
				Global.Dialog.showDialog("goUpdate",{
					title:"客户类型明细--复制",
					url:"${ctx}/admin/custType/goUpdate?id="+ret.code+"&m_umState=C",	
				    height:720,
				    width:1400,
					returnFun:goto_query
				});
		});

        // 默认预算报表
        $("#defaultPlanReport").on("click", function() {
            var ret = selectDataTableRow();
            
            if (!ret) {
                art.dialog({content: "请选择一条记录！"});
                return;
            }
            
            if (ret.type !== "2") {
                art.dialog({content: "非套餐客户暂不可设置默认预算报表！"});
                return;
            }
            
            Global.Dialog.showDialog("goDetail", {
                title: "客户类型--默认预算报表",
                url: "${ctx}/admin/custType/goDefaultPlanReport?id=" + ret.code,
                height: 420,
                width: 600,
                returnFun: goto_query
            });
        });
        
        //查看
        $("#view").on("click", function() {
            var ret = selectDataTableRow();
            if (!ret) {
                art.dialog({
                    content: "请选择一条记录！",
                    width: 200
                });
                return;
            }
            Global.Dialog.showDialog("goDetail", {
                title: "客户类型明细--查看",
                url: "${ctx}/admin/custType/goDetail?id=" + ret.code,
                height: 720,
                width: 1400,
                returnFun: goto_query
            });
        });
        //分段发包
        $("#ctrlExpr").on("click", function() {
            var ret = selectDataTableRow();
            if (!ret) {
                art.dialog({
                    content: "请选择一条记录！",
                    width: 200
                });
                return;
            }
            Global.Dialog.showDialog("goCtrlExpr", {
                title: "客户类型明细--分段发包",
                url: "${ctx}/admin/custType/goCtrlExpr?id=" + ret.code,
                height: 680,
                width: 1050,
                returnFun: goto_query
            });
        });
        //合同模板管理
        $("#custContractTemp").on("click", function() {
            Global.Dialog.showDialog("custContractTemp", {
                title: "合同模板管理",
                url: "${ctx}/admin/custType/goCustContractTemp",
                height: 675,
                width: 1300,
                returnFun: goto_query
            });
        });
        //分段业绩公式
        $("#custTypePerfExpr").on("click", function() {
            var ret = selectDataTableRow();
            if (!ret) {
                art.dialog({
                    content: "请选择一条记录！",
                    width: 200
                });
                return;
            }
            Global.Dialog.showDialog("goCustTypePerfExpr", {
                title: "客户类型明细--分段业绩公式",
                url: "${ctx}/admin/custType/goCustTypePerfExpr?id=" + ret.code,
                height: 680,
                width: 1050,
                returnFun: goto_query
            });
        });
    });
    function doExcel() {
        var url = "${ctx}/admin/custType/doExcel";
        doExcelAll(url);
    }
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li class="form-validate"><label>编号</label> <input type="text"
						id="code" maxlength="10" name="code" style="width:160px;"
						value="${custType.code}" />
					</li>
					<li class="form-validate"><label>说明</label> <input type="text"
						id="desc1" name="desc1" style="width:160px;" />
					</li>
					<li><label>客户分类</label> <house:xtdm id="type"
							dictCode="CUSTTYPESORT"></house:xtdm>
					</li>
					<li class="search-group"><input type="checkbox" id="expired"
						name="expired" value="${custType.expired}"
						onclick="hideExpired(this)" ${custType.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<button type="button" class="btn btn-system" id="add">
				<span>新增</span>
			</button>
			<button type="button" class="btn btn-system" id="copy">
				<span>复制</span>
			</button>
			<button type="button" class="btn btn-system"
				id="update">
				<span>修改</span>
			</button>
			<button type="button" class="btn btn-system" id="defaultPlanReport">
				<span>默认预算报表</span>
			</button>
			<button type="button" class="btn btn-system"
				id="view">
				<span>查看</span>
			</button>
			<button type="button" class="btn btn-system"
				id="ctrlExpr">
				<span>分段发包</span>
			</button>
			<house:authorize authCode="CUSTTYPE_PERFEXPR">
				<button type="button" class="btn btn-system" id="custTypePerfExpr">
					<span>分段业绩公式</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CUSTTYPE_CONTEMP">
				<button type="button" class="btn btn-system" id="custContractTemp">
					<span>合同模板管理</span>
				</button>
			</house:authorize>
			<button type="button" class="btn btn-system" onclick="doExcel()">
				<span>导出excel</span>
			</button>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>
