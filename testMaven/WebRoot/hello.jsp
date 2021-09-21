<%@page language="java" contentType="text/html; character=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>管理员后台</title>
    <link rel="stylesheet" href="${ctx}/resource/css/pintuer.css">
    <link rel="stylesheet" href="${ctx}/resource/css/admin.css">
    <script src="${ctx}/resource/js/jquery.js"></script>
    <script src="${ctx}/resource/js/pintuer.js"></script>
    <script>
        $("#username").blur(function () {
            $("#nameInfo").css("color","red");
        })

    </script>
</head>
<body>
    <input type="button" id="btn" value="提交">

 <p id="nameInfo">用户名：</p>   <input id="username" type="text" name="username" ><br>
 密码：   <input id="password" type="password" name="password">




</body>

</html>