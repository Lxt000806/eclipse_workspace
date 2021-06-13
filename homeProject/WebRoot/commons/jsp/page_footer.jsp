<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${resourceRoot}/js/iss.core.js"></script>

<script type="text/javascript">
	//需要再分页前执行自己的函数，请自己重写方法beforePage
	function jump_page(pageNo){
		var r = /^\+?[1-9][0-9]*$/;　　//正整数 
		if(typeof(window.beforePage) == "function"){
			beforePage();
		}
		if(typeof(pageNo) == "undefined" || Number(pageNo) == 'NaN'|| pageNo < 1 || !r.test(pageNo)){
			pageNo = 1;
		}else{
			var totalPage = parseInt('${page.totalPages }');
			if(pageNo > totalPage){
				pageNo = totalPage;
			}
		}
		
		var pageSize = $("#page_size").val();
		
		if(typeof(pageSize) == "undefined" || Number(pageSize) == 'NaN'|| pageSize < 1 || !r.test(pageSize)){
			pageSize = 10;
		}

		//设置当前请页数
		//$("input[type=hidden][name=pageNo]", $form).val(pageNo);

		//设置每页数
		//var $pageSize = $("input[type=hidden][name=pageSize]", $form).val($();
		$.form_submit($("#" + "${param.form_id }").get(0), {
			"action" : "${param.action }",
			"pageNo" : pageNo,
			"pageSize" : pageSize
		});
	}
	
	$(function(){
		$("#jump_page_input").keydown(function(event){
			if(event.keyCode == 13){
				jump_page($('#jump_page_input').val());
			}
		});
		
		$("#page_size").keydown(function(event){
			if(event.keyCode == 13){
				jump_page(1);
			}
		});
		
		$("#page_form input[type='text']").keydown(function(event){
			if(event.keyCode == 13){
				jump_page(1);
			}
		});
	})
	
</script>
<div class="pagebar" form_name="${form_name }">
	<div class="pages">
		<span>第[&nbsp;<font color="red">${page.pageNo }</font>&nbsp;]页 &nbsp;&nbsp;共[&nbsp;<font color="red">${page.totalCount }</font>&nbsp;]条&nbsp;&nbsp; 共[&nbsp;<font color="red">${page.totalPages }</font>&nbsp;]页&nbsp;&nbsp;&nbsp;&nbsp;每页<input type="text" id="page_size" name="pageSize" value="${page.pageSize }" class="pageSzie" />条
		</span>
	</div>
	<div class="pagination">
		<ul>
		<c:if test="${page.pageNo <=1}">
			<li class="disabled j-first" >
				<span class="first" disabled="disabled">
					<span>首页</span>
				</span>
			</li>
		</c:if>
		<c:if test="${page.pageNo >1}">
			<li class="j-first" name="page-li-click">
				<a class="first" onclick="jump_page(1); return false;">
					<span>首页</span>
				</a>
			</li>
		</c:if>

		<c:if test="${page.hasPre}">
			<li class="j-prev" name="page-li-click">
				<a class="previous" onclick="jump_page(${page.pageNo -1 }); return false;">
					<span>
						上一页
					</span>
				</a>
			</li>
		</c:if>
		<c:if test="${!page.hasPre}">
			<li class="disabled j-prev">
				<span class="previous" disabled="disabled">
					<span>
						上一页
					</span>
				</span>
			</li>
		</c:if>

		<c:if test="${page.hasNext}">
			<li class="j-next" name="page-li-click">
				<a class="next" onclick="jump_page(${page.pageNo +1}); return false;">
					<span>
						下一页
					</span>
				</a>
			</li>
		</c:if>

		<c:if test="${!page.hasNext}">
			<li class="disabled j-next">
				<span class="next" disabled="disabled">
					<span>
						下一页 
					</span>
				</span>
			</li>
		</c:if>

		<c:if test="${page.pageNo < page.totalPages}">
			<li class="j-last" name="page-li-click">
				<a class="last" onclick="jump_page(${page.totalPages }); return false;">
					<span>
						末页
					</span>
				</a>
			</li>
		</c:if>
		<c:if test="${page.pageNo >= page.totalPages}">
			<li class="disabled j-last">
				<span class="last" disabled="disabled">
					<span>
						末页
					</span>
				</span>

			</li>
		</c:if>

			<li class="jumpto">
				<input class="textInput" type="text" value="${page.pageNo}" id="jump_page_input" style="line-height:13px;" />
				<input class="goto" type="button" value="确定" onclick="jump_page(document.getElementById('jump_page_input').value); return false;" />
			</li>

		</ul>
	</div><!--pagination end-->
</div><!--pagebar end-->
