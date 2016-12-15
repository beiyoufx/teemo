<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="../../core.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>Teemo后台 - 用户管理</title>

    <meta name="keywords" content="teemo是一个Java EE企业级通用开发框架，提供底层抽象和常用功能。">
    <meta name="description" content="teemo是一个Java EE企业级通用开发框架，提供底层抽象和常用功能。">

    <link rel="shortcut icon" href="${staticPath}/static/favicon.ico">
    <link rel="bookmark" href="${staticPath}/static/favicon.ico"/>
    <link href="${staticPath}/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${staticPath}/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${staticPath}/static/css/animate.css" rel="stylesheet">
    <link href="${staticPath}/static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="${staticPath}/static/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <!-- Panel Other -->
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>用户管理</h5>
            <div class="ibox-tools">
                <a class="collapse-link">
                    <i class="fa fa-chevron-up"></i>
                </a>
            </div>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <!-- Pagination Table -->
                    <div class="table-wrap">
                        <h4 class="table-title">用户列表</h4>
                        <div class="table-container">
                            <table id="paginationTable" data-toggle="table" data-mobile-responsive="true" data-height="600" data-icon-size="outline">
                                <thead>
                                <tr>
                                    <th data-checkbox="true"></th>
                                    <th data-field="id">ID</th>
                                    <th data-field="nickname">姓名</th>
                                    <th data-field="username">用户名</th>
                                    <th data-field="mobilePhone">手机号</th>
                                    <th data-field="email">邮箱</th>
                                    <th data-field="createTime">创建时间</th>
                                    <th data-field="status" data-formatter="stateFormatter">状态</th>
                                    <th data-formatter="optionFormatter">选项</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <!-- End Pagination Table -->
                </div>
            </div>
        </div>
    </div>
    <!-- End Panel Other -->
</div>

<!-- 全局js -->
<script src="${staticPath}/static/js/jquery.min.js?v=2.1.4"></script>
<script src="${staticPath}/static/js/bootstrap.min.js?v=3.3.6"></script>

<!-- 自定义js -->
<script src="${staticPath}/static/js/content.js?v=1.0.0"></script>

<!-- Bootstrap table -->
<script src="${staticPath}/static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${staticPath}/static/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="${staticPath}/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

<!-- layer javascript -->
<script src="${staticPath}/static/js/plugins/layer/layer.min.js"></script>

<script>
    tableModel = {
        table : $('#paginationTable'),
        url : "${ctx}/sys/user/find",
        deleteUrl : "${ctx}/sys/user/delete"
    };

    function stateFormatter(value) {
        var availableHtml = "<span class='badge badge-primary'>&nbsp;正 常&nbsp;</span>";
        var unavailableHtml = "<span class='badge badge-warning'>&nbsp;锁 定&nbsp;</span>";
        return value == "normal" ? availableHtml : unavailableHtml;
    }

    function optionFormatter(value, row, index) {
        var authHtml = "&nbsp;<shiro:hasPermission name="sys:user:update"><button type='button' class='fa fa-user btn btn-success' onclick='auth(" + row.id + ")'>授 权</button></shiro:hasPermission>&nbsp;";
        var editHtml = "&nbsp;<shiro:hasPermission name="sys:user:update"><button type='button' class='fa fa-edit btn btn-primary' onclick='editUser(" + row.id + ")'>编 辑</button></shiro:hasPermission>&nbsp;";
        var deleteHtml = "&nbsp;<shiro:hasPermission name="sys:user:delete"><button type='button' class='delete fa fa-times btn btn-default' onclick='commonDelete(" + row.id + ")'>删 除</button></shiro:hasPermission>&nbsp;";
        return "<div class='text-center'>" + authHtml + editHtml + deleteHtml + "</div>";
    }

    function auth(userId) {
        parent.layer.msg("授权成功", {icon: 1});
    }

    function editUser(userId) {
        parent.layer.msg("编辑成功", {icon: 1});
    }
</script>

<!-- 通用表格初始化和删除代码 -->
<script src="${staticPath}/static/js/admin/table.js"></script>
</body>

</html>

