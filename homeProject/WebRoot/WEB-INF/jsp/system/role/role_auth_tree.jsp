<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head >
    <title>设置角色权限</title>
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
				nocheckInherit: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
	
	//var zNodes =${nodes};
	
	$(document).ready(function(){
		document.execCommand("BackgroundImageCache",false,true);//IE6缓存背景图片
		var nodeDates = ${nodes};
		if("${operatorType}" != "ADMIN"){
			$.each(nodeDates, function(k, v){
	            if(v.nodeType == "auth" && v.isAdminAssign == "1" )                  	
	               v.chkDisabled = true;
	        });
		}
		var zNodes = nodeDates; 
		
		$.fn.zTree.init($("#tree"), setting, zNodes);
		zTree = $.fn.zTree.getZTreeObj("tree");
		$document = $(document);
		$('div.panelBar1', $document).panelBar();
		var nodes = zTree.getCheckedNodes(true);
		for(var i=0;i<nodes.length;i++){
			if("auth" == nodes[i].nodeType){
				initSelIds.push(nodes[i].id);
			}
		}
		
	});
	
	function save(){
		var nodes = zTree.getCheckedNodes(true);
		var addIds = [];
		var delIds = initSelIds;
		if(nodes != null && nodes.length > 0){
			for(var i=0;i<nodes.length;i++){
				if("auth" == nodes[i].nodeType){
					var flag=false;
					for(var j=0; j<delIds.length; j++){
						if(nodes[i].id == delIds[j]){
							//与初始一样，未发生改变，则不是属于待删除或待增加
							delIds.splice(j,1);
							flag = true;
							break;
						}
					}
					//不在初始选项中，则为后面新增的
					if(!flag){
						addIds.push(nodes[i].id);
					}
				}
			}
		}
		$.ajax({
			url: "${ctx}/admin/role/doSaveRoleAuth",
			data : "id=${roleId}&addIds="+addIds.join(',')+"&delIds="+delIds.join(','),
			type: "POST",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			dataType: 'json',
			cache: false,
			error: function(){
		        art.dialog({
					content: '设置角色权限出现异常'
				});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: '设置角色权限成功！',
						time: 1000,
						beforeunload: function () {
							closeWin();
					    }
					});
		    	}else{
		    		art.dialog({
						content: obj.msg
					});
		    	}
		    }
		});
	}
    </script>
</head>
<body style="overflow:hidden;padding:0;margin:0;">
<div class="body-box-form" >
	<div class="content-form">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="save()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
			</div>
			</div>
		<div style="width:99.5%;height:420px;flow:left;overflow:auto;">
			<ul id="tree" class="ztree" style="width:350px; overflow:auto;"></ul>
		</div>
	</div>
</div>
</body>
</html>


