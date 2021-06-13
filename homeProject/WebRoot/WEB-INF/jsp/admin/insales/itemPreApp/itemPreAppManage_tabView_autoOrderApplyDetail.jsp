<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
	var cantordernum=0;//当前表格中不拆单记录数量
	var loadTimes=0;//表格加载次数
	$(function(){
		url="${ctx}/admin/itemPreApp/goAutoOrderJqGrid";
		initApplyDetailDataTable(url);
	});
	function initApplyDetailDataTable(url, height){

		Global.JqGrid.initEditJqGrid("dataTable_applyDetail", {
			height:height?height:($("#m_umState").val()=="A"?285:250),
			url:url,
			rowNum: 10000,
			styleUI:"Bootstrap",
			postData:{
				custCode:$("#custCode").val(),
				itemType1:$("#itemType1").val(),
				itemType2:$("#itemType2").val(),
				preAppNo:$("#preAppNo").val(),
				isService:$("#isService").val(),
				preAppDTPks:"",
				reqPks:"",
				appNo:$("#appNo").val(),
				m_umState:$("#m_umState").val(),
				isSetItem:$("#isSetItem").val(),
				itemCodes:""
			},
			colModel:[
				{name: "ordernamecode", index: "ordernamecode", width: 75, label: "ordernamecode", sortable: true, align: "left", hidden: true},
				{name: "ordername", index: "ordername", width: 75, label: "ordername", sortable: true, align: "left", hidden: true},
				{name: "specreqpk", index: "specreqpk", width: 75, label: "specreqpk", sortable: true, align: "left", hidden: true},
				{name: "preappdtpk", index: "preappdtpk", width: 75, label: "preappdtpk", sortable: true, align: "left", hidden: true},
				{name: "reqpk", index: "reqpk", width: 75, label: "领料标识", sortable: true, align: "left", hidden: true},
				{name: "pk", index: "pk", width: 50, label: "编号", sortable: true, align: "left", hidden: true},
				{name: "itemcode", index: "itemcode", width: 75, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 189, label: "材料名称", sortable: true, align: "left"},
				{name: "no", index: "no", width: 80, label: "批次号", sortable: true, align: "left", hidden: true},
				{name: "fixareapk", index: "fixareapk", width: 84, label: "fixareapk", sortable: true, align: "left", hidden: true},
				{name: "fixareadescr", index: "fixareadescr", width: 120, label: "装修区域", sortable: true, align: "left"},
				{name: "intprodpk", index: "intprodpk", width: 84, label: "intprodpk", sortable: true, align: "left", hidden: true},
				{name: "intproddescr", index: "intproddescr", width: 70, label: "集成产品", sortable: true, align: "left"},
				{name: "uomdescr", index: "uomdescr", width: 50, label: "单位", sortable: true, align: "left"},
				{name: "color", index: "color", width: 50, label: "颜色", sortable: true, align: "left", hidden: true,editable:true},
				{name: "sqlcodedescr", index: "sqlcodedescr", width: 70, label: "品牌", sortable: true, align: "left", hidden: true,editable:true},
				{name: "supplcodedescr", index: "supplcodedescr", width: 70, label: "供应商", sortable: true, align: "left", hidden: true,editable:true, hidden:true},
				{name: "declareqty", index: "declareqty", width: 70, label: "申报数量", sortable: true, align: "right",editable:true, editrules: {number:true,required:true},sum:true,summaryType:'sum'},
				{name: "qty", index: "qty", width: 70, label: "采购数量", sortable: true, align: "right", sum: true,editable:true, editrules: {number:true,required:true},summaryType:'sum'},
				{name: "preremarks", index: "preremarks", width: 150, label: "备注", sortable: true, align: "left",editable:true},
				{name: "reqremarks", index: "reqremarks", width: 150, label: "预算备注", sortable: true, align: "left"},//,editable:true
				{name: "sendqty", index: "sendqty", width: 80, label: "已发货数量", sortable: true, align: "right", hidden: true,editable:true,	editrules: {number:true,required:true}},
				{name: "shortqty", index: "shortqty", width: 70, label: "缺货数量", sortable: true, align: "right", hidden: true,editable:true,	editrules: {number:true,required:true}},
				{name: "reqqty", index: "reqqty", width: 70, label: "预算数量", sortable: true, align: "right", sum: true,summaryType:'sum'},
				{name: "sendedqty", index: "sendedqty", width: 80, label: "总共已发货", sortable: true, align: "right", sum: true,summaryType:'sum'},
				{name: "applyqty", index: "applyqty", width: 80, label: "已申请数量", sortable: true, align: "right",editable:true,	editrules: {number:true,required:true}},
				{name: "confirmedqty", index: "confirmedqty", width: 80, label: "已审核数量", sortable: true, align: "right", sum: true,summaryType:'sum'},
				{name: "allqty", index: "allqty", width: 80, label: "库存数量", sortable: true, align: "right", sum: true,summaryType:'sum'},
				{name: "userqty", index: "userqty", width: 80, label: "可用数量", sortable: true, align: "right", sum: true,summaryType:'sum'},
				{name: "cost", index: "cost", width: 70, label: "成本单价", sortable: true, align: "right",hidden:true},
				{name: "allcost", index: "allcost", width: 70, label: "成本总价", sortable: true, align: "right", sum: true,editable:true,hidden:true, editrules: {number:true,required:true},summaryType:'sum'},
				{name: "projectcost", index: "projectcost", width: 110, label: "项目经理结算价", sortable: true, align: "right", hidden: true},
				{name: "allprojectcost", index: "allprojectcost", width: 115, label: "项目经理结算总价", sortable: true, align: "right", sum: true, hidden: true,editable:true, editrules: {number:true,required:true},summaryType:'sum'},
				{name: "price", index: "price", width: 60, label: "销售价", sortable: true, align: "right", hidden: true},
				{name: "processcost", index: "processcost", width: 70, label: "其它费用", sortable: true, align: "right", sum: true,summaryType:'sum'},
				{name: "differences", index: "differences", width: 80, label: "成本差异额 ", sortable: true, align: "right", sum: true, hidden: true,summaryType:'sum'},
				{name: "pricedifferences", index: "pricedifferences", width: 80, label: "销售差异额", sortable: true, align: "right", hidden: true},
				{name: "sendtypedescr", index: "sendtypedescr", width: 70, label: "发货类型", sortable: true, align: "left",editable:true, hidden:true},
				{name: "weight", index: "weight", width: 60, label: "总重量", sortable: true, align: "right", sum: true,editable:true,	editrules: {number:true,required:true},formatter:function(cellvalue, options, rowObject){return myRound(cellvalue,2);},summaryType:'sum'},
				{name: "numdescr", index: "numdescr", width: 60, label: "总片数", sortable: true, align: "right",editable:true,	editrules: {number:true,required:true}},
				{name: "lastupdate", index: "lastupdate", width: 100, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "reqprocesscost", index: "reqprocesscost", width: 156, label: "reqprocesscost", sortable: true, align: "right", hidden:true},
				{name: "perweight", index: "perweight", width: 156, label: "perweight", sortable: true, align: "right", hidden:true},
				{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 60, label: "操作", sortable: true, align: "left"},
				{name: "whcode", index: "whcode", width: 90, label: "whcode", sortable: true, align: "left", hidden:true},
				{name: "whdescr", index: "whdescr", width: 90, label: "whdescr", sortable: true, align: "left", hidden:true},
				{name: "supplcode", index: "supplcode", width: 90, label: "supplcode", sortable: true, align: "left", hidden:true},
				{name: "sendtype", index: "sendtype", width: 90, label: "sendtype", sortable: true, align: "left", hidden:true},
				{name: "itemtype2", index: "itemtype2", width: 90, label: "itemtype2", sortable: true, align: "left", hidden:true},
				{name: "remarks", index: "remarks", width: 90, label: "remarks", sortable: true, align: "left", hidden:true},
				{name: "delivtype", index: "delivtype", width: 90, label: "delivtype", sortable: true, align: "left", hidden:true},
				{name: "issetitem", index: "issetitem", width: 90, label: "issetitem", sortable: true, align: "left", hidden:true},
			],
		 	grouping : true , // 是否分组,默认为false
		 	showDelivType:true,//显示配送方式下拉框
			groupingView : {
				groupField : [ 'ordername'], // 按照哪一列进行分组
				groupColumnShow : [ false], // 是否显示分组列
				groupText : ['<b>{0}({1})</b>'], // 表头显示的数据以及相应的数据量
				groupCollapse : false , // 加载数据时是否只显示分组的组信息
				groupDataSorted : false , // 分组中的数据是否排序
				groupOrder : [ 'asc'], // 分组后的排序
				groupSummary : [ true], // 是否显示汇总.如果为true需要在colModel中进行配置
				summaryType : 'max' , // 运算类型，可以为max,sum,avg</div>
				summaryTpl : '<b>Max: {0}</b>' ,
				showSummaryOnHide : true //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
			}, 
			gridComplete:function (){
				//删除操作会加载两次gridComplete,记录次数，autoSetDelivType()中设置列第二次才生效，
				//因为第一次加载时id没有更新，比如1,2,3,4中删除2,仍然是1,3,4,而不会更新为1,2,3,导致删除一条记录后，后面的记录错位重复
				if($("#isDel").val()=="1"){
					var delNum=parseFloat($("#delNum").val());
					$("#delNum").val(delNum+1);
				}
				selectFirstRow();
				remarksDiffColor();
				qtyDiffColor();
				if($("#needCheckQty").val()=="1"){
					checkQty();
				}
				changeDiffPerf();
				if(loadTimes==0){//第一次加载时获取不下单次数
					cantordernum=getCantOrderNum();
				}
				loadTimes+=1;
				//不下单数量提示
		        if(parseInt(cantordernum)>0){
					$("#tip").show().html("不下单数量："+cantordernum);
		        }
	         	//修复分组合计js小数计算异常
	        	$(".jqfoot").each(function(i){
	        		var sumQty= myRound($(this).children("td[aria-describedby=dataTable_applyDetail_qty]").text(),2);
	        		var sumReqqty= myRound($(this).children("td[aria-describedby=dataTable_applyDetail_reqqty]").text(),2);
	        		var sumAllcost= myRound($(this).children("td[aria-describedby=dataTable_applyDetail_allcost]").text(),2);
	        		var sumAllprojectcost= myRound($(this).children("td[aria-describedby=dataTable_applyDetail_allprojectcost]").text(),2);
	        		var sumAllDeclareqty= myRound($(this).children("td[aria-describedby=dataTable_applyDetail_declareqty]").text(),2);
	        		$(this).children("td[aria-describedby=dataTable_applyDetail_qty]").text(sumQty);
	        		$(this).children("td[aria-describedby=dataTable_applyDetail_reqqty]").text(sumReqqty);
	        		$(this).children("td[aria-describedby=dataTable_applyDetail_allcost]").text(sumAllcost);
	        		$(this).children("td[aria-describedby=dataTable_applyDetail_allprojectcost]").text(sumAllprojectcost);
	        		$(this).children("td[aria-describedby=dataTable_applyDetail_declareqty]").text(sumAllDeclareqty);
		        });
		        //不下单的分组设置灰色不可选
		        var ids = $("#dataTable_applyDetail").getDataIDs();
				for(var i=0;i<ids.length;i++){
					var rowData = $("#dataTable_applyDetail").getRowData(ids[i]);
					if(rowData.ordername=="不下单"){
						$("#dataTable_applyDetail #"+ids[i]).find("td").css({
							"background":"#bbb"
						});
					}
				} 
				//把配送方式设置到分组旁边的下拉框 by cjg 20191020  begin 
				var groupNum=$(".dataTable_applyDetailghead_0").length;//统计一共几个分组
			/* 	var custType=$("#custType").val();
				var regionCode=$("#regionCode").val();
				var itemType1=$("#itemType1").val();
				var rowData= $("#dataTable_applyDetail").jqGrid('getRowData'); */
				for(var i=0;i<groupNum;i++){
					/* var ordernamecode=$("tr[id=dataTable_applyDetailghead_0_"+i+"]").nextAll(".jqgrow").eq(0).children("td[aria-describedby=dataTable_applyDetail_ordernamecode]").text();
					var allcost=$("tr[id=dataTable_applyDetailghead_0_"+i+"]").nextAll(".jqfoot").eq(0).children("td[aria-describedby=dataTable_applyDetail_allcost]").text(); */
					var delivtype=$("tr[id=dataTable_applyDetailghead_0_"+i+"]").nextAll(".jqgrow").eq(0).children("td[aria-describedby=dataTable_applyDetail_delivtype]").text();
					$("#delivType"+i).val(delivtype);
					/* 
					if((itemType1=="ZC"||itemType1=="JC")&& ordernamecode.split(" - ")[0]=="1"){//供应商直送的进行判断,基础主材发货类型在第一个，供应商编号在第二个
						var isSendCmpWh=checkSendCmpWh(ordernamecode.split(" - ")[1],allcost);
						if(isSendCmpWh=="1"){
							autoSetDelivType(i,"4",ordernamecode,rowData);
						}else{
							autoSetDelivType(i,"2",ordernamecode,rowData);
						}
					}else if((itemType1=="RZ"||itemType1=="JZ")&& ordernamecode.split(" - ")[1]=="1"){//供应商直送的进行判断,软装基装发货类型在第二个，供应商编号在第三个
						var isSendCmpWh=checkSendCmpWh(ordernamecode.split(" - ")[2],allcost);
						if(isSendCmpWh=="1"){
							autoSetDelivType(i,"4",ordernamecode,rowData);
						}else{
							autoSetDelivType(i,"2",ordernamecode,rowData);
						}
					} */
				}
				//把配送方式设置到分组旁边的下拉框  by cjg 20191020  end
			},
			beforeSelectRow:function(id){
				setTimeout(function(){
					relocate(id,"dataTable_applyDetail");
				},10);
			},
			beforeSaveCell:function(rowId, name, val, iRow, iCol){
				$("#isTipExit").val("T");
				var ret = $("#dataTable_applyDetail").jqGrid("getRowData", rowId);

				ret[name]=val;
				
				var reg = /^[0-9]+.?[0-9]*$/;
				if(!reg.test(ret.qty)) return;  
				
				if(name == "remarks" && remarksDiffColor()){
					if(val != ret.reqremarks){
						$("#"+rowId+" td").css("background", "#FFAA00");
					}else{
						$("#"+rowId+" td").css("background", "");
					}
				}
				if(name == "reqremarks" && remarksDiffColor()){
					if(val != ret.remarks){
						$("#"+rowId+" td").css("background", "#FFAA00");
					}else{
						$("#"+rowId+" td").css("background", "");
					}
				}
				ajaxPost("${ctx}/admin/itemPreApp/getBeforeSaveCellInfo",
					{
						"itemCode":ret.itemcode,
						"qty":ret.qty
					},
					function(data){
						var i=0.0;
						
						if(data.result == "1" && parseFloat(ret.reqqty) != 0){
							i = parseFloat(ret.reqprocesscost) * parseFloat(ret.qty) / parseFloat(ret.reqqty);
						}else{
							i = parseFloat(ret.reqprocesscost);
						}
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "processcost", i);
						i = parseFloat(ret.qty) * parseFloat(ret.cost);
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "allcost", i.toFixed(2));
						i = parseFloat(ret.qty) * parseFloat(ret.projectcost);

						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "allprojectcost", i.toFixed(2));
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "lastupdatedby", $("#czybh").val());
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "lastupdate", new Date());
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "actionlog", "EDIT");
						
						i = parseFloat(ret.qty) * parseFloat(ret.perweight);
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "weight", i.toFixed(2));
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "numdescr", i.toFixed(2));
						
						if(data.numDescr != ""){
							$("#dataTable_applyDetail").jqGrid("setCell", rowId, "numdescr", data.numDescr);
						}
						$("#dataTable_applyDetail").jqGrid("setCell", rowId, "declareqty", parseFloat(ret.qty)); //by zjfvar 
						$("#dataTable_applyDetail").footerData("set", {
							allcost:$("#dataTable_applyDetail").getCol("allcost", false, "sum") ,
							allprojectcost:$("#dataTable_applyDetail").getCol("allprojectcost", false, "sum") ,
							weight:$("#dataTable_applyDetail").getCol("weight", false, "sum") ,
							numdescr:$("#dataTable_applyDetail").getCol("numdescr", false, "sum")
						});
					}
				);
				if(name=="qty"){
					$("#dataTable_applyDetail").jqGrid("setCell", rowId, "differencesperf",0); //by cjg
					$("#dataTable_applyDetail").footerData('set', { "differencesperf": 0 });
				}
				if(name=="declareqty") {
					var result;
					if(val==0 && ret.qty!=0){
						result=1;
					}else if(val==0 && ret.qty==0){
						result=0;
					}else {
						result=((parseFloat(val)-parseFloat(ret.qty))/parseFloat(val)).toFixed(2);
					}
					$("#dataTable_applyDetail").jqGrid("setCell", rowId, "differencesperf",result ); //by cjg
				} 
			},
			onCellSelect:function(rowid,iCol,cellcontent,e){
				if($("#m_umState").val() == "C" || $("#m_umState").val().trim() == "V" || $("#m_umState").val().trim() == "Q"){
                	$("#dataTable_applyDetail").jqGrid("setCell", rowid, iCol, "", "not-editable-cell");  
				}
			},
			afterSaveCell:function(rowId, name, val, iRow, iCol){
				changeDiffPerf();
				var rowData = $("#dataTable_applyDetail").getRowData(rowId);
				//供应商直送，修改采购数量和成本总价，判断配送方式发送公司仓规则 by cjg 20191020
				if(rowData.sendtype == "1" &&(name=="qty" || name=="allcost")){
		   			var allcost=$("#dataTable_applyDetail").getCol('allcost',false,'sum');
		   			var isSendCmpWh=checkSendCmpWh(rowData.supplcode,allcost);
		   			if(isSendCmpWh=="1"){
		   				rowData.delivtype="4";
		   			}else{
		   				rowData.delivtype="2";
		   			}
		   			var groupNum=$(".dataTable_applyDetailghead_0").length;//统计一共几个分组
		   			for(var i=0;i<groupNum;i++){
		   				var ordernamecode=$("tr[id=dataTable_applyDetailghead_0_"+i+"]").nextAll(".jqgrow").eq(0).children("td[aria-describedby=dataTable_applyDetail_ordernamecode]").text();
						if(ordernamecode==rowData.ordernamecode){//找到修改行所属的分组，设置配送方式
							$("#delivType"+i).val(rowData.delivtype);
						}
		   			}
		   			//更新同组记录的配送方式
		   			var rowDatas= $("#dataTable_applyDetail").jqGrid('getRowData');
					$.each(rowDatas, function(k, v){
						if(v.ordernamecode==rowData.ordernamecode){
							v.delivtype=rowData.delivtype;
							$("#dataTable_applyDetail").setRowData(k+1, v);
						}
					});	
				}
			}
		}); 
	}
	
	//差异性占比sum
	function changeDiffPerf(){
		var declareqtySum=$("#dataTable_applyDetail").getCol('declareqty',false,'sum');
		var qtySum=$("#dataTable_applyDetail").getCol('qty',false,'sum');
		var result;
		if(declareqtySum==0 && qtySum!=0){
			result=1;
		}else if(declareqtySum==0 && qtySum==0){
			result=0;
		}else {
			result=((parseFloat(declareqtySum)-parseFloat(qtySum))/parseFloat(declareqtySum)).toFixed(2);
		}
		$("#dataTable_applyDetail").footerData('set', { "differencesperf": result });
	}
	
	//获取不能拆单的数量
	function getCantOrderNum(){
		var num;
		$.ajax({
			url : "${ctx}/admin/itemPreApp/getCantOrderNum",
			type : "post",
			dataType : "json",
			async:false,
			cache : false,
			success:function(data){
				num=data;
			}
		});
		return num;
	}
	
	//手动修改配送方式
	function changeDelivType(delivTypeId,grpTextStr){
		var groupName=grpTextStr.substring(3,grpTextStr.length-7);
		var rowData= $("#dataTable_applyDetail").jqGrid('getRowData');
		var delivType= $("#"+delivTypeId).val();
		$.each(rowData, function(k, v){
			if(groupName.indexOf(v.ordername)!=-1){
				v.delivtype=delivType;
				$("#dataTable_applyDetail").setRowData(k+1, v);
			}
		});	
	}
	
	//自动匹配配送方式设置到row中
	function autoSetDelivType(i,delivType,ordernamecode,rowData){
		$("#delivType"+i).val(delivType);
		var ids = $("#dataTable_applyDetail").getDataIDs();
		if(($("#delNum").val()>1 && $("#isDel").val()=="1") || ($("#isDel").val()=="0" && $("#delNum").val()==0)){
			$.each(rowData, function(k, v){
				if(v.ordernamecode==ordernamecode){
					v.delivtype=delivType;
					$("#dataTable_applyDetail").setRowData(k+1, v);
				}
			});	
		}
	}
	
	//是否发货公司仓
	function checkSendCmpWh(supplCode,allcost){
		var isSendCmpWh="0";
		ajaxPost("${ctx}/admin/itemPreApp/checkSendCmpWh",
			{
				supplCode:supplCode,
				allcost:allcost,
				custType:$("#custType").val(),
				regionCode:$("#regionCode").val(),
				toCmItemAppType:"1",
			},
			function(data){
				isSendCmpWh=data;
			}
		);
		return isSendCmpWh;
	}
	
	//备注离焦时，设置到row中
	function blurRemarks(remarksId,grpTextStr){
		var groupName=grpTextStr.substring(3,grpTextStr.length-7);
		var rowData= $("#dataTable_applyDetail").jqGrid('getRowData');
		var remarks= $("#"+remarksId).val();
		$.each(rowData, function(k, v){
			if(groupName.indexOf(v.ordername)!=-1){
				v.remarks=remarks;
				$("#dataTable_applyDetail").setRowData(k+1, v);
			}
		});	
	}
	
	//手动修改仓库
	function changeWhcode(whcodeId,grpTextStr){
		var groupName=$("#"+whcodeId).prev().text();
		var rowData= $("#dataTable_applyDetail").jqGrid('getRowData');
		var whcode= $("#"+whcodeId).val();
		var whdescr=getWhDescr(whcodeId);
		var groupNameArr=groupName.split("(");//拆分(
		var endIndex=groupName.lastIndexOf("-");
		var groupFront=groupName.substring(0,endIndex+1);//前半部分
		var newGroupName=groupFront+" "+whdescr+"("+groupNameArr[1];
		$("#"+whcodeId).prev().text(newGroupName);
		$.each(rowData, function(k, v){
			if(groupName.indexOf(v.ordername)!=-1){
				v.whcode=whcode;
				$("#dataTable_applyDetail").setRowData(k+1, v);
			}
		});	
	}
	
	//获取仓库名称
	function getWhDescr(codeId){
		var selectText=$("#"+codeId).find("option:selected").text();
		var arr = selectText.split(" ");
		var descr="";
		if(arr.length>=2){
			descr=arr[1];
		}
		return descr;
	}
	
	//显示配送方式下拉框等
	function showDelivType(str,grpTextStr,i){
		var startIndex=grpTextStr.indexOf("(");//左边括号开始位置
		var grpTextStr_new=grpTextStr.substring(0, startIndex);//去掉括号后的分组标题
		var addClass="";//添加配送方式的隐藏class 
		var addClass_remarks="";//添加备注的隐藏class
		var addClass_whcode="class='hidden'";//添加仓库的隐藏class
		if(grpTextStr.indexOf("不下单")!=-1 || grpTextStr.indexOf("测量单")!=-1 ){//不下单和测量单隐藏下拉框
			addClass="class='hidden'";
			addClass_remarks="class='hidden'";
		}else if(grpTextStr.indexOf("仓库发货")!=-1 ){//仓库发货隐藏下拉框
			addClass="class='hidden'";
			if(grpTextStr_new.split(" - ")[3]==""){//仓库为空的,显示仓库下拉框手动填写
				addClass_whcode="";
			}
		}
		var delivTypeId="delivType"+i;
		var remarksId="remarks"+i;
		var whcodeId="whcode"+i;
		//添加仓库
		var whcodeStr=$("#whcode").prop("outerHTML")
              .replace('whcode', whcodeId)
              .replace('请选择', "请选择仓库")
              .replace('onmousedown=\"javascript:return false;\"','');
		str+="<select onchange='changeWhcode(\""+whcodeId+"\",\""+grpTextStr+"\")'"+addClass_whcode+whcodeStr.substring(7, whcodeStr.length);
		//添加配送方式
		str+="<select id='"+delivTypeId+"'"+addClass+" name='delivType' style='width:160px;border-radius:5px;' onchange='changeDelivType(\""
		+delivTypeId+"\",\""+grpTextStr+"\")'>	<option value=''>请选择配送方式...</option><option value='1' >1 公司司机送货</option><option value='2'>2 供应商送货</option><option value='3'>3 项目经理自提</option><option value='4'>4 发货到公司仓</option></select>"
		//添加备注
		str+="&nbsp;<input placeholder='领料单备注' type='text' id='"+remarksId+"'"+addClass_remarks+" name='remarks' style='border-radius:5px;width:300px;height:19px' onchange='blurRemarks(\""
		+remarksId+"\",\""+grpTextStr+"\")'>";
		return str;
	}
</script>
<div class="btn-panel" style="margin-top:2px">
	<div class="btn-group-sm"  >
		<button id="addBtn" type="button" class="funBtn funBtn-system " onclick="add()" title="根据拆单规则自动添加到相应分组">新增</button>
		<button id="insertBtn" type="button" class="funBtn funBtn-system " onclick="add('1')" title="添加到选中的分组">添加到分组</button>
		<button id="yyxxzBtn" type="button" class="funBtn funBtn-system " onclick="yyxxz()">已有项新增</button>
		<button id="delBtn" type="button" class="funBtn funBtn-system" onclick="del()">删除</button>
		<span id="tip" style="color:red" hidden></span>
	</div>
</div>	
<table id="dataTable_applyDetail"></table>
