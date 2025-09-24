<%--
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

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
    // Get data from request attributes set in doView()
    List<Utilisateur> fournisseurs = (List<Utilisateur>) request.getAttribute("fournisseurs");
    List<Medicament> medicaments = (List<Medicament>) request.getAttribute("medicaments");
    List<Commande> commandes = (List<Commande>) request.getAttribute("commandes");

    if (fournisseurs == null) fournisseurs = new java.util.ArrayList<>();
    if (medicaments == null) medicaments = new java.util.ArrayList<>();
    if (commandes == null) commandes = new java.util.ArrayList<>();

    NumberFormat money = NumberFormat.getNumberInstance(Locale.FRANCE);
    money.setMinimumFractionDigits(2);
    money.setMaximumFractionDigits(2);
%>

<portlet:actionURL name="createCommande" var="createCommandeURL" />

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8" />
    <title>Commandes</title>
    <style>
        /* Your CSS styles here */
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

        /* Table Styles - ADD THESE */
        .controls{display:flex;gap:12px;align-items:center;flex-wrap:wrap;margin-bottom:10px}
        .controls input[type="search"]{flex:1;min-width:240px;padding:10px;border:1px solid var(--border);border-radius:10px;background:#fff}
        table{width:100%;border-collapse:collapse; margin-top: 10px;}
        thead th{text-align:left;background:#F8FAFC;color:var(--primary);padding:10px 12px;border-bottom:1px solid var(--border);font-weight:700}
        tbody td{padding:10px 12px; border-bottom: 1px solid var(--border);}
        tr:hover td{background:#F9FAFB}
        th.sortable{cursor:pointer;user-select:none}
        th.sortable .arrow{font-size:12px;opacity:.6;margin-left:6px}
        .pager{display:flex;gap:8px;align-items:center;flex-wrap:wrap;margin-top:12px}
        .pager .btn{padding:6px 12px}
        .pager .btn.btn-primary{background:var(--secondary);border-color:var(--secondary)}
        .desc{max-width:380px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;color:#334155}

        .order-grid{display:grid; grid-template-columns: 1fr 1fr; gap:12px}
        .order-grid .full{ grid-column:1/-1 }
        .small{ font-size:0.95rem; color:var(--muted) }

        /* Modal styles */
        .modal-overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            z-index: 1000;
        }
        .modal-content {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: white;
            padding: 20px;
            border-radius: 12px;
            max-width: 800px;
            width: 90%;
            max-height: 80vh;
            overflow: auto;
        }

        /* Form controls */
        .form-control {
            width: 100%;
            padding: 8px 12px;
            border: 1px solid var(--border);
            border-radius: 6px;
            font-size: 14px;
        }
    </style>
</head>
<body>

<div class="wrap">
    <div class="header">
        <h2>🧾 Commandes</h2>
        <div class="header-actions">
            <div class="small">Créer une commande auprès d'un fournisseur</div>
            <button type="button" class="btn btn-primary js-order-open" data-mode="add">+ Nouvelle commande</button>
        </div>
    </div>

    <!-- Success/Error Messages -->
    <liferay-ui:success key="commande-created-success" message="Commande créée avec succès !" />
    <liferay-ui:error key="commande-create-error" message="Erreur lors de la création de la commande." />
    <liferay-ui:error key="commande-no-items" message="Aucun médicament sélectionné." />

    <div class="card">
        <!-- Add search controls -->
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
                    Utilisateur f = gestion_de_pharmacie.service.UtilisateurLocalServiceUtil.getUtilisateur(c.getIdFournisseur());
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
                    <% } else { %>
                    -
                    <% } %>
                </td>
                <td data-key="statut"><%= HtmlUtil.escape(c.getStatut()) %></td>
                <td data-key="montant" data-sort="<%= c.getMontantTotal() %>"><%= money.format(c.getMontantTotal()) %></td>
                <td>
                    <button class="btn" onclick="alert('Voir détails - à implémenter');">Voir</button>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>

        <!-- Add pager -->
        <div class="pager" id="pagerOrders"></div>
    </div>
</div>

<!-- Modal content -->
<div id="orderModalContent" style="display:none;">
    <form id="orderForm" method="post" class="card">
        <div class="order-grid">
            <div class="full">
                <label for="fournisseurId" style="font-weight:600">Fournisseur</label>
                <select class="fournisseur-select form-control" name="fournisseurId" required>
                    <option value="">-- Choisir un fournisseur --</option>
                    <% for (Utilisateur f : fournisseurs) { %>
                    <option value="<%= f.getIdUtilisateur() %>"><%= HtmlUtil.escape(f.getNom() + " " + f.getPrenom()) %></option>
                    <% } %>
                </select>
            </div>

            <div class="full">
                <label style="font-weight:600">Médicaments</label>
                <div style="border:1px solid var(--border); padding:12px; border-radius:8px; max-height:320px; overflow:auto;">
                    <table style="width:100%;border-collapse:collapse;">
                        <thead><tr><th>Sel</th><th>Médicament</th><th>Prix</th><th>Quantité</th></tr></thead>
                        <tbody>
                        <% for (Medicament m : medicaments) { %>
                        <tr>
                            <td><input type="checkbox" name="medicamentId" value="<%= m.getIdMedicament() %>" /></td>
                            <td><%= HtmlUtil.escape(m.getNom()) %></td>
                            <td><%= money.format(m.getPrixUnitaire()) %></td>
                            <td><input type="number" name="quantite_<%= m.getIdMedicament() %>" value="1" min="1" class="form-control" style="width:84px;" /></td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </form>
</div>

<!-- Simple Modal Implementation -->
<div id="orderModal" class="modal-overlay">
    <div class="modal-content">
        <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:20px;">
            <h3 style="margin:0;">Nouvelle commande</h3>
            <button onclick="closeModal()" style="background:none;border:none;font-size:24px;cursor:pointer;">×</button>
        </div>
        <div id="modalBody"></div>
        <div style="margin-top:20px;text-align:right;">
            <button onclick="closeModal()" class="btn" style="margin-right:10px;">Annuler</button>
            <button onclick="submitOrderForm()" class="btn btn-primary">Créer</button>
        </div>
    </div>
</div>

<script>
    function openOrderModal() {
        const body = document.getElementById('orderModalContent').innerHTML;
        document.getElementById('modalBody').innerHTML = body;
        document.getElementById('orderModal').style.display = 'block';

        // Add debug info to console
        console.log('Modal opened');
        console.log('Fournisseur select:', document.querySelector('.fournisseur-select'));
        console.log('Checkboxes:', document.querySelectorAll('input[name="medicamentId"]'));
    }

    function submitOrderForm() {
        const modal = document.getElementById('orderModal');
        const supplier = modal.querySelector('.fournisseur-select');
        const checkboxes = Array.from(modal.querySelectorAll('input[type="checkbox"][name="medicamentId"]:checked'));

        console.log('DEBUG - Form submission:');
        console.log('Supplier value:', supplier ? supplier.value : 'NOT FOUND');
        console.log('Checked medicaments:', checkboxes.length);

        if (!supplier || supplier.value === "") {
            alert('Veuillez choisir un fournisseur.');
            return;
        }

        if (checkboxes.length === 0) {
            alert('Veuillez sélectionner au moins un médicament.');
            return;
        }

        // Prepare form data
        const formData = new FormData();
        formData.append('fournisseurId', supplier.value);

        checkboxes.forEach(checkbox => {
            formData.append('medicamentId', checkbox.value);
            const qtyField = modal.querySelector('input[name="quantite_' + checkbox.value + '"]');
            formData.append('quantite_' + checkbox.value, qtyField ? qtyField.value : '1');
        });

        // Add Liferay portlet parameters
        formData.append('p_p_id', 'commande_web');
        formData.append('p_p_lifecycle', '1'); // 1 = action phase
        formData.append('p_p_state', 'normal');
        formData.append('p_p_mode', 'view');
        formData.append('_commande_web_javax.portlet.action', 'createCommande');

        // Get the current URL without parameters
        const currentUrl = window.location.href.split('?')[0];
        const actionUrl = currentUrl;

        console.log('Submitting to:', actionUrl);
        console.log('Form data:');
        for (let [key, value] of formData.entries()) {
            console.log('  ', key, '=', value);
        }

        fetch(actionUrl, {
            method: 'POST',
            body: formData
        })
            .then(response => {
                console.log('Response status:', response.status, response.statusText);
                console.log('Response headers:', response.headers);

                if (response.ok) {
                    return response.text();
                } else {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
            })
            .then(data => {
                console.log('Response data received, length:', data.length);
                // Check if the response contains success indicators
                if (data.includes('commande-created-success') || data.includes('success')) {
                    alert('Commande créée avec succès!');
                    location.reload();
                } else {
                    console.log('Response content sample:', data.substring(0, 500));
                    alert('Commande créée mais réponse inattendue. Voir la console.');
                }
            })
            .catch(error => {
                console.error('AJAX Error:', error);
                alert('Erreur réseau: ' + error.message);
            });

        closeModal();
    }
    function closeModal() {
        document.getElementById('orderModal').style.display = 'none';
    }

    document.addEventListener('click', function(e) {
        if (e.target.closest('.js-order-open')) {
            e.preventDefault();
            openOrderModal();
        }

        if (e.target.id === 'orderModal') {
            closeModal();
        }
    });

    // Add table sorting functionality
    document.addEventListener('DOMContentLoaded', function() {
        const table = document.getElementById('ordersTable');
        if (table) {
            const headers = table.querySelectorAll('th.sortable');
            headers.forEach(header => {
                header.addEventListener('click', function() {
                    const sortKey = this.getAttribute('data-sort-key');
                    console.log('Sort by:', sortKey);
                    // Add sorting logic here
                });
            });
        }
    });
</script>

</body>
</html>--%>




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
%>

<portlet:actionURL name="createCommande" var="createCommandeURL" />



<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8" />
    <title>Commandes</title>
    <style>
        /* (your CSS: keep same as before) */
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
    <liferay-ui:error key="commande-create-error" message="Erreur lors de la création de la commande." />
    <liferay-ui:error key="commande-no-items" message="Aucun médicament sélectionné." />

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
                    Utilisateur f = gestion_de_pharmacie.service.UtilisateurLocalServiceUtil.getUtilisateur(c.getIdFournisseur());
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
                <td><button class="btn" onclick="alert('Voir détails - à implémenter');">Voir</button></td>
            </tr>
            <% } %>
            </tbody>
        </table>

        <div class="pager" id="pagerOrders"></div>
    </div>
</div>

<!-- --- Modal (already in DOM, no cloning) --- -->
<div id="orderModal" class="modal-overlay" aria-hidden="true">
    <div class="modal-content" role="dialog" aria-modal="true">
        <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:12px;">
            <h3 style="margin:0;">Nouvelle commande</h3>
            <button type="button" onclick="closeModal()" style="font-size:20px;border:none;background:none;cursor:pointer;">×</button>
        </div>

        <!-- IMPORTANT: plain form with action set to the portlet action URL -->
        <form id="orderForm" method="post" action="${createCommandeURL}" class="card">
            <div class="order-grid">
                <div class="full">
                    <label for="fournisseurId" style="font-weight:600">Fournisseur</label>
                    <select id="fournisseurId" name="fournisseurId" class="form-control" required>
                        <option value="">-- Choisir un fournisseur --</option>
                        <% for (Utilisateur f : fournisseurs) { %>
                        <option value="<%= f.getIdUtilisateur() %>"><%= HtmlUtil.escape(f.getNom() + " " + f.getPrenom()) %></option>
                        <% } %>
                    </select>
                </div>

                <div class="full">
                    <label style="font-weight:600">Médicaments</label>
                    <div style="border:1px solid var(--border); padding:12px; border-radius:8px; max-height:320px; overflow:auto;">
                        <table style="width:100%;border-collapse:collapse;">
                            <thead><tr><th>Sel</th><th>Médicament</th><th>Prix</th><th>Quantité</th></tr></thead>
                            <tbody>
                            <% for (Medicament m : medicaments) { %>
                            <tr>
                                <td><input type="checkbox" name="medicamentId" value="<%= m.getIdMedicament() %>" /></td>
                                <td><%= HtmlUtil.escape(m.getNom()) %></td>
                                <td><%= money.format(m.getPrixUnitaire()) %></td>
                                <td><input type="number" name="quantite_<%= m.getIdMedicament() %>" value="1" min="1" class="form-control" style="width:84px;" /></td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- Hidden submit button so we can call form.requestSubmit() if needed -->
            <button type="submit" id="orderFormSubmit" style="display:none;"></button>
        </form>

        <div style="margin-top:14px;text-align:right;">
            <button type="button" class="btn" onclick="closeModal()" style="margin-right:8px;">Annuler</button>
            <!-- This triggers the real form submission -->
            <button type="button" class="btn btn-primary" onclick="submitOrderForm()">Créer</button>
        </div>
    </div>
</div>

<script>
    // Open/close modal (we keep the modal DOM in place, no cloning)
    function openModal() {
        const modal = document.getElementById('orderModal');
        modal.style.display = 'flex';
        modal.setAttribute('aria-hidden','false');
    }
    function closeModal() {
        const modal = document.getElementById('orderModal');
        modal.style.display = 'none';
        modal.setAttribute('aria-hidden','true');
    }

    document.addEventListener('click', function(e) {
        if (e.target.closest('.js-order-open')) {
            e.preventDefault();
            openModal();
        }
        // click outside modal content closes it
        if (e.target === document.getElementById('orderModal')) {
            closeModal();
        }
    });

    function submitOrderForm() {
        // Use the real form already in DOM. No AJAX.
        const form = document.getElementById('orderForm');
        const supplier = form.querySelector('select[name="fournisseurId"]');
        const checked = Array.from(form.querySelectorAll('input[name="medicamentId"]:checked'));

        if (!supplier || supplier.value === '') {
            alert('Veuillez choisir un fournisseur.');
            supplier && supplier.focus();
            return;
        }
        if (checked.length === 0) {
            alert('Veuillez sélectionner au moins un médicament.');
            return;
        }

        // Submit the form (regular POST) to the portlet action URL
        if (typeof form.requestSubmit === 'function') {
            form.requestSubmit();
        } else {
            form.submit();
        }
    }
</script>

</body>
</html>
