/*
 * Created on May 27, 2004
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

import java.util.*;

public class Language_en extends ListResourceBundle {

	public Object [][] getContents(){
					return contents;
				}
				private Object[][] contents = {

					//Common
					{"--", ""},
					{"close", "Close"},
					{"content", "Content"},
					{"form_close","Formular schliessen?"},
					{"close_wnd","Fenster schliessen"},
					{"local_db_error","EN-Verbindung zur lokalen Datenbank fehlgeschlagen!"},
					{"no_entry_selection","No entries selected!"},
					{"connected", "Connected..."},

					// login window (LoginWnd.java)
					{"liabolo", "Liab'o'lo - A library tool hot as hell" },
					{"ok", "Ok"},
					{"cancel", "Cancel"},
					{"login", "Login"},
					{"user", "User"},
					{"password", "Password"},
					{"please_log", "Please login"},
					{"leave", "Text f�r Verlassen"},
					{"leaving", "Text f�r Verlassen"},
					{"error", "Error"},
					{"input_not_correct", "Data incomplete or incorrect !"},
					{"edit_opened", "Edit Form already in use. Discard changes and edit chosen dataset?"},

					// menues (Menu.java)
					{"file", "File"},
					{"backup", "Backup"},
					{"restore", "Restore"},
					{"connect", "(Dis-)Connect"},

					{"print", "Print"},
					{"exit", "Exit"},
					{"statistic","Statistic"},
					
					{"clipboard","Clipboard"},
					{"clear_cb","Clear"},
					{"edited_cb","Insert edited"},

					
					{"workspace","Clipboard"},
					{"clear_ws","Empty"},

					{"metadata", "Metadata"},
					{"new", "New"},
					{"book", "Book"},
					{"article", "Article"},
					{"audio", "Audio"},

					{"import", "Import"},

					{"search", "Search"},
					//{"branch", "Branch"},
					{"edit", "Edit"},
					
					
					{"selected_sets", "Selected data sets "},

					//{"location", "Location"},
					{"ind_list", "Indiv. Lists"},
					{"environment", "Environment"},
					{"new_branch_menu",  "Fachgebiet hinzuf�gen"},
					{"edit_branch_menu", "Fachgebiete bearbeiten"},
					{"new_location_menu", "Standort hinzuf�gen"},
					{"edit_location_menu", "Standorte bearbeiten"},
					{"new_list_menu", "Indiv. Liste hinzuf�gen"},
					{"edit_list_menu", "Indiv. Listen bearbeiten"},

					{"connection", "Connection"},
					{"sync", "Synchronise with.."},
					{"conn_with", "Connect with.."},

					{"extras", "Extras"},
					{"form_editor", "Formular Editor"},
					{"prefs", "Preferences"},

					{"help", "Help"},
					{"help_content", "Help Contents"},
					{"info", "Info"},

					{"add_book", "Add new book"},
					{"switch_to_book", "Really switch to bool formular?"},
					{"switching", "Switching formular"},
					{"add_article", "Add new article"},
					{"switch_to_article", "Really switch to article formular?"},
					{"add_audio", "Add new audio file"},
					{"switch_to_audio", "Really switch to audio formular?"},

					{"exit_liabolo", "Exit application "},
					{"exit_liabolo2", "Realy exit Liabolo?"},


					// metadata form (MetadataForm.java)
					{"branch_not_selected", "EN-Bitte w�hlen Sie einen g�ltigen Fachbereich aus!"},
					{"location_not_selected", "EN-Bitte w�hlen Sie einen g�ltigen Standort aus!"},
					{"book_array", new String[]{"Title","Author","Subject", "Description", "Publisher",
									"Contributors","Date", "Format", "Source",
									"Language", "Relation","Coverage", "Rights"}},
					{"article_array", new String[] {"Title","Subject","Description", "Publisher",
									"Date", "Format", "Language", "Relation",
									"Coverage", "Rights"}},
					{"audio_array", new String[] {"Title","Author","Subject", "Description",
									"Publisher", "Date", "Format","Source",
									 "Language", "Relation","Coverage", "Rights"}},
					{"select_location", "Select location"},
					{"step_one", "Step 1 of 2"},
					{"step_two", "Step 2 of 2"},
					{"next", "Next"},
					{"proceed_step2", "Go to Step 2"},
					{"warning1", "Warning: Selected branch cannot be edited afterwards!"},
					{"select_branch", "Select branch!"},
					{"back", "Back"},
					{"return_step_one", "Back to Step 1"},
					{"save", "Save"},
					{"save_entry", "Save entries"},
					{"close_wnd", "Close window"},
					{"continue","really close window?"},
					{"add_new_mdset","Add new metadata set"},
					{"add_new_md","Add new metadata"},
					{"no_types_av","Es sind keine Mediatypen definiert! M�chten Sie jetzt einen Mediatyp anlegen?"},
					{"no_book_type_av","Es ist kein Buchtyp definiert! M�chten Sie diesen jetzt anlegen?"},
					{"check_for_add_to_list","Select the box to assign the data set to individual list!"},


					// BackupForm.java
					//{"backup", "Backup"},
					//{"restore", "Restore"},
					{"backup_restore", "Backup/Restore"},

					//BranchForm.java
					{"add_branch", "Add branch"},
					{"abreviation", "Abreviation"},
					{"save_branch", "Save branch"},
					{"edit_branch", "Edit branch"},

					//EditBranch.java
					{"edit_branch_columns", new String[] {"#", "Branch", "Abreviation"}},

//					BrowseBranch.java

					{"browse_branches", "Browse branches"},
					{"no_branches_avail", "No branches available!"},
					{"list_branches", "List branches"},
					{"selected_branch_entries","Entries in selected branches"},
					{"branch_content", "Metadatens�tze der ausgew�hlten Fachbereiche anzeigen"},

					//ConnectionForm.java
					{"add_connection", "Add new connection"},
					{"name", "Name"},
					{"db_uri", "DB-URI"},
					{"save_connection", "Save connection"},
					{"edit_connection", "Edit connection"},

//					BrowseConnection.java
					{"browse_connections", "Browse connection"},
					{"list_connections", "List connections"},
					{"no_connections_avail", "No connections available!"},

					//EditMetadataForm.java
					{"edit_meta", "Edit metadata"},
					{"save_changes", "Save changes"},

					//ListForm.java
					{"add_list", "Add new list"},
					{"save_list", "Save list"},
					{"description", "Description"},
					{"edit_list", "Edit individual list"},

					//BrowseList.java
					{"browse_lists", "Browse individual lists"},
					{"list_lists", "Show individual lists"},
					{"no_lists_avail", "No individual lists available!"},
					{"list_content", "Contents"},
					{"no_export_selection","Es wurden keine Eintr�ge zum Exportieren ausgew�hlt!"},
					{"selected_list_entries","Entries in selected individual lists"},
					{"add_list_tip", "W�hlen Sie bitte die gew�nschte Individualliste:"},
					{"export","Export"},

					//AddToList.java
					{"add_to_list", "Add entries to individual lists"},
					{"av_lists", "Available individual lists"},
					{"add_list_tip", "Choose desired individual lists:"},
					{"entry_error","EN-Es sind Fehler aufgetretten!"},

					//LocationForm.java
					{"add_location", "Add new location"},
					{"enter_location", "Enter location"},
					{"save_location", "Save location"},

					//BrowseLocation.java
					{"list_locations", "List locations"},
					{"no_locations_avail", "No locations available!"},
					{"browse_locations", "Browse locations"},
					{"selected_location_entries","Entries in selected locations"},
					{"location_content", "Metadatens�tze der ausgew�hlten Standorte anzeigen"},

					//BrowseMetadataResults.java
					{"checkout", "->Clipboard"},
					{"referrence", "Referrence"},
					{"column_array", new String[] {"#","Author", "Title", "Branch", "Date", "Type"}},
					{"search_results", "Search results"},
					{"search_for", "Search for"},
					{"no_matches", "No matches found"},
					{"no_entries", "Keine entries gefunden"},
					{"results", "Results"},
					{"entries_clipboard", "Browse local repository"},
					{"select_all", "Select all"},
					{"show_edited_only", "Show edited sets only"},
					{"selected", "selected"},
					{"so_array", new String[] {"------", "add to list", "delete"}},
					{"choose_action", "Action for selected entries"},
					{"delete", "Delete"},
					{"author", "Author"},
					{"title", "Title"},
					{"date", "Date"},
					{"type", "Type"},
					
					{"update_cb", "Update"},
					{"commit_cb", "Commit"},
					
					{"tolist","To list"},
					{"warning","Attention"},
					{"deleting","Delete entries"},
					{"delete_items","Delete selected entries?"},
					{"no_delete_selection","No entries selected!"},
					{"no_edit_selection","No entry selected!"},
					{"no_refer_selection","EN-Es wurden keine Eintr�ge zum Referenzieren ausgew�hlt!"},
					{"addMD_not_opened","EN-Das \"Metadata hinzuf�gen\" Formular ist nicht ge�ffnet! Wollen sie jetzt �ffen ?"},
					{"no_tolist_selection","No entries selected!"},
					{"selected_branches", "Selected branches"},
					{"no_lists_av", "No individal lists available!"},
					{"add_new?", "Wollen Sie neue anlegen!"},
					{"signature", "Signature"},
					{"print_preview", "Vorschau"},
					{"no_tolist_selection","Es wurden keine Eintr�ge zur (Druck-)Vorschau ausgew�hlt!"},
					{"md_edit_opened","Edit form already opened! Discard changes and edit selected metadata set ?"},
					{"md_print_opened","Print form already opened! Discard preview and list new selection ?"},

					{"rt_tt_check","Doppelklicken um alle Eintr�ge zu selektieren oder deselektieren"},
					{"rt_tt_author","Doppelklicken um nach Autor alphabetisch zu sortieren"},
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

					{"warning2", "Edit form already opened! Discard changes and edit selected metadata set?"},

					//MetadataSearchForm.java
					{"creator", "Creator"},
					{"publisher", "Publisher"},
					{"description", "Description"},
					{"string", "Searchstring"},
					{"db_search", "Search in Database"},
					{"submit", "Submit"},
					{"search_metadata", "Search Metadata"},
					{"browse", "Browse"},
					{"browse_branches", "Browse in branches"},
					{"adv_search", "Advanced search"},
					{"x_path_search", "X-Path search"},
					{"no_branch_selection", "No branch selected!"},
					{"empty_string", "Please enter search string!"},
					{"new_search_warning", "Search window already opened! Close window and start new search?"},

					//OptionsForm.java
					{"options", "Options"},

					//ResultTableMode.java
					{"column_array2", new String[] {"test", "Author", "Title", "Branch", "Date", "Type"}},

					// StatusBar.java

					{"connected_with_localDB", "Connected with local Database.."},

					// ImportForm.java
					{"excel", "Excel files"},
					{"add_excel", "Import excel files"},
					{"add_tip", "Instructions for import coming soon..."},
					{"import_column_array", new String[] {"Source", "Conflict", "Target"}},
					{"import_tt1", "Indicates conflicts for target fields"},
					{"import_tt2", "Click for edit"},
					{"import_items", "Import data"},
					{"savein", "Save in"},
					{"browse_entries", "Browse entries"},
					{"select_branch", "Select branch"},
					{"is_collision", "Conflicts occured while mapping"},
					{"items_added", "Import successful!"},
					{"next_item", "next"},
					{"previous_item", "back"},
					{"import_types", new String[] {"Book", "Article", "Audio"}},
					{"saveas", "Save as"},
					{"file_error", "Wrong file format or file could not be read!"},


                    //Metadata descriptions
                    {"title_desc","ENName des Titels"},
                    {"creator_desc","ENAutor des Werkes"},
                    {"subject_desc","ENSchlagw�rter, die den Titel n�her spezifizieren"},
                    {"description_desc", "ENBeschreibung des Werkes"},
                    {"publisher_desc", "ENHerausgeber des Werkes"},
                    {"contributors_desc", "ENMitwirkende des Werkes"},
                    {"date_desc", "ENDatum - meist Jahr - der Herausgebung"},
                    {"type_desc", "ENMediantyp, z.B. Buch, Aritkel, etc."},
                    {"format_desc", "ENFormat, z.B. Tontr�ger, Papier, etc."},
                    {"identifier_desc", "ENISBN Nummer"},
                    {"source_desc", "ENUrsprung des Werkes, z.B. URI"},
                    {"language_desc", "ENSprache"},
                    {"relation_desc", "ENBeziehung zu anderen Werken, z.B. Sammelband, Duplikate"},
                    {"coverage_desc", "ENStandort"},
                    {"rights_desc", "ENRechte am Dokument"},
                    {"branch_desc", "ENFachgebiet"},
                    {"abstract_desc", "ENEinleitung"},
                    {"signature_desc", "ENSignatur aus der DB -  wird automatisch generiert"},
                    {"archivesource_desc", "ENDadadudu"},

                    {"title","ENTitel"},
                    {"creator","ENAutor"},
                    {"subject","ENSchlagw�rter"},
                    {"description", "ENBeschreibung"},
                    {"publisher", "ENHerausgeber"},
                    {"contributors", "ENMitwirkende"},
                    {"date", "ENDatum"},
                    {"type", "ENMediantyp"},
                    {"format", "ENFormat"},
                    {"identifier", "ENISBN-Nummer"},
                    {"source", "ENUrsprung"},
                    {"language", "ENSprache"},
                    {"relation", "ENBeziehung"},
                    {"coverage", "ENStandort"},
                    {"rights", "ENRechte"},
                    {"branch", "ENFachgebiet"},
                    {"abstract", "ENEinleitung"},
                    {"signature", "ENSignatur"},
                    {"archivesource", "ENDadadudu"},
					{"list_assign", "Assign indv. lists"},

					// Dispatcher.java
					{"results_found", " results found for search-pattern '"},

					// AddNewMetadata.java
					{"add_new_md", "Add new metadata"},
					{"add_new_md_tip", "Please choose the desired media type for metadata"},
					{"av_mediatypes", "Media types"},

					//TreeForm
					{"The_library", "The Library"},
					{"MyLib","My Library"},
					{"branches","Branches"},
					{"connections", "Connections"},
					{"locations","Locations"},

					//PreferencesDialog
					{"Language","Select language"},
					{"getBranchWarning","Show Warnings"},
					{"getToolBarVisible","Show Toolbar"},
					{"german","German"},
					{"english","English"},
					{"languages_change","Change of language only takes effect after restart!"},
					{"autom_connecten","Automatic connect"},
					{"show_warnings","Warndialoge ein-/ auschalten"},
					
					{"connect_to_all_server_aut","Automatischer Verbindungsaufbau beim Start"},
					{"show_confirmations","Best\u00e4tigungsdialoge ein-/ausschalten"},
					{"show_toolbar","Die Toolbar links ein-/ausblenden"},
					{"confirm_message","Best\u00e4tigungen anzeigen"},
					{"automatic_update","'Meine Bibliothek' automatisch aktualisieren"},
					{"automatic_update2","'Meine Bibliothek' wird nach jedem Hinzuf\u00c3\u00bcgen oder L\u00c3\u00b6schen von Metadaten automatisch aktualisiert"},


					//WaitDialog
					{"please_wait1","Please wait"},
					{"please_wait2","Please wait, loading..."},

//					MediaTypesEditor
					{"new","New"},
					{"media_editor","Mediatypen editieren"},
					{"media_type_name","Name des Medientyps"},
					{"med_delete","Medium l�schen"},
					{"no_name_selected","Bitte den Namen f�r das Medium eingeben"},
					{"delete_medium_quest","Das Medium wird gel�scht, fortfahren?"},
					{"add_media","Mediatypen bearbeiten oder hinzuf�gen"},
					
					{"book","book"},
					{"magazine","magazine"},
					{"book-article","book-article"},
					{"online-article","online-article"},
					{"magazine-article","magazine-article"},

					//Help.java
					{"liabolo_help","Liab`o�lo help content"},
					{"index","Inhalt"},

					//ReferreceDialog
					{"add_referrence","Referenz hinzuf�gen"},
					{"options","Optionen"},

					{"liabolo_help","Liab'o'lo help content"},
					{"index","Inhalt"},

					//chooseConnectedLibs
					{"choose_connection","EN-Verbindung ausw�hlen"},
					{"choose_connection_tip","EN-W�hlen Sie bitte die gew�nschte Verbindung"},
					{"not_connected","Nicht verbunden!"},
					
					//ConflictManager.java
					{"upload_local","Upload local version"},
					{"download_global","Download global version"},
					{"upload_local_tt","EN-Lokale �nderungen hochladen und globale �nderungen �berschreiben"},
					{"download_global_tt","EN-Globale Version herunterladen und lokale �berschreiben"},

				};
}
