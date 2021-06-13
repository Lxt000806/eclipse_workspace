<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
  
  	<title>新增客户</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
  	<script type="text/javascript">
		$(function() {
			$("#code").openComponent_customer({
				callBack: function(ret){
					var codes=$("#custCodeDataTable").getCol("code", false);
					var i = 0;
					while(i < codes.length){
						if(codes[i] == ret.code){
							break;
						}
						i++;
					}
					if(i == codes.length){
						Global.JqGrid.addRowData("custCodeDataTable", ret);
					}else{
						art.dialog({
							content: ret.address+" 楼盘已经存在"
						});
					}
				},
				buttonId: "component_customer",
				condition: {
					status: "4",
					//laborFeeCustStatus: "1,2,3,5"
				}
			});
			Global.JqGrid.initJqGrid("custCodeDataTable", {
				height:280,
				url:"#",
				colModel : [			
					{name: "code", index: "code", width: 75, label: "code", sortable: true, align: "left", hidden:true},
					{name: "address", index: "address", width: 140, label: "楼盘地址", sortable: true, align: "left"},
					{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
					{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
					{name: "statusdescr", index: "statusdescr", width: 80, label: "客户状态", sortable: true, align: "left"},
	            ]
			});
			
			$("#mobile1").on("blur", function(){
				if($("#mobile1").val() == ""){
					return;
				}
				$.ajax({
					url: "${ctx}/admin/custManage/getCustomers",
					type: "post",
			    	data: {
			    		phone: $("#mobile1").val()
			    	},
					dataType: "json",
					cache: false,
					error: function(obj){			    		
						art.dialog({
							content: "访问出错,请重试",
							time: 3000,
							beforeunload: function () {}
						});
					},
					success: function(res){
						if(res.length > 0){
							var tips = "";
							var codes=$("#custCodeDataTable").getCol("code", false);
							for(var i = 0;i < res.length;i++){
								var j = 0;
								while(j < codes.length){
									if(codes[j] == res[i].code){
										break;
									}
									j++;
								}
								if(j == codes.length){
									res[i].canAdd = true;
									tips += "["+res[i].address+"]<br/>";
								}else{
									res[i].canAdd = false;
								}
							}
							if(tips != ""){
								tips += "是否直接添加以上楼盘?";
								art.dialog({
									content: tips,
									ok: function(){
										for(var i = 0;i < res.length;i++){
											if(res[i].canAdd){
												Global.JqGrid.addRowData("custCodeDataTable", res[i]);
											}
										}
									},
									cancel: function(){}
								});
							}
						}
					}
				});	
			});
			
			$("#confirmMm").on("blur", function(){
				if($("#mm").val() != $("#confirmMm").val()){
					art.dialog({
						content: "两次输入的密码不同"
					});
					return;
				}
				passwordRule("confirmMm");
			});
			
			$("#mm").on("blur", function(){
				passwordRule("mm");
			});
		});
		
		function passwordRule(id, tipFlag){
		
			if(!tipFlag) {
				tipFlag = true;
			}
			
			var reg = /(^.{0,5}$)|(^.{17,})/;//(^[0-9]*$)|(^[a-zA-Z]*$)|
			if(reg.test($("#"+id).val())){
				if(tipFlag){
					art.dialog({
						content: "密码格式不正确,规则如下:<br/>1.6-16位长度;"//<br/>2.同时包含字母和数字.
					});
				}
				return false;
			}
			return true;
		}
		
		function save(){
			if($("#mobile1").val() == ""){
				art.dialog({
					content: "手机号不能为空"
				});
				return;
			}
			if($("#mm").val() == ""){
				art.dialog({
					content: "密码不能为空"
				});
				return;
			}
			
			if($("#confirmMm").val() == ""){
				art.dialog({
					content: "确认密码不能为空"
				});
				return;
			}
			if($("#mm").val() != $("#confirmMm").val()){
				art.dialog({
					content: "两次输入的密码不一样"
				});
				return;
			}

/* 			if($("#nickName").val() == ""){
				art.dialog({
					content: "请输入用户昵称"
				});
				return;
			} */
				
			if(!passwordRule("mm", false)){
				art.dialog({
					content: "密码格式不正确,规则如下:<br/>1.8-16位长度;<br/>2.同时包含字母和数字."
				});
				return;
			}

			if(!passwordRule("confirmMm", false)){
				art.dialog({
					content: "确认密码格式不正确,规则如下:<br/>1.8-16位长度;<br/>2.同时包含字母和数字."
				});
				return;
			}
			
			var codes=$("#custCodeDataTable").getCol("code", false).join(",");
			var datas = $("#dataForm").jsonForm();
			$.extend(datas, {codes: codes});
			
			$.ajax({
				url: "${ctx}/admin/custManage/doSave",
				type: "post",
		    	data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){			    		
					art.dialog({
						content: "访问出错,请重试",
						time: 3000,
						beforeunload: function () {}
					});
				},
				success: function(res){
					art.dialog({
						content: res.msg,
						ok: function(){
							if(res.rs){
								closeWin();
							}
						}
					});
				}
			});	
		}
		
		function add(){
			$("#component_customer").click();
		}
		
		function del(){
			var id = $("#custCodeDataTable").jqGrid("getGridParam", "selrow");
			if(id){
	     		$("#custCodeDataTable").jqGrid("delRowData", id);
	   	    	var ids = $("#custCodeDataTable").jqGrid("getDataIDs");
	   	    	$("#custCodeDataTable").jqGrid("setSelection",ids[0],true);
			}else{
				art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		
  	</script>

  </head>
  
  <body>
  	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
     				<button id="saveBut" type="button" class="btn btn-system "   onclick="save()">保存</button>
     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<li>
							<label>手机号</label>
							<input type="text" id="mobile1" name="mobile1"/>
						</li>
						<li>
							<label>用户昵称</label>
							<input type="text" id="nickName" name="nickName"/>
						</li>
						<li>
							<label>密码</label>
							<input type="password" id="mm" name="mm" minLength="6"/>
						</li>
						<li>
							<label>确认密码</label>
							<input type="password" id="confirmMm" name="confirmMm"/>
						</li>
					</ul>	
				</form>
			</div>
		</div>
		
		<div  class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#tabView_custCodeDataTable" data-toggle="tab">楼盘明细</a></li>  
			</ul>  
		    <div class="tab-content">  
				<div class="btn-panel" style="margin-top:2px;border: 1px solid #DDDDDD;border-top: 0px;border-bottom: 0px;">
					<div class="btn-group-sm" >
						<button id="saveBtn" type="button" class="funBtn funBtn-system " onclick="add()">新增</button>
						<div style="display:none">
							<input type="text" id="code" name="code"/>
						</div>
						<button id="delBtn" type="button" class="funBtn funBtn-system" onclick="del()">删除</button>
					</div>
				</div>	
				<div id="tabView_custCodeDataTable"  class="tab-pane fade in active"> 
					<table id="custCodeDataTable"></table>
				</div> 
			</div>	
		</div>
  	</div>
  </body>
</html>
