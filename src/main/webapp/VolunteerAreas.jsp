<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page import="ucl.ac.uk.main.Volunteer" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ucl.ac.uk.main.Area" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Volunteer volunteer = (Volunteer) request.getAttribute("user");
    Model model = (Model) request.getAttribute("model");
%>
<html>
<head>
    <jsp:include page="/meta.jsp" />
    <title>Working Areas</title>
    <link rel="stylesheet" href="css/VolunteerAreas.css">
    <script type="text/javascript">
        function createNewElement() {
            const newLine = document.createElement('li');
            newLine.innerHTML = "<ul><li><input class='address' type='text' name='address' placeholder='Address'><input type='text' name='city' placeholder='City'><input type='text' name='country' placeholder='Country'><input type='number' name='radius' placeholder='Radius (in km)'></li>" +
                "<li><input class='inputStart' type='datetime-local' name='start'> - <input class='inputFinish' type='datetime-local' name='finish'></li></ul>";
            document.getElementById("productList").appendChild(newLine);
        }
    </script>
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
    <div class="listTitle">
        <div class="start"></div><div class="column2">Address</div><div class="column">Radius(km)</div><div class="column">Time Available</div>
    </div>
    <form id="productForm" method="post" action="VolunteerAreas">
        <ul id="productList">
            <%
                Iterator<Area> iterator = volunteer.getAreasIterator();
                int i = 0;
                while(iterator.hasNext()){
                    Area area = iterator.next();
            %>
            <li><div class="column2"><%=area.getAddress()%>, <%=area.getCity()%>, <%=area.getCountry()%></div><div class="column"><%=area.getRadius()%></div><div class="column"><%=area.getStartDateArea()%> <%=area.getStartTime()%> - <%=area.getFinishDateArea()%> <%=area.getFinishTime()%></div> <a href="VolunteerAreas?remove=<%=i%>" class="close"></a></li>
            <%
                    i++;
                }
            %>
        </ul>
    </form>
</div>
<div class="buttons">
    <input class="saveButton" type="submit" form="productForm" value="Save">
    <input class="newButton" type="button" value="New" onclick="createNewElement()">
</div>
</body>

</html>
