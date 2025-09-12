<%--
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
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%@ page import="java.util.*" %>
<%@ page import="gestion_de_pharmacie.model.*" %>
<%@ page import="gestion_de_pharmacie.service.*" %>

<%
    List<Utilisateur> users = UtilisateurLocalServiceUtil.getUtilisateurs(-1, -1);
    pageContext.setAttribute("users", users);
%>

<style>
    :root{
        --primary:#1E3A8A;   /* deep blue */
        --secondary:#3B82F6; /* light blue */
        --accent:#10B981;    /* green */
        --bg:#F3F4F6;
        --white:#fff;
        --text:#111827;
        --muted:#6B7280;
        --border:#E5E7EB;
    }
    .roles-wrap{font-family:'Segoe UI',Tahoma,Verdana,sans-serif;color:var(--text)}
    .roles-header{display:flex;align-items:center;gap:12px;margin:0 0 14px 0}
    .roles-header .chip{
        width:38px;height:38px;border-radius:10px;background:linear-gradient(135deg,var(--accent),var(--secondary));
        display:flex;align-items:center;justify-content:center;color:var(--white)
    }
    .table{
        width:100%;border-collapse:separate;border-spacing:0;background:var(--white);
        border:1px solid var(--border);border-radius:12px;overflow:hidden;box-shadow:0 10px 28px rgba(17,24,39,.08)
    }
    .table thead th{
        background:var(--primary);color:var(--white);text-align:left;padding:12px 14px;font-weight:700
    }
    .table tbody td{padding:12px 14px;border-bottom:1px solid var(--border)}
    .table tbody tr:nth-child(even){background:#F9FAFB}
    .role-select{padding:8px 10px;border:1px solid var(--border);border-radius:8px}
    .btn{
        background:var(--secondary);color:var(--white);border:none;border-radius:8px;padding:8px 14px;cursor:pointer;font-weight:700
    }
    .btn:hover{background:#2563EB}
    .btn:disabled{opacity:.5;cursor:not-allowed}
    .tag{
        display:inline-block;padding:4px 8px;border-radius:999px;font-size:12px;font-weight:700
    }
    .tag-admin{background:linear-gradient(135deg,#F59E0B,#FCD34D);color:#111827}
    .tag-super{background:linear-gradient(135deg,#EF4444,#FB7185);color:#fff}
    .tag-pharma{background:linear-gradient(135deg,#10B981,#34D399);color:#064E3B}
    .tag-four{background:linear-gradient(135deg,#3B82F6,#60A5FA);color:#0B224A}
</style>

<div class="roles-wrap">
    <div class="roles-header">
        <div class="chip">🔐</div>
        <h2 style="margin:0;color:var(--primary)">Gestion des Rôles</h2>
    </div>

    <c:set var="currentUserRole" value="${empty requestScope.userRole ? sessionScope.USER_ROLE : requestScope.userRole}" />

    <table class="table">
        <thead>
        <tr>
            <th>Email</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Rôle actuel</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${users}">
            <portlet:actionURL name="changeRole" var="changeRoleURL" />
            <tr>
                <td>${u.email}</td>
                <td>${u.nom}</td>
                <td>${u.prenom}</td>
                <td>
                    <c:choose>
                        <c:when test="${u.role == 'SUPER_ADMIN'}"><span class="tag tag-super">SUPER_ADMIN</span></c:when>
                        <c:when test="${u.role == 'ADMIN'}"><span class="tag tag-admin">ADMIN</span></c:when>
                        <c:when test="${u.role == 'PHARMACIEN'}"><span class="tag tag-pharma">PHARMACIEN</span></c:when>
                        <c:when test="${u.role == 'FOURNISSEUR'}"><span class="tag tag-four">FOURNISSEUR</span></c:when>
                        <c:otherwise>${u.role}</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${currentUserRole == 'SUPER_ADMIN'}">
                            <form action="${changeRoleURL}" method="post" style="margin:0;display:flex;gap:8px;align-items:center;">
                                <input type="hidden" name="<portlet:namespace/>idUtilisateur" value="${u.idUtilisateur}" />
                                <select name="<portlet:namespace/>newRole" class="role-select">
                                    <option value="ADMIN"       ${u.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                                    <option value="PHARMACIEN"  ${u.role == 'PHARMACIEN' ? 'selected' : ''}>PHARMACIEN</option>
                                    <option value="FOURNISSEUR" ${u.role == 'FOURNISSEUR' ? 'selected' : ''}>FOURNISSEUR</option>
                                    <option value="SUPER_ADMIN" ${u.role == 'SUPER_ADMIN' ? 'selected' : ''}>SUPER_ADMIN</option>
                                </select>
                                <button type="submit" class="btn">Changer</button>
                            </form>
                        </c:when>

                        <c:otherwise>
                            <c:choose>
                                <c:when test="${u.role == 'ADMIN' || u.role == 'SUPER_ADMIN'}">
                                    <button class="btn" disabled>Non modifiable</button>
                                </c:when>
                                <c:otherwise>
                                    <form action="${changeRoleURL}" method="post" style="margin:0;display:flex;gap:8px;align-items:center;">
                                        <input type="hidden" name="<portlet:namespace/>idUtilisateur" value="${u.idUtilisateur}" />
                                        <select name="<portlet:namespace/>newRole" class="role-select">
                                            <option value="PHARMACIEN"  ${u.role == 'PHARMACIEN' ? 'selected' : ''}>PHARMACIEN</option>
                                            <option value="FOURNISSEUR" ${u.role == 'FOURNISSEUR' ? 'selected' : ''}>FOURNISSEUR</option>
                                        </select>
                                        <button type="submit" class="btn">Changer</button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

