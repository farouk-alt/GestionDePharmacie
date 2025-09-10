<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:defineObjects />

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Tableau de Bord - PharmaCare</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .dashboard-container{max-width:1200px;margin:0 auto;padding:20px;font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif}
        .dashboard-header{background:linear-gradient(135deg,#4e54c8,#8f94fb);color:#fff;padding:20px;border-radius:10px;margin-bottom:20px;display:flex;justify-content:space-between;align-items:center}
        .user-info{display:flex;align-items:center;gap:15px}
        .user-avatar{width:60px;height:60px;background:rgba(255,255,255,.2);border-radius:50%;display:flex;align-items:center;justify-content:center;font-size:24px}
        .btn{background:#fff;color:#4e54c8;border:none;padding:10px 16px;border-radius:8px;cursor:pointer;font-weight:600;text-decoration:none;display:inline-block;transition:all .2s}
        .btn:hover{background:#f5f5f5;transform:translateY(-1px)}
        .tabs{display:flex;gap:10px;margin:14px 0 22px 0;flex-wrap:wrap}
        .tab{background:#eef1ff;color:#4e54c8;padding:8px 12px;border-radius:8px;text-decoration:none;font-weight:600}
        .tab.active{background:#4e54c8;color:#fff}
        .dashboard-cards{display:grid;grid-template-columns:repeat(auto-fill,minmax(300px,1fr));gap:20px;margin-bottom:30px}
        .card{background:#fff;border-radius:10px;padding:20px;box-shadow:0 5px 15px rgba(0,0,0,.08);transition:transform .2s}
        .card:hover{transform:translateY(-3px)}
        .card-header{display:flex;align-items:center;margin-bottom:12px}
        .card-icon{width:40px;height:40px;background:#f0f4ff;border-radius:10px;display:flex;align-items:center;justify-content:center;margin-right:12px;color:#4e54c8}
        .admin-section{background:#fff;border-left:4px solid #ffc107;padding:18px;border-radius:8px}
        .employee-management{margin-top:16px;background:#fff;border-radius:10px;padding:18px;box-shadow:0 5px 15px rgba(0,0,0,.06)}
        .employee-table{width:100%;border-collapse:collapse;margin-top:10px}
        .employee-table th,.employee-table td{padding:10px 12px;text-align:left;border-bottom:1px solid #eee}
        .employee-table th{background:#f8f9fa;font-weight:600}
        .role-select{padding:8px;border-radius:6px;border:1px solid #ddd;width:100%}
        .btn-primary{background:#4e54c8;color:#fff}
        .btn-primary:hover{background:#3a3fb8}
        .btn-danger{background:#dc3545;color:#fff}
        .btn-danger:hover{background:#c82333}
        .message{padding:10px;margin:10px 0;border-radius:6px;text-align:center}
        .success{background:#d4edda;color:#155724;border:1px solid #c3e6cb}
        .error{background:#f8d7da;color:#721c24;border:1px solid #f5c6cb}
        .debug-info{background:#f8f9fa;border:1px solid #dee2e6;padding:10px;margin:10px 0;border-radius:6px;font-family:monospace;font-size:12px}
        form.inline{margin:0}
        .stack-sm{display:flex;gap:8px;flex-wrap:wrap}
        .stack-sm input{padding:8px;border:1px solid #ddd;border-radius:6px}
    </style>
</head>
<body>
<div class="dashboard-container">

    <!-- Header -->
    <portlet:actionURL var="logoutURL" name="logout" />
    <div class="dashboard-header">
        <div class="user-info">
            <div class="user-avatar"><i class="fas fa-user-md"></i></div>
            <div>
                <h2 style="margin:0;">
                    Bonjour, ${empty userEmail ? 'Utilisateur' : userEmail}!
                </h2>
                <p style="opacity:.9;margin:.25rem 0 0 0;">
                    Rôle: ${empty userRole ? '-' : userRole}
                </p>

            </div>
        </div>
        <a href="${logoutURL}" class="btn"><i class="fas fa-sign-out-alt"></i> Se déconnecter</a>
    </div>

    <!-- Resolve section -->
    <c:set var="currentSection" value="${empty requestScope.section ? param.section : requestScope.section}" />
    <c:if test="${empty currentSection}">
        <c:set var="currentSection" value="overview"/>
    </c:if>

    <!-- Tabs -->
    <portlet:renderURL var="tabOverview">
        <portlet:param name="mvcPath" value="/Super Admin/dashboard.jsp"/>
        <portlet:param name="section" value="overview"/>
    </portlet:renderURL>
    <portlet:renderURL var="tabAdmins">
        <portlet:param name="mvcPath" value="/Super Admin/dashboard.jsp"/>
        <portlet:param name="section" value="admins"/>
    </portlet:renderURL>
    <portlet:renderURL var="tabUsers">
        <portlet:param name="mvcPath" value="/Super Admin/dashboard.jsp"/>
        <portlet:param name="section" value="users"/>
    </portlet:renderURL>
    <portlet:renderURL var="tabLogs">
        <portlet:param name="mvcPath" value="/Super Admin/dashboard.jsp"/>
        <portlet:param name="section" value="logs"/>
    </portlet:renderURL>
    <portlet:renderURL var="tabStats">
        <portlet:param name="mvcPath" value="/Super Admin/dashboard.jsp"/>
        <portlet:param name="section" value="stats"/>
    </portlet:renderURL>
    <portlet:renderURL var="tabSecurity">
        <portlet:param name="mvcPath" value="/Super Admin/dashboard.jsp"/>
        <portlet:param name="section" value="security"/>
    </portlet:renderURL>

    <div class="tabs">
        <a class="tab ${currentSection=='overview' ? 'active' : ''}" href="${tabOverview}">Aperçu</a>
        <a class="tab ${currentSection=='admins'   ? 'active' : ''}" href="${tabAdmins}">Admins</a>
        <a class="tab ${currentSection=='users'    ? 'active' : ''}" href="${tabUsers}">Employés</a>
        <a class="tab ${currentSection=='logs'     ? 'active' : ''}" href="${tabLogs}">Logs</a>
        <a class="tab ${currentSection=='stats'    ? 'active' : ''}" href="${tabStats}">Statistiques</a>
        <a class="tab ${currentSection=='security' ? 'active' : ''}" href="${tabSecurity}">Sécurité</a>
    </div>

    <!-- Flash messages -->
    <c:if test="${not empty param.successMsg}">
        <div class="message success">${fn:escapeXml(param.successMsg)}</div>
    </c:if>
    <c:if test="${not empty param.errorMsg}">
        <div class="message error">${fn:escapeXml(param.errorMsg)}</div>
    </c:if>

    <!-- Debug -->
    <div class="debug-info">
        <b>Debug:</b>
        section=${currentSection}
        · user=${empty userEmail ? '-' : userEmail}
        · role=${empty userRole ? '-' : userRole}
        · employees=${employeesTotal}
    </div>


    <c:choose>

        <%-- Overview --%>
        <c:when test="${currentSection == 'overview'}">
            <div class="dashboard-cards">
                <div class="card">
                    <div class="card-header">
                        <div class="card-icon"><i class="fas fa-pills"></i></div>
                        <h3 style="margin:0;">Gestion des Médicaments</h3>
                    </div>
                    <p>Consulter et gérer votre inventaire de médicaments.</p>
                </div>

                <div class="card">
                    <div class="card-header">
                        <div class="card-icon"><i class="fas fa-prescription"></i></div>
                        <h3 style="margin:0;">Ordonnances</h3>
                    </div>
                    <p>Gérer les ordonnances et les commandes des clients.</p>
                </div>

                <div class="card">
                    <div class="card-header">
                        <div class="card-icon"><i class="fas fa-users"></i></div>
                        <h3 style="margin:0;">Clients</h3>
                    </div>
                    <p>Consulter et gérer les informations des clients.</p>
                </div>

                <div class="card">
                    <div class="card-header">
                        <div class="card-icon"><i class="fas fa-chart-line"></i></div>
                        <h3 style="margin:0;">Rapports</h3>
                    </div>
                    <p>Générer des rapports et analyses de ventes.</p>
                </div>

                <div class="card">
                    <div class="card-header">
                        <div class="card-icon"><i class="fas fa-clipboard-list"></i></div>
                        <h3 style="margin:0;">Logs</h3>
                    </div>
                    <p>Consulter l’historique des connexions et actions.</p>
                    <a href="${tabLogs}" class="btn btn-primary">Voir Logs</a>
                </div>

                <div class="card">
                    <div class="card-header">
                        <div class="card-icon"><i class="fas fa-chart-pie"></i></div>
                        <h3 style="margin:0;">Statistiques</h3>
                    </div>
                    <p>Visualiser les statistiques globales de l’application.</p>
                    <a href="${tabStats}" class="btn btn-primary">Voir Statistiques</a>
                </div>

                <div class="card">
                    <div class="card-header">
                        <div class="card-icon"><i class="fas fa-lock"></i></div>
                        <h3 style="margin:0;">Sécurité</h3>
                    </div>
                    <p>Gérer les paramètres de sécurité et les autorisations.</p>
                    <a href="${tabSecurity}" class="btn btn-primary">Paramètres Sécurité</a>
                </div>
            </div>
        </c:when>

        <%-- Admins management --%>
        <c:when test="${currentSection == 'admins'}">
            <c:if test="${userRole == 'SUPER_ADMIN' || userRole == 'ADMIN'}">
                <div class="admin-section">
                    <h3 style="margin-top:0;"><i class="fas fa-shield-alt"></i> Panneau d'Administration</h3>

                    <div class="employee-management">
                        <h4><i class="fas fa-user-shield"></i> Liste des Admins</h4>
                        <c:if test="${not empty employees}">
                            <table class="employee-table">
                                <thead>
                                <tr>
                                    <th>Nom</th><th>Email</th><th>Rôle</th><th>Date Création</th><th>Dernière Connexion</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="employee" items="${employees}">
                                    <c:if test="${employee.role == 'ADMIN'}">
                                        <tr>
                                            <td>${employee.prenom} ${employee.nom}</td>
                                            <td>${employee.email}</td>
                                            <td>${employee.role}</td>
                                            <td><fmt:formatDate value="${employee.dateCreation}" pattern="dd/MM/yyyy HH:mm"/></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty employee.lastLogin}">
                                                        <fmt:formatDate value="${employee.lastLogin}" pattern="dd/MM/yyyy HH:mm"/>
                                                    </c:when>
                                                    <c:otherwise>-</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                    </div>

                    <div class="employee-management">
                        <h4><i class="fas fa-users"></i> Liste des Employés</h4>
                        <c:if test="${not empty employees}">
                            <table class="employee-table">
                                <thead>
                                <tr>
                                    <th>Nom</th><th>Email</th><th>Rôle</th><th>Date Création</th><th>Dernière Connexion</th><th>Nouveau Rôle</th><th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="employee" items="${employees}">
                                    <c:if test="${employee.role != 'ADMIN'}">
                                        <tr>
                                            <td>${employee.prenom} ${employee.nom}</td>
                                            <td>${employee.email}</td>
                                            <td>${employee.role}</td>
                                            <td><fmt:formatDate value="${employee.dateCreation}" pattern="dd/MM/yyyy HH:mm"/></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty employee.lastLogin}">
                                                        <fmt:formatDate value="${employee.lastLogin}" pattern="dd/MM/yyyy HH:mm"/>
                                                    </c:when>
                                                    <c:otherwise>-</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <portlet:actionURL name="switchRole" var="switchRoleURL"/>
                                                <form class="inline" method="post" action="${switchRoleURL}">
                                                    <input type="hidden" name="<portlet:namespace/>targetUserEmail" value="${employee.email}"/>
                                                    <select name="<portlet:namespace/>newRole" class="role-select">
                                                        <option value="PHARMACIEN" ${employee.role=='PHARMACIEN' ? 'selected' : ''}>Pharmacien</option>
                                                        <option value="FOURNISSEUR" ${employee.role=='FOURNISSEUR' ? 'selected' : ''}>Fournisseur</option>
                                                        <c:if test="${userRole == 'SUPER_ADMIN'}">
                                                            <option value="ADMIN" ${employee.role=='ADMIN' ? 'selected' : ''}>Admin</option>
                                                        </c:if>
                                                    </select>
                                                    <button type="submit" class="btn btn-primary" style="width:100%;margin-top:6px;">Mettre à jour</button>
                                                </form>
                                            </td>
                                            <td>
                                                <portlet:actionURL name="deleteAdmin" var="deleteAdminURL">
                                                    <portlet:param name="email" value="${employee.email}" />
                                                </portlet:actionURL>
                                                <form method="post" action="${deleteAdminURL}" style="margin:0;">
                                                    <button type="submit" class="btn btn-danger"
                                                            onclick="return confirm('Supprimer ${employee.email}?')">
                                                        Supprimer
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                        <c:if test="${empty employees}"><p>Aucun employé à afficher.</p></c:if>
                    </div>

                    <div class="employee-management">
                        <h4><i class="fas fa-user-plus"></i> Ajouter un Admin</h4>
                        <portlet:actionURL name="addAdmin" var="addAdminURL"/>
                        <form method="post" action="${addAdminURL}" class="stack-sm">
                            <input type="text"  name="<portlet:namespace/>nom"          placeholder="Nom" required />
                            <input type="text"  name="<portlet:namespace/>prenom"       placeholder="Prénom" required />
                            <input type="email" name="<portlet:namespace/>email"        placeholder="Email" required />
                            <input type="password" name="<portlet:namespace/>motDePasse" placeholder="Mot de passe" required />
                            <button type="submit" class="btn btn-primary"><i class="fas fa-user-plus"></i> Ajouter Admin</button>
                        </form>
                    </div>

                    <div class="employee-management">
                        <h4><i class="fas fa-user-times"></i> Supprimer un Administrateur</h4>
                        <portlet:actionURL name="deleteAdmin" var="deleteAdminDirectURL"/>
                        <form method="post" action="${deleteAdminDirectURL}" class="stack-sm">
                            <input type="email" name="<portlet:namespace/>email" placeholder="Email Admin à supprimer" required />
                            <button type="submit" class="btn btn-danger"><i class="fas fa-user-times"></i> Supprimer Admin</button>
                        </form>
                    </div>
                </div>
            </c:if>
        </c:when>

        <%-- Users (non-admins) --%>
        <c:when test="${currentSection == 'users'}">
            <div class="admin-section">
                <h3 style="margin-top:0;"><i class="fas fa-users"></i> Employés</h3>
                <c:if test="${not empty employees}">
                    <table class="employee-table">
                        <thead>
                        <tr>
                            <th>Nom</th><th>Email</th><th>Rôle</th><th>Date Création</th><th>Dernière Connexion</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="employee" items="${employees}">
                            <c:if test="${employee.role != 'ADMIN'}">
                                <tr>
                                    <td>${employee.prenom} ${employee.nom}</td>
                                    <td>${employee.email}</td>
                                    <td>${employee.role}</td>
                                    <td><fmt:formatDate value="${employee.dateCreation}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty employee.lastLogin}">
                                                <fmt:formatDate value="${employee.lastLogin}" pattern="dd/MM/yyyy HH:mm"/>
                                            </c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty employees}"><p>Aucun employé à afficher.</p></c:if>
            </div>
        </c:when>

        <%-- Logs --%>
        <c:when test="${currentSection == 'logs'}">
            <liferay-util:include page="/Super Admin/logs.jsp" servletContext="<%= application %>" />
        </c:when>

        <%-- Stats --%>
        <c:when test="${currentSection == 'stats'}">
            <liferay-util:include page="/Super Admin/stats.jsp" servletContext="<%= application %>" />
        </c:when>

        <%-- Security --%>
        <c:when test="${currentSection == 'security'}">
            <liferay-util:include page="/Super Admin/security.jsp" servletContext="<%= application %>" />
        </c:when>

        <%-- Fallback --%>
        <c:otherwise>
            <p>Section inconnue.</p>
        </c:otherwise>

    </c:choose>

</div>
</body>
</html>
