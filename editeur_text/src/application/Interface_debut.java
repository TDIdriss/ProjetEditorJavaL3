package application;

import java.awt.Font;
import java.io.File;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.application.Preloader.PreloaderNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/*
 * Fait  par : Sidibe Mohamed
 * 				 Andrea Gbamele
 * 					Adou lorraine
 * 						Traore Idriss
 * 							Koet Bi-BOH
 * 								Sopi Diplo Adonis
 * */
public class Interface_debut extends Preloader{
	private  Stage splashScreen;
	  
	@Override
	    public void start(Stage stage) throws Exception {
	    		 
	    	splashScreen = stage;
	    	final BorderPane root = new BorderPane(); 
	        VBox vbox = new VBox();//declaration d'un box
	        ProgressIndicator progressIndicator = new ProgressIndicator();//barre indicatrice
	        
	        FlowPane fp = new FlowPane();
	        fp.setPadding(new Insets(0,0,0,45));
	        fp.setHgap(10);
	        fp.getChildren().addAll( progressIndicator);
	        vbox.setSpacing(15);//espace entre les logo
		    vbox.setAlignment(Pos.BOTTOM_CENTER);
		    vbox.setPadding(new Insets(0,0,0,180));
	    	String fimage = new File("C://Users//Mohamed. S//Desktop//imgedit//logodebut.png").toURI().toString();
	    	final Image image = new Image(fimage,500,330,true,true);
	    	ImageView iview = new ImageView(image);
	    	root.getChildren().add(iview);
	    	//bar de chargement
	    	vbox.getChildren().addAll(fp);
	    	root.setCenter(vbox);
	    	Text zonetextbas = new Text("  Demarrage");
	    	//zonetextbas.setFill(Color.WHITE);
	    	Text zonetexthaut = new Text("  G-TASKD");
	    	//zonetexthaut.setFill(Color.WHITE);
	    	Text version = new Text(" Version 1.0");
	    	//version.setFill(Color.WHITE);
	    	HBox hbox =new HBox();

	    	
	    	hbox.getChildren().addAll(zonetextbas,version);
	    	hbox.setSpacing(330);
	    	root.setTop(zonetexthaut);
	    	root.setBottom(hbox);
	        Scene scene = new Scene(root, 500, 330, Color.WHITE);
	        splashScreen.setTitle("Fx-Editor");
	        String imageURL = new File("C://Users//Mohamed. S//Desktop//imgedit//logoedit1.jpg").toURI().toString();//assignation du lien de l'image 
	        final Image img = new Image(imageURL);//creation de l'image
	        System.out.println(img);
	        splashScreen.getIcons().add(img);    
	        splashScreen.setResizable(false);  //desactivatin du redimensionement de la fenetre
	        
	        /**ajouter le style a la scene **/
	            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());            
	       stage.initStyle(StageStyle.UNDECORATED); 
	       splashScreen.setScene(scene);
	       splashScreen.show();
	        
	       
}
	    public void handleApplicationNotification(PreloaderNotification notification) {
	        if (notification instanceof StateChangeNotification) {
	            splashScreen.hide();;
	        }
	    }
	    


}

