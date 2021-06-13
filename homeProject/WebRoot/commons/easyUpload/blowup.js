// blowup.js
//

$(function ($) {

  $.fn.blowup = function (attributes) {

    var $element = this;

    // If the target element is not an image
    if (!$element.is("img")) {
      console.log("%c Blowup.js Error: " + "%cTarget element is not an image.", 
        "background: #FCEBB6; color: #F07818; font-size: 17px; font-weight: bold;",
        "background: #FCEBB6; color: #F07818; font-size: 17px;");
      return;
    }


    var $IMAGE_URL    = $element.attr("src");
    var $IMAGE_WIDTH  = $element.width();
    var $IMAGE_HEIGHT = $element.height();
    var NATIVE_IMG    = new Image();
    NATIVE_IMG.src    = $element.attr("src");

    // 默认属性
    var defaults = {
      round      : true,
      width      : 200,
      height     : 200,
      background : "#FFF",
      shadow     : "0 8px 17px 0 rgba(0, 0, 0, 0.2)",
      border     : "6px solid #FFF",
      cursor     : true,
      zIndex     : 999999
    }

    // 使用自定义属性更新默认值
    var $options = $.extend(defaults, attributes);

    // 修改目标图像
    $element.on('dragstart', function (e) { e.preventDefault(); });
    $element.css("cursor", $options.cursor ? "crosshair" : "none");

    // 创建放大镜头元素
    var lens = document.createElement("div");
    lens.id = "BlowupLens";

    // 攻击元素到身体
    $("body").append(lens);

    // 修改样式
    $blowupLens = $("#BlowupLens");

    $blowupLens.css({
      "position"          : "absolute",
      "visibility"        : "hidden",
      "pointer-events"    : "none",
      "zIndex"            : $options.zIndex,
      "width"             : $options.width,
      "height"            : $options.height,
      "border"            : $options.border,
      "background"        : $options.background,
      "border-radius"     : $options.round ? "50%" : "none",
      "box-shadow"        : $options.shadow,
      "background-repeat" : "no-repeat",
    });

    // 显示放大镜头
    $element.mouseenter(function () {
      $blowupLens.css("visibility", "visible");
    })

    // 图像上的鼠标移动
    $element.mousemove(function (e) {

      // 镜头的位置坐标
      var lensX = e.pageX - $options.width / 2;
      var lensY = e.pageY - $options.height / 2;

      // 图像相对坐标
      var relX = e.pageX - this.offsetLeft;
      var relY = e.pageY - this.offsetTop;
     
      // 放大图像坐标 
      var zoomX = -Math.floor(relX / $element.width() * NATIVE_IMG.width - $options.width / 2);
      var zoomY = -Math.floor(relY / $element.height() * NATIVE_IMG.height - $options.height / 2);

      // 在镜头上应用样式
      $blowupLens.css({
        left                  : lensX,
        top                   : lensY,
        "background-image"    : "url(" + $IMAGE_URL + ")",
        "background-position" : $.trim(zoomX)+"px" + " " + $.trim(zoomY)+"px"
      });
    })

    // Hide magnification lens
    $element.mouseleave(function () {
      $blowupLens.css("visibility", "hidden");
    })

  }
})
