/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
 *
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
 * Greek (Ελληνικά)
 * 
 * First author Mike Filippidis ( efthimios.filippidis@easycomtech.gr )
 * Second author Aristotle University of Thessaloniki - Network Operations Center ( noc@auth.gr )
 */
public class Lang_el_GR {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd-MM-yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd-MM-yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Εκκίνηση OpenKM");
	    lang.put("startup.starting.loading", "Φόρτωση του OpenKM");
	    lang.put("startup.taxonomy", "Λήψη κατάταξης");
	    lang.put("startup.categories", "Λήψη κατηγοριών");
	    lang.put("startup.thesaurus", "Λήψη θησαυρού λέξεων");
	    lang.put("startup.template", "Λήψη προτύπων");
	    lang.put("startup.personal", "Λήψη προσωπικών φακέλων");
	    lang.put("startup.mail", "Λήψη φακέλων e-mail");
	    lang.put("startup.trash", "Λήψη φακέλων διεγραμμένων");
	    lang.put("startup.user.home", "Λήψη αρχικού φακέλου χρήστη");
	    lang.put("startup.bookmarks", "Λήψη σελιδοδεικτών");
	    lang.put("startup.loading.taxonomy", "Φόρτωση κατάταξης");
	    lang.put("startup.loading.taxonomy.getting.folders", "Φόρτωση κατάταξης - Λήψη φακέλων ");
	    lang.put("startup.loading.taxonomy.eval.params", "Φόρτωση κατάταξης - EVAL παράμετροι φυλλομετρητή");
	    lang.put("startup.loading.taxonomy.open.path", "Φόρτωση κατάταξης - ανοιχτή διαδρομή");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Φόρτωση κατάταξης - Λήψη φακέλων ");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Φόρτωση κατάταξης - Λήψη εγγράφων ");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Φόρτωση κατάταξης - Λήψη e-mails");
	    lang.put("startup.loading.personal", "Φόρτωση προσωπικών φακέλων");
	    lang.put("startup.loading.mail", "Φόρτωση e-mail");
	    lang.put("startup.loading.categories", "Φόρτωση κατηγοριών");
	    lang.put("startup.loading.thesaurus", "Φόρτωση θησαυρού λέξεων");
	    lang.put("startup.loading.templates", "Φόρτωση προτύπων");
	    lang.put("startup.loading.trash", "Φόρτωση διεγραμμένων");
	    lang.put("startup.loading.history.search", "Φόρτωση ιστορικού αναζήτησης");
	    lang.put("startup.loading.user.values", "Φόρτωση παραμέτρων χρήστη");
	    lang.put("startup.keep.alive", "Φόρτωση keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", " Υπάρχει διαθέσιμη ενημέρωση για το OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Κατάταξη");
	    lang.put("leftpanel.label.trash", "Διεγραμμένα");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Αποθηκευμένη αναζήτηση");
	    lang.put("leftpanel.label.categories", "Κατηγορίες");
	    lang.put("leftpanel.label.thesaurus", "Θησαυρός λέξεων");
	    lang.put("leftpanel.label.templates", "Πρότυπα");
	    lang.put("leftpanel.label.my.documents", "Τα έγγραφά μου");
	    lang.put("leftpanel.label.user.search", "Αναζήτηση");
	    lang.put("leftpanel.label.all.repository", "Όλοι οι φάκελοι");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Δημιουργία φακέλου");
	    lang.put("tree.menu.directory.remove", "Διαγραφή");
	    lang.put("tree.menu.directory.rename", "Μετονομασία");
	    lang.put("tree.menu.directory.refresh", "Ανανέωση");
	    lang.put("tree.menu.directory.move", "Μετακίνηση");
	    lang.put("tree.menu.directory.copy", "Αντιγραφή");
	    lang.put("tree.menu.directory.add.document", "Προσθήκη εγγράφου");
	    lang.put("tree.menu.add.bookmark", "Προσθήκη σελιδοδείκτη");
	    lang.put("tree.menu.set.home", "Ορισμός σαν αρχική σελίδα");
	    lang.put("tree.menu.export", "Εξαγωγή σε αρχείο");
	    lang.put("tree.status.refresh.folder", "Ανανέωση φακέλων");
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
	    lang.put("tree.status.refresh.get.keywords", "Ανανέωση λέξεων κλειδιών");
	    lang.put("tree.status.refresh.get.user.home", "Λήψη αρχικού φακέλου χρήστη");
	    lang.put("tree.status.refresh.purge.trash", "Εκκαθάριση διεγραμμένων");
	    lang.put("tree.menu.directory.find.folder","Εύρεση φακέλου");
	    
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
			lang.put("general.menu.file.download.document", "Λήψη εγγράφου");
			lang.put("general.menu.file.download.document.pdf", "Λήψη εγγράφου ως αρχείο PDF");
			lang.put("general.menu.file.send.link", "Αποστολή συνδέσμου εγγράφου ");
			lang.put("general.menu.file.send.attachment", "Αποστολή συνημμένου εγγράφου");
			lang.put("general.menu.file.lock", "Κλείδωμα");
			lang.put("general.menu.file.unlock", "Ξεκλείδωμα");
			lang.put("general.menu.file.add.document", "Προσθήκη εγγράφου");
			lang.put("general.menu.file.checkout", "Check out");
			lang.put("general.menu.file.checkin", "Check in");
			lang.put("general.menu.file.cancel.checkout", "Ακύρωση check out");
			lang.put("general.menu.file.delete", "Διαγραφή");
			lang.put("general.menu.file.refresh", "Ανανέωση");
			lang.put("general.menu.file.scanner", "Σάρωση");
			lang.put("general.menu.file.uploader", "Ανέβασμα αρχείων");
	    lang.put("general.menu.tools", "Εργαλεία");
	    	lang.put("general.menu.tools.languages", "Γλώσσες");
	    	lang.put("general.menu.tools.skin", "Εμφάνιση");
    			lang.put("general.menu.tools.skin.default", "Προεπιλογή Εμφάνισης");
    			lang.put("general.menu.tools.skin.default2", "Προεπιλογή Εμφάνισης 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Μεσαία γραμματοσειρά");
    			lang.put("general.menu.tools.skin.bigfont", "Μεγάλη γραμματοσειρά");
    		lang.put("general.menu.debug.console", "Κονσόλα Debug");
    		lang.put("general.menu.administration", "Διαχείριση");
    		lang.put("general.menu.tools.preferences", "Προτιμήσεις");
    			lang.put("general.menu.tools.user.preferences", "Προτιμήσεις χρήστη");
    	lang.put("general.menu.bookmark", "Σελιδοδείκτες");
	    	lang.put("general.menu.bookmark.home", "Ορισμός σαν Αρχική Σελίδα");
	    	lang.put("general.menu.bookmark.default.home", "Ορισμός σαν Προεπιλεγμένη Σελίδα");
	    	lang.put("general.menu.bookmark.edit", "Επεξεργασία σελιδοδεικτών");
	    lang.put("general.menu.help", "Βοήθεια");
		    lang.put("general.menu.bug.report", "Αναφορά προβλήματος");
		    lang.put("general.menu.support.request", "Αίτηση υποστήριξης");
		    lang.put("general.menu.public.forum", "Δημόσιο forum");
		    lang.put("general.menu.project.web", "Ιστοσελίδα του OpenKM");
		    lang.put("general.menu.version.changes", "Αλλαγές στις εκδόσεις του προγράμματος ");
		    lang.put("general.menu.documentation", "Documentation");
		    lang.put("general.menu.about", "Σχετικά με το OpenKM");
	    lang.put("general.connected", "Συνδεδεμένος ως");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Όνομα Αρχείου");
	    lang.put("filebrowser.date.update", "Ημερομηνία ενημέρωσης");
	    lang.put("filebrowser.size", "Μέγεθος Αρχείου");
	    lang.put("filebrowser.path", "Θέση Αρχείου");
	    lang.put("filebrowser.author", "Συγγραφέας");
	    lang.put("filebrowser.version", "Έκδοση");
	    lang.put("filebrowser.menu.checkout", "Check out");
	    lang.put("filebrowser.menu.checkin", "Check in");
	    lang.put("filebrowser.menu.delete", "Διαγραφή");
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
	    lang.put("filebrowser.menu.start.workflow", "Έναρξη ροής εργασιών");
	    lang.put("filebrowser.menu.add.subscription", "Προσθήκη συνδρομής");
	    lang.put("filebrowser.menu.remove.subscription", "Διαγραφή συνδρομής");
	    lang.put("filebrowser.menu.add.bookmark", "Προσθήκη σελιδοδείκτη");
	    lang.put("filebrowser.menu.set.home", "Ορισμός σαν αρχική σελίδα");
	    lang.put("filebrowser.menu.refresh", "Ανανέωση");
	    lang.put("filebrowser.menu.export", "Εξαγωγή σε μορφή ZIP");
	    lang.put("filebrowser.menu.play", "Παιχνίδι");
	    lang.put("filebrowser.menu.image.viewer", "Προβολή εικόνας");
	    lang.put("filebrowser.status.refresh.folder", "Ενημέρωση λίστας φακέλων");
	    lang.put("filebrowser.status.refresh.document", "Ενημέρωση λίστας εγγράφων");
	    lang.put("filebrowser.status.refresh.mail", "Ενημέρωση λίστας e-mail");
	    lang.put("filebrowser.status.refresh.delete.folder", "Διαγραφή φακέλου");
	    lang.put("filebrowser.status.refresh.delete.document", "Διαγραφή εγγράφου");
	    lang.put("filebrowser.status.refresh.checkout", "Checking out εγγράφου");
	    lang.put("filebrowser.status.refresh.lock", "Κλείδωμα εγγράφου");
	    lang.put("filebrowser.status.refresh.unlock", "Ξεκλείδωμα εγγράφου");
	    lang.put("filebrowser.status.refresh.document.rename", "Μετονομασία εγγράφου");
	    lang.put("filebrowser.status.refresh.folder.rename", "Μετονομασία φακέλου");
	    lang.put("filebrowser.status.refresh.document.purge", "Οριστική Διαγραφή εγγράφου");
	    lang.put("filebrowser.status.refresh.folder.purge", "Οριστική Διαγραφή φακέλου");
	    lang.put("filebrowser.status.refresh.folder.get", "Λήψη φακέλου");
	    lang.put("filebrowser.status.refresh.document.get", "Λήψη εγγράφου");
	    lang.put("filebrowser.status.refresh.add.subscription", "Προσθήκη συνδρομής");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Διαγραφή συνδρομής");
	    lang.put("filebrowser.status.refresh.get.user.home", "Αρχική σελίδα χρήστη");
	    lang.put("filebrowser.status.refresh.delete.mail", "Διαγραφή e-mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Οριστική διαγραφή e-mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Αποστολή");
	    lang.put("fileupload.status.sending", "Μεταφόρτωση αρχείου ...");
	    lang.put("fileupload.status.indexing", "Indexing...");
	    lang.put("fileupload.status.ok", "Το αρχείο ανέβηκε σωστά");
	    lang.put("fileupload.upload.status", "Κατάσταση μεταφόρτωσης");
	    lang.put("fileupload.upload.uploaded", "Μεταφοτώθηκε");
	    lang.put("fileupload.upload.completed", "Ολοκληρώθηκε η μεταφόρτωση");
	    lang.put("fileupload.upload.runtime", "Διάρκεια εκτέλεσης");
	    lang.put("fileupload.upload.remaining", "Απομένει");
	    lang.put("fileupload.button.close", "Κλείσιμο");
	    lang.put("fileupload.button.add.other.file", "Προσθήκη άλλου αρχείου");
	    lang.put("fileupload.status.move.file", "Μετακίνηση αρχείου");
	    lang.put("fileupload.status.move.mail", "Μετακίνηση e-mail...");
	    lang.put("fileupload.status.copy.file", "Αντιγραφή αρχείου ...");
	    lang.put("fileupload.status.copy.mail", "Αντιγραφή e-mail ...");
	    lang.put("fileupload.status.restore.file", "Επαναφορά αρχείου ...");
	    lang.put("fileupload.status.restore.mail", "Επαναφορά e-mail ...");
	    lang.put("fileupload.status.move.folder", "Μετακίνηση φακέλου ...");
	    lang.put("fileupload.status.copy.folder", "Αντιγραφή φακέλου ...");
	    lang.put("fileupload.status.restore.folder", "Επαναφορά φακέλου ...");
	    lang.put("fileupload.status.create.from.template", "Δημιουργία αρχείου από πρότυπο ...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Προσθήκη νέων εγγράφων");
	    lang.put("fileupload.label.update", "Ενημέρωση εγγράφων");
	    lang.put("fileupload.label.users.notify", "Ενημέρωση χρηστών");
	    lang.put("fileupload.label.comment", "Σχόλιο");
	    lang.put("fileupload.label.users.to.notify",  "Χρήστες προς ενημέρωση");
	    lang.put("fileupload.label.users",  "Χρήστες");
	    lang.put("fileupload.label.groups.to.notify","Ομάδες προς ενημέρωση");
	    lang.put("fileupload.label.groups","Ομάδες");
	    lang.put("fileupload.label.must.select.users",  "Επιλογή χρηστών");
	    lang.put("fileupload.label.importZip", "Εισαγωγή εγγράφων σε μορφή zip");
	    lang.put("fileupload.label.notify.comment", "Μήνυμα ειδοποίησης");
	    lang.put("fileupload.label.error.importing.zip", "Σφάλμα κατά την εισαγωγή αρχείου");
	    lang.put("fileupload.label.error.move.file", "Σφάλμα κατά τη μετακίνηση αρχείου");
	    lang.put("fileupload.label.error.move.mail", "Σφάλμα κατά τη μετακίνηση e-mail");
	    lang.put("fileupload.label.error.copy.file", "Σφάλμα κατά την αντιγραφή αρχείου");
	    lang.put("fileupload.label.error.copy.mail", "Σφάλμα κατά την αντιγραφή e-mail");
	    lang.put("fileupload.label.error.restore.file", "Σφάλμα κατά την επαναφορά αρχείου");
	    lang.put("fileupload.label.error.restore.mail", "Σφάλμα κατά την επαναφορά e-mail");
	    lang.put("fileupload.label.error.move.folder", "Σφάλμα κατά τη μετακίνηση φακέλου");
	    lang.put("fileupload.label.error.copy.folder", "Σφάλμα κατά την αντιγραφή φακέλου");
	    lang.put("fileupload.label.error.restore.folder", "Σφάλμα κατά την επαναφορά φακέλου");
	    lang.put("fileupload.label.error.create.from.template", "Σφάλμα κατά τη δημιουργία αρχείου από πρότυπο");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Δεν επιτρέπεται η μετακίνηση γονικού φακέλου");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Δεν επιτρέπεται η αντιγραφή ίδιου φακέλου");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Δεν επιτρέπεται να δημιουργήσετε ένα αρχείο σε γονικό φάκελο");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Ιδιότητες");
	    lang.put("tab.document.notes", "Σημειώσεις");
	    lang.put("tab.document.history", "Ιστορικό");
	    lang.put("tab.document.status.history", "Κατάσταση ιστορικού");
	    lang.put("tab.status.security.role", "Κατάσταση ασφάλειας ");
	    lang.put("tab.status.security.user", "Κατάσταση  ασφάλειας χρηστών");
	    lang.put("tab.document.status.group.properties", "Κατάσταση ομάδας ιδιοτήτων");
	    lang.put("tab.document.status.set.keywords", "Ορισμός λέξεων-κλειδιών");
	    lang.put("tab.document.status.set.categories", "Ορισμός κατηγοριών");
	    lang.put("tab.document.status.get.version.history.size", "Ανανέωση μεγέθους ιστορικού");
	    lang.put("tab.document.status.purge.version.history", "Συμπύκνωση μεγέθους ιστορικού");
	    lang.put("tab.document.status.restore.version", "Επαναφορά έκδοσης εγγράφου");
	    lang.put("tab.document.security", "Ασφάλεια");
	    lang.put("tab.document.preview", "Προεπισκόπηση");
	    lang.put("tab.folder.properties", "Ιδιότητες");
	    lang.put("tab.folder.security", "Ασφάλεια");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Επιφάνεια εργασίας");
	    lang.put("tab.workspace.search", "Αναζήτηση");
	    lang.put("tab.workspace.dashboard", "Πίνακας Ελέγχου");
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
	    lang.put("document.categories", "Κατηγορίες");
	    
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
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Φάκελοι");
	    lang.put("folder.number.documents","Έγγραφα");
	    lang.put("folder.number.mails", "Ε-mails");
	    
	    // Version
	    lang.put("version.name", "Έκδοση");
	    lang.put("version.created", "Ημερομηνία");
	    lang.put("version.author", "Συγγραφέας");
	    lang.put("version.size", "Μέγεθος");
	    lang.put("version.purge.document", "Εκκαθάριση εγγράφου");
	    lang.put("version.comment", "Σχόλιο");
	    
	    // Security
	    lang.put("security.label", "Ασφάλεια");
	    lang.put("security.group.name", "Ομάδα");
	    lang.put("security.group.permission.read", "Ανάγνωση");
	    lang.put("security.group.permission.write", "Εγγραφή");
	    lang.put("security.group.permission.delete", "Διαγραφή");
	    lang.put("security.group.permission.security", "Ασφάλεια");
	    lang.put("security.user.name", "Χρήστης");
	    lang.put("security.user.permission.read", "Ανάγνωση");
	    lang.put("security.user.permission.write", "Εγγραφή");
	    lang.put("security.user.permission.delete", "Delete");
	    lang.put("security.user.permission.security", "Ασφάλεια");
	    lang.put("security.users", "Χρήστες");
	    lang.put("security.groups", "Ομάδες");
	    lang.put("security.recursive", "Αναδρομικές αλλαγές αδειών ");
	    lang.put("secutiry.filter.by.users","Φίλτρα χρηστών");
	    lang.put("secutiry.filter.by.groups","Φίλτρα ομάδων");
	    lang.put("security.status.updating","Ενημέρωση ασφάλειας");
	    
	    // Preview
	    lang.put("preview.unavailable", "Μη διαθέσιμη προεπισκόπιση");

	    // Mail
	    lang.put("mail.from", "Από");
	    lang.put("mail.reply", "Απάντηση");
	    lang.put("mail.to", "Προς");
	    lang.put("mail.subject", "Θέμα");
	    lang.put("mail.attachment", "Συνημμένα");
	    
	    // Error
	    lang.put("error.label", "Λάθος στο σύστημα ");
	    lang.put("error.invocation", "Σφάλμα κατά την επικοινωνία με το διακομιστή");
	    
	    // About
	    lang.put("about.label", "Σχετικά με το OpenKM");
	    lang.put("about.loading", "Φόρτωση ...");
	    
	    // Logout
	    lang.put("logout.label", "Έξοδος");
	    lang.put("logout.closed", "Το OpenKM έχει τερματιστεί σωστά.");
	    lang.put("logout.logout", "Το OpenKM αποσυνδέεται, παρακαλώ περιμένετε.");
	    
	    // Confirm
	    lang.put("confirm.label", "Επιβεβαίωση");
	    lang.put("confirm.delete.folder", "Είστε σίγουροι ότι θέλετε να διαγράψετε το φάκελο ?");
	    lang.put("confirm.delete.document", "Είστε σίγουροι ότι θέλετε να διαγράψετε το έγγραφο ?");
	    lang.put("confirm.delete.trash", "Είστε σίγουροι ότι θέλετε να εκκαθαρίσετε τα διεγραμμένα ?");
	    lang.put("confirm.purge.folder", "Είστε σίγουροι ότι θέλετε να διαγράψετε το φάκελο ?");
	    lang.put("confirm.purge.document", "Είστε σίγουροι ότι θέλετε να διαγράψετε το έγγραφο ?");
	    lang.put("confirm.delete.propety.group", "Είστε σίγουροι ότι θέλετε να διαγράψετε την ομάδα ιδιοτήτων ?");
	    lang.put("confirm.purge.version.history.document", "Είστε σίγουροι ότι θέλετε να διαγράψετε το ιστορικό εγγράφου ?");
	    lang.put("confirm.purge.restore.document", "Είστε σίγουροι ότι θέλετε να αποκατασταθεί η έκδοση αυτού του εγγράφου ?");
	    lang.put("confirm.set.default.home", "Είστε σίγουροι ότι θέλετε να ορίσετε μία προεπιλεγμένη αρχική σελίδα ?");
	    lang.put("confirm.delete.saved.search", "Είστε σίγουροι ότι θέλετε να διαγράψετε τις αποθηκευμένες αναζητήσεις ?");
	    lang.put("confirm.delete.user.news", "Είστε σίγουροι ότι θέλετε να διαγράψετε τις ειδήσεις χρήστη ?");
	    lang.put("confirm.delete.mail", " Είστε σίγουροι ότι θέλετε να διαγράψετε e-mail ?");
	    lang.put("confirm.get.pooled.workflow.task","Do you want to assign this task to you ?");
	    
	    // Search inputs
	    lang.put("search.context", "Συμφραζόμενα");
	    lang.put("search.content", "Περιεχόμενα");
	    lang.put("search.name", "Όνομα");
	    lang.put("search.keywords", "Λέξεις-κλειδιά");
	    lang.put("search.folder", "Φάκελος");
	    lang.put("search.category", "Κατηγορία");
	    lang.put("search.results", "Αποτελέσματα");
	    lang.put("search.to", "Προς");
	    lang.put("search.page.results", "Σελίδα αποτελεσμάτων");
	    lang.put("search.add.property.group", "Προσθήκη ομάδας ιδιοτήτων");
	    lang.put("search.mimetype", "Τύπος Mime");
	    lang.put("search.type", "Τύπος");
	    lang.put("search.type.document", "Έγγραφο");
	    lang.put("search.type.folder", "Φάκελος");
	    lang.put("search.type.mail", "Ε-mail");
	    lang.put("search.advanced", "Σύνθετη αναζήτηση");
	    lang.put("search.user", "Χρήστης");
	    lang.put("search.date.and", "και");
	    lang.put("search.date.range", "Ημερομηνίες μεταξύ");
	    lang.put("search.save.as.news", "Αποθήκευση ως νέα");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Φίλτρο Φακέλου");
	    lang.put("search.category.filter", "Φίλτρο κατηγορίας");
	    
	    // Search results
	    lang.put("search.result.name", "Όνομα");
	    lang.put("search.result.score", "Συνάφεια");
	    lang.put("search.result.size", "Μέγεθος");
	    lang.put("search.result.date.update", "Ημερομηνία ενημέρωσης");
	    lang.put("search.result.author", "Συγγραφέας");
	    lang.put("search.result.version", "Έκδοση");
	    lang.put("search.result.path", "Θέση");
	    lang.put("search.result.menu.download", "Λήψη");
	    lang.put("search.result.menu.go.folder", "Μετάβαση στο φάκελο");
	    lang.put("search.result.menu.go.document", "Μετάβαση στο έγγραφο");
	    lang.put("search.result.status.findPaginated", "Ενημέρωση αναζήτησης");
	    lang.put("search.result.status.runsearch", "Ενημέρωση αποθηκευμένης αναζήτησης");
	    
	    // Search saved
	    lang.put("search.saved.run", "Εκτέλεση");
	    lang.put("search.saved.delete", "Διαγραφή");
	    lang.put("search.saved.status.getsearchs", "Ανανέωση αποθηκευμένων αναζητήσεων");
	    lang.put("search.saved.status.savesearch", "Ενημέρωση αποθηκευμένων αναζητήσεων");
	    lang.put("search.saved.status.deletesearch", "Διαγραφή αποθηκευμένων αναζητήσεων");
	    lang.put("search.saved.status.getusernews", "Λήψη Ειδήσεων χρηστών");
	    
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
	    lang.put("button.clean", "Εκκαθάριση");
	    lang.put("button.add", "Προσθήκη");
	    lang.put("button.delete", "Διαγραφή");
	    lang.put("button.copy", "Αντιγραφή");
	    lang.put("button.create", "Δημιουργία");
	    lang.put("button.show", "Προβολή");
	    lang.put("button.memory", "Ενημέρωση");
	    lang.put("button.copy.clipboard", "Αντιγραφή στο πρόχειρο");	
	    lang.put("button.start", "Εκκίνηση");
	    lang.put("button.select", "Επιλογή");
	    lang.put("button.test", "Test");
	    lang.put("button.next", "Επόμενο");
	    
	    // Group
	    lang.put("group.label", "Όνομα ομάδας ιδιοτήτων");
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
	    lang.put("notify.label", "Αποστολή συνδέσμου εγγράφου");
	    lang.put("notify.label.attachment", "Αποστολή συνημμένου εγγράφου");
	    
	    // Status
	    lang.put("status.document.copied", "Έγγραφο με σήμανση για αντιγραφή");
	    lang.put("status.document.cut", "Έγγραφο με σήμανση για αποκοπή");
	    lang.put("status.folder.copied", "Φάκελος με σήμανση για αντιγραφή");
	    lang.put("status.folder.cut", "Φάκελος με σήμανση για αποκοπή");
	    lang.put("status.keep.alive.error", "Απώλεια σύνδεσης με διακομιστή");
	    lang.put("status.debug.enabled", "Debug ενεργοποιημένο");
	    lang.put("status.debug.disabled", "Debug απενεργοποιημένο");
	    lang.put("status.network.error.detected", "Διάγνωση σφάλματος δικτύου");
	    
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
	    lang.put("dashboard.tab.workflow", "Ροή εργασιών");
	    lang.put("dashboard.tab.mail", "Mail");
	    lang.put("dashboard.tab.keymap", "Keyword map");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Νέο");
	    lang.put("dashboard.user.locked.documents", "Κλειδωμένα έγγραφα");
	    lang.put("dashboard.user.checkout.documents", "Checkout έγγραφα");
	    lang.put("dashboard.user.last.modified.documents", "Πρόσφατα τροποποιημένα εγγράφα");
	    lang.put("dashboard.user.last.downloaded.documents", "Πρόσφατα ληφθέντα έγγραφα");
	    lang.put("dashboard.user.subscribed.documents", "Έγγραφα με συνδρομή");
	    lang.put("dashboard.user.subscribed.folders", "Φάκελοι με συνδρομή");
	    lang.put("dashboard.user.last.uploaded.documents", "Πρόσφατα ανέβασμένα έγγραφα");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Έγγραφα με τις περισσότερες εμφανίσεις την περασμένη εβδομάδα ");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Έγγραφα με τις περισσότερες εμφανίσεις τον περασμένο μήνα");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Έγγραφα με τις περισσότερες τροποποιήσεις την περασμένη εβδομάδα ");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Έγγραφα με τις περισσότερες εμφανίσεις τον περασμένο μήνα");
	    lang.put("dashboard.general.last.uploaded.documents", "Τελευταία ανεβασμένα έγγραφα");
	    lang.put("dashboard.workflow.pending.tasks", "Εκκρεμείς εργασίες");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Εκκρεμείς εργασίες χωρίς ανάθεση");
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
	    lang.put("dashboard.workflow.comments", "Σχόλια");
	    lang.put("dashboard.workflow.task.process.forms", "Φόρμες");
	    lang.put("dashboard.workflow.add.comment","Προσθήκη σχολίου");
	    lang.put("dashboard.workflow.task.process.definition", "Διαδικασία");
	    lang.put("dashboard.workflow.task.process.path", "Θέση");
	    lang.put("dashboard.refreshing", "Ανανέωση");
	    lang.put("dashboard.keyword", "Λέξεις κλειδιά");
	    lang.put("dashboard.keyword.suggest", "Πληκτρολογήστε τη λέξη κλειδί");
	    lang.put("dashboard.keyword.all", "Όλες οι λέξεις κλειδιά");
	    lang.put("dashboard.keyword.top", "Top keywords");
	    lang.put("dashboard.keyword.related", " Σχετικές λέξεις κλειδιά");
	    lang.put("dashboard.keyword.goto.document", "Μετάβαση στο έγγραφο");
	    lang.put("dashboard.keyword.clean.keywords", "Εκκαθάριση λέξεων κλειδιών");
	    lang.put("dashboard.mail.last.imported.mails", "Εισερχόμενα e-mails");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Συνημμένα Έγγραφα");
	    
	    // Workflow
	    lang.put("workflow.label", "Έναρξη ροής εργασίας");
	    
	    // User configuration
	    lang.put("user.preferences.label", "User configuration");
	    lang.put("user.preferences.user", "Χρήστης");
	    lang.put("user.preferences.password", "Κωδικός");
	    lang.put("user.preferences.mail", "E-mail");
	    lang.put("user.preferences.roles","Roles");
	    lang.put("user.preferences.imap.host", "Διακομιστής IMAP");
	    lang.put("user.preferences.imap.user", "IMAP όνομα χρήστη");
	    lang.put("user.preferences.imap.user.password", "IMAP κωδικός");
	    lang.put("user.preferences.imap.folder", "IMAP φάκελος");
	    lang.put("user.preferences.password.error", "Σφάλμα:οι κωδικοί διαφέρουν");
	    lang.put("user.preferences.user.data", "Λογαριασμός χρήστη");
	    lang.put("user.preferences.mail.data", "Λογαριασμός mail");
	    lang.put("user.preferences.imap.error", "Όλα τα πεδία είναι υποχρεωτικά για να ρυθμίσετε το e-mail");
	    lang.put("user.preferences.imap.password.error.void", "O κωδικός δεν πρέπει να είναι κενός κατά τη δημιουργία ενός IMAP mail");
	    lang.put("user.preferences.imap.test.error","Σφάλμα ρύθμισης IMAP");
	    lang.put("user.preferences.imap.test.ok","Έλεγχος ρυθμίσεων IMAP ok");

	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Προσθήκη λέξης κλειδί");
	    lang.put("thesaurus.tab.tree", "Δένδρο");
	    lang.put("thesaurus.tab.keywords", "Λέξεις κλειδιά");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "Προσθήκη κατηγορίας");
	    lang.put("categories.folder.error.delete", "Δεν μπορεί να γίνει η διαγραφή κατηγορίας που περιέχει έγγραφα");
	    
	    // Wizard
	    lang.put("wizard.document.uploading", "Document wizard");
	    
	    // User info
	    lang.put("user.info.chat.connect", "Σύνδεση στη συζήτηση");
	    lang.put("user.info.chat.disconnect", "Αποσύνδεση από τη συζήτηση");
	    lang.put("user.info.chat.new.room", "Δωμάτιο συζήτησης");
	    lang.put("user.info.locked.actual", "Κλειδωμένα έγγραφα");
	    lang.put("user.info.checkout.actual", "Checkout documents");
	    lang.put("user.info.subscription.actual", "Actual subscriptions");
	    lang.put("user.info.news.new", "Νέα");
	    lang.put("user.info.workflow.pending", "Εκκρεμής ροή εργασιών");
	    lang.put("user.info.user.quota", "User quota");
	    
	    // Users online popup
	    lang.put("user.online", "Συνδεδεμένοι χρήστες");
	    
	    // Chat room
	    lang.put("chat.room", "Συζήτηση");
	    lang.put("chat.users.in.room", "Χρήστες");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Απαγορεύεται η πρόσβαση στο έγγραφο");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Απαγορεύεται το κλείδωμα του εγγράφου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Δεν βρέθηκε η διαδρομή εγγράφου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Σφάλμα έκδοσης");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Απαγορεύεται η πρόσβαση στο έγγραφο");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Δεν βρέθηκε το έγγραφο");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Το έγγραφο υπάρχει ήδη");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Απαγορεύεται το κλείδωμα του εγγράφου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Επιθυμητό ξεκλείδωμα εγγράφου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Εσωτερικό σφάλμα εφαρμογής");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Δεν βρέθηκε η διαδρομή εγγράφου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_DatabaseException, "Σφάλμα Βάσης");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Άπαγορεύεται η πρόσβαση στο φάκελο");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Δεν βρέθηκε ο φάκελος ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Ο φάκελος υπάρχει ήδη");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Δεν βρέθηκε η διαδρομή φακέλου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_DatabaseException, "Σφάλμα Βάσης");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_AccessDenied, "Άπαγορεύεται η πρόσβαση στοιχείου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_ItemNotFound, "Το στοιχείο δεν βρέθηκε");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_Repository, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_General, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_DatabaseException, "Σφάλμα Βάσης");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Δεν βρέθηκε το έγγραφο");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Εσωτερικό σφάλμα Repository");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Μη υποστηριζόμενη μορφή αρχείου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Το έγγραφο υπάρχει ήδη");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Υπέρβαση μεγέθους εγγράφου");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Τερματισμός συνόδου");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_DatabaseException, "Σφάλμα Βάσης");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Γενικό σφάλμα εκτέλεσης ερωτήματος");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Η αποθηκευμένη ονομασία αναζήτησης πρέπει να είναι μοναδική");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_DatabaseException, "Σφάλμα Βάσης");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Η ονομασία σελιδοδείκτη πρέπει να είναι μοναδική");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Απώλεια συνόδου");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_DatabaseException, "Σφάλμα Βάσης");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Σφάλμα επικοινωνίας");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Εσωτερικό σφάλμα Repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_DatabaseException, "Σφάλμα Βάσης");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Σφάλμα στις ρυθμίσεις του πλοηγού");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_QuotaExceed, "Error user quota exceed, contact with adminitrator");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_AccessDenied, "Απαγορεύεται η πρόσβαση στο έγγραφο");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemNotFound, "Το έγγραφο δεν βρέθηκε");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemExists, "Το έγγραφο υπάρχει ήδη");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Lock, "Document lock denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Repository, "Repository εσωτερικό σφάλμα");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_General, "Εσωτερικό σφάλμα εφαρμογής");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_PathNotFound, "Η διαδρομή εγγράφου δεν βρέθηκε");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_DatabaseException, "Σφάλμα Βάσης");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_PathNotFound, "Η διαδρομή δεν βρέθηκε");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_AccessDenied, "Σφάλμα Βάσης");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_DatabaseException, "Σφάλμα Βάσης");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_General, "Εσωτερικό σφάλμα εφαρμογής"); 
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Lock, "Κλειδωμένο");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_PathNotFound, "Η διαδρομή δεν βρέθηκε");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_AccessDenied, "Απαγορεύεται η πρόσβαση");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_DatabaseException, "Σφάλμα Βάσης");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Εσωτερικό σφάλμα εφαρμογής");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchGroup, "Η ομάδα δεν υπάρχει");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchProperty, "Property not exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_General, "Εσωτερικό Σφάλμα Εφαρμογής");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_PathNotFound, "Σφάλμα Βάσης");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_DatabaseException, "Σφάλμα Βάσης");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_AccessDenied, "Απαγορεύεται η πρόσβαση");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_General, "Εσωτερικό Σφάλμα Εφαρμογής");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_DatabaseException, "Σφάλμα Βάσης");
	  }
}
