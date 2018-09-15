package com.export.controller;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.export.model.FileModel;
import com.export.model.SaveFile;
import com.export.presentation.ProgressStage;
import com.export.util.WordUtil;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ExportController implements Initializable {
	@FXML
	private TextField tenField, soCMNDField, namSinhField, noiCapField, ngheNghiepField, diaChiThuongTruField, thuaDatField,
	diaChiField, dienTichField, mucDichSuDungField, tuThoiDiemField, suDungRiengField;
	
	@FXML
	private DatePicker ngayCapfield;
	@FXML
	private TextArea descriptionArea, txaThoiHanSDD, txaNguonGocSD, txaDiaChiThuongTru;

	@FXML
	private Button printButton;

	private FileModel fileModel;

	public FileModel getFileModel() {
		return fileModel;
	}

	public void setFileModel(FileModel fileModel) {
		this.fileModel = fileModel;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void handlePrint(ActionEvent event) {
		System.out.println("handlePrint");
		System.out.println("tenField " + tenField.getText());
		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			mappings.put("hoVaTen", tenField.getText());
			mappings.put("namSinh", "1991");
			
			Stage primaryStage = new Stage();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Lưu tệp tin");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DOC files (*.doc)", "*.doc");
			fileChooser.getExtensionFilters().add(extFilter);
			File fileOutput = fileChooser.showSaveDialog(primaryStage);
			if (fileOutput != null) {
				final String fileName = "DON-CAP-GIAY-CN.docx";
				Task<Void> task = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						// TODO Auto-generated method stub
						try {
							SaveFile saver = WordUtil.exportWord(fileOutput, fileName, mappings);
							if (saver.save()) {
								saver.getOutput().close();
								updateProgress(10, 10);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return null;
					}
				};
				
				try {
					new ProgressStage(primaryStage, task);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
    public void handleCancel(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
    }
	
	@FXML
    public void closeAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
