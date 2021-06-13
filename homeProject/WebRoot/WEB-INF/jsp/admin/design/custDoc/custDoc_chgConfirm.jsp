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
	<title>变更图纸审核</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
      .SelectBG{
          background-color:#198ede!important;
            color:rgb(255,255,255)
            }
      .SelectBG_yellow{
          background-color:yellow;
         }
	</style>
<script type="text/javascript"> 
function doExcel(){
	var url = "${ctx}/admin/custDoc/doChgConfirmExcel";
	doExcelAll(url,"dataTable","dataForm");
}

function chgIsPrePlanArea(){
	$("#isPrePlanAreaChg").val($("#isPrePlanAreaChg_sel").val());
}

function chgIsFullColorDraw(){
	$("#isFullColorDraw").val($("#isFullColorDraw_sel").val());
}
$(function() {
	if("${hasAuthByCzybh}" =="false"){
		$("#planDiffAnaly").hide();
	}
	
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-82,
		colModel : [
			{name:'PK',	index:'PK',	width:90,	label:'PK',	sortable:true,align:"left",hidden:true},
			{name:'DocType2',  index:'DocType2', width:90,   label:'DocType2', sortable:true,align:"left",hidden:true},
			{name:'CustCode',	index:'CustCode',	width:90,	label:'CustCode',	sortable:true,align:"left",hidden:true},
			{name:'address',	index:'address',	width:120,	label:'楼盘',	sortable:true,align:"left",},
			{name:'Descr',	index:'Descr',	width:70,	label:'资料名称',	sortable:true,align:"left",},
			{name:'DocName',	index:'DocName',	width:70,	label:'资料名称',	sortable:true,align:"left",hidden:true},
			{name:'typedescr',	index:'typedescr',	width:70,	label:'资料类型',	sortable:true,align:"left" ,},
			{name:'Status',	index:'Status',	width:120,	label:'状态',	sortable:true,align:"left" ,hidden:true},
			{name:'statusdescr',	index:'statusdescr',	width:60,	label:'状态',	sortable:true,align:"left" ,},
			{name:'custtypedescr',	index:'custtypedescr',	width:80,	label:'客户类型',	sortable:true,align:"left" },
			{name:'ConfirmRemark',	index:'ConfirmRemark',	width:200,	label:'变更审核说明',	sortable:true,align:"left" ,},
			{name:'confirmremark2',	index:'confirmremark2',	width:200,	label:'图纸审核说明',	sortable:true,align:"left" ,},
			{name:'confirmczydescr',	index:'confirmczydescr',	width:70,	label:'审核人员',	sortable:true,align:"left" ,},
			{name:'ConfirmDate',	index:'ConfirmDate',	width:110,	label:'审核时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'uploaddescr',	index:'uploaddescr',	width:70,	label:'上传人员',	sortable:true,align:"left" ,},
			{name:'UploadDate',	index:'UploadDate',	width:110,	label:'上传时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'LastUpdate',	index:'LastUpdate',	width:95,	label:'最后修改时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'LastUpdatedBy',	index:'LastUpdatedBy',	width:90,	label:'最后修改人员',	sortable:true,align:"left" ,hidden:true},
			{name:'Remark',	index:'Remark',	width:175,	label:'备注',	sortable:true,align:"left" ,},
			{name:'updatedescr',	index:'updatedescr',	width:95,	label:'最后修改人员',	sortable:true,align:"left" ,},
			{name:'isfullcolordraw', index:'isfullcolordraw', width:95,	label:'效果图类型', sortable:true, align:"left" ,hidden:true},
			{name:'drawqty', index:'drawqty', width:95,	label:'效果图数量',	 sortable:true, align:"right", hidden: true},
			{name:'draw3dqty', index:'draw3dqty', width:95,	label:'3d效果图数量',	 sortable:true, align:"right", hidden: true},
		],
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs"); 
			var rowData = $("#dataTable").jqGrid('getRowData',id) 
			var url =$.trim($("#url").val());
			var docName = Global.JqGrid.allToJson("dataTable","DocName");
			var arr = new Array();
				arr = docName.fieldJson.split(",");
			var arry = new Array();
				arry = arr[id-1].split(".");
			if(arry[1]=="png"||arry[1]=='jpg'||arry[1]=='gif'||arry[1]=='jpeg'||arry[1]=='bmp'){//jpg/gif/jpeg/png/bmp.
				document.getElementById("docPic").src =url+rowData.CustCode+"/"+arr[id-1];	
			}else{
				document.getElementById("docPic").src ="";	
			}
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$('#'+ids[i]).find("td").removeClass("SelectBG");
				}
			}
			$('#'+ids[id-1]).find("td").addClass("SelectBG");
		},
	}
	
	$.extend(gridOption,{
        url:"${ctx}/admin/custDoc/goDocJqGrid",
        postData:{status:"2",type:"2"},
    });
    Global.JqGrid.initEditJqGrid("dataTable",gridOption);
	
	$("#confirmPass").on("click",function(){
		var rowData= selectDataTableRow('dataTable');
		if(!rowData){
			art.dialog({
				content:"请选择一条数据进行操作",
			});
			return;
		}
		if("2"!=rowData.Status){
			art.dialog({
				content:"待审核状态才能进行审核通过操作",
			});
			return;
		}
		
		var contents = ''
		
		switch (rowData.DocType2) {
		  case '2':
		    contents = "<div>" +
		    			"<div class='row'>"+
		               "  <label style='width:126px;font-weight:normal'>效果图类型</label>" +
		               $("#isFullColorDraw").prop("outerHTML")
		               .replace('isFullColorDraw', 'isFullColorDraw_sel')
		               .replace('onmousedown=\"javascript:return false;\"','')
		               .replace('value=\"'+rowData.isfullcolordraw+'\"','value=\"'+rowData.isfullcolordraw+'\" selected ')+
                       "  </div><div class='row "+(rowData.isfullcolordraw=="0" ||rowData.isfullcolordraw=="3" ?"":"hidden") +"'>" +
			           "  <label style='width:120px;font-weight:normal;'>普通效果图数量</label>" +
			           "  <input style='width:120px;height:22px;margin-top:10px;border-radius:5px' type='text' id='drawQty_sel' value='" + rowData.drawqty + "'></input></div>"+
			           "<div class='row "+(rowData.isfullcolordraw=="1" ||rowData.isfullcolordraw=="3" ?"":"hidden") +"'>" +
			           "  <label style='width:120px;font-weight:normal;'>3D效果图数量</label>" +
			           "  <input style='width:120px;height:22px;margin-top:10px;border-radius:5px' type='text' id='draw3dQty_sel' value='" + rowData.draw3dqty + "'></input></div>" +
			           "</div>"
		    break
		  case '6':
		    contents = "<div style='margin-top:-15px'>" +
                       "  <label style='width:100px;font-family:宋体,Verdana, Arial, Helvetica, AppleGothic, sans-serif;padding-right:10px'>空间信息变动</label>" +
                       "  <select style='width:120px;border-radius:5px' id='isPrePlanAreaChg_sel'>" +
                       "    <option value=''>请选择...</option>" +
                       "    <option value='0'>否</option>" +
                       "    <option value='1'>是</option>" +
                       "  <select>" +
                       "</div>"
		    break
		  default:
		}
		

		art.dialog({
			content:contents,
			lock: true,
			padding:0,
			width: 400,
			height: 120,
			ok: function () {
			    var drawQty = $("#drawQty_sel").val()
	            var draw3dQty = $("#draw3dQty_sel").val()
	            if (rowData.DocType2 === '2' && !/^\d+$/.test(drawQty)) {
	                art.dialog({content:"请填写正确的普通效果图数量！"})
	                return false
	            }
				if (rowData.DocType2 === '2' && !/^\d+$/.test(draw3dQty)) {
	                art.dialog({content:"请填写正确的3D效果图数量！"})
	                return false
	            }
				$.ajax({
					url:'${ctx}/admin/custDoc/doConfirmPass',
					type: 'post',
					data: {
					    pk: rowData.PK,
					    status: "4",
					    custCode: rowData.CustCode,
						isPrePlanAreaChg: $("#isPrePlanAreaChg_sel").val(),
						isFullColorDraw: $("#isFullColorDraw_sel").val(),
						drawQty: drawQty,
						draw3DQty: draw3dQty
					},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
						art.dialog({
							content:"审核成功",
							time:500,
							beforeunload:function(){
								$("#dataTable").jqGrid("setGridParam", {
								    postData:{custCode:$("#code").val(),
								    status:$("#status").val(),type:"2",
								    address:$.trim($("#address").val())
								}, page:1,sortname:''}).trigger("reloadGrid");
							}
						});
					}	
				});
			},
			cancel: function () {
					return true;
			}
		});
	});
	
	$("#confirmBack").on("click",function(){
		var rowData= selectDataTableRow('dataTable');
		if(!rowData){
			art.dialog({
				content:"请选择一条数据进行操作",
			});
			return;
		}
		if("2"!=rowData.Status){
			art.dialog({
				content:"待审核状态才能进行审核通过操作",
			});
			return;
		}
		art.dialog({
   			title: "请输入退回原因",
   			padding: "0",
   			width: "300px",
   			height: "100px",
   			content: "<textarea id=\"confirmRemarkDialog\" style=\"width:300px;height:100px\">"+$("#confirmRemark").val()+"</textarea>",
   			lock: true,
   			ok: function(){
   				$("#confirmRemark").val($("#confirmRemarkDialog").val());
   				var datas = $("#dataForm").serialize();
		  		$.ajax({
					url:'${ctx}/admin/custDoc/doConfirmBack',
					type: 'post',
					data: {
					    pk:rowData.PK,
					    status:"3",
					    confirmRemark:$("#confirmRemark").val()
					},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
						art.dialog({
							content:"审核成功",
							time:500,
							beforeunload:function(){
								$("#confirmRemark").val("");
								$("#dataTable").jqGrid("setGridParam",{postData:{custCode:$("#code").val(),status:$("#status").val(),type:"2",address:$.trim($("#address").val())},
										page:1,sortname:''}).trigger("reloadGrid");
							}
						});
					}	
				});
   			},
   			cancel: function(){}
   		});
	});
	
	$("#confirmCancel").on("click", function() {
	    var rowData = selectDataTableRow('dataTable');
	    if (!rowData) {
	        art.dialog({content: "请选择一条数据进行操作"});
	        return;
	    }
	    if ("2" !== rowData.Status) {
	        art.dialog({content: "待审核状态才能进行审核取消操作"});
	        return;
	    }
	    art.dialog({
	        title: "请输入取消原因",
	        padding: "0",
	        width: "300px",
	        height: "100px",
	        content: '<textarea id="confirmCancelRemark" style="width:300px;height:100px"></textarea>',
	        lock: true,
	        ok: function() {
	            $.ajax({
	                url: '${ctx}/admin/custDoc/doConfirmCancel',
	                type: 'post',
	                data: {
	                    pk: rowData.PK,
	                    confirmRemark: $("#confirmCancelRemark").val()
	                },
	                dataType: 'json',
	                cache: false,
	                error: function(obj) {
	                    showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	                },
	                success: function(obj) {
	                    if (obj.rs) {
		                    art.dialog({
		                        content: "审核取消成功",
		                        time: 1000,
		                        beforeunload: function() {
		                            $("#dataTable").jqGrid("setGridParam", {
		                                postData: {
		                                    custCode: $("#code").val(),
		                                    status: $("#status").val(),
		                                    type: "2",
		                                    address: $.trim($("#address").val())
		                                },
		                                page: 1, sortname: ''
		                            }).trigger("reloadGrid");
		                        }
		                    });
		                } else {
		                    art.dialog({content: obj.msg})
		                }
	                }
	            });
	        },
	        cancel: function() {
	        }
	    });
	});
	
	$("#del").on("click",function(){
        var id = $("#dataTable").jqGrid("getGridParam", "selrow");
        var docName = Global.JqGrid.allToJson("dataTable","DocName");
        var arr = new Array();
            arr = docName.fieldJson.split(",");
        var ret=selectDataTableRow();
        var lastUpdatedBy=$.trim("${customer.lastUpdatedBy}");
        var hasAuthByCzybh=$.trim("${hasAuthByCzybh}");
        var updatedescr=$.trim(ret.UploadCZY);
        if(updatedescr!=lastUpdatedBy&&hasAuthByCzybh!='true'){
            art.dialog({
                content:'只能删除本人上传的图片',
            });
            return false;
        }
        if(!ret){
            art.dialog({
                content:"请选择一条数据进行删除",
            });
            return;
        }
        if($.trim(ret.Status)=="2"||$.trim(ret.Status)=="4"){
            art.dialog({
                content:'该图片为待审核或已审核状态，不允许删除！',
            });
            return false;
        }
        
        var path="D:/homePhoto/custDoc/"+ret.CustCode+"/"+arr[id-1];

        art.dialog({
            content:"是否确定删除",
            lock: true,
            width: 200,
            height: 100,
            ok: function () {
                $.ajax({
                    url:'${ctx}/admin/custDoc/doDeleteDoc',
                    type: 'post',
                    data:{custCode:ret.CustCode,path:path,docName:ret.DocName},
                    dataType: 'json',
                    cache: false,
                    error: function(obj){
                        showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
                    },
                    success: function(obj){
                        if(obj){
                            art.dialog({
                                content:'删除成功',
                                time:500,
                            });
                            $("#dataTable").jqGrid("setGridParam",{postData:{custCode:$.trim($("#code").val()),docType1:'2'},page:1,sortname:''}).trigger("reloadGrid");
                        }else{
                            art.dialog({
                                content:'操作失败',
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
	
	$("#planDiffAnaly").on("click",function(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("PlanDiffAnaly",{
			title:"项目资料管理——预算差异分析",
			url:"${ctx}/admin/custDoc/goPlanDiffAnaly",
			postData:{custCode:ret.CustCode},
			height:700,
			width:1250,
			returnFun:goto_query,
		});
	});
	
	$("#chgFinish").on("click",function(){
		var rowData= selectDataTableRow('dataTable');
		if(!rowData){
			art.dialog({
				content:"请选择一条数据进行操作",
			});
			return;
		}
		if("4"!=rowData.Status){
			art.dialog({
				content:"只有已审核状态才能做增减完成操作",
			});
			return;
		}
		art.dialog({
   			width: "200px",
   			height: "100px",
   			content: "是否确定做增减完成操作？",
   			lock: true,
   			ok: function(){
   				$.ajax({
					url:'${ctx}/admin/custDoc/doFinishChg',
					type: 'post',
					data:{pk:rowData.PK,status:"5"},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
						art.dialog({
							content:"审核成功",
							time:500,
							beforeunload:function(){
								$("#dataTable").jqGrid("setGridParam",{postData:{custCode:$("#code").val(),status:$("#status").val(),type:"2",address:$.trim($("#address").val())},
										page:1,sortname:''}).trigger("reloadGrid");
							}
						});
					}	
				});
   			},
   			cancel: function(){}
   		});
	});
	
	$("#download").on("click",function(){
		ret = selectDataTableRow("dataTable");
		if(!ret){
			art.dialog({
				content:"请选择下载的资料"
			});
			return;
		}
		window.open("${ctx}/admin/custDoc/download?docNameArr="+ret.DocName+"&docType2Descr="+'变更图纸'+"&custCode="+ret.CustCode);
	});
	
	$("#editArea").on("click",function(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("EditArea",{
			title:"项目资料管理——空间管理",
			url:"${ctx}/admin/custDoc/goPrePlanArea",
			postData:{custCode:ret.CustCode,module:"custDoc"},
			height:700,
			width:1230,
			returnFun:goto_query,
		});
	});
});
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{
		postData:{
			custCode:$("#code").val(),
			isPrePlanAreaChg:$("#isPrePlanAreaChg").val(),
			status:$("#status").val(),
			address:$.trim($("#address").val()),
			type:"2",
			confirmDateFrom:$("#confirmDateFrom").val(),
			confirmDateTo:$("#confirmDateTo").val()
		},page:1,sortname:''
	}).trigger("reloadGrid");
}

function updateDrawQty() {
	var isFullColorDraw = $("#isFullColorDraw_sel").val();
    if (isFullColorDraw == '0') {
    	$("#draw3dQty_sel").val(0);
    	$("#drawQty_sel").val('');
        $("#draw3dQty_sel").parent().addClass("hidden");
        $("#drawQty_sel").parent().removeClass("hidden");
    }else if(isFullColorDraw == '1'){
    	$("#drawQty_sel").val(0);
    	$("#draw3dQty_sel").val('');
        $("#drawQty_sel").parent().addClass("hidden");
        $("#draw3dQty_sel").parent().removeClass("hidden");
    }else if(isFullColorDraw == '2' || isFullColorDraw ==''){
    	$("#draw3dQty_sel").val(0);
        $("#draw3dQty_sel").parent().addClass("hidden");
        $("#drawQty_sel").val(0);
        $("#drawQty_sel").parent().addClass("hidden");
    }else if(isFullColorDraw == '3'){
    	 $("#drawQty_sel").val('');
    	 $("#draw3dQty_sel").val('');
         $("#draw3dQty_sel").parent().removeClass("hidden");
         $("#drawQty_sel").parent().removeClass("hidden");
    }
}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="url" name="url" value="${url}" />
					<input type="hidden" id="confirmRemark" name="confirmRemark" value="" />
					<input type="hidden" id="code" name="code" value="" />
					<input type="hidden" id="type" name="type" value="2" />
					<ul class="ul-form">
						<li >
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;"/>
						</li>
						<li>
							<label>状态</label>
							<house:DictMulitSelect id="status" dictCode=""
									sql="select IBM Code,note Descr from txtdm where id ='PICPRGSTS'
										 and cbm in ('1','2','3','4','5','6')  " 
							sqlValueKey="Code" sqlLableKey="Descr" selectedValue="2"></house:DictMulitSelect>
						</li>
						<li>
							<label>审核日期从</label>
							<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>空间信息变动</label>
							<house:xtdm id="isPrePlanAreaChg" dictCode="YESNO"  style="width:160px;"></house:xtdm>
						</li>
						<li class="search-group-shrink" >
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						</li>
						<li hidden >
							<house:xtdm style="width:120px;height:22px;border-radius:5px" id="isFullColorDraw" dictCode='DRAWTYPE' value=""
								    onchange="updateDrawQty()"></house:xtdm>
						</li>
					</ul>	
				</form>
			</div>
			<div class="btn-panel" >
    			<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="confirmPass">
						<span>审核通过</span>
					</button>
					<button type="button" class="btn btn-system " id="confirmBack">
						<span>审核退回</span>
					</button>
					<button type="button" class="btn btn-system " id="confirmCancel">
						<span>审核取消</span>
					</button>
					<button type="button" class="btn btn-system " id="del">
                        <span>删除</span>
                    </button>
					<button type="button" class="btn btn-system " id="download">
						<span>下载</span>
					</button>
					<button type="button" class="btn btn-system " id="chgFinish">
						<span>增减完成</span>
					</button>
					<button type="button" class="btn btn-system " id="planDiffAnaly">
						<span>预算差异分析</span> 
					</button>
					<button type="button" class="btn btn-system " id="editArea">
						<span>空间管理</span>
					</button>
					<button type="button" class="btn btn-system " onclick="doExcel()" title="导出当前excel数据" >
						<span>导出excel</span>
					</button>
				</div>
			</div>
		</div>
		<div class="body-box-form" >
			<div class="container-fluid" style="whith:800px">  
				<div id="content-list" style="whith:800px">
					<table id= "dataTable"></table>
					<div id= "dataTablePager"></div>
				</div>	
			</div>
		</div>
		<div hidden="true" style="width:46.5%; float:right; margin-left:0px; ">
			<img id="docPic" src=" " onload="AutoResizeImage(500,500,'docPic');" width="521" height="510" >  
		</div>
	</body>	
</html>
