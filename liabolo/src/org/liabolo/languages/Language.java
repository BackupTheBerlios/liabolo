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
import java.util.*;

public class Language extends ListResourceBundle {

	public Object [][] getContents(){
					return contents;
				}
				private Object[][] contents = {

					// login window (LoginWnd.java)
					{"liabolo", "Liabolo 'O' - A library tool hot as hell" },
					{"ok", "Ok"},
					{"cancel", "Cancel"},
					{"login", "Login"},
					{"user", "User"},
					{"password", "Password"},
					{"please_log", "Please login"},
					{"leave", "Text für Verlassen"},
					{"leaving", "Text für Verlassen"},

					// menues (Menu.java)
					{"file", "File"},
					{"connect", "Connect"},
					{"prefs", "Preferences"},
					{"print", "Print"},
					{"exit", "Exit"},

					{"metadata", "Metadata"},
					{"new", "New"},
					{"book", "Book"},
					{"article", "Article"},
					{"audio", "Audio"},

					{"search", "Search"},
					{"branch", "Branch"},
					{"edit", "Edit"},

					{"location", "Location"},
					{"ind_list", "Indiv. Lists"},

					{"connection", "Connection"},
					{"sync", "Synchronise with.."},

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


					// metadata form (MetadataForm.java)
					{"bookArray", new String[]{"Title","Author","Subject", "Description", "Publisher",
									"Contributors","Date","Type", "Format","Identifier", "Source",
									"Language", "Relation","Coverage", "Rights"}},
					{"articleArray", new String[] {"Title","Subject","Description", "Publisher",
									"Date","Type", "Format", "Identifier", "Language", "Relation",
									"Coverage", "Rights"}},
					{"audioArray", new String[] {"Title","Author","Subject", "Description",
									"Publisher", "Date","Type", "Format","Identifier", "Source",
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


					// BackupForm.java
					{"backup", "Backup"},
					{"restore", "Restore"},

					//BranchForm.java
					{"add_branch", "Add branch"},
					{"abreviation", "Abreviation"},
					{"save_branch", "Save branch"},


					//ConnectionForm.java
					{"add_connection", "Add new connection"},
					{"name", "Name"},
					{"db_uri", "DB-URI"},
					{"save_connection", "Save connection"},

					//EditMetadataForm.java
					{"edit_meta", "Edit metadata"},
					{"save_changes", "Save changes"},

					//ListForm.java
					{"add_list", "Add new list"},
					{"save_list", "Save list"},

					//LocationForm.java
					{"add_location", "Add new location"},
					{"enter_location", "Enter location"},
					{"save_location", "Save location"},

					//BrowseMetadataResults.java
					{"column_array", new String[] {"#","Author", "Title", "Branch", "Date", "Type"}},
					{"search_results", "Search results"},
					{"search_for", "Search for"},
					{"no_matches", "No matches found"},
					{"results", "Results"},
					{"select_all", "Select all"},
					{"so_array", new String[] {"------", "add to list", "delete"}},
					{"choose_action", "Action for selected entries"},
					{"delete", "Delete"},
					{"author", "Author"},
					{"title", "Title"},
					{"date", "Date"},
					{"type", "Type"},
					{"warning2", "Edit form already opened! Discard changes and edit selected metadata set?"},

					//	MetadataSearchForm.java
					{"creator", "Creator"},
					{"publisher", "Publisher"},
					{"description", "Description"},
					{"string", "Searchstring"},
					{"db_search", "Search in database"},
					{"submit", "Submit"},
					{"search_metadata", "Search Metadata"},
					{"browse", "Browse"},
					{"browse_branches", "Browse in branches"},

					//OptionsForm.java
					{"options", "Options"},

					//ResultTableMode.java
					{"column_array2", new String[] {"test", "Author", "Title", "Branch", "Date", "Type"}},

					// StatusBar.java
					{"connected", "Connected..."},

                    //Metadata descriptions
                    {"title_desc","ENName des Titels"},
                    {"creator_desc","ENAutor des Werkes"},
                    {"subject_desc","ENSchlagwörter, die den Titel näher spezifizieren"},
                    {"description_desc", "ENBeschreibung des Werkes"},
                    {"publisher_desc", "ENHerausgeber des Werkes"},
                    {"contributors_desc", "ENMitwirkende des Werkes"},
                    {"date_desc", "ENDatum - meist Jahr - der Herausgebung"},
                    {"type_desc", "ENMediantyp, z.B. Buch, Aritkel, etc."},
                    {"format_desc", "ENFormat, z.B. Tonträger, Papier, etc."},
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
                    {"subject","ENSchlagwörter"},
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
                    {"archivesource", "ENDadadudu"}


				};
}
