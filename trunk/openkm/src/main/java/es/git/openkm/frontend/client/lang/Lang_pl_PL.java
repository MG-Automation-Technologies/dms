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
 * Polish
 * 
 * @author Stanisław Klunder & Łukasz Frankowski
 *  
 * sklunder@gmail.com
 */
public class Lang_pl_PL {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd.MM.yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd.MM.yyyy");
	    lang.put("general.hour.pattern", "hh:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Trwa ładowanie OpenKM");
	    lang.put("startup.starting.loading", "Rozpoczynam ładowanie OpenKM");
	    lang.put("startup.taxonomy", "Pobieranie korzenia repozytorium");
	    lang.put("startup.thesaurus", "Getting thesaurus root node");
	    lang.put("startup.template", "Pobieranie korzenia repozytorium szablonów");
	    lang.put("startup.personal", "Pobieranie korzenia repozytorium osobistego");
	    lang.put("startup.mail", "Getting e-mail root node");
	    lang.put("startup.trash", "Pobieranie korzenia śmietnika");
	    lang.put("startup.user.home", "Pobieranie foldera domowego");
	    lang.put("startup.bookmarks", "Pobieranie zakładek");
	    lang.put("startup.loading.taxonomy", "Ładowanie repozytorium");
	    lang.put("startup.loading.taxonomy.getting.folders", "Ładowanie repozytorium - pobieranie folderów");
	    lang.put("startup.loading.taxonomy.eval.params", "Ładowanie repozytorium - ustalanie parametrów przeglądania");
	    lang.put("startup.loading.taxonomy.open.path", "Ładowanie repozytorium - otwieranie ścieżki");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Ładowanie repozytorium - pobieranie folderów");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Ładowanie repozytorium - pobieranie dokumentów");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
	    lang.put("startup.loading.personal", "Ładowanie ustawień");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.thesaurus", "Loading thesaurus");
	    lang.put("startup.loading.templates", "Ładowanie szablonów");
	    lang.put("startup.loading.trash", "Ładowanie śmietnika");
	    lang.put("startup.loading.history.search", "Ładowanie historii wyszukiwania");
	    lang.put("startup.loading.user.values", "Ładowanie danych użytkownika");
	    lang.put("startup.loading.property.group.translations", "Ładowanie tłumaczeń grup właściwości");
	    lang.put("startup.keep.alive", "Ładowanie systemu utrzymywana połączenia");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Dostępne są aktualizacje OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Repozytorium");
	    lang.put("leftpanel.label.trash", "Śmietnik");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Zapamiętane wyszukiwanie");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Szablony");
	    lang.put("leftpanel.label.my.documents", "Moje dokumenty");
	    lang.put("leftpanel.label.user.search", "Nowości");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Utwórz folder");
	    lang.put("tree.menu.directory.remove", "Usuń");
	    lang.put("tree.menu.directory.rename", "Zmień nazwę");
	    lang.put("tree.menu.directory.refresh", "Odśwież");
	    lang.put("tree.menu.directory.move", "Przenieś");
	    lang.put("tree.menu.directory.copy", "Kopiuj");
	    lang.put("tree.menu.directory.add.document", "Dodaj dokument");
	    lang.put("tree.menu.add.bookmark", "Dodaj zakładkę");
	    lang.put("tree.menu.set.home", "Ustaw jako katalog domowy");
	    lang.put("tree.menu.export", "Wyeksportuj do pliku");
	    lang.put("tree.status.refresh.folder", "Aktualizacja drzewa folderów");
	    lang.put("tree.status.refresh.create", "Tworzenie folderu");
	    lang.put("tree.status.refresh.delete", "Usuwanie folderu");
	    lang.put("tree.status.refresh.rename", "Zmiana nazwy folderu");
	    lang.put("tree.status.refresh.restore", "Odtwarzanie folderu");
	    lang.put("tree.status.refresh.purge", "Niedwracalne usuwanie folderu");
	    lang.put("tree.status.refresh.get", "Aktualizacja folderu");
	    lang.put("tree.folder.new", "Nowy folder");
	    lang.put("tree.status.refresh.add.subscription", "Dodawanie subskrypcji");
	    lang.put("tree.status.refresh.remove.subscription", "Usuwanie subskrypcji");
	    lang.put("tree.status.refresh.get.root", "Odświeżanie korzenia folderów");
	    lang.put("tree.status.refresh.get.user.home", "Pobieranie zawartości katalogu domowego");
	    lang.put("tree.status.refresh.purge.trash", "Opróżnianie śmietnika");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Przywróć");
	    lang.put("trash.menu.directory.purge", "Opróżnij");
	    lang.put("trash.menu.directory.purge.trash", "Opróznij śmietnik");
	    lang.put("trash.directory.select.label", "Wybierz folder docelowy");
	    
	    // General menu
	    lang.put("general.menu.file", "Plik");
	    	lang.put("general.menu.file.exit", "Wyjście");
	    	lang.put("general.menu.file.purge.trash", "Opróżnij śmietnik");
	    lang.put("general.menu.edit", "Edycja");
			lang.put("general.menu.file.create.directory", "Utwórz folder");
			lang.put("general.menu.file.download.document", "Pobierz dokument");
			lang.put("general.menu.file.download.document.pdf", "Pobierz dokument jako PDF");
			lang.put("general.menu.file.send.link", "Wyślij link do dokumentu");
			lang.put("general.menu.file.lock", "Zablokuj");
			lang.put("general.menu.file.unlock", "Odblokuj");
			lang.put("general.menu.file.add.document", "Dodaj dokument");
			lang.put("general.menu.file.checkout", "Wyciągnij i zablokuj");
			lang.put("general.menu.file.checkin", "Załaduj i odblokuj");
			lang.put("general.menu.file.cancel.checkout", "Anuluj wyciągnięcie");
			lang.put("general.menu.file.delete", "Usuń");
			lang.put("general.menu.file.refresh", "Odśwież");
	    lang.put("general.menu.tools", "Narzędzia");
	    	lang.put("general.menu.tools.languages", "Języki");
	    	lang.put("general.menu.tools.skin", "Skórka");
    			lang.put("general.menu.tools.skin.default", "Domyślna");
    			lang.put("general.menu.tools.skin.default2", "Domyślna 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Średnia czcionka");
    			lang.put("general.menu.tools.skin.bigfont", "Duża czcionka");
    		lang.put("general.menu.debug.console", "Konsola debug");
    		lang.put("general.menu.administration", "Pokaż panel administracyjny");
    		lang.put("general.menu.tools.preferences", "Prefererences");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Zakłądki");
	    	lang.put("general.menu.bookmark.home", "Folder domowy");
	    	lang.put("general.menu.bookmark.default.home", "Ustaw domyślny folder domowy");
	    	lang.put("general.menu.bookmark.edit", "Edycja zakładek");
	    lang.put("general.menu.help", "Pomoc");
		    lang.put("general.menu.bug.report", "Zgłoś błąd");
		    lang.put("general.menu.support.request", "Prośba o wsparcie");
		    lang.put("general.menu.public.forum", "Forum publiczne");
		    lang.put("general.menu.project.web", "Strona projektu");
		    lang.put("general.menu.version.changes", "Informacje o wersji");
		    lang.put("general.menu.documentation", "Dokumentacja");
		    lang.put("general.menu.about", "O OpenKM...");
	    lang.put("general.connected", "Podłączony jako");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Nazwa");
	    lang.put("filebrowser.date.update", "Data aktualizacji");
	    lang.put("filebrowser.size", "Wielkość");
	    lang.put("filebrowser.path", "Ścieżka");
	    lang.put("filebrowser.author", "Autor");
	    lang.put("filebrowser.version", "Wersja");
	    lang.put("filebrowser.menu.checkout", "Wyciągnij i zablokuj");
	    lang.put("filebrowser.menu.checkin", "Załaduj i odblokuj");
	    lang.put("filebrowser.menu.delete", "Usuń");
	    lang.put("filebrowser.menu.rename", "Zmień nazwę");
	    lang.put("filebrowser.menu.checkout.cancel", "Anuluj wyciągnięcie");
	    lang.put("filebrowser.menu.lock", "Zablokuj");
	    lang.put("filebrowser.menu.unlock", "Odblokuj");
	    lang.put("filebrowser.menu.download", "Pobierz");
	    lang.put("filebrowser.menu.move", "Przenieś");
	    lang.put("filebrowser.menu.copy", "Kopiuj");
	    lang.put("filebrowser.menu.create.from.template", "Utwórz dokument z szablonu");
	    lang.put("filebrowser.menu.add.property.group", "Dodaj grupę właściwości");
	    lang.put("filebrowser.menu.remove.property.group", "Usuń grupę właściwości");
	    lang.put("filebrowser.menu.start.workflow", "Rozpocznij obieg dokumentu");
	    lang.put("filebrowser.menu.add.subscription", "Dodaj subskrypcję");
	    lang.put("filebrowser.menu.remove.subscription", "Usuń subskrypcję");
	    lang.put("filebrowser.menu.add.bookmark", "Dodaj zakładkę");
	    lang.put("filebrowser.menu.set.home", "Ustaw domyślny folder domowy");
	    lang.put("filebrowser.menu.refresh", "Odśwież");
	    lang.put("filebrowser.menu.export", "Eksportuj do ZIP");
	    lang.put("filebrowser.menu.play", "Uruchom");
	    lang.put("filebrowser.menu.image.viewer", "Przeglądarka obrazów");
	    lang.put("filebrowser.status.refresh.folder", "Aktualizacja listy folderów");
	    lang.put("filebrowser.status.refresh.document", "Aktualizacji listy dokumentów");
	    lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "Usuwanie folderu");
	    lang.put("filebrowser.status.refresh.delete.document", "Usuwanie dokumentu");
	    lang.put("filebrowser.status.refresh.checkout", "Wyciąganie dokumentu");
	    lang.put("filebrowser.status.refresh.lock", "Blokowani dokumentu");
	    lang.put("filebrowser.status.refresh.unlock", "Odblokowywanie dokumentu");
	    lang.put("filebrowser.status.refresh.document.rename", "Zmiana nazwy dokumentu");
	    lang.put("filebrowser.status.refresh.folder.rename", "Zmiana nazwy folderu");
	    lang.put("filebrowser.status.refresh.document.purge", "Usuwanie dokumentu");
	    lang.put("filebrowser.status.refresh.folder.purge", "Usuwanie folderu");
	    lang.put("filebrowser.status.refresh.folder.get", "Aktualizacja folderu");
	    lang.put("filebrowser.status.refresh.document.get", "Aktualizacja dokumentu");
	    lang.put("filebrowser.status.refresh.add.subscription", "Dodawanie subskrypcji");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Usuwanie subskrypcji");
	    lang.put("filebrowser.status.refresh.get.user.home", "Pobieranie katalogu domowego");
	    lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Wyślij");
	    lang.put("fileupload.status.sending", "Ładowanie pliku...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "Plik został poprawnie załadowany");
	    lang.put("fileupload.upload.status", "Status ładowania");
	    lang.put("fileupload.upload.uploaded", "Załadowany");
	    lang.put("fileupload.upload.completed", "Ładowanie zakończone");
	    lang.put("fileupload.upload.runtime", "Czas wykonania");
	    lang.put("fileupload.upload.remaining", "Pozostało");
	    lang.put("fileupload.button.close", "Zamknij");
	    lang.put("fileupload.button.add.other.file", "Dodaj kolejny plik");
	    lang.put("fileupload.status.move.file", "Przenoszenie pliku...");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "Kopiowanie pliku...");
	    lang.put("fileupload.status.copy.mail", "Coping mail...");
	    lang.put("fileupload.status.restore.file", "Przywracanie pliku...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
	    lang.put("fileupload.status.move.folder", "Przenoszenie folderu...");
	    lang.put("fileupload.status.copy.folder", "Kopiowanie folderu...");
	    lang.put("fileupload.status.restore.folder", "Przywracanie folderu...");
	    lang.put("fileupload.status.create.from.template", "Tworzenie pliku z szablonu...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Dodaj nowe dokumenty");
	    lang.put("fileupload.label.update", "Zaktualizuj dokumenty");
	    lang.put("fileupload.label.users.notify", "Poinformuj użytkowników");
	    lang.put("fileupload.label.comment", "Skomentuj");
	    lang.put("fileupload.label.users.to.notify",  "Informowani użytkownicy");
	    lang.put("fileupload.label.users",  "Użytkownicy");
	    lang.put("fileupload.label.must.select.users",  "Musisz wybrać użytkowników do powiadomienia");
	    lang.put("fileupload.label.importZip", "Importuj dokumenty z archiwum ZIP");
	    lang.put("fileupload.label.notify.comment", "Wiadomość");
	    lang.put("fileupload.label.error.importing.zip", "Błąd importowania pliku");
	    lang.put("fileupload.label.error.move.file", "Błąd przenoszenia pliku");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "Błąd kopiowania pliku");
	    lang.put("fileupload.label.error.copy.mail", "Error coping mail");
	    lang.put("fileupload.label.error.restore.file", "Błąd przywracania pliku");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
	    lang.put("fileupload.label.error.move.folder", "Błąd przenoszenia folderu");
	    lang.put("fileupload.label.error.copy.folder", "Błąd kopiowania folderu");
	    lang.put("fileupload.label.error.restore.folder", "Błąd przywracania folderu");
	    lang.put("fileupload.label.error.create.from.template", "Błąd tworzenia pliku z szablonu");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Przeniesienie do folderu źródłowego lub podrzędnego nie jest dozwolone");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Przeniesienie do folderu źródłowego nie jest dozwolone");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Nie wolno tworzyć plików w folderze źródłowym");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Właściwości");
	    lang.put("tab.document.notes", "Notatki");
	    lang.put("tab.document.history", "Historia");
	    lang.put("tab.document.status.history", "Historia aktualizacji");
	    lang.put("tab.status.security.role", "Aktualizacja uprawnień ról");
	    lang.put("tab.status.security.user", "Aktualizacja uprawnień użytkowników");
	    lang.put("tab.document.status.group.properties", "Aktualizacja grup właściwości");
	    lang.put("tab.document.status.set.keywords", "Tworzenie słów kluczowych");
	    lang.put("tab.document.status.get.version.history.size", "Odświeżanie historii dokumentu");
	    lang.put("tab.document.status.purge.version.history", "Kompresja historii dokumentu");
	    lang.put("tab.document.status.restore.version", "Przywracanie historii dokumentu");
	    lang.put("tab.document.security", "Uprawnienia");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Właściwości");
	    lang.put("tab.folder.security", "Uprawnienia");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Pulpit");
	    lang.put("tab.workspace.search", "Szukaj");
	    lang.put("tab.workspace.dashboard", "Strona domowa");
	    lang.put("tab.workspace.administration", "Administracja");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Nazwa");
	    lang.put("document.folder", "Folder");
	    lang.put("document.size", "Wielkość");
	    lang.put("document.created", "Utworzono");
	    lang.put("document.lastmodified", "ZModyfikowano");
	    lang.put("document.mimetype", "Typ dokumentu");
	    lang.put("document.keywords", "Słowa kluczowe");
	    lang.put("document.by", "przez");
	    lang.put("document.status", "Status");
	    lang.put("document.status.checkout", "Edytowany przez");
	    lang.put("document.status.locked", "Zablokowany przez");
	    lang.put("document.status.normal", "Dostępny");
	    lang.put("document.subscribed", "Zasubskrybowany");
	    lang.put("document.subscribed.yes", "Tak");
	    lang.put("document.subscribed.no", "Nie");
	    lang.put("document.history.size", "Wielkość historii");
	    lang.put("document.subscribed.users", "Subskrybujący użytkownicy");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Dodaj notatkę");
	    lang.put("document.keywords.cloud", "Keywords cloud");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Nazwa");
	    lang.put("folder.parent", "Nadrzędny");
	    lang.put("folder.created", "Utworzono");
	    lang.put("folder.by", "przez");
	    lang.put("folder.subscribed", "Zasubskrybowany");
	    lang.put("folder.subscribed.yes", "Tak");
	    lang.put("folder.subscribed.no", "Nie");
	    lang.put("folder.subscribed.users", "Subskrybujący użytkownicy");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Wersja");
	    lang.put("version.created", "Data");
	    lang.put("version.author", "Autor");
	    lang.put("version.size", "Wielkość");
	    lang.put("version.purge.document", "Wielkość skompresowanej historii");
	    lang.put("version.comment", "Komentarz");
	    
	    // Security
	    lang.put("security.label", "Uprawnienia");
	    lang.put("security.group.name", "Grupa");
	    lang.put("security.group.permission.read", "Odczyt");
	    lang.put("security.group.permission.write", "Zapis");
	    lang.put("security.user.name", "Użytkownik");
	    lang.put("security.user.permission.read", "Odczyt");
	    lang.put("security.user.permission.write", "Zapis");
	    lang.put("security.users", "Użytkownik");
	    lang.put("security.groups", "Grupy");
	    lang.put("security.recursive", "Dziedziczenie uprawnień");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "Wystąpił błąd");
	    lang.put("error.invocation", "Błąd komunikacji z serwerem");
	    
	    // About
	    lang.put("about.label", "O OpenKM");
	    lang.put("about.loading", "Ładowanie ...");
	    
	    // Logout
	    lang.put("logout.label", "Wyjście");
	    lang.put("logout.closed", "OpenKM został poprawnie zamknięty.");
	    lang.put("logout.logout", "Wylogowywanie trwa, proszę czekać");
	    
	    // Confirm
	    lang.put("confirm.label", "Potwierdzenie");
	    lang.put("confirm.delete.folder", "Czy na pewno chcesz usunąć folder?");
	    lang.put("confirm.delete.document", "Czy na pewno chcesz usunąć dokument?");
	    lang.put("confirm.delete.trash", "Czy na pewno chcesz opróżnić śmietnik?");
	    lang.put("confirm.purge.folder", "Czy na pewno chcesz nieodwracalnie usunąć folder?");
	    lang.put("confirm.purge.document", "Czy na pewno chcesz nieodwracalnie usunąć dokument?");
	    lang.put("confirm.delete.propety.group", "Czy na pewno chcesz usunąć grupę właściwości?");
	    lang.put("confirm.purge.version.history.document", "Czy na pewno chcesz usunąć historię dokumentu?");
	    lang.put("confirm.purge.restore.document", "Czy na pewno chcesz przywrócić dokument w tej wersji?");
	    lang.put("confirm.set.default.home", "Czy na pewno chcesz ustawić domyślny folder domowy?");
	    lang.put("confirm.delete.saved.search", "Czy na pewno chcesz usunąć zapisane wyniki wyszukiwania?");
	    lang.put("confirm.delete.user.news", "Czy na pewno chcesz usunąć nowości?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
	    
	    // Search inputs
	    lang.put("search.context", "Kontext");
	    lang.put("search.content", "Kontent");
	    lang.put("search.name", "Nazwa");
	    lang.put("search.keywords", "Słowa kluczowe");
	    lang.put("search.folder", "Folder");
	    lang.put("search.results", "Wyniki");
	    lang.put("search.to", "dla");
	    lang.put("search.page.results", "Stronnicowane wyniki");
	    lang.put("search.add.property.group", "Dodaj grupę właściwości");
	    lang.put("search.mimetype", "Typ");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Wyszukiwanie zaawansowane");
	    lang.put("search.user", "Użytkownik");
	    lang.put("search.date.and", "i");
	    lang.put("search.date.range", "Data pomiędzy");
	    lang.put("search.save.as.news", "Zapisz nowości");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
	    
	    // Search results
	    lang.put("search.result.name", "Nazwa");
	    lang.put("search.result.score", "Związaek");
	    lang.put("search.result.size", "Wielkość");
	    lang.put("search.result.date.update", "Data aktualizacji");
	    lang.put("search.result.author", "Autor");
	    lang.put("search.result.version", "Wersja");
	    lang.put("search.result.path", "Ścieżka");
	    lang.put("search.result.menu.download", "Pobierz");
	    lang.put("search.result.menu.go.folder", "Idź do folderu");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Aktualizacja wyszukiwania");
	    lang.put("search.result.status.runsearch", "Aktualizacja zapisanych wyników wyszukiwania");
	    
	    // Search saved
	    lang.put("search.saved.run", "Uruchom");
	    lang.put("search.saved.delete", "Usuń");
	    lang.put("search.saved.status.getsearchs", "Odświeżanie zapisanych wyników wyszkiwania");
	    lang.put("search.saved.status.savesearch", "Aktualizacja zapisanych wyników wyszkiwania");
	    lang.put("search.saved.status.deletesearch", "Usuwanie zapisanych wyników wyszkiwania");
	    lang.put("search.saved.status.getusernews", "Odświeżanie nowości");
	    
	    // Button
	    lang.put("button.close", "Zamknij");
	    lang.put("button.logout", "Wyloguj");
	    lang.put("button.update", "Aktualizuj");
	    lang.put("button.cancel", "Anuluj");
	    lang.put("button.accept", "Akcpetuj");
	    lang.put("button.restore", "Przywróć");
	    lang.put("button.move", "Przenieś");
	    lang.put("button.change", "Zmień");
	    lang.put("button.search", "Szukaj");
	    lang.put("button.save.search", "Zapisz wyniki wyszukiwania");
	    lang.put("button.view", "Widok");
	    lang.put("button.clean", "Opróżnij");
	    lang.put("button.add", "Dodaj");
	    lang.put("button.delete", "Usuń");
	    lang.put("button.copy", "Kopiuj");
	    lang.put("button.create", "Utwórz");
	    lang.put("button.show", "Pokaż");
	    lang.put("button.memory", "Aktualizuj");
	    lang.put("button.copy.clipboard", "Skopiuj do schowka");	
	    lang.put("button.start", "Start");
	    lang.put("button.select", "Select");
	    
	    // Group
	    lang.put("group.label", "Dodaj grupę właściwości");
	    lang.put("group.group", "Grupa");
	    lang.put("group.property.group", "Właściwość");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Dodaj zakładkę");
	    lang.put("bookmark.name", "Nazwa");
	    lang.put("bookmark.edit.label", "Edycja zakładek");
	    lang.put("bookmark.path", "Ścieżka");
	    lang.put("bookmark.type", "Typ");
	    lang.put("bookmark.type.document", "Dokument");
	    lang.put("bookmark.type.folder", "Folder");
	    
	    // Notify
	    lang.put("notify.label", "Wysyłanie linka do dokumentu");
	    
	    // Status
	    lang.put("status.document.copied", "Dokument oznaczony do skopiowania");
	    lang.put("status.document.cut", "Dokument oznaczony do wycięcia");
	    lang.put("status.folder.copied", "Folder oznaczony do skopiowania");
	    lang.put("status.folder.cut", "Folder oznaczony do wycięcia");
	    lang.put("status.keep.alive.error", "Wykryto zerwanie połączenia z serwerem (mechanizm utrzymywania połączenia)");
	    lang.put("status.debug.enabled", "Debug włączony");
	    lang.put("status.debug.disabled", "Debug wyłączony");
	    lang.put("status.network.error.detected", "Network error detected");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Niedziela");
	    lang.put("calendar.day.monday", "Poniedziałek");
	    lang.put("calendar.day.tuesday", "Wtorek");
	    lang.put("calendar.day.wednesday", "Środa");
	    lang.put("calendar.day.thursday", "Czwartek");
	    lang.put("calendar.day.friday", "Piątek");
	    lang.put("calendar.day.saturday", "Sobota");
	    lang.put("calendar.month.january", "Styczeń");
	    lang.put("calendar.month.february", "Luty");
	    lang.put("calendar.month.march", "Marzec");
	    lang.put("calendar.month.april", "Kwiecień");
	    lang.put("calendar.month.may", "Maj");
	    lang.put("calendar.month.june", "Czerwiec");
	    lang.put("calendar.month.july", "Lipiec");
	    lang.put("calendar.month.august", "Sierpień");
	    lang.put("calendar.month.september", "Wrzesień");
	    lang.put("calendar.month.october", "Październik");
	    lang.put("calendar.month.november", "Listopad");
	    lang.put("calendar.month.december", "Grudzień");
	    
	    // Media player
	    lang.put("media.player.label", "Odtwarzacz");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Przeglądarka obraów");
	    lang.put("image.viewer.zoom.in", "Przybliż");
	    lang.put("image.viewer.zoom.out", "Oddal");
	    
	    // Debug console
	    lang.put("debug.console.label", "Konsola debug");
	    lang.put("debug.enable.disable", "Naciśnij CTRL+Z aby aktywować trzyb debug");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Główne");
	    lang.put("dashboard.tab.news", "Nowości");
	    lang.put("dashboard.tab.user", "Użytkownik");
	    lang.put("dashboard.tab.workflow", "Obieg dokumentu");
	    lang.put("dashboard.tab.mail", "Mail");
	    lang.put("dashboard.tab.keymap", "Keyword map");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Nowe");
	    lang.put("dashboard.user.locked.documents", "Zablokowane dokumenty");
	    lang.put("dashboard.user.checkout.documents", "Wyciągnięte i zablokowane");
	    lang.put("dashboard.user.last.modified.documents", "Ostatnio zmodyfikowane");
	    lang.put("dashboard.user.last.downloaded.documents", "Last downloaded documents");
	    lang.put("dashboard.user.subscribed.documents", "Zasybskrybowane dokumenty");
	    lang.put("dashboard.user.subscribed.folders", "Zasubskrybowane foldery");
	    lang.put("dashboard.user.last.uploaded.documents", "Ostatnio załadowane dokumenty");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Najczęściej przeglądane w ostatnim tygodniu");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Najczęściej przeglądane w ostatnim miesiącu");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Najczęściej modyfikowane w ostatnim tygodniu");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Najczęściej modyfikowane w ostatnim miesiącu");
	    lang.put("dashboard.general.last.uploaded.documents", "Ostatnio załadowane dokumenty");
	    lang.put("dashboard.workflow.pending.tasks", "Zadania oczekujące");
	    lang.put("dashboard.workflow.task", "Zadanie");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Nazwa");
	    lang.put("dashboard.workflow.task.created", "Data utworzenia");
	    lang.put("dashboard.workflow.task.start", "Data aktywacji");
	    lang.put("dashboard.workflow.task.duedate", "Termin zakończenia");
	    lang.put("dashboard.workflow.task.end", "Data zakończenia");
	    lang.put("dashboard.workflow.task.description", "Opis");
	    lang.put("dashboard.workflow.task.process.instance", "Proces");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Wersja");
	    lang.put("dashboard.workflow.task.process.name", "Nazwa");
	    lang.put("dashboard.workflow.task.process.description", "Opis");
	    lang.put("dashboard.workflow.task.process.data", "Dane");
	    lang.put("dashboard.workflow.task.process.definition", "Definicja procesu");
	    lang.put("dashboard.workflow.task.process.path", "Ścieżka");
	    lang.put("dashboard.refreshing", "Odśwież");
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
	    lang.put("workflow.label", "Uruchom obieg dokumentu");
	    
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
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Brak dostępu do dokumentu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Nie znaleziono dokumentu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Dokument już istnieje");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Dokument jest zablokowany");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Wymagane odblokowanie dokumentu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Wewnętrzny błąd repozytorium");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Wewnętrzny błąd aplikacji");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Nie odnaleziono ścieżki do dokumentu");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Brak dostępu do folderu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Nie znaleziono folderu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Folder już istnieje");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Wewnętrzny błąd repozytorium");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Wewnętrzny błąd aplikacji");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Nie odnaleziono ścieżki do folderu");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Brak dostępu do elementu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Nie znaleziono elementu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Wewnętrzny błąd repozytorium");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Wewnętrzny błąd aplikacji");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Nie znaleziono dokumentu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Wewnętrzny błąd repozytorium");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Nieobsługiwany format pliku");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Dokument już istnieje");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Przekroczono maksymalną wielkość dokumentu");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Sesja została zamknięta");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Wewnętrzny błąd wykonania zapuytania");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Nazwa zapamiętanych wyników wyszukiwania musi być unikalna");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Nazwa zakładki musi być unikalna");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Sesja została przerwana");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Wewnętrzny błąd repozytorium");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Wewnętrzny błąd repozytorium");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Błąd wejścia/wyjścia");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Wewnętrzny błąd repozytorium");
	  }
}
