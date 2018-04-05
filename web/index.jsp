<%--
  Created by IntelliJ IDEA.
  User: fil-n
  Date: 17.09.17
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>НИРС</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script language="javascript" type="text/javascript" src="script.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <link rel="stylesheet" type="text/css" href="style.css">

</head>
<body>
<h2>Выберите картинку с тегом АПЕЛЬСИН </h2>
<div class="items">
    <div class="item" onclick="addOutline(this);"><img src="/resourse/${names.get(0).getImage()}"
                                                       onclick="selectItem(0);"></div>
    <div class="item" onclick="addOutline(this);"><img src="/resourse/${names.get(1).getImage()}"
                                                       onclick="selectItem(1);"></div>
    <div class="item" onclick="addOutline(this);"><img src="/resourse/${names.get(2).getImage()}"
                                                       onclick="selectItem(2);"></div>
    <div class="item" onclick="addOutline(this);"><img src="/resourse/${names.get(3).getImage()}"
                                                       onclick="selectItem(3);"></div>
    <div class="item" onclick="addOutline(this);"><img src="/resourse/${names.get(4).getImage()}"
                                                       onclick="selectItem(4);"></div>
    <div class="item" onclick="addOutline(this);"><img src="/resourse/${names.get(5).getImage()}"
                                                       onclick="selectItem(5);"></div>
    <div class="item" onclick="addOutline(this);"><img src="/resourse/${names.get(6).getImage()}"
                                                       onclick="selectItem(6);"></div>
    <div class="item" onclick="addOutline(this);"><img src="/resourse/${names.get(7).getImage()}"
                                                       onclick="selectItem(7);"></div>
    <div class="item" onclick="addOutline(this);"><img src="/resourse/${names.get(8).getImage()}"
                                                       onclick="selectItem(8);"></div>
</div>
<center>
    <button type="submit" onclick="submitArray();">Oтправить</button>
</center>
<div id="res"></div>
</body>
</html>
