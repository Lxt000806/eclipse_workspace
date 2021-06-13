/**
 * iss.core.js
 */
(function($){
	$.fn.extend({

		hoverClass: function(className){
			var _className = className || "hover";
			return this.each(function(){
				$(this).hover(function(){
					$(this).addClass(_className);
				},function(){
					$(this).removeClass(_className);
				});
			});
		} //hoverClass
		
	})
})(jQuery);
/** 
 *	使用5.3.2版本的ui.multiselect
 * 	add by zb on 20190123 
 */
function useNewMultiselect() {
	(function($){
		$.widget("ui.multiselect", {
		  	options: {
				// sortable: true, //关闭排序，否则prjPerfProfit_list会出错 modify by zb
				sortable: false,
				searchable: true,
				doubleClickable: true,
				animated: 'fast',
				show: 'slideDown',
				hide: 'slideUp',
				dividerLocation: 0.6,
				availableFirst: false,
				nodeComparator: function(node1,node2) {
					var text1 = node1.text(),
					    text2 = node2.text();
					return text1 == text2 ? 0 : (text1 < text2 ? -1 : 1);
				}
			},
			_create: function() {
				this.element.hide();
				this.id = this.element.attr("id");
				this.container = $('<div class="ui-multiselect ui-helper-clearfix ui-widget"></div>').insertAfter(this.element);
				this.count = 0; // number of currently selected options
				this.selectedContainer = $('<div class="selected"></div>').appendTo(this.container);
				this.availableContainer = $('<div class="available"></div>')[this.options.availableFirst?'prependTo': 'appendTo'](this.container);
				this.selectedActions = $('<div class="actions ui-widget-header ui-helper-clearfix"><span class="count">0 '+$.ui.multiselect.locale.itemsCount+'</span><a href="#" class="remove-all">'+$.ui.multiselect.locale.removeAll+'</a></div>').appendTo(this.selectedContainer);
				this.availableActions = $('<div class="actions ui-widget-header ui-helper-clearfix"><input type="text" class="search empty ui-widget-content ui-corner-all"/><a href="#" class="add-all">'+$.ui.multiselect.locale.addAll+'</a></div>').appendTo(this.availableContainer);
				this.selectedList = $('<ul class="selected connected-list"><li class="ui-helper-hidden-accessible"></li></ul>').bind('selectstart', function(){return false;}).appendTo(this.selectedContainer);
				this.availableList = $('<ul class="available connected-list"><li class="ui-helper-hidden-accessible"></li></ul>').bind('selectstart', function(){return false;}).appendTo(this.availableContainer);
				
				var that = this;

				// set dimensions
				this.container.width(this.element.width()+1);
				this.selectedContainer.width(Math.floor(this.element.width()*this.options.dividerLocation));
				this.availableContainer.width(Math.floor(this.element.width()*(1-this.options.dividerLocation)));

				// fix list height to match <option> depending on their individual header's heights
				this.selectedList.height(Math.max(this.element.height()-this.selectedActions.height(),1));
				this.availableList.height(Math.max(this.element.height()-this.availableActions.height(),1));
				
				if ( !this.options.animated ) {
					this.options.show = 'show';
					this.options.hide = 'hide';
				}
				this.useProp = !!$.fn.prop;
				// init lists
				this._populateLists(this.element.find('option'));
				
				// make selection sortable
				if (this.options.sortable) {
					this.selectedList.sortable({
						placeholder: 'ui-state-highlight',
						axis: 'y',
						update: function(event, ui) {
							// apply the new sort order to the original selectbox
							that.selectedList.find('li').each(function() {
								if ($(this).data('optionLink'))
									$(this).data('optionLink').remove().appendTo(that.element);
							});
						},
						receive: function(event, ui) {
							ui.item.data('optionLink')[ this.useProp ? 'prop' : 'attr' ]('selected', true);
							// increment count
							that.count += 1;
							that._updateCount();
							// workaround, because there's no way to reference 
							// the new element, see http://dev.jqueryui.com/ticket/4303
							that.selectedList.children('.ui-draggable').each(function() {
								$(this).removeClass('ui-draggable');
								$(this).data('optionLink', ui.item.data('optionLink'));
								$(this).data('idx', ui.item.data('idx'));
								that._applyItemState($(this), true);
							});
					
							// workaround according to http://dev.jqueryui.com/ticket/4088
							setTimeout(function() { ui.item.remove(); }, 1);
						}
					});
				}
				
				// set up livesearch
				if (this.options.searchable) {
					this._registerSearchEvents(this.availableContainer.find('input.search'));
				} else {
					$('.search').hide();
				}
				
				// batch actions
				this.container.find(".remove-all").click(function() {
					that._populateLists(that.element.find('option').removeAttr('selected'));
					return false;
				});
				
				this.container.find(".add-all").click(function() {
					var options = that.element.find('option').not(":selected");
					if (that.availableList.children('li:hidden').length > 1) {
						that.availableList.children('li').each(function(i) {
							if ($(this).is(":visible")) $(options[i-1])[ that.useProp ? 'prop' : 'attr' ]('selected', true); 
						});
					} else {
						options[ that.useProp ? 'prop' : 'attr' ]('selected', true);
					}
					that._populateLists(that.element.find('option'));
					return false;
				});
			},
			destroy: function() {
				this.element.show();
				this.container.remove();

				$.Widget.prototype.destroy.apply(this, arguments);
			},
			_populateLists: function(options) {
				this.selectedList.children('.ui-element').remove();
				this.availableList.children('.ui-element').remove();
				this.count = 0;

				var that = this;
				var items = $(options.map(function(i) {
			      var item = that._getOptionNode(this).appendTo(this.selected ? that.selectedList : that.availableList).show();

					if (this.selected) that.count += 1;
					that._applyItemState(item, this.selected);
					item.data('idx', i);
					return item[0];
		    }));
				
				// update count
				this._updateCount();
				that._filter.apply(this.availableContainer.find('input.search'), [that.availableList]);
		  },
			_updateCount: function() {
				this.element.trigger('change');
				// this.selectedContainer.find('span.count').text(this.count+" "+$.ui.multiselect.locale.itemsCount);
				this.selectedContainer.find('span.count').text($.ui.multiselect.locale.itemsCount1+this.count+$.ui.multiselect.locale.itemsCount2);
			},
			_getOptionNode: function(option) {
				option = $(option);
				var node = $('<li class="ui-state-default ui-element" title="'+option.text()+'"><span class="ui-icon"/>'+option.text()+'<a href="#" class="action"><span class="ui-corner-all ui-icon"/></a></li>').hide();
				node.data('optionLink', option);
				return node;
			},
			// clones an item with associated data
			// didn't find a smarter away around this
			_cloneWithData: function(clonee) {
				var clone = clonee.clone(false,false);
				clone.data('optionLink', clonee.data('optionLink'));
				clone.data('idx', clonee.data('idx'));
				return clone;
			},
			_setSelected: function(item, selected) {
				item.data('optionLink')[ this.useProp ? 'prop' : 'attr' ]('selected', selected);

				if (selected) {
					var selectedItem = this._cloneWithData(item);
					item[this.options.hide](this.options.animated, function() { $(this).remove(); });
					selectedItem.appendTo(this.selectedList).hide()[this.options.show](this.options.animated);
					
					this._applyItemState(selectedItem, true);
					return selectedItem;
				} else {
					
					// look for successor based on initial option index
					var items = this.availableList.find('li'), comparator = this.options.nodeComparator;
					var succ = null, i = item.data('idx'), direction = comparator(item, $(items[i]));

					// test needed for dynamic list populating
					if ( direction ) {
						while (i>=0 && i<items.length) {
							direction > 0 ? i++ : i--;
							if ( direction != comparator(item, $(items[i])) ) {
								// going up, go back one item down, otherwise leave as is
								succ = items[direction > 0 ? i : i+1];
								break;
							}
						}
					} else {
						succ = items[i];
					}
					
					var availableItem = this._cloneWithData(item);
					succ ? availableItem.insertBefore($(succ)) : availableItem.appendTo(this.availableList);
					item[this.options.hide](this.options.animated, function() { $(this).remove(); });
					availableItem.hide()[this.options.show](this.options.animated);
					
					this._applyItemState(availableItem, false);
					return availableItem;
				}
			},
			_applyItemState: function(item, selected) {
				// 修改因拖动添加造成的长度缩短 add by zb
				item.prop("style","");
				if (selected) {
					if (this.options.sortable)
						item.children('span').addClass('ui-icon-arrowthick-2-n-s').removeClass('ui-helper-hidden').addClass('ui-icon');
					else
						item.children('span').removeClass('ui-icon-arrowthick-2-n-s').addClass('ui-helper-hidden').removeClass('ui-icon');
					item.find('a.action span').addClass('ui-icon-minus').removeClass('ui-icon-plus');
					this._registerRemoveEvents(item.find('a.action'));
					
				} else {
					item.children('span').removeClass('ui-icon-arrowthick-2-n-s').addClass('ui-helper-hidden').removeClass('ui-icon');
					item.find('a.action span').addClass('ui-icon-plus').removeClass('ui-icon-minus');
					this._registerAddEvents(item.find('a.action'));
				}
				
				this._registerDoubleClickEvents(item);
				this._registerHoverEvents(item);
			},
			// taken from John Resig's liveUpdate script
			_filter: function(list) {
				var input = $(this);
				var rows = list.children('li'),
					cache = rows.map(function(){
						
						return $(this).text().toLowerCase();
					});
				
				var term = $.trim(input.val().toLowerCase()), scores = [];
				
				if (!term) {
					rows.show();
				} else {
					rows.hide();

					cache.each(function(i) {
						if (this.indexOf(term)>-1) { scores.push(i); }
					});

					$.each(scores, function() {
						$(rows[this]).show();
					});
				}
			},
			_registerDoubleClickEvents: function(elements) {
				if (!this.options.doubleClickable) return;
				elements.dblclick(function(ev) {
					if ($(ev.target).closest('.action').length === 0) {
						// This may be triggered with rapid clicks on actions as well. In that
						// case don't trigger an additional click.
						elements.find('a.action').click();
					}
				});
			},
			_registerHoverEvents: function(elements) {
				elements.removeClass('ui-state-hover');
				elements.mouseover(function() {
					$(this).addClass('ui-state-hover');
				});
				elements.mouseout(function() {
					$(this).removeClass('ui-state-hover');
				});
			},
			_registerAddEvents: function(elements) {
				var that = this;
				elements.click(function() {
					var item = that._setSelected($(this).parent(), true);
					that.count += 1;
					that._updateCount();
					return false;
				});
				
				// make draggable
				if (this.options.sortable) {
					elements.each(function() {
						$(this).parent().draggable({
							connectToSortable: that.selectedList,
							helper: function() {
								var selectedItem = that._cloneWithData($(this)).width($(this).width() - 50);
								selectedItem.width($(this).width());
								return selectedItem;
							},
							appendTo: that.container,
							containment: that.container,
							revert: 'invalid'
						});
					});		  
				}
			},
			_registerRemoveEvents: function(elements) {
				var that = this;
				elements.click(function() {
					that._setSelected($(this).parent(), false);
					that.count -= 1;
					that._updateCount();
					return false;
				});
		 	},
			_registerSearchEvents: function(input) {
				var that = this;

				input.focus(function() {
					$(this).addClass('ui-state-active');
				})
				.blur(function() {
					$(this).removeClass('ui-state-active');
				})
				.keypress(function(e) {
					if (e.keyCode == 13)
						return false;
				})
				.keyup(function() {
					that._filter.apply(this, [that.availableList]);
				});
			}
		});
				
		$.extend($.ui.multiselect, {
			// 替换为中文 modify by zb
			/*locale: {
				addAll:'Add all',
				removeAll:'Remove all',
				itemsCount:'items selected'
			}*/
			locale: {
				addAll:'添加所有列',
				removeAll:'移除所有列',
				itemsCount1:'已选择',
				itemsCount2:'条列',
			}
		});
	})(jQuery);
}

(function($){
	$.extend({   

		clientHeight: function(){
			var winHeight;
			if (window.innerHeight) winHeight = window.innerHeight; 
			else if ((document.body) && (document.body.clientHeight)) 
				winHeight = document.body.clientHeight; 

			//计算window高度
			if (document.documentElement 
				&& document.documentElement.clientHeight){winHeight = document.documentElement.clientHeight;}

			return winHeight;
		},
		
		/** window.open 参数对象{url:'',width:'',height:'',vArguments:{}}*/
		showModalDialog: function(param){
			var sUrl = param.url;
			var sWidth = param.width;
			var sHeight = param.height;
			var vArguments = param.vArguments;
			
			if(typeof(vArguments) == "undefined" || vArguments == null){
				vArguments = window.self;
			}

			if(typeof(sWidth) == "undefined"){
				sWidth = screen.availWidth*0.65;
			}else{
				sWidth = parseInt(sWidth);
			}

			if(typeof(sHeight) == "undefined"){
				sHeight = screen.availHeight*0.85;
			}else{
				sHeight = parseInt(sHeight);
			}
			
			sFeatures = "dialogHeight:"+sHeight+"px;dialogWidth:"+ sWidth +"px;status:yes;scroll:yes;resizable:no;center:yes";
			return window.showModalDialog(sUrl, vArguments, sFeatures);
		},

		/**设定固定大小*/
		showModalDialogFix_min: function(sUrl, vArguments){
			var sHeight = screen.availHeight * 0.55;
			var sWidth = screen.availWidth * 0.85;
			
			return $.showModalDialog({url:sUrl, width:sWidth, height:sHeight, vArguments:vArguments});
		},

		/**设定固定大小*/
		showModalDialogFix_mid: function(sUrl, vArguments){
			var sHeight = screen.availHeight * 0.65;
			var sWidth = screen.availWidth * 0.85;

			return $.showModalDialog({url:sUrl, width:sWidth, height:sHeight, vArguments:vArguments});
		},


		/**设定固定大小*/
		showModalDialogFix_max: function(sUrl, vArguments){
			var sHeight = screen.availHeight * 0.75;
			var sWidth = screen.availWidth * 0.85;

			return $.showModalDialog({url:sUrl, width:sWidth, height:sHeight, vArguments:vArguments});
		},
		
		/** window.open 参数对象{url:'',width:'',height:''}*/
		fnShowWindow: function(param){
			param.height = parseInt(param.height);
			param.width = parseInt(param.width);
			param.url = $.trim(param.url);
			var sTop = (window.screen.availHeight - param.height)/2;
			var sLeft = (window.screen.availWidth - param.width)/2;
			var sFeatures = "height="+param.height+", width="+param.width+", top="+sTop+", left="+sLeft+", scrollbars=yes, toolbar=no, menubar=no, resizable=yes, location=no, status=yes"
			openShadow();
			var oNewWindow = window.open(param.url, "_blank", sFeatures);
			if(parseInt(navigator.appVersion) >= 4){oNewWindow.window.focus();}
			return oNewWindow;
		},
		
		/**window.open 打开小窗口*/
		fnShowWindow_small: function(sUrl, sFeatures){
			var sHeight = screen.availHeight * 0.55;
			var sWidth = screen.availWidth * 0.65;
			return $.fnShowWindow({url:sUrl, width:sWidth, height:sHeight});
		},
		
		/**window.open 打开小窗口*/
		fnShowWindow_min: function(sUrl, sFeatures){
			var sHeight = screen.availHeight * 0.55;
			var sWidth = screen.availWidth * 0.85;
			return $.fnShowWindow({url:sUrl, width:sWidth, height:sHeight});
		},

		/**window.open 打开中型窗口*/
		fnShowWindow_mid: function(sUrl, sFeatures){
			var sHeight = screen.availHeight * 0.65;
			var sWidth = screen.availWidth * 0.85;
			return $.fnShowWindow({url:sUrl, width:sWidth, height:sHeight});
			
		},
		
		/**window.open 打开code窗口*/
		fnShowWindow_code: function(sUrl, sFeatures){
			var sHeight = screen.availHeight * 0.65;
			var sWidth = screen.availWidth * 0.65;
			return $.fnShowWindow({url:sUrl, width:sWidth, height:sHeight});
			
		},

		/**window.open 打开大窗口*/
		fnShowWindow_max: function(sUrl, sFeatures){
			var sHeight = screen.availHeight * 0.75;
			var sWidth = screen.availWidth * 0.85;
			return $.fnShowWindow({url:sUrl, width:sWidth, height:sHeight});
		},
		
		/**判断是否是非法字符**/
		resolvChar: function(str){
			/*if (str.length == 1){
				if( str == '&' || str == '<'
				  || str == '>' || c == '\''|| c == '\"'
				  || str == '%'
				  || str == '|' 
				  || str == '%' 
				  || str == '＆' || str == '＜'
				  || str == '＞' ){
					return true;
				}
		   	}else {
			  	for (i = 0; i < str.length; i++){
					var c = str.charAt(i);
					if( c == '&' || c == '<'
					  || c == '>' || c == '\''|| c == '\"'
					  || c == '%'
					  || str == '|' 
					  || c == '%' 
					  || c == '＆' || c == '＜'
					  || c == '＞' ){
						return true;
					}
				}
			}*/

			//判断是否包含html标签
			var htmlReg = /<.*?>/g;
			if(htmlReg.test(str)){ return true; }

			return false;
		},
		
		/**重新设置token*/
		resetFormToken: function(XMLHttpRequest){
			if(XMLHttpRequest.token && XMLHttpRequest.token.token){
				$("#_form_token_uniq_id").val(XMLHttpRequest.token.token);//重写token到表单
			}
			else{
				$.ajax({
						url: "formToken/newToken",
						type: 'post',
						data: null,
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						timeout: 10000,
						dataType: 'json',
						cache: false,
						error: function(){
						},
						success: function(XMLHttpRequest){
							if(XMLHttpRequest.token && XMLHttpRequest.token.token){
								$("#_form_token_uniq_id").val(XMLHttpRequest.token.token);//重写token到表单
							}
						}
				});
			}
		},

		//列表查询提交
		form_submit: function(submit_form, param){
			if(param.action){ submit_form.action = param.action; }
			if(param.method){ submit_form.method = param.method; }
			if(param.pageNo){ $("input[name=pageNo]", $(submit_form)).val(param.pageNo); }
			if(param.pageSize){ $("input[name=pageSize]", $(submit_form)).val(param.pageSize); }
			if(param.pageOrderBy){ $("input[name=pageOrderBy]", $(submit_form)).val(param.pageOrderBy); }
			if(param.pageOrder){ $("input[name=pageOrder]", $(submit_form)).val(param.pageOrder); }
			if(param.jsonString){ $("input[name=jsonString]", $(submit_form)).val(param.jsonString); }
			
			submit_form.submit();
		},
		// 判断是不是chrome浏览器 add by zzr 2019/03/05
		isChrome: function(){
			var userAgent = navigator.userAgent;
			if(userAgent && userAgent.toLowerCase().indexOf("chrome") != -1){
				return true;
			}
			return false;
		},
		// 采用xmlHttpRequest下载Excel add by zzr 2019/03/05
		downloadExcel_xmlHttpRequest: function(submit_form, param, loadEle){
			var formData = new FormData();
			if(param.pageNo){ $("input[name=pageNo]", $(submit_form)).val(param.pageNo); }
			if(param.pageSize){ $("input[name=pageSize]", $(submit_form)).val(param.pageSize); }
			if(param.pageOrderBy){ $("input[name=pageOrderBy]", $(submit_form)).val(param.pageOrderBy); }
			if(param.pageOrder){ $("input[name=pageOrder]", $(submit_form)).val(param.pageOrder); }
			if(param.jsonString){ $("input[name=jsonString]", $(submit_form)).val(param.jsonString); }
			
			//将表单参数保存至formData对象
			var datas = $(submit_form).jsonForm();
			$.each(datas, function(index, value){
				formData.append(index, value);
			});
			
			//创建xhr对象 
			var xhrObject = {responseType: "blob"};
			var xhr = null;
			if (window.XMLHttpRequest){
				xhr = new XMLHttpRequest();
			}
			
			if(xhr == null){
				if(loadEle){
					loadEle.close();
				}
				art.dialog({
					content: "导出Excel异常"
				})
				return;
			}
			
			xhr.onloadstart = function(ev) {
				//设置响应返回的数据格式
			    xhr.responseType = xhrObject.responseType;
				//设置响应超时时间5分钟
				xhr.timeout = xhrObject.timeout;
			}
			//创建一个 post 请求，采用异步
			xhr.open("POST", param.action, true);
			
			//注册相关事件回调处理函数
			xhr.onload = function(e) {
				try{
					if(this.status == 200 || this.status == 304){
						var res = "response" in xhr ? xhr.response : xhr.responseText;
						var name = xhr.getResponseHeader("Content-Disposition");
						console.log(name);
						var filename = name.substring(20,name.length);
						var blob = new Blob([xhr.response], {type: "text/xls"});
						var csvUrl = URL.createObjectURL(blob);
						var link = document.createElement("a");
						link.href = csvUrl;
						link.download = decodeURI(filename).substring(1, decodeURI(filename).length-1);
						link.click();
					}
				}catch(e){
					art.dialog({
						content: "导出异常"
					})
				}finally{
					if(loadEle){
						loadEle.close();
					}
				}
			};
			//注册超时事件
			xhr.ontimeout = function(e) {
				if(loadEle){
					loadEle.close();
				}
				art.dialog({
					content: "导出Excel超时，请重试！"
				})
			};
			//注册异常事件
			xhr.onerror = function(e) {
				if(loadEle){
					loadEle.close();
				}
				art.dialog({
					content: "导出Excel异常，请重试！"
				})
			};
			//发送数据
			xhr.send(formData);
		}
		
	});
})(jQuery);
//导出当前EXCEL数据
function doExcelNow(preName,tableId,pageFormId,isDataTableCheckBox,selectData){
	var preName=preName?preName:"";
	if (!tableId || typeof(tableId)!="string"){
		tableId = "dataTable";
	}
	var pageFormId=pageFormId?pageFormId:"page_form";
	var colModel = $("#"+tableId).jqGrid('getGridParam','colModel');
	var rows=[];
	if(selectData){
		rows=selectData;
	}else{
		if(!isDataTableCheckBox){//非列名下拉筛选，导出所有
			rows = $("#"+tableId).jqGrid("getRowData");
		}else{
			var ids = $("#"+tableId).jqGrid("getDataIDs");
			for(var i=0;i<ids.length;i++){//列名下拉筛选，只导出筛选后的数据 update by cjg 
				if($("#"+tableId+" tbody tr[id="+ids[i]+"]").css("display")=="table-row"){
					var ret=$("#"+tableId).jqGrid('getRowData',ids[i]);
					rows.push(ret);
				}
			}
		}
	}
	
	if (!rows || rows.length==0){
		art.dialog({
			content: "无数据导出"
		});
		return;
	}
	var datas = {
		colModel: JSON.stringify(colModel),
		rows: JSON.stringify(rows),
		preName: preName
	};
	if($.isChrome()){
		$.downloadExcel_xmlHttpRequest(
			$("#"+pageFormId).get(0), 
			{
				"action": ctx+"/system/doExcel",
				"jsonString": JSON.stringify(datas)
			}, 
			art.dialog({
				content: "导出中...", 
				lock: true,
				esc: false,
				unShowOkButton: true
			})
		);
	}else{
		$.form_submit($("#"+pageFormId).get(0), {
			"action": ctx+"/system/doExcel",
			"jsonString": JSON.stringify(datas)
		});
	}
	
	//刷新token,可以连续导出Excel
	if($("#_form_token_uniq_id").length > 0){
		$.ajax({
			url: ctx+"/admin/formToken/newToken",
			type: 'post',
			data: null,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			timeout: 10000,
			dataType: 'json',
			cache: false,
			error: function(){
			},
			success: function(XMLHttpRequest){
				if(XMLHttpRequest.token && XMLHttpRequest.token.token){
					$("#_form_token_uniq_id").val(XMLHttpRequest.token.token);//重写token到表单
					$("input[name=jsonString]").val("");//清空jsonString——add by zb 2018-08-06
				}
			}
		});
	}
}
//导出查询的所有数据
function doExcelAll(url,tableId,pageFormId){
	if (!tableId || typeof(tableId)!="string"){
		tableId = "dataTable";
	}
	var pageFormId=pageFormId?pageFormId:"page_form";
	var colModel = $("#"+tableId).jqGrid('getGridParam','colModel');
	var rows = $("#"+tableId).jqGrid("getRowData");
	if (!rows || rows.length==0){
		art.dialog({
			content: "无数据导出"
		});
		return;
	}
	var datas = {
		colModel: JSON.stringify(colModel)
	};
	if($.isChrome()){
		$.downloadExcel_xmlHttpRequest(
			$("#"+pageFormId).get(0), 
			{
			"action": url,
			"jsonString": JSON.stringify(datas)
			}, 
			art.dialog({
				content: "导出中...", 
				lock: true,
				esc: false,
				unShowOkButton: true
			})
		);
	}else{
		$.form_submit($("#"+pageFormId).get(0), {
			"action": url,
			"jsonString": JSON.stringify(datas)
		});
	}
}
function frozenHeightReset(tableId){
	if (!tableId || typeof(tableId)!="string"){
		tableId = "dataTable";
	}
	$("#"+tableId+"_frozen tr").each(function(i){
		var h1 = $("#"+tableId+"_frozen tr:eq("+i+")").height();
		var h2 = $("#"+tableId+" tr:eq("+i+")").height();
		if (h1>h2){
			$("#"+tableId+" tr:eq("+i+")").height(h1-2);
		}
		if (h1<h2){
			$("#"+tableId+"_frozen tr:eq("+i+")").height(h2+2);
		}
	});
	$("#gview_"+tableId+" .ui-jqgrid-hdiv").css({"width":""});
	$("#gview_"+tableId+" .ui-jqgrid-bdiv").css({"width":""});
	
	// 固定列中存在checkbox时,由于input checkbox中z-index都为12,导致x轴溢出拖动时,复选框浮在上层 add by zzr 2020/07/02
	$("#gview_"+tableId+" .ui-jqgrid-hdiv:not(.frozen-div) input[type=checkbox]").css({"z-index": "0"});
	$("#gview_"+tableId+" .ui-jqgrid-bdiv:not(.frozen-bdiv) input[type=checkbox]").css({"z-index": "0"});
}
/** 垃圾回收 */
$(function(){
//	if ($.browser.msie) {
//		window.setInterval("CollectGarbage();", 5000);
//	}
	//如果窗口为子窗口，则关闭窗口删除掉遮罩层 add by liuxiaobin
	$(".form-search").bind('keydown',function(){
		if(window.event.keyCode==13 && event.target.type!="textarea")
			event.preventDefault();
	})
	document.body.onunload = closeWin;
	$('#page_form').attr('onsubmit','return false;');
	$('input','#page_form').keydown(function(e){
		if(e.keyCode==13){
			goto_query();
		}
	});
	
	//20191126 add by xzp 日期控件获得焦点时不要弹出输入过的记录
	$('input[type=text].i-date').attr('autocomplete', 'off');
});

//param.hidden: false/true(是否可显示),  param.msg: 显示信息
function showAjaxHtml(param){
	if(param.hidden){
		$("#saveBut").attr("disabled", "disabled");
		$("#infoBoxDiv").html("").css("display","none");
	}else{
		if(typeof(param.msg) == "undefined"){
			param.msg = "";
		}
		$("#saveBut").removeAttr("disabled");
	    $("#infoBoxDiv").html(param.msg).css("display","block");
	}
}
function openShadow(){
	if (window.parent){
		if (window.parent.document.getElementById("bgShadow")){
			window.parent.document.getElementById("bgShadow").style.display = "block";
		}else{
			if (window.parent.parent.document.getElementById("bgShadow")){
				window.parent.parent.document.getElementById("bgShadow").style.display = "block";
			}
		}
	}
}
function closeShadow(){
	if (window.opener && window.opener.parent){
		if (window.opener.parent.document.getElementById("bgShadow")){
			window.opener.parent.document.getElementById("bgShadow").style.display = "none";
		}else{
			if (window.opener.parent.parent.document.getElementById("bgShadow")){
				window.opener.parent.parent.document.getElementById("bgShadow").style.display = "none";
			}
		}
	}
}
function closeWin(isCallBack){
//	closeShadow();
//	window.close();
	Global.Dialog.closeDialog(isCallBack);
}
/**复选框单点选择**/
function check_all(){
	var ck_flag = $("#ck_all").attr("checked");

	$("div.content-list").find("table>tbody").find("input[type='checkbox']").each(function(){
		this.checked = ck_flag;
	});
}
/**隐藏过期记录
 * @param obj
 */
function hideExpired(obj){
	if ($(obj).is(':checked')){
		$('#expired').val('F');
	}else{
		$('#expired').val('T');
	}
}
/**是否过期
 * @param obj
 */
function checkExpired(obj){
	if ($(obj).is(':checked')){
		$('#expired').val('T');
	}else{
		$('#expired').val('F');
	}
}
/**
 * 字节长度，用法password.getByteLen()
 */
String.prototype.getByteLen = function(){
    var len = 0;
    for (var i = 0; i < this.length; i++){
        if (this.charCodeAt(i) > 255) len += 2;
        else len++;
    }
    return len;
}
/**
 * @param fetchId 返回值获取id
 * @param path 请求路径
 * @param ctx 上下文路径
 * @param params 参数json对象{}
 */
function fetch(fetchId,path,ctx,params){
	var str = "";
	if (typeof(params)=="object"){
		for(var x in params){
			str = str+"&"+x+"="+params[x];
		}
	}
	$.fnShowWindow_code(ctx+"/admin/"+path+"/goCode?fetchId="+fetchId+str);
}
/**
 * @param code 获取的编号
 * @param descr 获取的名称
 * @param fetchId 返回值获取id
 */
function getCode(code,descr,fetchId){
	if (window.opener){
		if (fetchId){
			var obj = window.opener.document.getElementById(fetchId);
			var objSpan = window.opener.document.getElementById(fetchId+'_span');
			if (obj){
				obj.value = code;
				if (objSpan){
					objSpan.innerText = descr;
				}
				window.close();
			}
		}
	}
}
/**
 * 排序
 * @param obj
 * @param a
 */
function paixu(obj,orderField){
	$("#pageOrderBy").val(orderField);
	if ($(obj).find("span").hasClass("desc")){
		$(".content-list table thead tr th span").removeClass();
		$("#pageOrder").val('asc');
	}else{
		$(".content-list table thead tr th span").removeClass();
		$("#pageOrder").val('desc');
	}
	goto_query();
}
/**一级部门联动
 * @param objSelf 触发对象
 * @param returnId 返回值id
 * @param ctx 上下文路径
 */
function changeDepartment1(objSelf,returnId,ctx){
	$.ajax({
		url:ctx+'/admin/department1/changeDepartment1?code='+objSelf.value,
		type:'post',
		data:{},
		dataType:'json',
		cache:false,
		async:false,
		error:function(obj){
			
		},
		success:function(obj){
			if (obj){
				$("#"+returnId).html(obj.strSelect).trigger("onchange");
				$("#"+returnId).searchableSelect();
			}
		}
	});
}
/**二级部门联动
 * @param objSelf 触发对象
 * @param returnId 返回值id
 * @param ctx 上下文路径
 */
function changeDepartment2(objSelf,returnId,ctx){
	$.ajax({
		url:ctx+'/admin/department2/changeDepartment2?code='+objSelf.value,
		type:'post',
		data:{},
		dataType:'json',
		cache:false,
		error:function(obj){
			
		},
		success:function(obj){
			if (obj){
				$("#"+returnId).html(obj.strSelect).trigger("onchange");
				$("#"+returnId).searchableSelect();
			}
		}
	});
}
/**
 * 一级部门联动多选
 * depType 部门类型
 */
function checkDepartment1(id,childId,thirdId,depType,czybh){
	id = id?id:'department1';
	childId = childId?childId:'department2';
	thirdId = thirdId?thirdId:'department3';
	//$.fn.zTree.init($("#tree_"+childId), "setting_"+childId, "zNodes_"+childId);
	if(depType === undefined){
		depType = '';
	}
	if(czybh === undefined){
		czybh = '';
	}
	var setting = "{\n" +
	"	check: {\n" +
	"		enable: true,\n" +
	"		chkboxType: {\"Y\":\"ps\", \"N\":\"ps\"}\n" +
	"	},\n" +
	"	view: {\n" +
	"		dblClickExpand: false\n" +
	"	},\n" +
	"	data: {\n" +
	"		simpleData: {\n" +
	"			enable: true,\n" +
	"		}\n" +
	"	},\n" +
	"	callback: {\n" +
	"		beforeCheck: beforeCheck_"+childId+",\n" +
	"		onCheck: onCheck_"+childId+"\n" +
	"	}\n" +
	"}\n\n"; 
	var datas = {"code" : $("#"+id).val(), "depType":depType,'czybh':czybh};
	$.ajax({
		url:ctx+'/admin/department1/changeDepartment1s',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
	
	    },
	    success: function(obj){
	    	if (obj){
	    		$("#"+childId).val('');
	    		$("#"+childId+"_NAME").val('');
	    		//$.fn.zTree.getZTreeObj("tree_"+childId).checkAllNodes(false);
	    		$("#"+thirdId).val('');
	    		$("#"+thirdId+"_NAME").val('');
	    		//$.fn.zTree.getZTreeObj("tree_"+thirdId).checkAllNodes(false);
	    		$.fn.zTree.init($("#tree_"+thirdId), {}, []);
	    		$.fn.zTree.init($("#tree_"+childId), eval("("+setting+")"), eval("("+obj.strSelect+")"));
			}
	    }
	});
}
/**
 * 二级部门联动多选
 */
function checkDepartment2(id,childId){
	id = id?id:'department2';
	childId = childId?childId:'department3';
    var setting = "{\n" +
	"	check: {\n" +
	"		enable: true,\n" +
	"		chkboxType: {\"Y\":\"ps\", \"N\":\"ps\"}\n" +
	"	},\n" +
	"	view: {\n" +
	"		dblClickExpand: false\n" +
	"	},\n" +
	"	data: {\n" +
	"		simpleData: {\n" +
	"			enable: true,\n" +
	"		}\n" +
	"	},\n" +
	"	callback: {\n" +
	"		beforeCheck: beforeCheck_"+childId+",\n" +
	"		onCheck: onCheck_"+childId+"\n" +
	"	}\n" +
	"}\n\n";
	var datas = {"code" : $("#"+id).val()};
	$.ajax({
		url:ctx+'/admin/department2/changeDepartment2s',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
	
	    },
	    success: function(obj){
	    	if (obj){
	    		$("#"+childId).val('');
	    		$("#"+childId+"_NAME").val('');
	    		$.fn.zTree.getZTreeObj("tree_"+childId).checkAllNodes(false);
	    		$.fn.zTree.init($("#tree_"+childId), eval("("+setting+")"), eval("("+obj.strSelect+")"));
			}
	    }
	 });
}

function checkItemType1(id,childId){
	id = id?id:'itemType1';
	childId = childId?childId:'itemType12';
	//$.fn.zTree.init($("#tree_"+childId), "setting_"+childId, "zNodes_"+childId);
	var setting = "{\n" +
	"	check: {\n" +
	"		enable: true,\n" +
	"		chkboxType: {\"Y\":\"ps\", \"N\":\"ps\"}\n" +
	"	},\n" +
	"	view: {\n" +
	"		dblClickExpand: false\n" +
	"	},\n" +
	"	data: {\n" +
	"		simpleData: {\n" +
	"			enable: true,\n" +
	"		}\n" +
	"	},\n" +
	"	callback: {\n" +
	"		beforeCheck: beforeCheck_"+childId+",\n" +
	"		onCheck: onCheck_"+childId+"\n" +
	"	}\n" +
	"}\n\n"; 
	var datas = {"code" : $("#"+id).val()};
	$.ajax({
		url:ctx+'/admin/itemType1/checkItemType1',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
	
	    },
	    success: function(obj){
	    	if (obj){
	    		$("#"+childId).val('');
	    		$("#"+childId+"_NAME").val('');
	    		$.fn.zTree.getZTreeObj("tree_"+childId).checkAllNodes(false);
	    		$.fn.zTree.init($("#tree_"+childId), eval("("+setting+")"), eval("("+obj.strSelect+")"));
			}
	    }
	 });
}

/**格式化日期yyyy-MM-dd
 * @param strTime
 * @returns {String}
 */
function formatDate(strTime) {
	if (strTime){
		var date = new Date(strTime);
		
		/* 20200328 add by xzp 
		 * 某些浏览器（如旧版的Chrome、Edge）new Date(676047600000)不精确，会少一天
		 * 676047600000对应的应该是1991-06-05，结果某些浏览器会当成1991-06-04
		 * 经测试，先toLocaleDateString再转成日期就没问题
		 */
		var s = date.toLocaleDateString(); 
		date = new Date(Date.parse(s.replace(/-/g,"/"))); 
		
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
	    return y+"-"
	    	+(m>9?m:'0'+m)+"-"
	    	+(d>9?d:'0'+d);
	}
	return "";
}
/**格式化日期yyyy-MM-dd HH:mm:ss
 * @param strTime
 * @returns {String}
 */
function formatTime(strTime) {
	if (strTime){
		var date = new Date(strTime);
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		var h = date.getHours();
		var mi = date.getMinutes();
		var s = date.getSeconds();
		if (strTime.length<=10){
			return y+"-"
	    	+(m>9?m:'0'+m)+"-"
	    	+(d>9?d:'0'+d)+" "
	    	+"00:00:00";
		}else{
			return y+"-"
	    	+(m>9?m:'0'+m)+"-"
	    	+(d>9?d:'0'+d)+" "
	    	+(h>9?h:'0'+h)+":"
	    	+(mi>9?mi:'0'+mi)+":"
	    	+(s>9?s:'0'+s);
		}
	}
	return "";
}

/**
 * 计算两个日期天数差的函数，通用
 * @param sDate1 后天
 * @param sDate2 前天
 * @returns 后天-前天
 */
function DateDiff(sDate1, sDate2) { 
    return parseInt(Math.floor(Date.parse(sDate1) - Date.parse(sDate2)) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
}

/**保留2位小数
 */
function formatterNumber(v, x, r) {
	return myRound(v, 2);
}

function formatWeekDay (strTime){ 
	if (strTime){
		var show_day=new Array('星期日','星期一','星期二','星期三','星期四','星期五','星期六'); 
		var date = new Date(strTime);
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		var day=date.getDay();
		var h = date.getHours();
		var mi = date.getMinutes();
		var s = date.getSeconds();
		var now_time=y+'/'+(m>9?m:'0'+m)+'/'+(d>9?d:'0'+d)+' '+' '+show_day[day]; 
		return now_time;
	}
	return "";
};
function formatWeekTime (strTime){ 
	if (strTime){
		var show_day=new Array('星期日','星期一','星期二','星期三','星期四','星期五','星期六'); 
		var date = new Date(strTime);
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		var day=date.getDay();
		var h = date.getHours();
		var mi = date.getMinutes();
		var s = date.getSeconds();
		var now_time=y+'/'+(m>9?m:'0'+m)+'/'+(d>9?d:'0'+d)+' '+' '+show_day[day]+' '
						+(h>9?h:'0'+h)+':'+(mi>9?mi:'0'+mi)+':'+(s>9?s:'0'+s); 
		return now_time;
	}
	return "";
};

function cutStr(str){
	if(!str || str == "null"){
		return "";
	}
	if (str.length>30){
		return str.substring(0,30)+"...";
	}
	return str;
}
//选择单行记录
function selectDataTableRow(tableId){
	if (!tableId || typeof(tableId)!="string"){
		tableId = "dataTable";
	}
	var ret;
	var id = $("#"+tableId).jqGrid('getGridParam', 'selrow');
    if (id) {
      var ret = $("#"+tableId).jqGrid('getRowData', id);
    }
    return ret;
}
//选择多行记录
function selectDataTableRows(tableId){
	if (!tableId || typeof(tableId)!="string"){
		tableId = "dataTable";
	}
	var ret=[];
	var ids = $("#"+tableId).jqGrid('getGridParam', 'selarrrow');
	if (ids){
		for (var i=0;i<ids.length;i++){
			ret.push($("#"+tableId).jqGrid('getRowData', ids[i]));
		}
	}
    return ret;
}
//刷新表格
function goto_query(tableId,formId){
	if (!tableId || typeof(tableId)!="string"){
		tableId = "dataTable";
	}
	if (!formId || typeof(formId)!="string"){
		formId = "page_form";
	}
	$(".s-ico").css("display","none");
	$("#"+tableId).jqGrid("setGridParam",{postData:$("#"+formId).jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
}
//清空查询条件
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
}
function clearDataForm(){
	$("#dataForm input[type='text']").val('');
	$("#dataForm select").val('');
	$("#dataForm textarea").val('');
}
/**
 * 清空表单和表格 --add by zb
 * @param pageFormId 表单ID：默认“page_form”
 * @param tableId 表格ID：不添加就不清空
 */
function clearFormTable(pageFormId,tableId){
	var pageFormId=pageFormId?pageFormId:"page_form";
	$("#"+pageFormId+" input[type='text']").val("");
	$("#"+pageFormId+" select").val("");
	$("#"+pageFormId+" textarea").val("");
	if (tableId) {
		$("#"+tableId).clearGridData();
	}
	$("#"+pageFormId).data("bootstrapValidator").resetForm();//重置所有验证
}
/**
 * 将form .ul-from中的组件全部disabled --add by zb
 * @param pageFormId 表单ID
 * @param disable 状态
 * @returns
 */
function disabledForm(pageFormId, disable){
	var pageFormId=pageFormId?pageFormId:"page_form";
    if(disable === undefined){
		disable = true;
	}
	return $("#"+pageFormId+" .ul-form").each(function(){
		$("input,select,textarea,button", this).each(function(m,n){
			this.disabled = disable;
		});
	});
}
/**
 * 替换窗口右上角关闭事件 add by zb on 20190401
 * @param dialog_name 窗口名称
 * @param fn 替换的方法
 */
function replaceCloseEvt(dialog_name, fn) {
	var titleCloseEle = parent.$("div[aria-describedby=dialog_"+dialog_name+"]").children(".ui-dialog-titlebar");
	$(titleCloseEle[0]).children("button").remove();
	$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
		+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
	$(titleCloseEle[0]).children("button").on("click", fn);
}
/**
 * 显示冻结列底部统计栏
 * add by zb on 20190418
 */
function displayFrozenBottom() {
	$.each($(".frozen-sdiv.ui-jqgrid-sdiv table tbody tr").children(), function (i, value) {
		$.each($(".ui-jqgrid-sdiv > .ui-jqgrid-hbox table tbody tr").children(), function (j, val) {
			if ($(value).attr("aria-describedby") == $(val).attr("aria-describedby")) { //底部栏的描述相等时，将统计赋值给冻结栏
				$(value).text($(val).text());
			}
		});
	});
}
/**
 * 动态赋值多选下拉树
 * add by zb on 20191022
 * @param treeName 组件名字
 * @param value 赋值
 */
function checkMulitTree(treeName, value) {
	$("#"+treeName).val("");
	$.fn.zTree.getZTreeObj("tree_"+treeName).checkAllNodes(false);
	zTreeObj = $.fn.zTree.getZTreeObj("tree_"+treeName);
	var idArr = value.split(',');
	var nameArr = [];
	for(var i=0; i<idArr.length; i++){
		var nodes = zTreeObj.getNodesByParam("id", idArr[i], null);
		if(nodes.length > 0){
			zTreeObj.checkNode(nodes[0], true, true);
			nameArr.push(nodes[0]['name']);
		}
	}
	$("#"+treeName).val(value);
	$("#"+treeName+"_NAME").val(nameArr.join(', '));
}
//删除记录
function beforeDel(url,key,tishi){
	var tishi = tishi?tishi:"删除记录";
	var ret = selectDataTableRows();
	if (ret && ret.length>0){
		if (!key){
			for (var str in ret[0]){
				key = str;break;
			}
		}
		var arr=[];
		for (var i=0;i<ret.length;i++){
			arr.push(ret[i][key]);
		}
		art.dialog({
			 content:'您确定要'+tishi+'吗？',
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
		        $.ajax({
					url : url,
					data : "deleteIds="+arr.join(','),
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: 'json',
					type: 'post',
					cache: false,
					error: function(){
				        art.dialog({
							content: tishi+'出现异常'
						});
				    },
				    success: function(obj){
				    	if(obj.rs){
				    		art.dialog({
								content: obj.msg,
								time: 1000,
								beforeunload: function () {
									goto_query();
							    }
							});
				    	}else{
				    		art.dialog({
								content: obj.msg
							});
				    	}
					}
				});
		    	return true;
			},
			cancel: function () {
				return true;
			}
		});
	}else{
		if (!key){
			for (var str in ret){
				key = str;break;
			}
		}
		ret = selectDataTableRow();
		if (ret){
			art.dialog({
				 content:'您确定要'+tishi+'吗？',
				 lock: true,
				 width: 200,
				 height: 100,
				 ok: function () {
			        $.ajax({
						url : url,
						data : "deleteIds="+ret[key],
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						dataType: 'json',
						type: 'post',
						cache: false,
						error: function(){
					        art.dialog({
								content: tishi+'出现异常'
							});
					    },
					    success: function(obj){
					    	if(obj.rs){
					    		art.dialog({
									content: obj.msg,
									time: 1000,
									beforeunload: function () {
										goto_query();
								    }
								});
					    	}else{
					    		art.dialog({
									content: obj.msg
								});
					    	}
						}
					});
			    	return true;
				},
				cancel: function () {
					return true;
				}
			});
		}else{
			art.dialog({
				content: "请选择要操作的记录"
			});
		}
	}
}
//是否确认操作记录
function confirmTodo(url,content,data){
	art.dialog({
		 content: content,
		 lock: true,
		 width: 200,
		 height: 100,
		 ok: function () {
	        $.ajax({
				url : url,
				data : data,
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: 'json',
				type: 'post',
				cache: false,
				error: function(){
			        art.dialog({
						content: '操作记录出现异常'
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 3000,
							beforeunload: function () {
								goto_query();
						    }
						});
			    	}else{
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});
	    	return true;
		},
		cancel: function () {
			return true;
		}
	});
}
//按比例缩放图片
function AutoResizeImage(maxWidth,maxHeight,imgId){
	var img = new Image();
	img.onload = function(){ 
		var hRatio;
		var wRatio;
		var Ratio = 1;
		var w = img.width;
		var h = img.height;
		wRatio = maxWidth / w;
		hRatio = maxHeight / h;
		if (maxWidth ==0 && maxHeight==0){
			Ratio = 1;
		}else if (maxWidth==0){//
			if (hRatio<1) Ratio = hRatio;
		}else if (maxHeight==0){
			if (wRatio<1) Ratio = wRatio;
		}else if (wRatio<1 || hRatio<1){
			Ratio = (wRatio<=hRatio?wRatio:hRatio);
		}
		if (Ratio<1){
			w = w * Ratio;
			h = h * Ratio;
		}
		$("#"+imgId).attr("height",h);
		$("#"+imgId).attr("width",w);
	};
	img.src = $("#"+imgId).attr("src");
}
//获取当前时间 格式 yyyy-mm-dd hh:mm:ss
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var minutes=date.getMinutes();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (minutes >= 1 && minutes <= 9) {
        minutes = "0" + minutes;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + minutes
            + seperator2 + date.getSeconds();
         
    return currentdate;
}
/**材料类型1联动
 * @param objSelf 触发对象
 * @param returnId 返回值id
 * @param ctx 上下文路径
 */
function changeItemType1(objSelf,returnId){
	$.ajax({
		url:ctx+'/admin/itemType1/changeItemType1?itemType1='+objSelf.value,
		type:'post',
		data:{},
		dataType:'json',
		cache:false,
		error:function(obj){
			
		},
		success:function(obj){
			if (obj){
				$("#"+returnId).html(obj.strSelect).trigger("onchange");
			}
		}
	});
}
function onCollapse(offset,target,table,shownFn,hideFn){
	if(!target) target="collapse";
	if(!table)  table="dataTable";
	$('#'+target).hide();
	$('#'+target).on('shown.bs.collapse', function (e) {
		$.each($(".panel-title").find("a").children(),function(k,v){
			if($(v).is(':hidden'))  $(v).show();
			else $(v).hide();
		});
		$("#"+table).setGridHeight($("#"+table).jqGrid("getGridParam","height")-e.currentTarget.clientHeight-offset);
		$("#loadMore").hide();
		if($('#'+target).is(":hidden")) 
		$('#'+target).show();
		else $('#'+target).hide();
		if(shownFn) shownFn();
		
	})
		$('#'+target).on('hide.bs.collapse', function (e) {
			$.each($(".panel-title").find("a").children(),function(k,v){
			if($(v).is(':hidden'))  $(v).show();
			else $(v).hide();
			});
			
			$("#"+table).setGridHeight($("#"+table).jqGrid("getGridParam","height")+e.currentTarget.clientHeight+offset);
			$("#loadMore").show();
			if($('#'+target).is(":hidden")) 
				$('#'+target).show();
				else $('#'+target).hide();
			if(hideFn) hideFn();
		})

}

function changeExpired(obj){
	obj.value=obj.value=='T'?'F':'T';
}
function clone(myObj){ 
	if(typeof(myObj) != 'object') return myObj; 
	if(myObj == null) return myObj; 
	var myNewObj = new Object(); 
	for(var i in myObj) 
	myNewObj[i] = myObj[i]; 
	return myNewObj; 
}
function relocate(rowId,table){
	if(!table)  table="dataTable";
	$("#"+table +" tr[class*='success'] td").removeClass("success");
 	$("#"+table +" tr[class*='success']").removeClass("success").attr("aria-selected","false");
    $("#"+table +" tr[id="+rowId+"]").addClass("success");
    $("#"+table).setSelection(rowId, true);
}
function replace(rowId,replaceId,table){
	if(!table)  table="dataTable";
	var row=$("table[id="+table+"] tr[id="+rowId+"]").html();
	var replaceRow=$("table[id="+table+"] tr[id="+replaceId+"]").html();
	$("table[id="+table+"] tr[id="+rowId+"]").html(replaceRow);
	$("table[id="+table+"] tr[id="+replaceId+"]").html(row);
	$("table[id="+table+"] tr[id="+rowId+"] td:eq(0)").html(rowId);
	$("table[id="+table+"] tr[id="+rowId+"] td:eq(0)").attr("title",rowId);
	$("table[id="+table+"] tr[id="+replaceId+"] td:eq(0)").html(replaceId);
	$("table[id="+table+"] tr[id="+replaceId+"] td:eq(0)").attr("title",replaceId);
	relocate(replaceId,table);
}
function moveToNext(id,rowNum,table){
	if(!table)  table="dataTable";
	if(rowNum>1){
		if(id<rowNum){
			relocate(id,table);
		}else if (id==rowNum){
			relocate(parseInt(id)-1,table);
		}
	}
}
function scrollToPosi(p,table,groupValue,hasSummary){
 	if(!table)  table="dataTable";
 	var groups=[];
 	if(groupValue) groups=$("#"+table)[0].p.groupingView.groups;
   	var div = $("#"+table).closest('.ui-jqgrid-bdiv')[0],
   	offsetHeight = div.offsetHeight,innerHeight=$("#"+table+" tr[id=1] td").innerHeight(),
	tdHeight=innerHeight+2,scrollHeight=offsetHeight-tdHeight,idx=-1,summary=0;
   	if(innerHeight<1) return;
   	$.each(groups,function(k,v){
   		if(v.value==groupValue){
   			idx=k;
   			if(hasSummary) summary=k;
   			return false;
   		}
   	})
   	var scrollY=Math.floor((p+idx+summary)*tdHeight/scrollHeight);
 	div.scrollTop=scrollY*scrollHeight;
}
//table转ul函数
$.fn.setTable = function () {
 var el=this;
 this.start=function(){
     $(el).map(function () {
         var list = '';
         $(this).find("table").map(function () {
             var ul = '<ul class="ul-form">';
             var indexSum = 0;
             $(this).find("td").map(function (index, item) {
             	if (index%2==0){
             		var st = $(this).attr('colspan');
             		if (st && st%2==0){
             			ul += '<li>' + $(this).html();
             		}else{
             			ul += '<li><label>' + $(this).html() + '</label>';
             		}
             	}else{
             		ul += $(this).html() + '</li>';
             	}
                 indexSum = index;
             });
             if (indexSum%2==0){
             	ul += '</li></ul>';
             }else{
             	ul += '</ul>';
             }
             list += ul;
         });
         $(this).find("table").html('');
         $(this).append(list);
     })
 }
 var _this=this;
 _this.start();

};
function getValidateVal($field){
	return {id:$("#"+$field.attr("id")).val().split("|")[0]}
}
function validateDataForm(dataForm){
	var dt = dataForm?dataForm:"dataForm";
	$("#"+dt).bootstrapValidator('validate');
}
function validateRefresh(name,status,dataForm){
	var st = status?status:"NOT_VALIDATED";
	var dt = dataForm?dataForm:"dataForm";
	if ($('#'+dt) && $('#'+dt).data('bootstrapValidator') 
			&& $('#'+name).attr("data-bv-field")==name){
		$('#'+dt).data('bootstrapValidator').updateStatus(name, st).validateField(name);
	}
}
function getJsonData(variables) {
	// 转换JSON为字符串
    var keys = "", values = "", types = "";
	if (variables) {
		$.each(variables, function() {
			if (keys != "") {
				keys += ",";
				values += ",";
				types += ",";
			}
			keys += this.key;
			if (this.type=='B'){
				values += this.value;
			}else{
				values += this.value?this.value:null;
			}
			types += this.type;
		});
	}
	return {
        keys: keys,
        values: values,
        types: types
    };
}
/**
 * 合并单元格(列,匹配连续相同值) add by zzr 2017/12/16
 * @param tableId
 * @param cellName
 */
function Merger(tableId, cellName) { 
	var rets = $("#"+tableId).jqGrid("getCol",cellName,true);
	if(rets.length > 0){
    	var index=0;
    	//update by zzr 2017/12/19 09:52 用rowspan方式,改为去除内容隐藏上下边的模拟方式 begin
    	
/*    	var i=1;
    	for(;i<rets.length;i++){
    		if(i>0 && rets[i].value == rets[index].value){
    			$("#"+tableId).jqGrid("setCell",rets[i].id,cellName,"",{"display":"none"});
    		}else{
                $("#"+tableId+" tbody tr #"+cellName+rets[index].id).attr("rowspan", i-index); 
    			index = i;
    		}
    	}
        $("#"+tableId+" tbody tr #"+cellName+rets[index].id).attr("rowspan", i-index); */
    	for(var i=1;i<rets.length+1;i++){
    		if(i == rets.length || rets[i].value != rets[index].value){
				if(i-index >= 2){
	                $("#"+tableId+" tbody tr #"+cellName+rets[index].id).css({"background":"white","border-bottom":"0px"}); 
	                $("#"+tableId+" tbody tr #"+cellName+rets[i-1].id).css({"background":"white","border-top":"0px"}).html(""); 
				}
				if(i-index > 2){
	    			for(var j = index+1 ;j<i-1;j++){
		                $("#"+tableId+" tbody tr #"+cellName+rets[j].id).css({"background":"white","border-bottom":"0px","border-top":"0px"}).html(""); 
	    			}
				}
    			index = i;
    		}
    	}
    	var id = $("#"+tableId).jqGrid("getGridParam", "selrow");
		if(id > 0){
 			$("#"+tableId+" tbody tr #"+cellName+id).css({"background":"#198EDE","color":"white"});
		}
    	//update by zzr 2017/12/19 09:52 用rowspan方式,改为去除内容隐藏上下边的模拟方式 end
	}
} 
/**
 * 列名下拉筛选全选方法 add by zzr 2017/12/16
 * @param tableId
 * @param flag 是否触发选中方法标志
 */
function selectAll(tableId,flag){
	$("#cb_"+tableId+"_new").get(0).checked=flag;
	if(flag == true){
		$("#"+tableId).jqGrid("resetSelection");
		var ids=$("#"+tableId).jqGrid("getDataIDs");
		for(var i=0;i<ids.length;i++){
			if($("#"+tableId+" tbody tr[id="+ids[i]+"]").css("display")=="table-row"){
				$("#"+tableId).jqGrid("setSelection",ids[i],flag);
			}
		}
	}else{
		$("#"+tableId).jqGrid("resetSelection");
	}
}
/**
 * 列名下拉筛选   add by zzr 2017/12/16
 * @param tableId
 * @param colName
 */
function dataTableCheckBox(tableId,colName){
	var table = $("#gview_"+tableId+" table th[id='"+tableId+"_"+colName+"']");
	if($("#all_"+tableId+"_checkBoxs").size() == 0){
		$("#gbox_"+tableId).after("<div id=\"all_"+tableId+"_checkBoxs\" style=\"width:100%;\"></div>")
	}
	if($("#"+tableId+"_"+colName+"_checkBoxs").size() > 0){
		$("#"+tableId+"_"+colName+"_checkBoxs").remove();
	}
	// replace click event for headerbox
	if($("#cb_"+tableId+"_new").size() == 0){
		var box = $("#cb_"+tableId);
		box.attr("id",box.attr("id")+"_new");
		box.unbind("click");
		box.bind("click",function(){
			var checked = $("#cb_"+tableId+"_new").get(0).checked;
			selectAll(tableId,checked);
		});
	}
	
	if($("#"+tableId+"_"+colName+"_checkBoxsBtn").length > 0){
		$("#"+tableId+"_"+colName+"_checkBoxsBtn").remove();
	}
	
	$("#"+tableId+"_"+colName+">div").append("<span id=\""+tableId+"_"+colName+"_checkBoxsBtn\" style=\"margin-left:20px;\">▼</span>");
	
	$("#"+tableId+"_"+colName+"_checkBoxsBtn").on("click", function(e){
		if($("#"+tableId+"_"+colName+"_checkBoxs").css("display") == "none"){
			$("#"+tableId+"_"+colName+"_checkBoxs").css("display", "block");
		}else{
			$("#"+tableId+"_"+colName+"_checkBoxs").css("display", "none");
		}
		var ele = $("#gbox_"+tableId+" thead #"+tableId+"_"+colName);
		$("#"+tableId+"_"+colName+"_checkBoxs").css({
			left:ele.offset().left-40,
			width:parseInt(ele.css("width").substring(0,ele.css("width").lastIndexOf("px")))+70
		}); 
		e.stopPropagation();
	});
	
	$("#all_"+tableId+"_checkBoxs").append("<div id=\""+tableId+"_"+colName+"_checkBoxs\" style=\"display:none;position:absolute;z-index:9999;border:1px solid #617775;background:white;height:200px;overflow-y:auto;overflow-x:auto;border-radius:8px;\"></div>");
	
	$("#"+tableId+"_"+colName+"_checkBoxs").css({
		top:table.offset().top+parseInt(table.css("height").substring(0,table.css("height").lastIndexOf("px"))),
		left:table.offset().left-40,
		width:parseInt(table.css("width").substring(0,table.css("width").lastIndexOf("px")))+70
	});
/*	table.on("mouseout",function(){
		$("#"+tableId+"_"+colName+"_checkBoxs").css({"display":"none"});
	});
	table.on("mouseover",function(){
		var ele = $("#gbox_"+tableId+" thead #"+tableId+"_"+colName);
		$("#"+tableId+"_"+colName+"_checkBoxs").css({"display":"block",left:ele.offset().left-40}); 
	});*/
	$("#"+tableId+"_"+colName+"_checkBoxs").on("mouseout",function(){
		$("#"+tableId+"_"+colName+"_checkBoxs").css({"display":"none"});
	})
	$("#"+tableId+"_"+colName+"_checkBoxs").on("mouseover",function(){
		$("#"+tableId+"_"+colName+"_checkBoxs").css({"display":"block"});
	})
	
	var arr=[];
	
	var colValues = $("#"+tableId).jqGrid("getCol",colName,false);
	for(var i=0;i<colValues.length;i++){
		var j = 0;
		for(;j<arr.length && arr[j] != colValues[i];j++);
		if(j == arr.length){
			arr.push(colValues[i]);
		}
	}
	var changFun = function(){
		if(this){
			if(this.checked){
				$(this).attr("checked",true);
			}else{
				$(this).removeAttr("checked");
			}
		}
		var boxs = $("#all_"+tableId+"_checkBoxs div");
		var checkValues = [];
		for(var i = 0;i < boxs.size();i++){
			var id = boxs[i].id;
			var checkboxs = $("#"+id+" input[type=\"checkbox\"][checked=\"checked\"]");
			checkValues.push({
				id: id.substring(tableId.length+1,id.lastIndexOf("_checkBoxs")), 
				checks: checkboxs
			});
		}
		
		var ids = $("#"+tableId).jqGrid("getDataIDs");
		var showIndex = 0;
		for(var i = 0;i < ids.length;i++){
			var ret = $("#"+tableId).jqGrid("getRowData", ids[i]);
			var findCount = 0;
			for(var j = 0;j < checkValues.length;j++){
				if(!checkValues[j].checks || checkValues[j].checks.length == 0){
					findCount++;
				}
				for(var k = 0;k < checkValues[j].checks.length;k++){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
					if(ret[checkValues[j].id] == checkValues[j].checks[k].value){
						findCount++;
						break;
					}
				}
			}
			var tr = $("#"+tableId+" tbody tr[id="+ids[i]+"]");
			if(findCount == checkValues.length){
				showIndex++;
				tr.css({"display":"table-row"});
				tr.find("td:first").attr("title",showIndex).html(showIndex);
			}else{
				tr.css({"display":"none"});
			}
		}
	}
	$("#"+tableId+"_"+colName+"_checkBoxs").append("<input type=\"checkbox\" name=\""+tableId+"_"+colName+"_checkbox_all\" id=\""+tableId+"_"+colName+"_checkbox_all\" value=\"all\" style=\"margin-left:10px;margin-top:0px;\" /><input type=\"text\" value=\"全部选中\" style=\"width:"+(parseInt(table.css("width").substring(0,table.css("width").lastIndexOf("px")))+20)+"px;border:0px;\" readonly/><br/>");
	$("#"+tableId+"_"+colName+"_checkbox_all").change(function(){
		var all = $("#"+tableId+"_"+colName+"_checkBoxs input:first");
		var inputs;
		if(all.get(0).checked){
			inputs = $("#"+tableId+"_"+colName+"_checkBoxs input[type=\"checkbox\"]:gt(0)").attr("checked",true);
		}else{
			inputs = $("#"+tableId+"_"+colName+"_checkBoxs input[type=\"checkbox\"]:gt(0)").removeAttr("checked");
		}
		for(var i=0;i<inputs.length;i++){
			if(all.get(0).checked){
				inputs.get(i).checked = true;
			}else{
				inputs.get(i).checked = false;
			}
		}
		changFun();
	});
	for(var i=0;i<arr.length;i++){
		$("#"+tableId+"_"+colName+"_checkBoxs").append("<input type=\"checkbox\" name=\""+tableId+"_"+colName+"_checkbox\" id=\""+tableId+"_"+colName+"_checkbox_"+i+"\" value=\""+arr[i]+"\" style=\"margin-left:10px;margin-top:0px;\" /><input type=\"text\" value=\""+arr[i]+"\" style=\"width:"+(parseInt(table.css("width").substring(0,table.css("width").lastIndexOf("px")))+20)+"px;border:0px;\" readonly/><br/>");
		$("#"+tableId+"_"+colName+"_checkbox_"+i).change(changFun);
	}	

}
/**
 * 集合字符串转换json(过滤所有空格)     add by zzr 2017/12/18
 * @param listStr
 * @returns {Array}
 */
function listStrToJson(listStr){
	var listStr = listStr.substring(listStr.indexOf("[")+1,listStr.lastIndexOf("]"));
	var jsonArr = [];
	if(listStr.trim() != ""){
		var startIndex = 0;
		var endIndex = 0;
		for(var i=0;i<listStr.length;i++){
			startIndex = listStr.indexOf("{", i);
			if(startIndex == -1){
				break;
			}
			endIndex = listStr.indexOf("}", startIndex);
			var data = {};
			var dataStr = listStr.substring(startIndex+1, endIndex).split(",");
			for(var j=0;j<dataStr.length;j++){
				var str = dataStr[j].trim();
				var type = str.substring(0,str.indexOf("=")).trim();
				var value = str.substring(str.indexOf("=")+1).trim();
				value = value == "" || value=="null" ?null:value;
				data[type]=value;
			}
			jsonArr.push(data);
			i=endIndex;
		}
	}
	return jsonArr;
}
/**
 * 四舍五入,修改负数规则
 * @param number
 * @param digit
 * @returns {Number}
 * add by zzr 2017/12/20 15:18
 */
function myRound(number,digit){
	if(isNaN(number)) return "";
	var sign = number >= 0 ? 1:(-1);
	var magnify = 1;
	if(digit){
		for(var i=0;i<digit;i++){
			magnify *= 10;
		}
	}
	return (Math.round(Math.abs(number)*magnify)/magnify)*sign;
}
/**
 * 获取表格分组合计
 */
function getGridFootSum(id,name){
	return myRound($("tr[id="+id+"]").nextAll(".jqfoot").eq(0).children("td[aria-describedby=dataTable_"+name+"]").text());
}
/**
 * 更新表格分组合计
 */
function setGridFootSum(id,name,val){
	$("tr[id="+id+"]").nextAll(".jqfoot").eq(0).children("td[aria-describedby=dataTable_"+name+"]").text(val);
	
}
/**
 * 多选框组件刷新选项
 * @param id
 * @param options
 */
function setMulitOption(id,options){
	if(!id){
		console.error("id is null or \"\"");
		return;
	}
	if(options.length <= 0){
		console.error("options null");
		return;
	}
    var setting = "{\n" +
	"	check: {\n" +
	"		enable: true,\n" +
	"		chkboxType: {\"Y\":\"ps\", \"N\":\"ps\"}\n" +
	"	},\n" +
	"	view: {\n" +
	"		dblClickExpand: false\n" +
	"	},\n" +
	"	data: {\n" +
	"		simpleData: {\n" +
	"			enable: true,\n" +
	"		}\n" +
	"	},\n" +
	"	callback: {\n" +
	"		beforeCheck: beforeCheck_"+id+",\n" +
	"		onCheck: onCheck_"+id+"\n" +
	"	}\n" +
	"}\n\n";
	$("#"+id).val('');
	$("#"+id+"_NAME").val('');
	$.fn.zTree.getZTreeObj("tree_"+id).checkAllNodes(false);
	$.fn.zTree.init($("#tree_"+id), eval("("+setting+")"), eval("("+options+")"));
}

/**
 * 责任部门联动
 * @param id
 * @param childId
 */
function checkPromDept(id,childId){
	id = id?id:'promDeptCode';
	childId = childId?childId:'promTypeCode';
	//$.fn.zTree.init($("#tree_"+childId), "setting_"+childId, "zNodes_"+childId);
	var setting = "{\n" +
	"	check: {\n" +
	"		enable: true,\n" +
	"		chkboxType: {\"Y\":\"ps\", \"N\":\"ps\"}\n" +
	"	},\n" +
	"	view: {\n" +
	"		dblClickExpand: false\n" +
	"	},\n" +
	"	data: {\n" +
	"		simpleData: {\n" +
	"			enable: true,\n" +
	"		}\n" +
	"	},\n" +
	"	callback: {\n" +
	"		beforeCheck: beforeCheck_"+childId+",\n" +
	"		onCheck: onCheck_"+childId+"\n" +
	"	}\n" +
	"}\n\n"; 
	var datas = {"code" : $("#"+id).val()};
	$.ajax({
		url:ctx+'/admin/prjProblem/changePromTypes',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
	
	    },
	    success: function(obj){
	    	if (obj){
	    		$("#"+childId).val('');
	    		$("#"+childId+"_NAME").val('');
	    		$.fn.zTree.getZTreeObj("tree_"+childId).checkAllNodes(false);
	    		$.fn.zTree.init($("#tree_"+childId), eval("("+setting+")"), eval("("+obj.strSelect+")"));
			}
	    }
	 });
}
/**
 * 职位类型联动
 * @param id
 * @param childId
 */
function checkPosType(id,childId){
	id = id?id:'type';
	childId = childId?childId:'position';
	//$.fn.zTree.init($("#tree_"+childId), "setting_"+childId, "zNodes_"+childId);
	var setting = "{\n" +
	"	check: {\n" +
	"		enable: true,\n" +
	"		chkboxType: {\"Y\":\"ps\", \"N\":\"ps\"}\n" +
	"	},\n" +
	"	view: {\n" +
	"		dblClickExpand: false\n" +
	"	},\n" +
	"	data: {\n" +
	"		simpleData: {\n" +
	"			enable: true,\n" +
	"		}\n" +
	"	},\n" +
	"	callback: {\n" +
	"		beforeCheck: beforeCheck_"+childId+",\n" +
	"		onCheck: onCheck_"+childId+"\n" +
	"	}\n" +
	"}\n\n"; 
	var datas = {"code" : $("#"+id).val()};
	$.ajax({
		url:ctx+'/admin/employee/changePosTypes',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
	
	    },
	    success: function(obj){
	    	if (obj){
	    		$("#"+childId).val('');
	    		$("#"+childId+"_NAME").val('');
	    		$.fn.zTree.getZTreeObj("tree_"+childId).checkAllNodes(false);
	    		$.fn.zTree.init($("#tree_"+childId), eval("("+setting+")"), eval("("+obj.strSelect+")"));
			}
	    }
	 });		
}
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] != undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

/**
 * 自定义搜索组件按钮状态切换
 * @param componentName 组件名称
 * @param id 元素id
 * @param disabled(true可点击,false不可点击) 
 */
function componentDisabled(componentName, id, disabled){
	if(disabled){
		$("#openComponent_"+componentName+"_"+id).attr("readonly", false);
		$("#openComponent_"+componentName+"_"+id).next().attr("data-disabled", false).css({
			"color":""
		});
	}else{
		$("#openComponent_"+componentName+"_"+id).attr("readonly", true);
		$("#openComponent_"+componentName+"_"+id).next().attr("data-disabled", true).css({
			"color":"#888"
		});
	}
}

//判断时间
var treeLastTimeStamp = 0;

/**
 * 树搜索
 * @param obj
 * @param treeId
 * @param event
 */
function treeKeyup(obj, treeId, event){
	//原方法仅支持一级搜索,改为支持n级搜索,并且增加延迟执行
	treeLastTimeStamp = event.timeStamp;
	setTimeout(function(){
		if(treeLastTimeStamp - event.timeStamp == 0){
			var str = obj.value;
			var nodes = window["zTree_"+treeId].getNodes();
			treeKeyupChildrenNode(treeId, nodes[0].children, str);
		}
		// 多级节点查找须展开节点
		window["zTree_"+treeId].expandAll(true);  
	}, 100);
}

/**
 * 递归搜索函数
 * @param treeId
 * @param nodes
 * @param value
 * @returns {Boolean}
 */
function treeKeyupChildrenNode(treeId, nodes, value){
	if(!nodes){ return; }
	var result = false;
	for(var i = 0;i < nodes.length;i++){
		//值为空或者找到相应节点
		if(!value || value == "" || nodes[i].name.indexOf(value) != -1){
			//已展开节点收缩
			if(nodes[i].open){ window["zTree_"+treeId].expandCollapseNode(window["zTree_"+treeId].setting, nodes[i]); }
			//显示
			$("#"+nodes[i].tId).show();
			//是父节点则所有子节点都需要设置显示
			if(nodes[i].isParent){ treeKeyupChildrenNode(treeId, nodes[i].children, ""); }
			result = true;
		}else{
			// 当前节点非搜索节点,则搜索子元素
			if(nodes[i].isParent){
				if(treeKeyupChildrenNode(treeId, nodes[i].children, value)){
					$("#"+nodes[i].tId).show();
					//已展开节点收缩
					if(nodes[i].open){ window["zTree_"+treeId].expandCollapseNode(window["zTree_"+treeId].setting, nodes[i]); }
					result = true;
				}else{
					$("#"+nodes[i].tId).hide();
				}
			}else{
				$("#"+nodes[i].tId).hide();
			}
		}
		
	}
	return result;
}

/**
 * 添加zTree子元素
 * @param treeId
 */
function appendAllDom(treeId){
	var nodes = window["zTree_"+treeId].getNodes();
	if(!window["zTree_"+treeId].isAppendAllDom){
		appendParentULDom(treeId, nodes);
		window["zTree_"+treeId].isAppendAllDom = true;
	}
}

/**
 * 递归添加可展开子节点的子元素
 * @param treeId
 * @param nodes
 */
function appendParentULDom(treeId, nodes){
	$.each(nodes, function(k, v){
		window["zTree_"+treeId].appendParentULDom(window["zTree_"+treeId].setting, v);
		if(v.children && v.children.length > 0){ 
			appendParentULDom(treeId, v.children)
		}
	});
}

function resetJsonObj(jsonObj){
	if (jsonObj && typeof(jsonObj)=="object"){
		for(var p in jsonObj){
			jsonObj[p.toLowerCase()]=jsonObj[p];
		}
	}
	return jsonObj;
}
var conditionStr={};
function setComponentData(obj,options){
    if (!obj.length || !options) {
        return obj;
    }
    var $input1 = $("#"+options.preStr+$(obj).attr("id"));
    if(options.readonly){
    	$input1.attr("readonly",true)&&$input1.next("button").attr("data-disabled","true");
	}else{
		if (options.readonly==false){
    		$input1.removeAttr("readonly")&&$input1.next("button").attr("data-disabled","false");
    	}
	}
    if(options.disabled){
    	$input1.attr("disabled",true)&&$input1.next("button").attr("data-disabled","true");
	}else{
		if (options.disabled==false){
    		$input1.removeAttr("disabled")&&$input1.next("button").attr("data-disabled","false");
    	}
	}
    if($input1.next("button").attr("data-disabled")=="true"){
    	$input1.next("button").unbind("click");
	}
    if(options.showValue){
    	if (options.showLabel){
    		$(obj).val(options.showValue)&&$input1.val($.trim(options.showValue)
    				+"|"+$.trim(options.showLabel))&&$input1.data("data-valid","true");
    	}else{
    		$(obj).val(options.showValue)&&$input1.val($.trim(options.showValue))&&$input1.data("data-valid","true");
    	}
    }
}
function fillSuggestOptions(obj,options){
	if(options.condition == undefined){
    	options["condition"] = {};
    }
	var conditionStr = "";
	$.map(options.condition,function(v,k){
		conditionStr += k + "=" + v + "&";
	});
	if(conditionStr.length > 0){
		conditionStr = conditionStr.substring(0,conditionStr.length-1);
	}
	$.extend(options,{
	    processData: function(json){// url 获取数据时，对数据的处理，作为 getData 的回调函数
	        if(!json || !json.rows || json.rows.length == 0) return false;
	        var jsonStr = "{'data':[";
	        for (var i = json.rows.length - 1; i >= 0; i--) {
	            jsonStr += "{'id':'" + i
	                + "','word':'"+ json.rows[i][options.code]
	                + "', 'description':'" +  JSON.stringify(json.rows[i])
	                +"'},";
	        }
	        jsonStr += "],'defaults':''}";
	 
	        //字符串转化为 js 对象
	        return json = (new Function("return "+jsonStr ))();
	    },
	    getData: function(word, parent, callback) {//数据获取方法
	        if(!word) return;
	        $.ajax({
	            type: 'POST',
	            data: options.code+"="+word+(conditionStr?"&"+conditionStr:"")+"&rows=7&isCount=false",
	            url: options.url,
	            dataType: 'json',
	            timeout: 3000,
	            success: function(data) {
	                callback(parent, data);
	            },
	            error: function(data){callback(parent, data);}
	        });
	    }
	});
    $(obj).searchSuggest(options);
}
function fillComponentOptions(obj,options){
	options.dataForm = options.dataForm?options.dataForm:"dataForm";
	var $input = $(obj),$input1 = $("#"+options.preStr+$input.attr("id")),
    isSet = false,$form = $input1.parents("form").eq(0),validator = $form.data('validator');
    var validateStr="";
    var alertStr=$input.attr("data-bv-notempty-message");
    if(alertStr) validateStr=" data-bv-notempty data-bv-notempty-message=\""+alertStr+"\"";
    if(!$input1.attr("id")){
    	var $component = "<div class=\"input-group\"><input type=\"text\" class=\"form-control\""+validateStr+"name=\""+options.preStr
		+$input.attr("name")+"\" id=\""+options.preStr
		+$input.attr("id")+"\" autocomplete=\"off\" disableautocomplete/><button type=\"button\" "+(options.buttonId && options.buttonId !="" ? "id=\""+options.buttonId+"\"" : "")+" class=\"btn btn-system\" ><span class=\"glyphicon glyphicon-search\"></span></button></div>";
	    $input.css("display","none");		   
	    $input.before($component);
	    $input1 = $("#"+options.preStr+$input.attr("id"));
	    $form = $input1.parents("form").eq(0),validator = $form.data('validator');
	    //添加验证信息
	    if(validator){
	    	$.validator.addMethod(options.preStr+"valid",function(value,element,params){
		    	if(params === true){
			    	if($.trim(value)==''||$(element).data("data-valid")=="true"){
			    		return true;
			    	}
			    	return false;
		    	}
		    	return true;
		    },"未通过验证");
	    	var func = eval(options.preStr+"valid");
		    $input1.rules("add", {func: true});
	    }
	    //添加事件
	    $input1.on("change",function(){
	    	$input.val('');
	    	isSet = false;
	    	$input1.data("data-valid","false");
	    }).on("keyup",function(e){//查询表单按回车提交
		    if(options.isValidate){
		    	if(e.keyCode === 13){
					if(isSet){
						if (options.valueOnly){
							setComponentData(obj,{showValue:$.trim($input1.val()).split("|")[0],preStr:options.preStr});
						}else{
							setComponentData(obj,{showLabel:$.trim($input1.val()).split("|")[1],
								showValue:$.trim($input1.val()).split("|")[0],preStr:options.preStr});
						}
					}else{
						$input.val($.trim($input1.val()).split("|")[0]);	
					}
		    	}
		    }
	    }).on("blur",function(e){//离焦事件，判断输入是否正确
		    if(options.isValidate){
		    	if($.trim($input1.val())!="" && !isSet){
		    		var inputVal = $.trim($input1.val()).split("|")[0];
		    		$input1.data("data-valid","false");
		    		var params=options.condition||{};
		    		params.id=$.trim(inputVal);
			    	$.post(options.blurUrl,params,function(ret){
			    		if(ret.code=='0'){
			    			if (options.valueOnly){
			    				setComponentData(obj,{showValue:ret.data[options.blurCode],preStr:options.preStr});
			    			}else{
			    				setComponentData(obj,{showLabel:ret.data[options.blurDescr],
			    					showValue:ret.data[options.blurCode],preStr:options.preStr});
			    			}
		    				if(options.callBack && $.isFunction(options.callBack)){
		    					options.callBack(resetJsonObj(ret.data));
		    				}
			    			isSet = true;
			    			$input1.data("data-valid","true");
			    			//validator&&validator.element($input)&&validator.element($input1);
			    			validateRefresh($input1.attr("id"),"VALID",options.dataForm);
			    		}else{
			    			isSet = false;
			    			$input1.data("data-valid","false");
			    			validateRefresh($input1.attr("id"),"INVALID",options.dataForm);
			    		}
			    		//增加组件失焦事件 
			    		if($.isFunction(options.blurFun)){
				    		options.blurFun();
			    		}
			    	},"json");
		    	}else{
	    			$input1.data("data-valid","true");
	    			//validator&&validator.element($input)&&validator.element($input1);
	    			validateRefresh($input1.attr("id"),"VALID",options.dataForm);
		    		//增加组件失焦事件
		    		if($.isFunction(options.blurFun)){
			    		options.blurFun();
		    		}
		    	}
		    }
	    });
    }	
	$input1.removeAttr("disabled")&&$input1.next("button").attr("data-disabled","false");
	//把condition条件转化成&字符串
	if(options.condition == undefined){
    	options["condition"] = {};
    }
	var sid = $input1.attr("id");
	conditionStr[sid] = "";
	$.map(options.condition,function(v,k){
		conditionStr[sid] += k + "=" + v + "&";
	});
	if(conditionStr[sid].length > 0){
		conditionStr[sid] = conditionStr[sid].substring(0,conditionStr[sid].length-1);
	}
    $input1.next("button").unbind("mousedown").on("mousedown",function(){
    	if($(obj).attr("data-disabled")=="true"){
    		options.isValidate = false;
    	}
    }).unbind("click").on("click",function(){
    	if($(this).attr("data-disabled")=="true"){
    		return;
    	}
    	console.log(options.width);
		Global.Dialog.showDialog("fetch_"+$input.attr("id"),{
			title:options.fetchTitle,
			url:options.fetchUrl+(conditionStr[sid]?"?"+conditionStr[sid]:""),
			height: options.dialogHeight?options.dialogHeight:600,
			width: options.dialogWidth?options.dialogWidth:1000,
			returnFun:function(returnData){
				if(returnData){
					if (options.valueOnly){
						$input.val(returnData[options.fetchCode])&&$input1.val(returnData[options.fetchCode]);
					}else{
						$input.val(returnData[options.fetchCode])&&$input1.val(returnData[options.fetchCode]
						+'|'+returnData[options.fetchDescr]);
					}
					isSet = true;
					$input1.data("data-valid","true");
					//validator&&validator.element($input)&&validator.element($input1);
					validateRefresh($input1.attr("id"),"VALID",options.dataForm);
    				if(options.callBack && $.isFunction(options.callBack)){
    					options.callBack(resetJsonObj(returnData));
    				}
				}
			}
		});	
	});
    //设置值
    if(options.readonly){
    	$input1.attr("readonly",true)&&$input1.next("button").attr("data-disabled","true");
	}else{
		if (options.readonly==false){
    		$input1.removeAttr("readonly")&&$input1.next("button").attr("data-disabled","false");
    	}
	}
    if(options.disabled){
    	$input1.attr("disabled",true)&&$input1.next("button").attr("data-disabled","true");
	}else{
		if (options.disabled==false){
    		$input1.removeAttr("disabled")&&$input1.next("button").attr("data-disabled","false");
    	}
	}
    if($input1.next("button").attr("data-disabled")=="true"){
    	$input1.next("button").unbind("click");
	}
    if(options.showValue){
    	if (options.showLabel){
    		$(obj).val(options.showValue)&&$input1.val($.trim(options.showValue)
    				+"|"+$.trim(options.showLabel))&&$input1.data("data-valid","true");
    	}else{
    		$(obj).val(options.showValue)&&$input1.val($.trim(options.showValue))&&$input1.data("data-valid","true");
    	}
    	validator&&validator.element($input)&&validator.element($input1);
    }
    $.extend(options,{
	    processData: function(json){// url 获取数据时，对数据的处理，作为 getData 的回调函数
	    	console.log(json);
	        if(!json || !json.rows || json.rows.length == 0) return false;
	        var jsonStr = "{'data':[";
	        for (var i = json.rows.length - 1; i >= 0; i--) {
	            jsonStr += "{'id':'" + i
	                + "','word':'"+ json.rows[i][options.dropDownCode]
	                + "', 'description':'" +  JSON.stringify(json.rows[i][options.dropDownDescr])
	                +"'},";
	        }
	        jsonStr += "],'defaults':''}";
	 
	        //字符串转化为 js 对象
	        return json = (new Function("return "+jsonStr ))();
	    },
	    getData: function(word, parent, callback) {//数据获取方法
	        if(!word) return;
	        $.ajax({
	            type: 'POST',
	            data: options.dropDownCode+"="+word+(conditionStr[sid]?"&"+conditionStr[sid]:"")+"&rows=7",
	            url: options.dropDownUrl,
	            dataType: 'json',
	            timeout: 30000,
	            success: function(data) {
	            	console.log(data);
	                callback(parent, data);
	            },
	            error: function(data){callback(parent, data);}
	        });
	    }
	});
    $input1.searchSuggest(options);
}
/**
 * 表格内字符串查找 add by cjg 2018/8/23
 */
var zfcz_countIndex=0;//计数
var zfcz_downArry=[];//向上查询数组
var zfcz_upArry=[];//向下查询数组
//添加字符串查找方法，在jqgrid中的gridComplete里面调用
function addCharSearch(tableId){
	if (!tableId || typeof(tableId)!="string"){
		tableId = "dataTable";
	}
	var colNames=$("#"+tableId).jqGrid('getGridParam','colNames');//所有列名
	var colModel=$("#"+tableId).jqGrid('getGridParam','colModel');//所有列index
	//拼接弹出框的布局
	var div="<div style='display:none;' id='zfcz' >"
	+"<div style='position:absolute;left:60px;top:60px;'><div>查找内容：<input id='zfcz_content' autofocus='autofocus' style='width:160px; height:25px' name='zfcz_content'"
	+"type='text' />&nbsp;&nbsp;"
	+'<button onclick="zfcz_next(\''+tableId+'\')" id="zfcz_btn">下一个</button></div><br><div>查找范围：<select style="width:160px; height:25px" id="zfcz_range" name="zfcz_range">';
	for(var i=1;i<=colNames.length-1;i++){
		for(var j=1;j<=colModel.length-1;j++){
			if(i==j){
				if(!colModel[j].hidden && colModel[j].name!="cb" && colNames[i].indexOf("input")==-1)//过滤掉隐藏的列和input框
					div+="<option value="+colModel[j].name+">"+colNames[i]+"</option>";
			}	
		}
	}
	div+="</select></div><br>"
	+"<div>查找 &nbsp   ：<select style='width:160px; height:25px' id='zfcz_direction' name='zfcz_direction'><option>全部</option><option>向下</option><option>向上</option>"
	+"</select></div></div><br><div style='position:absolute;left:60px;top:180px;'><p id='zfcz_totolNum'></p></div><input type='hidden' id='zfcz_count_down' value='0' /><input type='hidden' id='zfcz_count_up' value='init'/>"
	+"<input type='hidden' id='zfcz_direction_last' /><input type='hidden' id='zfcz_content_last' /><input type='hidden' id='zfcz_range_last' /></div>";
	$("body").append(div);
	$("#"+tableId+"_rn").css("width","50");//设置序号列名的宽度
	$("#"+tableId+" tr:first td:first").css("width", "50");//设置序号数据列宽度 
	//修正固定列样式走样问题
	var frozenDiv = $("#gview_"+tableId).children(".frozen-div");//获取固定列label元素
	if(frozenDiv && frozenDiv.length > 0){//判断是否存在frozen固定列
		frozenDiv.children("table").children("thead").children("tr").children("#"+tableId+"_rn").css("width", "50px");//修改固定列表列rn宽度
		if(frozenDiv.attr("addTop") != "true"){//判断是否修改过,防止重复追加top的值
			var top = $("#gview_"+tableId).children(".frozen-bdiv");//获取固定列表格元素
			if(top && top.length > 0){//固定列表格元素是否存在,存在则向下偏移3px的距离
				top = top.css("top");
				top = parseFloat(top.substring(0, top.indexOf("px")));
				$("#gview_"+tableId).children(".frozen-bdiv").css("top", top+3);
			}
			top = $("#gview_"+tableId).children(".frozen-sdiv");//获取固定列合计栏元素
			if(top && top.length > 0){//固定列合计栏元素是否存在,存在则向下偏移3px的距离
				top = top.css("top");
				top = parseFloat(top.substring(0, top.indexOf("px")));
				$("#gview_"+tableId).children(".frozen-sdiv").css("top", top+3);
			}
			frozenDiv.attr("addTop", true);
		}
	}
	//合计栏rn列宽度修改(frozen)
	frozenDiv = $("#gview_"+tableId).children(".frozen-sdiv");
	if(frozenDiv && frozenDiv.length > 0){//判断是否存在合计栏
		frozenDiv.children("table").children("tbody").children("tr").children("td[aria-describedby="+tableId+"_rn]").css("width", "50px");
	}
	//合计栏rn列宽度修改
	frozenDiv = $("#gview_"+tableId).children(".ui-jqgrid-sdiv");
	if(frozenDiv && frozenDiv.length > 0){//判断是否存在合计栏
		frozenDiv.children("div").children("table").children("tbody").children("tr").children("td[aria-describedby="+tableId+"_rn]").css("width", "50px");
	}
	var imgUrl=ctx+"/images/search.png";
	//图片路径
	$("#"+tableId).jqGrid("setLabel","rn",
    "<img id='bgcx' text-align:center  src="+imgUrl+" style='width:20px;height:20px;' onclick='go_zfcz()'>  ", "labelstyle");//修改序号列名为查询按钮
	//回车键触发搜索
	$(document).keydown(function(event){
	    if(event.keyCode == '13'){
	       $('#zfcz_btn').click();
	    }
	}); 
}
//通过点击单元格，初始化默认选中的查找范围，在jqgrid中的onCellSelect里面调用
function initDefaultRenge(index,tableId){
	if (!tableId || typeof(tableId)!="string"){
		tableId = "dataTable";
	}
	var colModel=$("#"+tableId).jqGrid('getGridParam','colModel');//所有列
    $("#zfcz_range").val(colModel[index].name);//设置默认选中的查找范围
    $("#zfcz_count_down").val("0");
	$("#zfcz_count_up").val("init");
	zfcz_downArry=[];
	zfcz_upArry=[];
	zfcz_countIndex=0;
}
//查找字符界面
function go_zfcz() {
	art.dialog({
		drag:true,
		fixed:true,
		title:"查找字符",
		content : document.getElementById("zfcz"),
		width : 400,
		height : 200,
		okValue:"关闭",
		ok:function(){
			$("#zfcz_count_down").val("0");
			$("#zfcz_count_up").val("init");
			$("#zfcz_totolNum").text("");
			zfcz_upArry=[];
			zfcz_downArry=[];
			zfcz_countIndex=0;
		},
		cancel:false,
	});
	$("#zfcz_content").focus();
}


//查找下一个功能的实现
function zfcz_next(tableId){
	if (!tableId || typeof(tableId)!="string"){
		tableId = "dataTable";
	}
	var rowId = $("#"+tableId).jqGrid("getGridParam","selrow");//选中行id
	var zfcz_count_down=parseInt($("#zfcz_count_down").val(), 0);
	if(!isNaN(zfcz_count_up)){
		var zfcz_count_up=parseInt($("#zfcz_count_up").val(), 0);
	}else{
		var zfcz_count_up=$("#zfcz_count_up").val();
	}
	var idArr = new Array();//满足查询条件的id数组
	var content=$("#zfcz_content").val();//查询内容
	var zfcz_content_last=$("#zfcz_content_last").val();//上一个查询内容
	$("#zfcz_content_last").val(content);
	var range=$("#zfcz_range").val();//查询范围
	var zfcz_range_last=$("#zfcz_range_last").val();//上一次查询范围
	$("#zfcz_range_last").val(range);
	var place=$("#zfcz_place").val();//字符串位置
	var direction=$("#zfcz_direction").val();//方向（向上，向下，全部）
	var direction_last=$("#zfcz_direction_last").val();//上次次的方向
	$("#zfcz_direction_last").val(direction);//记录上一次选的方向，用于判断是否改变
	var zfcz_scrollTop=$('.ui-jqgrid .ui-jqgrid-bdiv').scrollTop();//滚动条值
	var scrollIdIndex=0;//标识选中id在原ids数组的位置，解决删除记录后滚动错位
	if(content==""){
		$("#zfcz_totolNum").text("查询内容不能为空！").css("color","red");
		$("#zfcz_content").focus();
		return;
	}
	//判断是否改变查询内容和范围
	if((content!=zfcz_content_last)||(range!=zfcz_range_last)){
		zfcz_upArry=[];
		zfcz_downArry=[];
		zfcz_count_down=0;
		zfcz_count_up="init";
		zfcz_countIndex=0;
	}
	 //有修改方向的话，就把计算清零
	if((direction=="向下"||direction=="全部")&&(direction_last=="向上")||
	(direction_last=="向下"||direction_last=="全部")&&(direction=="向上")){
		$("#zfcz_count_down").val("0");
		$("#zfcz_count_up").val("init");
		zfcz_upArry=[];
		zfcz_downArry=[];
		zfcz_countIndex=0;
	} 
	var ids = $("#"+tableId).getDataIDs();
	var rows = $("#"+tableId).jqGrid("getRowData");
	for(var i=0;i<ids.length;i++){
		if(rows[i][range].indexOf(content)!=-1){
			rows[i]['matchedRowId']=ids[i];//满足条件则添加一个标记列,且值为id值
		}
	}
	$.each(rows, function(i,rowData){
		 if(rowData.matchedRowId){
		 	idArr[idArr.length]=rowData.matchedRowId;//把满足条件的记录的id存入数组
		}
	});	
	if(idArr.length==0){
		$("#zfcz_totolNum").text("没有找到满足条件的记录！").css("color","red");;
		return;
	}
	
	if(direction=="向下"){//方向向下
		//获取当前选中行id在满足条件的id数组中的位置
		for(var i=0;i<=idArr.length-1;i++){
			if(idArr[i]>parseInt(rowId, 0)){
				zfcz_countIndex=i;
				break;
			}else{
				zfcz_countIndex=null;
			}
		}
		$("#zfcz_direction_last").val("向下");
		$("#zfcz_totolNum").text("");
		if(zfcz_countIndex!=null){
			for(var j=parseInt(zfcz_countIndex, 0);j<idArr.length;j++){
				if(zfcz_downArry.length<idArr.length-parseInt(zfcz_countIndex, 0)+1){
					zfcz_downArry.push(idArr[j]);
				}
			}
		}else{
			$("#zfcz_totolNum").text("向下已经没有满足条件的记录了！").css("color","red");;
			return;
		}
		if(zfcz_downArry.length>zfcz_count_down){
			for(var i=0;i<ids.length;i++){//找到选中的id在所有ids数组中的位置，计算滚动距离用
				if(idArr[zfcz_count_down]==ids[i]){
					scrollIdIndex=i;
					break;
				}
			}
			$('.ui-jqgrid .ui-jqgrid-bdiv').scrollTop(24*scrollIdIndex);//滚动条滚动
			$("#"+tableId).jqGrid("setSelection", zfcz_downArry[zfcz_count_down]);//选中此行
			$("#zfcz_count_down").val(zfcz_count_down+1);
		}else{
			$("#"+tableId).jqGrid("setSelection", zfcz_downArry[zfcz_count_down-1]);//往后没有数据，选中之前的最后一行
			$("#zfcz_totolNum").text("向下已经没有满足条件的记录了！").css("color","red");;
		}
	}else if(direction=="向上"){//方向向上
		//获取当前选中行id在满足条件的id数组中的位置
		for(var i=idArr.length-1;i>=0;i--){
			if(idArr[i]<parseInt(rowId, 0)){
				zfcz_countIndex=i;
				break;
			}else{
				zfcz_countIndex=null;
			}
		}
		$("#zfcz_direction_last").val("向上");
		$("#zfcz_totolNum").text("");
		if(zfcz_countIndex!=null){
			for(var j=0;j<=zfcz_countIndex;j++){
				if(zfcz_upArry.length<zfcz_countIndex+1){
					zfcz_upArry.push(idArr[j]);
				}
			}
		}else{
			$("#zfcz_totolNum").text("向上已经没有满足条件的记录了！").css("color","red");
			return;
		}
		if(zfcz_upArry.length>0){
			if(isNaN(zfcz_count_up)){zfcz_count_up=zfcz_upArry.length-1;}
			if(zfcz_count_up>=0){
				for(var i=0;i<ids.length;i++){
					if(idArr[zfcz_count_up]==ids[i]){
						scrollIdIndex=i;
						break;
					}
				}
				$('.ui-jqgrid .ui-jqgrid-bdiv').scrollTop(24*scrollIdIndex);//滚动条滚动
				$("#"+tableId).jqGrid("setSelection", zfcz_upArry[zfcz_count_up]);//选中此行
				$("#zfcz_count_up").val(zfcz_count_up-1);
			}else{
				$("#zfcz_totolNum").text("向上已经没有满足条件的记录了！").css("color","red");
			}
		}
	}else{//全部
		$("#zfcz_direction_last").val("全部");
		if(idArr.length>zfcz_count_down){
			for(var i=0;i<ids.length;i++){
				if(idArr[zfcz_count_down]==ids[i]){
					scrollIdIndex=i;
					break;
				}
			}
			$("#zfcz_totolNum").text("共找到"+idArr.length+"条记录，当前第"+(zfcz_count_down+1)+"条。").css("color","black");
			$('.ui-jqgrid .ui-jqgrid-bdiv').scrollTop(24*scrollIdIndex);//滚动条滚动
			$("#"+tableId).jqGrid("setSelection", idArr[zfcz_count_down]);//选中此行
			$("#zfcz_count_down").val(zfcz_count_down+1);
		}else{
			$('.ui-jqgrid .ui-jqgrid-bdiv').scrollTop(24*(idArr[0]-1));//滚动条滚动
			$("#"+tableId).jqGrid("setSelection", idArr[0]);//往后没有数据，选中第一行
			$("#zfcz_count_down").val("0");
			$("#zfcz_totolNum").text("");
		}
	}
}
/***
 * 下拉框根据code找descr add by cjg 2018/11/23
 * @param codeId 下拉框id
 * @param descrId 存放descr隐藏框的id
 */
function getDescrByCode(codeId,descrId){
	var selectText=$("#"+codeId).find("option:selected").text();
	var arr = selectText.split(" ");
	var descr=arr[1];
	if(descr=="请选择..."){
		descr="";
	}
	$("#"+descrId).val(descr);
}
/**
 * 开启列名title（如果colModel中有title，就先显示title内容）
 * add by zb on 20200113
 * @param tableId 表格ID
 * @param option jqGrid参数
 */
function showColumnTitle(tableId, option) {
	var colModel = option.colModel;
	if (tableId && colModel) {
		$.each(colModel, function (i, val) {
			var triggerElement = "th#"+tableId+"_"+val.name;
			if (val.title) addColTitle(triggerElement, val.title);
			else addColTitle(triggerElement, val.label);
		});
	}
}
/**
 * 添加列名title
 * add by zb on 20200113
 * @param triggerElement 绑定元素
 * @param content 内容
 */
function addColTitle(triggerElement, content) {
	/*
	将所有特殊符号（除了_、#）前面加一个斜杠
	转义只需要一个斜杠就行，不是JQuery文档说得双个
	*/
	triggerElement = triggerElement.replace(/((?=[\x21-\x7e]+)[^A-Za-z0-9_#])/g,"\\\$1");
	content = content.replace(/<\/br>/g, "");
	$(triggerElement).prop("title", content);
}
/**
 * Bootstrap 弹出框
 * add by zb on 20200110
 * @param triggerElement 绑定元素
 * @param content 内容
 */
function popover(triggerElement, content) {
	triggerElement = triggerElement.replace(/((?=[\x21-\x7e]+)[^A-Za-z0-9_#])/g,"\\\$1");
	content = content.replace(/<\/br>/g, "");
	var popoverOptions = {
		trigger: "hover", //click 点击| hover 悬停| focus 聚焦| manual 自定义
		placement: "top", //显示位置
		container: ".body-box-list",//指定弹出框父元素，否则默认绑定的触发元素。
		content: content,//内容
	};
	$(triggerElement).popover(popoverOptions);
}
/**
 * 替换成5.3.2版本的columnChooser方法
 * add by zb on 20190112
 * opts.useIndex: 是否使用Index作为列名显示
 */
function useNewColumnChooser() {
	$.jgrid.extend({
	    columnChooser : function(opts) {
			var self = this, selector, select, colMap = {}, fixedCols = [], dopts, mopts, $dialogContent, multiselectData, listHeight,
				colModel = self.jqGrid("getGridParam", "colModel"),
				colNames = self.jqGrid("getGridParam", "colNames"),
				getMultiselectWidgetData = function ($elem) {
					return ($.ui.multiselect.prototype && $elem.data($.ui.multiselect.prototype.widgetFullName || $.ui.multiselect.prototype.widgetName)) ||
						$elem.data("ui-multiselect") || $elem.data("multiselect");
				},
				regional =  $.jgrid.getRegional(this[0], 'col');

			if ($("#colchooser_" + $.jgrid.jqID(self[0].p.id)).length) { return; }
			selector = $('<div id="colchooser_'+self[0].p.id+'" style="position:relative;overflow:hidden"><div><select multiple="multiple"></select></div></div>');
			select = $('select', selector);

			/*if (opts && opts.notShowCol) {opts.notShowCol.forEach(function(v){for (var j = colModel.length - 1; j >= 0; j--) {if (v == colModel[j].name) colModel.splice(j, 1); } }); }*/ 
			//先检测是否存在opts，在检测useIndex
			if (opts && opts.useIndex) {
				// 用from()获取colModel的index到新的参数中去
				// var colIndexs = Array.from(colModel,function (x) { return x.index; }); //CS上不能用Array.from()
				var colIndexs = [];
				$.each(colModel, function(i, e) { colIndexs.push(e.index); }); 
				colNames = colIndexs;
			}

			function insert(perm,i,v) {
				var a, b;
				if(i>=0){
					a = perm.slice();
					b = a.splice(i,Math.max(perm.length-i,i));
					if(i>perm.length) { i = perm.length; }
					a[i] = v;
					return a.concat(b);
				}
				return perm;
			}
			function call(fn, obj) {
				if (!fn) { return; }
				if (typeof fn === 'string') {
					if ($.fn[fn]) {
						$.fn[fn].apply(obj, $.makeArray(arguments).slice(2));
					}
				} else if ($.isFunction(fn)) {
					fn.apply(obj, $.makeArray(arguments).slice(2));
				}
			}
			function resize_select() {

				var widgetData = getMultiselectWidgetData(select),
				$thisDialogContent = widgetData.container.closest(".ui-dialog-content");
				if ($thisDialogContent.length > 0 && typeof $thisDialogContent[0].style === "object") {
					$thisDialogContent[0].style.width = "";
				} else {
					$thisDialogContent.css("width", ""); // or just remove width style
				}

				widgetData.selectedList.height(Math.max(widgetData.selectedContainer.height() - widgetData.selectedActions.outerHeight() -1, 1));
				widgetData.availableList.height(Math.max(widgetData.availableContainer.height() - widgetData.availableActions.outerHeight() -1, 1));
			}

			opts = $.extend({
				width : 400,
				height : 240,
				classname : null,
				done : function(perm) { if (perm) { self.jqGrid("remapColumns", perm, true); } },
				/* msel is either the name of a ui widget class that
				   extends a multiselect, or a function that supports
				   creating a multiselect object (with no argument,
				   or when passed an object), and destroying it (when
				   passed the string "destroy"). */
				msel : "multiselect",
				/* "msel_opts" : {}, */

				/* dlog is either the name of a ui widget class that 
				   behaves in a dialog-like way, or a function, that
				   supports creating a dialog (when passed dlog_opts)
				   or destroying a dialog (when passed the string
				   "destroy")
				   */
				dlog : "dialog",
				dialog_opts : {
					minWidth: 470,
					dialogClass: "ui-jqdialog"
				},
				/* dlog_opts is either an option object to be passed 
				   to "dlog", or (more likely) a function that creates
				   the options object.
				   The default produces a suitable options object for
				   ui.dialog */
				dlog_opts : function(options) {
					var buttons = {};
					buttons[options.bSubmit] = function() {
						options.apply_perm();
						options.cleanup(false);
					};
					buttons[options.bCancel] = function() {
						options.cleanup(true);
					};
					return $.extend(true, {
						buttons: buttons,
						close: function() {
							options.cleanup(true);
						},
						modal: options.modal || false,
						resizable: options.resizable || true,
						width: options.width + 70,
						resize: resize_select
					}, options.dialog_opts || {});
				},
				/* Function to get the permutation array, and pass it to the
				   "done" function */
				apply_perm : function() {
					var perm = [];
					$('option',select).each(function() {
						if ($(this).is(":selected")) {
							self.jqGrid("showCol", colModel[this.value].name);
						} else {
							self.jqGrid("hideCol", colModel[this.value].name);
						}
					});
					
					//fixedCols.slice(0);
					$('option[selected]',select).each(function() { perm.push(parseInt(this.value,10)); });
					$.each(perm, function() { delete colMap[colModel[parseInt(this,10)].name]; });
					$.each(colMap, function() {
						var ti = parseInt(this,10);
						perm = insert(perm,ti,ti);
					});
					if (opts.done) {
						opts.done.call(self, perm);
					}
					self.jqGrid("setGridWidth", self[0].p.width, self[0].p.shrinkToFit);
				},
				/* Function to cleanup the dialog, and select. Also calls the
				   done function with no permutation (to indicate that the
				   columnChooser was aborted */
				cleanup : function(calldone) {
					call(opts.dlog, selector, 'destroy');
					call(opts.msel, select, 'destroy');
					selector.remove();
					if (calldone && opts.done) {
						opts.done.call(self);
					}
				},
				msel_opts : {}
			}, regional, opts || {} );
			if($.ui) {
				if ($.ui.multiselect && $.ui.multiselect.defaults) {
					if (!$.jgrid._multiselect) {
						// should be in language file
						alert("Multiselect plugin loaded after jqGrid. Please load the plugin before the jqGrid!");
						return;
					}
					// ??? the next line uses $.ui.multiselect.defaults which will be typically undefined
					opts.msel_opts = $.extend($.ui.multiselect.defaults, opts.msel_opts);
				}
			}
			if (opts.caption) {
				selector.attr("title", opts.caption);
			}
			if (opts.classname) {
				selector.addClass(opts.classname);
				select.addClass(opts.classname);
			}
			if (opts.width) {
				$(">div",selector).css({width: opts.width,margin:"0 auto"});
				select.css("width", opts.width);
			}
			if (opts.height) {
				$(">div",selector).css("height", opts.height);
				select.css("height", opts.height - 10);
			}

			select.empty();
			$.each(colModel, function(i) {
				colMap[this.name] = i;
				if (this.hidedlg) {
					if (!this.hidden) {
						fixedCols.push(i);
					}
					return;
				}

				select.append("<option value='"+i+"' "+
							  (this.hidden?"":"selected='selected'")+">"+$.jgrid.stripHtml(colNames[i])+"</option>");
			});

			dopts = $.isFunction(opts.dlog_opts) ? opts.dlog_opts.call(self, opts) : opts.dlog_opts;
			call(opts.dlog, selector, dopts);
			mopts = $.isFunction(opts.msel_opts) ? opts.msel_opts.call(self, opts) : opts.msel_opts;
			call(opts.msel, select, mopts);

			// fix height of elements of the multiselect widget
			$dialogContent = $("#colchooser_" + $.jgrid.jqID(self[0].p.id));
			// fix fontsize
			var fs =  $('.ui-jqgrid').css('font-size') || '11px';
			$dialogContent.parent().css("font-size",fs);

			$dialogContent.css({ margin: "auto" });
			$dialogContent.find(">div").css({ width: "100%", height: "100%", margin: "auto" });

			multiselectData = getMultiselectWidgetData(select);
			multiselectData.container.css({ width: "100%", height: "100%", margin: "auto" });

			multiselectData.selectedContainer.css({ width: multiselectData.options.dividerLocation * 100 + "%", height: "100%", margin: "auto", boxSizing: "border-box" });
			multiselectData.availableContainer.css({ width: (100 - multiselectData.options.dividerLocation * 100) + "%", height: "100%", margin: "auto", boxSizing: "border-box" });

			// set height for both selectedList and availableList
			// 将auto修改为固定值 modify by zb
			// multiselectData.selectedList.css("height", "auto");
			// multiselectData.availableList.css("height", "auto");
			multiselectData.selectedList.css("height", "300px");
			multiselectData.availableList.css("height", "300px");
			listHeight = Math.max(multiselectData.selectedList.height(), multiselectData.availableList.height());
			listHeight = Math.min(listHeight, $(window).height());
			multiselectData.selectedList.css("height", listHeight);
			multiselectData.availableList.css("height", listHeight);
			
			resize_select();
		},
	});
}

function moneyToCapital(e,id) {
	var num = $("#"+e.id).val().replace(/[^\-?\d.]/g,'');
    $("#"+e.id).val(num);
    var minus = "";
    var text = num + "";
    if (text.indexOf("-") > -1) {
        num = text.replace("-", "");
        minus = "负"
    }
    var money1 = new Number(num);
    var monee = Math.round(money1 * 100).toString(10);
    var leng = monee.length;
    var monval = "";
    for (i = 0; i < leng; i++) {
        monval = monval + to_upper(monee.charAt(i)) + to_mon(leng - i - 1)
    }
    $("#"+id).val(minus + repace_acc(monval));
}
// 将数字转为大写的中文字
function to_upper(a) {
    switch (a) {
    case "0":
        return "零";
        break;
    case "1":
        return "壹";
        break;
    case "2":
        return "贰";
        break;
    case "3":
        return "叁";
        break;
    case "4":
        return "肆";
        break;
    case "5":
        return "伍";
        break;
    case "6":
        return "陆";
        break;
    case "7":
        return "柒";
        break;
    case "8":
        return "捌";
        break;
    case "9":
        return "玖";
        break;
    default:
        return ""
    }
}
function to_mon(a) {
    if (a > 10) {
        a = a - 8;
        return (to_mon(a))
    }
    switch (a) {
    case 0:
        return "分";
        break;
    case 1:
        return "角";
        break;
    case 2:
        return "元";
        break;
    case 3:
        return "拾";
        break;
    case 4:
        return "佰";
        break;
    case 5:
        return "仟";
        break;
    case 6:
        return "万";
        break;
    case 7:
        return "拾";
        break;
    case 8:
        return "佰";
        break;
    case 9:
        return "仟";
        break;
    case 10:
        return "亿";
        break
    }
}
function repace_acc(Money) {
    Money = Money.replace("零分", "");
    Money = Money.replace("零角", "零");
    var yy;
    var outmoney;
    outmoney = Money;
    yy = 0;
    while (true) {
        var lett = outmoney.length;
        outmoney = outmoney.replace("零元", "元");
        outmoney = outmoney.replace("零万", "万");
        outmoney = outmoney.replace("零亿", "亿");
        outmoney = outmoney.replace("零仟", "零");
        outmoney = outmoney.replace("零佰", "零");
        outmoney = outmoney.replace("零零", "零");
        outmoney = outmoney.replace("零拾", "零");
        outmoney = outmoney.replace("亿万", "亿零");
        outmoney = outmoney.replace("万仟", "万零");
        outmoney = outmoney.replace("仟佰", "仟零");
        yy = outmoney.length;
        if (yy == lett) {
            break
        }
    }
    yy = outmoney.length;
    if (outmoney.charAt(yy - 1) == "零") {
        outmoney = outmoney.substring(0, yy - 1)
    }
    yy = outmoney.length;
    if (outmoney.charAt(yy - 1) == "元") {
        outmoney = outmoney + "整"
    }
    return outmoney
}
/**
 * 材料类型2联动多选 add by cjg 2019/08/15
 */
function checkItemType2(id,childId){
	id = id?id:'itemType2';
	childId = childId?childId:'itemType3';
    var setting = "{\n" +
	"	check: {\n" +
	"		enable: true,\n" +
	"		chkboxType: {\"Y\":\"ps\", \"N\":\"ps\"}\n" +
	"	},\n" +
	"	view: {\n" +
	"		dblClickExpand: false\n" +
	"	},\n" +
	"	data: {\n" +
	"		simpleData: {\n" +
	"			enable: true,\n" +
	"		}\n" +
	"	},\n" +
	"	callback: {\n" +
	"		beforeCheck: beforeCheck_"+childId+",\n" +
	"		onCheck: onCheck_"+childId+"\n" +
	"	}\n" +
	"}\n\n";
	var datas = {"code" : $("#"+id).val()};
	$.ajax({
		url:ctx+'/admin/itemType2/changeItemType2s',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
	
	    },
	    success: function(obj){
	    	if (obj){
	    		$("#"+childId).val('');
	    		$("#"+childId+"_NAME").val('');
	    		$.fn.zTree.getZTreeObj("tree_"+childId).checkAllNodes(false);
	    		$.fn.zTree.init($("#tree_"+childId), eval("("+setting+")"), eval("("+obj.strSelect+")"));
			}
	    }
	 });
}
/**
 * 日期加上天数得到新的日期,日期格式：YYYY-MM-DD  add by cjg 2020/1/3
 * @dateTemp 需要参加计算的日期，
 * @days 要添加的天数
 */
function getAddDate(dateTemp, days) {  
    var dateTemp = dateTemp.split("-");  
    var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式    
    var millSeconds = Math.abs(nDate) + (days * 24 * 60 * 60 * 1000);  
    var rDate = new Date(millSeconds);  
    var year = rDate.getFullYear();  
    var month = rDate.getMonth() + 1;  
    if (month < 10) month = "0" + month;  
    var date = rDate.getDate();  
    if (date < 10) date = "0" + date;  
    return (year + "-" + month + "-" + date);  
}
/**
 * 日期相减算法,日期格式：YYYY-MM-DD  add by cjg 2020/5/8
 * @beginDate 开始时间
 * @endDate 结束时间
 */
function dateMinus(beginDate,endDate){ 
   var beginDateFmt = new Date(beginDate.replace(/-/g, "/"));
   var endDateFmt = new Date(endDate.replace(/-/g, "/"));
   var days = endDateFmt.getTime() - beginDateFmt.getTime(); 
   var day = parseInt(days / (1000 * 60 * 60 * 24)); 
   if(isNaN(day)){
  	 return "";
  }
  return day; 
}
/**
 * 获取前后n天的日期 add by cjg 2020/6/15
 * @addDays 增加天数
 */
function getAddDays(date,addDays) { 
   date.setDate(date.getDate()+addDays);//获取addDays天后的日期
   var y = date.getFullYear(); 
   var m = (date.getMonth()+1)<10?"0"+(date.getMonth()+1):(date.getMonth()+1);//获取当前月份的日期，不足10补0
   var d = date.getDate()<10?"0"+date.getDate():date.getDate();//获取当前几号，不足10补0
   return y+"-"+m+"-"+d; 
}

//返回光标在元素中的位置
function getFocus(elem) {
    var index = 0;
    if (document.selection) {// IE Support
        elem.focus();
        var Sel = document.selection.createRange();
        if (elem.nodeName === 'TEXTAREA') {//textarea
            var Sel2 = Sel.duplicate();
            Sel2.moveToElementText(elem);
            var index = -1;
            while (Sel2.inRange(Sel)) {
                Sel2.moveStart('character');
                index++;
            };
        }
        else if (elem.nodeName === 'INPUT') {// input
            Sel.moveStart('character', -elem.value.length);
            index = Sel.text.length;
        }
    }
    else if (elem.selectionStart || elem.selectionStart == '0') { // Firefox support
        index = elem.selectionStart;
    }
    return (index);
}

//设置光标的位置
function selectRange(id, start, end) {
	var element = $("#"+id);

	if(end === undefined) {
		end = start;
	}
	return element.each(function() {
		if('selectionStart' in this) {
			this.selectionStart = start;
			this.selectionEnd = end;
			this.setSelectionRange(start, end);
		} else if(this.setSelectionRange) {
			this.setSelectionRange(start, end);
		} else if(this.createTextRange) {
			var range = this.createTextRange();
			range.collapse(true);
			range.moveEnd('character', end);
			range.moveStart('character', start);
			range.select();
		}
		element.focus();
	});
};

function filterHtml(value){
	if(!value) {
		value = "";
	}
	return value.replace(/<\/?[^>]*>/g, '');
}

function refreshToken() {
	$.ajax({
		url: ctx+"/admin/formToken/newToken",
		type: 'post',
		data: null,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		timeout: 10000,
		dataType: 'json',
		cache: false,
		error: function(){
		},
		success: function(XMLHttpRequest){
			if(XMLHttpRequest.token && XMLHttpRequest.token.token){
				$("#_form_token_uniq_id").val(XMLHttpRequest.token.token);//重写token到表单
			}
		}
	});
}