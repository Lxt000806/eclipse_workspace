<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>系统代码明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
</head>
<body>
<div class="body-box-form">
	<div class="content-form">
		<!--panelBar-->
		<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
			</div>
			</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
			     <input type="hidden" id="expired" name="expired" value="${xtdm.expired}"/>
				<ul class="ul-form">
					<li>
						<label><span class="required">*</span>对象ID</label>
						<input type="text" style="width:160px;" id="id" name="id" value="${xtdm.id }"/> 
					</li>	
					<li>
						<label><span class="required">*</span>字母编码</label>
						<input type="text" style="width:160px;" id="cbm" name="cbm" value="${xtdm.cbm }"/> 
					</li>	
					<li>
						<label><span class="required">*</span>数字编码</label>
						<input type="text" style="width:160px;" id="ibm" name="ibm" value="${xtdm.ibm }"/> 
					</li>	
					<li>
						<label><span class="required">*</span>中文对象说明</label>
						<input type="text" style="width:160px;" id="idnote" name="idnote" value="${xtdm.idnote }"/> 
					</li>	
					<li>
						<label><span class="required">*</span>中文含义</label>
						<input type="text" style="width:160px;" id="note" name="note" value="${xtdm.note }"/> 
					</li>	
					<li>
						<label><span class="required">*</span>英文含义</label>
						<input type="text" style="width:160px;" id="noteE" name="noteE" value="${xtdm.noteE }"/> 
					</li>	
					<li>
						<label><span class="required">*</span>英文对象说明</label>
						<input type="text" style="width:160px;" id="idnoteE" name="idnoteE" value="${xtdm.idnoteE }"/>
					</li>
					<li>
                        <label>过期</label>
                        <input type="checkbox" name="expiredCheckbox" ${xtdm.expired == 'T' ? 'checked' : ''}
                            onclick="checkExpired(this)" disabled/>
                    </li>
				</ul>
			</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>

