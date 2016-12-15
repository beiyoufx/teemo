<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="../../core.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">

    <title>Teemo后台 - 角色编辑</title>

    <link rel="shortcut icon" href="${staticPath}/static/favicon.ico">
    <link rel="bookmark" href="${staticPath}/static/favicon.ico"/>
    <link href="${staticPath}/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${staticPath}/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${staticPath}/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${staticPath}/static/css/animate.css" rel="stylesheet">
    <link href="${staticPath}/static/css/style.css?v=4.1.0" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <form class="form-horizontal m-t" id="validationSaveRoleFrom" action="#" method="post">
        <input id="roleId" name="roleId" type="hidden" class="form-control" maxlength="32" value="${role.id}"/>
        <div class="form-group">
            <label class="col-sm-2 control-label">角色关键字：</label>
            <div class="col-sm-10">
                <input id="roleKey" name="roleKey" type="text" class="form-control" maxlength="32" placeholder="请输入4-32位字母" value="${role.roleKey}" ${role.roleKey != null ? "readonly" : ""}/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">角色名称：</label>
            <div class="col-sm-10">
                <input id="roleValue" name="roleValue" type="text" class="form-control" maxlength="32" placeholder="最多32个汉字" value="${role.roleValue}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">描述：</label>
            <div class="col-sm-10">
                <textarea id="description" name="description" type="text" class="form-control" maxlength="128" placeholder="最多32个汉字" >${role.description}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="available">是否可用：</label>
            <div class="col-sm-10">
                <input id="available" name="available" type="checkbox" class="i-checks" ${role.available == true ? "checked" : ""}>
            </div>
        </div>
    </form>
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
<!-- validate -->
<script src="${staticPath}/static/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${staticPath}/static/js/admin/validate.js"></script>
<!-- iCheck -->
<script src="${staticPath}/static/js/plugins/iCheck/icheck.min.js"></script>
<!-- 后台通用js -->
<script src="${staticPath}/static/js/admin/common.js"></script>
<script>

    $().ready(function () {
        // 验证注册表单
        $("#saveButton").bind("click", function() {
            $('#validationSaveRoleFrom').submit();
        });
        $("#validationSaveRoleFrom").validate({
            rules : {
                roleKey : {
                    required : true,
                    minlength : 4,
                    maxlength : 32,
                    alnumunderline: true
                },
                roleValue : {
                    required : true,
                    minlength : 2,
                    maxlength : 32
                },
                description : {
                    maxlength : 128
                }
            },
            messages : {
                roleKey : {
                    required : "请输入角色关键字",
                    minlength : "角色关键字长度至少为{0}个字符",
                    maxlength : "角色关键字长度至多为{0}个字符"
                },
                roleValue : {
                    required : "请输入角色名",
                    minlength : "角色名长度至少为{0}个字符",
                    maxlength : "角色名长度至多为{0}个字符"
                },
                description : {
                    maxlength : "描述长度至多为{0}个字符"
                }
            },
            submitHandler : function(form) {
                $.ajax({
                    dataType : "json",
                    url : "${ctx}/sys/role/save",
                    type : "post",
                    data : {
                        id : $('#roleId').val(),
                        roleKey : $('#roleKey').val(),
                        roleValue : $("#roleValue").val(),
                        description : $("#description").val(),
                        available : $("#available").prop("checked")
                    },
                    complete : function(response) {
                        var result = response.responseJSON;
                        if (result.code == 1) {
                            parent.layer.msg("保存角色信息成功，请刷新后查看", {icon : 1});
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再执行关闭
                        } else  {
                            parent.layer.msg(result.message, {icon: 2});
                        }
                    }
                });
            },
            invalidHandler : function(form) {
            }
        });
    });
</script>
</body>
</html>
