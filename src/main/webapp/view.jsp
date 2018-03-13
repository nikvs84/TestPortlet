<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aui" uri="http://alloy.liferay.com/tld/aui" %>
<portlet:defineObjects/>

<jsp:useBean id="userName" class="java.lang.String" scope="request"/>
<jsp:useBean id="authUrl" class="java.lang.String" scope="request"/>
<%--<portlet:actionURL name="login" var="loginAction" ></portlet:actionURL>--%>

<p>Hello <%= userName %>!</p>

<aui:form action="<%=authUrl%>" id="authForm" method="post">
    <aui:select name="authType">
        <aui:option value="E-mail" selected="selected">E-mail</aui:option>
        <aui:option value="UserName">UserName</aui:option>
        <aui:option value="PhoneNumber">PhoneNumber</aui:option>
        <aui:option value="UserId">UserId</aui:option>
    </aui:select>
    <aui:input type="text" name="login"/>
    <aui:input type="password" name="password"/>
    <aui:input title="LOGIN" value="Login" name="login" type="submit" />
</aui:form>

This is the <b>TestPortlet</b>.