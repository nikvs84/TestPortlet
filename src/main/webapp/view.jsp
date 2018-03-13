<%@page import="com.liferay.portal.kernel.util.StringPool" %>
<%@page import="com.liferay.portal.kernel.util.Validator" %>
<%@page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@page import="javax.portlet.ActionRequest" %>
<%@page import="javax.portlet.PortletURL" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<portlet:defineObjects/>


<%
    String passwordNotMatched = Validator.isNotNull((String) request.getAttribute("PASSWORD_NOT_MATCHED")) ? (String) request.getAttribute("PASSWORD_NOT_MATCHED") : StringPool.BLANK;
    String emailNotExist = Validator.isNotNull((String) request.getAttribute("EMAIL_NOT_EXIST")) ? (String) request.getAttribute("EMAIL_NOT_EXIST") : StringPool.BLANK;

    PortletURL loginURL = renderResponse.createActionURL();
    loginURL.setParameter(ActionRequest.ACTION_NAME, "loginUser");
%>

<%
    if (Validator.isNotNull(emailNotExist)) {%>
<div style="color:red;"><%=emailNotExist%>
</div>
<%} else if (Validator.isNotNull(passwordNotMatched)) {%>
<div style="color:red;"><%=passwordNotMatched%>
</div>
<%}%>

<aui:form method="post" action="<%=loginURL.toString() %>" id="aui-login-form">
    <div class="col-lg-4 col-a-vik col-b-vik">
        <span style="color:black;font-size: 10.5px;" id="label-email">Email Id</span>
        <div class="input-group">
            <input class="form-control" type="text" placeholder="Email Id" name="<portlet:namespace/>emailId"
                   id="<portlet:namespace/>emailId" style="margin-bottom: 0px;">
        </div>
    </div>
    <div class="col-lg-4 col-a-vik col-b-vik">
        <span style="color:black;font-size: 10.5px;" id="label-pwd">Password</span>
        <div class="input-group">
            <input class="form-control" type="password" placeholder="password" id="<portlet:namespace/>password"
                   name="<portlet:namespace/>password" style="margin-bottom: 0px;">
        </div>
    </div>
    <div class="col-lg-4 col-a-vik col-b-vik">
        <aui:button type="submit" id="btn-submit" style="background:#EE9400" value="Log In"/>
    </div>
</aui:form>