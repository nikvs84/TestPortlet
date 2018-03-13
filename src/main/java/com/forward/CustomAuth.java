package com.forward;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.*;
import java.io.IOException;
import java.util.Map;

public class CustomAuth extends MVCPortlet {
    protected String editJSP;
    protected String viewJSP;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuth.class);

    @Override
    public void init() throws PortletException {
        editJSP = getInitParameter("edit-jsp");
        viewJSP = getInitParameter("view-jsp");

    }

    @Override
    public void doEdit(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        renderResponse.setContentType("text/html");
        PortletURL addNameURL = renderResponse.createActionURL();
        addNameURL.setParameter("addName", "addName");
        String url = addNameURL.toString();
        renderRequest.setAttribute("addNameURL", url);
        include(editJSP, renderRequest, renderResponse);
    }

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        PortletPreferences prefs = renderRequest.getPreferences();
        String userName = (String) prefs.getValue("name", "no");
        if (userName == null || userName.equalsIgnoreCase("no")) {
            userName = "";
        }
        renderRequest.setAttribute("userName", userName);

        PortletURL authURL = renderResponse.createActionURL();
        authURL.setParameter("auth", "auth");
        renderRequest.setAttribute("authURL", authURL.toString());

        include(viewJSP, renderRequest, renderResponse);

    }

    @Override
    protected void include(String path, RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);
        if (portletRequestDispatcher == null) {
            System.out.println(path + " is not a valid include");
        } else {
            portletRequestDispatcher.include(renderRequest, renderResponse);
        }
    }


    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
        String addName = actionRequest.getParameter("addName");
        PortletPreferences prefs = actionRequest.getPreferences();
        if (addName != null) {
            String userName = actionRequest.getParameter("userName");
            prefs.setValue("name", userName);
            prefs.store();

        }

        String login = actionRequest.getParameter("login");
        String password = actionRequest.getParameter("password");
        LOGGER.info("Login: /t {}/nPassword /t {}", login, password);

        try {
            int companyId = 10154;
            UserLocalServiceUtil.authenticateByScreenName(companyId, login, password, null, null, null);
            long userId = UserLocalServiceUtil.getUserIdByScreenName(companyId, login);
            String userIdString = String.valueOf(userId);
        } catch (PortalException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }

        actionResponse.setPortletMode(PortletMode.VIEW);
    }

    public void login(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
        String login = actionRequest.getParameter("login");
        String password = actionRequest.getParameter("password");
        LOGGER.info("Login: /t {}/nPassword /t {}", login, password);
    }
}
