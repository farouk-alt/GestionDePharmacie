<%@ include file="/init.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<portlet:defineObjects/>

<%@ page import="gestion_de_pharmacie.model.Notification" %>
<%@ page import="gestion_de_pharmacie.model.Utilisateur" %>
<%@ page import="gestion_de_pharmacie.service.UtilisateurLocalServiceUtil" %>
<%@ page import="java.util.*" %>

<%
    // If your portlet controller already does req.setAttribute("notifs", ...),
    // you can delete this whole scriptlet block.
    @SuppressWarnings("unchecked")
    List<Notification> notifs = (List<Notification>) request.getAttribute("notifs");
    if (notifs == null) notifs = Collections.emptyList();
    pageContext.setAttribute("notifs", notifs); // expose to EL as ${notifs}
%>

<style>
    :root{ --primary:#1E3A8A; --border:#E5E7EB; --text:#111827; --white:#fff; }
    .tbl{width:100%;border-collapse:separate;border-spacing:0;background:var(--white);border:1px solid var(--border);border-radius:12px;overflow:hidden}
    .tbl thead th{background:var(--primary);color:#fff;text-align:left;padding:12px 14px}
    .tbl tbody td{padding:12px 14px;border-bottom:1px solid var(--border)}
</style>

<h3 style="margin:10px 0;">🔔 Notifications</h3>

<table class="tbl">
    <thead>
    <tr>
        <th>Utilisateur</th>
        <th>Type</th>
        <th>Message</th>
        <th>Statut</th>
        <th>Date</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="n" items="${notifs}">
        <%
            // Access the JSTL var "n" via pageContext inside scriptlet
            Notification nObj = (Notification) pageContext.getAttribute("n");
            Utilisateur u = (nObj != null) ? UtilisateurLocalServiceUtil.fetchUtilisateur(nObj.getIdUtilisateur()) : null;
            String email = (u != null) ? u.getEmail() : "Inconnu";
        %>
        <tr>
            <td><%= email %></td>
            <td><c:out value="${n.type}"/></td>
            <td><c:out value="${n.message}"/></td>
            <td><c:out value="${n.statut}"/></td>
            <td><fmt:formatDate value="${n.dateCreation}" pattern="dd/MM/yyyy HH:mm"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
