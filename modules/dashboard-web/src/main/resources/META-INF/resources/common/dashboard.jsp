<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>



<portlet:defineObjects />

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Tableau de Bord - PharmaCare</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        :root{
            --primary:#1E3A8A;   /* Deep blue */
            --secondary:#3B82F6; /* Light blue */
            --accent:#10B981;    /* Green */
            --bg:#F3F4F6;        /* Light gray background */
            --white:#FFFFFF;
            --text:#111827;      /* Slate-900 */
            --muted:#6B7280;     /* Slate-500 */
            --border:#E5E7EB;    /* Slate-200 */
            --tab-bg:#EFF6FF;    /* very light blue */
            --icon-bg:#E0F2FE;   /* light blue tint */
        }

        body{background:var(--bg);margin:0;color:var(--text)}
        .dashboard-container{max-width:1200px;margin:0 auto;padding:20px;font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif}

        /* Header */
        .dashboard-header{
            background:linear-gradient(135deg,var(--primary),var(--secondary));
            color:#fff;padding:20px;border-radius:14px;margin-bottom:20px;
            display:flex;justify-content:space-between;align-items:center;box-shadow:0 10px 24px rgba(30,58,138,.25)
        }
        .user-info{display:flex;align-items:center;gap:15px}
        .user-avatar{
            width:60px;height:60px;border-radius:14px;
            background:rgba(255,255,255,.15);display:flex;align-items:center;justify-content:center;font-size:24px
        }

        /* Buttons */
        .btn{
            background:var(--white);color:var(--primary);border:1px solid rgba(255,255,255,.0);
            padding:10px 16px;border-radius:10px;cursor:pointer;font-weight:600;text-decoration:none;display:inline-block;transition:all .2s
        }
        .btn:hover{background:#F8FAFC;transform:translateY(-1px)}
        .btn-primary{background:var(--accent);color:#fff;border:1px solid var(--accent)}
        .btn-primary:hover{filter:brightness(.95);color:var(--primary)}
        .btn-danger{background:#DC2626;color:#fff;border:1px solid #DC2626}
        .btn-danger:hover{filter:brightness(.95)}

        /* Tabs */
        .tabs{display:flex;gap:10px;margin:14px 0 22px 0;flex-wrap:wrap}
        .tab{
            background:var(--tab-bg);color:var(--primary);padding:8px 12px;border-radius:10px;text-decoration:none;font-weight:600;
            border:1px solid var(--border)
        }
        .tab:hover{background:#DBEAFE}
        .tab.active{background:var(--primary);color:#fff;border-color:var(--primary)}

        /* Cards */
        .dashboard-cards{
            display:grid;grid-template-columns:repeat(auto-fill,minmax(300px,1fr));
            gap:20px;margin-bottom:30px
        }
        .card{
            background:var(--white);border-radius:12px;padding:20px;border:1px solid var(--border);
            box-shadow:0 10px 24px rgba(17,24,39,.06);transition:transform .2s
        }
        .card:hover{transform:translateY(-3px)}
        .card-header{display:flex;align-items:center;margin-bottom:12px}
        .card-icon{
            width:44px;height:44px;background:var(--icon-bg);border-radius:12px;display:flex;align-items:center;justify-content:center;margin-right:12px;color:var(--primary)
        }

        /* Admin sections */
        .admin-section{
            background:var(--white);border-left:4px solid var(--accent);padding:18px;border-radius:10px;border:1px solid var(--border)
        }
        .employee-management{
            margin-top:16px;background:var(--white);border-radius:12px;padding:18px;border:1px solid var(--border);
            box-shadow:0 6px 18px rgba(17,24,39,.05)
        }

        /* Tables */
        .employee-table{width:100%;border-collapse:collapse;margin-top:10px}
        .employee-table th,.employee-table td{padding:10px 12px;text-align:left;border-bottom:1px solid var(--border)}
        .employee-table th{background:#F8FAFC;font-weight:700;color:var(--primary)}
        .employee-table tr:hover td{background:#F9FAFB}
        .role-select{padding:8px;border-radius:8px;border:1px solid var(--border);width:100%}

        /* Messages & debug */
        .message{padding:10px;margin:10px 0;border-radius:8px;text-align:center;border:1px solid}
        .success{background:#ECFDF5;color:#065F46;border-color:#A7F3D0}
        .error{background:#FEF2F2;color:#991B1B;border-color:#FECACA}
        .debug-info{background:#F9FAFB;border:1px solid var(--border);padding:10px;margin:10px 0;border-radius:8px;font-family:monospace;font-size:12px;color:var(--muted)}

        /* Sortable headers + controls */
        th.sortable{cursor:pointer;user-select:none}
        th.sortable .arrow{font-size:12px;opacity:.6;margin-left:6px}
        .table-controls{display:flex;justify-content:space-between;gap:12px;align-items:center;margin-top:10px;margin-bottom:6px;flex-wrap:wrap}
        .table-controls input[type="search"]{flex:1;min-width:220px;padding:10px;border:1px solid var(--border);border-radius:10px;background:#fff}

        /* Pagination */
        .pager{display:flex;gap:8px;align-items:center;flex-wrap:wrap;margin-top:12px}
        .pager .btn{padding:6px 12px}
        .pager .btn.btn-primary{background:var(--secondary);border-color:var(--secondary)}
        .pager-info{color:var(--muted);font-size:12px;margin-left:auto}
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
                <h2 style="margin:0;" class="b">
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
    <portlet:renderURL var="tabOverview"><portlet:param name="mvcPath" value="/common/dashboard.jsp"/><portlet:param name="section" value="overview"/></portlet:renderURL>
    <portlet:renderURL var="tabAdmins"><portlet:param name="mvcPath" value="/common/dashboard.jsp"/><portlet:param name="section" value="admins"/></portlet:renderURL>
    <portlet:renderURL var="tabUsers"><portlet:param name="mvcPath" value="/common/dashboard.jsp"/><portlet:param name="section" value="users"/></portlet:renderURL>
    <portlet:renderURL var="tabLogs"><portlet:param name="mvcPath" value="/common/dashboard.jsp"/><portlet:param name="section" value="logs"/></portlet:renderURL>
    <portlet:renderURL var="tabStats"><portlet:param name="mvcPath" value="/common/dashboard.jsp"/><portlet:param name="section" value="stats"/></portlet:renderURL>
    <portlet:renderURL var="tabSecurity"><portlet:param name="mvcPath" value="/common/dashboard.jsp"/><portlet:param name="section" value="security"/></portlet:renderURL>
    <!-- add this alongside your other tab URLs -->
    <portlet:renderURL var="tabMedicaments">
        <portlet:param name="mvcPath" value="/common/dashboard.jsp"/>
        <portlet:param name="section" value="medicaments"/>
    </portlet:renderURL>



    <div class="tabs">
        <a class="tab ${currentSection=='overview' ? 'active' : ''}" href="${tabOverview}">Aperçu</a>
        <a class="tab ${currentSection=='admins'   ? 'active' : ''}" href="${tabAdmins}">Admins</a>
        <a class="tab ${currentSection=='users'    ? 'active' : ''}" href="${tabUsers}">Employés</a>
        <a class="tab ${currentSection=='logs'     ? 'active' : ''}" href="${tabLogs}">Logs</a>
        <a class="tab ${currentSection=='stats'    ? 'active' : ''}" href="${tabStats}">Statistiques</a>
        <a class="tab ${currentSection=='security' ? 'active' : ''}" href="${tabSecurity}">Sécurité</a>
        <a class="tab ${currentSection=='medicaments' ? 'active' : ''}" href="${tabMedicaments}">Médicaments</a>


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

    <%-- base for includes --%>
    <c:set var="fragBase" value="/common" />

    <c:choose>
        <%-- Overview --%>
        <c:when test="${currentSection == 'overview'}">
            <div class="dashboard-cards">
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-pills"></i></div><h3 style="margin:0;">Gestion des Médicaments</h3></div><p>Consulter et gérer votre inventaire de médicaments.</p><a href="${medicamentsURL}" class="btn btn-primary">Ouvrir les Médicaments</a>
                </div>
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-prescription"></i></div><h3 style="margin:0;">Ordonnances</h3></div><p>Gérer les ordonnances et les commandes des clients.</p></div>
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-users"></i></div><h3 style="margin:0;">Clients</h3></div><p>Consulter et gérer les informations des clients.</p></div>
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-chart-line"></i></div><h3 style="margin:0;">Rapports</h3></div><p>Générer des rapports et analyses de ventes.</p></div>
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-clipboard-list"></i></div><h3 style="margin:0;">Logs</h3></div><p>Consulter l’historique des connexions et actions.</p><a href="${tabLogs}" class="btn btn-primary">Voir Logs</a></div>
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-chart-pie"></i></div><h3 style="margin:0;">Statistiques</h3></div><p>Visualiser les statistiques globales de l’application.</p><a href="${tabStats}" class="btn btn-primary">Voir Statistiques</a></div>
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-lock"></i></div><h3 style="margin:0;">Sécurité</h3></div><p>Gérer les paramètres de sécurité et les autorisations.</p><a href="${tabSecurity}" class="btn btn-primary">Paramètres Sécurité</a></div>
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

                            <%-- SEARCH + CONTROLS --%>
                        <c:if test="${not empty employees}">
                            <div class="table-controls">
                                <input id="employeeSearch" type="search" placeholder="Rechercher par nom, email ou rôle…" />
                                <span class="pager-info" id="pagerInfo"></span>
                            </div>
                        </c:if>

                        <c:if test="${not empty employees}">
                            <table class="employee-table" id="employeesTable">
                                <thead>
                                <tr>
                                    <th class="sortable" data-sort-key="name">Nom <span class="arrow">↕</span></th>
                                    <th class="sortable" data-sort-key="email">Email <span class="arrow">↕</span></th>
                                    <th class="sortable" data-sort-key="role">Rôle <span class="arrow">↕</span></th>
                                    <th class="sortable" data-sort-key="dateCreation">Date Création <span class="arrow">↕</span></th>
                                    <th class="sortable" data-sort-key="lastLogin">Dernière Connexion <span class="arrow">↕</span></th>
                                    <th>Nouveau Rôle</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="employee" items="${employees}">
                                    <c:if test="${employee.role != 'ADMIN'}">
                                        <tr>
                                            <td data-key="name">${employee.prenom} ${employee.nom}</td>
                                            <td data-key="email">${employee.email}</td>
                                            <td data-key="role">${employee.role}</td>
                                            <td data-key="dateCreation"
                                                data-sort="${employee.dateCreation.time}">
                                                <fmt:formatDate value="${employee.dateCreation}" pattern="dd/MM/yyyy HH:mm"/>
                                            </td>
                                            <td data-key="lastLogin"
                                                data-sort="${empty employee.lastLogin ? 0 : employee.lastLogin.time}">
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
                                                <c:if test="${userRole == 'SUPER_ADMIN'}">
                                                    <portlet:actionURL name="deleteAdmin" var="deleteAdminURL">
                                                        <portlet:param name="email" value="${employee.email}" />
                                                    </portlet:actionURL>
                                                    <form method="post" action="${deleteAdminURL}" style="margin:0;">
                                                        <button type="submit" class="btn btn-danger"
                                                                onclick="return confirm('Supprimer ${employee.email}?')">
                                                            Supprimer
                                                        </button>
                                                    </form>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                                </tbody>
                            </table>

                            <%-- PAGINATION --%>
                            <div class="pager" id="pager"></div>
                        </c:if>

                        <c:if test="${empty employees}"><p>Aucun employé à afficher.</p></c:if>
                    </div>

                    <c:if test="${userRole == 'SUPER_ADMIN'}">
                        <div class="employee-management">
                            <h4><i class="fas fa-user-plus"></i> Ajouter un Admin</h4>
                            <portlet:actionURL name="addAdmin" var="addAdminURL"/>
                            <form method="post" action="${addAdminURL}" class="stack-sm">
                                <input type="text"  name="<portlet:namespace/>nom"         placeholder="Nom" required />
                                <input type="text"  name="<portlet:namespace/>prenom"      placeholder="Prénom" required />
                                <input type="email" name="<portlet:namespace/>email"       placeholder="Email" required />
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
                    </c:if>
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
            <c:catch var="incErr">
                <liferay-util:include page="${fragBase}/logs.jsp" servletContext="<%= application %>" />
            </c:catch>
            <c:if test="${not empty incErr}">
                <div class="message error">Impossible d’inclure ${fragBase}/logs.jsp. Vérifiez le chemin et les erreurs de compilation.</div>
                <pre class="debug-info">${fn:escapeXml(incErr)}</pre>
            </c:if>
        </c:when>

        <%-- Stats --%>
        <c:when test="${currentSection == 'stats'}">
            <c:catch var="incErr">
                <liferay-util:include page="${fragBase}/stats.jsp" servletContext="<%= application %>" />
            </c:catch>
            <c:if test="${not empty incErr}">
                <div class="message error">Impossible d’inclure ${fragBase}/stats.jsp. Vérifiez le chemin et les erreurs de compilation.</div>
                <pre class="debug-info">${fn:escapeXml(incErr)}</pre>
            </c:if>
        </c:when>

        <%-- Security --%>
        <c:when test="${currentSection == 'security'}">
            <c:catch var="incErr">
                <liferay-util:include page="${fragBase}/security.jsp" servletContext="<%= application %>" />
            </c:catch>
            <c:if test="${not empty incErr}">
                <div class="message error">Impossible d’inclure ${fragBase}/security.jsp. Vérifiez le chemin et les erreurs de compilation.</div>
                <pre class="debug-info">${fn:escapeXml(incErr)}</pre>
            </c:if>
        </c:when>
        <c:when test="${currentSection == 'medicaments'}">
            <div class="admin-section">
                <h3 style="margin-top:0;"><i class="fas fa-pills"></i> Médicaments</h3>

                <!-- DO NOT pass mvcPath=/list.jsp here -->
                <liferay-portlet:runtime
                        portletName="medicament_web_MedicamentWebPortlet"
                        instanceId="MEDS" />
            </div>
        </c:when>



        <%-- Fallback --%>
        <c:otherwise>
            <p>Section inconnue.</p>
        </c:otherwise>

    </c:choose>

</div>

<script>
    (function(){
        // Elements
        var searchInput = document.getElementById('employeeSearch');
        var table = document.getElementById('employeesTable');
        if(!table){ return; } // not on admins section or no employees
        var tbody = table.querySelector('tbody');
        var allRows = Array.prototype.slice.call(tbody.querySelectorAll('tr'));
        var pager = document.getElementById('pager');
        var pagerInfo = document.getElementById('pagerInfo');

        var pageSize = 5;
        var currentPage = 1;
        var currentSort = { key: null, dir: 1 };
        var filtered = allRows.slice();

        function text(el){ return (el.textContent || '').trim(); }
        function num(v){ v = parseInt(v,10); return isNaN(v) ? 0 : v; }

        function getCell(row, key){
            return row.querySelector('td[data-key="'+key+'"]');
        }
        function getVal(row, key){
            switch(key){
                case 'name': return text(getCell(row,'name')).toLowerCase();
                case 'email': return text(getCell(row,'email')).toLowerCase();
                case 'role': return text(getCell(row,'role')).toLowerCase();
                case 'dateCreation': return num(getCell(row,'dateCreation').getAttribute('data-sort'));
                case 'lastLogin': return num(getCell(row,'lastLogin').getAttribute('data-sort'));
                default: return '';
            }
        }

        function applyFilter(){
            var q = (searchInput && searchInput.value ? searchInput.value : '').trim().toLowerCase();
            filtered = allRows.filter(function(row){
                var name = getVal(row,'name'), email = getVal(row,'email'), role = getVal(row,'role');
                return !q || name.indexOf(q) > -1 || email.indexOf(q) > -1 || role.indexOf(q) > -1;
            });
            currentPage = 1;
            applySort();
        }

        function applySort(){
            if(currentSort.key){
                filtered.sort(function(a,b){
                    var va = getVal(a, currentSort.key), vb = getVal(b, currentSort.key);
                    if(va < vb) return -1 * currentSort.dir;
                    if(va > vb) return  1 * currentSort.dir;
                    return 0;
                });
            }
            renderPage();
        }

        function renderPage(){
            tbody.innerHTML = '';
            var total = filtered.length;
            var totalPages = Math.max(1, Math.ceil(total / pageSize));
            if(currentPage > totalPages) currentPage = totalPages;
            var start = (currentPage - 1) * pageSize;
            var end = start + pageSize;
            filtered.slice(start, end).forEach(function(r){ tbody.appendChild(r); });

            // pager buttons
            pager.innerHTML = '';
            if(totalPages > 1){
                var prev = document.createElement('button');
                prev.textContent = 'Précédent';
                prev.className = 'btn';
                prev.disabled = currentPage === 1;
                prev.addEventListener('click', function(){ currentPage--; renderPage(); });
                pager.appendChild(prev);

                for(var p=1; p<=totalPages; p++){
                    var btn = document.createElement('button');
                    btn.textContent = p;
                    btn.className = 'btn' + (p===currentPage ? ' btn-primary' : '');
                    (function(page){ btn.addEventListener('click', function(){ currentPage = page; renderPage(); }); })(p);
                    pager.appendChild(btn);
                }

                var next = document.createElement('button');
                next.textContent = 'Suivant';
                next.className = 'btn';
                next.disabled = currentPage === totalPages;
                next.addEventListener('click', function(){ currentPage++; renderPage(); });
                pager.appendChild(next);
            }

            if(pagerInfo){
                var from = total === 0 ? 0 : start + 1;
                var to = Math.min(end, total);
                pagerInfo.textContent = 'Affichage ' + from + '–' + to + ' sur ' + total;
            }
        }

        // sorting header clicks
        Array.prototype.slice.call(table.querySelectorAll('thead th.sortable')).forEach(function(th){
            th.addEventListener('click', function(){
                var key = th.getAttribute('data-sort-key');
                if(currentSort.key === key){
                    currentSort.dir *= -1;
                }else{
                    currentSort.key = key;
                    currentSort.dir = 1;
                }
                // update arrow indicators
                Array.prototype.slice.call(table.querySelectorAll('thead th.sortable .arrow')).forEach(function(a){ a.textContent = '↕'; });
                var arrow = th.querySelector('.arrow');
                if(arrow){ arrow.textContent = currentSort.dir === 1 ? '↑' : '↓'; }
                applySort();
            });
        });

        if(searchInput){ searchInput.addEventListener('input', applyFilter); }

        // init
        applyFilter();
    })();
</script>
</body>
</html>
