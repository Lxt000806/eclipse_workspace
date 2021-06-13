<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>基础人工成本管理</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
		
		/**初始化表格*/
		$(function(){
			var baseOneCtrl=myRound("${BaseOneCtrl}"*100);//单项超过比例
			var baseAllCtrl=myRound("${BaseAllCtrl}"*100);//总发包成本控制
			$("#redTip").html('*"红色"代表总成本金额大于总发包金额'+baseAllCtrl+'%');
			$("#yellowTip").html(' *"黄色"代表人工总成本超过人工发包控制');
			$("#appCzy").openComponent_employee({
				showValue:"${workCost.appCzy}",
				showLabel:"${workCost.appCZYDescr}",
				readonly:true
			});
			$("#confirmCzy").openComponent_employee({
				showValue:"${workCost.confirmCzy}",
				showLabel:"${workCost.confirmCZYDescr}",
				readonly:true
			});
			$("#payCzy").openComponent_employee({
				showValue:"${workCost.payCzy}",
				showLabel:"${workCost.payCZYDescr}",
				readonly:true
			});
		});
		
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
		//保存，审核，审核取消，反审核，出纳签字
		function save(umState){
			$("#m_umState").val(umState);
			if(umState=='A'){//保存
				var ids=$("#detailDataTable").jqGrid("getDataIDs");
				var date=$("#date").val();
				var type=$("#type").val();
				if(type==''){
					art.dialog({    	
						content: "请选择申请类型！"
					});
					return;
				}	
				if(ids==null || ids==''){
					art.dialog({    	
						content: "明细表无数据保存无意义！"
					});
					return;
				}
				if(date==''){
					art.dialog({    	
						content: "请选择申请日期！"
					});
					return;
				}
				if('${workCost.button}'!='add'){
					umState='M';
					$("#m_umState").val(umState);
				}
				$("#status").val("1");
				doSave();		
			}else if(umState=='C'){//审核
				var documentNo=$("#documentNo").val();
				var msg="项目经理要领和已领的楼盘【";
				var flag=true;
				if(documentNo==''){
			 		art.dialog({    	
						content: "请输入完整信息！"
					});
				$("#documentNo").focus();
				}else{
					$.ajax({
						url : '${ctx}/admin/workCostDetail/isAllowConfirm',
						type : 'post',
						data : {
							'no' : '${workCost.no}',
						},
						async:false,
						dataType : 'json',
						cache : false,
						error : function(obj) {
							showAjaxHtml({
								"hidden" : false,
								"msg" : '保存数据出错~'
							});
						},
						success : function(obj) {
							if(obj!=null && obj!=""){
								for ( var i = 0; i < obj.length; i++) {
									msg+=obj[i].address;
								}
								msg+="】不允许进行非预扣的工人工资审批";
								//$("#msg").val(msg);
								art.dialog({    	
									content: msg
								});
								flag=false;
							}
						}
					});
					if(!flag){
						return;
					}
					msg="";
					$.ajax({
						url : '${ctx}/admin/workCostDetail/overQualityFeeWorker',
						type : 'post',
						data : {
							'no' : '${workCost.no}',
						},
						async:false,
						dataType : 'json',
						cache : false,
						error : function(obj) {
							showAjaxHtml({
								"hidden" : false,
								"msg" : '保存数据出错~'
							});
						},
						success : function(obj) {
						console.log(obj);
							var qualityfee=$("#detailDataTable").getCol('qualityfee',false,'sum');//质保金总额
							if(parseFloat(qualityfee)>0)
							if(obj!=null && obj!="")
							for ( var i = 0; i < obj.length; i++) {
								msg+="工人["+obj[i].namechi+"],质保金总额["+obj[i].qualityfee+"]超出！\n";
							}
						art.dialog({
							content:msg+"是否确认要审核通过该条基础人工成本申请信息？",
							ok:function(){
							/* 	$.ajax({  //优秀班组奖励，限制人工发包额的10%控制要取消掉。
									url : '${ctx}/admin/workCostDetail/overCustCtrl',
									type : 'post',
									data : {
										'no' : '${workCost.no}',
									},
									async:false,
									dataType : 'json',
									cache : false,
									error : function(obj) {
										showAjaxHtml({
											"hidden" : false,
											"msg" : '保存数据出错~'
										});
									},
									success : function(obj) {
										if(obj!=null && obj!=""){
											for ( var i = 0; i < obj.length; i++) {
												art.dialog({    	
													content: "楼盘【"+obj[i].Address+"】档案编号【"+obj[i].DocumentNo+"】工种分类1【"+obj[i].WorkType1Descr
													+"】工种分类2【"+obj[i].WorkType2Descr+"】的优秀班组奖励累计总额【"+obj[i].ret1+"】超过单项人工发包额的10%【"
													+obj[i].ret2+"】超出金额为【"+(parseInt(obj[i].ret1)-parseInt(obj[i].ret2))+"】"
												});
											}
										}else{ */
											$.ajax({
												url : '${ctx}/admin/workCostDetail/overWithHold',
												type : 'post',
												data : {
													'no' : '${workCost.no}',
												},
												async:false,
												dataType : 'json',
												cache : false,
												error : function(obj) {
													showAjaxHtml({
														"hidden" : false,
														"msg" : '保存数据出错~'
													});
												},
												success : function(obj) {
													msg="审核后，楼盘【";
													if(obj!=null && obj!=""){
														for ( var i = 0; i < obj.length; i++) {
															msg+=+obj[i].Address+"】档案编号【"+obj[i].DocumentNo+"】工种分类1【"+obj[i].WorkType1Descr
																+"】工种分类2【"+obj[i].WorkType2Descr+"】领取金额将超过预扣金额 \n";
														}
														art.dialog({    	
															content: msg
														});
													}else{
														$("#status").val("2");
														doSave();
													}
												}
											});
								/* 		}
									}
								}); */
							},
							cancel:function(){}
						});
						}
					});
				}
				
			}else if(umState=='RC'){//审核取消
				art.dialog({
					content:"是否确认要审核取消该条基础人工成本申请信息？",
					ok:function(){
						$("#status").val("3");
						doSave();
					},
					cancel:function(){}
				});
				
			}else if(umState=='B'){//反审核
				art.dialog({
					content:"是否确认要反审核该条基础人工成本申请信息？",
					ok:function(){
						$("#status").val("1");
						doSave();
					},
					cancel:function(){}
				});
			}else if(umState=='W'){//出纳签字
				var payCzy=$("#payCzy").val();
				var payDate=$("#payDate").val();
					if(payCzy==''){
						art.dialog({    	
							content: "请输入完整信息！"
						});
						return;
					}
					if(payDate==''){
						art.dialog({    	
							content: "请选择出纳日期！"
						});
						return;
					}
					$("#status").val("2");
					doSave();
			}
		} 
		//保存
		function doSave(){
			var rets = $("#detailDataTable").jqGrid("getRowData");
			var params = {"workCostDetailJson": JSON.stringify(rets)};
			//var workCostDetailJson = Global.JqGrid.allToJson("detailDataTable");//数据量大保存不了
			//var param = {"workCostDetailJson": JSON.stringify(workCostDetailJson)};
				Global.Form.submit("page_form","${ctx}/admin/workCost/doSave",params,function(ret){
					if(ret.rs==true){
						art.dialog({
							content: ret.msg,
							time: 1000,
							beforeunload: function () {
			    				closeWin();
						    }
						});
					}else{
						$("#_form_token_uniq_id").val(ret.token.token);
			    		art.dialog({
							content: ret.msg,
							width: 200
						});
					}
					
				});
		}
	</script>
	</head>

<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<c:choose>
						<c:when
							test="${workCost.button=='update' || workCost.button=='add'}">
							<button id="saveBut" type="button" class="btn btn-system"
								onclick="save('A')">保存</button>
							<button id="confirmPassBut" type="button" class="btn btn-system"
								onclick="save('C')" disabled="disabled">审核</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('RC')" disabled="disabled">审核取消</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('B')" disabled="disabled">反审核</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('W')" disabled="disabled">出纳签字</button>
							<button id="closeBut" type="button" class="btn btn-system"
								onclick="addClose(false)">关闭</button>
						</c:when>
						<c:when test="${workCost.button=='examine'}">
							<button id="saveBut" type="button" class="btn btn-system"
								onclick="save('A')" disabled="disabled">保存</button>
							<button id="confirmPassBut" type="button" class="btn btn-system"
								onclick="save('C')">审核</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('RC')">审核取消</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('B')" disabled="disabled">反审核</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('W')" disabled="disabled">出纳签字</button>
							<button id="closeBut" type="button" class="btn btn-system"
								onclick="addClose(false)">关闭</button>
						</c:when>
						<c:when test="${workCost.button=='returnExamine'}">
							<button id="saveBut" type="button" class="btn btn-system"
								onclick="save('A')" disabled="disabled">保存</button>
							<button id="confirmPassBut" type="button" class="btn btn-system"
								onclick="save('C')" disabled="disabled">审核</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('RC')" disabled="disabled">审核取消</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('B')">反审核</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('W')" disabled="disabled">出纳签字</button>
							<button id="closeBut" type="button" class="btn btn-system"
								onclick="addClose(false)">关闭</button>
						</c:when>
						<c:when test="${workCost.button=='sign'}">
							<button id="saveBut" type="button" class="btn btn-system"
								onclick="save('A')" disabled="disabled">保存</button>
							<button id="confirmPassBut" type="button" class="btn btn-system"
								onclick="save('C')" disabled="disabled">审核</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('RC')" disabled="disabled">审核取消</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('B')" disabled="disabled">反审核</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('W')">出纳签字</button>
							<button id="closeBut" type="button" class="btn btn-system"
								onclick="addClose(false)">关闭</button>
						</c:when>
						<c:when test="${workCost.button=='view'}">
							<button id="saveBut" type="button" class="btn btn-system"
								onclick="save('A')" disabled="disabled">保存</button>
							<button id="confirmPassBut" type="button" class="btn btn-system"
								onclick="save('C')" disabled="disabled">审核</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('RC')" disabled="disabled">审核取消</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('B')" disabled="disabled">反审核</button>
							<button id="confirmCancelBut" type="button"
								class="btn btn-system" onclick="save('W')" disabled="disabled">出纳签字</button>
							<button id="closeBut" type="button" class="btn btn-system"
								onclick="addClose(false)">关闭</button>
						</c:when>
					</c:choose>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" /><input
					type="hidden" name="isExitTip" id="isExitTip" value="0" />
					<input
					type="hidden" name="m_umState" id="m_umState"  />
					<house:token></house:token>
				<ul class="ul-form">
					<div class="validate-group row">
						<li><label>申请单号</label> <input type="text" id="no" name="no"
							value="${workCost.no}" readonly="readonly" />
						</li>
						<li><label>状态</label> <house:dict id="status" dictCode=""
								sql="select CBM Code,CBM+' '+NOTE fd from tXTDM where id='WorkCostStatus'"
								sqlValueKey="Code" sqlLableKey="fd" disabled="true"
								value="${workCost.status}">
							</house:dict>
						</li>
						<c:if test="${workCost.button=='add'}">
							<li><label>申请类型</label> <house:xtdm id="type"
									dictCode="WorkCostType" value="${workCost.type}"
									onchange="changeGrid()"></house:xtdm>
							</li>
						</c:if>
						<c:if test="${workCost.button!='add'}">
							<li><label>申请类型</label> <house:xtdm id="type"
									dictCode="WorkCostType" value="${workCost.type}"
									onchange="changeGrid()" disabled="true"></house:xtdm>
							</li>
						</c:if>
					</div>
					<div class="validate-group row">
						<c:if
							test="${workCost.button=='add' || workCost.button=='update' }">
							<li><label>申请日期</label> <input type="text" id="date"
								name="date" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${workCost.date }' pattern='yyyy-MM-dd'/>" />
							</li>
						</c:if>
						<c:if
							test="${workCost.button!='add' && workCost.button!='update' }">
							<li><label>申请日期</label> <input type="text" id="date"
								name="date" class="i-date" 
								value="<fmt:formatDate value='${workCost.date }' pattern='yyyy-MM-dd'/>"
								readonly="readonly" />
							</li>
						</c:if>
						<c:if test="${workCost.button=='examine'}">
							<li><label>凭证号</label> <input type="text" id="documentNo"
								name="documentNo" value="${workCost.documentNo}" />
							</li>
						</c:if>
						<c:if test="${workCost.button!='examine'}">
							<li><label>凭证号</label> <input type="text" id="documentNo"
								name="documentNo" readonly="readonly"
								value="${workCost.documentNo}" />
							</li>
						</c:if>
						<li><label>申请人</label> <input type="text" id="appCzy"
							name="appCzy" style="width:160px;" value="${workCost.appCzy }" />
						</li>
					</div>
					<div class="validate-group row">
						<li><label>审批日期</label> <input type="text" id="confirmDate"
							name="confirmDate" class="i-date" style="width:160px;"
							value="<fmt:formatDate value='${workCost.confirmDate }' pattern='yyyy-MM-dd hh:mm:ss'/>"
							readonly="readonly" />
						</li>
						<li><label>审核人</label> <input type="text" id="confirmCzy"
							name="confirmCzy" style="width:160px;"
							value="${workCost.confirmCzy }" />
						</li>
						<li><label>出纳人</label> <input type="text" id="payCzy"
							name="payCzy" style="width:160px;" value="${workCost.payCzy }" />
						</li>

					</div>
					<div class="validate-group row">
						<c:if test="${workCost.button!='sign' }">
						<li><label>出纳日期</label> <input type="text" id="payDate"
							name="payDate" class="i-date" style="width:160px;"
							value="<fmt:formatDate value='${workCost.payDate }' pattern='yyyy-MM-dd'/>"
							readonly="readonly"  />
						</li>
						</c:if>
						<c:if test="${workCost.button=='sign' }">
						<li><label>出纳日期</label> <input type="text" id="payDate"
							name="payDate" class="i-date" style="width:160px;"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${workCost.payDate}' pattern='yyyy-MM-dd'/>"
							 />
						</li>
						</c:if>
						<c:if
							test="${workCost.button=='add' || workCost.button =='update'|| workCost.button =='examine'}">
							<li class="form-validate"><label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks"
									style="overflow-y:scroll; overflow-x:hidden; height:75px; " />${workCost.remarks
								}</textarea></li>
						</c:if>
						<c:if
							test="${workCost.button=='returnExamine' || workCost.button =='sign'|| workCost.button =='view'}">
							<li class="form-validate"><label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks"
									style="overflow-y:scroll; overflow-x:hidden; height:75px; "
									disabled="disabled" />${workCost.remarks }</textarea></li>
						</c:if>
						<c:if
							test="${workCost.button=='returnExamine'||workCost.button=='examine' }">
							<li class="form-validate"> 
								<span style="color:red" id="redTip"></span><br>
								&thinsp; &thinsp;<span style="color:red" id="yellowTip"></span>
							</li>
						</c:if>
					</div>
		</div>
		</ul>
		</form>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabWorkCostDetail" class="active"><a
				href="#tab_WorkCostDetail" data-toggle="tab">基础人工成本明细</a>
			</li>
			<li id="tabWorkCostGather" class=""><a
				href="#tab_WorkCostGather" data-toggle="tab">按班组长汇总</a>
			</li>
			<c:if test="${workCost.button!='update' && workCost.button!='add'}">
				<li id="tabWorkCostGather" class=""><a
					href="#tab_WorkCostMember" data-toggle="tab">班组成员工资明细</a>
				</li>
			</c:if>
		</ul>
		<div class="tab-content">
			<div id="tab_WorkCostDetail" class="tab-pane fade in active">
				<jsp:include page="workCost_detail.jsp"></jsp:include>
			</div>
			<div id="tab_WorkCostGather" class="tab-pane fade ">
				<jsp:include page="workCost_gather.jsp"></jsp:include>
			</div>
			<c:if test="${workCost.button!='update' && workCost.button!='add'}">
				<div id=tab_WorkCostMember class="tab-pane fade ">
					<jsp:include page="workCost_member.jsp"></jsp:include>
				</div>
			</c:if>
		</div>
	</div>
	</div>
</body>
</html>
