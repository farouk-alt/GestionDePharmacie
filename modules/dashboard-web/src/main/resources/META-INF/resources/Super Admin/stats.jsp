<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%@ page import="java.util.*" %>
<%@ page import="java.util.stream.*" %>
<%@ page import="gestion_de_pharmacie.model.*" %>
<%@ page import="gestion_de_pharmacie.service.*" %>

<style>
    ul.kpis { list-style:none; padding-left:0; display:grid; grid-template-columns:repeat(auto-fit,minmax(200px,1fr)); gap:10px;}
    ul.kpis li { background:#fff; border:1px solid #eee; border-radius:8px; padding:10px 12px; }
</style>

<h2>📊 Statistiques Globales</h2>

<%
    int nbUtilisateurs = UtilisateurLocalServiceUtil.getUtilisateursCount();
    int nbMedicaments  = MedicamentLocalServiceUtil.getMedicamentsCount();
    int nbCommandes    = CommandeLocalServiceUtil.getCommandesCount();
    int nbVentes       = VenteLocalServiceUtil.getVentesCount();

    double totalVentes = 0;
    Map<String, Integer> ventesParMois = new LinkedHashMap<>();
    Map<String, Integer> topMedicaments = new HashMap<>();

    List<Vente> ventes = VenteLocalServiceUtil.getVentes(-1, -1);
    Calendar cal = Calendar.getInstance();

    for (Vente v : ventes) {
        totalVentes += v.getMontantTotal();
        if (v.getDateVente() != null) {
            cal.setTime(v.getDateVente());
            String mois = (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
            ventesParMois.put(mois, ventesParMois.getOrDefault(mois, 0) + 1);
        }
    }

    List<VenteDetail> allDetails = VenteDetailLocalServiceUtil.getVenteDetails(-1, -1);
    for (VenteDetail d : allDetails) {
        Medicament med = MedicamentLocalServiceUtil.fetchMedicament(d.getIdMedicament());
        String medName = (med != null ? med.getNom() : "Inconnu");
        topMedicaments.put(medName, topMedicaments.getOrDefault(medName, 0) + d.getQuantite());    }

    topMedicaments = topMedicaments.entrySet().stream()
            .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
            .limit(5)
            .collect(LinkedHashMap::new, (m,e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

    Map<String, Double> ventesParRole = new HashMap<>();
    for (Vente v : ventes) {
        Utilisateur u = UtilisateurLocalServiceUtil.fetchUtilisateur(v.getIdUtilisateur());
        String role = (u != null ? u.getRole() : "INCONNU");
        ventesParRole.put(role, ventesParRole.getOrDefault(role, 0.0) + v.getMontantTotal());    }

    String moisLabels = ventesParMois.isEmpty() ? "\"Aucun\"" :
            "\"" + String.join("\", \"", ventesParMois.keySet()) + "\"";
    String moisData = ventesParMois.isEmpty() ? "0" :
            String.join(", ", ventesParMois.values().stream().map(String::valueOf).toArray(String[]::new));

    String medsLabels = topMedicaments.isEmpty() ? "\"Aucun\"" :
            "\"" + String.join("\", \"", topMedicaments.keySet()) + "\"";
    String medsData = topMedicaments.isEmpty() ? "0" :
            String.join(", ", topMedicaments.values().stream().map(String::valueOf).toArray(String[]::new));

    String rolesLabels = ventesParRole.isEmpty() ? "\"Aucun\"" :
            "\"" + String.join("\", \"", ventesParRole.keySet()) + "\"";
    String rolesData = ventesParRole.isEmpty() ? "0" :
            String.join(", ", ventesParRole.values().stream().map(String::valueOf).toArray(String[]::new));
%>

<ul class="kpis">
    <li>👥 Utilisateurs: <b><%= nbUtilisateurs %></b></li>
    <li>💊 Médicaments: <b><%= nbMedicaments %></b></li>
    <li>📦 Commandes:   <b><%= nbCommandes %></b></li>
    <li>🛒 Ventes:      <b><%= nbVentes %></b></li>
    <li>💰 Total Ventes: <b><%= String.format(java.util.Locale.FRANCE, "%,.2f", totalVentes) %> DH</b></li>
</ul>

<div style="width:70%; margin:auto;">
    <canvas id="ventesChart"></canvas>
</div>

<div style="width:70%; margin:auto; margin-top:30px;">
    <canvas id="medicamentsChart"></canvas>
</div>

<div style="width:60%; margin:auto; margin-top:30px;">
    <canvas id="rolesChart"></canvas>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    // Ventes par mois
    new Chart(document.getElementById("ventesChart"), {
        type: 'line',
        data: {
            labels: [<%= moisLabels %>],
            datasets: [{
                label: 'Ventes par mois',
                data: [<%= moisData %>],
                borderColor: 'blue',
                backgroundColor: 'rgba(0,0,255,0.1)',
                fill: true
            }]
        },
        options: { plugins: { title: { display: true, text: 'Évolution des ventes par mois' } } }
    });

    // Médicaments les plus vendus
    new Chart(document.getElementById("medicamentsChart"), {
        type: 'bar',
        data: {
            labels: [<%= medsLabels %>],
            datasets: [{
                label: 'Quantité vendue',
                data: [<%= medsData %>]
            }]
        },
        options: { plugins: { title: { display: true, text: 'Top 5 Médicaments les plus vendus' } } }
    });

    // Répartition par rôle
    new Chart(document.getElementById("rolesChart"), {
        type: 'pie',
        data: {
            labels: [<%= rolesLabels %>],
            datasets: [{ data: [<%= rolesData %>] }]
        },
        options: { plugins: { title: { display: true, text: "Répartition des ventes par rôle d’utilisateur" } } }
    });
</script>
