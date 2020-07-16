<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page import="ucl.ac.uk.main.Volunteer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Volunteer volunteer = (Volunteer) request.getAttribute("user");
    Model model = (Model) request.getAttribute("model");
%>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Shop</title>
    <link rel="stylesheet" href="css/VolunteerHome.css">
    <script>
        function redirect(url){
            window.location.href = url;
        }
    </script>
</head>
<body>
<header>
    <%=volunteer.getFirstName()%> <%=volunteer.getLastName()%>
</header>
<div class="welcomeBack">
    <div class="welcomeBackSign">
        <span>W</span><span>E</span><span>L</span><span>C</span><span>O</span><span>M</span><span>E</span><span>!</span>
    </div>
</div>
<div class="buttons">
    <input type="button" class="button volunteerRequests" onclick=redirect('VolunteerRequests') value="My Requests">
    <input type="button" class="button volunteerAreas" onclick=redirect('VolunteerAreas') value="Working Areas"><br>
    <input type="button" class="button volunteerDetails" onclick=redirect('VolunteerDetails') value="Details">
</div>
</body>

</html>
