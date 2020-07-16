<%@ page import="ucl.ac.uk.main.Shop" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Shop shop = (Shop) request.getAttribute("user");
%>
<html>

<head>
    <jsp:include page="/meta.jsp" />
    <title>Shop Opening Times</title>
    <link rel="stylesheet" href="css/ShopOpeningTimes.css">
    <script>
        document.addEventListener("DOMContentLoaded", function(event){
            let days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
            days.forEach(element => ch(element));
        })

        function ch(c){
            var el = document.getElementById(c);
            var start = "startTime" + c;
            var finish = "finishTime" + c;
            var dash = "dash" + c;
            if(el.checked){
                document.getElementById(start).style.display = 'none';
                document.getElementById(finish).style.display = 'none';
                document.getElementById(dash).style.display = 'none';
            }
        }

        function checkAction(c){
            var day = c.value;
            var start = "startTime" + day;
            var finish = "finishTime" + day;
            var dash = "dash" + day;
            var nz = "Closed" + day;
            if(c.checked){
                document.getElementById(start).style.display = 'none';
                document.getElementById(finish).style.display = 'none';
                document.getElementById(dash).style.display = 'none';
            }
            else{
                document.getElementById(start).style.display = 'inline';
                document.getElementById(finish).style.display = 'inline';
                document.getElementById(dash).style.display = 'inline';
            }
        }
    </script>
</head>

<body>
<header>
    <%=shop.getName()%>
</header>
<div class="menu">
    <a href="/ShopHome" class="button button1">Home</a>
    <a href="/ShopProducts" class="button button2">Products</a>
    <a href="/ShopWorkingTimes" class="button button3">Opening Times</a>
    <a href="/ShopDetails" class="button button4">Details</a>
</div>
<div class="list">
    <form id="productForm" method="post" action="/ShopOpeningTimes">
        <input type="hidden" name="First" value="1">
        <ul id="productList">
            <li> <span>Monday:</span>
                <input type="time" id="startTimeMonday" name="startTimeMonday" value="<%=shop.getOpeningTime("Monday")%>">
                <pre id="dashMonday"> - </pre>
                <input type="time" id="finishTimeMonday" name="finishTimeMonday" value="<%=shop.getClosingTime("Monday")%>">
                <input type="checkbox" id="Monday" name="closedMonday" value="Monday"
                       <%
                    if(!shop.getIsWorking("Monday")){
                        %>checked<%
                    }
                       %>
                       onchange="checkAction(this)">
                <label for="Monday">Closed</label>
            </li>
            <li> <span>Tuesday:</span>
                <input type="time" id="startTimeTuesday" name="startTimeTuesday" value="<%=shop.getOpeningTime("Tuesday")%>">
                <pre id="dashTuesday"> - </pre>
                <input type="time" id="finishTimeTuesday" name="finishTimeTuesday" value="<%=shop.getClosingTime("Tuesday")%>">
                <input type="checkbox" id="Tuesday" name="closedTuesday" value="Tuesday"
                       <%
                    if(!shop.getIsWorking("Tuesday")){
                        %>checked<%
                    }
                       %>
                       onchange="checkAction(this)">
                <label for="Tuesday">Closed</label>
            </li>
            <li> <span>Wednesday:</span>
                <input type="time" id="startTimeWednesday" name="startTimeWednesday" value="<%=shop.getOpeningTime("Wednesday")%>">
                <pre id="dashWednesday"> - </pre>
                <input type="time" id="finishTimeWednesday" name="finishTimeWednesday" value="<%=shop.getClosingTime("Wednesday")%>">
                <input type="checkbox" id="Wednesday" name="closedWednesday" value="Wednesday"
                       <%
                    if(!shop.getIsWorking("Wednesday")){
                        %>checked<%
                    }
                       %>
                       onchange="checkAction(this)">
                <label for="Wednesday">Closed</label>
            </li>
            <li> <span>Thursday:</span>
                <input type="time" id="startTimeThursday" name="startTimeThursday" value="<%=shop.getOpeningTime("Thursday")%>">
                <pre id="dashThursday"> - </pre>
                <input type="time" id="finishTimeThursday" name="finishTimeThursday" value="<%=shop.getClosingTime("Thursday")%>">
                <input type="checkbox" id="Thursday" name="closedThursday" value="Thursday"
                       <%
                    if(!shop.getIsWorking("Thursday")){
                        %>checked<%
                    }
                       %>
                       onchange="checkAction(this)">
                <label for="Thursday">Closed</label>
            </li>
            <li> <span>Friday:</span>
                <input type="time" id="startTimeFriday" name="startTimeFriday" value="<%=shop.getOpeningTime("Friday")%>">
                <pre id="dashFriday"> - </pre>
                <input type="time" id="finishTimeFriday" name="finishTimeFriday" value="<%=shop.getClosingTime("Friday")%>">
                <input type="checkbox" id="Friday" name="closedFriday" value="Friday"
                       <%
                    if(!shop.getIsWorking("Friday")){
                        %>checked<%
                    }
                       %>
                       onchange="checkAction(this)">
                <label for="Friday">Closed</label>
            </li>
            <li> <span>Saturday:</span>
                <input type="time" id="startTimeSaturday" name="startTimeSaturday" value="<%=shop.getOpeningTime("Saturday")%>">
                <pre id="dashSaturday"> - </pre>
                <input type="time" id="finishTimeSaturday" name="finishTimeSaturday" value="<%=shop.getClosingTime("Saturday")%>">
                <input type="checkbox" id="Saturday" name="closedSaturday" value="Saturday"
                       <%
                    if(!shop.getIsWorking("Saturday")){
                        %>checked<%
                    }
                       %>
                       onchange="checkAction(this)">
                <label for="Saturday">Closed</label>
            </li>
            <li> <span>Sunday:</span>
                <input type="time" id="startTimeSunday" name="startTimeSunday" value="<%=shop.getOpeningTime("Sunday")%>">
                <pre id="dashSunday"> - </pre>
                <input type="time" id="finishTimeSunday" name="finishTimeSunday" value="<%=shop.getClosingTime("Sunday")%>">
                <input type="checkbox" id="Sunday" name="closedSunday" value="Sunday"
                       <%
                    if(!shop.getIsWorking("Sunday")){
                        %>checked<%
                    }
                       %>
                       onchange="checkAction(this)">
                <label for="Sunday">Closed</label>
            </li>
        </ul>
    </form>
</div>
<div class="buttons">
    <input class="saveButton" type="submit" form="productForm" value="Save">
</div>
</body>

</html>
