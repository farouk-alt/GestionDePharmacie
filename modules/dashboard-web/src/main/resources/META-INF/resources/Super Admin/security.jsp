<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%@ page import="java.util.*" %>
<%@ page import="gestion_de_pharmacie.model.*" %>
<%@ page import="gestion_de_pharmacie.service.*" %>

<style>
    .table { width:100%; border-collapse:collapse; }
    .table th, .table td { padding:10px 12px; border-bottom:1px solid #eee; text-align:left; }
    .table th { background:#f8f9fa; }
    .role-select{ padding:8px;border:1px solid #ddd;border-radius:6px;}
    .btn{ background:#4e54c8;color:#fff;border:none;border-radius:6px;padding:8px 14px;cursor:pointer; }
    .btn:hover{ background:#3a3fb8; }
</style>

<h2>🔐 Gestion des Rôles</h2>

<%
    List<Utilisateur> users = UtilisateurLocalServiceUtil.getUtilisateurs(-1, -1);
%>

<table class="table">
    <tr>
        <th>Email</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Rôle Actuel</th>
        <th>Action</th>
    </tr>

    <c:forEach var="u" items="<%= users %>">
        <portlet:actionURL name="changeRole" var="changeRoleURL" />
        <tr>
            <td>${u.email}</td>
            <td>${u.nom}</td>
            <td>${u.prenom}</td>
            <td>${u.role}</td>
            <td>
                <form action="${changeRoleURL}" method="post" style="margin:0;">
                    <!-- NAMESPACED FIELDS -->
                    <input type="hidden" name="<portlet:namespace/>idUtilisateur" value="${u.idUtilisateur}" />
                    <select name="<portlet:namespace/>newRole" class="role-select">
                        <option value="ADMIN"       ${u.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                        <option value="PHARMACIEN"  ${u.role == 'PHARMACIEN' ? 'selected' : ''}>PHARMACIEN</option>
                        <option value="FOURNISSEUR" ${u.role == 'FOURNISSEUR' ? 'selected' : ''}>FOURNISSEUR</option>
                        <option value="SUPER_ADMIN" ${u.role == 'SUPER_ADMIN' ? 'selected' : ''}>SUPER_ADMIN</option>
                    </select>
                    <button type="submit" class="btn" style="margin-left:8px;">Changer</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
