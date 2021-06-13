<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>集成测量分析——明细</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");

var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${prjJob.itemType1}',
		disabled:"true"
	};
	Global.LinkSelect.setSelect(dataSet);
});
function photoDownload(){
		var number = Global.JqGrid.allToJson("dataTable_material","photoname").datas.length;
		if(number <= 0 ){
			art.dialog({
				content:"该记录没有图片",
				time:3000
			});
			return;
		}
		window.open("${ctx}/admin/prjJob/downLoad?no="+$("#no").val());
	}

</script>
</head>
    
<body >
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      	  <button type="button" class="btn btn-system "   onclick="photoDownload()">图片下载</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="V"/>
				<ul class="ul-form">
					<li>
						<label>编号</label>
							<input type="text" id="no" name="no"  value="${prjJob.no }" readonly="true" />
					</li>
					<li>
						<label>任务类型</label>
							<house:dict id="jobType" dictCode="" sql="select rtrim(Code)Code,Descr from tJobType where Expired='F' order by Code " 
								sqlValueKey="Code" sqlLableKey="Descr"  value="${prjJob.jobType }"  disabled="true"></house:dict>
					</li>
					<li>
						<label>处理日期</label>
							<input type="text" id="dealDate"  value="${prjJob.dealDate}"  readonly="true"/>	
					</li>
					<li>
						<label>楼盘地址</label>
							<input type="text" id="address" name="address"  value="${customer.address }" readonly="true" />
					</li>
					<li>
						<label>申请日期</label>
							<input type="text" id="date" value="${prjJob.date}"  readonly="true"/>	
					</li>
					<li>
						<label>处理结果</label>
							<house:xtdm  id="endCode" dictCode="PRJJOBENDCODE"  value="${prjJob.endCode}" disabled="true"></house:xtdm>
					</li>
					<li>
						<label>材料类型1</label>
							<select id="itemType1" name="itemType1" disabled="disabled"></select>
					</li>
					<li>
						<label>申请人</label>
							<input type="text" id="appCzy" name="applyMan" style="width:79px;"  value="${prjJob.appCzy}"  readonly="true" />
							<input type="text" id="appCzyDescr" name="applyMan" style="width:79px;"  value="${prjJob.appCzyDescr }"  readonly="true" />
					</li>
					<li>
						<label>状态</label>
							<house:xtdm  id="status" dictCode="PRJJOBSTATUS"  value="${prjJob.status}" disabled="true"></house:xtdm>
					</li>
					<li>
						<label>衣柜</label>
							<input type="text" id="warBrand" name="warBrand" style="width:79px;"  value="${prjJob.warBrand}"  readonly="true" />
							<input type="text" id="warBrandDescr" name="warBrandDescr" style="width:79px;"  value="${prjJob.warBrandDescr}"  readonly="true" />
					</li>
					<li>
						<label>处理人</label>
							<input type="text" id="dealCzy" name="dealCzy" style="width:79px;"  value="${prjJob.dealCzy}"  readonly="true" />
							<input type="text" id="dealCzyDescr" name="dealCzyDescr" style="width:79px;"  value="${prjJob.dealCzyDescr}"  readonly="true" />
					</li>
					<li>
						<label>计划处理日期</label>
						<input type="text" id="date" name="date" class="i-date"  
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${prjJob.planDate}' pattern='yyyy-MM-dd'/>"  readonly="true"/>
					</li>
					<li>
						<label>橱柜</label>
							<input type="text" id="cupBrand" name="cupBrand" style="width:79px;"  value="${prjJob.cupBrand}"  readonly="true" />
							<input type="text" id="cupBrandDescr" name="cupBrandDescr" style="width:79px;"  value="${prjJob.cupBrandDescr}"  readonly="true" />
					</li>
					<li>
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${prjJob.remarks }</textarea>
					</li>		
					<li>
						<label class="control-textarea" >处理说明：</label>
						<textarea id="remarks" name="remarks">${prjJob.dealRemark }</textarea>
					</li>				
				</div>
			</form>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_PrjXJ" data-toggle="tab">图片</a></li>
		    </ul> 
		    <div class="tab-content">  		    
		        <div id="tab_PrjXJ" class="tab-pane fade in active"> 
		         	<jsp:include page="prjjob_Picture.jsp"></jsp:include>
		        </div> 		     
		    </div>  
		</div>
	</div>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	
</body>
</html>


