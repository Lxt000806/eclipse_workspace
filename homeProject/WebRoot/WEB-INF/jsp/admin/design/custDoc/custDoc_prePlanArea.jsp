<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>预算管理新增空间</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
      .SelectBG{
          background-color:#198ede!important;
            color:rgb(255,255,255,0.8)
            }
		.SelectRed{
          background-color:rgb(255,113,116)!important;
			color:rgb(255,255,255) 
	    }
      .SelectBG_yellow{
          background-color:yellow;
         }
 </style>
  </head>
<body>
	<div class="body-box-form">
		<div class="content-form">
  			<div class="panel panel-system" >
				<div class="panel-body" >
			    	<div class="btn-group-xs" >
			    		<button type="button" class="btn btn-system" id="saveBtn" onclick="save()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="exitPage()">
							<span>关闭</span>
						</button>
						<span style="color:red">&nbsp&nbsp修改空间信息后，需进行基础和主材预算重新生成。</span>
						<c:if test="${customer.status.trim()=='4' && module.trim()=='custDoc'}">
							<span style="color:red">&nbsp&nbsp该客户为合同施工状态，只能修改安装电梯和搬运楼层及空间明细。</span>
						</c:if>
					</div>
				</div>
			</div>
  			<!-- edit-form -->
  			<div class="panel panel-info" >  
				<div class="panel-body">
					<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
						<input type="hidden" name="m_umState" id="m_umState" value="A"/>
						<input type="hidden" name="module" id="module" value="${module }"/>
						<input type="hidden" name="jsonString" id="jsonString" value="" />
						<ul class="ul-form">
							<div class="validate-group row" >
								<li class="form-validate">
									<label>客户编号</label>
									<input type="text" id="code" name="code"style="width:160px;"/>                                             
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" readonly="true"/>
								</li>
								<li>
									<label>户型</label>
									<house:xtdm id="layOut" dictCode="LAYOUT"  style="width:160px;" value="${customer.layout }" disabled="true"></house:xtdm>
								</li>
								<li>
									<label>设计师</label>
									<input type="text" id="designMan" name="designMan" style="width:160px;" value="${customer.designMan}" readonly="true"/>
								</li>
								<li>
									<label>面积</label>
									<input type="text" id="area" name="area" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;" value="${customer.area }"/>
								</li>
								<li>
									<label>6分墙面积</label>
									<input type="text" id="wallArea6" name="wallArea6" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;" value="${customer.wallArea6 }" />
								</li>
								<li>
									<label>12分墙面积</label>
									<input type="text" id="wallArea12" name="wallArea12" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;" value="${customer.wallArea12 }" />
								</li>
								<li>
									<label>18分墙面积</label>
									<input type="text" id="wallArea18" name="wallArea18" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;" value="${customer.wallArea18 }" />
								</li>
								<li>
									<label>24分墙面积</label>
									<input type="text" id="wallArea24" name="wallArea24" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;" value="${customer.wallArea24 }" />
								</li>
								<li>
									<label>安装电梯</label>
									<house:xtdm id="installElev" dictCode="YESNO"  style="width:160px;" value="${customer.installElev }" onchange="changeInstallElev()"></house:xtdm>
								</li>
								<li>
									<label>搬楼层数</label>
									<input type="text" id="carryFloor" name="carryFloor" onkeyup="value=value.replace(/\D/g,'')" style="width:160px;" value="${customer.carryFloor }" />
								</li>
								<li>
									<label class="control-textarea">搬运说明</label> 
									<textarea id="carryRemark" name="carryRemark" style="height: 35px;" ${customer.status=='4'?'':'readonly'}>${customer.carryRemark }</textarea>
								</li>
							</div>
						</ul>
  					</form>
  				</div>
  			</div>
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="add" >
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system " id="batchAdd" >
						<span>批量新增</span>
					</button>
					<button type="button" class="btn btn-system " id="copy" >
						<span>复制空间</span>
					</button>
					<button type="button" class="btn btn-system " id="addUpdate" >
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system " id="del">
						<span>删除</span>
					</button>
					<button type="button" class="btn btn-system " id="addView">
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system " id="upward">
						<span>向上</span>
					</button>
					<button type="button" class="btn btn-system " id="downward">
						<span>向下</span>
					</button>
					<button type="button" class="btn btn-system " onclick="doExcelNow('空间管理')" title="导出当前excel数据" >
						<span>导出excel</span>
					</button>
				</div>
			</div>	
			<ul class="nav nav-tabs" >
	      		<li class="active"><a data-toggle="tab">空间明细</a></li>
			</ul>
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>	
  		</div>
  	</div> 
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	$("#tabs").tabs();
	var oldDatas;
	function exitPage(){
		$("#jsonString").val("");
		var newDatas =JSON.stringify($("#page_form").jsonForm());
		
		if($.trim(oldDatas)!=$.trim(newDatas)){
			art.dialog({
				content:"面积已修改,是否确定退出",
				lock: true,
				width: 200,
				height: 90,
				okValue:"是",
				cancelValue:"否",
				ok: function () {
					//save();
					closeWin();
				},
				cancel: function () {
					//console.log($(this));	
					//console.log($(this).id);	
					//closeWin();
				}
			});	
		}else{
			closeWin();
		}
	}
	
	function save(){
		$("#page_form").bootstrapValidator('validate');
		if(!$("#page_form").data('bootstrapValidator').isValid()) return;
		var wallArea6 = $.trim($("#wallArea6").val());
		var wallArea12 = $.trim($("#wallArea12").val());
		var wallArea24 = $.trim($("#wallArea24").val());
			$("#area").val(myRound($("#area").val(),0));
		if(wallArea6==""){
			$("#wallArea6").val(0);
		}
		if(wallArea12==""){
			$("#wallArea12").val(0);
		}
		if(wallArea24==""){
			$("#wallArea24").val(0);
		}
		var datas = $("#page_form").serialize();
		var param= Global.JqGrid.allToJson("dataTable");
		var perimeter = Global.JqGrid.allToJson("dataTable","perimeter");
			preArry = perimeter.fieldJson.split(",");	
		var area = Global.JqGrid.allToJson("dataTable","area");
			areaArry = area.fieldJson.split(",");
		var isdefaultarea = Global.JqGrid.allToJson("dataTable","isdefaultarea");
			defaulArry = isdefaultarea.fieldJson.split(",");	
		var notify="";	
		for(var i=0;i<preArry.length;i++){
			if(preArry[i]==0 && defaulArry[i]==0){
				notify="存在周长为0的空间，是否保存？";
				break;
			}
			if(areaArry[i]==0 && defaulArry[i]==0){
				notify="存在面积为0的空间，是否保存？";
				break;
			}
		}	
		var installElev=$("#installElev").val();
		if(installElev==""){
			art.dialog({
				content:"请选择是否安装电梯！",
			});
			return;
		}
		if($.trim(notify)!=""){
			art.dialog({
				content:notify,
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
					$("#saveBtn").attr("disabled",true);
					 $.ajax({
						url:"${ctx}/admin/itemPlan/doSaveCustArea",
						type: "post",
						data: datas,
						dataType: "json",
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
					    },
					    success: function(obj){
					    	if(obj.rs==true){
								art.dialog({
									content:obj.msg,
									time:500,
									beforeunload:function(){
										$("#saveBtn").removeAttr("disabled",true);
										closeWin();
									}
								});				
							}else{
								$("#_form_token_uniq_id").val(obj.token.token);
								$("#saveBtn").removeAttr("disabled",true);
								art.dialog({
									content:obj.msg,
									width:200
								});
							}
					    }
					});
				},
				cancel: function () {
					return true;
				}
			});
		}else{
			$("#saveBtn").attr("disabled",true);
			 $.ajax({
				url:"${ctx}/admin/itemPlan/doSaveCustArea",
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if(obj.rs==true){
						art.dialog({
							content:obj.msg,
							time:500,
							beforeunload:function(){
								$("#saveBtn").removeAttr("disabled",true);
								closeWin();
							}
						});				
					}else{
						$("#_form_token_uniq_id").val(obj.token.token);
						$("#saveBtn").removeAttr("disabled",true);
						art.dialog({
							content:obj.msg,
							width:200
						});
					}
			    }
			});
		}
	}
	
	function doExcel(){
		var url = "${ctx}/admin/itemPlan/doPrePlanAreaExcel";
		doExcelAll(url);
	}
	$(function(){
		var oldArea=0;
		var oldPerimeter=0;
		
		var titleCloseEle = parent.$("div[aria-describedby=dialog_itemPlan_prePlanArea]").children(".ui-dialog-titlebar");
   			$(titleCloseEle[0]).children("button").remove();

   			var childBtn=$(titleCloseEle).children("button");
   			$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
   										+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
   			$(titleCloseEle[0]).children("button").on("click", exitPage); 
	
	
		$("#code").openComponent_customer({showValue:"${customer.code}",showLabel:"${customer.descr}",readonly:true});
		$("#designMan").openComponent_employee({showValue:"${customer.designMan}",showLabel:"${designManDescr}",readonly:true});
		oldDatas =JSON.stringify($("#page_form").jsonForm());
		var lastCellRowId;
		var postData=$("#page_form").jsonForm();
	
		var gridOption = {
			url:"${ctx}/admin/itemPlan/getAddPlanAreaJqgrid",
			postData:{custCode:"${customer.code }"},
			height:$(document).height()-$("#content-list").offset().top-90,
			rowNum:10000000,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "p1pk", index: "p1pk", width: 94, label: "pk1", sortable: true, align: "left",hidden:true},
				{name: "p2pk", index: "p2pk", width: 94, label: "pk2", sortable: true, align: "left",hidden:true},
				{name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "isdefaultarea", index: "isdefaultarea", width: 94, label: "isdefaultarea", sortable: true, align: "left",hidden:true},
				{name: "fixareatype", index: "fixareatype", width: 94, label: "fixareatype", sortable: true, align: "left",hidden:true},
				{name: "custcode", index: "custcode", width: 94, label: "custcode", sortable: true, align: "left",hidden:true},
				{name: "pavetype", index: "pavetype", width: 80, label: "铺贴类型", sortable: true, align: "left",hidden:true },
				{name: "fixareatypedescr", index: "fixareatypedescr", width: 80, label: "空间类型", sortable: true, align: "left",},
				{name: "preplanareadescr", index: "preplanareadescr", width: 80, label: "空间名称", sortable: true, align: "left", },
				{name: "area", index: "area", width: 50, label: "面积", sortable: true, align: "right", sum:true,editable:true,editrules:{number:true}},
				{name: "oldarea", index: "oldarea", width: 50, label: "原面积", sortable: true, align: "right", sum:true,},
				{name: "perimeter", index: "perimeter", width: 50, label: "周长", sortable: true, align: "right", sum:true,editable:true,editrules:{number:true}},
				{name: "oldperimeter", index: "oldperimeter", width: 50, label: "原周长", sortable: true, align: "right", sum:true,},
				{name: "height", index: "height", width: 50, label: "高度", sortable: true, align: "right",editable:true,editrules:{number:true}},
				{name: "oldheight", index: "oldheight", width: 50, label: "原高度", sortable: true, align: "right", },
				{name: "length1", index: "length1", width: 50, label: "长", sortable: true, align: "right",editable:true ,editrules:{number:true}},
				{name: "oldlength1", index: "oldlength1", width: 50, label: "原长", sortable: true, align: "right",},
				{name: "height1", index: "height1", width: 50, label: "高", sortable: true, align: "right",editable:true ,editrules:{number:true}},
				{name: "oldheight1", index: "oldheight1", width: 50, label: "原高", sortable: true, align: "right",},
				{name: "length2", index: "length2", width: 50, label: "长", sortable: true, align: "right",editable:true ,editrules:{number:true}},
				{name: "oldlength2", index: "oldlength2", width: 50, label: "原长", sortable: true, align: "right",},
				{name: "height2", index: "height2", width: 50, label: "高", sortable: true, align: "right",editable:true ,editrules:{number:true}},
				{name: "oldheight2", index: "oldheight2", width: 50, label: "原高", sortable: true, align: "right",},
				{name: "pavetypedescr", index: "pavetypedescr", width: 70, label: "铺贴类型", sortable: true, align: "left", },
				{name: "beamlength", index: "beamlength", width: 70, label: "包梁长度", sortable: true, align: "right", sum:true,editable:true,editrules:{number:true}},
				{name: "showerlength", index: "showerlength", width: 70, label: "淋浴房长", sortable: true, align: "right", sum:true,editable:true,editrules:{number:true}},
				{name: "showerwidth", index: "showerwidth", width: 70, label: "淋浴房宽", sortable: true, align: "right", sum:true,editable:true,editrules:{number:true}},
				{name: "dispseq", index: "dispseq", width: 70, label: "显示顺序", sortable: true, align: "right", },
			],
			gridComplete:function(){
				changeColor();
			},
			onCellSelect: function(id,iCol,cellParam,e){
 				var rowData = $("#dataTable").jqGrid("getRowData",id);
 				oldArea=rowData.area;
 				oldPerimeter=rowData.perimeter;
 					
				var ids = $("#dataTable").jqGrid("getDataIDs");  
				for(var i=0;i<=ids.length;i++){
					$('#'+ids[i]).find("td").removeClass("SelectBG");
					Global.JqGrid.jqGridSelectAll("dataTable",false);
				}
				$('#'+ids[id-1]).find("td").addClass("SelectBG");
				changeColor(id);
			},
			beforeSaveCell:function(rowId,name,val,iRow,iCol){
				var ret=selectDataTableRow();
				if(name=="area"&&val<=0){
					if($.trim("${module}")=="custDoc" && val==0 ){
						var isUpdate=false;
						var postData="pk="+ret.pk+"&name="+name+"&val="+val;
						art.dialog({
							content:"修改后面积为0,是否保存?",
							lock: true,
							width: 100,
							height: 80,
							ok: function () {
								$.ajax({
									url:'${ctx}/admin/itemPlan/doEditAreaPer',
									type: "post",
									data:postData,
									dataType: "json",
									cache: false,
									error: function(obj){
										showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
								    },
								    success: function(obj){
										if(obj.rs){	
											isUpdate=true;
										}
								    }
								});
							},
							cancel: function () {
								$("#dataTable").jqGrid('setCell',rowId,name,oldArea);
								isUpdate=false
								return true;
							}
						});
						return;
					}else{
					console.log(7);
						return oldArea;
					}
				}
				if(name=="perimeter"&&val<=0){
				if($.trim("${module}")=="custDoc" && val==0 ){
						var postData="pk="+ret.pk+"&name="+name+"&val="+val;
						art.dialog({
							content:"修改后周长为0,是否保存?",
							lock: true,
							width: 100,
							height: 80,
							ok: function () {
								$.ajax({
									url:'${ctx}/admin/itemPlan/doEditAreaPer',
									type: "post",
									data:postData,
									dataType: "json",
									cache: false,
									error: function(obj){
										showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
								    },
								    success: function(obj){
										
								    }
								});
							},
							cancel: function () {
								$("#dataTable").jqGrid('setCell',rowId,name,oldPerimeter);
								return true;
							}
						});
						return;
					}else{
						return oldPerimeter;
					}
				}
				if(name=="beamlength"){
					name="beamLength";
				}
				if(name=="showerlength"){
					name="showerLength";
				}
				if(name=="showerwidth"){
					name="showerWidth";
				}
				var postData="pk="+ret.pk+"&p1pk="+ret.p1pk+"&p2pk="+ret.p2pk+"&"+name+"="+val+"&custCode="+"${customer.code}"+"&module="+"${module }";
				if($.trim(ret.isdefaultarea)!="1"){
					/*(name=="height" && $.trim(ret.fixareatype) == "6" && $.trim(ret.fixareatype)=="7") || name !="height"  */
					if((checkArea(ret.fixareatype,val) && name=="height")|| name !="height"){
						$.ajax({
							url:'${ctx}/admin/itemPlan/doEditArea',
							type: "post",
							data:postData,
							dataType: "json",
							cache: false,
							error: function(obj){
								showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
						    },
						    success: function(obj){
								if(""!=$.trim(obj.msg) && $.trim(obj.msg)!="保存成功"){
									$("#dataTable").jqGrid('setCell',rowId,(name=="height1"||name=="length1")?"p1pk":"p2pk",obj.msg);
								}
								if(!obj.rs){
									if("4"==$.trim(obj.msg)){
										$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
										art.dialog({
											content:"该客户为合同施工状态，不允许修改",
										});
										return;
									}
								}
						    }
						});
					}else{
						if($.trim(ret.fixareatype)=="1"||$.trim(ret.fixareatype)=="7"){
							return "2.6";
						}
						if( $.trim(ret.fixareatype) =="3" ||$.trim(ret.fixareatype)=="5"){
							return "2.4";
						}
						if($.trim(ret.fixareatype)=="4"||$.trim(ret.fixareatype)=="2"){
							return "2.8";
						}
						
					}
				}else{
					return "0";
				}
			},
		};
		Global.JqGrid.initEditJqGrid("dataTable",gridOption);
		$("#dataTable").jqGrid('setGroupHeaders', {
		  	useColSpanStyle: true, 
			groupHeaders:[{startColumnName: 'length1', numberOfColumns: 4, titleText: '门洞'},
							{startColumnName: 'length2', numberOfColumns: 4, titleText: '窗洞'},
							
			],
		});
		
		//新增
		$("#add").on("click",function(){
			var tempNo=$.trim($("#tempNo").val());
			var dispseq = Global.JqGrid.allToJson("dataTable","dispseq");
			var arr = new Array();
			arr = dispseq.fieldJson.split(",");
			var dispSeq=0;
			if (arr==""){
				dispSeq=1;
			}else{
				dispSeq=arr.length+1;
			}
			Global.Dialog.showDialog("add",{
				title:"空间明细——新增",
				url:"${ctx}/admin/itemPlan/goAddPlanArea",
				postData:{custCode:"${customer.code}",dispSeq:dispSeq,module:"${module }" },
				height: 580,
				width:1050,
			    returnFun:goto_query
			});
		});
		
		$("#copy").on("click",function(){
			var tempNo=$.trim($("#tempNo").val());
			var dispseq = Global.JqGrid.allToJson("dataTable","dispseq");
			var arr = new Array();
			arr = dispseq.fieldJson.split(",");
			var dispSeq=0;
			if (arr==""){
				dispSeq=1;
			}else{
				dispSeq=arr.length+1;
			}
			
			Global.Dialog.showDialog("add",{
				title:"空间明细——复制",
				url:"${ctx}/admin/itemPlan/goPlanAreaCopy",
				postData:{custCode:"${customer.code}",dispSeq:dispSeq },
				height: 580,
				width:1130,
			    returnFun:goto_query
			});
		});
		
		$("#batchAdd").on("click",function(){
			var dispseq = Global.JqGrid.allToJson("dataTable","dispseq");
			var arr = new Array();
			arr = dispseq.fieldJson.split(",");
			var dispSeq=0;
			if (arr==""){
				dispSeq=1;
			}else{
				dispSeq=arr.length+1;
			}
			Global.Dialog.showDialog("add",{
				title:"空间明细——新增",
				url:"${ctx}/admin/itemPlan/goBatchAddPlanArea",
				postData:{custCode:"${customer.code}",dispSeq:dispSeq ,module:"${module }"},
				height: 480,
				width:1050,
			    returnFun:goto_query
			});
		});
		
		$("#addUpdate").on("click",function(){
			var ret = selectDataTableRow();
			var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
			var param =$("#dataTable").jqGrid("getRowData",rowId);
			param.module="${module}";
			if(!ret){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			if(ret.isdefaultarea=="1"){
				art.dialog({
					content:"不能对固定项进行编辑",
				});
				return;
			}
			Global.Dialog.showDialog("add",{
				title:"空间明细——编辑",
				url:"${ctx}/admin/itemPlan/goUpdatePlanArea",
				postData:param,
				height: 580,
				width:1000,
			    returnFun:goto_query
			});
		});
		
		$("#addView").on("click",function(){
			var ret = selectDataTableRow();
			var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
			var param =$("#dataTable").jqGrid("getRowData",rowId);
			if(!ret){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			Global.Dialog.showDialog("add",{
				title:"空间明细——查看",
				url:"${ctx}/admin/itemPlan/goViewPlanArea",
				postData:param,
				height: 580,
				width:1000,
			    returnFun:goto_query
			});
		});
		
		$("#upward").on("click",function(){
			var ret = selectDataTableRow();
			if(!ret){
				return;
			}
			if($.trim(ret.isdefaultarea)=="1"){
				art.dialog({
					content:"不能对固定项进行向上操作",
				});
				
				return;
			}
			var rowIds =$("#dataTable").jqGrid('getDataIDs');
			var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
			if(rowId =="1"){
				return;
			}
			$("#upward").attr("disabled",true);
			var thisRow =$("#dataTable").jqGrid("getRowData",rowId);
			var targeRow =$("#dataTable").jqGrid("getRowData",parseInt(rowId)-1);

			$.ajax({
				url:"${ctx}/admin/itemPlan/doUpward",
				type: "post",
				data: {pk:ret.pk,module:"${module }"},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if(obj.rs){
						var ids = $("#dataTable").jqGrid("getDataIDs");  
						thisRow.dispseq=targeRow.dispseq;
						targeRow.dispseq=ret.dispreq;
						$("#dataTable").setRowData(rowId,targeRow);
						$("#dataTable").setRowData(parseInt(rowId)-1,thisRow);
						for(var i=0;i<=ids.length;i++){
								$('#'+ids[i]).find("td").removeClass("SelectBG");
						}
						Global.JqGrid.jqGridSelectAll("dataTable",false);
						
						$("#dataTable").setSelection(parseInt(rowId)-1);
			    	}
					$("#upward").removeAttr("disabled",true);
			    }
			});
			
		});
		
		$("#downward").on("click",function(){
			var ret = selectDataTableRow();
			if(!ret){
				return;
			}
			if(ret.isdefaultarea=="1"){
				art.dialog({
					content:"不能对固定项进行向下操作",
				});
				
				return;
			}
			var rowIds =$("#dataTable").jqGrid('getDataIDs');
			var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
			if(rowId>=rowIds.length){
				return;
			}
			$("#downward").attr("disabled",true);
			var thisRow =$("#dataTable").jqGrid("getRowData",rowId);
			var targeRow =$("#dataTable").jqGrid("getRowData",parseInt(rowId)+1);
			$.ajax({
				url:"${ctx}/admin/itemPlan/doDownward",
				type: "post",
				data: {pk:ret.pk,module:"${module }"},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if(obj.rs){
		    			var ids = $("#dataTable").jqGrid("getDataIDs");  
			    		thisRow.dispseq=targeRow.dispseq;
						targeRow.dispseq=ret.dispreq;
						$("#dataTable").setRowData(rowId,targeRow);
						$("#dataTable").setRowData(parseInt(rowId)+1,thisRow);
						for(var i=0;i<=ids.length;i++){
							$('#'+ids[i]).find("td").removeClass("SelectBG");
						}
						Global.JqGrid.jqGridSelectAll("dataTable",false);
						$("#dataTable").setSelection(parseInt(rowId)+1);
			    	}
					$("#downward").removeAttr("disabled",true);
			    }
			});
			
		});
		
		//删除
		$("#del").on("click",function(){
			var ret = selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条数据 进行删除",
				});
				return;
			}
			if($.trim(ret.isdefaultarea)=="1"){
				art.dialog({
					content:"不能对固定项进行删除操作",
				});
				return;
			}
			$.ajax({
				url:"${ctx}/admin/itemPlan/getDelNotify",
				type: "post",
				data: {pk:ret.pk,custCode:"${customer.code }"},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if(obj.rs){
						art.dialog({
							content:"是删除该记录？",
							lock: true,
							width: 100,
							height: 80,
							ok: function () {
								$.ajax({
									url:"${ctx}/admin/itemPlan/doDelPrePlanArea",
									type: "post",
									data: {pk:ret.pk,module:"${module }",custCode:"${customer.code }"},
									dataType: "json",
									cache: false,
									error: function(obj){
										showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
								    },
								    success: function(obj){
								    	if(obj.rs){
								    		art.dialog({
												content: "删除成功",
												time: 1000,
												beforeunload: function () {
													$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
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
								
							},
							cancel: function () {
								return true;
							}
						});
			    	}else{
			    		if($.trim(obj.msg)=="该客户为合同施工状态，删除失败"){
			    			art.dialog({
			    				content:"该客户为合同施工状态，删除失败",
			    			});
			    			return;
			    		}else{
				    		art.dialog({
								content:obj.msg,
								lock: true,
								width: 150,
								height: 80,
								ok: function () {
									$.ajax({
										url:"${ctx}/admin/itemPlan/doDelPrePlanArea",
										type: "post",
										data: {pk:ret.pk,module:"${module }",custCode:"${customer.code }"},
										dataType: "json",
										cache: false,
										error: function(obj){
											showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
									    },
									    success: function(obj){
									    	if(obj.rs){
									    		art.dialog({
													content: "删除成功",
													time: 1000,
													beforeunload: function () {
														$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
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
									
								},
								cancel: function () {
									return true;
								}
							});
			    		}
			    	}
			    }
			});
		});
		
		(function ctlBtn(){
			var status=$.trim("${customer.status }");
			if($.trim(status)!="1"&&$.trim(status)!="2"&&$.trim(status)!="3"){
				// $("#add").attr("disabled",true); modify by zb on 20190621
				$("#batchAdd").attr("disabled",true);
				//$("#addUpdate").attr("disabled",true);
				$("#del").attr("disabled",true);
				//$("#upward").attr("disabled",true);
				//$("#downward").attr("disabled",true);
				//$("#saveBtn").attr("disabled",true);
				$("#copy").attr("disabled",true);
				$("#area").attr("readonly",true);
				$("#wallArea6").attr("readonly",true);
				$("#wallArea12").attr("readonly",true);
				$("#wallArea18").attr("readonly",true);
				$("#wallArea24").attr("readonly",true);
			}
		})();
	});
function checkArea(fixAreaType,val){
	if(fixAreaType=="1"||fixAreaType=="7"){//2.6
		if(val<2.6){
			return false;
		}else{
			return true;
		}
	}else if(fixAreaType=="3"||fixAreaType=="5"||fixAreaType=="6"){//2.4
		if(val<2.4){
			return false;
		}else{
			return true;
		}
		
	}else if(fixAreaType=="2"||fixAreaType=="4"){//2.8
		if(val<2.8){
			return false;
		}else{
			return true;
		}
	}else{
		return true;
	}

}	
function changeInstallElev(){
	var installElev=$("#installElev").val();
	if(installElev=="1"){
		$("#carryFloor").val("0");
	}
}
function changeColor(id){
	var detailJson = Global.JqGrid.allToJson("dataTable");
	$.each(detailJson.datas, function (k, v) {
		if(id != k+1 &&"${customer.status}"=="4" &&(v.area!=v.oldarea || v.perimeter!=v.oldperimeter|| v.height!=v.oldheight|| 
						v.length1!=v.oldlength1|| v.height1!=v.oldheight1|| v.length2!=v.oldlength2 || v.height2!=v.oldheight2)){
						console.log(k);
			$('#'+(k+1)).find("td").addClass("SelectRed");
		}else{
			$('#'+(k+1)).find("td").removeClass("SelectRed");
		}
    });
}
	</script>
  </body>
</html>


