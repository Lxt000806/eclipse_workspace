<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>工作流 -- 业务申请</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	var origin = "";

    function save(type, skipArtDialog){
    	if((type == "doApprovalTask" || type == "doRejectTask") && !skipArtDialog){
    		art.dialog({
    			title: type == "doApprovalTask" ? "请输入同意意见" : "请输入拒绝意见",
    			padding: "0",
    			width: "250px",
    			height: "250px",
    			content: "<textarea id=\"artDialogComment\" style=\"width:250px;height:250px\">"+$("#comment").val()+"</textarea>",
    			lock: true,
    			ok: function(){
    				$("#comment").val($("#artDialogComment").val());
    				save(type, true);
    			},
    			cancel: function(){}
    		});
    		return;
    	}
    
    	$("#applyBtn").attr("disabled", true).addClass("workFlowBtnDisabled");
    	$("#agreeBtn").attr("disabled", true).addClass("workFlowBtnDisabled");
    	$("#disAgreeBtn").attr("disabled", true).addClass("workFlowBtnDisabled");
    	$("#cancelBtn").attr("disabled", true).addClass("workFlowBtnDisabled");
    	
        $("#dataForm").bootstrapValidator("validate");
        if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
        var datas = $("#dataForm").serialize();
        $.ajax({
            url: "${ctx}/admin/wfProcInst/"+type,
            type: "post",
            data: datas,
            dataType: "json",
            cache: false,
            error: function(obj){
				art.dialog({
					content: "提交数据异常",
					lock: true
				});
    			$("#applyBtn").removeAttr("disabled").removeClass("workFlowBtnDisabled");
		    	$("#agreeBtn").removeAttr("disabled").removeClass("workFlowBtnDisabled");
		    	$("#disAgreeBtn").removeAttr("disabled").removeClass("workFlowBtnDisabled");
    			$("#cancelBtn").removeAttr("disabled").removeClass("workFlowBtnDisabled");
            },
            success: function(obj){
           		if(!obj){
           			obj = {};
           		}
           		obj.respType = 2;
				top.postMessage(obj, origin);
				if(!obj.rs){
					$("#_form_token_uniq_id").val(obj.token.token);
                }
    			$("#applyBtn").removeAttr("disabled").removeClass("workFlowBtnDisabled");
		    	$("#agreeBtn").removeAttr("disabled").removeClass("workFlowBtnDisabled");
		    	$("#disAgreeBtn").removeAttr("disabled").removeClass("workFlowBtnDisabled");
    			$("#cancelBtn").removeAttr("disabled").removeClass("workFlowBtnDisabled");
            }
         });
    }  
    
    $(function(){
		window.addEventListener("message", function(e){
			origin = e.origin;
		}, false);
		getCheckProcess();
    });
    function getCheckProcess(){
    	$.ajax({
			url: "${ctx}/admin/wfProcInst/getWfProcTrack?appType=${appType}&wfProcInstNo=${wfProcInstNo}",
			type: "get",
			dataType: "json",
			error: function(obj){},
			success: function(obj){
				for(var i = 0; i < obj.rows.length; i++){
					setData(obj.rows[i]);
				}
			}
		});	
    }
    
	function setData(data){
		$("#checkProcessUl").append("<li class=\"item\" >"
							  +data.checkczydescr+" "+(new Date(data.checkdate).format("yyyy-MM-dd hh:mm:ss"))+" "+data.actiontypedescr+"<br/>"
							  +(data.remarks && data.remarks != ''? "<br/>意见："+data.remarks+"<br/>" : "")+"</li>");
	}
</script>
<style type="text/css">
	.workFlowTabBar { 
		position: fixed;
		bottom: 0;
		width: 100%;
		height: 35px;
		background: white;
		padding: 0 5px;
		display: flex;
	}
	.workFlowBtn {    
		margin: 0 3px;
	    height: 100%;
	    width: 100%;
	    flex: 1;
	    background: #387EF5;
	    border: 0;
	    font-size: 17px;
	    color: white;
	}
	.workFlowBtn:focus{
		outline:0;
	}
	.workFlowBtn:active{
		background: rgba(56, 126, 245, 0.5);
	}
	
	.workFlowBtnDisabled {
		background: rgba(56, 126, 245, 0.5);
	}
	 
	.workFlowTabs {
		height: 30px;
		display: flex;
	}
	
	.workFlowTabs > li {
		flex: 1;
		width: 100%;
		background: #ABABAB;
		border-radius: 0px;
	}
	
	.workFlowForm {    
		position: fixed;
	    width: 100%;
	    overflow-y: auto;
	    bottom: 45px;
	    top: 30px;
	    border: 1px solid #ABABAB;
	    border-top: 0px;
	}
	
	.workFlowForm > div {
		border: 0px;
	}
	.news-list li{
		padding: 16px;
	}
	.news-list .new-content{
		font-size: 14px;
		margin-top: 6px;
		margin-left: 10px;
		color: #666;
	}
	
	.item {
		margin: 10px auto;
		width: 95%;
		border-radius: 10px;
		background: #F5F5F5;
	}
</style>
</head>
<body>
<div class="body-box-form" >
	<ul class="nav nav-tabs workFlowTabs" >
	    <li class="active"><a href="#workFlow_formInfo" data-toggle="tab">单据信息</a></li>  
<!-- 	    <li><a href="#workFlow_checkCZY" data-toggle="tab">审批人</a></li>   -->
		<c:if test="${m_umState != 'A'}">
	    	<li><a href="#workFlow_checkProcess" data-toggle="tab">审批流程</a></li>  
	    </c:if>
	    <li><a href="#workFlow_ProcessImg" data-toggle="tab">流程图</a></li>  
	</ul>  
    <div class="tab-content workFlowForm">  
    
		<div id="workFlow_formInfo"  class="tab-pane fade in active"> 
            <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<input type="hidden" name="wfProcNo" value="${wfProcNo}"/>
            	<input type="hidden" name="processDefinitionId" value="${processDefinition.id}"/>
            	<input type="hidden" name="taskId" value="${taskId}"/>
            	<input type="hidden" name="appType" value="${appType}"/>
            	<input type="hidden" id="comment" name="comment"/>
            	<input type="hidden" id="wfProcInstNo" value="${wfProcInstNo}"/>
                <house:token></house:token>
                <ul class="ul-form">
					<div class="validate-group row">
					    <li class="form-validate">
					        <label><span class="required">*</span>服务类型</label>
					        <select id="fp__tWfCust_erpAccountApply__0__type" name="fp__tWfCust_erpAccountApply__0__type" style="width: 160px;">
					            <option value="账号开通" ${ datas.fp__tWfCust_erpAccountApply__0__type == '账号开通' || m_umState == 'A' ? 'selected' : ''}>账号开通</option>
					            <option value="密码重置" ${ datas.fp__tWfCust_erpAccountApply__0__type == '密码重置' ? 'selected' : ''}>密码重置</option>
					        </select>
					    </li>
					</div>
                   
                </ul>
            </form>
		</div> 
		<div id="workFlow_checkCZY"  class="tab-pane fade"> 
			workFlow_checkCZY
		</div> 
		<div id="workFlow_checkProcess"  class="tab-pane fade"> 
			<ul id="checkProcessUl"></ul>
		</div> 
		<div id="workFlow_ProcessImg"  class="tab-pane fade"> 
			<c:if test="${m_umState  == 'A'}">
       			<iframe id="iframe_design" name="iframe_design" frameborder="0" 
       					src="${ctx }/admin/wfProcInst/getResource?resourceType=image&processDefinitionId=${processDefinition.id}" width="100%" height="550"></iframe>
			</c:if>
			<c:if test="${m_umState != 'A'}">
       			<iframe id="iframe_design" name="iframe_design" frameborder="0" 
       					src="${ctx }/admin/wfProcInst/getTraceImage?processInstanceId=${processDefinition.id}" width="100%" height="550"></iframe>
       		</c:if>
		</div> 
	</div>	
    <div class="workFlowTabBar">
		<c:if test="${m_umState == 'A'}">
    		<button class="workFlowBtn" type="button" id="applyBtn" onclick="save('doApply')">提交</button>
    	</c:if>
		<c:if test="${m_umState == 'C'}">
    		<button class="workFlowBtn" type="button" id="agreeBtn" onclick="save('doApprovalTask')">同意</button>
    		<button class="workFlowBtn" type="button" id="disAgreeBtn" onclick="save('doRejectTask')">不同意</button>
    	</c:if>
    	<c:if test="${m_umState == 'M' && status =='1'}">
    		<button class="workFlowBtn" type="button" id="cancelBtn" onclick="save('doCancelProcInst')">撤销</button>
    	</c:if>
    </div>
</div>
</body>
</html>
