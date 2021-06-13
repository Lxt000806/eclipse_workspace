<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>礼品出库记账新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
  </head>
  <body>
  	 <div class="body-box-form">
  	
  		<div class="content-form">
  			<!-- panelBar -->
  			<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
					</div>
			</div>
  			<div class="infoBox" id="infoBoxDiv"></div>
  			<!-- edit-form -->
  			<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<div class="validate-group row" >
							<li>
								<label>记账单号</label>
									<input type="text" id="No" name="No" style="width:160px;" placeholder="保存自动生成" value="${giftCheckOut.no }" readonly="readonly"/>                                             
							</li>
							
							<li>
								<label>申请人</label>
									<input type="text" id="appCzy" name="appCzy" style="width:160px;" value="${giftCheckOut.appCzy }" readonly="readonly"/>                                             
							</li>	
							<li>
								<label>状态</label>
									<house:xtdm id="status" dictCode="WHChkOutStatus"  value="${giftCheckOut.status }" disabled="true"></house:xtdm>
							</li>
							</div>
							<div class="validate-group row" >
							<li>
								<label>申请日期</label>
									<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftCheckOut.date}' pattern='yyyy-MM-dd'/>"  disabled="true"/>
							</li>	
							<li class="form-validate">
								<label ><span class="required">*</span>仓库编号</label>
									<input type="text" id="whCode" name="whCode" style="width:160px;" value="${giftCheckOut.whCode }" readonly="readonly"/>                                             
							</li>	
							<li>
								<label>审核人</label>
									<input type="text" id="confirmCzy" name="confirmCzy" style="width:160px;" value="${giftCheckOut.confirmCzy }" readonly="readonly"/>                                             
							</li>
							</div>
							<div class="validate-group row" >	
							<li>
								<label>记账日期</label>
									<input type="text" id="checkDate" name="checkDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftCheckOut.checkDate}' pattern='yyyy-MM-dd'/>"  disabled="true" disabled="true"/>
							</li>	
							<li>
								<label>审核日期</label>
									<input type="text" id="confirmDate" name="confirmDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftCheckOut.confirmDate}' pattern='yyyy-MM-dd'/>" disabled="true" disabled="true"/>
							</li>	
							<li>
								<label>凭证号</label>
									<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${giftCheckOut.documentNo }" readonly="readonly"/>                                             
							</li>
							</div>
							<div class="validate-group row" >	
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${giftCheckOut.remarks }</textarea>
  							</li>
						</ul>
  				</form>
  				</div>
  				</div>
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
								<button type="button" class="btn btn-system " id="add" >
									<span>新增</span>
									</button>
								<button type="button" class="btn btn-system " id="delDetail">
									<span>删除</span>
									</button>
								<button type="button" class="btn btn-system " onclick="doExcelNow('礼品领用单')" title="导出当前excel数据" >
									<span>导出excel</span>
								</button>
				</div>
			</div>	
			<ul class="nav nav-tabs" >
	      	<li class="active"><a data-toggle="tab">礼品领用信息</a></li>
	   	 </ul>
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>	
  		</div>
  	</div> 
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var checkSeq=0;
$("#tabs").tabs();

$(function() {
	$("#whCode").openComponent_wareHouse({callBack:validateRefresh,});

			$("#page_form").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  
					openComponent_wareHouse_whCode:{  
				        validators: {  
				            notEmpty: {  
				            message: '仓库编号不能为空'  
				            },
				             remote: {
					            message: '',
					            url: '${ctx}/admin/wareHouse/getWareHouse',
					            data: getValidateVal,  
					            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
					        }   
				        }  
				     },
				},
				submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
			});	
});
$(function(){
	$("#appCzy").openComponent_employee({showValue:'${giftCheckOut.appCzy}',showLabel:'${giftCheckOut.appCzyDescr}' ,readonly:true});
	$("#whCode").openComponent_wareHouse();
	$("#confirmCzy").openComponent_employee({showValue:'${giftCheckOut.confirmCzy}',showLabel:'${giftCheckOut.confirmCzyDescr}' ,readonly:true});
	var lastCellRowId;
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'CheckSeq', index:'checkSeq', width:80, label:'结算顺序号', sortable:true ,align:"left",hidden:true },
			{name:'No', index:'No', width:80, label:'领用单号', sortable:true ,align:"left",count:true },
			{name:'Status', index:'status', width:80, label:'状态', sortable:true ,align:"left" ,hidden:true},
			{name:'statusDescr', index:'statusDescr', width:80, label:'状态', sortable:true ,align:"left" ,},
			{name:'Type', index:'type', width:80, label:'类型', sortable:true ,align:"left" ,hidden:true},
			{name:'typeDescr', index:'typeDescr', width:80, label:'类型', sortable:true ,align:"left" },
			{name:'custCode', index:'custCode', width:80, label:'客户编号', sortable:true ,align:"left" }, 
			{name:'documentNo', index:'documentNo', width:80, label:'档案号', sortable:true ,align:"left" }, 
			{name:'address', index:'address', width:80, label:'楼盘', sortable:true ,align:"left" }, 
			{name:'sendDate', index:'sendDate', width:80, label:'发货时间', sortable:true ,align:"left" , formatter:formatDate},
			{name:'useMan', index:'useMan', width:80, label:'使用人', sortable:true ,align:"left",hidden:true },
			{name:'useDescr', index:'useDescr', width:80, label:'使用人', sortable:true ,align:"left" },
			{name:'AppCZY', index:'AppCZY', width:80, label:'申请人', sortable:true ,align:"left",hidden:true }, 
			{name:'appDescr', index:'appDescr', width:80, label:'申请人', sortable:true ,align:"left" ,hidden:true}, 
			{name:'Date', index:'date', width:80, label:'申请时间', sortable:true ,align:"left" , formatter:formatDate,hidden:true},
			{name:'itemsumcost', index:'itemsumcost', width:80, label:'金额', sortable:true ,align:"right",sum:true },
			{name:'Phone', index:'phone', width:90, label:'电话', sortable:true ,align:"left" ,hidden:true},
			{name:'businessmandescr', index:'businessmandescr', width:80, label:'业务员', sortable:true ,align:"left",},
			{name:'designmandescr', index:'designmandescr', width:80, label:'设计师', sortable:true ,align:"left",},
			{name:'busidpa', index:'busidpa', width:80, label:'业务部', sortable:true ,align:"left",},
			{name:'designdpa', index:'designdpa', width:80, label:'设计部', sortable:true ,align:"left",},
			{name:'actNo', index:'actNo', width:80, label:'活动编号', sortable:true ,align:"left" ,hidden:true},
			{name:'actName', index:'actName', width:80, label:'活动名称', sortable:true ,align:"left" },
			{name:'remarks', index:'remarks', width:80, label:'备注', sortable:true ,align:"left" },
			//{name:'custCode', index:'custCode', width:80, label:'客户编号', sortable:true ,align:"left" },
			{name:'lastUpdate', index:'lastUpdate', width:80, label:'最后修改时间', sortable:true ,align:"left",formatter:formatTime },
		],
	};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	//新增
	$("#add").on("click",function(){
		var whCode = $.trim($("#whCode").val());
		if(whCode ==''){
			art.dialog({content: "请选择仓库编号",width: 200});
			return false;
		}
		var no = Global.JqGrid.allToJson("dataTable","No");
		var arr = new Array();
			arr = no.fieldJson.split(",");
		Global.Dialog.showDialog("save",{
			  title:"礼品领用信息",
			  url:"${ctx}/admin/giftCheckOut/goAdd",
			  postData:{arr:arr,whCode:whCode},
			  height: 680,
			  width:1050,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
					  checkSeq++;
						  var json = {
							No:v.No,
							Type:v.Type,
							documentNo:v.documentno,
							custCode:v.CustCode,
							sendDate:v.SendDate,
							useMan:v.UseMan,
							Phone:v.Phone,
							actNo:v.ActNo,
							remarks:v.Remarks,
							lastUpdate:new Date(),
							address:v.address,
							typeDescr:v.typedescr,
							useDescr:v.usedescr,
							itemsumcost:v.itemsumcost,
							CheckSeq:checkSeq,
							Status:v.Status,
							Date:v.Date,
							AppCZY:v.AppCZY,
							appDescr:v.appdescr,
							statusDescr:v.statusdescr,
							actName:v.descr,
							businessmandescr:v.bm,
							designmandescr:v.dm,
							busidpa:v.bd,
							designdpa:v.dd
						  };
						  Global.JqGrid.addRowData("dataTable",json);
							$("#whCode").setComponent_wareHouse({readonly:true});
					  });
				  }
			  } 
		 });
	});
	//删除
	$("#delDetail").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		Global.JqGrid.delRowData("dataTable",id);
		var Ids =$("#dataTable").getDataIDs();
		for(var i=0;i<Ids.length;i++){
			$("#dataTable").setCell(Ids[i], 'checkSeq',i+1);
			checkSeq=i+1;
		}		
		
	});
	//查看
	$("#view").on("click",function(){
		var ret= selectDataTableRow('dataTable');
	}); 
	//保存
	$("#saveBtn").on("click",function(){
		var param= Global.JqGrid.allToJson("dataTable");
		var Ids =$("#dataTable").getDataIDs();
		if(Ids==null||Ids==''){
			art.dialog({
				content:'礼品领用表为空，不能保存',
			});
			return false;
		}
		Global.Form.submit("page_form","${ctx}/admin/giftCheckOut/doSave",param,function(ret){
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
	});
});
function validateRefresh(){
	 $('#page_form').data('bootstrapValidator')  
                   .updateStatus('openComponent_wareHouse_whCode', 'NOT_VALIDATED',null)  
                    .validateField('openComponent_wareHouse_whCode');  
}

</script>
  </body>
</html>

















