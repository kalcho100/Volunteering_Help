<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page import="ucl.ac.uk.main.Volunteer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Volunteer volunteer = (Volunteer) request.getAttribute("user");
    Model model = (Model) request.getAttribute("model");
%>
<html>
<head>
    <jsp:include page="/meta.jsp" />
    <title>Volunteer Details</title>
    <link rel="stylesheet" href="css/VolunteerDetails.css">
</head>

<body>
<header>
    <%=volunteer.getFirstName()%> <%=volunteer.getLastName()%>
</header>
<div class="menu">
        <a href="VolunteerHome" class="button button1">Home</a>
        <a href="VolunteerRequests" class="button button2">My Requests</a>
        <a href="VolunteerAreas" class="button button3">Working Areas</a>
        <a href="VolunteerDetails" class="button button4">Details</a>
</div>
<div class="list">
    <form id="detailsForm" method="post" class="inputForm" action="VolunteerDetails">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value="<%=volunteer.getUsername()%>" required> <br/>
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" value="<%=volunteer.getFirstName()%>" required> <br/>
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" value="<%=volunteer.getLastName()%>" required> <br/>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" value="<%=volunteer.getPassword()%>" required> <br/>
        <label for="repeatPassword">Repeat Password:</label>
        <input type="password" id="repeatPassword" name="repeatPassword" value="<%=volunteer.getPassword()%>" required> <br/>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="<%=volunteer.getAddress()%>" required> <br/>
        <input type="text" id="address2" name="address2"> <br/>
        <label for="city">City:</label>
        <input type="text" id="city" name="city" value="<%=volunteer.getCity()%>" readonly> <br/>
        <label for="country">Country:</label>
        <input type="text" id="country" name="country" value="<%=volunteer.getCountry()%>" readonly> <br/>
    </form>
</div>
<div class="buttons">
    <input class="saveButton" type="submit" form="detailsForm" value="Save">
</div>
</body>

</html>