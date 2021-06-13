<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp"%>
    <script type="text/javascript">

        $(function() {
            Global.JqGrid.initJqGrid("dataTable", {
                height: $(document).height() - $("#content-list").offset().top - 50,
                rowNum: 100000,
                colModel : [
                    {name: "autoimport", index: "autoimport", width: 70, label: "自动导入", sortable: true, align: "left", formatter: importingDescription, cellattr: importingState},
                    {name: "opertype", index: "opertype", width: 68, label: "操作类型", sortable: true, align: "left", hidden: true},
                    {name: "opertypedescr", index: "opertypedescr", width: 68, label: "操作类型", sortable: true, align: "left"},
                    {name: "preplanareapk", index: "preplanareapk", width: 70, label: "空间", sortable: true, align: "left"},
                    {name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间", sortable: true, align: "left"},
                    {name: "areaattr", index: "areaattr", width: 70, label: "区域分类", sortable: true, align: "left"},
                    {name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left"},
                    {name: "itemdescr", index: "itemdescr", width: 150, label: "材料名称", sortable: true, align: "left"},
                    {name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
                    {name: "itemremark", index: "itemremark", width: 200, label: "材料描述", sortable: true, align: "left", hidden: false},
                    {name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right"},
                    {name: "uom", index: "uom", width: 55, label: "单位", sortable: true, align: "left", hidden: true},
                    {name: "uomdescr", index: "uomdescr", width: 55, label: "单位", sortable: true, align: "left", hidden: true},
                    {name: "price", index: "price", width: 50, label: "单价", sortable: true, align: "right", hidden: false},
                    {name: "projectcost", index: "projectcost", width: 50, label: "项目经理结算价", sortable: true, align: "right", hidden: true},
                    {name: "algorithm", index: "algorithm", width: 50, label: "算法", sortable: true, align: "right", hidden: true},
                    {name: "algorithmdescr", index: "algorithmdescr", width: 50, label: "算法", sortable: true, align: "right", hidden: true},
                    {name: "cuttype", index: "cuttype", width: 50, label: "切割类型", sortable: true, align: "right", hidden: true},
                    {name: "cuttypedescr", index: "cuttypedescr", width: 50, label: "切割类型", sortable: true, align: "right", hidden: true},
                    {name: "processcost", index: "processcost", width: 50, label: "加工费", sortable: true, align: "right", hidden: true},
                    {name: "custtypeitempk", index: "custtypeitempk", width: 50, label: "套餐材料Pk", sortable: true, align: "left", hidden: true},
                    {name: "custtypeitemprice", index: "custtypeitemprice", width: 50, label: "套餐材料价格", sortable: true, align: "right", hidden: true},
                    {name: "custtypeitemprojectcost", index: "custtypeitemprojectcost", width: 50, label: "套餐材料项目经理结算价", sortable: true, align: "right", hidden: true},
                    {name: "custtypeitemremark", index: "custtypeitemremark", width: 50, label: "套餐材料描述", sortable: true, align: "left", hidden: true},
                    {name: "lineamount", index: "lineamount", width: 50, label: "总价", sortable: true, align: "right", hidden: true},
                    {name: "beflineamount", index: "beflineamount", width: 50, label: "折扣前金额", sortable: true, align: "right", hidden: true},
                    {name: "tmplineamount", index: "tmplineamount", width: 50, label: "小计", sortable: true, align: "right", hidden: true},
                    {name: "markup", index: "markup", width: 50, label: "折扣", sortable: true, align: "right", hidden: true},
                    {name: "oldreqpk", index: "oldreqpk", width: 100, label: "原需求PK", sortable: true, align: "left", hidden: true},
                    {name: "oldreqisoutsetdescr", index: "oldreqisoutsetdescr", width: 70, label: "套外材料", sortable: true, align: "left"},
                    {name: "oldreqalgorithmdescr", index: "oldreqalgorithmdescr", width: 100, label: "算法", sortable: true, align: "left"},
                    {name: "remarks", index: "remarks", width: 150, label: "选材备注", sortable: true, align: "left"},
                    {name: "oldreqitemcode", index: "oldreqitemcode", width: 80, label: "原材料编号", sortable: true, align: "left"},
                    {name: "oldreqitemdescr", index: "oldreqitemdescr", width: 150, label: "原材料名称", sortable: true, align: "left"},
                    {name: "oldreqitemremark", index: "oldreqitemremark", width: 120, label: "原材料描述", sortable: true, align: "left"},
                    {name: "oldreqfixareadescr", index: "oldreqfixareadescr", width: 100, label: "区域", sortable: true, align: "left"},
                    {name: "oldreqitemtype2descr", index: "oldreqitemtype2descr", width: 110, label: "原材料类型2", sortable: true, align: "left"},
                    {name: "oldreqqty", index: "oldreqqty", width: 55, label: "原数量", sortable: true, align: "right"},
                    {name: "oldrequomdescr", index: "oldrequomdescr", width: 55, label: "原单位", sortable: true, align: "left"},
                    {name: "oldreqisservicedescr", index: "oldreqisservicedescr", width: 100, label: "原服务性产品", sortable: true, align: "left"},
                    /* hidden columns */
					{name: "oldreqfixareapk", index: "oldreqfixareapk", width: 50, label: "oldreqfixareapk", sortable: true, align: "left", hidden: true},
					{name: "oldreqintprodpk", index: "oldreqintprodpk", width: 50, label: "oldreqintprodpk", sortable: true, align: "left", hidden: true},
					{name: "oldreqintproddescr", index: "oldreqintproddescr", width: 50, label: "oldreqintproddescr", sortable: true, align: "left", hidden: true},
					{name: "oldreqitemtype2", index: "oldreqitemtype2", width: 50, label: "oldreqitemtype2", sortable: true, align: "left", hidden: true},
					{name: "oldreqitemtype3", index: "oldreqitemtype3", width: 50, label: "oldreqitemtype3", sortable: true, align: "left", hidden: true},
					{name: "oldreqitemtype3descr", index: "oldreqitemtype3descr", width: 50, label: "oldreqitemtype3descr", sortable: true, align: "left", hidden: true},
					{name: "oldrequom", index: "oldrequom", width: 50, label: "oldrequom", sortable: true, align: "left", hidden: true},
					{name: "oldreqitemtype1", index: "oldreqitemtype1", width: 50, label: "oldreqitemtype1", sortable: true, align: "left", hidden: true},
					{name: "oldreqsendqty", index: "oldreqsendqty", width: 50, label: "oldreqsendqty", sortable: true, align: "left", hidden: true},
					{name: "oldreqcost", index: "oldreqcost", width: 50, label: "oldreqcost", sortable: true, align: "left", hidden: true},
					{name: "oldrequnitprice", index: "oldrequnitprice", width: 50, label: "oldrequnitprice", sortable: true, align: "left", hidden: true},
					{name: "oldreqbeflineamount", index: "oldreqbeflineamount", width: 50, label: "oldreqbeflineamount", sortable: true, align: "left", hidden: true},
					{name: "oldreqmarkup", index: "oldreqmarkup", width: 50, label: "oldreqmarkup", sortable: true, align: "left", hidden: true},
					{name: "oldreqlineamount", index: "oldreqlineamount", width: 50, label: "oldreqlineamount", sortable: true, align: "left", hidden: true},
					{name: "oldreqremark", index: "oldreqremark", width: 50, label: "oldreqremark", sortable: true, align: "left", hidden: true},
					{name: "oldreqprocesscost", index: "oldreqprocesscost", width: 50, label: "oldreqprocesscost", sortable: true, align: "left", hidden: true},
					{name: "oldreqdispseq", index: "oldreqdispseq", width: 50, label: "oldreqdispseq", sortable: true, align: "left", hidden: true},
					{name: "oldreqisservice", index: "oldreqisservice", width: 50, label: "oldreqisservice", sortable: true, align: "left", hidden: true},
					{name: "oldreqiscommi", index: "oldreqiscommi", width: 50, label: "oldreqiscommi", sortable: true, align: "left", hidden: true},
					{name: "oldreqdisccost", index: "oldreqdisccost", width: 50, label: "oldreqdisccost", sortable: true, align: "left", hidden: true},
					{name: "oldreqisoutset", index: "oldreqisoutset", width: 50, label: "oldreqisoutset", sortable: true, align: "left", hidden: true},
					{name: "oldreqitemsetno", index: "oldreqitemsetno", width: 50, label: "oldreqitemsetno", sortable: true, align: "left", hidden: true},
					{name: "oldreqcusttypeitempk", index: "oldreqcusttypeitempk", width: 50, label: "oldreqcusttypeitempk", sortable: true, align: "left", hidden: true},
					{name: "oldreqdoorpk", index: "oldreqdoorpk", width: 50, label: "oldreqdoorpk", sortable: true, align: "left", hidden: true},
					{name: "oldreqalgorithm", index: "oldreqalgorithm", width: 50, label: "oldreqalgorithm", sortable: true, align: "left", hidden: true},
					{name: "oldreqcommitype", index: "oldreqcommitype", width: 50, label: "oldreqcommitype", sortable: true, align: "left", hidden: true},
					{name: "oldreqcommiperc", index: "oldreqcommiperc", width: 50, label: "oldreqcommiperc", sortable: true, align: "left", hidden: true},
					{name: "oldreqpurchasecost", index: "oldreqpurchasecost", width: 50, label: "oldreqpurchasecost", sortable: true, align: "left", hidden: true},
					{name: "oldreqrefcustcode", index: "oldreqrefcustcode", width: 50, label: "oldreqrefcustcode", sortable: true, align: "left", hidden: true},
					{name: "oldreqsupplpromitempk", index: "oldreqsupplpromitempk", width: 50, label: "oldreqsupplpromitempk", sortable: true, align: "left", hidden: true},
					{name: "oldreqcuttype", index: "oldreqcuttype", width: 50, label: "oldreqcuttype", sortable: true, align: "left", hidden: true},
					{name: "oldreqcuttypedescr", index: "oldreqcuttypedescr", width: 50, label: "oldreqcuttypedescr", sortable: true, align: "left", hidden: true},
					{name: "oldreqisgift", index: "oldreqisgift", width: 50, label: "oldreqisgift", sortable: true, align: "left", hidden: true},
					{name: "oldreqgiftpk", index: "oldreqgiftpk", width: 50, label: "oldreqgiftpk", sortable: true, align: "left", hidden: true},
					{name: "oldreqoldprojectcost", index: "oldreqoldprojectcost", width: 50, label: "oldreqoldprojectcost", sortable: true, align: "left", hidden: true},
					{name: "oldreqprojectcost", index: "oldreqprojectcost", width: 50, label: "oldreqprojectcost", sortable: true, align: "left", hidden: true},
					{name: "oldreqalgorithmper", index: "oldreqalgorithmper", width: 50, label: "oldreqalgorithmper", sortable: true, align: "left", hidden: true},
					{name: "oldreqalgorithmdeduct", index: "oldreqalgorithmdeduct", width: 50, label: "oldreqalgorithmdeduct", sortable: true, align: "left", hidden: true},
					{name: "oldreqcanmodiqty", index: "oldreqcanmodiqty", width: 50, label: "oldreqcanmodiqty", sortable: true, align: "left", hidden: true},
                ],
                gridComplete: function() {
                    
                }
            });
            
            defaultLoadFirstItemBatch();
        });
        
        function importingDescription(cellvalue, options, rowObject) {
            if (cellvalue === "Y") return "是";
            else if (cellvalue === "N") return "否";
        }
        
        function importingState(rowid, val) {
            if (val === "否") {
                return 'style="color:red;"';
            }
        }
        
        function defaultLoadFirstItemBatch() {
            var ibdno = document.getElementById("ibdno");
            
            if (ibdno.options.length > 1) {
                ibdno.value = ibdno.options[1].value;
                ibdno.dispatchEvent(new Event("change"));
            }
        }
        
        function goto_query() {
        
            if ($("#ibdno").val() == "") {
                $("#dataTable").jqGrid("clearGridData");
                return;
            }
            
            var postData = $("#page_form").jsonForm();
            
            $("#dataTable").jqGrid("setGridParam", {
                url: "${ctx}/admin/itemBatchDetail/itemChgImportingJqGrid",
                postData: postData
            }).trigger("reloadGrid");
        }

        function save() {
            var grid = $("#dataTable");
            var rows = grid.getRowData();
            var importingReplaceRows = [];
            var importingDeleteRows = [];
            var generatedItemChgs = [];
            
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].autoimport === "是") {
                    if (rows[i].opertype === "1")
                        importingReplaceRows.push(rows[i]);
                    else if (rows[i].opertype === "3")
                        importingDeleteRows.push(rows[i]);
                }
            }
            
            if (importingReplaceRows.length + importingDeleteRows.length < 1) {
                art.dialog({content: "没有可以自动导入的记录！"});
                return;
            }
            
            // 删除项
            for (var i = 0; i < importingDeleteRows.length; i++) {
                var itemChg = convertOldObjectToNew(importingDeleteRows[i]);
                
                itemChg.preqty = itemChg.qty;
                itemChg.qty = -itemChg.qty;
                itemChg.beflineamount = -itemChg.beflineamount;
                itemChg.lineamount = -itemChg.lineamount;
                itemChg.processcost = -itemChg.processcost;
                itemChg.tmplineamount = -myRound(itemChg.unitprice * itemChg.qty * (itemChg.markup / 100), 2);
                
                itemChg.reqpk = importingDeleteRows[i].oldreqpk;
                itemChg.refchgreqpk = importingDeleteRows[i].oldreqpk;
                itemChg.preplanareapk = importingDeleteRows[i].preplanareapk;
                itemChg.preplanareadescr = importingDeleteRows[i].preplanareadescr;
                itemChg.remarks = itemChg.itemremark;
                
                generatedItemChgs.push(itemChg);
            }
            
            // 替换项
            for (var i = 0; i < importingReplaceRows.length; i++) {
                 var itemChgToDel = convertOldObjectToNew(importingReplaceRows[i]);
                
                itemChgToDel.preqty = itemChgToDel.qty;
                itemChgToDel.qty = -itemChgToDel.qty;
                itemChgToDel.beflineamount = -itemChgToDel.beflineamount;
                itemChgToDel.lineamount = -itemChgToDel.lineamount;
                itemChgToDel.processcost = -itemChgToDel.processcost;
                itemChgToDel.tmplineamount = -myRound(itemChgToDel.unitprice * itemChgToDel.qty * (itemChgToDel.markup / 100), 2);
                
                itemChgToDel.reqpk = importingReplaceRows[i].oldreqpk;
                itemChgToDel.refchgreqpk = importingReplaceRows[i].oldreqpk;
                itemChgToDel.preplanareapk = importingReplaceRows[i].preplanareapk;
                itemChgToDel.preplanareadescr = importingReplaceRows[i].preplanareadescr;
                itemChgToDel.remarks = itemChgToDel.itemremark;
                
                generatedItemChgs.push(itemChgToDel);
                
                
                var itemChgToAdd = importingReplaceRows[i];
                
                itemChgToAdd.fixareapk = itemChgToAdd.oldreqfixareapk;
                itemChgToAdd.fixareadescr = itemChgToAdd.oldreqfixareadescr;
                itemChgToAdd.intprodpk = itemChgToAdd.oldreqintprodpk;
                itemChgToAdd.unitprice = itemChgToAdd.price;
                itemChgToAdd.remarks = itemChgToAdd.itemremark;
                itemChgToAdd.isservice = itemChgToAdd.oldreqisservice;
                itemChgToAdd.iscommi = itemChgToAdd.oldreqiscommi;
                itemChgToAdd.isoutset = itemChgToAdd.oldreqisoutset;
                itemChgToAdd.isoutsetdescr = itemChgToAdd.oldreqisoutsetdescr;
                itemChgToAdd.commitype = itemChgToAdd.oldreqcommitype;
                itemChgToAdd.commiperc = itemChgToAdd.oldreqcommiperc;
                itemChgToAdd.doorpk = itemChgToAdd.oldreqdoorpk;
                itemChgToAdd.refchgreqpk = itemChgToAdd.oldreqpk;
                itemChgToAdd.algorithmper = itemChgToAdd.oldreqalgorithmper;
                itemChgToAdd.algorithmdeduct = itemChgToAdd.oldreqalgorithmdeduct;
                
                itemChgToAdd.preqty = 0;
                
                generatedItemChgs.push(itemChgToAdd);
            }
            
            Global.Dialog.returnData = generatedItemChgs;
            Global.Dialog.closeDialog();
        }
        
        function convertOldObjectToNew(obj) {
            var newObj = {};
            var prefix = "oldreq";
            
            for (var prop in obj) {
                if (prop.startsWith(prefix)) {                
                    newObj[prop.substring(prefix.length)] = obj[prop];
                }
            }
            
            return newObj;
        }

    </script>
</head>
<body>
<div class="body-box-list">
    <div id="buttonView" class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system " id="saveBtn" onclick="save()">确定</button>
                <button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" name="jsonString" value=""/>
            <input type="hidden" id="excludedReqPks" name="excludedReqPks" value="${itemChg.excludedReqPks}"/>
            <ul class="ul-form">
                <li>
                    <label>批次编号</label>
                    <house:dict id="ibdno" dictCode="" style="width:320px;"
                                sql="
                                    select rtrim(a.No) SQL_VALUE_KEY,
                                        rtrim(a.No) + ' ' + a.Remarks + ' ' + b.ZWXM + ' '
                                                + convert(varchar(10), a.Date, 23) SQL_LABEL_KEY
                                    from tItemBatchHeader a
                                    left join tCZYBM b on b.CZYBH = a.LastUpdatedBy
                                    where a.BatchType = '1'
                                        and a.status='1'
                                        and a.Expired = 'F'
                                        and a.custCode='${itemChg.custCode}'
                                        and a.itemType1='${itemChg.itemType1}'
                                    order by date desc"
                                onchange="goto_query()"/>
                </li>
                <li>
                    <label>操作类型</label>
                    <house:xtdm id="operType" dictCode="IBDTOPERTYPE" onchange="goto_query()"></house:xtdm>
                </li>
                <li class="search-group">
                    <input type="checkbox" id="expired" name="expired" value="F" onclick="hideExpired(this)" checked/>
                    <p>隐藏过期</p>
                    <button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
                    <button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
                </li>
            </ul>
        </form>
    </div>

    <div class="pageContent">
        <div id="content-list">
            <table id="dataTable"></table>
        </div>
    </div>
</div>
</body>
</html>
