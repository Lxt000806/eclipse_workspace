<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>采购入库</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
  </head>
  <body>
  	 <div class="body-box-form">
  			<!-- panelBar -->
  				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
  			<!-- edit-form -->
			  	<div class="panel panel-info" >  
         <div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
						<ul class="ul-form">
							<li hidden="true">
								<label>readonly</label>
								<input type="text" id="readonly" name="readonly"   style="width:160px;" value="${prjProg.readonly }" readonly="readonly"/>
							</li>
							<li>
								<label>施工项目编号</label>
								<input type="text" id="pk" name="pk"  placeholder="保存自动生成" style="width:160px;" value="${prjProg.pk }" readonly="readonly"/>
							</li>
							<li>
								<label>施工项目名称</label>
								<house:xtdm id="prjItem" dictCode="PRJITEM"  style="width:166px;"  value="${prjProg.prjItem }" ></house:xtdm>
						</li>
							<li hidden="true">
								<label>施工项目名称</label>
								<input type="text" id="prjDescr" name="prjDescr"   style="width:160px;" value="${prjProg.prjDescr }" readonly="readonly"/>
							</li>
							<li>
								<label>计划开始日期</label>
								<input type="text" id="planBegin" name="planBegin" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.planBegin }' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>计划结束日期</label>
								<input type="text" id="planEnd" name="planEnd" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.planEnd}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>实际开始日期</label>
								<input type="text" id="beginDate" name="beginDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.beginDate }' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>实际结束日期</label>
								<input type="text" id="endDate" name="endDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.endDate }' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>调整计划结束时间</label> 
								<input type="text" id="adjPlanEnd" name="adjPlanEnd" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.adjPlanEnd }' pattern='yyyy-MM-dd'/>"  />
							</li>
							<li>
								<label>是否过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${prjProg.expired }" onclick="confirm(this)" />
							</li>
							<li hidden="true">
								<label>是否过期</label>
								<input type="text" id="expired" name="expired"   style="width:160px;" value="${prjProg.expired }" />
  							</li>
  						</ul>
  				</form>
  				</div>
  		</div>
  	</div> 

<script type="text/javascript">
$(function(){

$("#saveBtn").on("click",function(){
	 var selectRows = [];
	 var datas=$("#dataForm").jsonForm();
		if(datas.prjItem ==''){
			art.dialog({
	            content: "请填写完整信息"
	        });
	        return false;
		}
	 	if(datas.prjItem=='1'){datas.prjDescr='开工、办物业手续成品保护';	 }
	 	if(datas.prjItem=='2'){ datas.prjDescr='拆地梁、放样、备料';	}
	 	if(datas.prjItem=='3'){	datas.prjDescr='砌墙、粉刷';	 }
	 	if(datas.prjItem=='4'){	datas.prjDescr='墙体养护';	 }
	 	if(datas.prjItem=='5'){ datas.prjDescr='水电预埋';	 }
	 	if(datas.prjItem=='6'){ datas.prjDescr='地面找平';	 }
	 	if(datas.prjItem=='7'){ datas.prjDescr='防水工程'; }
	 	if(datas.prjItem=='8'){ datas.prjDescr='泥水饰面（贴墙/地砖）';	  }
	 	if(datas.prjItem=='9'){ datas.prjDescr='木作工程'; }
	 	if(datas.prjItem=='10'){ datas.prjDescr='油漆工程';	  	}
	 	if(datas.prjItem=='11'){ datas.prjDescr='后期水电安装、玻璃安装';	  } 
	 	if(datas.prjItem=='12'){ datas.prjDescr='木地板安装';	 	 }
	 	if(datas.prjItem=='13'){ datas.prjDescr='饰面墙纸、墙布';	  }
	 	if(datas.prjItem=='14'){ datas.prjDescr='初检及局部修补';	 }
	 	if(datas.prjItem=='15'){ datas.prjDescr='竣工保洁';	  }
	 	if(datas.prjItem=='16'){	datas.prjDescr='竣工验收';	 }
	 	if(datas.prjItem=='17'){	datas.prjDescr='集成安装';	 }
	 	if(datas.prjItem=='18'){	datas.prjDescr='房门安装';	 }
	 	if(datas.expired==''){ datas.expired='F'}
	 	selectRows.push(datas);
		 Global.Dialog.returnData = selectRows;
		closeWin();
		
	}); 
	
	 if("${prjProg.readonly }"!=''){
	 	$(".a1").css("display","none");
	 }
	
	
});
function confirm(obj){
	if ($(obj).is(':checked')){
		$('#expired').val('T');
	}else{
		$('#expired').val('F');
	}
}



</script>
  </body>
</html>

















