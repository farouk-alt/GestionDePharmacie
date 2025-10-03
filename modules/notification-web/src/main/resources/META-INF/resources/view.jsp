<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>

<style>
    #<portlet:namespace/>badge{display:none;background:#ef4444;color:#fff;padding:2px 8px;border-radius:999px;font-size:12px}
    .toolbar{display:flex;gap:8px;align-items:center;margin-bottom:8px}
    .list{margin-top:12px;border:1px solid #e5e7eb;border-radius:10px;background:#fff}
    .item{padding:12px 14px;border-bottom:1px solid #e5e7eb;display:flex;justify-content:space-between;gap:12px}
    .item:last-child{border-bottom:none}
    .meta{color:#6b7280;font-size:12px}
    .btn{border:1px solid #e5e7eb;background:#fff;border-radius:8px;padding:6px 10px;cursor:pointer}
    .btn:hover{background:#f8fafc}
    .status{font-size:12px;padding:2px 8px;border-radius:999px}
    .status.UNREAD{background:#fee2e2;color:#991b1b}
    .status.READ{background:#e5e7eb;color:#374151}
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

<div class="list" id="<portlet:namespace />list"></div>
<div id="<portlet:namespace />pager" style="margin-top:10px; display:flex; gap:8px; align-items:center;"></div>


<!-- Hidden form used to mark as read (guarantees namespaced param is sent) -->
<form id="<portlet:namespace />markForm" method="post" action="${markReadURL}" data-senna-off="true" style="display:none">
    <input type="hidden" name="<portlet:namespace />idNotification" id="<portlet:namespace />idNotification_ns" />
    <input type="hidden" name="idNotification"                          id="<portlet:namespace />idNotification_plain" />
</form>

<script>
    (function(){
        const ns = '<portlet:namespace />';
        const badge = document.getElementById(ns+'badge');
        const list  = document.getElementById(ns+'list');
        const pager = document.getElementById(ns+'pager');

        let curPage = 1;
        const pageSize = 10;
        let total = 0;

        function escapeHTML(s){
            return String(s).replace(/[&<>"']/g, c => (
                {'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]
            ));
        }
        function buildListUrl(ns, page, size){
            const u = new URL('${listURL}', window.location.origin);
            u.searchParams.set(ns + 'page', page);
            u.searchParams.set(ns + 'size', size);
            return u.toString();
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
                b.type = 'button';           // <-- never submit a form
                b.setAttribute('form','nope'); // <-- disassociate from ANY form (id doesn't exist)
                b.textContent = label;
                b.disabled = disabled;
                if (!disabled) {
                    b.addEventListener('click', (e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        e.stopImmediatePropagation();  // <-- stop any other handlers that might submit forms
                        onclick();
                    }, {capture: true}); // run before other listeners
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
            // namespaced params + cache-buster to avoid any SPA caching
            const q = ns + 'page=' + encodeURIComponent(curPage) +
                '&' + ns + 'size=' + encodeURIComponent(pageSize) +
                '&ts=' + Date.now();

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
                        const div = document.createElement('div');
                        div.className = 'item';
                        div.innerHTML =
                            '<div>'
                            +   '<div><strong>' + escapeHTML(n.type||'') + '</strong> – ' + escapeHTML(n.message||'') + '</div>'
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

                    // (re)wire only the "mark as read" buttons
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



        // initial load + poll badge
        refreshBadge();
        refreshList();
        setInterval(refreshBadge, 30000);
    })();
</script>
<script>console.log('ns =', '<portlet:namespace />');</script>

