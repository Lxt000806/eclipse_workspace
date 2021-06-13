<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>查看销售客户信息</title>
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
                        <input type="hidden" id="code" name="code" value="${saleCust.code}" />
                        <input type="hidden" name="expired" id="expired" value="${saleCust.expired}" disabled="disabled" />
                        <ul class="ul-form">
                           <div class="validate-group">
                               <li class="form-validate">
                                   <label><span class="required">*</span>客户名称1</label>
                                   <input type="text" id="desc1" name="desc1" value="${saleCust.desc1.trim()}" disabled="disabled" />
                               </li>
                               <li class="form-validate">
                                   <label>地址</label>
                                   <input type="text" id="address" name="address" value="${saleCust.address.trim()}" disabled="disabled" />
                               </li>
                           </div>
                           <li class="form-validate">
                               <label>联系人</label>
                               <input type="text" id="contact" name="contact" value="${saleCust.contact.trim()}" disabled="disabled" />
                           </li>
                           <div class="validate-group">
                               <li class="form-validate">
                                   <label><span class="required">*</span>手机号码1</label>
                                   <input type="text" id="mobile1" name="mobile1" value="${saleCust.mobile1.trim()}" disabled="disabled" />
                               </li>
                           </div>
                           <li class="form-validate">
                               <label>手机号码2</label>
                               <input type="text" id="mobile2" name="mobile2" value="${saleCust.mobile2.trim()}" disabled="disabled" />
                           </li>
                           <li class="form-validate">
                               <label>传真1</label>
                               <input type="text" id="fax1" name="fax1" value="${saleCust.fax1.trim()}" disabled="disabled" />
                           </li>
                           <li class="form-validate">
                               <label>传真2</label>
                               <input type="text" id="fax2" name="fax2" value="${saleCust.fax2.trim()}" disabled="disabled" />
                           </li>
                           <li class="form-validate">
                               <label>电话1</label>
                               <input type="text" id="phone1" name="phone1" value="${saleCust.phone1.trim()}" disabled="disabled" />
                           </li>
                           <li class="form-validate">
                               <label>电话2</label>
                               <input type="text" id="phone2" name="phone2" value="${saleCust.phone2.trim()}" disabled="disabled" />
                           </li>
                           <li class="form-validate">
                               <label>邮件地址1</label>
                               <input type="text" id="email1" name="email1" value="${saleCust.email1.trim()}" disabled="disabled" />
                           </li>
                           <li class="form-validate">
                               <label>邮件地址2</label>
                               <input type="text" id="email2" name="email2" value="${saleCust.email2.trim()}" disabled="disabled" />
                           </li>
                           <li class="form-validate">
                               <label>QQ</label>
                               <input type="text" id="qq" name="qq" value="${saleCust.qq.trim()}" disabled="disabled" />
                           </li>
                           <li class="form-validate">
                               <label>MSN</label>
                               <input type="text" id="msn" name="msn" value="${saleCust.msn.trim()}" disabled="disabled" />
                           </li>
                           <li class="form-validate">
                               <label>纪念日1</label>
                               <input type="text" id="remDate1" name="remDate1" class="i-date"
                                    onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                                    value="${saleCust.remDate1.trim()}" disabled="disabled" />
                           </li>
                           <li class="form-validate">
                               <label>纪念日2</label>
                               <input type="text" id="remDate2" name="remDate2" class="i-date"
                                    onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                                    value="${saleCust.remDate2.trim()}" disabled="disabled" />
                           </li>
                           <li class="form-validate">
                             <label class="control-textarea">备注</label>
                             <textarea id="remarks" name="remarks" rows="4" style="width:450px;" disabled="disabled" >${saleCust.remarks}</textarea>
                            </li>
                            <li class="form-validate">
                             <label>过期</label>
                             <input type="checkbox" onclick="checkExpired(this)" ${saleCust.expired.equals('T')?'checked':''} disabled="disabled"  />
                            </li>
                            
                        </ul>
                    </form>
                </div>
            </div>
        </div>
    </body>
    </html>
