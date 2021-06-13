<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
	$(function(){
		//查看时部分按钮不可用
		var m_umState=$("#m_umState").val();
		if(m_umState=="V"){
			$("#del").attr("disabled",true);
			$("#quickSave").attr("disabled",true);
			$("#up").attr("disabled",true);
			$("#down").attr("disabled",true);
			$("#insert").attr("disabled",true);
		}
		var lastCellRowId;
		/**初始化表格*/
		var gridOption ={
				url:"${ctx}/admin/itemBatchDetail/getItemBatchDetailByIbdNo",
				postData:{ibdno:"${itemBatchHeader.no}",itemExpired:"1"},
			    rowNum:10000000,
				height:280,
				styleUI: 'Bootstrap', 
				onSortColEndFlag:true,
				colModel : [
					{name: "itcode", index: "itcode", width: 72, label: "材料编号", sortable: true, align: "left"},
					{name: "itcodedescr", index: "itcodedescr", width: 241, label: "材料名称", sortable: true, align: "left"},
					{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
					{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right", sum: true,editrules:{number:true},editable:true},
					{name: "uomdescr", index: "uomdescr", width: 60, label: "单位", sortable: true, align: "left"},
					{name: "price", index: "price", width: 80, label: "单价", sortable: true, align: "right"},
					{name: "beflineamount", index: "beflineamount", width: 90, label: "总价", sortable: true, align: "right", sum: true},
					{name: "dispseq", index: "dispseq", width: 60, label: "顺序", sortable: true, align: "left",editable:true},
					{name: "remarks", index: "remarks", width: 140, label: "材料描述", sortable: true, align: "left",editable:true},
					{name: "oldreqpk", index: "oldreqpk", width: 70, label: "原需求PK", sortable: true, align: "left", hidden: true},
					{name: "olditemdescr", index: "olditemdescr", width: 100, label: "原材料", sortable: true, align: "left", hidden: true},
					{name: "preplanareadescr", index: "preplanareadescr", width: 60, label: "空间", sortable: true, align: "left", hidden: true},
					{name: "areaattr", index: "areaattr", width: 70, label: "区域分类", sortable: true, align: "left", hidden: true},
					{name: "opertypedescr", index: "opertypedescr", width: 80, label: "操作类型", sortable: true, align: "left", hidden: true},
					{name: "ibdno", index: "ibdno", width: 122, label: "批次编号", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 126, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
					{name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable: true, align: "left"}
				], 
				onSortCol:function(index, iCol, sortorder){
						var rows = $("#detailDataTable").jqGrid("getRowData");
			   			rows.sort(function (a, b) {
			   				var reg = /^[0-9]+.?[0-9]*$/;
							if(reg.test(a[index])){
			   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
							}else{
			   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
							}  
			   			});    
			   			Global.JqGrid.clearJqGrid("detailDataTable"); 
			   			$.each(rows,function(k,v){
							Global.JqGrid.addRowData("detailDataTable", v);
						});
					},
				beforeSelectRow: function (id) {
		          setTimeout(function () {
		            relocate(id, 'detailDataTable');
		          }, 100);
		        },
				gridComplete:function(){
		   		  	   var records = $("#detailDataTable").jqGrid('getGridParam', 'records'); //获取数据总条数
				 	   if(records==0){//表格为空时可以选材料类型1
						   $("#itemType1").removeAttr("disabled");
					   }else{
					   	   $("#itemType1").attr("disabled",true);
					   }
					   var ids=$("#detailDataTable").jqGrid('getDataIDs');
					   for(var i=0;i<ids.length;i++){
					   		$("#detailDataTable").jqGrid('setCell',ids[i],"dispseq",i);
					   }
					   if($("#m_umState").val()=="V"&&"${itemBatchHeader.batchType}"=="1"){
							$("#detailDataTable").jqGrid('showCol', "oldreqpk");
							$("#detailDataTable").jqGrid('showCol', "olditemdescr");
							$("#detailDataTable").jqGrid('showCol', "preplanareadescr");
							$("#detailDataTable").jqGrid('showCol', "areaattr");
							$("#detailDataTable").jqGrid('showCol', "opertypedescr");  
					   }
	            },
	            beforeSaveCell:function(rowId,name,val,iRow,iCol){
			    		var ret = $("#detailDataTable").jqGrid('getRowData', rowId);
			    		var beflineamount;
			    		//计算总价
			    		if(!isNaN(val)){
				    		if(val!=""){
				    			beflineamount=parseFloat(val)*parseFloat(ret.price).toFixed(2);
				    		}else{
				    			beflineamount=" ";
				    		}
			    		}else{
			    			beflineamount=ret.beflineamount;
			    		}
			    		$("#detailDataTable").jqGrid('setCell',rowId,"beflineamount",beflineamount);
	    	  }
	 		};
	       //初始化材料批次明细
		   Global.JqGrid.initEditJqGrid("detailDataTable",gridOption);
		 
		   
	});
	//快速新增
	function quickSave(){
		var itemType1=$("#itemType1").val();
			if(itemType1==""){
				art.dialog({
					content:"请选择材料类型！",
					width: 200
				});
				return;
			}
		var itcodes = $("#detailDataTable").getCol("itcode", false).join(",");
		Global.Dialog.showDialog("goQuickSave",{
						title:"材料批次--快速新增",
						url:"${ctx}/admin/itemBatchHeader/goQuickSave",
						postData : {
							'itcodes':itcodes,'itemType1':itemType1,'custType':$("#custType").val(),
						},
					    height:750,
					    width:1200,
						returnFun:function(data){
									if(data.length > 0){
										$("#itemType1").attr("disabled",true);
										var ids = $("#detailDataTable").jqGrid("getDataIDs");
										$.each(data, function(i,rowData){
											rowData['dispseq']=i+ids.length;
											$("#detailDataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
										});	
									}
								}
					});
	}
	//插入
	function insert(){
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
		var posi=id-1;
		var ids = $("#detailDataTable").jqGrid("getDataIDs");
		var ret = selectDataTableRow("detailDataTable");
		var rowData = $("#detailDataTable").jqGrid('getRowData');
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		var itemType1=$("#itemType1").val();
			if(itemType1==""){
				art.dialog({
					content:"请选择材料类型！",
					width: 200
				});
				return;
			}
		var itcodes = $("#detailDataTable").getCol("itcode", false).join(",");
		Global.Dialog.showDialog("goQuickSave",{
						title:"材料批次--快速新增",
						url:"${ctx}/admin/itemBatchHeader/goQuickSave",
						postData : {
							'itcodes':itcodes,'itemType1':itemType1,'itCode':ret.itcode
						},
					    height:750,
					    width:1200,
						returnFun:function(data){
									if(data.length > 0){
										$("#itemType1").attr("disabled",true);
									 	//插入row
									 	$.each(data, function(i,v){
									 		rowData.splice(posi++,0,v);	
										});
										//重置表格
										$("#detailDataTable").jqGrid("clearGridData");
										$.each(rowData, function(i,r){
									 		r['dispseq']=i;//设置顺序
											$("#detailDataTable").jqGrid("addRowData", i+1, r);
										});
										 
									}
								}
					});
	}
	//删除
	function del(){
		 	var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
			if(!id){
				art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
				return false;
			}
			art.dialog({
				 content:"是否确认要删除",
				 lock: true,
				 width: 200,
				 height: 100,
				 ok: function () {
					Global.JqGrid.delRowData("detailDataTable",id);
					var records = $("#detailDataTable").jqGrid('getGridParam', 'records'); //获取数据总条数
					if(records==0){//表格为空时可以选材料类型1
						$("#itemType1").removeAttr("disabled");
					}
				},
				cancel: function () {
						return true;
				}
			}); 
	}
	//向上
	function up(){
		var rowId = $("#detailDataTable").jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)-1;
		if(rowId){
			if(rowId>1){
			    var ret1 = $("#detailDataTable").jqGrid("getRowData", rowId);
				var ret2 = $("#detailDataTable").jqGrid("getRowData", replaceId);
				replace(rowId,replaceId,"detailDataTable");
				scrollToPosi(replaceId,"detailDataTable");
				$("#detailDataTable").setCell(rowId,"dispseq",ret1.dispseq);
				$("#detailDataTable").setCell(replaceId,"dispseq",ret2.dispseq);
				$("#dataChanged").val("1");
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	//向下
	function down(){
		var rowId = $("#detailDataTable").jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)+1;
		var rowNum = $("#detailDataTable").jqGrid("getGridParam","records");
		if(rowId){
			if(rowId<rowNum){
				var ret1 = $("#detailDataTable").jqGrid("getRowData", rowId);
				var ret2 = $("#detailDataTable").jqGrid("getRowData", replaceId);
				scrollToPosi(replaceId,"detailDataTable");
				replace(rowId,replaceId,"detailDataTable");
				$("#detailDataTable").setCell(rowId,"dispseq",ret1.dispseq);
				$("#detailDataTable").setCell(replaceId,"dispseq",ret2.dispseq);
				$("#dataChanged").val("1");
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="btn-group-xs">
				<button style="align:left" type="button" class="btn btn-system "
					id="quickSave" onclick="quickSave()">
					<span>快速新增 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					id="insert" onclick="insert()">
					<span>插入</span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					id="up" onclick="up()">
					<span>向上</span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					id="down" onclick="down()">
					<span>向下</span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					id="del" onclick="del()">
					<span>删除</span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					onclick="doExcelNow('材料批次明细','detailDataTable','form')">
					<span>导出excel </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="detailDataTable"></table>
	</div>
</div>



