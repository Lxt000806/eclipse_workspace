<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
    String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<script type="text/javascript">
$(function(){
	if("V"=="${customer.m_umState}"){
		$("#rePrint").remove();
	}
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/custPay/goPayInfoJqGrid",
			postData:{code:"${customerPayMap.code }"},
		    rowNum:10000000,
			height:200,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel : [
				{name: "pk", index: "pk", width: 100, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "custpaytranpk", index: "custpaytranpk", width: 100, label: "CustPayTranPK", sortable: true, align: "left",hidden:true},
				{name: "ischeckout", index: "ischeckout", width: 100, label: "是否收入记账", sortable: true, align: "left",hidden:true},
				{name: "paycheckoutno", index: "paycheckoutno", width: 100, label: "记账号", sortable: true, align: "left",hidden:true},
				{name: "adddate", index: "adddate", width: 127, label: "登记日期", sortable: true, align: "left", formatter: formatTime},
				{name: "date", index: "date", width: 137, label: "付款日期", sortable: true, align: "left", formatter: formatTime},
				{name: "amount", index: "amount", width: 100, label: "付款金额", sortable: true, align: "right", sum: true},
				{name: "rcvactdescr", index: "rcvactdescr", width: 115, label: "收款账号", sortable: true, align: "left"},
				{name: "posdescr", index: "posdescr", width: 144, label: "POS机", sortable: true, align: "left"},
				{name: "procedurefee", index: "procedurefee", width: 78, label: "手续费", sortable: true, align: "right"},
				{name: "payno", index: "payno", width: 129, label: "收款单号", sortable: true, align: "left"},
				{name: "typedescr", index: "typedescr", width: 70, label: "类型", sortable: true, align: "left"},
				{name: "disctokenno", index: "disctokenno", width: 120, label: "优惠券号", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
				{name: "printczyname", index: "printczyname", width: 80, label: "打印人", sortable: true, align: "left"},
				{name: "wfprocinstno", index: "wfprocinstno", width: 80, label: "流程实例编号", sortable: true, align: "left", hidden: true},
				{name: "printdate", index: "printdate", width: 130, label: "打印时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "操作人员", sortable: true, align: "left"},
			],
				onSortCol:function(index, iCol, sortorder){
					var rows = $("#detailDataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("detailDataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("detailDataTable", v);
					});
				},
		        ondblClickRow:function(){
					d_view();
	            },	
 		};
	   	Global.JqGrid.initJqGrid("detailDataTable",gridOption);
	// 重打小票
   	$("#rePrint").on("click",function () {
   		var rowId = $("#detailDataTable").jqGrid("getGridParam","selrow");//选中行id
		var ret = $("#detailDataTable").jqGrid('getRowData',rowId);//根据id获取选中行data
		if(!ret){
			art.dialog({content: "请选择一条数据"});
			return;
		}
		if (!ret.custpaytranpk && !ret.wfprocinstno) {
			art.dialog({
				content:"非POS收银或业主转账记录不可使用此功能",
			});
			return;
		}
		
		if (ret.custpaytranpk) {
		    ret.pk = ret.custpaytranpk;
	        Global.Dialog.showDialog("rePrint",{
	            title:"重打小票",
	            url:"${ctx}/admin/custPayTran/goRePrint",
	            postData:{map:JSON.stringify(ret)},
	            height:260,
	            width:700,
	            returnFun: goto_query
	        });
		} else if (ret.wfprocinstno) {
            Global.Print.showPrint("custPayDoc.jasper", {
                   pk: parseInt(ret.pk),
                   LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
                   SUBREPORT_DIR: "<%=jasperPath%>" 
               }, null, function() {
                   art.dialog({
					   content: "是否已打印凭条?",
					   ok: function() {
						   $.ajax({
					           url: "${ctx}/admin/custPay/updateLatestPrintInfo",
					           type: "post",
					           data: {custPayPk: ret.pk},
					           dataType: "json",
					           cache: false,
					           success: function(obj) {
					               art.dialog({
									    content: "操作成功！",
									    time: 1000,
									    beforeunload: function() {
									        goto_query("detailDataTable")
									    }
								   })
				               }
			               })
				       },
				       cancel: function() {}
			     })
 
             })
               
		}
		
   });
   
   $("#addFromExcel").on("click",function(){
		Global.Dialog.showDialog("addFromExcel",{
			title:"收款信息——从Excel导入",
			url:"${ctx}/admin/custPay/goImportExcel",
			postData:{custCode:$("#code").val()},
			height:600,
			width:1200,
			returnFun:function(data){
				var amount=parseFloat(data);
				$("#haspay").val(parseFloat($("#haspay").val())+amount);
				$("#nowShouldPay").val(parseFloat($("#nowShouldPay").val())-amount);
				$("#nextShouldPay").val(parseFloat($("#nextShouldPay").val())-amount);
				goto_query("detailDataTable");
			}
		});
	});
   
});
	//新增
	function d_add() {
		Global.Dialog.showDialog("save", {
			title : "收款信息——新增",
			url : "${ctx}/admin/custPay/goSave",
			postData : {
				'custCode' : $("#code").val(),
			},
			height : 620,
			width : 650,
			returnFun:function(data){
				var amount=parseFloat(data);
				$("#haspay").val(parseFloat($("#haspay").val())+amount);
				$("#nowShouldPay").val(parseFloat($("#nowShouldPay").val())-amount);
				$("#nextShouldPay").val(parseFloat($("#nextShouldPay").val())-amount);
				goto_query("detailDataTable");
			}
		});
	}
	//编辑
	function d_update() {
		var rowId = $("#detailDataTable").jqGrid("getGridParam","selrow");//选中行id
		var ret = $("#detailDataTable").jqGrid('getRowData',rowId);//根据id获取选中行data
		if (rowId) {
			var ischeckout=ret.ischeckout;
			var paycheckoutno=ret.paycheckoutno;
			if($.trim(ischeckout)=="1"){
			 	art.dialog({content: "该付款已经做收入记账，记账单号【"+paycheckoutno+"】，不允许编辑！",width: 430});
				return;
			}
			if($.trim(ret.disctokenno)!=""){
			 	art.dialog({content: "优惠券生成记录，不允许编辑！",width: 160});
				return;
			}
			Global.Dialog.showDialog("update", {
				title : "收款信息——编辑",
				url : "${ctx}/admin/custPay/goUpdate",
				postData : {
					'custCode' : $("#code").val(),pk:ret.pk
				},
				height : 620,
				width : 650,
				returnFun:function(){
					goto_query("detailDataTable");
				}
			});
		}else{
		  	art.dialog({    	
				content: "请选择一条记录"
			});
		}
	}
	//复制
	function d_copy() {
		var rowId = $("#detailDataTable").jqGrid("getGridParam","selrow");//选中行id
		var ret = $("#detailDataTable").jqGrid('getRowData',rowId);//根据id获取选中行data
		if (rowId) {
			if($.trim(ret.disctokenno)!=""){
			 	art.dialog({content: "优惠券生成记录，不允许复制！",width: 430});
				return;
			}
			Global.Dialog.showDialog("copy", {
				title : "收款信息——复制",
				url : "${ctx}/admin/custPay/goCopy",
				postData : {
					'custCode' : $("#code").val(),pk:ret.pk
				},
				height : 620,
				width : 650,
				returnFun:function(){
					goto_query("detailDataTable");
				}
			});
		}else{
		  	art.dialog({    	
				content: "请选择一条记录"
			});
		}
	}
	//查看
	function d_view() {
		var rowId = $("#detailDataTable").jqGrid("getGridParam","selrow");//选中行id
		var ret = $("#detailDataTable").jqGrid('getRowData',rowId);//根据id获取选中行data
		if (rowId) {
			Global.Dialog.showDialog("view", {
				title : "收款信息——查看",
				url : "${ctx}/admin/custPay/goView",
				postData : {
					'custCode' : $("#code").val(),pk:ret.pk
				},
				height : 650,
				width : 650,
				returnFun:function(){
					goto_query("detailDataTable");
				}
			});
		}else{
		  	art.dialog({    	
				content: "请选择一条记录"
			});
		}
	}
	
	//删除
	function d_delete(){
	 	var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#detailDataTable").jqGrid('getRowData',id);//根据id获取选中行data
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		var ischeckout=ret.ischeckout;
		var paycheckoutno=ret.paycheckoutno;
		if($.trim(ischeckout)=="1"){
			art.dialog({content: "该付款已经做收入记账，记账单号【"+paycheckoutno+"】，不允许删除！",width: 430});
			return;
		}
		if($.trim(ret.disctokenno)!=""){
		 	art.dialog({content: "优惠券生成记录，不允许删除！",width: 160});
			return;
		}
		
		if (ret.wfprocinstno) {
		    art.dialog({content: "业主转账审批生成的记录不允许删除！"})
		    return
		}
		
		art.dialog({
			 content:"是否确认要删除",
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
				$.ajax({
					url : "${ctx}/admin/custPay/doDelete",
					type : "post",
					data : {deleteIds:ret.pk},
					dataType : "json",
					cache : false,
					error : function(obj) {
						showAjaxHtml({
							"hidden" : false,
							"msg" : "保存数据出错~"
						});
					},
					success : function(obj) {
						if (obj.rs) {
							var amount=parseFloat(ret.amount);
							$("#haspay").val(parseFloat($("#haspay").val())-amount);
							$("#nowShouldPay").val(parseFloat($("#nowShouldPay").val())+amount);
							$("#nextShouldPay").val(parseFloat($("#nextShouldPay").val())+amount);
							goto_query("detailDataTable");
							art.dialog({
								content : obj.msg,
								time : 1000,
								beforeunload : function() {
								}
							});
						} else {
							$("#_form_token_uniq_id").val(obj.token.token);
							art.dialog({
								content : obj.msg,
								width : 200
							});
						}
					}
				}); 
			},
			cancel: function () {
					return true;
			}
		}); 
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form" method="post">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="test" >
			</form>
			<div class="btn-group-xs" id="custpaybtn">
				<button style="align:left" type="button" class="btn btn-system "
					onclick="d_add()">
					<span>新增 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					onclick="d_delete()">
					<span>删除 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					onclick="d_copy()">
					<span>复制</span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					onclick="d_update()">
					<span>编辑 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					onclick="d_view()">
					<span>查看 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					id="rePrint">
					<span>重打小票 </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="detailDataTable"></table>
	</div>
</div>



