<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>整改验收确认</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	$("#confirmCZY").openComponent_employee({showValue:'${prjProg.confirmCZY}',showLabel:'${prjProg.confirmCZYDescr}' ,readonly:true});

	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjProgPhoto/goJqGrid",
		postData:{CustCode:'${prjProg.custCode }',Type:'1',PrjItem:'${prjProg.prjItem}'},
		height:$(document).height()-$("#content-list").offset().top-82,
		colModel : [
				{name: "PK", index: "PK", width: 175, label: "pk", sortable: true, align: "left",hidden:true},
				{name:'prjitemdescr',index:'prjitemdescr',width:175, label:'施工项目', sortable:true ,align:'left'},
				{name: "PhotoName", index: "PhotoName", width: 175, label: "图片名称", sortable: true, align: "left",},
				{name: "LastUpdate", index: "LastUpdate", width: 175, label: "上传图片时间", sortable: true, align: "left",formatter:formatTime},
			],
	});


    //验收通过     
	$("#pass").on("click",function(){
		var datas = $("#page_form").serialize();
		$.ajax({
			url:'${ctx}/admin/prjProg/doCheck',
			type: 'post',
			data: datas,
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
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
	});
	
	//退回重拍
	$("#return").on("click",function(){
		if(!$("#page_form").valid()) {return false;}//表单校验
		//if($("#infoBoxDiv").css("display")!='none'){return false;}
		var datas = $("#page_form").serialize();
			$.ajax({
				url:'${ctx}/admin/prjProg/doReturn',
				type: 'post',
				data: datas,
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
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
		});
	
	/*  $("#picture").on("click",function(){
    	var ret = selectDataTableRow();
		if(ret){
		Global.Dialog.showDialog("goPicture",{
			title:"查看图片",
			url:"${ctx}/admin/prjProg/goPicture",
			postData:{custCode:"${prjProg.custCode }",prjItem:"${prjProg.prjItem}",photoName:ret.PhotoName},
			height:550,
			width:800,
			returnFun:goto_query
		});
		};
	}); */    
		
		
	$("#picture").val("");
	$("#uploadify").uploadify({
		　'swf': '${resourceRoot}/uploadify/uploadify.swf', 
		  'uploader':'${ctx}/admin/prjProg/uploadPrjProgCheck;jsessionid=<%=session.getId()%>',                  
		　'queueID' : 'fileQueue', //和存放队列的DIV的id一致  
		　//'fileDataName': 'fileupload', //必须，和以下input的name属性一致                   
		　'auto'  : false, //是否自动开始  
		　'multi': true, //是否支持多文件上传  
		  'buttonText': '选择图片', //按钮上的文字  
		  'rollover':true,
		  'width':108,
		  'height':25, 
		  'wmode' :'transparent',
		  'cancelImg' : '${resourceRoot}/uploadify/uploadify-cancel.png',
		　'simUploadLimit' : 2, //一次同步上传的文件数目  
		　//'sizeLimit': 19871202, //设置单个文件大小限制，单位为byte  
		　'fileSizeLimit' : '6000KB',
		  'queueSizeLimit' : 10, //队列中同时存在的文件个数限制  
		　'fileTypeExts': '*.jpg;*.gif;*.jpeg;*.png;*.bmp',//允许的格式
		  'fileTypeDesc': '支持格式:jpg/gif/jpeg/png/bmp.', //如果配置了以下的'fileExt'属性，那么这个属性是必须的  
		  'onUploadSuccess':function(event, response, data) {
			  var json = eval("("+response+")");
			  $("#photoName").val(event.name)
			  $("#picture").val($("#picture").val()+json.path+";");
				var photoName = $.trim($("#photoName").val());
		  	 
		  	 $.ajax({
				url:"${ctx}/admin/prjProg/ajaxSavePhotoName",
				type:'post',
				data:{photoName:photoName,custCode:'${prjProg.custCode}',prjItem:'${prjProg.prjItem}'},
				dataType:'json',
				cache:false,
				error:function(object){
					showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
				},
				success:function(object){
					art.dialog({
						content:"上传成功",
					});
					$("#dataTable").jqGrid("setGridParam",{postData:{CustCode:'${prjProg.custCode }',Type:'1',PrjItem:'${prjProg.prjItem}'}}).trigger("reloadGrid");
					$("#dataTable_picture").jqGrid("setGridParam",{postData:{CustCode:'${prjProg.custCode }',Type:'1',PrjItem:'${prjProg.prjItem}'}}).trigger("reloadGrid");
					
				}
			}); 

		  }
	　});
		var sub = document.getElementById("SWFUpload_0");
	sub.style.cssText = "position:absolute;left:1px;";
	
});
function deleteImg() {
	$("tbody tr:eq(14) td:eq(1) img").remove();
	$("#picture").val("");
}


</script>
</head>
<body>
		<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="pass">
						<span>验收通过</span>
					</button>
					<button type="button" class="btn btn-system " id="return">
						<span>退回重拍</span>
					</button>
				<li style="float:right;"></li>
				<li style="float:right;"></li>
					<button style="float:right;" type="button" class="btn btn-system " id="del">
						<span>删除</span>
					</button>
				<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
				<li style="float:right;"> 
						<span>当前第：</span>
						<input type="text" id="nowNo" name="nowNo" style="width:20px; outline:none; background:transparent; 
							border:none" value="${prjProg.nowNo }" readonly="true"/>张,				
				</li>
				<li style="float:right;"> 
					<span>总共：</span>
						<input type="text" id="allNo" name="allNo" style="width:20px; outline:none; background:transparent; 
							border:none" value="${prjProg.allNo }" readonly="true"/>张,				
				</li>
					<button type="button"  style="float:right;" class="btn btn-system " id="pre">
						<span>上一张</span>
					</button>
					<button type="button"  style="float:right;"class="btn btn-system " id="next">
						<span>下一张</span>
					</button>
			</div>
			</div>
	</div>
		<div  style="width:40%;float:left ;margin-left: 1px; class="container"  >
			  	<div class="panel panel-info" >  
         <div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
			  <input type="hidden" name="m_umState" id="m_umState" value="A"/>
						<ul class="ul-form">
							<li hidden="true">
								<label>pk</label>
								<input type="text" id="pk" name="pk" style="width:160px;"  value="${prjProg.pk }" readonly="readonly"/>                                             
							</li>								
							<li hidden="true">
								<label>prjDescr</label>
								<input type="text" id="prjDescr" name="prjDescr" style="width:160px;"  value="${prjProg.prjDescr }" readonly="readonly"/>                                             
							</li>								
							<li hidden="true">
								<label>projectMan</label>
								<input type="text" id="projectMan" name="projectMan" style="width:160px;"  value="${prjProg.projectMan }" readonly="readonly"/>                                             
							</li>								
							<li hidden="true">
								<label>custCode</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${prjProg.custCode }" readonly="readonly"/>                                             
							</li>								
							<li>
								<label>地址</label>
								<input type="text" id="address" name="address" style="width:160px;"  value="${prjProg.address }" readonly="readonly"/>                                             
							</li>								
							<li>
								<label>施工项目</label>
								<house:xtdm id="prjItem" dictCode="PRJITEM"  value="${prjProg.prjItem}" disabled="true"></house:xtdm>
							</li>								
							<li>
								<label>计划开始日期</label>
								<input type="text" id="planBegin" name="planBegin" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProg.planBegin}' pattern='yyyy-MM-dd'/>" disabled="disabled"/>
							</li>								
							<li>
								<label>实际开始日期</label>
								<input type="text" id="beginDate" name="beginDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProg.beginDate}' pattern='yyyy-MM-dd'/>" disabled="disabled"/>
							</li>								
							<li>
								<label>计划结束日期</label>
								<input type="text" id="planEnd" name="planEnd" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProg.planEnd}' pattern='yyyy-MM-dd'/>" disabled="disabled"/>
							</li>								
							<li>
								<label>实际结束日期</label>
								<input type="text" id="endDate" name="endDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProg.endDate}' pattern='yyyy-MM-dd'/>" disabled="disabled"/>
							</li>								
							<li>
								<label>图片最后上传日期</label>
								<input type="text" id="photoLastUpDate" name="photoLastUpDate"  style="width:160px" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',timeFmt:'yyyy-MM-dd hh:mm:ss'})" 
								value="<fmt:formatDate value='${prjProg.photoLastUpDate}' pattern='yyyy-MM-dd hh:mm:ss'/>" disabled="disabled"/>
							</li>								
							<li>
								<label>审核日期</label>
								<input type="text" id="confirmDate" name="confirmDate"  style="width:160px" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								    value="<fmt:formatDate value='${prjProg.confirmDate}' pattern='yyyy-MM-dd'/>" />
							</li>								
							<li>
								<label>审核人</label>
								<input type="text" id="confirmCZY" name="confirmCZY" style="width:160px;" value="${prjProg.confirmCZY }" readonly='readonly'/>
							</li>								
							<li hidden="true">
								<label>图片名称hidden</label>
								<input type="text" id="photoName" name="photoName" style="width:160px;" value="${prjProg.photoName }" readonly='readonly'/>
							</li>								
							<li>
								<label class="control-textarea">验收说明</label>
								<textarea style="width:160px" id="confirmDesc" name="confirmDesc" rows="2">${prjProg.confirmDesc }</textarea>
							</li>								
							<li>
								<label>上传图片</label>
							</li>
							<li>
								<input type="file"  name="uploadify" id="uploadify" />
								<a href="javascript:jQuery('#uploadify').uploadify('upload','*')">开始上传</a>&nbsp;
								<a href="javascript:jQuery('#uploadify').uploadify('cancel','*')" onclick="deleteImg()">取消所有上传</a>
								<input type="hidden" id="picture" name="picture" />
							</li>	
							<li>
								<div id="fileQueue" style="float:right"></div>
							</li>							
							<li hidden="true">
								<label>图片hidden</label>
								<input style="width:160px" id="photoPath" name="photoPath" value="${prjProg.photoPath }"/>
							</li>
							<li><label></label></li>
							<li><label></label></li>
							<li><label></label></li>
							<li><label></label></li>
						</ul>	
				</form>
				</div>
				</div>
			</div>
			<div style="width:58%; height:520px ; float: right;margin-left: 1px; ">
			  	<div class="panel panel-info" >  
         <div class="panel-body">	
	<div class="edit-form">
		<form action="" method="post" id="dataForm">
			<house:token></house:token>
			<input type="hidden" name="m_umState" id="m_umState" value="M"/>
			<table cellspacing="0" cellpadding="0" width="100%">
				<col  width="72"/>
				<col  width="150"/>
				<col  width="72"/>
				<col  width="150"/>
				<tbody>
					<tr hidden="true">	
						<td class="td-label"><span class="required">*</span>客户编号</td>
						<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${prjProg.custCode}" />
						</td>
						<td class="td-label">施工项目</td>
						<td class="td-value">
							<input type="text" id="prjItem" name="prjItem" style="width:220px;"  value="${prjProg.prjItem}" />
						</td><td class="td-label">photoName</td>
						<td class="td-value">
							<input type="text" id="photoName" name="photoName" style="width:220px;"  value="${prjProg.photoName}" />
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
			<div hidden="true"  >
				<table id="dataTable_picture" ></table>
			</div>
			<div id="picture" style="width:48%;float: left;margin-left: 1px; class="container" >
					<input style="width:160px" value="${prjProg.photoPath }" id="photoPath" name="photoPath" hidden="true"/>
					 <div align="center">  
			           	 <div align="center">  
			          		 <img id="conPicture" src="${prjProg.photoPath }" onload="AutoResizeImage(555,470,'conPicture');" width="555" height="475" >  
					  	 </div>  
				  	</div> 
			</div>
	</div>	
			
			
	<div id="content-list" hidden="true" >
		<table id= "dataTable"></table>
		<div id="dataTablePager"></div>
	</div> 	
	</div>
	</div>
	<script type="text/javascript">
$(function(){
	var x=0;
	 Global.JqGrid.initJqGrid("dataTable_picture",{
		url:"${ctx}/admin/prjProgPhoto/goJqGrid",
		postData:{CustCode:'${prjProg.custCode }',Type:'1',PrjItem:'${prjProg.prjItem}'},
		colModel : [
				{name: "PK", index: "PK", width: 175, label: "pk", sortable: true, align: "left",hidden:true},
				{name:'prjitemdescr',index:'prjitemdescr',width:175, label:'施工项目', sortable:true ,align:'left'},
				{name: "PhotoName", index: "PhotoName", width: 175, label: "图片名称", sortable: true, align: "left",},
				{name: "LastUpdate", index: "LastUpdate", width: 175, label: "上传图片时间", sortable: true, align: "left",formatter:formatTime},
			],
			gridComplete:function(){
					 var b= $("#dataTable_picture").getCol('PhotoName',false) ;
					 Name=b[0],
					 $('#photoPath').val(Name);
					 $.ajax({
						url:"${ctx}/admin/prjProg/ajaxGetPicture",
						type:'post',
						data:{photoName:b[0],readonly:'1'},
						dataType:'json',
						cache:false,
						error:function(obj){
							showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
						},
						success:function(obj){
							$('#photoPath').val(obj.photoPath);
							document.getElementById("conPicture").src =obj.photoPath;
							$('#allNo').val(b.length);
							if(b.length>0){
								$('#nowNo').val('1');
							}else{
								$('#nowNo').val('0');
							}
						}
					});
		          },
			
	}); 
				
	$("#next").on("click",function(){
			x++;
			var prjphoto = Global.JqGrid.allToJson("dataTable_picture","PhotoName");
				arry = prjphoto.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[x],readonly:'1'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#photoPath').val(obj.photoPath);
						document.getElementById("conPicture").src = obj.photoPath;
						$('#nowNo').val(x+1);
					}
				});
			}else{
			x--;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	
	//上一张
	$("#pre").on("click",function(){
			x--;
			var prjphoto = Global.JqGrid.allToJson("dataTable_picture","PhotoName");
				arry = prjphoto.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[x],readonly:'1'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#photoPath').val(obj.photoPath);
						document.getElementById("conPicture").src = obj.photoPath;	
						$('#nowNo').val(x+1);
					}
				});
			}else{
			x++;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	//删除
	$("#del").on("click",function(){
			var prjphoto = Global.JqGrid.allToJson("dataTable_picture","PhotoName");
				arry = prjphoto.fieldJson.split(",");
			if(arry[x]){
			   art.dialog({
				 content:"是否删除",
				 lock: true,
				 width: 200,
				 height: 100,
				 ok: function () {
					$.ajax({
						url:"${ctx}/admin/prjProg/ajaxDelPicture",
						type:'post',
						data:{photoName:arry[x]},
						dataType:'json',
						cache:false,
						error:function(obj){
							showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
						},
						success:function(obj){
							if(x>=1){
								$.ajax({
									url:"${ctx}/admin/prjProg/ajaxGetPicture",
									type:'post',
									data:{photoName:arry[x-1],readonly:'1'},
									dataType:'json',
									cache:false,
									error:function(obj){
										showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
									},
									success:function(obj){
										$('#photoPath').val(obj.photoPath);
											document.getElementById("conPicture").src = obj.photoPath;
										$("#dataTable_picture").jqGrid("setGridParam",{postData:{CustCode:'${prjProg.custCode }',Type:'1',PrjItem:'${prjProg.prjItem}'}}).trigger("reloadGrid");
									}
								});
							}else{
								$.ajax({
									url:"${ctx}/admin/prjProg/ajaxGetPicture",
									type:'post',
									data:{photoName:arry[x+1],readonly:'1'},
									dataType:'json',
									cache:false,
									error:function(obj){
										showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
									},
									success:function(obj){
										$('#photoPath').val(obj.photoPath);
											document.getElementById("conPicture").src = obj.photoPath;
										$("#dataTable_picture").jqGrid("setGridParam",{postData:{CustCode:'${prjProg.custCode }',Type:'1',PrjItem:'${prjProg.prjItem}'}}).trigger("reloadGrid");
									}
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
	});
	
});

</script>
	
</body>
</html>
