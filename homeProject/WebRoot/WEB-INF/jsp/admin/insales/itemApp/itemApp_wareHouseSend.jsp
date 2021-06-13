<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemApp列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
	.panelBar{
		height:46px;
	}
	</style>

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
	$.fnShowWindow_min("${ctx}/admin/itemApp/goSave");
}

function copy(id){
	openShadow();
	$.fnShowWindow_min("${ctx}/admin/itemApp/goSave?id="+id);
}

function update(id){
	openShadow();
	$.fnShowWindow_min("${ctx}/admin/itemApp/goUpdate?id="+id);
}

function view(id){
	$.fnShowWindow_min("${ctx}/admin/itemApp/goDetail?id="+id);
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
				url : "${ctx}/admin/itemApp/doDelete",
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
		"action": "${ctx}/admin/itemApp/doExcel"
	});
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemApp/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
		      {name : 'type',index : 'a.Type',width : 100,label:'领料类型',sortable : true,align : "left",hidden:true},
		      {name : 'typedescr',index : 'a.Type',width : 100,label:'领料类型',sortable : true,align : "left"},
		      {name : 'itemtype1',index : 'a.ItemType1',width : 100,label:'材料类型1',sortable : true,align : "left",hidden:true},
		      {name : 'itemtype1descr',index : 'i.Descr',width : 100,label:'材料类型1',sortable : true,align : "left"},
		      {name : 'itemtype2',index : 'a.ItemType2',width : 100,label:'材料类型2',sortable : true,align : "left",hidden:true},
		      {name : 'itemtype2descr',index : 'i2.Descr',width : 100,label:'材料类型2',sortable : true,align : "left"},
		      {name : 'issetitem',index : 'a.IsSetItem',width : 100,label:'是否套餐材料',sortable : true,align : "left",hidden:true},
		      {name : 'issetitemdescr',index : 'a.IsSetItem',width : 100,label:'是否套餐材料',sortable : true,align : "left"},
		      {name : 'documentno',index : 'b.DocumentNo',width : 100,label:'档案编号',sortable : true,align : "left"},
		      {name : 'address',index : 'b.Address',width : 100,label:'楼盘',sortable : true,align : "left"},
			  {name : 'no',index : 'a.No',width : 100,label:'领料单号',sortable : true,align : "left"},
		      {name : 'custcode',index : 'a.CustCode',width : 100,label:'客户编号',sortable : true,align : "left",hidden:true},
		      {name : 'custdescr',index : 'c.Descr',width : 100,label:'客户名称',sortable : true,align : "left"},
		      {name : 'status',index : 'a.Status',width : 100,label:'领料单状态',sortable : true,align : "left",hidden:true},
		      {name : 'statusdescr',index : 'a.Status',width : 100,label:'领料单状态',sortable : true,align : "left"},
		      {name : 'remarks',index : 'a.Remarks',width : 100,label:'备注',sortable : true,align : "left"},
		      {name : 'sendtype',index : 'a.SendType',width : 100,label:'发货类型',sortable : true,align : "left",hidden:true},
		      {name : 'sendtypeDescr',index : 'a.SendType',width : 100,label:'发货类型',sortable : true,align : "left"},
		      {name : 'delivtype',index : 'a.DelivType',width : 100,label:'配送方式',sortable : true,align : "left",hidden:true},
		      {name : 'delivtypeDescr',index : 'a.DelivType',width : 100,label:'配送方式',sortable : true,align : "left"},
		      {name : 'appczy',index : 'a.AppCZY',width : 100,label:'申请人员',sortable : true,align : "left",hidden:true},
		      {name : 'appczydescr',index : 'e1.NameChi',width : 100,label:'申请人员',sortable : true,align : "left"},
		      {name : 'date',index : 'a.Date',width : 100,label:'申请日期',sortable : true,align : "left"},
		      {name : 'confirmczy',index : 'a.ConfirmCZY',width : 100,label:'审批人员',sortable : true,align : "left",hidden:true},
		      {name : 'fonfirmczydescr',index : 'e2.NameChi',width : 100,label:'审批人员',sortable : true,align : "left"},
		      {name : 'confirmdate',index : 'a.ConfirmDate',width : 100,label:'审批日期',sortable : true,align : "left"},
		      {name : 'sendczy',index : 'a.SendCzy',width : 100,label:'发货人员',sortable : true,align : "left",hidden:true},
		      {name : 'sendczydescr',index : 'e3.NameChi',width : 100,label:'发货人员',sortable : true,align : "left"},
		      {name : 'senddate',index : 'a.SendDate',width : 100,label:'发货日期',sortable : true,align : "left"},
		      {name : 'supplcode',index : 'a.SupplCode',width : 100,label:'供应商',sortable : true,align : "left",hidden:true},
		      {name : 'supplcodedescr',index : 'sp.Descr',width : 100,label:'供应商',sortable : true,align : "left"},
		      {name : 'puno',index : 'a.PUNo',width : 100,label:'采购单号',sortable : true,align : "left"},
		      {name : 'whcode',index : 'a.WHCode',width : 100,label:'仓库编号',sortable : true,align : "left",hidden:true},
		      {name : 'whcodedescr',index : 'w.Desc1',width : 100,label:'仓库名称',sortable : true,align : "left"},
		      {name : 'projectman',index : 'a.ProjectMan',width : 100,label:'项目经理',sortable : true,align : "left"},
		      {name : 'phone',index : 'a.Phone',width : 100,label:'电话',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'a.LastUpdate',width : 100,label:'最后更新日期',sortable : true,align : "left",hidden:true},
		      {name : 'lastupdatedby',index : 'a.LastUpdatedBy',width : 100,label:'最后更新人员',sortable : true,align : "left",hidden:true},
		      {name : 'expired',index : 'a.Expired',width : 100,label:'是否过期',sortable : true,align : "left",hidden:true},
		      {name : 'actionlog',index : 'a.ActionLog',width : 100,label:'操作',sortable : true,align : "left",hidden:true}
            ]
		});

});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="expired" name="expired" value="${itemApp.expired}" />
			
				<table cellpadding="0" cellspacing="0" width="100%">
						<col  width="82" />
						<col  width="25.4%"/>
						<col  width="82"/>
						<col  width="25.3%"/>
						<col  width="82" />
						<col  width="25.4%"/>
					<tbody>
						<tr class="td-btn">	
							<td class="td-label">领料单号</td>
							<td class="td-value">
							<input type="text" id="no" name="no" style="width:160px;"  value="${itemApp.no}" />
							</td>
							<td class="td-label">申请日期</td>
							<td class="td-value">
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.date}' pattern='yyyy-MM-dd'/>" />
							</td>							
							<td class="td-label">至</td>
							<td class="td-value">
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.date}' pattern='yyyy-MM-dd'/>" />
							</td>
						</tr>
						<tr class="td-btn">
							<td class="td-label">供应商编号</td>
							<td class="td-value">
							<input type="text" id="supplCode" name="supplCode" style="width:160px;"  value="${itemApp.supplCode}" />
							<a href="javascript:void(0)" class="a1" onclick="fetch('supplCode','supplier','${ctx}')">【选取】</a>
							</td>
							<td class="td-label">领料类型</td>
							<td class="td-value">
							<house:xtdm id="type" dictCode="ITEMAPPTYPE"></house:xtdm>
							</td>
							<td class="td-label">配送方式</td>
							<td class="td-value">
							<house:xtdm id="delivType" dictCode="ITEMAPPSENDTYPE"></house:xtdm>
							</td>														
						</tr>
						<tr class="td-btn">
							<td class="td-label">材料类型1</td>
							<td class="td-value">
							<input type="text" id="itemType1" name="itemType1" style="width:160px;"  value="${itemApp.itemType1}" />
							</td>
							<td class="td-label">材料类型2</td>
							<td class="td-value">
							<input type="text" id="itemType2" name="itemType2" style="width:160px;"  value="${itemApp.itemType2}" />
							</td>	
							<td class="td-label">申请人员</td>
							<td class="td-value">
							<input type="text" id="appCzy" name="appCzy" style="width:160px;"  value="${itemApp.confirmDate}" />
							</td>																			
						</tr>
						<tr class="td-btn">
							<td class="td-label">客户编号</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${itemApp.custCode}" />
							</td>
							<td class="td-label">楼盘地址</td>
							<td class="td-value">
							<input type="text" id="custAddress" name="custAddress" style="width:160px;"  value="${itemApp.custCode}" />
							</td>
							<td class="td-label">领料单状态</td>
							<td class="td-value">
							<house:xtdm id="status" dictCode="ITEMAPPSTATUS"></house:xtdm>
							</td>																				
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input type="checkbox" id="expired_show" name="expired_show" value="${itemApp.expired}" onclick="hideExpired(this)" ${itemApp.expired!='T'?'checked':'' }/>隐藏过期记录&nbsp;
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
            	<house:authorize authCode="ITEMAPP_SAVE">
                	<li>
                    	<a href="javascript:void(0)" class="a1" onclick="add()">
					       <span>新增领料</span>
						</a>	
                    </li>
                </house:authorize>
            	<house:authorize authCode="ITEMAPP_SAVE">
                	<li>
                    	<a href="javascript:void(0)" class="a1" onclick="add()">
					       <span>领料退回</span>
						</a>	
                    </li>
                </house:authorize>
                <house:authorize authCode="ITEMAPP_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a1" onclick="del()">
							<span>领料修改</span>
						</a>
					</li>
                 </house:authorize>
                <house:authorize authCode="ITEMAPP_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a1" onclick="del()">
							<span>审核</span>
						</a>
					</li>
                 </house:authorize>
                <house:authorize authCode="ITEMAPP_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a1" onclick="del()">
							<span>反审核</span>
						</a>
					</li>
                 </house:authorize>
                <house:authorize authCode="ITEMAPP_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a1" onclick="del()">
							<span>供应商直送</span>
						</a>
					</li>
                 </house:authorize>
                <house:authorize authCode="ITEMAPP_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a1" onclick="del()">
							<span>仓库发货</span>
						</a>
					</li>
                 </house:authorize>
                <house:authorize authCode="ITEMAPP_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a1" onclick="del()">
							<span>仓库分批发货</span>
						</a>
					</li>
                 </house:authorize>
                <house:authorize authCode="ITEMAPP_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a1" onclick="del()">
							<span>查看</span>
						</a>
					</li>
                 </house:authorize>
                <house:authorize authCode="ITEMAPP_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a1" onclick="del()">
							<span>明细查询</span>
						</a>
					</li>
                 </house:authorize>
                <house:authorize authCode="ITEMAPP_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a1" onclick="del()">
							<span>发送短信通知</span>
						</a>
					</li>
                 </house:authorize>
                <house:authorize authCode="ITEMAPP_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a1" onclick="del()">
							<span>打印</span>
						</a>
					</li>
                 </house:authorize>
                <house:authorize authCode="ITEMAPP_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a1" onclick="del()">
							<span>批量打印</span>
						</a>
					</li>
                 </house:authorize>
					<li>
						<a href="javascript:void(0)" class="a3" onclick="doExcel()" title="导出检索条件数据">
							<span>输出至excel</span>
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


