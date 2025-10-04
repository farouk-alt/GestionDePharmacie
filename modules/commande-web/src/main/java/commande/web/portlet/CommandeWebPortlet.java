package commande.web.portlet;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import gestion_de_pharmacie.model.*;
import gestion_de_pharmacie.service.*;
import org.osgi.service.component.annotations.Component;

import javax.portlet.*;
import java.io.IOException;
import java.util.*;

import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;

import static commande.web.constants.CommandeStatus.*;

@Component(
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=CommandeWeb",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=commande_web",
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class CommandeWebPortlet extends MVCPortlet {

    private static final Log _log = LogFactoryUtil.getLog(CommandeWebPortlet.class);

    @Override
    public void doView(RenderRequest req, RenderResponse res) throws PortletException, IOException {
        try {
            String mode = ParamUtil.getString(req, "mode", "list");
            long commandeId = ParamUtil.getLong(req, "commandeId", 0);

            // Try to read from original request if not in render params
            if ("list".equals(mode) && commandeId == 0) {
                javax.servlet.http.HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);
                javax.servlet.http.HttpServletRequest origReq = PortalUtil.getOriginalServletRequest(httpReq);
                String portletId = PortalUtil.getPortletId(req);
                String ns = PortalUtil.getPortletNamespace(portletId);

                if (origReq.getParameter(ns + "mode") != null) {
                    mode = ParamUtil.getString(origReq, ns + "mode", "list");
                }
                if (origReq.getParameter(ns + "commandeId") != null) {
                    commandeId = ParamUtil.getLong(origReq, ns + "commandeId", 0);
                }
            }

            // Get user role and filter data accordingly
            String userRole = getUserRole(req);
            Utilisateur meFournisseur = getCurrentFournisseur(req);
            boolean isFournisseur = "FOURNISSEUR".equalsIgnoreCase(userRole);

            // Always provide common data
            req.setAttribute("fournisseurs", UtilisateurLocalServiceUtil.getUtilisateurByRole("FOURNISSEUR"));
            req.setAttribute("medicaments", MedicamentLocalServiceUtil.getMedicaments(-1, -1));

            // Filter commandes based on role
            List<Commande> commandes;
            if (isFournisseur && meFournisseur != null) {
                List<Commande> mine = CommandeLocalServiceUtil.getCommandesByUtilisateurId(meFournisseur.getIdUtilisateur());
                List<Commande> pendingOnly = new ArrayList<>();
                for (Commande c : mine) {
                    String st = (c.getStatut() != null) ? c.getStatut().trim().toUpperCase() : "";
                    if (ST_PENDING.equalsIgnoreCase(st)) {
                        pendingOnly.add(c);
                    }
                }
                commandes = pendingOnly;
            } else {
                commandes = CommandeLocalServiceUtil.getCommandes(-1, -1);
            }
            req.setAttribute("commandes", commandes);

            // Load detail/edit data if needed
            if (("detail".equalsIgnoreCase(mode) || "edit".equalsIgnoreCase(mode)) && commandeId > 0) {
                Commande cmd = CommandeLocalServiceUtil.fetchCommande(commandeId);
                req.setAttribute("commande", cmd);

                Utilisateur fournisseur = (cmd != null)
                        ? UtilisateurLocalServiceUtil.fetchUtilisateur(cmd.getIdUtilisateur())
                        : null;
                req.setAttribute("fournisseur", fournisseur);

                List<CommandeDetail> details;
                try {
                    details = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId);
                } catch (Throwable t) {
                    details = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId, -1, -1);
                }
                req.setAttribute("details", details);
            }

            req.setAttribute("mode", mode);
            req.setAttribute("editMode", "edit".equalsIgnoreCase(mode));
            req.setAttribute("isFournisseur", isFournisseur);
            req.setAttribute("currentFournisseurId", (meFournisseur != null) ? meFournisseur.getIdUtilisateur() : 0L);

        } catch (Exception e) {
            _log.error("Error in doView", e);
            req.setAttribute("mode", "list");
            try {
                req.setAttribute("fournisseurs", UtilisateurLocalServiceUtil.getUtilisateurByRole("FOURNISSEUR"));
                req.setAttribute("medicaments", MedicamentLocalServiceUtil.getMedicaments(-1, -1));
                req.setAttribute("commandes", CommandeLocalServiceUtil.getCommandes(-1, -1));
                req.setAttribute("isFournisseur", false);
                req.setAttribute("currentFournisseurId", 0L);
            } catch (Exception ignore) {}
        }
        super.doView(req, res);
    }

    @ProcessAction(name = "createCommande")
    public void createCommande(ActionRequest request, ActionResponse response) {
        String role = getUserRole(request);
        if ("FOURNISSEUR".equalsIgnoreCase(role)) {
            SessionMessages.add(request, "commande-update-error");
            redirectToDashboard(request, response, PortalUtil.getUploadPortletRequest(request));
            return;
        }

        UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);

        try {
            long fournisseurId = ParamUtil.getLong(uploadRequest, "fournisseurId");
            String[] medicamentIds = uploadRequest.getParameterValues("medicamentId");

            if (fournisseurId <= 0 || medicamentIds == null || medicamentIds.length == 0) {
                SessionMessages.add(request, "commande-no-items");
                redirectToDashboard(request, response, uploadRequest);
                return;
            }

            Utilisateur fournisseur = UtilisateurLocalServiceUtil.fetchUtilisateur(fournisseurId);
            if (fournisseur == null) {
                SessionMessages.add(request, "commande-create-error");
                redirectToDashboard(request, response, uploadRequest);
                return;
            }

            long commandeId = CounterLocalServiceUtil.increment(Commande.class.getName());
            Commande commande = CommandeLocalServiceUtil.createCommande(commandeId);
            commande.setIdUtilisateur(fournisseurId);
            commande.setDateCommande(new Date());
            commande.setStatut(ST_CREATED);
            commande.setMontantTotal(0.0);

            double total = 0.0;

            for (String medIdStr : medicamentIds) {
                if (medIdStr == null || medIdStr.trim().isEmpty()) continue;

                long medId = Long.parseLong(medIdStr);
                Medicament medicament = MedicamentLocalServiceUtil.fetchMedicament(medId);
                if (medicament == null) continue;

                int quantity = ParamUtil.getInteger(uploadRequest, "quantite_" + medId, 1);
                if (quantity <= 0) quantity = 1;

                double pu = medicament.getPrixUnitaire();
                total += pu * quantity;

                long detailId = CounterLocalServiceUtil.increment(CommandeDetail.class.getName());
                CommandeDetail detail = CommandeDetailLocalServiceUtil.createCommandeDetail(detailId);
                detail.setIdCommande(commandeId);
                detail.setIdMedicament(medId);
                detail.setQuantite(quantity);
                detail.setPrixUnitaire(pu);
                CommandeDetailLocalServiceUtil.addCommandeDetail(detail);
            }

            commande.setMontantTotal(total);
            CommandeLocalServiceUtil.addCommande(commande);

            SessionMessages.add(request, "commande-created-success");
        } catch (Exception e) {
            _log.error("Error creating commande", e);
            SessionMessages.add(request, "commande-create-error");
        }

        redirectToDashboard(request, response, uploadRequest);
    }

    @ProcessAction(name = "updateCommande")
    public void updateCommande(ActionRequest request, ActionResponse response) {
        String role = getUserRole(request);
        if ("FOURNISSEUR".equalsIgnoreCase(role)) {
            SessionMessages.add(request, "commande-update-error");
            redirectToDashboard(request, response, PortalUtil.getUploadPortletRequest(request));
            return;
        }

        UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);

        try {
            long commandeId = ParamUtil.getLong(uploadRequest, "commandeId");
            long fournisseurId = ParamUtil.getLong(uploadRequest, "fournisseurId");
            String[] medicamentIds = uploadRequest.getParameterValues("medicamentId");

            if (commandeId <= 0 || fournisseurId <= 0 || medicamentIds == null || medicamentIds.length == 0) {
                SessionMessages.add(request, "commande-update-error");
                redirectToDashboard(request, response, uploadRequest);
                return;
            }

            Commande commande = CommandeLocalServiceUtil.fetchCommande(commandeId);
            if (commande == null) {
                SessionMessages.add(request, "commande-update-error");
                redirectToDashboard(request, response, uploadRequest);
                return;
            }

            commande.setIdUtilisateur(fournisseurId);
            commande.setDateCommande(new Date());

            List<CommandeDetail> oldDetails;
            try {
                oldDetails = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId);
            } catch (Throwable t) {
                oldDetails = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId, -1, -1);
            }
            for (CommandeDetail d : oldDetails) {
                CommandeDetailLocalServiceUtil.deleteCommandeDetail(d);
            }

            double total = 0.0;
            for (String medIdStr : medicamentIds) {
                if (medIdStr == null || medIdStr.trim().isEmpty()) continue;

                long medId = Long.parseLong(medIdStr);
                int quantity = ParamUtil.getInteger(uploadRequest, "quantite_" + medId, 1);
                if (quantity <= 0) quantity = 1;

                Medicament medicament = MedicamentLocalServiceUtil.fetchMedicament(medId);
                if (medicament == null) continue;

                double pu = medicament.getPrixUnitaire();
                total += pu * quantity;

                long detailId = CounterLocalServiceUtil.increment(CommandeDetail.class.getName());
                CommandeDetail detail = CommandeDetailLocalServiceUtil.createCommandeDetail(detailId);
                detail.setIdCommande(commandeId);
                detail.setIdMedicament(medId);
                detail.setQuantite(quantity);
                detail.setPrixUnitaire(pu);
                CommandeDetailLocalServiceUtil.addCommandeDetail(detail);
            }

            commande.setMontantTotal(total);
            CommandeLocalServiceUtil.updateCommande(commande);

            long actorId = getCurrentUtilisateurId(request);
            CommandeLocalServiceUtil.getService().notifyCommandeEdited(actorId, commandeId);

            SessionMessages.add(request, "commande-updated-success");

        } catch (Exception e) {
            _log.error("Error updating commande", e);
            SessionMessages.add(request, "commande-update-error");
        }

        redirectToDashboard(request, response, uploadRequest);
    }

    @ProcessAction(name = "deleteCommande")
    public void deleteCommande(ActionRequest request, ActionResponse response) {
        String role = getUserRole(request);
        if ("FOURNISSEUR".equalsIgnoreCase(role)) {
            SessionMessages.add(request, "commande-update-error");
            redirectToDashboard(request, response, PortalUtil.getUploadPortletRequest(request));
            return;
        }

        long commandeId = ParamUtil.getLong(request, "commandeId");
        UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);

        try {
            Commande cmd = CommandeLocalServiceUtil.fetchCommande(commandeId);
            if (cmd == null) {
                SessionMessages.add(request, "commande-delete-error");
                redirectToDashboard(request, response, uploadRequest);
                return;
            }

            String statut = (cmd.getStatut() != null) ? cmd.getStatut().trim().toUpperCase() : "";
            boolean deletable = commande.web.constants.CommandeStatus.isCancelable(statut);

            if (!deletable) {
                SessionMessages.add(request, "commande-delete-not-allowed");
                redirectToDashboard(request, response, uploadRequest);
                return;
            }

            CommandeLocalServiceUtil.getService().deleteCommandeWithDetails(commandeId);
            SessionMessages.add(request, "commande-deleted-success");
        } catch (Exception e) {
            _log.error("Error deleting commande", e);
            SessionMessages.add(request, "commande-delete-error");
        }

        redirectToDashboard(request, response, uploadRequest);
    }

    @ProcessAction(name = "cancelCommande")
    public void cancelCommande(ActionRequest request, ActionResponse response) {
        long actorId = getCurrentUtilisateurId(request);
        long commandeId = ParamUtil.getLong(request, "commandeId");
        try {
            CommandeLocalServiceUtil.getService().cancelCommande(actorId, commandeId);
            SessionMessages.add(request, "commande-updated-success");
        } catch (Exception e) {
            _log.error("Error canceling commande", e);
            SessionMessages.add(request, "commande-update-error");
        }
        redirectToDashboard(request, response, PortalUtil.getUploadPortletRequest(request));
    }

    @ProcessAction(name = "sendCommande")
    public void sendCommande(ActionRequest request, ActionResponse response) {
        long actorId = getCurrentUtilisateurId(request);
        long commandeId = ParamUtil.getLong(PortalUtil.getUploadPortletRequest(request), "commandeId");
        try {
            CommandeLocalServiceUtil.getService().sendCommande(actorId, commandeId);
            SessionMessages.add(request, "commande-updated-success");
        } catch (Exception e) {
            _log.error("Error sending commande", e);
            SessionMessages.add(request, "commande-update-error");
        }
        redirectToDashboard(request, response, PortalUtil.getUploadPortletRequest(request));
    }

    @ProcessAction(name = "acceptCommande")
    public void acceptCommande(ActionRequest request, ActionResponse response) {
        UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(request);
        try {
            Utilisateur me = getCurrentFournisseur(request);
            if (me == null) {
                SessionMessages.add(request, "commande-update-error");
                redirectToDashboard(request, response, upload);
                return;
            }

            long commandeId = ParamUtil.getLong(upload, "commandeId");
            Commande cmd = CommandeLocalServiceUtil.fetchCommande(commandeId);
            if (cmd == null || cmd.getIdUtilisateur() != me.getIdUtilisateur()) {
                SessionMessages.add(request, "commande-update-error");
                redirectToDashboard(request, response, upload);
                return;
            }

            long actorId = getCurrentUtilisateurId(request);
            CommandeLocalServiceUtil.getService().acceptCommande(actorId, commandeId);
            SessionMessages.add(request, "commande-updated-success");
        } catch (Exception e) {
            _log.error("Error accepting commande", e);
            SessionMessages.add(request, "commande-update-error");
        }
        redirectToDashboard(request, response, upload);
    }

    @ProcessAction(name = "rejectCommande")
    public void rejectCommande(ActionRequest request, ActionResponse response) {
        UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(request);
        try {
            Utilisateur me = getCurrentFournisseur(request);
            if (me == null) {
                SessionMessages.add(request, "commande-update-error");
                redirectToDashboard(request, response, upload);
                return;
            }

            long commandeId = ParamUtil.getLong(upload, "commandeId");
            Commande cmd = CommandeLocalServiceUtil.fetchCommande(commandeId);
            if (cmd == null || cmd.getIdUtilisateur() != me.getIdUtilisateur()) {
                SessionMessages.add(request, "commande-update-error");
                redirectToDashboard(request, response, upload);
                return;
            }

            long actorId = getCurrentUtilisateurId(request);
            CommandeLocalServiceUtil.getService().rejectCommande(actorId, commandeId);
            SessionMessages.add(request, "commande-updated-success");
        } catch (Exception e) {
            _log.error("Error rejecting commande", e);
            SessionMessages.add(request, "commande-update-error");
        }
        redirectToDashboard(request, response, upload);
    }

    @ProcessAction(name = "reassignCommande")
    public void reassignCommande(ActionRequest request, ActionResponse response) {
        if ("FOURNISSEUR".equalsIgnoreCase(getUserRole(request))) {
            SessionMessages.add(request, "commande-update-error");
            redirectToDashboard(request, response, PortalUtil.getUploadPortletRequest(request));
            return;
        }
        UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(request);
        try {
            long commandeId = ParamUtil.getLong(upload, "commandeId");
            long newFournisseurId = ParamUtil.getLong(upload, "newFournisseurId");
            boolean sendNow = ParamUtil.getBoolean(upload, "sendNow", false);

            long actorId = getCurrentUtilisateurId(request);
            CommandeLocalServiceUtil.getService().reassignCommande(actorId, commandeId, newFournisseurId, sendNow);
            SessionMessages.add(request, "commande-updated-success");
        } catch (Exception e) {
            _log.error("Error reassigning commande", e);
            SessionMessages.add(request, "commande-update-error");
        }
        redirectToDashboard(request, response, upload);
    }

    @ProcessAction(name = "receiveCommande")
    public void receiveCommande(ActionRequest request, ActionResponse response) {
        long actorId = getCurrentUtilisateurId(request);
        long commandeId = ParamUtil.getLong(PortalUtil.getUploadPortletRequest(request), "commandeId");
        try {
            CommandeLocalServiceUtil.getService().receiveCommande(actorId, commandeId);
            SessionMessages.add(request, "commande-updated-success");
        } catch (Exception e) {
            _log.error("Error receiving commande", e);
            SessionMessages.add(request, "commande-update-error");
        }
        redirectToDashboard(request, response, PortalUtil.getUploadPortletRequest(request));
    }

    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response)
            throws IOException, PortletException {

        String rid = request.getResourceID();
        if (!"downloadCommandePdf".equals(rid)) {
            super.serveResource(request, response);
            return;
        }

        long commandeId = ParamUtil.getLong(request, "commandeId");
        Commande cmd = CommandeLocalServiceUtil.fetchCommande(commandeId);
        if (cmd == null) {
            response.getWriter().write("Commande introuvable");
            return;
        }

        Utilisateur fournisseur = UtilisateurLocalServiceUtil.fetchUtilisateur(cmd.getIdUtilisateur());
        String fournisseurName = (fournisseur != null)
                ? (fournisseur.getNom() + " " + fournisseur.getPrenom())
                : "-";

        java.util.List<CommandeDetail> details;
        try {
            details = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId);
        } catch (Throwable t) {
            details = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId, -1, -1);
        }

        response.setContentType("application/pdf");
        response.addProperty("Content-Disposition",
                "attachment; filename=\"commande_" + commandeId + ".pdf\"");

        com.lowagie.text.Document doc = new com.lowagie.text.Document();
        try {
            com.lowagie.text.pdf.PdfWriter.getInstance(doc, response.getPortletOutputStream());
            doc.open();

            com.lowagie.text.Font h1 = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 16, com.lowagie.text.Font.BOLD);
            com.lowagie.text.Font normal = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 11);

            doc.add(new com.lowagie.text.Paragraph("Bon de commande #" + commandeId, h1));
            doc.add(new com.lowagie.text.Paragraph("Fournisseur : " + fournisseurName, normal));
            doc.add(new com.lowagie.text.Paragraph(
                    "Date : " + (cmd.getDateCommande() != null ? cmd.getDateCommande().toString() : "-"), normal));
            doc.add(new com.lowagie.text.Paragraph("Statut : " + cmd.getStatut(), normal));
            doc.add(new com.lowagie.text.Paragraph(" "));

            com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(5);
            table.setWidths(new int[]{8, 40, 16, 12, 16});
            table.setWidthPercentage(100);

            java.awt.Color headerBg = new java.awt.Color(248, 250, 252);
            com.lowagie.text.Font headerFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 11, com.lowagie.text.Font.BOLD);

            for (String h : new String[]{"N°", "Médicament", "Prix unitaire", "Qté", "Sous-total"}) {
                com.lowagie.text.pdf.PdfPCell hc = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(h, headerFont));
                hc.setBackgroundColor(headerBg);
                hc.setPadding(6f);
                if (!"Médicament".equals(h)) {
                    hc.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                }
                table.addCell(hc);
            }

            java.text.NumberFormat moneyFr = java.text.NumberFormat.getNumberInstance(java.util.Locale.FRANCE);
            moneyFr.setMinimumFractionDigits(2);
            moneyFr.setMaximumFractionDigits(2);

            int i = 1;
            double total = 0.0;

            for (CommandeDetail d : details) {
                Medicament m = MedicamentLocalServiceUtil.fetchMedicament(d.getIdMedicament());
                String medName = (m != null) ? m.getNom() : ("ID=" + d.getIdMedicament());
                double subtotal = d.getPrixUnitaire() * d.getQuantite();
                total += subtotal;

                com.lowagie.text.pdf.PdfPCell c0 = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(String.valueOf(i++)));
                c0.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                table.addCell(c0);

                table.addCell(new com.lowagie.text.Phrase(medName));

                com.lowagie.text.pdf.PdfPCell cPrix = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(moneyFr.format(d.getPrixUnitaire()) + " DH"));
                cPrix.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                table.addCell(cPrix);

                com.lowagie.text.pdf.PdfPCell cQt = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(String.valueOf(d.getQuantite())));
                cQt.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                table.addCell(cQt);

                com.lowagie.text.pdf.PdfPCell cSt = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(moneyFr.format(subtotal) + " DH"));
                cSt.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                table.addCell(cSt);
            }

            com.lowagie.text.Font totalFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 11, com.lowagie.text.Font.BOLD);
            com.lowagie.text.pdf.PdfPCell totalLabel = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase("TOTAL", totalFont));
            totalLabel.setColspan(4);
            totalLabel.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
            totalLabel.setBackgroundColor(headerBg);
            totalLabel.setPadding(6f);

            com.lowagie.text.pdf.PdfPCell totalValue = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(moneyFr.format(total) + " DH", totalFont));
            totalValue.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
            totalValue.setBackgroundColor(headerBg);
            totalValue.setPadding(6f);

            table.addCell(totalLabel);
            table.addCell(totalValue);

            doc.add(table);
            doc.close();
        } catch (Exception e) {
            doc.close();
            throw new PortletException(e);
        }
    }

    private void redirectToDashboard(ActionRequest request, ActionResponse response, UploadPortletRequest uploadRequest) {
        try {
            String redirect = ParamUtil.getString(uploadRequest, "redirect");
            if (com.liferay.portal.kernel.util.Validator.isNotNull(redirect)) {
                response.sendRedirect(redirect);
                return;
            }

            com.liferay.portal.kernel.theme.ThemeDisplay td =
                    (com.liferay.portal.kernel.theme.ThemeDisplay) request.getAttribute(com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY);

            javax.servlet.http.HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);

            com.liferay.portal.kernel.portlet.LiferayPortletURL dash =
                    com.liferay.portal.kernel.portlet.PortletURLFactoryUtil.create(
                            httpReq,
                            "dashboard_web_DashboardWebPortlet_INSTANCE_oddl",
                            td.getPlid(),
                            PortletRequest.RENDER_PHASE
                    );
            dash.setPortletMode(PortletMode.VIEW);
            dash.setWindowState(WindowState.NORMAL);
            dash.setParameter("mvcPath", "/common/dashboard.jsp");
            dash.setParameter("section", "commandes");

            response.sendRedirect(dash.toString());
        } catch (Exception ex) {
            try {
                response.sendRedirect("/dashboard");
            } catch (IOException ignored) {}
        }
    }

    private String getUserRole(PortletRequest req) {
        PortletSession ps = req.getPortletSession();
        String userRole = (String) ps.getAttribute("USER_ROLE", PortletSession.APPLICATION_SCOPE);
        return (userRole != null) ? userRole : "";
    }

    private String getUserEmail(PortletRequest req) {
        PortletSession ps = req.getPortletSession();
        String userEmail = (String) ps.getAttribute("USER_EMAIL", PortletSession.APPLICATION_SCOPE);
        return (userEmail != null) ? userEmail : "";
    }

    private Utilisateur getCurrentFournisseur(PortletRequest req) {
        String role = getUserRole(req);
        if (!"FOURNISSEUR".equalsIgnoreCase(role)) return null;
        String email = getUserEmail(req);
        if (email == null || email.isEmpty()) return null;
        try {
            return UtilisateurLocalServiceUtil.getUtilisateurByEmail(email);
        } catch (Exception ignore) {
            return null;
        }
    }

    private long getCurrentUtilisateurId(PortletRequest req) {
        try {
            String email = getUserEmail(req);
            if (email != null && !email.isEmpty()) {
                Utilisateur u = UtilisateurLocalServiceUtil.getUtilisateurByEmail(email);
                if (u != null) return u.getIdUtilisateur();
            }
        } catch (Exception ignore) {}
        return 0L;
    }
}