<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<portlet:defineObjects />

<liferay-portlet:resourceURL var="listURL" id="list" />

<portlet:actionURL name="adjustStock" var="adjustURL" />
<portlet:actionURL name="setStock" var="setURL" />

<style>
    .stk-card{background:#fff;border:1px solid #E5E7EB;border-radius:12px;padding:16px;box-shadow:0 6px 18px rgba(17,24,39,.06);margin-bottom:20px}
    .filters{display:flex;gap:8px;align-items:center;margin-bottom:12px;padding:12px;background:#f9fafb;border-radius:8px;flex-wrap:wrap}
    .filter-group{display:flex;gap:6px;align-items:center}
    .filter-group label{font-size:13px;font-weight:500;color:#374151}
    .filter-input, .filter-select{border:1px solid #d1d5db;border-radius:6px;padding:6px 10px;font-size:13px;background:#fff}
    .filter-input{min-width:180px}
    .btn{display:inline-block;background:#fff;color:#1E3A8A;border:1px solid #E5E7EB;padding:8px 12px;border-radius:10px;font-weight:600;text-decoration:none;cursor:pointer;font-size:13px}
    .btn:hover{background:#f8fafc}
    .btn:disabled{opacity:0.5;cursor:not-allowed}
    .btn-primary{background:#3b82f6;color:#fff;border-color:#3b82f6}
    .btn-primary:hover{background:#2563eb}
    #stkTable{width:100%;border-collapse:collapse}
    #stkTable thead th{background:#F8FAFC;color:#1E3A8A;padding:10px 12px;border-bottom:1px solid #E5E7EB;font-weight:700;text-align:left}
    #stkTable tbody td{padding:10px 12px;border-bottom:1px solid #E5E7EB}
    .num{text-align:right}
    .stock-badge{font-size:11px;padding:3px 8px;border-radius:999px;font-weight:600}
    .stock-out{background:#fef2f2;color:#991b1b}
    .stock-low{background:#fef3c7;color:#92400e}
    .stock-medium{background:#dbeafe;color:#1e40af}
    .stock-high{background:#d1fae5;color:#065f46}
    .empty-state{text-align:center;padding:40px;color:#6b7280}
</style>

<div class="stk-card">
<%--
    <portlet:actionURL name="deleteAllStocks" var="deleteAllStocksURL" />
        <div class="stk-card">
            <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:12px;">
                <h2 style="margin:0;color:#1E3A8A">Gestion des Stocks</h2>

                <c:if test="${userRole == 'ADMIN' || userRole == 'SUPER_ADMIN'}">
                    <form action="${deleteAllStocksURL}" method="post" data-senna-off="true"
                          onsubmit="return confirm('⚠️ Supprimer TOUT le stock ? Cette action est irréversible.');"
                          style="margin:0;">
                        <button type="submit" class="btn"
                                style="background:#DC2626;color:#fff;border-color:#DC2626;">
                            🗑 Supprimer tout
                        </button>
                    </form>
                </c:if>
            </div>

--%>

            <h2 style="margin:0 0 16px 0;color:#1E3A8A">Gestion des Stocks</h2>

    <!-- Filters -->
    <div class="filters">
        <div class="filter-group">
            <label for="<portlet:namespace />filterSearch">Recherche:</label>
            <input type="text" id="<portlet:namespace />filterSearch" class="filter-input" placeholder="Nom du médicament..." />
        </div>
        <div class="filter-group">
            <label for="<portlet:namespace />filterStockLevel">Niveau:</label>
            <select id="<portlet:namespace />filterStockLevel" class="filter-select">
                <option value="">Tous</option>
                <option value="OUT">Épuisé (0)</option>
                <option value="LOW">Bas (≤10)</option>
                <option value="MEDIUM">Moyen (11-50)</option>
                <option value="HIGH">Élevé (>50)</option>
            </select>
        </div>
        <div class="filter-group">
            <label for="<portlet:namespace />filterDateFrom">MAJ Du:</label>
            <input type="date" id="<portlet:namespace />filterDateFrom" class="filter-input" />
        </div>
        <div class="filter-group">
            <label for="<portlet:namespace />filterDateTo">Au:</label>
            <input type="date" id="<portlet:namespace />filterDateTo" class="filter-input" />
        </div>
        <button type="button" id="<portlet:namespace />applyFilters" class="btn btn-primary">Appliquer</button>
        <button type="button" id="<portlet:namespace />resetFilters" class="btn">Réinitialiser</button>
    </div>

    <!-- Table -->
    <div id="<portlet:namespace />tableContainer">
        <table id="stkTable">
            <thead>
            <tr>
                <th>Médicament</th>
                <th class="num">Prix (DH)</th>
                <th class="num">Quantité</th>
                <th>Niveau</th>
                <th>Dernière MAJ</th>
            </tr>
            </thead>
            <tbody id="<portlet:namespace />stkBody">
            <!-- Populated by JavaScript -->
            </tbody>
        </table>
    </div>

    <!-- Pagination -->
    <div id="<portlet:namespace />pager" style="margin-top:16px;display:flex;gap:8px;align-items:center;justify-content:center"></div>
</div>

<script>
    (function(){
        const ns = '<portlet:namespace />';
        const tbody = document.getElementById(ns + 'stkBody');
        const pager = document.getElementById(ns + 'pager');

        // Filter elements
        const filterSearch = document.getElementById(ns + 'filterSearch');
        const filterStockLevel = document.getElementById(ns + 'filterStockLevel');
        const filterDateFrom = document.getElementById(ns + 'filterDateFrom');
        const filterDateTo = document.getElementById(ns + 'filterDateTo');
        const applyFiltersBtn = document.getElementById(ns + 'applyFilters');
        const resetFiltersBtn = document.getElementById(ns + 'resetFilters');

        let curPage = 1;
        const pageSize = 10;
        let total = 0;

        // Filter state
        let activeFilters = {
            search: '',
            stockLevel: '',
            dateFrom: '',
            dateTo: ''
        };

        // Cached data for client-side search
        let allData = [];

        function escapeHTML(s){
            return String(s).replace(/[&<>"']/g, c => (
                {'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]
            ));
        }

        function getStockBadge(qty){
            if (qty === 0) return '<span class="stock-badge stock-out">Épuisé</span>';
            if (qty <= 10) return '<span class="stock-badge stock-low">Bas</span>';
            if (qty <= 50) return '<span class="stock-badge stock-medium">Moyen</span>';
            return '<span class="stock-badge stock-high">Élevé</span>';
        }

        function buildPager(){
            if (!pager) return;
            pager.innerHTML = '';
            const pages = Math.max(1, Math.ceil(total / pageSize));

            const mk = (label, disabled, onclick) => {
                const b = document.createElement('button');
                b.className = 'btn';
                b.type = 'button';
                b.textContent = label;
                b.disabled = disabled;
                if (!disabled) {
                    b.addEventListener('click', (e) => {
                        e.preventDefault();
                        onclick();
                    }, {capture: true});
                }
                return b;
            };

            pager.appendChild(mk('« Précédent', curPage <= 1, () => { curPage--; refreshList(); }));
            const span = document.createElement('span');
            span.textContent = `Page ${curPage} / ${pages}`;
            span.style.margin = '0 12px';
            pager.appendChild(span);
            pager.appendChild(mk('Suivant »', curPage >= pages, () => { curPage++; refreshList(); }));
        }

        function applyClientSideSearch(items) {
            if (!activeFilters.search) return items;

            const searchLower = activeFilters.search.toLowerCase();
            return items.filter(item => {
                const name = (item.nomMedicament || '').toLowerCase();
                return name.includes(searchLower);
            });
        }

        function refreshList(){
            let q = ns + 'page=' + encodeURIComponent(curPage) +
                '&' + ns + 'size=' + encodeURIComponent(pageSize) +
                '&ts=' + Date.now();

            // Add server-side filters (stockLevel, dates)
            if (activeFilters.stockLevel) {
                q += '&' + ns + 'stockLevel=' + encodeURIComponent(activeFilters.stockLevel);
            }
            if (activeFilters.dateFrom) {
                q += '&' + ns + 'dateFrom=' + encodeURIComponent(activeFilters.dateFrom);
            }
            if (activeFilters.dateTo) {
                q += '&' + ns + 'dateTo=' + encodeURIComponent(activeFilters.dateTo);
            }

            const url = '${listURL}' + ('${listURL}'.includes('?') ? '&' : '?') + q;

            return fetch(url, {credentials:'same-origin'})
                .then(r => r.ok ? r.json() : {items:[], total:0, page:1, size:pageSize})
                .then(d => {
                    let arr = Array.isArray(d.items) ? d.items
                        : (typeof d.items === 'string' ? JSON.parse(d.items) : []);

                    allData = arr; // Cache for client-side search

                    // Apply client-side search filter
                    arr = applyClientSideSearch(arr);

                    total = arr.length; // Use filtered count for pagination
                    curPage = d.page || 1;

                    tbody.innerHTML = '';
                    if (!arr || arr.length === 0) {
                        tbody.innerHTML = '<tr><td colspan="5" class="empty-state">Aucun stock trouvé.</td></tr>';
                        pager.innerHTML = '';
                        return;
                    }

                    arr.forEach(item => {
                        const tr = document.createElement('tr');
                        tr.innerHTML =
                            '<td><strong>' + escapeHTML(item.nomMedicament) + '</strong></td>' +
                            '<td class="num">' + parseFloat(item.prixUnitaire).toFixed(2) + ' DH</td>' +
                            '<td class="num"><strong>' + item.quantite + '</strong></td>' +
                            '<td>' + getStockBadge(item.quantite) + '</td>' +
                            '<td>' + escapeHTML(item.dateMaj || '-') + '</td>';
                        tbody.appendChild(tr);
                    });

                    buildPager();
                })
                .catch(err => {
                    console.error('Error loading stock:', err);
                    tbody.innerHTML = '<tr><td colspan="5" class="empty-state">Erreur de chargement.</td></tr>';
                    pager.innerHTML = '';
                });
        }

        // Filter event handlers
        applyFiltersBtn.addEventListener('click', () => {
            activeFilters.search = filterSearch.value.trim();
            activeFilters.stockLevel = filterStockLevel.value;
            activeFilters.dateFrom = filterDateFrom.value;
            activeFilters.dateTo = filterDateTo.value;
            curPage = 1;
            refreshList();
        });

        resetFiltersBtn.addEventListener('click', () => {
            filterSearch.value = '';
            filterStockLevel.value = '';
            filterDateFrom.value = '';
            filterDateTo.value = '';
            activeFilters = { search: '', stockLevel: '', dateFrom: '', dateTo: '' };
            curPage = 1;
            refreshList();
        });

        // Allow Enter key to apply filters
        [filterSearch, filterDateFrom, filterDateTo].forEach(el => {
            el.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    applyFiltersBtn.click();
                }
            });
        });

        // Real-time search (optional - debounced)
        let searchTimeout;
        filterSearch.addEventListener('input', () => {
            clearTimeout(searchTimeout);
            searchTimeout = setTimeout(() => {
                activeFilters.search = filterSearch.value.trim();
                // For real-time search, we re-filter the cached data
                refreshList();
            }, 300); // 300ms debounce
        });

        // Initial load
        refreshList();
    })();
</script>