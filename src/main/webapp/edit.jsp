<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 28.02.2018
  Time: 12:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<portlet:defineObjects/>
<jsp:useBean id="addNameURL" class="java.lang.String" scope="request" />

<aui:form id="helloForm" action="<%=addNameURL%>" method="post" >
    <table>
        <tr>
            <td>Name</td>
            <td>
                <aui:input type="text" name="userName" />
            </td>
        </tr>
    </table>
    <input type="submit" id="nameButton" title="Add Name" value="Add Name">
</aui:form>