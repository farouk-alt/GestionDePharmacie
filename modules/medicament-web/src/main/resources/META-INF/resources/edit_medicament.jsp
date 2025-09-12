<%@ include file="/init.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="gestion_de_pharmacie.model.Medicament" %>
<%@ page import="gestion_de_pharmacie.service.MedicamentLocalServiceUtil" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ taglib uri="http://liferay.com/tld/ui"  prefix="liferay-ui" %>

<%
    boolean inModal = ParamUtil.getBoolean(request, "modal");
    String medicamentIdParam = request.getParameter("medicamentId");
    Medicament medicament = null;
    if (medicamentIdParam != null && !medicamentIdParam.isEmpty()) {
        try {
            long medicamentId = Long.parseLong(medicamentIdParam);
            medicament = MedicamentLocalServiceUtil.getMedicament(medicamentId);
        } catch (Exception ignored) {}
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>
<style>
    /* always available (modal or full page) */
    .edit-med-modal{ padding:16px; }
    .edit-med-grid{
        display:grid;
        grid-template-columns:repeat(auto-fit,minmax(220px,1fr));
        gap:12px;
    }
    .edit-med-grid .fld{ display:flex; flex-direction:column; gap:6px; }
    .edit-med-grid label{ font-weight:600; color:#1E3A8A; }
    .edit-med-grid input,
    .edit-med-grid textarea{
        display:block !important;
        width:100% !important;
        box-sizing:border-box !important;
        background:#fff !important;
        color:#111827 !important;
        border:1px solid #E5E7EB !important;
        border-radius:10px !important;
        padding:10px !important;
        line-height:1.2 !important;
        min-height:40px;
    }
    .edit-med-grid textarea{ min-height:96px; resize:vertical; }
    .edit-actions{ display:flex; gap:8px; justify-content:flex-end; }
</style>

<% if (!inModal) { %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <title>Modifier Médicament</title>
    <style>
        :root{--primary:#1E3A8A;--secondary:#3B82F6;--accent:#10B981;--bg:#F3F4F6;--white:#fff;--text:#111827;--muted:#6B7280;--border:#E5E7EB}
        body{background:var(--bg);margin:0;color:var(--text);font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif}
        .wrap{max-width:900px;margin:24px auto;padding:0 16px}
        .header{background:linear-gradient(135deg,var(--primary),var(--secondary));color:#fff;border-radius:14px;padding:18px 20px;margin-bottom:16px;display:flex;align-items:center;justify-content:space-between;box-shadow:0 10px 24px rgba(30,58,138,.25)}
        .header h2{margin:0;font-size:20px}
        .sub{opacity:.9;font-size:12px;margin-top:4px}
        .btn{display:inline-block;background:#fff;color:var(--primary);border:1px solid transparent;padding:8px 12px;border-radius:10px;font-weight:600;text-decoration:none;cursor:pointer;transition:.2s}
        .btn:hover{background:#F8FAFC;transform:translateY(-1px)}
        .btn-primary{background:var(--accent);color:#fff;border-color:var(--accent)}
        .btn-primary:hover{filter:brightness(.95);color:var(--primary)}
        .btn-ghost{background:#fff;color:var(--primary);border:1px solid var(--border)}
        .card{background:var(--white);border:1px solid var(--border);border-radius:12px;padding:16px;box-shadow:0 6px 18px rgba(17,24,39,.06);margin-bottom:16px}
        .grid{display:grid;grid-template-columns:1fr 280px;gap:16px}
        .form-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:12px}
        .form-grid .fld{display:flex;flex-direction:column;gap:6px}
        .form-grid label{font-weight:600;color:var(--primary)}
        .form-grid input,.form-grid textarea{width:100%;box-sizing:border-box;background:#fff;color:var(--text);border:1px solid var(--border);border-radius:10px;padding:10px;line-height:1.2;min-height:40px}
        .form-grid textarea{min-height:96px;resize:vertical}
        .form-grid input:focus,.form-grid textarea:focus{outline:none;border-color:var(--secondary);box-shadow:0 0 0 3px rgba(59,130,246,.15)}
        .actions{display:flex;gap:8px;flex-wrap:wrap;margin-top:12px}
        .meta{background:#F8FAFC;border:1px solid var(--border);border-radius:10px;padding:12px;font-size:13px}
        .meta dt{color:var(--muted)} .meta dd{margin:0 0 8px 0}
        .empty{padding:14px;border:1px dashed var(--border);border-radius:12px;background:#fff}

    </style>

</head>
<body>
<div class="wrap">
    <div class="header">
        <div>
            <h2>✏️ Modifier Médicament</h2>
            <div class="sub">
                <% if (medicament != null) { %>
                ID: <strong><%= medicament.getIdMedicament() %></strong>
                <% if (medicament.getDateAjout()!=null) { %> · Créé le <%= sdf.format(medicament.getDateAjout()) %><% } %>
                <% } else { %>
                Aucun médicament sélectionné
                <% } %>
            </div>
        </div>
        <button type="button" class="btn btn-ghost" onclick="history.back()">← Retour</button>
    </div>
    <% } %>

    <!-- Flash messages (work inside modal too) -->
    <liferay-ui:success key="medicament-updated-successfully" message="Médicament mis à jour avec succès !" />
    <liferay-ui:error   key="medicament-update-error"       message="Échec de la mise à jour du médicament." />

    <div class="card">
        <% if (medicament == null) { %>
        <div class="empty">
            <p>Aucun médicament sélectionné pour modification.</p>
            <% if (!inModal) { %><button type="button" class="btn" onclick="history.back()">← Revenir à la liste</button><% } %>
        </div>
        <% } else { %>

        <%-- If redirected with ?modal=1&close=1 after update, auto-close modal and refresh parent --%>
        <%
            boolean shouldClose = ParamUtil.getBoolean(request, "close");
        %>
        <% if (inModal && shouldClose) { %>
        <script>
            (function(){
                var opener = Liferay.Util.getOpener && Liferay.Util.getOpener();
                if (opener) {
                    opener.Liferay.fire('closeModal', { id: '<portlet:namespace/>editModal' });
                    opener.location.reload();
                }
            })();
        </script>
        <% } %>

        <div class="<%= inModal ? "edit-med-modal" : "edit-med-modal" %>">
            <portlet:actionURL name="updateMedicament" var="updateMedicamentURL" />

            <form action="${updateMedicamentURL}" method="post"
                  id="<portlet:namespace/>editForm" class="edit-med-grid" novalidate>

                <input type="hidden" name="<portlet:namespace/>fromModal" value="<%= inModal ? "1" : "0" %>"/>
                <input type="hidden" name="<portlet:namespace/>medicamentId" value="<%= medicament.getIdMedicament() %>"/>

                <div class="fld">
                    <label for="<portlet:namespace/>nom">Nom</label>
                    <input id="<portlet:namespace/>nom" name="<portlet:namespace/>nom"
                           type="text" required
                           value="<%= HtmlUtil.escape(String.valueOf(medicament.getNom())) %>"/>
                </div>

                <div class="fld">
                    <label for="<portlet:namespace/>prix">Prix Unitaire (DH)</label>
                    <input id="<portlet:namespace/>prix" name="<portlet:namespace/>prix"
                           type="number" step="0.01" min="0" required
                           value="<%= medicament.getPrixUnitaire() %>"/>
                </div>

                <div class="fld">
                    <label for="<portlet:namespace/>categorie">Catégorie</label>
                    <input id="<portlet:namespace/>categorie" name="<portlet:namespace/>categorie"
                           type="text"
                           value="<%= HtmlUtil.escape(String.valueOf(medicament.getCategorie())) %>"/>
                </div>

                <div class="fld">
                    <label for="<portlet:namespace/>seuilMinimum">Seuil Minimum</label>
                    <input id="<portlet:namespace/>seuilMinimum" name="<portlet:namespace/>seuilMinimum"
                           type="number" min="0"
                           value="<%= medicament.getSeuilMinimum() %>"/>
                </div>

                <div class="fld" style="grid-column:1/-1">
                    <label for="<portlet:namespace/>description">Description</label>
                    <textarea id="<portlet:namespace/>description" name="<portlet:namespace/>description"><%= HtmlUtil.escape(String.valueOf(medicament.getDescription())) %></textarea>
                </div>

                <div class="edit-actions" style="grid-column:1/-1">
                    <button type="submit" class="btn btn-primary">Mettre à jour</button>
                    <% if (!inModal) { %><button type="button" class="btn" onclick="history.back()">Annuler</button><% } %>
                </div>
            </form>
        </div>

        <% if (!inModal) { %>
            <aside class="meta">
                <dl>
                    <dt>Identifiant</dt>
                    <dd><strong><%= medicament.getIdMedicament() %></strong></dd>

                    <dt>Date d’ajout</dt>
                    <dd><%= medicament.getDateAjout()!=null ? sdf.format(medicament.getDateAjout()) : "-" %></dd>

                    <dt>Catégorie</dt>
                    <dd><%= medicament.getCategorie()!=null ? medicament.getCategorie() : "-" %></dd>

                    <dt>Seuil minimum</dt>
                    <dd><%= medicament.getSeuilMinimum() %></dd>
                </dl>
            </aside>
            <% } %>
        </div>
        <% } %>
    </div>

    <% if (!inModal) { %>
</div>
</body>
</html>
<% } %>
