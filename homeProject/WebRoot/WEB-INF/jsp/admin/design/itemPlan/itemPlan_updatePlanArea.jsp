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
	<title>预算管理空间编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script type="text/javascript">
	var oldDatas;
	function exitPage(){
	var newDatas =JSON.stringify($("#dataForm").jsonForm());
		if($.trim(oldDatas)!=$.trim(newDatas)){
			art.dialog({
				content:"数据已更改，是否确定退出？",
				lock: true,
				width: 200,
				height: 90,
				okValue:"是",
				cancelValue:"否",
				ok: function () {
					closeWin();
//					save();
				},
				cancel: function () {
				}
			});	
		}else{
			closeWin();
		}
	}
	$(function() {
		//放开铺贴类型的修改
/* 		if($.trim("${module }")!=""){
			$("#paveType").attr("disabled",true);
		} */
		$("#height1").val($.trim("${dMap.height1}"));
		$("#height2").val($.trim("${dMap.height2}"));
		$("#height3").val($.trim("${dMap.height3}"));
		$("#height4").val($.trim("${dMap.height4}"));
		$("#height5").val($.trim("${dMap.height5}"));
		$("#length1").val($.trim("${dMap.length1}"));
		$("#length2").val($.trim("${dMap.length2}"));
		$("#length3").val($.trim("${dMap.length3}"));
		$("#length4").val($.trim("${dMap.length4}"));
		$("#length5").val($.trim("${dMap.length5}"));
		
		$("#height6").val($.trim("${wMap.height6}"));
		$("#height7").val($.trim("${wMap.height7}"));
		$("#height8").val($.trim("${wMap.height8}"));
		$("#height9").val($.trim("${wMap.height9}"));
		$("#height10").val($.trim("${wMap.height10}"));
		$("#length6").val($.trim("${wMap.length6}"));
		$("#length7").val($.trim("${wMap.length7}"));
		$("#length8").val($.trim("${wMap.length8}"));
		$("#length9").val($.trim("${wMap.length9}"));
		$("#length10").val($.trim("${wMap.length10}"));
		
		$("#fixAreaType").val($.trim("${map.fixareatype[0]}"));
		changeType();
		$("#descr").val("${map.preplanareadescr[0]}");
		$("#area").val("${map.area[0]}");
		$("#perimeter").val("${map.perimeter[0]}");
		$("#height").val("${map.height[0]}");
		$("#paveType").val("${map.pavetype[0]}");
		$("#showerWidth").val("${map.showerwidth[0]}");
		$("#showerLength").val("${map.showerlength[0]}");
		$("#beamLength").val("${map.beamlength[0]}");
		$("#custCode").val("${map.custcode[0]}");
		$("#dispSeq").val("${map.dispseq[0]}");
		$("#pk").val("${map.pk[0]}");
		$("#fixAreaType").attr("disabled",true);
		oldDatas=JSON.stringify($("#dataForm").jsonForm());
		/* var gridOption = {
			url:"${ctx}/admin/itemPlan/getDoorWindList",
			postData:{pk:"${map.pk[0]}"},
			height:$(document).height()-$("#content-list").offset().top-55,
			rowNum:10000000,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "type", index: "type", width: 101, label: "类型", sortable: true, align: "left",hidden:true},
				{name: "typedescr", index: "typedescr", width: 101, label: "类型", sortable: true, align: "left"},
				{name: "length", index: "length", width: 91, label: "长度", sortable: true, align: "left"},
				{name: "height", index: "height", width: 90, label: "高度", sortable: true, align: "left"}
			],
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
		 */
		$("#addDoorWind").on("click",function(){
			
			Global.Dialog.showDialog("addDoorWind",{
				title:"门窗——新增",
				url:"${ctx}/admin/itemPlan/goAddDoorWind",
				height: 280,
				width:750,
			    returnFun:function(data){
					if(data){
						$.each(data,function(k,v){
							var json = {
								type:v.type,
								typedescr:$.trim(v.type)=="1"?"门":"窗",
								length:v.length,
								height:v.height,
							};
							Global.JqGrid.addRowData("dataTable",json);
							oldDatas="";
						});
					}
				} 
			});
		});
		
		$("#updateDoorWind").on("click",function(){
			var ret = selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
			var param =$("#dataTable").jqGrid("getRowData",rowId);
		
			Global.Dialog.showDialog("updateDoorWind",{
				title:"门窗——编辑",
				url:"${ctx}/admin/itemPlan/goUpdateDoorWind",
				postData:param,
				height: 280,
				width:750,
			    returnFun:function(data){
					if(data){
						$.each(data,function(k,v){
							var json = {
								type:v.type,
								typedescr:$.trim(v.type)=="1"?"门":"窗",
								length:v.length,
								height:v.height,
							};
			   				$('#dataTable').setRowData(rowId,json);
			   				oldDatas="";
						});
					}
				} 
			});
		});
		
		$("#delDetail").on("click",function(){
			oldDatas="";
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
				},
				cancel: function () {
					return true;
				}
			});
		});
		
	});
	 
	function save(){
		var datas = $("#dataForm").serialize();
		var param= Global.JqGrid.allToJson("dataTable");
		
		var fixAreaType = $.trim($("#fixAreaType").val());
			var paveType = $.trim($("#paveType").val());
			var beamLength = $.trim($("#beamLength").val());
			var showerLength = $.trim($("#showerLength").val());
			var showerWidth = $.trim($("#showerWidth").val());
			var dispSeq = $.trim($("#dispSeq").val());
			var height = $.trim($("#height").val());
			var perimeter = $.trim($("#perimeter").val());
			var area = $.trim($("#area").val());
			var descr = $.trim($("#descr").val());
			var fixAreaType = $.trim($("#fixAreaType").val());
			if(paveType==""){
				art.dialog({
					content:"铺贴类型必填",
				});
				return false;
			}
			if(fixAreaType=="3"){
				if(showerLength==""){
					art.dialog({
						content:"空间类型为卫生间时,淋浴房长必填",
					});
					return false;
				}
				if(showerWidth==""){
					art.dialog({
						content:"空间类型为卫生间时,淋浴房宽必填",
					});
					return false;
				}
			}
			if((fixAreaType=="6" || fixAreaType=="5" )&&beamLength==""){
				art.dialog({
					content:"空间类型为厨房时,包梁长度必填",
				});
				return false;
			}
			if(fixAreaType==""){
				art.dialog({
					content:"空间类型不能为空",
				});
				return false;
			}
			if(descr==""){
				art.dialog({
					content:"请填写空间名称",
				});
				return false;
			}
			if(area==""){
				art.dialog({
					content:"请填写面积",
				});
				return false;
			}
			if(perimeter==""){
				art.dialog({
					content:"请填写周长",
				});
				return false;
			}
			if(height==""){
				art.dialog({
					content:"请填写高度",
				});
				return false;
			}
			var Ids =$("#dataTable").getDataIDs();
			$("#saveBtn").attr("disabled",true);
			doSave();
	};
	function doSave(){
		var param= Global.JqGrid.allToJson("dataTable");
		var fixAreaType = $.trim($("#fixAreaType").val());
		var area = $.trim($("#area").val());
		var perimeter = $.trim($("#perimeter").val());
		var length1 = $.trim($("#length1").val());
		var length2 = $.trim($("#length2").val());
		var length3 = $.trim($("#length3").val());
		var length4 = $.trim($("#length4").val());
		var length5 = $.trim($("#length5").val());
		var length6 = $.trim($("#length6").val());
		var length7 = $.trim($("#length7").val());
		var length8 = $.trim($("#length8").val());
		var length9 = $.trim($("#length9").val());
		var length10 = $.trim($("#length10").val());
		var height1 = $.trim($("#height1").val());
		var height2 = $.trim($("#height2").val());
		var height3 = $.trim($("#height3").val());
		var height4 = $.trim($("#height4").val());
		var height5 = $.trim($("#height5").val());
		var height6 = $.trim($("#height6").val());
		var height7 = $.trim($("#height7").val());
		var height8 = $.trim($("#height8").val());
		var height9 = $.trim($("#height9").val());
		var height10 = $.trim($("#height10").val());
		var detailJson =[];
		if(area<=0){
			art.dialog({
				content:"面积必须大于0",
			})
			$("#saveBtn").removeAttr("disabled",true);
			return;
		}
		if(perimeter<=0){
			art.dialog({
				content:"周长必须大于0",
			})
			$("#saveBtn").removeAttr("disabled",true);
			return;
		}
		var height = $.trim($("#height").val());
		if(fixAreaType=="1"||fixAreaType=="7"){//2.6
			if(height<2.6){
				art.dialog({
					content:"高度必须大于2.6",				
				});
				$("#saveBtn").removeAttr("disabled",true);
				return;
			}
		}else if(fixAreaType=="3"||fixAreaType=="5"||fixAreaType=="6"){//2.4
			if(height<2.4){
				art.dialog({
					content:"高度必须大于2.4",				
				});
				$("#saveBtn").removeAttr("disabled",true);
				return;
			}
		}else if(fixAreaType=="2"||fixAreaType=="4"){//2.8
			if(height<2.8){
				art.dialog({
					content:"高度必须大于2.8",				
				});
				$("#saveBtn").removeAttr("disabled",true);
				return;
			}
		}else{
			if(height<0){
				art.dialog({
					content:"高度必须大于0",				
				});
				$("#saveBtn").removeAttr("disabled",true);
				return;
			}
		}
		
		if(fixAreaType=="2"||fixAreaType=="3"||fixAreaType=="5"){
			if((length1=="" || length1==0)&&(length2=="" || length2==0)&&(length3=="" || length3==0)
				&&(length4=="" || length4==0)&&(length5=="" || length5==0)){
				art.dialog({
					content:"没有录入门洞,不允许保存",
				});
				$("#saveBtn").removeAttr("disabled",true);
				return;
			}
			if((height1=="" || height1==0)&&(height2=="" || height2==0)&&(height3=="" || height3==0)
				&&(height4=="" || height4==0)&&(height5=="" || height5==0)){
				art.dialog({
					content:"没有录入门洞,不允许保存",
				});
				$("#saveBtn").removeAttr("disabled",true);
				return;
			}
		}
		
		detailJson.push({"pk":$.trim("${dMap.pk1}"),"length":length1,"height":height1,type:"1"});
		detailJson.push({"pk":$.trim("${dMap.pk2}"),"length":length2,"height":height2,type:"1"});
		detailJson.push({"pk":$.trim("${dMap.pk3}"),"length":length3,"height":height3,type:"1"});
		detailJson.push({"pk":$.trim("${dMap.pk4}"),"length":length4,"height":height4,type:"1"});
		detailJson.push({"pk":$.trim("${dMap.pk5}"),"length":length5,"height":height5,type:"1"});
		detailJson.push({"pk":$.trim("${wMap.pk6}"),"length":length6,"height":height6,type:"2"});
		detailJson.push({"pk":$.trim("${wMap.pk7}"),"length":length7,"height":height7,type:"2"});
		detailJson.push({"pk":$.trim("${wMap.pk8}"),"length":length8,"height":height8,type:"2"});
		detailJson.push({"pk":$.trim("${wMap.pk9}"),"length":length9,"height":height9,type:"2"});
		detailJson.push({"pk":$.trim("${wMap.pk10}"),"length":length10,"height":height10,type:"2"});		
		
		var param ;
			param.detailJson=JSON.stringify(detailJson),
		Global.Form.submit("dataForm","${ctx}/admin/itemPlan/doUpdatePrePlanArea",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content:ret.msg,
					time:1000,
					beforeunload:function(){
						closeWin();
					}
				});				
			}else{
				$("#saveBtn").removeAttr("disabled",true);
				$("#_form_token_uniq_id").val(ret.token.token);
				art.dialog({
					content:ret.msg,
					width:200
				});
			}
		});
	}
	
	function changeType(){
		var fixAreaType = $.trim($("#fixAreaType").val());
		if(fixAreaType=="1"||fixAreaType=="7"){//height 2.6
			$("#height").val(2.6);
			if(fixAreaType=="1"){
				$("#beamLength_li").css("display","none");
				$("#showerLength_li").css("display","none");
				$("#showerWidth_li").css("display","none");
			}else if(fixAreaType=="7"){
				$("#beamLength_li").css("display","none");
				$("#showerLength_li").css("display","none");
				$("#showerWidth_li").css("display","none");
			}
		}else if(fixAreaType=="3"||fixAreaType=="5"||fixAreaType=="6"){//height 2.4
			$("#height").val(2.4);
			if(fixAreaType=="6" || fixAreaType=="5" ){
				$("#beamLength_li").css("display","block");//显示
				$("#showerLength_li").css("display","none");
				$("#showerWidth_li").css("display","none");
			}else if(fixAreaType=="3"){
				$("#beamLength_li").css("display","none");
				$("#showerLength_li").css("display","block");//显示
				$("#showerWidth_li").css("display","block");//显示
			}else{
				$("#beamLength_li").css("display","none");
				$("#showerLength_li").css("display","none");
				$("#showerWidth_li").css("display","none");
			}
		}else if(fixAreaType=="2"||fixAreaType=="4"){//height 2.8
			$("#height").val(2.8);
			$("#beamLength_li").css("display","none");
			$("#showerLength_li").css("display","none");//显示
			$("#showerWidth_li").css("display","none");
		}else{
			$("#beamLength_li").css("display","none");
			$("#showerLength_li").css("display","none");//显示
			$("#showerWidth_li").css("display","none");
		}
		//clareText();
	}
	function clareText(){
		$("#paveType").val("1");
		$("#paveLength").val("");
		$("#showerLength").val("");
		$("#showerWidth").val("");
		$("#area").val("");
		$("#perimeter").val("");
		$("#descr").val("");
	}
	
	function chgHeight(e){
		if(e.value>3){
			art.dialog({
				content:"高度不允许超过3米",
				time:1000,
				beforeunload:function(){
					e.value=3;
				}
			});
		}
	}
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn" onclick="save()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="exitPage()">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="custCode" name="custCode" >
						<input type="hidden" id="dispSeq" name="dispSeq"/>
						<input type="hidden" id="module" name="module" value="${module }"/>
						<input type="hidden" id="pk" name="pk"/>
						<ul class="ul-form">
							<li class="form-validate">
								<label><span class="required">*</span>空间类型</label>
								<house:dict id="fixAreaType" dictCode="" onchange="changeType()" sqlValueKey="Code" sqlLableKey="Descr"
								sql="select code,code+' '+descr descr from tFixAreaType where expired ='F'"   ></house:dict>
							</li>
							<li class="form-validate">
								<label>空间名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;"/>
							</li>
							<li class="form-validate">
								<label>面积</label>
								<input type="text" id="area" name="area" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
							</li>
							<li class="form-validate">
								<label>周长</label>
								<input type="text" id="perimeter" name="perimeter" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
							</li>
							<li class="form-validate">
								<label>高度</label>
								<input type="text" id="height" name="height" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
							</li>
							<li id="paveType_li" class="form-validate">
								<label>铺贴类型</label>
								<house:xtdm  id="paveType" dictCode="PAVETYPE" style="width:160px;"></house:xtdm>
							</li>
							<li id="showerWidth_li" class="form-validate">
								<label>淋浴房宽</label>
								<input type="text" id="showerWidth" name="showerWidth" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
							</li>
							<li id="showerLength_li" class="form-validate">
								<label>淋浴房长</label>
								<input type="text" id="showerLength" name="showerLength" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
							</li>
							<li id="beamLength_li" class="form-validate">
								<label>包梁长度</label>
								<input type="text" id="beamLength" name="beamLength" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div class="panel-body">
				<form action="" method="post" id="page_from" class="form-search">
					<ul class="ul-form" id="jsp_ul">
						<div  style="width:48%;height:200px ;float:left; border:1px solid #cfcfcf ;margin-top: 15px; class="container"  >
							<span style="position:relative;top:-10px;left:-200px;background: white; margin-left:-158px">门洞</span>
							<li class="form-validate" style="margin-top:15px">
								<label style="width:55px">门洞1：长</label>
								<input type="text" id="length1" name="length1" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" />
							</li>
							<li class="form-validate" style="margin-top:15px">
								<label style="width:55px">高</label>
								<input type="text" id="height1" name="height1" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" onchange="chgHeight(this)"/>
							</li>
							<li class="form-validate">
								<label style="width:55px">门洞2：长</label>
								<input type="text" id="length2" name="length2" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" />
							</li>
							<li class="form-validate">
								<label style="width:55px">高</label>
								<input type="text" id="height2" name="height2" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" onchange="chgHeight(this)"/>
							</li>
							<li class="form-validate">
								<label style="width:55px">门洞3：长</label>
								<input type="text" id="length3" name="length3" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" />
							</li>
							<li class="form-validate">
								<label style="width:55px">高</label>
								<input type="text" id="height3" name="height3" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" onchange="chgHeight(this)"/>
							</li>
							<li class="form-validate">
								<label style="width:55px">门洞4：长</label>
								<input type="text" id="length4" name="length4" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" />
							</li>
							<li class="form-validate">
								<label style="width:55px">高</label>
								<input type="text" id="height4" name="height4" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" onchange="chgHeight(this)"/>
							</li>
							<li class="form-validate">
								<label style="width:55px">门洞5：长</label>
								<input type="text" id="length5" name="length5" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" />
							</li>
							<li class="form-validate">
								<label style="width:55px">高</label>
								<input type="text" id="height5" name="height5" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" onchange="chgHeight(this)"/>
							</li>
						</div>
						<div style="width:48%; height:200px ;border:1px solid #cfcfcf; float: right;margin-top: 15px; ">
							<span style="position:relative;top:-10px; text-align: center;background: white; margin-left:-355px;">窗洞</span>
							<li class="form-validate" style="margin-top:15px">
								<label style="width:55px">窗洞1：长</label>
								<input type="text" id="length6" name="length6" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" />
							</li>
							<li class="form-validate" style="margin-top:15px">
								<label style="width:55px">高</label>
								<input type="text" id="height6" name="height6" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" onchange="chgHeight(this)"/>
							</li>
							<li class="form-validate">
								<label style="width:55px">窗洞2：长</label>
								<input type="text" id="length7" name="length7" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" />
							</li>
							<li class="form-validate">
								<label style="width:55px">高</label>
								<input type="text" id="height7" name="height7" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" onchange="chgHeight(this)"/>
							</li>
							<li class="form-validate">
								<label style="width:55px">窗洞3：长</label>
								<input type="text" id="length8" name="length8" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" />
							</li>
							<li class="form-validate">
								<label style="width:55px">高</label>
								<input type="text" id="height8" name="height8" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" onchange="chgHeight(this)"/>
							</li>
							<li class="form-validate">
								<label style="width:55px">窗洞4：长</label>
								<input type="text" id="length9" name="length9" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" />
							</li>
							<li class="form-validate">
								<label style="width:55px">高</label>
								<input type="text" id="height9" name="height9" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" onchange="chgHeight(this)"/>
							</li>
							<li class="form-validate">
								<label style="width:55px">窗洞5：长</label>
								<input type="text" id="length10" name="length10" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" />
							</li>
							<li class="form-validate">
								<label style="width:55px">高</label>
								<input type="text" id="height10" name="height10" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:120px;" onchange="chgHeight(this)"/>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</body>	
</html>
