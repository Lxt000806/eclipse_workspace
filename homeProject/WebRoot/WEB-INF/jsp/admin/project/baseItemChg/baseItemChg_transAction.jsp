<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  	<title>已有项变动列表</title>
  	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  	<META HTTP-EQUIV="expires" CONTENT="0"/>
  	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  	<%@ include file="/commons/jsp/common.jsp" %>
  	<script type="text/javascript">
    var reqpk;
    var showOutSet = "1";
    /**初始化表格*/
    $(function () {
    	if (top.$("#iframe_baseItemChg_Add")[0]) {
        	var topFrame = "#iframe_baseItemChg_Add";
      	} else if (top.$("#iframe_baseItemChgConfirm")[0]) {
        	var topFrame = "#iframe_baseItemChgConfirm";
      	} else {
        	var topFrame = "#iframe_baseItemChgUpdate";
      	}
      	reqpk = top.$(topFrame)[0].contentWindow.getReqPk().join(",");
      	//初始化表格
		if("${baseItemChg.custTypeType}"=="1"){
			$("#showOutSet").attr("check","true");
			showOutSet = "1"
		}else{
			showOutSet = "0"
		}
      	
		$("#fixAreaDescr").unbind("keydown").on("keyup",function(e){
			if(e.keyCode == 13){
				changeShow();
			}
	  	});
		
		$("#baseItemDescr").unbind("keydown").on("keyup",function(e){
			if(e.keyCode == 13){
				changeShow();
			}
	  	});
      	
      	Global.JqGrid.initJqGrid("dataTable", {
        	url: "${ctx}/admin/baseItemChg/goTransActionJqGrid?custCode=${baseItemChg.custCode}&unSelected=" + reqpk + "&showOutSet=" + showOutSet,
        	height: $(document).height() - $("#content-list").offset().top - 62,
        	multiselect: true,
        	rowNum: 10000,
        	footerrow: false,
			colModel : [
			    {name: "pk", index: "pk", width: 50, label: "PK", sortable: true, align: "left", hidden: true},
			    {name: "fixareaseq", index: "fixareaseq", width: 50, label: "fixareaseq", sortable: true, align: "left", hidden: true},
		  		{name: "custcode", index: "custcode", width: 85, label: "客户编号", sortable: true, align: "left", hidden: true},
		  		{name: "baseitemcode", index: "baseitemcode", width: 85, label: "材料编号", sortable: true, align: "left", hidden: true},
		  		{name: "customerdescr", index: "customerdescr", width: 85, label: "客户名称", sortable: true, align: "left", hidden: true},
		  		{name: "address", index: "address", width: 130, label: "楼盘", sortable: true, align: "left", hidden: true},
		  		{name: "fixareadescr", index: "fixareadescr", width: 150, label: "区域名称", sortable: true, align: "left", hidden: true},
		  		{name: "fixareadescr2", index: "fixareadescr2", width: 150, label: "区域名称2", sortable: true, align: "left", hidden: true},
		  		{name: "fixareapk", index: "fixareapk", width: 90, label: "区域编号", sortable: true, align: "left", hidden: true},
		  		{name: "baseitemdescr", index: "baseitemdescr", width: 200, label: "基装项目名称", sortable: true, align: "left"},
		  		{name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right"},
		  		{name: "uom", index: "uom", width: 50, label: "单位", sortable: true, align: "left", hidden: true},
		  		{name: "cost", index: "cost", width: 60, label: "成本", sortable: true, align: "right",hidden: true},
		  		{name: "unitprice", index: "unitprice", width: 80, label: "人工单价", sortable: true, align: "right"},
		  		{name: "material", index: "material", width: 80, label: "材料单价", sortable: true, align: "right"},
		  		{name: "lineamount", index: "lineamount", width: 90, label: "总价", sortable: true, align: "right"},
		  		{name: "isoutsetdescr", index: "isoutsetdescr", width: 85, label: "套餐外项目", sortable: true, align: "left"},
		  		{name: "isoutset", index: "isoutset", width: 85, label: "套餐外项目", sortable: true, align: "left",hidden:true},
		  		{name: "remark", index: "remark", width: 200, label: "备注", sortable: true, align: "left"},
		  		{name: "lastupdate", index: "lastupdate", width: 120, label: "修改时间", sortable: true, align: "left", formatter: formatTime},
		  		{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left"},
		  		{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
		  		{name: "actionlog", index: "actionlog", width: 60, label: "操作", sortable: true, align: "left"},
		  		{name: "category", index: "category", width: 80, label: "项目类型", sortable: true, align: "left", hidden: true},
		  		{name: "prjctrltype", index: "prjctrltype", width: 80, label: "发包方式", sortable: true, align: "left", hidden: true},
		  		{name: "offerctrl", index: "offerctrl", width: 80, label: "人工发包", sortable: true, align: "left", hidden: true},
		  		{name: "materialctrl", index: "materialctrl", width: 80, label: "材料发包", sortable: true, align: "left", hidden: true},
		  		{name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left", hidden: true},
		  		{name: "baseitemsetno", index: "baseitemsetno", width: 68, label: "套餐包", sortable: true, align: "left"},
	            {name: "ismainitem", index: "ismainitem", width: 68, label: "主项目", sortable: true, align: "left", hidden: true},
	            {name: "ismainitemdescr", index: "ismainitemdescr", width: 68, label: "主项目", sortable: true, align: "left"},
                {name: "chgempcodes", index: "chgempcodes", width: 90, label: "增减干系人", sortable: true, align: "left", hidden: true},
	            {name: "isaddallinfo", index: "isaddallinfo", width: 90, label: "非独立销售", sortable: true, align: "left", hidden: true},
	            {name: "basealgorithmdescr", index: "basealgorithmdescr", width: 90, label: "算法", sortable: true, align: "left", hidden: true},
	            {name: "basealgorithm", index: "basealgorithm", width: 90, label: "算法", sortable: true, align: "left", hidden: true},
	            {name: "preplanareadescr", index: "preplanareadescr", width: 90, label: "空间", sortable: true, align: "left", hidden: true},
			],
			grouping : true,
	        groupingView: {
	            groupField: ["fixareadescr2"],
	            groupColumnShow: [false],
	            groupText: ["<b title=\"{0}({1})\">{0}({1})</b>"],
	            groupOrder: ["asc"],
	            groupSummary: [false],
	            groupCollapse: false
	        }
      	});
      	if ("${baseItemChg.costRight}".trim() == "1") {
        	$("#dataTable").setGridParam().showCol("cost").trigger("reloadGrid");
      	}
      	//保存
      	$("#saveBtn").on("click", function () {
        	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
        	if (ids.length == 0) {
          		Global.Dialog.infoDialog("请选择要增减的材料！");
          		return;
        	}
            var returnData = {};
            var selectRows = [];
            var empCodes = "${baseItemChg.empCode}";
            var chgEmpCodesArray = empCodes.split(",");
            var reqEmpListArray = [], reqEmpCodesArray = [], temEmpListArray = [];
            //合并的数组。将1，2数组合并，通过1数组和合并数组的长度比较和1数组长度等于2数组的来判断1是否完全等于2数组。
            var combinedArray = [];
            var isAddAblely = true;
            var errorNameList = "";
            $.each(ids, function (k, id) {
                var row = $("#dataTable").jqGrid("getRowData", id);
                row.reqpk = row.pk;
                row.preqty = row.qty;
                row.qty = 0;
                row.lineamount = 0;
                selectRows.push(row);
                reqEmpListArray = row.chgempcodes.split(",");
                /*
             	如果本单已设置增减干系人，只有需求增减单干系人相同时才可添加。
				如果未设置，需求单有对应增减干系人，须带出增减干系人。
				add by zb on 20191225
				已有项新增，不需要判断独立销售干系人和之前是否一致 modify by zb on 20200420
                */
                /*if ("" == empCodes) {
                    reqEmpCodesArray =reqEmpListArray[0].split("/");
                    if (k==0) {
                		temEmpListArray = reqEmpListArray;
                        chgEmpCodesArray = reqEmpCodesArray;
                        returnData.isaddallinfo = row.isaddallinfo;
                    }
                    combinedArray = unique(chgEmpCodesArray.concat(reqEmpCodesArray));
                    if (combinedArray.length != chgEmpCodesArray.length || 
                            chgEmpCodesArray.length != reqEmpCodesArray.length) {
                        isAddAblely = false;
                        errorNameList += (errorNameList!=""?",<br/>":"") + row.fixareadescr2.replace(/\s/g,"") +
                    		"： "+row.baseitemdescr;
                    }
                } else {
                    if ("" == row.chgempcodes) {
                        isAddAblely = false;
                        errorNameList += (errorNameList!=""?",<br/>":"")+row.fixareadescr2.replace(/\s/g,"")+"： "+row.baseitemdescr;
                    } else {
                        reqEmpCodesArray = reqEmpListArray[0].split("/");
                    	// 利用se6的set去除数组重复项 参考：https://segmentfault.com/a/1190000016418021
                     	// CS不支持 [...new Set(chgEmpCodesArray.concat(reqEmpCodesArray))];
                        combinedArray = unique(chgEmpCodesArray.concat(reqEmpCodesArray));
                        if (combinedArray.length != chgEmpCodesArray.length || 
                            chgEmpCodesArray.length != reqEmpCodesArray.length) {
                            isAddAblely = false;
                            errorNameList += (errorNameList!=""?",<br/>":"")+row.fixareadescr2.replace(/\s/g,"")+"： "+row.baseitemdescr;
                        }
                    }
                }*/
            });
            /*if (!isAddAblely) {
                art.dialog({
                    content: (empCodes != ""?"你当前选择的干系人与已设置的干系人不一致。":"")+
                        "<br/>错误数据：<br/>"+
                        errorNameList,
                    width: 300,
                });
                return;
            }
			// 如果未设置，需求单有对应增减干系人，须带出增减干系人
            if ("" == empCodes && temEmpListArray[1]) {
                returnData.reqEmpCodesArray = chgEmpCodesArray;
                returnData.reqEmpNamesArray = temEmpListArray[1].split("/");
            }*/
            returnData.selectRows = selectRows;
        	Global.Dialog.returnData = returnData;
        	closeWin();
      	});

      	//全选
      	$("#selectallBtn").on("click", function () {
        	Global.JqGrid.jqGridSelectAll("dataTable", true);
      	});

      	//不选
      	$("#unselectallBtn").on("click", function () {
        	Global.JqGrid.jqGridSelectAll("dataTable", false);
      	});
    });

    //SE5 去重方法 。cs版本太老了：(KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36
    function unique(arr){            
        for(var i=0; i<arr.length; i++){
            for(var j=i+1; j<arr.length; j++){
                if(arr[i]==arr[j]){         //第一个等同于第二个，splice方法删除第二个
                    arr.splice(j,1);
                    j--;
                }
            }
        }
        return arr;
    }

    function changeShow(obj) {
    	console.log($("#showOutSet").is(":checked"));
      	if ($("#showOutSet").is(":checked")) {
        	$("#showOutSet").val("1");
      	} else {
        	$("#showOutSet").val("");
      	}
      	$("#dataTable").jqGrid("setGridParam", {
        	url: "${ctx}/admin/baseItemChg/goTransActionJqGrid?custCode=${baseItemChg.custCode}&unSelected=" 
        			+ reqpk + "&showOutSet=" + $("#showOutSet").val()+"&fixAreaDescr="+$("#fixAreaDescr").val()+
        			"&baseItemDescr="+$("#baseItemDescr").val(),                                          
        	page: 1,
      	}).trigger("reloadGrid");
   	}
    
    function goto_query(){
    	console.log(13);
    }
  	</script>
</head>
<body>
<div class="body-box-list">
  	<div class="panel panel-system">
    	<div class="panel-body">
      		<div class="btn-group-xs">
        		<button type="button" class="btn btn-system" id="saveBtn">保存</button>
        		<button type="button" class="btn btn-system" id="selectallBtn">全选</button>
        		<button type="button" class="btn btn-system" id="unselectallBtn">不选</button>
        		<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
      		</div>
    	</div>
  	</div>
  	<div class="query-form">
    	<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
      	<ul class="ul-form">
        	<li>
				<label>装修区域</label>
				<input type="text" id="fixAreaDescr" name="fixAreaDescr"/>
			</li>
			<li>
				<label>基础项目名称</label>
				<input type="text" id="baseItemDescr" name="baseItemDescr"/>
			</li>
		<c:if test="${baseItemChg.custTypeType!='1'}">
        	<li style = "width:135px">
          		<input id="showOutSet" name="showOutSet" type="checkbox" onclick="changeShow('showOutSet')" style="margin-left:10px"/>显示套餐内项目&nbsp
        	</li>
	  	</c:if>
        	<li class="search-group">
				<button type="button" class="btn btn-sm btn-system" onclick="changeShow('showOutSet')">查询</button>
			</li>
      	</ul>
    	</form>
  	</div>

  	<div id="content-list">
    	<table id="dataTable"></table>

  	</div>
</div>
</body>
</html>


