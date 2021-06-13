<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>下定管理</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	function clearForm(){
		$("#page_form input[type='text'] ").val('');
		$("#page_form select[id!='scopeType'][id!='scopeOperate']").val('');
		$.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
		$("#department1").val('');
	} 
	
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
	});
	
	function create(){
		var hasCon = getHasCon();
		var hasDraftCon = getHasDraftCon();
		
		if(hasDraftCon == "1"){
			art.dialog({
				content: "已存在草稿状态设计协议，不能创建设计协议"
			});
			return;
		}
		
		if(hasCon == "1"){
			art.dialog({
				content: "当前楼盘设计协议正在签约或者已签约，不能创建设计协议"
			});
			return;
		}
		
		if("3" == $.trim("${customer.status}")){
			art.dialog({
				content:"订单跟踪状态不能创建设计协议",
			});
			return;
		}
		
		if("4" == $.trim("${customer.status}")){
			art.dialog({
				content:"设计协议施工状态不能创建设计协议",
			});
			return;
		}
		
		if("5" == $.trim("${customer.status}")){
			art.dialog({
				content:"归档状态不能创建设计协议",
			});
			return;
		}
		
		Global.Dialog.showDialog("custContractCreateDesign",{
			  title:"下定管理--创建设计协议",
			  url:"${ctx}/admin/custContract/goCreateDesign",
			  postData:{custCode:"${custCode}",conType:"2"},
			  height: 715,
			  width:700,
			  returnFun: goto_query
		});
		
	}
	
	function update(){
		var ret = selectDataTableRow();
		if (ret) {
			if(ret.status != "1"){
				art.dialog({
					content: "非草稿状态，不能编辑"
				});
				return;
			}
	      	Global.Dialog.showDialog("custContractUpdate",{
				title:"下定管理--设计协议编辑",
				url:"${ctx}/admin/custContract/goUpdateDesign",
				postData:{custCode:"${custCode}",pk:ret.pk},
				height: 715,
			    width:700,
				returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function print(){
		var ret = selectDataTableRow();
		if (ret) {
			if(ret.status == "4" && ret.effectdoc != ""){//已签约且有生效文件可以打印生效设计协议
				Global.Dialog.showDialog("doPrintDesign",{ 
		   	  		title:"打印生效设计协议",
		   	  		url:"${ctx}/admin/custContract/doPrintDesign",
		   	  		postData: {
		   	  		    pk:ret.pk
		   	  		},
		   	  		height:755,
		   	  		width:937,
		        },function(){
	   	  			art.dialog({
						content:"是否已打印设计协议?",
						ok:function(){
							updatePrint(ret.pk);
						},
						cancel:function(){},
						okValue:"是",
						cancelValue:"否"
					});
	   	  		});	
			}else if(ret.status == "1"){//草稿状态可以预览
				var waitDialog=art.dialog({
					content: "设计协议生成中，请稍候...", 
					lock: true,
					esc: false,
					unShowOkButton: true
				});
				$.ajax({
			         url : '${ctx}/admin/custContract/beforePrint',
			         type : 'post',
			         data : {pk:ret.pk},
			         dataType : 'json',
			         cache : false,
			         error : function(obj) {
			             showAjaxHtml({
		                    "hidden" : false,
		                    "msg" : '保存数据出错~'
		              	 });
			          	waitDialog.close();
		              	 art.dialog({
							content: "预览失败，请检查是否上传模板",
							width: 200
						});
			         },
			         success : function(obj) {
			          	waitDialog.close();
			          	if(obj.rs){
			          		Global.Dialog.showDialog("doPrint",{ 
					   	  		title:"打印合同",
					   	  		url:"${ctx}/admin/custContract/doPrintDesign?#toolbar=0",
					   	  		postData: {pk:ret.pk,tempFile:obj.msg},
					   	  		height:755,
					   	  		width:937,
					   	  		shade:true
					        });
			          	}else{
			          		art.dialog({
								content: obj.msg,
								width: 200
							});
			          	}
			         }
			    }); 
			}else if(ret.status == "2"){//申请状态可以预览
				Global.Dialog.showDialog("doPrint",{ 
			 		title:"预览设计协议",
			 		url:"${ctx}/admin/custContract/doPrintDesign?#toolbar=0",
			 		postData: {
			 		    pk:ret.pk,m_umState:"C"
			 		},
			 		height:755,
			 		width:937,
			 		shade:true
			   });	
			}else {
		    	art.dialog({
					content: "当前状态无法预览/打印设计协议"
				});
	  		}	
        } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }	
	}
	
	function updatePrint(conPk){
		$.ajax({
	         url : '${ctx}/admin/custContract/updatePrint',
	         type : 'post',
	         data : {
	              'pk' : conPk
	         },
	         async:false,
	         dataType : 'json',
	         cache : false,
	         error : function(obj) {
	             showAjaxHtml({
                    "hidden" : false,
                    "msg" : '保存数据出错~'
              	 });
	         },
	         success : function(obj) {
	          	goto_query();
	         }
	    });
	}
	
	function view(){
		var ret = selectDataTableRow();
	    if (ret) {
	      Global.Dialog.showDialog("custContractViewDesign",{
			  title:"下定管理--查看",
			  url:"${ctx}/admin/custContract/goViewDesign",
			  postData:{pk:ret.pk},
			  height: 715,
			  width:720
		  });
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function send(){
		var ret = selectDataTableRow();
		if (ret) {
			art.dialog({
				content:"您确定要发送签署吗？",
				lock: true,
				ok: function () {
					var waitDialog=art.dialog({
						content: "发送签署中，请稍候...", 
						lock: true,
						esc: false,
						unShowOkButton: true
					});
					$.ajax({
				         url : '${ctx}/admin/custContract/doSendDesign',
				         type : 'post',
				         data : {
				              'pk' : ret.pk
				         },
				         dataType : 'json',
				         cache : false,
				         error : function(obj) {
				             showAjaxHtml({
			                    "hidden" : false,
			                    "msg" : '保存数据出错~'
			              	 });
				         },
				         success : function(obj) {
				         	waitDialog.close();
				          	if(obj.rs){
					    		art.dialog({
									content: obj.msg,
									time: 1000,
									beforeunload: function () {
			                            goto_query();
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
	
	function cancel(){
		var ret = selectDataTableRow();
		if (ret) {
			art.dialog({
				content:"您确定要取消设计协议吗？",
				lock: true,
				ok: function () {
					$.ajax({
				         url : '${ctx}/admin/custContract/doCancelDesign',
				         type : 'post',
				         data : {
				              'pk' : ret.pk
				         },
				         dataType : 'json',
				         cache : false,
				         error : function(obj) {
				             showAjaxHtml({
			                    "hidden" : false,
			                    "msg" : '保存数据出错~'
			              	 });
				         },
				         success : function(obj) {
				          	if(obj.rs){
					    		art.dialog({
									content: obj.msg,
									time: 1000,
									beforeunload: function () {
			                            goto_query();
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
	
	function refreshStatus(){
		var ret = selectDataTableRow();
		if (ret) {
			art.dialog({
				content:"您确定要进行状态更新吗？",
				lock: true,
				ok: function () {
					$.ajax({
				         url : '${ctx}/admin/custContract/doRefreshStatusDesign',
				         type : 'post',
				         data : {
				              'pk' : ret.pk
				         },
				         dataType : 'json',
				         cache : false,
				         error : function(obj) {
				             showAjaxHtml({
			                    "hidden" : false,
			                    "msg" : '保存数据出错~'
			              	 });
				         },
				         success : function(obj) {
				          	if(obj.rs){
					    		art.dialog({
									content: obj.msg,
									time: 1000,
									beforeunload: function () {
			                            goto_query();
			                        }
								});
					    	}else{
					    		$("#_form_token_uniq_id").val(obj.token.token);
					    		art.dialog({
									content: obj.msg,
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

	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			styleUI : "Bootstrap",
			url:"${ctx}/admin/custContract/goJqGrid",
			postData: {custCode:"${custCode}",conType:"2"},
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
				{name: "pk", index: "pk", width: 70, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "partyaname", index: "partyaname", width: 70, label: "甲方姓名", sortable: true, align: "left"},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "合同状态", sortable: true, align: "left"},
				{name: "status", index: "status", width: 70, label: "合同状态", sortable: true, align: "left",hidden:true},
				{name: "signdate", index: "signdate", width: 90, label: "发起日期", sortable: true, align: "left", formatter: formatDate},
				{name: "effectdoc", index: "effectdoc", width: 120, label: "生效设计协议文档", sortable: true, align: "left",hidden:true},
				{name: "effectdocdescr", index: "effectdocdescr", width: 120, label: "生效设计协议文档", sortable: true, align: "left",formatter:docBtn},
				{name: "partasigndate", index: "partasigndate", width: 90, label: "甲方签署日期", sortable: true, align: "left", formatter: formatDate},
				{name: "appczydescr", index: "appczydescr", width: 70, label: "申请人", sortable: true, align: "left",},
				{name: "appdate", index: "appdate", width: 90, label: "申请日期", sortable: true, align: "left", formatter: formatDate},
				{name: "enddescr", index: "enddescr", width: 70, label: "结束人", sortable: true, align: "left",},
				{name: "enddate", index: "enddate", width: 90, label: "结束日期", sortable: true, align: "left", formatter: formatDate},
				{name: "endremarks", index: "endremarks", width: 90, label: "结束原因", sortable: true, align: "left"},
				{name: "printnum", index: "printnum", width: 70, label: "打印次数", sortable: true, align: "right",},
				{name: "lastprintczy", index: "lastprintczy", width: 70, label: "最后打印人", sortable: true, align: "left",},
				{name: "lastprintdate", index: "lastprintdate", width: 110, label: "打印日期", sortable: true, align: "left", formatter: formatTime},
			], 
			ondblClickRow:function(){
				view();
            },	
		});
		onCollapse(44);
	});

	function docBtn(v,x,r){
		return v==null?"":"<a href='#' onclick='viewDoc("+x.rowId+");'>"+v+"</a>";
	}   		
	
	//预览设计协议
	function viewDoc(id){
		var ret = $("#dataTable").jqGrid('getRowData', id);
		Global.Dialog.showDialog("doPrintDesign",{ 
  	  		title:"预览设计协议",
  	  		url:"${ctx}/admin/custContract/doPrintDesign?#toolbar=0",
  	  		postData: {
  	  		    pk:ret.pk
  	  		},
  	  		height:755,
  	  		width:937,
  	  		shade:true
       });	
	} 
	
	//前置条件
	function getHasCon(){
		var hasCon = "0";
		$.ajax({
	         url : '${ctx}/admin/custContract/hasCon',
	         type : 'post',
	         data : {
	              'custCode' : "${custCode}",conType:"2"
	         },
	         async:false,
	         dataType : 'json',
	         cache : false,
	         error : function(obj) {
	             showAjaxHtml({
                    "hidden" : false,
                    "msg" : '保存数据出错~'
              	 });
	         },
	         success : function(obj) {
	            hasCon = obj;
	         }
	    });
	    return hasCon;
	}
	
	//前置条件
	function getHasDraftCon(){
		var hasCon = "0";
		$.ajax({
	         url : '${ctx}/admin/custContract/hasDraftCon',
	         type : 'post',
	         data : {
	              'custCode' : "${custCode}",conType:"2"
	         },
	         async:false,
	         dataType : 'json',
	         cache : false,
	         error : function(obj) {
	             showAjaxHtml({
                    "hidden" : false,
                    "msg" : '保存数据出错~'
              	 });
	         },
	         success : function(obj) {
	            hasCon = obj;
	         }
	    });
	    return hasCon;
	}		
		
</script>
</head> 
<body>
	<div class="body-box-list">
		<div class="btn-panel">
			<div class="btn-group-sm">
			        <house:authorize authCode="ITEMPLAN_CREATEDESIGN">
						<button type="button" class="btn btn-system " onclick="create()">
							<span>创建</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ITEMPLAN_UPDATEDESIGN">
						<button type="button" class="btn btn-system " onclick="update()">
							<span>编辑</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ITEMPLAN_CANCELDESIGN">
						<button type="button" class="btn btn-system " onclick="cancel()">
							<span>取消</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ITEMPLAN_SENDDESIGN">
						<button type="button" class="btn btn-system " onclick="send()">
							<span>发送签署</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ITEMPLAN_PRINTDESIGN">
						<button type="button" class="btn btn-system " onclick="print()">
							<span>预览/打印</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ITEMPLAN_REFRESHSTATUSDESIGN">
						<button type="button" class="btn btn-system " onclick="refreshStatus()">
							<span>状态更新</span>
						</button>
					</house:authorize>
					<button type="button" class="btn btn-system " onclick="goto_query()">
						<span>刷新</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
		</div>
		<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
		</div>
	</div>	
</body>
</html>
