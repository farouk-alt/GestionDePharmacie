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

/*
    @Override
    public void doView(RenderRequest req, RenderResponse res) throws PortletException, IOException {
        try {
            System.out.println("---- doView called ----");

            // 1. Get session info (from login)
            PortletSession ps = req.getPortletSession();
            String userEmail = (String) ps.getAttribute("USER_EMAIL", PortletSession.APPLICATION_SCOPE);
            String userRole  = (String) ps.getAttribute("USER_ROLE", PortletSession.APPLICATION_SCOPE);

            // 2. Common data
            List<Utilisateur> fournisseurs = UtilisateurLocalServiceUtil.getUtilisateurByRole("FOURNISSEUR");
            List<Medicament> medicaments = MedicamentLocalServiceUtil.getMedicaments(-1, -1);
            List<Commande> commandes = new ArrayList<>();

            // 3. Filter commandes
            // 3. Filter commandes
            if ("FOURNISSEUR".equalsIgnoreCase(userRole) && userEmail != null) {
                Utilisateur fournisseur = UtilisateurLocalServiceUtil.getUtilisateurByEmail(userEmail);
                if (fournisseur != null) {
                    commandes = CommandeLocalServiceUtil.getCommandesByUtilisateurId(fournisseur.getIdUtilisateur());
                    System.out.println("✅ Fournisseur commandes fetched for: " + userEmail);
                } else {
                    System.out.println("⚠ No fournisseur found for email: " + userEmail);
                }
            } else if ("PHARMACIEN".equalsIgnoreCase(userRole)
                    || "ADMIN".equalsIgnoreCase(userRole)) {             // <-- add this line
                commandes = CommandeLocalServiceUtil.getCommandes(-1, -1);
                System.out.println("✅ Admin-like role sees all commandes: " + userRole);
            } else {
                System.out.println("⛔ Role not authorized (will default to none): " + userRole);
            }


            // 4. Set attributes for JSP
            req.setAttribute("fournisseurs", fournisseurs);
            req.setAttribute("medicaments", medicaments);
            req.setAttribute("commandes", commandes);
            req.setAttribute("userRole", userRole);

        } catch (Exception e) {
            System.out.println("💥 doView failed: " + e.getMessage());
            e.printStackTrace();
        }
        super.doView(req, res);
    }
*/
/*
@Override
public void doView(RenderRequest req, RenderResponse res) throws PortletException, IOException {
    try {
        // 1) Try normal render params
        String mode = com.liferay.portal.kernel.util.ParamUtil.getString(req, "mode", null);
        long commandeId = com.liferay.portal.kernel.util.ParamUtil.getLong(req, "commandeId");

        // 2) If missing, read from ORIGINAL servlet request using the *instance* namespace
        if ((mode == null || mode.isEmpty()) || commandeId == 0) {
            javax.servlet.http.HttpServletRequest httpReq =
                    com.liferay.portal.kernel.util.PortalUtil.getHttpServletRequest(req);
            javax.servlet.http.HttpServletRequest origReq =
                    com.liferay.portal.kernel.util.PortalUtil.getOriginalServletRequest(httpReq);

            // ✅ Use the REAL portletId for this instance (e.g. "commande_web_INSTANCE_CMDS")
            String portletId = com.liferay.portal.kernel.util.PortalUtil.getPortletId(req);
            String ns = com.liferay.portal.kernel.util.PortalUtil.getPortletNamespace(portletId);
            // ns looks like "_commande_web_INSTANCE_CMDS_"

            if (mode == null || mode.isEmpty()) {
                mode = com.liferay.portal.kernel.util.ParamUtil.getString(origReq, ns + "mode", "list");
            }
            if (commandeId == 0) {
                commandeId = com.liferay.portal.kernel.util.ParamUtil.getLong(origReq, ns + "commandeId");
            }

            // Optional: debug what keys actually exist
            // java.util.Map<String,String[]> pm = origReq.getParameterMap();
            // for (String k : pm.keySet()) if (k.contains("mode") || k.contains("commande")) System.out.println("PARAM " + k + " = " + java.util.Arrays.toString(pm.get(k)));
        }

        System.out.println("DEBUG doView (AFTER ns read): mode=" + mode + ", commandeId=" + commandeId);

        // --- keep the rest as you had ---
        req.setAttribute("mode", (mode == null || mode.isEmpty()) ? "list" : mode);
        req.setAttribute("fournisseurs", UtilisateurLocalServiceUtil.getUtilisateurByRole("FOURNISSEUR"));
        req.setAttribute("medicaments", MedicamentLocalServiceUtil.getMedicaments(-1, -1));

        */
/*if ("detail".equalsIgnoreCase(mode) && commandeId > 0) {
            Commande cmd = CommandeLocalServiceUtil.fetchCommande(commandeId);
            req.setAttribute("commande", cmd);

            Utilisateur fournisseur = (cmd != null)
                    ? UtilisateurLocalServiceUtil.fetchUtilisateur(cmd.getIdUtilisateur())
                    : null;
            req.setAttribute("fournisseur", fournisseur);

            java.util.List<CommandeDetail> details;
            try {
                details = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId);
            } catch (Throwable t) {
                details = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId, -1, -1);
            }
            req.setAttribute("details", details);
        } else {
            java.util.List<Commande> commandes = CommandeLocalServiceUtil.getCommandes(-1, -1);
            req.setAttribute("commandes", commandes);
        }*//*

        if (( "detail".equalsIgnoreCase(mode) || "edit".equalsIgnoreCase(mode) ) && commandeId > 0) {
            Commande cmd = CommandeLocalServiceUtil.fetchCommande(commandeId);
            req.setAttribute("commande", cmd);

            Utilisateur fournisseur = (cmd != null)
                    ? UtilisateurLocalServiceUtil.fetchUtilisateur(cmd.getIdUtilisateur())
                    : null;
            req.setAttribute("fournisseur", fournisseur);

            java.util.List<CommandeDetail> details;
            try {
                details = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId);
            } catch (Throwable t) {
                details = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId, -1, -1);
            }
            req.setAttribute("details", details);
        } else {
            java.util.List<Commande> commandes = CommandeLocalServiceUtil.getCommandes(-1, -1);
            req.setAttribute("commandes", commandes);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    super.doView(req, res);
}
*/

    @Override
    public void doView(RenderRequest req, RenderResponse res) throws PortletException, IOException {
        try {
            String mode = ParamUtil.getString(req, "mode", null);
            long commandeId = ParamUtil.getLong(req, "commandeId");

            if ((mode == null || mode.isEmpty()) || commandeId == 0) {
                javax.servlet.http.HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);
                javax.servlet.http.HttpServletRequest origReq = PortalUtil.getOriginalServletRequest(httpReq);

                String portletId = PortalUtil.getPortletId(req);
                String ns = PortalUtil.getPortletNamespace(portletId);

                // Read ONLY if our namespaced params are actually present in the original request
                if ((mode == null || mode.isEmpty())
                        && origReq.getParameter(ns + "mode") != null) {
                    mode = ParamUtil.getString(origReq, ns + "mode", "list");
                }
                if (commandeId == 0
                        && origReq.getParameter(ns + "commandeId") != null) {
                    commandeId = ParamUtil.getLong(origReq, ns + "commandeId");
                }
            }


            System.out.println("DEBUG doView: mode=" + mode + ", commandeId=" + commandeId);

            // Always provide common data
            req.setAttribute("fournisseurs", UtilisateurLocalServiceUtil.getUtilisateurByRole("FOURNISSEUR"));
            req.setAttribute("medicaments", MedicamentLocalServiceUtil.getMedicaments(-1, -1));

            // Always populate the list for the table (even in edit mode)
            List<Commande> commandes = CommandeLocalServiceUtil.getCommandes(-1, -1);
            req.setAttribute("commandes", commandes);

            // For both "detail" and "edit", load header + details
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

            req.setAttribute("mode", (mode == null || mode.isEmpty()) ? "list" : mode);
            req.setAttribute("editMode", "edit".equalsIgnoreCase(mode)); // <-- add this
            req.setAttribute("commandes", CommandeLocalServiceUtil.getCommandes(-1, -1));


        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("mode", "list");
            // at least keep the table populated
            try {
                req.setAttribute("fournisseurs", UtilisateurLocalServiceUtil.getUtilisateurByRole("FOURNISSEUR"));
                req.setAttribute("medicaments", MedicamentLocalServiceUtil.getMedicaments(-1, -1));
                req.setAttribute("commandes", CommandeLocalServiceUtil.getCommandes(-1, -1));
            } catch (Exception ignore) {}
        }
        super.doView(req, res);
    }


    @ProcessAction(name = "createCommande")
    public void createCommande(ActionRequest request, ActionResponse response) {
        try {
            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                   CREATE COMMANDE DEBUG START                ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("📅 Timestamp: " + new Date());
            System.out.println("🌐 Remote Address: " + request.getRemoteUser());
            System.out.println("🔗 Content Type: " + request.getContentType());

            // Wrap request to handle multipart/form-data
            UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);

            // Retrieve fournisseurId
            long fournisseurId = ParamUtil.getLong(uploadRequest, "fournisseurId");
            String fournisseurIdParam = uploadRequest.getParameter("fournisseurId");
            System.out.println("👤 Fournisseur ID: " + fournisseurId + " | Raw param: " + fournisseurIdParam);

            // Retrieve medicament IDs
            String[] medicamentIds = uploadRequest.getParameterValues("medicamentId");
            System.out.println("💊 Medicament IDs: " + (medicamentIds != null ? java.util.Arrays.toString(medicamentIds) : "NONE"));

            if (fournisseurId <= 0 || medicamentIds == null || medicamentIds.length == 0) {
                System.out.println("❌ Missing fournisseurId or medicamentIds. Aborting...");
                SessionMessages.add(request, "commande-no-items");
                response.setRenderParameter("mvcPath", "/view.jsp");
                return;
            }

            // Verify fournisseur exists
            Utilisateur fournisseur = UtilisateurLocalServiceUtil.getUtilisateur(fournisseurId);
            System.out.println("✅ Fournisseur found: " + fournisseur.getNom() + " " + fournisseur.getPrenom());

            // Create Commande
            long commandeId = CounterLocalServiceUtil.increment(Commande.class.getName());
            Commande commande = CommandeLocalServiceUtil.createCommande(commandeId);
            commande.setIdUtilisateur(fournisseurId);
            commande.setDateCommande(new Date());
            commande.setStatut("CREATED");
            commande.setMontantTotal(0.0);

            double total = 0.0;
            List<CommandeDetail> details = new ArrayList<>();

            // Process each medicament
            for (String medIdStr : medicamentIds) {
                if (medIdStr == null || medIdStr.trim().isEmpty()) continue;

                long medId = Long.parseLong(medIdStr);
                System.out.println("💊 Processing medicament ID: " + medId);

                // Get quantity
                int quantity = ParamUtil.getInteger(uploadRequest, "quantite_" + medId, 1);
                if (quantity <= 0) quantity = 1;
                System.out.println("   📦 Quantity: " + quantity);

                // Fetch medicament
                Medicament medicament = MedicamentLocalServiceUtil.fetchMedicament(medId);
                if (medicament == null) {
                    System.out.println("   ❌ Medicament not found: " + medId);
                    continue;
                }

                double prixUnitaire = medicament.getPrixUnitaire();
                double subtotal = prixUnitaire * quantity;
                total += subtotal;

                System.out.println("   💰 Prix Unitaire: " + prixUnitaire);
                System.out.println("   📊 Subtotal: " + subtotal);
                System.out.println("   ✅ Medicament Name: " + medicament.getNom());

                // Create detail
                long detailId = CounterLocalServiceUtil.increment(CommandeDetail.class.getName());
                CommandeDetail detail = CommandeDetailLocalServiceUtil.createCommandeDetail(detailId);
                detail.setIdCommande(commandeId);
                detail.setIdMedicament(medId);
                detail.setQuantite(quantity);
                detail.setPrixUnitaire(prixUnitaire);
                details.add(detail);
            }

            // Finalize commande
            commande.setMontantTotal(total);
            CommandeLocalServiceUtil.addCommande(commande);
            System.out.println("✅ Commande saved: ID=" + commande.getIdCommande() + ", Total=" + total);

            // Save details
            for (CommandeDetail detail : details) {
                CommandeDetailLocalServiceUtil.addCommandeDetail(detail);
                System.out.println("✅ Detail saved for medicament ID: " + detail.getIdMedicament());
            }
// AFTER (align with your service.xml)
            List<Commande> commandes = CommandeLocalServiceUtil.getCommandes(-1, -1);
            List<Medicament> medicaments = MedicamentLocalServiceUtil.getMedicaments(-1, -1);
            List<Utilisateur> fournisseurs = UtilisateurLocalServiceUtil.getUtilisateurByRole("FOURNISSEUR");

            request.setAttribute("commandes", commandes);
            request.setAttribute("medicaments", medicaments);
            request.setAttribute("fournisseurs", fournisseurs);



            System.out.println("📦 TOTAL ITEMS: " + details.size());
            SessionMessages.add(request, "commande-created-success");
            response.setRenderParameter("mvcPath", "/view.jsp");

            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                   CREATE COMMANDE DEBUG END                  ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

        } catch (Exception e) {
            System.out.println("💥 CRITICAL ERROR IN CREATE COMMANDE:");
            e.printStackTrace();
            SessionMessages.add(request, "commande-create-error");
            response.setRenderParameter("mvcPath", "/view.jsp");
        }
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

        // details
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

            com.lowagie.text.Font h1 =
                    new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 16, com.lowagie.text.Font.BOLD);
            com.lowagie.text.Font normal =
                    new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 11);

            doc.add(new com.lowagie.text.Paragraph("Bon de commande #" + commandeId, h1));
            doc.add(new com.lowagie.text.Paragraph("Fournisseur : " + fournisseurName, normal));
            doc.add(new com.lowagie.text.Paragraph(
                    "Date : " + (cmd.getDateCommande() != null ? cmd.getDateCommande().toString() : "-"), normal));
            doc.add(new com.lowagie.text.Paragraph("Statut : " + cmd.getStatut(), normal));
            doc.add(new com.lowagie.text.Paragraph(" "));

            // Table
            com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(5);
            table.setWidths(new int[]{8, 40, 16, 12, 16});
            table.setWidthPercentage(100);

            // Header styling
            java.awt.Color headerBg = new java.awt.Color(248, 250, 252);
            com.lowagie.text.Font headerFont =
                    new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 11, com.lowagie.text.Font.BOLD);

            for (String h : new String[]{"N°", "Médicament", "Prix unitaire", "Qté", "Sous-total"}) {
                com.lowagie.text.pdf.PdfPCell hc =
                        new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(h, headerFont));
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

                // # (right)
                com.lowagie.text.pdf.PdfPCell c0 =
                        new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(String.valueOf(i++)));
                c0.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                table.addCell(c0);

                // Médicament (left)
                table.addCell(new com.lowagie.text.Phrase(medName));

                // Prix (right)
                com.lowagie.text.pdf.PdfPCell cPrix =
                        new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(moneyFr.format(d.getPrixUnitaire()) + " DH"));
                cPrix.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                table.addCell(cPrix);

                // Qté (right)
                com.lowagie.text.pdf.PdfPCell cQt =
                        new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(String.valueOf(d.getQuantite())));
                cQt.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                table.addCell(cQt);

                // Sous-total (right)
                com.lowagie.text.pdf.PdfPCell cSt =
                        new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(moneyFr.format(subtotal) + " DH"));
                cSt.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                table.addCell(cSt);
            }

            // TOTAL row (bold, right, soft background)
            com.lowagie.text.Font totalFont =
                    new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 11, com.lowagie.text.Font.BOLD);

            com.lowagie.text.pdf.PdfPCell totalLabel =
                    new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase("TOTAL", totalFont));
            totalLabel.setColspan(4);
            totalLabel.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
            totalLabel.setBackgroundColor(headerBg);
            totalLabel.setPadding(6f);

            com.lowagie.text.pdf.PdfPCell totalValue =
                    new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(moneyFr.format(total) + " DH", totalFont));
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
    @ProcessAction(name = "deleteCommande")
    public void deleteCommande(ActionRequest request, ActionResponse response) {
        long commandeId = ParamUtil.getLong(request, "commandeId");

        try {
            // 1) Cascade delete (your custom service impl)
            CommandeLocalServiceUtil.getService().deleteCommandeWithDetails(commandeId);
            SessionMessages.add(request, "commande-deleted-success");
        } catch (Exception e) {
            SessionMessages.add(request, "commande-delete-error");
        }

        // 2) Make sure the next render lands on the list view
        response.setRenderParameter("mvcPath", "/view.jsp");
        response.setRenderParameter("mode", "list");

        // 3) (Optional but robust) Preload data so even if doView changes later,
        //    the list still shows up after this action.
        try {
            request.setAttribute("fournisseurs",
                    gestion_de_pharmacie.service.UtilisateurLocalServiceUtil.getUtilisateurByRole("FOURNISSEUR"));
            request.setAttribute("medicaments",
                    gestion_de_pharmacie.service.MedicamentLocalServiceUtil.getMedicaments(-1, -1));

            // same logic you use in doView for list mode
            PortletSession ps = request.getPortletSession();
            String userEmail = (String) ps.getAttribute("USER_EMAIL", PortletSession.APPLICATION_SCOPE);
            String userRole  = (String) ps.getAttribute("USER_ROLE", PortletSession.APPLICATION_SCOPE);

            java.util.List<gestion_de_pharmacie.model.Commande> commandes;
            if ("FOURNISSEUR".equalsIgnoreCase(userRole) && userEmail != null) {
                gestion_de_pharmacie.model.Utilisateur f =
                        gestion_de_pharmacie.service.UtilisateurLocalServiceUtil.getUtilisateurByEmail(userEmail);
                commandes = (f != null)
                        ? gestion_de_pharmacie.service.CommandeLocalServiceUtil.getCommandesByUtilisateurId(f.getIdUtilisateur())
                        : new java.util.ArrayList<>();
            } else if ("PHARMACIEN".equalsIgnoreCase(userRole) || "ADMIN".equalsIgnoreCase(userRole)) {
                commandes = gestion_de_pharmacie.service.CommandeLocalServiceUtil.getCommandes(-1, -1);
            } else {
                commandes = new java.util.ArrayList<>();
            }
            request.setAttribute("commandes", commandes);
        } catch (Exception ignore) {
            // doView will still repopulate
        }
    }


    /*@ProcessAction(name = "updateCommande")
    public void updateCommande(ActionRequest request, ActionResponse response) {
        try {
            UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);

            long commandeId    = ParamUtil.getLong(uploadRequest, "commandeId");
            long fournisseurId = ParamUtil.getLong(uploadRequest, "fournisseurId");
            String[] medicamentIds = uploadRequest.getParameterValues("medicamentId");

            if (commandeId <= 0 || fournisseurId <= 0 || medicamentIds == null || medicamentIds.length == 0) {
                SessionMessages.add(request, "commande-update-error");
                response.setRenderParameter("mvcPath", "/view.jsp");
                response.setRenderParameter("mode", "list");
                return;
            }

            Commande commande = CommandeLocalServiceUtil.fetchCommande(commandeId);
            if (commande == null) {
                SessionMessages.add(request, "commande-update-error");
                response.setRenderParameter("mvcPath", "/view.jsp");
                response.setRenderParameter("mode", "list");
                return;
            }

            // --- header ---
            commande.setIdUtilisateur(fournisseurId);
            commande.setDateCommande(new Date()); // or keep existing date, your call

            // --- remove old details ---
            List<CommandeDetail> oldDetails;
            try {
                oldDetails = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId);
            } catch (Throwable t) {
                oldDetails = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId, -1, -1);
            }
            for (CommandeDetail d : oldDetails) {
                CommandeDetailLocalServiceUtil.deleteCommandeDetail(d);
            }

            // --- recreate details ---
            double total = 0.0;
            for (String medIdStr : medicamentIds) {
                if (medIdStr == null || medIdStr.trim().isEmpty()) continue;

                long medId = Long.parseLong(medIdStr);
                int quantity = ParamUtil.getInteger(uploadRequest, "quantite_" + medId, 1);
                if (quantity <= 0) quantity = 1;

                Medicament medicament = MedicamentLocalServiceUtil.fetchMedicament(medId);
                if (medicament == null) continue;

                double pu = medicament.getPrixUnitaire();
                double subtotal = pu * quantity;
                total += subtotal;

                long detailId = CounterLocalServiceUtil.increment(CommandeDetail.class.getName());
                CommandeDetail detail = CommandeDetailLocalServiceUtil.createCommandeDetail(detailId);
                detail.setIdCommande(commandeId);
                detail.setIdMedicament(medId);
                detail.setQuantite(quantity);
                detail.setPrixUnitaire(pu);
                CommandeDetailLocalServiceUtil.addCommandeDetail(detail);
            }

            // --- save header ---
            commande.setMontantTotal(total);
            CommandeLocalServiceUtil.updateCommande(commande);

            SessionMessages.add(request, "commande-updated-success");

        } catch (Exception e) {
            e.printStackTrace();
            SessionMessages.add(request, "commande-update-error");
        }

        // ✅ Preload data so list view isn’t empty after redirect
        try {
            request.setAttribute("fournisseurs",
                    UtilisateurLocalServiceUtil.getUtilisateurByRole("FOURNISSEUR"));
            request.setAttribute("medicaments",
                    MedicamentLocalServiceUtil.getMedicaments(-1, -1));

            PortletSession ps = request.getPortletSession();
            String userEmail = (String) ps.getAttribute("USER_EMAIL", PortletSession.APPLICATION_SCOPE);
            String userRole  = (String) ps.getAttribute("USER_ROLE", PortletSession.APPLICATION_SCOPE);

            List<Commande> commandes;
            if ("FOURNISSEUR".equalsIgnoreCase(userRole) && userEmail != null) {
                Utilisateur f = UtilisateurLocalServiceUtil.getUtilisateurByEmail(userEmail);
                commandes = (f != null)
                        ? CommandeLocalServiceUtil.getCommandesByUtilisateurId(f.getIdUtilisateur())
                        : new ArrayList<>();
            } else if ("PHARMACIEN".equalsIgnoreCase(userRole) || "ADMIN".equalsIgnoreCase(userRole)) {
                commandes = CommandeLocalServiceUtil.getCommandes(-1, -1);
            } else {
                commandes = new ArrayList<>();
            }
            request.setAttribute("commandes", commandes);
        } catch (Exception ignore) {
            // doView will repopulate anyway
        }

        // back to list
        response.setRenderParameter("mvcPath", "/view.jsp");
        response.setRenderParameter("mode", "list");
    }

*/
    @ProcessAction(name = "updateCommande")
    public void updateCommande(ActionRequest request, ActionResponse response) {
        UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);

        try {
            long commandeId    = ParamUtil.getLong(uploadRequest, "commandeId");
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

            // --- header ---
            commande.setIdUtilisateur(fournisseurId);
            commande.setDateCommande(new Date()); // or keep existing date

            // --- remove old details ---
            List<CommandeDetail> oldDetails;
            try {
                oldDetails = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId);
            } catch (Throwable t) {
                oldDetails = CommandeDetailLocalServiceUtil.findByCommandeId(commandeId, -1, -1);
            }
            for (CommandeDetail d : oldDetails) {
                CommandeDetailLocalServiceUtil.deleteCommandeDetail(d);
            }

            // --- recreate details ---
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

            // --- save header ---
            commande.setMontantTotal(total);
            CommandeLocalServiceUtil.updateCommande(commande);

            SessionMessages.add(request, "commande-updated-success");

        } catch (Exception e) {
            e.printStackTrace();
            SessionMessages.add(request, "commande-update-error");
        }

        // Always go back to Dashboard → Commandes
        redirectToDashboard(request, response, uploadRequest);
    }

    /**
     * Redirects to the Dashboard portlet "commandes" section.
     * If the form provided a "redirect" hidden field, use it; otherwise build the URL.
     */
    private void redirectToDashboard(ActionRequest request, ActionResponse response, UploadPortletRequest uploadRequest) {
        try {
            String redirect = ParamUtil.getString(uploadRequest, "redirect");
            if (com.liferay.portal.kernel.util.Validator.isNotNull(redirect)) {
                response.sendRedirect(redirect);
                return;
            }

            // Fallback: build the dashboard URL programmatically
            com.liferay.portal.kernel.theme.ThemeDisplay td =
                    (com.liferay.portal.kernel.theme.ThemeDisplay) request.getAttribute(com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY);

            javax.servlet.http.HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);

            com.liferay.portal.kernel.portlet.LiferayPortletURL dash =
                    com.liferay.portal.kernel.portlet.PortletURLFactoryUtil.create(
                            httpReq,
                            "dashboard_web_DashboardWebPortlet_INSTANCE_oddl", // <-- instance id
                            td.getPlid(),
                            PortletRequest.RENDER_PHASE
                    );
            dash.setPortletMode(PortletMode.VIEW);
            dash.setWindowState(WindowState.NORMAL);
            dash.setParameter("mvcPath", "/common/dashboard.jsp");
            dash.setParameter("section", "commandes");

            response.sendRedirect(dash.toString());
        } catch (Exception ex) {
            // Last resort: avoid breaking the flow
            try {
                response.sendRedirect("/dashboard");
            } catch (IOException ignored) {}
        }
    }

}
