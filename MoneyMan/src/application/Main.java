package application;
	
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import quickconverter.QuickConverter;
import moneyman.CacheWriter;
import moneyman.CachedDataFetcher;
import moneyman.CountryListReader;
import moneyman.CurrencyConverter;
import moneyman.CurrencyFetcherInterface;
import moneyman.URLDataFetcher;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	
	private GridPane root;
	private SuperListPanel fromPanel;
	private SuperListPanel toPanel;
	private Label toLabel = new Label("→");
	private Label infolabel = new Label();
	
	private TextField fromField = new TextField();
	private Button convertBTN = new Button("Convert");
	private TextField toField = new TextField();
	
	CurrencyFetcherInterface fetcher;
	HashMap<String, String[]> countryinfo;
	
	
	@Override
	public void start(Stage primaryStage) {
		//Fetch currency data
		fetcher = new URLDataFetcher();		//Try online
		if (fetcher.getAvailableCurrencyCount() == 0) {
			System.err.println("Could not connect to the internet, loading cache");
			fetcher = new CachedDataFetcher();
		}
		try {
			CacheWriter.cacheCurrencyData(fetcher.getCurrencyMap(), fetcher.getlongestLastUpdate());
		} catch (IOException e1) {
			System.err.println("Could not store cache...");
			e1.printStackTrace();
		}
		countryinfo = new CountryListReader().getCountries();
		try {
			root = new GridPane();
			root.setHgap(10);
			root.setVgap(10);
			
			fromPanel = new SuperListPanel(fetcher.getAvailableCurrencies().toArray(), countryinfo);
			toPanel = new SuperListPanel(fetcher.getAvailableCurrencies().toArray(), countryinfo);
			toLabel.getStyleClass().add("tolabel");
			
			fromField.setPromptText("How much?");
			toField.setEditable(false);

			
			convertBTN.setOnAction(this::buttonClicked);

			
			toLabel.setTextAlignment(TextAlignment.CENTER);
			root.setHalignment(toLabel, HPos.CENTER);
			setLastUpdatedText();
			
			root.add(fromPanel, 0, 0);
			root.add(toLabel, 1, 0);
			root.add(toPanel, 2, 0);
			root.add(fromField, 0, 1);
			root.add(convertBTN, 1, 1);
			root.add(toField, 2, 1);
			root.add(infolabel, 0, 2, 3, 1);
			
			root.setPadding(new Insets(15));
			Scene scene = new Scene(root);
			primaryStage.setTitle("MoneyMan 1.0");
			primaryStage.setResizable(false);
			
			primaryStage.setIconified(true);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.toFront();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setLastUpdatedText(){
		if (fetcher.getlongestLastUpdate().isEqual(LocalDate.now())){
			infolabel.setText("Currencies last updated today");
		} else {
			infolabel.setText("Currencies last updated "+fetcher.getlongestLastUpdate().toString());
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
		
//		Platform.runLater(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				try {
//					new QuickConverter().start(new Stage());
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
		launch(args);
	}
}
