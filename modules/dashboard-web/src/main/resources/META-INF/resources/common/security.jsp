<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%@ page import="java.util.*" %>
<%@ page import="gestion_de_pharmacie.model.*" %>
<%@ page import="gestion_de_pharmacie.service.*" %>

<%
    List<Utilisateur> users = UtilisateurLocalServiceUtil.getUtilisateurs(-1, -1);
    pageContext.setAttribute("users", users);

    // Stable id namespace for DOM elements
    String NS = renderResponse.getNamespace();
%>

<style>
    :root{
        --primary:#2563EB; --secondary:#3B82F6; --accent:#10B981;
        --bg:#F8FAFC; --white:#fff; --text:#0F172A; --muted:#64748B;
        --border:#E2E8F0; --hover:#F1F5F9; --danger:#EF4444;
    }
    .roles-container{font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,sans-serif;color:var(--text)}
    .roles-header{background:linear-gradient(135deg,#1E40AF,#3B82F6);border-radius:16px;padding:24px 28px;margin-bottom:16px;color:#fff}
    .header-row{display:flex;justify-content:space-between;gap:16px;align-items:center;flex-wrap:wrap}
    .header-left{display:flex;gap:12px;align-items:center}
    .header-icon{width:44px;height:44px;border-radius:10px;background:rgba(255,255,255,.18);display:flex;align-items:center;justify-content:center;font-size:22px}
    .stats{display:flex;gap:12px}
    .stat{background:rgba(255,255,255,.16);border-radius:10px;padding:10px 16px;text-align:center}
    .stat .n{font-size:22px;font-weight:800;line-height:1}
    .stat .l{font-size:11px;opacity:.95;letter-spacing:.06em;text-transform:uppercase}

    .toolbar{background:#fff;border:1px solid var(--border);padding:12px;border-radius:12px;display:flex;gap:10px;align-items:center;justify-content:space-between;margin-bottom:12px;flex-wrap:wrap}
    .t-left{display:flex;gap:8px;align-items:center;flex:1;min-width:260px}
    .input, .select{padding:10px 12px;border:1.5px solid var(--border);border-radius:10px;background:#fff;font-size:14px}
    .input:focus, .select:focus{outline:none;border-color:var(--secondary);box-shadow:0 0 0 3px rgba(59,130,246,.12)}
    .btn{padding:9px 14px;border:none;border-radius:10px;font-weight:700;cursor:pointer;display:inline-flex;align-items:center;gap:6px}
    .btn-primary{background:var(--secondary);color:#fff}
    .btn-ghost{background:var(--hover)}
    .btn:disabled{opacity:.5;cursor:not-allowed}

    .bulk{display:none;align-items:center;justify-content:space-between;background:linear-gradient(135deg,#6366F1,#8B5CF6);color:#fff;border-radius:10px;padding:10px 14px;margin-bottom:12px}
    .bulk.active{display:flex}
    .bulk .count{background:rgba(255,255,255,.2);padding:4px 10px;border-radius:999px;font-weight:800}

    .card{background:#fff;border:1px solid var(--border);border-radius:12px;overflow:hidden}
    table{width:100%;border-collapse:collapse;table-layout:fixed}
    thead th{background:#F8FAFC;border-bottom:2px solid var(--border);text-align:left;padding:12px;font-size:12px;text-transform:uppercase;letter-spacing:.06em}
    tbody td{padding:12px;border-bottom:1px solid var(--border);vertical-align:middle}
    tr:hover{background:var(--hover)}
    .sel{width:46px;text-align:center}
    .role-tag{display:inline-block;padding:6px 10px;border-radius:999px;font-weight:700;font-size:12px}
    .role-super{background:#FEE2E2;color:#991B1B}
    .role-admin{background:#FEF3C7;color:#78350F}
    .role-pharma{background:#D1FAE5;color:#065F46}
    .role-four{background:#DBEAFE;color:#1E3A8A}
    .avatar{width:36px;height:36px;border-radius:10px;background:linear-gradient(135deg,var(--secondary),var(--accent));color:#fff;display:inline-flex;align-items:center;justify-content:center;font-weight:800}

    .pager{display:flex;gap:6px;align-items:center;justify-content:center;padding:12px}
    .pbtn{padding:6px 10px;border:1px solid var(--border);background:#fff;border-radius:8px;cursor:pointer}
    .pbtn.active{background:var(--secondary);color:#fff;border-color:var(--secondary)}
    .action-form {
        display: flex;
        gap: 8px;
        align-items: center;
        margin: 0;
    }

    .role-select {
        padding: 6px 10px;
        border: 1.5px solid var(--border);
        border-radius: 8px;
        background: #fff;
        font-size: 13px;
        min-width: 140px;
    }

    .role-select:focus {
        outline: none;
        border-color: var(--secondary);
        box-shadow: 0 0 0 3px rgba(59,130,246,.12);
    }

    .btn-sm {
        padding: 6px 12px;
        font-size: 13px;
    }

    .btn-secondary {
        background: var(--hover);
        color: var(--muted);
    }

    .col-actions {
        min-width: 220px;
    }
</style>
<style>
    /* Bulk role selector: keep the closed select white on purple,
       but force dropdown options to be black on white */
    #<%=NS%>bulkRole{
        color:#fff; /* text when closed */
        background:rgba(255,255,255,.12);
        border-color:rgba(255,255,255,.35);
    }
    #<%=NS%>bulkRole option{
        color:#0F172A !important;   /* black/dark text in the dropdown */
        background:#fff !important; /* white background for options */
    }
    /* ===== Modal polish ===== */
    .modal-backdrop{position:fixed;inset:0;background:rgba(15,23,42,.45);display:none;align-items:center;justify-content:center;z-index:9999}
    .modal-backdrop.show{display:flex}

    .modal-card{
        background:#fff;border:1px solid var(--border);border-radius:14px;
        width:min(760px, 94vw); /* responsive max width */
        padding:18px; box-shadow:0 20px 60px rgba(2,6,23,.25);
    }
    .modal-head{
        display:flex; align-items:center; justify-content:space-between; gap:12px; margin-bottom:12px;
    }
    .modal-title{font-weight:800;font-size:18px;color:#0f172a}
    .modal-close.btn{background:transparent;border:none;color:#1f2937;font-weight:700}

    .modal-form{
        display:grid; gap:12px;
        grid-template-columns: 1fr 1fr; /* two columns on desktop */
    }
    @media (max-width:680px){
        .modal-form{ grid-template-columns: 1fr; }
    }
    .modal-form .field{display:flex; flex-direction:column; gap:6px}
    .modal-form label{font-size:12px; color:var(--muted); letter-spacing:.02em}
    .modal-form input{
        border:1.5px solid var(--border); border-radius:10px; padding:10px 12px; font-size:14px;
    }
    .modal-actions{ display:flex; justify-content:flex-end; margin-top:6px }

    /* make email span full width */
    .modal-form .field--full{ grid-column:1 / -1; }

    /* match button sizing inside the modal to table buttons */
    .btn-primary{min-height:40px}

    /* Optional: soften the background blur feel */
    body.modal-open{overflow:hidden}
    /* ===== Actions column tidy ===== */
    .col-actions .actions{
        display:flex; flex-wrap:wrap; gap:8px; align-items:center;
    }
    .col-actions .btn{height:34px; line-height:1}
    .col-actions .btn-ghost{border:1px solid var(--border)}

</style>

<div class="roles-container">
    <portlet:actionURL name="updateProfile" var="updateProfileURL" />

    <!-- Header -->
    <div class="roles-header">
        <div class="header-row">
            <div class="header-left">
                <div class="header-icon">🔐</div>
                <div>
                    <div style="font-size:20px;font-weight:800;margin-bottom:2px">Gestion des Rôles</div>
                    <div style="opacity:.9;font-size:13px">Gérez les permissions et les accès</div>
                </div>
            </div>
            <div class="stats">
                <div class="stat"><div class="n" id="<%=NS%>total">0</div><div class="l">Total</div></div>
                <div class="stat"><div class="n" id="<%=NS%>admins">0</div><div class="l">Admins</div></div>
                <div class="stat"><div class="n" id="<%=NS%>selected">0</div><div class="l">Sélectionnés</div></div>
            </div>
        </div>
    </div>

    <c:set var="currentUserRole" value="${empty requestScope.userRole ? sessionScope.USER_ROLE : requestScope.userRole}" />
    <c:set var="isSuperAdmin" value="${currentUserRole == 'SUPER_ADMIN'}" />
    <c:set var="isAdmin" value="${currentUserRole == 'ADMIN'}" />

    <!-- Toolbar -->
    <div class="toolbar">
        <div class="t-left">
            <input id="<%=NS%>q" class="input" placeholder="Rechercher (email, nom, prénom)" />
            <select id="<%=NS%>roleFilter" class="select">
                <option value="">Tous les rôles</option>
                <option value="SUPER_ADMIN">SUPER_ADMIN</option>
                <option value="ADMIN">ADMIN</option>
                <option value="PHARMACIEN">PHARMACIEN</option>
                <option value="FOURNISSEUR">FOURNISSEUR</option>
            </select>
            <select id="<%=NS%>pageSize" class="select">
                <option value="5">5 / page</option>
                <option value="10" selected>10 / page</option>
                <option value="20">20 / page</option>
                <option value="50">50 / page</option>
            </select>
            <button type="button" id="<%=NS%>reset" class="btn btn-ghost">Réinitialiser</button>
        </div>
        <button type="button" id="<%=NS%>export" class="btn btn-ghost">📥 Exporter</button>
    </div>

    <!-- Bulk bar -->
    <portlet:actionURL name="bulkChangeRoles" var="bulkURL" />
    <div class="bulk" id="<%=NS%>bulk">
        <div><span class="count" id="<%=NS%>bulkCount">0</span> utilisateur(s) sélectionné(s)</div>
        <form id="<%=NS%>bulkForm" action="${bulkURL}" method="post" style="display:flex;gap:8px;align-items:center;margin:0">
            <select name="<portlet:namespace/>bulkRole" id="<%=NS%>bulkRole" class="select" style="background:rgba(255,255,255,.15);color:#fff;border-color:rgba(255,255,255,.35)">
                <option value="">Changer le rôle…</option>
                <c:if test="${isSuperAdmin}">
                    <option value="ADMIN">ADMIN</option>
                    <option value="PHARMACIEN">PHARMACIEN</option>
                    <option value="FOURNISSEUR">FOURNISSEUR</option>
                </c:if>
                <c:if test="${isAdmin}">
                    <option value="PHARMACIEN">PHARMACIEN</option>
                    <option value="FOURNISSEUR">FOURNISSEUR</option>
                </c:if>
            </select>
            <input type="hidden" name="<portlet:namespace/>ids" id="<%=NS%>bulkIds"/>
            <button id="<%=NS%>bulkApply" class="btn" style="background:#fff;color:#4F46E5" disabled>✓ Appliquer</button>
            <button type="button" id="<%=NS%>bulkCancel" class="btn btn-ghost">Annuler</button>
        </form>
    </div>

    <!-- Table -->
    <div class="card">
        <table id="<%=NS%>table">
            <thead>
            <tr>
                <th class="sel"><input type="checkbox" id="<%=NS%>checkAll"></th>
                <th>Utilisateur</th>
                <th>Nom complet</th>
                <th>Rôle</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody id="<%=NS%>tbody">
            <c:forEach var="u" items="${users}">
                <portlet:actionURL name="changeRole" var="changeRoleURL" />
                <tr
                        data-email="${fn:escapeXml(u.email)}"
                        data-nom="${fn:escapeXml(u.nom)}"
                        data-prenom="${fn:escapeXml(u.prenom)}"
                        data-role="${u.role}">
                    <td class="sel">
                        <c:choose>
                            <c:when test="${u.role == 'SUPER_ADMIN'}"><input type="checkbox" disabled title="Protégé"></c:when>
                            <c:otherwise><input type="checkbox" class="rowCheck" value="${u.idUtilisateur}"></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <span class="avatar">
                            <c:out value="${empty u.prenom ? '' : fn:substring(u.prenom, 0, 1)}"/>
                            <c:out value="${empty u.nom ? '' : fn:substring(u.nom, 0, 1)}"/>
                        </span>
                        &nbsp;<strong class="user-email">${fn:escapeXml(u.email)}</strong>
                    </td>
                    <td class="col-name">${fn:escapeXml(u.prenom)} ${fn:escapeXml(u.nom)}</td>
                    <td class="col-role">
                        <c:choose>
                            <c:when test="${u.role == 'SUPER_ADMIN'}"><span class="role-tag role-super">SUPER_ADMIN</span></c:when>
                            <c:when test="${u.role == 'ADMIN'}"><span class="role-tag role-admin">ADMIN</span></c:when>
                            <c:when test="${u.role == 'PHARMACIEN'}"><span class="role-tag role-pharma">PHARMACIEN</span></c:when>
                            <c:otherwise><span class="role-tag role-four">FOURNISSEUR</span></c:otherwise>
                        </c:choose>
                    </td>
                    <c:set var="currentEmail" value="${empty requestScope.userEmail ? sessionScope.USER_EMAIL : requestScope.userEmail}" />

                    <td class="col-actions">
                        <c:choose>
                            <%-- If the TARGET user is SUPER_ADMIN: never render a form --%>
                            <c:when test="${u.role == 'SUPER_ADMIN'}">
                                <button class="btn btn-secondary btn-sm" disabled>🔒 Protégé</button>
                            </c:when>

                            <%-- Actor is SUPER_ADMIN: can set ADMIN/PHARMACIEN/FOURNISSEUR --%>
                            <c:when test="${isSuperAdmin}">
                                <form action="${changeRoleURL}" method="post" class="action-form">
                                    <input type="hidden" name="<portlet:namespace/>idUtilisateur" value="${u.idUtilisateur}" />
                                    <select name="<portlet:namespace/>newRole" class="role-select">
                                        <option value="ADMIN"       ${u.role=='ADMIN'?'selected':''}>ADMIN</option>
                                        <option value="PHARMACIEN"  ${u.role=='PHARMACIEN'?'selected':''}>PHARMACIEN</option>
                                        <option value="FOURNISSEUR" ${u.role=='FOURNISSEUR'?'selected':''}>FOURNISSEUR</option>
                                    </select>
                                    <button type="submit" class="btn btn-primary btn-sm">Modifier</button>
                                </form>
                            </c:when>

                            <%-- Actor is ADMIN: only allowed to switch PHARMACIEN <-> FOURNISSEUR --%>
                            <c:when test="${isAdmin}">

                                <c:choose>
                                    <c:when test="${u.role == 'PHARMACIEN' || u.role == 'FOURNISSEUR'}">
                                        <form action="${changeRoleURL}" method="post" class="action-form">
                                            <input type="hidden" name="<portlet:namespace/>idUtilisateur" value="${u.idUtilisateur}" />
                                            <select name="<portlet:namespace/>newRole" class="role-select">
                                                <option value="PHARMACIEN"  ${u.role=='PHARMACIEN'?'selected':''}>PHARMACIEN</option>
                                                <option value="FOURNISSEUR" ${u.role=='FOURNISSEUR'?'selected':''}>FOURNISSEUR</option>
                                            </select>
                                            <button type="submit" class="btn btn-primary btn-sm">Modifier</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-secondary btn-sm" disabled>🔒 Protégé</button>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>

                            <%-- Others: no rights --%>
                            <c:otherwise>
                                <button class="btn btn-secondary btn-sm" disabled>🔒 Protégé</button>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${fn:toLowerCase(u.email) == fn:toLowerCase(currentEmail)}">
                            <button
                                    type="button"
                                    class="btn btn-ghost btn-sm"
                                    data-self-edit="1"
                                    data-id="${u.idUtilisateur}"
                                    data-email="${fn:escapeXml(u.email)}"
                                    data-prenom="${fn:escapeXml(u.prenom)}"
                                    data-nom="${fn:escapeXml(u.nom)}">
                                ✏ Modifier mes infos
                            </button>
                        </c:if>
                    </td>


                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- Pagination -->
        <div class="pager" id="<%=NS%>pager"></div>
    </div>
    <!-- Self profile modal -->
    <div id="<%=NS%>selfModal" class="modal-backdrop">
        <div class="modal-card">
            <div class="modal-head">
                <div class="modal-title">Modifier mes informations</div>
                <button type="button" class="btn modal-close" id="<%=NS%>selfClose">Fermer</button>
            </div>

            <form method="post" action="${updateProfileURL}">
                <input type="hidden" name="<portlet:namespace/>idUtilisateur" id="<%=NS%>selfId">

                <div class="modal-form">
                    <div class="field field--full">
                        <label>Email</label>
                        <input id="<%=NS%>selfEmail" name="<portlet:namespace/>email" type="email" readonly>
                    </div>

                    <div class="field">
                        <label>Prénom</label>
                        <input id="<%=NS%>selfPrenom" name="<portlet:namespace/>prenom" required>
                    </div>

                    <div class="field">
                        <label>Nom</label>
                        <input id="<%=NS%>selfNom" name="<portlet:namespace/>nom" required>
                    </div>

                    <div class="field field--full">
                        <label>Nouveau mot de passe (optionnel)</label>
                        <input id="<%=NS%>selfPwd" name="<portlet:namespace/>motDePasse" type="password"
                               autocomplete="new-password" placeholder="Laisser vide pour ne pas changer">
                    </div>
                </div>

                <div class="modal-actions">
                    <button type="submit" class="btn btn-primary">Enregistrer</button>
                </div>
            </form>
        </div>
    </div>

</div>

<script>
    (function(){
        // Namespace helpers
        const ns = "<%=NS%>";
        const $  = (id) => document.getElementById(ns + id);

        // Controls
        const q          = $('q');
        const roleFilter = $('roleFilter');
        const pageSizeEl = $('pageSize');
        const resetBtn   = $('reset');
        const exportBtn  = $('export');

        const tbody   = $('tbody');
        const allRows = Array.from(tbody.querySelectorAll('tr')); // master list

        const checkAll   = $('checkAll');
        const bulkBar    = $('bulk');
        const bulkCount  = $('bulkCount');
        const bulkIds    = $('bulkIds');
        const bulkRole   = $('bulkRole');
        const bulkApply  = $('bulkApply');
        const bulkCancel = $('bulkCancel');

        const totalEl    = $('total');
        const adminsEl   = $('admins');
        const selectedEl = $('selected');

        const pager      = $('pager');

        // Stats
        function refreshStats(){
            totalEl.textContent = allRows.length;
            const admins = allRows.filter(r=>{
                const role = r.getAttribute('data-role');
                return role === 'ADMIN' || role === 'SUPER_ADMIN';
            }).length;
            adminsEl.textContent = admins;
        }
        refreshStats();

        // Filter + Pagination state
        let filtered = allRows.slice();
        let currentPage = 1;
        let pageSize = parseInt(pageSizeEl.value, 10);

        function applyFilters(){
            const term = (q.value || '').toLowerCase().trim();
            const rf   = roleFilter.value;

            filtered = allRows.filter(tr=>{
                const email  = (tr.getAttribute('data-email')||'').toLowerCase();
                const nom    = (tr.getAttribute('data-nom')||'').toLowerCase();
                const prenom = (tr.getAttribute('data-prenom')||'').toLowerCase();
                const role   = tr.getAttribute('data-role')||'';
                const textOk = !term || email.includes(term) || nom.includes(term) || prenom.includes(term);
                const roleOk = !rf || role === rf;

                // ensure checkbox not lingering when filtered out
                if(!(textOk && roleOk)){
                    const cb = tr.querySelector('.rowCheck');
                    if(cb) cb.checked = false;
                }
                return textOk && roleOk;
            });

            currentPage = 1;
            render();
        }

        function render(){
            // Rebuild tbody with only current page rows
            tbody.innerHTML = '';
            const pages = Math.max(1, Math.ceil(filtered.length / pageSize));
            if(currentPage > pages) currentPage = pages;
            const start = (currentPage - 1) * pageSize;
            const end   = start + pageSize;

            filtered.slice(start, end).forEach(tr => tbody.appendChild(tr));

            // Pager UI
            pager.innerHTML = '';
            const makeBtn = (label, page, disabled=false, active=false)=>{
                const b = document.createElement('button');
                b.className = 'pbtn' + (active ? ' active' : '');
                b.textContent = label;
                b.disabled = disabled;
                if(!disabled && !active) b.onclick = ()=>{ currentPage = page; render(); };
                pager.appendChild(b);
            };
            const totalPages = Math.max(1, Math.ceil(filtered.length / pageSize));
            makeBtn('‹', Math.max(1,currentPage-1), currentPage===1, false);

            const windowSize = 5;
            let s = Math.max(1, currentPage - 2);
            let e = Math.min(totalPages, s + windowSize - 1);
            if(e - s < windowSize - 1) s = Math.max(1, e - windowSize + 1);

            if(s > 1){
                makeBtn('1',1,false,currentPage===1);
                if(s > 2){ const dots=document.createElement('span'); dots.textContent='…'; pager.appendChild(dots); }
            }
            for(let p=s; p<=e; p++) makeBtn(String(p), p, false, p===currentPage);
            if(e < totalPages){
                if(e < totalPages-1){ const dots2=document.createElement('span'); dots2.textContent='…'; pager.appendChild(dots2); }
                makeBtn(String(totalPages), totalPages, false, currentPage===totalPages);
            }
            makeBtn('›', Math.min(totalPages,currentPage+1), currentPage===totalPages, false);

            // Reset header checkbox and bulk state after rerender
            checkAll.checked = false;
            updateBulkState();
        }

        // Selection helpers use only VISIBLE (current page) checkboxes
        function visibleCheckboxes(){
            return Array.from(tbody.querySelectorAll('.rowCheck'));
        }
        function updateBulkState(){
            const checked = visibleCheckboxes().filter(cb=>cb.checked);
            const ids = checked.map(cb=>cb.value);
            bulkIds.value = ids.join(',');
            bulkCount.textContent = ids.length;
            selectedEl.textContent = ids.length;
            bulkApply.disabled = !(ids.length>0 && bulkRole.value);
            bulkBar.classList.toggle('active', ids.length>0);
        }

        // Events
        let t; q.addEventListener('input', ()=>{ clearTimeout(t); t=setTimeout(applyFilters,200); });
        roleFilter.addEventListener('change', applyFilters);
        pageSizeEl.addEventListener('change', ()=>{ pageSize = parseInt(pageSizeEl.value,10); currentPage = 1; render(); });
        resetBtn.addEventListener('click', ()=>{ q.value=''; roleFilter.value=''; currentPage = 1; applyFilters(); });

        checkAll.addEventListener('change', ()=>{
            visibleCheckboxes().forEach(cb=>{ if(!cb.disabled) cb.checked = checkAll.checked; });
            updateBulkState();
        });
        tbody.addEventListener('change', (e)=>{
            if(e.target && e.target.classList.contains('rowCheck')){
                const vis = visibleCheckboxes();
                const checked = vis.filter(cb=>cb.checked);
                checkAll.checked = vis.length>0 && checked.length===vis.length;
                updateBulkState();
            }
        });
        bulkRole.addEventListener('change', updateBulkState);
        bulkCancel.addEventListener('click', ()=>{
            document.querySelectorAll('.rowCheck').forEach(cb=>cb.checked=false);
            checkAll.checked=false;
            updateBulkState();
        });
        $('bulkForm').addEventListener('submit', (e)=>{
            if(bulkApply.disabled){ e.preventDefault(); return; }
            if(!confirm('Appliquer le rôle "'+bulkRole.value+'" aux '+bulkCount.textContent+' utilisateur(s) sélectionné(s) ?')){
                e.preventDefault();
            }
        });

        // Export (exports ALL filtered rows across pages)
        exportBtn.addEventListener('click', ()=>{
            const rowsToExport = filtered; // all filtered rows (not just current page)
            let csv = 'Email,Nom,Prénom,Rôle\n';
            rowsToExport.forEach(tr=>{
                // Prefer data-* attributes (already escaped by server)
                let email  = tr.getAttribute('data-email')  || '';
                let nom    = tr.getAttribute('data-nom')    || '';
                let prenom = tr.getAttribute('data-prenom') || '';
                let role   = tr.getAttribute('data-role')   || '';

                // Fallback to visible text if any are missing
                if(!email){ email = (tr.querySelector('.user-email')?.textContent || '').trim(); }
                if(!nom || !prenom){
                    const full = (tr.querySelector('.col-name')?.textContent || '').trim();
                    if(!nom || !prenom){
                        const parts = full.split(/\s+/);
                        if(!prenom && parts[0]) prenom = parts[0];
                        if(!nom && parts[1])    nom    = parts.slice(1).join(' ');
                    }
                }
                if(!role){
                    role = (tr.querySelector('.col-role .role-tag')?.textContent || '').replace(/^●\s*/,'').trim();
                }

                const esc = s => '"' + String(s).replace(/"/g,'""') + '"';
                csv += [email, nom, prenom, role].map(esc).join(',') + '\n';
            });

            const blob = new Blob([csv], {type:'text/csv;charset=utf-8;'});
            const url  = URL.createObjectURL(blob);
            const a    = document.createElement('a');
            a.href = url;
            a.download = 'utilisateurs_' + new Date().toISOString().slice(0,10) + '.csv';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            URL.revokeObjectURL(url);
        });

        // Initial paint
        applyFilters();
    })();
</script>
<script>
    (function(){
        const ns = "<%=NS%>";
        const modal = document.getElementById(ns + "selfModal");
        const closeBtn = document.getElementById(ns + "selfClose");
        const fId     = document.getElementById(ns + "selfId");
        const fEmail  = document.getElementById(ns + "selfEmail");
        const fPrenom = document.getElementById(ns + "selfPrenom");
        const fNom    = document.getElementById(ns + "selfNom");
        const fPwd    = document.getElementById(ns + "selfPwd");

        document.addEventListener("click", function(e){
            const btn = e.target.closest('[data-self-edit="1"]');
            if (!btn) return;

            fId.value     = btn.getAttribute("data-id") || "";
            fEmail.value  = btn.getAttribute("data-email") || "";
            fPrenom.value = btn.getAttribute("data-prenom") || "";
            fNom.value    = btn.getAttribute("data-nom") || "";
            fPwd.value    = "";

            modal.style.display = "flex";
        });

        function close(){ modal.style.display = "none"; }
        closeBtn.addEventListener("click", close);
        modal.addEventListener("click", (e)=>{ if (e.target === modal) close(); });
    })();
</script>

