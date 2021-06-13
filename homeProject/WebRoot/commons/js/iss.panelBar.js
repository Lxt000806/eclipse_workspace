/**
 * iss.cssTable.js
 */
(function($){
	$.fn.extend({
		panelBar: function(options){
			return this.each(function(){
				var $this = $(this);
				var $lis = $this.find("ul>li[class != 'line']");

				$lis.hoverClass("hover").each(function(index_1_){
					var $li = $(this);
				});

			});
		}
	});
})(jQuery);

