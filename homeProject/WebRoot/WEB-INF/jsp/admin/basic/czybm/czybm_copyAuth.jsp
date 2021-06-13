<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>复制权限页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	var last_contents=""; //最后搜索内容
	var last_flag=0;//最后定位光标
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
		$.fn.zTree.init($("#allList"), setting, zNodes);
		$.fn.zTree.init($("#copyList"), setting, '');
	});
	//移动到复制人员
	function addToCopyList(){
		var allListZTree = $.fn.zTree.getZTreeObj("allList");
	    var selectedNodes = allListZTree.getCheckedNodes(true);
	    var copyListZTree = $.fn.zTree.getZTreeObj("copyList");
	    for (var i=0, l=selectedNodes.length; i < l; i++) 
		{
		    //删除选中的节点
			allListZTree.removeNode(selectedNodes[i]);	
		}
		copyListZTree.addNodes(null, selectedNodes);
	    allListZTree.checkAllNodes(false);
	    copyListZTree.checkAllNodes(false);
	    copyListZTree.expandAll(true);
	}
	//移动到操作员库
	function addToAllList(){
		var copyListZTree = $.fn.zTree.getZTreeObj("copyList");
	    var selectedNodes = copyListZTree.getCheckedNodes(true);
	    var allListZTree = $.fn.zTree.getZTreeObj("allList");
	    for (var i=0, l=selectedNodes.length; i < l; i++) 
		{
		    //删除选中的节点
			copyListZTree.removeNode(selectedNodes[i]);	
		}
	    allListZTree.addNodes(null, selectedNodes);
	    allListZTree.checkAllNodes(false);
	    copyListZTree.checkAllNodes(false);
	    copyListZTree.expandAll(true);
	}
	function  findContents(){
		var contents =$.trim($("#contents").val()); 
		if(contents==" ") return;
		var i_flag=0;
		var zTreeObj = $.fn.zTree.getZTreeObj("allList");
		var node=zTreeObj.getNodesByParamFuzzy('name',contents);
		if (node.length>0) {
			if(contents==last_contents&&(last_flag+1<node.length)){
			    i_flag=last_flag+1; 
			}
			zTreeObj.selectNode(node[i_flag]);
			last_contents=contents;
			last_flag=i_flag;
		}
	}
	$(function() {
		$("#contents").keydown(function(e) {  
           if (e.keyCode == 13) {  
             findContents(); 
             $("#contents").focus();  
           }  
     	 })
	
	});
	function save(){
		var data = {czybh:$("#czybh").val()};
		var treeObj = $.fn.zTree.getZTreeObj("copyList");
		var nodes=treeObj.getNodes();
		var rows=[];
		if(nodes != null && nodes.length > 0){
			for(var i=0;i<nodes.length;i++){
				var json = {};
				json["pk"] = i+1;
				json["czybh"] = nodes[i].id;
				json["isCheck"] = "1";
				json["dispSeq"] = (i+1);
				rows.push(json);
			}
		}else{
			art.dialog({
				content: '待复制权限列表不能为空'
			});
			return;
		}
		$.extend(data, {datas:rows,detailJson:JSON.stringify(rows)});
		art.dialog({
			content:"确定给“待复制权限列表”用户赋予操作员["+$("#czybh").val()+"]的操作权限！<br/><br/>注意：待复制权限列表原操作员操作权限将失效！",
			ok:function(){
				$.ajax({
					url:"${ctx}/admin/czybm/doCopyRight",
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
								width: 200
							});
				    	}		
					}
				});
			},
			cancel:function(){}
		});
	}
	</script>	
	</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	    				<button id="saveBut" type="button" class="btn btn-system "   onclick="save()">保存</button>
	    				<button id="closeBut" type="button" class="btn btn-system"   onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden"name="jsonString" value="" />
					<ul class="ul-form">
						<li><label>被复制操作员</label> <input type="text" id="czybh" name="czybh" value="${czybm.czybh }" />
						<span>${czybm.zwxm}</span>
						</li>
						<li>
						<li></li>
						<li class="search-group">
						<li><label>查找内容</label> <input type="text" id="contents" name="Contents" value=" " />
						</li>
							<button type="button" class="btn  btn-sm btn-system " style="line-height: 1;" onclick="findContents();">查找</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
			<div style="padding-top:10px">
				<div style="float:left;width:45%;height:400px;">
					<div style="height:30px;background:#F5F5F5;border:1px solid; border-color: #C0C0C0;border-bottom:0px;text-align: center;padding:6px;"><span >待复制权限列表</span></div>
					<div style="height:370px;border:1px solid; border-color: #C0C0C0;overflow-x:hidden">
						<ul id="copyList" class="ztree" >
						</ul>
					</div>
				</div>
				<div style="float:left;width:10%;height:400px;">
					<button style="width:90%;margin:5%;margin-top:100px" onclick="addToCopyList()">&#60;&#60;</button>
					<button style="width:90%;margin:5%;" onclick="addToAllList()">&#62;&#62;</button>
				</div>
				<div style="float:right;width:45%;height:400px;">
					<div style="height:30px;background:#F5F5F5;border:1px solid;border-color: #C0C0C0;border-bottom:0px;text-align: center;padding:6px;""><span>操作员库</span></div>
					<div style="height:370px;border:1px solid;border-color: #C0C0C0;overflow-x:hidden;">
						<ul id="allList" class="ztree" >
						</ul>
					</div>
				</div>
			</div> 
		</div>
	</body>
</html>
