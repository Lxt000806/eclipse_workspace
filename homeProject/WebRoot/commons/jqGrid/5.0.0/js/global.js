//操作父窗体元素的全局变量
function getDialog(index){
	if(index){
		return Global.Dialog.parentDialogIds[index];
	}else{
		return Global.Dialog.parentDialogIds;
	}
}
function setDialog(dialogId){
	Global.Dialog.parentDialogIds.push(dialogId);
}
function delDialog(){
	return Global.Dialog.parentDialogIds.pop();
}
//操作隐藏窗口元素的全局变量 add by zzr 2018/06/29
function getHideDialog(index){
	if(index){
		return Global.Dialog.hideDialogIds[index];
	}else{
		return Global.Dialog.hideDialogIds;
	}
}
function setHideDialog(dialogId){
	Global.Dialog.hideDialogIds.push(dialogId);
}
function delHideDialog(){
	return Global.Dialog.hideDialogIds.pop();
}
//操作子窗体的全局变量
function returnData(){
	return Global.Dialog.returnData;
}

function createSerchDiv(gridOption,tableId){
	var html="<div class=\"table-search\" id=\"show\"><button  class=\"btn btn-sm btn-link \" onclick=\"showSel()\">查询</button></div><li  class=\"ul-form\"><div id=\"selectWord\"><label class=\"btn btn-sm \" >列名</label><select id=\"selectTitle\" name=\"selectTitle\" style=\"-webkit-border-radius:4px;width: 160px;\"><option value=\"\">请选择...</option></select><label class=\"btn btn-sm \" >关键字</label><input type=\"text\" class=\"liInput\"  id=\"word\" name=\"word\" style=\"-webkit-border-radius:4px;height:24px ;width:160px;\"  />                                             <button  class=\"btn btn-sm btn-link \" onclick=\"doSelect()\" id=\"sel\">查询</button><button  class=\"btn btn-sm btn-link \" onclick=\"hiddenSel()\" id=\"hid\">隐藏</button></div></li> 		";
	
	$("#table_serch").append(html); 
	$("#selectWord").attr("hidden",true);
	$("#hid").attr("hidden",true);
	$("#sel").attr("hidden",true);
	
	for(var i=0;i<gridOption.colModel.length;i++){
		if(!gridOption.colModel[i].hidden){
			$("#selectTitle").append("<option value='"+gridOption.colModel[i].name+"'>"+gridOption.colModel[i].label+"</option>"); 
		}
	}
	$('input').keydown(function(e){
		if(e.keyCode==13){
			tableSerch(tableId);
		}
	});
	window.doSelect= function(){
		tableSerch(tableId);
	}
}
var selectTitle ='';
var word ='';
function tableSerch(tableId){
	var x=$('#'+tableId).jqGrid('getGridParam','selrow');
	var ids = $("#"+tableId).jqGrid('getDataIDs');
	if(selectTitle!=$.trim($("#selectTitle").val())){
		selectTitle=$.trim($("#selectTitle").val());
		x=0;
	}
	if(word!=$.trim($("#word").val())){
		word=$.trim($("#word").val());
		x=0;
	}
	if(selectTitle==''){
		art.dialog({
			content:'请选择列名',
		});
		return ;
	}	
	var selectTitleString = Global.JqGrid.allToJson(tableId,selectTitle);
	var arr = new Array();
		arr = selectTitleString.fieldJson.split(",");
	for(var i=x;i<arr.length;i++){
		var str=new Array;
			str=arr[i].split($.trim($("#word").val()));
			x++;
		if(str.length>1){
			Global.JqGrid.jqGridSelectAll(tableId,false);
			$("#"+tableId).jqGrid('setSelection',ids[x-1], true);
			$('.ui-jqgrid-bdiv').scrollTop((x-1)*30);
			
			return ;
		}
	}
}
function showSel(){
	$("#selectWord").removeAttr("hidden",true);
	$("#hid").removeAttr("hidden",true);
	$("#sel").removeAttr("hidden",true);
	$("#show").attr("hidden",true);
}
function hiddenSel(){
	$("#selectWord").attr("hidden",true);
	$("#hid").attr("hidden",true);
	$("#show").removeAttr("hidden",true);
}

var Global ={
	randomInt:100000,
	//弹窗操作
	Dialog:{
		//保留窗口层级名称
		parentDialogIds:[],
		//隐藏窗口层级名称 add by zzr 2018/06/29
		hideDialogIds:[],
		//设置窗口返回值
		returnData:{},
		//重置传参postData json对象
		resetPostData:function(postData){
			var str = "";
			if (typeof(postData)=='object'){
				for(var key in postData){
			        str = str + "<input type='hidden' id='"+key+"' name='"+key+"' value='"+postData[key]+"'/>";
			    }
			}
			return str;
		},
		//打开窗口
		showDialog:function(dialogId,option,closeFn){
			if(!option.url){
				alert("传递的参数中无url属性！");
				return;
			}
			if(top.$("#dialog_"+dialogId).attr("id")){
				//alert("已经存在ID="+dialogId+"的窗口，请重新定义一个新的ID");
				//return;
				top.$("#dialog_"+dialogId).dialog("destroy").remove();
				top.delDialog();
				top.delHideDialog();//add by zzr 2018/06/29
			}
			if(option.height && top.innerHeight && option.height>top.innerHeight){
				option.height = top.innerHeight-10
			}
			//if(option.width && top.innerWidth && option.width>top.innerWidth){
			//	option.width = top.innerWidth-10
			//}
			var dialogOption = {
				  title:"操作窗口",
				  dialogClass:"no-close",  
				  height:600,
				  width:1000,
				  autoOpen:true,
				  modal: true,
				  closeOnEscape: false,
				  close:function(){
					  //goto_query();//这里能不能加这个。
					  var retData = {};
					  if (top.$("#iframe_"+dialogId)[0].contentWindow.returnData){
						  retData = top.$("#iframe_"+dialogId)[0].contentWindow.returnData();
					  }
					  if(top.$("#dialog_"+dialogId).dialog("option","isCallBack")
							  && option.returnFun && $.isFunction(option.returnFun)){  
						  option.returnFun(retData);
					  }else{
						  if (!retData || JSON.stringify(retData)=='{}'){
							  var objStr = top.$("#iframe_"+dialogId)[0].contentWindow.document.getElementById("dataTableExists_selectRowAll");
							  if (objStr && objStr.value){
								  retData = eval("("+objStr.value+")");
								  option.returnFun(retData);
							  }
						  }
					  }
					  top.$("#dialog_"+dialogId).dialog("destroy").remove();
					  top.delDialog();
					  if(closeFn)  closeFn();
					
				  }
			};
			$.extend(dialogOption,option);
			if(!top.$("#dialog_"+dialogId).attr("id")){
				if(!top.$("#dialog_postdata_form").attr("id")){
					top.$("body").append(
						"<form id='dialog_postdata_form' action='' method='post' target=''>" +
							"<input type='hidden' id='dialog_postdata_form_postdata' name='postData'/>" + 
							Global.Dialog.resetPostData(option.postData) +
						"</form>"
					);
				}else{
					top.$("#dialog_postdata_form").html("<input type='hidden' id='dialog_postdata_form_postdata' name='postData'/>" + 
							Global.Dialog.resetPostData(option.postData));
				}
				top.$("body").append("<div id='dialog_"+dialogId+"'><iframe id='iframe_"+dialogId+"' name='iframe_"+dialogId+"' frameborder='0' src='' width='100%' height='99%'></iframe></div>");
				$.extend(dialogOption,{
					open:function(event, ui){
						top.$("#dialog_postdata_form_postdata").val(JSON.stringify(option.postData));
						top.$("#dialog_postdata_form").attr("action",option.url).attr("target","iframe_"+dialogId).submit();
					}
				});
				top.setDialog(dialogId);
			
				//添加到隐藏窗口列表中 add by zzr 2018/06/29
				if(dialogOption.hideDialog == true){
					top.setHideDialog(dialogId);
				}
			}
			
			//预览时，添加遮罩层，防止右键打印  add by cjg 2020/12/10
			if(option.shade){
				//top.$("#dialog_"+dialogId).width("98%");
				top.$("#dialog_"+dialogId).prepend("<div style='width: 95.5%;height: 98%;position: absolute;z-index: 9999;'></div>");
			}
			
			var dialogWindow = top.$("#dialog_"+dialogId).dialog(dialogOption);
			
			//增加弹窗隐藏效果(主要针对打印部分跳过预览) add by zzr 2018/06/28 
			if(dialogOption.hideDialog == true){
				top.$("#dialog_"+dialogId).parent().css("display", "none");
			}
			
			return dialogWindow;
		},
		//关闭窗口
		closeDialog:function(isCallBack){
			//增加隐藏窗口同时关闭(打印部分) add by zzr 2018/06/29 begin
			var hideDialogIds = top.getHideDialog();
			while(hideDialogIds.length > 0){
				var i = hideDialogIds.length - 1;
				top.$("#dialog_"+hideDialogIds[i]).dialog("close"); 
				top.delHideDialog();
			}
			//增加隐藏窗口同时关闭(打印部分) add by zzr 2018/06/29 end

			var parentDialogIds = top.getDialog(),dialogId = parentDialogIds[parentDialogIds.length-1];
			top.$("#dialog_"+dialogId).dialog("option","isCallBack",isCallBack==undefined?true:isCallBack);
			top.$("#dialog_"+dialogId).dialog("close");
		},
		//设置返回值
		setRetValue:function(retId,retValue){
			var parentDialogIds = top.getDialog();
			if(parentDialogIds.length < 2){//到tab下查询父窗体
				$.each($("#framecenter").find(".l-tab-content-item"),function(k,v){
					if($(v).css("display")=="" || $(v).css("display")=="block"){
						if($(v).find("iframe").eq(0).contentWindow.document.$("#"+retId).attr("id")){
							$(v).find("iframe").eq(0).contentWindow.document.$("#"+retId).val(retValue);
						}
					}else{
						alert("父亲窗口中没有该元素！");
						return false;
					}
				});
			}else{
				var parentDialogId = top.getDialog[parentDialogIds.length-2];
				if(!top.$("#iframe_"+parentDialogId).contentWindow.document.$("#"+retId).attr("id")){
					alert("父亲窗口中没有该元素！");
					return false;
				}
				top.$("#iframe_"+parentDialogId).contentWindow.document.$("#"+retId).val(retValue);
			}
		},
		//弹出选择窗口
		fetch:function(fetchId,url,title,key,value){
			Global.Dialog.showDialog("fetch_"+fetchId,{
				title:title,
				url:url,
				height: 600,
				width:1000,
				returnFun:function(returnData){
					//alert(JSON.stringify(returnData));
					if(returnData && returnData[key]){
						$("#"+fetchId).val(returnData[key]);
						if($("#"+fetchId+"_span") && value){
							$("#"+fetchId+"_span").html(returnData[value]);
						}
						if($("#"+fetchId+"_input") && value){
							$("#"+fetchId+"_input").val(returnData[value]);
						}
						$("#"+fetchId).valid && $("#"+fetchId).valid();
					}
				}
			});		
		},
		//弹出信息窗口
		infoDialog:function(content,lock){
			return art.dialog({content: content,width: 200,lock:lock});
		}
	},
	//jqGrid方法
	JqGrid:{
		//记录表格前端做的增、删、改记录
		jqGridAUDJson:{},
		//初始化表格
		initJqGrid:function(tableId,option){
			if(!option.colModel){
				alert("请初始化属性列colModel！");
				return;
			}
			var gridCompleteFun;
			if(option.gridComplete && $.isFunction(option.gridComplete)){
				gridCompleteFun = option.gridComplete;
				delete option.gridComplete;
			}
			var onCellSelectFun;
			if(option.onCellSelect && $.isFunction(option.onCellSelect)){
				onCellSelectFun = option.onCellSelect;
				delete option.onCellSelect;
			}
			/* modify by zb on 20190628
			 * var loadCompleteFun;
			if(option.loadComplete && $.isFunction(option.loadComplete)){
				loadCompleteFun = option.loadComplete;
				delete option.loadComplete;
			}*/
			
			var sumCols = [];
			$.each(option.colModel,function(k,v){
				if(v.sum||v.avg||v.count||v.min||v.max){
					sumCols.push({col:v.name,type:(v.sum&&"sum")||(v.avg&&"avg")||(v.count&&"count")||(v.min&&"min")||(v.max&&"max")});
				}
			});
			var minHeight = 250;
			//if(option.height && option.height > minHeight){
			if(option.height){
				minHeight = option.height; 
			}
			if(option.isSelectOne == undefined){
				option.isSelectOne = true;
			}
			var jqGridOption = {
				url : "",
	   	        mtype : "post",
	   	        datatype : "json",
	   	        postData : {},
	   	     	colModel : [],
	   	        autowidth : true,
	   	        height:minHeight,
	   	        width:"100%",
	   	        rowNum : 50,
	   	        rowList : [50,100,200,500,1000],
	   	        viewrecords : true,
	   	        pager : $("#"+tableId+"Pager"),
	   	        altRows:true,
	   	        altclass:"altrow",
	   	        rownumbers:true,
		   	    shrinkToFit:false,
		   	    autoScroll: true,
		   	    footerrow:sumCols.length>0?true:false,
		   	    sortable: false,
		   	    styleUI: 'Bootstrap',
		   	    gridComplete:function(){
		   	    	if(sumCols.length > 0){
		   	    		var footerData = {};
		   	    		footerData["rn"] = "合计";
		   	    		$.each(sumCols,function(k,v){
		   	    			var d = $("#"+tableId).getCol(v.col,false,v.type);
		   	    			footerData[v.col] = d!=parseInt(d)?d.toFixed(4):parseInt(d);
		   	    			$("#"+tableId+"_"+v.col+"_"+v.type).val(footerData[v.col]);
		   	    		});
		   	    		$("#"+tableId).footerData("set",footerData,false);
		   	    	}
		   	    	//非多选、非编辑且非选中状态下默认选中第一行
		   	    	if(!option.multiselect && !option.cellEdit && !$("#"+tableId).jqGrid("getGridParam","selrow") && option.isSelectOne){
			   	    	var ids = $("#"+tableId).jqGrid('getDataIDs');
			   	    	$("#"+tableId).jqGrid("setSelection",ids[0],true);
		   	    	}
		   	    	$("#gbox_"+tableId).css({"width":"100%"});
		   			$("#gview_"+tableId).css({"width":"100%"});
		   			$("#gview_"+tableId+" .ui-jqgrid-hdiv").css({"width":"100%"});
		   			$("#gview_"+tableId+" .ui-jqgrid-bdiv").css({"width":"100%"});
		   			$("#gview_"+tableId+" .ui-jqgrid-sdiv").css({"width":"100%"});
		   			
		   			if(gridCompleteFun){
		   	    		gridCompleteFun();
		   	    	}
		   	    },
		   		onCellSelect:function(rowid,iCol,cellcontent,e){
		   			if(onCellSelectFun){
		   				onCellSelectFun.apply(this,arguments);
		   	    	}
		   			if(option.searchBtn){
		   				initDefaultRenge(iCol,tableId);
		   			}
	        	},
	        	/* modify by zb on 20190628
	        	 * loadComplete: function(xhr){
	        		// 自动隐藏列
	        		if(xhr && xhr.hideColumns && xhr.hideColumns != ""){
	        			var hideColumns = xhr.hideColumns.split("|");
	        			for(var i = 0;i < hideColumns.length;i++){
	        				if(hideColumns[i] && hideColumns[i] != ""){
	        					$("#"+tableId).jqGrid("hideCol", hideColumns[i]);
	        				}
	        			}
	        		}
		   			if(loadCompleteFun){
		   				loadCompleteFun(xhr);
		   	    	}
	        	}*/
			};
			$.extend(jqGridOption,option);
			
			//20191129 add by xzp 
			//url为空时，会去请求当前页面地址，须将datatype改为local
			//函数结尾需要将datatype恢复，要不然第二次查询不会发送请求
			var oldDataType = jqGridOption.datatype;
			if (!jqGridOption.url || jqGridOption.url=="") {
				jqGridOption.datatype = "local";
			}
			
	   		$("#"+tableId).jqGrid(jqGridOption).navGrid("#"+tableId+"Pager", { refresh:false,search:false,edit : false,add : false,del : false});
	   		$("#"+tableId).jqGrid("setGridHeight",minHeight);
	   		//是否在右下脚显示统计按钮 属性名称为 sumDialog,给统计按钮调用的操作
	   		if(option.sumDialog && $.isFunction(option.sumDialog)){
	   			$("#"+tableId+"Pager_right").find(".ui-paging-info").before("(<a id='"+tableId+"Pager_right_sum' class='sumClass'>查看总计</a>)&nbsp;");
	   			$("#"+tableId+"Pager_right_sum").on("click",function(){
	   				option.sumDialog();
	   			});
	   		}
	   		if(option.searchBtn){
	   			addCharSearch(tableId);
	   		}
	   		$("#"+tableId).jqGrid("setGridParam", {datatype:oldDataType}); //20191129 add by xzp
	   		//全局默认开启列名title add by zb on 20200113
	   		if ("boolean" != typeof(option.showColumnTitle)) option.showColumnTitle = true;
	   		if (option.showColumnTitle && typeof showColumnTitle === "function") showColumnTitle(tableId, option);
		},
		//初始化编辑表格
		initEditJqGrid:function(tableId,option){
			var beforeSaveCellFun;
			if(option.beforeSaveCell && $.isFunction(option.beforeSaveCell)){
				beforeSaveCellFun = option.beforeSaveCell;
				delete option.beforeSaveCell;
			}
			var afterSaveCellFun;
			if(option.afterSaveCell && $.isFunction(option.afterSaveCell)){
				afterSaveCellFun = option.afterSaveCell;
				delete option.afterSaveCell;
			}
			if(option.multiselect){
				$.extend(option,{multiboxonly:true,multikey:!option.multikey || option.multikey == "" ? "ctrlKey" : option.multikey});
			}
			var jqGridOption = {
				postData : {},
				forceFit : true,
			    cellEdit: true,
			    cellsubmit: 'clientArray',
			    afterSetFooterData:undefined,
			    beforeSaveCell:function(rowId,name,val,iRow,iCol){
					if(beforeSaveCellFun){
						return beforeSaveCellFun(rowId,name,val,iRow,iCol);
					}
			    },
			    afterSaveCell:function(rowId,name,val,iRow,iCol) {
			    	var rowData = $("#"+tableId).jqGrid('getRowData', rowId);
			    	Global.JqGrid.jqGridAUDJson[tableId]==undefined?(Global.JqGrid.jqGridAUDJson[tableId]=[]):null;
					var isnew = true;
					$.each(Global.JqGrid.jqGridAUDJson[tableId],function(k,v){
						if(v.id==rowId){
							rowData["flag"]= v.data.flag;
							v.data = rowData;
							isnew = false;
						}
					});
					rowData["flag"]==undefined?(rowData["flag"]='u'):null;
					isnew?(Global.JqGrid.jqGridAUDJson[tableId].push({id:rowId,data:rowData})):null;
					Global.JqGrid.setFooterData(tableId);
					if(afterSaveCellFun){
						return afterSaveCellFun(rowId,name,val,iRow,iCol);
					}
			    }
			};
			$.extend(jqGridOption,option);
			Global.JqGrid.initJqGrid(tableId,jqGridOption);		
		},
		//添加表格行
		addRowData:function(tableId,rowData){
			rowData["flag"]='a';
			var rowId = Global.Random.nextInt();
			$("#"+tableId).jqGrid('addRowData',rowId,rowData);
			Global.JqGrid.jqGridAUDJson[tableId]==undefined?(Global.JqGrid.jqGridAUDJson[tableId]=[]):null;
			Global.JqGrid.jqGridAUDJson[tableId].push({id:rowId,data:rowData});
			Global.JqGrid.setFooterData(tableId);
		},
		//修改表格行
		updRowData:function(tableId,rowId,rowData){
			rowData["flag"]='u';
			$("#"+tableId).jqGrid('setRowData',rowId,rowData);
			Global.JqGrid.jqGridAUDJson[tableId]==undefined?(Global.JqGrid.jqGridAUDJson[tableId]=[]):null;
			var isnew = true;
			$.each(Global.JqGrid.jqGridAUDJson[tableId],function(k,v){
				if(v.id==rowId){
					rowData["flag"]= v.data.flag;
					v.data = rowData;
					isnew = false;
					return false;
				}
			});
			isnew?(Global.JqGrid.jqGridAUDJson[tableId].push({id:rowId,data:rowData})):null;
			Global.JqGrid.setFooterData(tableId);
		},
		//删除表格行
		delRowData:function(tableId,rowId){
			var rowData = $("#"+tableId).jqGrid('getRowData', rowId);
			rowData["flag"]='d';
			$("#"+tableId).jqGrid('delRowData',rowId);
			Global.JqGrid.jqGridAUDJson[tableId]==undefined?(Global.JqGrid.jqGridAUDJson[tableId]=[]):null;
			var isnew = true;
			$.each(Global.JqGrid.jqGridAUDJson[tableId],function(k,v){
				//console.info(v); remove by zzr 2018/01/08
				if(v.id==rowId){
					v.data.flag=='a'?(Global.JqGrid.jqGridAUDJson[tableId].splice(k,1)):(v.data=rowData);
					isnew = false;
					return false;
				}
			});
			isnew?(Global.JqGrid.jqGridAUDJson[tableId].push({id:rowId,data:rowData})):null;
			Global.JqGrid.setFooterData(tableId);
		},
		//设置表格页脚
		setFooterData:function(tableId,footerData){
			if(!footerData){
				footerData = {};
				var cm = $("#"+tableId).jqGrid("getGridParam","colModel");
				$.each(cm,function(m,n){
					if(n.sum||n.avg||n.count||n.min||n.max){
						var d = $("#"+tableId).getCol(n.name,false,(n.sum&&"sum")||(n.avg&&"avg")||(n.count&&"count")||(n.min&&"min")||(n.max&&"max"));
						footerData[n.name] = d>parseInt(d)?d.toFixed(4):parseInt(d);
					}
				});
			}
			if(footerData){
				footerData["rn"] = "合计";
				$("#"+tableId).footerData("set",footerData,false);
				var afterSetFooterData = $("#"+tableId).jqGrid("getGridParam","afterSetFooterData");
				if(afterSetFooterData && $.isFunction(afterSetFooterData)){
					afterSetFooterData($("#"+tableId).footerData());
				}
			}
		},
		//清空表格
		clearJqGrid:function(tableId){
			$("#"+tableId).jqGrid("clearGridData");
			delete Global.JqGrid.jqGridAUDJson[tableId];
		},
		//编辑的表格行转为json
		toJson:function(tableId){
			var json = {};
			var audJson = Global.JqGrid.jqGridAUDJson[tableId];
			if(audJson && audJson.length > 0){
				var detail = [];
				$.each(audJson,function(k,v){
					detail.push(v.data);
				});
				json["detailJson"] = JSON.stringify(detail);
			}
			return json;
		},
		//当前页的表格行转为json
		allToJson:function(tableId,field,seqField,disabledDatasArray){
			var json = {};
			var rows = $("#"+tableId).jqGrid("getRowData");
			if(field){//获取字段
				var fieldVals = [];
				$.each(rows,function(k,v){
					fieldVals.push(v[field]);
				});
				json["fieldJson"] = fieldVals.join(",");
				json["keys"] = fieldVals;
			}
			if(seqField){//要排序的字段
				$.each(rows,function(k,v){
					v[seqField] = k+1;
				});
			}
			json["detailJson"] = JSON.stringify(rows);
			if(!disabledDatasArray){
				json["datas"] = rows;
			}
			return json;
		},
		//所有的表格行转为json
		localToJson:function(tableId,field){
			var json = {};
			var rows = $("#"+tableId).jqGrid("getGridParam","data");
			if(field){//获取字段
				var fieldVals = [];
				$.each(rows,function(k,v){
					fieldVals.push(v[field]);
				});
				json["fieldJson"] = fieldVals.join(",");
			}
			json["detailJson"] = JSON.stringify(rows);
			return json;
		},
		//表格行转出excel
		exportExcel:function(tableId,excelUrl,excelTitle,targetForm){
			Global.Warting("show");
			var r = $("#"+tableId).jqGrid("jqGridExcelExport",{url:excelUrl,exportTitle:excelTitle,exportForm:targetForm});
			var int = setInterval(function(){
				if(r){
					Global.Warting("close");
					window.clearInterval(int);
				}
			},500);
		},
		//表格选中的行转为json
		selectToJson:function(tableId,field){
			var json = {};
			var rowIds = $("#" + tableId).jqGrid('getGridParam', 'selarrrow');
			if(rowIds && rowIds.length > 0){
				var detail = [];
				var detailField = [];
				$.each(rowIds,function(k,rowId){
					var row = $("#"+tableId).jqGrid("getRowData",rowId);
					detail.push(row);
					detailField.push(row[field]);
				});
				json["detailJson"] = JSON.stringify(detail);
				json["fieldJson"] = detailField.join(",");
			}
			return json;
		},
		/**
		 * 设置表格选中行
		 * @param tableId 表格id
		 * @param columnName 选中值列名
		 * @array 需要选中的行
		 */
		setSelection:function(tableId,columnName,array){
			var rows = [];//$("#"+tableId).jqGrid("getRowData");
			var grid = $("#"+tableId);
			var rowIds = grid.jqGrid("getDataIDs");
			
			var selarrrow = grid.getGridParam('selarrrow');
			
			//将表格设置为非选中状态
			for(var i=0;i<rowIds.length;i++){
				var row = grid.getRowData(rowIds[i]);
			   
				rows.push({rowId:rowIds[i],value:$.trim("" + row[columnName])});
				
			    if($.inArray(rowIds[i],selarrrow) > -1){
				    grid.setSelection(rowIds[i]);
			    }
			}
			//选择指定行数据
			if(columnName && array){
				if($.isArray(array)){
					$(array).each(function(index,value){
						$.each(rows,function(index,row){
							if(row.value == value){
								grid.setSelection(row.rowId);
							}
						});
						
					});
				}else {
					$.each(rows,function(index,row){
						if(row.value == array){
							grid.setSelection(row.rowId);
						}
					});
				}
			}
		},
		/**
		 * 表格全选或全不选
		 * @param flag true全选
		 */
		jqGridSelectAll : function(tableId,flag){
			var grid = $("#"+tableId);
			if (flag==true){
				grid.jqGrid('resetSelection');
	            var ids = grid.jqGrid('getDataIDs');
	            for (var i=0, il=ids.length; i < il; i++) {
	                grid.jqGrid('setSelection',ids[i], true);
	            }
			}else{
				grid.jqGrid('resetSelection');
			}
		}
	},
	//日期格式转换
	Date:{
		newDate:function(timestamp,format){ 
			if(!timestamp) return;
			if(!format || format==undefined || $.trim(format)==''){
				format = 'yyyy-MM-dd hh:mm:ss';
			}
			return new Date(timestamp).format(format);
		}
	},
	//树形结构展示
	Tree:{
		//初始化树
		init:function(treeId,url,option,data){
			var setting = {
				check: {
					enable: true,
					chkboxType: { "Y": "", "N": "" } 
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				view:{
					showIcon:false
				}
			};
			$.extend(setting,option);
			//请求数据
			$.ajax({
				url:url,
				type:"post",
				dataType:"json",
				async:false,
				data:data,
				success:function(ret){
					if(ret && ret.data.length>0){
						$.fn.zTree.init($("#"+treeId), setting, ret.data);
					}
				}
			});
		},
		//初始化下拉树
		initSelect:function(selectId,url,option,callBack){
			var treeId = "ul_"+selectId;
			if(!$("#"+treeId).attr("id")){
				$("body").append(
						"<div id=\""+treeId+"_div\" class=\"down-tree-select\">"
						+"<ul id=\""+treeId+"\" class=\"ztree\" style=\"margin-top:0; width:160px;\"></ul>"
						+"</div>"
				);
			}
			function onClick(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj(treeId),nodes = zTree.getSelectedNodes();
				if(nodes && nodes.length > 0){
					$("#"+selectId).val(nodes[0].id);
					if(callBack && $.isFunction(callBack)){
						callBack(nodes[0]);
					}
					$("#"+treeId+"_div").fadeOut("fast");	
				}
			}
			function onBodyDown(event) {
				if (!(event.target.id == selectId || event.target.id == (treeId+"_div") || $(event.target).parents("#"+treeId+"_div").length>0)) {
					$("#"+treeId+"_div").fadeOut("fast");
					$("body").unbind("mousedown", onBodyDown);
				}
			}
			var setting = {
				view: {
					dblClickExpand: false,
					showIcon:false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClick
				}
			};
			$.extend(setting,option);
			//请求数据
			$.ajax({
				url:url,
				type:"post",
				dataType:"json",
				async:false,
				success:function(ret){
					if(ret && ret.data.length>0){
						$.fn.zTree.init($("#"+treeId), setting, ret.data);
					}
				}
			});
			//绑定点击事件
			$("#"+selectId).on("click",function(){
				var offset = $("#"+selectId).offset();
				var selectWidth = $("#"+selectId).css("width");
				if($("#"+selectId).hasClass("form-control")){
					selectWidth = $("#"+selectId).parent().width();
				}
				$("#"+treeId+"_div").css({left:offset.left + "px", top:offset.top + $("#"+selectId).outerHeight() + "px",width:option.width?option.width:selectWidth}).slideDown("fast");
				$("body").bind("mousedown", onBodyDown);
			});
			return $.fn.zTree.getZTreeObj(treeId);
		}
	},
	Random:{
		nextInt:function(){
			return "N_"+Global.randomInt++;
		}
	},
	Print:{
		//调用pdf打印
		showPrint:function(report,json,tableDetail,closeFn, option){
			var isInstalledAcrobat = isAcrobatInstalled();
			var options = {
				title:"打印报表",
				url:"",
        	    height: 700,
        	    width:1000,
        	    postData:{},
        	    returnFun:function(){
        	    	$.isFunction(closeFn)?closeFn():undefined;
        	    }
			}
			if(!isInstalledAcrobat){
				options.url = ctx+"/system";
			}else{
				options.postData = {report:report};
				if(json){
					$.extend(options.postData,{data:JSON.stringify(json)});
				}
            	if(tableDetail && tableDetail.detailJson){
            		$.extend(options.postData,{detail:tableDetail.detailJson});
            	}
            	options.url = ctx+"/system/print";
			}
			//增加option参数对初始化参数进行自定义(针对预算管理报表有横有纵需要打开多个窗口) add by zzr 2018/06/28 begin
			if(!option){
				option = {};
			}
			if(!option.dialogWinName || option.dialogWinName == ""){
				option.dialogWinName = "modal-print"
			}
			$.extend(options, option);
			//增加option参数对初始化参数进行自定义(针对预算管理报表有横有纵需要打开多个窗口) add by zzr 2018/06/28 end
			Global.Dialog.showDialog(option.dialogWinName,options,closeFn);
		}
	},
	LinkSelect:{
		//初始化联动下拉组
		//自动选择第一个初始值(后三位true或者false)，或者根据组件重新回填(后三位输入索引) add by gdf 2018/10/26 
		//initOnlySingle true只有option个数为1个时，才进行默认初始化,暂时只加了第二级的
		initSelect:function(url,province,city,district,initProvince,initCity,initDistrict,initOnlySingle){//省市县三级联动 
			var oProvince = $("#"+province),oCity = $("#"+city),oDistrict = $("#"+district);
	        //初始化省
	        var province = function(){
	            var temp_html = "<option value=''>请选择...</option>";
	            $.ajax({
	            	url:url+"/1/null",
	            	type:"post",
	            	dataType:"json",
	            	async:false,
	            	data:{},
	            	success:function(data){
		            	if(data){
		                    $.each(data.data,function(i,province){
		                        temp_html+="<option value='"+$.trim(province.id)+"'";
		                        if(initProvince == true && i == 0){
		                        	temp_html+=" selected ";
		                        }
		                        if(initProvince-1== i){
		                        	temp_html+=" selected ";
		                        }
		                        temp_html+=">"+$.trim(province.id)+' '+$.trim(province.name)+"</option>";
		                    });
		                    oProvince.html(temp_html);
		                    oProvince.searchableSelect();
		            	}
	            	}
	            });
	        };
	        var city = function(){
	            var temp_html = "<option value=''>请选择...</option>";
	            var n = oProvince.val();
	            if(n==undefined||n==null||n==""){
	            	oCity.empty().append(temp_html);
	            	oCity.searchableSelect();
	            	district?district():null;
	            }else{
	                $.ajax({
	                	url:url+"/2/"+n,
		            	type:"post",
		            	dataType:"json",
		            	async:false,
	                	data:{},
	                	success:function(data){
	                		var defaultValue = "";
                			$.each(data.data,function(i,city){
                				temp_html+="<option value='"+$.trim(city.id)+"'";
                				if(initOnlySingle && data.data.length==1 || !initOnlySingle){
	                				if(initCity == true && i == 0){
	                					temp_html += " selected ";
	                				}
	                				if(initCity-1== i){
	                					temp_html+=" selected ";
	                				}
                				}
                				temp_html+=">"+$.trim(city.id)+' '+$.trim(city.name)+"</option>";
                				if($.trim(oCity.attr("value")) == $.trim(city.id)){
                					defaultValue = city.id;
                				}
                			});
	                			
			                oCity.empty().append(temp_html);
			                if($.trim(oCity.val()) == "" && defaultValue != ""){
			                	oCity.val($.trim(defaultValue));
			                }
			                oCity.searchableSelect();
			                district?district():null;
	                	}
	                });
	            }
	        };
	        var district = function(){
	            var temp_html = "<option value=''>请选择...</option>";
	            var n = oCity.val();
	            if(n==undefined||n==null||n==""){
	            	oDistrict.empty().append(temp_html);
	            	oDistrict.searchableSelect();
	            }else{
	                $.ajax({
	                	url:url+"/3/"+n,
		            	type:"post",
		            	dataType:"json",
		            	async:false,
	                	data:{},
	                	success:function(data){
	                		var defaultValue = "";
			                $.each(data.data,function(i,district){
			                    temp_html+="<option value='"+$.trim(district.id)+"'";
			                    if (initDistrict==true && i==0) {
				                    temp_html+=" selected ";	
								}
			                    if(initDistrict-1== i){
		                        	temp_html+=" selected ";
		                        }
			                    temp_html+=">"+$.trim(district.id)+' '+$.trim(district.name)+"</option>";
			                    if($.trim(oDistrict.attr("value")) == $.trim(district.id)){
			                    	defaultValue = $.trim(district.id);
			                    }
			                });
			                oDistrict.empty().append(temp_html);
			                if(oDistrict.val() == "" && defaultValue != ""){
			                	oDistrict.val($.trim(defaultValue));
			                }
			                oDistrict.searchableSelect();
	                	}
	                });                	
	            }
	        };
	         
	        province();
	
	        //选择省改变市
	        if(city){
	        	var targetNode = oProvince[0];//content监听的元素id
	        	//options：监听的属性
	        	var option = {attributes: true  };
	        	//回调事件
	        	function callback(mutationsList, observer) { 
	        		city();
		            if(oCity.attr("data-bv-field")){
		            	oCity.trigger("change.update.bv").trigger("change.live.bv");
		            }
		            oCity.parent().children("small").css("display","none");
		            //市级初始化完毕  
		            
		            //设置监听市级变动
		        	var cityNode = oCity[0];//content监听的元素id
		        	var cityOption = {attributes: true  };
		        	function cityCallback(mutationsList, observer) { 
		        		district();
			            if(oDistrict.attr("data-bv-field")){
			            	oDistrict.trigger("change.update.bv").trigger("change.live.bv");
			            }
		        	}
		        	var cytiObserver = new MutationObserver(cityCallback);
		        	if(cityNode){
		        		cytiObserver.observe(cityNode, cityOption);
		        	}
	        	}
	        	var mutationObserver = new MutationObserver(callback);
	        	if(targetNode){
	        		mutationObserver.observe(targetNode, option);
	        	}
	        	city();
	        }
	        //选择市改变县
	        /*if(district){
		        district();
	        	//DOMNodeInserted：监听新版本选择框文字变化
		        oCity.on("change",function(){
		            district();
		            if(oDistrict.attr("data-bv-field")){
		            	oDistrict.trigger("change.update.bv").trigger("change.live.bv");
		            }
		        });
		        district();
	        }*/
		},
		//回填写联动下拉组件
		setSelect:function(options,isDisabled){
			console.log(options);
			if(options.firstSelect && options.firstValue != null && options.firstValue != undefined){
				$("#"+options.firstSelect).val($.trim(options.firstValue));
				$("#"+options.firstSelect).attr("value",$.trim(options.firstValue));
				isDisabled&&$("#"+options.firstSelect).attr("disabled",true);
	            if($("#"+options.firstSelect).attr("data-bv-field")){
	            	$("#"+options.firstSelect).trigger("change.update.bv").trigger("change.live.bv");
	            }
				$("#"+options.firstSelect).change();
			}
			if(options.secondSelect && options.secondValue != null && options.secondValue != undefined){
				$("#"+options.secondSelect).val($.trim(options.secondValue));
				$("#"+options.secondSelect).attr("value",$.trim(options.secondValue));
				isDisabled&&$("#"+options.secondSelect).attr("disabled",true);
	            if($("#"+options.secondSelect).attr("data-bv-field")){
	            	$("#"+options.secondSelect).trigger("change.update.bv").trigger("change.live.bv");
	            }
	            $("#"+options.secondSelect).change();
			}
			if(options.thirdSelect && options.thirdValue != null && options.thirdValue != undefined){
				$("#"+options.thirdSelect).val($.trim(options.thirdValue));
				$("#"+options.thirdSelect).attr("value",$.trim(options.thirdValue));
				isDisabled&&$("#"+options.thirdSelect).attr("disabled",true);
	            if($("#"+options.thirdSelect).attr("data-bv-field")){
	            	$("#"+options.thirdSelect).trigger("change.update.bv").trigger("change.live.bv");
	            }
			}
		},
		//初始化下拉组件
		initOption:function(selectId,url,param,theSelectedId){
			$.post(url,param,function(ret){
				var temp_html = "<option value=''>请选择...</option>";
				if(ret && ret.code){
	                $.each(ret.data,function(i,option){
                	    temp_html+="<option value='"+$.trim(option.id)+"'";
	                    if (theSelectedId!=undefined&&$.trim(option.id)==theSelectedId) {
		                    temp_html+=" selected ";	
						}
	                    temp_html+=">"+option.id+' '+option.name+"</option>";
	                  
	                });
	                $("#"+selectId).empty().append(temp_html);
	                $("#"+selectId).searchableSelect();
	             }
			},"json");
		}
	},
	Button:{ //按钮点击事件,点击后设置按钮不可用三秒防止重复点击
		onClick:function(btn,fun){
			if(Object.prototype.toString.call(btn) === "[object String]"){
				btn = $("#" + btn);
			}
			$(btn).on("click",function(){
				$(btn).attr({"disabled":"disabled"});
				fun.call(this);
				//替换为遮罩层				
				setTimeout(function(){
					$(btn).removeAttr("disabled");
				},3000);
				
			});
		}
	},
	Form:{//设置表单提交 
		submit:function(form,url,data,fn){
			Global.Warting("show");
			$("#"+form).ajaxSubmit({
        		url:url,
        		type:"post",
        		dataType:"json",
        		data:data,
        		success:function(ret){
        			Global.Warting("close");
        			if(fn && $.isFunction(fn)){
        				fn(ret);
        			}
        		},
				error: function(ret){
					 Global.Warting("close");
					 art.dialog({
							content: '请求异常,稍后再试！'
					 });
        	    },
			});
		}
	},
	//调用遮罩层 show 打开 ; close 关闭  
	Warting:function(operType){
		switch(operType){
		case "show":
			document.getElementById("bgShadow").style.display = "block";
			break;
		case "close":
			document.getElementById("bgShadow").style.display = "none";
			break;
		}
	},
	Variable:{
		uiSortableWidth:0,//解决列拖动标签偏移消失问题,元素距离表格最左边的距离
		uiSortableLeft:0//解决列拖动标签偏移消失问题,元素offset.left
	}
}

/** 
 * 时间对象的格式化; 
 */  
Date.prototype.format = function(format) {  
    /* 
     * eg:format="yyyy-MM-dd hh:mm:ss"; 
     */  
    var o = {  
        "M+" : this.getMonth() + 1, // month  
        "d+" : this.getDate(), // day  
        "h+" : this.getHours(), // hour  
        "m+" : this.getMinutes(), // minute  
        "s+" : this.getSeconds(), // second  
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S" : this.getMilliseconds()  
        // millisecond  
    }  
  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
                        - RegExp.$1.length));  
    }  
  
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1  
                            ? o[k]  
                            : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}

function isAcrobatInstalled(){
	//检测浏览器类型：IE、火狐、谷歌、Safari
	function getBrowserName(){
	  var userAgent = navigator ? navigator.userAgent.toLowerCase() : "other";     
	  if(userAgent.indexOf("chrome") > -1) 
		  return "chrome";
	  else if(userAgent.indexOf("safari") > -1) 
		  return "safari";
	  else if(userAgent.indexOf("msie") > -1 || userAgent.indexOf("trident") > -1) 
		  return "ie";
	  else if(userAgent.indexOf("firefox") > -1) 
		  return "firefox";
	  return userAgent;
	}
	//针对IE返回ActiveXObject　
	function getActiveXObject(name){
	  try {
	     return new ActiveXObject(name);
	  } catch(e) {}
	}

	//针对除了IE之外浏览器　　
	function getNavigatorPlugin(name){
	  for(key in navigator.plugins) {
		  var plugin = navigator.plugins[key]; 
		  if(plugin.name == name) 
		      return plugin;
	  }
	}

	//获取Adobe Reader插件信息　　
	function getPDFPlugin(){
	    if(getBrowserName() == 'ie') {
	         // load the activeX control,AcroPDF.PDF is used by version 7 and later,PDF.PdfCtrl is used by version 6 and earlier
	    	return getActiveXObject('AcroPDF.PDF') ||getActiveXObject('PDF.PdfCtrl');
	     }else {
	        return getNavigatorPlugin('Adobe Acrobat') || getNavigatorPlugin('Chrome PDF Viewer') || getNavigatorPlugin('WebKit built-in PDF');
	     }
	}
	
	//获取Adobe Reader版本　　
	function getAcrobatVersion(){
	  try {
	       var plugin = getPDFPlugin();
	       if(getBrowserName() == 'ie') {
	          var versions = plugin.GetVersions().split(',');
	          var latest = versions[0].split('=');
	          return parseFloat(latest[1]);
	       }
	      if(plugin.version){
	          return parseInt(plugin.version);
	      }
	      return plugin.name;
	  }catch(e) {
	      return null;
	  }
	}

	//判断插件是否安装　　
	return !!getPDFPlugin();
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
		var flag = 0;
		for(var i=0;i<boxs.size();i++){
			var id = boxs[i].id;
			var checkboxs = $("#"+id+" input[type=\"checkbox\"][checked=\"checked\"]");	
			var colValues = $("#"+tableId).jqGrid("getCol",id.substring(tableId.length+1,id.lastIndexOf("_checkBoxs")),true);
			var showIndex = 0;
			
			if(!$("#"+tableId+"_"+id.substring(tableId.length+1,id.lastIndexOf("_checkBoxs"))+"_checkbox_all").checked){
				for(var j=0;j<colValues.length;j++){
					if( flag == 1 && $("#"+tableId+" tbody tr[id="+colValues[j].id+"]").css("display")=="none"){
						continue;
					}
					var k=0;
					for(;k<checkboxs.length;k++){
						if(colValues[j].value == checkboxs[k].value){
							showIndex++;
							$("#"+tableId+" tbody tr[id="+colValues[j].id+"]").css({"display":"table-row"});
							$("#"+tableId+" tbody tr[id="+colValues[j].id+"] td:first").attr("title",showIndex).html(showIndex);
					        break;
						}
					}
					if(k == checkboxs.length && k!=0){
						$("#"+tableId+" tbody tr[id="+colValues[j].id+"]").css({"display":"none"});
					}
				}
			}

			if(showIndex > 0 || $("#"+tableId+"_"+id.substring(tableId.length+1,id.lastIndexOf("_checkBoxs"))+"_checkbox_all").checked){
				flag = 1;
			}
		}
		if(flag == 0){
			var id = boxs[0].id;
			var colValues = $("#"+tableId).jqGrid("getCol",id.substring(tableId.length+1,id.lastIndexOf("_checkBoxs")),true);
			for(var i=0;i<colValues.length;i++){
				$("#"+tableId+" tbody tr[id="+colValues[i].id+"]").css({"display":"table-row"});
				$("#"+tableId+" tbody tr[id="+colValues[i].id+"] td:first").attr("title",i+1).html(i+1);
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