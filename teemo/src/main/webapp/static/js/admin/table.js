/**
 * Created by yongjie.teng on 16-12-08.
 */
function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

// 初始化表格，需要先定义tableModel
(function() {
    tableModel.table.bootstrapTable({
        url: tableModel.url,
        responseHandler: function (res) {
            return {rows : res.records, total: res.total};
        },
        cache: true,
        pagination: true,
        sidePagination: "server",
        queryParamsType: "",
        pageNumber: 1,
        pageSize: 5,
        pageList: [5, 10, 20, 50],
        search: true,
        striped: true,
        showRefresh: true,
        showToggle: true,
        showColumns: true,
        clickToSelect: true,
        uniqueId: "id",
        icons: {
            refresh: 'glyphicon-repeat',
            toggle: 'glyphicon-list-alt',
            columns: 'glyphicon-list'
        }
    });
})();

function commonDelete(id) {
    //询问框
    parent.layer.confirm('删除之后不可恢复，您确定要删除该用户吗？', {
        icon: 0,
        title: '提示',
        btn: ['删除','取消'], //按钮
        btnAlign: 'c',
        shadeClose: true,
        shade: [0.8, '#000'] //显示遮罩
    }, function(){
        $.ajax({
            url : tableModel.deleteUrl + "/" + id,
            type : "post",
            async : false,
            success : function(response) {
                if (response.code == 1) {
                    parent.layer.msg(response.message, {icon: 1});
                    tableModel.table.bootstrapTable("removeByUniqueId", id);
                } else {
                    parent.layer.msg(response.message, {icon: 2});
                }
            },
            error : function() {
                parent.layer.msg("删除失败", {icon: 2});
            }
        });
    });
}