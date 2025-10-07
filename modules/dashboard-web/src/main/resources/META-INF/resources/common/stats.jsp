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

    double ticketMoyen = (nbVentes > 0) ? (totalVentes / nbVentes) : 0.0;

    Map<String, Double> ventesParUtilisateur = new HashMap<>();
    for (Vente v : ventes) {
        Utilisateur uu = UtilisateurLocalServiceUtil.fetchUtilisateur(v.getIdUtilisateur());
        String key = (uu != null && uu.getEmail() != null) ? uu.getEmail() : "inconnu";
        ventesParUtilisateur.put(key, ventesParUtilisateur.getOrDefault(key, 0.0) + v.getMontantTotal());
    }
    List<Map.Entry<String, Double>> topVendeurs = ventesParUtilisateur.entrySet()
            .stream()
            .sorted((a,b)->Double.compare(b.getValue(), a.getValue()))
            .limit(5)
            .collect(Collectors.toList());

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
        --primary:#2563EB; --secondary:#3B82F6; --accent:#10B981;
        --bg:#F8FAFC; --white:#fff; --text:#0F172A; --muted:#64748B;
        --card:#FFFFFF; --border:#E2E8F0; --shadow:0 4px 20px rgba(15,23,42,.08);
        --gradient-primary:linear-gradient(135deg, #1E40AF 0%, #3B82F6 100%);
        --gradient-accent:linear-gradient(135deg, #059669 0%, #10B981 100%);
    }
    .stats-wrap{
        font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,sans-serif;
        color:var(--text);
        background:var(--bg);
        padding:20px;
        border-radius:16px;
    }
    .stats-header{
        background:var(--gradient-primary);
        border-radius:16px;
        padding:24px 28px;
        margin-bottom:24px;
        color:var(--white);
        display:flex;
        align-items:center;
        gap:16px;
        box-shadow:0 8px 24px rgba(37,99,235,.2);
    }
    .stats-header .chip{
        width:48px;
        height:48px;
        border-radius:12px;
        background:rgba(255,255,255,.2);
        display:flex;
        align-items:center;
        justify-content:center;
        font-size:24px;
        backdrop-filter:blur(8px);
    }
    .stats-header h2{margin:0;font-size:24px;font-weight:800;letter-spacing:-.02em}

    .kpis{
        display:grid;
        grid-template-columns:repeat(auto-fit,minmax(200px,1fr));
        gap:16px;
        margin:0 0 24px 0;
        padding:0;
        list-style:none;
    }
    .kpis li{
        background:var(--card);
        border:1px solid var(--border);
        border-radius:14px;
        padding:20px;
        box-shadow:var(--shadow);
        transition:all .2s ease;
        position:relative;
        overflow:hidden;
    }
    .kpis li::before{
        content:'';
        position:absolute;
        top:0;
        left:0;
        width:4px;
        height:100%;
        background:var(--gradient-primary);
        opacity:0;
        transition:opacity .2s ease;
    }
    .kpis li:hover{
        transform:translateY(-2px);
        box-shadow:0 8px 32px rgba(15,23,42,.12);
    }
    .kpis li:hover::before{opacity:1}
    .kpi-label{
        color:var(--muted);
        font-weight:600;
        font-size:11px;
        letter-spacing:.06em;
        text-transform:uppercase;
        margin-bottom:8px;
    }
    .kpi-value{
        font-size:28px;
        font-weight:800;
        line-height:1.2;
        letter-spacing:-.02em;
    }
    .kpi-accent{color:#10B981}
    .kpi-secondary{color:#3B82F6}
    .kpi-primary{color:#2563EB}

    .grid-2{
        display:grid;
        grid-template-columns:repeat(auto-fit,minmax(480px,1fr));
        gap:20px;
        margin-bottom:20px;
    }
    @media (max-width: 1100px){
        .grid-2{grid-template-columns:1fr}
    }

    .chart-card{
        background:var(--card);
        border:1px solid var(--border);
        border-radius:14px;
        padding:24px;
        box-shadow:var(--shadow);
        transition:box-shadow .2s ease;
    }
    .chart-card:hover{
        box-shadow:0 8px 32px rgba(15,23,42,.12);
    }
    .chart-title{
        margin:0 0 16px 0;
        font-weight:800;
        font-size:16px;
        color:var(--text);
        letter-spacing:-.01em;
    }
    .chart-container{
        position:relative;
        height:280px;
        width:100%;
    }
    .chart-container.small{
        height:240px;
    }

    .table{width:100%;border-collapse:collapse}
    .table thead{background:#F8FAFC}
    .table th{
        padding:12px 14px;
        border-bottom:2px solid var(--border);
        text-align:left;
        font-size:11px;
        font-weight:700;
        letter-spacing:.06em;
        text-transform:uppercase;
        color:var(--muted);
    }
    .table td{
        padding:12px 14px;
        border-bottom:1px solid var(--border);
        color:var(--text);
    }
    .table tbody tr{transition:background .15s ease}
    .table tbody tr:hover{background:#F8FAFC}
    .table tbody tr:last-child td{border-bottom:none}
</style>

<div class="stats-wrap">
    <div class="stats-header">
        <div class="chip">📊</div>
        <h2>Statistiques Globales</h2>
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
        <li>
            <div class="kpi-label">Ticket moyen</div>
            <div class="kpi-value kpi-accent"><%= String.format(java.util.Locale.FRANCE, "%,.2f", ticketMoyen) %> DH</div>
        </li>
    </ul>

    <div class="grid-2">
        <div class="chart-card">
            <h3 class="chart-title">Évolution des ventes par mois</h3>
            <div class="chart-container">
                <canvas id="ventesChart"></canvas>
            </div>
        </div>

        <div class="chart-card">
            <h3 class="chart-title">Top 5 Médicaments</h3>
            <div class="chart-container">
                <canvas id="medicamentsChart"></canvas>
            </div>
        </div>
    </div>

    <div class="grid-2">
        <div class="chart-card">
            <h3 class="chart-title">Ventes par rôle</h3>
            <div class="chart-container small">
                <canvas id="rolesChart"></canvas>
            </div>
        </div>

        <div class="chart-card">
            <h3 class="chart-title">Top 5 Vendeurs</h3>
            <table class="table">
                <thead>
                <tr><th>#</th><th>Utilisateur</th><th>Total ventes</th></tr>
                </thead>
                <tbody>
                <%
                    int rank = 1;
                    for (Map.Entry<String, Double> e : topVendeurs) {
                %>
                <tr>
                    <td style="font-weight:700;color:var(--muted)"><%= rank++ %></td>
                    <td><%= e.getKey() %></td>
                    <td style="font-weight:700;color:var(--accent)"><%= String.format(java.util.Locale.FRANCE, "%,.2f", e.getValue()) %> DH</td>
                </tr>
                <%
                    }
                    if (topVendeurs.isEmpty()) {
                %>
                <tr><td colspan="3" style="color:var(--muted);text-align:center;padding:24px">Aucune vente</td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js@4"></script>
<script>
    Chart.defaults.color = '#0F172A';
    Chart.defaults.borderColor = 'rgba(226,232,240,.6)';
    Chart.defaults.plugins.legend.labels.boxWidth = 12;
    Chart.defaults.plugins.legend.labels.padding = 12;
    Chart.defaults.plugins.legend.labels.font = {size:12, weight:'600'};
    Chart.defaults.plugins.tooltip.backgroundColor = 'rgba(15,23,42,.95)';
    Chart.defaults.plugins.tooltip.padding = 12;
    Chart.defaults.plugins.tooltip.cornerRadius = 8;
    Chart.defaults.plugins.tooltip.titleColor = '#fff';
    Chart.defaults.plugins.tooltip.bodyColor = '#fff';
    Chart.defaults.plugins.tooltip.titleFont = {size:13, weight:'700'};

    const palette = {
        primary:'#2563EB',
        secondary:'#3B82F6',
        accent:'#10B981',
        purple:'#8B5CF6',
        orange:'#F59E0B',
        cyan:'#06B6D4',
        gray:'#64748B'
    };

    // Line chart - NO FILL to avoid black area
    new Chart(document.getElementById('ventesChart'), {
        type: 'line',
        data: {
            labels: [<%= moisLabels %>],
            datasets: [{
                label: 'Nombre de ventes',
                data: [<%= moisData %>],
                borderColor: palette.secondary,
                backgroundColor: 'rgba(59, 130, 246, 0.1)',
                pointBackgroundColor: palette.accent,
                pointBorderColor: '#fff',
                pointBorderWidth: 2,
                pointRadius: 5,
                pointHoverRadius: 7,
                borderWidth: 3,
                tension: .3,
                fill: true
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { display: false },
                tooltip: {
                    callbacks: {
                        label: (ctx) => ctx.parsed.y + ' vente(s)'
                    }
                }
            },
            scales: {
                x: {
                    grid: { color: 'rgba(100, 116, 139, 0.15)', drawBorder:false },
                    ticks: {font:{size:11}}
                },
                y: {
                    grid: { color: 'rgba(100, 116, 139, 0.15)', drawBorder:false },
                    ticks: { precision:0, font:{size:11} },
                    beginAtZero: true
                }
            }
        }
    });

    // Bar chart - solid colors
    new Chart(document.getElementById('medicamentsChart'), {
        type: 'bar',
        data: {
            labels: [<%= medsLabels %>],
            datasets: [{
                label: 'Quantité',
                data: [<%= medsData %>],
                backgroundColor: [
                    palette.primary,
                    palette.secondary,
                    palette.accent,
                    palette.purple,
                    palette.orange
                ],
                borderColor: [
                    palette.primary,
                    palette.secondary,
                    palette.accent,
                    palette.purple,
                    palette.orange
                ],
                borderWidth: 2,
                borderRadius: 8,
                borderSkipped: false
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: { legend: { display:false } },
            scales: {
                x: {
                    grid: { display:false },
                    ticks: {font:{size:11}}
                },
                y: {
                    grid: { color: 'rgba(100, 116, 139, 0.15)', drawBorder:false },
                    ticks: { precision:0, font:{size:11} },
                    beginAtZero: true
                }
            }
        }
    });

    // Doughnut chart - solid vibrant colors
    new Chart(document.getElementById('rolesChart'), {
        type: 'doughnut',
        data: {
            labels: [<%= rolesLabels %>],
            datasets: [{
                data: [<%= rolesData %>],
                backgroundColor: [
                    palette.accent,    // Green
                    palette.secondary, // Blue
                    palette.purple,    // Purple
                    palette.orange,    // Orange
                    palette.cyan,      // Cyan
                    palette.primary    // Dark Blue
                ],
                borderColor: '#fff',
                borderWidth: 4,
                hoverOffset: 10
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position:'bottom',
                    labels: {
                        padding:16,
                        usePointStyle: true,
                        pointStyle: 'circle'
                    }
                },
                tooltip: {
                    callbacks: {
                        label: (ctx) => ctx.label + ': ' + ctx.parsed.toFixed(2) + ' DH'
                    }
                }
            },
            cutout: '60%'
        }
    });
</script>