<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>客户投诉管理新增</title>
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
  			<!-- edit-form -->
  			<div class="panel panel-info" >  
				<div class="panel-body">
					<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
						<input type="hidden" name="m_umState" id="m_umState" value="A"/>
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<div class="validate-group row" >
								<li class="form-validate">
									<label>投诉编号</label>
									<input type="text" id="no" name="no" style="width:160px;" placeholder="保存自动生成" readonly="readonly"/>                                             
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>客户编号</label>
									<input type="text" id="custCode" name="custCode"style="width:160px;"/>                                             
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" readonly="true"/>
								</li>
								<li>
									<label>楼盘状态</label>
									<house:xtdm id="custStatus" dictCode="CUSTOMERSTATUS"  style="width:160px;"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row" >
								<li>
									<label>客户类型</label>
									<house:xtdm id="type" dictCode="CUSTTYPE"  style="width:160px;"></house:xtdm>
								</li>
								<li>
									<label>业主姓名</label>
									<input type="text" id="descr" name="descr" style="width:160px;" readonly="true" />
								</li>
								<li>
									<label>业主电话</label>
									<input type="text" id="mobile1" name="mobile1" style="width:160px;"  />
								</li>
								<li>
									<label>业主电话2</label>
									<input type="text" id="mobile2" name="mobile2" style="width:160px;"  />
								</li>
							</div>	
							<div class="validate-group row" >
								<li>
									<label>状态</label>
									<house:xtdm id="status" dictCode="COMPSTATUS"  style="width:160px;" value="2"></house:xtdm>
								</li>
								<li>
									<label>设计师</label>
									<input type="text" id="designDescr" name="designDescr" style="width:160px;" readonly="readonly"/>                                             
								</li>
								<li>
									<label>设计师电话</label>
									<input type="text" id="designPhone" name="designPhone"style="width:160px;" readonly="true"/>                                             
								</li>	
								<li>
									<label>设计部</label>
									<input type="text" id="designDept2Descr" name="designDept2Descr" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftCheckOut.confirmDate}' pattern='yyyy-MM-dd'/>" disabled="true" disabled="true"/>
								</li>
							</div>	
							<div class="validate-group row" >	
								<li>
									<label>投诉时间</label>
									<input type="text" id="crtDate" name="crtDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${crtDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
								<li>
									<label>项目经理</label>
									<input type="text" id="projectDescr" name="projectDescr" style="width:160px;" readonly="true" />
								</li>
								<li>
									<label>项目经理电话</label>
									<input type="text" id="projectPhone" name="projectPhone" style="width:160px;" readonly="true" />
								</li>
								<li>
									<label>工程部</label>
									<input type="text" id=projectDeptDescr name="projectDeptDescr" style="width:160px;" readonly="true" />
								</li>
							</div>	
							<div class="validate-group row" >	
								<li>
									<label>登记人</label>
									<input type="text" id="crtCZY" name="crtCZY" style="width:160px;"/>                                             
								</li>
								<li>
									<label>实际结算时间</label>
									<input type="text" id="checkOutDate" name="checkOutDate" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>问题来源</label>
									<input type="text" id="source" name="source" style="width:160px;" />                                             
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>问题类型</label>
									<house:xtdm id="complType" dictCode="COMPLTYPE" style="width:160px;"></house:xtdm>
								</li>
								<li class="form-validate" style="max-height: 120px;">
									<label class="control-textarea"><span class="required">*</span>投诉内容</label>
									<textarea id="remarks" name="remarks" style="height: 90px;"></textarea>
	  							</li>
							</div>
						</ul>
  					</form>
  				</div>
  			</div>
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="add" >
						<span>新增</span>
						</button>
					<button type="button" class="btn btn-system " id="addUpdate" >
						<span>编辑</span>
						</button>
					<button type="button" class="btn btn-system " id="delDetail">
						<span>删除</span>
						</button>
					<button type="button" class="btn btn-system " id="addView">
						<span>查看</span>
						</button>
					<button type="button" class="btn btn-system " onclick="doExcelNow('投诉明细信息')" title="导出当前excel数据" >
						<span>导出excel</span>
					</button>
				</div>
			</div>	
			<ul class="nav nav-tabs" >
	      		<li class="active"><a data-toggle="tab">投诉明细信息</a></li>
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
	$("#tabs").tabs();
	$(function() {
		$(".form-search").unbind("keydown");// 解决textarea无法回车换行的问题 ——add by zb

		$("#status").attr("disabled","true")
		$("#custStatus").attr("disabled","true");
		$("#type").attr("disabled","true");
		function getData(data){
			if(!data) return;
			validateRefresh();
			$.ajax({
				url:"${ctx}/admin/custComplaint/getCustInfo?custCode="+data.code,
				type:"post",
				//data:{},
				dataType:"json",
				cache:false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": "出错~"});
				},
				success:function(obj){
					if (obj){
						$("#address").val(obj.Address);
						$("#descr").val(obj.custDescr);
						$("#designDescr").val(obj.designDescr);
						$("#designPhone").val(obj.designPhone);
						$("#designDept2Descr").val(obj.designDept2Descr);
						$("#custStatus").val($.trim(obj.status));
						$("#projectDescr").val(obj.projectDescr);
						$("#projectPhone").val(obj.projectPhone);
						$("#type").val($.trim(obj.CustType));
						$("#projectDeptDescr").val(obj.projectDeptDescr);
						$("#mobile1").val(obj.Mobile1);
						$("#mobile2").val(obj.Mobile2);
						$("#checkOutDate").val(obj.CheckOutDate);
						$("#workType12").val("1");
						//$("#custStatus").attr("disabled","true");
						//$("#type").attr("disabled","true");
					}
				}
			});
		}
		$("#crtCZY").openComponent_employee({showValue:"${uc.czybh}",showLabel:"${uc.zwxm}",readonly:true});
		$("#custCode").openComponent_customer({callBack:getData,condition:{mobileHidden:"false"}});
		$("#page_form").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {  
				source:{  
			        validators: {  
			            notEmpty: {  
				            message: "问题来源不能为空"  
			            },
			        }  
			    },
			    complType:{  
			        validators: {  
			            notEmpty: {  
				            message: "问题类型不能为空"  
			            },
			        }  
			    },
			    remarks:{  
			        validators: {  
			            notEmpty: {  
				            message: "投诉内容不能为空"  
			            },
			        }  
			    },
			    openComponent_customer_custCode:{  
			        validators: {  
			            notEmpty: {  
			            	message: "客户编号不能为空"  
			            },
			        }  
			     },
			},
			submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});	
	});
	$(function(){
		var lastCellRowId;
		var gridOption = {
			height:$(document).height()-$("#content-list").offset().top-55,
			rowNum:10000000,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "no", index: "no", width: 94, label: "no", sortable: true, align: "left", hidden: true},
				{name: "promtype1", index: "promtype1", width: 116, label: "问题分类", sortable: true, align: "left", hidden: true},
				{name: "promtype1descr", index: "promtype1descr", width: 116, label: "问题分类", sortable: true, align: "left"},
				{name: "supplcode", index: "supplcode", width: 126, label: "供应商", sortable: true, align: "left", hidden: true},
				{name: "suppldescr", index: "suppldescr", width: 126, label: "供应商", sortable: true, align: "left"},
				{name: "promtype2", index: "promtype2", width: 114, label: "材料分类", sortable: true, align: "left", hidden: true},
				{name: "promtype2descr", index: "promtype2descr", width: 114, label: "材料分类", sortable: true, align: "left"},
				{name: "promrsn", index: "promrsndescr", width: 90, label: "原因", sortable: true, align: "left", hidden: true},
				{name: "promrsndescr", index: "promrsndescr", width: 90, label: "原因", sortable: true, align: "left"},
				{name: "infodate", index: "infodate", width: 82, label: "通知时间", sortable: true, align: "left", formatter: formatTime},
				{name: "dealczycode", index: "dealczycode", width: 76, label: "接收人", sortable: true, align: "left", hidden: true},
				{name: "dealczydescr", index: "dealczydescr", width: 76, label: "接收人", sortable: true, align: "left"},
				{name: "rcvdate", index: "rcvdate", width: 93, label: "接收时间", sortable: true, align: "left", formatter: formatTime},
				{name: "plandealdate", index: "plandealdate", width: 99, label: "计划处理时间", sortable: true, align: "left", formatter: formatTime},
				{name: "dealdate", index: "dealdate", width: 98, label: "实际处理时间", sortable: true, align: "left", formatter: formatTime},
				{name: "status", index: "status", width: 86, label: "处理状态", sortable: true, align: "left",hidden: true},
				{name: "statusdescr", index: "statusdescr", width: 86, label: "处理状态", sortable: true, align: "left"},
				{name: "dealremarks", index: "dealremarks", width: 286, label: "处理结果", sortable: true, align: "left"},
				{name: "crtdate", index: "crtdate", width: 147, label: "crtdate", sortable: true, align: "left", formatter: formatTime,hidden:true},
				{name: "lastupdate", index: "lastupdate", width: 147, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 120, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 76, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 94, label: "操作标志", sortable: true, align: "left"},
			],
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
		//新增
		$("#add").on("click",function(){
			Global.Dialog.showDialog("add",{
				title:"客户投诉明细信息——新增",
				url:"${ctx}/admin/custComplaint/goAdd",
				//postData:{},
				height: 480,
				width:750,
			    returnFun:function(data){
					if(data){
						$.each(data,function(k,v){
							var json = {
							    pk: -1,
								promtype1:v.promType1,
								promtype1descr:v.promType1Descr,
								supplcode:v.supplCode,
								suppldescr:v.supplDescr,
								promtype2:v.promType2,
								promtype2descr:v.promType2Descr,
								promrsn:v.promRsn,
								promrsndescr:v.promRsnDescr,
								infodate:v.infoDate,
								dealczycode:v.dealCZY,
								dealczydescr:v.dealCZYDescr,
								rcvdate:v.rcvDate,
								plandealdate:v.planDealDate,
								dealdate:v.dealDate,
								status:v.status,
								statusdescr:v.statusDescr,
								dealremarks:v.dealRemarks,
								lastupdate:new Date(),
								lastupdatedby:"${uc.czybh}",
								expired:"F",
								actionlog:"ADD",
								crtdate:v.crtDate,
							};
							Global.JqGrid.addRowData("dataTable",json);
						});
						changeStatus();
					}
				} 
			});
		});
		
		$("#addUpdate").on("click",function(){
			var ret = selectDataTableRow();
			var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
			var param =$("#dataTable").jqGrid("getRowData",rowId);
			if(!ret){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			Global.Dialog.showDialog("addUpdate",{
				title:"客户投诉明细信息——编辑",
			  	url:"${ctx}/admin/custComplaint/goAddUpdate",
			  	postData:param,
			  	height: 480,
				width:750,
			    returnFun:function(data){
					if(data){
						$.each(data,function(k,v){
							var json = {
								promtype1:v.promType1,
								promtype1descr:v.promType1Descr,
								supplcode:v.supplCode,
								suppldescr:v.supplDescr,
								promtype2:v.promType2,
								promtype2descr:v.promType2Descr,
								promrsn:v.promRsn,
								promrsndescr:v.promRsnDescr,
								infodate:v.infoDate,
								dealczycode:v.dealCZY,
								dealczydescr:v.dealCZYDescr,
								rcvdate:v.rcvDate,
								plandealdate:v.planDealDate,
								dealdate:v.dealDate,
								status:v.status,
								statusdescr:v.statusDescr,
								dealremarks:v.dealRemarks,
								lastupdate:new Date(),
								lastupdatedby:"${uc.czybh}",
								expired:"F",
								actionlog:"ADD",
								crtdate:v.crtDate,
							  };
				   			$("#dataTable").setRowData(rowId,json);
						});
						changeStatus();
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
			art.dialog({
				content:"是删除该记录？",
				lock: true,
				width: 100,
				height: 80,
				ok: function () {
					Global.JqGrid.delRowData("dataTable",id);
					changeStatus();
				},
				cancel: function () {
					return true;
				}
			});
		});
		
		//查看
		$("#addView").on("click",function(){
			var ret = selectDataTableRow();
			var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
			var param =$("#dataTable").jqGrid("getRowData",rowId);
			if(!ret){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			Global.Dialog.showDialog("addUpdate",{
				title:"客户投诉明细信息——查看",
			  	url:"${ctx}/admin/custComplaint/goAddView",
			  	postData:param,
			  	height: 480,
				width:750,
			});
		});
		
		function changeStatus(){
			var status = Global.JqGrid.allToJson("dataTable","status");
			arry = status.fieldJson.split(",");
			for(var i=0;i<arry.length;i++){
				if(arry[i].trim()=="0"||arry[i].trim()=="2"){
					$("#status").val("1");
					return;
				}
			}
			$("#status").val("2");
		}
		
		//保存
		$("#saveBtn").on("click",function(){
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var param= Global.JqGrid.allToJson("dataTable");
			var Ids =$("#dataTable").getDataIDs();
			if($.trim($("#remarks").val())==""){
				art.dialog({
					content:"请填写投诉内容",
				});
				return;
			}
			if(Ids==null||Ids==""){
				art.dialog({
					content:"明细表为空，不能保存",
				});
				return false;
			}
			Global.Form.submit("page_form","${ctx}/admin/custComplaint/doSave",param,function(ret){
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
		 $("#page_form").data("bootstrapValidator")  
	                   .updateStatus("openComponent_customer_custCode", "NOT_VALIDATED",null)  
	                    .validateField("openComponent_customer_custCode");  
	}
	
	</script>
  </body>
</html>

















