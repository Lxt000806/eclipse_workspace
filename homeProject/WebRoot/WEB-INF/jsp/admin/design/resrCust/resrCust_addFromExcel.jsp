<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>资源客户管理从Excel导入</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
	var stepNum=1;
	$(document).ready(function() {  
			//初始化excel模板表格
			Global.JqGrid.initJqGrid("modelDataTable",{
				height:$(document).height()-$("#content-list").offset().top-70,
				colModel : [
						{name:'descr',	index:'descr',	width:90,	label:'客户名称',	sortable:true,align:"left",},
						{name:'mobile1',	index:'mobile1',	width:90,	label:'手机号',	sortable:true,align:"left",},
						{name:'mobile2',	index:'mobile2',	width:90,	label:'手机号2',	sortable:true,align:"left",},
						{name:'gender',	index:'gender',	width:90,	label:'性别',	sortable:true,align:"left",},
						{name:'address',	index:'address',	width:180,	label:'楼盘地址',	sortable:true,align:"left",},
						{name:'buildercode',	index:'buildercode',	width:90,	label:'小区名称',	sortable:true,align:"left"},
						{name:'buildernum',	index:'buildernum',	width:90,	label:'楼栋号',	sortable:true,align:"left",},
						{name:'areaforexcel',	index:'areaforexcel',	width:180,	label:'面积',	sortable:true,align:"left",},
						{name:'email2',	index:'email2',	width:110,	label:'微信/Email',	sortable:true,align:"left"},
						{name:'remark',	index:'remark',	width:150,	label:'备注',	sortable:true,align:"left"},
						{name:'netchanel', index:'netchanel', width: 100, label:'网络渠道', sortable:true, align:"left"},
	            ]
			});
			$("#modelDataTable").addRowData(1,
			{ "descr": "张三","mobile1":"必填字段，18888888888","mobile2":"19999999999","gender":"男","address":"必填字段，金色丽景36#102", 
			  "buildercode": "金色丽景","buildernum": "1#","area":"120","email2":"abc123","remark":"加油拿下！", "netchanel": "百度"
			}, "last");
	});
	$(function(){
		$("#self").prop("checked","checked"); 
		$("#backBtn,#submitBtn").addClass("hidden");
		$("#backBtn").on("click",function(){
			$("div[name='step"+stepNum+"']").each(function(){
				$(this).removeClass("step-completed");
			});
			stepNum-=1;
			changePage();
		});
		
		$("#nextBtn").on("click",function(){
			var checkExcel=true;
			checkExcel=check();
			if(!checkExcel){
				return;
			}
			stepNum+=1;
			$("div[name='step"+stepNum+"']").each(function(){
				$(this).addClass("step-completed");
			});
			changePage();
		});
		
		$("#submitBtn").on("click",function(){
			var resrCustPoolNo = $("#resrCustPoolNo").val();
			if(resrCustPoolNo == ""){
				art.dialog({
					content:"请选择线索池",
				});
				return;
			}
			
			var emp=$("#emp").is(':checked');
			if($("#emp").is(':checked') && $("#emps").val()==""){
				art.dialog({
					content : "请添加同事！"
				});
				return;
			} 
			stepNum+=1;
			$("div[name='step"+stepNum+"']").each(function(){
				$(this).addClass("step-completed");
			});
			changePage();
			//用表单来初始化
			var form = document.getElementById("data_form");
			var formData = new FormData(form);
			formData.append("file", document.getElementById("file").files[0]);
			//发送请求
			$.ajax({
				url : "${ctx}/admin/ResrCust/loadExcel",
				type : "POST",
				data : formData,
				contentType : false,
				processData : false,
				success : function(data) {
					
				},
			}); 
		});
		
		$("#continueBtn").on("click",function(){
			stepNum=1;
			//清空选择的文件
			$("#file").val("");
			//清空步骤条2,3
			$("div[name='step2']").each(function(){
				$(this).removeClass("step-completed");
			});
			$("div[name='step3']").each(function(){
				$(this).removeClass("step-completed");
			});
			//导入方案恢复默认选项
			$("#self").prop("checked","checked"); 
			$("#emps").val("");
			$("#descrs").val("");
			$(".selectEmp").each(function(){
	    		$(this).addClass("hidden");
	    	});
			changePage();
		});
		
		$("#viewTask").on("click",function(){
			Global.Dialog.showDialog("viewTask",{
				title:"查看任务",
				url:"${ctx}/admin/ResrCust/goViewTask",
				height:600,
				width:950,
			}); 
		});
		
		$("input[name=importPlan]").click(function(){
		    var importPlan = $(this).val();
		    if(importPlan=="3"){
		    	$(".selectEmp").each(function(){
		    		$(this).removeClass("hidden");
		    	});
		    }else{
		    	$(".selectEmp").each(function(){
		    		$(this).addClass("hidden");
		    	});
		    }
		});
		
		chgResrCustPoolNo();
	});
	
	//切换步骤页面
	function changePage(){
		if(stepNum==1){
			$("#content1").css("display","block");
			$("#content2,#content3").css("display","none");
			$("#backBtn,#submitBtn").addClass("hidden");
			$("#nextBtn").removeClass("hidden");
		}else if(stepNum==2){
			$("#content2").css("display","block");
			$("#content1,#content3").css("display","none");
			$("#nextBtn").addClass("hidden");
			$("#submitBtn,#backBtn").removeClass("hidden");
		}else{
			$("#content3").css("display","block");
			$("#content1,#content2").css("display","none");
			$("#nextBtn,#backBtn,#submitBtn").addClass("hidden");
		}
	}
	
	//加载文件验证
	function check() {
		var fileName = $("#file").val();
		var reg = /^.+\.(?:xls|xlsx|csv)$/i;
		if (fileName.length == 0) {
			art.dialog({
				content : "请选择需要导入的excel文件！"
			});
			return false;
		}
		if (fileName.match(reg) == null) {
			art.dialog({
				content : "文件格式不正确！请导入正确的excel文件！"
			});
			return false;
		}
		return true;
	}
	
	function addEmp(){
		var descrs=$("#descrs").val();
		if(descrs!=""){
			descrs+=",";
		}
		Global.Dialog.showDialog("addEmp", {
			title : "添加同事",
			url : "${ctx}/admin/ResrCust/goAddEmp",
			postData : {
				'numbers':$("#emps").val(),'descrs':descrs
			},
			height: 600,
			width:1000,
			returnFun:function(data){
				$("#emps").val(data.numbers);
				$("#descrs").val(data.descrs.substring(0,data.descrs.length-1));
			}
		});
	}
	function clearEmps(){
		$("#emps").val("");
		$("#descrs").val("");
	}
	
	function chgResrCustPoolNo(){
		var resrCustPoolNo = $("#resrCustPoolNo").val();
		if(resrCustPoolNo == ""){
			$("#radio_div").show();
			$("input[name='importPlan']").get(0).checked = true;					
			return;
		}
		$.ajax({
			url:"${ctx}/admin/ResrCust/getPoolType?resrCustPoolNo=" + resrCustPoolNo,
			type:'post',
			data:{},
			dataType:'json',
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '出错~'});
			},
			success:function(obj){
				if (obj){
					if($.trim(obj.dispatchRule)!="0"){ 
						$("#radio_div").hide();
						$("input:radio[name='importPlan']").removeAttr("checked");
						$("input[name='importPlan']").get(1).checked = true;					
						
					} else {
						$("#radio_div").show();
						$("input[name='importPlan']").get(0).checked = true;					
					}
				}
			}
		});
	}
</script>
</head>
	<body>
<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="nextBtn">
							<span>下一步</span>
						</button>
						<button type="button" class="btn btn-system hidden" id="submitBtn">
							<span>提交任务</span>
						</button>
						<button type="button" class="btn btn-system " id="backBtn">
							<span>上一步</span>
						</button>
						<button type="button" class="btn btn-system " id="viewTask">
							<span>查看任务</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin()">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<div class="steps" style="position:relative;padding-left:180px">
        				<div class="step-line step-completed"></div>
				        <div class="step step-completed">
				            <div class="step-text">1</div>
				            <div class="step-main">上传Excel文件</div>
				        </div>
				        <div class="step-line step-completed"></div>
				        <div class="step-line " name="step2"></div>
				
				        <div class="step" name="step2">
				            <div class="step-text">2</div>
				            <div class="step-main">选择导入方案</div>
				        </div>
				        <div class="step-line" name="step2"></div>
				        <div class="step-line" name="step3"> </div>
				        <div class="step" name="step3">
				            <div class="step-text">3</div>
				            <div class="step-main">后台执行任务</div>
				        </div>
				        <div class="step-line " name="step3"></div>
				    </div>
				    <br><br><br>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<div class="tab_content" style="display: block; " id="content1">
						<div class="edit-form">
							<form id="page_form">
								<house:token></house:token>
								<input type="hidden" name="jsonString" value="" />
								<div class="row">
									<input type="file" id="file" name="file" style="margin-left:185px"/><br><br>
									<span style="margin-left:185px;color:red">导入说明：文件必须为xls,xlsx或csv格式</span>
								</div><br><br>
								<div class="btn-group-xs row" style="margin-left:170px">
									<button type="button" class="btn btn-system " onclick="doExcelNow('资源客户导入模板','modelDataTable')">
										<span>下载导入模板</span>
									</button>
								</div>
							</form>
							<div class="btn-panel">
								<div class="pageContent">
									<div style="display: none" id="content-list">
										<table id="modelDataTable"></table>
										<div id="modelDataTable"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab_content" style="display: none;" id="content2" >
					<form id="data_form">
						<div class="row">
							<p style="font-weight: bold;margin-left:185px">选择导入方案</p>
						</div>
						<div class="row">
							<ul>
								<li class="form-validate" style="margin-left:180px" >
									<label><span class="required">*</span>线索池</label>
									<house:dict id="resrCustPoolNo" dictCode="" sqlValueKey="No" sqlLableKey="Descr"
									    sql="select a.No,a.Descr from tResrCustPool a
									    where (exists (select 1 from tResrcustPoolEmp in_a where in_a.ResrCustPoolNo = a.No and in_a.CZYBH = '${resrCust.czybh }')
									    or exists (select 1 from tCZYBM in_b where in_b.DefaultPoolNo = a.No and in_b.CZYBH = '${resrCust.czybh }')
									    or a.Descr = '默认线索池') and expired = 'F'" value="${resrCust.defPoolNo }" onchange="chgResrCustPoolNo()"></house:dict>
								</li>
							<div style="margin-left:180px" id="radio_div">
								<li>
									<input type="radio"  id="self" name="importPlan" value="1" style="vertical-align: -5px" /><label for="self" style="color:gray;">导入给自己</label>
								</li>
								<li>
									<input type="radio" id="pub" name="importPlan" value="2" style="vertical-align: -5px"/><label for="pub" style="color:gray">导入到公海</label>
								</li>
								<li>
									<input type="radio" id="emp" name="importPlan" value="3" style="vertical-align: -5px"/><label for="emp" style="color:gray">导入给同事</label>
									<button type="button" style="font-size: 12px;line-height: 1.5;padding: 1px 5px;" class="btn btn-system selectEmp hidden"  onclick="addEmp()">
										<span>添加</span>
									</button>
									<button type="button" style="font-size: 12px;line-height: 1.5;padding: 1px 5px;" class="btn btn-system selectEmp hidden"  onclick="clearEmps()">
										<span>清空</span>
									</button>
								</li>
							</div>
							<br>
							<div style="margin-left:180px;" class="selectEmp hidden">	
								<input type="hidden" name="emps" id="emps"  />
								<li>
									<textarea id="descrs" name="descrs" style=" height:70px;width:400px;border-radius:8px" readonly/></textarea>
								</li>
							</div>
							</ul>
						</div>
						</form>
					</div>
					<div class="tab_content" style="display: none;" id="content3">
						<div class="row" style="margin-left:320px">
							<img id='yes' text-align:center  src="${ctx}/images/yes.jpg" style="width:80px;height:80px;border: 0" >
						</div>
						<div class="row" style="margin-left:260px">
							<h3>该任务已提交至后台</h3>
						</div>
						<div class="row" style="margin-left:280px">
							<label style="color:gray">您可以点击"查看任务"关注结果</label>
						</div>
						<div class="btn-group-xs" style="margin-left:280px">
							<button type="button" class="btn btn-system" id="continueBtn">
								<span>继续添加任务</span>
							</button>&nbsp;&nbsp;
							<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin()">
								<span>关闭窗口</span>
							</button>
						</div>
					</div>
			</div>
		</div>
	</body>	
</html>
