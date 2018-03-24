package application;

import java.io.File;

import javafx.animation.PauseTransition;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Transition extends Preloader {
    ProgressBar bar;
    Stage stage;
 
    private Scene createPreloaderScene() {
        
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        return new Scene(p, 200, 150);        
    }
    @Override  
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Stage s = new Stage();
		  s.setScene(stage.getScene());
        s.initStyle(StageStyle.UTILITY); 
		 s.setTitle("FX-Editor");
		    String imageURL = new File("C://Users//Mohamed. S//Desktop//imgedit//logoedit1.png").toURI().toString();//assignation du lien de l'image 
		    final Image image = new Image(imageURL);//creation de l'image
		    System.out.println(image);//debug
		    s.getIcons().add(image);
        s.setScene(createPreloaderScene()); 
        
        /**ajouter le style a la scene **/
        createPreloaderScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
        s.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
	       delay.setOnFinished( event -> 
	       //affichage de lediteur
	       {
	       s.close();});
	       delay.play();

    }
    
    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        bar.setProgress(pn.getProgress());
    }
 
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    } 

    
}
