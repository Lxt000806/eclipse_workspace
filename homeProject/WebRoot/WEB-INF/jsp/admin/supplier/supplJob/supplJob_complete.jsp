<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>供应商任务处理——完成</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript"> 
	$(function(){
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
		
	$("#complete").on("click", function() {
			$("#dataForm").bootstrapValidator('validate');
			if (!$("#dataForm").data('bootstrapValidator').isValid())
				return;
			var datas = $("#dataForm").serialize();
			$.ajax({
				url : "${ctx}/admin/supplJob/doComplete",
				type : "post",
				data : {no:$("#no").val(),completeDate:$("#completeDate").val(),pk:$("#pk").val(),recvDate:$("#recvDate").val(),supplRemarks:$("#supplRemarks").val(),planDate:$("#planDate").val()},
				dataType : "json",
				cache : false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : "保存数据出错~"
					});
					art.dialog({
							content : "已完成",
							time : 1000,
							beforeunload : function() {
								closeWin();
							}
						});
						
				},
				success : function(obj) {
					if (obj.rs) {
						art.dialog({
							content : obj.msg,
							time : 1000,
							beforeunload : function() {
								closeWin();
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
		});
		
		var gridOption = {	
			height:$(document).height()-$("#content-list").offset().top-82,
			multiselect:true,
			url:"${ctx}/admin/prjJobPhoto/goJqGrid",
			postData:{prjJobNo:"${supplJob.no}"} ,
			rowNum:100000000,
			colModel : [
				{name:"PhotoName",	index:"PhotoName",	width:160,	label:"图片名称",	sortable:true,align:"left",},
				{name:"LastUpdate",	index:"LastUpdate",	width:95,	label:"最后修改时间",	sortable:true,align:"left" ,formatter:formatDate},
				{name:"LastUpdatedBy",	index:"LastUpdatedBy",	width:95,	label:"最后修改人员",	sortable:true,align:"left" ,},
			],
			onCellSelect: function(id,iCol,cellParam,e){
	 			var ids = $("#dataTable").jqGrid("getDataIDs");  
				var photoname = Global.JqGrid.allToJson("dataTable","PhotoName");
				var url =$.trim("${url}");
				var arr = new Array();
					arr = photoname.fieldJson.split(",");
				var arry = new Array();
					arry = arr[id-1].split(".");
				if(arry[1]=="png"||arry[1]=="jpg"||arry[1]=="gif"||arry[1]=="jpeg"||arry[1]=="bmp"){//jpg/gif/jpeg/png/bmp.
					document.getElementById("docPic").src =url+arr[id-1].substring(0,5)+"/"+arr[id-1];	
				}else{
					document.getElementById("docPic").src ="";	
				}
				for(var i=0;i<ids.length;i++){
					if(i!=id-1){
						$("#"+ids[i]).find("td").removeClass("SelectBG");
					}
				}
				$("#"+ids[id-1]).find("td").addClass("SelectBG"); 
			
			},
		};
		Global.JqGrid.initEditJqGrid("dataTable",gridOption);
		
		$("#add").on("click",function(){
			var ret=selectDataTableRow();
			$("#m_umState").val("A");
			Global.Dialog.showDialog("Add",{
				title:"图片上传",
				url:"${ctx}/admin/prjJobManage/goAddPic",
				postData:{no:"${supplJob.no}"},
				height:550,
				width:950,
				returnFun:function(data){
					if(data.length > 0){
						$("#dataTable").jqGrid("setGridParam",{postData:{no:"${supplJob.no}"},sortname:""}).trigger("reloadGrid");
					}
				}
			});
		});
	
		$("#del").on("click",function(){
		    var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
		    console.log(ids);
			if(ids==""||ids==null){
				art.dialog({
					content:"请选择一条或多条数据删除",
				});
				return false;
			}
			 art.dialog({
				 content:"是否确定删除",
				 lock: true,
				 ok: function () {
				 	var arry =new Array();
				 	for(var i=0;i<ids.length;i++){
				 		var row = $("#dataTable").jqGrid("getRowData", ids[i]);
			    		arry.push(row.PhotoName);
				 	}
					var photoNames="";
					for(var x=0;x<arry.length;x++){
						photoNames=photoNames+arry[x]+",";
					}
					console.log(photoNames);
					$.ajax({
						url:"${ctx}/admin/prjJobManage/doDeletePic",
						type: "post",
						data:{photoName:photoNames},
						dataType: "json",
						async:false,
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
					    },
					    success: function(obj){
							if(obj){
								art.dialog({
									content:"删除成功",
									time:500,
								});
								document.getElementById("docPic").src ="";	
								$("#dataTable").jqGrid("setGridParam",{postData:{no:"${supplJob.no}"},sortname:""}).trigger("reloadGrid");
							}else{
								art.dialog({
									content:"操作失败",
									time:500,
								});
							}
					    }
					 }); 
				},
				cancel: function () {
						return true;
				}
			});	 
		});
	});
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="complete">
						<span>完成</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<div class="validate-group row">
							<li><label>任务单号</label> <input type="text" id="no" name="no"
								style="width:160px;" value="${supplJob.no}" disabled="true" />
									<input type="hidden" id="pk" name="pk"
								style="width:160px;" value="${supplJob.pk}"  />
							</li>
							<li><label>楼盘</label> <input type="text" id="address"
								name="address" style="width:160px;" value="${supplJob.address}"
								disabled="true" />
							</li>
							<li><label>任务类型</label> <input type="text" id="jobTypeDescr"
								name="jobTypeDescr" style="width:160px;"
								value="${supplJob.jobTypeDescr}" disabled="true" />
							</li>
							<li><label>状态</label> <house:xtdm id="status"
									dictCode="SUPPLJOBSTS" value="${supplJob.status}" disabled="true"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>指派时间</label> <input
								type="text" id="date" name="date" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								value="<fmt:formatDate value='${supplJob.date}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								disabled="true" />
							</li>
							<li class="form-validate"><label>接收时间</label> <input
								type="text" id="recvDate" name="recvDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								value="<fmt:formatDate value='${supplJob.recvDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								disabled="true" />
							</li>
							<li class="form-validate"><label>完成时间</label> <input
								type="text" id="completeDate" name="completeDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								value="<fmt:formatDate value='${supplJob.completeDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								disabled="true" />
							</li>
							<li><label>计划处理时间</label> <input
								type="text" id="planDate" name="planDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${supplJob.planDate}' pattern='yyyy-MM-dd '/>" disabled="true" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label class="control-textarea"><span
									class="required">*</span>供应商备注</label> <textarea id="supplRemarks"
									name="supplRemarks" />${supplJob.supplRemarks
								}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel" >
   			<div class="btn-group-sm"  >
				<button type="button" class="btn btn-system " id="add" >
					<span>添加</span>
				</button>
				<button type="button" class="btn btn-system " id="del">
					<span>删除</span>
				</button>
			</div>
		</div>
		<div style="width:53%; float:left; margin-left:0px; ">
			<div class="body-box-form" >
				<div class="container-fluid" style="whith:800px">  
					<div id="content-list" style="whith:800px">
						<table id= "dataTable"></table>
					</div>	
				</div>
			</div>
		</div>
		<div style="width:46.5%; float:right; margin-left:0px; ">
				<img id="docPic" src=" " onload="AutoResizeImage(450,450,'docPic');" width="500" height="385" >  
		</div>	
	</div>
</body>
</html>
