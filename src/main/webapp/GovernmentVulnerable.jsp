<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page import="ucl.ac.uk.main.Government" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ucl.ac.uk.main.RegisteredVulnerable" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Government government = (Government) request.getAttribute("user");
    Model model = (Model) request.getAttribute("model");
%>
<html>
<head>
    <jsp:include page="/meta.jsp" />
    <title><%=government.getCity()%> Council - Vulnerables</title>
    <link rel="stylesheet" href="css/GovernmentVulnerable.css">
    <script type="text/javascript">
        function createNewElement() {
            const newLine = document.createElement('li');
            newLine.innerHTML = "<span><input type='text' name='firstName' placeholder='First name' required> <input type='text' name='lastName' placeholder='Last name' required> </span> <span><input type='text' name='address' placeholder='Address' required>" +
                "<input class='inputs' type='text' value='<%=government.getCity()%>' readonly><input class='inputs' type='text' value='<%=government.getCountry()%>' readonly> ";
            document.getElementById("productList").appendChild(newLine);
        }
    </script>
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
    <div class="listTitle">
        Name Address
    </div>
    <form id="productForm" method="post" action="GovernmentVulnerable">
        <ul id="productList">
                <%
                Iterator<RegisteredVulnerable> iterator = government.getRegisteredVulnerablesIterator();
                int i = 0;
                while(iterator.hasNext()){
                    RegisteredVulnerable a = iterator.next();
            %>
            <li><span><%=a.getFirstName()%> <%=a.getLastName()%></span> <span><%=a.getAddress()%>, <%=a.getCity()%>, <%=a.getCountry()%></span>
                <a href="GovernmentVulnerable?remove=<%=i%>" class="close"></a></li>
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
