<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript"> 
$(function() {
	if("${item.m_umState}"!='M'){
		 $("#addBtn").attr("disabled",true);
		 $("#updateBtn").attr("disabled",true);
		 $("#delBtn").attr("disabled",true);
	}
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-200,	  
		colModel : [
			{name:'pk',	index:'pk',	width:90,	label:'PK',	sortable:true,align:"left",hidden: true},
			{name:'dispseq',	index:'dispseq',	width:90,	label:'显示顺序',	sortable:true,align:"left",hidden: true},
			{name:'picfile',	index:'picfile',	width:160,	label:'图片',	sortable:true,align:"left"},
			{name:'pictype',	index:'pictype',	width:100,	label:'图片类型',	sortable:true,align:"left",hidden: true},
			{name: 'lastupdate', index: 'lastupdate', width: 120, label: '上传图片时间', sortable: true, align: "left",formatter:formatTime},
		],
		beforeSelectRow:function(rowId, e){
			setTimeout(function(){
				relocate(rowId,"dataTable");
			},10);
		},	
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs");
			var code =$.trim($("#code").val());
			var url =" ";
			var docName = Global.JqGrid.allToJson("dataTable","picfile");
			var arr = new Array();
				arr = docName.fieldJson.split(",");
			var arry = new Array();
				arry = arr[id-1].split(".");
			if(arry[1]=="png"||arry[1]=='jpg'||arry[1]=='gif'||arry[1]=='jpeg'||arry[1]=='bmp'){//jpg/gif/jpeg/png/bmp.
				$.ajax({
					url:'${ctx}/admin/item/ajaxGetPicture',					
					type: 'post',
					data:{code:code,photoName:arr[id-1]},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});	
				    },
				    success: function(obj){
						if(obj){
							url=obj.data;
							document.getElementById("docPic").src =url;
						}	
				    }
				 }); 
				
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
		url:"${ctx}/admin/itemPic/goJqGrid",
		postData:{itemCode:'${item.code}'} ,
	});
	Global.JqGrid.initEditJqGrid("dataTable",gridOption);

	$("#addBtn").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("ItemPicAdd",{
			title:"材料图片——上传",
			url:"${ctx}/admin/item/goItemPicAdd",
			postData:{code:'${item.code }',descr:'${item.descr}'},
			height:650,
			width:950,
			returnFun:goto_query
		});
	});
	
	
	$("#delBtn").on("click",function(){
		var code=$.trim($("#code").val());
		var ret=selectDataTableRow();;
	    if(ret==""||ret==null){
			art.dialog({
				content:'请选择一条数据删除',
			});
			return false
		}
		art.dialog({
				 content:"是否确定删除",
				 lock: true,
				 width: 200,
				 height: 100,
				 ok: function () {
					$.ajax({
						url:'${ctx}/admin/item/doDeletePicture',					
						type: 'post',
						data:{code:code,photoName:ret.picfile,pk:ret.pk},
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
								$("#dataTable").jqGrid("setGridParam",{postData:{code:'${item.code }'},page:1,sortname:''}).trigger("reloadGrid");
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
	//设置主图片
	$("#updateBtn").on("click",function(){
		var code=$.trim($("#code").val());
		var ret=selectDataTableRow();
	    if(ret==""||ret==null){
			art.dialog({
				content:'请选择一条数据',
			});
			return false
		}else{
			if(ret.pictype=="1"){
				art.dialog({
					content:'已经是主图片'
				});
				return false
			}	
		}
		var rowData=$('#dataTable').jqGrid("getRowData");
		var mainPicPk=0;
		$.each(rowData,function(k,v){
			if(v.pictype=="1"){
				mainPicPk=v.pk;
			}
		});
		art.dialog({
				 content:"是否确定修改为主图片",
				 lock: true,
				 width: 200,
				 height: 100,
				 ok: function () {
					$.ajax({
						url:'${ctx}/admin/item/doUpdateItemPic',					
						type: 'post',
						data:{code:code,photoName:ret.picfile,pk:ret.pk,mainPicPk:mainPicPk},
						dataType: 'json',
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
					    },
					    success: function(obj){
							if(obj){
								art.dialog({
									content:'设置成功',
									time:500,
								});
								$("#dataTable").jqGrid("setGridParam",{postData:{code:'${item.code }'},page:1,sortname:''}).trigger("reloadGrid");
							}else{
								art.dialog({
									content:'设置失败',
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
	function goto_query(){
		$("#dataTable").jqGrid("setGridParam",{postData:{custCode:'${customer.code }',docType1:'2'},page:1,sortname:''}).trigger("reloadGrid");
	}
</script>

<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
	    <div class="panel-body" >
			<div class="btn-group-xs">
				<button type="button" class="btn btn-system " id="addBtn">
					<span>添加</span>
				</button>
				<button type="button" class="btn btn-system " id="updateBtn">
					<span>设为主图片</span>
				</button>
				<button type="button" class="btn btn-system " id="delBtn">
					<span>删除</span>
				</button>
			</div>
		</div>
		</div>
	</div>
	<div style="width:53%; float:left; margin-left:0px; ">
		<div class="body-box-form">
			<div class="container-fluid" style="whith:520px">
				<div id="content-list" style="height: 520px; width: 100%;">
					<table id="dataTable"></table>
				</div>
			</div>
		</div>
	</div>
	<div style="width:47%; float:right; margin-left:0px; ">
		<img id="docPic" src=" " onload="AutoResizeImage(520,440,'docPic');" width="520" height="440">
	</div>
</body>
