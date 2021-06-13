<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>设置操作员权限</title>
<style type="text/css">
.panelBar1 {
	height: 23px;
	line-height: 24px;
	border-top: 1px solid #B8D0D6;
	border-bottom: 1px solid #B8D0D6;
	background: url('${resourceRoot}/images/grid.png');
	padding-left: 8px;
	width: 97%;
}

.panelBar1 a {
	float: left;
	height: 23px;
	color: #333;
	padding-right: 5px;
	text-decoration: none;
	line-height: 23px;
	margin
}

.panelBar1 a span {
	height: 23px;
	line-height: 23px;
	padding-left: 20px;
	background: url('${resourceRoot}/images/grid.png') no-repeat center;
	overflow: hidden;
}

.panelBar1 a:hover {
	text-decoration: none;
	color: #C00;
}

.panelBar1 a.a1 span {
	background-position: 0 -699px !important;
	background-position: 0 -700px; /* IE6 */
}

*+html .panelBar1 a.a1 span {
	background-position: 0 -701px !important;
	font-size: 12px; /* IE7 */
}

.panelBar1 li {
	height: 23px;
	float: left;
	padding-left: 5px;
}

.panelBar1 li.hover {
	background: url(../images/grid.png) left -100px;
}

.panelBar1 li.hover a {
	background: url(../images/grid.png) right -150px;
}
</style>
<%@ include file="/commons/jsp/common.jsp"%>

<script type="text/javascript">
    var initSelIds
	$(document).ready(function(){
		var setting = {
			check: {
				enable: true,
				nocheckInherit: true
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "pId",
				}
			}
		};
		var zNodes =${nodes};
		document.execCommand("BackgroundImageCache",false,true);//IE6缓存背景图片
		$.fn.zTree.init($("#tree"), setting, zNodes);
		zTree = $.fn.zTree.getZTreeObj("tree");
		$document = $(document);
		$('div.panelBar1', $document).panelBar();
		initSelIds = zTree.getCheckedNodes(true);
			
	});
	
	function save(){
		var zTree = $.fn.zTree.getZTreeObj("tree");
		var nodes = zTree.getCheckedNodes(true);
		var rows=[];
		var data = {czybh:$("#czybh").val()};
		var json = {};
		if(nodes != null && nodes.length > 0){
			for(var i=0;i<nodes.length;i++){
				var flag=false;
				for(var j=0; j<initSelIds.length; j++){
					if(nodes[i].id == initSelIds[j].id && nodes[i].pId == initSelIds[j].pId){
						//与初始一样，未发生改变，则不是属于待删除或待增加
						initSelIds.splice(j,1);
						flag = true;
						break;
					}
				}
				//不在初始选项中，则为后面新增的
				if(!flag){
					json = {};
					if("I" == nodes[i].pId){
						json["mkdm"] = nodes[i].id;
						json["gnmc"] = " ";
						json["type"] ="1"; //模块
						json["actionlog"] ="A"; //操作标志 
						rows.push(json);
					}else if(nodes[i].pId){
						json["mkdm"] = nodes[i].pId; 
						json["gnmc"] = nodes[i].id;
						json["type"] ="2"; //功能权限
						json["actionlog"] ="A";
						rows.push(json);
					}
				}
			}
		}
		if(initSelIds != null && initSelIds.length > 0){
			for(var i=0;i<initSelIds.length;i++){
				json = {};
				if("I" == initSelIds[i].pId){
					json["mkdm"] = initSelIds[i].id;
					json["gnmc"] = " ";
					json["type"] ="1"; //模块
					json["actionlog"] ="D"; //操作标志 
					rows.push(json);
				}else if(initSelIds[i].pId){
					json["mkdm"] = initSelIds[i].pId; 
					json["gnmc"] = initSelIds[i].id;
					json["type"] ="2"; //功能权限
					json["actionlog"] ="D";
					rows.push(json);
				}
			}		
		}
		$.extend(data, {detailJson:JSON.stringify(rows)});
		$.ajax({
			url:"${ctx}/admin/czybm/doAppRight",
			data:data,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			dataType: 'json',
			type: 'post',
			cache: false,
			error: function(){
		        art.dialog({
					content: '保存出现异常'
				});
		    },
		    success: function(obj){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
			}
		});
	}
</script>
</head>
<body style="overflow:hidden;padding:0;margin:0;">
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system " onclick="save()">保存</button>
					<button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<form action="" method="post" id="page_form" class="form-search" type="hidden">
			<input type="hidden" id="czybh" name="czybh" value="${czybm.czybh }" />
			<input type="hidden" name="jsonString" value="" />
		</form>
		<div style="width:100%;height:500px;flow:left;overflow:auto;">
			<ul id="tree" class="ztree" style="width:350px; overflow:auto;"></ul>
		</div>

	</div>
</body>
</html>


