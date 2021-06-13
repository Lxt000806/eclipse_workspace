<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>签单奖励管理-新增</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			var url = "";
			var postData = {};
			
			if("${data.m_umState}" != "A"){
				url = "${ctx}/admin/againAward/goJqGridAgainAwardDetail";
				$.extend(postData, {
					no:$("#no").val()
				});
			}else{
				url = "#";
			}
			
			$("#custCode").openComponent_customer();
			$("#appCZY").openComponent_employee({
				showValue:"${data.appCZY}",
				showLabel:"${data.appCZYDescr}"
			});
			
			$("#confirmCZY").openComponent_employee({
				showValue:"${data.confirmCZY}",
				showLabel:"${data.confirmCZYDescr}"
			});
			
			Global.JqGrid.initEditJqGrid("againAwardDetailDataTable",{
				url:url,
				postData:postData,
				height:250,
	        	styleUI: "Bootstrap",
				onSortColEndFlag:true,
				rowNum:100000,
				colModel : [
					{name : "address",index : "address",width : 140,label:"楼盘",sortable : true,align : "left"},
				  	{name : "custdescr",index : "custdescr",width : 80,label:"客户名称",sortable : true,align : "left"},
				  	{name : "custstatusdescr",index : "custstatusdescr",width : 80,label:"客户状态",sortable : true,align : "left"},
			      	{name : "layoutdescr",index : "layoutdescr",width : 70,label:"户型",sortable : true,align : "left"},
			      	{name : "area",index : "area",width : 50,label:"面积",sortable : true,align : "right"},
			      	{name : "role",index : "role",width : 90,label:"角色",sortable : true,align : "left", hidden: true},
			      	{name : "roledescr",index : "roledescr",width : 90,label:"角色",sortable : true,align : "left"},
			      	{name : "empcode",index : "empcode",width : 80,label:"员工姓名",sortable : true,align : "left", hidden: true},
			      	{name : "empname",index : "empname",width : 80,label:"员工姓名",sortable : true,align : "left"},
			      	{name : "empdept2descr",index : "empdept2descr",width : 120,label:"二级部门",sortable : true,align : "left"},
			      	{name : "contractfee",index : "contractfee",width : 80,label:"合同总价",sortable : true,align : "right"},
			      	{name : "amount",index : "amount",width : 80,label:"奖励金额",sortable : true,align : "right",editable:true, editrules: {number:true,required:true}, sum:true},
			      	{name : "remarks",index : "remarks",width : 140,label:"备注",sortable : true,align : "left",editable:true},
			      	{name : "receiveamount",index : "receiveamount",width : 80,label:"已领金额",sortable : true,align : "right", sum: true},
			      	{name : "hadcalcperfdescr",index : "hadcalcperfdescr",width : 80,label:"已算业绩",sortable : true,align : "left"},
			      	{name : "paidamount",index : "paidamount",width : 80,label:"已付款",sortable : true,align : "right"},
			      	{name : "firstpay",index : "firstpay",width : 80,label:"一期款",sortable : true,align : "right"},
			      	{name : "paytypedescr",index : "paytypedescr",width : 90,label:"付款类型",sortable : true,align : "left"},
			      	{name : "setdate",index : "setdate",width : 140,label:"下定日期",sortable : true,align : "left",formatter:formatTime},
			      	{name : "signdate",index : "signdate",width : 140,label:"签订日期",sortable : true,align : "left",formatter:formatTime},
			      	{name : "sourcedescr",index : "sourcedescr",width : 90,label:"客户来源",sortable : true,align : "left"},
			     	{name : "code",index : "code",width : 90,label:"code",sortable : true,align : "left", hidden:true},
			     	{name : "pk",index : "pk",width : 90,label:"pk",sortable : true,align : "left", hidden:true}
	            ],
				beforeSelectRow:function(id){
					setTimeout(function(){
						relocate(id,"againAwardDetailDataTable");
					},10);
				},
				onSortCol:function(index, iCol, sortorder){
					var rows = $("#againAwardDetailDataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("againAwardDetailDataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("againAwardDetailDataTable", v);
					});
				},
				onCellSelect:function(rowid,iCol,cellcontent,e){
					if("${data.m_umState}" != "A" && "${data.m_umState}" != "M"){
	                	$("#againAwardDetailDataTable").jqGrid("setCell", rowid, iCol, "", "not-editable-cell");  
					}
				}
			});
		});
		
		window.onload = function() {
		
			componentDisabled("employee", "appCZY", false);
			componentDisabled("employee", "confirmCZY", false);
			$("#no").attr("readonly", true);
			$("#oldStatus").attr("disabled", true);

			if("${data.m_umState}" == "A" || "${data.m_umState}" == "M"){
				$("#saveBut").removeAttr("disabled");
				$("#addDetailBtn").removeAttr("disabled");
				$("#addSingleDetailBtn").removeAttr("disabled");
				$("#delDetailBtn").removeAttr("disabled");
			}else if("${data.m_umState}" == "C"){
				$("#confirmPassBut").removeAttr("disabled");
				$("#confirmCancelBut").removeAttr("disabled");
				$("#documentNo").removeAttr("readonly");
			}
			
			if("${data.m_umState}" == "V"){
				$("#remarks").attr("readonly", true);
			}
			
		}
		
		function save(changeStatus){
		
			if(("2" == changeStatus || "3" == changeStatus) && "C" == $("#m_umState").val() && $("#documentNo").val() == ""){
				art.dialog({
					content:"凭证号不能为空"
				});
				return;
			}
			
			var param=Global.JqGrid.allToJson("againAwardDetailDataTable");
			
			if(param.datas.length <= 0){
				art.dialog({
					content:"签单明细不能为空"
				});
				return;
			}
			for(var i=0;i<param.datas.length;i++){
				if(param.datas[i].amount == 0){
					art.dialog({
						content:"存在明细申请金额为0,无法保存"
					});
					return;
				}
			}
			
			$.extend(param, {
				status:changeStatus
			});
			
			Global.Form.submit("page_form", "${ctx}/admin/againAward/doSave", param, function(ret){
				art.dialog({
					content:ret.msg,
					ok:function(){
						closeWin();
					}
				});
			});
		}
		
		function addDetail(){
		
			var codes = $("#againAwardDetailDataTable").getCol("code", false).join(",");
			
        	Global.Dialog.showDialog("againAwardAddDetail",{
        		title:"新增明细-批量新增",
        	  	url:"${ctx}/admin/againAward/goAddDetail",
        	  	postData:{
        	  		codes:codes
        	  	},
        	  	height: 620,
        	  	width:1000,
        	  	returnFun: function(data) {
        	  	    
     	  	        $.ajax({
				        url: "${ctx}/admin/againAward/applyBonusScheme?bonusScheme=" + data.bonusScheme + "&amount=" + data.amount,
				        type: "post",
				        data: JSON.stringify(data.selectedRows),
				        contentType: "application/json; charset=UTF-8",
				        dataType: "json",
				        cache: false,
				        success: function(result) {
				            var ids = $("#againAwardDetailDataTable").jqGrid("getDataIDs");
				            var maxId = 0
				            
				            for (var i = 0; i < ids.length; i++) {
				                var currentId = parseInt(ids[i])				                
				                if (currentId > maxId) maxId = currentId
				            }
                        
	                        $.each(result, function(i, rowData){
	                            $("#againAwardDetailDataTable").jqGrid("addRowData", ++maxId, rowData);
	                        })
	                        
	                        $("#isExitTip").val("1");
				        }
				    })
        	  	
        	  	}
        	})
		}
		
		function addClose(flag){
			if($("#isExitTip").val() == "1"){
				art.dialog({
					content:"关闭不保存数据,是否继续?",
					ok:function(){
						closeWin(flag);
					},
					cancel:function(){}
				});
			}else{
				closeWin(flag);
			}
		}
		
		function delDetail(){
			var id = $("#againAwardDetailDataTable").jqGrid("getGridParam", "selrow");
			
			if(id){
				art.dialog({
					content:"是否删除记录",
					ok:function(){
	     				$("#againAwardDetailDataTable").jqGrid("delRowData", id);
	     				selectFirstRow();
					},
					cancel:function(){}
				});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		
		function selectFirstRow(){
			var ids = $("#againAwardDetailDataTable").jqGrid("getDataIDs");
			if(ids.length > 0){
				$("#againAwardDetailDataTable").jqGrid("setSelection", ids[0]);
			}
		}
	</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs" style="float:left">
	    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()" disabled>保存</button>
	    				<button id="confirmPassBut" type="button" class="btn btn-system" onclick="save('2')" disabled>审核通过</button>
	    				<button id="confirmCancelBut" type="button" class="btn btn-system" onclick="save('3')" disabled>审核取消</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="addClose(false)">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="m_umState" id="m_umState" value="${data.m_umState}"/>
					<input type="hidden" name="isExitTip" id="isExitTip" value="0"/>
					<ul class="ul-form">
						<li>
							<label>单号</label>
							<input type="text" id="no" name="no" value="${data.no}" />
						</li>
						<li>							
							<label>状态</label>
							<house:xtdm id="oldStatus" dictCode="AGAINAWARDSTS" value="${data.oldStatus}"></house:xtdm>
						</li>
						<li>							
							<label>申请人</label>
							<input type="text" id="appCZY" name="appCZY" value="${data.appCZY}" />
						</li>
						<li>							
							<label>申请日期</label>
							<input type="text" id="date" name="date" class="i-date" 
								   value="<fmt:formatDate value='${data.date}' pattern='yyyy-MM-dd hh:mm:ss'/>"
								   readonly/>
						</li>
						<li>							
							<label>审核人</label>
							<input type="text" id="confirmCZY" name="confirmCZY" value="${data.confirmCZY}" />
						</li>
						<li>							
							<label>审核日期</label>
							<input type="text" id="confirmDate" name="confirmDate" class="i-date" 
								   value="<fmt:formatDate value='${data.confirmDate}' pattern='yyyy-MM-dd hh:mm:ss'/>"
								   readonly/>
						</li>
						<li>							
							<label>凭证号</label>
							<input type="text" id="documentNo" name="documentNo" value="${data.documentNo}" readonly/>
						</li>
						<li>
							<label class="control-textarea" >备注</label>
							<textarea id="remarks" name="remarks" rows="4">${data.remarks}</textarea>
						</li>
					</ul>
				</form>
			</div>
			
			<div  class="container-fluid" >  
				<ul class="nav nav-tabs" >
				    <li class="active"><a href="#againAwardDetail" data-toggle="tab">签单明细</a></li>  
				</ul>  
			    <div class="tab-content">  
					<div id="againAwardDetail"  class="tab-pane fade in active"> 
						<div class="btn-panel" style="margin-top:2px">
							<div class="btn-group-sm"  >
								<button id="addDetailBtn" type="button" class="btn btn-system" onclick="addDetail()" disabled>新增</button>
								<button id="delDetailBtn" type="button" class="btn btn-system" onclick="delDetail()" disabled>删除</button>
								<button id="excelDetailBtn" type="button" class="btn btn-system" onclick="doExcelNow('签单明细','againAwardDetailDataTable','page_form');">导出excel</button>
							</div>
						</div>	
						<table id="againAwardDetailDataTable"></table>
					</div> 
				</div>	
			</div>	
		</div>
	</body>
</html>
