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
					{"form_close","Close Form?"},
					{"close_wnd","Close Window?"},
					{"local_db_error","Error Connecting to local Databse!"},
					{"no_entry_selection","No entries selected!"},
					{"connected", "Connected..."},

					// login window (LoginWnd.java)
					{"liabolo", "Liab\u2035o\u2032lo - A library tool hot as hell" },
					{"ok", "Ok"},
					{"cancel", "Cancel"},
					{"login", "Login"},
					{"user", "User"},
					{"password", "Password"},
					{"please_log", "Please login"},
					{"leave", "Leave"},
					{"leaving", "Leaving"},
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
					{"new_branch_menu",  "Add new branch"},
					{"edit_branch_menu", "Edit branch"},
					{"new_location_menu", "Add new location"},
					{"edit_location_menu", "Edit location"},
					{"new_list_menu", "Add new indiv. list"},
					{"edit_list_menu", "Edit indiv. list"},

					{"connection", "Connection"},
					{"sync", "Synchronize with.."},
					{"conn_with", "Connect with.."},

					{"extras", "Tools"},
					{"form_editor", "Formular Editor"},
					{"prefs", "Preferences"},

					{"help", "Help"},
					{"help_content", "Help Contents"},
					{"info", "Info"},

					{"add_book", "Add new book"},
					{"switch_to_book", "Really switch to book formular?"},
					{"switching", "Switching formular"},
					{"add_article", "Add new article"},
					{"switch_to_article", "Really switch to article formular?"},
					{"add_audio", "Add new audio file"},
					{"switch_to_audio", "Really switch to audio formular?"},

					{"exit_liabolo", "Exit Liab\u2035o\u2032lo "},
					{"exit_liabolo2", "Really exit Liab\u2035o\u2032lo?"},


					// metadata form (MetadataForm.java)
					{"branch_not_selected", "Please select a valid branch!"},
					{"location_not_selected", "Please select a valid location!"},
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
					{"no_types_av","There are no media types defined! Would you like to create an new media type?"},
					{"no_book_type_av","There are no book types defined! Would you like to create an new book type?"},
					{"check_for_add_to_list","Select the box to assign the item to an individual list!"},


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
					{"branch_content", "Show items of selected branch"},

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
					{"no_export_selection","No items to export selected!"},
					{"selected_list_entries","Entries in selected individual lists"},
					{"add_list_tip", "Select individual list:"},
					{"export","Export"},

					//AddToList.java
					{"add_to_list", "Add entries to individual lists"},
					{"av_lists", "Available individual lists"},
					{"add_list_tip", "Choose desired individual lists:"},
					{"entry_error","An error occured!"},

					//LocationForm.java
					{"add_location", "Add new location"},
					{"enter_location", "Enter location"},
					{"save_location", "Save location"},

					//BrowseLocation.java
					{"list_locations", "List locations"},
					{"no_locations_avail", "No locations available!"},
					{"browse_locations", "Browse locations"},
					{"selected_location_entries","Entries in selected locations"},
					{"location_content", "Show items of selected locations"},

					//BrowseMetadataResults.java
					{"checkout", "->Clipboard"},
					{"referrence", "Referrence"},
					{"column_array", new String[] {"#","Author", "Title", "Branch", "Date", "Type"}},
					{"search_results", "Search results"},
					{"search_for", "Search for"},
					{"no_matches", "No matches found"},
					{"no_entries", "No entries found"},
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
					{"no_refer_selection","No entry to reference selected!"},
					{"addMD_not_opened","The \"Add item\" form is not open! Would you liek to open it now?"},
					{"no_tolist_selection","No entries selected!"},
					{"selected_branches", "Selected branches"},
					{"no_lists_av", "No individal lists available!"},
					{"add_new?", "Would you like to add new ones?"},
					{"signature", "Signature"},
					{"print_preview", "Preview"},
					{"no_tolist_selection","No items for Preview selected!"},
					{"md_edit_opened","Edit form already opened! Discard changes and edit selected metadata set ?"},
					{"md_print_opened","Print form already opened! Discard preview and list new selection ?"},

					{"rt_tt_check","double click to select or de-select all items"},
					{"rt_tt_author","double click to arrange alphabetically by author"},
					{"rt_tt_subject","double click to arrange alphabetically by keyword"},
					{"rt_tt_description","double click to arrange alphabetically by description"},
					{"rt_tt_publisher","double click to arrange alphabetically by publisher"},
					{"rt_tt_contributors","double click to arrange alphabetically by contributors"},
					{"rt_tt_type","double click to arrange alphabetically by media type"},
					{"rt_tt_date","double click to arrange alphabetically by date"},
					{"rt_tt_source","double click to arrange alphabetically by source"},
					{"rt_tt_relation","double click to arrange alphabetically by relation"},
					{"rt_tt_language","double click to arrange alphabetically by language"},
					{"rt_tt_rights","double click to arrange alphabetically by copyright"},
					{"rt_tt_title","double click to arrange alphabetically by title"},
					{"rt_tt_branch","double click to arrange alphabetically by branch"},
					{"rt_tt_coverage","double click to arrange alphabetically by location"},
					{"rt_tt_identifier","double click to arrange by ISBN/ISN number"},
					{"rt_tt_signature","double click to arrange by signature"},
					{"rt_tt_format","double click to arrange alphabetically by format"},

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
                    {"title_desc","exact title"},
                    {"creator_desc","author(s) of item"},
                    {"subject_desc","add categorizing keywords"},
                    {"description_desc", "description of item"},
                    {"publisher_desc", "publisher of item"},
                    {"contributors_desc", "contributors to item"},
                    {"date_desc", "date especially year of publication"},
                    {"type_desc", "media type, e.g. book, article, etc."},
                    {"format_desc", "format, e.g. compact disc, paper, Papier, etc."},
                    {"identifier_desc", "ISBN/ISN/EAN number"},
                    {"source_desc", "Source of item, e.g. URI"},
                    {"language_desc", "language"},
                    {"relation_desc", "relation to other items, e.g. part of anthology, duplicate"},
                    {"coverage_desc", "location were item is stored"},
                    {"rights_desc", "copyright"},
                    {"branch_desc", "realm, area of science"},
                    {"abstract_desc", "Please give a short abstract"},
                    {"signature_desc", "signatur - automatically generated"},
                    {"archivesource_desc", "ENDadadudu"},

                    {"title","Title"},
                    {"creator","Author(s)"},
                    {"subject","Keywords"},
                    {"description", "Description"},
                    {"publisher", "Publisher"},
                    {"contributors", "Contributors"},
                    {"date", "Date"},
                    {"type", "Media type"},
                    {"format", "Format"},
                    {"identifier", "ISBN-Number"},
                    {"source", "Source"},
                    {"language", "Language"},
                    {"relation", "Relation"},
                    {"coverage", "Location"},
                    {"rights", "Copyright"},
                    {"branch", "Subject"},
                    {"abstract", "Abstract"},
                    {"signature", "Signature"},
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
					{"Language","Select Language"},
					{"getBranchWarning","Show Warnings"},
					{"getToolBarVisible","Show Toolbar"},
					{"german","German"},
					{"english","English"},
					{"languages_change","Change of language only takes effect after restart!"},
					{"autom_connecten","Automatic connect"},
					{"show_warnings","Show Warnings"},
					
					{"connect_to_all_server_aut","Connect automatically at Startup"},
					{"show_confirmations","Show confirm-dialouges"},
					{"show_toolbar","Show left Toolbar"},
					{"confirm_message","Show confirmation messages"},
					{"automatic_update","Update 'My Library' automatically"},
					{"automatic_update2","'My Library' will be updated after every Add or Delete"},


					//WaitDialog
					{"please_wait1","Please wait"},
					{"please_wait2","Please wait, loading..."},

//					MediaTypesEditor
					{"new","New"},
					{"media_editor","Edit Media types"},
					{"media_type_name","Name of Media Type"},
					{"med_delete","Delete Media type"},
					{"no_name_selected","Please add a name for the Media type"},
					{"delete_medium_quest","Do you really want to delete this Media type?"},
					{"add_media","Edit or Add Media types"},
					
					{"book","book"},
					{"magazine","magazine"},
					{"book-article","book-article"},
					{"online-article","online-article"},
					{"magazine-article","magazine-article"},

					//Help.java
					{"liabolo_help","Liab\u2035o\u2032lo help content"},
					{"index","Content"},

					//ReferreceDialog
					{"add_referrence","Add reference"},
					{"options","Options"},

					//chooseConnectedLibs
					{"choose_connection","Select Connection"},
					{"choose_connection_tip","Please select a connection"},
					{"not_connected","Not connected!"},
					
					//ConflictManager.java
					{"upload_local","Upload local version"},
					{"download_global","Download global version"},
					{"upload_local_tt","Upload local modifications and overwrite global data"},
					{"download_global_tt","Download global version and overwrite local data"},

				};
}