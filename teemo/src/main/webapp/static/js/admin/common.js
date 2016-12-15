/**
 * Created by yongjie.teng on 16-12-15.
 */
function closeCurrentFrame() {
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

$().ready(function () {
    // check box样式
    $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green'
    });

    $("#cancelButton").bind("click", function() {
        closeCurrentFrame();
    });
})