<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>查看毛利参数</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <%@ include file="/commons/jsp/common.jsp" %>

<style type="text/css">
label{
    width:140px !important;   
}
</style>

<script type="text/javascript">
$(function(){
    //通过js选择器获取dataForm表单下的所有input后代，并添加disabled属性
    document.querySelectorAll("#dataForm input")
            .forEach(
	               function(input){
	                   input.disabled="disabled";
	              }
             );
});

</script>

</head> 

    <body>
        <div class="body-box-form">
            <div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(true)">
                            <span>关闭</span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="panel panel-info">
                <div class="panel-body">
                    <form action="" method="post" class="form-search">
                        <ul class="ul-form">
                           <li class="form-validate">
                               <label><span class="required">*</span>编号</label>
                               <input type="text" id="pk" name="pk" value="${profitPara.pk}" disabled="disabled" />
                           </li>
                        </ul>
                    </form>
                </div>
            </div>
            <div class="container-fluid">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab">主项目</a></li>
                </ul>
                <div class="tab-content">
                    <div id="tab_main" class="tab-pane fade in active">
                        <form action="" method="post" id="dataForm" class="form-search">
                            <input type="hidden" id="pk" name="pk" value="${profitPara.pk}" />
	                        <input type="hidden" name="expired" id="expired" value="${profitPara.expired}" />
	                        <br>
	                        <ul class="ul-form">
	                           <div class="validate-group">
		                           <li class="form-validate">
		                               <label><span class="required">*</span>设计费提成比例</label>
		                               <input type="text" id="designCalPer" name="designCalPer" value="${profitPara.designCalPer}" />
		                           </li>
		                           <li class="form-validate">
	                                   <label><span class="required">*</span>基础发包比例控制</label>
	                                   <input type="text" id="jobCtrl" name="jobCtrl" value="${profitPara.jobCtrl}" />
	                               </li>
                               </div>
                               <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>预提费用比例</label>
	                                   <input type="text" id="costPer" name="costPer" value="${profitPara.costPer}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>集成预估毛利率</label>
	                                   <input type="text" id="intProPer" name="intProPer" value="${profitPara.intProPer}" />
	                               </li>
	                           </div>
	                           <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>基础提成比例</label>
	                                   <input type="text" id="baseCalPer" name="baseCalPer" value="${profitPara.intProPer}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>集成提成比例</label>
	                                   <input type="text" id="intCalPer" name="intCalPer" value="${profitPara.intCalPer}" />
	                               </li>
                               </div>
                               <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>主材提成比例</label>
	                                   <input type="text" id="mainCalPer" name="mainCalPer" value="${profitPara.mainCalPer}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>橱柜预估毛利率</label>
	                                   <input type="text" id="cupProPer" name="cupProPer" value="${profitPara.cupProPer}" />
	                               </li>
                               </div>
                               <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>服务性产品预估毛利率</label>
	                                   <input type="text" id="servProPer" name="servProPer" value="${profitPara.servProPer}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>橱柜提成比例</label>
	                                   <input type="text" id="cupCalPer" name="cupCalPer" value="${profitPara.cupCalPer}" />
	                               </li>
                               </div>
                               <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>服务性产品提成比例</label>
	                                   <input type="text" id="servCalPer" name="servCalPer" value="${profitPara.servCalPer}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>软装预估毛利率</label>
	                                   <input type="text" id="softProPer" name="softProPer" value="${profitPara.softProPer}" />
	                               </li>
                               </div>
                               <div class="validate-group">
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>发包低比例</label>
	                                   <input type="text" id="jobLowPer" name="jobLowPer" value="${profitPara.jobLowPer}" />
	                               </li>
	                               <li class="form-validate">
	                                   <label><span class="required">*</span>软装提成比例</label>
	                                   <input type="text" id="softCalPer" name="softCalPer" value="${profitPara.softCalPer}" />
	                               </li>
                               </div>
                               <li class="form-validate">
                                   <label><span class="required">*</span>发包高比例</label>
                                   <input type="text" id="jobHighPer" name="jobHighPer" value="${profitPara.jobHighPer}" />
                               </li>
	                        </ul>
	                    </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
