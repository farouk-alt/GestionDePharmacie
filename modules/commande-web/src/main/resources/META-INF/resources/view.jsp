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

    Boolean isFournisseur = (Boolean) request.getAttribute("isFournisseur");
    if (isFournisseur == null) isFournisseur = false;
    Long currentFournisseurId = (Long) request.getAttribute("currentFournisseurId");
    if (currentFournisseurId == null) currentFournisseurId = 0L;

    gestion_de_pharmacie.model.Commande cmdEdit = (gestion_de_pharmacie.model.Commande) request.getAttribute("commande");
    java.util.List<gestion_de_pharmacie.model.CommandeDetail> detEdit = (java.util.List<gestion_de_pharmacie.model.CommandeDetail>) request.getAttribute("details");

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
        table{width:100%;border-collapse:collapse; margin-top: 10px;}
        #medTable thead th { position: sticky; top: 0; background: #F8FAFC; z-index: 1; }
        thead th{text-align:left;background:#F8FAFC;color:var(--primary);padding:10px 12px;border-bottom:1px solid var(--border);font-weight:700}
        tbody td{padding:10px 12px; border-bottom: 1px solid var(--border);}
        tr:hover td{background:#F9FAFB}
        .order-grid{display:grid; grid-template-columns: 1fr 1fr; gap:12px}
        .order-grid .full{ grid-column:1/-1 }
        .small{ font-size:0.95rem; color:var(--muted) }
        .modal-overlay { display:none; position:fixed; inset:0; background:rgba(0,0,0,0.5); z-index:1000; align-items:center; justify-content:center; }
        .modal-content{background:#fff; border-radius:12px;max-width:1200px;width:95%;padding:16px;max-height:90vh;overflow:auto;}
        td.num, th.num { text-align: right; }
        tfoot td { background:#F8FAFC; font-weight:700; }
        tfoot td:first-child { text-align:right; }
        .icon-btn { width:34px;height:34px;display:inline-flex;align-items:center;justify-content:center;border-radius:10px }
        .sortable { cursor: pointer; user-select: none; }
        .arrow { opacity: 0.5; margin-left: 4px; }
        .badge {
            padding: 4px 10px;
            border-radius: 12px;
            font-size: 0.8rem;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .badge-created { background: #E0F2FE; color: #0369A1; border: 1px solid #BAE6FD; }
        .badge-pending { background: #FEF3C7; color: #92400E; border: 1px solid #FDE68A; }
        .badge-accepted { background: #D1FAE5; color: #065F46; border: 1px solid #A7F3D0; }
        .badge-refused { background: #FEE2E2; color: #991B1B; border: 1px solid #FECACA; }
        .badge-canceled { background: #F3F4F6; color: #374151; border: 1px solid #D1D5DB; }
        .badge-received { background: #EDE9FE; color: #5B21B6; border: 1px solid #DDD6FE; }
    </style>
</head>
<body>

<div class="wrap">
    <div class="header">
        <h2>Commandes</h2>
        <div class="header-actions">
            <div class="small">Créer une commande auprès d'un fournisseur</div>
            <c:if test="${!isFournisseur}">
                <button type="button" class="btn btn-primary js-order-open">+ Nouvelle commande</button>
            </c:if>
        </div>
    </div>

    <liferay-ui:success key="commande-created-success" message="Commande créée avec succès !" />
    <liferay-ui:success key="commande-updated-success" message="Commande mise à jour." />
    <liferay-ui:success key="commande-deleted-success" message="Commande supprimée." />
    <liferay-ui:error key="commande-create-error" message="Erreur lors de la création de la commande." />
    <liferay-ui:error key="commande-update-error" message="Erreur lors de la mise à jour de la commande." />
    <liferay-ui:error key="commande-no-items" message="Aucun médicament sélectionné." />

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
            <h2>Bon de commande #<%= (cmd!=null)? cmd.getIdCommande() : "-" %></h2>
            <div class="header-actions">
                <portlet:renderURL var="backURL">
                    <portlet:param name="mvcPath" value="/view.jsp"/>
                    <portlet:param name="mode" value="list"/>
                </portlet:renderURL>
                <a class="btn" href="${backURL}">Retour</a>
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
                <% } else { %>-<% } %></div>
            <div class="small" style="margin-top:8px;">Statut</div>
            <div><%= (cmd!=null)? cmd.getStatut() : "-" %></div>
        </div>
        <div class="card">
            <table>
                <thead><tr><th class="num">#</th><th>Médicament</th><th class="num">Prix</th><th class="num">Qté</th><th class="num">Sous-total</th></tr></thead>
                <tbody>
                <% int i=1; double total=0;
                    if (details != null) for (gestion_de_pharmacie.model.CommandeDetail d : details) {
                        gestion_de_pharmacie.model.Medicament m = gestion_de_pharmacie.service.MedicamentLocalServiceUtil.fetchMedicament(d.getIdMedicament());
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
                <tfoot><tr><td colspan="4">TOTAL</td><td class="num"><%= money2.format(total) %> DH</td></tr></tfoot>
            </table>
        </div>
    </div>
    <% return; } %>

    <!-- FIXED: Added missing card wrapper around filters and table -->
    <div class="card">
        <div style="grid-column: 1/-1; display: flex; gap: 10px; flex-wrap: wrap;">
            <div style="flex: 1; min-width: 240px;">
                <label style="display: block; font-size: 0.85rem; color: var(--muted); margin-bottom: 4px;">Recherche</label>
                <input id="searchOrders" type="search" placeholder="ID, fournisseur, statut..." style="width: 100%; padding: 9px 12px; border: 1px solid var(--border); border-radius: 8px; background: #fff;" />
            </div>
            <div style="min-width: 160px;">
                <label for="ordersFrom" style="display: block; font-size: 0.85rem; color: var(--muted); margin-bottom: 4px;">Date début</label>
                <input id="ordersFrom" type="date" style="width: 100%; padding: 8px 12px; border: 1px solid var(--border); border-radius: 8px; background: #fff;" />
            </div>
            <div style="min-width: 160px;">
                <label for="ordersTo" style="display: block; font-size: 0.85rem; color: var(--muted); margin-bottom: 4px;">Date fin</label>
                <input id="ordersTo" type="date" style="width: 100%; padding: 8px 12px; border: 1px solid var(--border); border-radius: 8px; background: #fff;" />
            </div>
            <div style="min-width: 200px;">
                <label for="ordersFournisseur" style="display: block; font-size: 0.85rem; color: var(--muted); margin-bottom: 4px;">Fournisseur</label>
                <select id="ordersFournisseur" style="width: 100%; padding: 8px 12px; border: 1px solid var(--border); border-radius: 8px; background: #fff;">
                    <option value="">Tous les fournisseurs</option>
                </select>
            </div>
            <!-- Add Status Filter -->
            <div style="min-width: 180px;">
                <label for="ordersStatus" style="display: block; font-size: 0.85rem; color: var(--muted); margin-bottom: 4px;">Statut</label>
                <select id="ordersStatus" style="width: 100%; padding: 8px 12px; border: 1px solid var(--border); border-radius: 8px; background: #fff;">
                    <option value="">Tous les statuts</option>
                    <option value="CREATED">Créée</option>
                    <option value="PENDING">En attente</option>
                    <option value="ACCEPTED">Acceptée</option>
                    <option value="REFUSED">Refusée</option>
                    <option value="CANCELED">Annulée</option>
                    <option value="RECEIVED">Reçue</option>
                </select>
            </div>
            <div style="display: flex; align-items: flex-end;">
                <button type="button" id="resetFilters" class="btn" style="padding: 9px 16px; white-space: nowrap;">Réinitialiser</button>
            </div>
        </div>

        <!-- FIXED: Add range display and page size controls -->
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px; margin-top: 16px;">
            <span id="rangeOrders" class="small" style="color: var(--muted);"></span>
            <div style="display: flex; gap: 8px; align-items: center;">
                <label for="ordersPageSize" style="font-size: 0.9rem; color: var(--muted);">Par page:</label>
                <select id="ordersPageSize" style="padding: 6px 10px; border: 1px solid var(--border); border-radius: 8px; background: #fff;">
                    <option value="5">5</option>
                    <option value="10" selected>10</option>
                    <option value="20">20</option>
                    <option value="50">50</option>
                </select>
            </div>
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
                <td data-key="statut">
                    <%
                        String statut = c.getStatut();
                        String badgeClass = "badge";
                        if (statut != null) {
                            String statutLower = statut.toLowerCase();
                            if (statutLower.contains("created")) badgeClass += " badge-created";
                            else if (statutLower.contains("pending")) badgeClass += " badge-pending";
                            else if (statutLower.contains("accepted")) badgeClass += " badge-accepted";
                            else if (statutLower.contains("refused")) badgeClass += " badge-refused";
                            else if (statutLower.contains("canceled")) badgeClass += " badge-canceled";
                            else if (statutLower.contains("received")) badgeClass += " badge-received";
                        }
                    %>
                    <span class="<%= badgeClass %>"><%= HtmlUtil.escape(statut) %></span>
                </td>
                <td data-key="montant" data-sort="<%= c.getMontantTotal() %>"><%= money.format(c.getMontantTotal()) %></td>
                <td>
                    <% String statutUpper = (c.getStatut() != null) ? c.getStatut().trim().toUpperCase() : "";
                        boolean editable = commande.web.constants.CommandeStatus.isEditable(statutUpper);
                        boolean cancelable = commande.web.constants.CommandeStatus.isCancelable(statutUpper);
                        boolean deletable = "CREATED".equals(statutUpper);
                        boolean isMine = (currentFournisseurId != null && currentFournisseurId > 0 && c.getIdUtilisateur() == currentFournisseurId);
                        boolean canRespond = "PENDING".equals(statutUpper) && isMine;
                    %>
                    <portlet:renderURL var="viewURL" copyCurrentRenderParameters="false">
                        <portlet:param name="mvcPath" value="/view.jsp" />
                        <portlet:param name="mode" value="detail" />
                        <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                    </portlet:renderURL>
                    <a class="btn icon-btn" href="${viewURL}" data-senna-off="true" title="Voir">👁</a>

                    <liferay-portlet:resourceURL id="downloadCommandePdf" var="pdfUrlRow">
                        <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                    </liferay-portlet:resourceURL>
                    <a class="btn icon-btn" href="${pdfUrlRow}" title="PDF" style="margin-left:6px;">📄</a>

                    <c:choose>
                        <c:when test="${isFournisseur}">
                            <c:if test="<%= canRespond %>">
                                <portlet:actionURL name="acceptCommande" var="acceptURL">
                                    <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                                </portlet:actionURL>
                                <form action="${acceptURL}" method="post" style="display:inline;" data-senna-off="true" onsubmit="return confirm('Accepter la commande #<%= c.getIdCommande() %> ?');">
                                    <button type="submit" class="btn icon-btn" title="Accepter" style="margin-left:6px;">✅</button>
                                </form>
                                <portlet:actionURL name="rejectCommande" var="rejectURL">
                                    <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                                </portlet:actionURL>
                                <form action="${rejectURL}" method="post" style="display:inline;" data-senna-off="true" onsubmit="return confirm('Refuser la commande #<%= c.getIdCommande() %> ?');">
                                    <button type="submit" class="btn icon-btn" title="Refuser" style="margin-left:6px;">❌</button>
                                </form>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:if test="<%= editable %>">
                                <portlet:renderURL var="editURL" copyCurrentRenderParameters="false">
                                    <portlet:param name="mvcPath" value="/view.jsp" />
                                    <portlet:param name="mode" value="edit" />
                                    <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                                </portlet:renderURL>
                                <a class="btn icon-btn" href="${editURL}" data-senna-off="true" title="Modifier" style="margin-left:6px;">✏</a>
                            </c:if>
                            <c:if test="<%= \"CREATED\".equals(statutUpper) %>">
                                <portlet:actionURL name="sendCommande" var="sendURL">
                                    <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                                </portlet:actionURL>
                                <form action="${sendURL}" method="post" style="display:inline;" data-senna-off="true">
                                    <button type="submit" class="btn icon-btn" title="Envoyer" style="margin-left:6px;">📤</button>
                                </form>
                            </c:if>
                            <c:if test="<%= cancelable %>">
                                <portlet:actionURL name="cancelCommande" var="cancelURL">
                                    <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                                </portlet:actionURL>
                                <form action="${cancelURL}" method="post" style="display:inline;" data-senna-off="true" onsubmit="return confirm('Annuler la commande #<%= c.getIdCommande() %> ?');">
                                    <button type="submit" class="btn icon-btn" title="Annuler" style="margin-left:6px;">🚫</button>
                                </form>
                            </c:if>
                            <c:if test="<%= deletable %>">
                                <portlet:actionURL name="deleteCommande" var="deleteCommandeURL">
                                    <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                                </portlet:actionURL>
                                <form action="${deleteCommandeURL}" method="post" style="display:inline;" data-senna-off="true" onsubmit="return confirm('Supprimer la commande #<%= c.getIdCommande() %> ?');">
                                    <button type="submit" class="btn icon-btn" title="Supprimer" style="margin-left:6px;">🗑</button>
                                </form>
                            </c:if>
                            <c:if test="<%= \"REFUSED\".equals(statutUpper) %>">
                                <portlet:actionURL name="reassignCommande" var="reassignURL" />
                                <form action="${reassignURL}" method="post" style="display:inline; margin-left:6px;" data-senna-off="true" onsubmit="return confirm('Réaffecter la commande #<%= c.getIdCommande() %> ?');">
                                    <input type="hidden" name="commandeId" value="<%= c.getIdCommande() %>" />
                                    <select name="newFournisseurId" required style="padding:4px 6px; border:1px solid var(--border); border-radius:8px;">
                                        <option value="">-- Vers fournisseur --</option>
                                        <% for (Utilisateur f : fournisseurs) { if (f.getIdUtilisateur() == c.getIdUtilisateur()) continue; %>
                                        <option value="<%= f.getIdUtilisateur() %>"><%= HtmlUtil.escape(f.getNom() + " " + f.getPrenom()) %></option>
                                        <% } %>
                                    </select>
                                    <label style="margin-left:6px; font-size:.9rem; color:var(--muted);"><input type="checkbox" name="sendNow" /> Envoyer maintenant</label>
                                    <button type="submit" class="btn icon-btn" title="Réaffecter" style="margin-left:6px;">🔁</button>
                                </form>
                            </c:if>
                            <% boolean canReceive = "ACCEPTED".equals(statutUpper) && !isFournisseur; %>
                            <c:if test="<%= canReceive %>">
                                <portlet:actionURL name="receiveCommande" var="receiveURL">
                                    <portlet:param name="commandeId" value="<%= String.valueOf(c.getIdCommande()) %>" />
                                </portlet:actionURL>
                                <form action="${receiveURL}" method="post" style="display:inline;" data-senna-off="true" onsubmit="return confirm('Marquer la commande #<%= c.getIdCommande() %> comme reçue ?');">
                                    <button type="submit" class="btn icon-btn" title="Réceptionner" style="margin-left:6px;">📦</button>
                                </form>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <div id="pagerOrders" style="display: flex; gap: 8px; justify-content: center; align-items: center; margin-top: 16px; padding-top: 16px; border-top: 1px solid var(--border);"></div>
    </div>
</div>

<c:if test="${!isFournisseur}">
    <div id="orderModal" class="modal-overlay" aria-hidden="${editMode ? 'false' : 'true'}" style="${editMode ? 'display:flex' : 'display:none'}">
        <div class="modal-content" role="dialog" aria-modal="true">
            <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:12px;">
                <h3 style="margin:0;"><%= editMode ? ("Modifier la commande #" + (cmdEdit!=null?cmdEdit.getIdCommande():"-")) : "Nouvelle commande" %></h3>
                <button type="button" onclick="closeModal()" style="font-size:20px;border:none;background:none;cursor:pointer;">×</button>
            </div>
            <form id="orderForm" method="post" action="${editMode ? updateCommandeURL : createCommandeURL}" enctype="multipart/form-data" class="card" data-senna-off="true">
                <c:if test="${editMode}">
                    <input type="hidden" name="commandeId" value="${commande != null ? commande.idCommande : 0}" />
                </c:if>
                <liferay-portlet:renderURL var="afterSaveURL" portletName="dashboard_web_DashboardWebPortlet_INSTANCE_oddl">
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
                            <option value="<%= f.getIdUtilisateur() %>" <%= (editMode && f.getIdUtilisateur()==selectedFournisseurId) ? "selected" : "" %>><%= HtmlUtil.escape(f.getNom() + " " + f.getPrenom()) %></option>
                            <% } %>
                        </select>
                    </div>
                    <div class="full">
                        <label style="font-weight:600">Médicaments</label>
                        <div style="border:1px solid var(--border); padding:12px; border-radius:8px; max-height:420px; overflow:auto;">
                            <div style="display:flex; gap:8px; align-items:center; margin-bottom:10px;">
                                <input id="medSearch" type="search" placeholder="Rechercher un médicament..." style="flex:1; padding:8px; border:1px solid var(--border); border-radius:8px;" />
                                <label style="display:flex; align-items:center; gap:6px; font-size:.95rem; color:var(--muted);"><input id="medOnlySelected" type="checkbox" /> Sélectionnés uniquement</label>
                                <label style="display:flex; align-items:center; gap:6px; font-size:.95rem; color:var(--muted);"><input id="medToggleAllVisible" type="checkbox" /> Tout (visible)</label>
                            </div>
                            <table id="medTable" style="width:100%;border-collapse:collapse;">
                                <thead><tr><th style="width:60px;">Sel</th><th>Médicament</th><th style="width:120px;">Prix</th><th style="width:120px;">Quantité</th></tr></thead>
                                <tbody id="medTableBody">
                                <% for (Medicament m : medicaments) {
                                    Integer q = qtyMap.get(m.getIdMedicament());
                                    boolean checked = (q != null && q > 0);
                                    int value = checked ? q : 1;
                                %>
                                <tr data-name="<%= HtmlUtil.escape(m.getNom()) %>">
                                    <td><input type="checkbox" class="med-check" name="medicamentId" value="<%= m.getIdMedicament() %>" <%= checked ? "checked" : "" %> /></td>
                                    <td class="med-name"><%= HtmlUtil.escape(m.getNom()) %></td>
                                    <td class="med-price" data-price="<%= m.getPrixUnitaire() %>"><%= money.format(m.getPrixUnitaire()) %></td>
                                    <td><input type="number" class="med-qty" name="quantite_<%= m.getIdMedicament() %>" value="<%= value %>" min="1" style="width:84px;" /></td>
                                </tr>
                                <% } %>
                                </tbody>
                            </table>
                            <div id="medFooterBar" style="margin-top:10px; display:flex; justify-content:space-between; align-items:center; color:var(--muted);">
                                <div><span id="medCountVisible">0</span> éléments visibles • <span id="medCountSelected">0</span> sélectionnés</div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Moved Total Estimate to bottom of modal -->
                <div style="margin-top: 16px; padding: 16px; background: var(--bg); border-radius: 8px; border: 1px solid var(--border); display: flex; justify-content: space-between; align-items: center;">
                    <div style="font-size: 1.1rem; font-weight: 600; color: var(--primary);">
                        Total estimé: <strong id="medTotalEstime">0,00 DH</strong>
                    </div>
                    <div style="color: var(--muted); font-size: 0.9rem;">
                        <span id="medCountSelectedMain">0</span> médicament(s) sélectionné(s)
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
</c:if>

<script>
    function openModal(){
        const m=document.getElementById('orderModal');
        if(!m)return;
        m.style.display='flex';
        m.setAttribute('aria-hidden','false');
        setTimeout(()=>{const s=document.getElementById('medSearch');if(s)s.focus();},50);
    }
    function closeModal(){
        const m=document.getElementById('orderModal');
        if(!m)return;
        m.style.display='none';
        m.setAttribute('aria-hidden','true');
    }
    document.addEventListener('click',function(e){
        if(e.target.closest('.js-order-open')){e.preventDefault();openModal();}
        if(e.target===document.getElementById('orderModal')){closeModal();}
    });
    function submitOrderForm(){
        const form=document.getElementById('orderForm');
        if(!form)return;
        const supplier=form.querySelector('select[name="fournisseurId"]');
        const checked=Array.from(form.querySelectorAll('input[name="medicamentId"]:checked'));
        if(!supplier||supplier.value===''){alert('Veuillez choisir un fournisseur.');if(supplier)supplier.focus();return;}
        if(checked.length===0){alert('Veuillez sélectionner au moins un médicament.');return;}
        if(typeof form.requestSubmit==='function')form.requestSubmit();else form.submit();
    }
    <% if(editMode){%>window.addEventListener('DOMContentLoaded',function(){openModal();});<%}%>
</script>

<script>
    (function(){
        const medSearch=document.getElementById('medSearch');
        const onlySel=document.getElementById('medOnlySelected');
        const toggleAll=document.getElementById('medToggleAllVisible');
        const tbody=document.getElementById('medTableBody');
        const elCountV=document.getElementById('medCountVisible');
        const elCountS=document.getElementById('medCountSelected');
        const elCountSMain = document.getElementById('medCountSelectedMain');
        const elTotal=document.getElementById('medTotalEstime');
        if(!medSearch||!tbody)return;

        function normalize(str){return(str||'').toString().toLowerCase().trim();}
        function debounce(fn,wait){let t;return(...args)=>{clearTimeout(t);t=setTimeout(()=>fn(...args),wait);};}

        function applyFilter(){
            const q=normalize(medSearch.value);
            const rows=Array.from(tbody.querySelectorAll('tr'));
            let visible=0;
            rows.forEach(tr=>{
                const name=normalize(tr.dataset.name);
                const chk=tr.querySelector('.med-check');
                const isChecked=chk?chk.checked:false;
                const matches=name.includes(q);
                const keep=matches&&(!onlySel.checked||isChecked);
                tr.style.display=keep?'':'none';
                if(keep)visible++;
            });
            if(elCountV)elCountV.textContent=visible;
            updateSelectedAndTotal();
            syncToggleAllCheckbox();
        }

        function updateSelectedAndTotal(){
            const rows=Array.from(tbody.querySelectorAll('tr'));
            let selected=0,total=0;
            rows.forEach(tr=>{
                const visible=tr.style.display!=='none';
                const chk=tr.querySelector('.med-check');
                const qtyInput=tr.querySelector('.med-qty');
                const priceCell=tr.querySelector('.med-price');
                if(!chk||!qtyInput||!priceCell)return;
                const qty=parseInt(qtyInput.value||'0',10);
                const price=parseFloat(priceCell.dataset.price||'0');
                if(chk.checked)selected++;
                if(visible&&chk.checked&&qty>0)total+=price*qty;
            });
            // Update all selected count elements
            if(elCountS) elCountS.textContent = selected;
            if(elCountSMain) elCountSMain.textContent = selected;
            if(elTotal){
                try{
                    elTotal.textContent=total.toLocaleString('fr-FR',{minimumFractionDigits:2,maximumFractionDigits:2})+' DH';
                }catch(e){
                    elTotal.textContent=(Math.round(total*100)/100)+' DH';
                }
            }
        }

        function syncToggleAllCheckbox(){
            if(!toggleAll)return;
            const visibleRows=Array.from(tbody.querySelectorAll('tr')).filter(tr=>tr.style.display!=='none');
            if(visibleRows.length===0){toggleAll.checked=false;toggleAll.indeterminate=false;return;}
            const checkedCount=visibleRows.filter(tr=>{const chk=tr.querySelector('.med-check');return chk&&chk.checked;}).length;
            toggleAll.checked=checkedCount===visibleRows.length;
            toggleAll.indeterminate=checkedCount>0&&checkedCount<visibleRows.length;
        }

        if(medSearch)medSearch.addEventListener('input',debounce(applyFilter,200));
        if(onlySel)onlySel.addEventListener('change',applyFilter);
        if(toggleAll){
            toggleAll.addEventListener('change',()=>{
                const visibleRows=Array.from(tbody.querySelectorAll('tr')).filter(tr=>tr.style.display!=='none');
                visibleRows.forEach(tr=>{const chk=tr.querySelector('.med-check');if(chk)chk.checked=toggleAll.checked;});
                updateSelectedAndTotal();
                syncToggleAllCheckbox();
            });
        }
        if(tbody){
            tbody.addEventListener('change',e=>{
                if(e.target.classList.contains('med-check')||e.target.classList.contains('med-qty')){
                    updateSelectedAndTotal();
                    syncToggleAllCheckbox();
                }
            });
        }
        setTimeout(()=>applyFilter(),100);
    })();
</script>

<!-- FIXED: Improved orders table initialization -->
<script>
    (function(){
        function initOrdersTable(){
            const table=document.getElementById('ordersTable');
            const tbody=table?table.querySelector('tbody'):null;
            const search=document.getElementById('searchOrders');
            const fromInp=document.getElementById('ordersFrom');
            const toInp=document.getElementById('ordersTo');
            const fouSel=document.getElementById('ordersFournisseur');
            const statusSel=document.getElementById('ordersStatus');
            const rangeEl=document.getElementById('rangeOrders');
            const pager=document.getElementById('pagerOrders');
            const pageSizeSelect=document.getElementById('ordersPageSize');
            const resetBtn=document.getElementById('resetFilters');

            // Check if all required elements exist
            if(!table||!tbody||!search||!fouSel||!pager||!resetBtn){
                console.log('Some elements not found, retrying...');
                setTimeout(initOrdersTable, 100);
                return;
            }

            const allTrs=Array.from(tbody.querySelectorAll('tr'));
            const master=allTrs.map(tr=>{
                const getCell=key=>tr.querySelector('[data-key="'+key+'"]');
                const getText=el=>(el?el.textContent.trim():'');
                const idCell=getCell('id');
                const fouCell=getCell('fournisseur');
                const dateCell=getCell('date');
                const staCell=getCell('statut');
                const monCell=getCell('montant');
                return{
                    tr:tr,
                    id:Number(idCell?idCell.getAttribute('data-sort')||getText(idCell):0)||0,
                    fournisseur:getText(fouCell),
                    fournisseurLower:getText(fouCell).toLowerCase(),
                    dateText:getText(dateCell),
                    dateTs:Number(dateCell?dateCell.getAttribute('data-sort'):0)||0,
                    statut:getText(staCell),
                    statutLower:getText(staCell).toLowerCase(),
                    montant:Number(monCell?monCell.getAttribute('data-sort'):0)||0
                };
            });

            // Populate fournisseur dropdown
            const fournisseurs=[...new Set(master.map(r=>r.fournisseur).filter(f=>f&&f!='-'))].sort();
            fouSel.innerHTML = '<option value="">Tous les fournisseurs</option>';
            fournisseurs.forEach(name=>{
                const opt=document.createElement('option');
                opt.value=name;
                opt.textContent=name;
                fouSel.appendChild(opt);
            });

            let view=master.slice();
            let sortKey=null;
            let sortDir=1;
            let page=1;
            let pageSize=parseInt(pageSizeSelect?pageSizeSelect.value:'10',10);

            function applyFilter(){
                const q=(search.value||'').toLowerCase().trim();
                const fouValue=fouSel.value.trim();
                const statusValue=statusSel?statusSel.value.trim():'';
                const fromDate=fromInp.value?new Date(fromInp.value+'T00:00:00').getTime():null;
                const toDate=toInp.value?new Date(toInp.value+'T23:59:59.999').getTime():null;
                view=master.filter(r=>{
                    if(q){
                        const searchText=r.id+' '+r.fournisseurLower+' '+r.statutLower+' '+r.dateText.toLowerCase();
                        if(!searchText.includes(q))return false;
                    }
                    if(fouValue&&r.fournisseur!==fouValue)return false;
                    if(statusValue&&!r.statut.toLowerCase().includes(statusValue.toLowerCase()))return false;
                    if(fromDate&&r.dateTs<fromDate)return false;
                    if(toDate&&r.dateTs>toDate)return false;
                    return true;
                });
                page=1;
                applySort();
                render();
            }

            function applySort(){
                if(!sortKey)return;
                view.sort((a,b)=>{
                    let va,vb;
                    switch(sortKey){
                        case'id':va=a.id;vb=b.id;break;
                        case'fournisseur':va=a.fournisseurLower;vb=b.fournisseurLower;break;
                        case'date':va=a.dateTs;vb=b.dateTs;break;
                        case'statut':va=a.statutLower;vb=b.statutLower;break;
                        case'montant':va=a.montant;vb=b.montant;break;
                        default:return 0;
                    }
                    if(va<vb)return -1*sortDir;
                    if(va>vb)return 1*sortDir;
                    return 0;
                });
            }

            function render(){
                const total=view.length;
                const totalPages=Math.max(1,Math.ceil(total/pageSize));
                page=Math.min(Math.max(1,page),totalPages);
                const start=(page-1)*pageSize;
                const end=Math.min(start+pageSize,total);
                master.forEach(r=>r.tr.style.display='none');
                view.slice(start,end).forEach(r=>r.tr.style.display='');
                if(rangeEl){
                    if(total===0)rangeEl.textContent='Aucun résultat';
                    else rangeEl.textContent='Affichage '+(start+1)+'–'+end+' sur '+total+' commande'+(total>1?'s':'');
                }
                renderPager(totalPages);
                updateSortArrows();
            }

            function renderPager(totalPages){
                pager.innerHTML='';
                if(totalPages <= 1) return;

                const btnStyle='padding:8px 14px;border:1px solid var(--border);border-radius:8px;background:#fff;cursor:pointer;font-weight:600;transition:all 0.2s;';
                const disabledStyle=btnStyle+'opacity:0.5;cursor:not-allowed;';

                // Previous button
                const prevBtn=document.createElement('button');
                prevBtn.textContent='‹ Précédent';
                prevBtn.className='btn';
                prevBtn.disabled=page<=1;
                prevBtn.setAttribute('style',page<=1?disabledStyle:btnStyle);
                if(page>1)prevBtn.onclick=()=>{page--;render();};
                pager.appendChild(prevBtn);

                // Page info
                const pageInfo=document.createElement('span');
                pageInfo.textContent='Page '+page+' / '+totalPages;
                pageInfo.style.cssText='padding:0 12px;color:var(--muted);font-weight:600;';
                pager.appendChild(pageInfo);

                // Next button
                const nextBtn=document.createElement('button');
                nextBtn.textContent='Suivant ›';
                nextBtn.className='btn';
                nextBtn.disabled=page>=totalPages;
                nextBtn.setAttribute('style',page>=totalPages?disabledStyle:btnStyle);
                if(page<totalPages)nextBtn.onclick=()=>{page++;render();};
                pager.appendChild(nextBtn);
            }

            function updateSortArrows(){
                table.querySelectorAll('th.sortable').forEach(th=>{
                    const arrow=th.querySelector('.arrow');
                    const key=th.getAttribute('data-sort-key');
                    if(arrow){
                        if(key===sortKey){
                            arrow.textContent=sortDir===1?'▲':'▼';
                            arrow.style.opacity='1';
                            arrow.style.fontWeight='700';
                        }else{
                            arrow.textContent='↕';
                            arrow.style.opacity='0.5';
                            arrow.style.fontWeight='normal';
                        }
                    }
                });
            }

            // Add sort functionality
            table.querySelectorAll('th.sortable').forEach(th=>{
                th.addEventListener('click',()=>{
                    const key=th.getAttribute('data-sort-key');
                    if(sortKey===key){sortDir=-sortDir;}else{sortKey=key;sortDir=1;}
                    applySort();
                    render();
                });
            });

            // Event listeners
            let searchTimeout;
            search.addEventListener('input',()=>{clearTimeout(searchTimeout);searchTimeout=setTimeout(applyFilter,200);});
            fromInp.addEventListener('change',applyFilter);
            toInp.addEventListener('change',applyFilter);
            fouSel.addEventListener('change',applyFilter);
            if(statusSel)statusSel.addEventListener('change',applyFilter);
            pageSizeSelect.addEventListener('change',e=>{pageSize=parseInt(e.target.value,10)||10;page=1;render();});
            resetBtn.addEventListener('click',()=>{
                search.value='';
                fromInp.value='';
                toInp.value='';
                fouSel.value='';
                if(statusSel)statusSel.value='';
                pageSizeSelect.value='10';
                pageSize=10;
                page=1;
                applyFilter();
            });

            // Initial render
            applyFilter();
        }

        // Initialize on load with retry mechanism
        function initialize() {
            if (document.readyState === 'loading') {
                document.addEventListener('DOMContentLoaded', function() {
                    setTimeout(initOrdersTable, 100);
                });
            } else {
                setTimeout(initOrdersTable, 100);
            }
        }

        initialize();
    })();
</script>

</body>
</html>