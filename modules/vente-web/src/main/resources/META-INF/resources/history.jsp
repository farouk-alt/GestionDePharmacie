<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<portlet:defineObjects />

<portlet:resourceURL id="history" var="historyURL" />
<portlet:resourceURL id="venteDetails" var="detailsURL" />
<portlet:resourceURL id="exportCsv" var="exportURL" />

<style>
    .card{background:#fff;border:1px solid #E5E7EB;border-radius:12px;padding:16px}
    .row{display:flex;gap:10px;align-items:center;flex-wrap:wrap}
    .inp{border:1px solid #E5E7EB;border-radius:8px;padding:8px 10px}
    .btn{border:1px solid #E5E7EB;border-radius:8px;padding:8px 12px;background:#fff;cursor:pointer}
    .btn-primary{background:#10B981;border-color:#10B981;color:#fff}
    table{width:100%;border-collapse:collapse}
    th,td{padding:10px;border-bottom:1px solid #E5E7EB}
    th{background:#F8FAFC;text-align:left}
    .right{text-align:right}
</style>

<div class="card">
    <div class="row" style="margin-bottom:10px">
        <input id="<portlet:namespace />from" class="inp" type="datetime-local">
        <input id="<portlet:namespace />to" class="inp" type="datetime-local">
        <input id="<portlet:namespace />user" class="inp" type="text"
               placeholder="Nom / prénom / email" autocomplete="off" autocapitalize="off" spellcheck="false">
        <button id="<portlet:namespace />btnFilter" class="btn">Filtrer</button>
        <button id="<portlet:namespace />btnExport" class="btn">Exporter CSV</button>
        <a class="btn" href="<portlet:actionURL><portlet:param name="mvcPath" value="/view.jsp"/></portlet:actionURL>">← Retour ventes</a>
    </div>

    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Date</th>
            <th>Utilisateur</th>
            <th class="right">Montant</th>
            <th></th>
        </tr>
        </thead>
        <tbody id="<portlet:namespace />tbody"></tbody>
    </table>

    <div class="row" style="justify-content:space-between;margin-top:10px">
        <div id="<portlet:namespace />pagerInfo"></div>
        <div>
            <button id="<portlet:namespace />prev" class="btn">Préc.</button>
            <button id="<portlet:namespace />next" class="btn">Suiv.</button>
        </div>
    </div>
</div>

<!-- Modal -->
<div id="<portlet:namespace />modal" style="display:none;position:fixed;inset:0;background:rgba(0,0,0,.3);align-items:center;justify-content:center">
    <div style="background:#fff;min-width:480px;max-width:700px;border-radius:12px;padding:16px">
        <div class="row" style="justify-content:space-between">
            <strong>Détails de la vente <span id="<portlet:namespace />mId"></span></strong>
            <button id="<portlet:namespace />close" class="btn">Fermer</button>
        </div>
        <table style="margin-top:10px">
            <thead>
            <tr><th>Médicament</th><th class="right">Qté</th><th class="right">PU</th><th class="right">Sous-total</th></tr>
            </thead>
            <tbody id="<portlet:namespace />mBody"></tbody>
        </table>
    </div>
</div>

<script>
    (function(){
        const NS = '<portlet:namespace />';
        const historyURL = '${historyURL}';
        const detailsURL = '${detailsURL}';
        const exportURL  = '${exportURL}';

        let page = 1, size = 20, total = 0;

        function fmt(n){ return (Math.round(n*100)/100).toFixed(2); }
        function fmtDate(d){ const dt = new Date(d); return dt.toLocaleString(); }

        async function load(){
            const from = document.getElementById(NS+'from').value;
            const to   = document.getElementById(NS+'to').value;
            const user = document.getElementById(NS+'user').value;   // text filter

            const params = new URLSearchParams();
            // ✅ namespace everything you send to the resource URL
            params.set(NS+'page', page);
            params.set(NS+'size', size);
            if (from) params.set(NS+'from', from);
            if (to)   params.set(NS+'to', to);
            if (user) params.set(NS+'user', user);

            // resourceURL id="history" -> server routes by getResourceID()
            const res = await fetch(historyURL + '&' + params.toString(), {headers:{'Accept':'application/json'}});
            const data = await res.json();
            total = data.total || 0;

            const tb = document.getElementById(NS+'tbody');
            tb.innerHTML = '';
            (data.items||[]).forEach(v=>{
                const tr = document.createElement('tr');
                tr.innerHTML =
                    '<td>'+v.idVente+'</td>'+
                    '<td>'+fmtDate(v.date)+'</td>'+
                    '<td>'+(v.userName || v.userId || '')+'</td>'+
                    '<td class="right">'+fmt(v.montant)+' DH</td>'+
                    '<td><button class="btn btn-primary" data-id="'+v.idVente+'">Détails</button></td>';

                tr.querySelector('button').onclick = ()=> openDetails(v.idVente);
                tb.appendChild(tr);
            });

            const pages = Math.max(1, Math.ceil(total/size));
            document.getElementById(NS+'pagerInfo').textContent = 'Page '+page+'/'+pages+' — '+total+' ventes';
            updatePager();
        }
        function updatePager(){
            const pages = Math.max(1, Math.ceil(total/size));
            document.getElementById(NS+'pagerInfo').textContent = 'Page '+page+'/'+pages+' — '+total+' ventes';
            document.getElementById(NS+'prev').disabled = (page<=1);
            document.getElementById(NS+'next').disabled = (page>=pages);
        }
        async function openDetails(id){
            // detailsURL id="venteDetails": only idVente is needed (namespaced)
            const res = await fetch(detailsURL + '&' + NS + 'idVente=' + id, {headers:{'Accept':'application/json'}});
            const lines = await res.json();
            document.getElementById(NS+'mId').textContent = '#'+id;
            const mb = document.getElementById(NS+'mBody');
            mb.innerHTML = '';
            lines.forEach(l=>{
                const tr = document.createElement('tr');
                tr.innerHTML =
                    '<td>'+l.idMedicament+'</td>'+
                    '<td class="right">'+l.quantite+'</td>'+
                    '<td class="right">'+fmt(l.prixUnitaire)+'</td>'+
                    '<td class="right">'+fmt(l.sousTotal)+'</td>';
                mb.appendChild(tr);
            });
            document.getElementById(NS+'modal').style.display='flex';
        }

        document.getElementById(NS+'close').onclick = ()=> document.getElementById(NS+'modal').style.display='none';
        document.getElementById(NS+'prev').onclick = ()=> { if (page>1){ page--; load(); } };
        document.getElementById(NS+'next').onclick = ()=> {
            if (page * size < total) { page++; load(); }
        };
        document.getElementById(NS+'btnFilter').onclick = ()=> { page=1; load(); };

        // Export already namespaced per our previous change
        document.getElementById(NS+'btnExport').onclick = ()=>{
            const from = document.getElementById(NS+'from').value;
            const to   = document.getElementById(NS+'to').value;
            const user = document.getElementById(NS+'user').value;

            const u = new URL(exportURL, window.location.href); // safe base
            if (from) u.searchParams.set(NS + 'from', from);
            if (to)   u.searchParams.set(NS + 'to', to);
            if (user) u.searchParams.set(NS + 'user', user);

            // navigate in the same tab (popup blockers love to block window.open)
            window.location.href = u.toString();
        };


        load();
    })();

</script>
