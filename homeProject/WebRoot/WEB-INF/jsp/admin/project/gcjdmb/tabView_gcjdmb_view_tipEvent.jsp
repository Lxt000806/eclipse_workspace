<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
	$(function(){
		if($("#m_umState").val() != "A"){
			url="${ctx}/admin/gcjdmb/goJqGridProgTempAlarm?no="+$("#no").val();
		}else{
			url="#";
		}

		Global.JqGrid.initJqGrid("dataTable_tipEvent", {
			height:330,
			width:1200,
			rowNum: 10000,
			url:url,
			autowidth:false,
			styleUI:"Bootstrap",
			onSortColEndFlag:true,
			colModel:[
				{name: "prjitemdescr", index: "prjitemdescr", width: 130, label: "施工项目", sortable: true, align: "left"},
				{name: "daytypedescr", index: "daytypedescr", width: 95, label: "提醒日期类型", sortable: true, align: "left"},
				{name: "addday", index: "addday", width: 70, label: "增加天数", sortable: true, align: "right"},
				{name: "msgtexttemp", index: "msgtexttemp", width: 130, label: "消息内容模板", sortable: true, align: "left"},
				{name: "roledescr", index: "roledescr", width: 70, label: "提醒对象", sortable: true, align: "left"},
				{name: "czybh", index: "czybh", width: 70, label: "提醒人员", sortable: true, align: "left",hidden:true},
				{name: "czydescr", index: "czydescr", width: 70, label: "提醒人员", sortable: true, align: "left"},
				{name: "remindczytype", index: "remindczytype", width: 90, label: "提醒人类型", sortable: true, align: "left",hidden:true},
				{name: "remindczytypedescr", index: "remindczytypedescr", width: 90, label: "提醒人类型", sortable: true, align: "left"},
				{name: "typedescr", index: "typedescr", width: 70, label: "提醒类型", sortable: true, align: "left"},
				{name: "dealday", index: "dealday", width: 70, label: "处理时间", sortable: true, align: "right"},
				{name: "completeday", index: "completeday", width: 85, label: "进场完成时间", sortable: true, align: "right"},
				{name: "prepk", index: "prepk", width: 84, label: "前置提醒PK", sortable: true, align: "left", hidden: true},
				{name: "itemtype1descr", index: "itemtype1descr", width: 75, label: "材料类型1", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 75, label: "材料类型2", sortable: true, align: "left"},
				{name: "isneedreqdescr", index: "isneedreqdescr", width: 85, label: "是否判断需求", sortable: true, align: "left"},
				{name: "msgtexttemp2", index: "msgtexttemp2", width: 130, label: "消息内容模板2", sortable: true, align: "left"},
				{name: "worktype1descr", index: "worktype1descr", width: 75, label: "工种分类1", sortable: true, align: "left"},
				{name: "offerworktype2descr", index: "offerworktype2descr", width: 75, label: "工种分类2", sortable: true, align: "left"},
				{name: "worktype12descr", index: "worktype12descr", width: 80, label: "工种分类12", sortable: true, align: "left"},
				{name: "jobtypedescr", index: "jobtypedescr", width: 70, label: "任务类型", sortable: true, align: "left"},
				{name: "isautojobdescr", index: "isautojobdescr", width: 85, label: "是否自动任务", sortable: true, align: "left"},
				{name: "title", index: "title", width: 60, label: "标题", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 85, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "操作人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作代码", sortable: true, align: "left"},
				
				{name: "prjitem", index: "prjitem", width: 80, label: "prjitem", sortable: true, align: "left", hidden:true},
				{name: "daytype", index: "daytype", width: 80, label: "daytype", sortable: true, align: "left", hidden:true},
				{name: "role", index: "role", width: 80, label: "role", sortable: true, align: "left", hidden:true},
				{name: "type", index: "type", width: 80, label: "type", sortable: true, align: "left", hidden:true},
				{name: "itemtype1", index: "itemtype1", width: 80, label: "itemtype1", sortable: true, align: "left", hidden:true},
				{name: "itemtype2", index: "itemtype2", width: 80, label: "itemtype2", sortable: true, align: "left", hidden:true},
				{name: "isneedreq", index: "isneedreq", width: 80, label: "isneedreq", sortable: true, align: "left", hidden:true},
				{name: "worktype1", index: "worktype1", width: 80, label: "worktype1", sortable: true, align: "left", hidden:true},
				{name: "offerworktype2", index: "offerworktype2", width: 80, label: "offerworktype2", sortable: true, align: "left", hidden:true},
				{name: "worktype12", index: "worktype12", width: 80, label: "worktype12", sortable: true, align: "left", hidden:true},
				{name: "jobtype", index: "jobtype", width: 80, label: "jobtype", sortable: true, align: "left", hidden:true},
				{name: "isautojob", index: "isautojob", width: 80, label: "isautojob", sortable: true, align: "left", hidden:true},
				{name: "tempno", index: "tempno", width: 80, label: "tempno", sortable: true, align: "left", hidden:true},
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left", hidden:true}
			],
			ondblClickRow:function(rowid,iRow,iCol,e){
            	
				var ret = $("#dataTable_tipEvent").jqGrid("getRowData",rowid);
				
				if(ret){
					var postData={
							pk:ret.pk,
							prjItem:ret.prjitem,
							dayType:ret.daytype,
							addDay:ret.addday,
							msgTextTemp:ret.msgtexttemp,
							role:ret.role,
							type:ret.type,
							dealDay:ret.dealday,
							completeDay:ret.completeday,
							itemType1:ret.itemtype1,
							itemType2:ret.itemtype2,
							isNeedReq:ret.isneedreq,
							msgTextTemp2:ret.msgtexttemp2,
							workType1:ret.worktype1,
							offerWorkType2:ret.offerworktype2,
							workType12:ret.worktype12,
							jobType:ret.jobtype,
							title:ret.title,
							isAutoJob:ret.isautojob,
			       	  		mm_umState:"${data.m_umState}",
			       	  		tempNo:"${data.no }",
			       	  		lastUpdate:ret.lastupdate,
			       	  		lastUpdatedBy:ret.lastupdatedby
						};
					Global.Dialog.showDialog("tipEventView",{
			       		title:"提醒事项--查看",
			       	  	url:"${ctx}/admin/gcjdmb/goView_tipEvent",
			       	  	postData:postData,
			       	  	height: 650,
			       	  	width:750
			       	});
				}
            },
			onSortCol:function(index, iCol, sortorder){
				var rows = $("#dataTable_tipEvent").jqGrid("getRowData");
	   			rows.sort(function (a, b) {
	   				var reg = /^[0-9]+.?[0-9]*$/;
					if(reg.test(a[index])){
	   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
					}else{
	   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
					}  
	   			});    
	   			Global.JqGrid.clearJqGrid("dataTable_tipEvent"); 
	   			$.each(rows,function(k,v){
					Global.JqGrid.addRowData("dataTable_tipEvent", v);
				});
			}
		});
		$("#gbox_dataTable_tipEvent").css({"width":"1200px"}); 
	});
	function tipEventAdd(){
		var prjItems = $("#dataTable_tempDetail").jqGrid("getCol", "prjitem");

       	Global.Dialog.showDialog("tipEventAdd",{
       		title:"提醒事项--增加",
       	  	url:"${ctx}/admin/gcjdmb/goSave_tipEvent",
       	  	postData:{
       	  		mm_umState:"${data.m_umState}",
       	  		tempNo:"${data.no }",
       	  		nowPk:$("#nowPkTipEvent").val(),
       	  		prjItems:prjItems.join(",")
       	  	},
       	  	height: 650,
       	  	width:750,
       	  	returnFun:function(data){
       	  		if(data && data.length > 0){
					Global.JqGrid.addRowData("dataTable_tipEvent", data[0]);
					autoSortTipEvent();
					$("#nowPkTipEvent").val(data[0].pk);
       	  		}
       	  		getProgTempAlarm();
       	  	}
       	});
	}
	function tipEventFastAdd(){
		var prjItems = $("#dataTable_tempDetail").jqGrid("getCol", "prjitem");

       	Global.Dialog.showDialog("tipEventFastAdd",{
       		title:"提醒事项--快速增加",
       	  	url:"${ctx}/admin/gcjdmb/goFastSave_tipEvent",
       	  	postData:{
       	  		mm_umState:"${data.m_umState}",
       	  		tempNo:"${data.no }",
       	  		nowPk:$("#nowPkTipEvent").val(),
       	  		prjItems:prjItems.join(",")
       	  	},
       	  	height: 650,
       	  	width:750,
       	  	returnFun:function(data){
       	  		if(data && data.length > 0){
       	  			$.each(data, function(index, value){
						Global.JqGrid.addRowData("dataTable_tipEvent", value);
						$("#nowPkTipEvent").val(value.pk);
       	  			});
					autoSortTipEvent();
       	  		}
       	  		getProgTempAlarm();
       	  	}
       	});
	}
	function tipEventCopy(){
		var id = $("#dataTable_tipEvent").jqGrid("getGridParam", "selrow");
		var ret;
		if(id){
			ret = $("#dataTable_tipEvent").jqGrid("getRowData", id);
		}
		if(ret){
			var prjItems = $("#dataTable_tempDetail").jqGrid("getCol", "prjitem");
			var postData={
					prjItem:ret.prjitem,
					dayType:ret.daytype,
					addDay:ret.addday,
					czybh:ret.czybh,
					remindCzyType:ret.remindczytype,
					czyDescr:ret.czydescr,
					remindCzyTypeDescr:ret.remindczytypedescr,
					msgTextTemp:ret.msgtexttemp,
					role:ret.role,
					type:ret.type,
					dealDay:ret.dealday,
					completeDay:ret.completeday,
					itemType1:ret.itemtype1,
					itemType2:ret.itemtype2,
					isNeedReq:ret.isneedreq,
					msgTextTemp2:ret.msgtexttemp2,
					workType1:ret.worktype1,
					offerworktype2:ret.offerworktype2,
					workType12:ret.worktype12,
					jobType:ret.jobtype,
					title:ret.title,
					isAutoJob:ret.isautojob,
	       	  		mm_umState:"${data.m_umState}",
	       	  		tempNo:"${data.no }",
	       	  		rowId:id,
       	  			nowPk:$("#nowPkTipEvent").val(),
       	  			prjItems:prjItems.join(","),
       	  			roleDescr:ret.roledescr
				};
	       	Global.Dialog.showDialog("tipEventCopy",{
	       		title:"提醒事项--复制",
	       	  	url:"${ctx}/admin/gcjdmb/goCopy_tipEvent",
	       	  	postData:postData,
	       	  	height: 650,
	       	  	width:750,
	       	  	returnFun:function(data){
	       	  		if(data && data.length > 0){
						Global.JqGrid.addRowData("dataTable_tipEvent", data[0]);
						autoSortTipEvent();
						$("#nowPkTipEvent").val(data[0].pk);
	       	  		}
	       	  		getProgTempAlarm();
	       	  	}
	       	});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	}

	function tipEventUpdate(){
		var id = $("#dataTable_tipEvent").jqGrid("getGridParam", "selrow");
		var ret;
		if(id){
			ret = $("#dataTable_tipEvent").jqGrid("getRowData", id);
		}
		if(ret){
			var prjItems = $("#dataTable_tempDetail").jqGrid("getCol", "prjitem");
			var postData={
					pk:ret.pk,
					prjItem:ret.prjitem,
					dayType:ret.daytype,
					addDay:ret.addday,
					msgTextTemp:ret.msgtexttemp,
					role:ret.role,
					type:ret.type,
					dealDay:ret.dealday,
					completeDay:ret.completeday,
					itemType1:ret.itemtype1,
					itemType2:ret.itemtype2,
					isNeedReq:ret.isneedreq,
					msgTextTemp2:ret.msgtexttemp2,
					workType1:ret.worktype1,
					offerWorkType2:ret.offerworktype2,
					workType12:ret.worktype12,
					jobType:ret.jobtype,
					title:ret.title,
					isAutoJob:ret.isautojob,
	       	  		mm_umState:"${data.m_umState}",
	       	  		tempNo:"${data.no }",
	       	  		rowId:id,
       	  			nowPk:$("#nowPkTipEvent").val(),
       	  			prjItems:prjItems.join(",")
				};
	       	Global.Dialog.showDialog("tipEventUpdate",{
	       		title:"提醒事项--编辑",
	       	  	url:"${ctx}/admin/gcjdmb/goUpdate_tipEvent",
	       	  	postData:postData,
	       	  	height: 650,
	       	  	width:750,
	       	  	returnFun:function(data){
	       	  		if(data && data.length > 0){
	       	  			$("#dataTable_tipEvent").jqGrid("setRowData", data[0].rowId, data[0]);
						autoSortTipEvent();
	       	  		}
	       	  		getProgTempAlarm();
	       	  	}
	       	});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	}
	function tipEventDel(){
		var id = $("#dataTable_tipEvent").jqGrid("getGridParam", "selrow");
		var ret;
		if(id){
			ret = $("#dataTable_tipEvent").jqGrid("getRowData", id);
		}
		if(ret){
			art.dialog({
				content:"是否要删除数据",
				ok:function(){
					if($("#m_umState").val() == "E"){
						ajaxPost("${ctx}/admin/gcjdmb/doDelProgTempAlarm", {
							pk:ret.pk
						}, function(data){
							if(data.rs){
								$("#isTipExit").val("1");
								autoSortTipEvent();
							}else{
								art.dialog({
									content:data.msg
								});
							}
						});
					}else{
						$("#dataTable_tipEvent").jqGrid("delRowData", id);
						$("#isTipExit").val("1");
						autoSortTipEvent();
					}
					getProgTempAlarm();
				},
				cancel:function(){}
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	}
	function tipEventView(){
		var id = $("#dataTable_tipEvent").jqGrid("getGridParam", "selrow");
		var ret;
		if(id){
			ret = $("#dataTable_tipEvent").jqGrid("getRowData", id);
		}
		if(ret){
			var postData={
					pk:ret.pk,
					prjItem:ret.prjitem,
					dayType:ret.daytype,
					addDay:ret.addday,
					czybh:ret.czybh,
					remindCzyType:ret.remindczytype,
					czyDescr:ret.czydescr,
					remindCzyTypeDescr:ret.remindczytypedescr,
					msgTextTemp:ret.msgtexttemp,
					role:ret.role,
					type:ret.type,
					dealDay:ret.dealday,
					completeDay:ret.completeday,
					itemType1:ret.itemtype1,
					itemType2:ret.itemtype2,
					isNeedReq:ret.isneedreq,
					msgTextTemp2:ret.msgtexttemp2,
					workType1:ret.worktype1,
					offerWorkType2:ret.offerworktype2,
					workType12:ret.worktype12,
					jobType:ret.jobtype,
					title:ret.title,
					isAutoJob:ret.isautojob,
	       	  		mm_umState:"${data.m_umState}",
	       	  		tempNo:"${data.no }",
	       	  		lastUpdate:ret.lastupdate,
	       	  		lastUpdatedBy:ret.lastupdatedby
				};
	       	Global.Dialog.showDialog("tipEventView",{
	       		title:"提醒事项--查看",
	       	  	url:"${ctx}/admin/gcjdmb/goView_tipEvent",
	       	  	postData:postData,
	       	  	height: 650,
	       	  	width:750
	       	});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	}
	function autoSortTipEvent(){
		var rows = $("#dataTable_tipEvent").jqGrid("getRowData");
		rows.sort(function (a, b) {
			var result = 0;
			if(parseInt(a["daytype"])-parseInt(b["daytype"]) == 0){
				if(parseInt(a["addday"])-parseInt(b["addday"]) == 0){
					if(parseInt(a["pk"])-parseInt(b["pk"]) != 0){
						result = parseInt(a["pk"])-parseInt(b["pk"]);
					}
				}else{
					result = parseInt(a["addday"])-parseInt(b["addday"]);
				}
			}else{
				result = parseInt(a["daytype"])-parseInt(b["daytype"]);
			}
			return result;
		});    
		Global.JqGrid.clearJqGrid("dataTable_tipEvent"); 
		$.each(rows, function(k,v){
			Global.JqGrid.addRowData("dataTable_tipEvent", v);
		});
	}
	
	function getProgTempAlarm(){
		if($("#m_umState").val() == "E"){
			$("#dataTable_tipEvent").jqGrid("setGridParam",{
				url:"${ctx}/admin/gcjdmb/goJqGridProgTempAlarm?no="+$("#no").val(),
				page:1,
				sortname:''
			}).trigger("reloadGrid");
		}
	}
</script>
<div class="btn-panel" style="margin-top:2px">
	<div class="btn-group-sm"  >
		<button id="tipEventAddBtn" type="button" class="btn btn-system " onclick="tipEventAdd()">新增</button>
		<button id="tipEventFastAddBtn" type="button" class="btn btn-system " onclick="tipEventFastAdd()">快速新增</button>
		<button id="tipEventCopyBtn" type="button" class="btn btn-system " onclick="tipEventCopy()">复制</button>
		<button id="tipEventUpdateBtn" type="button" class="btn btn-system " onclick="tipEventUpdate()">编辑</button>
		<button id="tipEventDelBtn" type="button" class="btn btn-system " onclick="tipEventDel()">删除</button>
		<button id="tipEventViewBtn" type="button" class="btn btn-system " onclick="tipEventView()">查看</button>
	</div>
</div>	
<table id="dataTable_tipEvent"></table>
