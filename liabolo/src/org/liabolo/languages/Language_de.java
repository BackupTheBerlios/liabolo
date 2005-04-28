/*
 * Created on May 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.liabolo.languages;

/**
 * @author lexi
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import java.util.ListResourceBundle;

public class Language_de extends ListResourceBundle {



		public Object [][] getContents(){
				return contents;
		}
		private Object[][] contents = {

				//Common
				{"--", ""},
				{"close", "Schliessen"},
				{"content", "Inhalt"},
				{"form_close","Formular schliessen?"},
				{"close_wnd","Fenster schliessen"},
				{"local_db_error","Verbindung zur lokalen Datenbank fehlgeschlagen!"},

				{"no_entry_selection","Es wurden keine Eintr\u00e4ge zum Anzeigen ausgew\u00e4hlt!"},
				{"no_selection","Es wurden keine Eintr\u00e4ge ausgew\u00e4hlt!"},

				{"connected", "Verbunden..."},


				// LoginWnd.java
				{"liabolo", "Liab\u2035o\u2032lo - Die teuflisch gute Literaturverwaltung"},
				{"ok", "OK"},
				{"cancel", "Abbrechen"},
				{"login", "Login"},
				{"user", "Benutzer"},
				{"password", "Passwort"},
				{"please_log", "Bitte einloggen"},
				{"leave", "Wirklich beenden?"},
				{"leaving", "Anwendung beenden"},
				{"error", "Fehler"},

				{"input_not_correct", "Angaben nicht vollst\u00e4ndig/korrekt !"},
				{"edit_opened", "Bearbeitungsformular bereits ge\u00f6ffnet. \u00c4nderungen verwerfen und ausgew\u00e4hlten Datensatz bearbeiten?"},
				{"confirmation", "Best\u00e4tigung"},
				{"entry_success", "Eintr\u00e4ge erfolgreich gespeichert!"},


				// Menu.java
				{"file", "Datei"},
				{"connect", "Verbinden/Trennen"},
				{"backup", "Backup"},
				{"restore", "Wiederherstellen"},
				{"print", "Drucken"},
				{"exit", "Beenden"},
				{"statistic","Statistik"},
				
				
				{"clipboard","Zwischenablage"},
				{"clear_cb","Leeren"},
				{"edited_cb","Editierte Eintr\u00e4ge"},

				{"metadata", "Metadaten"},
				{"new", "Neu"},
				{"book", "Buch"},
				{"article", "Artikel"},
				{"audio", "Audio"},

				{"import", "Importieren"},

				{"search", "Suchen"},
				//{"branch", "Fachgebiet"},
				{"edit", "Bearbeiten"},

				//{"location", "Standort"},
				{"ind_list", "Indiv. Listen"},
				{"environment", "Umgebung"},

				{"new_branch_menu",  "Fachgebiet hinzuf\u00fcgen"},

				{"edit_branch_menu", "Fachgebiete bearbeiten"},

				{"new_location_menu", "Standort hinzuf\u00fcgen"},

				{"edit_location_menu", "Standorte bearbeiten"},

				{"new_list_menu", "Indiv. Liste hinzuf\u00fcgen"},

				{"edit_list_menu", "Indiv. Listen bearbeiten"},


				{"connection", "Verbindung"},
				{"sync", "Synchronisiere mit.."},
				{"conn_with", "Verbinde mit.."},

				{"extras", "Extras"},
				{"form_editor", "Formulareditor"},
				{"prefs", "Einstellungen"},

				{"help", "Hilfe"},
				{"help_content", "Inhalt"},
				{"info", "Info"},


				{"add_book", "Neues Buch hinzuf\u00fcgen"},

				{"switch_to_book", "Wollen Sie wirklich zum Buch-Formular wechseln?"},
				{"switching", "Formular wechslen"},

				{"add_article", "Neuen Artikel hinzuf\u00fcgen"},

				{"switch_to_article", "Wollen Sie wirklich zum Artikel-Formular wechseln?"},

				{"add_audio", "Neue Audio-Datei hinzuf\u00fcgen"},

				{"switch_to_audio", "Wollen Sie wirklich zum Audio-Formular wechseln?"},

				{"exit_liabolo", "Liab\u2035o\u2032lo beenden"},
				{"exit_liabolo2", "Wollen Sie Liab\u2035o\u2032lo wirklich beenden?"},

				// MetadataForm.java

				{"branch_not_selected", "Bitte w\u00e4hlen Sie einen g\u00fcltigen Fachbereich aus!"},
				{"location_not_selected", "Bitte w\u00e4hlen Sie einen g\u00fcltigen Standort aus!"},

				{"book_array", new String[]{"Titel","Autor","Fach", "Beschreibung", "Verlag",
									"Mitwirkende","Datum","Format", "Quelle",
									"Sprache", "Relation","Coverage", "Rechte"}},
				{"article_array", new String[] {"Titel","Fach","Beschreibung", "Verlag",
									"Datum","Format",  "Sprache", "Relation",
									"Coverage", "Rechte"}},

				{"audio_array", new String[] {"Titel","Autor","Fach", "Beschreibung",
									"Verlag", "Datum", "Format", "Quelle",
							 		"Sprache", "Relation","Coverage", "Rechte"}},


				{"select_location", "Standort ausw\u00e4hlen"},

				{"step_one", "Schritt 1 von 2"},
				{"step_two", "Schritt 2 von 2"},
				{"next", "weiter"},
				{"proceed_step2", "Weiter mit Schritt 2"},

				{"warning1", "Achtung: Gew\u00e4hltes Fachgebiet kann sp\u00e4ter nicht mehr ge\u00e4ndert werden!"},
				{"select_branch", "Fachgebiet ausw\u00e4hlen!"},
				{"back", "zur\u00fcck"},
				{"return_step_one", "Zur\u00fcck zu Schritt 1"},

				{"save", "Sichern"},

				{"save_entry", "Eintr\u00e4ge sichern"},

				{"close_wnd", "Fenster schliessen"},
				{"continue","Wirklich schliessen?"},
				{"add_new_mdset","Neuen Datensatz anlegen"},

				{"add_new_md","Neue Metadaten hinzuf\u00fcgen"},
				{"no_types_av","Es sind keine Medientypen definiert! M\u00f6chten Sie jetzt einen Medientyp anlegen?"},
				{"no_book_type_av","Es ist kein Buchtyp definiert! M\u00f6chten Sie diesen jetzt anlegen?"},
			    {"check_for_add_to_list","Markieren sie bitte das K\u00e4stchen, wenn sie den Datensatz den Individuallisten zuordnen wollen"},




				// BackupForm.java
				{"restore_failure", "Die Wiederherstellung der Datenbank ist fehlgeschlagen!"},
				{"restore_success", "Die Datenbank wurde erfolgreich wiederhergestellt"},
				{"backup_restore", "Sicherung/Wiederherstellung"},
				{"backup_success", "Die Datenbank wurde erfolgreich gesichert"},
				{"backup_failure", "Die Sicherung der Datenbank ist fehlgeschlagen!"},

				//BranchForm.java

				{"add_branch", "Neues Fachgebiet hinzuf\u00fcgen"},
				{"abreviation", "K\u00fcrzel"},

				{"save_branch", "Fachgebiet sichern"},
				{"edit_branch", "Fachgebiet bearbeiten"},

				//EditBranch.java
				//{"edit_branch_columns", new String[] {"#", "Branch", "Abreviation"}},

				//BrowseBranch.java

				{"edit_branch_columns", new String[] {"#", "Fachbereich", "K\u00fcrzel"}},
				{"browse_branches", "Fachgebiete bl\u00e4ttern"},

				{"no_branches_avail", "Keine Fachgebiete gefunden"},
				{"list_branches", "Fachgebiete auflisten"},

				{"selected_branch_entries","Eintr\u00e4ge in ausgew\u00e4hlten Fachbereichen"},
				{"branch_content", "Metadatens\u00e4tze der ausgew\u00e4hlten Fachbereiche anzeigen"},




				//ConnectionForm.java

				{"add_connection", "Neue Verbindung hinzuf\u00fcgen"},

				{"name", "Name"},
				{"db_uri", "DB-URI"},
				{"save_connection", "Verbindung sichern"},
				{"edit_connection", "Verbindung bearbeiten"},
				{"driver","Treiber"},

				//BrowseConnection.java

				{"browse_connections", "Verbindungen bl\u00e4ttern"},

				{"list_connections", "Verbindungen anzeigen"},
				{"no_connections_avail", "Keine Verbindungen vorhanden !"},
				{"activ", "Aktiv"},

				//EditMetadataForm.java
				{"edit_meta", "Metadaten bearbeiten"},

				{"save_changes", "\u00c4nderungen sichern"},


				//ListForm.java

				{"add_list", "Neue Individualliste hinzuf\u00fcgen"},

				{"save_list", "Individualliste sichern"},
				{"description", "Beschreibung"},
				{"edit_list", "Individualliste bearbeiten"},

				//BrowseList.java

				{"browse_lists", "Individuallisten bl\u00e4ttern"},

				{"list_lists", "Individuallisten anzeigen"},
				{"no_lists_avail", "Keine Individuallisten vorhanden !"},
				{"list_content", "Inhalt der Liste"},

				{"no_export_selection","Es wurden keine Eintr\u00e4ge zum Exportieren ausgew\u00e4hlt!"},
				{"selected_list_entries","Eintr\u00e4ge in ausgew\u00e4hlten Individuallisten anzeigen"},

				{"export","Exportieren"},

				//AddToList.java

				{"add_to_list", "Eintr\u00e4ge zu Individualliste(n) hinzuf\u00fcgen"},

				{"av_lists", "Vorhandene Individuallisten"},

				{"add_list_tip", "W\u00e4hlen Sie bitte die gew\u00fcnschte(n) Individualliste(n):"},

				{"entry_error","Es sind Fehler aufgetreten!"},


				//LocationForm.java

				{"add_location", "Neuen Standort hinzuf\u00fcgen"},

				{"enter_location", "Standort eingeben"},
				{"save_location", "Standort sichern"},
				{"edit_location", "Standort bearbeiten"},

				//BrowseLocation.java
				{"list_locations", "Standort auflisten"},
				{"no_locations_avail", "Keine Standorte vorhanden !"},

				{"browse_locations", "Standorte bl\u00e4ttern"},
				{"selected_location_entries","Eintr\u00e4ge in ausgew\u00e4hlten Standorten"},
				{"location_content", "Metadatens\u00e4tze der ausgew\u00e4hlten Standorte anzeigen"},


				//PrintPreview.java

				{"print_md_sets", "Metadatens\u00e4tze betrachten/drucken"},
				{"selected_sets", "Ausgew\u00e4hlte Datens\u00e4tze "},



				//BrowseMetadataResults.java
				{"checkout", "->Zwischenablage"},
				{"referrence", "Referenz"},
				{"column_array", new String[] {"#","Autor", "Titel", "Fachgebiet", "Datum", "Typ"}},
				{"search_results", "Suchergebnisse"},
				{"search_for", "Suche nach"},
				{"no_matches", "Keine Ergebnisse gefunden"},
				{"no_entries", "Keine Eintr\u00e4ge gefunden"},
				{"results", "Ergebnisse"},
				{"entries_clipboard", "Eintr\u00e4ge aus dem lokalen Repository"},
				{"select_all", "Alle markieren"},
				{"show_edited_only", "Nur editierte Eintr\u00e4ge zeigen"},
				{"selected", "markierte"},
				{"delete", "Entfernen"},
				{"creator", "Autor"},
				{"author", "Autor"},
				{"title", "Titel"},
				{"date", "Datum"},
				{"type", "Typ"},

				{"update_cb", "Update"},
				{"commit_cb", "Commit"},
				{"warning2", "Bearbeitungsformular bereits ge\u00f6ffnet. \u00c4nderungen verwerfen und ausgew\u00e4hlten Metadatensatz bearbeiten?"},

				{"tolist","Zu Liste"},
				{"warning","Warnung"},
				{"action_success","Aktion erfolgreich durchgef\u00fchrt"},
			

				{"deleting","Eintr\u00e4ge l\u00f6schen"},
				{"delete_items","Sollen die markierten Eintr\u00e4ge aus der Datenbank entfernt werden?"},
				{"no_delete_selection","Es wurden keine Eintr\u00e4ge zum L\u00f6schen ausgew\u00e4hlt!"},
				{"no_edit_selection","Es wurde kein Eintrag zum Bearbeiten ausgew\u00e4hlt!"},
				{"no_tolist_selection","Es wurden keine Eintr\u00e4ge zum Hinzuf\u00fcgen ausgew\u00e4hlt!"},
				{"no_refer_selection","Es wurden keine Eintr\u00e4ge zum Referenzieren ausgew\u00e4hlt!"},
				{"addMD_not_opened","Das \"Metadata hinzuf\u00fcgen\" Formular ist nicht ge\u00f6ffnet! Wollen sie jetzt \u00f6ffnen ?"},
				{"selected_branches", "ausgew\u00e4hlte Fachbereiche"},

				{"no_lists_av", "Keine Individuallisten vorhanden!"},
				{"add_new?", "Wollen Sie neue anlegen!"},
				{"signature", "Signatur"},
				{"print_preview", "Druckvorschau"},

				{"no_preview_selection","Es wurden keine Eintr\u00e4ge zur (Druck-)Vorschau ausgew\u00e4hlt!"},
				{"md_edit_opened","Bearbeitungsformular bereits ge\u00f6ffnet! \u00c4nderungen verwerfen und neue Auswahl bearbeiten ?"},
				{"md_print_opened","Druckvorschau bereits ge\u00f6ffnet! Bestehende Zusammenstellung verwerfen und neue Auswahl anzeigen ?"},



				{"rt_tt_check","Doppelklick f\u00fcr die (De-)selektion aller Eintr\u00e4ge"},

				{"rt_tt_author","Doppelklick f\u00fcr die alphabetische Sortierung nach Autor"},
				{"rt_tt_subject","Doppelklicken um nach Schlagworten alphabetisch zu sortieren"},
				{"rt_tt_description","Doppelklicken um nach Beschreibung alphabetisch zu sortieren"},
				{"rt_tt_publisher","Doppelklicken um nach Verlag alphabetisch zu sortieren"},
				{"rt_tt_contributors","Doppelklicken um nach Mitwirkenden alphabetisch zu sortieren"},
				{"rt_tt_type","Doppelklicken um nach Mediatypen alphabetisch zu sortieren"},
				{"rt_tt_date","Doppelklicken um nach Datum numerisch zu sortieren"},
				{"rt_tt_source","Doppelklicken um nach Quelle alphabetisch zu sortieren"},
				{"rt_tt_relation","Doppelklicken um nach Beziehung alphabetisch zu sortieren"},
				{"rt_tt_language","Doppelklicken um nach Sprache alphabetisch zu sortieren"},
				{"rt_tt_rights","Doppelklicken um nach Rechten alphabetisch zu sortieren"},
				{"rt_tt_title","Doppelklicken um nach Titel alphabetisch zu sortieren"},
				{"rt_tt_branch","Doppelklicken um nach Fachbereichen alphabetisch zu sortieren"},
				{"rt_tt_coverage","Doppelklicken um nach Standort alphabetisch zu sortieren"},
				{"rt_tt_identifier","Doppelklicken um nach ISBN/ISN Nummer numerisch zu sortieren"},
				{"rt_tt_signature","Doppelklicken um nach Signatur alphabetisch zu sortieren"},
				{"rt_tt_format","Doppelklicken um nach Format alphabetisch zu sortieren"},


				//	MetadataSearchForm.java
				{"creator", "Autor"},
				{"publisher", "Verlag"},
				{"description", "Beschreibung"},
				{"string", "Suchbegriff"},
				{"db_search", "Datenbanksuche"},
				{"submit", "Senden"},
				{"search_metadata", "Suche Metadaten"},

				{"browse", "Bl\u00e4ttern"},
				{"browse_branches", "Bl\u00e4ttern in Fachbereichen"},

				{"adv_search", "Profisuche"},
				{"x_path_search", "X-Path Suche"},

				{"no_branch_selection", "Es wurde kein Fachbereich zum Suchen ausgew\u00e4hlt!"},

				{"empty_string", "Bitte einen Suchbegriff eingeben !"},

				{"new_search_warning", "Suchergebnisse-Fenster ist bereits ge\u00f6ffnet! Alte Anzeige verwerfen und neue Suche starten ?"},

				{"publisher_array", new String[]{"Verlag, Ort","Ort"}},

				{"relation_array", new String[]{"zus\u00e4tzliche Angaben","Seiten","Ausgabe"}},

				{"source_array",new String[]{"Link","Quelle"}},
				{"identifier_array",new String[]{"ISBN","ISSN"}},
				{"magazine","Zeitschrift"},
				{"magazine_article","Zeitschriftenartikel"},
				{"online_article","Onlineartikel"},
				{"book_article","Buchartikel"},


				//OptionsForm.java
				{"options", "Optionen"},

				//ResultTableMode.java
				//{"column_array2", new String[] {"test", "Autor", "Titel", "Fachgebiet", "Datum", "Typ"}},

				// StatusBar.java

				{"connected_with_localDB", "Verbunden mit der lokalen Datenbank..."},

				// ImportForm.java
				{"excel", "Excel-Dateien"},
				{"add_excel", "Excel-Dateien importieren"},
				{"add_tip", "Eine Anleitung zum Excel-Import finden Sie in der Online-Hilfe"},
				{"import_column_array", new String[] {"Quelle", "Map", "Ziel", "!"}},

				{"import_tt1", "Zeigt Zuweisungskonflikte f\u00fcr Ziel"},
				{"import_tt2", "Zum Ausw\u00e4hlen anklicken"},
				{"import_items", "Datens\u00e4tze importieren"},

				{"savein", "Speichern in"},

				{"browse_entries", "Eintr\u00e4ge durchsuchen"},
				{"select_branch", "Fachbereich ausw\u00e4hlen"},

				{"is_collision", "Es sind Mapping-Konflikte vorhanden!"},

				{"items_added", "Datens\u00e4tze erfolgreich importiert"},

				{"next_item", "vor"},

				{"previous_item", "zur\u00fcck"},

				{"import_types", new String[] {"Buch", "Artikel", "Audio"}},
				{"saveas", "Speichern als"},
				{"file_error", "Importdatei ist fehlerhaft und/oder konnte nicht gelesen werden!"},

				// Dispatcher.java

				{"results_found", " Ergebnisse gefunden f\u00fcr Suchbegriff '"},


				// AddNewMetadata.java

				{"add_new_md", "Neuen Metadatensatz einf\u00fcgen"},
				{"add_new_md_tip", "W\u00e4hlen sie bitten den Medientyp"},

				{"av_mediatypes", "Medientypen"},


				//TreeForm
				{"The_library", "Die Bibliothek"},
				{"MyLib","Meine Bibliothek"},
				{"branches","Fachgebiete"},
				{"connections", "Verbindungen"},
				{"locations","Standorte"},
				{"MyLib","Meine Bibliothek"},

				//PreferencesDialog

				{"Language","Sprache w\u00e4hlen"},

				{"getBranchWarning","Warnungen anzeigen"},
				{"getToolBarVisible","ToolBar anzeigen"},
				{"german","Deutsch"},
				{"english","Englisch"},

				{"option_changes","Die \u00c4nderungen werden erst nach dem Neustart g\u00fcltig"},
				{"languages_change","Die Sprach\u00e4nderung wird erst nach dem Neustart wirksam"},

				{"autom_connecten","Automatischer Verbindungaufbau"},
				{"show_warnings","Warndialoge ein-/ auschalten"},

				
				{"connect_to_all_server_aut","Automatischer Verbindungsaufbau beim Start"},
				{"show_confirmations","Best\u00e4tigungsdialoge ein-/ausschalten"},
				{"show_toolbar","Die Toolbar links ein-/ausblenden"},
				{"confirm_message","Best\u00e4tigungen anzeigen"},
				{"automatic_update","'Meine Bibliothek' automatisch aktualisieren"},
				{"automatic_update2","'Meine Bibliothek' wird nach jedem Hinzuf\u00c3\u00bcgen oder L\u00c3\u00b6schen von Metadaten automatisch aktualisiert"},

				//WaitDialog
				{"please_wait1","Bitte warten"},
				{"please_wait2","Bitte warten, die Daten werden geladen"},

				//Help.java
				{"liabolo_help","Liab\u2035o\u2032lo Hilfe"},
				{"index","Inhalt"},


                //Metadata descriptions
                {"title_desc","Name des Titels"},
                {"creator_desc","Autor des Werkes"},

                {"subject_desc","Schlagw\u00f6rter, die den Titel n\u00e4her spezifizieren"},

                {"description_desc", "Beschreibung des Werkes"},
                {"publisher_desc", "Herausgeber des Werkes"},
                {"contributors_desc", "Mitwirkende des Werkes"},
                {"date_desc", "Datum - meist Jahr - der Herausgebung"},
                {"type_desc", "Mediantyp, z.B. Buch, Aritkel, etc."},

                {"format_desc", "Format, z.B. Tontr\u00e4ger, Papier, etc."},

                {"identifier_desc", "ISBN Nummer"},
                {"source_desc", "Geben Sie hier die Auflage des Werkes ein"},
                {"language_desc", "Sprache"},
                {"relation_desc", "Beziehung zu anderen Werken, z.B. Sammelband, Duplikate"},
                {"coverage_desc", "Standort"},
                {"rights_desc", "Rechte am Dokument"},
                {"branch_desc", "Fachgebiet"},
                {"abstract_desc", "Einleitung"},
                {"signature_desc", "Signatur aus der DB -  wird automatisch generiert"},
                {"archivesource_desc", "Dadadudu"},

                {"title","Titel"},
                {"creator","Autor"},

                {"subject","Schlagw\u00f6rter"},

                {"description", "Beschreibung"},
                {"publisher", "Verlag"},
                {"contributors", "Mitwirkende"},
                {"date", "Datum"},
                {"type", "Mediatyp"},
                {"format", "Auflage"},
                {"identifier", "ISBN-Nummer"},
                {"source", "Quelle"},
                {"language", "Sprache"},

                {"relation", "Beziehung"},

                {"coverage", "Standort"},
                {"rights", "Rechte"},
                {"branch", "Fachgebiet"},
                {"abstract", "Einleitung"},
                {"signature", "Signatur"},
                {"archivesource", "Dadadudu"},
				{"list_assign", "Indv. Listen zuweisen"},
                

				//MediaTypesEditor
				{"new","Neu"},
				{"media_editor","Mediatypen editieren"},
				{"media_type_name","Name des Medientyps"},

				{"med_delete","Mediatyp l\u00f6schen"},
				{"no_name_selected","Bitte den Namen f\u00fcr das Mediatyp eingeben"},
				{"delete_medium_quest","Das Mediatyp wird gel\u00f6scht, fortfahren?"},

				{"add_media","Attribute ein- / ausblenden"},
				
				{"book","Buch"},
				{"magazine","Zeitschrift"},
				{"book-article","Buchartikel"},
				{"online-article","Onlineartikel"},
				{"magazine-article","Zeitschriftenartikel"},
			
				//ReferreceDialog


				{"add_referrence","Referenz hinzuf\u00fcgen"},
				{"insert_new","Neu hinzuf\u00fcgen"},



			//chooseConnectedLibs
			{"choose_connection","Verbindung ausw\u00e4hlen"},
			{"choose_connection_tip","W\u00e4hlen Sie bitte die gew\u00fcnschte Verbindung"},
			{"not_connected","Nicht verbunden!"},
			
			//ConflictManager
			{"upload_local","Lokale Version hochladen"},
			{"download_global","Globale Version herunterladen"},
			{"upload_local_tt","Lokale \u00c4nderungen hochladen und globale \u00c4nderungen \u00fcberschreiben"},
			{"download_global_tt","Globale Version herunterladen und lokale \u00fcberschreiben"},

		};
}
