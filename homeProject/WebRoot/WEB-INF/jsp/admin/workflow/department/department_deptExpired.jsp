<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>部门过期</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<style type="text/css">
    	.panelBar1{ height:23px; line-height:24px; border-top:1px solid #B8D0D6; border-bottom:1px solid #B8D0D6; background:url('${resourceRoot}/images/grid.png'); padding-left:8px; width:97%;}
		.panelBar1 a{ float:left; height:23px; color:#333; padding-right:5px; text-decoration:none; line-height:23px; margin}
		.panelBar1 a span{ height:23px; line-height:23px; padding-left:20px; background:url('${resourceRoot}/images/grid.png') no-repeat center; overflow:hidden;}
		.panelBar1 a:hover{ text-decoration:none; color:#C00;}

		.panelBar1 a.a1 span{ background-position:0 -699px !important; background-position:0 -700px;/* IE6 */ }
		*+html .panelBar1 a.a1 span{ background-position:0 -701px !important; font-size:12px;/* IE7 */}
		
		.panelBar1 li{ height:23px; float:left; padding-left:5px; }
		.panelBar1 li.hover{ background:url(../images/grid.png) left -100px;}
		.panelBar1 li.hover a{ background:url(../images/grid.png) right -150px;}
    </style>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	var initSelIds = [];
	var zTree;
	var setting = {
			check: {
				enable: true,
				nocheckInherit: true,
				chkboxType: { "Y": "p", "N": "ps" } 
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
	
	var zNodes =${nodes};
	
	$(document).ready(function(){
		document.execCommand("BackgroundImageCache",false,true);//IE6缓存背景图片
		
		$.fn.zTree.init($("#tree"), setting, zNodes);
		zTree = $.fn.zTree.getZTreeObj("tree");
		$document = $(document);
		$('div.panelBar1', $document).panelBar();
	});
	
	/**初始化表格*/
	$(function(){
		if("${map.expired}"=="F"){
			$("#tips").text("注：设置为过期将会把所有下级部门都设置为过期。");
		}
	});
	function setExpired(){
		$.ajax({
			url:"${ctx}/admin/department/doSave",
			type: 'post',
			data: {code:"${map.code}",m_umState:"EX"},
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
					});
		    	}
		    }
		 });
	}
	function setNotExpired(){
		var nodes = zTree.getCheckedNodes(true);
		var codes="";
		for(var i=0;i<nodes.length;i++){
			codes+=nodes[i].id;
			if(codes!=""){
				codes+=",";
			}
		}
		if(codes==""){
			art.dialog({
				content: "请选择要设置非过期的部门！",
			});
			return;
		}
		$.ajax({
			url:"${ctx}/admin/department/doSave",
			type: 'post',
			data: {code:codes,m_umState:"ENX"},
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
					});
		    	}
		    }
		 }); 
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<c:if test="${map.expired=='F'}">
						<button type="button" class="btn btn-system" id="saveBut"
							onclick="setExpired()">
							<span>设置为过期</span>
						</button>
					</c:if>
					<c:if test="${map.expired!='F'}">
						<button type="button" class="btn btn-system" id="saveBut"
							onclick="setNotExpired()">
							<span>设置为非过期</span>
						</button>
					</c:if>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
					<font color="red"><span id="tips"></span> </font>
				</div>
			</div>
		</div>
		<div style="width:100%;height:500px;flow:left;overflow:auto;">
			<ul id="tree" class="ztree" style="width:350px; overflow:auto;"></ul>
		</div>
	</div>
</body>
</html>
