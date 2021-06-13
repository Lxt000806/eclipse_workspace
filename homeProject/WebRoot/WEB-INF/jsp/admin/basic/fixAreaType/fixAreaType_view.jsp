<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>查看装修区域类型</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

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
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<ul class="ul-form">
						   <li class="form-validate">
		                       <label><span class="required">*</span>编号</label>
		                       <input type="text" id="code" name="code" value="${fixAreaType.code}" disabled="disabled" />
	                       </li>
	                       <li class="form-validate">
	                           <label><span class="required">*</span>名称</label>
	                           <input type="text" id="descr" name="descr" value="${fixAreaType.descr}" disabled="disabled" />
	                       </li>
	                       <li class="form-validate">
                               <label>过期</label>
                               <input type="checkbox" disabled="disabled" ${fixAreaType.expired.equals('T')?'checked':''} />
                           </li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
	</html>
