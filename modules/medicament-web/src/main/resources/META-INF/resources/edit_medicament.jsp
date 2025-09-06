<%@ include file="/init.jsp" %>
<%@ page import="gestion_de_pharmacie.model.Medicament" %>
<%@ page import="gestion_de_pharmacie.service.MedicamentLocalServiceUtil" %>

<%
  long medicamentId = Long.parseLong(request.getParameter("medicamentId"));
  Medicament medicament = MedicamentLocalServiceUtil.getMedicament(medicamentId);
%>

<h2>Modifier Médicament</h2>

<portlet:actionURL name="updateMedicament" var="updateMedicamentURL" />

<aui:form action="${updateMedicamentURL}" method="post" name="fm">
  <aui:input name="medicamentId" type="hidden" value="<%= medicament.getIdMedicament() %>" />
  <aui:input name="nom" label="Nom" value="<%= medicament.getNom() %>" />
  <aui:input name="prix" label="Prix Unitaire" type="number" step="0.01" value="<%= medicament.getPrixUnitaire() %>" />
  <aui:button type="submit" value="Mettre à jour" />
</aui:form>
