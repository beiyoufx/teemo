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

    <title>Teemo后台 - 资源管理</title>

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
            <h5>资源列表</h5>
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
                        <div class="table-container">
                            <div class="btn-group hidden-xs" id="toolbar" role="group">
                                <shiro:hasPermission name="sys:resource:create">
                                    <button type="button" class="btn btn-outline btn-primary" onclick="addResource()">
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i><span> 新增资源</span>
                                    </button>
                                </shiro:hasPermission>
                            </div>
                            <table id="paginationTable" data-toggle="table" data-mobile-responsive="true" data-height="600" data-icon-size="outline" data-toolbar="#toolbar">
                                <thead>
                                <tr>
                                    <th data-field="id">ID</th>
                                    <th data-field="resourceKey">资源关键字</th>
                                    <th data-field="resourceValue">资源名称</th>
                                    <th data-field="url">资源地址</th>
                                    <th data-field="parentId">上级资源ID</th>
                                    <th data-field="type">资源类型</th>
                                    <th data-field="menuIcon"  data-formatter="iconFormatter">资源图标</th>
                                    <th data-field="sequence">菜单排序</th>
                                    <th data-field="available" data-formatter="stateFormatter">状态</th>
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
        url : "${ctx}/sys/resource/find",
        addUrl: "${ctx}/sys/resource/add",
        deleteUrl : "${ctx}/sys/resource/delete",
        editUrl: "${ctx}/sys/resource/edit"
    };

    function iconFormatter(value) {
        if (value) {
            value = "<span class='label label-info'><i class='" + value + "'></i></span> " + value;
        }
        return value;
    }

    function stateFormatter(value) {
        var availableHtml = "<span class='badge badge-primary'>&nbsp;可 用&nbsp;</span>";
        var unavailableHtml = "<span class='badge badge-warning'>&nbsp;不 可 用&nbsp;</span>";
        return value == true ? availableHtml : unavailableHtml;
    }

    function optionFormatter(value, row, index) {
        var editHtml = "&nbsp;<shiro:hasPermission name="sys:resource:update"><button type='button' class='fa fa-edit btn btn-primary' onclick='editResource(" + row.id + ")'>编 辑</button></shiro:hasPermission>&nbsp;";
        var deleteHtml = "&nbsp;<shiro:hasPermission name="sys:resource:delete"><button type='button' class='delete fa fa-times btn btn-default' onclick='commonDelete(" + row.id + ")'>删 除</button></shiro:hasPermission>&nbsp;";
        return "<div class='text-center'>" + editHtml + deleteHtml + "</div>";
    }

    function addResource() {
        //iframe层
        parent.layer.open({
            type: 2,
            title: ['新增资源', 'font-weight:bold;'],
            shadeClose: true,
            shade: 0.8,
            area: ['50%', '50%'],
            content: tableModel.addUrl
        });
    }

    function editResource(resourceId) {
        //iframe层
        parent.layer.open({
            type: 2,
            title: ['编辑资源', 'font-weight:bold;'],
            shadeClose: true,
            shade: 0.8,
            area: ['50%', '50%'],
            content: tableModel.editUrl + "/" + resourceId //iframe的url
        });
    }
</script>

<!-- 通用表格初始化和删除代码 -->
<script src="${staticPath}/static/js/admin/table.js"></script>
</body>

</html>

