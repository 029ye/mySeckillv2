<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/15
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/tag.jsp" %>
<html>
<head>
    <title>秒杀列表</title>
    <%@ include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>库存</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>创建时间</th>
                    <th>详情</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="l">
                    <tr>
                        <td>${l.name}</td>
                        <td>${l.number}</td>
                        <td><fmt:formatDate value="${l.startTime}" pattern="yyyy年MM月dd日"/></td>
                        <td><fmt:formatDate value="${l.endTime}" pattern="yyyy年MM月dd日"/></td>
                        <td><fmt:formatDate value="${l.endTime}" pattern="yyyy年MM月dd日"/></td>
                        <td><a href="${basePath}seckill/${l.seckillId}/detail" class="btn  btn-info" target="_blank">详情</a> </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
<script src="https://code.jquery.com/jquery.js"></script>
<!-- 包括所有已编译的插件 -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>
