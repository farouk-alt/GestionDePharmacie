<%@ include file="/init.jsp" %>
<%@ page import="gestion_de_pharmacie.model.Medicament" %>
<%@ page import="gestion_de_pharmacie.service.MedicamentLocalServiceUtil" %>
<%@ page import="java.util.List" %>

<h2>Liste des Médicaments</h2>

<%
    List<Medicament> medicaments = MedicamentLocalServiceUtil.getMedicaments(-1, -1);
%>

<table class="table table-bordered">
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prix</th>
        <th>Date Ajout</th>
    </tr>
    <%
        for (Medicament m : medicaments) {

    %>
    <tr>
        <td><%= m.getIdMedicament() %></td>
        <td><%= m.getNom() %></td>
        <td><%= m.getPrixUnitaire() %></td>
        <td><%= m.getDateAjout() %></td>
        <td>
            <portlet:actionURL name="updateMedicament" var="editURL">
                <portlet:param name="medicamentId" value="<%= String.valueOf(m.getIdMedicament()) %>" />
            </portlet:actionURL>

            <aui:form action="${editURL}" method="post" style="display:inline;">
                <aui:input name="nom" type="text" value="<%= m.getNom() %>" size="10"/>
                <aui:input name="prix" type="number" step="0.01" value="<%= m.getPrixUnitaire() %>" style="width:80px;"/>
                <aui:button type="submit" value="Éditer"/>
            </aui:form>
        </td>
    </tr>

    <% } %>
</table>

<h3>Ajouter un Médicament</h3>

<portlet:actionURL name="ajouterMedicament" var="addMedicamentURL" />

<aui:form action="${addMedicamentURL}" method="post" name="fm">
    <aui:input name="nom" label="Nom" />
    <aui:input name="prix" label="Prix Unitaire" type="number" step="0.01"/>
    <aui:button type="submit" value="Ajouter" />
</aui:form>


<liferay-ui:success key="medicament-added-successfully" message="Médicament ajouté avec succès !" />
