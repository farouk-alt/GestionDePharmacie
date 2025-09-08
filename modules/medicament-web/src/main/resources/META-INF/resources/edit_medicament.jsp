<%@ include file="/init.jsp" %>
<%@ page import="gestion_de_pharmacie.model.Medicament" %>
<%@ page import="gestion_de_pharmacie.service.MedicamentLocalServiceUtil" %>

<%
  String medicamentIdParam = request.getParameter("medicamentId");
  Medicament medicament = null;

  if (medicamentIdParam != null && !medicamentIdParam.isEmpty()) {
    long medicamentId = Long.parseLong(medicamentIdParam);
    medicament = MedicamentLocalServiceUtil.getMedicament(medicamentId);
  }
%>

<h2>Modifier Médicament</h2>

<% if (medicament != null) { %>
<portlet:actionURL name="updateMedicament" var="updateMedicamentURL" />

<aui:form action="${updateMedicamentURL}" method="post" name="fm">
  <aui:input name="medicamentId" type="hidden" value="<%= medicament.getIdMedicament() %>" />

  <aui:input name="nom" label="Nom" value="<%= medicament.getNom() %>" />
  <aui:input name="prix" label="Prix Unitaire" type="number" step="0.01" value="<%= medicament.getPrixUnitaire() %>" />
  <aui:input name="description" label="Description" type="textarea" value="<%= medicament.getDescription() %>" />
  <aui:input name="categorie" label="Catégorie" value="<%= medicament.getCategorie() %>" />
  <aui:input name="seuilMinimum" label="Seuil Minimum" type="number" value="<%= medicament.getSeuilMinimum() %>" />

  <aui:button type="submit" value="Mettre à jour" />
</aui:form>

<% } else { %>
<p>Aucun médicament sélectionné pour modification.</p>
<% } %>
