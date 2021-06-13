<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Information明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	function downloadInformation(pk){
		window.location.href="${ctx}/admin/information/downloadNew?pk="+pk;
	}
	
	function preViewInformation(pk,filename){
		if(filename.indexOf("pdf") != -1){
			Global.Dialog.showDialog("previewPdf",{ 
	   	  		title:"pdf预览",
	   	  		url:"${ctx}/admin/information/previewPdf",
	   	  		postData: {
	   	  		    pk:pk
	   	  		},
	   	  		height:755,
	   	  		width:937,
	   	  		returnFun:goto_query
	        });		
		}else{
			Global.Dialog.showDialog("previewPic",{ 
	   	  		title:"图片预览",
	   	  		url:"${ctx}/admin/information/goPreviewPic",
	   	  		postData: {
	   	  		    pk:pk
	   	  		},
	   	  		height:755,
	   	  		width:937,
	   	  		returnFun:goto_query
	        });		
		}
		
	}
	
	function updateStatus(){
		var url = "${ctx}/admin/infoRead/doUpdateStatus?id=${information.number}";
		$.ajax({
			url : url,
			data : {},
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			dataType: 'json',
			type: 'post',
			cache: false,
			error: function(){
		        art.dialog({
					content: '修改记录出现异常'
				});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
							goto_query();
					    }
					});
		    		closeWin();
		    	}else{
		    		art.dialog({
						content: obj.msg
					});
		    	}
			}
		});
	}
	$(function(){
		replaceCloseEvt("information_detail",doClose );
	});
	
	function doClose(){
		closeWin();
	}
	</script>
</head>
<body>
<div class="body-box-form">
	<div class="content-form">
		<div class="panel panel-system">
		    <div class="panel-body" >
		      <div class="btn-group-xs" >
		      <c:if test="${information.infoType=='2'}">
		      	<button type="button" id="saveBtn" class="btn btn-system" onclick="updateStatus()">已读</button>
		      </c:if>
		      <button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
		      </div>
		    </div>
		</div>
		<div class="panel panel-info">  
        	<div class="panel-body">
			<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
				<ul class="ul-form">
					<li>
						<label>主题</label>
						<input type="text" id="infoTitle" name="infoTitle" value="${information.infoTitle }" readonly="readonly"/>
					</li>
					<li>
						<label>发布人</label>
						<input type="text" id="sendCzyDescr" name="sendCzyDescr" value="${information.sendCzyDescr }" readonly="readonly"/>
					</li>
					<li>
						<label>发布时间</label>
						<input type="text" id="sendDate" name="sendDate" value="<fmt:formatDate value="${information.sendDate}" pattern="YYYY-MM-dd HH:mm:ss"/>" readonly="readonly"/>
					</li>
					<li>
						<label class="control-textarea" style="vertical-align: middle;">内容</label>
						<textarea id="infoText" name="infoText" readonly="readonly" style="height: 200px;width: 740px;">${information.infoText}</textarea>
					</li>
					<li class="col-sm-12" style="text-align: left;margin-top: 130px;display:flex;flex-direction:row;">
						<label>附件</label>
						<div style="display:flex;flex-direction:column;margin-left:7px"> 
							<c:forEach items="${infoAttachList}" var="item" varStatus="item_index">
								<c:choose>
									<c:when test="${item.filename.indexOf('pdf') != -1 || item.filename.indexOf('jpg') != -1 
										|| item.filename.indexOf('png') != -1 || item.filename.indexOf('gif') != -1 
										|| item.filename.indexOf('jpeg') != -1 }">
										<a href="javascript:void(0);" onclick="preViewInformation(${item.pk},'${item.filename}')">${item.filename}预览</a>
										<a href="javascript:void(0);" onclick="downloadInformation(${item.pk},'${item.filename}')">${item.filename}下载</a><br>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0);" onclick="downloadInformation(${item.pk})">${item.filename}&nbsp下载</a><br>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>
					</li>
				</ul>
			</form>
		</div>
		</div>
	</div>
</div>
</body>
</html>

