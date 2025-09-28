<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="gestion_de_pharmacie.model.Utilisateur" %>
<%@ page import="gestion_de_pharmacie.model.Medicament" %>
<%@ page import="gestion_de_pharmacie.model.Commande" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
    List<Utilisateur> fournisseurs = (List<Utilisateur>) request.getAttribute("fournisseurs");
    List<Medicament> medicaments = (List<Medicament>) request.getAttribute("medicaments");
    List<Commande> commandes = (List<Commande>) request.getAttribute("commandes");

    if (fournisseurs == null) fournisseurs = new java.util.ArrayList<>();
    if (medicaments == null) medicaments = new java.util.ArrayList<>();
    if (commandes == null) commandes = new java.util.ArrayList<>();

    NumberFormat money = NumberFormat.getNumberInstance(Locale.FRANCE);
    money.setMinimumFractionDigits(2);
    money.setMaximumFractionDigits(2);

    String mode = (String)request.getAttribute("mode");
    if (mode == null) mode = "list";
    boolean editMode = "edit".equals(mode);
    request.setAttribute("editMode", editMode);   // <-- make it visible to EL

    // Prefill map for edit
    gestion_de_pharmacie.model.Commande cmdEdit =
            (gestion_de_pharmacie.model.Commande) request.getAttribute("commande");
    java.util.List<gestion_de_pharmacie.model.CommandeDetail> detEdit =
            (java.util.List<gestion_de_pharmacie.model.CommandeDetail>) request.getAttribute("details");

    java.util.Map<Long, Integer> qtyMap = new java.util.HashMap<>();
    if (editMode && detEdit != null) {
        for (gestion_de_pharmacie.model.CommandeDetail d : detEdit) {
            qtyMap.put(d.getIdMedicament(), d.getQuantite());
        }
    }
    long selectedFournisseurId = (cmdEdit != null) ? cmdEdit.getIdUtilisateur() : 0L;
%>

<portlet:actionURL name="createCommande" var="createCommandeURL" />
<portlet:actionURL name="updateCommande" var="updateCommandeURL" />

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8" />
    <title>Commandes</title>
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
        .card{background:var(--white);border:1px solid var(--border);border-radius:12px;padding:16px;box-shadow:0 6px 18px rgba(17,24,39,.06);margin-bottom:16px}
        .controls{display:flex;gap:12px;align-items:center;flex-wrap:wrap;margin-bottom:10px}
        .controls input[type="search"]{flex:1;min-width:240px;padding:10px;border:1px solid var(--border);border-radius:10px;background:#fff}
        table{width:100%;border-collapse:collapse; margin-top: 10px;}
        thead th{text-align:left;background:#F8FAFC;color:var(--primary);padding:10px 12px;border-bottom:1px solid var(--border);font-weight:700}
        tbody td{padding:10px 12px; border-bottom: 1px solid var(--border);}
        tr:hover td{background:#F9FAFB}
        .order-grid{display:grid; grid-template-columns: 1fr 1fr; gap:12px}
        .order-grid .full{ grid-column:1/-1 }
        .small{ font-size:0.95rem; color:var(--muted) }
        .modal-overlay { display:none; position:fixed; inset:0; background:rgba(0,0,0,0.5); z-index:1000; align-items:center; justify-content:center; }
        .modal-content { background:#fff; border-radius:10px; max-width:900px; width:90%; padding:16px; max-height:80vh; overflow:auto; }
        td.num, th.num { text-align: right; }
        tfoot td { background:#F8FAFC; font-weight:700; }
        tfoot td:first-child { text-align:right; }
        .icon-btn { width:34px;height:34px;display:inline-flex;align-items:center;justify-content:center;border-radius:10px }
    </style>
</head>
<body>

<div class="wrap">
    <div class="header">
        <h2>🧾 Commandes</h2>
        <div class="header-actions">
            <div class="small">Créer une commande auprès d'un fournisseur</div>
            <button type="button" class="btn btn-primary js-order-open">+ Nouvelle commande</button>
        </div>
    </div>

    <liferay-ui:success key="commande-created-success" message="Commande créée avec succès !" />
    <liferay-ui:success key="commande-updated-success" message="Commande mise à jour." />
    <liferay-ui:success key="commande-deleted-success" message="Commande supprimée." />
    <liferay-ui:error key="commande-create-error" message="Erreur lors de la création de la commande." />
    <liferay-ui:error key="commande-update-error" message="Erreur lors de la mise à jour de la commande." />
    <liferay-ui:error key="commande-no-items" message="Aucun médicament sélectionné." />
    <liferay-ui:success key="commande-cancel-success" message="Commande annulée." />
    <liferay-ui:error   key="commande-cancel-error" message="Échec de l’annulation." />
    <liferay-ui:error   key="commande-cancel-not-allowed" message="Impossible d’annuler : la commande a déjà été traitée." />



<%-- DETAIL PAGE (separate screen) --%>
    <% if ("detail".equals(mode)) {
        Commande cmd = (Commande) request.getAttribute("commande");
        Utilisateur fournisseur = (Utilisateur) request.getAttribute("fournisseur");
        java.util.List<gestion_de_pharmacie.model.CommandeDetail> details =
                (java.util.List<gestion_de_pharmacie.model.CommandeDetail>) request.getAttribute("details");

        java.text.NumberFormat money2 = java.text.NumberFormat.getNumberInstance(java.util.Locale.FRANCE);
        money2.setMinimumFractionDigits(2); money2.setMaximumFractionDigits(2);
    %>

    <div class="wrap">
        <div class="header">
            <h2>🧾 Bon de commande #<%= (cmd!=null)? cmd.getIdCommande() : "-" %></h2>
            <div class="header-actions">
                <portlet:renderURL var="backURL">
                    <portlet:param name="mvcPath" value="/view.jsp"/>
                    <portlet:param name="mode" value="list"/>
                </portlet:renderURL>
                <a class="btn" href="${backURL}">← Retour</a>

                <portlet:resourceURL id="downloadCommandePdf" var="pdfUrlDetail">
                    <portlet:param name="commandeId" value="<%= String.valueOf((cmd!=null)? cmd.getIdCommande():0) %>" />
                </portlet:resourceURL>
                <a class="btn" href="${pdfUrlDetail}" style="margin-left:8px;">PDF</a>
            </div>
        </div>

        <div class="card">
            <div class="small">Fournisseur</div>
            <div><%= (fournisseur!=null)? (fournisseur.getNom()+" "+fournisseur.getPrenom()) : "-" %></div>
            <div class="small" style="margin-top:8px;">Date</div>
            <div><% if (cmd!=null && cmd.getDateCommande()!=null) { %>
                <fmt:formatDate value="<%= cmd.getDateCommande() %>" pattern="dd/MM/yyyy HH:mm" />
                <% } else { %>-<% } %>
            </div>
            <div class="small" style="margin-top:8px;">Statut</div>
            <div><%= (cmd!=null)? cmd.getStatut() : "-" %></div>
        </div>

        <div class="card">
            <table>
                <thead>
                <tr>
                    <th class="num">#</th>
                    <th>Médicament</th>
                    <th class="num">Prix</th>
                    <th class="num">Qté</th>
                    <th class="num">Sous-total</th>
                </tr>
                </thead>
                <tbody>
                <%
                    int i=1; double total=0;
                    if (details != null) for (gestion_de_pharmacie.model.CommandeDetail d : details) {
                        gestion_de_pharmacie.model.Medicament m =
                                gestion_de_pharmacie.service.MedicamentLocalServiceUtil.fetchMedicament(d.getIdMedicament());
                        String medName = (m!=null)? m.getNom() : ("ID="+d.getIdMedicament());
                        double st = d.getPrixUnitaire()*d.getQuantite(); total += st;
                %>
                <tr>
                    <td class="num"><%= i++ %></td>
                    <td><%= HtmlUtil.escape(medName) %></td>
                    <td class="num"><%= money2.format(d.getPrixUnitaire()) %> DH</td>
                    <td class="num"><%= d.getQuantite() %></td>
                    <td class="num"><%= money2.format(st) %> DH</td>
                </tr>
                <% } %>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="4">TOTAL</td>
                    <td class="num"><%= money2.format(total) %> DH</td>
                </tr>
                </tfoot>
            </table>

        </div>
    </div>

    <%  return; } %>

    <%-- LIST --%>
    <div class="card">
        <div class="controls">
            <input id="searchOrders" type="search" placeholder="Rechercher (id, fournisseur, statut, date)..." />
            <span id="rangeOrders" class="small"></span>
        </div>

        <table id="ordersTable">
            <thead>
            <tr>
                <th class="sortable" data-sort-key="id">ID <span class="arrow">↕</span></th>
                <th class="sortable" data-sort-key="fournisseur">Fournisseur <span class="arrow">↕</span></th>
                <th class="sortable" data-sort-key="date">Date <span class="arrow">↕</span></th>
                <th class="sortable" data-sort-key="statut">Statut <span class="arrow">↕</span></th>
                <th class="sortable" data-sort-key="montant">Montant (DH) <span class="arrow">↕</span></th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <% for (Commande c : commandes) {
                String fournisseurName = "-";
                try {
                    Utilisateur f = gestion_de_pharmacie.service.UtilisateurLocalServiceUtil.getUtilisateur(c.getIdUtilisateur());
                    fournisseurName = f.getNom() + " " + f.getPrenom();
                } catch (Exception ignore) {}
                long ts = (c.getDateCommande() != null) ? c.getDateCommande().getTime() : 0L;
            %>
            <tr>
                <td data-key="id" data-sort="<%= c.getIdCommande() %>"><%= c.getIdCommande() %></td>
                <td data-key="fournisseur"><%= HtmlUtil.escape(fournisseurName) %></td>
                <td data-key="date" data-sort="<%= ts %>">
                    <% if (c.getDateCommande() != null) { %>
                    <fmt:formatDate value="<%= c.getDateCommande() %>" pattern="dd/MM/yyyy HH:mm" />
                    <% } else { %> - <% } %>
                </td>
                <td data-key="statut"><%= HtmlUtil.escape(c.getStatut()) %></td>
                <td data-key="montant" data-sort="<%= c.getMontantTotal() %>"><%= money.format(c.getMontantTotal()) %></td>
                <td>
                    <%-- View --%>
                    <portlet:renderURL var="viewURL" copyCurrentRenderParameters="false">
                        <portlet:param name="mvcPath" value="/view.jsp" />
                        <portlet:param name="mode" value="detail" />
                        <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                    </portlet:renderURL>
                    <a class="btn icon-btn" href="${viewURL}" data-senna-off="true" title="Voir">👁️</a>

                    <%-- PDF --%>
                    <liferay-portlet:resourceURL id="downloadCommandePdf" var="pdfUrlRow">
                        <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                    </liferay-portlet:resourceURL>
                    <a class="btn icon-btn" href="${pdfUrlRow}" title="Télécharger PDF" style="margin-left:6px;">📄</a>

                    <%
                        String statutUpper = (c.getStatut() != null) ? c.getStatut().trim().toUpperCase() : "";
                        boolean editable   = commande.web.constants.CommandeStatus.isEditable(statutUpper);     // CREATED
                        boolean cancelable = commande.web.constants.CommandeStatus.isCancelable(statutUpper);   // CREATED,PENDING
                        boolean deletable  = "CREATED".equals(statutUpper); // or widen to CREATED||PENDING if you want
                    %>

                    <%-- Edit (CREATED) --%>
                    <c:if test="<%= editable %>">
                        <portlet:renderURL var="editURL" copyCurrentRenderParameters="false">
                            <portlet:param name="mvcPath" value="/view.jsp" />
                            <portlet:param name="mode" value="edit" />
                            <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                        </portlet:renderURL>
                        <a class="btn icon-btn" href="${editURL}" data-senna-off="true" title="Modifier" style="margin-left:6px;">✏️</a>
                    </c:if>

                    <%-- Send (CREATED) --%>
                    <c:if test="<%= "CREATED".equals(statutUpper) %>">
                        <portlet:actionURL name="sendCommande" var="sendURL">
                            <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                        </portlet:actionURL>
                        <form action="${sendURL}" method="post" style="display:inline;" data-senna-off="true">
                            <button type="submit" class="btn icon-btn" title="Envoyer" style="margin-left:6px;">📤</button>
                        </form>
                    </c:if>

                    <%-- Cancel (CREATED or PENDING) --%>
                    <c:if test="<%= cancelable %>">
                        <portlet:actionURL name="cancelCommande" var="cancelURL">
                            <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                        </portlet:actionURL>
                        <form action="${cancelURL}" method="post" style="display:inline;" data-senna-off="true"
                              onsubmit="return confirm('Annuler la commande #<%= c.getIdCommande() %> ?');">
                            <button type="submit" class="btn icon-btn" title="Annuler" style="margin-left:6px;">🚫</button>
                        </form>
                    </c:if>

                    <%-- Delete (CREATED only, or broaden if you want) --%>
                    <c:if test="<%= deletable %>">
                        <portlet:actionURL name="deleteCommande" var="deleteCommandeURL">
                            <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                        </portlet:actionURL>
                        <form action="${deleteCommandeURL}" method="post" style="display:inline;"
                              data-senna-off="true"
                              onsubmit="return confirm('Supprimer la commande #<%= c.getIdCommande() %> ?');">
                            <button type="submit" class="btn icon-btn" title="Supprimer" style="margin-left:6px;">🗑️</button>
                        </form>
                    </c:if>
                </td>

            </tr>
            <% } %>
            </tbody>
        </table>

        <div class="pager" id="pagerOrders"></div>
    </div>
</div>

<!-- Modal (create & edit) -->
<%-- compute once at top of JSP, or just inline EL as below --%>
<div id="orderModal"
     class="modal-overlay"
     aria-hidden="${editMode ? 'false' : 'true'}"
     style="${editMode ? 'display:flex' : 'display:none'}">

    <div class="modal-content" role="dialog" aria-modal="true">
        <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:12px;">
            <h3 style="margin:0;"><%= editMode ? ("Modifier la commande #" + (cmdEdit!=null?cmdEdit.getIdCommande():"-")) : "Nouvelle commande" %></h3>
            <button type="button" onclick="closeModal()" style="font-size:20px;border:none;background:none;cursor:pointer;">×</button>
        </div>

<%--        <form id="orderForm"
              method="post"
              action="${editMode ? updateCommandeURL : createCommandeURL}"
              enctype="multipart/form-data"
              class="card">--%>
        <form id="orderForm"
              method="post"
              action="${editMode ? updateCommandeURL : createCommandeURL}"
              enctype="multipart/form-data"
              class="card"
              data-senna-off="true">   <%-- <-- add this --%>



            <c:if test="${editMode}">
                <input type="hidden" name="commandeId"
                       value="${commande != null ? commande.idCommande : 0}" />
            </c:if>

            <liferay-portlet:renderURL var="afterSaveURL"
                                       portletName="dashboard_web_DashboardWebPortlet_INSTANCE_oddl">
                <portlet:param name="mvcPath" value="/common/dashboard.jsp" />
                <portlet:param name="section" value="commandes" />
            </liferay-portlet:renderURL>

            <input type="hidden" name="redirect" value="${afterSaveURL}" />



                <div class="order-grid">
                <div class="full">
                    <label for="fournisseurId" style="font-weight:600">Fournisseur</label>
                    <select id="fournisseurId" name="fournisseurId" class="form-control" required>
                        <option value="">-- Choisir un fournisseur --</option>
                        <% for (Utilisateur f : fournisseurs) { %>
                        <option value="<%= f.getIdUtilisateur() %>"
                                <%= (editMode && f.getIdUtilisateur()==selectedFournisseurId) ? "selected" : "" %>>
                            <%= HtmlUtil.escape(f.getNom() + " " + f.getPrenom()) %>
                        </option>
                        <% } %>
                    </select>
                </div>

                <div class="full">
                    <label style="font-weight:600">Médicaments</label>
                    <div style="border:1px solid var(--border); padding:12px; border-radius:8px; max-height:320px; overflow:auto;">
                        <table style="width:100%;border-collapse:collapse;">
                            <thead><tr><th>Sel</th><th>Médicament</th><th>Prix</th><th>Quantité</th></tr></thead>
                            <tbody>
                            <% for (Medicament m : medicaments) {
                                Integer q = qtyMap.get(m.getIdMedicament());
                                boolean checked = (q != null && q > 0);
                                int value = checked ? q : 1;
                            %>
                            <tr>
                                <td><input type="checkbox" name="medicamentId" value="<%= m.getIdMedicament() %>" <%= checked ? "checked" : "" %> /></td>
                                <td><%= HtmlUtil.escape(m.getNom()) %></td>
                                <td><%= money.format(m.getPrixUnitaire()) %></td>
                                <td><input type="number" name="quantite_<%= m.getIdMedicament() %>" value="<%= value %>" min="1" class="form-control" style="width:84px;" /></td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <button type="submit" id="orderFormSubmit" style="display:none;"></button>
        </form>

        <div style="margin-top:14px;text-align:right;">
            <button type="button" class="btn" onclick="closeModal()" style="margin-right:8px;">Annuler</button>
            <button type="button" class="btn btn-primary" onclick="submitOrderForm()"><%= editMode ? "Enregistrer" : "Créer" %></button>
        </div>
    </div>
</div>

<script>
    function openModal(){const m=document.getElementById('orderModal');m.style.display='flex';m.setAttribute('aria-hidden','false')}
    function closeModal(){const m=document.getElementById('orderModal');m.style.display='none';m.setAttribute('aria-hidden','true')}
    document.addEventListener('click',function(e){if(e.target.closest('.js-order-open')){e.preventDefault();openModal()} if(e.target===document.getElementById('orderModal')){closeModal()}})

    function submitOrderForm(){
        const form=document.getElementById('orderForm');
        const supplier=form.querySelector('select[name="fournisseurId"]');
        const checked=Array.from(form.querySelectorAll('input[name="medicamentId"]:checked'));
        if(!supplier||supplier.value===''){alert('Veuillez choisir un fournisseur.');supplier&&supplier.focus();return;}
        if(checked.length===0){alert('Veuillez sélectionner au moins un médicament.');return;}
        if(typeof form.requestSubmit==='function') form.requestSubmit(); else form.submit();
    }
    // Auto-open on edit
    <% if (editMode) { %> openModal(); <% } %>
</script>
<script>
    const isEdit = ${editMode ? "true" : "false"};
    const hasCmd = ${requestScope.commande != null ? "true" : "false"};
    if (isEdit && hasCmd) openModal();
</script>
</body>
</html>
