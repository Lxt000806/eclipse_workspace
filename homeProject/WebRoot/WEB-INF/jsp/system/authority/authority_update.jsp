<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改权限</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	
	if(!checkUrls()){
		return ;
	}

	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/authority/doUpdate',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		$("#infoBoxDiv").css("display","none");
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
}
//校验函数
$(function() {
		  $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
              
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {  
	       menuId_NAME: {  
	        validators: {  
	            notEmpty: {  
	            message: '所属菜单不能为空'  
	            }  
	        }  
	       }
	       },
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        });
});

function fliterMenuType(treeId, treeNode){
	if(treeNode.menuType != '${URL_MENU}')
		return false;
	return true;
}

function changeMenuIcon(){
	var nodes = zNodes_menuId;
	for(var i=0; i<nodes.length; i++){
		if(nodes[i].menuType != '${URL_MENU}'){
			nodes[i].isParent = true;
		}
	}
}

var seq_num = "${size}";

function addTr(){
	seq_num++;
	var str =  "<tr index="+seq_num+">"+
					"<td class=\"td-seq\">"+seq_num+"</td>"+
					"<td><input type=\"text\" name=\"urlStr\" style=\"width:90%;\"/></td>"+
					"<td><input type=\"text\" name=\"urlRemark\" style=\"width:90%;\"/></td>"+
					"<td style=\"text-align: center;\"><a href=\"javascript:void(0)\" onclick=\"delTr('"+seq_num+"')\">删除</a></td>"+
				"</tr>";
	$("#fix_layout_table").append(str);
}

function delTr(index){
	$("#fix_layout_table tr[index='"+index+"']").remove();
}


function checkUrls(){
	var checkArr = [];
	var flag = false;
	$("input[name='urlStr']").each(function(){
		var urlStr = $.trim($(this).val());
		if(urlStr == '' || urlStr == null){
			checkArr.push($(this).parent().parent().attr('index'));
		}else{
			flag = true;
		}
	});
	if(checkArr.length > 0){
		art.dialog({
			content: '行号为 ['+checkArr.join(',')+'] 的链接地址为空，请填写或删除！'
		});
		return false;
	}
	if(!flag){
		art.dialog({
			content: '请至少填写一个链接地址'
		});
		return false;
	}
	return true;
}

</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
      <button type="button" id="saveBtn" class="btn btn-system " id="saveBut"   onclick="save()">保存</button>
      <button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
       <button id="closeBut" type="button" class="btn btn-system "   onclick="addTr()">增加URL</button>
      </div>
   </div>
	</div>
	
		<div class="edit-form">
			<form action="" method="post" id="dataForm" enctype="multipart/form-data" class="form-search">
				<house:token></house:token>
				<input type="hidden" id="authorityId" name="authorityId" value="${authority.authorityId }" />
				<input type="hidden" id="oldAuthCode" name="oldAuthCode" value="${authority.authCode }" />
					<ul class="ul-form">
							<div class="validate-group row">
								<div class="col-sm-6" >
								<li class="form-validate">
								<label><span class="required">*</span>权限名称</label>
								<input type="text"  id="authName" name="authName" data-bv-notempty data-bv-notempty-message="权限名称不能为空" value="${authority.authName }"/>
								</li>
								</div>
								<div class="col-sm-6" >
								<li class="form-validate">
								<label><span class="required">*</span>权限编码</label>
						        <input type="text"  id="authCode" name="authCode" data-bv-notempty data-bv-notempty-message="权限编码不能为空" value="${authority.authCode }"/>
						        </li>
						        </div>
						        </div>
						        
							<div class="validate-group row">
								<div class="col-sm-6" >
									<li class="form-validate">
									<label><span class="required">*</span>所属菜单</label>
						
								<house:zTree nodes="${menus}" id="menuId" nodeId="menuId" nodePId="parentId" nodeName="menuName" value="${authority.menuId }" canSelectParent="false" beforeClick="fliterMenuType" beforeLoadTree="changeMenuIcon" onClick="validateRefresh('menuId_NAME')" ></house:zTree>
								</li>
								</div>
								</div>
								<div class="validate-group row">
								<div class="col-sm-12" >
								<li>
								<label  class="control-textarea">备注</label>
								<textarea style="width: 660px"   id="remark" name="remark">${authority.remark }</textarea>
								</li>
								</div>
							</div>
							</ul>
				<table class="table-layout-fixed" id="fix_layout_table">
					<thead>
						<tr isHead="1">
							<th style="width: 30px;">序号</th>
							<th style="width: 300px;">链接地址</th>
							<th>链接说明</th>
							<th style="width: 80px;">操作</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${authority.resourcesList}" varStatus="item_index" var="resources">
						<tr index="${item_index.index+1}">
							<td class="td-seq">${item_index.index+1}</td>
							<td><input type="text" name="urlStr" style="width:90%;" value="${resources.urlStr }"/></td>
							<td><input type="text" name="urlRemark" style="width:90%;" value="${resources.remark }"/></td>
							<td style="text-align: center;"><a href="javascript:void(0)" onclick="delTr('${item_index.index+1}')">删除</a></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>
