package application;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.fxml.*;
import java.io.File;
import java.sql.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List.*;
import javafx.css.*;
import javafx.application.Application;

/*
 * Fait  par : Sidibe Mohamed
 * 				 Andrea Gbamele
 * 					Adou lorraine
 * 						Traore Idriss
 * 							Koet Bi-BOH
 * 								Sopi Diplo Adonis
 * */
@SuppressWarnings("unused")
public class ControllerBD
{
	    @FXML
	    private Button valid1;
	    @FXML
	    private Button  valid2;
	    @FXML
	    private Button create;
	    @FXML
	    private Button insert;
	    @FXML
	    private Button propos;
	    @FXML
	    private Button select;
	    @FXML
	    private Button update;
	    @FXML
	    private Button execute;
	    @FXML
	    private Button delete;
	    @FXML
	    private TextField nameBD;
	    @FXML
	    private TextArea requete ;
	    @FXML
	    private TextArea erreur;
	    @FXML
	    private ListView <String> repertoire ;
	    ObservableList<String> listViewData=FXCollections.observableArrayList();
	    @FXML
	    private ListView <String> bd ;
	    @FXML
	    private ComboBox <String>  deroulant;
	    @SuppressWarnings("rawtypes")
		@FXML
	    private ObservableList<ObservableList> data;
	    @SuppressWarnings("rawtypes")
		@FXML
	    private TableView tableview;
	    @FXML
	    private TabPane cadre;
	    @FXML
	    private TabPane cadre2;
	    @FXML
	    private AnchorPane tables;
	    @FXML
	    private Tab acceuil;
	    @FXML
	    private Tab creationBD;
	    @FXML
	    private Tab visualisation;
	    @FXML
	    private Tab sql;
			@SuppressWarnings("rawtypes")
			@FXML
			private void initialize()
			{
			                       // PERMET D'AFFICHER LES BASES DE DONNEE DEJA CREE
				File[] files = new File("C://Users//Mohamed. S//eclipse-workspace//editeur_text").listFiles();

				for(File file : files)
				{
					String ext="";
					try
					{
						ext = file.getName().substring(file.getName().lastIndexOf(".")+1);
					}
					catch(Exception e){}
					if((file.isFile()) && ext.equals("db"))
					{
						listViewData.add(file.getName().substring(0, file.getName().lastIndexOf(".")));

					}
				}
				 repertoire.setItems(listViewData);

				 // CREATION D'UNE BASE DE DONNEE
				valid1.setOnAction((event)->
				{

					if (!nameBD.getText().equals(""))
					{
						 Connection c = null;

					      try	{

					    	  Class.forName("org.sqlite.JDBC");
					         c = DriverManager.getConnection("jdbc:sqlite:"+nameBD.getText()+".db");

					         Alert alert = new Alert(AlertType.INFORMATION);
					         alert.setTitle("Fx-SQL");
					         alert.setHeaderText(null);
					         alert.setContentText("Operation effectuée avec succes");
					         alert.showAndWait();

					            } catch ( Exception e )
					      			{

					            		Alert alert = new Alert(AlertType.INFORMATION);
					            		alert.setTitle("Fx-SQL");
					            		alert.setHeaderText(null);
					            		alert.setContentText("Operation non effectuée");
					            		alert.showAndWait();
					      			}


					      visualisation.setDisable(false);
					      sql.setDisable(false);
					      cadre.getSelectionModel().select(3);
					      creationBD.setDisable(true);
					}


				});

				propos.setOnAction((event)->
				{
					Alert alert= new Alert(AlertType.INFORMATION);
					alert.setTitle("A Propos");
					alert.setHeaderText("MEMBRE DU GROUPE");
					alert.setContentText("ADOU LORRAINE VICTOIRE\n DIPLO SOPI ADONIS MAXENCE\n GBAMELE ANDREA AIMEE STEPHANIE\n KOET BI BOH CHABEL\n SIDIBE MOHAMED\n TRAORE DAHABA IDRISS\n ");
					alert.show();
				});

				create.setOnAction((event)->
				{
					requete.setText("CREATE TABLE nom_de_la_table( nom_du_champ1 type_donnees, nom_du_champ2 type_donnees)");
				});

				select.setOnAction((event)->
				{
					requete.setText("SELECT nom_du_champ1, nom_du_champ2 FROM nom_de_la_table");
				});

				insert.setOnAction((event)->
				{
					requete.setText("INSERT INTO table (nom_du_champ1,nom_du_champ2) VALUES ('valeur1','valeur2')");
				});

				update.setOnAction((event)->
				{
					requete.setText("UPDATE nom_de_la_table SET nom_du_champ1 = 'nouvelle_valeur1', nom_du_champ2 = 'nouvelle_valeur2' WHERE condition");
				});

				execute.setOnAction((event)->
				{
					erreur.setText("");
					SQL( nameBD.getText() ,requete.getText());
				});

				deroulant.valueProperty().addListener((observable,oldValue,newValue)->
				{
					cadre2.getSelectionModel().select(1);
			        tableview= new TableView();
			        buildData(newValue);
			        tableview.setPrefHeight(362);
			        tableview.setPrefWidth(801);
			        tableview.setLayoutX(0);
			        tableview.setLayoutY(133);
			        tables.getChildren().add(tableview);
				});

				delete.setOnAction((event)->
				{
					requete.setText("DELETE FROM nom_de_la_table WHERE condition");
				});

					cadre.getSelectionModel().selectedItemProperty().addListener((observable,oldvalue,newvalue)->
					{
				       if (cadre.getSelectionModel().getSelectedIndex()==2)
				       {
				    	   try
				    	   {
				    		   Class.forName("org.sqlite.JDBC");
				    		   Connection c = DriverManager.getConnection("jdbc:sqlite:"+nameBD.getText()+".db");
				    		   ObservableList<String> recup= getTablesList(c);
				    		   bd.setItems(recup);
				    		   deroulant.setItems(getTablesList(c));
			               }
				    	   catch(Exception a )
				    	   {

				    	   }
				       }
					});

					bd.getSelectionModel().selectedItemProperty().addListener((observable,oldvalue,newvalue)->
					{
						cadre2.getSelectionModel().select(1);
			            tableview= new TableView();
			            buildData(newvalue);
			            tableview.setPrefHeight(362);
			            tableview.setPrefWidth(801);
			            tableview.setLayoutX(0);
			            tableview.setLayoutY(133);
			            tables.getChildren().add(tableview);

					});



					repertoire.getSelectionModel().selectedItemProperty().addListener((observable,oldvalue,newvalue)->
					{
						nameBD.setText(newvalue);
						connect(newvalue);
			     	});

					                           //CODE CSS
					valid1.setStyle("-fx-background-color: rgb(191,128,128); -fx-text-fill: white;");
					update.setStyle("-fx-background-color: rgb(191,128,128); -fx-text-fill: white;");
					select.setStyle("-fx-background-color: rgb(191,128,128); -fx-text-fill: white;");
					delete.setStyle("-fx-background-color: rgb(191,128,128); -fx-text-fill: white;");
					delete.setStyle("-fx-background-color: rgb(191,128,128); -fx-text-fill: white;");
					create.setStyle("-fx-background-color: rgb(191,128,128); -fx-text-fill: white;");
					execute.setStyle("-fx-background-color: rgb(191,128,128); -fx-text-fill: white;");
					propos.setStyle("-fx-background-color: rgb(191,128,128); -fx-text-fill: white;");
					deroulant.setStyle("-fx-background-color: rgb(191,128,128); -fx-text-fill: white;");
					insert.setStyle("-fx-background-color: rgb(191,128,128); -fx-text-fill: white;");
					nameBD.setStyle("-fx-font: normal bold 20px 'serif' ");
				    repertoire.setStyle("-fx-font: normal bold 20px 'serif' ");
				    bd.setStyle("-fx-font: normal bold 20px 'serif' ");
				    tables.setStyle("-fx-font: normal bold 20px 'serif' ");
				    requete.setStyle("-fx-font: normal bold 20px 'serif' ");
				    erreur.setStyle("-fx-font: normal bold 20px 'serif' ");
			}

                              //CONNEXION A UNE BASE DE DONNEE DEJA EXISTANTE
			public void connect(String nom)
			{
				 try
			     {
			   	  cadre.getSelectionModel().select(2);
			        Class.forName("org.sqlite.JDBC");
			       Connection c = DriverManager.getConnection("jdbc:sqlite:"+nom+".db");
			        visualisation.setDisable(false);
			        sql.setDisable(false);
			        creationBD.setDisable(true);
			     }
			     catch ( Exception e )
			     {
			        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			        System.exit(0);
			        System.out.println("Opened database successfully");
			     }
			}

                                   //REMPLISSAGE DE TABLES

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void buildData(String table)
			{
			      Connection c ; //variable ce connection
			      data = FXCollections.observableArrayList(); //variable de collection de données de la table

			      try
			      {
			    	  //CONNECTION A LA BASE DE DONNEE

				         Class.forName("org.sqlite.JDBC");
				         String lien = "jdbc:sqlite:"+nameBD.getText()+".db";
				         c = DriverManager.getConnection(lien);


				         //EXECUTION DE REQUETE DE SELECTION

			        String sql = "SELECT * FROM "+table;
			        ResultSet rs = c.createStatement().executeQuery(sql);


			           //AFFICHAGE DYNAMIQUE DE LA TABLE DES COLLONNES

			        for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++)
			        {

			            final int j = i;
			            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));

			            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>()
			            {
			                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param)
			                {
			                    return new SimpleStringProperty(param.getValue().get(j).toString());
			                }
			            });
			            col.setPrefWidth(755/rs.getMetaData().getColumnCount());
			            col.setResizable(false);
			            col.setStyle("-fx-font-size:15px");
			            tableview.getColumns().addAll(col);
			        }

			                                //AJOUT DE DONNEES AUX COLONNE
			        while(rs.next())
			        {
			            //Iterate Row
			            ObservableList<String> row = FXCollections.observableArrayList();
			            for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++)
			            {
			                //Iterate Column
			                row.add(rs.getString(i));
			            }
			            data.add(row);

			        }

			        tableview.setItems(data);  //AJOUT DES COLONNES A LA TABLEVIEW
			        c.close();
			      }

			      catch(Exception e)
			      {
			          e.printStackTrace();
			      }
			}
			                //GERE LES REQUETE SQL EXECUTE
			public void SQL(String nameBD , String requete)
			{
				Connection c = null;
			      Statement stmt = null;
			      try
			      {
			         Class.forName("org.sqlite.JDBC");
			         String lien = "jdbc:sqlite:"+nameBD+".db";
			         c = DriverManager.getConnection(lien);

			         stmt = c.createStatement();
			         String sql = requete;
			         stmt.executeUpdate(sql);
			         stmt.close();

			         c.close();
			         Alert alert = new Alert(AlertType.INFORMATION);
			   	  	 alert.setTitle("Fx-SQL");
			   	  	 alert.setHeaderText(null);
			   	  	 alert.setContentText("Operation effectuée avec succes");
			   	     alert.show();


			      }
			      catch ( Exception e )      //GERE LES ERREURS SURVENU A L'EXECUTION DES REQUETES
			      {

			    	  erreur.setText(e.getMessage().substring(e.getMessage().lastIndexOf("(")+1, e.getMessage().length()-1));
			    	  Alert alert = new Alert(AlertType.INFORMATION);
			    	  alert.setTitle("Fx-SQL");
			    	  alert.setHeaderText(null);
			    	  alert.setContentText("Operation non effectuée");
			    	  alert.show();
			      }
			}

                                   //METHODE STATIQUE QUI RETOURNE LA LISTE DES TABLES D'UNE BASE DE DONNEE
			public static ObservableList<String> getTablesList(Connection conn) throws SQLException
			{

				ObservableList<String> listofTable = FXCollections.observableArrayList();;

			    DatabaseMetaData md = conn.getMetaData();

			    ResultSet rs = md.getTables(null, null, "%", null);

			    while (rs.next())
			    	{
			        	if (rs.getString(4).equalsIgnoreCase("TABLE"))
			        	{
			            listofTable.add(rs.getString(3));
			        	}
			    	}
			    return listofTable;
			}
	}

