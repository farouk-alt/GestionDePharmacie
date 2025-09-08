<%@ include file="/init.jsp" %>
<%@ page import="gestion_de_pharmacie.model.Medicament" %>
<%@ page import="gestion_de_pharmacie.service.MedicamentLocalServiceUtil" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<h2>Liste des Médicaments</h2>

<%
    List<Medicament> medicaments = MedicamentLocalServiceUtil.getMedicaments(-1, -1);
%>

<table class="table table-bordered">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prix</th>
        <th>Date Ajout</th>
        <th>Actions</th>
        <th>Description</th>
        <th>Catégorie</th>
        <th>Seuil Minimum</th>

    </tr>
    </thead>
    <tbody>
    <%
        for (Medicament m : medicaments) {
    %>
    <tr>
        <td><%= m.getIdMedicament() %></td>
        <td><%= m.getNom() %></td>
        <td><%= m.getPrixUnitaire() %></td>
        <td><%= m.getDateAjout() %></td>
        <td><%= m.getDescription() %></td>
        <td><%= m.getCategorie() %></td>
        <td><%= m.getSeuilMinimum() %></td>
        <td>
            <portlet:renderURL var="editURL">
                <portlet:param name="mvcPath" value="/edit_medicament.jsp" />
                <portlet:param name="medicamentId" value="<%= String.valueOf(m.getIdMedicament()) %>" />
            </portlet:renderURL>
            <a href="${editURL}" class="btn btn-primary btn-sm">Éditer</a>

            <portlet:actionURL name="deleteMedicament" var="deleteURL">
                <portlet:param name="medicamentId" value="<%= String.valueOf(m.getIdMedicament()) %>" />
            </portlet:actionURL>

            <aui:form action="${deleteURL}" method="post" style="display:inline;" onsubmit="return confirm('Voulez-vous vraiment supprimer ce médicament ?');">
                <aui:button type="submit" value="Supprimer" cssClass="btn btn-danger btn-sm" />
            </aui:form>
        </td>

        </td>
    </tr>
    <% } %>
    </tbody>
</table>

<h3>Ajouter un Médicament</h3>

<h3>Ajouter un Médicament</h3>

<portlet:actionURL name="ajouterMedicament" var="addMedicamentURL" />

<aui:form action="${addMedicamentURL}" method="post" name="fm">
    <aui:input name="nom" label="Nom" />
    <aui:input name="prix" label="Prix Unitaire" type="number" step="0.01" />
    <aui:input name="description" label="Description" type="textarea" />
    <aui:input name="categorie" label="Catégorie" />
    <aui:input name="seuilMinimum" label="Seuil Minimum" type="number" />
    <aui:button type="submit" value="Ajouter" />
</aui:form>


<liferay-ui:success key="medicament-added-successfully" message="Médicament ajouté avec succès !" />
<liferay-ui:success key="medicament-updated-successfully" message="Médicament mis à jour avec succès !" />
<liferay-ui:success key="medicament-deleted-successfully" message="Médicament supprimé avec succès !" />
