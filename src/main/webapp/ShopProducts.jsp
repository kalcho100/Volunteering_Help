<%@ page import="ucl.ac.uk.main.Shop" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Shop shop = (Shop) request.getAttribute("user");
%>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Shop Products</title>
    <link rel="stylesheet" href="css/ShopProducts.css">
    <script type="text/javascript">
        function createNewElement() {
            const newLine = document.createElement('li');
            newLine.innerHTML = "<span><input type='Text' id='newProduct' name='newProduct'></span> <input type='number' id='newQuantity' name='newQuantity' value='1' min='0' max='9999'>";
            document.getElementById("productList").appendChild(newLine);
        }
    </script>
</head>
<body>
<header>
    <%=shop.getName()%>
</header>
<div class="menu">
    <a href="/ShopHome" class="button button1">Home</a>
    <a href="/ShopProducts" class="button button2">Products</a>
    <a href="/ShopOpeningTimes" class="button button3">Opening Times</a>
    <a href="/ShopDetails" class="button button4">Details</a>
</div>
<div class="list">
    <form id="productForm" method="post" action="/ShopProducts">
        <ul id="productList">
            <%

                Set<String> products = shop.getProducts();
                for (String a : products) {
            %>
            <li><span><%=a%></span> <input type="number" id="quantity" name="<%=a%>" value="<%=shop.getQuantity(a)%>"
                                           min="0"
                                           max="9999">
            </li>
            <%
                }
            %>
        </ul>
    </form>
</div>
<div class="buttons">
    <input class="saveButton" type="submit" form="productForm" value="Save">
    <input class="newButton" type="button" value="New Product" onclick="createNewElement()">
</div>
</body>

</html>

