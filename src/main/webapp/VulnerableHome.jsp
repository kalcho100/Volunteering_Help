<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page import="ucl.ac.uk.main.Vulnerable" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Vulnerable vulnerable = (Vulnerable) request.getAttribute("user");
    Model model = (Model) request.getAttribute("model");
%>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Shop</title>
    <link rel="stylesheet" href="css/VulnerableHome.css">
    <script>
        function redirect(url){
            window.location.href = url;
        }
    </script>
</head>
<body>
<header>
    <%=vulnerable.getFirstName()%> <%=vulnerable.getLastName()%>
</header>
<div class="welcomeBack">
    <div class="welcomeBackSign">
        <span>W</span><span>E</span><span>L</span><span>C</span><span>O</span><span>M</span><span>E</span><span>!</span>
    </div>
</div>
<div class="buttons">
    <input type="button" class="button vulnerableRequests" onclick=redirect('VulnerableRequests') value="Update Requests">
    <input type="button" class="button vulnerableDetails" onclick=redirect('VulnerableDetails') value="Update Details">
</div>
</body>

</html>