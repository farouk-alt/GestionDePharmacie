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
<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<portlet:defineObjects />

<%
    int unreadCount = 0;
    Object cObj = request.getAttribute("unreadCount");
    if (cObj instanceof Integer) unreadCount = (Integer)cObj;

    Boolean editModeObj = (Boolean) request.getAttribute("editMode");
    boolean editMode = (editModeObj != null && editModeObj);
    Medicament editMed = (Medicament) request.getAttribute("medicament");

    List<Medicament> medicaments = MedicamentLocalServiceUtil.getMedicaments(-1, -1);
    NumberFormat money = NumberFormat.getNumberInstance(Locale.FRANCE);
    money.setMinimumFractionDigits(2);
    money.setMaximumFractionDigits(2);

    // Get unique categories for filter
    Set<String> categories = new TreeSet<String>();
    for (Medicament m : medicaments) {
        if (m.getCategorie() != null && !m.getCategorie().trim().isEmpty()) {
            categories.add(m.getCategorie());
        }
    }
%>

<portlet:actionURL name="ajouterMedicament" var="addMedURL" />
<portlet:actionURL name="updateMedicament" var="updateMedURL" />

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <title>Liste des Médicaments</title>
    <style>
        :root{ --primary:#1E3A8A; --secondary:#3B82F6; --accent:#10B981;
            --bg:#F3F4F6; --white:#FFFFFF; --text:#111827; --muted:#6B7280; --border:#E5E7EB; }
        body{background:var(--bg);margin:0;color:var(--text);font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif}
        .wrap{max-width:1400px;margin:24px auto;padding:0 16px}
        .header{background:linear-gradient(135deg,var(--primary),var(--secondary));color:#fff;border-radius:14px;padding:18px 20px;margin-bottom:16px;display:flex;align-items:center;justify-content:space-between;box-shadow:0 10px 24px rgba(30,58,138,.25)}
        .header h2{margin:0;font-size:20px}
        .header-actions{display:flex;gap:8px;align-items:center}
        .btn{display:inline-block;background:#fff;color:var(--primary);border:1px solid transparent;padding:8px 16px;border-radius:10px;font-weight:600;text-decoration:none;cursor:pointer;transition:.2s;font-size:14px;}
        .btn:hover{background:#F8FAFC;transform:translateY(-1px);color:var(--primary)}
        .btn-primary{background:var(--accent);color:#fff;border-color:var(--accent)}
        .btn-primary:hover{filter:brightness(.95);color:#fff}
        .btn-danger{background:#DC2626;color:#fff;border-color:#DC2626}
        .btn-danger:hover{filter:brightness(.95)}
        .card{background:var(--white);border:1px solid var(--border);border-radius:12px;padding:20px;box-shadow:0 6px 18px rgba(17,24,39,.06);margin-bottom:16px}

        .filters{display:grid;grid-template-columns:2fr 1fr 1fr 1fr 1fr auto;gap:12px;margin-bottom:16px;align-items:end;}
        .filter-group{display:flex;flex-direction:column;}
        .filter-group label{font-size:12px;font-weight:600;color:var(--muted);margin-bottom:6px;}
        .filter-group input,.filter-group select{padding:10px 12px;border:1px solid var(--border);border-radius:10px;background:#fff;font-size:14px;}
        .filter-group input:focus,.filter-group select:focus{outline:none;border-color:var(--secondary);box-shadow:0 0 0 3px rgba(59,130,246,.1);}

        .stats-bar{display:flex;justify-content:space-between;align-items:center;margin-bottom:12px;padding-bottom:12px;border-bottom:1px solid var(--border);}
        #rangeText{color:var(--muted);font-size:14px;}

        table{width:100%;border-collapse:collapse;table-layout:fixed;}
        thead th{text-align:left;background:#F8FAFC;color:var(--primary);padding:12px;border-bottom:2px solid var(--border);font-weight:700;font-size:13px;}
        tbody td{padding:12px;font-size:14px;overflow:hidden;}
        tbody tr{border-bottom:1px solid var(--border);}
        tbody tr:last-child{border-bottom:none;}
        tbody tr:hover{background:#F9FAFB;}
        th.sortable{cursor:pointer;user-select:none;}
        th.sortable:hover{background:#F1F5F9;}
        th.sortable .arrow{font-size:12px;opacity:.6;margin-left:6px;font-weight:normal;}
        .desc{max-width:300px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;color:#334155;word-break:break-all;}
        .pager{display:flex;gap:8px;align-items:center;justify-content:center;flex-wrap:wrap;margin-top:16px;padding-top:16px;border-top:1px solid var(--border)}
        .pager .btn{padding:8px 12px;min-width:40px;}
        .pager .btn.btn-primary{background:var(--secondary);border-color:var(--secondary);color:#fff}

        #medsTable td.actions{ white-space:nowrap; vertical-align:middle; }
        #medsTable td.actions form{ display:inline; margin:0; }
        .icon-btn{ display:inline-flex; align-items:center; justify-content:center; width:36px; height:36px; margin-right:6px;
            background:transparent; border:1px solid var(--border); border-radius:8px; color:#334155; cursor:pointer;
            transition:all .15s; vertical-align:middle; }
        .icon-btn:last-child{ margin-right:0; }
        .icon-btn:hover{ background:#F1F5F9; color:#0f172a; border-color:#cbd5e1; }
        .icon-btn:active{ transform:scale(.95); }
        .icon-btn.danger{ color:#b91c1c; border-color:#fecaca; }
        .icon-btn.danger:hover{ background:#fef2f2; color:#991b1b; border-color:#fca5a5; }
        .icon-btn .lexicon-icon{ width:18px; height:18px; }
        .bell-btn{
            position:relative; display:inline-flex; align-items:center; gap:8px;
            background:#fff; color:var(--primary); border:1px solid var(--border);
            padding:8px 12px; border-radius:10px; font-weight:600; cursor:pointer;
        }
        .bell-dot{
            min-width:20px; height:20px; padding:0 5px;
            display:inline-flex; align-items:center; justify-content:center;
            font-size:11px; font-weight:700; color:#fff; background:#ef4444; border-radius:999px;
        }

        .modal-overlay { display:none; position:fixed; inset:0; background:rgba(0,0,0,0.6); z-index:9999; align-items:center; justify-content:center;animation:fadeIn .2s;overflow-y:auto;padding:20px 0;}
        @keyframes fadeIn{from{opacity:0;}to{opacity:1;}}
        .modal-content{background:#fff; border-radius:16px;max-width:680px;width:95%;max-height:none;display:flex;flex-direction:column;box-shadow:0 25px 50px -12px rgba(0,0,0,0.25);animation:slideUp .3s;margin:auto;}
        @keyframes slideUp{from{transform:translateY(20px);opacity:0;}to{transform:translateY(0);opacity:1;}}
        .modal-header{display:flex;justify-content:space-between;align-items:center;padding:24px 24px 16px;border-bottom:1px solid var(--border);}
        .modal-header h3{margin:0;font-size:22px;color:var(--primary);font-weight:700;}
        .modal-close{background:none;border:none;font-size:32px;cursor:pointer;color:var(--muted);line-height:1;width:40px;height:40px;display:flex;align-items:center;justify-content:center;border-radius:8px;transition:.2s;}
        .modal-close:hover{background:#F3F4F6;color:var(--text);}
        .modal-body{padding:24px;overflow-y:auto;flex:1;max-height:calc(85vh - 180px);}
        .modal-footer{display:flex;justify-content:flex-end;gap:10px;padding:16px 24px;border-top:1px solid var(--border);background:#F9FAFB;border-radius:0 0 16px 16px;}

        .form-group{margin-bottom:18px;}
        .form-group label{display:block;font-weight:600;margin-bottom:8px;color:var(--text);font-size:14px;}
        .form-group label .required{color:#DC2626;margin-left:2px;}
        .form-control{width:100%;padding:12px 14px;border:1px solid var(--border);border-radius:10px;background:#fff;box-shadow:inset 0 1px 2px rgba(0,0,0,.04);font-size:14px;transition:all .2s;}
        .form-control:focus{outline:none;border-color:#3B82F6;box-shadow:0 0 0 3px rgba(59,130,246,.1);}
        textarea.form-control{min-height:100px;resize:vertical;font-family:inherit;}
        .hint{color:#6B7280;font-size:12px;margin-top:6px;font-style:italic;}
        .ean-calc{color:#059669;font-size:12px;margin-top:4px;font-weight:500;background:#ECFDF5;padding:4px 8px;border-radius:4px;display:inline-block;}
        .form-row{display:grid;grid-template-columns:1fr 1fr;gap:16px;}

        @media (max-width: 768px) {
            .filters{grid-template-columns:1fr;gap:12px;}
            .form-row{grid-template-columns:1fr;}
            .pager{justify-content:center;}
        }


    </style>
</head>
<body>
<div class="wrap">
    <liferay-portlet:renderURL portletName="notification_web_NotificationWebPortlet" var="notifURL" />

    <div class="header">
        <h2>Liste des Médicaments</h2>
        <div class="header-actions">
            <a class="bell-btn" href="${notifURL}" title="Notifications">
                🔔
                <% if (unreadCount > 0) { %>
                <span class="bell-dot"><%= unreadCount %></span>
                <% } %>
            </a>
            <button type="button" class="btn btn-primary" onclick="openAddModal()">+ Ajouter un médicament</button>
        </div>
    </div>

    <liferay-ui:success key="medicament-added-successfully"    message="Médicament ajouté avec succès !" />
    <liferay-ui:success key="medicament-updated-successfully"  message="Médicament mis à jour avec succès !" />
    <liferay-ui:success key="medicament-deleted-successfully"  message="Médicament supprimé avec succès !" />
    <liferay-ui:error   key="medicament-already-exists"        message="Un médicament avec ce nom existe déjà." />
    <liferay-ui:error   key="medicament-required"               message="Veuillez renseigner au minimum le nom et le code." />
    <liferay-ui:error   key="medicament-code-exists"            message="Ce code interne existe déjà." />
    <liferay-ui:error   key="medicament-barcode-invalid"        message="Le code-barres doit être un EAN-13 valide." />
    <liferay-ui:error   key="medicament-barcode-exists"         message="Ce code-barres existe déjà." />

    <div class="card">
        <div class="filters">
            <div class="filter-group">
                <label for="search">Recherche globale</label>
                <input id="search" type="search" placeholder="Code, nom, code-barres, description..." />
            </div>
            <div class="filter-group">
                <label for="filterCategorie">Catégorie</label>
                <select id="filterCategorie">
                    <option value="">Toutes</option>
                    <% for (String cat : categories) { %>
                    <option value="<%= HtmlUtil.escapeAttribute(cat) %>"><%= HtmlUtil.escape(cat) %></option>
                    <% } %>
                </select>
            </div>
            <div class="filter-group">
                <label for="filterPrixMin">Prix min (DH)</label>
                <input id="filterPrixMin" type="number" step="0.01" min="0" placeholder="0" />
            </div>
            <div class="filter-group">
                <label for="filterPrixMax">Prix max (DH)</label>
                <input id="filterPrixMax" type="number" step="0.01" min="0" placeholder="∞" />
            </div>
            <div class="filter-group">
                <label for="filterSeuil">Seuil min</label>
                <input id="filterSeuil" type="number" min="0" placeholder="Tous" />
            </div>
            <div class="filter-group">
                <label>&nbsp;</label>
                <button type="button" class="btn" onclick="resetFilters()" title="Réinitialiser les filtres">Réinitialiser</button>
            </div>
        </div>

        <div class="stats-bar">
            <span id="rangeText"></span>
            <div style="display:flex;gap:8px;align-items:center;">
                <label for="pageSize" style="font-size:14px;color:var(--muted);">Par page:</label>
                <select id="pageSize" style="padding:8px 12px;border:1px solid var(--border);border-radius:8px;">
                    <option value="5">5</option>
                    <option value="10" selected>10</option>
                    <option value="20">20</option>
                    <option value="50">50</option>
                </select>
            </div>
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
                <th class="sortable" data-sort-key="seuil">Seuil <span class="arrow">↕</span></th>
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
                    String dateAff = (m.getDateAjout()!=null) ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(m.getDateAjout()) : "-";
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
                    <button type="button" class="icon-btn edit-btn"
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
                    </button>

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
</div>

<!-- Add Modal -->
<div id="addModal" class="modal-overlay">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Ajouter un médicament</h3>
            <button type="button" class="modal-close" onclick="closeAddModal()">&times;</button>
        </div>
        <form id="addForm" action="${addMedURL}" method="post">
            <div class="modal-body">
                <div class="form-row">
                    <div class="form-group">
                        <label for="<portlet:namespace/>add_code">Code interne <span class="required">*</span></label>
                        <input class="form-control" id="<portlet:namespace/>add_code" name="<portlet:namespace/>code" maxlength="64" required placeholder="Ex: AMOX500" />
                    </div>
                    <div class="form-group">
                        <label for="<portlet:namespace/>add_codeBarre">Code-barres EAN-13</label>
                        <input class="form-control" id="<portlet:namespace/>add_codeBarre" name="<portlet:namespace/>codeBarre" inputmode="numeric" pattern="[0-9]{13}" maxlength="13" placeholder="13 chiffres" />
                        <div id="add_eanCalc" class="ean-calc" style="display:none;"></div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="<portlet:namespace/>add_nom">Nom du médicament <span class="required">*</span></label>
                    <input class="form-control" id="<portlet:namespace/>add_nom" name="<portlet:namespace/>nom" required placeholder="Ex: Amoxicilline 500mg" />
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="<portlet:namespace/>add_prix">Prix unitaire (DH) <span class="required">*</span></label>
                        <input class="form-control" id="<portlet:namespace/>add_prix" name="<portlet:namespace/>prix" type="number" step="0.01" min="0" required placeholder="0.00" />
                    </div>
                    <div class="form-group">
                        <label for="<portlet:namespace/>add_categorie">Catégorie</label>
                        <input class="form-control" id="<portlet:namespace/>add_categorie" name="<portlet:namespace/>categorie" placeholder="Ex: Antibiotique" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="<portlet:namespace/>add_seuilMinimum">Seuil minimum de stock</label>
                    <input class="form-control" id="<portlet:namespace/>add_seuilMinimum" name="<portlet:namespace/>seuilMinimum" type="number" min="0" value="10" placeholder="10" />
                    <small class="hint">Quantité minimale avant alerte de stock bas</small>
                </div>
                <div class="form-group">
                    <label for="<portlet:namespace/>add_description">Description</label>
                    <textarea class="form-control" id="<portlet:namespace/>add_description" name="<portlet:namespace/>description" placeholder="Description détaillée du médicament..."></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn" onclick="closeAddModal()">Annuler</button>
                <button type="submit" class="btn btn-primary">Ajouter le médicament</button>
            </div>
        </form>
    </div>
</div>

<!-- Edit Modal -->
<div id="editModal" class="modal-overlay">
    <div class="modal-content">
        <div class="modal-header">
            <h3 id="editModalTitle">Modifier le médicament</h3>
            <button type="button" class="modal-close" onclick="closeEditModal()">&times;</button>
        </div>
        <form id="editForm" action="${updateMedURL}" method="post">
            <input type="hidden" id="<portlet:namespace/>edit_medicamentId" name="<portlet:namespace/>medicamentId" />
            <div class="modal-body">
                <div class="form-row">
                    <div class="form-group">
                        <label for="<portlet:namespace/>edit_code">Code interne <span class="required">*</span></label>
                        <input class="form-control" id="<portlet:namespace/>edit_code" name="<portlet:namespace/>code" maxlength="64" required />
                    </div>
                    <div class="form-group">
                        <label for="<portlet:namespace/>edit_codeBarre">Code-barres EAN-13</label>
                        <input class="form-control" id="<portlet:namespace/>edit_codeBarre" name="<portlet:namespace/>codeBarre" inputmode="numeric" pattern="[0-9]{13}" maxlength="13" />
                        <div id="edit_eanCalc" class="ean-calc" style="display:none;"></div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="<portlet:namespace/>edit_nom">Nom du médicament <span class="required">*</span></label>
                    <input class="form-control" id="<portlet:namespace/>edit_nom" name="<portlet:namespace/>nom" required />
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="<portlet:namespace/>edit_prix">Prix unitaire (DH) <span class="required">*</span></label>
                        <input class="form-control" id="<portlet:namespace/>edit_prix" name="<portlet:namespace/>prix" type="number" step="0.01" min="0" required />
                    </div>
                    <div class="form-group">
                        <label for="<portlet:namespace/>edit_categorie">Catégorie</label>
                        <input class="form-control" id="<portlet:namespace/>edit_categorie" name="<portlet:namespace/>categorie" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="<portlet:namespace/>edit_seuilMinimum">Seuil minimum de stock</label>
                    <input class="form-control" id="<portlet:namespace/>edit_seuilMinimum" name="<portlet:namespace/>seuilMinimum" type="number" min="0" />
                    <small class="hint">Quantité minimale avant alerte de stock bas</small>
                </div>
                <div class="form-group">
                    <label for="<portlet:namespace/>edit_description">Description</label>
                    <textarea class="form-control" id="<portlet:namespace/>edit_description" name="<portlet:namespace/>description"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn" onclick="closeEditModal()">Annuler</button>
                <button type="submit" class="btn btn-primary">Mettre à jour</button>
            </div>
        </form>
    </div>
</div>

<script>
    // EAN-13 calculation function
    function calculateEAN13CheckDigit(first12) {
        if (first12.length !== 12 || !/^\d+$/.test(first12)) return null;

        let sum = 0;
        for (let i = 0; i < 12; i++) {
            const digit = parseInt(first12.charAt(i), 10);
            // EAN-13: odd positions (1,3,5,7,9,11) = multiply by 1, even positions (2,4,6,8,10,12) = multiply by 3
            sum += (i % 2 === 0) ? digit : digit * 3;
        }
        const checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit;
    }

    // Setup EAN calculation for an input field
    function setupEANCalculation(inputId, displayId) {
        const input = document.getElementById(inputId);
        const display = document.getElementById(displayId);

        if (!input || !display) return;

        input.addEventListener('input', function(e) {
            const value = e.target.value.replace(/\D/g, '');

            if (value.length === 12) {
                const checkDigit = calculateEAN13CheckDigit(value);
                if (checkDigit !== null) {
                    display.textContent = 'EAN-13 complet: ' + value + checkDigit;
                    display.style.display = 'block';
                }
            } else if (value.length === 13) {
                const first12 = value.substring(0, 12);
                const actualCheckDigit = parseInt(value.charAt(12), 10);
                const calculatedCheckDigit = calculateEAN13CheckDigit(first12);

                if (calculatedCheckDigit === actualCheckDigit) {
                    display.textContent = '✓ EAN-13 valide';
                    display.style.display = 'block';
                } else {
                    display.textContent = '✗ Invalide! Devrait finir par ' + calculatedCheckDigit;
                    display.style.display = 'block';
                }
            } else {
                display.style.display = 'none';
            }
        });
    }

    // Modal functions
    function openAddModal() {
        document.getElementById('addModal').style.display = 'flex';
        setTimeout(function() {
            document.getElementById('<portlet:namespace/>add_code').focus();
        }, 150);
    }

    function closeAddModal() {
        document.getElementById('addModal').style.display = 'none';
        document.getElementById('addForm').reset();
        document.getElementById('add_eanCalc').style.display = 'none';
    }

    function openEditModal(button) {
        const id = button.getAttribute('data-id');
        const code = button.getAttribute('data-code');
        const codeBarre = button.getAttribute('data-codebarre');
        const nom = button.getAttribute('data-nom');
        const prix = button.getAttribute('data-prix');
        const categorie = button.getAttribute('data-categorie');
        const seuil = button.getAttribute('data-seuil');
        const description = button.getAttribute('data-description');

        document.getElementById('<portlet:namespace/>edit_medicamentId').value = id;
        document.getElementById('<portlet:namespace/>edit_code').value = code || '';
        document.getElementById('<portlet:namespace/>edit_codeBarre').value = codeBarre || '';
        document.getElementById('<portlet:namespace/>edit_nom').value = nom || '';
        document.getElementById('<portlet:namespace/>edit_prix').value = prix || '';
        document.getElementById('<portlet:namespace/>edit_categorie').value = categorie || '';
        document.getElementById('<portlet:namespace/>edit_seuilMinimum').value = seuil || '';
        document.getElementById('<portlet:namespace/>edit_description').value = description || '';

        document.getElementById('editModalTitle').textContent = 'Modifier: ' + (nom || 'Médicament');
        document.getElementById('editModal').style.display = 'flex';

        setupEANCalculation('<portlet:namespace/>edit_codeBarre', 'edit_eanCalc');

        setTimeout(function() {
            document.getElementById('<portlet:namespace/>edit_code').focus();
        }, 150);
    }

    function closeEditModal() {
        document.getElementById('editModal').style.display = 'none';
        document.getElementById('edit_eanCalc').style.display = 'none';
    }

    // ESC key to close modals
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            if (document.getElementById('addModal').style.display === 'flex') {
                closeAddModal();
            }
            if (document.getElementById('editModal').style.display === 'flex') {
                closeEditModal();
            }
        }
    });

    // Setup EAN calculation for add modal
    setupEANCalculation('<portlet:namespace/>add_codeBarre', 'add_eanCalc');

    // Event delegation for edit buttons
    document.addEventListener('click', function(e) {
        if (e.target.closest('.edit-btn')) {
            e.preventDefault();
            openEditModal(e.target.closest('.edit-btn'));
        }
    });

    // Advanced filtering, sorting, and pagination
    (function(){
        var table = document.getElementById('medsTable');
        if(!table) return;

        var tbody = table.querySelector('tbody');
        var allRows = Array.prototype.slice.call(tbody.querySelectorAll('tr'));
        var filtered = allRows.slice();
        var search = document.getElementById('search');
        var filterCategorie = document.getElementById('filterCategorie');
        var filterPrixMin = document.getElementById('filterPrixMin');
        var filterPrixMax = document.getElementById('filterPrixMax');
        var filterSeuil = document.getElementById('filterSeuil');
        var pager = document.getElementById('pager');
        var rangeText = document.getElementById('rangeText');
        var pageSizeSelect = document.getElementById('pageSize');

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

        function applyFilters(){
            var q = (search && search.value ? search.value.toLowerCase().trim() : '');
            var cat = filterCategorie.value.toLowerCase();
            var prixMin = filterPrixMin.value ? parseFloat(filterPrixMin.value) : null;
            var prixMax = filterPrixMax.value ? parseFloat(filterPrixMax.value) : null;
            var seuilMin = filterSeuil.value ? parseInt(filterSeuil.value) : null;

            filtered = allRows.filter(function(r){
                if(q){
                    var nom=getVal(r,'nom'), code=getVal(r,'code'), cb=getVal(r,'codeBarre'),
                        catText=getVal(r,'categorie'), desc=txt(r.querySelector('.desc')).toLowerCase();
                    if(!(nom.indexOf(q)>-1 || code.indexOf(q)>-1 || cb.indexOf(q)>-1 ||
                        catText.indexOf(q)>-1 || desc.indexOf(q)>-1)) {
                        return false;
                    }
                }

                if(cat && getVal(r,'categorie') !== cat) {
                    return false;
                }

                var prix = getVal(r,'prix');
                if(prixMin !== null && prix < prixMin) return false;
                if(prixMax !== null && prix > prixMax) return false;

                if(seuilMin !== null) {
                    var seuil = getVal(r,'seuil');
                    if(seuil < seuilMin) return false;
                }

                return true;
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
                .forEach(function(a){ a.textContent='↕'; a.style.opacity='0.6'; a.style.fontWeight='normal'; });
            if(sort.key){
                var th = table.querySelector('th.sortable[data-sort-key="'+sort.key+'"] .arrow');
                if(th) {
                    th.textContent = (sort.dir===1 ? '▲' : '▼');
                    th.style.opacity = '1';
                    th.style.fontWeight = '700';
                }
            }

            pager.innerHTML = '';
            if(total === 0) {
                pager.innerHTML = '<div style="color:var(--muted);font-style:italic;">Aucun résultat trouvé</div>';
            } else if(pages > 1){
                var prev=document.createElement('button');
                prev.className='btn';
                prev.textContent='‹ Précédent';
                prev.disabled=currentPage===1;
                prev.style.opacity = currentPage===1 ? '0.5' : '1';
                if(currentPage > 1) {
                    prev.onclick=function(){ currentPage--; render(); };
                }
                pager.appendChild(prev);

                var startPage = Math.max(1, currentPage - 2);
                var endPage = Math.min(pages, startPage + 4);
                if(endPage - startPage < 4) {
                    startPage = Math.max(1, endPage - 4);
                }

                if(startPage > 1) {
                    var first = document.createElement('button');
                    first.className = 'btn';
                    first.textContent = '1';
                    first.onclick = function(){ currentPage = 1; render(); };
                    pager.appendChild(first);

                    if(startPage > 2) {
                        var dots = document.createElement('span');
                        dots.textContent = '...';
                        dots.style.padding = '0 8px';
                        dots.style.color = 'var(--muted)';
                        pager.appendChild(dots);
                    }
                }

                for(var i=startPage; i<=endPage; i++){
                    var b=document.createElement('button');
                    b.className='btn' + (i===currentPage?' btn-primary':'');
                    b.textContent=i;
                    (function(p){ b.onclick=function(){ currentPage=p; render(); }; })(i);
                    pager.appendChild(b);
                }

                if(endPage < pages) {
                    if(endPage < pages - 1) {
                        var dots2 = document.createElement('span');
                        dots2.textContent = '...';
                        dots2.style.padding = '0 8px';
                        dots2.style.color = 'var(--muted)';
                        pager.appendChild(dots2);
                    }

                    var last = document.createElement('button');
                    last.className = 'btn';
                    last.textContent = pages;
                    last.onclick = function(){ currentPage = pages; render(); };
                    pager.appendChild(last);
                }

                var next=document.createElement('button');
                next.className='btn';
                next.textContent='Suivant ›';
                next.disabled=currentPage===pages;
                next.style.opacity = currentPage===pages ? '0.5' : '1';
                if(currentPage < pages) {
                    next.onclick=function(){ currentPage++; render(); };
                }
                pager.appendChild(next);
            }

            if(rangeText){
                if(total === 0) {
                    rangeText.textContent = 'Aucun médicament trouvé';
                } else {
                    var from = total===0?0:start+1;
                    var to   = Math.min(end,total);
                    rangeText.textContent = 'Affichage '+from+'–'+to+' sur '+total+' médicament' + (total>1?'s':'');
                }
            }
        }

        function resetFilters() {
            if(search) search.value = '';
            if(filterCategorie) filterCategorie.value = '';
            if(filterPrixMin) filterPrixMin.value = '';
            if(filterPrixMax) filterPrixMax.value = '';
            if(filterSeuil) filterSeuil.value = '';
            applyFilters();
        }
        window.resetFilters = resetFilters;

        var filterTimeout;
        function debounceFilter() {
            clearTimeout(filterTimeout);
            filterTimeout = setTimeout(applyFilters, 300);
        }

        if(search) search.addEventListener('input', debounceFilter);
        if(filterCategorie) filterCategorie.addEventListener('change', applyFilters);
        if(filterPrixMin) filterPrixMin.addEventListener('input', debounceFilter);
        if(filterPrixMax) filterPrixMax.addEventListener('input', debounceFilter);
        if(filterSeuil) filterSeuil.addEventListener('input', debounceFilter);

        if(pageSizeSelect) {
            pageSizeSelect.addEventListener('change', function() {
                pageSize = parseInt(this.value, 10);
                currentPage = 1;
                render();
            });
        }

        Array.prototype.slice.call(table.querySelectorAll('th.sortable')).forEach(function(th){
            th.addEventListener('click', function(){
                var key = th.getAttribute('data-sort-key');
                if(sort.key===key){ sort.dir*=-1; } else { sort.key=key; sort.dir=1; }
                applySort();
            });
        });

        applyFilters();
    })();
</script>

</body>
</html>