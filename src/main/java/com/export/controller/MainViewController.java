package com.export.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainViewController extends Application implements Initializable {

	@FXML
	private StackPane stackPane;
	@FXML
	private VBox overflowContainer;
	@FXML
	private ToolBar toolBar;
	@FXML
	private HBox hbox;
	@FXML
	private Label lbCurrentUserName, lblMenu;
	@FXML
	private AnchorPane sideAnchor;
	@FXML
	public VBox vbox;
	@FXML
	public MenuBar menubar;
	@FXML
	private Menu mnDanhSachDonThu;
	@FXML
	private MenuItem menuItemDDKQSDD;
	@FXML
	private Button btnComponent;
	@FXML
	private AnchorPane holderPane;
	@FXML
	private TabPane tabParent;
	private SingleSelectionModel<Tab> selectionModel;
	private static int flagForTab = -1;
	private static int flagForDanhSachDon;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		addEvents();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@FXML
	void openComponent(ActionEvent event) {

	}
	
	@FXML
	void openCustomer(ActionEvent event) {

	}
	
	private void loadDanhSachDon() throws IOException {
		Tab tab = new Tab();
		tab.setText("Danh sách đơn");
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Export.fxml"));
		tab.setContent(root);
		tabParent.getTabs().add(tab);
	}
	
	private void addEvents() {
		menuItemDDKQSDD.setOnAction(e -> {
			System.out.println("menuItemDDKQSDD");
			try {
				loadDanhSachDon();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		});
	}
}
