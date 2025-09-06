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

        .admin-features {
            display: flex;
            gap: 15px;
            margin-top: 15px;
        }

        .admin-feature {
            background: white;
            padding: 15px;
            border-radius: 8px;
            flex: 1;
            text-align: center;
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
<div class="dashboard-container">
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

    <c:if test="${sessionScope.userRole == 'SUPER_ADMIN'}">
        <div class="admin-section">
            <h3><i class="fas fa-shield-alt"></i> Panneau d'Administration</h3>
            <p>En tant qu'administrateur, vous avez accès aux fonctionnalités avancées.</p>

            <div class="admin-features">
                <div class="admin-feature">
                    <h4><i class="fas fa-user-cog"></i> Gestion des Utilisateurs</h4>
                    <p>Créer, modifier et supprimer des comptes utilisateur.</p>
                </div>

                <div class="admin-feature">
                    <h4><i class="fas fa-tasks"></i> Gestion des Rôles</h4>
                    <p>Attribuer et modifier les rôles des utilisateurs.</p>
                </div>

                <div class="admin-feature">
                    <h4><i class="fas fa-cog"></i> Paramètres Système</h4>
                    <p>Configurer les paramètres de l'application.</p>
                </div>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>