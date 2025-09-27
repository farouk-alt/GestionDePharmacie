<%@ page import="com.liferay.portal.kernel.model.Layout" %>
<%@ page import="com.liferay.portal.kernel.service.LayoutLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.theme.ThemeDisplay" %>


<%
    ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute("THEME_DISPLAY");
    Layout commandesLayout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(
            themeDisplay.getScopeGroupId(), false, "/commandes"); // change friendly URL if needed
    long commandesPlid = (commandesLayout != null) ? commandesLayout.getPlid() : themeDisplay.getPlid();
%>

<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<liferay-portlet:renderURL var="viewURL"
                           portletName="commande_web"
                           plid="<%= String.valueOf(commandesPlid) %>">
    <portlet:param name="mvcPath" value="/detail.jsp" />
    <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
</liferay-portlet:renderURL>

<a class="btn" href="${viewURL}">Voir</a>
