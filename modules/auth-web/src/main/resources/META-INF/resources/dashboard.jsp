<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<portlet:defineObjects />

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord - PharmaCare</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .dashboard-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .dashboard-header {
            background: linear-gradient(135deg, #4e54c8, #8f94fb);
            color: white;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .user-avatar {
            width: 60px;
            height: 60px;
            background: rgba(255, 255, 255, 0.2);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
        }

        .logout-btn {
            background: white;
            color: #4e54c8;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
        }

        .logout-btn:hover {
            background: #f5f5f5;
            transform: translateY(-2px);
        }

        .dashboard-cards {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .card-header {
            display: flex;
            align-items: center;
            margin-bottom: 15px;
        }

        .card-icon {
            width: 40px;
            height: 40px;
            background: #f0f4ff;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 15px;
            color: #4e54c8;
        }

        .admin-section {
            background: #fff9e6;
            border-left: 4px solid #ffc107;
            padding: 20px;
            border-radius: 5px;
            margin-top: 30px;
        }

        .employee-management {
            margin-top: 30px;
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .employee-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        .employee-table th, .employee-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }

        .employee-table th {
            background-color: #f8f9fa;
            font-weight: 600;
        }

        .employee-table tr:hover {
            background-color: #f8f9fa;
        }

        .role-select {
            padding: 8px;
            border-radius: 4px;
            border: 1px solid #ddd;
            width: 100%;
        }

        .update-btn {
            background: #4e54c8;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            transition: background 0.3s;
            width: 100%;
        }

        .update-btn:hover {
            background: #3a3fb8;
        }

        .message {
            padding: 10px;
            margin: 10px 0;
            border-radius: 4px;
            text-align: center;
        }

        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .loading {
            display: none;
            text-align: center;
            margin: 20px 0;
        }

        /* Debug styles */
        .debug-info {
            background: #f8f9fa;
            border: 1px solid #dee2e6;
            padding: 10px;
            margin: 10px 0;
            border-radius: 4px;
            font-family: monospace;
            font-size: 12px;
        }
    </style>
</head>
<body>
<div class="dashboard-container">
    <!-- Debug Info -->
    <div class="debug-info">
        <strong>Debug Info:</strong><br>
        User Role: ${sessionScope.userRole}<br>
        User Email: ${sessionScope.userEmail}<br>
        Employees Count: ${not empty employees ? employees.size() : 0}
    </div>

    <div class="dashboard-header">
        <div class="user-info">
            <div class="user-avatar">
                <i class="fas fa-user-md"></i>
            </div>
            <div>
                <h2>Bonjour, ${sessionScope.userEmail}!</h2>
                <p>Rôle: ${sessionScope.userRole}</p>
            </div>
        </div>
        <a href="<portlet:actionURL name='logout'/>" class="logout-btn">
            <i class="fas fa-sign-out-alt"></i> Se déconnecter
        </a>
    </div>

    <div class="dashboard-cards">
        <div class="card">
            <div class="card-header">
                <div class="card-icon">
                    <i class="fas fa-pills"></i>
                </div>
                <h3>Gestion des Médicaments</h3>
            </div>
            <p>Consulter et gérer votre inventaire de médicaments.</p>
        </div>

        <div class="card">
            <div class="card-header">
                <div class="card-icon">
                    <i class="fas fa-prescription"></i>
                </div>
                <h3>Ordonnances</h3>
            </div>
            <p>Gérer les ordonnances et les commandes des clients.</p>
        </div>

        <div class="card">
            <div class="card-header">
                <div class="card-icon">
                    <i class="fas fa-users"></i>
                </div>
                <h3>Clients</h3>
            </div>
            <p>Consulter et gérer les informations des clients.</p>
        </div>

        <div class="card">
            <div class="card-header">
                <div class="card-icon">
                    <i class="fas fa-chart-line"></i>
                </div>
                <h3>Rapports</h3>
            </div>
            <p>Générer des rapports et analyses de ventes.</p>
        </div>
    </div>

    <c:if test="${sessionScope.userRole == 'SUPER_ADMIN' || sessionScope.userRole == 'ADMIN'}">
        <div class="admin-section">
            <h3><i class="fas fa-shield-alt"></i> Panneau d'Administration</h3>

            <!-- Display success/error messages -->
            <c:if test="${not empty successMessage}">
                <div class="message success">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="message error">${errorMessage}</div>
            </c:if>

            <div class="employee-management">
                <h4><i class="fas fa-users-cog"></i> Gestion des Employés</h4>

                <!-- Debug: Show the action URL -->
                <portlet:actionURL name="switchRole" var="switchRoleURL" />
                <div class="debug-info">
                    Action URL: ${switchRoleURL}
                </div>

                <!-- Remove the hidden form since we're using individual forms now -->
                <!-- <form id="roleUpdateForm" method="post" action="${switchRoleURL}">
                    <input type="hidden" id="targetUserEmail" name="targetUserEmail" />
                    <input type="hidden" id="newRole" name="newRole" />
                </form> -->

                <c:if test="${not empty employees}">
                    <table class="employee-table">
                        <thead>
                        <tr>
                            <th>Nom</th>
                            <th>Email</th>
                            <th>Rôle Actuel</th>
                            <th>Nouveau Rôle</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="employee" items="${employees}">
                            <tr>
                                <td>${employee.prenom} ${employee.nom}</td>
                                <td>${employee.email}</td>
                                <td>${employee.role}</td>
                                <td>
                                    <portlet:actionURL name="switchRole" var="individualSwitchRoleURL" />
                                    <form method="post" action="${individualSwitchRoleURL}" style="margin:0;">
                                        <input type="hidden" name="<portlet:namespace/>targetUserEmail" value="${employee.email}" />
                                        <select name="<portlet:namespace/>newRole" class="role-select">
                                            <option value="PHARMACIEN" ${employee.role == 'PHARMACIEN' ? 'selected' : ''}>Pharmacien</option>
                                            <option value="FOURNISSEUR" ${employee.role == 'FOURNISSEUR' ? 'selected' : ''}>Fournisseur</option>
                                            <c:if test="${sessionScope.userRole == 'SUPER_ADMIN'}">
                                                <option value="ADMIN" ${employee.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
                                            </c:if>
                                        </select>
                                        <button type="submit" class="update-btn">Mettre à jour</button>
                                    </form>

                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </c:if>

                <c:if test="${empty employees}">
                    <p>Aucun employé à afficher.</p>
                </c:if>

                <div class="loading" id="loadingIndicator">
                    <i class="fas fa-spinner fa-spin"></i> Mise à jour en cours...
                </div>
            </div>
        </div>
    </c:if>
</div>

</body>
</html>