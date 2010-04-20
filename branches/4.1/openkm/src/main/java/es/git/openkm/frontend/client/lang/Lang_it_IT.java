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
 * Italian (Italy)
 * 
 * @author unknown
 */
public class Lang_it_IT {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd/MM/yyyy HH:mm:ss");
	    lang.put("general.day.pattern", "dd/MM/yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Caricamento OpenKM");
	    lang.put("startup.starting.loading", "Inizio caricamento OpenKM");
	    lang.put("startup.taxonomy", "Sto richiedendo il root node dell'albero");
	    lang.put("startup.template", "Sto richiedendo il root node del modello");
	    lang.put("startup.personal", "Sto richiedendo il root node personale");
	    lang.put("startup.mail", "Getting e-mail root node");
	    lang.put("startup.trash", "Sto richiedendo il root node del cestino");
	    lang.put("startup.user.home", "Sto richiedendo l'home node dell'utente");
	    lang.put("startup.bookmarks", "Sto richiedendo i segnalibri");
	    lang.put("startup.loading.taxonomy", "Caricamento albero");
	    lang.put("startup.loading.taxonomy.getting.folders", "Caricamento albero - richiesta cartelle");
	    lang.put("startup.loading.taxonomy.eval.params", "Caricamento albero - valutazione parametri del browser");
	    lang.put("startup.loading.taxonomy.open.path", "Caricamento albero - apertura path");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Caricamento albero - richiesta cartelle");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Caricamento albero - richiesta documenti");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
	    lang.put("startup.loading.personal", "Caricamento personale");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.templates", "Caricamento modelli");
	    lang.put("startup.loading.trash", "Caricamento cestino");
	    lang.put("startup.loading.history.search", "Caricamento ricerca");
	    lang.put("startup.loading.user.values", "Caricamento valori dell'utente");
	    lang.put("startup.loading.property.group.translations", "Caricamento proprietà gruppo traduzione");
	    lang.put("startup.keep.alive", "Tenere attivo");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Disponibile nuovo update di OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Albero");
	    lang.put("leftpanel.label.trash", "Cestino");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Richieste memorizzate");
	    lang.put("leftpanel.label.templates", "Modelli");
	    lang.put("leftpanel.label.my.documents", "Miei documenti");
	    lang.put("leftpanel.label.user.search", "User news");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Crea cartella");
	    lang.put("tree.menu.directory.remove", "Cancella");
	    lang.put("tree.menu.directory.rename", "Rinomina");
	    lang.put("tree.menu.directory.refresh", "Aggiorna");
	    lang.put("tree.menu.directory.move", "Sposta");
	    lang.put("tree.menu.directory.copy", "Copia");
	    lang.put("tree.menu.directory.add.document", "Aggiungi documento");
	    lang.put("tree.menu.add.bookmark", "Aggiungi segnalibro");
	    lang.put("tree.menu.set.home", "Imposta home predefinita");
	    lang.put("tree.menu.export", "Esporta su file");
	    lang.put("tree.status.refresh.folder", "Aggiornamento albero cartelle");
	    lang.put("tree.status.refresh.create", "Creazione cartella");
	    lang.put("tree.status.refresh.delete", "Cancellazione cartella");
	    lang.put("tree.status.refresh.rename", "Sto rinominando cartella");
	    lang.put("tree.status.refresh.restore", "Ripristino cartella");
	    lang.put("tree.status.refresh.purge", "Eliminazione cartella");
	    lang.put("tree.status.refresh.get", "Aggiornamento cartella");
	    lang.put("tree.folder.new", "Nuova cartella");
	    lang.put("tree.status.refresh.add.subscription", "Aggiunta sottoscrizione");
	    lang.put("tree.status.refresh.remove.subscription", "Cancellazione sottoscrizione");
	    lang.put("tree.status.refresh.get.root", "Aggiornamento radice cartella");
	    lang.put("tree.status.refresh.get.user.home", "Caricamento home dell'utente");
	    lang.put("tree.status.refresh.purge.trash", "Pulizia cestino");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Ripristina");
	    lang.put("trash.menu.directory.purge", "Elimina");
	    lang.put("trash.menu.directory.purge.trash", "Elimina cestino");
	    lang.put("trash.directory.select.label", "Seleziona cartella di destinazione");
	    
	    // General menu
	    lang.put("general.menu.file", "File");
	    	lang.put("general.menu.file.exit", "Esci");
	    	lang.put("general.menu.file.purge.trash", "Elimina cestino");
	    lang.put("general.menu.edit", "Modifica");
			lang.put("general.menu.file.create.directory", "Crea cartella");
			lang.put("general.menu.file.download.document", "Scarica documento");
			lang.put("general.menu.file.download.document.pdf", "Download document as PDF");
			lang.put("general.menu.file.send.link", "Invia collegamento a documento");
			lang.put("general.menu.file.lock", "Blocca");
			lang.put("general.menu.file.unlock", "Sblocca");
			lang.put("general.menu.file.add.document", "Aggiungi documento");
			lang.put("general.menu.file.checkout", "Checkout");
			lang.put("general.menu.file.checkin", "Checkin");
			lang.put("general.menu.file.cancel.checkout", "Annulla checkout");
			lang.put("general.menu.file.delete", "Cancella");
			lang.put("general.menu.file.refresh", "Aggiorna");
			lang.put("general.menu.file.scanner", "Scanner");
	    lang.put("general.menu.tools", "Strumenti");
	    	lang.put("general.menu.tools.languages", "Lingue");
	    	lang.put("general.menu.tools.skin", "Aspetto");
    			lang.put("general.menu.tools.skin.default", "Di default");
    			lang.put("general.menu.tools.skin.default2", "Di default 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Medium font");
    			lang.put("general.menu.tools.skin.bigfont", "Caratteri grandi");
    		lang.put("general.menu.debug.console", "Debug console");
    		lang.put("general.menu.administration", "Show administration");
    		lang.put("general.menu.tools.preferences", "Prefererences");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Segnalibri");
	    	lang.put("general.menu.bookmark.home", "Home");
	    	lang.put("general.menu.bookmark.default.home", "Configura home standard");
	    	lang.put("general.menu.bookmark.edit", "Modifica segnalibri");
	    lang.put("general.menu.help", "Aiuto");
		    lang.put("general.menu.bug.report", "Report dei bug");
		    lang.put("general.menu.support.request", "Richieste di supporto");
		    lang.put("general.menu.public.forum", "Forum pubblici");
		    lang.put("general.menu.project.web", "Sito OpenKM");
		    lang.put("general.menu.version.changes", "Version notes");
		    lang.put("general.menu.documentation", "Documentazione");
		    lang.put("general.menu.about", "Informazioni su OpenKM");
	    lang.put("general.connected", "Connesso come");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Nome");
	    lang.put("filebrowser.date.update", "Data aggiornamento");
	    lang.put("filebrowser.size", "Dimensione");
	    lang.put("filebrowser.path", "Indirizzo");
	    lang.put("filebrowser.author", "Autore");
	    lang.put("filebrowser.version", "Versione");
	    lang.put("filebrowser.menu.checkout", "Checkout");
	    lang.put("filebrowser.menu.checkin", "Checkin");
	    lang.put("filebrowser.menu.delete", "Cancella");
	    lang.put("filebrowser.menu.rename", "Rinomina");
	    lang.put("filebrowser.menu.checkout.cancel", "Annulla checkout");
	    lang.put("filebrowser.menu.lock", "Blocca");
	    lang.put("filebrowser.menu.unlock", "Sblocca");
	    lang.put("filebrowser.menu.download", "Scarica");
	    lang.put("filebrowser.menu.move", "Sposta");
	    lang.put("filebrowser.menu.copy", "Copia");
	    lang.put("filebrowser.menu.create.from.template", "Crea da modello");
	    lang.put("filebrowser.menu.add.property.group", "Aggiungi gruppo proprietà");
	    lang.put("filebrowser.menu.remove.property.group", "Rimuovi gruppo proprietà");
	    lang.put("filebrowser.menu.start.workflow", "Start workflow");
	    lang.put("filebrowser.menu.add.subscription", "Aggiungi sottoscrizione");
	    lang.put("filebrowser.menu.remove.subscription", "Rimuovi sottoscrizione");
	    lang.put("filebrowser.menu.add.bookmark", "Aggiungi segnalibri");
	    lang.put("filebrowser.menu.set.home", "Configura home standard");
	    lang.put("filebrowser.menu.refresh", "Aggiorna");
	    lang.put("filebrowser.menu.export", "Esporta su ZIP");
	    lang.put("filebrowser.menu.play", "Riproduci");
	    lang.put("filebrowser.menu.image.viewer", "Visualizzatore d'immagine");
	    lang.put("filebrowser.status.refresh.folder", "Aggiornamento lista cartelle");
	    lang.put("filebrowser.status.refresh.document", "Aggiornamento lista documenti");
	    lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "Cancellazione cartella");
	    lang.put("filebrowser.status.refresh.delete.document", "Cancellazione documento");
	    lang.put("filebrowser.status.refresh.checkout", "Documento in checkout");
	    lang.put("filebrowser.status.refresh.lock", "Documento bloccato");
	    lang.put("filebrowser.status.refresh.unlock", "Documento sbloccato");
	    lang.put("filebrowser.status.refresh.document.rename", "Documento rinominato");
	    lang.put("filebrowser.status.refresh.folder.rename", "Cartella rinominata");
	    lang.put("filebrowser.status.refresh.document.purge", "Cancellazione documento");
	    lang.put("filebrowser.status.refresh.folder.purge", "Cancellazione cartella");
	    lang.put("filebrowser.status.refresh.folder.get", "Aggiornamento cartella");
	    lang.put("filebrowser.status.refresh.document.get", "Aggiornamento documento");
	    lang.put("filebrowser.status.refresh.add.subscription", "Aggiunta sottoscrizione");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Cancellazione sottoscrizione");
	    lang.put("filebrowser.status.refresh.get.user.home", "Generazione home utente");
	    lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Invia");
	    lang.put("fileupload.status.sending", "Caricamento file...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "File caricato correttamente");
	    lang.put("fileupload.upload.status", "Stato caricamento");
	    lang.put("fileupload.upload.uploaded", "Caricato");
	    lang.put("fileupload.upload.completed", "Caricamento completato");
	    lang.put("fileupload.upload.runtime", "Runtime");
	    lang.put("fileupload.upload.remaining", "Rimanenti");
	    lang.put("fileupload.button.close", "Chiudi");
	    lang.put("fileupload.button.add.other.file", "Aggiungi un altro file");
	    lang.put("fileupload.status.move.file", "Spostamento file...");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "Copia file...");
	    lang.put("fileupload.status.copy.mail", "Coping mail...");
	    lang.put("fileupload.status.restore.file", "Ripristino file...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
	    lang.put("fileupload.status.move.folder", "Spostamento cartella...");
	    lang.put("fileupload.status.copy.folder", "Copia cartella...");
	    lang.put("fileupload.status.restore.folder", "Ripristino cartella...");
	    lang.put("fileupload.status.create.from.template", "Creazione file da modello...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Aggiungi nuovi documenti");
	    lang.put("fileupload.label.update", "Aggiorna documenti");
	    lang.put("fileupload.label.users.notify", "Avvisa utenti");
	    lang.put("fileupload.label.comment", "Commento");
	    lang.put("fileupload.label.users.to.notify",  "Utenti avvisati");
	    lang.put("fileupload.label.users",  "Utenti");
	    lang.put("fileupload.label.must.select.users",  "Seleziona gli utenti da avvisare");
	    lang.put("fileupload.label.importZip", "Import Documents from ZIP");
	    lang.put("fileupload.label.notify.comment", "Messaggio di avviso");
	    lang.put("fileupload.label.error.importing.zip", "Errore nell'importazione file");
	    lang.put("fileupload.label.error.move.file", "Errore nello spostamento file");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "Errore nella copia file");
	    lang.put("fileupload.label.error.copy.mail", "Error coping mail");
	    lang.put("fileupload.label.error.restore.file", "Errore nel ripristino file");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
	    lang.put("fileupload.label.error.move.folder", "Errore nello spostamento cartella");
	    lang.put("fileupload.label.error.copy.folder", "Errore nella copia cartella");
	    lang.put("fileupload.label.error.restore.folder", "Errore nel ripristino cartella");
	    lang.put("fileupload.label.error.create.from.template", "Errore nella creazione file da modello");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Non autorizzato a spostare su cartella di origine o figlia");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Non autorizzato a spostare su cartella di origine");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Non autorizzato a creare file su cartella di origine");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Proprietà");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "Storia");
	    lang.put("tab.document.status.history", "Storia delle modifiche");
	    lang.put("tab.status.security.role", "Aggiornamento permessi ruoli");
	    lang.put("tab.status.security.user", "Aggiornamento permessi utenti");
	    lang.put("tab.document.status.group.properties", "Aggiornamento gruppo proprietà");
	    lang.put("tab.document.status.set.keywords", "Parole chiave in definizione");
	    lang.put("tab.document.status.get.version.history.size", "Aggiorna dimensione storia documento");
	    lang.put("tab.document.status.purge.version.history", "Compattazione storia documento");
	    lang.put("tab.document.status.restore.version", "Ripristino versione documento");
	    lang.put("tab.document.security", "Sicurezza");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Proprietà");
	    lang.put("tab.folder.security", "Sicurezza");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Desktop");
	    lang.put("tab.workspace.search", "Ricerca");
	    lang.put("tab.workspace.dashboard", "DashBoard");
	    lang.put("tab.workspace.administration", "Administration");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Nome");
	    lang.put("document.folder", "Cartella");
	    lang.put("document.size", "Dimensione");
	    lang.put("document.created", "Creato");
	    lang.put("document.lastmodified", "Modificato");
	    lang.put("document.mimetype", "Tipo MIME");
	    lang.put("document.keywords", "Parole chiave");
	    lang.put("document.by", "Da");
	    lang.put("document.status", "Stato");
	    lang.put("document.status.checkout", "Eseguito checkout da");
	    lang.put("document.status.locked", "Bloccato da");
	    lang.put("document.status.normal", "Disponibile");
	    lang.put("document.subscribed", "Registrazione");
	    lang.put("document.subscribed.yes", "Si");
	    lang.put("document.subscribed.no", "No");
	    lang.put("document.history.size", "Dimensione storico");
	    lang.put("document.subscribed.users", "Utenti registrati");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Add note");
	    lang.put("document.keywords.cloud", "Keywords cloud");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Nome");
	    lang.put("folder.parent", "Superiore");
	    lang.put("folder.created", "Creato");
	    lang.put("folder.by", "Da");
	    lang.put("folder.subscribed", "Registrazione");
	    lang.put("folder.subscribed.yes", "Si");
	    lang.put("folder.subscribed.no", "No");
	    lang.put("folder.subscribed.users", "Utenti registrati");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Versione");
	    lang.put("version.created", "Data");
	    lang.put("version.author", "Autore");
	    lang.put("version.size", "Dimensione");
	    lang.put("version.purge.document", "Compact history");
	    lang.put("version.comment", "Commento");
	    
	    // Security
	    lang.put("security.label", "Sicurezza");
	    lang.put("security.group.name", "Gruppo");
	    lang.put("security.group.permission.read", "Lettura");
	    lang.put("security.group.permission.write", "Scrittura");
	    lang.put("security.user.name", "Utente");
	    lang.put("security.user.permission.read", "Lettura");
	    lang.put("security.user.permission.write", "Scrittura");
	    lang.put("security.users", "Utenti");
	    lang.put("security.groups", "Gruppi");
	    lang.put("security.recursive", "Cambia permessi ricorsivamente");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "Il sistema ha generato un errore");
	    lang.put("error.invocation", "Errore di comunicazione con il server");
	    
	    // About
	    lang.put("about.label", "A proposito di OpenKM");
	    lang.put("about.loading", "Caricamento ...");
	    
	    // Logout
	    lang.put("logout.label", "Uscita");
	    lang.put("logout.closed", "OpenKM è stato chiuso correttamente.");
	    lang.put("logout.logout", "OpenKM sta uscendo, attendre prego");
	    
	    // Confirm
	    lang.put("confirm.label", "Conferma");
	    lang.put("confirm.delete.folder", "Sei sicuro di voler cancellare la cartella?");
	    lang.put("confirm.delete.document", "Sei sicuro di voler cancellare il documento?");
	    lang.put("confirm.delete.trash", "Sei sicuro di voler svuotare il cestino?");
	    lang.put("confirm.purge.folder", "Sei sicuro di voler cancellare la cartella?");
	    lang.put("confirm.purge.document", "Sei sicuro di voler cancellare il documento?");
	    lang.put("confirm.delete.propety.group", "Sei sicuro di voler cancellare il gruppo di proprietà?");
	    lang.put("confirm.purge.version.history.document", "Sei sicuro di voler cancellare lo storico del documento?");
	    lang.put("confirm.purge.restore.document", "Sei sicuro di voler ripristinare questa version del documento?");
	    lang.put("confirm.set.default.home", "Sei sicuro di voler impostare questa come pagina home predefinita?");
	    lang.put("confirm.delete.saved.search", "¿ Do you really want to delete saved search ?");
	    lang.put("confirm.delete.user.news", "¿ Do you really want to delete user news ?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
	    lang.put("confirm.get.pooled.workflow.task","¿ Do you want to assign this task to you ?");
	    
	    // Search inputs
	    lang.put("search.context", "Contesto");
	    lang.put("search.content", "Contenuto");
	    lang.put("search.name", "Nome");
	    lang.put("search.keywords", "Parole chiave");
	    lang.put("search.folder", "Folder");
	    lang.put("search.results", "Risultati");
	    lang.put("search.to", "a");
	    lang.put("search.page.results", "Risultati");
	    lang.put("search.add.property.group", "Aggiungi gruppo di proprietà");
	    lang.put("search.mimetype", "Tipo Mime");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Ricerca avanzata");
	    lang.put("search.user", "Utente");
	    lang.put("search.date.and", "e");
	    lang.put("search.date.range", "Intervallo data tra");
	    lang.put("search.save.as.news", "Save as user news");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
	    
	    // Search results
	    lang.put("search.result.name", "Nome");
	    lang.put("search.result.score", "Importanza");
	    lang.put("search.result.size", "Dimensione");
	    lang.put("search.result.date.update", "Data aggiornamento");
	    lang.put("search.result.author", "Autore");
	    lang.put("search.result.version", "Versione");
	    lang.put("search.result.path", "Percorso");
	    lang.put("search.result.menu.download", "Scarica");
	    lang.put("search.result.menu.go.folder", "Vai alla cartella");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Aggiornamento ricerca");
	    lang.put("search.result.status.runsearch", "Salvataggio ricerca");
	    
	    // Search saved
	    lang.put("search.saved.run", "Esegui");
	    lang.put("search.saved.delete", "Cancella");
	    lang.put("search.saved.status.getsearchs", "Aggiornamento ricerche salvate");
	    lang.put("search.saved.status.savesearch", "Aggiornamento ricerca salvata");
	    lang.put("search.saved.status.deletesearch", "Cancellazione ricerca salvata");
	    lang.put("search.saved.status.getusernews", "Refreshing user news");
	    
	    // Button
	    lang.put("button.close", "Chiudi");
	    lang.put("button.logout", "Esci");
	    lang.put("button.update", "Aggiorna");
	    lang.put("button.cancel", "Cancella");
	    lang.put("button.accept", "Accetta");
	    lang.put("button.restore", "Ripristina");
	    lang.put("button.move", "Sposta");
	    lang.put("button.change", "Cambia");
	    lang.put("button.search", "Ricerca");
	    lang.put("button.save.search", "Salva ricerca");
	    lang.put("button.view", "Visualizza");
	    lang.put("button.clean", "Pulisci");
	    lang.put("button.add", "Aggiungi");
	    lang.put("button.delete", "Cancella");
	    lang.put("button.copy", "Copia");
	    lang.put("button.create", "Crea");
	    lang.put("button.show", "Mostra");
	    lang.put("button.memory", "Aggiorna");
	    lang.put("button.copy.clipboard", "Copy to clipboard");	
	    lang.put("button.start", "Start");
	    lang.put("button.select", "Select");
	    
	    // Group
	    lang.put("group.label", "Aggiungi gruppo di proprietà");
	    lang.put("group.group", "Gruppo");
	    lang.put("group.property.group", "Proprietà");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Aggiungi segnalibro");
	    lang.put("bookmark.name", "Nome");
	    lang.put("bookmark.edit.label", "Modifica segnalibro");
	    lang.put("bookmark.path", "Percorso");
	    lang.put("bookmark.type", "Tipo");
	    lang.put("bookmark.type.document", "Documento");
	    lang.put("bookmark.type.folder", "Cartella");
	    
	    // Notify
	    lang.put("notify.label", "Sending document link");
	    
	    // Status
	    lang.put("status.document.copied", "Documento da copiare");
	    lang.put("status.document.cut", "Documento da tagliare");
	    lang.put("status.folder.copied", "Cartella da copiare");
	    lang.put("status.folder.cut", "artella da tagliare");
	    lang.put("status.keep.alive.error", "La connession al server è stata persa (keep alive)");
	    lang.put("status.debug.enabled", "Debug abilitato");
	    lang.put("status.debug.disabled", "Debug disabilitato");
	    lang.put("status.network.error.detected", "Network error detected");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Domenica");
	    lang.put("calendar.day.monday", "Lunedì");
	    lang.put("calendar.day.tuesday", "Martedì");
	    lang.put("calendar.day.wednesday", "Mercoledì");
	    lang.put("calendar.day.thursday", "Giovedì");
	    lang.put("calendar.day.friday", "Venerdì");
	    lang.put("calendar.day.saturday", "Sabato");
	    lang.put("calendar.month.january", "Gennaio");
	    lang.put("calendar.month.february", "Febbraio");
	    lang.put("calendar.month.march", "Marzo");
	    lang.put("calendar.month.april", "Aprile");
	    lang.put("calendar.month.may", "Maggio");
	    lang.put("calendar.month.june", "Giugno");
	    lang.put("calendar.month.july", "Luglio");
	    lang.put("calendar.month.august", "Agosto");
	    lang.put("calendar.month.september", "Settembre");
	    lang.put("calendar.month.october", "Ottobre");
	    lang.put("calendar.month.november", "Novembre");
	    lang.put("calendar.month.december", "Dicembre");
	    
	    // Media player
	    lang.put("media.player.label", "Lettore multimediale");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Visualizzatore di immagini");
	    lang.put("image.viewer.zoom.in", "Zoom avanti");
	    lang.put("image.viewer.zoom.out", "Zoom indietro");
	    
	    // Debug console
	    lang.put("debug.console.label", "Debug console");
	    lang.put("debug.enable.disable", "CTRL+Z per abilitare o disabilitare la modalità debug");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "General");
	    lang.put("dashboard.tab.news", "News");
	    lang.put("dashboard.tab.user", "User");
	    lang.put("dashboard.tab.workflow", "Workflow");
	    lang.put("dashboard.tab.mail", "Mail");
	    lang.put("dashboard.tab.keymap", "Keyword map");

	    // Dahboard general
	    lang.put("dashboard.new.items", "News");
	    lang.put("dashboard.user.locked.documents", "Locked documents");
	    lang.put("dashboard.user.checkout.documents", "Checkout documents");
	    lang.put("dashboard.user.last.modified.documents", "Last modified documents");
	    lang.put("dashboard.user.last.downloaded.documents", "Last downloaded documents");
	    lang.put("dashboard.user.subscribed.documents", "Subscribed documents");
	    lang.put("dashboard.user.subscribed.folders", "Subscribed folders");
	    lang.put("dashboard.user.last.uploaded.documents", "Last uploaded documents");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Last week top viewed documents");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Last month top viewed documents");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Last week top modified documents");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Last month top viewed documents");
	    lang.put("dashboard.general.last.uploaded.documents", "Last uploaded documents");
	    lang.put("dashboard.workflow.pending.tasks", "Pending tasks");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Unassigned pending tasks");
	    lang.put("dashboard.workflow.task", "Task");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Name");
	    lang.put("dashboard.workflow.task.created", "Creation date");
	    lang.put("dashboard.workflow.task.start", "Start date");
	    lang.put("dashboard.workflow.task.duedate", "Due date");
	    lang.put("dashboard.workflow.task.end", "End date");
	    lang.put("dashboard.workflow.task.description", "Description");
	    lang.put("dashboard.workflow.task.process.instance", "Process instance");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Version");
	    lang.put("dashboard.workflow.task.process.name", "Name");
	    lang.put("dashboard.workflow.task.process.description", "Description");
	    lang.put("dashboard.workflow.task.process.data", "Data");
	    lang.put("dashboard.workflow.comments", "Comments");
	    lang.put("dashboard.workflow.task.process.forms", "Form");
	    lang.put("dashboard.workflow.add.comment","Add comment");
	    lang.put("dashboard.workflow.task.process.definition", "Process definition");
	    lang.put("dashboard.workflow.task.process.path", "Path");
	    lang.put("dashboard.refreshing", "Refreshing");
	    lang.put("dashboard.keyword", "Keywords");
	    lang.put("dashboard.keyword.suggest", "Type the keyword");
	    lang.put("dashboard.keyword.all", "All keywords");
	    lang.put("dashboard.keyword.top", "Top keywords");
	    lang.put("dashboard.keyword.related", "Related keywords");
	    lang.put("dashboard.keyword.goto.document", "Goto document");
	    lang.put("dashboard.keyword.clean.keywords", "Clean keywords");
	    lang.put("dashboard.mail.last.imported.mails", "Electronic mails");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Attachments");
	    
	    // Workflow
	    lang.put("workflow.label", "Start workflow");
	    
	    // User configuration
	    lang.put("user.preferences.label", "User configuration");
	    lang.put("user.preferences.user", "User");
	    lang.put("user.preferences.password", "Password");
	    lang.put("user.preferences.mail", "E-mail");
	    lang.put("user.preferences.imap.host", "IMAP server");
	    lang.put("user.preferences.imap.user", "IMAP user name");
	    lang.put("user.preferences.imap.user.password", "IMAP user password");
	    lang.put("user.preferences.imap.folder", "IMAP folder");
	    lang.put("user.preferences.password.error", "Error: passwords are diferents");
	    lang.put("user.preferences.user.data", "User account");
	    lang.put("user.preferences.mail.data", "Mail account");
	    lang.put("user.preferences.imap.error", "All fields are obligatory to set the mail configurations");
	    lang.put("user.preferences.imap.password.error.void", "Password must not be empty on IMAP mail creation");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Accesso al documento non consentito");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Il documento non è stato trovato");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Il documento esiste già");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Il documento non permette il blocco");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Il document necessita di essere sbloccato");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Errore interno del repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Errore internal dell'applicazione");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Il percorso del documento non è stato trovato");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Accesso alla cartella non consentito");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "La cartella non è stata trovata");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "La cartella esiste già");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Errore interno del repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Errore interno del repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Il percorso della cartella non è stata trovata");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Accesso all'elemento non consentito");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "L'elemento non è stato trovato");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Errore interno del repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Errore interno del repository");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Il documento non è stato trovato");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Errore interno del repository");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Il formato del documento non è tra quelli supportati");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Il documento esiste già");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "La dimensione del documento supera la soglia permessa");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Sessione chiusa");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Il nome della ricerca deve essere unico");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Il nome del segnalibro deve essere unico");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Sessione persa");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Errore interno del repository");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Errore interno del repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Errore interno del repository");
	  }
}