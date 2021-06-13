(function($){
	$.fn.extend({
		fillContent: function(msg){//输入框初始化，msg初始化内容,包括密码输入框
			var $this = $(this);
			var $type = $this.attr("type");
			var $width = $this.css("width");
			var passWord_id = $this.attr("id");
			var passWord_text_id = passWord_id+"_text";
			var $passWord_text = $("#"+passWord_text_id);
			if ($type == 'password'){
				$this.after("<input name=\""+passWord_text_id+"\" type=\"text\" id=\""+passWord_text_id+"\" style=\"width:"+$width+";display: none\"/>");
			}
			$this.focus(function(){
				if ($this.val() == msg){
					$this.val('');
					$this.css("color","#000000");
				}else{
					$this.css("color","#B7B7B7");
				}
			}).blur(function(){
				if ($.trim($this.val()) == ''){
					$this.val(msg);
					$this.css("color","#B7B7B7");
					if ($type == 'password'){
						$passWord_text = $("#"+passWord_text_id);
						$this.hide();
						$passWord_text.show().val(msg);
						$passWord_text.focus(function(){
							$passWord_text.hide();
							$this.show().focus();
						});
					}
				}else{
					$this.css("color","#000000");
				}
			});
			$this.blur();
		},
		fillMaxLength: function(maxLen){//maxLen单行或多行文本框输入汉字长度
			var $this = $(this);
			var $id = $this.attr("id");
			var $countId = $id+"_countNum";
			var $length = $this.val().length;
			$this.after("<div style=\"text-align: right;padding-right: 18px;color: grey\">已输入<span id=\""+$countId+"\">"+$length+"</span>/"+maxLen+"</div>");
			$this.keyup(function(){
				$length = $this.val().length;
				if ($length > maxLen){
					$this.val($this.val().substr(0,maxLen));
					$("#"+$countId).html($maxLen);
				}else{
					$("#"+$countId).html($length);
				}
			});
		}
	});
})(jQuery);
