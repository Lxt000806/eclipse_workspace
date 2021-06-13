<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>专盘管理新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function() {
	$("#leaderCode").openComponent_employee({showValue:"${spcBuilder.leaderCode}",showLabel:"${leaderDescr}",readonly:true});	
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields: {  
				descr:{  
					validators: {  
						notEmpty: {  
							message: '专盘名称不能为空'  
						}
					}  
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});	
$(function() {
	$("#leaderCode").openComponent_employee({showValue:"${spcBuilder.leaderCode}",showLabel:"${leaderDescr}",readonly:true});	
	var lastCellRowId;
	var gridOption = {
		url:"${ctx}/admin/builder/goSpcBuilderJqGrid",
		postData:{spcBuilder:$.trim($("#code").val())},
		height:$(document).height()-$("#content-list").offset().top-80,
		rowNum:10000000,
		colModel : [
			{name:'SpcSeq', index:'SpcSeq', width:80, label:'结算顺序号', sortable:true ,align:"left" ,hidden:true},
			{name:'Code', index:'Code', width:80, label:'项目编号', sortable:true ,align:"left"},
			{name:'Descr', index:'Descr', width:80, label:'项目名称', sortable:true ,align:"left"},
			{name:'Adress', index:'Adress', width:180, label:'楼盘地址', sortable:true ,align:"left"},
			{name:'regiondescr1', index:'regiondescr1', width:80, label:'区域', sortable:true ,align:"left"},
			{name:'regiondescr2', index:'regiondescr2', width:80, label:'二级区域', sortable:true ,align:"left"},
			{name:'delivcode', index:'delivcode', width:80, label:'批次号', sortable:true ,align:"left"},
			{name:'buildercode', index:'buildernum', width:300, label:'楼号', sortable:true ,align:"left"},
		
		],
	};
		var detailJson = Global.JqGrid.allToJson("dataTable","itcode");
		Global.JqGrid.initJqGrid("dataTable",gridOption);
		
	$("#add").on("click",function(){
	    var item2=new Array();
	    var days=0;
		var item1 = $.trim($("#itemType1").val());
		var supplier = $.trim($("#supplier").val());
		var custCode = $.trim($("#custCode").val());
		var code = Global.JqGrid.allToJson("dataTable","Code");
		var arr = new Array();
		arr = code.fieldJson.split(",");
		var Ids =$("#dataTable").getDataIDs();
		spcSeq=Ids.length;
		      Global.Dialog.showDialog("save",{
			  title:"专盘管理——新增项目",
			  url:"${ctx}/admin/spcBuilder/goAdd",
			  postData:{arr:arr,code:"${spcBuilder.code}"},
			  height: 680,
			  width:1000,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
					  spcSeq++;
						  var json = {
							Code:v.Code,
					  		SpcSeq:spcSeq,							
							Descr:v.Descr,
							Adress:v.Adress,
							regiondescr1:v.regiondescr1,
							regiondescr2:v.regiondescr2,
						  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
				  }
			  } 
		 });
	});	
	
	$("#deliv").on("click",function(){
		var ret = selectDataTableRow();
		var code = Global.JqGrid.allToJson("dataTable","Code");
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		var rowDatas = $("#dataTable").jqGrid('getRowData', rowId);
		
		var delivcode = Global.JqGrid.allToJson("dataTable","delivcode");
		var arr = new Array();
			arr = delivcode.fieldJson.split(",");
		
		if(!ret){
			art.dialog({
				content:"请选择一条专盘数据",
			});
			return;
		}
		
		Global.Dialog.showDialog("save",{
			  title:"专盘管理——设置批次号",
			  url:"${ctx}/admin/spcBuilder/goDeliv",
			  postData:{delivNums:arr,code:ret.Code,descr:ret.Descr},
			  height: 680,
			  width:1000,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						var json = {
							Code:ret.Code,
							SpcSeq:ret.SpcSeq,
							Descr:ret.Descr,
							Adress:ret.Adress,
							regiondescr1:ret.regiondescr1,
							regiondescr2:ret.regiondescr2,
							delivcode:v.Code,
							buildercode:v.buildernum,
						};
						if(rowDatas.delivcode==""){
							Global.JqGrid.delRowData("dataTable",rowId);
						}
						Global.JqGrid.addRowData("dataTable",json);
					  });
				  }
			  } 
		 });
	})
	
	$("#del").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		
		art.dialog({
			 content:"是删除该记录？",
			 lock: true,
			 width: 100,
			 height: 80,
			 ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
				var Ids =$("#dataTable").getDataIDs();
				for(var i=0;i<Ids.length;i++){
					$("#dataTable").setCell(Ids[i], 'SpcSeq',i+1);
					spcSeq=i+1;
				}	
			},
			cancel: function () {
				return true;
			}
		});
	});
	
	//保存
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			var descr=$.trim($("#descr").val());
			if(descr==null||descr==""){
				art.dialog({
					content:专盘名称不能为空,
				});
				return false;
			}
			var param= Global.JqGrid.allToJson("dataTable");
			var code = Global.JqGrid.allToJson("dataTable","Code");
			var delivCode = Global.JqGrid.allToJson("dataTable","delivcode");
			codeArry = code.fieldJson.split(",");	
			delivCodeArry = delivCode.fieldJson.split(",");
			for(var i=0;i<delivCodeArry.length ;i++){
				for(var j=i+1;j<delivCodeArry.length;j++){
					if(($.trim(delivCodeArry[i])==""||$.trim(delivCodeArry[j])=="")&&$.trim(codeArry[i])==$.trim(codeArry[j])){
						art.dialog({
							content:"存在重复的项目名称",
						});
						return;
					}
				}
			}
			
			Global.Form.submit("dataForm","${ctx}/admin/spcBuilder/doUpdate",param,function(ret){
				if(ret.rs==true){
					art.dialog({
						content:ret.msg,
						time:1000,
						beforeunload:function(){
							closeWin();
						}
					});				
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content:ret.msg,
						width:200
					});
				}
			});
		});
		
});
function isExpired(obj){
	if ($(obj).is(':checked')){
		$('#expired').val('T');
	}else{
		$('#expired').val('F');
	}
}
</script>
</head>
	<body>
<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="expired" id="expired" value="${spcBuilder.expired }" />
						<ul class="ul-form">
							<li>
								<label>专盘编号</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${spcBuilder.code }" readonly="readonly"/>
							</li>
							<li class="form-validate">
								<label>专盘名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${spcBuilder.descr }" readonly="true"/>
							</li>
							<li >
								<label>队长</label>
								<input type="text" id="leaderCode" name="leaderCode" style="width:160px;" value="${spcBuilder.leaderCode }" disabled="true"/>
							</li>
							<li class="form-validate">
								<label>楼盘类型</label>
								<house:xtdm id="type" dictCode="SPCBUILDERTYPE" value="${spcBuilder.type }" disabled="true"></house:xtdm>                     
							</li>
							<li >
								<label>项目类型</label>
								<house:xtdm id="builderType" dictCode="BUILDERTYPE" value="${spcBuilder.builderType }" disabled="true"></house:xtdm>                     
							</li>
							<li class="form-validate">
								<label>交房时间</label>
								<input type="text" id="delivDate" name="delivDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${spcBuilder.delivDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
							</li>
							<li >
								<label>总户数</label>
								<input type="text" id="totalQty" name="totalQty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.totalQty }" readonly="true"/>
							</li>
							<li class="form-validate">
								<label>交房户数</label>
								<input type="text" id="delivQty" name="delivQty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.delivQty }"/>
							</li>
							<li >
								<label>总开工户数</label>
								<input type="text" id="totalBeginQty" name="totalBeginQty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.totalBeginQty }"/>
							</li>
							<li class="form-validate">
								<label>业主自装户数</label>
								<input type="text" id="selfDecorQty" name="selfDecorQty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.selfDecorQty }"/>
							</li>
							<li >
								<label>装修公司开工户数</label>
								<input type="text" id="decorCmpBeginQty" name="decorCmpBeginQty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.decorCmpBeginQty}"/>
							</li>
							<li class="form-validate">
								<label>装修公司1</label>
								<input type="text" id="decorCmp1" name="decorCmp1" style="width:160px;" value="${spcBuilder.decorCmp1 }"/>
							</li>
							<li >
								<label>套数1</label>
								<input type="text" id="decorCmp1Qty" name="decorCmp1Qty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.decorCmp1Qty }"/>
							</li>
							<li class="form-validate">
								<label>装修公司2</label>
								<input type="text" id="decorCmp2" name="decorCmp2" style="width:160px;" value="${spcBuilder.decorCmp2 }"/>
							</li>
							<li >
								<label>套数2</label>
								<input type="text" id="decorCmp2Qty" name="decorCmp2Qty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.decorCmp2Qty }"/>
							</li>
							<li class="form-validate">
								<label>装修公司3</label>
								<input type="text" id="decorCmp3" name="decorCmp3" style="width:160px;" value="${spcBuilder.decorCmp3 }"/>
							</li>
							<li >
								<label>套数3</label>
								<input type="text" id="decorCmp3Qty" name="decorCmp3Qty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.decorCmp3Qty }"/>
							</li>
							<li class="form-validate">
								<label>装修公司4</label>
								<input type="text" id="decorCmp4" name="decorCmp4" style="width:160px;" value="${spcBuilder.decorCmp4 }"/>
							</li>
							<li >
								<label>套数4</label>
								<input type="text" id="decorCmp4Qty" name="decorCmp4Qty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.decorCmp4Qty }"/>
							</li>
							<li class="form-validate">
								<label>装修公司5</label>
								<input type="text" id="decorCmp5" name="decorCmp5" style="width:160px;"  value="${spcBuilder.decorCmp5 }"/>
							</li>
							<li >
								<label>套数5</label>
								<input type="text" id="decorCmp5Qty" name="decorCmp5Qty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.decorCmp5Qty }"/>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${spcBuilder.remarks }</textarea>
 							</li>
 							<li class="search-group">					
								<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${spcBuilder.expired }" onclick="isExpired(this)" 
								 ${spcBuilder.expired!='F'?'checked':'' } /><p>过期</p>
							</li>
						</ul>	
				</form>
				</div>
			</div>
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active"><a href="#tab_detail" data-toggle="tab">项目名称列表</a></li>
			    </ul> 
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>	
			</div>
		</div>
	</body>	
</html>
