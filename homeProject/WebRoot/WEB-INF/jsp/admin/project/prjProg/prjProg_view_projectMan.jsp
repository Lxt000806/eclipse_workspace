<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--员工</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript"> 
$(function(){
	$("#id_detail>.tab_container .tab_content").hide(); 
    $("#id_detail>ul.tabs li:first").addClass("active").show();  
    $("#id_detail>.tab_container .tab_content:first").show();
    $("#id_detail>ul.tabs li").click(function() { 
        $("#id_detail>ul.tabs li").removeClass("active");  
        $(this).addClass("active");  
        $("#id_detail>.tab_container .tab_content").hide(); 
        var activeTab = $(this).find("a").attr("href"); 
        $(activeTab).fadeIn();
        //去掉最后一个竖线
        if($(this).find("a").attr('href')=='#tab_totalByBrand'){
        	if ($(this).attr("class")=='active'){
        		$(this).css("border-right","none");
        	}
        }else{
        	$("#id_detail>ul.tabs li").css("border-right","");
        }
        $("#id_detail>.tab_container .tab_content").find(".tab_content:first").show();
        return false;
    });
		
		
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="expired" name="expired" value="${employee.expired }" />
				<table cellpadding="0" cellspacing="0" width="100%">
						<col  width="82" />
						<col  width="24.%"/>
						<col  width="82"/>
						<col  width="25.3%"/>
					<tbody>
					<tr class="td-btn" hidden="true">
							<td class="td-label">一级部门</td>
							<td class="td-value">
							<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${employee.department1 }"></house:department1>
							</td>
						</tr>
						<tr class="td-btn">
							<td class="td-label">二级部门</td>
							<td class="td-value">
							<house:department2 id="department2" dictCode="${employee.department1 }" value="${employee.department2 }" onchange="changeDepartment2(this,'department3','${ctx }')"></house:department2>
							</td>
							<td class="td-label">项目经理</td>
							<td class="td-value">
							<input type="text" id="number" name="number" style="width:160px;" value="${employee.number }" />
							</td>
						</tr>
						<tr class="td-btn">
							<td class="td-label">日期</td>
							<td class="td-value">
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</td>
							<td class="td-label">至</td>
							<td class="td-value" >
								<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</td>
						</tr>
						<tr>
							<td colspan="4" class="td-btn">
								<div class="sch_qk_con"> 
									<input onclick="goto_query();"  class="i-btn-s" type="button"  value="检索"/>
									<input onclick="clearForm();"  class="i-btn-s" type="button"  value="清空"/>
								</div>
						  	</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="container" id="id_detail">  
		    <ul class="tabs">  
		        <li class="active"><a href="#tab_appLog">App登录信息</a></li>
		        <li class=""><a href="#tab_prjProgUpdateJD">进度更新信息</a></li>
		        <li class=""><a href="#tab_prjProgPhoto">图片更新信息</a></li>
		    </ul> 
		    <div class="tab_container">  
		        <div id="tab_appLog"" class="tab_content" style="display: block; "> 
		         	<jsp:include page="tab_appLog.jsp"></jsp:include>
		        </div> 
		        <div id="tab_prjProgUpdateJD" class="tab_content" style="display: block; "> 
		         	<jsp:include page="tab_prjProgUpdateJD.jsp"></jsp:include>
		        </div> 
		        <div id="tab_prjProgPhoto" class="tab_content" style="display: block; "> 
		         	<jsp:include page="tab_prjProgPhoto.jsp"></jsp:include>
		        </div> 
		    </div>  
		</div>
	</div>
</body>
</html>


