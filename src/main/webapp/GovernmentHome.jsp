<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page import="ucl.ac.uk.main.Government" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Government government = (Government) request.getAttribute("user");
    Model model = (Model) request.getAttribute("model");
%>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Shop</title>
    <link rel="stylesheet" href="css/GovernmentHome.css">
    <script>
        function redirect(url){
            window.location.href = url;
        }
    </script>
</head>
<body>
<header>
    <%=government.getCity()%> Council
</header>
<div class="welcomeBack">
    <div class="welcomeBackSign">
        <span>W</span><span>E</span><span>L</span><span>C</span><span>O</span><span>M</span><span>E</span><span>!</span>
    </div>
</div>
<div class="buttons">
    <input type="button" class="button governmentVulnerables" onclick=redirect('GovernmentVulnerable') value="Add/Remove Vulnerable People">
    <input type="button" class="button governmentDetails" onclick=redirect('GovernmentDetails') value="Update Details">
</div>
</body>

</html>
