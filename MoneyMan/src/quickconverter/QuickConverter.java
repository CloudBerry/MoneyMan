package quickconverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.sun.org.apache.xalan.internal.utils.FeatureManager.Feature;

import languageparser.LanguageParser;
import languageparser.ParsedResult;
import moneyman.CurrencyConverter;
import moneyman.CurrencyFetcherInterface;
import moneyman.URLDataFetcher;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class QuickConverter extends Application {

	private CurrencyFetcherInterface fetcher;

	private TextField textField = new TextField();
	private Label exampleLabel = new Label("Example: 100 USD to NOK");
	private boolean calculated = false;

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {

			Thread thread = new Thread(){
				@Override public void run() {
					fetcher = new URLDataFetcher();		//Try online
					if (fetcher.getAvailableCurrencyCount() == 0) {
						//TODO: TRY OFFLINE
					}
				}
			};
			thread.start();
			thread.join();
			

			textField.setFont(new Font(30));
			exampleLabel.setPadding(new Insets(2));
			textField.setOnKeyPressed(event -> keyEventHandler(event));
			BorderPane root = new BorderPane();
			root.setCenter(textField);
			root.setBottom(exampleLabel);

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	private void keyEventHandler(KeyEvent event){
		textField.setStyle("-fx-text-inner-color: black;");
		if (calculated) {
			textField.setText("");
			textField.positionCaret(0);
			calculated = false;
		}


		if (event.getCode() == KeyCode.ESCAPE){
			Platform.exit();
		} else if (event.getCode() == KeyCode.ENTER){
			ParsedResult parsedResult = LanguageParser.parseString(textField.getText(), fetcher.getAvailableCurrencies(), true);
			if (parsedResult == null){
				textField.setStyle("-fx-text-inner-color: red;");
			}
			BigDecimal result = CurrencyConverter.convert(fetcher.getCurrencyMap(), parsedResult.getFromCurrency(), parsedResult.getToCurrency(), parsedResult.getAmount());
			result = result.setScale(2, RoundingMode.CEILING);
			textField.setStyle("-fx-text-inner-color: green;");
			textField.setText(parsedResult.getAmount().toString()+" "+parsedResult.getFromCurrency()+" = "+result.toString()+" "+parsedResult.getToCurrency());
			calculated = true;
			textField.positionCaret(textField.getText().length());
		}
	}


	public static void main(String[] args) {

		launch(args);
	}

}
