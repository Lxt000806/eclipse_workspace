<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%> --%>
<%
String path = request.getContextPath();
System.out.println(path);
/* String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/"; */
%>
<!DOCTYPE HTML>
<html>
  <head>
  
  	<title>添加广告信息</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript" src="${ctx}/commons/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctx}/commons/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" src="${ctx}/commons/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
		UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	    UE.Editor.prototype.getActionUrl = function(action) {
	        if (action == "uploadimage" || action == "uploadscrawl") {
	            return "${ctx}/admin/advert/uploadImage";//这就是自定义的上传地址
	        } else if (action == "uploadvideo") {
	            return "";
	        } else {
	            return this._bkGetActionUrl.call(this, action);
	        }
	    };
    </script>
  	<script type="text/javascript">
		$(function() {
			UE.getEditor("editor", {
    			autoHeightEnabled: false,
    			focus: true
			});
		});
		
		function save(){
			if($("#advType").val() == ""){
				art.dialog({
					content: "请选择广告类型"
				});
				return;
			}
			$.ajax({
				url: "${ctx}/admin/advert/doSave",
				type: "post",
		    	data: {
		    		advType: $("#advType").val(),
		    		picAddr: $("#picAddr").val(),
		    		content: UE.getEditor("editor").getContent(),
		    		title: $("#title").val(),
		    		dispSeq: $("#dispSeq").val(),
		    		outUrl: $("#outUrl").val()
		    	},
				dataType: "json",
				cache: false,
				error: function(obj){			    		
					art.dialog({
						content: "访问出错,请重试",
						time: 3000,
						beforeunload: function () {}
					});
				},
				success: function(res){
					art.dialog({
						content: res.msg,
						ok: function(){
							if(res.rs){
								closeWin();
							}
						}
					});
				}
			});	
		}
		
		function loadImage(){
			$("#file").click();
		}
		
		function uploadImage(){
			var formData = new FormData();
			formData.append("file", document.getElementById("file").files[0]);
			$.ajax({
				url: "${ctx}/admin/advert/uploadImage",
			  	type: "POST",
			  	data: formData,
			  	contentType: false,
			  	processData: false,
			  	success: function (data){
			  		if(data.rs){
						$("#picAddr").val(data.datas.url);
			  		}else{
			  			art.dialog({
			  				content: data.datas.state
			  			});
			  		}
			  	},
				error: function () {
					art.dialog({
						content: "上传文件失败!"
					});
				}
			});
		}
		
  	</script>

  </head>
  
  <body>
  	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
     				<button id="saveBut" type="button" class="btn btn-system "   onclick="save()">保存</button>
     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<li>
							<label>广告类型</label>
							<house:xtdm id="advType" dictCode="ADVTYPE"></house:xtdm>
						</li>
						<li>
							<label>图片地址</label>
							<input type="text" id="picAddr" name="picAddr" placeholder="请输入图片地址" onchange="imageChange()" style="width: 500px"/>
							<button type="button" onclick="loadImage()">广告图</button>
							<input type="file" id="file" onchange="uploadImage()" accept="image/*" style="display:none"/>
						</li>
						<li>
							<label>标题</label>
							<input type="text" id="title" name="title" placeholder="请输入标题"/>
						</li>
						<li>
							<label>显示顺序</label>
							<input type="text" id="dispSeq" name="dispSeq" placeholder="请输入显示顺序" value="0"/>
						</li>
						<li>
							<label>外部链接</label>
							<input type="text" id="outUrl" name=""outUrl"" placeholder="请输入外部链接"/>
						</li>
					</ul>	
				</form>
			</div>
		</div>
		<script id="editor" type="text/plain" style="width: 100%;height: 280px"></script>
  	</div>
  </body>
</html>
