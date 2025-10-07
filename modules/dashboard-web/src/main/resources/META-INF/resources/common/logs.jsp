<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%@ page import="java.util.*" %>
<%@ page import="gestion_de_pharmacie.model.Utilisateur" %>
<%@ page import="gestion_de_pharmacie.service.UtilisateurLocalServiceUtil" %>

<%
    List<Utilisateur> users = UtilisateurLocalServiceUtil.getUtilisateurs(-1, -1);
    pageContext.setAttribute("users", users);

    String NS = renderResponse.getNamespace();

    // Calculate stats
    int totalUsers = users.size();
    long activeUsers = users.stream().filter(u -> u.getLastLogin() != null).count();
    long neverLoggedIn = totalUsers - activeUsers;

    // Count by role
    Map<String, Long> roleCount = new HashMap<>();
    for (Utilisateur u : users) {
        String role = u.getRole() != null ? u.getRole() : "INCONNU";
        roleCount.put(role, roleCount.getOrDefault(role, 0L) + 1);
    }

    pageContext.setAttribute("totalUsers", totalUsers);
    pageContext.setAttribute("activeUsers", activeUsers);
    pageContext.setAttribute("neverLoggedIn", neverLoggedIn);
    pageContext.setAttribute("roleCount", roleCount);
%>

<style>
    :root{
        --primary:#2563EB; --secondary:#3B82F6; --accent:#10B981;
        --danger:#EF4444; --warning:#F59E0B;
        --bg:#F8FAFC; --white:#fff; --text:#0F172A; --muted:#64748B;
        --border:#E2E8F0; --hover:#F1F5F9;
        --gradient-primary:linear-gradient(135deg, #1E40AF 0%, #3B82F6 100%);
    }

    .logs-container{
        font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,sans-serif;
        color:var(--text);
        padding:20px;
        background:var(--bg);
        border-radius:16px;
    }

    .logs-header{
        background:var(--gradient-primary);
        border-radius:16px;
        padding:24px 28px;
        margin-bottom:24px;
        color:var(--white);
        box-shadow:0 8px 24px rgba(37,99,235,.2);
    }

    .header-content{
        display:flex;
        align-items:center;
        justify-content:space-between;
        flex-wrap:wrap;
        gap:16px;
    }

    .header-left{display:flex;align-items:center;gap:16px}
    .header-icon{
        width:48px;height:48px;border-radius:12px;
        background:rgba(255,255,255,.2);backdrop-filter:blur(8px);
        display:flex;align-items:center;justify-content:center;
        font-size:24px;
    }
    .header-title{margin:0;font-size:24px;font-weight:800;letter-spacing:-.02em}
    .header-subtitle{opacity:.9;font-size:13px;margin-top:4px}

    .stats-grid{
        display:grid;
        grid-template-columns:repeat(auto-fit,minmax(200px,1fr));
        gap:16px;
        margin-bottom:24px;
    }

    .stat-card{
        background:var(--white);
        border:1px solid var(--border);
        border-radius:14px;
        padding:20px;
        box-shadow:0 4px 20px rgba(15,23,42,.08);
        transition:all .2s ease;
        position:relative;
        overflow:hidden;
    }

    .stat-card::before{
        content:'';position:absolute;top:0;left:0;
        width:4px;height:100%;
        background:var(--gradient-primary);
        opacity:0;transition:opacity .2s;
    }

    .stat-card:hover{
        transform:translateY(-2px);
        box-shadow:0 8px 32px rgba(15,23,42,.12);
    }
    .stat-card:hover::before{opacity:1}

    .stat-label{
        color:var(--muted);
        font-size:11px;
        font-weight:600;
        letter-spacing:.06em;
        text-transform:uppercase;
        margin-bottom:8px;
    }

    .stat-value{
        font-size:28px;
        font-weight:800;
        line-height:1.2;
        letter-spacing:-.02em;
    }

    .stat-primary{color:var(--primary)}
    .stat-accent{color:var(--accent)}
    .stat-warning{color:var(--warning)}

    .toolbar{
        background:var(--white);
        border:1px solid var(--border);
        border-radius:12px;
        padding:16px;
        margin-bottom:20px;
        display:flex;
        gap:12px;
        align-items:center;
        justify-content:space-between;
        flex-wrap:wrap;
        box-shadow:0 4px 20px rgba(15,23,42,.08);
    }

    .toolbar-left{display:flex;gap:10px;align-items:center;flex:1;min-width:300px}

    .search-input,.filter-select{
        padding:10px 14px;
        border:1.5px solid var(--border);
        border-radius:10px;
        font-size:14px;
        background:var(--white);
        transition:all .2s;
    }

    .search-input{flex:1;min-width:200px}
    .search-input:focus,.filter-select:focus{
        outline:none;
        border-color:var(--secondary);
        box-shadow:0 0 0 3px rgba(59,130,246,.12);
    }

    .btn{
        padding:10px 16px;
        border:none;
        border-radius:10px;
        font-weight:700;
        font-size:14px;
        cursor:pointer;
        transition:all .2s;
        display:inline-flex;
        align-items:center;
        gap:6px;
    }

    .btn-primary{background:var(--secondary);color:var(--white)}
    .btn-primary:hover{background:var(--primary)}
    .btn-ghost{background:var(--hover);color:var(--text)}
    .btn-ghost:hover{background:var(--border)}

    .table-card{
        background:var(--white);
        border:1px solid var(--border);
        border-radius:14px;
        overflow:hidden;
        box-shadow:0 4px 20px rgba(15,23,42,.08);
    }

    .table{width:100%;border-collapse:collapse}

    .table thead{background:#F8FAFC}
    .table th{
        padding:14px 16px;
        text-align:left;
        font-size:11px;
        font-weight:700;
        letter-spacing:.06em;
        text-transform:uppercase;
        color:var(--muted);
        border-bottom:2px solid var(--border);
        cursor:pointer;
        user-select:none;
        transition:background .15s;
    }

    .table th:hover{background:#F1F5F9}
    .table th.sortable::after{
        content:'⇅';
        margin-left:6px;
        opacity:.4;
    }
    .table th.sorted-asc::after{content:'↑';opacity:1;color:var(--primary)}
    .table th.sorted-desc::after{content:'↓';opacity:1;color:var(--primary)}

    .table td{
        padding:14px 16px;
        border-bottom:1px solid var(--border);
        color:var(--text);
    }

    .table tbody tr{transition:background .15s}
    .table tbody tr:hover{background:#F8FAFC}
    .table tbody tr:last-child td{border-bottom:none}

    .role-badge{
        display:inline-block;
        padding:4px 10px;
        border-radius:999px;
        font-size:11px;
        font-weight:700;
        letter-spacing:.02em;
    }

    .role-super{background:#FEE2E2;color:#991B1B}
    .role-admin{background:#FEF3C7;color:#78350F}
    .role-pharma{background:#D1FAE5;color:#065F46}
    .role-four{background:#DBEAFE;color:#1E3A8A}

    .status-badge{
        display:inline-flex;
        align-items:center;
        gap:6px;
        padding:4px 10px;
        border-radius:999px;
        font-size:11px;
        font-weight:700;
    }

    .status-active{background:#D1FAE5;color:#065F46}
    .status-active::before{content:'●';color:#10B981}
    .status-inactive{background:#F3F4F6;color:#6B7280}
    .status-inactive::before{content:'●';color:#9CA3AF}

    .empty-state{
        text-align:center;
        padding:60px 20px;
        color:var(--muted);
    }

    .empty-state-icon{font-size:48px;margin-bottom:12px;opacity:.5}

    .pagination{
        display:flex;
        gap:8px;
        align-items:center;
        justify-content:center;
        padding:20px;
        background:var(--white);
        border-top:1px solid var(--border);
    }

    .page-btn{
        padding:8px 12px;
        border:1px solid var(--border);
        background:var(--white);
        border-radius:8px;
        cursor:pointer;
        font-size:14px;
        font-weight:600;
        transition:all .2s;
    }

    .page-btn:hover:not(:disabled){
        background:var(--hover);
        border-color:var(--secondary);
    }

    .page-btn.active{
        background:var(--secondary);
        color:var(--white);
        border-color:var(--secondary);
    }

    .page-btn:disabled{
        opacity:.4;
        cursor:not-allowed;
    }

    @media (max-width: 768px) {
        .stats-grid{grid-template-columns:1fr}
        .toolbar{flex-direction:column;align-items:stretch}
        .toolbar-left{flex-direction:column}
        .table{font-size:13px}
        .table th,.table td{padding:10px}
    }
</style>

<div class="logs-container">
    <!-- Header -->
    <div class="logs-header">
        <div class="header-content">
            <div class="header-left">
                <div class="header-icon">📋</div>
                <div>
                    <h2 class="header-title">Logs & Activité</h2>
                    <p class="header-subtitle">Suivi des connexions et activités utilisateurs</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Statistics -->
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-label">Total Utilisateurs</div>
            <div class="stat-value stat-primary">${totalUsers}</div>
        </div>
        <div class="stat-card">
            <div class="stat-label">Utilisateurs Actifs</div>
            <div class="stat-value stat-accent">${activeUsers}</div>
        </div>
        <div class="stat-card">
            <div class="stat-label">Jamais Connectés</div>
            <div class="stat-value stat-warning">${neverLoggedIn}</div>
        </div>
        <c:forEach var="entry" items="${roleCount}">
            <div class="stat-card">
                <div class="stat-label">${entry.key}</div>
                <div class="stat-value">${entry.value}</div>
            </div>
        </c:forEach>
    </div>

    <!-- Toolbar -->
    <div class="toolbar">
        <div class="toolbar-left">
            <input type="text"
                   id="<%=NS%>search"
                   class="search-input"
                   placeholder="Rechercher par email, nom ou prénom...">
            <select id="<%=NS%>roleFilter" class="filter-select">
                <option value="">Tous les rôles</option>
                <option value="SUPER_ADMIN">SUPER_ADMIN</option>
                <option value="ADMIN">ADMIN</option>
                <option value="PHARMACIEN">PHARMACIEN</option>
                <option value="FOURNISSEUR">FOURNISSEUR</option>
            </select>
            <select id="<%=NS%>statusFilter" class="filter-select">
                <option value="">Tous les statuts</option>
                <option value="active">Actifs</option>
                <option value="inactive">Jamais connectés</option>
            </select>
        </div>
        <div>
            <button id="<%=NS%>reset" class="btn btn-ghost">Réinitialiser</button>
            <button id="<%=NS%>export" class="btn btn-primary">📥 Exporter CSV</button>
        </div>
    </div>

    <!-- Table -->
    <div class="table-card">
        <table class="table" id="<%=NS%>logsTable">
            <thead>
            <tr>
                <th class="sortable" data-sort="email">Email</th>
                <th class="sortable" data-sort="nom">Nom</th>
                <th class="sortable" data-sort="prenom">Prénom</th>
                <th class="sortable" data-sort="role">Rôle</th>
                <th class="sortable" data-sort="lastLogin">Dernière connexion</th>
                <th>Statut</th>
            </tr>
            </thead>
            <tbody id="<%=NS%>tbody">
            <c:forEach var="u" items="${users}">
                <tr data-email="${fn:escapeXml(u.email)}"
                    data-nom="${fn:escapeXml(u.nom)}"
                    data-prenom="${fn:escapeXml(u.prenom)}"
                    data-role="${u.role}"
                    data-lastlogin="${not empty u.lastLogin ? u.lastLogin.time : 0}">
                    <td><strong>${fn:escapeXml(u.email)}</strong></td>
                    <td>${fn:escapeXml(u.nom)}</td>
                    <td>${fn:escapeXml(u.prenom)}</td>
                    <td>
                        <c:choose>
                            <c:when test="${u.role == 'SUPER_ADMIN'}">
                                <span class="role-badge role-super">SUPER_ADMIN</span>
                            </c:when>
                            <c:when test="${u.role == 'ADMIN'}">
                                <span class="role-badge role-admin">ADMIN</span>
                            </c:when>
                            <c:when test="${u.role == 'PHARMACIEN'}">
                                <span class="role-badge role-pharma">PHARMACIEN</span>
                            </c:when>
                            <c:otherwise>
                                <span class="role-badge role-four">FOURNISSEUR</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty u.lastLogin}">
                                <fmt:formatDate value="${u.lastLogin}" pattern="dd/MM/yyyy HH:mm"/>
                            </c:when>
                            <c:otherwise>
                                <span style="color:var(--muted)">Jamais</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty u.lastLogin}">
                                <span class="status-badge status-active">Actif</span>
                            </c:when>
                            <c:otherwise>
                                <span class="status-badge status-inactive">Inactif</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="pagination" id="<%=NS%>pagination"></div>
    </div>
</div>

<script>
    (function() {
        const ns = "<%=NS%>";
        const $ = (id) => document.getElementById(ns + id);

        const search = $('search');
        const roleFilter = $('roleFilter');
        const statusFilter = $('statusFilter');
        const resetBtn = $('reset');
        const exportBtn = $('export');
        const tbody = $('tbody');
        const pagination = $('pagination');
        const table = $('logsTable');

        let allRows = Array.from(tbody.querySelectorAll('tr'));
        let filtered = allRows.slice();
        let currentPage = 1;
        const rowsPerPage = 10;
        let currentSort = {column: 'lastLogin', direction: 'desc'};

        // Filter function
        function applyFilters() {
            const searchTerm = search.value.toLowerCase().trim();
            const role = roleFilter.value;
            const status = statusFilter.value;

            filtered = allRows.filter(row => {
                const email = (row.dataset.email || '').toLowerCase();
                const nom = (row.dataset.nom || '').toLowerCase();
                const prenom = (row.dataset.prenom || '').toLowerCase();
                const rowRole = row.dataset.role || '';
                const lastLogin = parseInt(row.dataset.lastlogin) || 0;

                const matchesSearch = !searchTerm ||
                    email.includes(searchTerm) ||
                    nom.includes(searchTerm) ||
                    prenom.includes(searchTerm);

                const matchesRole = !role || rowRole === role;

                const matchesStatus = !status ||
                    (status === 'active' && lastLogin > 0) ||
                    (status === 'inactive' && lastLogin === 0);

                return matchesSearch && matchesRole && matchesStatus;
            });

            sortRows();
            currentPage = 1;
            render();
        }

        // Sort function
        function sortRows() {
            filtered.sort((a, b) => {
                let aVal, bVal;

                if (currentSort.column === 'lastLogin') {
                    aVal = parseInt(a.dataset.lastlogin) || 0;
                    bVal = parseInt(b.dataset.lastlogin) || 0;
                } else {
                    aVal = (a.dataset[currentSort.column] || '').toLowerCase();
                    bVal = (b.dataset[currentSort.column] || '').toLowerCase();
                }

                if (aVal < bVal) return currentSort.direction === 'asc' ? -1 : 1;
                if (aVal > bVal) return currentSort.direction === 'asc' ? 1 : -1;
                return 0;
            });
        }

        // Render function
        function render() {
            tbody.innerHTML = '';

            const totalPages = Math.max(1, Math.ceil(filtered.length / rowsPerPage));
            if (currentPage > totalPages) currentPage = totalPages;

            const start = (currentPage - 1) * rowsPerPage;
            const end = start + rowsPerPage;
            const pageRows = filtered.slice(start, end);

            if (pageRows.length === 0) {
                tbody.innerHTML = `
                <tr>
                    <td colspan="6" class="empty-state">
                        <div class="empty-state-icon">🔍</div>
                        <div>Aucun résultat trouvé</div>
                    </td>
                </tr>
            `;
            } else {
                pageRows.forEach(row => tbody.appendChild(row));
            }

            renderPagination(totalPages);
        }

        // Pagination
        function renderPagination(totalPages) {
            pagination.innerHTML = '';

            const prevBtn = document.createElement('button');
            prevBtn.className = 'page-btn';
            prevBtn.textContent = '‹';
            prevBtn.disabled = currentPage === 1;
            prevBtn.onclick = () => { currentPage--; render(); };
            pagination.appendChild(prevBtn);

            const maxVisible = 5;
            let start = Math.max(1, currentPage - 2);
            let end = Math.min(totalPages, start + maxVisible - 1);

            if (end - start < maxVisible - 1) {
                start = Math.max(1, end - maxVisible + 1);
            }

            for (let i = start; i <= end; i++) {
                const btn = document.createElement('button');
                btn.className = 'page-btn' + (i === currentPage ? ' active' : '');
                btn.textContent = i;
                btn.onclick = () => { currentPage = i; render(); };
                pagination.appendChild(btn);
            }

            const nextBtn = document.createElement('button');
            nextBtn.className = 'page-btn';
            nextBtn.textContent = '›';
            nextBtn.disabled = currentPage === totalPages;
            nextBtn.onclick = () => { currentPage++; render(); };
            pagination.appendChild(nextBtn);
        }

        // Event listeners
        let searchTimeout;
        search.addEventListener('input', () => {
            clearTimeout(searchTimeout);
            searchTimeout = setTimeout(applyFilters, 300);
        });

        roleFilter.addEventListener('change', applyFilters);
        statusFilter.addEventListener('change', applyFilters);

        resetBtn.addEventListener('click', () => {
            search.value = '';
            roleFilter.value = '';
            statusFilter.value = '';
            applyFilters();
        });

        // Sort on column click
        table.querySelectorAll('th.sortable').forEach(th => {
            th.addEventListener('click', () => {
                const column = th.dataset.sort;

                if (currentSort.column === column) {
                    currentSort.direction = currentSort.direction === 'asc' ? 'desc' : 'asc';
                } else {
                    currentSort.column = column;
                    currentSort.direction = 'asc';
                }

                table.querySelectorAll('th').forEach(h => {
                    h.classList.remove('sorted-asc', 'sorted-desc');
                });
                th.classList.add('sorted-' + currentSort.direction);

                sortRows();
                render();
            });
        });

        // Export to CSV
        exportBtn.addEventListener('click', () => {
            let csv = 'Email,Nom,Prénom,Rôle,Dernière connexion,Statut\n';

            filtered.forEach(row => {
                const cells = row.querySelectorAll('td');
                const data = [
                    row.dataset.email,
                    row.dataset.nom,
                    row.dataset.prenom,
                    row.dataset.role,
                    cells[4].textContent.trim(),
                    cells[5].textContent.trim()
                ];
                csv += data.map(d => '"' + String(d).replace(/"/g, '""') + '"').join(',') + '\n';
            });

            const blob = new Blob([csv], {type: 'text/csv;charset=utf-8;'});
            const url = URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'logs_' + new Date().toISOString().slice(0,10) + '.csv';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            URL.revokeObjectURL(url);
        });

        // Initial render with default sort
        table.querySelector('th[data-sort="lastLogin"]').classList.add('sorted-desc');
        applyFilters();
    })();
</script>