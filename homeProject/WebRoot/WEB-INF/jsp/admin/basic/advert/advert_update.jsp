<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
  
  	<title>编辑广告信息</title>
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
  		var editor;
		$(function() {
			editor = UE.getEditor("editor", {
				autoHeightEnabled: false,
    			focus: true
			});
			editor.ready(function(){
				editor.execCommand("insertHtml", '${data.content}');
				var value = "${data.picAddr}";
				if(value && value != ""){
					$("#setFileName").val(value.substring(value.lastIndexOf("/")+1));
				}
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
				url: "${ctx}/admin/advert/doUpdate",
				type: "post",
		    	data: {
		    		advType: $("#advType").val(),
		    		picAddr: $("#picAddr").val(),
		    		content: editor.getContent(),
		    		pk: $("#pk").val(),
		    		expired: $("#expired").val(),
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
					<input type="hidden" id="pk" value="${data.pk}"/>
					<input type="hidden" id="expired" value="${data.expired}"/>
					<ul class="ul-form">
						<li>
							<label>广告类型</label>
							<house:xtdm id="advType" dictCode="ADVTYPE" value="${data.advType }"></house:xtdm>
						</li>
						<li>
							<label>图片地址</label>
							<input type="text" id="picAddr" name="picAddr" placeholder="请输入图片地址" value="${data.picAddr }" style="width: 400px"/>
							<button type="button" onclick="loadImage()">广告图</button>
							<input type="file" id="file" onchange="uploadImage()" accept="image/*" style="display:none"/>
						</li>
						<li>
							<label>标题</label>
							<input type="text" id="title" name="title" placeholder="请输入标题" value="${data.title }"/>
						</li>
						<li>
							<label>显示顺序</label>
							<input type="text" id="dispSeq" name="dispSeq" placeholder="请输入显示顺序" value="${data.dispSeq }"/>
						</li>
						<li>
							<label>外部链接</label>
							<input type="text" id="outUrl" name="outUrl" placeholder="请输入外部链接" value="${data.outUrl }" style="width: 400px"/>
						</li>
						<li class="search-group-shrink">
							<input type="checkbox" id="expired_show" name="expired_show"
									value="${data.expired}" onclick="checkExpired(this)"
									${data.expired=='T'?'checked':'' }/><p>过期&nbsp;&nbsp;&nbsp;&nbsp;</p>
						</li>
					</ul>	
				</form>
			</div>
		</div>
		<script id="editor" type="text/plain" style="width: 100%;height: 280px"></script>
  	</div>
  </body>
</html>
