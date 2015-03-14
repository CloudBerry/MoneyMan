package application;
	
import java.math.BigDecimal;
import java.util.HashMap;

import moneyman.CurrencyConverter;
import moneyman.CurrencyFetcherInterface;
import moneyman.URLDataFetcher;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	
	private GridPane root;
	private SuperListPanel fromPanel;
	private SuperListPanel toPanel;
	private Label toLabel = new Label("to");
	
	private TextField fromField = new TextField();
	private Button convertBTN = new Button("Convert");
	private TextField toField = new TextField();
	
	CurrencyFetcherInterface fetcher;
	
	@Override
	public void start(Stage primaryStage) {
		//Fetch currency data
		fetcher = new URLDataFetcher();		//Try online
		if (fetcher.getAvailableCurrencyCount() == 0) {
			//TODO: TRY OFFLINE
		}
		
		try {
			root = new GridPane();
			root.setHgap(10);
			root.setVgap(10);
			
			fromPanel = new SuperListPanel(fetcher.getAvailableCurrencies().toArray());
			toPanel = new SuperListPanel(fetcher.getAvailableCurrencies().toArray());
			toLabel.getStyleClass().add("tolabel");
			
			fromField.setPromptText("How much?");
			fromField.setMaxWidth(150);
			toField.setEditable(false);
			toField.setMaxWidth(150);
			
			convertBTN.setOnAction(this::buttonClicked);
			
			root.add(fromPanel, 0, 0);
			root.add(toLabel, 1, 0);
			root.add(toPanel, 2, 0);
			root.add(fromField, 0, 1);
			root.add(convertBTN, 1, 1);
			root.add(toField, 2, 1);
			
			root.setPadding(new Insets(15));
			Scene scene = new Scene(root);
			primaryStage.setTitle("MoneyMan 1.0");
			primaryStage.setResizable(false);
			try {
				primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icons/moneyman_icon_16.png")));
				primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icons/moneyman_icon_32.png")));
				primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icons/moneyman_icon_64.png")));
				primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icons/moneyman_icon_128.png")));
				primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icons/moneyman_icon_256.png")));
				primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icons/moneyman_icon_512.png")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			primaryStage.setIconified(true);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void buttonClicked(ActionEvent event){
		String inputdata = fromField.getText();
		try {
			BigDecimal inputNumber = new BigDecimal(inputdata);
			BigDecimal output = CurrencyConverter.convert(fetcher.getCurrencyMap(), (String)fromPanel.getSelected(), (String)toPanel.getSelected(), inputNumber);
			System.out.println(output);
			toField.setText(output.toString());
		} catch (Exception e) {
			System.err.println("Error parsing input data...");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
