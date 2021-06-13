<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
	<head>
		<title>提醒事件详情页</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				Global.LinkSelect.initSelect("${ctx}/admin/workType1/workTypeAll","workType1","offerWorkType2","0");
				Global.LinkSelect.setSelect({firstSelect:'workType1',
											firstValue:$.trim('${data.workType1}'),
											secondSelect:'offerWorkType2',
											secondValue:$.trim('${data.offerWorkType2}')
				});		
				$("#role").openComponent_roll({
					showValue:"${data.role}",
					showLabel:"${data.roleDescr}",
					callBack:function(ret){
						$("#roleDescr").val(ret.descr);
					}
				});
				$("#czybh").openComponent_employee({
					showValue:"${data.czybh}",
					showLabel:"${data.czyDescr}",
				});
				Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2");
				Global.LinkSelect.setSelect({
					firstSelect:"itemType1",
					firstValue:"${data.itemType1}",
					secondSelect:"itemType2",
					secondValue:"${data.itemType2}"
				});
				Global.Dialog.returnData = [];
				
				if($("#m_umState").val() != "A" && $("#m_umState").val() != "P"){
					typeChange();
					if($("#m_umState").val() == "V"){
						$("#tipEventSaveBtn").attr("disabled", true);
						$("#updateInfoDiv").removeAttr("hidden");
					}
				}
				
				$("#tipEventDataForm").bootstrapValidator({
					message : "This value is not valid",
					feedbackIcons : {/*input状态样式图片*/
						validating : "glyphicon glyphicon-refresh"
					},
					fields: {  
						addDay:{  
							validators: {
								numeric: {
		                            message: "只能输入数字", 
		                        }
							}  
						},
						dealDay:{  
							validators: {
								numeric: {
		                            message: "只能输入数字", 
		                        }
							}  
						},
						completeDay:{  
							validators: {
								numeric: {
		                            message: "只能输入数字", 
		                        }
							}  
						}
					},
					submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
				});	
				$("#openComponent_roll_role").attr("readonly", true);
				
				//改变提醒人类型时，清空提醒人员、对象 add by zb on 20200413
				$("#remindCzyType").on("change", function () {
					if ("V" == $("#m_umState").val() ) return false;
					$("#czybh, #role, #openComponent_employee_czybh, #openComponent_roll_role").val("");
				});
			});
			function typeChange(){
				if($("#type").val() == "5" || $("#type").val() == "2" ){
					$("#isAutoJob").removeAttr("disabled");
				}else{
					$("#isAutoJob").attr("disabled", true);
					$("#isAutoJob").val("0");
				}
			}
			function EditValid(str, closeFlag){
				if(!str || str.trim() == ""){
					art.dialog({
						content:"请输入完整的信息",
						ok:function(){
							if(closeFlag == true){
								closeWin();
							}
						}
					});
					return true;
				}
				return false;
			}
			function ajaxPost(url, data, successFun){
			 	$.ajax({
					url:url,
					type:"post",
			    	data:data,
					dataType:"json",
					cache:false,
					async:false,
					error:function(obj){			    		
						art.dialog({
							content:"访问出错,请重试",
							time:3000,
							beforeunload: function () {}
						});
					},
					success:successFun
				});	
			}
			
			function isProgTempDtExist(prjItem){
				var result = true;
				if($("#mm_umState").val() == "E"){
					ajaxPost("${ctx}/admin/gcjdmb/isProgTempDtExist", {
						tempNo:$("#tempNo").val(),
						prjItem:prjItem
					}, function(data){
						result = data;
					});
				}else{
					var prjItems = $("#prjItems").val().trim();
					if(prjItems && prjItems != ""){
						var prjItemArr = prjItems.split(",");
						var i = 0;
						for(;i < prjItemArr.length;i++){
							if(prjItemArr[i]==prjItem){
								break;
							}
						}
						if(i == prjItemArr.length){
							result = false;
						}
					}else{
						result = false;
					}
				}
				return result;
			}
			function save(){
				
				$("#tipEventDataForm").bootstrapValidator("validate");
				if(!$("#tipEventDataForm").data("bootstrapValidator").isValid()) return;
				
				if($("#prjItem").val() == ""){
					art.dialog({
						content:"请选择施工项目名称"
					});
					return;
				}
				
				if($("#dayType").val() == ""){
					art.dialog({
						content:"请选择提醒日期类型"
					});
					return;
				}
				
				if($("#type").val() == ""){
					art.dialog({
						content:"请输入提醒类型"
					});
					return;
				}
				
				if($("#dealDay").val() == ""){
					art.dialog({
						content:"请输入处理时间"
					});
					return;
				}
				
				if($("#completeDay").val() == ""){
					art.dialog({
						content:"请输入进场完成时间"
					});
					return;
				}
				
				if($("#type").val() == "3" && $("#itemType1").val() == ""){
					art.dialog({
						content:"提醒类型为:"+$("#type > option[value="+$("#type").val()+"]").html()+"时材料类型1必填"
					});
					return;
				}
				
				if(($("#type").val() == "2" || $("#type").val() == "7") && $("#workType12").val() == ""){
					art.dialog({
						content:"提醒类型为:"+$("#type > option[value="+$("#type").val()+"]").html()+"时工种类型12必填"
					});
					return;
				}
				
				if($("#type").val() == "5" && $("#jobType").val() == ""){
					art.dialog({
						content:"提醒类型为:"+$("#type > option[value="+$("#type").val()+"]").html()+"时任务类型必填"
					});
					return;
				}
				
				if(EditValid($("#addDay").val())){
					return;
				}
				
				if($("#msgTextTemp").val() == ""){
					art.dialog({
						content:"请输入消息内容模板"
					});
					$("#msgTextTemp").focus();
					return;
				}
				
				if(!isProgTempDtExist($("#prjItem").val())){
					art.dialog({
						content:"请先在模板明细中输入 ["+$("#prjItem > option[value="+$("#prjItem").val()+"]").html()+"] 信息"
					});
					return;
				}
				if($("#remindCzyType").val()=="1" && $("#czybh").val()==""){
					art.dialog({
						content:"请填写提醒人员！"
					});
					return;
				}
				if($("#mm_umState").val() == "E"){//从编辑页面进入
					if($("#m_umState").val() == "A" || $("#m_umState").val() == "C" || $("#m_umState").val() == "P"){
						ajaxPost("${ctx}/admin/gcjdmb/doSaveProgTempAlarm", {
							tempNo:$("#tempNo").val(),
							prjItem:$("#prjItem").val(),
							dayType:$("#dayType").val(),
							addDay:$("#addDay").val(),
							czybh:$("#czybh").val(),
							remindCzyType:$("#remindCzyType").val(),
							addDay:$("#addDay").val(),
							msgTextTemp:$("#msgTextTemp").val(),
							role:$("#role").val(),
							type:$("#type").val(),
							dealDay:$("#dealDay").val(),
							completeDay:$("#completeDay").val(),
							itemType1:$("#itemType1").val(),
							itemType2:$("#itemType2").val(),
							isNeedReq:$("#isNeedReq").val(),
							msgTextTemp2:$("#msgTextTemp2").val(),
							workType1:$("#workType1").val(),
							offerWorkType2:$("#offerWorkType2").val(),
							workType12:$("#workType12").val(),
							jobType:$("#jobType").val(),
							title:$("#title").val(),
							isAutoJob:$("#isAutoJob").val()
						}, function(data){
							if(data.rs){
								$("#isTipExit").val("1");
								if($("#m_umState").val() == "P"){
									$("#addDay").val("");
									$("#msgTextTemp").val("");
									return;
								}
								closeWin();
							}else{
								art.dialog({
									content:data.msg
								});
							}
						});
					}else if($("#m_umState").val() == "M"){
						ajaxPost("${ctx}/admin/gcjdmb/doUpdateProgTempAlarm", {
							prjItem:$("#prjItem").val(),
							dayType:$("#dayType").val(),
							addDay:$("#addDay").val(),
							czybh:$("#czybh").val(),
							remindCzyType:$("#remindCzyType").val(),
							msgTextTemp:$("#msgTextTemp").val(),
							pk:$("#pk").val(),
							role:$("#role").val(),
							type:$("#type").val(),
							dealDay:$("#dealDay").val(),
							completeDay:$("#completeDay").val(),
							itemType1:$("#itemType1").val(),
							itemType2:$("#itemType2").val(),
							isNeedReq:$("#isNeedReq").val(),
							msgTextTemp2:$("#msgTextTemp2").val(),
							workType1:$("#workType1").val(),
							offerWorkType2:$("#offerWorkType2").val(),
							workType12:$("#workType12").val(),
							jobType:$("#jobType").val(),
							title:$("#title").val(),
							isAutoJob:$("#isAutoJob").val(),
							expired:$("#expired").val()
						}, function(data){
							art.dialog({
								content:data.msg
							});
							if(data.rs){
								$("#isTipExit").val("1");
								closeWin();
							}
						});
					} 
				}else if($("#mm_umState").val() == "A"){//从新增界面进入
					if($("#m_umState").val() == "A" || $("#m_umState").val() == "C" || $("#m_umState").val() == "P"){
						Global.Dialog.returnData.push({
							tempno:"-1",
							prjitem:$("#prjItem").val(),
							prjitemdescr:$("#prjItem").val() != ""?$("#prjItem > option[value="+$("#prjItem").val()+"]").html():"",
							daytype:$("#dayType").val(),
							daytypedescr:$("#dayType").val() != ""?$("#dayType > option[value="+$("#dayType").val()+"]").html():"",
							addday:$("#addDay").val(),
							czybh:$("#czybh").val(),
							czydescr:$("#openComponent_employee_czybh").val().split("|")[1],
							remindczytype:$("#remindCzyType").val(),
							remindczytypedescr:$("#type").val() != ""?$("#type > option[value="+$("#remindCzyType").val()+"]").html():"",
							msgtexttemp:$("#msgTextTemp").val(),
							lastupdatedby:"${data.lastUpdatedBy}",
							role:$("#role").val(),
							roledescr:$("#roleDescr").val(),
							type:$("#type").val(),
							typedescr:$("#type").val() != ""?$("#type > option[value="+$("#type").val()+"]").html():"",
							dealday:$("#dealDay").val(),
							completeday:$("#completeDay").val(),
							itemtype1:$("#itemType1").val(),
							itemtype1descr:$("#itemType1").val() != ""?$("#itemType1 > option[value="+$("#itemType1").val()+"]").html():"",
							itemtype2:$("#itemType2").val(),
							itemtype2descr:$("#itemType2").val() != ""?$("#itemType2 > option[value="+$("#itemType2").val()+"]").html():"",
							isneedreq:$("#isNeedReq").val(),
							isneedreqdescr:$("#isNeedReq").val() != ""?$("#isNeedReq > option[value="+$("#isNeedReq").val()+"]").html():"",
							msgtexttemp2:$("#msgTextTemp2").val(),
							worktype1:$("#workType1").val(),
							offerworktype2:$("#offerWorkType2").val(),
							worktype1descr:$("#workType1").val() != ""?$("#workType1 > option[value="+$("#workType1").val()+"]").html():"",
							worktype2descr:$("#offerWorkType2").val() != ""?$("#offerWorkType2 > option[value="+$("#offerWorkType2").val()+"]").html():"",
							worktype12:$("#workType12").val(),
							worktype12descr:$("#workType12").val() != ""?$("#workType12 > option[value="+$("#workType12").val()+"]").html():"",
							jobtype:$("#jobType").val(),
							jobtypedescr:$("#jobType").val() != ""?$("#jobType > option[value="+$("#jobType").val()+"]").html():"",
							title:$("#title").val(),
							isautojob:$("#isAutoJob").val(),
							isautojobdescr:$("#isAutoJob").val() != ""?$("#isAutoJob > option[value="+$("#isAutoJob").val()+"]").html():"",
							expired:"F",
							actionlog:"ADD",
							lastupdate:new Date(),
							pk:parseInt($("#nowPk").val())+1
						});
						$("#nowPk").val(parseInt($("#nowPk").val())+1);
						$("#isTipExit").val("1");
						if($("#m_umState").val() == "P"){
							$("#addDay").val("");
							$("#msgTextTemp").val("");
							return;
						}
						closeWin();
					}else if($("#m_umState").val() == "M"){
						Global.Dialog.returnData.push({
							rowId:$("#rowId").val(),
							prjitem:$("#prjItem").val(),
							prjitemdescr:$("#prjItem").val() != ""?$("#prjItem > option[value="+$("#prjItem").val()+"]").html():"",
							daytype:$("#dayType").val(),
							daytypedescr:$("#dayType").val() != ""?$("#dayType > option[value="+$("#dayType").val()+"]").html():"",
							addday:$("#addDay").val(),
							czybh:$("#czybh").val(),
							czydescr:$("#openComponent_employee_czybh").val().split("|")[1],
							remindczytype:$("#remindCzyType").val(),
							remindczytypedescr:$("#type").val() != ""?$("#type > option[value="+$("#remindCzyType").val()+"]").html():"",
							msgtexttemp:$("#msgTextTemp").val(),
							lastupdatedby:"${data.lastUpdatedBy}",
							role:$("#role").val(),
							roledescr:$("#roleDescr").val(),
							type:$("#type").val(),
							typedescr:$("#type").val() != ""?$("#type > option[value="+$("#type").val()+"]").html():"",
							dealday:$("#dealDay").val(),
							completeday:$("#completeDay").val(),
							itemtype1:$("#itemType1").val(),
							itemtype1descr:$("#itemType1").val() != ""?$("#itemType1 > option[value="+$("#itemType1").val()+"]").html():"",
							itemtype2:$("#itemType2").val(),
							itemtype2descr:$("#itemType2").val() != ""?$("#itemType2 > option[value="+$("#itemType2").val()+"]").html():"",
							isneedreq:$("#isNeedReq").val(),
							isneedreqdescr:$("#isNeedReq").val() != ""?$("#isNeedReq > option[value="+$("#isNeedReq").val()+"]").html():"",
							msgtexttemp2:$("#msgTextTemp2").val(),
							worktype1:$("#workType1").val(),
							offerworktype2:$("#offerWorkType2").val(),
							worktype1descr:$("#workType1").val() != ""?$("#workType1 > option[value="+$("#workType1").val()+"]").html():"",
							worktype2descr:$("#offerWorkType2").val() != ""?$("#offerWorkType2 > option[value="+$("#offerWorkType2").val()+"]").html():"",
							worktype12:$("#workType12").val(),
							worktype12descr:$("#workType12").val() != ""?$("#workType12 > option[value="+$("#workType12").val()+"]").html():"",
							jobtype:$("#jobType").val(),
							jobtypedescr:$("#jobType").val() != ""?$("#jobType > option[value="+$("#jobType").val()+"]").html():"",
							title:$("#title").val(),
							isautojob:$("#isAutoJob").val(),
							isautojobdescr:$("#isAutoJob").val() != ""?$("#isAutoJob > option[value="+$("#isAutoJob").val()+"]").html():"",
							expired:$("#expired").val(),
							actionlog:"EDIT"
						});
						$("#isTipExit").val("1");
						closeWin();
					}
				}
			}
			function validateRefresh(){
				 $("#tipEventDataForm").data("bootstrapValidator")
	                 .updateStatus("addDay", "NOT_VALIDATED",null)   
	                 .validateField("addDay")
	                 .updateStatus("dealDay", "NOT_VALIDATED",null)  
	                 .validateField("dealDay")
	                 .updateStatus("completeDay", "NOT_VALIDATED",null)  
	                 .validateField("completeDay");                    
			}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs" style="float:left">
	    				<button id="tipEventSaveBtn" type="button" class="btn btn-system" onclick="save()">保存</button>
	    				<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					</div>
					<div id="updateInfoDiv" class="container" style="margin-left:20px;float:left" hidden>
						<div class="row">
							<span style="color:red">最后更新人员:&nbsp;&nbsp;${data.lastUpdatedBy}</span>
						</div>
						<div class="row">
							<span style="color:red">最后更新时间:&nbsp;&nbsp;<fmt:formatDate value="${data.lastUpdate}" pattern="yyyy-MM-dd hh:mm:ss"/></span>
						</div>
					</div>
				</div>
			</div>
			<ul class="nav nav-tabs">
			    <li class="active"><a href="#mainItem" data-toggle="tab">主项目</a></li>  
			</ul>  
		    <div class="tab-content">  
				<div id="mainItem"  class="tab-pane fade in active" style="border:0px"> 
					<div class="panel panel-info" style="border-top:0px">  
						<div class="panel-body" style="padding:0px;">
							<form action="" method="post" id="tipEventDataForm" class="form-search">
								<input type="hidden" name="jsonString" value="" />
								<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState }"/>
								<input type="hidden" id="mm_umState" name="mm_umState" value="${data.mm_umState }"/>
								<input type="hidden" id="tempNo" name="tempNo" value="${data.tempNo }"/>
								<input type="hidden" id="expired" name="expired" value="${data.expired}"/>
								<input type="hidden" id="isTipExit" name="isTipExit" value=""/>
								<input type="hidden" id="prjItems" name="prjItems" value="${data.prjItems }"/>	
								<input type="hidden" id="rowId" name="rowId" value="${data.rowId }"/>
								<input type="hidden" id="nowPk" name="nowPk" value="${data.nowPk }"/>
								<input type="hidden" id="roleDescr" name="roleDescr" value="${data.roleDescr }"/>
								<ul class="ul-form">
									<li>
										<label style="width:145px;">施工项目编号</label>
										<input type="text" id="pk" name="pk" value="${data.pk}" placeholder="保存自动生成" readonly/>
									</li>
									<li>	
										<label style="width:145px;">施工项目名称</label>
										<house:dict id="prjItem" dictCode="" sql="select Descr, Code from tPrjItem1 where Expired='F' order by Seq" 
													sqlValueKey="Code" sqlLableKey="Descr" value="${data.prjItem }"></house:dict>
									</li>
									<li>	
										<label style="width:145px;">提醒日期类型</label>
										<house:xtdm id="dayType" dictCode="ALARMDAYTYPE" value="${data.dayType}"></house:xtdm>
									</li>
									<li class="form-validate">
										<label style="width:145px;">增加天数</label>
										<input type="text" id="addDay" name="addDay" value="${data.addDay }"/>
									</li>
									<li>	
										<label style="width:145px;">提醒人类型</label>
										<house:xtdm id="remindCzyType" dictCode="REMINCZYTYPE" value="${data.remindCzyType}"></house:xtdm>
									</li>
									<li class="form-validate">
										<label style="width:145px;">提醒人员</label>
										<input type="text" id="czybh" name="czybh" value="${data.czybh }"/>
									</li>
									<li>
										<label style="width:145px;">提醒对象</label>
										<input type="text" id="role" name="role" value="${data.role }"/>
									</li>
									<li>	
										<label style="width:145px;">提醒类型</label>
										<house:xtdm id="type" dictCode="ALARMTYPE" value="${data.type}" onchange="typeChange()"></house:xtdm>
									</li>
									<li class="form-validate">
										<label style="width:145px;">处理时间</label>
										<input type="text" id="dealDay" name="dealDay" value="${data.dealDay }" />
									</li>
									<li class="form-validate">
										<label style="width:145px;">进场完成时间</label>
										<input type="text" id="completeDay" name="completeDay" value="${data.completeDay }" />
									</li>
									<li>	
										<label style="width:145px;">判断材料需求</label>
										<house:xtdm id="isNeedReq" dictCode="YESNO" value="${data.isNeedReq}"></house:xtdm>
									</li>
									<li>
										<label style="width:145px;">材料类型1</label>
										<select id="itemType1" name="itemType1" value="${data.itemType1}"></select>
									</li>
									<li>
										<label style="width:145px;">材料类型2</label>
										<select id="itemType2" name="itemType2" value="${data.itemType2}"></select>
									</li>
									<li>
										<label style="width:145px;">存在工种分类1需求</label> 
										<select id="workType1" name="workType1"></select>
									</li>
									<li>
										<label style="width:145px;">存在工种分类2需求</label>
										<select id="offerWorkType2" name="offerWorkType2"></select>
									</li>
									<li>
										<label style="width:145px;">工种分类12</label>
										<house:dict id="workType12" dictCode="" 
												    sql="select Code+' '+Descr fd,Code from tWorkType12  where Expired='F'"
												    sqlValueKey="Code" sqlLableKey="fd" value="${data.workType12}"></house:dict>
									</li>
									<li>
										<label style="width:145px;">任务类型</label>
										<house:dict id="jobType" dictCode="" 
												    sql="select Code+' '+Descr fd,Code from tJobType  where Expired='F'"
												    sqlValueKey="Code" sqlLableKey="fd" value="${data.jobType}"></house:dict>
									</li>
									<li>	
										<label style="width:145px;">是否触发任务</label>
										<house:xtdm id="isAutoJob" dictCode="YESNO" value="${data.isAutoJob}"></house:xtdm>
									</li>
									<li>
										<label style="width:145px;">标题</label>
										<input type="text" id="title" name="title" value="${data.title }"/>
									</li>
									<li>
										<label class="control-textarea" style="width:145px;">消息内容模板</label>
										<textarea id="msgTextTemp" rows="4">${data.msgTextTemp }</textarea>
									</li>
									<li>
										<label class="control-textarea" style="width:145px;">消息内容模板2</label>
										<textarea id="msgTextTemp2" rows="4">${data.msgTextTemp2 }</textarea>
									</li>
									<li class="search-group-shrink" hidden>
										<input type="checkbox" id="expired_show" name="expired_show"
											   onclick="checkExpired(this)" ${data.expired=='T'?'checked':'' }/><p>过期</p>
									</li>
								</ul>
							</form>
						</div> 
					</div>	
				</div>
			</div>
		</div>
	</body>
</html>


