<%--
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%@ page import="java.util.*" %>
<%@ page import="gestion_de_pharmacie.model.*" %>
<%@ page import="gestion_de_pharmacie.service.*" %>

<style>
    .table { width:100%; border-collapse:collapse; }
    .table th, .table td { padding:10px 12px; border-bottom:1px solid #eee; text-align:left; }
    .table th { background:#f8f9fa; }
    h2, h3 { margin:16px 0 10px 0; }
</style>

<h2>📋 Logs du Système</h2>

<h3>Connexions</h3>
<table class="table">
    <tr>
        <th>Email</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Dernière Connexion</th>
    </tr>
    <%
        List<Utilisateur> users = UtilisateurLocalServiceUtil.getUtilisateurs(-1, -1);
        for (Utilisateur u : users) {
    %>
    <tr>
        <td><%= u.getEmail() %></td>
        <td><%= u.getNom() %></td>
        <td><%= u.getPrenom() %></td>
        <td>
            <%
                Date ll = u.getLastLogin();
                if (ll != null) {
            %>
            <fmt:formatDate value="<%= ll %>" pattern="dd/MM/yyyy HH:mm"/>
            <%
            } else {
            %>Jamais connecté<% } %>
        </td>
    </tr>
    <% } %>
</table>

<h3>Notifications</h3>
<table class="table">
    <tr>
        <th>Utilisateur</th>
        <th>Type</th>
        <th>Message</th>
        <th>Date</th>
    </tr>
    <%
        List<Notification> notifs = NotificationLocalServiceUtil.getNotifications(-1, -1);
        for (Notification n : notifs) {
            Utilisateur u = UtilisateurLocalServiceUtil.fetchUtilisateur(n.getIdUtilisateur());
    %>
    <tr>
        <td><%= (u != null ? u.getEmail() : "Inconnu") %></td>
        <td><%= n.getType() %></td>
        <td><%= n.getMessage() %></td>
        <td><fmt:formatDate value="<%= n.getDateCreation() %>" pattern="dd/MM/yyyy HH:mm"/></td>
    </tr>
    <% } %>
</table>
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%@ page import="java.util.*" %>
<%@ page import="gestion_de_pharmacie.model.*" %>
<%@ page import="gestion_de_pharmacie.service.*" %>

<%
    List<Utilisateur> users = UtilisateurLocalServiceUtil.getUtilisateurs(-1, -1);
    pageContext.setAttribute("users", users);

    List<Notification> notifs = NotificationLocalServiceUtil.getNotifications(-1, -1);
    pageContext.setAttribute("notifs", notifs);
%>

<style>
    :root{
        --primary:#1E3A8A;
        --secondary:#3B82F6;
        --accent:#10B981;
        --bg:#F3F4F6;
        --white:#fff;
        --text:#111827;
        --muted:#6B7280;
        --border:#E5E7EB;
    }
    .logs-wrap{font-family:'Segoe UI',Tahoma,Verdana,sans-serif;color:var(--text)}
    .logs-header{display:flex;align-items:center;gap:12px;margin:0 0 14px 0}
    .logs-header .chip{
        width:38px;height:38px;border-radius:10px;background:linear-gradient(135deg,var(--primary),var(--accent));
        display:flex;align-items:center;justify-content:center;color:var(--white)
    }
    h2:not(.b), h3 {
        margin: 12px 0;
        color: var(--primary);
    }
    .table{
        width:100%;border-collapse:separate;border-spacing:0;background:var(--white);
        border:1px solid var(--border);border-radius:12px;overflow:hidden;box-shadow:0 10px 28px rgba(17,24,39,.08);margin-bottom:18px
    }
    .table thead th{background:var(--primary);color:var(--white);text-align:left;padding:12px 14px;font-weight:700}
    .table tbody td{padding:12px 14px;border-bottom:1px solid var(--border)}
    .table tbody tr:nth-child(even){background:#F9FAFB}
    .badge{
        display:inline-block;padding:4px 8px;border-radius:999px;font-size:12px;font-weight:700;background:linear-gradient(135deg,var(--secondary),#60A5FA);color:#fff
    }
</style>

<div class="logs-wrap">
    <div class="logs-header">
        <div class="chip">📋</div>
        <h2 style="margin:0;">Logs du Système</h2>
    </div>

    <h3>Connexions</h3>
    <table class="table">
        <thead>
        <tr>
            <th>Email</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Dernière connexion</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${users}">
            <tr>
                <td>${u.email}</td>
                <td>${u.nom}</td>
                <td>${u.prenom}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty u.lastLogin}">
                            <fmt:formatDate value="${u.lastLogin}" pattern="dd/MM/yyyy HH:mm"/>
                        </c:when>
                        <c:otherwise>Jamais connecté</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h3>Notifications</h3>
    <table class="table">
        <thead>
        <tr>
            <th>Utilisateur</th>
            <th>Type</th>
            <th>Message</th>
            <th>Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="n" items="${notifs}">
            <c:set var="user" value="${null}" />
            <%-- Lookup user object via scriptlet then expose --%>
            <%
                Notification nObj = (Notification) pageContext.getAttribute("n");
                Utilisateur u1 = (nObj != null) ? UtilisateurLocalServiceUtil.fetchUtilisateur(nObj.getIdUtilisateur()) : null;
                pageContext.setAttribute("user", u1);
            %>
            <tr>
                <td><c:out value="${user != null ? user.email : 'Inconnu'}"/></td>
                <td><span class="badge"><c:out value="${n.type}"/></span></td>
                <td><c:out value="${n.message}"/></td>
                <td><fmt:formatDate value="${n.dateCreation}" pattern="dd/MM/yyyy HH:mm"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
