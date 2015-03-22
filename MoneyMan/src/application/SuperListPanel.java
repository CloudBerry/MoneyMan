package application;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class SuperListPanel extends BorderPane{
	
	
	Object[] allData;
	
	//UI Components
	TextField searchField;
	
	ObservableList<Object> listDataHandler;
	ListView<Object> listView;
	
	
	public SuperListPanel(Object[] data, HashMap<String, String[]> countryInfo) {
		allData = data;
		
		//Add country description if found
		for (int i = 0; i < allData.length; i++){
			if (countryInfo.containsKey(allData[i])){
				allData[i] = ((String) allData[i]).concat(", "+countryInfo.get(allData[i])[1]);
			}
		}
		
		searchField = new TextField();
		searchField.addEventFilter(KeyEvent.ANY, event);
		searchField.setPromptText("Type here to search");
		
		
		listDataHandler = FXCollections.observableArrayList();
		listDataHandler.addAll(allData);
		
		listView = new ListView<>(listDataHandler);
		listView.getSelectionModel().select(0);
		listView.requestFocus();
		
		
		setMaxWidth(200);
		setMaxHeight(200);
		setTop(searchField);
		setCenter(listView);
		
	}
	
	
	
	public TextField getSearchField() {
		return searchField;
	}

	public ListView<Object> getListView() {
		return listView;
	}



	EventHandler<KeyEvent> event = new EventHandler<KeyEvent>() {
		
		@Override
		public void handle(KeyEvent event) {
			if (event.getEventType() == KeyEvent.KEY_RELEASED){
				Object[] filtered = simpleFilter(searchField.getText());
				if (filtered.length == 0){
					searchField.setStyle("-fx-text-inner-color: red;");
					
				} else {
					searchField.setStyle("-fx-text-inner-color: black;");
				}
				listDataHandler.removeAll(allData);
				listDataHandler.addAll(filtered);
				if (filtered.length == 1){
					listView.getSelectionModel().select(0);
				}
			}
		}
	};
	
	public Object getSelected(){
		if (((String)listView.getSelectionModel().getSelectedItem()).length() != 3){	//Remove currency description
			return ((String)listView.getSelectionModel().getSelectedItem()).split(",")[0];
		}
		return listView.getSelectionModel().getSelectedItem();
	}
	
	private Object[] simpleFilter(String searchTerm){
		ArrayList<Object> results = new ArrayList<>();
		for (Object o:allData){
			if (o.toString().toLowerCase().contains(searchTerm.toLowerCase())){
				results.add(o);
			}
		}
		Object[] resData = new Object[results.size()];
		return results.toArray(resData);
		
	}
	
}
