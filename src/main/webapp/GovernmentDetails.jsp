<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page import="ucl.ac.uk.main.Government" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Government government = (Government) request.getAttribute("user");
    Model model = (Model) request.getAttribute("model");
%>
<html>
<head>
    <jsp:include page="/meta.jsp" />
    <title>Shop Details</title>
    <link rel="stylesheet" href="css/GovernmentDetails.css">
</head>

<body>
<header>
    <%=government.getCity()%> Council
</header>
<div class="menu">
    <a href="GovernmentHome" class="button button1">Home</a>
    <a href="GovernmentVulnerable" class="button button2">Add/Remove</a>
    <a href="GovernmentDetails" class="button button3">Details</a>
</div>
<div class="list">
    <form id="detailsForm" method="post" class="inputForm" action="GovernmentDetails">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value="<%=government.getUsername()%>" required> <br/>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" value="<%=government.getPassword()%>" required> <br/>
        <label for="repeatPassword">Repeat Password:</label>
        <input type="password" id="repeatPassword" name="repeatPassword" value="<%=government.getPassword()%>" required> <br/>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="<%=government.getAddress()%>" required> <br/>
        <input type="text" id="address2" name="address2"> <br/>
        <label for="city">City:</label>
        <input type="text" id="city" name="city" value="<%=government.getCity()%>" readonly> <br/>
        <label for="country">Country:</label>
        <input type="text" id="country" name="country" value="<%=government.getCountry()%>" readonly> <br/>
    </form>
</div>
<div class="buttons">
    <input class="saveButton" type="submit" form="detailsForm" value="Save">
</div>
</body>

</html>