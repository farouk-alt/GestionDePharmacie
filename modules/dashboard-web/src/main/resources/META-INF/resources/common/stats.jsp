<%--
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
--%>
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
            String mois = String.format("%02d/%d", cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
            ventesParMois.put(mois, ventesParMois.getOrDefault(mois, 0) + 1);
        }
    }

    List<VenteDetail> allDetails = VenteDetailLocalServiceUtil.getVenteDetails(-1, -1);
    for (VenteDetail d : allDetails) {
        Medicament med = MedicamentLocalServiceUtil.fetchMedicament(d.getIdMedicament());
        String medName = (med != null ? med.getNom() : "Inconnu");
        topMedicaments.put(medName, topMedicaments.getOrDefault(medName, 0) + d.getQuantite());
    }

    topMedicaments = topMedicaments.entrySet().stream()
            .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
            .limit(5)
            .collect(LinkedHashMap::new, (m,e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

    Map<String, Double> ventesParRole = new LinkedHashMap<>();
    for (Vente v : ventes) {
        Utilisateur u = UtilisateurLocalServiceUtil.fetchUtilisateur(v.getIdUtilisateur());
        String role = (u != null && u.getRole() != null ? u.getRole() : "INCONNU");
        ventesParRole.put(role, ventesParRole.getOrDefault(role, 0.0) + v.getMontantTotal());
    }

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

<style>
    :root{
        --primary:#1E3A8A;    /* deep blue */
        --secondary:#3B82F6;  /* light blue */
        --accent:#10B981;     /* green */
        --bg:#F3F4F6;         /* light gray */
        --white:#fff;
        --text:#111827;
        --muted:#6B7280;
        --card:#FFFFFF;
        --border:#E5E7EB;
    }
    .stats-wrap{font-family:'Segoe UI',Tahoma,Verdana,sans-serif;color:var(--text)}
    .stats-header{
        display:flex;align-items:center;gap:12px;margin:0 0 14px 0
    }
    .stats-header .chip{
        width:38px;height:38px;border-radius:10px;background:linear-gradient(135deg,var(--primary),var(--secondary));
        display:flex;align-items:center;justify-content:center;color:var(--white)
    }
    .kpis{
        display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:14px;margin:18px 0 22px 0;padding:0;list-style:none
    }
    .kpis li{
        background:var(--card);border:1px solid var(--border);border-radius:12px;padding:16px 14px;
        box-shadow:0 8px 24px rgba(30,58,138,.08)
    }
    .kpi-label{color:var(--muted);font-weight:600;font-size:12px;letter-spacing:.4px;text-transform:uppercase}
    .kpi-value{font-size:22px;font-weight:800;margin-top:6px}
    .kpi-accent{color:var(--accent)}
    .kpi-secondary{color:var(--secondary)}
    .kpi-primary{color:var(--primary)}

    .chart-card{
        background:var(--card);border:1px solid var(--border);border-radius:14px;padding:18px;margin:16px auto;max-width:980px;
        box-shadow:0 12px 36px rgba(17,24,39,.08)
    }
    .chart-title{margin:0 0 10px 0;font-weight:800;color:var(--primary)}
</style>

<div class="stats-wrap">
    <div class="stats-header">
        <div class="chip">📊</div>
        <h2 style="margin:0;color:var(--primary)">Statistiques Globales</h2>
    </div>

    <ul class="kpis">
        <li>
            <div class="kpi-label">Utilisateurs</div>
            <div class="kpi-value kpi-primary"><%= nbUtilisateurs %></div>
        </li>
        <li>
            <div class="kpi-label">Médicaments</div>
            <div class="kpi-value kpi-secondary"><%= nbMedicaments %></div>
        </li>
        <li>
            <div class="kpi-label">Commandes</div>
            <div class="kpi-value"><%= nbCommandes %></div>
        </li>
        <li>
            <div class="kpi-label">Ventes</div>
            <div class="kpi-value"><%= nbVentes %></div>
        </li>
        <li>
            <div class="kpi-label">Total Ventes</div>
            <div class="kpi-value kpi-accent"><%= String.format(java.util.Locale.FRANCE, "%,.2f", totalVentes) %> DH</div>
        </li>
    </ul>

    <div class="chart-card">
        <h3 class="chart-title">Évolution des ventes par mois</h3>
        <canvas id="ventesChart"></canvas>
    </div>

    <div class="chart-card">
        <h3 class="chart-title">Top 5 Médicaments les plus vendus</h3>
        <canvas id="medicamentsChart"></canvas>
    </div>

    <div class="chart-card" style="max-width:760px">
        <h3 class="chart-title">Répartition des ventes par rôle d’utilisateur</h3>
        <canvas id="rolesChart"></canvas>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js@4"></script>
<script>
    const palette = {
        primary:'#1E3A8A',
        secondary:'#3B82F6',
        accent:'#10B981',
        gray:'#9CA3AF',
        white:'#FFFFFF'
    };

    // Helpers
    const withAlpha = (hex, alpha) => {
        // convert #RRGGBB to rgba
        const m = hex.replace('#','');
        const r = parseInt(m.substring(0,2),16);
        const g = parseInt(m.substring(2,4),16);
        const b = parseInt(m.substring(4,6),16);
        return `rgba(${r}, ${g}, ${b}, ${alpha})`;
    };

    // Ventes par mois (line)
    new Chart(document.getElementById('ventesChart'), {
        type: 'line',
        data: {
            labels: [<%= moisLabels %>],
            datasets: [{
                label: 'Ventes par mois',
                data: [<%= moisData %>],
                borderColor: palette.secondary,
                backgroundColor: withAlpha(palette.secondary, .18),
                pointBackgroundColor: palette.accent,
                pointBorderColor: palette.white,
                pointRadius: 4,
                tension: .25,
                fill: true
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: false },
                title: { display: false }
            },
            scales: {
                x: { grid: { color: withAlpha(palette.gray,.2) } },
                y: { grid: { color: withAlpha(palette.gray,.2) }, ticks: { precision:0 } }
            }
        }
    });

    // Top médicaments (bar)
    const medsColors = [palette.primary, palette.secondary, palette.accent, '#0EA5E9', '#22D3EE'];
    new Chart(document.getElementById('medicamentsChart'), {
        type: 'bar',
        data: {
            labels: [<%= medsLabels %>],
            datasets: [{
                label: 'Quantité vendue',
                data: [<%= medsData %>],
                backgroundColor: medsColors.map(c => withAlpha(c,.85)),
                borderColor: medsColors,
                borderWidth: 1.5
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display:false },
                title: { display:false }
            },
            scales: {
                x: { grid: { display:false } },
                y: { grid: { color: withAlpha(palette.gray,.2) }, ticks: { precision:0 } }
            }
        }
    });

    // Répartition par rôle (pie)
    const roleColors = [palette.accent, palette.secondary, palette.primary, '#14B8A6', '#60A5FA', '#64748B'];
    new Chart(document.getElementById('rolesChart'), {
        type: 'pie',
        data: {
            labels: [<%= rolesLabels %>],
            datasets: [{
                data: [<%= rolesData %>],
                backgroundColor: roleColors.map(c => withAlpha(c,.9)),
                borderColor: palette.white,
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { position:'bottom' },
                title: { display:false }
            }
        }
    });
</script>
