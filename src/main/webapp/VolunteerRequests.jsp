<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ucl.ac.uk.main.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Volunteer volunteer = (Volunteer) request.getAttribute("user");
    Model model = (Model) request.getAttribute("model");
%><html>

<head>
    <jsp:include page="/meta.jsp" />
    <title>My Requests</title>
    <link rel="stylesheet" href="css/VolunteerRequests.css">
</head>

<body>
<header>
    <%=volunteer.getFirstName()%> <%=volunteer.getLastName()%>
</header>
<div class="menu">
    <a href="VolunteerHome" class="button button1">Home</a>
    <a href="VolunteerRequests" class="button button3">My Requests</a>
    <a href="VolunteerAreas" class="button button4">Working Areas</a>
    <a href="VolunteerDetails" class="button button5">Details</a>
</div>
<div class="list">
    <div class="listTitle">
        <div class="column">Type</div><div class="column">Address</div><div class="column">Name</div><div class="column">Product</div><div class="column">Quantity</div>
    </div>
    <ul id="productList">
        <%
            Iterator<Area> iterator = volunteer.getAreasIterator();
            while(iterator.hasNext()){
                Area area = iterator.next();
                if(area.lessThen30()){
                    ArrayList<Object> objects = model.listSuitableRequests(area);
                    for(Object a : objects){
                                %>
                        <li><ul>
                            <%
                                if(a instanceof Area){
                            %>
                            <li><div class="column">Start</div><div class="column"><%=((Area) a).getAddress()%></div><div class="column"> - </div><div class="column"> - </div><div class="column"> - </div></li>
                            <%
                                }
                            %>
                            <%
                                if(a instanceof Shop){
                            %>
                            <li><div class="column">Shop</div><div class="column"><%=((Shop) a).getAddress()%></div><div class="column"><%=((Shop) a).getName()%></div><div class="column"> - </div><div class="column"> - </div></li>
                            <%
                                }
                            %>
                            <%
                                if(a instanceof Request){
                            %>
                            <li><div class="column">Request</div><div class="column"><%=((Request) a).getRequestedBy().getAddress()%></div><div class="column"><%=((Request) a).getRequestedBy().getFirstName()%> <%=((Request) a).getRequestedBy().getLastName()%></div><div class="column"><%=((Request) a).getProduct()%>></div><div class="column"><%=((Request) a).getQuantity()%></div></li>
                            <%
                                }
                            %>
                        </ul></li>
                        <%
                    }
                }
            }
        %>
    </ul>
</div>
</body>

</html>
