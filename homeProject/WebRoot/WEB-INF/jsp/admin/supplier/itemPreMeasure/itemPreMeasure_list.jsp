<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>tItemPreMeasure列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
<script type="text/javascript">
$(function(){
		$("#page_form").setTable();
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/supplierItemPreMeasure/goJqGrid",
			postData:{status:"${itemPreMeasure.status}",address:"${itemPreMeasure.address}"},
			height:$(document).height()-$("#content-list").offset().top-85,
			colModel : [
			  {name: 'pk', index: 'pk', width: 50, label: '编号', sortable: true, align: "left", count: true},
			  {name: 'address', index: 'address', width: 150, label: '楼盘', sortable: true, align: "left"},
			  {name: 'custtypedescr', index: 'custtypedescr', width: 80, label: '客户类型', sortable: true, align: "left"},
		      {name: 'status', index: 'status', width: 60, label: '状态', sortable: true, align: "left", hidden: true},
		      {name: 'statusdescr', index: 'statusdescr', width: 60, label: '状态', sortable: true, align: "left"},
		      {name: 'namechi', index: 'namechi', width: 70, label: '项目经理', sortable: true, align: "left"},
		      {name: 'phone', index: 'phone', width: 90, label: '电话', sortable: true, align: "left"},
		      {name: 'appczydescr', index: 'appczydescr', width: 80, label: '申请人员', sortable: true, align: "left"},
		      {name: 'date', index: 'date', width: 120, label: '申请日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'recvdate', index: 'recvdate', width: 120, label: '接收日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'confirmczydescr', index: 'confirmczydescr', width: 80, label: '测量人员', sortable: true, align: "left"},
		      {name: 'confirmdate', index: 'confirmdate', width: 120, label: '测量日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'remarks', index: 'remarks', width: 180, label: '备注', sortable: true, align: "left"},
		      {name: 'preappno', index: 'preappno', width: 100, label: '预申请单号', sortable: true, align: "left"},
		      {name: 'measureremark', index: 'measureremark', width: 180, label:'测量数据', sortable: true, align: "left"},
		      {name: 'lastupdate', index: 'lastupdate', width: 120, label: '最后更新日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'lastupdatedby', index: 'lastupdatedby', width: 100, label: '最后更新人员', sortable: true, align : "left"},
		      {name: 'expired', index: 'expired', width: 100, label: '是否过期', sortable: true, align: "left"},
		      {name: 'actionlog', index: 'actionlog', width: 100, label: '操作', sortable: true, align: "left"}
            ]
		});
		
        //接收
        $("#receiveItemPreMeasure").on("click", function() {
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行接收操作！",width: 200});
    			return false;
    		}else{
    			if ($.trim(row.status)!='1' && $.trim(row.status)!='2'){
    				art.dialog({content: "只有【申请】或【接收退回】的测量单才能进行接收操作!",width: 200});
        			return false;
    			}
    			
    		}
    		Global.Dialog.showDialog("receiveItemPreMeasure", {
        	  title: "接收工地测量",
        	  url: "${ctx}/admin/supplierItemPreMeasure/goReceive?pk=" + row.pk+"&preAppNo="+row.preappno,
        	  height: 720,
        	  width: 1050,
        	  returnFun: goto_query
        	});
        });   
        //修改
        $("#updateItemPreMeasure").on("click",function(){
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行修改操作！",width: 200});
    			return false;
    		}else{
    			if ($.trim(row.status)!='3' && $.trim(row.status)!='4'){
    				art.dialog({content: "只有【接收】或【完成】的测量单才能进行修改操作!",width: 200});
        			return false;
    			}
    		}
        	Global.Dialog.showDialog("updateItemPreMeasure",{
        	  title: "修改工地测量",
        	  url: "${ctx}/admin/supplierItemPreMeasure/goUpdate?pk=" + row.pk+"&preAppNo="+row.preappno,
        	  height: 750,
        	  width: 1300,
        	  returnFun: goto_query
        	});
        });
        //查看
        $("#viewItemPreMeasure").on("click",function(){
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行查看操作！",width: 200});
    			return false;
    		}
        	Global.Dialog.showDialog("viewItemPreMeasure",{
        	  title: "查看工地测量",
        	  url: "${ctx}/admin/supplierItemPreMeasure/goView?pk=" + row.pk+"&preAppNo="+row.preappno,
        	  height: 750,
        	  width: 1300,
        	  returnFun: goto_query
        	});
        });   
        //完成
        $("#finishItemPreMeasure").on("click",function(){
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行完成操作！",width: 200});
    			return false;
    		}else{
    			if ($.trim(row.status)!='3'){
    				art.dialog({content: "只有【接收】的测量单才能进行完成操作!",width: 200});
        			return false;
    			}
    			
    		}
        	Global.Dialog.showDialog("finishItemPreMeasure",{
        	  title: "完成工地测量",
        	  url: "${ctx}/admin/supplierItemPreMeasure/goFinish?pk=" + row.pk+"&preAppNo="+row.preappno,
        	  height: 700,
        	  width: 1000,
        	  returnFun: goto_query
        	});
        });
        //退回
        $("#backItemPreMeasure").on("click", function() {
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行退回操作！",width: 200});
    			return false;
    		}else{
    			if ($.trim(row.status)!='3'){
    				art.dialog({content: "只有【接收】的测量单才能进行退回操作!",width: 200});
        			return false;
    			}
    		}
    		//confirmTodo("${ctx}/admin/supplierItemPreMeasure/doBack","是否确认退回？",{pk: row.pk});
    		Global.Dialog.showDialog("backItemPreMeasure",{
          	  title: "退回工地测量",
          	  url: "${ctx}/admin/supplierItemPreMeasure/goBack?pk=" + row.pk,
          	  height: 300,
          	  width: 800,
          	  returnFun: goto_query
          	});
        });
        
});

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
}
</script>
<style type="text/css">
.panelBar {background: url('')}
</style>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<table cellpadding="0" cellspacing="0" width="100%">
						<col  width="82" />
						<col  width="25.4%"/>
						<col  width="82"/>
						<col  width="25.3%"/>
						<col  width="82" />
						<col  width="25.4%"/>
					<tbody>
					<tr>	
						<td class="td-label">状态</td>
						<td class="td-value" colspan="1">
						<house:xtdmMulit id="status" dictCode="MEASURESTATUS" selectedValue="${itemPreMeasure.status }"></house:xtdmMulit>
						</td>
						<td class="td-label">楼盘</td>
						<td class="td-value" colspan="1">
						<input type="text" id="address" name="address" style="width:160px;"  value="${itemPreMeasure.address}" />
						</td>
					</tr>
					<tr>
						<td colspan="6" class="td-btn">
							<div class="sch_qk_con"> 
								<input onclick="goto_query();"  class="i-btn-s" type="button"  value="查询"/>
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
            	<house:authorize authCode="ITEMPREMEASURE_RECEIVE">
                	<li>
                    	<a href="javascript:void(0)" class="a1" id="receiveItemPreMeasure">
					       <span>接收</span>
						</a>	
                    </li>
                </house:authorize>
                <house:authorize authCode="ITEMPREMEASURE_UPDATE">
                	<li>
                    	<a href="javascript:void(0)" class="a1" id="updateItemPreMeasure">
					       <span>修改</span>
						</a>	
                    </li>
                </house:authorize>
            	<house:authorize authCode="ITEMPREMEASURE_VIEW">
                	<li>
                    	<a href="javascript:void(0)" class="a1" id="viewItemPreMeasure">
					       <span>查看</span>
						</a>	
                    </li>
                </house:authorize>
                <house:authorize authCode="	ITEMPREMEASURE_FINISH">
                	<li>
						<a href="javascript:void(0)" class="a1" id="finishItemPreMeasure">
							<span>完成</span>
						</a>
					</li>
                 </house:authorize>
                <house:authorize authCode="ITEMPREMEASURE_BACK">
                	<li>
						<a href="javascript:void(0)" class="a1" id="backItemPreMeasure">
							<span>退回</span>
						</a>
					</li>
                 </house:authorize>
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


