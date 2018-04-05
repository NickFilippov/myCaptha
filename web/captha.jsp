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
  <link rel="stylesheet" type="text/css" href="style.css">

</head>
<body>
<h1>НИРС</h1>
<section>
  <h2>Выберите картинку с тегом </h2>
  <div class="grid">
    <div class="item"><img onclick="selectItem(0)" src="/resourse/images(1).jpg"></div>
    <div class="item"><img onclick="selectItem(1)" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNQTD5n-R3QDouljE7wSttw9fCaRbY20H7Iz-RekTsDsY390BjHQ"></div>
    <div class="item"><img onclick="selectItem(2)" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNQTD5n-R3QDouljE7wSttw9fCaRbY20H7Iz-RekTsDsY390BjHQ"></div>
    <div class="item"><img onclick="selectItem(3)" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNQTD5n-R3QDouljE7wSttw9fCaRbY20H7Iz-RekTsDsY390BjHQ"></div>
    <div class="item"><img onclick="selectItem(4)" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNQTD5n-R3QDouljE7wSttw9fCaRbY20H7Iz-RekTsDsY390BjHQ"></div>
    <div class="item"><img onclick="selectItem(5)" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNQTD5n-R3QDouljE7wSttw9fCaRbY20H7Iz-RekTsDsY390BjHQ"></div>
    <div class="item"><img onclick="selectItem(6)" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNQTD5n-R3QDouljE7wSttw9fCaRbY20H7Iz-RekTsDsY390BjHQ"></div>
    <div class="item"><img onclick="selectItem(7)" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNQTD5n-R3QDouljE7wSttw9fCaRbY20H7Iz-RekTsDsY390BjHQ"></div>
    <div class="item"><img onclick="selectItem(8)" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNQTD5n-R3QDouljE7wSttw9fCaRbY20H7Iz-RekTsDsY390BjHQ"></div>
  </div>
  <button type="submit" onclick="submitArray()"></button>
</section>
</body>
</html>
