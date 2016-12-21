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

    <title>Teemo后台 - 用户授权</title>

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
        将角色授予用户 .【当前被授权的用户是：${user.nickname}】
    </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">用户授权</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-sm-10">
                        <label class="control-label">
                                角色列表
                        </label>
                    </div>
                    <div class="col-sm-10">
                        <c:forEach var="role" items="${roles}">
                            <label class="checkbox-inline i-checks">
                                <input type="checkbox" value="${role.id}" <c:forEach var="rl" items="${user.roles}"> ${rl.id == role.id ? "checked" : ""} </c:forEach> > <i></i> ${role.roleValue} </label>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
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
        var prepareRoles = function () {
            var roles = new Array();
            var $rolesBox = $("input:checkbox:checked");
            $rolesBox.each(function () {
                roles.push($(this).val());
            });
            console.log(JSON.stringify(roles));
            return roles;
        }

        $("#saveButton").bind("click", function() {
            var data = prepareRoles();
            $.ajax({
                dataType : "json",
                url : "${ctx}/sys/auth/user/${user.id}",
                type : "post",
                data : {
                    roleIds : data
                },
                traditional : true,
                success : function(response) {
                    if (response.code == 1) {
                        parent.layer.msg("保存用户授权信息成功", {icon : 1});
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
