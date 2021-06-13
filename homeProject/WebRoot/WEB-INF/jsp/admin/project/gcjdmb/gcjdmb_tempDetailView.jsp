<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
	<head>
		<title>模板明细详情页</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script type="text/javascript">
			$(function(){//类似cs的formshow函数
				Global.Dialog.returnData = [];//保存添加的记录数据
				if($("#m_umState").val() != "A"){
					$("#pk").attr("readonly", true);
					if($("#m_umState").val() == "V"){
						$("#tempDetailViewSaveBtn").attr("disabled", true);
						$("#updateInfoDiv").removeAttr("hidden");
					}
				}
				if($("#m_umState").val() == "A" || $("#m_umState").val() == "P"){
					$("#planBegin").val("");
					$("#planEnd").val("");
					$("#pk").attr("readonly", true);
				}
				if($("#m_umState").val() == "C"){
					$("#pk").attr("readonly", true);
				}
				if($("#tmpType").val()=="1"){
					$("#dynamicDiv").attr("hidden",true);
					$(".dynamic").attr("hidden",true);
				}else{
					$("#staticDiv").attr("hidden",true);
					$("#planBegin").val("0");
					$("#planEnd").val("0");
				}
				$("#tempDetailDataForm").bootstrapValidator({
					message : "This value is not valid",
					feedbackIcons : {/*input状态样式图片*/
						validating : "glyphicon glyphicon-refresh"
					},
					fields: {  
						planBegin:{  
							validators: {
								numeric: {
		                            message: "只能输入数字", 
		                        }
							}  
						},
						planEnd:{  
							validators: {
								numeric: {
		                            message: "只能输入数字", 
		                        }
							}  
						},
						spaceDay:{  
							validators: {
								numeric: {
		                            message: "只能输入数字", 
		                        }
							}  
						}
					},
					submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
				});	
			});
			
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
					async:false,//关闭异步加载
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
			function isPrjItemExist(){
				var result = true;
				ajaxPost("${ctx}/admin/gcjdmb/isPrjItemExist", {
					pk:($("#m_umState").val() == "A" || $("#m_umState").val() == "P" || $("#pk").val() == "")?0:parseInt($("#pk")),
					tempNo:$("#tempNo").val(),
					prjItem:$("#prjItem").val()
				}, function(data){
					result = data;
				});
				return result;
			}
			function save(){
			
				$("#tempDetailDataForm").bootstrapValidator("validate");
				if(!$("#tempDetailDataForm").data("bootstrapValidator").isValid()) return;
				
				if(EditValid($("#planBegin").val()) || EditValid($("#planEnd").val())){
					return;
				}
				
				if($("#prjItem").val() == ""){
					art.dialog({
						content:"请选择施工项目名称"
					});
					return;
				}
				
				if($("#spaceDay").val() == ""){
					art.dialog({
						content:"请填写间隔天数"
					});
					$("#spaceDay").focus();
					return;
				}
				
				if($("#tmpType").val()==="2"&&($("#befPrjItem").val()==$("#prjItem").val())){
					art.dialog({
						content:"施工项目和上一节点不能相同"
					});
					return;
				}
				
				if($("#mm_umState").val() == "E"){//从编辑界面进入
					if($("#m_umState").val() == "A" || $("#m_umState").val() == "C" || $("#m_umState").val() == "P"){
						if(isPrjItemExist()){
							art.dialog({
								content:"您选择的施工项目名称已存在,请重新选择"
							});
							return;
						}
						ajaxPost("${ctx}/admin/gcjdmb/doSaveProgTempDt", {
							tempNo:$("#tempNo").val(),
							prjItem:$("#prjItem").val(),
							planBegin:$("#planBegin").val(),
							planEnd:$("#planEnd").val(),
							spaceDay:$("#spaceDay").val(),
							befPrjItem:$("#befPrjItem").val(),
							dispSeq:$("#dispSeq").val(),
							type:$("#type").val(),
							baseConDay:$("#baseConDay").val(),
							baseWork:$("#baseWork").val(),
							dayWork:$("#dayWork").val(),
						}, function(data){
							if(data.rs){
								$("#isTipExit").val("1");
								if($("#m_umState").val() == "P"){
									if($("#tmpType").val()=="1"){
										$("#planBegin").val("");
										$("#planEnd").val("");
									}
									$("#dispSeq").val(Number($("#dispSeq").val())+1);
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
						ajaxPost("${ctx}/admin/gcjdmb/doUpdateProgTempDt", {
							pk:$("#pk").val(),
							prjItem:$("#prjItem").val(),
							planBegin:$("#planBegin").val(),
							planEnd:$("#planEnd").val(),
							spaceDay:$("#spaceDay").val(),
							expired:$("#expired").val(),
							befPrjItem:$("#befPrjItem").val(),
							type:$("#type").val(),
							baseConDay:$("#baseConDay").val(),
							baseWork:$("#baseWork").val(),
							dayWork:$("#dayWork").val(),
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
							planbegin:$("#planBegin").val(),
							planend:$("#planEnd").val(),
							spaceday:$("#spaceDay").val(),
							expired:"F",
							actionlog:"ADD",
							lastupdatedby:"${data.lastUpdatedBy}",
							lastupdate:new Date(),
							note:$("#prjItem").val() != ""?$("#prjItem > option[value="+$("#prjItem").val()+"]").html():"",
							pk:parseInt($("#nowPk").val())+1,
							befprjitem:$("#befPrjItem").val(),
							befprjitemdescr:$("#befPrjItem").val() != ""?$("#befPrjItem > option[value="+$("#befPrjItem").val()+"]").html():"",
							dispseq:$("#dispSeq").val(),
							type:$("#type").val(),
							typedescr:$("#type").val() != ""?$("#type > option[value="+$("#type").val()+"]").html():"",
							baseconday:$("#baseConDay").val(),
							basework:$("#baseWork").val(),
							daywork:$("#dayWork").val(),
						});
						$("#nowPk").val(parseInt($("#nowPk").val())+1);
						$("#isTipExit").val("1");
						if($("#m_umState").val() == "P"){
							if($("#tmpType").val()=="1"){
								$("#planBegin").val("");
								$("#planEnd").val("");
							}
							$("#dispSeq").val(Number($("#dispSeq").val())+1);
							return;
						}
						closeWin();
					}else if($("#m_umState").val() == "M"){
						Global.Dialog.returnData.push({
							rowId:$("#rowId").val(),
							prjitem:$("#prjItem").val(),
							planbegin:$("#planBegin").val(),
							planend:$("#planEnd").val(),
							spaceday:$("#spaceDay").val(),
							lastupdatedby:"${data.lastUpdatedBy}",
							lastupdate:new Date(),
							note:$("#prjItem").val() != ""?$("#prjItem > option[value="+$("#prjItem").val()+"]").html():"",
							expired:$("#expired").val(),
							actionlog:"EDIT",
							befprjitem:$("#befPrjItem").val(),
							befprjitemdescr:$("#befPrjItem").val() != ""?$("#befPrjItem > option[value="+$("#befPrjItem").val()+"]").html():"",
							type:$("#type").val(),
							typedescr:$("#type").val() != ""?$("#type > option[value="+$("#type").val()+"]").html():"",
							baseconday:$("#baseConDay").val(),
							basework:$("#baseWork").val(),
							daywork:$("#dayWork").val(),
						});
						$("#isTipExit").val("1");
						closeWin();
					}
				}
			}
			
			function validateRefresh(){
				 $("#tempDetailDataForm").data("bootstrapValidator")
	                 .updateStatus("planBegin", "NOT_VALIDATED",null)   
	                 .validateField("planBegin")
	                 .updateStatus("planEnd", "NOT_VALIDATED",null)  
	                 .validateField("planEnd")
	                 .updateStatus("spaceDay", "NOT_VALIDATED",null)  
	                 .validateField("spaceDay");                    
			}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs" style="float:left">
	    				<button id="tempDetailViewSaveBtn" type="button" class="btn btn-system" onclick="save()">保存</button>
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
							<form action="" method="post" id="tempDetailDataForm" class="form-search">
								<input type="hidden" name="jsonString" value="" />
								<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState }"/>
								<input type="hidden" id="mm_umState" name="mm_umState" value="${data.mm_umState }"/>
								<input type="hidden" id="tempNo" name="tempNo" value="${data.tempNo }"/>
								<input type="hidden" id="expired" name="expired" value="${data.expired}"/>
								<input type="hidden" id="isTipExit" name="isTipExit" value="0"/>
								<input type="hidden" id="rowId" name="rowId" value="${data.rowId }"/>
								<input type="hidden" id="nowPk" name="nowPk" value="${data.nowPk }"/>
								<input type="hidden" id="dispSeq" name="dispSeq" value="${data.dispSeq+1 }"/>
								<input type="hidden" id="tmpType" name="tmpType" value="${data.tmpType }"/>
								<ul class="ul-form">
									<li>
										<label>施工项目编号</label>
										<input type="text" id="pk" name="pk" value="${data.pk}" placeholder="保存自动生成"/>
									</li>
									<li class="dynamic">
										<label>计算类型</label>
										<house:xtdm id="type" dictCode="DAYCALCTYPE" style="width:160px;" value="${data.type}"/>
									</li>
									<li>	
										<label>施工项目名称</label>
										<house:dict id="prjItem" dictCode="" sql="select Descr, Code from tPrjItem1 where Expired='F' order by Seq" 
													sqlValueKey="Code" sqlLableKey="Descr" value="${data.prjItem }"></house:dict>
									</li>
									<li class="dynamic">
										<label>基础天数</label>
										<input type="text" id="baseConDay" name="baseConDay" style="width:160px;"
											onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
											value="${data.baseConDay}"/>
									</li>
									<div id="staticDiv">
										<li class="form-validate">
											<label>计划开始日(第n日)</label>
											<input type="text" id="planBegin" name="planBegin" value="${data.planBegin }"/>
										</li>
										<li class="form-validate">
											<label>计划结束日(第n日)</label>
											<input type="text" id="planEnd" name="planEnd" value="${data.planEnd }" />
										</li>
									</div>
									
									<li class="form-validate ">
										<label>间隔天数</label>
										<input type="text" id="spaceDay" name="spaceDay" value="${data.spaceDay}"/>
									</li>
									<li class="dynamic">
										<label>基础工作量</label>
										<input type="text" id="baseWork" name="baseWork" style="width:160px;"
											onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
											value="${data.baseWork}"/>
									</li>
									<div id="dynamicDiv">
										<li>
											<label>上一节点</label>
											<house:dict id="befPrjItem" dictCode="" sql="select Descr, Code from tPrjItem1 where Expired='F' order by Seq" 
														sqlValueKey="Code" sqlLableKey="Descr" value="${data.befPrjItem }"></house:dict>
										</li>
										<li class="dynamic">
											<label>每日工作量</label>
											<input type="text" id="dayWork" name="dayWork" style="width:160px;"
												onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" 
												value="${data.dayWork}"/>
										</li>
										<li class="search-group-shrink" hidden>
											<input type="checkbox" id="expired_show" name="expired_show"
												   onclick="checkExpired(this)" ${data.expired=='T'?'checked':'' }/><p>过期</p>
										</li>
									</div>
									
								</ul>
							</form>
						</div> 
					</div>	
				</div>
			</div>
		</div>
	</body>
</html>


