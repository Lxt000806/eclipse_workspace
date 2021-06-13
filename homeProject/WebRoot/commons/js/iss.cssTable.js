/**
 * iss.cssTable.js
 */
(function($) {
	$.fn.extend({
		cssTable : function(options) {

			return this.each(function() {

				var $this = $(this);
				var $trs = $this.find('tbody>tr');

				$trs.hoverClass("hover").each(function(index_1_) {
					var $tr = $(this);

					$tr.click(function() {
						$trs.filter(".selected").removeClass("selected");
						$tr.addClass("selected");

					});
				});

			});
		},// end cssTable~~~

		resetFixTableHeight : function() {

			return this.each(function(index) {

				var $bodyBoxList = $("div.body-box-list");
				var $queryForm = $bodyBoxList.find("div.query-form");

				var $pageContent = $bodyBoxList.find("div.pageContent");
				var $panelBar = $pageContent.find("div.panelBar");
				var $pagebar = $pageContent.find("div.pagebar");
				var $contentList = $pageContent.find("div.content-list");
				var $table = $pageContent.find("table");

					$bodyBoxList.height($.clientHeight() - 4);

					$pageContent.height($bodyBoxList.height()
							- $queryForm.height());
					$contentList.height($pageContent.height()
							- $panelBar.height() - $pagebar.height() - 2);

			});

		}// end resetTableHeight~~~

	});
})(jQuery);
