<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />
<%@ page import="java.util.*, gestion_de_pharmacie.model.*, gestion_de_pharmacie.service.*" %>

<h2>📋 Logs du Système</h2>

<h3>Connexions</h3>
<table border="1" cellpadding="5">
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
        <td><%= u.getLastLogin() != null ? u.getLastLogin() : "Jamais connecté" %></td>
    </tr>
    <%
        }
    %>
</table>

<h3>Notifications</h3>
<table border="1" cellpadding="5">
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
        <td><%= u != null ? u.getEmail() : "Inconnu" %></td>
        <td><%= n.getType() %></td>
        <td><%= n.getMessage() %></td>
        <td><%= n.getDateCreation() %></td>
    </tr>
    <%
        }
    %>
</table>


