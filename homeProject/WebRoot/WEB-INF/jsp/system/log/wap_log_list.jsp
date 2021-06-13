<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>

	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>日志列表</title>
	<link href="${resourceRoot}/css/iss.core.css" rel="stylesheet" />
	<link href="${resourceRoot}/artDialog/skins/idialog.css" rel="stylesheet" />
	<link href="${resourceRoot}/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"  />
	<link href="${resourceRoot}/css/select.css" rel="stylesheet" />
	<script type="text/javascript" src="${resourceRoot}/js/jquery.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.core.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.cssTable.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.panelBar.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.pageBar.js"></script>
	<script type="text/javascript" src="${resourceRoot}/artDialog/artDialog.min.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/common.js"></script>
	<script type="text/javascript" src="${resourceRoot}/datePicker/WdatePicker.js" ></script>
	<script type="text/javascript" src="${resourceRoot}/zTree/js/jquery.ztree.all-3.1.js"></script>

<script type="text/javascript">

/**初始化表格*/
$(function(){
	$document = $(document);
	$('div.content-list>table', $document).cssTable().resetFixTableHeight();;
	$('div.panelBar', $document).panelBar();
	$('div.pagebar', $document).pageBar();

});

function clearForm(){
	$("#page_form input[type='text'], select").val('');
	$("#userid").val('');
}

function goto_query(){
	if($("#userid").val() == ''){
		$("#userid").remove();
	}

	$.form_submit($("#page_form").get(0), {
		"action" : "${ctx}/admin/log/goListWap",
		"pageNo" : "1"
	})
}

//联系关联池信息
$(function(){
	$('#search_auto').css({'width':$('input[name="username"]').width()+4});
	$('input[name="username"]').keyup(getData);
});
function getData(){
  var userName = document.getElementById("username").value;
  var pars = "userName="+userName;
  var url = '${ctx}/admin/log/selectuser';
  $.ajax({
       type: "post",
       cache:false,
       dataType:"html",
       url: url,
       data: pars,
       complete: function(html){
         if(html.responseText!=''){
	         $('#search_auto').html(html.responseText).css('display','block');
	         var showDiv = document.getElementById("search_auto");
	         showDiv.innerHTML=html.responseText;
         }else{
        	$('#search_auto').html('').css('display','none');
	     }
	   }
   });
}

function getvalue(id, username){
	$("#link").css("display","block");
	if(id != '-1'){
		$("#username").val(username);
		$("#userid").val(id);
	} else {
		$("#username").val('');
		$("#userid").val('');
	}
	
	$("#search_auto").fadeOut(100);
}


function view(logId){
	$.fnShowWindow_mid("${ctx}/admin/log/goDetailWap?logId="+logId);
}

</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="${ctx}/admin/log/goListWap" method="post" id="page_form" >
				<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }" />
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }" />
				<input type="hidden" id="totalPages" name="totalPages" value="${page.totalPages }" />
			
				<table cellpadding="0" cellspacing="0" width="100%">
						<col  width="82" />
						<col  width="250"/>
						<col  width="82"/>
						<col  width="250"/>
						<col  width="82"/>
						<col  width=""/>
					<tbody>
						<tr class="td-btn">
							<td class="td-label">操作人</td>
							<td class="td-value">
								<span style="position:relative;width:160px;z-index: 999">
									<input autocomplete="off" class="k" type="text" id="username" name="username" style="width:160px;" onfocus="getData();" value='<house:username userId="${logObj.userid }"></house:username>'/>
								  	<span id=search_auto style="position: absolute;left: 0px;top:23px;height: 150px;overflow-X: none; overflow-y: scroll;z-index:9999;"></span>
									<input type="hidden" id="userid" name="userid" value="${logObj.userid }"/>
						        </span>
							</td>
							<td class="td-label">模块名称</td>
							<td class="td-value">
								<house:dict id="modelCode" dictCode="${ABSTRACT_DICT_LOG_MODULE}" headerLabel="--请选择日志模块--" value="${logObj.modelCode }"></house:dict>
							</td>
							<td class="td-label">IP地址</td>
							<td class="td-value"><input type="text" id="ip" name="ip" style="width:160px;"  value="${logObj.ip }" /></td>
						</tr>
						<tr class="td-btn">
							<td class="td-label">操作起始时间</td>
							<td class="td-value">
							<input type="text" style="width:160px;" id="startTime" name="startTime" class="i-date"  value="${startTime }" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
							</td>
							<td class="td-label">操作结束时间</td>
							<td class="td-value">
							<input type="text" style="width:160px;" id="endTime" name="endTime" class="i-date" value="${endTime }"  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'})"/>
							</td>
							<td class="td-label">操作内容</td>
							<td class="td-value"><input type="text" id="logcontent" name="logcontent" style="width:160px;"  value="${logObj.logcontent }" /></td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input onclick="goto_query();"  class="i-btn-s" type="button"  value="检索"/>
									<input onclick="clearForm();"  class="i-btn-s" type="button"  value="清空"/>
								</div>
						  	</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div class="content-list">
				<table cellpadding="0" cellspacing="0" style="width:99.8%">
					<col width="30px"/>
					<col width="100px"/>
					<col width=""/>
					<col width="100px"/>
					<col width="140px"/>
					<col width=""/>
					<thead>
						<tr>
							<th>
								序号
							</th>
							<th>
								模块名称
							</th>
							<th>
								操作人
							</th>
							<th>
								IP地址
							</th>
							<th>
								操作时间
							</th>
							<th>
								操作内容
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${page.result}"  varStatus="item_index">
						<tr >
							<td class="td-seq">
								${item_index.index+1}
							</td>
							<td  class="td-center">
								<a href="javascript:void(0)" onclick="view('${item.ID }')">
									<house:dict id="tt" dictCode="${ABSTRACT_DICT_LOG_MODULE}" readonly="true" value="${item.MODEL_CODE}"></house:dict>
								</a>
							</td>
							<td class="td-center">
								<house:username userId="${item.USER_ID}"></house:username>
							</td>
							<td class="td-center">
								${item.IP}
							</td>
							<td class="td-center">
								${item.ADDTIME}
							</td>
							<td title="${item.OPRCONTENT}" class="td-center">
								${item.OPRCONTENT}
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- form_id: 查询表单ID;action: 表单查义URL   -->
			<jsp:include page="/commons/jsp/page_footer.jsp" >
				<jsp:param name="action" value="${ctx}/admin/log/goListWap"/>
				<jsp:param name="form_id" value="page_form" />
			</jsp:include>
			</div> 
		</div>
</body>
</html>


