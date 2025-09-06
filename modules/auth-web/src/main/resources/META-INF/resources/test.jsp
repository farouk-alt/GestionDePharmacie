<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PharmaCare - Testing Guide</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
            min-height: 100vh;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .guide-container {
            width: 90%;
            max-width: 1000px;
            background: white;
            border-radius: 20px;
            overflow: hidden;
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.3);
        }

        .guide-header {
            background: #2c3e50;
            color: white;
            padding: 30px;
            text-align: center;
        }

        .guide-header h1 {
            margin-bottom: 10px;
        }

        .guide-content {
            padding: 30px;
        }

        .test-section {
            margin-bottom: 30px;
            padding: 20px;
            border-radius: 10px;
            background: #f8f9fa;
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.08);
        }

        .test-section h2 {
            color: #2c3e50;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #4e54c8;
            display: flex;
            align-items: center;
        }

        .test-section h2 i {
            margin-right: 10px;
            color: #4e54c8;
        }

        .test-data {
            background: white;
            border-radius: 8px;
            padding: 15px;
            margin: 15px 0;
        }

        .test-data h3 {
            color: #2c3e50;
            margin-bottom: 10px;
        }

        .data-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .data-item {
            padding: 10px;
            background: #e8f4fd;
            border-radius: 6px;
        }

        .data-item strong {
            color: #2c3e50;
        }

        .test-steps {
            margin-top: 20px;
            position: relative;
        }

        .step {
            display: flex;
            margin-bottom: 15px;
            align-items: flex-start;
        }

        .step-number {
            background: #4e54c8;
            color: white;
            width: 30px;
            height: 30px;
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            margin-right: 15px;
            flex-shrink: 0;
        }

        .step-content {
            flex: 1;
        }

        .expected-result {
            background: #e8f5e9;
            padding: 15px;
            border-radius: 8px;
            margin-top: 15px;
            border-left: 4px solid #4caf50;
        }

        .expected-result h4 {
            color: #2e7d32;
            margin-bottom: 10px;
        }

        .test-actions {
            display: flex;
            gap: 15px;
            margin-top: 20px;
        }

        .btn {
            padding: 12px 20px;
            border: none;
            border-radius: 8px;
            background: #4e54c8;
            color: white;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
        }

        .btn i {
            margin-right: 8px;
        }

        .btn:hover {
            background: #3a3fb8;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .btn-primary {
            background: #4e54c8;
        }

        .btn-success {
            background: #2ecc71;
        }

        .btn-danger {
            background: #e74c3c;
        }

        .nav-back {
            position: absolute;
            top: 20px;
            right: 20px;
            background: #4e54c8;
            color: white;
            padding: 10px 15px;
            border-radius: 5px;
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .nav-back:hover {
            background: #3a3fb8;
        }

        @media (max-width: 768px) {
            .data-grid {
                grid-template-columns: 1fr;
            }

            .nav-back {
                position: relative;
                top: 0;
                right: 0;
                margin-bottom: 20px;
                display: inline-flex;
            }
        }
    </style>
</head>
<body>
<div class="guide-container">
    <div class="guide-header">
        <h1><i class="fas fa-vial"></i> Guide de Test d'Authentification</h1>
        <p>Données de test pour votre système PharmaCare</p>
    </div>

    <div class="guide-content">
        <a href="<portlet:renderURL />" class="nav-back">
            <i class="fas fa-arrow-left"></i> Retour à l'application
        </a>

        <!-- Test 1: Super Admin Login -->
        <div class="test-section">
            <h2><i class="fas fa-user-shield"></i> Test 1: Connexion Super Admin</h2>

            <div class="test-data">
                <h3>Données de test:</h3>
                <div class="data-grid">
                    <div class="data-item">
                        <strong>Email:</strong> admin@pharma.com
                    </div>
                    <div class="data-item">
                        <strong>Mot de passe:</strong> 12345
                    </div>
                    <div class="data-item">
                        <strong>Rôle attendu:</strong> SUPER_ADMIN
                    </div>
                    <div class="data-item">
                        <strong>Redirection:</strong> /web/guest/dashboard
                    </div>
                </div>
            </div>

            <div class="test-steps">
                <div class="step">
                    <div class="step-number">1</div>
                    <div class="step-content">
                        Allez à la page de connexion (<code>pharmacy-login.jsp</code>)
                    </div>
                </div>
                <div class="step">
                    <div class="step-number">2</div>
                    <div class="step-content">
                        Entrez l'email <code>admin@pharma.com</code>
                    </div>
                </div>
                <div class="step">
                    <div class="step-number">3</div>
                    <div class="step-content">
                        Entrez le mot de passe <code>12345</code>
                    </div>
                </div>
                <div class="step">
                    <div class="step-number">4</div>
                    <div class="step-content">
                        Cliquez sur "Se connecter"
                    </div>
                </div>
            </div>

            <div class="expected-result">
                <h4><i class="fas fa-check-circle"></i> Résultat attendu:</h4>
                <p>Connexion réussie avec redirection vers le dashboard. La session doit contenir les attributs: <code>authenticated=true</code>, <code>userRole=SUPER_ADMIN</code>, et <code>userEmail=admin@pharma.com</code>.</p>
            </div>

            <div class="test-actions">
                <a href="<portlet:renderURL />" class="btn btn-primary">
                    <i class="fas fa-sign-in-alt"></i> Tester la connexion Admin
                </a>
            </div>
        </div>

        <!-- Test 2: User Registration -->
        <div class="test-section">
            <h2><i class="fas fa-user-plus"></i> Test 2: Inscription Utilisateur</h2>

            <div class="test-data">
                <h3>Données de test:</h3>
                <div class="data-grid">
                    <div class="data-item">
                        <strong>Nom complet:</strong> Jean Dupont
                    </div>
                    <div class="data-item">
                        <strong>Email:</strong> jean.dupont@example.com
                    </div>
                    <div class="data-item">
                        <strong>Mot de passe:</strong> password123
                    </div>
                    <div class="data-item">
                        <strong>Rôle attendu:</strong> PHARMACIEN
                    </div>
                </div>
            </div>

            <div class="test-steps">
                <div class="step">
                    <div class="step-number">1</div>
                    <div class="step-content">
                        Allez à la page d'inscription via <a href="<portlet:renderURL><portlet:param name="action" value="register"/></portlet:renderURL>">ce lien</a>
                    </div>
                </div>
                <div class="step">
                    <div class="step-number">2</div>
                    <div class="step-content">
                        Entrez le nom complet <code>Jean Dupont</code>
                    </div>
                </div>
                <div class="step">
                    <div class="step-number">3</div>
                    <div class="step-content">
                        Entrez l'email <code>jean.dupont@example.com</code>
                    </div>
                </div>
                <div class="step">
                    <div class="step-number">4</div>
                    <div class="step-content">
                        Entrez le mot de passe <code>password123</code>
                    </div>
                </div>
                <div class="step">
                    <div class="step-number">5</div>
                    <div class="step-content">
                        Confirmez le mot de passe <code>password123</code>
                    </div>
                </div>
                <div class="step">
                    <div class="step-number">6</div>
                    <div class="step-content">
                        Cliquez sur "S'inscrire"
                    </div>
                </div>
            </div>

            <div class="expected-result">
                <h4><i class="fas fa-check-circle"></i> Résultat attendu:</h4>
                <p>Inscription réussie avec création d'un nouvel utilisateur en base de données. Le mot de passe doit être haché (SHA-256). Redirection vers la page de connexion avec message de succès.</p>
            </div>

            <div class="test-actions">
                <a href="<portlet:renderURL><portlet:param name="action" value="register"/></portlet:renderURL>" class="btn btn-success">
                    <i class="fas fa-user-plus"></i> Tester l'inscription
                </a>
            </div>
        </div>

        <!-- Additional test sections would follow the same pattern -->

    </div>
</div>

<script>
    // Simple simulation for testing buttons
    document.querySelectorAll('.btn').forEach(btn => {
        btn.addEventListener('click', function(e) {
            // Allow default navigation for buttons with actual links
            if (this.getAttribute('href') === '#') {
                e.preventDefault();
                const testName = this.closest('.test-section').querySelector('h2').textContent;
                alert(`Simulation du test: ${testName}\n\nOuvrez votre application et effectuez manuellement les étapes décrites.`);
            }
        });
    });
</script>
</body>
</html>