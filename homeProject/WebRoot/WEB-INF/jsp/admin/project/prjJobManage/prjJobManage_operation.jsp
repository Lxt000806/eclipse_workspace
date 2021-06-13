<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>任务管理操作页面</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	var tableId="dataTable_material";  
	/**初始化表格*/
	$(function(){
		if("${prjJob.isConfirmed}"=="0"){
			$("#tips").text("该${prjJob.prjItemDescr}未验收！");
		}
		if("${prjJob.m_umState}"=="S"){
			$("#photo").removeClass();
			$("#appointSuppl").addClass("active");
			$("#prjJobManage_tabView_photo").removeClass();
			$("#prjJobManage_tabView_photo").addClass("tab-pane fade");
			$("#prjJobManage_tabView_appointSuppl").addClass("tab-pane fade in active");
		}
		//${prjJob.warBrand}${prjJob.cupBrand}
		if("${prjJob.warBrand}"==""){
			$("#warBrand").val("");
		}
		if("${prjJob.cupBrand}"==""){
			$("#cupBrand").val("");
		}
		$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
			if(e.currentTarget.id){
		    	tableId="dataTable_dealMaterial";
	    	} else{
	    		tableId="dataTable_material";
	    	} 
       
		});
		$("#appCzy").openComponent_employee({showValue:"${prjJob.appCzy}",showLabel:"${prjJob.appCzyDescr}", 'readonly': true});	
		$("#${prjJob.m_umState}But").removeAttr("disabled");
		if("${prjJob.m_umState}"=="W"){
			$("#BBut").removeAttr("disabled");
			$("#dealRemark").removeAttr("readonly");
			//$("#endCode0Li").show();//带0
			$("#jobType").attr("disabled","disabled");
			$("#status").attr("disabled","disabled");
			$("#itemType1").attr("disabled","disabled");

			/* if("${prjJob.endCode}"=="0"){
				var obj=document.getElementById("endCode0"); 
				obj.options.add(new Option("0 未完成","0")); 
				document.getElementById("endCode0")[3].selected=true;
			} */
		}else{
			$("#endCode0").attr("disabled", "disabled");
			//$("#endCodeLi").show();//不带0
			if("${prjJob.endCode}"=="0"){
				var obj=document.getElementById("endCode0"); 
				obj.options.add(new Option("0 未完成","0")); 
				document.getElementById("endCode0")[3].selected=true;
			}
		
		}
		if("${prjJob.m_umState}"=="Z"){
			$("#dealCzy").openComponent_employee({showValue:"${prjJob.dealCzy}",showLabel:"${prjJob.dealCzyDescr}"});
			$("#supplCode").openComponent_supplier({showValue:"${prjJob.supplCode}",showLabel:"${prjJob.supplDescr}"});		
			$("#planDate").removeAttr("disabled");
			$("#remarks").removeAttr("readonly");
			$("#jobType").attr("disabled","disabled");
			$("#status").attr("disabled","disabled");
			$("#itemType1").attr("disabled","disabled");
			//$('input[name=planDate]').css('background','#CCCCCC');
			//$('input[name=openComponent_employee_dealCzy]').css('background','#CCCCCC');
		}else{
			if("${prjJob.m_umState}"=="R") $("#supplCode").openComponent_supplier({showValue:"${prjJob.supplCode}",showLabel:"${prjJob.supplDescr}"});
			
			else $("#supplCode").openComponent_supplier({showValue:"${prjJob.supplCode}",showLabel:"${prjJob.supplDescr}", 'readonly': true});			
			$("#dealCzy").openComponent_employee({showValue:"${prjJob.dealCzy}",showLabel:"${prjJob.dealCzyDescr}", 'readonly': true});	
			
		}
		if("${prjJob.m_umState}"=="C"){
			$("#dealRemark").removeAttr("readonly");
		}
		if("${prjJob.m_umState}"!="Z"){
			$("#dealCzy_star").hide();
			$("#dealDate_star").hide();
		}
		if("${prjJob.m_umState}"!="W"){
			$("#dealRemarks_star").hide();
			$("#endCode_star").hide();
		}
		
		if("${prjJob.m_umState}"=="R"){
			$("#planDate").removeAttr("disabled");
			$("#remarks").removeAttr("readonly");
			$("#jobType").attr("disabled","disabled");
			$("#status").attr("disabled","disabled");
			$("#itemType1").attr("disabled","disabled");
			$("#dealCzy").openComponent_employee({showValue:"${uc.czybh}",showLabel:"${uc.zwxm}", 'readonly': true});	
		} 
		if("${prjJob.isDispCustPhn}"=="1"){
			$("#custNameLi").show();
			$("#custPhoneLi").show();
		}
		if("${prjJob.isNeedSuppl}"=="1"){
			$("#supplCodeLi").show();
			$("#phoneLi").show();
		}
	})
    function doOperation(fcName) {
	    var datas = $("#dataForm").jsonForm();
	    fcName=fcName||"doOperation";
	    $.ajax({
        	url: '${ctx}/admin/prjJobManage/'+fcName,
	      	type: 'post',
	      	data: datas,
	      	dataType: 'json',
	      	cache: false,
	      	error: function (obj) {
	       		showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	      	},
     	 	success: function (obj) {
		        if (obj.rs) {
	          		art.dialog({
			            content: obj.msg,
			            time: 1000,
			            beforeunload: function () {
		              		closeWin();
			            }
	          		});
		        } else {
	          		$("#_form_token_uniq_id").val(obj.token.token);
		          	art.dialog({
			            content: obj.msg,
			            width: 200
		          	});
	        	}
	      }
	    });
    }
	function photoDownload(){
		var number = Global.JqGrid.allToJson(tableId,"PhotoName").datas.length;
		if(number <= 0 ){
			art.dialog({
				content:"该记录没有图片",
				time:3000
			});
			return;
		}
		window.open("${ctx}/admin/prjJob/downLoad?no="+$("#no").val());
	}
	function receive(){
		if(!$("#planDate").val()){
			art.dialog({
				content:'请填写计划处理时间！'
			});
			return ;
		}
		if("${prjJob.isNeedSuppl}"=="1"&&!$("#supplCode").val()){
			art.dialog({
				content:'请选择供应商！'
			});
			return ;
		}
	  	art.dialog({
	        content: '是否确认要进行接收操作!',
	        lock: true,
	        width: 260,
	        height: 100,
	        ok: function () {
            	doOperation();
	        },
	        cancel: function () {
	          	return true;
	        }
         });
	}
	function assign(){
		if(!$("#planDate").val()){
			art.dialog({
				content:'请填写计划处理时间！'
			});
			return ;
		}
		if("${prjJob.isNeedSuppl}"=="1"&&!$("#supplCode").val()){
			art.dialog({
				content:'请选择供应商！'
			});
			return ;
		}
	  	art.dialog({
	        content: '是否确认要进行指派操作!',
	        lock: true,
	        width: 260,
	        height: 100,
	        ok: function () {
            	doOperation();
	        },
	        cancel: function () {
	          	return true;
	        }
         });
	}
	function finish(){
		if($("#endCode0").val()==''){
			art.dialog({
				content:'请填写处理结果!'
			});
			return ;
		}
		if($("#dealRemark").val()==''){
			art.dialog({
				content:'请填写处理说明!'
			});
			return ;
		}
	  	art.dialog({
	        content: '是否确认要进行任务完成操作!',
	        lock: true,
	        width: 260,
	        height: 100,
	        ok: function () {
            	doOperation();
	        },
	        cancel: function () {
	          	return true;
	        }
    	});
	}
	function cancel(){
	  	art.dialog({
	        content: '是否确认要进行任务取消操作!',
	        lock: true,
	        width: 260,
	        height: 100,
	        ok: function () {
            	doOperation();
	        },
	        cancel: function () {
	          	return true;
	        }
         });
	}			
	function saveDealRemark(){
		if(!$("#dealRemark").val()){
			art.dialog({
				content:'请填写处理说明！'
			});
			return ;
		}
		doOperation("doUpdate");
	}			
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs buttons" >
    				<button id="RBut" type="button" class="btn btn-system " disabled="disabled"  onclick="receive()" >接收</button>
    				<button id="ZBut" type="button" class="btn btn-system " disabled="disabled"  onclick="assign()" >指派</button>
    				<button id="WBut" type="button" class="btn btn-system " disabled="disabled"  onclick="finish()" >完成</button>
    				<button id="CBut" type="button" class="btn btn-system " disabled="disabled"  onclick="cancel()" >取消</button>
    				<button id="BBut" type="button" class="btn btn-system " disabled="disabled"  onclick="saveDealRemark()" >保存处理说明</button>
    				<button id="photoBut" type="button" class="btn btn-system "   onclick="photoDownload()">图片下载</button>
    				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
    				<font color="red" ><span id="tips"></span></font>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" enctype="multipart/form-data" class="form-search">
					<input type="hidden" value="${prjJob.m_umState}" id="m_umState" name="m_umState"/> 
					<ul class="ul-form">
						<li>
							<label>编号</label>
							<input type="text" id="no" name="no" value="${prjJob.no}" readonly/>
						</li>
						
						<li>
							<label>任务类型</label>
							<house:dict id="jobType" dictCode="" sql="select rtrim(Code)Code,Descr from tJobType where Expired='F' order by Code " 
										sqlValueKey="Code" sqlLableKey="Descr" value="${prjJob.jobType }"   ></house:dict>   
						</li>
						<li>
							<label>处理日期</label>
							<input type="text" id="dealDate" name="dealDate" value="<fmt:formatDate value='${prjJob.dealDate}' pattern='yyyy-MM-dd'/>" readonly/>
						</li>
						<li>
							<label>楼盘地址</label>
							<input type="text" id="address" name="address" value="${prjJob.address}" readonly/>
						</li>
						<li>
							<label>申请日期</label>
							<input type="text" id="date" name="date" value="<fmt:formatDate value='${prjJob.date }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly/>
						</li>
						<li id="endCodeLi" style="display: none" >
							<label><span class="required" id="endCode_star">*</span>处理结果</label>
							<house:xtdm id="endCode" dictCode="PREAPPENDCODE" value="${prjJob.endCode}"></house:xtdm>
						</li>
						<li id="endCode0Li">
							<label><span class="required" id="endCode_star">*</span>处理结果</label>
							<house:dict id="endCode0" dictCode="" sql="select ID,CBM,IBM,IDNOTE,NOTE,NOTE_E,IDNOTE_E from tXTDM where ID='PRJJOBENDCODE' and CBM not in (0) order by IBM ASC"
								 sqlValueKey="cbm" sqlLableKey="note" value="${prjJob.endCode}" ></house:dict>
						</li>
						<%-- <li>
							<label><span class="required" id="endCode_star" >*</span>处理结果</label>
							<select id="endCode" name="endCode" value="${prjJob.endCode}">
								<option value=""></option>
								<option value="1">1 解决</option>
								<option value="2">2 未解决</option>
							</select>
						</li> --%>
						<li>
							<label>材料类型1</label>
							<house:dict id="itemType1" dictCode="" sql="SELECT cbm,(cbm+' '+note) note FROM tXTDM  WHERE ID='ITEMRIGHT'  ORDER BY IBM ASC" sqlValueKey="cbm" sqlLableKey="note" value="${prjJob.itemType1 }" ></house:dict>
						</li>
						<li>
							<label>申请人</label>
							<input type="text" id="appCzy" name="appCzy" value="${prjJob.appCzy}" readonly/>
						</li>
						<li>
							<label>状态</label>
							<house:xtdm id="status" dictCode="PRJJOBSTATUS" value="${prjJob.status }" ></house:xtdm>
						</li>
						<li>
							<label><span class="required" id="dealCzy_star">*</span>处理人</label>
							<input type="text" id="dealCzy" name="dealCzy" value="${prjJob.dealCzy}" />
						</li>
						<li>
							<label>衣柜</label>
							<input type="text" id="warBrand" name="warBrand" value="${prjJob.warBrand}|${prjJob.warBrandDescr}" readonly/>
						</li>
						<li>
							<label>橱柜</label>
							<input type="text" id="cupBrand" name="cupBrand" value="${prjJob.cupBrand}|${prjJob.cupBrandDescr}" readonly/>
						</li>
        				<li id="phoneLi" style="display: none">
							<label>申请人电话</label>
							<input type="text" id="phone" name="phone" value="${prjJob.phone}" readonly/>
						</li>
						<li id="custNameLi" style="display: none">
							<label>客户名称</label>
							<input type="text" id="custName" name="custName" value="${prjJob.custName}" readonly/>
						</li>
						<li id="custPhoneLi" style="display: none">
							<label>客户电话</label>
							<input type="text" id="custPhone" name="custPhone" value="${prjJob.custPhone}" readonly/>
						</li>
						
						<li >
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" readonly="readonly">${prjJob.remarks }</textarea>
						</li>
						<li >
							<label class="control-textarea"><span class="required" id="dealRemarks_star">*</span>处理说明</label>
							<textarea id="dealRemark" name="dealRemark" readonly="readonly">${prjJob.dealRemark }</textarea>
						</li>
						<li>
          					<label><span class="required" id="dealDate_star" type="hidden">*</span>计划处理日期</label>
          					<input type="text" id="planDate" name="planDate" class="i-date" disabled="disabled"
                 				   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                 				   value="<fmt:formatDate value='${prjJob.planDate}' pattern='yyyy-MM-dd'/>"/>
        				</li>
						<li id="supplCodeLi" style="display: none" > 
							<label>供应商</label>
							<input type="text" id="supplCode" name="supplCode"  value="${prjJob.supplCode}" />
						</li>
						<li >
							<label>客户类型</label>
							<house:xtdm id="custType" dictCode="CUSTTYPE" value="${prjJob.custType.trim() }" disabled="true"></house:xtdm></label>						
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div  class="container-fluid" >  
		     <ul class="nav nav-tabs" >
		        <li class="active" id="photo" ><a href="#prjJobManage_tabView_photo" data-toggle="tab">图片</a></li>
			    <li ><a id="custRight_show" href="#prjJobManage_tabView_dealPhoto" data-toggle="tab">处理图片</a></li>
			    <li id="appointSuppl" ><a href="#prjJobManage_tabView_appointSuppl" data-toggle="tab">指派给供应商</a></li>
		    </ul>
		    <div class="tab-content">  
				<div id="prjJobManage_tabView_photo"  class="tab-pane fade in active" > 
		         	<jsp:include page="prjJobManage_tabView_photo.jsp">
		         		<jsp:param value="${prjJob.no}" name="no"/>
		         	</jsp:include>
				</div>
				<div id="prjJobManage_tabView_dealPhoto"  class="tab-pane fade " > 
		         	<jsp:include page="prjJobManage_tabView_dealPhoto.jsp">
		         		<jsp:param value="${prjJob.no}" name="no"/>
		         	</jsp:include>
				</div>
				<div id="prjJobManage_tabView_appointSuppl"  class="tab-pane fade " > 
		         	<jsp:include page="prjJobManage_tabView_appointSuppl.jsp">
		         		<jsp:param value="${prjJob.no}" name="no"/>
		         	</jsp:include>
				</div>
			</div>	
		</div>	
	</div>
</body>
</html>
