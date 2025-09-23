<%@ page import="java.util.List" %>
<%@ page import="gestion_de_pharmacie.model.Utilisateur" %>
<%@ page import="gestion_de_pharmacie.model.Medicament" %>
<%@ page import="gestion_de_pharmacie.model.Commande" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="gestion_de_pharmacie.service.UtilisateurLocalServiceUtil" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<liferay-theme:defineObjects />

<h2>Créer une nouvelle commande</h2>

<%
    List<Utilisateur> fournisseurs = (List<Utilisateur>) request.getAttribute("fournisseurs");
    List<Medicament> medicaments = (List<Medicament>) request.getAttribute("medicaments");
    if (fournisseurs == null) fournisseurs = new java.util.ArrayList<>();
    if (medicaments == null) medicaments = new java.util.ArrayList<>();
%>

<aui:form actionName="createCommande" method="post">

    <!-- Fournisseur -->
    <c:choose>
        <c:when test="${not empty fournisseurs}">
            <aui:select label="Fournisseur" name="fournisseurId" required="true">
                <%
                    for (Utilisateur f : fournisseurs) {
                %>
                <aui:option value="<%= f.getIdUtilisateur() %>">
                    <%= HtmlUtil.escape(f.getNom() + " " + f.getPrenom()) %>
                </aui:option>
                <%
                    }
                %>
            </aui:select>
        </c:when>
        <c:otherwise>
            <div class="message info">Aucun fournisseur disponible.</div>
        </c:otherwise>
    </c:choose>

    <hr/>

    <!-- Médicaments -->
    <c:choose>
        <c:when test="${not empty medicaments}">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Sélection</th>
                    <th>Médicament</th>
                    <th>Prix Unitaire</th>
                    <th>Quantité</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (Medicament m : medicaments) {
                %>
                <tr>
                    <td>
                        <aui:input type="checkbox" name="medicamentId" value="<%= m.getIdMedicament() %>" />
                    </td>
                    <td><%= HtmlUtil.escape(m.getNom()) %></td>
                    <td><fmt:formatNumber value="<%= m.getPrixUnitaire() %>" type="number" minFractionDigits="2" maxFractionDigits="2" /> DH</td>
                    <td>
                        <aui:input type="number" name="quantite_<%= m.getIdMedicament() %>" value="1" min="1" />
                    </td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>

            <aui:button type="submit" value="Créer la commande" />
        </c:when>
        <c:otherwise>
            <div>Aucun médicament trouvé.</div>
        </c:otherwise>
    </c:choose>
</aui:form>

<hr/>

<h2>Liste des Commandes</h2>

<%
    List<Commande> commandes = (List<Commande>) request.getAttribute("commandes");
%>

<c:if test="${empty commandes}">
    <div>Aucune commande trouvée.</div>
</c:if>

<c:if test="${not empty commandes}">
    <table class="table table-striped table-bordered" style="width:100%;border-collapse:collapse;">
        <thead>
        <tr>
            <th>ID</th>
            <th>Fournisseur</th>
            <th>Date</th>
            <th>Statut</th>
            <th>Montant Total (DH)</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Commande c : commandes) {
                String fournisseurName = "-";
                try {
                    Utilisateur f = UtilisateurLocalServiceUtil.getUtilisateur(c.getIdFournisseur());
                    fournisseurName = f.getNom() + " " + f.getPrenom();
                } catch (Exception ignore) {}
        %>
        <tr>
            <td><%= c.getIdCommande() %></td>
            <td><%= HtmlUtil.escape(fournisseurName) %></td>
            <td><fmt:formatDate value="<%= c.getDateCommande() %>" pattern="dd/MM/yyyy HH:mm" /></td>
            <td><%= HtmlUtil.escape(c.getStatut()) %></td>
            <td><fmt:formatNumber value="<%= c.getMontantTotal() %>" type="number" minFractionDigits="2" maxFractionDigits="2" /></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</c:if>
