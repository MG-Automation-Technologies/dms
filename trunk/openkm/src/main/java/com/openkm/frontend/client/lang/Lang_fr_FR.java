/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
 *upload
 *  No bytes were intentionally harmed during the development of this application.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.frontend.client.lang;

import java.util.HashMap;

import com.openkm.frontend.client.config.ErrorCode;

/**
 * French (français)
 * 
 * @author placouture, Bidouille
 */
public class Lang_fr_FR {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();

	    // General configuration
	    lang.put("general.date.pattern", "dd.MM.yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd.MM.yyyy");
	    lang.put("general.hour.pattern", "hh:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Chargement d'OpenKM");
	    lang.put("startup.starting.loading", "Démarrage du chargement de OpenKM");
	    lang.put("startup.taxonomy", "Récupération du nœud racine taxinomie");
	    lang.put("startup.template", "Récupération du nœud racine modèle");
	    lang.put("startup.categories", "Récupération du nœud racine catégories");
	    lang.put("startup.thesaurus", "Récupération du noeud racine thésaurus");
	    lang.put("startup.personal", "Récupération du nœud racine personnel");
	    lang.put("startup.mail", "Récupération du nœud racine courriel");
	    lang.put("startup.trash", "Récupération du nœud racine corbeille");
	    lang.put("startup.user.home", "Récupération du nœud accueil utilisateur");
	    lang.put("startup.bookmarks", "Récupération des signets");
	    lang.put("startup.loading.taxonomy", "Chargement taxinomie");
	    lang.put("startup.loading.taxonomy.getting.folders", "Chargement taxinomie - récupération des dossiers");
	    lang.put("startup.loading.taxonomy.eval.params", "Chargement taxinomie - paramètres du navigateur");
	    lang.put("startup.loading.taxonomy.open.path", "Chargement taxinomie - ouverture du chemin");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Chargement de la taxinomie - récupération des dossiers");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Chargement de la taxinomie - récupération des documents");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Chargement de la taxinomie - récupération des courriels");
	    lang.put("startup.loading.personal", "Chargement personnel");
	    lang.put("startup.loading.mail", "Chargement des courriels");
	    lang.put("startup.loading.categories", "Chargement categories");
	    lang.put("startup.loading.thesaurus", "Chargement thésaurus");
	    lang.put("startup.loading.templates", "Chargement des modèles");
	    lang.put("startup.loading.trash", "Chargement de la corbeille");
	    lang.put("startup.loading.history.search", "Chargement historique de recherche");
	    lang.put("startup.loading.user.values", "Chargement des valeurs utilisateur");
	    lang.put("startup.keep.alive", "Chargement surveillance");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Mise à jour disponible pour OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Taxinomie");
	    lang.put("leftpanel.label.trash", "Corbeille");
	    lang.put("leftpanel.label.mail", "Courriel");
	    lang.put("leftpanel.label.stored.search", "Recherches sauvegardées");
	    lang.put("leftpanel.label.categories", "Catégories");
	    lang.put("leftpanel.label.thesaurus", "Thésaurus");
	    lang.put("leftpanel.label.templates", "Modèles");
	    lang.put("leftpanel.label.my.documents", "Mes documents");
	    lang.put("leftpanel.label.user.search", "Brèves utilisateur");
	    lang.put("leftpanel.label.all.repository", "Tous les dépôts");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Créer un dossier");
	    lang.put("tree.menu.directory.remove", "Supprimer");
	    lang.put("tree.menu.directory.rename", "Renommer");
	    lang.put("tree.menu.directory.refresh", "Actualiser");
	    lang.put("tree.menu.directory.move", "Déplacer");
	    lang.put("tree.menu.directory.copy", "Copier");
	    lang.put("tree.menu.directory.add.document", "Ajouter un document");
	    lang.put("tree.menu.add.bookmark", "Ajouter un signet");
	    lang.put("tree.menu.set.home", "Mettre en accueil par défaut");
	    lang.put("tree.menu.export", "Export du fichier");
	    lang.put("tree.status.refresh.folder", "Actualiser le dossier");
	    lang.put("tree.status.refresh.create", "Créer un dossier");
	    lang.put("tree.status.refresh.delete", "Supprimer le dossier");
	    lang.put("tree.status.refresh.rename", "Renommer le dossier");
	    lang.put("tree.status.refresh.restore", "Restaurer le dossier");
	    lang.put("tree.status.refresh.purge", "Vider le dossier");
	    lang.put("tree.status.refresh.get", "Actualiser le dossier");
	    lang.put("tree.folder.new", "Nouveau dossier");
	    lang.put("tree.status.refresh.add.subscription", "Ajouter un abonnement");
	    lang.put("tree.status.refresh.remove.subscription", "Supprimer un abonnement");
	    lang.put("tree.status.refresh.get.root", "Actualiser le dossier racine");
	    lang.put("tree.status.refresh.get.keywords", "Actualiser les mots clés");
	    lang.put("tree.status.refresh.get.user.home", "Actualiser l'accueil utilisateur");
	    lang.put("tree.status.refresh.purge.trash", "Vider la corbeille");
	    lang.put("tree.menu.directory.find.folder","Rechercher Dossier");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Restaurer");
	    lang.put("trash.menu.directory.purge", "Vider");
	    lang.put("trash.menu.directory.purge.trash", "Vider la corbeille");
	    lang.put("trash.directory.select.label", "Choisir le dossier destination");
	    
	    // General menu
	    lang.put("general.menu.file", "Fichier");
	    	lang.put("general.menu.file.exit", "Quitter");
	    	lang.put("general.menu.file.purge.trash", "Vider la corbeille");
	    lang.put("general.menu.edit", "Edition");
			lang.put("general.menu.file.create.directory", "Créer un dossier");
			lang.put("general.menu.file.download.document", "Télécharger le document");
			lang.put("general.menu.file.download.document.pdf", "Télécharger le document en PDF");
			lang.put("general.menu.file.send.link", "Envoi du document en tant que lien");
			lang.put("general.menu.file.send.attachment", "Envoi pièce jointe du document");
			lang.put("general.menu.file.lock", "Verrouiller");
			lang.put("general.menu.file.unlock", "Déverrouiller");
			lang.put("general.menu.file.add.document", "Ajouter un document");
			lang.put("general.menu.file.checkout", "Réserver");
			lang.put("general.menu.file.checkin", "Restituer");
			lang.put("general.menu.file.cancel.checkout", "Annuler la réservation");
			lang.put("general.menu.file.delete", "Supprimer");
			lang.put("general.menu.file.refresh", "Actualiser");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "Chargeur Fichier");
	    lang.put("general.menu.tools", "Outils");
	    	lang.put("general.menu.tools.languages", "Langues");
	    	lang.put("general.menu.tools.skin", "Thème");
    			lang.put("general.menu.tools.skin.default", "Normal");
    			lang.put("general.menu.tools.skin.default2", "Normal 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Police moyenne");
    			lang.put("general.menu.tools.skin.bigfont", "Grande police");
    		lang.put("general.menu.debug.console", "Console d'erreur");
    		lang.put("general.menu.administration", "Montrer l'administration");
    		lang.put("general.menu.tools.preferences", "Préférences");
    			lang.put("general.menu.tools.user.preferences", "Configuration personnelle");
    	lang.put("general.menu.bookmark", "Signets");
	    	lang.put("general.menu.bookmark.home", "Accueil");
	    	lang.put("general.menu.bookmark.default.home", "Mettre en accueil par défaut");
	    	lang.put("general.menu.bookmark.edit", "Éditer les signets");
	    lang.put("general.menu.help", "Aide");
		    lang.put("general.menu.bug.report", "Signaler un problème");
		    lang.put("general.menu.support.request", "Demande de support");
		    lang.put("general.menu.public.forum", "Forums publics");
		    lang.put("general.menu.project.web", "Site du projet");
		    lang.put("general.menu.version.changes", "Notes de version");
		    lang.put("general.menu.documentation", "Documentation");
		    lang.put("general.menu.about", "À propos d'OpenKM");
	    lang.put("general.connected", "Utilisateur : ");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Nom");
	    lang.put("filebrowser.date.update", "Date de modification");
	    lang.put("filebrowser.size", "Taille");
	    lang.put("filebrowser.path", "Chemin");
	    lang.put("filebrowser.author", "Auteur");
	    lang.put("filebrowser.version", "Version");
	    lang.put("filebrowser.menu.checkout", "Réserver");
	    lang.put("filebrowser.menu.checkin", "Restituer");
	    lang.put("filebrowser.menu.delete", "Supprimer");
	    lang.put("filebrowser.menu.rename", "Renommer");
	    lang.put("filebrowser.menu.checkout.cancel", "Annuler la réservation");
	    lang.put("filebrowser.menu.lock", "Verrouiller");
	    lang.put("filebrowser.menu.unlock", "Déverrouiller");
	    lang.put("filebrowser.menu.download", "Télécharger");
	    lang.put("filebrowser.menu.move", "Déplacer");
	    lang.put("filebrowser.menu.copy", "Copier");
	    lang.put("filebrowser.menu.create.from.template", "Créer depuis le modèle");
	    lang.put("filebrowser.menu.add.property.group", "Ajouter une propriété de groupe");
	    lang.put("filebrowser.menu.remove.property.group", "Enlever une propriété de groupe");
	    lang.put("filebrowser.menu.start.workflow", "Démarrer le workflow");
	    lang.put("filebrowser.menu.add.subscription", "Ajouter un abonnement");
	    lang.put("filebrowser.menu.remove.subscription", "Éliminer un abonnement");
	    lang.put("filebrowser.menu.add.bookmark", "Ajouter un signet");
	    lang.put("filebrowser.menu.set.home", "Mettre en accueil par défaut");
	    lang.put("filebrowser.menu.refresh", "Actualiser");
	    lang.put("filebrowser.menu.export", "Export en ZIP");
	    lang.put("filebrowser.menu.play", "Jouer");
	    lang.put("filebrowser.menu.image.viewer", "Aperçu de l'image");
	    lang.put("filebrowser.status.refresh.folder", "Actualiser la liste des dossiers");
	    lang.put("filebrowser.status.refresh.document", "Actualiser la liste des documents");
	    lang.put("filebrowser.status.refresh.mail", "Actualiser la liste des courriels");
	    lang.put("filebrowser.status.refresh.delete.folder", "Suppression du dossier en cours");
	    lang.put("filebrowser.status.refresh.delete.document", "Suppression du document en cours");
	    lang.put("filebrowser.status.refresh.checkout", "Réservation du document en cours");
	    lang.put("filebrowser.status.refresh.lock", "Verrouillage du document en cours");
	    lang.put("filebrowser.status.refresh.unlock", "Déverrouillage du document en cours");
	    lang.put("filebrowser.status.refresh.document.rename", "Renommage du document");
	    lang.put("filebrowser.status.refresh.folder.rename", "Renommage du dossier");
	    lang.put("filebrowser.status.refresh.document.purge", "Suppression du document");
	    lang.put("filebrowser.status.refresh.folder.purge", "Suppression du dossier");
	    lang.put("filebrowser.status.refresh.folder.get", "Actualiser le dossier");
	    lang.put("filebrowser.status.refresh.document.get", "Actualiser le document");
	    lang.put("filebrowser.status.refresh.add.subscription", "Ajouter un abonnement");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Supprimer un abonnement");
	    lang.put("filebrowser.status.refresh.get.user.home", "Obtenir le dossier utilisateur");
	    lang.put("filebrowser.status.refresh.delete.mail", "Supprimer le courriel");
	    lang.put("filebrowser.status.refresh.mail.purge", "Purger le courriel");
	    
	    // File Upload
	    lang.put("fileupload.send", "Envoi");
	    lang.put("fileupload.status.sending", "Téléchargement du fichier en cours...");
	    lang.put("fileupload.status.indexing", "Indexation en cours...");
	    lang.put("fileupload.status.ok", "Téléchargement du fichier effectué");
	    lang.put("fileupload.upload.status", "Statut du téléchargement");
	    lang.put("fileupload.upload.uploaded", "Téléchargé");
	    lang.put("fileupload.upload.completed", "Téléchargement effectué");
	    lang.put("fileupload.upload.runtime", "Temps");
	    lang.put("fileupload.upload.remaining", "Restant");
	    lang.put("fileupload.button.close", "Fermer");
	    lang.put("fileupload.button.add.other.file", "Ajouter un autre document");
	    lang.put("fileupload.status.move.file", "Déplacement du fichier...");
	    lang.put("fileupload.status.move.mail", "Déplacement du courriel...");
	    lang.put("fileupload.status.copy.file", "Copie du fichier...");
	    lang.put("fileupload.status.copy.mail", "Copie du courriel...");
	    lang.put("fileupload.status.restore.file", "Restauration du fichier...");
	    lang.put("fileupload.status.restore.mail", "Restauration du courriel...");
	    lang.put("fileupload.status.move.folder", "Déplacement du dossier...");
	    lang.put("fileupload.status.copy.folder", "Copie du dossier...");
	    lang.put("fileupload.status.restore.folder", "Restauration du dossier...");
	    lang.put("fileupload.status.create.from.template", "Création du fichier depuis le modèle...");
	    lang.put("fileupload.status.of", "de");
	    lang.put("fileupload.label.insert", "Ajout de documents");
	    lang.put("fileupload.label.update", "Mise à jour des documents");
	    lang.put("fileupload.label.users.notify", "Notifier à des utilisateurs");
	    lang.put("fileupload.label.comment", "Commentaire");
	    lang.put("fileupload.label.users.to.notify",  "Utilisateurs notifiés");
	    lang.put("fileupload.label.users",  "Utilisateurs");
	    lang.put("fileupload.label.groups.to.notify","Groupes à notifier");
	    lang.put("fileupload.label.groups","Groupes");
	    lang.put("fileupload.label.must.select.users",  "Il faut choisir un utilisateur à notifier");
	    lang.put("fileupload.label.importZip", "Import de documents en ZIP");
	    lang.put("fileupload.label.notify.comment", "Message de notification");
	    lang.put("fileupload.label.error.importing.zip", "Erreur d'importation du fichier");
	    lang.put("fileupload.label.error.move.file", "Erreur de déplacement du fichier");
	    lang.put("fileupload.label.error.move.mail", "Erreur de déplacement du courriel");
	    lang.put("fileupload.label.error.copy.file", "Erreur de copie du fichier");
	    lang.put("fileupload.label.error.copy.mail", "Erreur de copie du courriel");
	    lang.put("fileupload.label.error.restore.file", "Erreur de restauration du fichier");
	    lang.put("fileupload.label.error.restore.mail", "Erreur de restauration du courriel");
	    lang.put("fileupload.label.error.move.folder", "Erreur de déplacement du dossier");
	    lang.put("fileupload.label.error.copy.folder", "Erreur de copie du dossier");
	    lang.put("fileupload.label.error.restore.folder", "Erreur de restauration du dossier");
	    lang.put("fileupload.label.error.create.from.template", "Erreur de création du fichier depuis le modèle");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Déplacement depuis l'origine ou dossier enfant : refusé");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Déplacement dans le dossier d'origine : refusé");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Création du fichier dans le dossier d'origine : refusé");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Propriétés");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "Historique");
	    lang.put("tab.document.status.history", "Actualiser l'historique");
	    lang.put("tab.status.security.role", "Mise à jour sécurité rôles en cours");
	    lang.put("tab.status.security.user", "Mise à jour sécurité utilisateurs en cours");
	    lang.put("tab.document.status.group.properties", "Mise à jour propriétés de groupe");
	    lang.put("tab.document.status.set.keywords", "Réglage des mots-clés");
	    lang.put("tab.document.status.set.categories", "Mise à jour des catégories");
	    lang.put("tab.document.status.get.version.history.size", "Régénérer la taille de l'historique du document");
	    lang.put("tab.document.status.purge.version.history", "Compacter l'historique du document");
	    lang.put("tab.document.status.restore.version", "Restaurer la version du document");
	    lang.put("tab.document.security", "Sécurité");
	    lang.put("tab.document.preview", "Prévisualisation");
	    lang.put("tab.folder.properties", "Propriétés");
	    lang.put("tab.folder.security", "Sécurité");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Bureau");
	    lang.put("tab.workspace.search", "Recherche");
	    lang.put("tab.workspace.dashboard", "Tableau de bord");
	    lang.put("tab.workspace.administration", "Administration");
	    
	    // Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Nom");
	    lang.put("document.folder", "Dossier");
	    lang.put("document.size", "Taille");
	    lang.put("document.created", "Créé");
	    lang.put("document.lastmodified", "Modifié");
	    lang.put("document.mimetype", "Type");
	    lang.put("document.keywords", "Mots-clés");
	    lang.put("document.by", "par");
	    lang.put("document.status", "Statut");
	    lang.put("document.status.checkout", "Réservé par");
	    lang.put("document.status.locked", "Verrouillé par");
	    lang.put("document.status.normal", "Disponible");
	    lang.put("document.subscribed", "Abonné");
	    lang.put("document.subscribed.yes", "Oui");
	    lang.put("document.subscribed.no", "Non");
	    lang.put("document.history.size", "Taille historique");
	    lang.put("document.subscribed.users", "Abonnés");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Ajouter une note");
	    lang.put("document.keywords.cloud", "Mots clés");
	    lang.put("document.categories", "Catégories");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Nom");
	    lang.put("folder.parent", "Parent");
	    lang.put("folder.created", "Créé");
	    lang.put("folder.by", "par");
	    lang.put("folder.subscribed", "Abonné");
	    lang.put("folder.subscribed.yes", "Oui");
	    lang.put("folder.subscribed.no", "Non");
	    lang.put("folder.subscribed.users", "Abonnés");
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Dossiers");
	    lang.put("folder.number.documents", "Documents");
	    lang.put("folder.number.mails", "Mails");
	    
	    // Version
	    lang.put("version.name", "Version");
	    lang.put("version.created", "Date");
	    lang.put("version.author", "Auteur");
	    lang.put("version.size", "Taille");
	    lang.put("version.purge.document", "Purger l'historique");
	    lang.put("version.comment", "Commentaire");
	    
	    // Security
	    lang.put("security.label", "Securité");
	    lang.put("security.group.name", "Groupe");
	    lang.put("security.group.permission.read", "Lecture");
	    lang.put("security.group.permission.write", "Ecriture");
	    lang.put("security.group.permission.delete", "Suppression");
	    lang.put("security.group.permission.security", "Sécurité");
	    lang.put("security.user.name", "Utilisateur");
	    lang.put("security.user.permission.read", "Lecture");
	    lang.put("security.user.permission.write", "Ecriture");
	    lang.put("security.user.permission.delete", "Suppression");
	    lang.put("security.user.permission.security", "Sécurité");
	    lang.put("security.users", "Utilisateurs");
	    lang.put("security.groups", "Groupes");
	    lang.put("security.recursive", "Appliquer les changements de façon recursive");
	    lang.put("secutiry.filter.by.users","Filtre Utilisateurs");
	    lang.put("secutiry.filter.by.groups","Filtre Groupes");
	    lang.put("security.status.updating","Mise à jour Sécurité");
	    
	    // Preview
	    lang.put("preview.unavailable", "Prévisualisation non disponible");

	    // Mail
	    lang.put("mail.from", "De");
	    lang.put("mail.reply", "Réponse à");
	    lang.put("mail.to", "A");
	    lang.put("mail.subject", "Sujet");
	    lang.put("mail.attachment", "Pièces jointes");
	    
	    // Error
	    lang.put("error.label", "OpenKM a généré une erreur");
	    lang.put("error.invocation", "Erreur de communication avec le serveur");
	    
	    // About
	    lang.put("about.label", "A propos de OpenKM");
	    lang.put("about.loading", "Chargement...");
	    
	    // Logout
	    lang.put("logout.label", "Quitter");
	    lang.put("logout.closed", "OpenKM a été fermé correctement.");
	    lang.put("logout.logout", "OpenKM se déconnecte, patientez...");
	    
	    // Confirm
	    lang.put("confirm.label", "Confirmation");
	    lang.put("confirm.delete.folder", "Voulez-vous vraiment supprimer ce dossier ?");
	    lang.put("confirm.delete.document", "Voulez-vous vraiment supprimer ce document ?");
	    lang.put("confirm.delete.trash", "Voulez-vous vraiment vider la corbeille ?");
	    lang.put("confirm.purge.folder", "Voulez-vous vraiment purger ce dossier ?");
	    lang.put("confirm.purge.document", "Voulez-vous vraiment purger ce document ?");
	    lang.put("confirm.delete.propety.group", "Voulez-vous vraiment supprimer les propriétés de ce groupe ?");
	    lang.put("confirm.purge.version.history.document", "Voulez-vous vraiment supprimer l'historique ?");
	    lang.put("confirm.purge.restore.document", "Voulez-vous vraiment restaurer la version du document ?");
	    lang.put("confirm.set.default.home", "Voulez-vous vraiment mettre cet accueil par défaut ?");
	    lang.put("confirm.delete.saved.search", "Voulez-vous vraiment supprimer les recherches sauvegardées ?");
	    lang.put("confirm.delete.user.news", "Voulez-vous vraiment supprimer les brèves ?");
	    lang.put("confirm.delete.mail", "Voulez-vous vraiment supprimer ce courriel ?");
	    lang.put("confirm.get.pooled.workflow.task", "Voulez-vous assigner cette tâche à vous-même ?");
	    lang.put("confirm.force.unlock", "Voulez-vous vraiment forcer le dévérouillage du document ?");
	    lang.put("confirm.force.cancel.checkout", "Voulez-vous vraiment forcer l'annulation de réservation du document ?");
	    
	    // Search
	    lang.put("search.context", "Contexte");
	    lang.put("search.content", "Contenu");
	    lang.put("search.name", "Nom");
	    lang.put("search.keywords", "Mots-clés");
	    lang.put("search.folder", "Dossier");
	    lang.put("search.category", "Catégorie");
	    lang.put("search.results", "Résultats");
	    lang.put("search.to", "à");
	    lang.put("search.page.results", "Résultats par page");
	    lang.put("search.add.property.group", "Ajout propriété de groupe");
	    lang.put("search.mimetype", "Type Mime");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Dossier");
	    lang.put("search.type.mail", "Courriel");
	    lang.put("search.advanced", "Recherche avancée");
	    lang.put("search.user", "Utilisateur");
	    lang.put("search.date.and", "et");
	    lang.put("search.date.range", "Plage de date entre");
	    lang.put("search.save.as.news", "Sauvegarder en tant que brève");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filtre par dossier");
	    lang.put("search.category.filter", "Filtre par catégorie");
	    
	    // Search results
	    lang.put("search.result.name", "Nom");
	    lang.put("search.result.score", "Pertinence");
	    lang.put("search.result.size", "Taille");
	    lang.put("search.result.date.update", "Date de modification");
	    lang.put("search.result.author", "Auteur");
	    lang.put("search.result.version", "Version");
	    lang.put("search.result.path", "Chemin");
	    lang.put("search.result.menu.download", "Téléchargement");
	    lang.put("search.result.menu.go.folder", "Aller au dossier");
	    lang.put("search.result.menu.go.document", "Aller au document");
	    lang.put("search.result.status.findPaginated", "Actualiser la recherche");
	    lang.put("search.result.status.runsearch", "Actualiser la recherche sauvegardée");
	    
	    // Search saved
	    lang.put("search.saved.run", "Démarrer");
	    lang.put("search.saved.delete", "Supprimer");
	    lang.put("search.saved.status.getsearchs", "Actualiser les recherches sauvegardées");
	    lang.put("search.saved.status.savesearch", "Actualiser la recherche sauvegardée");
	    lang.put("search.saved.status.deletesearch", "Actualiser les recherches effacées");
	    lang.put("search.saved.status.getusernews", "Actualiser les brèves");
	    
	    // Button
	    lang.put("button.close", "Fermer");
	    lang.put("button.logout", "Déconnecter");
	    lang.put("button.update", "Mettre à jour");
	    lang.put("button.cancel", "Annuler");
	    lang.put("button.accept", "Accepter");
	    lang.put("button.restore", "Restaurer");
	    lang.put("button.move", "Déplacer");
	    lang.put("button.change", "Modifier");
	    lang.put("button.search", "Recherche");
	    lang.put("button.save.search", "Sauvegarder recherche");
	    lang.put("button.view", "Visualiser");
	    lang.put("button.clean", "Nettoyer");
	    lang.put("button.add", "Ajouter");
	    lang.put("button.delete", "Supprimer");
	    lang.put("button.copy", "Copier");
	    lang.put("button.create", "Créer");
	    lang.put("button.show", "Afficher");
	    lang.put("button.memory", "Mettre à jour");
	    lang.put("button.copy.clipboard", "Copie presse-papier");	
	    lang.put("button.start", "Démarrer");
	    lang.put("button.select", "Sélectionner");
	    lang.put("button.test", "Test");
	    lang.put("button.next", "Suivant");
	    
	    // Group
	    lang.put("group.label", "Ajout propriété de groupe");
	    lang.put("group.group", "Groupe");
	    lang.put("group.property.group", "Propriété");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Ajout de signet");
	    lang.put("bookmark.name", "Nom");
	    lang.put("bookmark.edit.label", "Édition des signets");
	    lang.put("bookmark.path", "Chemin");
	    lang.put("bookmark.type", "Type");
	    lang.put("bookmark.type.document", "Document");
	    lang.put("bookmark.type.folder", "Dossier");
	    
	    // Notify
	    lang.put("notify.label", "Envoi du lien vers le document");
	    lang.put("notify.label.attachment", "Envoi pièce jointe du document");
	    
	    // Status
	    lang.put("status.document.copied", "Copier le document marqué");
	    lang.put("status.document.cut", "Couper le document marqué");
	    lang.put("status.folder.copied", "Copier le dossier marqué");
	    lang.put("status.folder.cut", "Couper le dossier marqué");
	    lang.put("status.keep.alive.error", "Connexion au serveur perdue (surveillance)");
	    lang.put("status.debug.enabled", "Activation du déboggage");
	    lang.put("status.debug.disabled", "Désactivation du déboggage");
	    lang.put("status.network.error.detected", "Erreur réseau detectée");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Dimanche");
	    lang.put("calendar.day.monday", "Lundi");
	    lang.put("calendar.day.tuesday", "Mardi");
	    lang.put("calendar.day.wednesday", "Mercredi");
	    lang.put("calendar.day.thursday", "Jeudi");
	    lang.put("calendar.day.friday", "Vendredi");
	    lang.put("calendar.day.saturday", "Samedi");
	    lang.put("calendar.month.january", "Janvier");
	    lang.put("calendar.month.february", "Février");
	    lang.put("calendar.month.march", "Mars");
	    lang.put("calendar.month.april", "Avril");
	    lang.put("calendar.month.may", "Mai");
	    lang.put("calendar.month.june", "Juin");
	    lang.put("calendar.month.july", "Juillet");
	    lang.put("calendar.month.august", "Août");
	    lang.put("calendar.month.september", "Septembre");
	    lang.put("calendar.month.october", "Octobre");
	    lang.put("calendar.month.november", "Novembre");
	    lang.put("calendar.month.december", "Décembre");
	    
	    // Media player
	    lang.put("media.player.label", "Lecteur multimedia");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Aperçu de l'image");
	    lang.put("image.viewer.zoom.in", "Zoom +");
	    lang.put("image.viewer.zoom.out", "Zoom -");
	    
	    // Debug console
	    lang.put("debug.console.label", "Console de déboggage");
	    lang.put("debug.enable.disable", "CTRL+Z pour activer ou désactiver le mode déboggage");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Général");
	    lang.put("dashboard.tab.news", "Brèves");
	    lang.put("dashboard.tab.user", "Utilisateur");
	    lang.put("dashboard.tab.workflow", "Workflow");
	    lang.put("dashboard.tab.mail", "Courriel");
	    lang.put("dashboard.tab.keymap", "Plan mots-clés");

	    // Dahboard general
	    lang.put("dashboard.new.items", "Nouveau");
	    lang.put("dashboard.user.locked.documents", "Documents verrouillés");
	    lang.put("dashboard.user.checkout.documents", "Documents réservés");
	    lang.put("dashboard.user.last.modified.documents", "Derniers documents modifiés");
	    lang.put("dashboard.user.last.downloaded.documents", "Derniers documents téléchargés");
	    lang.put("dashboard.user.subscribed.documents", "Abonnement à des documents");
	    lang.put("dashboard.user.subscribed.folders", "Abonnement à des dossiers");
	    lang.put("dashboard.user.last.uploaded.documents", "Derniers documents chargés");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Documents les plus vus durant la semaine");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Documents les plus vus durant le mois");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Documents modifiés les plus vus durant la semaine");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Documents modifiés les plus vus durant le mois");
	    lang.put("dashboard.general.last.uploaded.documents", "Derniers documents chargés");
	    lang.put("dashboard.workflow.pending.tasks", "Tâches en attente");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Tâches en attente non affectées");
	    lang.put("dashboard.workflow.task", "Tâche");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Nom");
	    lang.put("dashboard.workflow.task.created", "Date de création");
	    lang.put("dashboard.workflow.task.start", "Date de début");
	    lang.put("dashboard.workflow.task.duedate", "Date d'échéance");
	    lang.put("dashboard.workflow.task.end", "Date de fin");
	    lang.put("dashboard.workflow.task.description", "Description");
	    lang.put("dashboard.workflow.task.process.instance", "Instance");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Version");
	    lang.put("dashboard.workflow.task.process.name", "Nom");
	    lang.put("dashboard.workflow.task.process.description", "Description");
	    lang.put("dashboard.workflow.task.process.data", "Données");
	    lang.put("dashboard.workflow.comments", "Commentaires");
	    lang.put("dashboard.workflow.task.process.forms", "Formulaire");
	    lang.put("dashboard.workflow.add.comment","Ajouter un commentaire");
	    lang.put("dashboard.workflow.task.process.definition", "Définition du processus");
	    lang.put("dashboard.workflow.task.process.path", "Chemin");
	    lang.put("dashboard.refreshing", "Actualiser");
	    lang.put("dashboard.keyword", "Mots-clés");
	    lang.put("dashboard.keyword.suggest", "Saisir le mot-clé");
	    lang.put("dashboard.keyword.all", "Tous");
	    lang.put("dashboard.keyword.top", "Top");
	    lang.put("dashboard.keyword.related", "Mots-clés connexes");
	    lang.put("dashboard.keyword.goto.document", "Aller au document");
	    lang.put("dashboard.keyword.clean.keywords", "Supprimer les mots-clés");
	    lang.put("dashboard.mail.last.imported.mails", "Courriels importés");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Pièces jointes importées");
	    
	    // Workflow
	    lang.put("workflow.label", "Démarrage du workflow");
	    
	    // User configuration
	    lang.put("user.preferences.label", "Configuration");
	    lang.put("user.preferences.user", "Utilisateur");
	    lang.put("user.preferences.password", "Mot de passe");
	    lang.put("user.preferences.mail", "Courriel");
	    lang.put("user.preferences.roles","Roles");
	    lang.put("user.preferences.imap.host", "Serveur IMAP");
	    lang.put("user.preferences.imap.user", "Identifiant IMAP");
	    lang.put("user.preferences.imap.user.password", "Mot de passe IMAP");
	    lang.put("user.preferences.imap.folder", "Dossier IMAP");
	    lang.put("user.preferences.password.error", "Erreur: mots de passe différents");
	    lang.put("user.preferences.user.data", "Compte utilisateur");
	    lang.put("user.preferences.mail.data", "Compte mail");
	    lang.put("user.preferences.imap.error", "Tous les champs sont obligatoires pour la configuration du mail");
	    lang.put("user.preferences.imap.password.error.void", "Le mot de passe IMAP ne doit pas être vide");
	    lang.put("user.preferences.imap.test.error","IMAP configuration error");
	    lang.put("user.preferences.imap.test.ok","IMAP configuration ok");
	    
	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Ajouter un mot-clé du thesaurus");
	    lang.put("thesaurus.tab.tree", "Arborescence");
	    lang.put("thesaurus.tab.keywords", "Mots-clés");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "Ajouter une catégorie");
	    lang.put("categories.folder.error.delete", "Suppression impossible d'une catégorie associée à des documents");
	    
	    // Wizard
	    lang.put("wizard.document.uploading", "Assistant Document");
	    
	    // User info
	    lang.put("user.info.chat.connect", "Connexion à la discussion");
	    lang.put("user.info.chat.disconnect", "Déconnexion de la discussion");
	    lang.put("user.info.chat.new.room", "Salle de Discussion réseau");
	    lang.put("user.info.locked.actual", "Documents verrouillés");
	    lang.put("user.info.checkout.actual", "Documents réservés");
	    lang.put("user.info.subscription.actual", "Abonnements actuels");
	    lang.put("user.info.news.new", "Nouveautés");
	    lang.put("user.info.workflow.pending", "Workflows en attente");
	    lang.put("user.info.user.quota", "Quota utilisé");
	    
	    // Users online popup
	    lang.put("user.online", "Utilisateurs en ligne");
	    
	    // Chat room
	    lang.put("chat.room", "Chat");
	    lang.put("chat.users.in.room", "Utilisateurs");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Vous n'avez pas la permission d'accéder à ce document");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Impossible de verrouiller ce document");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Erreur interne du dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Chemin du document non trouvé");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Erreur Version");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Vous n'avez pas la permission d'accéder à ce document");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Il n'existe pas de document portant ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Il existe déjà un document portant ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Impossible de verrouiller ce document");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Impossible de déverrouiller ce document");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Erreur interne du dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Erreur interne de l'application");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Chemin document non trouvé");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Database, "Erreur Base de Donnée");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Vous n'avez pas la permission d'accéder à ce dossier");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Il n'existe pas de document portant ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Il existe déjà un dossier portant ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Erreur interne du dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Erreur interne de l'application");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Chemin dossier non trouvé");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Database, "Erreur Base de Donnée");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_AccessDenied, "Vous n'avez pas la permission d'accéder à cette item");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_ItemNotFound, "Il n'existe pas d'élément avec ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_Repository, "Erreur interne du dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_General, "Erreur interne de l'application");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_Database, "Erreur Base de Donnée");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Il n'existe pas de document portant ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Erreur interne de l'application");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Format de fichier non supporté");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Il existe déjà un document portant ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Ce document est trop gros");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_DocumentNameMismatch, "Le Nom du document est différent");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "La session a été fermée");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Database, "Erreur Base de Donnée");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Erreur générique d'exécution d'une requête");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Le nom de la recherche enregistrée doit être unique");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Database, "Erreur Base de Donnée");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Le nom du signet doit être unique");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Session perdue");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Erreur interne du dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Database, "Erreur Base de Donnée");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Erreur interne du dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IO, "Erreur E/S");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Erreur interne du dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Database, "Erreur Base de Donnée");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Erreur de configuration du navigateur");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_QuotaExceed, "Erreur Quota utilisateur dépassé, contactez votre administrateur");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_AccessDenied, "Accès au document refusé");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemNotFound, "Document non trouvé");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemExists, "Document déjà existant");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Lock, "Verrouillage du document refusé");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Repository, "Erreur interne du Dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_General, "Erreur interne de l'Application");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_PathNotFound, "Chemin du Document non trouvé");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Database, "Erreur Base de Donnée");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_PathNotFound, "Chemin non trouvé");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_AccessDenied, "Accès refusé");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_Repository, "Erreur interne du Dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_Database, "Erreur Base de Donnée");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_General, "Erreur interne de l'Application"); 
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Lock, "Verrouillé");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_PathNotFound, "Chemin inexistant");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_AccessDenied, "Accès refusé");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Repository, "Erreur interne du Dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Database, "Erreur Base de Donnée");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Erreur interne de l'Application");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchGroup, "Groupe inexistant");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchProperty, "Propriété inexistante");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_IO, "Erreur E/S");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_Repository, "Erreur interne du Dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_General, "Erreur interne de l'Application");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_PathNotFound, "Chemin inexistant");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_Database, "Erreur Base de Donnée");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_AccessDenied, "Accès refusé");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_Repository, "Erreur interne du Dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_General, "Erreur interne de l'Application");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_Database, "Erreur Base de Donnée");
	  }
}
