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
        :root{
            --primary:#1E3A8A; --secondary:#3B82F6; --accent:#10B981;
            --bg:#F3F4F6; --white:#FFFFFF; --text:#111827; --muted:#6B7280; --border:#E5E7EB;
        }
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
        table{width:100%;border-collapse:collapse}
        thead th{text-align:left;background:#F8FAFC;color:var(--primary);padding:10px 12px;border-bottom:1px solid var(--border);font-weight:700}
        tbody td{padding:10px 12px;border-bottom:1px solid var(--border)}
        tr:hover td{background:#F9FAFB}
        th.sortable{cursor:pointer;user-select:none}
        th.sortable .arrow{font-size:12px;opacity:.6;margin-left:6px}
        .desc{max-width:380px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;color:#334155}
        .pager{display:flex;gap:8px;align-items:center;flex-wrap:wrap;margin-top:12px}
        .pager .btn{padding:6px 12px}
        .pager .btn.btn-primary{background:var(--secondary);border-color:var(--secondary)}

        /* Form fields in modal */
        .form-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:12px}
        .form-grid .fld{display:flex;flex-direction:column;gap:6px}
        .form-grid label{font-weight:600;color:var(--primary)}
        .form-grid input,.form-grid textarea,
        .modal-body .form-grid input,.modal-body .form-grid textarea{
            width:100%;box-sizing:border-box;background:#fff;color:var(--text);
            border:1px solid var(--border);border-radius:10px;padding:10px;
        }
        .form-grid input:focus,.form-grid textarea:focus{
            outline:none;border-color:var(--secondary);box-shadow:0 0 0 3px rgba(59,130,246,.15);
        }
        .form-grid input[type="number"]::-webkit-inner-spin-button,
        .form-grid input[type="number"]::-webkit-outer-spin-button{ -webkit-appearance:none; margin:0; }
        .form-grid input[type="number"]{ -moz-appearance:textfield; }
        .med-add-modal { padding-top: 6px; }

        .med-add-grid{
            display:grid;
            grid-template-columns:repeat(auto-fit,minmax(240px,1fr));
            gap:12px;
        }

        /* field wrapper */
        .med-add-grid .fld{ display:flex; flex-direction:column; gap:6px; }

        /* labels */
        .med-add-grid label{ font-weight:600; color:var(--primary); }

        /* inputs */
        .med-add-grid input,
        .med-add-grid textarea{
            display:block !important;
            width:100% !important;
            box-sizing:border-box !important;
            background:#fff !important;
            color:var(--text) !important;
            border:1px solid var(--border) !important;
            border-radius:10px !important;
            padding:10px !important;
            line-height:1.2 !important;
            min-height:40px;
        }

        /* focus */
        .med-add-grid input:focus,
        .med-add-grid textarea:focus{
            outline:none;
            border-color:var(--secondary) !important;
            box-shadow:0 0 0 3px rgba(59,130,246,.15) !important;
        }

        /* number spinners off for a cleaner UI */
        .med-add-grid input[type="number"]::-webkit-inner-spin-button,
        .med-add-grid input[type="number"]::-webkit-outer-spin-button{ -webkit-appearance:none; margin:0; }
        .med-add-grid input[type="number"]{ -moz-appearance:textfield; }

        /* make the textarea taller by default */
        .med-add-grid textarea{ min-height:96px; resize:vertical; }
        /* Actions column: keep buttons on one line with spacing */
        #medsTable td:last-child{
            display:flex;
            align-items:center;
            gap:8px;             /* space between Edit/Supprimer */
            white-space:nowrap;  /* prevent wrapping to next line */
        }
        #medsTable td:last-child form{
            margin:0;            /* remove default form margins */
            display:inline;      /* stay inline with the link */
        }
        #medsTable td:last-child .btn{
            white-space:nowrap;  /* button text won’t wrap either */
        }
        #<portlet:namespace/>medModal .modal-dialog { max-width: 1000px; width: 92vw; }
        #<portlet:namespace/>medModal .modal-body { max-height: calc(100vh - 180px); overflow: auto; }

    </style>
</head>
<body>
<div class="wrap">
    <div class="header">
        <h2>💊 Liste des Médicaments</h2>
        <div class="header-actions">
            <div class="count" id="topCount"></div>
<%--
            <button id="openAddModal" type="button" class="btn btn-primary">+ Ajouter</button>
--%>
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
                <td>
<%--                    <portlet:renderURL var="editURL"
                                       windowState="<%= LiferayWindowState.POP_UP.toString() %>">
                        <portlet:param name="mvcPath" value="/edit_medicament.jsp" />
                        <portlet:param name="medicamentId" value="<%= String.valueOf(m.getIdMedicament()) %>" />
                        <portlet:param name="modal" value="1" />
                    </portlet:renderURL>


                    <a href="${editURL}" class="btn btn-primary btn-sm js-open-edit"
                       data-title="Modifier : <%= HtmlUtil.escape(m.getNom()) %>">Éditer</a>--%>
                    <a href="#"
                       class="btn btn-primary btn-sm js-med-open"
                       data-mode="edit"
                       data-id="<%= m.getIdMedicament() %>"
                       data-code="<%= HtmlUtil.escapeAttribute(String.valueOf(m.getCode())) %>"
                       data-codebarre="<%= HtmlUtil.escapeAttribute(String.valueOf(m.getCodeBarre())) %>"
                       data-nom="<%= HtmlUtil.escapeAttribute(String.valueOf(m.getNom())) %>"
                       data-prix="<%= m.getPrixUnitaire() %>"
                       data-categorie="<%= HtmlUtil.escapeAttribute(String.valueOf(m.getCategorie())) %>"
                       data-seuil="<%= m.getSeuilMinimum() %>"
                       data-description="<%= HtmlUtil.escapeAttribute(String.valueOf(m.getDescription())) %>">
                        Éditer
                    </a>



    <portlet:actionURL name="deleteMedicament" var="deleteURL">
                        <portlet:param name="medicamentId" value="<%= String.valueOf(m.getIdMedicament()) %>" />
                    </portlet:actionURL>
                    <form action="${deleteURL}" method="post" style="display:inline;" onsubmit="return confirm('Voulez-vous vraiment supprimer ce médicament ?');">
                        <button type="submit" class="btn btn-danger btn-sm">Supprimer</button>
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
    <!-- Support both legacy and new keys -->
    <liferay-ui:error   key="medicament-already-exists"        message="Un médicament avec ce nom existe déjà." />
    <liferay-ui:error   key="medicament-required"               message="Veuillez renseigner au minimum le nom et le code." />
    <liferay-ui:error   key="medicament-code-exists"            message="Ce code interne existe déjà." />
    <liferay-ui:error   key="medicament-barcode-invalid"        message="Le code-barres doit être un EAN-13 valide." />
    <liferay-ui:error   key="medicament-barcode-exists"         message="Ce code-barres existe déjà." />
</div>

<!-- Modal template -->
<%--<template id="addMedModalTPL">
    <div class="med-add-modal">
        <portlet:actionURL name="ajouterMedicament" var="addMedicamentURL" />
        <form action="${addMedicamentURL}"
              method="post"
              id="<portlet:namespace/>addMedForm"
              class="med-add-grid">


            <div class="fld">
                <label for="<portlet:namespace/>code">Code (interne)</label>
                <input id="<portlet:namespace/>code" name="<portlet:namespace/>code"
                       maxlength="64" required placeholder="Ex. AMOX500-CAP-20" />
            </div>

            <div class="fld">
                <label for="<portlet:namespace/>codeBarre">Code-barres (EAN-13)</label>
                <input id="<portlet:namespace/>codeBarre"
                       name="<portlet:namespace/>codeBarre"
                       inputmode="numeric"
                       autocomplete="off"
                       pattern="[0-9]{12,13}"
                maxlength="13"
                placeholder="12 ou 13 chiffres (auto-complété)"/>

                <small id="<portlet:namespace/>eanHint" style="color:#6B7280"></small>
            </div>


            <div class="fld">
                <label for="<portlet:namespace/>nom">Nom</label>
                <input id="<portlet:namespace/>nom" name="<portlet:namespace/>nom" required />
            </div>

            <div class="fld">
                <label for="<portlet:namespace/>prix">Prix Unitaire (DH)</label>
                <input id="<portlet:namespace/>prix" name="<portlet:namespace/>prix"
                       type="number" step="0.01" min="0" required />
            </div>

            <div class="fld">
                <label for="<portlet:namespace/>categorie">Catégorie</label>
                <input id="<portlet:namespace/>categorie" name="<portlet:namespace/>categorie" />
            </div>

            <div class="fld">
                <label for="<portlet:namespace/>seuilMinimum">Seuil Minimum</label>
                <input id="<portlet:namespace/>seuilMinimum" name="<portlet:namespace/>seuilMinimum"
                       type="number" min="0" />
            </div>

            <div class="fld" style="grid-column:1/-1">
                <label for="<portlet:namespace/>description">Description</label>
                <textarea id="<portlet:namespace/>description" name="<portlet:namespace/>description"></textarea>
            </div>
        </form>
    </div>
</template>--%>
<template id="medFormTPL">
    <form id="<portlet:namespace/>medForm" class="med-add-grid" method="post">
        <!-- hidden for EDIT -->
        <input type="hidden" id="<portlet:namespace/>medicamentId" name="<portlet:namespace/>medicamentId"/>

        <div class="fld">
            <label for="<portlet:namespace/>code">Code (interne)</label>
            <input id="<portlet:namespace/>code" name="<portlet:namespace/>code" maxlength="64" required placeholder="Ex. AMOX500-CAP-20" />
        </div>

        <div class="fld">
            <label for="<portlet:namespace/>codeBarre">Code-barres (EAN-13)</label>
            <input id="<portlet:namespace/>codeBarre" name="<portlet:namespace/>codeBarre"
                   inputmode="numeric" autocomplete="off" pattern="[0-9]{12,13}" maxlength="13"
                   placeholder="12 ou 13 chiffres (auto-complété)" />
            <small id="<portlet:namespace/>eanHint" style="color:#6B7280"></small>
        </div>

        <div class="fld">
            <label for="<portlet:namespace/>nom">Nom</label>
            <input id="<portlet:namespace/>nom" name="<portlet:namespace/>nom" required />
        </div>

        <div class="fld">
            <label for="<portlet:namespace/>prix">Prix Unitaire (DH)</label>
            <input id="<portlet:namespace/>prix" name="<portlet:namespace/>prix" type="number" step="0.01" min="0" required />
        </div>

        <div class="fld">
            <label for="<portlet:namespace/>categorie">Catégorie</label>
            <input id="<portlet:namespace/>categorie" name="<portlet:namespace/>categorie" />
        </div>

        <div class="fld">
            <label for="<portlet:namespace/>seuilMinimum">Seuil Minimum</label>
            <input id="<portlet:namespace/>seuilMinimum" name="<portlet:namespace/>seuilMinimum" type="number" min="0" />
        </div>

        <div class="fld" style="grid-column:1/-1">
            <label for="<portlet:namespace/>description">Description</label>
            <textarea id="<portlet:namespace/>description" name="<portlet:namespace/>description"></textarea>
        </div>
    </form>
</template>

<script>
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
    (function () {
        const ns = '<portlet:namespace/>';
        const modalId = ns + 'medModal';
        const addURL = '${addMedURL}';
        const updateURL = '${updateMedURL}';

        // --- EAN helpers ---
        const ENFORCE_EAN13 = false; // set true in prod
        const cleanDigits = s => (s || '').replace(/\D/g, '');
        function computeEAN13(b12){ let s=0; for(let i=0;i<12;i++){const d=b12.charCodeAt(i)-48; s+=(i%2?3:1)*d;} return (10-(s%10))%10; }
        function isValidEAN13(s){ return /^[0-9]{13}$/.test(s) && (s.charCodeAt(12)-48)===computeEAN13(s.slice(0,12)); }
        function prepareBarcode(form){
            const cb = form.querySelector('[name="'+ns+'codeBarre"]');
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
            const form = document.querySelector('#'+modalId+' form#'+ns+'medForm');
            if(!form) return;
            if(!prepareBarcode(form)) return;
            if(form.checkValidity && !form.checkValidity()){ form.reportValidity && form.reportValidity(); return; }
            form.requestSubmit ? form.requestSubmit() : form.submit();
        }

        // Wait until the modal's form is actually inserted
        function whenFormInDOM(cb){
            const obs = new MutationObserver(() => {
                const form = document.querySelector('#'+modalId+' form#'+ns+'medForm');
                if(form){
                    obs.disconnect();
                    cb(form, document.getElementById(modalId));
                }
            });
            obs.observe(document.body, {childList:true, subtree:true});
        }

        function openForm(mode, data){
            const tpl = document.getElementById('medFormTPL');

            Liferay.Util.openModal({
                id: modalId,
                title: mode==='add' ? 'Ajouter un Médicament' : ('Modifier : ' + (data.nom || '')),
                size: 'lg',
                bodyHTML: tpl.innerHTML,
                buttons: [
                    { label: 'Annuler', onClick: ({processClose}) => processClose && processClose() },
                    { label: mode==='add' ? 'Ajouter' : 'Mettre à jour', primary: true, onClick: submitForm }
                ]
            });

            whenFormInDOM((form, modalEl) => {
                // choose action
                form.action = (mode==='add') ? addURL : updateURL;

                const norm = v => (v===undefined || v===null || v==='null') ? '' : v;
                const set = (name, val) => {
                    const el = modalEl.querySelector('[name="'+ns+name+'"]');
                    if(el) el.value = norm(val);
                };

                if(mode==='edit'){
                    set('medicamentId', data.id);
                    set('code',        data.code);
                    set('codeBarre',   data.codebarre);
                    set('nom',         data.nom);
                    set('prix',        data.prix);
                    set('categorie',   data.categorie);
                    set('seuilMinimum',data.seuil);
                    set('description', data.description);
                }else{
                    // clear for add
                    ['medicamentId','code','codeBarre','nom','prix','categorie','seuilMinimum','description']
                        .forEach(k => set(k, ''));
                }

                // EAN hint
                const cb = modalEl.querySelector('[name="'+ns+'codeBarre"]');
                const hint = modalEl.querySelector('#'+ns+'eanHint');
                if(cb && hint){
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
            });
        }

        // Openers (Add + Edit)
        document.querySelectorAll('.js-med-open').forEach(el=>{
            el.addEventListener('click', e=>{
                e.preventDefault();
                const mode = el.dataset.mode || 'add';
                const data = (mode==='edit') ? {
                    id: el.dataset.id,
                    code: el.dataset.code,
                    codebarre: el.dataset.codebarre,
                    nom: el.dataset.nom,
                    prix: el.dataset.prix,
                    categorie: el.dataset.categorie,
                    seuil: el.dataset.seuil,
                    description: el.dataset.description
                } : {};
                openForm(mode, data);
            });
        });
    })();
</script>


</body>
</html>
