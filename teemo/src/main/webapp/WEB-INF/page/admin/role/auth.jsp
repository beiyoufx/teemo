<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="../../core.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">

    <title>Teemo后台 - 角色授权</title>

    <link rel="shortcut icon" href="${staticPath}/static/favicon.ico">
    <link rel="bookmark" href="${staticPath}/static/favicon.ico"/>
    <link href="${staticPath}/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${staticPath}/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${staticPath}/static/css/animate.css" rel="stylesheet">
    <link href="${staticPath}/static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="${staticPath}/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${staticPath}/static/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="alert alert-info alert-dismissable">
        <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
        将操作资源的权限授予角色 .【当前授权的角色是：${role.roleValue}】
    </div>
    <c:forEach var="resourceNode" items="${resourceNodes}">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">${resourceNode.value}</h3>
            </div>
            <div class="panel-body">
            <c:if test="${resourceNode.children != null}">
                <c:set var="index" value="${fn:length(resourceNode.children)}" />
                <c:forEach var="childResourceNode" items="${resourceNode.children}">
                    <div class="row">
                        <div class="col-sm-10">
                            <label class="control-label">
                                    ${childResourceNode.value}
                            </label>
                        </div>
                    <c:if test="${childResourceNode.children != null}">
                        <c:forEach var="resourceTailNode" items="${childResourceNode.children}">
                            <div class="col-sm-10">
                                <label class="col-sm-2 control-label">${resourceTailNode.value}：</label>
                                <div class="col-sm-8">
                                <c:forEach var="permissionState" items="${resourceTailNode.permissionStates}">
                                    <label class="checkbox-inline i-checks">
                                        <input type="checkbox" data-resource-id="${resourceTailNode.id}" value="${permissionState.id}" ${permissionState.authorized == true ? "checked" : ""} > <i></i> ${permissionState.permissionValue} </label>
                                </c:forEach>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${childResourceNode.children == null}">
                        <div class="col-sm-10">
                            <div class="col-sm-8">
                                <c:forEach var="permissionState" items="${childResourceNode.permissionStates}">
                                    <label class="checkbox-inline i-checks">
                                        <input type="checkbox" data-resource-id="${childResourceNode.id}" value="${permissionState.id}" ${permissionState.authorized == true ? "checked" : ""} > <i></i> ${permissionState.permissionValue} </label>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                    </div>
                    <c:set var="index" value="${index - 1}"/>
                    <c:if test="${index != 0}">
                        <div class="hr-line-dashed"></div>
                    </c:if>
                </c:forEach>
            </c:if>
            </div>
        </div>
    </c:forEach>
    <div class="row">
        <div class="col-sm-5">
            <button id="saveButton" type="button" class="btn btn-primary pull-right">保 存</button>
        </div>
        <div class="col-sm-5">
            <button id="cancelButton" type="button" class="btn btn-default pull-left">取 消</button>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="${staticPath}/static/js/jquery.min.js?v=2.1.4"></script>
<script src="${staticPath}/static/js/bootstrap.min.js?v=3.3.6"></script>
<!-- iCheck -->
<script src="${staticPath}/static/js/plugins/iCheck/icheck.min.js"></script>
<!-- 后台通用js -->
<script src="${staticPath}/static/js/admin/common.js"></script>
<script>
    $().ready(function () {
        var role = {
            id : ${role.id},
            key : "${role.roleKey}",
            value : "${role.roleValue}",
            available : ${role.available}
        };

        var preparePermissions = function () {
            var resources = new Array();
            var $permissionsBox = $("input:checkbox:checked");
            $permissionsBox.each(function () {
                var resource = {
                    role : {
                        id : role.id
                    },
                    resourceId : "",
                    permissionIds : new Array()
                };
                var resourceId = $(this).attr("data-resource-id");
                var permissionId = $(this).val();
                var flag = false;
                if (resources.length > 0) {
                    for (var i=0; i<resources.length; i++) {
                        // 如果资源在resources中已存在，将该资源对应的权限添加到permissionIds数组中
                        if (resourceId == resources[i].resourceId) {
                            resources[i].permissionIds.push(permissionId);
                            flag = true;
                        }
                    }
                }
                // 如果资源在resources中不存在，将该资源添加到resources数组中
                if (!flag) {
                    resource.resourceId = resourceId;
                    resource.permissionIds.push(permissionId);
                    resources.push(resource);
                }
            });
            console.log(JSON.stringify(resources));
            return resources;
        }

        $("#saveButton").bind("click", function() {
            var data = preparePermissions();
            $.ajax({
                dataType : "json",
                contentType : "application/json",
                url : "${ctx}/sys/role/auth",
                type : "post",
                data : JSON.stringify(data),
                success : function(response) {
                    if (response.code == 1) {
                        parent.layer.msg("保存角色信息成功", {icon : 1});
                        closeCurrentFrame();
                    } else {
                        parent.layer.msg(response.message, {icon: 2});
                    }
                }
            });
        });
    });
</script>
</body>
</html>
