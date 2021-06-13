<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.web.login.UserContext"%>
<%@ page import="com.house.framework.commons.conf.CommonConstant"%>

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <style>
        .form-search .ul-form li label {
            width: 60px
        }
    </style>

    <script type="text/javascript">
    
        // 传'0'时匹配普通材料；
        // 传'1'时匹配套餐材料
        var matchType = "0";
        
        function setMatchTypeThenQuery(type) {
            matchType = type || matchType;
            
            goto_query();
        }

        $(function() {
        
            var itemType1 = "${itemChg.itemType1}".trim();
            var id_detailW = window.parent.document.getElementById("id_detail").style.width.substring(0, 4);
            $("#clpc").css('width', id_detailW);

            Global.JqGrid.initJqGrid("itemBatchDetaildataTable", {
                rowNum: 100000,
                height: 220,
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
                    {name: "oldreqisoutsetdescr", index: "oldreqisoutsetdescr", width: 80, label: "原套外材料", sortable: true, align: "left"},
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
                ondblClickRow: function (rowid, iRow, iCol, e) {
                    
                    insertToChgDetailTable($(this).getRowData(rowid));

                },
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
        
        function insertToChgDetailTable(row) {
            
            if (matchType === "1"
                    && (row.opertype === "1" || row.opertype === "2")
                    && !row.custtypeitempk) {
            
                art.dialog({content: "未匹配到相应套餐材料，无法导入！"});
                return;
            }
        
            var parentDocument = window.parent.document;
            
            var fixAreaPk           = parentDocument.getElementById("fixAreaPk").value,
                fixAreaDescr        = parentDocument.getElementById("fixAreaDescr").value,
                intProdPk           = parentDocument.getElementById("intProdPk").value,
                intProdDescr        = parentDocument.getElementById("intProdDescr").value,
                prePlanAreaPk       = parentDocument.getElementById("prePlanAreaPK").value,
                prePlanAreaDescr    = parentDocument.getElementById("prePlanAreaDescr").value,
                chgDetailTable      = parentDocument.getElementById("itemChgDetailDataTable"),
                rowNo               = parentDocument.getElementById("rowNo");
            
            if (row.opertype === "1") {             // 替换
            
                var itemChgToDel = convertOldObjectToNew(row);
                
                itemChgToDel.preqty = itemChgToDel.qty;
                itemChgToDel.qty = -itemChgToDel.qty;
                itemChgToDel.beflineamount = -itemChgToDel.beflineamount;
                itemChgToDel.lineamount = -itemChgToDel.lineamount;
                itemChgToDel.processcost = -itemChgToDel.processcost;
                itemChgToDel.tmplineamount = -myRound(itemChgToDel.unitprice * itemChgToDel.qty * (itemChgToDel.markup / 100), 2);
                
                itemChgToDel.reqpk = row.oldreqpk;
                itemChgToDel.refchgreqpk = row.oldreqpk;
                itemChgToDel.preplanareapk = row.preplanareapk;
                itemChgToDel.preplanareadescr = row.preplanareadescr;
                itemChgToDel.remarks = itemChgToDel.itemremark;
                
                $(chgDetailTable).addRowData(rowNo.value++, itemChgToDel, "last");
                
                var itemChgToAdd = row;
                
                itemChgToAdd.fixareapk = itemChgToAdd.oldreqfixareapk;
                itemChgToAdd.fixareadescr = itemChgToAdd.oldreqfixareadescr;
                itemChgToAdd.intprodpk = itemChgToAdd.oldreqintprodpk;
                itemChgToAdd.unitprice = itemChgToAdd.price;
                itemChgToAdd.remarks = itemChgToAdd.itemermark;
                itemChgToAdd.isservice = itemChgToAdd.oldreqisservice;
                itemChgToAdd.iscommi = itemChgToAdd.oldreqiscommi;
                itemChgToAdd.commitype = itemChgToAdd.oldreqcommitype;
                itemChgToAdd.commiperc = itemChgToAdd.oldreqcommiperc;
                itemChgToAdd.doorpk = itemChgToAdd.oldreqdoorpk;
                itemChgToAdd.refchgreqpk = itemChgToAdd.oldreqpk;
                itemChgToAdd.algorithmper = itemChgToAdd.oldreqalgorithmper;
                itemChgToAdd.algorithmdeduct = itemChgToAdd.oldreqalgorithmdeduct;
                
                if (matchType === "0") {
                    itemChgToAdd.isoutset = "1";
                    itemChgToAdd.isoutsetdescr = "是";
                } else if (matchType === "1") {
                    itemChgToAdd.isoutset = "0";
                    itemChgToAdd.isoutsetdescr = "否";
                }
                
                itemChgToAdd.preqty = 0;
                
                $(chgDetailTable).addRowData(rowNo.value++, itemChgToAdd, "last");
                
            } else if (row.opertype === "2") {      // 增加
            
	            if (!fixAreaDescr) {
	                art.dialog({content: "请先选择区域!"});
	                return;
	            }
	            
                row.fixareapk = fixAreaPk;
                row.fixareadescr = fixAreaDescr;
                row.intprodpk = intProdPk;
                row.intproddescr = intProdDescr;
                row.preplanareapk = prePlanAreaPk;
                row.preplanareadescr = prePlanAreaDescr;
                
                row.unitprice = row.price;
                
                if (matchType === "0") {
	                row.isoutset = "1";
	                row.isoutsetdescr = "是";
                } else if (matchType === "1") {
                    row.isoutset = "0";
                    row.isoutsetdescr = "否";
                }

                row.algorithmdeduct = 0;
                row.algorithmper = 1.0;
                
                $(chgDetailTable).addRowData(rowNo.value++, row, "last");
                
            } else if (row.opertype === "3") {      // 删除
            
                var itemChgToDel = convertOldObjectToNew(row);
            
                itemChgToDel.preqty = itemChgToDel.qty;
                itemChgToDel.qty = -itemChgToDel.qty;
                itemChgToDel.beflineamount = -itemChgToDel.beflineamount;
                itemChgToDel.lineamount = -itemChgToDel.lineamount;
                itemChgToDel.processcost = -itemChgToDel.processcost;
                itemChgToDel.tmplineamount = -myRound(itemChgToDel.unitprice * itemChgToDel.qty * (itemChgToDel.markup / 100), 2);
                
                itemChgToDel.reqpk = row.oldreqpk;
                itemChgToDel.refchgreqpk = row.oldreqpk;
                itemChgToDel.preplanareapk = row.preplanareapk;
                itemChgToDel.preplanareadescr = row.preplanareadescr;
                itemChgToDel.remarks = itemChgToDel.itemremark;
                
                $(chgDetailTable).addRowData(rowNo.value++, itemChgToDel, "last");
            }
            
            goto_query();
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
        
        function defaultLoadFirstItemBatch() {
            var ibdno = document.getElementById("ibdno");
            
            if (ibdno.options.length > 1) {
                ibdno.value = ibdno.options[1].value;
                ibdno.dispatchEvent(new Event("change"));
            }
        }
        
        function goto_query() {
        
            if ($("#ibdno").val() == "") {
                $("#itemBatchDetaildataTable").jqGrid("clearGridData");
                return;
            }
            
            var postData = $("#page_form").jsonForm();
            postData.matchType = matchType;
            
            var chgDetailTable = $(window.parent.document.getElementById("itemChgDetailDataTable"));
            var reqPks = chgDetailTable.getCol("reqpk");
            
            [].push.apply(reqPks, postData.excludedReqPks.split(","));
            postData.excludedReqPks = reqPks.join(",");
            
            $("#itemBatchDetaildataTable").jqGrid("setGridParam", {
                url: "${ctx}/admin/itemBatchDetail/itemChgImportingJqGrid",
                postData: postData
            }).trigger("reloadGrid");
        }

    </script>
</head>
<body>
<div>
    <div id="clpc" style="float: right;">
        <div>
            <div id="tab1" class="tab_content" style="display: block;">
                <div class="edit-form">
                    <form role="form" id="page_form" class="form-search" action="" method="post">
                        <input type="hidden" id="excludedReqPks" name="excludedReqPks" value="${itemChg.excludedReqPks}"/>
                        <ul class="ul-form">
			                <li>
			                    <label>批次编号</label>
			                    <house:dict id="ibdno" dictCode="" style="width:220px;"
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
			                    <label>空间</label>
			                    <house:dict id="prePlanAreaPk" dictCode="" style="width:100px;"
			                                sql="select a.Descr SQL_LABEL_KEY, a.PK SQL_VALUE_KEY
                                                 from tPrePlanArea a
                                                 where a.Expired = 'F' and a.CustCode = '${itemChg.custCode}'"
                                            onchange="goto_query()"></house:dict>
			                </li>
			                <li>
			                    <label>材料分类</label>
			                    <house:dict id="itemType12" dictCode="" style="width:100px;"
			                                sql="select Code SQL_VALUE_KEY, Descr SQL_LABEL_KEY
												 from tItemType12
												 where ItemType1 = '${itemChg.itemType1}'
												 order by DispSeq"
			                                onchange="goto_query()"></house:dict>
			                </li>
			                <li>
			                    <label>操作类型</label>
			                    <house:xtdm id="operType" dictCode="IBDTOPERTYPE" onchange="goto_query()" style="width:80px;"></house:xtdm>
			                </li>
			                <li>
			                    <label>自动导入</label>
			                    <select name="autoImport" onchange="goto_query()" style="width:80px;">
			                        <option value="">请选择</option>
			                        <option value="Y">是</option>
			                        <option value="N" selected>否</option>
			                    </select>
			                </li>
		                <li class="search-group">
		                    <input type="checkbox" id="expired" name="expired" value="F" onclick="hideExpired(this);goto_query()" checked/>
		                    <p>隐藏过期</p>
		                </li>
                        </ul>
                    </form>
                </div>
            </div>
            <div class="clear_float"></div>

            <div class="pageContent">
                <div id="content-list">
                    <table id="itemBatchDetaildataTable"></table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
