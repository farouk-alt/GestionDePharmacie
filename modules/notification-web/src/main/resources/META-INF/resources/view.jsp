<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>

<style>
    #<portlet:namespace/>badge{display:none;background:#ef4444;color:#fff;padding:2px 8px;border-radius:999px;font-size:12px}
    .toolbar{display:flex;gap:8px;align-items:center;margin-bottom:8px;flex-wrap:wrap}
    .filters{display:flex;gap:8px;align-items:center;margin-bottom:12px;padding:12px;background:#f9fafb;border-radius:8px;flex-wrap:wrap}
    .filter-group{display:flex;gap:6px;align-items:center}
    .filter-group label{font-size:13px;font-weight:500;color:#374151}
    .filter-input{border:1px solid #d1d5db;border-radius:6px;padding:6px 10px;font-size:13px}
    .filter-select{border:1px solid #d1d5db;border-radius:6px;padding:6px 10px;font-size:13px;background:#fff}
    .list{margin-top:12px;border:1px solid #e5e7eb;border-radius:10px;background:#fff}
    .item{padding:12px 14px;border-bottom:1px solid #e5e7eb;display:flex;justify-content:space-between;gap:12px}
    .item:last-child{border-bottom:none}
    .meta{color:#6b7280;font-size:12px}
    .btn{border:1px solid #e5e7eb;background:#fff;border-radius:8px;padding:6px 10px;cursor:pointer;font-size:13px}
    .btn:hover{background:#f8fafc}
    .btn:disabled{opacity:0.5;cursor:not-allowed}
    .btn-primary{background:#3b82f6;color:#fff;border-color:#3b82f6}
    .btn-primary:hover{background:#2563eb}
    .status{font-size:12px;padding:2px 8px;border-radius:999px}
    .status.UNREAD{background:#fee2e2;color:#991b1b}
    .status.READ{background:#e5e7eb;color:#374151}
    .category-badge{font-size:11px;padding:2px 6px;border-radius:4px;background:#dbeafe;color:#1e40af;margin-right:6px}
</style>

<!-- Resource & Action URLs -->
<liferay-portlet:resourceURL var="unreadURL" id="unread" />
<liferay-portlet:resourceURL var="listURL"   id="list" />

<liferay-portlet:actionURL name="delete_all" var="deleteAllURL" />
<liferay-portlet:actionURL name="mark_read"  var="markReadURL"  />
<liferay-portlet:actionURL name="mark_all" var="markAllURL" />

<div class="toolbar">
    <strong>Notifications</strong>
    <span id="<portlet:namespace />badge">0</span>

    <form method="post" action="${deleteAllURL}" data-senna-off="true" style="display:inline">
        <button type="submit" class="btn btn-secondary">Supprimer tout (temp.)</button>
    </form>

    <a class="btn" href="${markAllURL}">Tout marquer comme lus</a>
</div>

<!-- Filters Section -->
<div class="filters">
    <div class="filter-group">
        <label for="<portlet:namespace />filterDateFrom">Du:</label>
        <input type="date" id="<portlet:namespace />filterDateFrom" class="filter-input" />
    </div>
    <div class="filter-group">
        <label for="<portlet:namespace />filterDateTo">Au:</label>
        <input type="date" id="<portlet:namespace />filterDateTo" class="filter-input" />
    </div>
    <div class="filter-group">
        <label for="<portlet:namespace />filterCategory">Catégorie:</label>
        <select id="<portlet:namespace />filterCategory" class="filter-select">
            <option value="">Toutes</option>
            <option value="VENTE">Vente</option>
            <option value="LOW_STOCK">Stock</option>
            <option value="MED_ADDED">Médicament</option>
            <option value="COMMANDE">Commande</option>
        </select>
    </div>
    <button type="button" id="<portlet:namespace />applyFilters" class="btn btn-primary">Appliquer</button>
    <button type="button" id="<portlet:namespace />resetFilters" class="btn">Réinitialiser</button>
</div>

<div class="list" id="<portlet:namespace />list"></div>
<div id="<portlet:namespace />pager" style="margin-top:10px; display:flex; gap:8px; align-items:center;"></div>

<!-- Hidden form used to mark as read (guarantees namespaced param is sent) -->
<form id="<portlet:namespace />markForm" method="post" action="${markReadURL}" data-senna-off="true" style="display:none">
    <input type="hidden" name="<portlet:namespace />idNotification" id="<portlet:namespace />idNotification_ns" />
    <input type="hidden" name="idNotification" id="<portlet:namespace />idNotification_plain" />
</form>

<script>
    (function(){
        const ns = '<portlet:namespace />';
        const badge = document.getElementById(ns+'badge');
        const list  = document.getElementById(ns+'list');
        const pager = document.getElementById(ns+'pager');

        // Filter elements
        const filterDateFrom = document.getElementById(ns+'filterDateFrom');
        const filterDateTo = document.getElementById(ns+'filterDateTo');
        const filterCategory = document.getElementById(ns+'filterCategory');
        const applyFiltersBtn = document.getElementById(ns+'applyFilters');
        const resetFiltersBtn = document.getElementById(ns+'resetFilters');

        let curPage = 1;
        const pageSize = 10;
        let total = 0;

        // Filter state
        let activeFilters = {
            dateFrom: '',
            dateTo: '',
            category: ''
        };

        function escapeHTML(s){
            return String(s).replace(/[&<>"']/g, c => (
                {'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]
            ));
        }

        function refreshBadge(){
            return fetch('${unreadURL}', {credentials:'same-origin'})
                .then(r => r.ok ? r.json() : {unread:0})
                .then(d => {
                    const n = (d && typeof d.unread === 'number') ? d.unread : 0;
                    if (n > 0){ badge.textContent = n; badge.style.display='inline-block'; }
                    else { badge.style.display='none'; }
                }).catch(()=>{});
        }

        function buildPager(){
            if (!pager) return;
            pager.innerHTML = '';
            const pages = Math.max(1, Math.ceil(total / pageSize));

            const mk = (label, disabled, onclick) => {
                const b = document.createElement('button');
                b.className = 'btn';
                b.type = 'button';
                b.setAttribute('form','nope');
                b.textContent = label;
                b.disabled = disabled;
                if (!disabled) {
                    b.addEventListener('click', (e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        e.stopImmediatePropagation();
                        onclick();
                    }, {capture: true});
                }
                return b;
            };

            pager.appendChild(mk('« Précédent', curPage <= 1, () => { curPage--; refreshList(); }));
            const span = document.createElement('span');
            span.textContent = `Page ${curPage} / ${pages}`;
            pager.appendChild(span);
            pager.appendChild(mk('Suivant »', curPage >= pages, () => { curPage++; refreshList(); }));
        }

        function refreshList(){
            // Build query string with namespaced params + filters
            let q = ns + 'page=' + encodeURIComponent(curPage) +
                '&' + ns + 'size=' + encodeURIComponent(pageSize) +
                '&ts=' + Date.now();

            // Add filter parameters
            if (activeFilters.dateFrom) {
                q += '&' + ns + 'dateFrom=' + encodeURIComponent(activeFilters.dateFrom);
            }
            if (activeFilters.dateTo) {
                q += '&' + ns + 'dateTo=' + encodeURIComponent(activeFilters.dateTo);
            }
            if (activeFilters.category) {
                q += '&' + ns + 'category=' + encodeURIComponent(activeFilters.category);
            }

            const url = '${listURL}' + ('${listURL}'.includes('?') ? '&' : '?') + q;

            return fetch(url, {credentials:'same-origin'})
                .then(r => r.ok ? r.json() : {items:[], total:0, page:1, size:pageSize})
                .then(d => {
                    const arr = Array.isArray(d.items) ? d.items
                        : (typeof d.items === 'string' ? JSON.parse(d.items) : []);
                    total   = d.total || 0;
                    curPage = d.page  || 1;

                    list.innerHTML = '';
                    if (!arr || arr.length === 0) {
                        list.innerHTML = '<div class="item"><span>Aucune notification.</span></div>';
                        pager.innerHTML = '';
                        return;
                    }

                    arr.forEach(n => {
                        const id = n.idNotification || n.id;
                        const status = (n.status || n.statut || '');
                        const type = n.type || '';
                        const div = document.createElement('div');
                        div.className = 'item';
                        div.innerHTML =
                            '<div>'
                            +   '<div>'
                            +     '<span class="category-badge">' + escapeHTML(type) + '</span>'
                            +     '<strong>' + escapeHTML(n.message||'') + '</strong>'
                            +   '</div>'
                            +   '<div class="meta">' + escapeHTML(n.date||'') + '</div>'
                            + '</div>'
                            + '<div style="display:flex;align-items:center;gap:8px">'
                            +   '<span class="status ' + status + '">' + status + '</span>'
                            +   (status === 'UNREAD'
                                ? '<button type="button" class="btn" data-id="'+ id +'" form="nope">Marquer comme lue</button>'
                                : '')
                            + '</div>';
                        list.appendChild(div);
                    });

                    // Wire "mark as read" buttons
                    list.querySelectorAll('button[data-id]').forEach(btn => {
                        btn.type = 'button';
                        btn.setAttribute('form','nope');
                        btn.addEventListener('click', (e) => {
                            e.preventDefault(); e.stopPropagation(); e.stopImmediatePropagation();
                            const id = btn.getAttribute('data-id');
                            const f  = document.getElementById(ns+'markForm');
                            document.getElementById(ns+'idNotification_ns').value    = id;
                            document.getElementById(ns+'idNotification_plain').value = id;
                            f.submit();
                        }, {capture:true});
                    });

                    buildPager();
                })
                .catch(()=>{
                    list.innerHTML = '<div class="item"><span>Erreur de chargement.</span></div>';
                    pager.innerHTML = '';
                });
        }

        // Filter event handlers
        applyFiltersBtn.addEventListener('click', () => {
            activeFilters.dateFrom = filterDateFrom.value;
            activeFilters.dateTo = filterDateTo.value;
            activeFilters.category = filterCategory.value;
            curPage = 1; // Reset to first page when applying filters
            refreshList();
        });

        resetFiltersBtn.addEventListener('click', () => {
            filterDateFrom.value = '';
            filterDateTo.value = '';
            filterCategory.value = '';
            activeFilters = { dateFrom: '', dateTo: '', category: '' };
            curPage = 1;
            refreshList();
        });

        // Allow Enter key to apply filters
        [filterDateFrom, filterDateTo, filterCategory].forEach(el => {
            el.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    applyFiltersBtn.click();
                }
            });
        });

        // Initial load + poll badge
        refreshBadge();
        refreshList();
        setInterval(refreshBadge, 30000);
    })();
</script>
<script>console.log('ns =', '<portlet:namespace />');</script>