/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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
 * English (United Kingdom)
 * 
 * @author Mike Filippidis ( efthimios.filippidis@easycomtech.gr )
 */
public class Lang_el_GR {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd-MM-yyyy hh:mm:ss");
	    lang.put("general.hour.pattern", "hh:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Φόρτωση OpenKM");
	    lang.put("startup.starting.loading", "Έναρξκ φόρτωσης OpenKM");
	    lang.put("startup.taxonomy", "Λήψη ριζικού κόμβου δένδρου");
	    lang.put("startup.template", "Λήψη ριζικού κόμβου προτύπων");
	    lang.put("startup.personal", "Λήψη προσωπικού ριζικού κόμβου");
	    lang.put("startup.mail", "Getting e-mail root node");
	    lang.put("startup.trash", "Λήψη ριζικού κόμβου διεγραμμένων");
	    lang.put("startup.user.home", "Λήψη κόμβο χρήστη");
	    lang.put("startup.bookmarks", "Λήψη σελιδοδεικτών");
	    lang.put("startup.loading.taxonomy", "Φόρτωση δένδρου");
	    lang.put("startup.loading.taxonomy.getting.folders", "Φόρτωση δένδρου - Λήψη φακέλων ");
	    lang.put("startup.loading.taxonomy.eval.params", "Φόρτωση δένδρου - EVAL παράμετροι φυλλομετρητή");
	    lang.put("startup.loading.taxonomy.open.path", "Φόρτωση δένδρου - ανοιχτή διαδρομή");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Φόρτωση δένδρου - Λήψη φακέλων ");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Φόρτωση δένδρου - Λήψη εγγράφων ");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
	    lang.put("startup.loading.personal", "Φόρτωση προσωπικών");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.templates", "Φόρτωση προτύπων");
	    lang.put("startup.loading.trash", "Φόρτωση διεγραμμένων");
	    lang.put("startup.loading.history.search", "Φόρτωση ιστορικού αναζήτησης");
	    lang.put("startup.loading.user.values", "Φόρτωση τιμές χρήστη");
	    lang.put("startup.loading.property.group.translations", "Φόρτωση μεταφράσεων ομάδων ιδιοτήτων");
	    lang.put("startup.keep.alive", "Φόρτωση keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Υπάρχει διαθέσιμη ενημέρωση  OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Δένδρο");
	    lang.put("leftpanel.label.trash", "Απορρίματα");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Αποθηκευμένες αναζητήσεις");
	    lang.put("leftpanel.label.templates", "Πρότυπα");
	    lang.put("leftpanel.label.my.documents", "Τα έγγραφά μου");
	    lang.put("leftpanel.label.user.search", "Νέα χρήστη");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Δημιουργία φακέλου");
	    lang.put("tree.menu.directory.remove", "Διαγραφή");
	    lang.put("tree.menu.directory.rename", "Μετονομασία");
	    lang.put("tree.menu.directory.refresh", "Ανανέωση");
	    lang.put("tree.menu.directory.move", "Μετακίνηση");
	    lang.put("tree.menu.directory.copy", "Αντιγραφή");
	    lang.put("tree.menu.directory.add.document", "Προσθήκη εγγράφου");
	    lang.put("tree.menu.add.bookmark", "Προσθήκη σελιδοδείκτη");
	    lang.put("tree.menu.set.home", "Ορισμός προεπιλεγμένου home");
	    lang.put("tree.menu.export", "Εξαγωγή σε αρχείο");
	    lang.put("tree.status.refresh.folder", "Ενημέρωση δένδρου φακέλων");
	    lang.put("tree.status.refresh.create", "Δημιουργία φακέλου");
	    lang.put("tree.status.refresh.delete", "Διαγραφή φακέλου");
	    lang.put("tree.status.refresh.rename", "Μετονομασία φακέλου");
	    lang.put("tree.status.refresh.restore", "Επαναφορά φακέλου");
	    lang.put("tree.status.refresh.purge", "Εκκαθάριση φακέλου");
	    lang.put("tree.status.refresh.get", "Ενημέρωση φακέλου");
	    lang.put("tree.folder.new", "Νέος φάκελος");
	    lang.put("tree.status.refresh.add.subscription", "Προσθήκη συνδρομής");
	    lang.put("tree.status.refresh.remove.subscription", "Διαγραφή συνδρομής");
	    lang.put("tree.status.refresh.get.root", "Ανανέωση ριζικού φακέλου");
	    lang.put("tree.status.refresh.get.user.home", "Λήψη κόμβο χρήστη");
	    lang.put("tree.status.refresh.purge.trash", "Άδειασμα απορριμάτων");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Επαναφορά");
	    lang.put("trash.menu.directory.purge", "Εκκαθάριση");
	    lang.put("trash.menu.directory.purge.trash", "Εκκαθάριση διεγραμμένων");
	    lang.put("trash.directory.select.label", "Επιλέξτε φάκελο προορισμού");
	    
	    // General menu
	    lang.put("general.menu.file", "Αρχείο");
	    	lang.put("general.menu.file.exit", "Έξοδος");
	    	lang.put("general.menu.file.purge.trash", "Εκκαθάριση διεγραμμένων");
	    lang.put("general.menu.edit", "Επεξεργασία");
			lang.put("general.menu.file.create.directory", "Δημιουργία φακέλου");
			lang.put("general.menu.file.download.document", "Κατεβάστε το έγγραφο");
			lang.put("general.menu.file.download.document.pdf", "Κατεβάστε το έγγραφο ως PDF");
			lang.put("general.menu.file.send.link", "Αποστολή σύνδεσμου εγγράφου ");
			lang.put("general.menu.file.lock", "Κλείδωμα");
			lang.put("general.menu.file.unlock", "Ξεκλείδωμα");
			lang.put("general.menu.file.add.document", "Προσθήκη έγγραφου");
			lang.put("general.menu.file.checkout", "Check out");
			lang.put("general.menu.file.checkin", "Check in");
			lang.put("general.menu.file.cancel.checkout", "Ακύρωση check out");
			lang.put("general.menu.file.delete", "Διαγραφή");
			lang.put("general.menu.file.refresh", "Ανανέωση");
	    lang.put("general.menu.tools", "Εργαλεία");
	    	lang.put("general.menu.tools.languages", "Γλώσσες");
	    	lang.put("general.menu.tools.skin", "Εμφάνιση");
    			lang.put("general.menu.tools.skin.default", "Προεπιλογή");
    			lang.put("general.menu.tools.skin.default2", "Προεπιλογή 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Μεσαία γραμματοσειρά");
    			lang.put("general.menu.tools.skin.bigfont", "Μεγάλη γραμματοσειρά");
    		lang.put("general.menu.debug.console", "Κονσόλα Debug");
    		lang.put("general.menu.administration", "Προβολή διαχείρισης");
    		lang.put("general.menu.tools.preferences", "Prefererences");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Σελιδοδείκτες");
	    	lang.put("general.menu.bookmark.home", "Σπίτι");
	    	lang.put("general.menu.bookmark.default.home", "Ορίστε προεπιλεγμένο σπίτι");
	    	lang.put("general.menu.bookmark.edit", "Επεξεργασία σελιδοδεικτών");
	    lang.put("general.menu.help", "Βοήθεια");
		    lang.put("general.menu.bug.report", "Έκθεση Bug");
		    lang.put("general.menu.support.request", "Αίτηση υποστήριξης");
		    lang.put("general.menu.public.forum", "Δημόσιο forum");
		    lang.put("general.menu.project.web", "Εργο web");
		    lang.put("general.menu.version.changes", "Σημειώσεις έκδοσης");
		    lang.put("general.menu.documentation", "Τεκμηρίωση");
		    lang.put("general.menu.about", "Σχετικά με το OpenKM");
	    lang.put("general.connected", "Σύνδεδεμένος ως");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Όνομα");
	    lang.put("filebrowser.date.update", "Ημερομηνία ενημέρωσης");
	    lang.put("filebrowser.size", "Μέγεθος");
	    lang.put("filebrowser.path", "Θέση");
	    lang.put("filebrowser.author", "Συγγραφέας");
	    lang.put("filebrowser.version", "Έκδοση");
	    lang.put("filebrowser.menu.checkout", "Check out");
	    lang.put("filebrowser.menu.checkin", "Check in");
	    lang.put("filebrowser.menu.delete", "Διαγράφω");
	    lang.put("filebrowser.menu.rename", "Μετονομασία");
	    lang.put("filebrowser.menu.checkout.cancel", "Ακύρωση Check out");
	    lang.put("filebrowser.menu.lock", "Κλείδωμα");
	    lang.put("filebrowser.menu.unlock", "Ξεκλείδωμα");
	    lang.put("filebrowser.menu.download", "Λήψη");
	    lang.put("filebrowser.menu.move", "Μετακίνηση");
	    lang.put("filebrowser.menu.copy", "Αντιγραφή");
	    lang.put("filebrowser.menu.create.from.template", "Δημιουργία από πρότυπο");
	    lang.put("filebrowser.menu.add.property.group", "Προσθήκη ομάδας ιδιοτήτων");
	    lang.put("filebrowser.menu.remove.property.group", "Διαγραφή ομάδας ιδιοτήτων");
	    lang.put("filebrowser.menu.start.workflow", "Έναρξη ροής εργασίας");
	    lang.put("filebrowser.menu.add.subscription", "Προσθήκη συνδρομής");
	    lang.put("filebrowser.menu.remove.subscription", "Διαγραφή συνδρομής");
	    lang.put("filebrowser.menu.add.bookmark", "Προσθήκη σελιδοδείκτη");
	    lang.put("filebrowser.menu.set.home", "Ορίστε προεπιλεγμένο σπίτι");
	    lang.put("filebrowser.menu.refresh", "Ανανέωση");
	    lang.put("filebrowser.menu.export", "Εξαγωγή σε ZIP");
	    lang.put("filebrowser.menu.play", "Παιχνίδι");
	    lang.put("filebrowser.menu.image.viewer", "Προβολή εικόνας");
	    lang.put("filebrowser.status.refresh.folder", "Ενημέρωση λίστα φακέλων");
	    lang.put("filebrowser.status.refresh.document", "Ενημέρωση λίστα εγγράφων");
	    lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "Διαγραφή φακέλου");
	    lang.put("filebrowser.status.refresh.delete.document", "Διαγραφή εγγράφου");
	    lang.put("filebrowser.status.refresh.checkout", "Checking out εγγράφου");
	    lang.put("filebrowser.status.refresh.lock", "Κλείδωμα εγγράφου");
	    lang.put("filebrowser.status.refresh.unlock", "Απελευθέρωση εγγράφου");
	    lang.put("filebrowser.status.refresh.document.rename", "Μετονομασία εγγράφου");
	    lang.put("filebrowser.status.refresh.folder.rename", "Μετονομασία φακέλου");
	    lang.put("filebrowser.status.refresh.document.purge", "Διαγραφή εγγράφου");
	    lang.put("filebrowser.status.refresh.folder.purge", "Διαγραφή φακέλου");
	    lang.put("filebrowser.status.refresh.folder.get", "Ενημέρωση φακέλου");
	    lang.put("filebrowser.status.refresh.document.get", "Ενημέρωση εγγράφου");
	    lang.put("filebrowser.status.refresh.add.subscription", "Προσθήκη συνδρομής");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Διαγραφή συνδρομής");
	    lang.put("filebrowser.status.refresh.get.user.home", "Λήψη σπίτι χρήστη");
	    lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Αποστολή");
	    lang.put("fileupload.status.sending", "Μεταφόρτωση αρχείου ...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "Το αρχείο ανέβηκε σωστά");
	    lang.put("fileupload.upload.status", "Κατάσταση μεταφόρτωσης");
	    lang.put("fileupload.upload.uploaded", "Μεταφοτώθηκε");
	    lang.put("fileupload.upload.completed", "Ολοκληρώθηκε η μεταφότωση");
	    lang.put("fileupload.upload.runtime", "Διάρκεια εκτέλεσης");
	    lang.put("fileupload.upload.remaining", "Απομένει");
	    lang.put("fileupload.button.close", "Κλείσιμο");
	    lang.put("fileupload.button.add.other.file", "Προσθήκη άλλου αρχείου");
	    lang.put("fileupload.status.move.file", "Μετακίνηση αρχείου");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "Αντιγραφή αρχείου ...");
	    lang.put("fileupload.status.copy.mail", "Coping mail...");
	    lang.put("fileupload.status.restore.file", "Επαναφορά αρχείου ...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
	    lang.put("fileupload.status.move.folder", "Μετακίνηση φακέλου ...");
	    lang.put("fileupload.status.copy.folder", "Αντιγραφή φακέλου ...");
	    lang.put("fileupload.status.restore.folder", "Επαναφορά φακέλου ...");
	    lang.put("fileupload.status.create.from.template", "Δημιουργία αρχείου από πρότυπο ...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Προσθήκη νέων εγγράφων");
	    lang.put("fileupload.label.update", "Ενημέρωση εγγράφων");
	    lang.put("fileupload.label.users.notify", "Ειδοποίηση προς χρήστες");
	    lang.put("fileupload.label.comment", "Σχόλιο");
	    lang.put("fileupload.label.users.to.notify",  "Ειδοποίηση χρηστών");
	    lang.put("fileupload.label.users",  "Χρήστες");
	    lang.put("fileupload.label.must.select.users",  "Πρέπει να επιλέξετε κάποιο χρήστη για ειδοποίηση");
	    lang.put("fileupload.label.importZip", "Εισαγωγή εγγράφων από zip");
	    lang.put("fileupload.label.notify.comment", "Μήνυμα ειδοποίησης");
	    lang.put("fileupload.label.error.importing.zip", "Σφάλμα στην εισαγωγή αρχείου");
	    lang.put("fileupload.label.error.move.file", "Σφάλμα στην μετακίνηση αρχείου");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "Σφάλμα στην αντιγραφή αρχείου");
	    lang.put("fileupload.label.error.copy.mail", "Error coping mail");
	    lang.put("fileupload.label.error.restore.file", "Σφάλμα στην επαναφορά αρχείου");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
	    lang.put("fileupload.label.error.move.folder", "Σφάλμα στην μετακίνηση φακέλου");
	    lang.put("fileupload.label.error.copy.folder", "Σφάλμα στην αντιγραφή φακέλου");
	    lang.put("fileupload.label.error.restore.folder", "Σφάλμα στην επαναφορά φακέλου");
	    lang.put("fileupload.label.error.create.from.template", "Σφάλμα κατά τη δημιουργία του αρχείου από πρότυπο");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Δεν επιτρέπεται η μετακίνηση γονικού φακέλου");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Δεν επιτρέπεται η μετακίνηση γονικού φακέλου");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Δεν επιτρέπεται να δημιουργήσετε ένα αρχείο σε γονικό φάκελο");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Ιδιότητες");
	    lang.put("tab.document.notes", "Σημειώσεις");
	    lang.put("tab.document.history", "Ιστορικό");
	    lang.put("tab.document.status.history", "Ενημέρωση ιστορικού");
	    lang.put("tab.status.security.role", "Ενημέρωση ρόλων ασφαλείας ");
	    lang.put("tab.status.security.user", "Ενημέρωση ασφάλεια χρηστών");
	    lang.put("tab.document.status.group.properties", "Ενημέρωση ομάδα ιδιοτήτων");
	    lang.put("tab.document.status.set.keywords", "Ορισμός λέξεων-κλειδιών");
	    lang.put("tab.document.status.get.version.history.size", "Ανανέωση μεγέθους ιστορικού");
	    lang.put("tab.document.status.purge.version.history", "Συμπύκνωση μεγέθους ιστορικού");
	    lang.put("tab.document.status.restore.version", "Επαναφορά έκδοσης εγγράφου");
	    lang.put("tab.document.security", "Ασφάλεια");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Ιδιότητες");
	    lang.put("tab.folder.security", "Ασφάλεια");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Επιφάνεια εργασίας");
	    lang.put("tab.workspace.search", "Αναζήτηση");
	    lang.put("tab.workspace.dashboard", "DashBoard");
	    lang.put("tab.workspace.administration", "Διαχείριση");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Όνομα");
	    lang.put("document.folder", "Φάκελος");
	    lang.put("document.size", "Μέγεθος");
	    lang.put("document.created", "Δημιουργήθηκε");
	    lang.put("document.lastmodified", "Τροποποιήθηκε");
	    lang.put("document.mimetype", "Τύπος MIME");
	    lang.put("document.keywords", "Λέξεις-κλειδιά");
	    lang.put("document.by", "από");
	    lang.put("document.status", "Κατάσταση");
	    lang.put("document.status.checkout", "Επεξεργάστηκε από");
	    lang.put("document.status.locked", "Κλειδωμένο από");
	    lang.put("document.status.normal", "Διαθέσιμο");
	    lang.put("document.subscribed", "Εγγεγραμμένος");
	    lang.put("document.subscribed.yes", "Ναι");
	    lang.put("document.subscribed.no", "Όχι");
	    lang.put("document.history.size", "Μέγεθος ιστορικού");
	    lang.put("document.subscribed.users", "Εγγεγραμμένοι χρήστες");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Προσθήκη Σημείωσης");
	    lang.put("document.keywords.cloud", "Keywords cloud");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Όνομα");
	    lang.put("folder.parent", "Γονέας");
	    lang.put("folder.created", "Δημιουργήθηκε");
	    lang.put("folder.by", "από");
	    lang.put("folder.subscribed", "Εγγεγραμμένος");
	    lang.put("folder.subscribed.yes", "Ναι");
	    lang.put("folder.subscribed.no", "Όχι");
	    lang.put("folder.subscribed.users", "Εγγεγραμμένοι χρήστες");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Έκδοση");
	    lang.put("version.created", "Ημερομηνία");
	    lang.put("version.author", "Συγγραφέας");
	    lang.put("version.size", "Μέγεθος");
	    lang.put("version.purge.document", "Συμπύκνωση ιστορικού");
	    lang.put("version.comment", "Σχόλιο");
	    
	    // Security
	    lang.put("security.label", "Ασφαλεία");
	    lang.put("security.group.name", "Ομάδα");
	    lang.put("security.group.permission.read", "Ανάγνωση");
	    lang.put("security.group.permission.write", "Εγγραφή");
	    lang.put("security.user.name", "Χρήστης");
	    lang.put("security.user.permission.read", "Ανάγνωση");
	    lang.put("security.user.permission.write", "Εγγραφή");
	    lang.put("security.users", "Χρήστες");
	    lang.put("security.groups", "Ομάδες");
	    lang.put("security.recursive", "Αναδρομικές αλλαγές αδειών ");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "Δημιουργήθηκε ένα λάθος στο σύστημα ");
	    lang.put("error.invocation", "Σφάλμα κατά την επικοινωνία με το διακομιστή");
	    
	    // About
	    lang.put("about.label", "Σχετικά με το OpenKM");
	    lang.put("about.loading", "Φότωση ...");
	    
	    // Logout
	    lang.put("logout.label", "Έξοδος");
	    lang.put("logout.closed", "Το OpenKM έχει τερματιστεί σωστά.");
	    lang.put("logout.logout", "Το OpenKM αποσυνδέεται, παρακαλώ περιμένετε.");
	    
	    // Confirm
	    lang.put("confirm.label", "Επιβεβαίωση");
	    lang.put("confirm.delete.folder", "¿ Είστε σίγουροι ότι θέλετε να διαγράψετε το φάκελο ?");
	    lang.put("confirm.delete.document", "¿ Είστε σίγουροι ότι θέλετε να διαγράψετε το έγγραφο ?");
	    lang.put("confirm.delete.trash", "¿ Θέλετε πραγματικά να αδειάσουν τα απορρίματα ?");
	    lang.put("confirm.purge.folder", "¿ Είστε σίγουροι ότι θέλετε να διαγράψετε το φάκελο ?");
	    lang.put("confirm.purge.document", "¿ Είστε σίγουροι ότι θέλετε να διαγράψετε το έγγραφο ?");
	    lang.put("confirm.delete.propety.group", "¿ Είστε σίγουροι ότι θέλετε να διαγράψετε ομάδα ιδιοτήτων ?");
	    lang.put("confirm.purge.version.history.document", "¿ Είστε σίγουροι ότι θέλετε να διαγράψετε το ιστορικό εγγράφου ?");
	    lang.put("confirm.purge.restore.document", "¿ Θέλετε πραγματικά να αποκατασταθεί η έκδοση αυτού του εγγράφου ?");
	    lang.put("confirm.set.default.home", "¿ Είστε σίγουροι ότι θέλετε να ρυθμίσετε προεπιλεγμένο σπίτι ?");
	    lang.put("confirm.delete.saved.search", "¿ Είστε σίγουροι ότι θέλετε να διαγράψετε τις αποθηκευμένες αναζητήσεις ?");
	    lang.put("confirm.delete.user.news", "¿ Είστε σίγουροι ότι θέλετε να διαγράψετε τις ειδήσεις χρήστη ?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
	    
	    // Search inputs
	    lang.put("search.context", "Συμφραζόμενα");
	    lang.put("search.content", "Περιεχόμενο");
	    lang.put("search.name", "Όνομα");
	    lang.put("search.keywords", "Λέξεις-κλειδιά");
	    lang.put("search.folder", "Folder");
	    lang.put("search.results", "Αποτελέσματα");
	    lang.put("search.to", "προς");
	    lang.put("search.page.results", "Σελίδα αποτελεσμάτων");
	    lang.put("search.add.property.group", "Προσθήκη ομάδας ιδιοτήτων");
	    lang.put("search.mimetype", "Mime type");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Σύνθετη αναζήτηση");
	    lang.put("search.user", "Χρήστης");
	    lang.put("search.date.and", "και");
	    lang.put("search.date.range", "Ημερομηνίες μεταξύ");
	    lang.put("search.save.as.news", "Αποθήκευση ως ειδήσεις χρηστών");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
	    
	    // Search results
	    lang.put("search.result.name", "Όνομα");
	    lang.put("search.result.score", "Συνάφεια");
	    lang.put("search.result.size", "Μέγεθος");
	    lang.put("search.result.date.update", "Ημερομηνία ενημέρωσης");
	    lang.put("search.result.author", "Συγγραφέας");
	    lang.put("search.result.version", "Έκδοση");
	    lang.put("search.result.path", "Θέση");
	    lang.put("search.result.menu.download", "Λήψη");
	    lang.put("search.result.menu.go.folder", "Μετάβαση σε φάκελο");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Ενημέρωση αναζήτησης");
	    lang.put("search.result.status.runsearch", "Ενημέρωση αποθηκευμένης αναζήτησης");
	    
	    // Search saved
	    lang.put("search.saved.run", "Εκτέλεση");
	    lang.put("search.saved.delete", "Διαγραφή");
	    lang.put("search.saved.status.getsearchs", "Ανανέωση αποθηκευμένων αναζητήσεων");
	    lang.put("search.saved.status.savesearch", "Ενημέρωση αποθηκευμένων αναζητήσεων");
	    lang.put("search.saved.status.deletesearch", "Διαγραφή αποθηκευμένων αναζητήσεων");
	    lang.put("search.saved.status.getusernews", "Ανανέωση Ειδήσεων χρηστών");
	    
	    // Button
	    lang.put("button.close", "Κλείσιμο");
	    lang.put("button.logout", "Αποσύνδεση");
	    lang.put("button.update", "Ενημέρωση");
	    lang.put("button.cancel", "Ακύρωση");
	    lang.put("button.accept", "Αποδοχή");
	    lang.put("button.restore", "Επαναφορά");
	    lang.put("button.move", "Μετακίνηση");
	    lang.put("button.change", "Αλλαγή");
	    lang.put("button.search", "Αναζήτηση");
	    lang.put("button.save.search", "Αποθήκευση αναζήτησης");
	    lang.put("button.view", "Προβολή");
	    lang.put("button.clean", "Καθαρισμός");
	    lang.put("button.add", "Προσθήκη");
	    lang.put("button.delete", "Διαγραφή");
	    lang.put("button.copy", "Αντιγραφή");
	    lang.put("button.create", "Δημιουργία");
	    lang.put("button.show", "Προβολή");
	    lang.put("button.memory", "Ενημέρωση");
	    lang.put("button.copy.clipboard", "Αντιγραφή στο πρόχειρο");	
	    lang.put("button.start", "Εκκίνηση");
	    lang.put("button.select", "Select");
	    
	    // Group
	    lang.put("group.label", "Προσθήκη ομάδας ιδιοτήτων");
	    lang.put("group.group", "Ομάδα");
	    lang.put("group.property.group", "Ιδιότητα");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Προσθήκη σελιδοδείκτη");
	    lang.put("bookmark.name", "Όνομα");
	    lang.put("bookmark.edit.label", "Επεξεργασία σελιδοδεικτών");
	    lang.put("bookmark.path", "Θέση");
	    lang.put("bookmark.type", "Τύπος");
	    lang.put("bookmark.type.document", "Έγγραφο");
	    lang.put("bookmark.type.folder", "Φάκελος");
	    
	    // Notify
	    lang.put("notify.label", "Αποστολή σύνδεσμου εγγράφου");
	    
	    // Status
	    lang.put("status.document.copied", "Έγγραφο με σήμανση για αντιγραφή");
	    lang.put("status.document.cut", "Έγγραφο με σήμανση για αποκοπή");
	    lang.put("status.folder.copied", "Φάκελος με σήμανση για αντιγραφή");
	    lang.put("status.folder.cut", "Φάκελος με σήμανση για αποκοπή");
	    lang.put("status.keep.alive.error", "Απώλεια σύνδεσης με διακομιστή");
	    lang.put("status.debug.enabled", "Debug ενεργοποιημένο");
	    lang.put("status.debug.disabled", "Debug απενεργοποιημένο");
	    lang.put("status.network.error.detected", "Network error detected");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Κυριακή");
	    lang.put("calendar.day.monday", "Δευτέρα");
	    lang.put("calendar.day.tuesday", "Τρίτη");
	    lang.put("calendar.day.wednesday", "Τετάρτη");
	    lang.put("calendar.day.thursday", "Πέμπτη");
	    lang.put("calendar.day.friday", "Παρασκευή");
	    lang.put("calendar.day.saturday", "Σάββατο");
	    lang.put("calendar.month.january", "Ιανουάριος");
	    lang.put("calendar.month.february", "Φεβρουάριος");
	    lang.put("calendar.month.march", "Μάρτιος");
	    lang.put("calendar.month.april", "Απρίλιος");
	    lang.put("calendar.month.may", "Μάιος");
	    lang.put("calendar.month.june", "Ιούνιος");
	    lang.put("calendar.month.july", "Ιούλιος");
	    lang.put("calendar.month.august", "Αύγουστος");
	    lang.put("calendar.month.september", "Σεπτέμβριος");
	    lang.put("calendar.month.october", "Οκτώβριος");
	    lang.put("calendar.month.november", "Νοέμβριος");
	    lang.put("calendar.month.december", "Δεκέμβριος");
	    
	    // Media player
	    lang.put("media.player.label", "Media Player");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Προβολή εικόνας");
	    lang.put("image.viewer.zoom.in", "Μεγέθυνση");
	    lang.put("image.viewer.zoom.out", "Σμίκρυνση");
	    
	    // Debug console
	    lang.put("debug.console.label", "Debug κονσόλα");
	    lang.put("debug.enable.disable", "CTRL Z για να ενεργοποιήσετε ή να απενεργοποιήσετε τον εντοπισμό σφαλμάτων");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Γενικά");
	    lang.put("dashboard.tab.news", "Ειδήσεις");
	    lang.put("dashboard.tab.user", "Χρήστης");
	    lang.put("dashboard.tab.workflow", "Ροή εργασίας");
	    lang.put("dashboard.tab.mail", "Mail");
	    lang.put("dashboard.tab.keymap", "Keyword map");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Νέο");
	    lang.put("dashboard.user.locked.documents", "Κλειδωμένα έγγραφα");
	    lang.put("dashboard.user.checkout.documents", "Checkout έγγραφα");
	    lang.put("dashboard.user.last.modified.documents", "Πρόσφατα τροποποιημένα εγγράφα");
	    lang.put("dashboard.user.last.downloaded.documents", "Πρόσφατα κατεβασμένα έγγραφα");
	    lang.put("dashboard.user.subscribed.documents", "Έγγραφα με συνδρομή");
	    lang.put("dashboard.user.subscribed.folders", "Φάκελοι με συνδρομή");
	    lang.put("dashboard.user.last.uploaded.documents", "Πρόσφατα ανέβασμένα έγγραφα");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Έγγραφα με τις περισσότερες εμφανίσεις την περασμένη εβδομάδα ");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Έγγραφα με τις περισσότερες εμφανίσεις τον περασμένο μήνα");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Έγγραφα με τις περισσότερες τροποποιήσεις την περασμένη εβδομάδα ");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Έγγραφα με τις περισσότερες εμφανίσεις τον περασμένο μήνα");
	    lang.put("dashboard.general.last.uploaded.documents", "Τελευταία ανεβασμένα έγγραφα");
	    lang.put("dashboard.workflow.pending.tasks", "Εν αναμονή εργασίες");
	    lang.put("dashboard.workflow.task", "Εργασία");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Όνομα");
	    lang.put("dashboard.workflow.task.created", "Ημερομηνία δημιουργίας");
	    lang.put("dashboard.workflow.task.start", "Ημερομηνία έναρξης");
	    lang.put("dashboard.workflow.task.duedate", "Ημερομηνία εκκρεμότητας");
	    lang.put("dashboard.workflow.task.end", "Ημερομηνία λήξης");
	    lang.put("dashboard.workflow.task.description", "Περιγραφή");
	    lang.put("dashboard.workflow.task.process.instance", "Διαδικασία");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Έκδοση");
	    lang.put("dashboard.workflow.task.process.name", "Όνομα");
	    lang.put("dashboard.workflow.task.process.description", "Περιγραφή");
	    lang.put("dashboard.workflow.task.process.data", "Δεδομένα");
	    lang.put("dashboard.workflow.task.process.definition", "Διαδικασία");
	    lang.put("dashboard.workflow.task.process.path", "Θέση");
	    lang.put("dashboard.refreshing", "Ανανέωση");
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
	    lang.put("workflow.label", "Έναρξη ροής εργασίας");
	    
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
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Άρνηση πρόσβασης εγγράφου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Δεν βρέθηκε το έγγραφο");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Το έγγραφο υπάρχει ήδη");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Άρνηση κλειδώματος εγγράφου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Επιθυμητό ξεκλείδωμα εγγράφου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Εσωτερικό σφάλμα εφαρμογής");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Διαδρομή εγγράφου δεν βρέθηκε");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Άρνηση πρόσβασης φακέλου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Δεν βρέθηκε ο φάκελος ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Ο φάκελος υπάρχει ήδη");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Διαδρομή φακέλου δεν βρέθηκε");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Άρνηση πρόσβασης στοιχείου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Στοιχείο δεν βρέθηκε");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Εσωτερικό σφάλμα Repository");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Έγγραφο δεν βρέθηκε");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Εσωτερικό σφάλμα Repository");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Μη υποστηριζόμενη μορφή αρχείου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Έγγραφο υπάρχει ήδη");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Υπέρβαση μεγέθους εγγράφου");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Τερματισμός συνόδου");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Γενικό σφάλμα εκτέλεσης ερωτήματος");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Η αποθηκευμένη ονομασία αναζήτησης πρέπει να είναι μοναδικό");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Η ονομασία σελιδοδείκτη πρέπει να είναι μοναδική");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Απώλεια συνόδου");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Εσωτερικό σφάλμα Repository");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Σφάλμα επικοινωνίας");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Εσωτερικό σφάλμα Repository");
	  }
}
