/**
 * iss.cssTable.js
 */
(function($){
	$.fn.extend({
		pageBar: function(options){
			return this.each(function(){
				var $this = $(this);
				var $lis = $this.find("ul>li[name = 'page-li-click']");

				$lis.hoverClass("page_hover").each(function(index_1_){
					var $li = $(this);
				});

			});//pageBar end~~
		}
	});
})(jQuery);

