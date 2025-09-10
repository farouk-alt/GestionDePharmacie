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
