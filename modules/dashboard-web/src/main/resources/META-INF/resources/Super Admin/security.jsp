<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />
<%@ page import="java.util.*, gestion_de_pharmacie.model.*, gestion_de_pharmacie.service.*" %>

<h2>🔐 Gestion des Rôles</h2>

<table border="1" cellpadding="5">
    <tr>
        <th>Email</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Rôle Actuel</th>
        <th>Action</th>
    </tr>
    <%
        List<Utilisateur> users = UtilisateurLocalServiceUtil.getUtilisateurs(-1, -1);
        for (Utilisateur u : users) {
    %>
    <tr>
        <td><%= u.getEmail() %></td>
        <td><%= u.getNom() %></td>
        <td><%= u.getPrenom() %></td>
        <td><%= u.getRole() %></td>
        <td>
            <form action="<portlet:actionURL name='changeRole'/>" method="post">
                <input type="hidden" name="idUtilisateur" value="<%= u.getIdUtilisateur() %>"/>
                <select name="newRole">
                    <option value="ADMIN">ADMIN</option>
                    <option value="PHARMACIEN">PHARMACIEN</option>
                    <option value="FOURNISSEUR">FOURNISSEUR</option>
                    <option value="SUPER_ADMIN">SUPER_ADMIN</option>
                </select>
                <button type="submit">Changer</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>

