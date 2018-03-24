package application;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.layout.*;


public class Editeur_sql extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Parent root =  FXMLLoader.load(getClass().getResource("interfaceBD.fxml"));
			Scene scene = new Scene(root,800,600);
			primaryStage.setScene(scene);
                      primaryStage.setScene(scene);
			primaryStage.setTitle("Fx-SQL");
		    primaryStage.setTitle("FX-Editor");
		    String imageURL = new File("C://Users//Mohamed. S//Desktop//imgedit//logoedit1.png").toURI().toString();//assignation du lien de l'image 
		    final Image image = new Image(imageURL);//creation de l'image
		    System.out.println(image);
		    primaryStage.getIcons().add(image);
			primaryStage.setTitle("Fx-SQL");
			//primaryStage.show();
			
			Stage s = new Stage();
			s.setScene(primaryStage.getScene());
			s.initStyle(StageStyle.DECORATED);
	 
			 s.setTitle("Fx-SQL");;
			    
			    s.getIcons().add(image);
			s.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


}
