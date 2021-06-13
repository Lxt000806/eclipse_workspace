<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemAppDetail列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
}

function add(){
	openShadow();
	$.fnShowWindow_min("${ctx}/admin/itemAppDetail/goSave");
}

function copy(id){
	openShadow();
	$.fnShowWindow_min("${ctx}/admin/itemAppDetail/goSave?id="+id);
}

function update(id){
	openShadow();
	$.fnShowWindow_min("${ctx}/admin/itemAppDetail/goUpdate?id="+id);
}

function view(id){
	$.fnShowWindow_min("${ctx}/admin/itemAppDetail/goDetail?id="+id);
}

function del(){
	var ids = [];
	$("div.content-list").find("table>tbody").find("input[type='checkbox']").each(function(){
		if(this.checked){
			ids.push(this.value);
		}
	})
	if(ids.length <1){
		 art.dialog({
			content: '请选择需要删除的记录',
			lock: true
		 });
		return ;
	}
	art.dialog({
		 content:'您确定要删除记录吗？',
		 lock: true,
		 width: 260,
		 height: 100,
		 ok: function () {
	        $.ajax({
				url : "${ctx}/admin/itemAppDetail/doDelete",
				data : "deleteIds="+ids.join(','),
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: 'json',
				type: 'post',
				cache: false,
				error: function(){
			        art.dialog({
						content: '删除记录出现异常'
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
}

function doExcel(){
	$.form_submit($("#page_form").get(0), {
		"action": "${ctx}/admin/itemAppDetail/doExcel"
	});
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemAppDetail/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'pk',index : 'pk',width : 100,label:'PK',sortable : true,align : "left",hidden:true},
			  {name : 'pkLink',index : 'pk',width : 100,label:'PK',sortable : true,align : "left",          	  	
					formatter:function(cellvalue, options, rowObject){
	        			if(rowObject==null || rowObject.pk == null){
	          				return '';
	          			}
	          			return "<a href=\"javascript:void(0)\" onclick=\"showLink('"+rowObject.pk+"')\">"+rowObject.pk+"</a>";
        	  		} 
			  },
		      {name : 'no',index : 'no',width : 100,label:'批次号',sortable : true,align : "left"},
		      {name : 'reqPk',index : 'reqPk',width : 100,label:'领料需求标识',sortable : true,align : "left"},
		      {name : 'fixAreaPk',index : 'fixAreaPk',width : 100,label:'区域编码',sortable : true,align : "left"},
		      {name : 'intProdPk',index : 'intProdPk',width : 100,label:'集成成品PK',sortable : true,align : "left"},
		      {name : 'itemCode',index : 'itemCode',width : 100,label:'材料编号',sortable : true,align : "left"},
		      {name : 'qty',index : 'qty',width : 100,label:'数量',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 100,label:'Remarks',sortable : true,align : "left"},
		      {name : 'lastUpdate',index : 'lastUpdate',width : 100,label:'LastUpdate',sortable : true,align : "left"},
		      {name : 'lastUpdatedBy',index : 'lastUpdatedBy',width : 100,label:'LastUpdatedBy',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'Expired',sortable : true,align : "left"},
		      {name : 'actionLog',index : 'actionLog',width : 100,label:'ActionLog',sortable : true,align : "left"},
		      {name : 'cost',index : 'cost',width : 100,label:'移动平均成本',sortable : true,align : "left"},
		      {name : 'aftQty',index : 'aftQty',width : 100,label:'变动后数量',sortable : true,align : "left"},
		      {name : 'aftCost',index : 'aftCost',width : 100,label:'变动后平均成本',sortable : true,align : "left"},
		      {name : 'sendQty',index : 'sendQty',width : 100,label:'发货数量',sortable : true,align : "left"},
		      {name : 'projectCost',index : 'projectCost',width : 100,label:'项目经理结算价',sortable : true,align : "left"}
            ]
		});

});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="expired" name="expired" value="${itemAppDetail.expired}" />
			
				<table cellpadding="0" cellspacing="0" width="100%">
						<col  width="82" />
						<col  width="25.4%"/>
						<col  width="82"/>
						<col  width="25.3%"/>
						<col  width="82" />
						<col  width="25.4%"/>
					<tbody>
						<tr class="td-btn">	
							<td class="td-label">PK</td>
							<td class="td-value">
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${itemAppDetail.pk}" />
							</td>
							<td class="td-label">批次号</td>
							<td class="td-value">
							<input type="text" id="no" name="no" style="width:160px;"  value="${itemAppDetail.no}" />
							</td>
							<td class="td-label">领料需求标识</td>
							<td class="td-value">
							<input type="text" id="reqPk" name="reqPk" style="width:160px;"  value="${itemAppDetail.reqPk}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">区域编码</td>
							<td class="td-value">
							<input type="text" id="fixAreaPk" name="fixAreaPk" style="width:160px;"  value="${itemAppDetail.fixAreaPk}" />
							</td>
							<td class="td-label">集成成品PK</td>
							<td class="td-value">
							<input type="text" id="intProdPk" name="intProdPk" style="width:160px;"  value="${itemAppDetail.intProdPk}" />
							</td>
							<td class="td-label">材料编号</td>
							<td class="td-value">
							<input type="text" id="itemCode" name="itemCode" style="width:160px;"  value="${itemAppDetail.itemCode}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">数量</td>
							<td class="td-value">
							<input type="text" id="qty" name="qty" style="width:160px;"  value="${itemAppDetail.qty}" />
							</td>
							<td class="td-label">Remarks</td>
							<td class="td-value">
							<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${itemAppDetail.remarks}" />
							</td>
							<td class="td-label">LastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemAppDetail.lastUpdate}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">LastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemAppDetail.lastUpdatedBy}" />
							</td>
							<td class="td-label">Expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemAppDetail.expired}" />
							</td>
							<td class="td-label">ActionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemAppDetail.actionLog}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">移动平均成本</td>
							<td class="td-value">
							<input type="text" id="cost" name="cost" style="width:160px;"  value="${itemAppDetail.cost}" />
							</td>
							<td class="td-label">变动后数量</td>
							<td class="td-value">
							<input type="text" id="aftQty" name="aftQty" style="width:160px;"  value="${itemAppDetail.aftQty}" />
							</td>
							<td class="td-label">变动后平均成本</td>
							<td class="td-value">
							<input type="text" id="aftCost" name="aftCost" style="width:160px;"  value="${itemAppDetail.aftCost}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">发货数量</td>
							<td class="td-value">
							<input type="text" id="sendQty" name="sendQty" style="width:160px;"  value="${itemAppDetail.sendQty}" />
							</td>
							<td class="td-label">项目经理结算价</td>
							<td class="td-value" colspan="3">
							<input type="text" id="projectCost" name="projectCost" style="width:160px;"  value="${itemAppDetail.projectCost}" />
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input type="checkbox" id="expired_show" name="expired_show" value="${itemAppDetail.expired}" onclick="hideExpired(this)" ${itemAppDetail.expired!='T'?'checked':'' }/>隐藏过期记录&nbsp;
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
			<!--panelBar-->
			<div class="panelBar">
            	<ul>
            	<house:authorize authCode="ITEMAPPDETAIL_SAVE">
                	<li>
                    	<a href="javascript:void(0)" class="a1" onclick="add()">
					       <span>添加</span>
						</a>	
                    </li>
                </house:authorize>
                <house:authorize authCode="ITEMAPPDETAIL_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a2" onclick="del()">
							<span>删除</span>
						</a>
					</li>
                 </house:authorize>
					<li>
						<a href="javascript:void(0)" class="a3" onclick="doExcel()" title="导出检索条件数据">
							<span>导出excel</span>
						</a>
					</li>
                    <li class="line"></li>
                </ul>
				<div class="clear_float"> </div>
			</div><!--panelBar end-->
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
</body>
</html>


