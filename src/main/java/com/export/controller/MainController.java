package com.export.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

import com.export.dal.FileModelDAL;
import com.export.model.FileModel;

import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

@Service
public class MainController implements Initializable {

	@FXML
	private TableView<FileModel> fileTable;
	@FXML
	private TableColumn<FileModel, Long> idColumn;
	@FXML
	private TableColumn<FileModel, String> nameColumn, typeColumn, descriptionColumn;

	private ObservableList<FileModel> FILELIST = FXCollections.observableArrayList();

	private FileModelDAL fileDAL = new FileModelDAL();
	
	@FXML
    private Button exportButton, viewButton;
	@FXML
	private Button menu;
	@FXML
	private VBox drawer;

	@FXML
	CategoryAxis ixAxis;
	@FXML
	CategoryAxis pxAxis;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		drawerAction();
		loadData();
		idColumn.setCellValueFactory(new PropertyValueFactory<>("ma"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		fileTable.setItems(FILELIST);
		
		exportButton.disableProperty().bind(Bindings.isEmpty(fileTable.getSelectionModel().getSelectedItems()));
	}

	private void loadData() {
		// TODO Auto-generated method stub
		if (!FILELIST.isEmpty()) {
			FILELIST.clear();
		}
		System.out.println("fileDAL.getFiles() " + fileDAL.getFiles().size());
		FILELIST.addAll(fileDAL.getFiles());
	}

	private void drawerAction() {

		TranslateTransition openNav = new TranslateTransition(new Duration(350), drawer);
		openNav.setToX(0);
		TranslateTransition closeNav = new TranslateTransition(new Duration(350), drawer);
		menu.setOnAction((ActionEvent evt) -> {
			if (drawer.getTranslateX() != 0) {
				openNav.play();
				menu.getStyleClass().remove("hamburger-button");
				menu.getStyleClass().add("open-menu");
			} else {
				closeNav.setToX(-(drawer.getWidth()));
				closeNav.play();
				menu.getStyleClass().remove("open-menu");
				menu.getStyleClass().add("hamburger-button");
			}
		});
	}

	@FXML
	public void exportAction(ActionEvent event) throws Exception {
		FileModel selectedModel = fileTable.getSelectionModel().getSelectedItem();
        //int selectedModelId = fileTable.getSelectionModel().getSelectedIndex();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/Export.fxml")));
        ExportController controller = new ExportController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Product");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        controller.setFileModel(selectedModel);
        fileTable.getSelectionModel().clearSelection();
	}

	@FXML
	public void logoutAction(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
		/*Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
		Stage stage = new Stage();
		root.setOnMousePressed((MouseEvent e) -> {
			xOffset = e.getSceneX();
			yOffset = e.getSceneY();
		});
		root.setOnMouseDragged((MouseEvent e) -> {
			stage.setX(e.getScreenX() - xOffset);
			stage.setY(e.getScreenY() - yOffset);
		});
		Scene scene = new Scene(root);
		stage.setTitle("Inventory:: Version 1.0");
		stage.getIcons().add(new Image("/images/logo.png"));
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.show();*/
	}

	@SuppressWarnings("unused")
	private void windows(String path, String title, ActionEvent event) throws Exception {
		double width = ((Node) event.getSource()).getScene().getWidth();
		double height = ((Node) event.getSource()).getScene().getHeight();

		Parent root = FXMLLoader.load(getClass().getResource(path));
		Scene scene = new Scene(root, width, height);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle(title);
		stage.getIcons().add(new Image("/images/logo.png"));
		stage.setScene(scene);
		stage.show();
	}
}
