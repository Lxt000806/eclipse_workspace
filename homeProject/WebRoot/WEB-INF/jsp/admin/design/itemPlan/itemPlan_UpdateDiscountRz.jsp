<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>添加IntProd</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp" %>

  <script type="text/javascript">
	function save() {
		$("#dataForm").bootstrapValidator('validate');
		if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var selectRows = [];
		if($.trim($("#count").val())==""){
			art.dialog({
				content:"总价不能为空！",
			});
			return;
		}
		
		selectRows.push($("#itemSetNo").val());
		selectRows.push($("#count").val());
		art.dialog({
			content: '是否确定将该套餐包的总价设置为:'+$("#count").val()+'元？',
			lock: true,
			width: 260,
			height: 100,
			ok: function () {
				Global.Dialog.returnData = selectRows;
				closeWin();
			},
			cancel: function () {
				return true;
			}
		});
	}
	
    //校验函数
    $(function () {
      $("#dataForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
          /*input状态样式图片*/
          validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
          itemSetNo: {
            validators: {
              notEmpty: {
                message: '套餐包不能为空'
              },
            }
          },
          count: {
            validators: {
              notEmpty: {
                message: '总价不能为空'
              },
              regexp: {
                  regexp: /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
                  message: '总价只能是数字',
              }
            }
          }
        },
        submitButtons: 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
      });

    })

  </script>

</head>

<body>
<div class="body-box-form">
  <div class="panel panel-system">
    <div class="panel-body">
      <div class="btn-group-xs">
        <button type="button" id="saveBtn" class="btn btn-system " onclick="save()">保存</button>
        <button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
      </div>
    </div>
  </div>
  <div class="panel panel-info">
    <div class="panel-body">
      <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
        <house:token></house:token>
        <ul class="ul-form">
          <li class="form-validate">
			<label><span class="required">*</span>套餐包</label>
			<house:dict id="itemSetNo" dictCode="" sql="select no Code,Descr from tItemSet where no in (${itemSetNoList })"  sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>
		  </li>
          <li class="form-validate">
            <label>总价</label>
            <input id="count" name="count" type="text" value="0" >
          </li>
        </ul>
      </form>
    </div>
  </div>
</div>
</body>
</html>
