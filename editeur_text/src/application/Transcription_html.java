package application;

import java.awt.Label;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
//import javafx.JavaFXSaveText;



public class Transcription_html extends Application {
	    private static final String INITIAL_TEXT = null;
		private HTMLEditor editeur;
	    private File f;
	    private FileChooser fc;
	    private FileChooser.ExtensionFilter fc_ext;
		private Stage secondStage;
		private String zonetext="";
		private String selection;
		private MenuItem enregistrer;
		private Scene fenetre;
		private VBox roots; //racine envoyer email
		private BorderPane root;//racine-conteneur principal
		private Scene scene; //scene
		@Override
  public void start (Stage primaryStage) {
    root = new BorderPane();//declaration du format du conteneur
    
    roots = new VBox();      
    roots.setPadding(new Insets(8, 8, 8, 8));
    roots.setSpacing(5);
    roots.setAlignment(Pos.BOTTOM_LEFT);
    
    editeur = new HTMLEditor();//creation de l editeur
    root.setCenter(roots);//assingnation de l editeur au centre de la fenetre
    scene = new Scene(root, 600, 400, Color.BLACK);//creation de la fenetre 
    final PrinterJob impression = PrinterJob.createPrinterJob();//declaration impression
    fenetre = scene;//creation d'une nouvelle fenetre
	//Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + selection + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);//boutton de confirmation
   // titre de l'appli
    primaryStage.setTitle("FX-Editor");
    String imageURL = new File("C://Users//Mohamed. S//Desktop//imgedit//logoedit1.png").toURI().toString();//assignation du lien de l'image 
    final Image image = new Image(imageURL);//creation de l'image
    System.out.println(image);
    primaryStage.getIcons().add(image);
    //Creation du menu
    MenuBar bardemenu = new MenuBar();
    root.setTop(bardemenu);//met la bar de menu en haut 
    Menu fichier = new Menu("Fichier");
	 final HTMLEditor htmlEditor = editeur;
     htmlEditor.setPrefHeight(245);
     htmlEditor.setHtmlText(INITIAL_TEXT);       

     final TextArea codehtml = new TextArea();
     codehtml.setWrapText(true);
 
     ScrollPane scrollPane = new ScrollPane();
     scrollPane.getStyleClass().add("noborder-scroll-pane");
     scrollPane.setContent(codehtml );
     scrollPane.setFitToWidth(true);
     scrollPane.setPrefHeight(180);

     Button showHTML= new Button("Generer le code HTML ");
     roots.setAlignment(Pos.CENTER);
     showHTML.setOnAction(new EventHandler<ActionEvent>() {
         @Override public void handle(ActionEvent arg0) {
        	 codehtml.setText(htmlEditor.getHtmlText());
             zonetext =htmlEditor.getHtmlText();
         }
     });
     
     Button showTEXT= new Button("Generer le Text");
     roots.setAlignment(Pos.CENTER);
     showTEXT.setOnAction(new EventHandler<ActionEvent>() {
         @Override public void handle(ActionEvent arg0) {
            // htmlCode.setText(htmlEditor.getHtmlText());
             zonetext= recupText (codehtml.getText());
             htmlEditor.setHtmlText(zonetext);
         }
     });
     HBox boutons = new HBox(showHTML,showTEXT);
           // Set Spacing to the HBox
             boutons.setSpacing(10);

     roots.getChildren().addAll(htmlEditor, boutons, scrollPane);
     scene.setRoot(root);

     primaryStage.setScene(scene);
    // primaryStage.show();

    
   
    		/********************sous menu fichier (nouveau,enregistrer,enregistrer sous ,imprimer, mise en page, envoyer par courrier elctronic, a propos,quitter************/
    
   /**Item de  MEnu "Nouveau" **/ 			
   MenuItem nouveau = new MenuItem("Nouveau");  			
        
   					/**action de nouveau **/
    			nouveau.setOnAction((ActionEvent event)->{    		   	 
     				Dialog<ButtonType> alert = null;//declaration de la variable locale du bouton alerte
     				Dialog<ButtonType> warning = null;//declaration de la variable locale du bouton alerte
    				zonetext= editeur.getHtmlText();
    				System.out.println(zonetext);//debug
    				System.out.println(zonetext.isEmpty());//debug

    				//presente les message d'erreur suivant si un fichier est deja ouvert ou  enregistrer
    				if(f!=null)
    				{
    							if(zonetext.isEmpty()== false) {
    								warning = new Alert(AlertType.WARNING, "le fichier " + f + " \n est ouvert !!!", ButtonType.CLOSE);//boutton de confirmation
    		                      	warning.showAndWait(); 
    		                      	alert = new Alert(AlertType.CONFIRMATION, "Voulez vous enregistrer le fichier actuellement ouvert ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);//boutton de confirmation
    		                      	alert.showAndWait();
    		        				if (alert.getResult() == ButtonType.YES) 
    		        				{
    		        					System.out.println(f);
    		            		    	if(fc!=null && fc_ext!=null) 
    		            		    				{
    		            	            FileChooser fileChooser =fc ;
    		            	            //ajout de filtre d'extension de fichiers
    		            	            FileChooser.ExtensionFilter extFilter = fc_ext;
    		            	            fileChooser.getExtensionFilters().add(extFilter);
    		            	            //montrer boite de dialogue
    		            	            // f = fileChooser.showSaveDialog(primaryStage);
    		            	            zonetext=editeur.getHtmlText();
    		            	            //System.out.println(":"+c);
    		            	            if(f != null){
    		            	                enrfile(zonetext, f);
    		            	            			 }
    		            		    				}
    		        				}	

    							}else{
    								
    								
        								warning = new Alert(AlertType.WARNING, "le fichier " + f + " \n est ouvert et est vide!!!", ButtonType.CLOSE);//boutton de confirmation
        		                      	warning.showAndWait(); 
        		                      	alert = new Alert(AlertType.CONFIRMATION, "Voulez vous quand meme l'enregistrer ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);//boutton de confirmation
        		                      	alert.showAndWait();
        		        				if (alert.getResult() == ButtonType.YES) 
        		        				{
        		        					System.out.println(f);
        		            		    	if(fc!=null && fc_ext!=null) 
        		            		    				{
        		            	            FileChooser fileChooser =fc ;
        		            	            //ajout de filtre d'extension de fichiers
        		            	            FileChooser.ExtensionFilter extFilter = fc_ext;
        		            	            fileChooser.getExtensionFilters().add(extFilter);
        		            	            //montrer boite de dialogue
        		            	            // f = fileChooser.showSaveDialog(primaryStage);
        		            	            zonetext=editeur.getHtmlText();
        		            	            //System.out.println(":"+c);
        		            	            if(f != null){
        		            	                enrfile(zonetext, f);
        		            	            			 }
        		            		    				}
        		        				}	
    								
    							    							}
    							    
    				}else{
							
					
							//warning = new Alert(AlertType.WARNING, "le fichier " + f + "est ouvert !!!", ButtonType.CLOSE);//boutton de confirmation
	                      	//warning.showAndWait(); 
	                      	alert = new Alert(AlertType.CONFIRMATION, "Voulez vous enregistrer les modifications apportees " + f + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);//boutton de confirmation
	                      	alert.showAndWait();
	        				if (alert.getResult() == ButtonType.YES) 
	        				{
	        					System.out.println(f);
	            		    	if(fc!=null && fc_ext!=null) 
	            		    				{
	            	            FileChooser fileChooser =fc ;
	            	            //ajout de filtre d'extension de fichiers
	            	            FileChooser.ExtensionFilter extFilter = fc_ext;
	            	            fileChooser.getExtensionFilters().add(extFilter);
	            	            //montrer boite de dialogue
	            	            File file = fileChooser.showOpenDialog(primaryStage);
	            	            file = fileChooser.showSaveDialog(primaryStage);
	            	            zonetext=editeur.getHtmlText();
	            	            //System.out.println(":"+c);
	            	            if(file != null){
	            	                enrfile(zonetext, file);
	            	            			 }
	            		    				}
	    
                        	
                    }	
    				}		

    	       zonetext="";
    	       editeur.setHtmlText("");
    	       f=null;
    	       fc=null;
    	       fc_ext=null;
    	           /*Stage aff = new Stage();
    	           aff.setScene(fenetre);
    	           aff.show(); */
                        });
    			
    			
 /**Item de  MEnu "Ouvrir" **/   			
   MenuItem ouvrir = new MenuItem("Ouvrir");
   
                      /**action d'ouvrir**/
    	        ouvrir.setOnAction(new EventHandler<ActionEvent>(){
       	            public void handle(ActionEvent arg0) {
    	                FileChooser fileChooser = new FileChooser();    	                 
    	                //mettre extension fichier
    	                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
    	                fileChooser.getExtensionFilters().add(extFilter);    	                 
    	                //montrer la fenetre de dialog
    	                File file = fileChooser.showOpenDialog(primaryStage);
    	               
    	                if(file != null){
    	                	 zonetext=lirefile(file); // lit le fichier et recupere le texte 
    	                	editeur.setHtmlText(zonetext);//assignation du text a l'editeur
    	                	enregistrer.setDisable(false);
    	                }
    	                f=file;
        	            fc=fileChooser;
        	            fc_ext=extFilter; 
        	            System.out.println(f);//debug
       	            }
 
    	        });
    	        
    	        
    	        
    	        
/**Item de  MEnu "Enregistrer" **/  
    	        
 enregistrer = new MenuItem("Enregistrer");
    		    enregistrer.setDisable(true);//masquer le bouton enregistre 
    		    
    				/**action d'enregistrer**/
  			
    		    enregistrer.setOnAction((ActionEvent event) -> {
    		    	System.out.println(f);
    		    	enregistrer.setDisable(false);
    		    	if(fc!=null && fc_ext!=null) {
    	            FileChooser fileChooser =fc ;
    	            //ajout de filtre d'extension de fichiers
    	            FileChooser.ExtensionFilter extFilter = fc_ext;
    	            fileChooser.getExtensionFilters().add(extFilter);
    	            //montrer boite de dialogue
    	            //File file = fileChooser.showSaveDialog(primaryStage);
    	            zonetext=editeur.getHtmlText();
    	            //System.out.println(":"+c);
    	            if(f != null){
    	                enrfile(zonetext, f);
    	            }   	            
    		    	}});
    		    
    		    
    		    
    		    
    		    
/**Item de  MEnu "Enregistrersous" **/    
    		    
  MenuItem enregistrersous = new MenuItem("Enregistrer sous");
    			  
    			/**action enregistrer sous**/
          	        enregistrersous.setOnAction((ActionEvent event) -> {
         	        	zonetext=editeur.getHtmlText();
         	        	//zonetext=recupText(zonetext);
         	        	System.out.println("text:"+zonetext);//debug
         	        	if(zonetext.equals("")) 
    	            	{
         	        		System.out.println("text:"+zonetext);//debug
    	            		Alert warning = new Alert(AlertType.WARNING, "le fichier actuellement ouvert est vide \n Voulez l'enregistrer ?", ButtonType.YES,ButtonType.NO);//boutton de confirmation
    	    				warning.showAndWait(); 
    	    				if (warning.getResult() == ButtonType.YES) 
    	    				{    	            FileChooser fileChooser = new FileChooser();
    	            //ajout de filtre d'extension de fichiers
    	            FileChooser.ExtensionFilter extFilter = 
    	                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt","SOPI files (*.sopi)","*.sopi");
    	            fileChooser.getExtensionFilters().add(extFilter);
    	            //montrer boite de dialogue
    	            File file = fileChooser.showSaveDialog(primaryStage);
    	           
    	            
    	            //c=recupText(c);
    	            //System.out.println(":"+c);
    	            if(file != null){	            	
    	            		enrfile(zonetext, file);
    	            		enregistrer.setDisable(false);
    	               // System.out.println(zonetext);
    	            }
    	            
    	            f=file;
    	            fc=fileChooser;
    	            fc_ext=extFilter;
    	    				}
    	            	}else {
    	            		FileChooser fileChooser = new FileChooser();
    	    	            //ajout de filtre d'extension de fichiers
    	    	            FileChooser.ExtensionFilter extFilter = 
    	    	                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt","SOPI files (*.sopi)","*.sopi");
    	    	            fileChooser.getExtensionFilters().add(extFilter);
    	    	            //montrer boite de dialogue
    	    	            File file = fileChooser.showSaveDialog(primaryStage);
    	    	           
    	    	            
    	    	            //c=recupText(c);
    	    	            //System.out.println(":"+c);
    	    	            if(file != null){	            	
    	    	            		enrfile(zonetext, file);
    	    	            		enregistrer.setDisable(false);
    	    	               // System.out.println(zonetext);
    	    	            }
    	    	            
    	    	            f=file;
    	    	            fc=fileChooser;
    	    	            fc_ext=extFilter;
    	            	}
    	            	

    	        });
    	        
/**Item de  MEnu "Imprimer" **/  
         	        
    			MenuItem imprimer = new MenuItem("Imprimer");
    			
    			/**action imprimer**/ 
    			imprimer.setOnAction((ActionEvent event)->{
    				if (impression.showPageSetupDialog(primaryStage)) { 
    					if (impression.showPrintDialog(primaryStage)) { 
    						 final JobSettings settings = impression.getJobSettings();
    						  final PageLayout pageLayout = settings.getPageLayout();            
        				}            
    				}
    			});
    			
    			
/**Item de  MEnu "Mise en page" **/   			
    			
MenuItem miseenpage = new MenuItem("Mise en page");

    			/**action de mise en page**/
    			miseenpage.setOnAction((ActionEvent event)->{
    				if (impression.showPageSetupDialog(primaryStage)){}});
    			
/**Item de  MEnu "Envoyer par mail electronic"  **/      			
    			MenuItem envoyer = new MenuItem("Envoyer par courrier Electronique");
    			
    			/**Action "Envoyer par mail electronic  **/ 
    			envoyer.setOnAction((ActionEvent event)->{
    				root.setCenter(roots);
    				   final GridPane grid = new GridPane();
    			        grid.setVgap(5);
    			        grid.setHgap(10);
    			              
    			        final ChoiceBox envoyera =  new ChoiceBox(FXCollections.observableArrayList("To:", "Cc:", "Bcc:"));
    			        
    			        envoyera.setPrefWidth(100);                
    			        GridPane.setConstraints(envoyera, 0, 0);
    			        grid.getChildren().add(envoyera);
    			        
    			        final TextField tbTo = new TextField();
    			        tbTo.setPrefWidth(400);
    			        GridPane.setConstraints(tbTo, 1, 0);
    			        grid.getChildren().add(tbTo);
    			        
    			        final Text subjectLabel = new Text("Subject:");
       			        GridPane.setConstraints(subjectLabel, 0, 1);
    			        grid.getChildren().add(subjectLabel);        
    			        
    			        final TextField tbSubject = new TextField();
    			        tbTo.setPrefWidth(400);
    			        GridPane.setConstraints(tbSubject, 1, 1);
    			        grid.getChildren().add(tbSubject);
    			        
    			        roots.getChildren().add(grid);
    			        
    			        final HTMLEditor htmlEditeur = editeur;
    			        htmlEditeur.setPrefHeight(370);
    			 
    			        roots.getChildren().addAll(htmlEditeur, new Button("Envoyer"));        
    			      
    			       // final Label htmlLabel = new Label();
    			       // htmlLabel.setText(true);
    			                      
    			        scene.setRoot(root);
    			        primaryStage.setScene(scene);
    			        primaryStage.show();
    				
    			});
    			
    			
    			MenuItem apropos = new MenuItem("A propos");
    			
    			//action a propos de ceux qui ont fait le programme
    			apropos.setOnAction((ActionEvent event)->{
    				fenetre();
    			});
    			MenuItem quitter = new MenuItem("Quitter");
    			quitter.setOnAction(ActionEvent -> Platform.exit());
    			
    			//ajout de ces items au bouton de menu "Fichier"
    			fichier.getItems().addAll(nouveau,ouvrir,enregistrer,enregistrersous,new SeparatorMenuItem(),imprimer,miseenpage,envoyer,new SeparatorMenuItem(),apropos,quitter);
    			
    Menu accueil = new Menu ("Accueil");      
    Menu affichage = new Menu("Affichage");
    bardemenu.getMenus().addAll(fichier,accueil,affichage);
    primaryStage.setScene(scene);
    Stage s = new Stage();
 		s.setScene(primaryStage.getScene());
 		s.initStyle(StageStyle.DECORATED);
 	    
 		/**ajouter le style a la scene **/
 		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
 		    
     //primaryStage.show();
 		 s.setTitle("FX-SQL");
 		    System.out.println(image);//debug
 		    s.getIcons().add(image);
 		s.show();
    apparence(editeur); //fonction d'apparence de l editeur
    

  }
  
  

//Apparence de l'editeur
  private void apparence(HTMLEditor editeur)
  {	  
   //bar de navigation 
	       //cacher element dans la bar bar de notification
	 /* Node seperator = editeur.lookup(".separator");
	    seperator.setVisible(false);
	    seperator.setManaged(false);  
	    cacher(editeur, ".separator");
	    cacher(editeur, ".html-editor-cut", ".html-editor-copy", ".html-editor-paste", ".html-editor-strike",
	             ".html-editor-hr");
	    cacher(editeur, ".html-editor-align-left"
	             , ".html-editor-align-center"
	             , ".html-editor-align-right"
	             , ".html-editor-align-justify", ".html-editor-outdent"
	             , ".html-editor-indent", ".html-editor-bullets"
	             , ".html-editor-numbers",".html-editor-italic");*/
      Node bottom= editeur .lookup(".top-toolbar");
      GridPane.setConstraints(bottom,0,2);
      Node top= editeur.lookup(".bottom-toolbar");
      GridPane.setConstraints(top,0,1);
      Node web= editeur.lookup("WebView");
      GridPane.setConstraints(web,0,3);
      final GridPane grid = new GridPane();
      grid.setVgap(5);
      grid.setHgap(10);
            
      
  // ajout de bouton
      //ajoutera(editeur,".top-toolbar",".html-editor-italic");
     
      }
      
      



private void ajoutera(HTMLEditor editeur2, String element, String... selectors) {
 
    for (String selector : selectors) 
    {
        Set<Node> nodes = editeur.lookupAll(selector);
        for (Node node : nodes) {
            node.setVisible(true);
            node.setManaged(true);
        }
    }
}



private void  cacher(HTMLEditor editeur, String... selectors) {
        for (String selector : selectors) {
            Set<Node> nodes = editeur.lookupAll(selector);
            for (Node node : nodes) {
                node.setVisible(false);
                node.setManaged(false);
            }
        }
    }
private void enrfile(String content, File file){
    try {
        FileWriter ecrirefichier;
          
        ecrirefichier = new FileWriter(file);
        ecrirefichier.write(content);
        ecrirefichier.close();
    } catch (IOException ex) {
        Logger.getLogger(Editeurtext.class
            .getName()).log(Level.SEVERE, null, ex);
    }
      
}

private String lirefile(File file){
    StringBuilder stringBuffer = new StringBuilder();
    BufferedReader bufferedReader = null;
     
    try {

        bufferedReader = new BufferedReader(new FileReader(file));
         
        String text;
        while ((text = bufferedReader.readLine()) != null) {
            stringBuffer.append(text);
        }

    } catch (FileNotFoundException ex) {
        Logger.getLogger(Editeurtext.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Editeurtext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            bufferedReader.close();
        } catch (IOException ex) {
            Logger.getLogger(Editeurtext.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    return stringBuffer.toString();
}

private void fenetre() {
	 
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    Text zonetext = new Text("Fx-Editor"
    		+ " est un logiciel destiné à la création et "
    		+ "l'édition de fichiers textes,\n"
    		+ "sql et Html.\n "
    		+ "Il comporte un editeur de texte entierement complet \n"
    		+ " qui fournit toutes les comodites des éditeurs, tant son \n"
    		+ " usage est simpliste,\n "
    		+ "et attrayant.En outre il permet de realiser certaines \n "
    		+ "tâches informatiques de base\n "
    		+ "sur les textes.\n"
    		+ "A cela s'ajoute une option developpement pour la  \n"
    		+ " et l'edition des bases de donnees.\n"
    		+ "Une autre facette de ce logiciel est le transcripteur Html qui \n"
    		+ "traduit un text\n"
    		+ "complet avec toutes la mise en page ce qui est ideal "
    		+ "pour tout text \n "
    		+ "a incorporer dans du code \n "
    		+ "");
    Button closeButton = new Button("Close");
    closeButton.setOnAction(e -> stage.close());
    VBox root = new VBox();
    root.getChildren().addAll(zonetext,closeButton);
    Scene scene = new Scene(root, 200, 100);
    stage.setScene(scene);
    
    stage.show();
}

public static String recupText(String htmlText) {

	  String result = "";

	  Pattern pattern = Pattern.compile("<[^>]*>");
	  Matcher matcher = pattern.matcher(htmlText);
	  final StringBuffer text = new StringBuffer(htmlText.length());

	  while (matcher.find()) {
	    matcher.appendReplacement(
	      text,
	      " ");
	  }
	  matcher.appendTail(text);

	  result = text.toString().trim();  

	  return result;
	}






}















