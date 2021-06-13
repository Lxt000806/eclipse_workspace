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
      art.dialog({
        content: '系统将批量调整此增减单的折扣为：' + $("#disCount").val() + '，是否确定调整？',
        lock: true,
        width: 260,
        height: 100,
        ok: function () {
          Global.Dialog.returnData = $("#disCount").val();
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
          disCount: {
            validators: {
              notEmpty: {
                message: '折扣不能为空'
              },

              between: {
                min: '0',
                max: '100',
                message: '请输入0-100之间的数值'
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
            <label>折扣</label>
            <input id="disCount" name="disCount" type="text" value="100">%
          </li>
        </ul>
      </form>
    </div>
  </div>
</div>
</body>
</html>
