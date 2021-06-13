 /**
 * 供应商结算
 */
var json_datas = [];
var json_keys = [];
function getJsonDatas(){
	if (json_datas.length==0){
		var json = Global.JqGrid.allToJson("splCheckOutDataTable","no");
		json_datas = json.datas;
		json_keys = json.keys;
	}else{
		var json = Global.JqGrid.allToJson("splCheckOutDataTable","no");
		var json_datas_now = json.datas;
		$.each(json_datas_now,function(k,v){
			  var index_no = json_keys.indexOf(v.no);
			  if (index_no!=-1){
				  json_datas[index_no].othercost = v.othercost;
				  json_datas[index_no].othercostadj = v.othercostadj;
				  json_datas[index_no].splamount = v.splamount;
				  json_datas[index_no].hjamount = v.hjamount;
			  }else{
				  json_datas.push(v);
				  json_keys.push(v.no);
			  }
		  });
	}
}
function deleteJsonDatas(no){
	var index_no = json_keys.indexOf(no);
	if (index_no!=-1){
		json_datas.splice(index_no, 1);
		json_keys.splice(index_no, 1);
	}
}
function fillJsonDatas(str){
	Global.JqGrid.clearJqGrid("splCheckOutDataTable");
	var json_temp = [];
	if(json_datas){
	  $.each(json_datas,function(k,v){
		  if (v.address.indexOf(str)!=-1){
			  var json = {
				  checkoutno: v.checkoutno,
				  no: v.no,
				  appno: v.appno,
				  isservicedescr: v.isservicedescr,
				  issetitemdescr: v.issetitemdescr,
				  address: v.address,
				  type: v.type,
				  typedescr: v.typedescr,
				  date: v.date,
				  othercost: v.othercost,
				  othercostadj: v.othercostadj,
				  splamount: v.splamount,
				  hjamount: v.hjamount,
				  remarks: v.remarks,
			      payremark: v.payremark,
				  ischeckout: '1',
				  documentno: v.documentno,
				  amount: v.amount,
				  firstamount: v.firstamount,
				  secondamount: v.secondamount
			  };
			  json_temp.push(json);
		  }
	  });
	  Global.JqGrid.addRowData("splCheckOutDataTable",json_temp);
	}
}
function goto_query(){
	getJsonDatas();
	var str = $.trim($("#address").val());
	fillJsonDatas(str);
}