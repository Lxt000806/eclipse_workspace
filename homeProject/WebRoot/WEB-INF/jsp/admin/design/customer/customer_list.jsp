<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$("#custType").val('');
	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	
	$("#department1").val('');
    $("#department1"+"_NAME").val('');
    $.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
    $("#department2").val('');
    $("#department2"+"_NAME").val('');
    $.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
    $.fn.zTree.init($("#tree_department2"), {}, []);
    $("#department3").val('');
    $("#department3"+"_NAME").val('');
    $.fn.zTree.getZTreeObj("tree_department3").checkAllNodes(false);
    $.fn.zTree.init($("#tree_department3"), {}, []);
}

function add(){
	Global.Dialog.showDialog("customerAdd",{
		  title:"添加客户",
		  url:"${ctx}/admin/customer/goSave",
		  height: 600,
		  width:1200,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("customerUpdate",{
		  title:"修改客户",
		  url:"${ctx}/admin/customer/goUpdate?id="+ret.code,
		  height:600,
		  width:1200,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function copy(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("customerCopy",{
		  title:"复制客户",
		  url:"${ctx}/admin/customer/goSave?id="+ret.code,
		  height:600,
		  width:1200,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function doc(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("customerDoc",{
		  title:"修改档案号",
		  url:"${ctx}/admin/customer/goUpdate?flag=doc&id="+ret.code,
		  height:300,
		  width:500,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function busi(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("customerBusi",{
		  title:"修改业务员",
		  url:"${ctx}/admin/customer/goBusinessMan?id="+ret.code,
		  height:350,
		  width:500,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function updateDesign(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("customerDesign",{
		  title:"修改设计师",
		  url:"${ctx}/admin/customer/goUpdateDesign?id="+ret.code,
		  height:300,
		  width:500,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("customerView",{
		  title:"查看客户",
		  url:"${ctx}/admin/customer/goDetail?id="+ret.code,
		  height:600,
		  width:1200
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function measureHouse() {
    var ret = selectDataTableRow()
    
    if (!ret) {
        art.dialog({content: "请选择一条记录"})
        
        return
    }
    
    if (ret.measuredate !== '') {
        art.dialog({content: "该楼盘已量房"})
        
        return
    }
    
    if (ret.status === '4' || ret.status === '5') {
        art.dialog({content: "合同施工或已归档状态的楼盘不允许量房"})
        
        return
    }
    var today=formatDate(new Date());
    var minDate=getAddDays(new Date(),-3);
    var content="确认此楼盘已量房吗？</br><input type='text' style='width:120px;border-radius:5px;margin-top:20px' id='measureDate' "
    			+"class='i-date' onfocus='WdatePicker({skin:\"whyGreen\",dateFmt:\"yyyy-MM-dd\",maxDate:\""+today+"\",minDate:\""+minDate+"\"})' value='"+today+"' /> ";
    art.dialog({
        content:content,
        lock: true,
        width: 200,
        height: 100,
        ok: function() {
            $.ajax({
                url: "${ctx}/admin/customer/doMeasure",
                data: {Code: ret.code,measureDate:$("#measureDate").val()==""?today:$("#measureDate").val()},
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                dataType: "json",
                type: "post",
                cache: false,
                error: function() {
                    art.dialog({content:"确认量房异常"});
                },
                success: function(obj) {
                    if (obj.rs) {
                        art.dialog({
                            content: obj.msg,
                            time: 1000,
                            beforeunload: function () {
                                goto_query();
                            }
                        });
                    } else {
                        art.dialog({content:obj.msg});
                    }
                }
            });
            return true;
        },
        cancel: function() {
            return true;
        }
    });
    
}

function visit(){
	var ret = selectDataTableRow();
	if(ret){
		if(ret.status=='1'){
			var content="确认客户到店吗？</br><input type='text' style='width:120px;border-radius:5px;margin-top:20px' id='visitDate' "
			+"class='i-date' onfocus='WdatePicker({skin:\"whyGreen\",dateFmt:\"yyyy-MM-dd\",maxDate:\"${today}\",minDate:\"${minDate}\"})' value='${today}' /> ";
				art.dialog({
					content:content,
					lock: true,
					width: 200,
					height: 100,
					ok: function () {
						$.ajax({
							url:"${ctx}/admin/customer/doVisit",
							data:{Code:ret.code,visitDate:$("#visitDate").val()==""?"${today}":$("#visitDate").val()},
							contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							dataType:"json",
							type:"post",
							cache:false,
							error:function(){
								art.dialog({
									content:"确认到店异常"
								});
							},
							success:function(obj){
								if(obj.rs){
									art.dialog({
										content:obj.msg,
										time:1000,
										beforeunload: function () {
											goto_query();
										}
									});
								}else{
									art.dialog({
										content:obj.msg
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
			}else{
				art.dialog({content: "只有状态为未到公司的才可以设置到店 ",width: 200});
				return false;
			}
	}else{
		art.dialog({
			content: "请选择一条记录"
		});
	}
}

function modifyPhone(){
	var ret = selectDataTableRow();
	if (ret) {
      Global.Dialog.showDialog("modifyPhone",{
		  title:"修改电话",
		  url:"${ctx}/admin/customer/goModifyPhone?code="+ret.code,
		  height:400,
		  width:400,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function modifyStat(){
	var ret = selectDataTableRow();
	if (ret) {
      Global.Dialog.showDialog("modifyPhone",{
		  title:"修改状态",
		  url:"${ctx}/admin/customer/goModifyStat?code="+ret.code,
		  height:400,
		  width:400,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function setAgainType(){
	var ret = selectDataTableRow();
	if (ret) {
      Global.Dialog.showDialog("setAgainType",{
		  title:"设置跟单",
		  url:"${ctx}/admin/customer/goSetAgainType?code="+ret.code,
		  height:340,
		  width:425,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function modifyPhoneQuery(){
	Global.Dialog.showDialog("modifyPhoneQuery",{
		  title:"电话修改查询",
		  url:"${ctx}/admin/customer/goModifyPhoneQuery",
		  height:800,
		  width:1200
		});
}

function del(){
	var url = "${ctx}/admin/customer/doDelete";
	beforeDel(url,"code");
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/customer/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/customer/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-80,
			colModel : [
			  {name : 'cmpname',index : 'cmpname',width : 120,label:'公司',sortable : true,align : "left",frozen : true},
		      {name : 'documentno',index : 'documentno',width : 70,label:'档案号',sortable : true,align : "left",frozen: true},
		      {name : 'code',index : 'code',width : 70,label:'客户编号',sortable : true,align : "left",frozen: true},
		      {name : 'descr',index : 'descr',width : 70,label:'客户名称',sortable : true,align : "left",frozen: true},
		      {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left",frozen: true},
		      {name : 'genderdescr',index : 'genderdescr',width : 50,label:'性别',sortable : true,align : "left"},
		      {name : 'custtypedescr',index : 'custtypedescr',width : 70,label:'客户类型',sortable : true,align : "left"},
		      {name : 'saletypedescr',index : 'saletypedescr',width : 70,label:'销售类型',sortable : true,align : "left"},
		      {name : 'oldcode',index : 'oldcode',width : 70,label:'原客户号',sortable : true,align : "left"},
		      {name : 'sourcedescr',index : 'sourcedescr',width : 70,label:'客户来源',sortable : true,align : "left"},
		      {name : 'netchaneldescr',index : 'netchaneldescr',width : 70,label:'渠道',sortable : true,align : "left"},
		      {name : 'layoutdescr',index : 'layoutdescr',width : 50,label:'户型',sortable : true,align : "left"},
		      {name : 'area',index : 'area',width : 45,label:'面积',sortable : true,align : "left"},
		      {name : 'designstyledescr',index : 'designstyledescr',width : 70,label:'设计风格',sortable : true,align : "left"},
		      {name : 'statusdescr',index : 'statusdescr',width : 70,label:'客户状态',sortable : true,align : "left"},
		      {name : 'qq',index : 'qq',width : 60,label:'QQ',sortable : true,align : "left"},
		      {name : 'email2',index : 'email2',width : 60,label:'Email2',sortable : true,align : "left"},
		      {name : 'crtdate',index : 'crtdate',width : 150,label:'创建时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'measuredate',index : 'measuredate',width : 150,label:'量房时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'visitdate',index : 'visitdate',width : 150,label:'到店时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'designmandescr',index : 'designmandescr',width : 60,label:'设计师',sortable : true,align : "left"},
		      {name : 'businessmandescr',index : 'businessmandescr',width : 60,label:'业务员',sortable : true,align : "left"},
		      {name : 'againmandescr',index : 'againmandescr',width : 60,label:'翻单员',sortable : true,align : "left"},
		      {name : 'begindate',index : 'begindate',width : 150,label:'开工时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'constructtypedescr',index : 'constructtypedescr',width : 70,label:'施工方式',sortable : true,align : "left"},
		      {name : 'planamount',index : 'planamount',width : 70,label:'意向金额',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 150,label:'备注',sortable : true,align : "left",formatter:cutStr},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'status',index : 'status',width:70,label:'客户状态',sortable : true,align :" left",hidden : true},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 60,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 70,label:'操作日志',sortable : true,align : "left"}
            ],
            loadComplete: function(){
            	frozenHeightReset("dataTable");
            }
		});
		$("#dataTable").jqGrid('setFrozenColumns');
		$("#designMan").openComponent_employee();
		$("#businessMan").openComponent_employee();
		$("#builderCode").openComponent_builder();
		$("#groupCode").openComponent_builderGroup();
		onCollapse(0);
});
</script>
<style type="text/css">

</style>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" id="expired" name="expired" value="${customer.expired }" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
						<li>
							<label>客户名称</label>
							<input type="text" id="descr" name="descr" style="width:160px;" value="${customer.descr }" />
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" />
						</li>
						<li>
							<label>客户编号</label>
							<input type="text" id="code" name="code" style="width:160px;" value="${customer.code }" />
						</li>
						<li>
							<label>设计师编号</label>
							<input type="text" id="designMan" name="designMan" style="width:160px;" value="${customer.designMan}" />
						</li>
						<li>
							<label>业务员编号</label>
							<input type="text" id="businessMan" name="businessMan" style="width:160px;" value="${customer.businessMan}" />
						</li>
						<li>
							<label>客户电话</label>
							<input type="text" id="mobile1" name="mobile1" style="width:160px;" value="${customer.mobile1 }" />
						</li>
						<li>
							<label>客户来源</label>
							<house:xtdm id="source" dictCode="CUSTOMERSOURCE" value="${customer.source }"></house:xtdm>
						</li>
						<li>
							<label>客户状态</label>
							<house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" selectedValue="${customer.status }"></house:xtdmMulit>
						</li>
						<li>
							<label>项目名称</label>
							<input type="text" id="builderCode" name="builderCode" style="width:160px;" value="${customer.builderCode}"/>
						</li>
						<li>
							<label>项目大类</label>
							<input type="text" id="groupCode" name="groupCode" style="width:160px;" value="${customer.groupCode}"/>
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType"  selectedValue="${customer.custType }"></house:custTypeMulit>
						</li>
						<li id="loadMore"  class="search-group-shrink" >
							<button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">更多</button>
							<input type="checkbox" id="expired_show" name="expired_show" value="${customer.expired }" onclick="hideExpired(this)" ${customer.expired!='T'?'checked':'' }/><p>隐藏过期</p>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
						<div class="collapse" id="collapse">
							<ul>
								<li>
									<label>创建时间从</label>
									<input type="text" style="width:160px;" id="crtDateFrom" name="crtDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.crtDateFrom }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>到</label>
									<input type="text" style="width:160px;" id="crtDateTo" name="crtDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.crtDateTo }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>到店时间从</label>
									<input type="text" style="width:160px;" id="visitDateFrom" name="visitDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.visitDateFrom }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>到</label>
									<input type="text" style="width:160px;" id="visitDateTo" name="visitDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.visitDateTo }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
		                            <label>一级部门</label>
		                            <house:DictMulitSelect id="department1" dictCode="" sql="select code,desc1 from tDepartment1 where Expired='F'" 
		                                sqlLableKey="desc1" sqlValueKey="code" onCheck="checkDepartment1()"></house:DictMulitSelect>
		                        </li>
		                        <li>
		                            <label>二级部门</label>
		                            <house:DictMulitSelect id="department2" dictCode="" sql="select code,desc1 from tDepartment2 where 1=2" 
		                                sqlLableKey="desc1" sqlValueKey="code" onCheck="checkDepartment2()"></house:DictMulitSelect>
		                        </li>
		                        <li>
		                            <label>三级部门</label>
		                            <house:DictMulitSelect id="department3" dictCode="" sql="select code,desc1 from tDepartment3 where 1=2"
		                                sqlLableKey="desc1" sqlValueKey="code"></house:DictMulitSelect>
		                        </li>
								<li class="search-group-shrink">
									<button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">收起</button>
									<input type="checkbox" id="expired_show" name="expired_show" value="${customer.expired }" onclick="hideExpired(this)" ${customer.expired!='T'?'checked':'' }/><p>隐藏过期</p>
									<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
									<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
								</li>
							</ul>
						</div>
					</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="btn-panel">
			 <div class="btn-group-sm">
            	<house:authorize authCode="CUSTOMER_ADD">
            	<button type="button" class="btn btn-system" onclick="add()">添加</button>
                </house:authorize>
                
                <house:authorize authCode="CUSTOMER_COPY">
                <button type="button" class="btn btn-system" onclick="copy()">复制</button>
				</house:authorize>
				
				<house:authorize authCode="CUSTOMER_UPDATE">
				<button type="button" class="btn btn-system" onclick="update()">编辑</button>
				</house:authorize>
				
				<house:authorize authCode="CUSTOMER_UPDATEDOC">
				<button type="button" class="btn btn-system" onclick="doc()">修改档案号</button>
				</house:authorize>
				
				<house:authorize authCode="CUSTOMER_UPDATEBUSI">
				<button type="button" class="btn btn-system" onclick="busi()">修改业务员</button>
				</house:authorize>
				
				<house:authorize authCode="CUSTOMER_UPDATEDESIGN">
				<button type="button" class="btn btn-system" onclick="updateDesign()">修改设计师</button>
				</house:authorize>
				
				<house:authorize authCode="CUSTOMER_MEASURE">
                <button type="button" class="btn btn-system" onclick="measureHouse()">量房</button>
                </house:authorize>
				
				<house:authorize authCode="CUSTOMER_VISITCMP">
				<button type="button" class="btn btn-system" onclick="visit()">到店</button>
				</house:authorize>
				
				<house:authorize authCode="CUSTOMER_MODIFYPHONE">
				<button type="button" class="btn btn-system" onclick="modifyPhone()">修改电话</button>
				</house:authorize>
				
				<house:authorize authCode="CUSTOMER_MODIFYSTAT">
				<button type="button" class="btn btn-system" onclick="modifyStat()">修改状态</button>
				</house:authorize>
				
				<house:authorize authCode="CUSTOMER_SETAGAINTYPE">
				<button type="button" class="btn btn-system" onclick="setAgainType()">设置跟单</button>
				</house:authorize>
				
				<house:authorize authCode="CUSTOMER_MODIFYPHONEQUERY">
				<button type="button" class="btn btn-system" onclick="modifyPhoneQuery()">电话修改查询</button>
				</house:authorize>
				
                <house:authorize authCode="CUSTOMER_DELETE">
                <button type="button" class="btn btn-system" onclick="del()">删除</button>
                 </house:authorize>
                 
                 <house:authorize authCode="CUSTOMER_VIEW">
                 <button type="button" class="btn btn-system" onclick="view()">查看</button>
				</house:authorize>
				
				<house:authorize authCode="CUSTOMER_EXCEL">
				<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


