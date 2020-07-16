<%@ page import="ucl.ac.uk.main.Shop" %>
<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Shop shop = (Shop) request.getAttribute("user");
    Model model = (Model) request.getAttribute("model");
%>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Shop</title>
    <link rel="stylesheet" href="css/ShopHome.css">
    <script>
        function redirect(url){
            window.location.href = url;
        }
    </script>
</head>
<body>
<header>
    <%=shop.getName()%>
</header>
<div class="welcomeBack">
    <div class="welcomeBackSign">
        <span>W</span><span>E</span><span>L</span><span>C</span><span>O</span><span>M</span><span>E</span><span>!</span>
    </div>
</div>
<div class="buttons">
    <input type="button" class="button shopProducts" onclick=redirect('ShopProducts') value="Update Products">
    <input type="button" class="button shopDetails" onclick=redirect('ShopDetails') value="Update Details">
    <input type="button" class="button shopWorkingTimes" onclick=redirect('ShopOpeningTimes') value="Update Opening Times">
</div>
</body>

</html>

