package gestion_de_pharmacie.service.impl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import gestion_de_pharmacie.model.Utilisateur;
import gestion_de_pharmacie.service.base.UtilisateurLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation custom pour la gestion des Utilisateurs métiers
 * On crée un User Liferay + un Utilisateur (métier) lié par liferayUserId
 */
@Component(
        property = "model.class.name=gestion_de_pharmacie.model.Utilisateur",
        service = AopService.class
)
public class UtilisateurLocalServiceImpl extends UtilisateurLocalServiceBaseImpl {

    public Utilisateur addUtilisateur(
            long creatorUserId,
            long companyId,
            String email,
            String motDePasse,
            String prenom,
            String nom,
            String role,
            ServiceContext serviceContext) throws PortalException {

        // 1. Validation
        if (Validator.isNull(email) || Validator.isNull(motDePasse)
                || Validator.isNull(prenom) || Validator.isNull(nom)) {
            throw new PortalException("Required fields cannot be empty");
        }

        // 2. Créer le User Liferay
        User liferayUser = UserLocalServiceUtil.addUser(
                creatorUserId,
                companyId,
                false,                     // autoPassword
                motDePasse,                // password1
                motDePasse,                // password2
                false,                     // autoScreenName
                email.split("@")[0],       // screenName
                email,                     // emailAddress
                LocaleUtil.getDefault(),   // locale
                prenom,                    // firstName
                "",                        // middleName
                nom,                       // lastName
                0L,                        // prefixListTypeId
                0L,                        // suffixListTypeId
                true,                      // male
                1,                         // birthdayMonth
                1,                         // birthdayDay
                2000,                      // birthdayYear
                "",                        // jobTitle
                UserConstants.TYPE_REGULAR,
                null,                      // groupIds
                null,                      // organizationIds
                null,                      // roleIds
                null,                      // userGroupIds
                false,                     // sendEmail
                serviceContext
        );

        // 3. Assigner un rôle Liferay
        try {
            Role liferayRole = RoleLocalServiceUtil.getRole(companyId, role);
            RoleLocalServiceUtil.addUserRole(liferayUser.getUserId(), liferayRole.getRoleId());
        } catch (Exception e) {
            throw new PortalException("Le rôle '" + role + "' n’existe pas dans Liferay. Créez-le d’abord.", e);
        }

        // 4. Créer votre entité Utilisateur
        long utilisateurId = counterLocalService.increment();
        Utilisateur utilisateur = utilisateurPersistence.create(utilisateurId);

        utilisateur.setEmail(email);
        utilisateur.setPrenom(prenom);
        utilisateur.setNom(nom);
        utilisateur.setMotDePasse(motDePasse); // ⚠️ Hasher si tu veux sécuriser
        utilisateur.setRole(role);
        utilisateur.setDateCreation(new java.util.Date());

        // 5. Sauvegarder
        return utilisateurPersistence.update(utilisateur);
    }

    // In UtilisateurLocalServiceImpl
    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateurPersistence.fetchByEmail(email);
    }

    public List<Utilisateur> getUtilisateurByRole(String role) {
        return utilisateurPersistence.findByRole(role);
    }

}
