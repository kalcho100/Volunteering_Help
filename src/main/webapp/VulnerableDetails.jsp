<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page import="ucl.ac.uk.main.Vulnerable" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Vulnerable vulnerable = (Vulnerable) request.getAttribute("user");
    Model model = (Model) request.getAttribute("model");
%>
<html>
<head>
    <jsp:include page="/meta.jsp" />
    <title>Vulnerable Details</title>
    <link rel="stylesheet" href="css/VulnerableDetails.css">
</head>

<body>
<header>
    <%=vulnerable.getFirstName()%> <%=vulnerable.getLastName()%>
</header>
<div class="menu">
    <a href="VulnerableHome" class="button button1">Home</a>
    <a href="VulnerableRequests" class="button button2">Requests</a>
    <a href="VulnerableDetails" class="button button3">Details</a>
</div>
<div class="list">
    <form id="detailsForm" method="post" class="inputForm" action="VulnerableDetails">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value="<%=vulnerable.getUsername()%>" required> <br/>
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" value="<%=vulnerable.getFirstName()%>" required> <br/>
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" value="<%=vulnerable.getLastName()%>" required> <br/>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" value="<%=vulnerable.getPassword()%>" required> <br/>
        <label for="repeatPassword">Repeat Password:</label>
        <input type="password" id="repeatPassword" name="repeatPassword" value="<%=vulnerable.getPassword()%>" required> <br/>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="<%=vulnerable.getAddress()%>" required> <br/>
        <input type="text" id="address2" name="address2"> <br/>
        <label for="city">City:</label>
        <input type="text" id="city" name="city" value="<%=vulnerable.getCity()%>" readonly> <br/>
        <label for="country">Country:</label>
        <input type="text" id="country" name="country" value="<%=vulnerable.getCountry()%>" readonly> <br/>
    </form>
</div>
<div class="buttons">
    <input class="saveButton" type="submit" form="detailsForm" value="Save">
</div>
</body>

</html>