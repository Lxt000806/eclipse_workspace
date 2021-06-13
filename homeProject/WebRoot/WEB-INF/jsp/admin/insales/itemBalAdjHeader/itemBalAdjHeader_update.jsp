<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>仓库调整-编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
var noPosiQty;
$(function(){
	$("#whCode").openComponent_wareHouse({condition: {czybh:'${czybh}'}});
	$("#whCode").openComponent_wareHouse({showValue:'${itemBalAdjHeader.whCode}',showLabel:'${itemBalAdjHeader.whDescr}',readonly:true,condition: {czybh:'${czybh}'} });
	$("#appEmp").openComponent_employee({showValue:'${itemBalAdjHeader.appEmp}',showLabel:'${itemBalAdjHeader.appEmpDescr}',readonly:true });
	$("#status_NAME").attr("disabled","disabled");
	$("#adjType").attr("disabled","disabled");
	$("#tabs").tabs();
});

//新增是否允许重复
function noRepeat(obj){
	if ($(obj).is(':checked')){
		$('#noRepeat').val('1');
	}else{
		$('#noRepeat').val('');
	}
}
//校验函数
$(function() {
	$("#page_form").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			date:{  
				validators: {  
					notEmpty: {  
						message: '必填字段'  
					}
				}  
			},
			adjReason:{  
		        validators: {  
		            notEmpty: {  
		           		 message: '必填字段'  
		            }
		        }  
		     },
		     adjType:{  
		        validators: {  
		            notEmpty: {  
		           		 message: '必填字段'  
		            }
		        }  
		     },
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});

$(function(){

	Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemBalAdjDetail/goJqGrid",
			postData:{ibhNo:'${itemBalAdjHeader.no }',noRepeat:'1'},
			height:271,
			rowNum:10000000,
			styleUI: 'Bootstrap', 
			colModel : [
				{name:'pk', index:'pk', width:105, label:'PK', sortable:true ,align:"left", hidden:true},
				{name:'itcode', index:'itcode', width:105, label:'材料编号', sortable:true ,align:"left"},
				{name:'ibhno', index:'ibhno', width:105, label:'仓库调整编号', sortable:true ,align:"left", hidden:true},
				{name:'itdescr', index:'itdescr', width:250, label:'材料名称', sortable:true ,align:"left"},
				{name:'uomdescr', index:'uomdescr', width:60, label:'单位', sortable:true, align:"left"},
				{name:'adjcost',index:'adjcost',width:70, label:'成本单价', sortable:true,align:"left",},
				{name:'aftcost',index:'aftcost',width:75, label:'变动后成本', sortable:true,align:"left", sum:true},
				{name:'cost', index:'cost', width:60, label:'原成本', sortable : true,align : "left", sum:true,hidden:true},
				{name:'adjqty',	index:'adjqty',width:80, label:'调整数量', sortable : true,align : "left",sum:true},
				{name:'qty', index:'qty', width:80, label:'变动后数量', sortable:true ,align:"left",sum:true},
				{name:"adjamount", index:"adjamount", width:70, label:"金额", sortable:true, align:"left", 
					formatter:formatterAdjAmount, sum:true},
				{name:'remarks',index:'remarks',width:220, label:'备注', sortable:true,align:"left"},	
				{name:'allqty',index:'allqty',width:250, label:'现存数量', sortable:true,align:"left", hidden:true},	
				{name:'lastupdate',index : 'lastupdate',width : 135,label:'最后修改时间',sortable : true,align : "left",formatter:formatTime, hidden:true},
			    {name:'lastupdatedby',index : 'lastupdatedby',width : 42,label:'修改人',sortable : true,align : "left", hidden:true}
			],
		});

	//保存
	$("#saveBtn").on("click",function(){
	$("#page_form").bootstrapValidator('validate');
		if(!$("#page_form").data('bootstrapValidator').isValid()) return;
			var no = $.trim($("#no").val());
			var date = $.trim($("#date").val());
			var adjReason = $.trim($("#adjReason").val());
			 if (date ==''){
		 		art.dialog({content: "请选择调整日期",width: 200});
				return false;
	 		}else if (adjReason ==''){
		 		art.dialog({content: "请选择调整原因",width: 200});
				return false;
	 		}else{
			var param= Global.JqGrid.allToJson("dataTable");
			Global.Form.submit("page_form","${ctx}/admin/itemBalAdjHeader/doItemBalAdjHeaderUpdate",param,function(ret){
				if(ret.rs==true){
					art.dialog({
						content:ret.msg,
						time:1000,
						beforeunload:function(){
							closeWin();
						}
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
		});
});

		
//新增
function add(){
		var detailJson = Global.JqGrid.allToJson("dataTable","itcode");
		var adjType = $.trim($("#adjType").val());
		var noRepeat = $.trim($("#noRepeat").val());
		arry = detailJson.fieldJson.split(",");
		Global.Dialog.showDialog("saveAdd",{
			  title:"仓库调整明细——新增",
			  url:"${ctx}/admin/itemBalAdjHeader/goSaveAdd",
			  postData:{whCode:$("#whCode").val(),adjType:adjType,allItCode:arry,noRepeat:noRepeat},
			  height: 480,
			  width:800,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
					  	   if($("#noRepeat").val()!="1"){
					  	  		for(var i=0;i<arry.length;i++){

		       	  					if( $.trim(v.itCode)==$.trim(arry[i])){
		       	  						art.dialog({
		       	  							content:'该材料编号已存在，不能重复选择',
		       	  						});
		       	  					return false;
	       	  						}
	       	 					}
					  	  }
						  var json = {
							itcode:v.itCode,
							uomdescr:v.uom,
							cost:v.cost,
							adjqty:v.adjQty,
							qty:v.qty,
							remarks:v.remarks,
							itdescr:v.itDescr,
							allqty:v.allQty,
							adjcost:v.adjCost,
							aftcost:v.aftCost,
							lastupdate:v.lastUpdate,
							lastupdatedby:v.lastUpdatedBy,
						  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
				  }
			  } 
		 });
};

//快速新增
function quickAdd(){
		var detailJson = Global.JqGrid.allToJson("dataTable","itcode");
		var adjType = $.trim($("#adjType").val());
		var noRepeat = $.trim($("#noRepeat").val());
		arry = detailJson.fieldJson.split(",");
		Global.Dialog.showDialog("saveQuickAdd",{
			  title:"仓库调整明细——快速新增",
			  url:"${ctx}/admin/itemBalAdjHeader/goSaveQuickAdd",
			  postData:{whCode:$("#whCode").val(),adjType:adjType,noRepeat:noRepeat,allItCode:arry},
			  height: 480,
			  width:800,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
					  	  if($("#noRepeat").val()!="1"){
					  	  		for(var i=0;i<arry.length;i++){

		       	  					if( $.trim(v.itCode)==$.trim(arry[i])){
		       	  						art.dialog({
		       	  							content:'该材料编号已存在，不能重复选择',
		       	  						});
		       	  					return false;
	       	  						}
	       	 					}
					  	  }
						  var json = {
							itcode:v.itCode,
							uomdescr:v.uom,
							cost:v.cost,
							adjqty:v.adjQty,
							qty:v.qty,
							remarks:v.remarks,
							itdescr:v.itDescr,
							allqty:v.allQty,
							adjcost:v.adjCost,
							aftcost:v.aftCost,
							lastupdate:v.lastUpdate,
							lastupdatedby:v.lastUpdatedBy,
						  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
				  }
			  }  
		 });
};

//编辑
function update(){
		var ret= selectDataTableRow('dataTable');
		var whCode = $.trim($('#whCode').val());
		var adjType = $.trim($("#adjType").val());
		var noRepeat = $.trim($("#noRepeat").val());
		var itcode = Global.JqGrid.allToJson("dataTable",'itcode');
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		
		arr= itcode.fieldJson.split(",");//
		$.ajax({
			url:"${ctx}/admin/itemBalAdjHeader/getAjaxNoPosi",
			type:'post',
			data:{whCode:whCode, itCode:ret.itcode},
			dataType:'json',
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				if (obj){
					noPosiQty=obj.noPosiQty;
				 	if(ret){
				 		var detailJson = Global.JqGrid.allToJson("dataTable","itcode");
						arry = detailJson.fieldJson.split(",");
						Global.Dialog.showDialog("SaveUpdate",{
							title:"仓库调整明细——编辑",
							url:"${ctx}/admin/itemBalAdjHeader/goSaveUpdate",
							postData:{whCode:$("#whCode").val(),adjType:adjType,itCode:ret.itcode, uom:ret.uomdescr,
							 cost:ret.cost, adjQty:ret.adjqty, qty:ret.qty, remarks:ret.remarks, itDescr:ret.itdescr, 
							 allQty:ret.allqty, adjCost:ret.adjcost, aftCost:ret.aftcost,noPosiQty:noPosiQty,noRepeat:noRepeat,allItCode:arr},
							height:480,
							width:800,
							returnFun:function(data){
							  if(data){
								  $.each(data,function(k,v){
									 if($("#noRepeat").val()=="1"){
								  	  		for(var i=0;i<arry.length;i++){
			
					       	  					if( $.trim(v.itCode)==$.trim(arry[i])&&$.trim(v.itCode)!=$.trim(ret.itcode)){
					       	  						art.dialog({
					       	  							content:'该材料编号已存在，不能重复选择',
					       	  						});
					       	  					return false;
				       	  						}
				       	 					}
								  	  }
								  	  
									  var json = {
										itcode:v.itCode,
										uomdescr:v.uom,
										cost:v.cost,
										adjqty:v.adjQty,
										qty:v.qty,
										remarks:v.remarks,
										itdescr:v.itDescr,
										allqty:v.allQty,
										adjcost:v.adjCost,
										aftcost:v.aftCost,
										lastupdate:v.lastUpdate,
										lastupdatedby:v.lastUpdatedBy,
									  };
			   							$('#dataTable').setRowData(rowId,json);
								  });
							  }
						  } 
						});
					}else{
						art.dialog({
							content:"请选择一条记录"
						});		
					}
				}
			}
		});
		
};  

//删除
function del(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		Global.JqGrid.delRowData("dataTable",id);
		var Ids =$("#dataTable").getDataIDs();
		if(Ids.length==0){
			$("#adjType").removeAttr("disabled","disabled");
		}
		
		
};
	
//查看
function view(){
		var ret= selectDataTableRow('dataTable');
		var whCode = $.trim($("#whCode").val());
		$.ajax({
			url:"${ctx}/admin/itemBalAdjHeader/getAjaxNoPosi",
			type:'post',
			data:{whCode:whCode, itCode:ret.itcode},
			dataType:'json',
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				if (obj){
					noPosiQty=obj.noPosiQty;
				 	if(ret){
						Global.Dialog.showDialog("SaveView",{
							title:"仓库调整明细——查看",
							url:"${ctx}/admin/itemBalAdjHeader/goSaveView",
							postData:{itCode:ret.itcode, uom:ret.uomdescr, cost:ret.cost,
							 adjQty:ret.adjqty, qty:ret.qty ,remarks:ret.remarks, itDescr:ret.itdescr, 
							 allQty:ret.allqty, adjCost:ret.adjcost, aftCost:ret.aftcost, lastUpdate:ret.lastupdate, 
							 lastUpdatedBy:ret.lastupdatedby ,noPosiQty:noPosiQty,
							  whCode:whCode
							 },
							height:480,
							width:800,
						});
					}else{
						art.dialog({
							content:"请选择一条记录"
						});		
					}
				}
			}
		});
		
};
function importExcel(){
		var itcode = Global.JqGrid.allToJson("dataTable",'itcode');
		arr= itcode.fieldJson.split(",");
		
		var ret= selectDataTableRow('dataTable');
		var adjType = $.trim($("#adjType").val());
		var whCode = $.trim($("#whCode").val());
		if(whCode ==''){
			art.dialog({content: "请选择仓库编号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable","itcode");
		arry = detailJson.fieldJson.split(",");
		Global.Dialog.showDialog("importExcel",{
			  title:"仓库调整明细——新增",
			  url:"${ctx}/admin/itemBalAdjHeader/goImportExcel",
			  postData:{whCode:$("#whCode").val(),noRepeat:$("#noRepeat").val(),adjType:adjType,allItCode:arr},
			  height: 580,
			  width:1030,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
							itcode:v.itemcode,
							itdescr:v.itemdescr,
							uomdescr:v.uomdescr,
							cost:v.avgCost,//当前成本
							adjqty:v.adjqty,
							qty:v.aftqty,//调整后数量
							remarks:v.remarks,
							allqty:v.allqty,
							adjcost:v.cost,
							aftcost:v.aftcost,
							lastupdate:v.lastupdate,
							lastupdatedby:v.lastupdatedby,
						  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
					 $("#whCode").attr("disabled","disabled");
					 $("#adjType").attr("disabled","disabled");
					 $("#whCode").setComponent_wareHouse({readonly:true});
				  }
			  } 
		 });
};
// 规范金额 add by zb
function formatterAdjAmount(cellvalue, options, rowObject) {
	return myRound(rowObject.adjcost * rowObject.adjqty, 2);
}
</script>
</head>
  
<body>
<div class="body-box-form" >
	<div class="content-form">
  			<!-- panelBar -->
  			<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
 						<button type="button" class="btn btn-system " style="color:#777777; cursor:default">
 							<span>审核通过</span>
 						</button>	
 						<button type="button" class="btn btn-system " style="color:#777777; cursor:default">
 							<span>审核取消</span>
 						</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
			</div>
		
		<div class="infoBox" id="infoBoxDiv"></div>
		
		<div class="panel panel-info" >  
         <div class="panel-body">
         			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
							<ul class="ul-form">
							<li>
								<label>仓库调整编号</label>
								<input type="text" id="No" name="No" style="width:160px;" placeholder="保存自动生成" value="${itemBalAdjHeader.no }" readonly="readonly"/>                                             
							</li>
							<li>
								<label><span class="required">*</span>仓库编号</label>
									<input type="text" id="whCode" name="whCode" style="width:160px;" value="${itemBalAdjHeader.whCode }"/>
							</li>
							<li>
								<label>状态</label>
								<house:xtdmMulit id="status" dictCode="BALADJSTATUS" selectedValue="${itemBalAdjHeader.status}"></house:xtdmMulit>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>调整日期</label>
								<input type="text" style="width:160px;" id="date" name="date" class="i-date" value="<fmt:formatDate value='${itemBalAdjHeader.date}' pattern='yyyy-MM-dd'/>"  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
							</li>
							<li>
								<label>申请日期</label>
								<input type="text" style="width:160px;" id="appDate" name="appDate" class="i-date" value="<fmt:formatDate value='${itemBalAdjHeader.appDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>调整类型</label>
								<house:xtdm id="adjType" dictCode="ADJTYP"  value="${itemBalAdjHeader.adjType }" ></house:xtdm>
							</li>
							<li>
								<label>申请人</label>
									<input type="text" id="appEmp" name="appEmp" style="width:160px;" value="${itemBalAdjHeader.appEmp }"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>调整原因</label>
								<house:xtdm id="adjReason" dictCode="AdjReason"  value="${itemBalAdjHeader.adjReason }"></house:xtdm>
							</li>
							<li>
								<label>审核日期</label>
								<input type="text" style="width:160px;" id="confirmDate" name="confirmDate" class="i-date" value="<fmt:formatDate value='${itemBalAdjHeader.confirmDate}'  pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
							</li>
							<li>
								<label>凭证号</label>
								<input type="text" id="documentNo" name="documentNo" style="width:160px;"  value="${itemBalAdjHeader.documentNo}" disabled='true'/>
							</li>
							<li>
								<label>审核人</label>
								<input type="text" id="confirmEmp" name="confirmEmp" style="width:160px;"  value="${itemBalAdjHeader.confirmEmp}" disabled='true'/>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea style="width:160px" id="remarks" name="remarks" rows="2">${itemBalAdjHeader.remarks }</textarea>
							</li>
						</ul>	
			</form>
			</div>
		</div> <!-- edit-form end -->
		<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
								<button type="button" class="btn btn-system "  id="add" onclick="add()">
									<span>新增</span>
									</button>
								<button type="button" class="btn btn-system "  id="quickAdd" onclick="quickAdd()">
									<span>快速新增</span>
									</button>
								<button type="button" class="btn btn-system "  id="update" onclick="update()">
									<span>编辑</span>
									</button>
								<button type="button" class="btn btn-system " id="delete" onclick="del()">
									<span>删除</span>
									</button>
								<button type="button" class="btn btn-system " id="view" onclick="view()">
									<span>查看</span>
									</button>
								<button type="button" class="btn btn-system " onclick="doExcelNow('仓库调整明细')" title="导出检索条件数据">
									<span>输出到excel</span>
									</button>
								<button type="button" class="btn btn-system " id="importExcel" onclick="importExcel()">
									<span>从excel导入</span>
									</button>
				</div>
			</div>
			
		<ul class="nav nav-tabs" >
	      	<li class="active"><a  data-toggle="tab">详情</a></li>
	      	<li class="nav-checkbox">
	      		<input type="checkbox" id="noRepeat" name="noRepeat" value="0"  onclick="noRepeat(this)" ${itemBalAdjHeader!='1'?'':'checked'  }/><span>新增是否允许重复</span>					
	      	</li>
	   	 </ul>
		<div class="pageContent">
			<table id="dataTable" style="overflow: auto;"></table>
		</div>	
</div>
</body>
</html>
