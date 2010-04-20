/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

package es.git.openkm.frontend.client.lang;

import java.util.HashMap;

import es.git.openkm.frontend.client.config.ErrorCode;

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
	    lang.put("general.date.pattern", "dd.MM.yyyy HH:mm:ss");
	    lang.put("general.day.pattern", "dd.MM.yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Chargement de OpenKM");
	    lang.put("startup.starting.loading", "Démarrage du chargement de OpenKM");
	    lang.put("startup.taxonomy", "Récupération du noeud racine taxinomie");
	    lang.put("startup.template", "Récupération du noeud racine modèle");
	    lang.put("startup.personal", "Récupération du noeud racine personnel");
	    lang.put("startup.mail", "Récupération du noeud racine courriel");
	    lang.put("startup.trash", "Récupération du noeud racine corbeille");
	    lang.put("startup.user.home", "Récupération du noeud accueil utilisateur");
	    lang.put("startup.bookmarks", "Récupération signets");
	    lang.put("startup.loading.taxonomy", "Chargement taxonomie");
	    lang.put("startup.loading.taxonomy.getting.folders", "Chargement taxonomie - récupération dossiers");
	    lang.put("startup.loading.taxonomy.eval.params", "Chargement taxonomie - paramètres navigateur");
	    lang.put("startup.loading.taxonomy.open.path", "Chargement taxonomie - ouverture chemin");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Chargement taxonomie - récupération dossiers");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Chargement taxonomie - récupération documents");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Chargement taxonomie - récupération courriels");
	    lang.put("startup.loading.personal", "Chargement personnel");
	    lang.put("startup.loading.mail", "Chargement courriels");
	    lang.put("startup.loading.templates", "Chargement modèles");
	    lang.put("startup.loading.trash", "Chargement corbeille");
	    lang.put("startup.loading.history.search", "Chargement historique recherche");
	    lang.put("startup.loading.user.values", "Chargement valeurs utilisateur");
	    lang.put("startup.loading.property.group.translations", "Chargement propriétés de groupe");
	    lang.put("startup.keep.alive", "Chargement surveillance");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Mise à jour disponible pour OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Taxinomie");
	    lang.put("leftpanel.label.trash", "Corbeille");
	    lang.put("leftpanel.label.mail", "Courriel");
	    lang.put("leftpanel.label.stored.search", "Recherches sauvegardées");
	    lang.put("leftpanel.label.templates", "Modèles");
	    lang.put("leftpanel.label.my.documents", "Mes documents");
	    lang.put("leftpanel.label.user.search", "Brèves utilisateur");
	    lang.put("leftpanel.label.all.repository", "Tous les dépôts");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Créer dossier");
	    lang.put("tree.menu.directory.remove", "Supprimer");
	    lang.put("tree.menu.directory.rename", "Renommer");
	    lang.put("tree.menu.directory.refresh", "Actualiser");
	    lang.put("tree.menu.directory.move", "Déplacer");
	    lang.put("tree.menu.directory.copy", "Copier");
	    lang.put("tree.menu.directory.add.document", "Ajouter document");
	    lang.put("tree.menu.add.bookmark", "Ajouter signet");
	    lang.put("tree.menu.set.home", "Mettre en accueil par défaut");
	    lang.put("tree.menu.export", "Export du fichier");
	    lang.put("tree.status.refresh.folder", "Actualiser dossier");
	    lang.put("tree.status.refresh.create", "Créer dossier");
	    lang.put("tree.status.refresh.delete", "Effacer dossier");
	    lang.put("tree.status.refresh.rename", "Renommer dossier");
	    lang.put("tree.status.refresh.restore", "Restaurer dossier");
	    lang.put("tree.status.refresh.purge", "Vider dossier");
	    lang.put("tree.status.refresh.get", "Actualiser dossier");
	    lang.put("tree.folder.new", "Nouveau dossier");
	    lang.put("tree.status.refresh.add.subscription", "En établissant souscription");
	    lang.put("tree.status.refresh.remove.subscription", "En éliminant souscription");
	    lang.put("tree.status.refresh.get.root", "Actualiser le dossier racine");
	    lang.put("tree.status.refresh.get.user.home", "Actualiser l'accueil utilisateur");
	    lang.put("tree.status.refresh.purge.trash", "Vider la corbeille");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Restaurer");
	    lang.put("trash.menu.directory.purge", "Vider");
	    lang.put("trash.menu.directory.purge.trash", "Vider la corbeille");
	    lang.put("trash.directory.select.label", "Choisir le dossier destination");
	    
	    // General menu
	    lang.put("general.menu.file", "Fichier");
	    	lang.put("general.menu.file.exit", "Quitter");
	    	lang.put("general.menu.file.purge.trash", "Vider corbeille");
	    lang.put("general.menu.edit", "Edition");
			lang.put("general.menu.file.create.directory", "Créer dossier");
			lang.put("general.menu.file.download.document", "Télécharger le document");
			lang.put("general.menu.file.download.document.pdf", "Télécharger le document en PDF");
			lang.put("general.menu.file.send.link", "Envoi du document en tant que lien");
			lang.put("general.menu.file.lock", "Verrouiller");
			lang.put("general.menu.file.unlock", "Déverrouiller");
			lang.put("general.menu.file.add.document", "Ajouter document");
			lang.put("general.menu.file.checkout", "Emprunter");
			lang.put("general.menu.file.checkin", "Restituer");
			lang.put("general.menu.file.cancel.checkout", "Annuler emprunt");
			lang.put("general.menu.file.delete", "Effacer");
			lang.put("general.menu.file.refresh", "Actualiser");
			lang.put("general.menu.file.scanner", "Scanner");
	    lang.put("general.menu.tools", "Outils");
	    	lang.put("general.menu.tools.languages", "Langages");
	    	lang.put("general.menu.tools.skin", "Thème");
    			lang.put("general.menu.tools.skin.default", "Normal");
    			lang.put("general.menu.tools.skin.default2", "Normal 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Police moyenne");
    			lang.put("general.menu.tools.skin.bigfont", "Grande police");
    		lang.put("general.menu.debug.console", "Console d'erreur");
    		lang.put("general.menu.administration", "Montrer l'administration");
    		lang.put("general.menu.tools.preferences", "Prefererences");
    			lang.put("general.menu.tools.user.preferences", "Configuration personnelle");
    	lang.put("general.menu.bookmark", "Signets");
	    	lang.put("general.menu.bookmark.home", "Accueil");
	    	lang.put("general.menu.bookmark.default.home", "Mettre en accueil par défaut");
	    	lang.put("general.menu.bookmark.edit", "Editer les signets");
	    lang.put("general.menu.help", "Aide");
		    lang.put("general.menu.bug.report", "Signaler un problème");
		    lang.put("general.menu.support.request", "Demande de support");
		    lang.put("general.menu.public.forum", "Forums publics");
		    lang.put("general.menu.project.web", "Site du projet");
		    lang.put("general.menu.version.changes", "Notes de version");
		    lang.put("general.menu.documentation", "Documentation");
		    lang.put("general.menu.about", "A propos de OpenKM");
	    lang.put("general.connected", "Utilisateur : ");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Nom");
	    lang.put("filebrowser.date.update", "Date modification");
	    lang.put("filebrowser.size", "Taille");
	    lang.put("filebrowser.path", "Chemin");
	    lang.put("filebrowser.author", "Auteur");
	    lang.put("filebrowser.version", "Version");
	    lang.put("filebrowser.menu.checkout", "Emprunter");
	    lang.put("filebrowser.menu.checkin", "Restituer");
	    lang.put("filebrowser.menu.delete", "Supprimer");
	    lang.put("filebrowser.menu.rename", "Renommer");
	    lang.put("filebrowser.menu.checkout.cancel", "Annuler emprunt");
	    lang.put("filebrowser.menu.lock", "Verrouiller");
	    lang.put("filebrowser.menu.unlock", "Deverrouiller");
	    lang.put("filebrowser.menu.download", "Télécharger");
	    lang.put("filebrowser.menu.move", "Déplacer");
	    lang.put("filebrowser.menu.copy", "Copier");
	    lang.put("filebrowser.menu.create.from.template", "Créer depuis le modèle");
	    lang.put("filebrowser.menu.add.property.group", "Ajouter une propriété de groupe");
	    lang.put("filebrowser.menu.remove.property.group", "Enlver une propriété de groupe");
	    lang.put("filebrowser.menu.start.workflow", "Démarrer le workflow");
	    lang.put("filebrowser.menu.add.subscription", "Ajouter une souscription");
	    lang.put("filebrowser.menu.remove.subscription", "Éliminer une souscription");
	    lang.put("filebrowser.menu.add.bookmark", "Ajouter un signet");
	    lang.put("filebrowser.menu.set.home", "Mettre en accueil par défaut");
	    lang.put("filebrowser.menu.refresh", "Actualiser");
	    lang.put("filebrowser.menu.export", "Export en ZIP");
	    lang.put("filebrowser.menu.play", "Jouer");
	    lang.put("filebrowser.menu.image.viewer", "Aperçu image");
	    lang.put("filebrowser.status.refresh.folder", "Actualiser liste dossiers");
	    lang.put("filebrowser.status.refresh.document", "Actualiser liste documents");
	    lang.put("filebrowser.status.refresh.mail", "Actualiser liste courriels");
	    lang.put("filebrowser.status.refresh.delete.folder", "Effacement dossier en cours");
	    lang.put("filebrowser.status.refresh.delete.document", "Effacement document en cours");
	    lang.put("filebrowser.status.refresh.checkout", "Emprunt document en cours");
	    lang.put("filebrowser.status.refresh.lock", "Verrouillage document en cours");
	    lang.put("filebrowser.status.refresh.unlock", "Déverrouillage document en cours");
	    lang.put("filebrowser.status.refresh.document.rename", "Renommage du document");
	    lang.put("filebrowser.status.refresh.folder.rename", "Renommage du dossier");
	    lang.put("filebrowser.status.refresh.document.purge", "Effacement du document");
	    lang.put("filebrowser.status.refresh.folder.purge", "Effacement du dossier");
	    lang.put("filebrowser.status.refresh.folder.get", "Actualiser dossier");
	    lang.put("filebrowser.status.refresh.document.get", "Actualiser du document");
	    lang.put("filebrowser.status.refresh.add.subscription", "En établissant souscription");
	    lang.put("filebrowser.status.refresh.remove.subscription", "En éliminant souscription");
	    lang.put("filebrowser.status.refresh.get.user.home", "Obtenir le dossier utilisateur");
	    lang.put("filebrowser.status.refresh.delete.mail", "Effacer le courriel");
	    lang.put("filebrowser.status.refresh.mail.purge", "Purger le courriel");
	    
	    // File Upload
	    lang.put("fileupload.send", "Envoi");
	    lang.put("fileupload.status.sending", "Téléchargement du fichier en cours...");
	    lang.put("fileupload.status.indexing", "Indexation fichier...");
	    lang.put("fileupload.status.ok", "Téléchargement fichier effectué");
	    lang.put("fileupload.upload.status", "Statut téléchargement");
	    lang.put("fileupload.upload.uploaded", "Téléchargé");
	    lang.put("fileupload.upload.completed", "Téléchargement effectué");
	    lang.put("fileupload.upload.runtime", "Temps");
	    lang.put("fileupload.upload.remaining", "Restant");
	    lang.put("fileupload.button.close", "Fermer");
	    lang.put("fileupload.button.add.other.file", "Ajouter un autre document");
	    lang.put("fileupload.status.move.file", "Déplacement fichier...");
	    lang.put("fileupload.status.move.mail", "Déplacement courriel...");
	    lang.put("fileupload.status.copy.file", "Copie fichier...");
	    lang.put("fileupload.status.copy.mail", "Copie courriel...");
	    lang.put("fileupload.status.restore.file", "Restauration fichier...");
	    lang.put("fileupload.status.restore.mail", "Restauration courriel...");
	    lang.put("fileupload.status.move.folder", "Déplacement dossier...");
	    lang.put("fileupload.status.copy.folder", "Copie dossier...");
	    lang.put("fileupload.status.restore.folder", "Restauration dossier...");
	    lang.put("fileupload.status.create.from.template", "Création du fichier depuis le modèle...");
	    lang.put("fileupload.status.of", "de");
	    lang.put("fileupload.label.insert", "Ajout de documents");
	    lang.put("fileupload.label.update", "Mise à jour des documents");
	    lang.put("fileupload.label.users.notify", "Notifier à des utilisateurs");
	    lang.put("fileupload.label.comment", "Commentaire");
	    lang.put("fileupload.label.users.to.notify",  "Utilisateurs notifiés");
	    lang.put("fileupload.label.users",  "Utilisateurs");
	    lang.put("fileupload.label.must.select.users",  "Il doit choisir un utilisateur à notifier");
	    lang.put("fileupload.label.importZip", "Import documents en ZIP");
	    lang.put("fileupload.label.notify.comment", "Notification message");
	    lang.put("fileupload.label.error.importing.zip", "Erreur d'importation du fichier");
	    lang.put("fileupload.label.error.move.file", "Erreur déplacement fichier");
	    lang.put("fileupload.label.error.move.mail", "Erreur déplacement courriel");
	    lang.put("fileupload.label.error.copy.file", "Erreur copie fichier");
	    lang.put("fileupload.label.error.copy.mail", "Erreur copie courriel");
	    lang.put("fileupload.label.error.restore.file", "Erreur restauration fichier");
	    lang.put("fileupload.label.error.restore.mail", "Erreur restauration courriel");
	    lang.put("fileupload.label.error.move.folder", "Erreur déplacement dossier");
	    lang.put("fileupload.label.error.copy.folder", "Erreur copie dossier");
	    lang.put("fileupload.label.error.restore.folder", "Erreur restauration dossier");
	    lang.put("fileupload.label.error.create.from.template", "Erreur création du fichier depuis le modèle");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Déplacement depuis l'origine ou dossier enfant : refusé");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Déplacement dans le dossier d'origine : refusé");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Création du fichier dans le dossier d'origine : refusé");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Propriétés");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "Historique");
	    lang.put("tab.document.status.history", "Actualiser historique");
	    lang.put("tab.status.security.role", "Mise à jour sécurité rôles en cours");
	    lang.put("tab.status.security.user", "Mise à jour sécurité utilisateurs en cours");
	    lang.put("tab.document.status.group.properties", "Mise à jour propriétés de groupe");
	    lang.put("tab.document.status.set.keywords", "Réglage des mots-clés");
	    lang.put("tab.document.status.get.version.history.size", "Régénérer la taille de l'historique du document");
	    lang.put("tab.document.status.purge.version.history", "Compacter l'historique du document");
	    lang.put("tab.document.status.restore.version", "Restaurer la version du document");
	    lang.put("tab.document.security", "Securité");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Propriétés");
	    lang.put("tab.folder.security", "Securité");
	    
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
	    lang.put("document.status", "Status");
	    lang.put("document.status.checkout", "Emprunté par");
	    lang.put("document.status.locked", "Verrouillé par");
	    lang.put("document.status.normal", "Disponible");
	    lang.put("document.subscribed", "Souscrit");
	    lang.put("document.subscribed.yes", "Oui");
	    lang.put("document.subscribed.no", "Non");
	    lang.put("document.history.size", "Taille historique");
	    lang.put("document.subscribed.users", "Souscripteurs");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Ajouter note");
	    lang.put("document.keywords.cloud", "Mots-clés");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Nom");
	    lang.put("folder.parent", "Parent");
	    lang.put("folder.created", "Créé");
	    lang.put("folder.by", "par");
	    lang.put("folder.subscribed", "Souscrit");
	    lang.put("folder.subscribed.yes", "Oui");
	    lang.put("folder.subscribed.no", "Non");
	    lang.put("folder.subscribed.users", "Souscripteurs");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Version");
	    lang.put("version.created", "Date");
	    lang.put("version.author", "Auteur");
	    lang.put("version.size", "Taille");
	    lang.put("version.purge.document", "Unir historique");
	    lang.put("version.comment", "Commentaire");
	    
	    // Security
	    lang.put("security.label", "Securité");
	    lang.put("security.group.name", "Groupe");
	    lang.put("security.group.permission.read", "Lecture");
	    lang.put("security.group.permission.write", "Ecriture");
	    lang.put("security.user.name", "Utilisateur");
	    lang.put("security.user.permission.read", "Lecture");
	    lang.put("security.user.permission.write", "Ecriture");
	    lang.put("security.users", "Utilisateurs");
	    lang.put("security.groups", "Groupes");
	    lang.put("security.recursive", "Appliquer les changements de façon recursive");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

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
	    lang.put("confirm.delete.folder", "Voulez-vous vraiment effacer ce dossier ?");
	    lang.put("confirm.delete.document", "Voulez-vous vraiment effacer ce document ?");
	    lang.put("confirm.delete.trash", "Voulez-vous vraiment vider la corbeille ?");
	    lang.put("confirm.purge.folder", "Voulez-vous vraiment purger ce dossier ?");
	    lang.put("confirm.purge.document", "Voulez-vous vraiment purger ce document ?");
	    lang.put("confirm.delete.propety.group", "Voulez-vous vraiment effacer les propriétés de ce groupe ?");
	    lang.put("confirm.purge.version.history.document", "Voulez-vous vraiment effacer l'historique ?");
	    lang.put("confirm.purge.restore.document", "Voulez-vous vraiment restaurer la version du document ?");
	    lang.put("confirm.set.default.home", "Voulez-vous vraiment mettre cet accueil par défaut ?");
	    lang.put("confirm.delete.saved.search", "Voulez-vous vraiment effacer les recherches sauvegardées ?");
	    lang.put("confirm.delete.user.news", "Voulez-vous vraiment effacer les brèves ?");
	    lang.put("confirm.delete.mail", "Voulez-vous vraiment effacer ce courriel ?");
	    
	    // Search
	    lang.put("search.context", "Contexte");
	    lang.put("search.content", "Contenu");
	    lang.put("search.name", "Nom");
	    lang.put("search.keywords", "Mots-clés");
	    lang.put("search.folder", "Dossier");
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
	    
	    // Search results
	    lang.put("search.result.name", "Nom");
	    lang.put("search.result.score", "Relevance");
	    lang.put("search.result.size", "Taille");
	    lang.put("search.result.date.update", "Date modification");
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
	    lang.put("search.saved.delete", "Effacer");
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
	    lang.put("button.delete", "Effacer");
	    lang.put("button.copy", "Copier");
	    lang.put("button.create", "Créer");
	    lang.put("button.show", "Afficher");
	    lang.put("button.memory", "Mettre à jour");
	    lang.put("button.copy.clipboard", "Copie presse-papier");	
	    lang.put("button.start", "Démarrer");
	    lang.put("button.select", "Sélectionner");
	    
	    // Group
	    lang.put("group.label", "Ajout propriété de group");
	    lang.put("group.group", "Groupe");
	    lang.put("group.property.group", "Propriété");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Ajout signet");
	    lang.put("bookmark.name", "Name");
	    lang.put("bookmark.edit.label", "Edition signet");
	    lang.put("bookmark.path", "Chemin");
	    lang.put("bookmark.type", "Type");
	    lang.put("bookmark.type.document", "Document");
	    lang.put("bookmark.type.folder", "Dossier");
	    
	    // Notify
	    lang.put("notify.label", "Envoi du lien vers le document");
	    
	    // Status
	    lang.put("status.document.copied", "Copier le document marqué");
	    lang.put("status.document.cut", "Couper le document marqué");
	    lang.put("status.folder.copied", "Copier le dossier marqué");
	    lang.put("status.folder.cut", "Couper le dossier marqué");
	    lang.put("status.keep.alive.error", "Connexion au serveur perdue (surveillance)");
	    lang.put("status.debug.enabled", "Activation déboggage");
	    lang.put("status.debug.disabled", "Désactivation déboggage");
	    lang.put("status.network.error.detected", "Network error detected");
	    
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
	    lang.put("media.player.label", "Media player");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Aperçu image");
	    lang.put("image.viewer.zoom.in", "Zoom +");
	    lang.put("image.viewer.zoom.out", "Zoom -");
	    
	    // Debug console
	    lang.put("debug.console.label", "Console déboggage");
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
	    lang.put("dashboard.user.checkout.documents", "Documents sortis");
	    lang.put("dashboard.user.last.modified.documents", "Derniers documents modifiés");
	    lang.put("dashboard.user.last.downloaded.documents", "Derniers documents téléchargés");
	    lang.put("dashboard.user.subscribed.documents", "Souscription documents");
	    lang.put("dashboard.user.subscribed.folders", "Souscription dossiers");
	    lang.put("dashboard.user.last.uploaded.documents", "Derniers documents chargés");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Documents les plus vus durant la semaine");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Documents les plus vus durant ce mois");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Documents modifiés les plus vus durant la semaine");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Documents modifiés les plus vus durant ce mois");
	    lang.put("dashboard.general.last.uploaded.documents", "Derniers documents chargés");
	    lang.put("dashboard.workflow.pending.tasks", "Tâches en attente");
	    lang.put("dashboard.workflow.task", "Tâche");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Nom");
	    lang.put("dashboard.workflow.task.created", "Date de création");
	    lang.put("dashboard.workflow.task.start", "Date de début");
	    lang.put("dashboard.workflow.task.duedate", "Date dû");
	    lang.put("dashboard.workflow.task.end", "Date de fin");
	    lang.put("dashboard.workflow.task.description", "Description");
	    lang.put("dashboard.workflow.task.process.instance", "Instance");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Version");
	    lang.put("dashboard.workflow.task.process.name", "Nom");
	    lang.put("dashboard.workflow.task.process.description", "Description");
	    lang.put("dashboard.workflow.task.process.data", "Donnée");
	    lang.put("dashboard.workflow.comments", "Comments");
	    lang.put("dashboard.workflow.task.process.forms", "Form");
	    lang.put("dashboard.workflow.add.comment","Add comment");
	    lang.put("dashboard.workflow.task.process.definition", "Définition du processus");
	    lang.put("dashboard.workflow.task.process.path", "Chemin");
	    lang.put("dashboard.refreshing", "Actualiser");
	    lang.put("dashboard.keyword", "Mots-clés");
	    lang.put("dashboard.keyword.suggest", "Saisir le mot-clé");
	    lang.put("dashboard.keyword.all", "Tous");
	    lang.put("dashboard.keyword.top", "Top");
	    lang.put("dashboard.keyword.related", "Mots-clés connexes");
	    lang.put("dashboard.keyword.goto.document", "Aller au document");
	    lang.put("dashboard.keyword.clean.keywords", "Effacer les mots-clés");
	    lang.put("dashboard.mail.last.imported.mails", "Electronic mails");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Attachments");
	    
	    // Workflow
	    lang.put("workflow.label", "Démarrage workflow");
	    
	    // User configuration
	    lang.put("user.preferences.label", "Configuration");
	    lang.put("user.preferences.user", "User");
	    lang.put("user.preferences.password", "Mot de passe");
	    lang.put("user.preferences.mail", "E-mail");
	    lang.put("user.preferences.imap.host", "Serveur IMAP");
	    lang.put("user.preferences.imap.user", "Identifiant IMAP");
	    lang.put("user.preferences.imap.user.password", "Mot de passe IMAP");
	    lang.put("user.preferences.imap.folder", "Dossier IMAP");
	    lang.put("user.preferences.password.error", "Erreur: mots de passe différents");
	    lang.put("user.preferences.user.data", "User account");
	    lang.put("user.preferences.mail.data", "Mail account");
	    lang.put("user.preferences.imap.error", "All fields are obligatory to set the mail configurations");
	    lang.put("user.preferences.imap.password.error.void", "Password must not be empty on IMAP mail creation");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Vous n'avez pas la permission d'accéder à ce document");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Il n'existe pas de document portant ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Il existe déjà un document portant ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Impossible de verrouiller ce document");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Impossible de déverrouiller ce document");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Erreur interne du dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Erreur interne de l'application");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Chemin document non trouvé");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Vous n'avez pas la permission d'accéder à ce dossier");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Il n'existe pas de document portant ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Il existe déjà un dossier portant ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Erreur interne du dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Erreur interne de l'application");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Chemin dossier non trouvé");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Vous n'avez pas la permission d'accéder à cette item");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Il n'existe pas d'élément avec ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Erreur interne du dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Erreur interne de l'application");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Il n'existe pas de document portant ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Erreur interne de l'application");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Format de fichier non supporté");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Il existe déjà un document portant ce nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Ce document est trop gros");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "La session a été fermée");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Generic error executing query");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Le nom de la recherche enregistrée doit être unique");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "The bookmark name must be unique");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Session perdue");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Erreur interne du dépôt");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Erreur interne du dépôt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Erreur E/S");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Erreur interne du dépôt");
	  }
}
