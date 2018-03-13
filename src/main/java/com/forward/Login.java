package com.forward;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PasswordTrackerLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Portlet implementation class Login
 */
public class Login extends MVCPortlet {

    public void loginUser(ActionRequest actionRequest,
                          ActionResponse actionResponse) throws IOException, PortletException {

        System.out.println("login action called");

        String email = ParamUtil.getString(actionRequest, "emailId").trim();
        String password = ParamUtil.getString(actionRequest, "password").trim();

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        HttpServletRequest request = PortalUtil.getHttpServletRequest(actionRequest);
        HttpServletResponse response = PortalUtil.getHttpServletResponse(actionResponse);

        User userDetail = null;
        try {
            userDetail = UserLocalServiceUtil.fetchUserByEmailAddress(themeDisplay.getCompanyId(), email);
            if (Validator.isNull(userDetail)) {
                actionRequest.setAttribute("EMAIL_NOT_EXIST", "Account not exist with email");
            } else {
                boolean passwordMatched = PasswordTrackerLocalServiceUtil.isSameAsCurrentPassword(userDetail.getUserId(), password);
                if (!passwordMatched) {
                    actionRequest.setAttribute("PASSWORD_NOT_MATCHED", "Please Enter Correct Password");
                } else {
                    // if user exist with provide email and given password matched with user database password
                    ClassLoader pcl = PortalClassLoaderUtil.getClassLoader();
                    Class lClass = pcl.loadClass("com.liferay.portlet.login.util.LoginUtil");
                    java.lang.reflect.Method method = lClass.getDeclaredMethod("login", HttpServletRequest.class, HttpServletResponse.class, String.class, String.class, Boolean.TYPE, String.class);
                    method.invoke(null, request, response, email, password, false, CompanyConstants.AUTH_TYPE_EA);

                    //redirection after login...
                    String screenName = userDetail.getScreenName();
                    String portalURL = PortalUtil.getPortalURL(actionRequest);
                    if (PortalUtil.isOmniadmin(userDetail.getUserId())) {
                        System.out.println("admin logged in, redirecting to admin  specified private page");
//                        actionResponse.sendRedirect(portalURL + "/user/"+ screenName+"/adminuser");
                        actionResponse.sendRedirect(portalURL + "/welcome");
                    } else {
                        System.out.println("normal user logged in, redirecting to private page specified for normal user");
                        actionResponse.sendRedirect(portalURL + "/user/" + screenName + "/normaluser");
                        actionResponse.sendRedirect(portalURL + "/welcome");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
