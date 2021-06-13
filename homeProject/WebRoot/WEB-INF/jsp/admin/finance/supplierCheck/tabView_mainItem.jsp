<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
	$(function(){
		var url = "#";
		if($("#m_umState").val() != "A"){
			url = "${ctx}/admin/supplierCheck/goJqGridMainItem?checkOutNo="+$("#no").val();
		}
		initMainItemDataTable(url, 200);
		$("#mainItemDataTable").jqGrid("setFrozenColumns");	
	});
	
	function initMainItemDataTable(url, height){
		Global.JqGrid.initEditJqGrid("mainItemDataTable", {
			height:height,
			url:url,
			rowNum: 10000,
			styleUI:"Bootstrap",
			onSortColEndFlag:true,
			mustUseEdit:true,
			searchBtn:true,
			cellsubmit:'clientArray',
			colModel:[ 
                {name: "checkbox", index: "checkbox", width: 40, label: "<input type='checkbox'/>", sortable:false, align: "center", title: false, formatter: checkbox, frozen: true},      
				{name: "no", index: "no", width: 75, label: "采购单号", sortable: true, align: "left", frozen: true},
				{name: "appno", index: "appno", width: 75, label: "领料单号", sortable: true, align: "left", frozen: true},
				{name: "cmpname", index: "cmpname", width: 70, label: "签约公司", sortable: true, align: "left", frozen: true},
				{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left", frozen: true},
				{name: "custtype", index: "custtype", width: 80, label: "客户类型", sortable: true, align: "left", frozen: true,hidden:true},
				{name: "region", index: "region", width: 60, label: "片区", sortable: true, align: "left", frozen: true,hidden:true},
				{name: "regiondescr", index: "regiondescr", width: 60, label: "片区", sortable: true, align: "left", frozen: true},
				{name: "address", index: "address", width: 220, label: "楼盘", sortable: true, align: "left", frozen: true},
				{name: "documentno", index: "documentno", width: 65, label: "档案号", sortable: true, align: "left",frozen: true},
				
				{name: "ischeckout", index: "ischeckout", width: 70, label: "是否结帐", sortable: true, align: "left", hidden: true},
				{name: "amount", index: "amount", width: 70, label: "amount", sortable: true, align: "right", sum: true, hidden: true},
				{name: "showamount", index: "showamount", width: 70, label: "材料金额", sortable: true, align: "right", sum: true},
				{name: "splamount", index: "splamount", width: 70, label: "对账金额", sortable: true, align: "right"},
				{name: "diffamount", index: "diffamount", width: 50, label: "差额", sortable: true, align: "right"},
				{name: "chaochue", index: "chaochue", width: 60, label: "超出额", sortable: true, align: "right", sum: true},
				{name: "xmjljsj", index: "xmjljsj", width: 120, label: "项目经理结算价", sortable: true, align: "right", sum: true},
				{name: "projectothercost", index: "projectothercost", width: 110, label: "项目经理调整", sortable: true, align: "right", editable:true, editrules: {number:true,required:true}},
				{name: "xmjljszj", index: "xmjljszj", width: 120, label: "项目经理结算总价", sortable: true, align: "right", sum: true},
				{name: "othercost", index: "othercost", width: 70, label: "其它费用", sortable: true, align: "right", sum: true, editable:true, editrules: {number:true,required:true}},
				{name: "processcost", index: "processcost", width: 90, label: "标准其它费用", sortable: true, align: "right"},
				{name: "othercostadj", index: "othercostadj", width: 90, label: "其它费用调整", sortable: true, align: "right", sum: true, editable:true, editrules: {number:true,required:true}},
				{name: "intinstallfee", index: "intinstallfee", width: 90, label: "集成安装费", sortable: true, align: "right", sum: true},
				{name: "sumamount", index: "sumamount", width: 60, label: "总金额", sortable: true, align: "right", sum: true},
				{name: "remainamount", index: "remainamount", width: 70, label: "应付余额", sortable: true, align: "right", sum: true,formatter:"number", formatoptions:{decimalPlaces: 2}},//formatter:function(cellvalue, options, rowObject){return myRound(cellvalue*1.00,2);},
				{name: "firstamount", index: "firstamount", width: 70, label: "已付定金", sortable: true, align: "right", sum: true},
				{name: "secondamount", index: "secondamount", width: 80, label: "已付到货款", sortable: true, align: "right", hidden: true},
				{name: "sumsaleamount", index: "sumsaleamount", width: 70, label: "销售金额", sortable: true, align: "right", sum: true},
				{name: "payremark", index: "payremark", width: 120, label: "记账说明", sortable: true, align: "left", editable:true},
				{name: "checkconfirmremarks", index: "checkconfirmremarks", width: 120, label: "结算审核说明", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
				{name: "isservicedescr", index: "isservicedescr", width: 90, label: "是否服务产品", sortable: true, align: "left"},
				{name: "issetitemdescr", index: "issetitemdescr", width: 70, label: "套餐材料", sortable: true, align: "left"},
				{name: "iscupboarddescr", index: "iscupboarddescr", width: 70, label: "是否橱柜", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 70, label: "材料类型2", sortable: true, align: "left"},
				{name: "warehousedesc", index: "warehousedesc", width: 70, label: "仓库", sortable: true, align: "left"},
				{name: "typedescr", index: "typedescr", width: 90, label: "采购单类型", sortable: true, align: "left"},
				{name: "date", index: "date", width: 90, label: "采购日期", sortable: true, align: "left", formatter: formatTime},
				{name: "checkstatusdescr", index: "checkstatusdescr", width: 90, label: "客户结算状态", sortable: true, align: "left"},
                {name : "splstatusdescr",index : "splstatusdescr",width : 80,label:'供应商状态',sortable : true,align : "left"},
                {name : "sendnum",index : "sendnum",width : 70,label:'配送次数',sortable : true,align : "right"},
                {name: "sourcedescr", index: "sourcedescr", width: 70, label: "客户来源", sortable: true, align: "left"},
                
			    {name : "splstatus",index : "splstatus",width : 80,label:'供应商状态',sortable : true,align : "left",hidden:true},
				{name: "checkseq", index: "checkseq", width: 160, label: "checkseq", sortable: true, align: "left", hidden: true},
				{name: "status", index: "status", width: 160, label: "status", sortable: true, align: "left", hidden: true},
				{name: "type", index: "type", width: 160, label: "type", sortable: true, align: "left", hidden: true},
				{name: "whcode", index: "whcode", width: 160, label: "whcode", sortable: true, align: "left", hidden: true},
				{name: "supplier", index: "supplier", width: 160, label: "supplier", sortable: true, align: "left", hidden: true},
				{name: "checkoutno", index: "checkoutno", width: 160, label: "checkoutno", sortable: true, align: "left", hidden: true},
				{name: "itemtype1", index: "itemtype1", width: 160, label: "itemtype1", sortable: true, align: "left", hidden: true},
				{name: "delivtype", index: "delivtype", width: 160, label: "delivtype", sortable: true, align: "left", hidden: true},
				{name: "isservice", index: "isservice", width: 160, label: "isservice", sortable: true, align: "left", hidden: true},
				{name: "custcode", index: "custcode", width: 160, label: "custcode", sortable: true, align: "left", hidden: true}

			],
			loadonce: true,
			gridComplete:function(){
				if($("#m_umState").val() != "A"){
					setDBGrid();
					if($("#firstFlag").val()=="1"){
						$("#firstFlag").val("0");
						var maxCheckSeq = $("#maxCheckSeq").val();
						var checkseqs = $("#mainItemDataTable").jqGrid("getCol", "checkseq");
				 		for(var i = 0;i < checkseqs.length;i++){
							if(parseInt(maxCheckSeq) < parseInt(checkseqs[i])){
								maxCheckSeq = checkseqs[i];
							}
						} 
						$("#maxCheckSeq").val(maxCheckSeq);
					}
					var rowData = $("#mainItemDataTable").jqGrid('getRowData');
					$.each(rowData,function(k,v){
		             	if(v.splstatus == "2" && ($("#m_umState").val() == "A" || $("#m_umState").val() == "M") ){ 
		             		$("#mainItemDataTable").jqGrid('setCell', k+1, 'projectothercost', '', 'not-editable-cell');
		             		$("#mainItemDataTable").jqGrid('setCell', k+1, 'othercost', '', 'not-editable-cell');
		             		$("#mainItemDataTable").jqGrid('setCell', k+1, 'othercostadj', '', 'not-editable-cell');	 
		             	}
		            })
				}
                // $(".ui-jqgrid-bdiv").scrollTop(0);
	            frozenHeightReset("mainItemDataTable");
			},
	
			beforeSelectRow:function(id){
				setTimeout(function(){
					relocate(id, "mainItemDataTable");
					$("#mainItemDataTable_frozen tr[class*='success'] td").removeClass("success");
				 	$("#mainItemDataTable_frozen tr[class*='success']").removeClass("success").attr("aria-selected","false");
				    $("#mainItemDataTable_frozen tr[id="+id+"]").addClass("success");
				},10);
			},
			
			beforeSaveCell:function(rowId, name, val, iRow, iCol){
				var ret = $("#mainItemDataTable").jqGrid("getRowData", rowId);
				ret[name]=val;
				var othercost = !ret.othercost || ret.othercost=="" ? 0 : parseFloat(ret.othercost);
				var othercostadj = !ret.othercostadj || ret.othercostadj=="" ? 0 : parseFloat(ret.othercostadj);
				var amount = !ret.amount || ret.amount=="" ? 0 : parseFloat(ret.amount);
				var xmjljsj = !ret.xmjljsj || ret.xmjljsj=="" ? 0 : parseFloat(ret.xmjljsj);
				var projectothercost = !ret.projectothercost || ret.projectothercost=="" ? 0 : parseFloat(ret.projectothercost);
				var firstamount = !ret.firstamount || ret.firstamount=="" ? 0 : parseFloat(ret.firstamount);
				var secondamount = !ret.secondamount || ret.secondamount=="" ? 0 : parseFloat(ret.secondamount);
				
				if(ret.type == "R"){
					amount = amount * -1;
				}
				
				$("#mainItemDataTable").jqGrid("setCell", rowId, "sumamount", myRound(othercost+othercostadj+amount, 2));
				$("#mainItemDataTable").jqGrid("setCell", rowId, "xmjljszj", myRound(othercost+othercostadj+xmjljsj+projectothercost));
				$("#mainItemDataTable").jqGrid("setCell", rowId, "remainamount", myRound(othercost+othercostadj+amount-firstamount-secondamount, 2));
				
			},
			afterSaveCell:function(rowId, cellName, value, iRow, iCol){
				setPayAmount();
			},
			onCellSelect:function(rowid,iCol,cellcontent,e){
				if($("#m_umState").val() != "C"){
                	$("#mainItemDataTable").jqGrid("setCell", rowid, "othercost", "", "not-editable-cell");  
				}
				if($("#m_umState").val() == "B"){
                	$("#mainItemDataTable").jqGrid("setCell", rowid, "othercostadj", "", "not-editable-cell");  
				}
				var ret = $("#mainItemDataTable").jqGrid("getRowData", rowid);
				if(ret.splstatus == "2" && ($("#m_umState").val() == "A" || $("#m_umState").val() == "M") ){ 
             		$("#mainItemDataTable").jqGrid('setCell', rowid, 'projectothercost', '', 'not-editable-cell');
             		$("#mainItemDataTable").jqGrid('setCell', rowid, 'othercost', '', 'not-editable-cell');
             		$("#mainItemDataTable").jqGrid('setCell', rowid, 'othercostadj', '', 'not-editable-cell');	 
             	}
			}
		}); 
		setSelectAllCheckbox();
	}
	
	function add(){
		if($("#splCode").val() == ""){
			art.dialog({
				content:"请选择供应商编号"
			});
			return;
		}
		var nos=$("#mainItemDataTable").getCol("no", false);

       	Global.Dialog.showDialog("addPurchase",{
			title:"新增采购单结算",
       	 	url:"${ctx}/admin/supplierCheck/goAddPurchase",
       	  	postData:{
				checkOutNo:$("#no").val(),
				splName:$("#splCodeDescr").val(),
				splCode:$("#splCode").val(),
				nos:nos,
				maxCheckSeq:$("#maxCheckSeq").val()
       	  	},
       	  	height: 600,
       	  	width:1200,
			returnFun:function(data){
				if(data.selectRows.length > 0){
		   			$("#openComponent_supplier_splCode").attr("readonly", true);
					$("#openComponent_supplier_splCode").next().attr("data-disabled", true).css({
						"color":"#888"
					});
		   			data.selectRows.sort(function (a, b) {
		   				//return a.date.localeCompare(b.date);
		   				return parseInt(a.checkseq)-parseInt(b.checkseq);
		   			});
		   			var ids = $("#mainItemDataTable").jqGrid("getDataIDs");
		   			var max = 1;
		   			if(ids.length > 0){
		   				max = parseInt(ids[ids.length-1])+1;
		   			}
					$.each(data.selectRows, function(i,rowData){
						$("#mainItemDataTable").jqGrid("addRowData", (max+i), rowData);
					});	
					$("#maxCheckSeq").val(data.maxCheckSeq);
					setDBGrid();
					setPayAmount();
				}
			}
       	}); 
	}
	/*
	function del(){
		var id = $("#mainItemDataTable").jqGrid("getGridParam", "selrow");
		var ret = $("#mainItemDataTable").jqGrid("getRowData", id);
		var ids = $("#mainItemDataTable").jqGrid("getDataIDs");
		var nextId = 0;
		if(ids.length > 1){
			for(;nextId < ids.length;nextId++){
				if(ids[nextId] == id){
					nextId++;
					break;
				}
			}
			if(nextId == ids.length){
				nextId = ids[0];
			}
		}else{
			nextId = -1;
		}
		if(ret && id){
			art.dialog({
				content:"是否确认要删除采购单["+ret.no.trim()+"]的结算",
				ok:function(){
					if(ret.checkseq  == parseInt($("#maxCheckSeq").val())){
						$("#maxCheckSeq").val(parseInt($("#maxCheckSeq").val())-1);
					}
					$("#mainItemDataTable").jqGrid("delRowData", id);
            		setDBGrid();
					if(nextId != -1){
						setTimeout(function(){
							relocate(ids[nextId], "mainItemDataTable");
						},10);
					}
            		setPayAmount();
            		if($("#m_umState").val() == "A"){
            			var nos = $("#mainItemDataTable").jqGrid("getDataIDs");
            			if(nos.length == 0){
				   			$("#openComponent_supplier_splCode").attr("readonly", false);
							$("#openComponent_supplier_splCode").next().attr("data-disabled", false).css({
								"color":""
							});
            			}
            		}
				},
				cancel:function(){}
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	}
	*/
    function del() {
		
        var grid = $('#mainItemDataTable_frozen'); 
	    var selectedRows = $('td input.ibox:checked', grid);
	    var ids = [];
	    selectedRows.each(function(index, element) {
	        var elementId = $(element).parent().parent().prop('id')
	        var rowid = parseInt(elementId.substring(elementId.lastIndexOf('_') + 1))
	        ids.unshift(rowid)
	    })
	    
	    if (!ids.length) {
	        art.dialog({content: "没有要删除的记录"})
	        return
	    }
	    art.dialog({
			content:"是否要删除以下勾选项",
			ok:function(){
				for (var i = 0; i < ids.length; i++) {
			    	$('#mainItemDataTable').delRowData(ids[i])
			    }
				setDBGrid();
				setPayAmount();
				if($("#m_umState").val() == "A"){
        			var nos = $("#mainItemDataTable").jqGrid("getDataIDs");
        			if(nos.length == 0){
			   			$("#openComponent_supplier_splCode").attr("readonly", false);
						$("#openComponent_supplier_splCode").next().attr("data-disabled", false).css({
							"color":""
						});
        			}
        		}
			},
			cancel:function(){}
		});
	    
	}
	function view(){
		var ret = selectDataTableRow("mainItemDataTable");

		if(ret){
			var title = "";
			if(ret.type == "S"){
				if(ret.delivtype == "1"){
					title = "采购单--查看";
				}else{
					title = "采购到工地--查看";
				}
			}else{
				if(ret.delivtype == "1"){
					title = "采购退回--查看";
				}else{
					title = "工地退回--查看";
				}
			}
			var url = "";
			
			if(ret.delivtype=="1"){
				url="${ctx}/admin/purchase/goViewNew";
			}else{
				url="${ctx}/admin/purchase/goViewNew2";
			}
	       	Global.Dialog.showDialog("PurhaseView",{
				title:title,
	       	 	url:url,
	       	  	postData:{
	       	  		id:ret.no,
	       	  		fromPage:"supplierCheck",
	       	  		isService:ret.isservice
	       	  	},
	       	  	height:730,
	       	  	width:1250
	       	}); 
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	}
	function viewIntInstall(){
		var ret = selectDataTableRow("mainItemDataTable");

		if(ret){

	       	Global.Dialog.showDialog("intInstall",{
				title:"集成安装费明细",
	       	 	url:"${ctx}/admin/supplierCheck/goIntInstall",
	       	  	postData:{
	       	  		custCode:ret.custcode
	       	  	},
	       	  	height:500,
	       	  	width:900
	       	}); 
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	}
	function up(){
		var id = $("#mainItemDataTable").jqGrid("getGridParam", "selrow");
		var ids = $("#mainItemDataTable").jqGrid("getDataIDs");		
		if(id && ids.length >= 2){
			var index = 0;
			for(;index < ids.length;index++){
				if(ids[index] == id){
					index--;
					break;
				}
			}
			if(index == -1){
				toId = ids[ids.length-1];
			}else{
				toId = ids[index];
			}
			changeRow(id, toId);
			relocate(toId, "mainItemDataTable");
		}
		setDBGrid();
	}
	function down(){
		var id = $("#mainItemDataTable").jqGrid("getGridParam", "selrow");
		var ids = $("#mainItemDataTable").jqGrid("getDataIDs");		
		if(id && ids.length >= 2){
			var index = 0;
			for(;index < ids.length;index++){
				if(ids[index] == id){
					index++;
					break;
				}
			}
			if(index == ids.length){
				toId = ids[0];
			}else{
				toId = ids[index];
			}
			changeRow(id, toId);
			relocate(toId, "mainItemDataTable");
		}
		setDBGrid();
	}
	
	function changeRow(id, toId){
		var checkSeq = parseInt($("table[id=mainItemDataTable] tr[id="+id+"] td[aria-describedby=mainItemDataTable_checkseq]").attr("title"));
		var toCheckSeq = parseInt($("table[id=mainItemDataTable] tr[id="+toId+"] td[aria-describedby=mainItemDataTable_checkseq]").attr("title"));
		$("table[id=mainItemDataTable] tr[id="+id+"] td[aria-describedby=mainItemDataTable_checkseq]").attr("title", toCheckSeq).html(toCheckSeq);
		$("table[id=mainItemDataTable] tr[id="+toId+"] td[aria-describedby=mainItemDataTable_checkseq]").attr("title", checkSeq).html(checkSeq);
		var row=$("table[id=mainItemDataTable] tr[id="+id+"]").html();
		var replaceRow=$("table[id=mainItemDataTable] tr[id="+toId+"]").html();
		$("table[id=mainItemDataTable] tr[id="+id+"]").html(replaceRow);
		$("table[id=mainItemDataTable] tr[id="+toId+"]").html(row);
		$("table[id=mainItemDataTable] tr[id="+id+"] td:eq(0)").html(id);
		$("table[id=mainItemDataTable] tr[id="+id+"] td:eq(0)").attr("title", id);
		$("table[id=mainItemDataTable] tr[id="+toId+"] td:eq(0)").html(toId);
		$("table[id=mainItemDataTable] tr[id="+toId+"] td:eq(0)").attr("title", toId);

		row=$("table[id=mainItemDataTable_frozen] tr[id="+id+"]").html();
		replaceRow=$("table[id=mainItemDataTable_frozen] tr[id="+toId+"]").html();
		$("table[id=mainItemDataTable_frozen] tr[id="+id+"]").html(replaceRow);
		$("table[id=mainItemDataTable_frozen] tr[id="+toId+"]").html(row);
		$("table[id=mainItemDataTable_frozen] tr[id="+id+"] td:eq(0)").html(id);
		$("table[id=mainItemDataTable_frozen] tr[id="+id+"] td:eq(0)").attr("title", id);
		$("table[id=mainItemDataTable_frozen] tr[id="+toId+"] td:eq(0)").html(toId);
		$("table[id=mainItemDataTable_frozen] tr[id="+toId+"] td:eq(0)").attr("title", toId);			
	}
	function genOthercost(){
			var ids = $("#mainItemDataTable").jqGrid("getDataIDs");
			if(ids.length == 0){
				art.dialog({
					content:"无采购明细，无法进行计算"
				});
				return;
			}
			var rets = $("#mainItemDataTable").jqGrid("getRowData");
			var params = {"detailJson": JSON.stringify(rets)};
			Global.Form.submit("dataForm", "${ctx}/admin/supplierCheck/doGenOtherCost", params,function(ret){
				if(ret.rs==true){
					var list1=ret.datas.list1
					var ids = $("#mainItemDataTable").getDataIDs();
					var rows = $("#mainItemDataTable").jqGrid("getRowData");
					if(ids){
						for(var i=0;i<ids.length;i++){
							for(var j=0;j<=list1.length-1;j++){
								if(i==j){
									rows[i]['processcost']=list1[i].processcost;
								}
							}
				 		}
						$("#mainItemDataTable").jqGrid("clearGridData");
						$.each(rows, function(i,rowData){
							$("#mainItemDataTable").jqGrid("addRowData", i+1, rowData);
						});	
					}	
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content:ret.msg,
						time:1000,
					});				
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content:ret.msg,
						width:200
					});
				}	
			});	
	}

	function checkbox(cellvalue, options, rowObject) {
	    return '<input type="checkbox" class="ibox" onclick="updateSelectAllCheckbox(event)"/>'
	}

	function updateSelectAllCheckbox(event) {
	    var grid = $(event.target).parents("table");
	    var th = $("#mainItemDataTable_checkbox");
	    
	    if (!event.target.checked) {
	        $("input", th).prop("checked", false)
	    } else {
	        var checkedAll = true
	        
	        $("td input.ibox", grid).each(function(index, element) {
	            if (!element.checked) {
	                checkedAll = false
	                return
	            }
	        })
	        
	        if (checkedAll) $("input", th).prop("checked", true)
	    }
	}

	function setSelectAllCheckbox() {
	    var th = $("#mainItemDataTable_checkbox");
	    th.off("click");
	    th.prop("title", "");
	    
	    $("input", th).on("click", function(event) {
	        $("td input.ibox", $("#mainItemDataTable_frozen")).each(function(index, element) {
	            element.checked = event.target.checked
	        });
	    })
	}
</script>
<div class="btn-panel" style="margin-top:2px">
	<div class="btn-group-sm">
		<button id="addBtn" type="button" class="funBtn funBtn-system" onclick="add()">新增</button>
		<button id="delBtn" type="button" class="funBtn funBtn-system" onclick="del()">删除</button>
		<button id="genOthercost" type="button" class="funBtn funBtn-system"  onclick="genOthercost()" disabled="true" >计算其他费用</button>
		<button id="viewBtn" type="button" class="funBtn funBtn-system" onclick="view()">查看</button>
		<button id="viewIntInstallBtn" type="button" class="funBtn funBtn-system" onclick="viewIntInstall()">查看集成安装费</button>
		<button id="upBtn" type="button" class="funBtn funBtn-system" onclick="up()">向上</button>
		<button id="downBtn" type="cysqdrBtn" class="funBtn funBtn-system" onclick="down()">向下</button>
	</div>
</div>	
<input type="hidden" id="firstFlag" name="firstFlag" value="1"/>
<input type="hidden" id="maxCheckSeq" name="maxCheckSeq" value="0"/>
<table id="mainItemDataTable"></table>
