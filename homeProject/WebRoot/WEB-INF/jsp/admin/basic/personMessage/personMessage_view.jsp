<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>查看毛利参数</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <%@ include file="/commons/jsp/common.jsp" %>

<style type="text/css">
label{
    width:140px !important;   
}
</style>

<script type="text/javascript">
$(function(){
    //通过js选择器获取dataForm表单下的所有input后代，并添加disabled属性
    document.querySelectorAll("#dataForm input")
            .forEach(
	               function(input){
	                   input.disabled="disabled";
	              }
             );
             
     $("#msgText").prop("readonly","readonly");
     $("#remarks").prop("readonly","readonly");
});

</script>

</head> 

    <body>
        <div class="body-box-form">
            <div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(true)">
                            <span>关闭</span>
                        </button>
                    </div>
                </div>
            </div>

            <div class="panel panel-info" style="margin: 0px;">  
				<div class="panel-body">
                	<form action="" method="post" id="dataForm" class="form-search">
	                	<ul class="ul-form">
	                    	<div class="validate-group">
		                    	<li class="form-validate">
		                        	<label><span class="required">*</span>消息编号</label>
		                            <input type="text" id="pk" name="pk" value="${personMessage.pk}" />
		                        </li>
		                        <li class="form-validate">
	                                <label><span class="required">*</span>消息类型</label>
	                                <input type="text" id="bMsgType" name="bMsgType" value="${personMessage.bMsgType}" />
	                            </li>
                            </div>
	                           <div class="validate-group">
		                           <li class="form-validate">
		                               <label><span class="required">*</span>施工消息类型</label>
		                               <input type="text" id="progMsgTypeDescr" name="progMsgTypeDescr" value="${personMessage.progMsgTypeDescr}" />
		                           </li>
		                           <li class="form-validate">
	                                   <label><span class="required">*</span>生成日期</label>
	                                   <input type="text" id="crtDate" name="crtDate" value="${personMessage.crtDate}" />
	                               </li>
                               </div>
                               <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>材料类型1</label>
	                                   <input type="text" id="itemType1Descr" name="itemType1Descr" value="${personMessage.itemType1Descr}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>发送日期</label>
	                                   <input type="text" id="sendDate" name="sendDate" value="${personMessage.sendDate}" />
	                               </li>
	                           </div>
	                           <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>材料类型2</label>
	                                   <input type="text" id="itemType2Descr" name="itemType2Descr" value="${personMessage.itemType2Descr}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>消息相关客户号</label>
	                                   <input type="text" id="msgRelCustCode" name="msgRelCustCode" value="${personMessage.msgRelCustCode}" />
	                               </li>
                               </div>
                               <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>工种分类12</label>
	                                   <input type="text" id="workType12Descr" name="workType12Descr" value="${personMessage.workType12Descr}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>接收人类型</label>
	                                   <input type="text" id="bRcvType" name="bRcvType" value="${personMessage.bRcvType}" />
	                               </li>
                               </div>
                               <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>施工项目</label>
	                                   <input type="text" id="prjItemDescr" name="prjItemDescr" value="${personMessage.prjItemDescr}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>接收人</label>
	                                   <input type="text" id="bRcvCZY" name="bRcvCZY" value="${personMessage.bRcvCZY}" />
	                               </li>
                               </div>
                               <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>任务类型</label>
	                                   <input type="text" id="jobTypeDescr" name="jobTypeDescr" value="${personMessage.jobTypeDescr}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>接收日期</label>
	                                   <input type="text" id="rcvDate" name="rcvDate" value="${personMessage.rcvDate}" />
	                               </li>
                               </div>
                               <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>计划处理时间</label>
	                                   <input type="text" id="planDealDate" name="planDealDate" value="${personMessage.planDealDate}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>接收状态</label>
	                                   <input type="text" id="bRcvStatus" name="bRcvStatus" value="${personMessage.bRcvStatus}" />
	                               </li>
                               </div>
                               <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>计划进场时间</label>
	                                   <input type="text" id="planArrDate" name="planArrDate" value="${personMessage.planArrDate}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>是否推送</label>
	                                   <input type="text" id="bIsPush" name="bIsPush" value="${personMessage.bIsPush}" />
	                               </li>
                               </div>
                               <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>标题</label>
	                                   <input type="text" id="title" name="title" value="${personMessage.title}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>推送标志</label>
	                                   <input type="text" id="bPushStatus" name="bPushStatus" value="${personMessage.bPushStatus}" />
	                               </li>
                               </div>
                               <div class="validate-group">
	                               <li >
	                                   <label style="top: -40px;" class="control-textarea"><span class="required">*</span>消息内容</label>
	                                   <textarea type="text" id="msgText" name="msgText" style="height:80px;width:160px" >${personMessage.msgText}</textarea>
	                               </li>
	                               <li>
	                                   <label class="control-textarea" style="top: -40px;"><span class="required">*</span>处理说明</label>
	                                   <textarea type="text" id="remarks" name="remarks" style="height:80px;width:160px">${personMessage.remarks}</textarea>
	                               </li>
                               </div>
	                        </ul>
	                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
