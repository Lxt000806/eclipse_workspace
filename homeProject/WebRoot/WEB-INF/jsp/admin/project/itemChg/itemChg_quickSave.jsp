<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>材料增减--快速新增</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp" %>
  <script src="${resourceRoot}/pub/component_prePlanArea.js?v=${v}" type="text/javascript"></script>
  <script type="text/javascript">
    var fixAreaPk = 0;
    var intProdPk = 0;
    var leftOffset;
    var isAddFixArea = false;
    function saveData() {
      //var ischecked=$("#clearPrice").is(':checked');
      //var ids = $("#itemChgDetailDataTable").getDataIDs();
// 			var gridDatas = [];
// 			$.each(ids,function(k,id){
//     		var row = $("#itemChgDetailDataTable").jqGrid('getRowData', id);
//     		row.isoutset="1"
//     		if('${itemChg.isOutSet}'=='2') row.isoutsetdescr='是';

//     		if(ischecked){
// 	    		row.unitprice=0;
// 	    		row.beflineamount=0;
// 	    		row.tmplineamount=0;
// 	    		row.lineamount=row.processcost;
// 	    		row.isoutsetdescr="否";
// 	    		row.isoutset="0";
//     		}

//     		gridDatas.push(row);

//     		});
      Global.Dialog.returnData = $("#itemChgDetailDataTable").jqGrid("getRowData");
    }
    function doSave() {
      if ($("#itemChgDetailDataTable").jqGrid('getGridParam', 'records') > 0)  saveData();
      closeWin();
    }
    function closeAddUi() {
      if ($("#itemChgDetailDataTable").jqGrid('getGridParam', 'records') > 0) {
        art.dialog({
          content: "是否保存被更改的数据?",
          ok: function () {
            saveData();
            closeWin();
          },
          cancel: function () {
            closeWin();
          }
        });
        return;
      }
      closeWin();
    }
    function reloadIntProd() {
      intProdPk = 0;
      $("#intProdPk").val(intProdPk);
      $("#intProdDescr").val("");
      $("#intProdDataTable").jqGrid("setGridParam", {
        postData: {
          'fixAreaPk': fixAreaPk,
          'isCupboard': '${itemChg.isCupboard}'
        }, page: 1
      }).trigger("reloadGrid");
    }
    
    function clearPriceClick(){
    	search();
    	//加载套餐包下拉菜单 
    	if ($("#clearPrice").is(':checked')){
    		document.getElementById("mainItemSetFrame").contentWindow.loadItemSet("0");
        }else{
        	document.getElementById("mainItemSetFrame").contentWindow.loadItemSet("1");
        }
    }
    
    function search() {
    	var ret = selectDataTableRow();
    	var prePlanAreaPK = null;
    	if(ret){
    		prePlanAreaPK = ret.PrePlanAreaPK;
    	}
        if ($("#clearPrice").is(':checked')){
            document.getElementById("iframe").contentWindow.document.getElementById("listFrame_q").src = "${ctx}/admin/custTypeItem/goItemSelect?code=" + $("#code").val() + "&descr=" + $("#itemDescr").val() + "&itemType1=${itemChg.itemType1}&custType=${itemChg.custType}&custCode=${itemChg.custCode}&prePlanAreaPk="+prePlanAreaPK;
            document.getElementById("clpcIframe").contentWindow.setMatchTypeThenQuery("1");
        }else{
            document.getElementById("iframe").contentWindow.document.getElementById("listFrame_q").src = "${ctx}/admin/item/goItemSelect?code=" + $("#code").val() + "&descr=" + $("#itemDescr").val() + "&itemType1=${itemChg.itemType1}&custCode=${itemChg.custCode}&custType=${itemChg.custType}";
            document.getElementById("clpcIframe").contentWindow.setMatchTypeThenQuery("0");
        }
        document.getElementById("itemSetFrame").contentWindow.goto_query1($("#code").val(),$("#itemDescr").val(),this);
    }
    function addIntProd() {
      Global.Dialog.showDialog("intProdAdd", {
        title: "集成成品--增加",
        url: "${ctx}/admin/intProd/goSave?custCode=${itemChg.custCode}&itemType1=${itemChg.itemType1}&isService=${itemChg.isService}&isCupboard=${itemChg.isCupboard}&fixAreaPk=" + fixAreaPk,
        height: 400,
        width: 470,
        returnFun: reloadIntProd
      }, reloadIntProd);
    }
    function updateIntProd() {
      var ret = selectDataTableRow('intProdDataTable');
      if (ret) {
        Global.Dialog.showDialog("intProdUpdate", {
          title: "集成成品--编辑",
          url: "${ctx}/admin/intProd/goUpdate?custCode=${itemChg.custCode}&itemType1=${itemChg.itemType1}&isService=${itemChg.isService}&isCupboard=${itemChg.isCupboard}&descr=" + ret.Descr + "&fixAreaPk=" + fixAreaPk + "&pk=" + ret.PK,
          height: 400,
          width: 470,
          returnFun: reloadIntProd
        }, reloadIntProd);
      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }

    }
    function goto_query(table, param) {
      fixAreaPk = 0;
      $("#fixAreaPk").val(fixAreaPk);
      $("#fixAreaDescr").val("");
      $("#" + table).jqGrid("setGridParam", {postData: param, page: 1}).trigger("reloadGrid");
    }
    function update(e) {
      var ret = selectDataTableRow();
      if (ret) {
        var descr = e.value;
        if (descr == '水电项目' || descr == '土建项目' || descr == '安装项目' || descr == '综合项目') {
          art.dialog({
            content: "不能在材料预算中将装修区域修改为" + descr
          });
          return;
        }
        $.ajax({
          url: '${ctx}/admin/fixArea/updateFixArea?custCode=${itemChg.custCode}&itemType1=${itemChg.itemType1}&isService=${itemChg.isService}&descr=' + descr + '&pk=' + ret.PK,
          cache: false,
          success: function (obj) {
            if (obj) {
              art.dialog({
                content: "装修区域已存在！"
              });
            } else {
              e.value = '';
              return goto_query("dataTable", {"descr": ''});
            }

          }
        });

      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }

    }
    function add(e) {
      var descr = e.value;
      if (!descr) return;
      if (descr == '水电项目' || descr == '土建项目' || descr == '安装项目' || descr == '综合项目') {
        art.dialog({
          content: "不能在材料预算中新增" + descr + "区域"
        });
        return;
      }
      var datas={custCode:"${itemChg.custCode}",itemType1:"${itemChg.itemType1}",isService:"${itemChg.isService}",descr:descr};
      var url="${ctx}/admin/fixArea/doSave";
      if("${itemChg.mainTempNo}"&&"${itemChg.itemType1}"==="ZC"){
	      Global.Dialog.showDialog("select_prePlanArea",{
			  title:"选择空间",
			  url:"${ctx}/admin/prePlanArea/goCode?custCode=${itemChg.custCode}",
			  height:500,
			  width:550,
			  returnFun: function(data){
				  if(data){
				  	 datas.prePlanAreaPK=data.pk;
				  }
				  isAddFixArea=true;
				  doSavefixArea(url,datas,e);	
			  },
			 
		  });  
      }else{
      	  isAddFixArea=true;
      	  doSavefixArea(url,datas,e);
      } 
    }
    function doSavefixArea(url,datas,e){
		$.ajax({
			url:url,
			type: 'post',
			data: datas,
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
					});
					e.value = '';
					return goto_query("dataTable", {"descr": ''});
		    	}else{
					$("#_form_token_uniq_id").val(obj.token.token);
					art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
		    }
	  });
    }
    function insert(e) {
      var ret = selectDataTableRow();
      if (ret) {
        var descr = e.value;
        if (descr == '水电项目' || descr == '土建项目' || descr == '安装项目' || descr == '综合项目') {
          art.dialog({
            content: "不能在材料预算中插入" + descr
          });
          return;
        }
        var datas={custCode:"${itemChg.custCode}",itemType1:"${itemChg.itemType1}",isService:"${itemChg.isService}",descr:descr,dispSeq:ret.DispSeq};
        var url="${ctx}/admin/fixArea/doInsertFixArea";
        if("${itemChg.mainTempNo}"&&"${itemChg.itemType1}"==="ZC"){
       		 Global.Dialog.showDialog("select_prePlanArea",{
				  title:"选择空间",
				  url:"${ctx}/admin/prePlanArea/goCode?custCode=${itemChg.custCode}",
				  height:500,
				  width:550,
				  returnFun: function(data){
					  if(data){
					  	 datas.prePlanAreaPK=data.pk;
					  }
					   doSavefixArea(url,datas,e);
				  }, 
			   });  
        }else{
        	 doSavefixArea(url,datas,e);
        }
      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }
    }
    function delete_() {
      var ret = selectDataTableRow();
      if (ret) {
        var itemType1 = '${itemChg.itemType1}'.trim();
        var descr = ret.Descr;
        var strmsg, warn;
        if (itemType1 == 'ZC') strmsg = "删除该区域将会删除该区域下的主材预算";
        if (itemType1 == 'RZ') strmsg = "删除该区域将会删除该区域下的软装预算";
        if (itemType1 == 'JC') strmsg = "删除该区域将会删除该区域下的集成成品、集成橱柜预算";
        warn = "确定要删除[" + descr + "]装修区域?<br/>" + strmsg;
        art.dialog({
          content: warn,
          ok: function () {
            $.ajax({
              url: '${ctx}/admin/fixArea/deleteFixArea?pk=' + ret.PK,
              cache: false,
              success: function (obj) {
                if (obj && obj.ret != 1) {
                  art.dialog({
                    content: obj.errmsg
                  });
                  return;
                }
                return goto_query("dataTable", {"descr": ''});

              }
            });
          },
          cancel: function () {

          }
        });
      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }
    }
    function deleteBtn(v, x, r) {
    	
      return "<button type='button' class='btn btn-system btn-xs' onclick='deleteRow(" + x.rowId + ");'>删除</button>";
    }
    function deleteRow(rowId) {
    	console.log(rowId);
      var nextId = $("#itemChgDetailDataTable tr[id=" + rowId + "]").next().attr("id");
      var preId = $("#itemChgDetailDataTable tr[id=" + rowId + "]").prev().attr("id");
      $('#itemChgDetailDataTable').delRowData(rowId);
      if ($("#itemChgDetailDataTable").jqGrid('getGridParam', 'records') > 0) {
        if (nextId) {
          relocate(nextId, 'itemChgDetailDataTable');
          $("#itemChgDetailDataTable tr[id=" + nextId + "] button").focus();
        }
        else {
          relocate(preId, 'itemChgDetailDataTable');
          $("#itemChgDetailDataTable tr[id=" + preId + "] button").focus();
        }
      }

    }
    //tab分页
    $(document).ready(function () {
      $('input', '#page_form').unbind('keydown');
      if ('${itemChg.itemType1}'.trim() == 'JC') {
        $("#intProdDiv").show();
        $("#copy").show();
        $("#id_detail").css('width', '1040px');
        if (top.$("#iframe_itemChg_Add")[0]) {
          var topFrame = "#iframe_itemChg_Add";
        } else if (top.$("#iframe_itemChgConfirm")[0]) {
          var topFrame = "#iframe_itemChgConfirm";
        } else {
          var topFrame = "#iframe_itemChgUpdate";
        }
        setTimeout(function () {
          var arr = $(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable")).jqGrid("getRowData");
          $.each(arr, function (id, opt) {
            $(document.getElementById("itemIframe").contentWindow.document.getElementById("itemCopyDataTable")).addRowData(id, opt, "last");
          })
        }, 1000);
      }
      if ('${itemChg.itemType1}'.trim() == 'RZ') {
        $("#itemSet").show();
      }
      if ('${itemChg.itemType1}'.trim() == 'ZC') {
          $("#mainItemSet").show();  
      }

      Global.JqGrid.initJqGrid("dataTable", {
        url: "${ctx}/admin/fixArea/goJqGrid?custCode=${itemChg.custCode}&itemType1=${itemChg.itemType1}&isService=${itemChg.isService}",
        height: 210,
        rownumbers: false,
        rowNum: 10000,
        styleUI: 'Bootstrap',
        colModel: [
          {name: 'Descr', index: 'Descr', width: 155, label: '<h7 style = "float:left;margin-top:3px">装修区域</h7><input type="text" style="width:80px;height:22px;float:right " placeholder="按回车时搜索" class="form-control" onkeydown="enterClick(window.event,\'fixAreaSearch\',this)">', sortable: false, align: "left"},
          {name: 'PK', index: 'PK', width: 100, label: '区域编号', sortable: false, align: "center", hidden: true},
          {name: 'DispSeq', index: 'DispSeq', width: 100, label: '序号', sortable: false, align: "center", hidden: true},
          {name: "PrePlanAreaPK", index: "PrePlanAreaPK", width: 100, label: "空间pk", sortable: true, align: "left", hidden: true},
		  {name: "preplanareadescr", index: "preplanareadescr", width: 100, label: "名称", sortable: true, align: "left", hidden: true}   
        ],
        gridComplete: function () {
          if ('${itemChg.isOutSet}' == '2') $("#clearPriceTd").css("display", "block");
          if ('${itemChg.fixAreaPk}') {
            var rowData = $("#dataTable").jqGrid('getRowData');
            $.each(rowData, function (k, v) {
              if (v.PK == '${itemChg.fixAreaPk}') {
                $("#dataTable").setSelection(k + 1, true);
                scrollToPosi(k + 1);
                if ('${itemChg.itemType1}'.trim() == 'JC') {
                  setTimeout(function () {
                    $("#intProdDataTable").jqGrid("setGridParam", {
                      postData: {
                        'fixAreaPk': v.PK,
                        'isCupboard': '${itemChg.isCupboard}'
                      }, page: 1
                    }).trigger("reloadGrid");
                  }, 200);
                }
                return false;

              }
            })
          } else {
            if ('${itemChg.itemType1}'.trim() == 'JC') {
              var rowData = $("#dataTable").jqGrid('getRowData', '1');
              setTimeout(function () {
                $("#intProdDataTable").jqGrid("setGridParam", {
                  postData: {
                    'fixAreaPk': rowData.PK,
                    'isCupboard': '${itemChg.isCupboard}'
                  }, page: 1
                }).trigger("reloadGrid");
              }, 200);

            }

          }
          if (isAddFixArea) {
            isAddFixArea = false;
            var ids = $("#dataTable").jqGrid("getDataIDs");
            $("#dataTable").setSelection(ids.length, true);
            scrollToPosi(ids.length);
          }

        },
        onSelectRow: function (rowid, status) {
          var itemType1 = '${itemChg.itemType1}'.trim();
          var rowData = $("#dataTable").jqGrid('getRowData', rowid);
          fixAreaPk = parseInt(rowData.PK);
          $("#fixAreaPk").val(fixAreaPk);
          $("#fixAreaDescr").val(rowData.Descr);
          $("#prePlanAreaPK").val(rowData.PrePlanAreaPK);
          $("#prePlanAreaDescr").val(rowData.preplanareadescr);
          if (itemType1 == 'JC') {
            $("#jqgh_intProdDataTable_Descr").text(rowData.Descr + "的集成成品");
            $("#intProdDataTable").jqGrid("setGridParam", {
              postData: {
                'fixAreaPk': rowData.PK,
                'isCupboard': '${itemChg.isCupboard}'
              }, page: 1
            }).trigger("reloadGrid");
          }

        }
      });
      Global.JqGrid.initJqGrid("intProdDataTable", {
        url: "${ctx}/admin/intProd/goJqGrid",
        height: 250,
        rownumbers: false,
        rowNum: 10000,
        styleUI: 'Bootstrap',
        colModel: [
          {name: 'Descr', index: 'Descr', width: 130, label: '集成成品', sortable: false, align: "left"},
          {name: 'PK', index: 'PK', width: 100, label: '编号', sortable: false, align: "center", hidden: true}
        ],
        onSelectRow: function (rowid, status) {
          var rowData = $("#intProdDataTable").jqGrid('getRowData', rowid);
          intProdPk = parseInt(rowData.PK);
          $("#intProdPk").val(intProdPk);
          $("#intProdDescr").val(rowData.Descr);
        },
      });
      Global.JqGrid.initJqGrid("itemChgDetailDataTable", {
        height: 233,
        cellEdit: true,
        cellsubmit: 'clientArray',
        styleUI: 'Bootstrap',
        rowNum: 10000,
		colModel : [
		  {name: "deleteBtn", index: "deleteBtn", width: 80, label: "删除", sortable: false, align: "left", formatter:deleteBtn},          
		  {name: "fixareapk", index: "fixareapk", width: 80, label: "区域编号", sortable: true, align: "left", hidden: true},
		  {name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品编号", sortable: true, align: "left", hidden: true},
		  {name: "preplanareadescr", index: "preplanareadescr", width: 80, label: "空间名称", sortable: true, align: "left"},
	      {name: "preplanareapk", index: "preplanareapk", width: 80, label: "空间编号", sortable: true, align: "left", hidden: true},
		  {name: "fixareadescr", index: "fixareadescr", width: 100, label: "区域名称", sortable: true, align: "left"},
		  {name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
		  {name: "itemcode", index: "itemcode", width: 272, label: "材料编号", sortable: true, align: "left", hidden: true},
		  {name: "itemdescr", index: "itemdescr", width: 160, label: "材料名称", sortable: true, align: "left"},
		  {name: "algorithm", index: "algorithm", width: 85, label: "算法", sortable:false, align: "left", hidden: true},
	      {name: "algorithmdescr", index: "algorithmdescr", width: 100, label: "算法", sortable: true, align: "left",editable:true,hidden:($.trim('${itemChg.itemType1}')== 'RZ'|| $.trim('${itemChg.itemType1}')== 'JC')?true:false,
		  		edittype:'select',
		  		editoptions:{ 
		  			dataUrl: "${ctx}/admin/item/getAlgorithm",
					postData: function(){
						var ret = selectDataTableRow("itemChgDetailDataTable");
						if(ret){
							return {
								code: ret.itemcode
							};
						}
						return {code: ""};
					},
					buildSelect: function(e){
						var ret = selectDataTableRow("itemChgDetailDataTable");

						var lists = JSON.parse(e);
						var html = "<option value=\"\" ></option>";
						
						for(var i = 0; lists && i < lists.length; i++){
							html += "<option value=\""+ lists[i].Code +"\">" + lists[i].Descr + "</option>"
						}
						return "<select style=\"font-family:宋体;\"> " + html + "</select>";
					},
		  			dataEvents:[{
	  					type:'change',
	  					fn:function(e){ 
	  						algorithmClick(e)
	  					}
		  			}, 
		  		]}
	 		},
		  {name: "cuttype", index: "cuttype", width: 85, label: "切割类型编码", sortable: true, align: "left", hidden:true},
		  {name: "cuttypedescr", index: "cuttypedescr", width: 85, label: "切割类型", sortable: true, align: "left", editable:true,hidden:($.trim('${itemChg.itemType1}')== 'RZ'|| $.trim('${itemChg.itemType1}')== 'JC')?true:false,
		  		edittype:'select',
		  		editoptions:{ 
		  			dataUrl: "${ctx}/admin/prePlanTempDetail/getQtyByCutType",
					postData: function(){
						var ret = selectDataTableRow("itemChgDetailDataTable");
						if(ret){
							return {
								itemCode: ret.itemcode
							};
						}
						return {itemCode: ""};
					},
					buildSelect: function(e){
						var ret = selectDataTableRow("itemChgDetailDataTable");

						var lists = JSON.parse(e);
						var html = "<option value=\"\" ></option>";
						
						for(var i = 0; lists && i < lists.length; i++){
							html += "<option value=\""+ lists[i].Code +"\">" + lists[i].fd + "</option>"
						}
						return "<select style=\"font-family:宋体;\"> " + html + "</select>";
					},
		  			dataEvents:[{
	  					type:'change',
	  					fn:function(e){ 
	  						cuttypeClick(e);
	  					}
		  			}, 
		  		]}
	 		},
		  {name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "itemtype3descr", index: "itemtype3descr", width: 85, label: "材料类型3", sortable: true, align: "left", hidden: true},
		  {name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
		  {name: "uomdescr", index: "uomdescr", width: 40, label: "单位", sortable: true, align: "left"},
		  {name: "unitprice", index: "unitprice", width: 50, label: "单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
		  {name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "right", sum: true},
		  {name: "markup", index: "markup", width: 40, label: "折扣", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true,integer:false,minValue:1}},
		  {name: "tmplineamount", index: "tmplineamount", width: 77, label: "小计", sortable: true, align: "right", sum: true},
		  {name: "processcost", index: "processcost", width: 60, label: "其他费用", sortable: true, align: "right", sum: true,editable:true,edittype:'text',editrules:{number:true,required:true}},
		  {name: "lineamount", index: "lineamount", width: 80, label: "总价", sortable: true, align: "right", sum: true},
		  {name: "remarks", index: "remarks", width: 200, label: "材料描述", sortable: true, align: "left",editable:true,edittype:'textarea'},
		  {name: "isfixprice", index: "isfixprice", width: 85, label: "是否固定价", sortable: true, align: "left", hidden: true},
		  {name: "cost", index: "cost", width: 20, label: "成本", sortable: true, align: "left", hidden: true},
		  {name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
		  {name: "itemsetno", index: "itemsetno", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "itemsetdescr", index: "itemsetdescr", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "isoutsetdescr", index: "isoutsetdescr", width: 80, label: "套餐外材料", sortable: true, align: "left", hidden: true},
		  {name: "isoutset", index: "isoutset", width: 20, label: "套餐外材料", sortable: true, align: "left", hidden: true},
		  {name: "custtypeitempk", index: "custtypeitempk", width: 85, label: "套餐材料信息编号", sortable: true, align: "left", hidden: true},
		  {name: "algorithmper", index: "algorithmper", width: 80, label: "算法系数", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true},hidden:($.trim('${itemChg.itemType1}')== 'RZ'|| $.trim('${itemChg.itemType1}')== 'JC')?true:false,},
		  {name: "algorithmdeduct", index: "algorithmdeduct", width: 80, label: "算法扣除数", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true},hidden:($.trim('${itemChg.itemType1}')== 'RZ'|| $.trim('${itemChg.itemType1}')== 'JC')?true:false,},
		  {name: "reqpk", index: "reqpk", width: 50, label: "需求PK", sortable: false, align: "left", hidden: true},
		  {name: "refchgreqpk", index: "refchgreqpk", width: 65, label: "相关需求pk", sortable:false, align: "left", hidden: true},
		],

        gridComplete: function () {
          var ids = $("#itemChgDetailDataTable").jqGrid("getDataIDs");
          $.each(ids, function (k, v) {
            var isfixprice = $("#itemChgDetailDataTable").getCell(v, "isfixprice");

            if (isfixprice == '0' && !$("#clearPrice").is(':checked')) {

            } else {
              $("#itemChgDetailDataTable").jqGrid('setCell', v, 'unitprice', '', 'not-editable-cell');
            }
          })
          scrollToPosi(ids.length, "itemChgDetailDataTable");
        },
        afterSaveCell: function (id, name, val, iRow, iCol) {
          var rowData = $("#itemChgDetailDataTable").jqGrid('getRowData', id);
          var processcost = parseFloat(rowData.processcost);
          switch (name) {
            case 'qty':
              $("#itemChgDetailDataTable").setCell(id, 'beflineamount', myRound(val * rowData.unitprice, 4));
              $("#itemChgDetailDataTable").setCell(id, 'tmplineamount', myRound(myRound(val * rowData.unitprice, 4) * rowData.markup / 100));
              $("#itemChgDetailDataTable").setCell(id, 'lineamount', myRound(myRound(myRound(val * rowData.unitprice, 4) * rowData.markup / 100) + processcost));
              if(rowData.cuttype != "" &&rowData.isoutset){
	              changeAlgorithm(id,rowData.cuttype,"");
              }
              break;
            case 'unitprice':
              $("#itemChgDetailDataTable").setCell(id, 'beflineamount', myRound(val * rowData.qty, 4));
              $("#itemChgDetailDataTable").setCell(id, 'tmplineamount', myRound(myRound(val * rowData.qty, 4) * rowData.markup / 100));
              $("#itemChgDetailDataTable").setCell(id, 'lineamount', myRound(myRound(myRound(val * rowData.qty, 4) * rowData.markup / 100) + processcost));
              break;
            case 'markup':
              $("#itemChgDetailDataTable").setCell(id, 'tmplineamount', myRound(myRound(rowData.qty * rowData.unitprice, 4) * val / 100));
              $("#itemChgDetailDataTable").setCell(id, 'lineamount', myRound(myRound(myRound(rowData.qty * rowData.unitprice, 4) * val / 100) + processcost));
              break;
            case 'processcost':
              $("#itemChgDetailDataTable").setCell(id, 'lineamount', myRound(myRound(rowData.tmplineamount) + processcost));
              break;
            case 'algorithmper':
            	if(rowData.algorithm != '' ){
            		changeAlgorithm(id, rowData.algorithm, "algorithm" );			
                }
        		break;
    		case 'algorithmdeduct':
	        	if(rowData.algorithm !='' ){
	        		changeAlgorithm(id, rowData.algorithm, "algorithm" );			
                }
        		break;
          }
          var beflineamount = $("#itemChgDetailDataTable").getCol('beflineamount', false, 'sum');
          var tmplineamount = $("#itemChgDetailDataTable").getCol('tmplineamount', false, 'sum');
          var processcost = $("#itemChgDetailDataTable").getCol('processcost', false, 'sum');
          var lineamount = $("#itemChgDetailDataTable").getCol('lineamount', false, 'sum');
          $("#itemChgDetailDataTable").footerData('set', {"beflineamount": beflineamount});
          $("#itemChgDetailDataTable").footerData('set', {"tmplineamount": tmplineamount});
          $("#itemChgDetailDataTable").footerData('set', {"processcost": processcost});
          $("#itemChgDetailDataTable").footerData('set', {"lineamount": lineamount});

        },
        beforeSelectRow: function (id) {
          setTimeout(function () {
            relocate(id, 'itemChgDetailDataTable');
          }, 100)
        }
      });
      if ('${itemChg.itemType1}'.trim() == 'JC') $("#itemChgDetailDataTable").setGridParam().showCol("intproddescr").trigger("reloadGrid");
      if ('${itemChg.itemType1}'.trim() == 'RZ') $("#itemChgDetailDataTable").setGridParam().showCol("itemsetdescr").trigger("reloadGrid");
    });
    function enterClick(e, type, d) {
      if (e.keyCode == 13) {
        switch (type) {
          case 'item':
            search();
            break;
          case 'fixAreaSearch':
            goto_query('dataTable', {'descr': d.value});
            break;
          case 'fixAreaAdd':
            add(d);
            break;
          case 'fixAreaEdit':
            update(d);
            break;
          case 'fixAreaInsert':
            insert(d);
            break;
        }
      }
    }
    function showValue(e) {
      var ret = selectDataTableRow();
      if (ret) {
        e.value = ret.Descr;
      }
    }
    function scale(opt) {
      switch (opt) {
        case 'big':
          top.$("#dialog_quickSave").parent().css({"width": "1380px", "left": leftOffset});
          $("#" + opt).hide();
          $("#small").show();
          break;
        default:
          leftOffset = top.$("#dialog_quickSave").parent().css("left");
          top.$("#dialog_quickSave").parent().css({"width": "60%", "left": "40%"});
          $("#" + opt).hide();
          $("#big").show();
          break;
      }


    }
    
function fixAreaAdd(){
	
    var datas={custCode:"${itemChg.custCode}",itemType1:"${itemChg.itemType1}",isService:"${itemChg.isService}"};

	Global.Dialog.showDialog("goFixAreaAdd",{
		title:"装修区域——新增",
		postData:datas,
		url:"${ctx}/admin/itemChg/goFixAreaAdd",
		height:260,
		width:580,
		returnFun:function(){
			goto_query("dataTable", {"descr": ''});	
		}
	});		
}

function fixAreaEdit(){
    // url: '${ctx}/admin/fixArea/updateFixArea?
    //custCode=${itemChg.custCode}&itemType1=${itemChg.itemType1}&isService=${itemChg.isService}&descr=' + descr + '&pk=' + ret.PK,
	var ret = selectDataTableRow();

   	if(!ret){
   		art.dialog({
   			content:"请选择一条记录进行编辑",
   		});
   		return;
   	}
    var datas={custCode:"${itemChg.custCode}",itemType1:"${itemChg.itemType1}",isService:"${itemChg.isService}",fixAreaPk:ret.PK};

	Global.Dialog.showDialog("goFixAreaEdit",{
		title:"装修区域——编辑",
		postData:datas,
		url:"${ctx}/admin/itemChg/goFixAreaEdit",
		height:260,
		width:580,
		returnFun:function(){
			goto_query("dataTable", {"descr": ''});	
		}
	});		
}

function fixAreaInsert(){
    var ret = selectDataTableRow();

   	if(!ret){
   		art.dialog({
   			content:"请选择插入位置",
   		});
   		return;
   	}
	var datas={custCode:"${itemChg.custCode}",itemType1:"${itemChg.itemType1}",isService:"${itemChg.isService}",dispSeq:ret.DispSeq};
    
	Global.Dialog.showDialog("goFixAreaInsert",{
		title:"装修区域——插入",
		postData:datas,
		url:"${ctx}/admin/itemChg/goFixAreaInsert",
		height:260,
		width:580,
		returnFun:function(){
			goto_query("dataTable", {"descr": ''});	
		}
	});		
}

function algorithmClick(e){
	var rowid = $("#itemChgDetailDataTable").getGridParam("selrow");
    var rowData = $("#itemChgDetailDataTable").jqGrid('getRowData', $(e.target).attr("rowid"));
    $("#itemChgDetailDataTable").setRowData($(e.target).attr("rowid"), {
    	algorithm:$(e.target).val(),
    });
    
    changeAlgorithm($(e.target).attr("rowid"),$(e.target).val(),"algorithm");
}

function cuttypeClick(e){
	var rowid = $("#itemChgDetailDataTable").getGridParam("selrow");
    var rowData = $("#itemChgDetailDataTable").jqGrid('getRowData', $(e.target).attr("rowid"));
    $("#itemChgDetailDataTable").setRowData($(e.target).attr("rowid"), {
    	cuttype:$(e.target).val(),
    });
    // 选择切割方式,套餐内默认不自动计算其它费用
	if(rowData.isoutset=='0'){
		return;
	}
    changeAlgorithm($(e.target).attr("rowid"),$(e.target).val(),"cutType");
}

//根据算法计算
function changeAlgorithm(id,val,type){
	var ret = selectDataTableRow("itemChgDetailDataTable");
	var datas={};
	datas.custCode=$.trim("${itemChg.custCode}");
	datas.itemCode=ret.itemcode;
	
	if(type=="algorithm"){
		datas.algorithm = val;
		datas.cutType=ret.cuttype; 
	} else {
		datas.algorithm = ret.algorithm;
		datas.cutType = val; 
		datas.type = '2';
		datas.qty = ret.qty;
	}
	
	datas.doorPK=ret.doorpk;
	datas.prePlanAreaPK=ret.preplanareapk;
	datas.isOutSet=ret.isoutset;
	datas.algorithmPer=ret.algorithmper;
	datas.algorithmDeduct=ret.algorithmdeduct;	
	
	$.ajax({
		url:"${ctx}/admin/itemPlan/getPrePlanQty",
			type:"post",
	        dataType:"json",
	        async:false,
	        data:datas,
			cache: false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				console.log(obj);
				if (obj){
					if(datas.type == "2"){
						$("#itemChgDetailDataTable").setRowData(id, {
							processcost:obj.processcost,
			              	'lineamount': myRound(myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100) + myRound(obj.processcost))
						});
					} else {
						$("#itemChgDetailDataTable").setRowData(id, {
							processcost:obj.processcost,
							qty:obj.qty
						}); 
					 	$("#itemChgDetailDataTable").setCell(id, 'beflineamount', myRound(obj.qty * ret.unitprice, 4));
		              	$("#itemChgDetailDataTable").setCell(id, 'tmplineamount', myRound(myRound(obj.qty * ret.unitprice, 4) * ret.markup / 100));
		              	$("#itemChgDetailDataTable").setCell(id, 'lineamount', myRound(myRound(myRound(obj.qty * ret.unitprice, 4) * ret.markup / 100) + myRound(obj.processcost)));
		              	$("#itemChgDetailDataTable").footerData('set', {"qty": $("#itemChgDetailDataTable").getCol('qty', false, 'sum')});
					}
				}
			}
	});	
}
  </script>
</head>

<body>
	<div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${itemChg.expired}"/>
				<input type="hidden" id="fixAreaPk" name="fixAreaPk"/>
				<input type="hidden" id="intProdPk" name="intProdPk"/>
				<input type="hidden" id="fixAreaDescr" name="fixAreaDescr"/>
				<input type="hidden" id="intProdDescr" name="intProdDescr"/>
				<input type="hidden" id="rowNo" name="rowNo" value="1"/>
				<input type="hidden" id="prePlanAreaPK" name="prePlanAreaPK" />
				<input type="hidden" id="prePlanAreaDescr" name="prePlanAreaDescr" />
				<ul class="ul-form">
					<li style="width: 30px;position: relative;">
						<button id="small" type="button" class="btn btn-default" 
									style="width: 100%;position: absolute;left: -10px" onclick="scale('small')">
							<span class="glyphicon glyphicon-chevron-right" style="font-size: 5px"></span>
						</button>
						<button id="big" type="button" class="btn btn-default"
							style="width: 100%;position: absolute;left: -10px;display: none" onclick="scale('big')">
							<span class="glyphicon glyphicon-chevron-left" style="font-size: 5px"></span>
						</button>
					</li>
					<li>
						<button onclick="doSave();" class="btn btn-system btn-sm " type="button">保存</button>
					</li>
					<li>
						<button onclick="closeAddUi();" class="btn btn-system btn-sm " type="button">退出</button>
					</li>
					<li id="clearPriceTd" style="display: none">
						<input type="checkbox" id="clearPrice" onclick="clearPriceClick()" name="clearPrice"/>套餐及升级材料
					</li>
					<li>
						<label>材料编码</label>
						<input type="text" id="code" name="code" value="${item.code}" onkeydown="enterClick(window.event,'item')"/>
					</li>
					<li>
						<label>材料名称</label>
					<input type="text" id="itemDescr" name="itemDescr" value="${item.descr}"
								onkeydown="enterClick(window.event,'item')"/>
					</li>
					<li>
						<button onclick="search();" class="btn btn-system btn-sm " type="button">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div style="width:1330px;">
			<div style="width: 155px;height: 310px;float: left;">
				<div>
					<table id="dataTable"></table>
				</div>
		      <!--标签页内容 -->
				<div style="padding:0px 0px 2px 6px;border: 1px solid #DDDDDD">
					<c:if test="${itemChg.itemType1.trim() == 'RZ' || itemChg.itemType1.trim() == 'JC'}">
						<button type="button" class="btn btn-xs btn-system btn-block" onclick="delete_();">删除</button>
					</c:if>
					<c:if test="${itemChg.itemType1.trim() != 'RZ' && itemChg.itemType1.trim() != 'JC'}">
						<div class="panel panel-system" style="margin-left:-8px" >
							<div class="panel-body">
								<div class="btn-group-xs" style="background-color: rgb(244,244,244)">
							    	<button type="button" id="saveBtn" class="btn btn-system" style="padding:1px;width:30px;margin-left: 5px"  onclick="fixAreaAdd()">新增</button>
							    	<button type="button" id="saveBtn" class="btn btn-system" style="padding:1px;width:30px;margin-left: 0px"  onclick="fixAreaEdit()">编辑</button>
							    	<button type="button" id="saveBtn" class="btn btn-system" style="padding:1px;width:30px;margin-left: 0px"  onclick="fixAreaInsert()">插入</button>
							    	<button type="button" id="saveBtn" class="btn btn-system" style="padding:1px;width:30px;margin-left: 0px"  onclick="delete_()">删除</button>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${itemChg.itemType1.trim() == 'RZ' || itemChg.itemType1.trim() == 'JC'}">
						<ul class="nav nav-sm nav-tabs">
							<li class="active"><a href="#menu1" data-toggle="tab">新增</a></li>
							<li><a href="#menu2" data-toggle="tab">编辑</a></li>
							<li><a href="#menu3" data-toggle="tab">插入</a></li> 
						</ul>
						<div class="tab-content">
							<div id="menu1" class="tab-pane fade in active">
								<input type="text" style="width: 140px" class="form-control" placeholder="按回车时新增" value="${fixArea.descr}"
			                		   onkeydown="enterClick(window.event,'fixAreaAdd',this)"/>
							</div>
							<div id="menu2" class="tab-pane fade ">
								<input type="text" style="width: 140px" class="form-control" placeholder="按回车时编辑" value="${fixArea.descr}"
			                   		onkeydown="enterClick(window.event,'fixAreaEdit',this)" onfocus="showValue(this)"/>
							</div>
							<div id="menu3" class="tab-pane fade">
								<input type="text" style="width: 140px" class="form-control" placeholder="按回车时插入" value="${fixArea.descr}"
			                	   onkeydown="enterClick(window.event,'fixAreaInsert',this)"/>
							</div>
						</div>
					</c:if>
				</div>
			</div>
			<div id="intProdDiv" style="width: 130px;height: 310px;float: left;display: none">
				<div>
					<table id="intProdDataTable"></table>
				</div>
				<div class="btn-panel">
		        	<div class="btn-group-sm">
		          		<button type="button" class="btn btn-system " onclick="addIntProd();">新增</button>
		          		<button type="button" class="btn btn-system " onclick="updateIntProd();">编辑</button>
		        	</div>
		      	</div>
		    </div>
		    <div style="width: 1170px;height: 310px;float: left;margin-left: 1px;" id="id_detail">
		    	<ul class="nav nav-sm nav-tabs">
		        	<li class="active"><a href="#tab1" data-toggle="tab">查找</a></li>
			        <li id="copy" class="" style="display: none"><a href="#tab2" data-toggle="tab">复制</a></li>
			        <li class=""><a href="#tab3" data-toggle="tab">材料批次</a></li>
			        <li id="itemSet" class="" style="display: none"><a href="#tab4" data-toggle="tab">套餐包</a></li>
			        <li id="mainItemSet" class="" style="display: none"><a href="#tab5" data-toggle="tab">套餐包</a></li>
		      	</ul>
		      	<div class="tab-content">
		        	<div id="tab1" class="tab-pane fade in active" style="height: 300px; ">
		          		<iframe id="iframe" style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:none "
		                 	 src="${ctx}/admin/itemChg/goItem?itemType1=${itemChg.itemType1}&itemType2=${itemChg.itemType2}&custType=${itemChg.custType}&custCode=${itemChg.custCode}"></iframe>
		        	</div>
		        	<div id="tab2" class="tab-pane fade " style="height: 300px; ">
		         		<iframe id="itemIframe" style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:none"
		                	  src="${ctx}/admin/itemChg/goItemCopy?itemType1=${itemChg.itemType1}"></iframe>
		        	</div>
		        	<div id="tab3" class="tab-pane fade" style="height: 300px;border: none ">
		          		<iframe id="clpcIframe" style="width: 100%;height: 100%;border-top:none;border-left: 1px solid #DDDDDD;"
		                  	src="${ctx}/admin/itemChg/goItemBatchDetailList?custCode=${itemChg.custCode}&itemType1=${itemChg.itemType1}&excludedReqPks=${itemChg.excludedReqPks}"></iframe>
		        	</div>
		        	<div id="tab4" class="tab-pane fade" style="height: 300px;border: none ">
		          		<iframe id="itemSetFrame" style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:1px solid #DDDDDD"
		                  	src="${ctx}/admin/itemChg/goItemSetDetailList?itemType1=${itemChg.itemType1}&custType=${itemChg.custType}"></iframe>
		        	</div>
		        	<div id="tab5" class="tab-pane fade" style="height: 300px;border: none ">
		          		<iframe id="mainItemSetFrame" style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:1px solid #DDDDDD"
		                  	src="${ctx}/admin/itemChg/goMainItemSet?itemType1=${itemChg.itemType1}&custType=${itemChg.custType}&custCode=${itemChg.custCode}"></iframe>
		        	</div>
				</div>
			</div>
		</div>
		<div style="width: 1330px;height: 258px;float: left;margin-top: 20px">
			<div style="width: 1330px">
		    	<table id="itemChgDetailDataTable"></table>
			</div>
		</div>
	</div>
</body>
</html>
