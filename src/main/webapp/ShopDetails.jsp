<%@ page import="ucl.ac.uk.main.Shop" %>
<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Shop shop = (Shop) request.getAttribute("user");
    Model model = (Model) request.getAttribute("model");
%>
<html>
<head>
    <jsp:include page="/meta.jsp" />
    <title>Shop Details</title>
    <link rel="stylesheet" href="css/ShopDetails.css">
</head>

<body>
<header>
    <%=shop.getName()%>
</header>
<div class="menu">
    <a href="ShopHome" class="button button1">Home</a>
    <a href="ShopProducts" class="button button2">Products</a>
    <a href="ShopOpeningTimes" class="button button3">Opening Times</a>
    <a href="ShopDetails" class="button button4">Details</a>
</div>
<div class="list">
    <form id="detailsForm" method="post" class="inputForm" action="ShopDetails">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value="<%=shop.getUsername()%>" required> <br/>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required> <br/>
        <label for="repeatPassword">Repeat Password:</label>
        <input type="password" id="repeatPassword" name="repeatPassword" required> <br/>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="<%=shop.getAddress()%>" required> <br/>
        <input type="text" id="address2" name="address2"> <br/>
        <label for="city">City:</label>
        <input type="text" id="city" name="city" value="<%=shop.getCity()%>" required> <br/>
        <label for="country">Country:</label>
        <input type="text" id="country" name="country" value="<%=shop.getCountry()%>" required> <br/>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="<%=shop.getName()%>" required> <br/>
        <label for="type">Type:</label>
        <select id="type" name="type">
            <%
                if(shop.getType().equals("grocery store")){
            %>
            <option value="grocery store">Grocery store</option>
            <option value="pharmacy">Pharmacy</option>
            <%
                }else{
            %>
            <option value="pharmacy">Pharmacy</option>
            <option value="grocery store">Grocery store</option>
            <% } %>
        </select> <br/>
    </form>
</div>
<div class="buttons">
    <input class="saveButton" type="submit" form="detailsForm" value="Save">
</div>
</body>

</html>
