package application;


import java.io.File;


import javafx.animation.PauseTransition;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;

import javafx.scene.control.Hyperlink;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
/*
 * Fait  par : Sidibe Mohamed
 * 				 Andrea Gbamele
 * 					Adou lorraine
 * 						Traore Idriss
 * 							Koet Bi-BOH
 * 								Sopi Diplo Adonis
 * */



public class Menu_editeur extends Application{
	static String logo_editeur = new File("C://Users//Mohamed. S//Desktop//imgedit//edit.png").toURI().toString();//assignation du lien de l'image 
	static String logo_sql = new File("C://Users//Mohamed. S//Desktop//imgedit//sql.png").toURI().toString();//assignation du lien de l'image 
	static String logo_transcripteur_html = new File("C://Users//Mohamed. S//Desktop//imgedit//html.png").toURI().toString();//assignation du lien de l'image 
	static String logo_quitter = new File("C://Users//Mohamed. S//Desktop//imgedit//off.png").toURI().toString();//assignation du lien de l'image 
	static String logo_info = new File("C://Users//Mohamed. S//Desktop//imgedit//info.png").toURI().toString();//assignation du lien de l'image 

	final static String[] imageFiles = new String[]{
	       logo_editeur,
	       logo_sql,
	       logo_transcripteur_html ,
	       logo_quitter,
	      logo_info,
	       
	    };
	    final static String[] captions = new String[]{
	        " Editeur de Texte",
	        " Editeur SQL",
	        " Trancripteur HTML",
	        "  Quitter",
	        " info"
	    };
	 
	   
	    final ImageView selectedImage = new ImageView();
	    final Hyperlink[] hpls = new Hyperlink[captions.length];
	    final Image[] images = new Image[imageFiles.length];   
	 
	    public static void main(String[] args){
	        launch(args);
	    }
	 
	    @Override
	    
	    public void start(Stage stage) throws Exception {
	    	
	    	Editeurtext editext = new Editeurtext();//appel de Editeurtext.java
	    	Transition transit = new Transition(); //transition.java
	    	Transcription_html transc= new Transcription_html();//appel transcription java
	    	Editeur_sql sql = new Editeur_sql();//appel editeur sql
	    	
	    	
	    	final BorderPane pane= new BorderPane(); 
	    	
	        VBox vbox = new VBox();
	        Scene scene = new Scene(pane,600,400);
	        stage.setTitle("Fx-Editor");
	        String imageURL = new File("C://Users//Mohamed. S//Desktop//imgedit//logoedit1.jpg").toURI().toString();//assignation du lien de l'image 
	        final Image img = new Image(imageURL);//creation de l'image
	        System.out.println(img);
	        stage.getIcons().add(img);
	        String fimage = new File("C://Users//Mohamed. S//Desktop//imgedit//back.png").toURI().toString();//assignation du lien de l'image 
	        final Image imageb = new Image(fimage,600,400,true,true);
	    	ImageView iview = new ImageView(imageb);	    	
	    	pane.getChildren().add(iview);
	    	vbox.setSpacing(5);
	    	vbox.setStyle("-fx-font: 18 verdana ");
	        
	       
	 
	        //selectedImage.setLayoutX(1);
	       // selectedImage.setLayoutY(1);
	        
	       // final WebView browser = new WebView();
	       // final WebEngine webEngine = browser.getEngine();
	        
	        for (int i = 0; i < captions.length; i++) {
	            final Hyperlink hpl = hpls[i] = new Hyperlink(captions[i]);
	          hpl.setUnderline(false);
	          
	          BooleanProperty x = hpl.visitedProperty();
	          System.out.println(x);
	         // hpl.setViewOrder();
	           //images[4] = new Image(imageFiles[4],6,6,true,true);
	            final Image image = images[i] = new Image(imageFiles[i],70,70,true,true);
	                  
	            hpl.setGraphic(new ImageView (image));
	            hpl.setVisited(false);
	            	//ici faire un switch pour les actions
	           switch(i) 
	           {
	           case 0:
	        	   //actions editeur de texte
	        	   hpl.setOnAction(new EventHandler<ActionEvent>() {
		                @Override
		                public void handle(ActionEvent e) {
		                 //   webEngine.load(url);
		                	try {
								transit.start(stage);
								
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		         	       PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
		        	       delay.setOnFinished( event -> 
		        	       //affichage de lediteur
		        	       {
		        	       editext.start(stage);});
		        	       delay.play();
		        	       
		                	
		                }
		            });
	            break;
	            
	           case 1:
	        	   //actions editeur sql
	        	   hpl.setOnAction(new EventHandler<ActionEvent>() {
		                @Override
		                public void handle(ActionEvent e) {
		                 //   webEngine.load(url);
		                	try {
								transit.start(stage);
								
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		         	       PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
		        	       delay.setOnFinished( event -> 
		        	       //affichage de lediteur
		        	       {
		        	       sql.start(stage);});
		        	       delay.play();
		        	       
		                	
		                }
		            });
		                

	           break;
	           
	           case 2:
	        	   //actions transcripteur html
	        	   hpl.setOnAction(new EventHandler<ActionEvent>() {
		                @Override
		                public void handle(ActionEvent e) {
		                 //   webEngine.load(url);
		                	try {
								transit.start(stage);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		         	       PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
		        	       delay.setOnFinished( event -> 
		        	       //affichage de lediteur
		        	       transc.start(stage));
		        	       delay.play();
		                	
		                	//stage.show();
		                }
		            });

	           break;
	           
	           case 3:
	        	   //actions quitter
	        	   hpl.setOnAction(new EventHandler<ActionEvent>() {
		                @Override
		                public void handle(ActionEvent e) {
		                   //affichage de lediteur
		        	       Platform.exit();		                	
		                }
		            });
	           break;
	           case 4:
	        	   //infos
	           	   
	        	break;   
	           
	           }
	        }
	        
	       pane.setCenter(vbox);
	       pane.setBottom(hpls[4]);

	       hpls[4].setPadding(new Insets(0,0,0,490));
	       vbox.getChildren().addAll(hpls[0],hpls[1],hpls[2],hpls[3]);
	       vbox.setSpacing(2);//espace entre les logo
	       vbox.setAlignment(Pos.CENTER_LEFT);
	       vbox.setPadding(new Insets(8,0,0,150)); 
	       
	    
	       stage.setScene(scene);

	        Stage s = new Stage();
	        s.setTitle("FX-Editor");
	        System.out.println(img);
	        s.getIcons().add(img);
	 		s.setScene(stage.getScene());
	 		s.initStyle(StageStyle.UTILITY);//pour une contour avec que le close
	       s.show();
}
	


}
