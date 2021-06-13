<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>批次号</label>
								<input type="text" id="code" name="code" style="width:160px;" value=""/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;"/>
								<%-- <house:dict id="descr" dictCode="" sql=" select distinct Descr from tConfItemType where Expired = 'F' " 
									sqlValueKey="Descr" sqlLableKey="Descr" value=""/> --%>          
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>是否套餐材料</label>
								<house:xtdm id="isSetItem" dictCode="YESNO" style="width:160px;" value=""/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>提示一起下单</label>
								<house:xtdm id="infoAppAll" dictCode="YESNO" style="width:160px;" value=""/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>显示顺序</label>
								<input type="text" id="dispSeq" name="dispSeq" style="width:160px;" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'');"
									onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'');"
									value="0"/>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#custVisit_tabView_customer" data-toggle="tab">下单材料类型批次明细</a></li>  
			</ul>
		    <div class="tab-content">  
				<div id="custVisit_tabView_customer"  class="tab-pane fade in active"> 
		         	<div class="body-box-list">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="detailSave">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="detailUpdate" onclick="update()">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="delete">
									<span>删除</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
							<div id="dataTablePager"></div>
						</div>
					</div>
				</div> 
			</div>	
		</div>
	</div>
</body>	
<script type="text/javascript"> 
$(function() {
	var originalData = $("#page_form").serialize();

	var gridOption = {
		sortable: true,
		height : $(document).height()-$("#content-list").offset().top - 80,
		rowNum : 10000000,
		pager:'1',
		colModel : [
			{name: "PK", index: "PK", width: 70, label: "pk", sortable: true, align: "left",hidden:true},
			{name: "AppItemTypeBatch", index: "AppItemTypeBatch", width: 140, label: "下单材料类型批次号", sortable: true, align: "left",hidden:true},
			{name: "ConfItemType", index: "ConfItemType", width: 90, label: "施工材料分类", sortable: true, align: "left",hidden:true},
			{name: "confitemtypedescr", index: "confitemtypedescr", width: 90, label: "施工材料分类", sortable: true, align: "left"},
			{name: "DispSeq", index: "DispSeq", width: 70, label: "显示顺序", sortable: true, align: "right"},
			{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 100, label: "最后更新人员", sortable: true, align: "left"},
			{name: "LastUpdate", index: "LastUpdate", width: 130, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "ActionLog", index: "ActionLog", width: 70, label: "操作日志", sortable: true, align: "left"},
			{name: "Expired", index: "Expired", width: 70, label: "过期标志", sortable: true, align: "left"}
		],
		loadonce: true,
		ondblClickRow: function(){
			update();
		}
	};
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			code:{  
				validators: {  
					notEmpty: {  
						message: "批次号不能为空"  
					},
					remote: {
			            message: "该批次号重复",
			            url: "${ctx}/admin/appItemTypeBatch/checkCode",
			            delay:1000, //每输入一个字符，就发ajax请求，服务器压力还是太大，设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
			            type:"post",
			            cache: false
			        }
				}  
			},
			descr:{  
				validators: {  
					notEmpty: {  
						message: "名称不能为空"  
					}
				}  
			},
			isSetItem:{  
				validators: {  
					notEmpty: {  
						message: "是否套餐材料不能为空"  
					}
				}  
			},
			infoAppAll:{  
				validators: {  
					notEmpty: {  
						message: "提示一起下单不能为空"  
					}
				}  
			},
			dispSeq:{  
				validators: {  
					notEmpty: {  
						message: "显示顺序不能为空"  
					}
				}  
			}
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	
	/* 关闭按钮绑定数据是否更改校验 */
	$("#closeBut").on("click",function(){
		var newData = $("#page_form").serialize();
		var param= Global.JqGrid.allToJson("dataTable");
		
		if (param.datas.length != 0||newData != originalData) {
			art.dialog({
				content: "数据已变动,是否保存？",
				width: 200,
				okValue: "确定",
				ok: function () {
					doSave();
				},
				cancelValue: "取消",
				cancel: function () {
					closeWin();
				}
			});
		} else {
			closeWin();
		}
	});
	
	/* 保存 */
	$("#saveBtn").on("click",function(){
		doSave();
	});
	
	/* 明细新增 */
	$("#detailSave").on("click",function(){
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()) return;
		
		var confItemTypes = Global.JqGrid.allToJson("dataTable","ConfItemType");
		var keys = confItemTypes.keys;//获取ConfItemType的数组
		
		Global.Dialog.showDialog("detailSave",{
			title:"下单材料类型批次明细——新增",
			url:"${ctx}/admin/appItemTypeBatch/goDetailSave",
			postData:{code:$("#code").val(),keys:keys},
			height:310,
			width:450,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							AppItemTypeBatch : $("#code").val(),
							ConfItemType : v.confItemType,
							confitemtypedescr : v.confItemTypeDescr,
							DispSeq : v.dispSeq,
							LastUpdatedBy : v.lastUpdatedBy,
							LastUpdate : new Date(),
							ActionLog : "ADD",
							Expired : "F"
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
					  	/* 当有数据添加时，访问类型不可点 */
					  	var ids = $("#dataTable").getDataIDs();
						$("#code").attr("readonly",false);
						if (ids.length != 0) {
							$("#code").attr("readonly",true);
						}
					});
				}
			}
		});
	});
	
	/* 明细删除 */
	$("#delete").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作",width: 220});
			return false;
		}
		art.dialog({
			content:"是否删除该记录？",
			lock: true,
			width: 200,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
				/* 判断是否还有数据，对code进行操作 */
				var ids = $("#dataTable").getDataIDs();
				$("#code").attr("readonly",false);
				if (ids.length != 0) {
					$("#code").attr("readonly",true);
				}
			},
			cancel: function () {
				return true;
			}
		});
		
	});
	
	/* 主页面保存方法 */
	function doSave(){
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()) return;
		
		var datas = $("#page_form").jsonForm();
		var param= Global.JqGrid.allToJson("dataTable");
		if(param.datas.length == 0){
			art.dialog({content: "无法保存，请输入明细",width: 220});
			return;
		}
		/* 将datas（json）合并到param（json）中 */
		$.extend(param,datas);
		//console.log(param);
		
		$.ajax({
			url:"${ctx}/admin/appItemTypeBatch/doSave",
			type: "post",
			data: param,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
		    },
		    success: function(obj){
		    	if(obj.rs){
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
	
});

/* 明细编辑 */
function update(){
	var ret=selectDataTableRow();
	/* 选择数据的id */
	var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
	if(!ret){
		art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
		return false;
	}
	
	var confItemTypes = Global.JqGrid.allToJson("dataTable","ConfItemType");
	var keys = confItemTypes.keys;//获取ConfItemType的数组
	var index = keys.indexOf(ret.ConfItemType);
	if (index > -1) {
	    keys.splice(index, 1);
	}//去掉选择的ConfItemType
	
	Global.Dialog.showDialog("detailUpdate",{
		title:"下单材料类型批次明细——编辑",
		url:"${ctx}/admin/appItemTypeBatch/goDetailSave",
		postData:{code:$("#code").val(),confItemType:ret.ConfItemType,dispSeq:ret.DispSeq,m_umState:"M",keys:keys},
		height:310,
		width:450,
		returnFun : function(data){
			if(data){
				$.each(data,function(k,v){
					var json = {
						AppItemTypeBatch : $("#code").val(),
						ConfItemType : v.confItemType,
						confitemtypedescr : v.confItemTypeDescr,
						DispSeq : v.dispSeq,
						LastUpdatedBy : v.lastUpdatedBy,
						LastUpdate : new Date(),
						ActionLog : "ADD",
						Expired : "F"
				  	};
				   	$("#dataTable").setRowData(rowId,json);
				  	/* 当有数据添加时，访问类型不可点 */
				  	var ids = $("#dataTable").getDataIDs();
					$("#code").attr("readonly",false);
					if (ids.length != 0) {
						$("#code").attr("readonly",true);
					}
				});
			}
		}
	});
}

</script>
</html>
