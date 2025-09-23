<%@ include file="/init.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="gestion_de_pharmacie.model.Medicament" %>
<%@ page import="gestion_de_pharmacie.service.MedicamentLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %>
<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<%
    int unreadCount = 0;
    Object cObj = request.getAttribute("unreadCount");
    if (cObj instanceof Integer) unreadCount = (Integer)cObj;
%>

<portlet:actionURL name="ajouterMedicament" var="addMedURL" />
<portlet:actionURL name="updateMedicament"  var="updateMedURL" />

<%
    List<Medicament> medicaments = MedicamentLocalServiceUtil.getMedicaments(-1, -1);
    NumberFormat money = NumberFormat.getNumberInstance(Locale.FRANCE);
    money.setMinimumFractionDigits(2);
    money.setMaximumFractionDigits(2);
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <title>Liste des Médicaments</title>
    <style>
        :root{ --primary:#1E3A8A; --secondary:#3B82F6; --accent:#10B981;
            --bg:#F3F4F6; --white:#FFFFFF; --text:#111827; --muted:#6B7280; --border:#E5E7EB; }
        body{background:var(--bg);margin:0;color:var(--text);font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif}
        .wrap{max-width:1200px;margin:24px auto;padding:0 16px}
        .header{background:linear-gradient(135deg,var(--primary),var(--secondary));color:#fff;border-radius:14px;padding:18px 20px;margin-bottom:16px;display:flex;align-items:center;justify-content:space-between;box-shadow:0 10px 24px rgba(30,58,138,.25)}
        .header h2{margin:0;font-size:20px}
        .header-actions{display:flex;gap:8px;align-items:center}
        .btn{display:inline-block;background:#fff;color:var(--primary);border:1px solid transparent;padding:8px 12px;border-radius:10px;font-weight:600;text-decoration:none;cursor:pointer;transition:.2s}
        .btn:hover{background:#F8FAFC;transform:translateY(-1px);color:var(--primary)}
        .btn-primary{background:var(--accent);color:#fff;border-color:var(--accent)}
        .btn-primary:hover{filter:brightness(.95);color:var(--primary)}
        .btn-danger{background:#DC2626;color:#fff;border-color:#DC2626}
        .btn-danger:hover{filter:brightness(.95)}
        .card{background:var(--white);border:1px solid var(--border);border-radius:12px;padding:16px;box-shadow:0 6px 18px rgba(17,24,39,.06);margin-bottom:16px}
        .controls{display:flex;gap:12px;align-items:center;flex-wrap:wrap;margin-bottom:10px}
        .controls input[type="search"]{flex:1;min-width:240px;padding:10px;border:1px solid var(--border);border-radius:10px;background:#fff}
        .count{color:var(--bg);font-size:12px;margin-left:auto}
        #rangeText{color:var(--muted);font-size:12px;margin-left:auto}
        table{width:100%;border-collapse:collapse}
        thead th{text-align:left;background:#F8FAFC;color:var(--primary);padding:10px 12px;border-bottom:1px solid var(--border);font-weight:700}
        tbody td{padding:10px 12px}
        tr:hover td{background:#F9FAFB}
        th.sortable{cursor:pointer;user-select:none}
        th.sortable .arrow{font-size:12px;opacity:.6;margin-left:6px}
        .desc{max-width:380px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;color:#334155}
        .pager{display:flex;gap:8px;align-items:center;flex-wrap:wrap;margin-top:12px}
        .pager .btn{padding:6px 12px}
        .pager .btn.btn-primary{background:var(--secondary);border-color:var(--secondary)}

        /* Modal size like Admin */
        #<portlet:namespace/>medModal .modal-dialog{
            max-width:720px; width:min(92vw,720px); margin-top:6vh; margin-bottom:6vh;
        }
        #<portlet:namespace/>medModal .modal-content{ min-height:560px; }
        #<portlet:namespace/>medModal .modal-body{
            max-height:calc(100vh - 180px); overflow:auto; padding-bottom:24px;
        }

        /* Stacked form layout */
        #<portlet:namespace/>medModal .med-form .stack{
            display:grid; grid-template-columns:1fr; gap:16px; align-items:start;
        }
        #<portlet:namespace/>medModal .med-form .fld{ display:flex; flex-direction:column; }
        #<portlet:namespace/>medModal .med-form label{ font-weight:600; margin-bottom:6px; color:var(--primary); }
        #<portlet:namespace/>medModal .med-form .hint{ color:#6B7280; font-size:12px; margin-top:6px; }

        #<portlet:namespace/>medModal .form-control{
            width:100%; height:52px; padding:12px 14px; line-height:normal; box-sizing:border-box;
            background:#fff; color:#111827; border:1px solid #E5E7EB; border-radius:10px;
            box-shadow:inset 0 1px 2px rgba(0,0,0,.04);
        }
        #<portlet:namespace/>medModal .form-control:focus{
            outline:none; border-color:#3B82F6; box-shadow:0 0 0 3px rgba(59,130,246,.15);
        }
        #<portlet:namespace/>medModal textarea.form-control{ min-height:120px; height:auto; resize:vertical; }

        #<portlet:namespace/>medModal input[type="number"]::-webkit-outer-spin-button,
        #<portlet:namespace/>medModal input[type="number"]::-webkit-inner-spin-button{ -webkit-appearance:none; margin:0; }
        #<portlet:namespace/>medModal input[type="number"]{ -moz-appearance:textfield; }

        /* Row separators in table */
        #medsTable tbody td { border-bottom: 0; }
        #medsTable tbody tr { box-shadow: inset 0 -1px 0 var(--border); }
        #medsTable tbody tr:last-child { box-shadow: none; }

        /* Actions */
        #medsTable td.actions{ white-space:nowrap; vertical-align:middle; }
        #medsTable td.actions form{ display:inline; margin:0; }
        .icon-btn{ display:inline-flex; align-items:center; justify-content:center; width:32px; height:32px; margin-right:6px;
            background:transparent; border:1px solid transparent; border-radius:8px; color:#334155; cursor:pointer;
            transition:background .15s, color .15s, border-color .15s, transform .05s; vertical-align:middle; }
        .icon-btn:last-child{ margin-right:0; }
        .icon-btn:hover{ background:#F1F5F9; color:#0f172a; }
        .icon-btn:active{ transform:scale(.98); }
        .icon-btn:focus{ outline:0; box-shadow:0 0 0 2px rgba(59,130,246,.35); }
        .icon-btn.danger{ color:#b91c1c; }
        .icon-btn.danger:hover{ background:rgba(220,38,38,.08); color:#991b1b; }
        .icon-btn .lexicon-icon{ width:18px; height:18px; }
        .bell-btn{
            position:relative; display:inline-flex; align-items:center; gap:8px;
            background:#fff; color:var(--primary); border:1px solid var(--border);
            padding:8px 12px; border-radius:10px; font-weight:600; cursor:pointer;
        }
        .bell-dot{
            min-width:22px; height:22px; padding:0 6px;
            display:inline-flex; align-items:center; justify-content:center;
            font-size:12px; font-weight:800; color:#fff; background:#ef4444; border-radius:999px;
        }

    </style>
</head>
<body>
<div class="wrap">
    <liferay-portlet:renderURL portletName="notification_web_NotificationWebPortlet" var="notifURL" />

    <div class="header">
        <h2>💊 Liste des Médicaments</h2>
        <div class="header-actions">
            <a class="bell-btn" href="${notifURL}" title="Notifications">
                🔔
                <% if (unreadCount > 0) { %>
                <span class="bell-dot"><%= unreadCount %></span>
                <% } %>
            </a>
            <div class="count" id="topCount"></div>
            <button type="button" class="btn btn-primary js-med-open" data-mode="add">+ Ajouter</button>
        </div>
    </div>

    <div class="card">
        <div class="controls">
            <input id="search" type="search" placeholder="Rechercher (code, code-barres, nom, catégorie, description)…" />
            <span class="count" id="rangeText"></span>
        </div>

        <table id="medsTable">
            <thead>
            <tr>
                <th class="sortable" data-sort-key="id">ID <span class="arrow">↕</span></th>
                <th class="sortable" data-sort-key="code">Code <span class="arrow">↕</span></th>
                <th class="sortable" data-sort-key="codeBarre">Code-barres <span class="arrow">↕</span></th>
                <th class="sortable" data-sort-key="nom">Nom <span class="arrow">↕</span></th>
                <th class="sortable" data-sort-key="categorie">Catégorie <span class="arrow">↕</span></th>
                <th class="sortable" data-sort-key="prix">Prix (DH) <span class="arrow">↕</span></th>
                <th class="sortable" data-sort-key="seuil">Seuil min. <span class="arrow">↕</span></th>
                <th class="sortable" data-sort-key="date">Date ajout <span class="arrow">↕</span></th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Medicament m : medicaments) {
                    long ts = (m.getDateAjout() != null) ? m.getDateAjout().getTime() : 0L;
                    String prixAff = money.format(m.getPrixUnitaire());
                    String dateAff = (m.getDateAjout()!=null) ? new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(m.getDateAjout()) : "-";
            %>
            <tr>
                <td data-key="id" data-sort="<%= m.getIdMedicament() %>"><%= m.getIdMedicament() %></td>
                <td data-key="code"><%= HtmlUtil.escape(String.valueOf(m.getCode())) %></td>
                <td data-key="codeBarre"><%= HtmlUtil.escape(String.valueOf(m.getCodeBarre())) %></td>
                <td data-key="nom"><%= HtmlUtil.escape(String.valueOf(m.getNom())) %></td>
                <td data-key="categorie"><%= HtmlUtil.escape(m.getCategorie()!=null? m.getCategorie() : "-") %></td>
                <td data-key="prix" data-sort="<%= m.getPrixUnitaire() %>"><%= prixAff %></td>
                <td data-key="seuil" data-sort="<%= m.getSeuilMinimum() %>"><%= m.getSeuilMinimum() %></td>
                <td data-key="date" data-sort="<%= ts %>"><%= dateAff %></td>
                <td class="desc" title="<%= HtmlUtil.escape(m.getDescription()!=null? m.getDescription() : "") %>">
                    <%= HtmlUtil.escape(m.getDescription()!=null? m.getDescription() : "-") %>
                </td>
                <td class="actions">
                    <!-- Edit -->
                    <a href="#"
                       class="icon-btn js-med-open"
                       data-mode="edit"
                       data-id="<%= m.getIdMedicament() %>"
                       data-code="<%= HtmlUtil.escapeAttribute(String.valueOf(m.getCode())) %>"
                       data-codebarre="<%= HtmlUtil.escapeAttribute(String.valueOf(m.getCodeBarre())) %>"
                       data-nom="<%= HtmlUtil.escapeAttribute(String.valueOf(m.getNom())) %>"
                       data-prix="<%= m.getPrixUnitaire() %>"
                       data-categorie="<%= HtmlUtil.escapeAttribute(String.valueOf(m.getCategorie())) %>"
                       data-seuil="<%= m.getSeuilMinimum() %>"
                       data-description="<%= HtmlUtil.escapeAttribute(String.valueOf(m.getDescription())) %>"
                       aria-label="Éditer" title="Éditer">
                        <clay:icon symbol="pencil" />
                    </a>

                    <!-- Delete -->
                    <portlet:actionURL name="deleteMedicament" var="deleteURL">
                        <portlet:param name="medicamentId" value="<%= String.valueOf(m.getIdMedicament()) %>" />
                    </portlet:actionURL>
                    <form action="${deleteURL}" method="post"
                          onsubmit="return confirm('Voulez-vous vraiment supprimer ce médicament ?');">
                        <button type="submit" class="icon-btn danger" aria-label="Supprimer" title="Supprimer">
                            <clay:icon symbol="trash" />
                        </button>
                    </form>
                </td>

            </tr>
            <% } %>
            </tbody>
        </table>

        <div class="pager" id="pager"></div>
    </div>


    <!-- Flash messages -->
    <liferay-ui:success key="medicament-added-successfully"    message="Médicament ajouté avec succès !" />
    <liferay-ui:success key="medicament-updated-successfully"  message="Médicament mis à jour avec succès !" />
    <liferay-ui:success key="medicament-deleted-successfully"  message="Médicament supprimé avec succès !" />
    <liferay-ui:error   key="medicament-already-exists"        message="Un médicament avec ce nom existe déjà." />
    <liferay-ui:error   key="medicament-required"               message="Veuillez renseigner au minimum le nom et le code." />
    <liferay-ui:error   key="medicament-code-exists"            message="Ce code interne existe déjà." />
    <liferay-ui:error   key="medicament-barcode-invalid"        message="Le code-barres doit être un EAN-13 valide." />
    <liferay-ui:error   key="medicament-barcode-exists"         message="Ce code-barres existe déjà." />
</div> <!-- close .wrap once -->
<template id="medFormTPL">
    <form id="<portlet:namespace/>medForm" class="med-form" method="post">
        <input type="hidden" id="<portlet:namespace/>medicamentId" name="<portlet:namespace/>medicamentId"/>
        <div class="stack">
            <div class="fld">
                <label for="<portlet:namespace/>code">Code (interne)</label>
                <input class="form-control" id="<portlet:namespace/>code" name="<portlet:namespace/>code" maxlength="64" required placeholder="Ex. AMOX500-CAP-20" />
            </div>
            <div class="fld">
                <label for="<portlet:namespace/>codeBarre">Code-barres (EAN-13)</label>
                <input class="form-control" id="<portlet:namespace/>codeBarre" name="<portlet:namespace/>codeBarre" inputmode="numeric" autocomplete="off" pattern="[0-9]{12,13}" maxlength="13" placeholder="12 ou 13 chiffres (auto-complété)" />
                <small id="<portlet:namespace/>eanHint" class="hint"></small>
            </div>
            <div class="fld">
                <label for="<portlet:namespace/>nom">Nom</label>
                <input class="form-control" id="<portlet:namespace/>nom" name="<portlet:namespace/>nom" required />
            </div>
            <div class="fld">
                <label for="<portlet:namespace/>prix">Prix Unitaire (DH)</label>
                <input class="form-control" id="<portlet:namespace/>prix" name="<portlet:namespace/>prix" type="number" step="0.01" min="0" required />
            </div>
            <div class="fld">
                <label for="<portlet:namespace/>categorie">Catégorie</label>
                <input class="form-control" id="<portlet:namespace/>categorie" name="<portlet:namespace/>categorie" />
            </div>
            <div class="fld">
                <label for="<portlet:namespace/>seuilMinimum">Seuil Minimum</label>
                <input class="form-control" id="<portlet:namespace/>seuilMinimum" name="<portlet:namespace/>seuilMinimum" type="number" min="0" />
            </div>
            <div class="fld">
                <label for="<portlet:namespace/>description">Description</label>
                <textarea class="form-control" id="<portlet:namespace/>description" name="<portlet:namespace/>description"></textarea>
            </div>
        </div>
    </form>
</template>

<script>
    /* List filtering/sorting/paging */
    (function(){
        var table = document.getElementById('medsTable');
        if(!table) return;

        var tbody = table.querySelector('tbody');
        var allRows = Array.prototype.slice.call(tbody.querySelectorAll('tr'));
        var filtered = allRows.slice();
        var search = document.getElementById('search');
        var pager = document.getElementById('pager');
        var rangeText = document.getElementById('rangeText');
        var topCount = document.getElementById('topCount');

        var pageSize = 10;
        var currentPage = 1;
        var sort = { key: null, dir: 1 };

        function txt(el){ return (el && el.textContent || '').trim(); }
        function num(v){ v = parseFloat(v); return isNaN(v) ? 0 : v; }
        function getCell(row, key){ return row.querySelector('td[data-key="'+key+'"]'); }
        function getVal(row, key){
            var cell = getCell(row,key);
            if(!cell) return '';
            if(key==='prix' || key==='seuil' || key==='date' || key==='id'){
                return num(cell.getAttribute('data-sort')||'0');
            }
            return txt(cell).toLowerCase();
        }

        function applyFilter(){
            var q = (search && search.value ? search.value.toLowerCase().trim() : '');
            filtered = allRows.filter(function(r){
                if(!q) return true;
                var nom=getVal(r,'nom'), code=getVal(r,'code'), cb=getVal(r,'codeBarre'),
                    cat=getVal(r,'categorie'), desc=txt(r.querySelector('.desc')).toLowerCase();
                return nom.indexOf(q)>-1 || code.indexOf(q)>-1 || cb.indexOf(q)>-1 ||
                    cat.indexOf(q)>-1 || desc.indexOf(q)>-1;
            });
            currentPage = 1;
            applySort();
        }

        function applySort(){
            if(sort.key){
                filtered.sort(function(a,b){
                    var va=getVal(a,sort.key), vb=getVal(b,sort.key);
                    if(va<vb) return -1*sort.dir;
                    if(va>vb) return  1*sort.dir;
                    return 0;
                });
            }
            render();
        }

        function render(){
            tbody.innerHTML = '';
            var total = filtered.length;
            var pages = Math.max(1, Math.ceil(total/pageSize));
            if(currentPage>pages) currentPage = pages;
            var start = (currentPage-1)*pageSize;
            var end   = start+pageSize;
            filtered.slice(start,end).forEach(function(r){ tbody.appendChild(r); });

            Array.prototype.slice.call(table.querySelectorAll('th.sortable .arrow'))
                .forEach(function(a){ a.textContent='↕'; });
            if(sort.key){
                var th = table.querySelector('th.sortable[data-sort-key="'+sort.key+'"] .arrow');
                if(th) th.textContent = (sort.dir===1 ? '↑' : '↓');
            }

            pager.innerHTML = '';
            if(pages>1){
                var prev=document.createElement('button');
                prev.className='btn'; prev.textContent='Précédent'; prev.disabled=currentPage===1;
                prev.onclick=function(){ currentPage--; render(); }; pager.appendChild(prev);

                for(var i=1;i<=pages;i++){
                    var b=document.createElement('button');
                    b.className='btn' + (i===currentPage?' btn-primary':'');
                    b.textContent=i;
                    (function(p){ b.onclick=function(){ currentPage=p; render(); }; })(i);
                    pager.appendChild(b);
                }

                var next=document.createElement('button');
                next.className='btn'; next.textContent='Suivant'; next.disabled=currentPage===pages;
                next.onclick=function(){ currentPage++; render(); }; pager.appendChild(next);
            }

            if(rangeText){
                var from = total===0?0:start+1;
                var to   = Math.min(end,total);
                rangeText.textContent = 'Affichage '+from+'–'+to+' sur '+total;
            }
            if(topCount){
                topCount.textContent = total + ' médicament' + (total>1?'s':'');
            }
        }

        if(search){ search.addEventListener('input', applyFilter); }
        Array.prototype.slice.call(table.querySelectorAll('th.sortable')).forEach(function(th){
            th.addEventListener('click', function(){
                var key = th.getAttribute('data-sort-key');
                if(sort.key===key){ sort.dir*=-1; } else { sort.key=key; sort.dir=1; }
                applySort();
            });
        });

        applyFilter();
    })();
</script>

<script>
    /* Modal open + PREFILL (by ID) — no MutationObserver, no template.content */
    (function () {
        const ns = '<portlet:namespace/>';
        const modalId = ns + 'medModal';
        const addURL = '${addMedURL}';
        const updateURL = '${updateMedURL}';

        // EAN helpers
        const ENFORCE_EAN13 = false;
        const cleanDigits = s => (s || '').replace(/\D/g, '');
        function computeEAN13(b12){ let s=0; for(let i=0;i<12;i++){const d=b12.charCodeAt(i)-48; s+=(i%2?3:1)*d;} return (10-(s%10))%10; }
        function isValidEAN13(s){ return /^[0-9]{13}$/.test(s) && (s.charCodeAt(12)-48)===computeEAN13(s.slice(0,12)); }

        function prepareBarcode(){
            const cb = document.getElementById(ns+'codeBarre');
            if(!cb) return true;
            cb.value = cleanDigits(cb.value);
            if(cb.value.length===12){ cb.value += computeEAN13(cb.value); }
            else if(cb.value.length===13){
                if(!isValidEAN13(cb.value)){
                    if(ENFORCE_EAN13){ cb.setCustomValidity('Code-barres EAN-13 invalide.'); cb.reportValidity && cb.reportValidity(); return false; }
                    cb.value = cb.value.slice(0,12)+computeEAN13(cb.value.slice(0,12));
                } else { cb.setCustomValidity(''); }
            } else if(cb.value.length===0){ cb.setCustomValidity(''); }
            else{
                if(ENFORCE_EAN13){ cb.setCustomValidity('Entrez 12 ou 13 chiffres.'); cb.reportValidity && cb.reportValidity(); return false; }
                cb.value=''; cb.setCustomValidity('');
            }
            return true;
        }

        function submitForm(){
            const form = document.getElementById(ns+'medForm');
            if(!form) return;
            if(!prepareBarcode()) return;
            if(form.checkValidity && !form.checkValidity()){ form.reportValidity && form.reportValidity(); return; }
            form.requestSubmit ? form.requestSubmit() : form.submit();
        }

        // Prefill helpers
        const setVal = (idNoNS, val) => {
            const el = document.getElementById(ns + idNoNS);
            if(el) el.value = (val===undefined || val===null || val==='null') ? '' : val;
        };

        function wireEANHint(){
            const cb = document.getElementById(ns+'codeBarre');
            const hint = document.getElementById(ns+'eanHint');
            if (cb && hint){
                const updateHint = () => {
                    const raw = cleanDigits(cb.value||'');
                    if(raw.length===12) hint.textContent = 'Calcul : ' + raw + computeEAN13(raw);
                    else if(raw.length===13) hint.textContent = isValidEAN13(raw) ? 'EAN-13 valide'
                        : 'EAN-13 invalide — devrait finir par ' + computeEAN13(raw.slice(0,12));
                    else hint.textContent = '';
                };
                cb.addEventListener('input', updateHint);
                updateHint();
            }
        }

        function populate(mode, data){
            const form = document.getElementById(ns+'medForm');
            if(form) form.action = (mode==='add') ? addURL : updateURL;

            if(mode==='edit'){
                setVal('medicamentId', data.id);
                setVal('code',        data.code);
                setVal('codeBarre',   data.codebarre);
                setVal('nom',         data.nom);
                setVal('prix',        data.prix);
                setVal('categorie',   data.categorie);
                setVal('seuilMinimum',data.seuil);
                setVal('description', data.description);
            } else {
                ['medicamentId','code','codeBarre','nom','prix','categorie','seuilMinimum','description']
                    .forEach(k => setVal(k, ''));
            }

            const first = document.getElementById(ns+'code');
            if (first) first.focus();

            wireEANHint();
        }

        function openForm(mode, data){
            const tpl = document.getElementById('medFormTPL');
            const bodyHTML = tpl ? tpl.innerHTML : '<div>Form template introuvable</div>';

            Liferay.Util.openModal({
                id: modalId,
                title: mode==='add' ? 'Ajouter un Médicament' : ('Modifier : ' + (data.nom || '')),
                size: 'lg',
                bodyHTML,
                buttons: [
                    { label: 'Annuler', onClick: ({processClose}) => processClose && processClose() },
                    { label: mode==='add' ? 'Ajouter' : 'Mettre à jour', primary: true, onClick: submitForm }
                ],
                onOpen: () => {
                    // Give Clay a tick to mount, then fill
                    setTimeout(() => populate(mode, data), 0);
                }
            });
        }

        // Event delegation for openers
        document.addEventListener('click', function (e) {
            const trigger = e.target.closest('.js-med-open');
            if (!trigger) return;
            e.preventDefault();

            const mode = trigger.dataset.mode || 'add';
            const data = (mode === 'edit') ? {
                id:          trigger.dataset.id,
                code:        trigger.dataset.code,
                codebarre:   trigger.dataset.codebarre,
                nom:         trigger.dataset.nom,
                prix:        trigger.dataset.prix,
                categorie:   trigger.dataset.categorie,
                seuil:       trigger.dataset.seuil,
                description: trigger.dataset.description
            } : {};

            openForm(mode, data);
        });
    })();
</script>


</body>
</html>
