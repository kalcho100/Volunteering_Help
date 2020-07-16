<%@ page import="java.util.Iterator" %>
<%@ page import="ucl.ac.uk.main.Request" %>
<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page import="ucl.ac.uk.main.Vulnerable" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Vulnerable vulnerable = (Vulnerable) request.getAttribute("user");
    Model model = (Model) request.getAttribute("model");
%>
<html>
<head>
    <jsp:include page="/meta.jsp" />
    <title>Requests</title>
    <link rel="stylesheet" href="css/VulnerableRequests.css">
    <script type="text/javascript">
        function createNewElement() {
            const newLine = document.createElement('li');
            newLine.innerHTML = "<input class='inputProduct' type='text' name='product' placeholder='Product'> <input type='number' name='quantity' placeholder='Quantity'> <input type='number' class='inputDays' name='wait' min='1' max='30' placeholder='Days to wait'><select name='type'><option value='grocery store'>Grocery store</option><option value='pharmacy'>Pharmacy</option></select>";
            document.getElementById("productList").appendChild(newLine);
        }
    </script>
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
    <div class="listTitle">
        <div class="start"></div> <div class="column">Product</div><div class="column2">Quantity</div><div class="column">Deliver By</div><div class="column">Accepted</div><div class="column">Type</div>
    </div>
    <form id="productForm" method="post" action="VulnerableRequests">
        <ul id="productList">
            <%
                Iterator<Request> iterator = vulnerable.getRequestsIterator();
                int i = 0;
                while(iterator.hasNext()){
                    Request a = iterator.next();
            %>
            <li>
                <div class="column"><%=a.getProduct()%></div><div class="column2"><%=a.getQuantity()%></div><div class="column"><%=a.getDeliverBy().get(Calendar.DATE)%>-<%=a.getDeliverBy().get(Calendar.MONTH) + 1%>-<%=a.getDeliverBy().get(Calendar.YEAR)%></div>
                <%
                    if(a.getIsAccepted()){
                %>
                <div class="column">Yes (<%=a.getAcceptedBy().getFirstName()%> <%=a.getAcceptedBy().getLastName()%>)</div>
                <%
                    }else{
                %>
                <div class="column">No</div>
                <%}%>
                <div class="column"><%=a.getType()%></div>
                <a href="VulnerableRequests?remove=<%=i%>" class="close"></a></li>
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
