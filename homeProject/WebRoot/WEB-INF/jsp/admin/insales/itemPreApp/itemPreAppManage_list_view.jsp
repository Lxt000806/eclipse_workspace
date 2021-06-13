<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>领料预申请 修改查看页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">
		function validateRefresh(){
			$("#dataForm").data("bootstrapValidator").updateStatus("remarks", "NOT_VALIDATED", null).validateField("remarks");                    
		}
		function ajaxPost(opChinese, opEnglish){
			art.dialog({
	    		content:"是否确认要进行"+opChinese+"操作",
	    		ok:function(){
					$("#dataForm").bootstrapValidator("validate");
					if(!$("#dataForm").data("bootstrapValidator").isValid()){
						return;
			    	}
			    	$.ajax({
						url:"${ctx}/admin/itemPreApp/do"+opEnglish,
						type:"post",
						dataType:"json",
			  			data:{
			  				no:$("#no").val(),
			  				remarks:$("#remarks").val(),
			  				endCode:$("#endCode").val(),
			  				m_umState:$("#m_umState").val()
			  			},
						cache: false,
						error: function(obj){			    		
							art.dialog({
								content: opChinese+"出错,请重试",
								time: 3000,
								beforeunload: function () {
							    }
							});
					    },
					    success: function(obj){
					    	if(obj.rs){
					    		art.dialog({
									content: obj.msg,
									time: 3000,
									beforeunload: function () {
										closeWin();
								    }
								});
					    	}else{
					    		$("#_form_token_uniq_id").val(obj.token.token);
					    		art.dialog({
									content: obj.msg,
									width: 200
								});
					    	}
					    }
					}); 
	    		},
	    		cancel:function(){}
	    	});	
		}
		/**初始化表格*/
		$(function(){
			// 工人申请的就显示工人信息 --add by zb
			if ("" == "${itemPreApp.appCzy}") {
				$("#appCzy").openComponent_employee({
		   			showValue:"${itemPreApp.workerCode}",
		   			showLabel:"${itemPreApp.workerName}"
		   		});
			} else {
		   		$("#appCzy").openComponent_employee({
		   			showValue:"${itemPreApp.appCzy}",
		   			showLabel:"${itemPreApp.appCzyDescr}"
		   		});	
			}
			if("${itemPreApp.itemType1}"=="JZ"){//基装显示申请工人
				$("#workerCode").openComponent_employee({
		   			showValue:"${itemPreApp.workerCode}",
		   			showLabel:"${itemPreApp.workerName}",
		   			readonly:true
		   		});
			}
			$("#openComponent_employee_appCzy").attr("readonly", true);
			$("#openComponent_employee_appCzy").next().attr("data-disabled", true);
	   		$("#confirmCzy").openComponent_employee({
	   			showValue:"${itemPreApp.confirmCzy}",
	   			showLabel:"${itemPreApp.confirmCzyDescr}"
	   		});	
	   		$("#openComponent_employee_confirmCzy").attr("readonly", true);
			$("#openComponent_employee_confirmCzy").next().attr("data-disabled", true);
			$("select").attr("disabled", true);
		    if($("#m_umState").val().trim()!="M"){
		    	$(".buttons > #"+$("#m_umState").val().trim()+"But").removeAttr("disabled");
		    }
			if($("#m_umState").val().trim() == "M"){
				$(".container-fluid > ul.nav-tabs a:last").tab("show");
			}else{
				$(".container-fluid > ul.nav-tabs a:first").tab("show");
			}
			if($("#m_umState").val().trim() == "OC"){
				$("#endCode").removeAttr("disabled");
			}
			$("#dataForm").bootstrapValidator({
				message : "This value is not valid",
				feedbackIcons : {/*input状态样式图片*/
					invalid : "glyphicon glyphicon-remove",
					validating : "glyphicon glyphicon-refresh"
				},
				fields: {  
					remarks:{
						validators:{
							stringLength: {
	                            max: 1000,
	                            message: "长度不超过1000个字符"
	                        } 
						}
					}
				},
				submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
			});	
		})
		function photoDownload(){
			var number = Global.JqGrid.allToJson("dataTable_material", "photoname").datas.length;
			if(number <= 0 ){
				art.dialog({
					content:"该记录没有图片",
					time:3000
				});
				return;
			}
			window.open("${ctx}/admin/itemPreApp/downLoad?no="+$("#no").val());
		}
		function qxsq(){
			ajaxPost("取消申请", "Qxsq");
		}
		function receive(){
			if($("#itemType1").val() == "ZC"){
				var appItemCodes = $("#dataTable_applyList").jqGrid("getCol", "itemcode");
				$.ajax({
					url:"${ctx}/admin/itemPreApp/checkAllInfo",
					type:"post",
					dataType:"json",
		  			data:{
		  				appItemCodes : appItemCodes.join(","), 
		  				itemCode : appItemCodes[0], 
		  				custCode : $("#custCode").val(),
		  				isSetItem : $("#isSetItem").val()
		  			},
					cache: false,
					error: function(obj){			    		
						art.dialog({
							content: "接收检查出现异常",
							time: 3000,
							beforeunload: function () {
						    }
						});
				    },
				    success: function(obj){
				    	if(obj.rs){
				    		if(obj.datas.length > 0){
				    			art.dialog({
				    				content:"存在"+obj.datas[0].descr+"等"+obj.datas.length+"项材料本次应下单，但未下单，是否继续接收?",
				    				ok:function(){
										ajaxPost("接收", "Receive");
				    				},
				    				cancel:function(){}
				    			});
				    		}else{
								ajaxPost("接收", "Receive");
				    		}
				    	}else{
				    		art.dialog({
				    			content:obj.msg
				    		});
				    	}
				    }
				}); 
			}else{
				ajaxPost("接收", "Receive");
			}
		}
		function thsq(){
			ajaxPost("退回申请", "Thsq");
		}
		function thtj(){
			ajaxPost("退回提交", "Thtj");
		}
		function xdwc(){
			if($("#endCode").val()==""){
				art.dialog({
					content:"处理结果不能为空"
				});
				return ;
			}
			ajaxPost("下单完成", "Xdwc");
		}
		</script>
	</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs buttons" >
	    				<button id="RBut" type="button" class="btn btn-system" onclick="receive()" disabled>接收</button>
	    				<button id="CBut" type="button" class="btn btn-system" onclick="qxsq()" disabled>取消申请</button>
	    				<button id="BCBut" type="button" class="btn btn-system" onclick="thtj()" disabled>退回提交</button>
	    				<button id="BABut" type="button" class="btn btn-system" onclick="thsq()" disabled>退回申请</button>
	    				<button id="OCBut" type="button" class="btn btn-system" onclick="xdwc()" disabled>下单完成</button>
	    				<button id="photoBut" type="button" class="btn btn-system" onclick="photoDownload()">图片下载</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" enctype="multipart/form-data" class="form-search">
						<input type="hidden" value="${itemPreApp.m_umState}" id="m_umState" name="m_umState"/> 
						<input type="hidden" id="custCode" name="custCode" value="${itemPreApp.custCode}" readonly/>
						<ul class="ul-form">
							<li>
								<label>领料预申请单号</label>
								<input type="text" id="no" name="no" value="${itemPreApp.no}" readonly/>
							</li>
							<li>
								<label>材料类型1</label>
								<house:dict id="itemType1" dictCode="" 
											sql="SELECT cbm,(cbm+' '+note) note FROM tXTDM  WHERE ID='ITEMRIGHT' AND CBM IN ${itemPreApp.itemRight } ORDER BY IBM ASC" 
											sqlValueKey="cbm" sqlLableKey="note" value="${itemPreApp.itemType1 }" >
								</house:dict>
							</li>
							<li>
								<label>申请人电话</label>
								<input type="text" id="phone" name="phone" value="${itemPreApp.phone}" readonly/>
							</li>
							<li>
								<label>签订时间</label>
								<input type="text" id="signDate" name="signDate" value="<fmt:formatDate value='${itemPreApp.signDate }' pattern='yyyy-MM-dd'/>" readonly/>
							</li>
	<%-- 						<li>
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" value="${itemPreApp.custCode}" readonly/>
							</li> --%>
							<li>
								<label>楼盘地址</label>
								<input type="text" id="address" name="address" value="${itemPreApp.address}" readonly/>
							</li>
							<li>	
								<label>是否套餐材料</label>
								<house:xtdm id="isSetItem" dictCode="YESNO" value="${itemPreApp.isSetItem }" ></house:xtdm>
							</li>
							<li>
								<label>接收时间</label>
								<input type="text" id="confirmDate" name="confirmDate" value="<fmt:formatDate value='${itemPreApp.confirmDate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly/>
							</li>
							<li>
								<label>状态</label>
								<house:xtdm id="status" dictCode="PREAPPSTATUS" value="${itemPreApp.status }" ></house:xtdm>
							</li>
							<li>
								<label>档案号</label>
								<input type="text" id="documentNo" name="documentNo" value="${itemPreApp.documentNo}" readonly/>
							</li>
							<li>
								<label>申请日期</label>
								<input type="text" id="date" name="date" value="<fmt:formatDate value='${itemPreApp.date }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly/>
							</li>
							<li>
								<label>接收人</label>
								<input type="text" id="confirmCzy" name="confirmCzy" value="${itemPreApp.confirmCzy}" />
							</li>
							<li>
								<label>处理结果</label>
								<house:xtdm id="endCode" dictCode="PREAPPENDCODE" unShowValue="${itemPreApp.unShowEndCode }" value="${itemPreApp.endCode}" ></house:xtdm>
							</li>
							<li>
								<label>面积</label>
								<input type="text" id="area" name="area" value="${itemPreApp.area}" readonly/>
							</li>
							<li>
								<label>申请人</label>
								<input type="text" id="appCzy" name="appCzy" value="${itemPreApp.appCzy}" />
							</li>
							<c:if test="${itemPreApp.itemType1=='JZ' }">
								<li>
									<label>申请工人</label>
									<input type="text" id="workerCode" name="workerCode" value="${itemPreApp.workerCode}" />
								</li>
							</c:if>
							<li class="form-validate">
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks">${itemPreApp.remarks }</textarea>
							</li>
						</ul>
					</form>
				</div>
			</div>
			
			<div class="clear_float"> </div>
			
			<div  class="container-fluid" >  
			     <ul class="nav nav-tabs" >
			        <li class=""><a href="#itemPreAppManage_tabView_applyList" data-toggle="tab">领料预申请管理明细</a></li>  
			        <li class=""><a href="#itemPreAppManage_tabView_materialPhoto" data-toggle="tab">材料图片</a></li>
			        <li class=""><a href="#itemPreAppManage_tabView_measure" data-toggle="tab">测量下单</a></li>  
			    </ul>  
			    <div class="tab-content">  
					<div id="itemPreAppManage_tabView_applyList"  class="tab-pane fade "> 
			         	<jsp:include page="itemPreAppManage_tabView_applyList.jsp">
			         		<jsp:param value="${itemPreApp.no}" name="no"/>
			         	</jsp:include>
					</div>  
					<div id="itemPreAppManage_tabView_materialPhoto"  class="tab-pane fade "> 
			         	<jsp:include page="itemPreAppManage_tabView_materialPhoto.jsp">
			         		<jsp:param value="${itemPreApp.no}" name="no"/>
			         	</jsp:include>
					</div>
					<div id="itemPreAppManage_tabView_measure"  class="tab-pane fade "> 
			         	<jsp:include page="itemPreAppManage_tabView_measure.jsp">
			         		<jsp:param value="${itemPreApp.m_umState }" name="fromFlag"/>
			         	</jsp:include>
					</div>
				</div>	
			</div>	
		</div>
	</body>
</html>
