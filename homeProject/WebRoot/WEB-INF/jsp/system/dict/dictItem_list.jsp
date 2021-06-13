<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>字典元素列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("dictItemAdd",{
		  title:"添加字典元素",
		  url:"${ctx}/admin/dictItem/goSave?dictId=${dictItem.dictId }&dictType=${dictType}",
		  height: 300,
		  width:700,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("dictItemUpdate",{
		  title:"修改字典元素",
		  url:"${ctx}/admin/dictItem/goUpdate?dictId=${dictItem.dictId }&dictType=${dictType}&itemId="+ret.itemId,
		  height:300,
		  width:700,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/dictItem/doDelete";
	beforeDel(url);
}

/**初始化表格*/
$(function(){
		$("#page_form").setTable();
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/dictItem/goJqGrid?dictId=${dictItem.dictId }",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'itemId',index : 'itemId',width : 100,label:'元素编码',sortable : true,align : "left",hidden:true},
		      {name : 'itemLabel',index : 'itemLabel',width : 200,label:'元素文本',sortable : true,align : "left"},
		      {name : 'itemCode',index : 'itemCode',width : 300,label:'助记码',sortable : true,align : "left"},
		      {name : 'itemValue',index : 'itemValue',width : 100,label:'元素值',sortable : true,align : "left"},
		      {name : 'remark',index : 'remark',width : 200,label:'备注',sortable : true,align : "left"},
		      {name : 'status',index : 'status',width : 100,label:'状态',sortable : true,align : "left",formatter:formatStatus}
            ]
		});
});

function setStatus(type){
	var ret = selectDataTableRow();
    if (ret) {
    	var url = "${ctx}/admin/dictItem/doEnable";
    	var opt = "启用";
    	if(type == '1'){
    		url = "${ctx}/admin/dictItem/doDisable"
    		opt = "禁用"
    	}
    	art.dialog({
    		 content:'您确定要[ '+opt+' ] 字典元素 [ '+ret.itemLabel+' ]吗？',
    		 width: 260,
    		 lock: true,
    		 height: 100,
    		 ok: function () {
    	        $.ajax({
    				url : url,
    				data : "dictItemId="+ret.itemId,
    				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
    				dataType: 'json',
    				type: 'post',
    				cache: false,
    				error: function(){
    			        art.dialog({
    						content: opt+' 字典元素 '+ret.itemLabel+' 出现异常'
    					});
    			    },
    			    success: function(obj){
    			    	if(obj.rs){
    			    		art.dialog({
    							content: obj.msg,
    							time: 1000,
    							beforeunload: function () {
    								parent.treeFrame.location.href = "${ctx}/admin/dict/goTree?defaultId="+ret.dictId+"&dictType=${dictType}";
    						        goto_query();
    						    }
    						});
    			    	}else{
    			    		art.dialog({
    							content: obj.msg
    						});
    			    	}
    				}
    			});
    	    	return true;
    		},
    		cancel: function () {
    			return true;
    		}
    	});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function formatStatus(value){
	if (value=='1'){
		return '启用'
	}else{
		return '禁用'
	}
}
</script>
<style type="text/css">
.panelBar{background: url('')}
</style>
</head>
    
<body>
	<div class="body-box-list" >
		<div class="query-form">
			<form method="post" id="page_form" >
				<table cellpadding="0" cellspacing="0" width="100%">
						<col  width="72" />
						<col  width="250"/>
						<col  width="72"/>
						<col  width=""/>
					<tbody>
						<tr class="td-btn">
							<td class="td-label">元素文本</td>
							<td class="td-value"><input type="text" id="itemLabel" name="itemLabel" style="width:160px;" value="${dictItem.itemLabel }" /></td>
							<td class="td-label">元素编码</td>
							<td class="td-value" colspan="3"><input type="text" id="itemCode" name="itemCode" style="width:160px;"  value="${dictItem.itemCode }" /></td>
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
		</div><!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
			<div class="panelBar">
            	<ul>
            <c:if test="${flag}">
            	<house:authorize authCode="DICT_ITEM_SAVE">
                	<li>
                    	<a href="javascript:void(0)" class="a1" onclick="add()">
					       <span>添加</span>
						</a>	
                    </li>
                </house:authorize>
                <c:if test="${flag}">
				<house:authorize authCode="DICT_ITEM_UPDATE">
					<li>
                    	<a href="javascript:void(0)" class="a1" onclick="update()">
					       <span>编辑</span>
						</a>	
                    </li>
				</house:authorize>
				<house:authorize authCode="DICT_ITEM_ENABLE">
					<li>
                    	<a href="javascript:void(0)" class="a3" onclick="setStatus('0')">
					       <span>启用</span>
						</a>
                    </li>
				</house:authorize>
				<house:authorize authCode="DICT_ITEM_DISABLE">
					<li>
                    	<a href="javascript:void(0)" class="a4" onclick="setStatus('1')">
					       <span>禁用</span>
						</a>	
                    </li>
				</house:authorize>
			</c:if>
            </c:if>
                    <li class="line"></li>
                </ul>
				<div class="clear_float"> </div>
			</div><!--panelBar end-->

			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
