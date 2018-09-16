package com.export.controller;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.export.model.FileModel;
import com.export.model.SaveFile;
import com.export.presentation.ProgressStage;
import com.export.util.Utils;
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
	private static final Logger logger = LoggerFactory.getLogger(ExportController.class);
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
	
	private HashMap<String, String> initDonCapGiayChungNhanSDD() {
		HashMap<String, String> mappings = new HashMap<String, String>();
		String hoVaTen = tenField.getText();
		String namSinh = namSinhField.getText();
		String soCMND = soCMNDField.getText();
		LocalDate ngayCap = ngayCapfield.getValue();
		String noiCap = noiCapField.getText();
		String ngheNghiep = ngheNghiepField.getText();
		String diaChiThuongTru = txaDiaChiThuongTru.getText();
		String soThuaDat = thuaDatField.getText();
		String soBanDo = "";
		String diaChiTai = diaChiField.getText();
		String dienTichThuaDat = dienTichField.getText();
		String dienTichSuDungRieng = suDungRiengField.getText();
		String mucDichSuDung = suDungRiengField.getText();
		String tuThoiDiem = tuThoiDiemField.getText();
		String thoiHanDeNghiSuDungDat = txaThoiHanSDD.getText();
		String nguonGocSuDung = txaNguonGocSD.getText();
		
		mappings.put("hoVaTen", hoVaTen);
		mappings.put("namSinh", namSinh);
		mappings.put("soCMND", soCMND);
		mappings.put("ngayCap", "............");
		mappings.put("noiCap", noiCap);
		mappings.put("ngheNghiep", ngheNghiep);
		mappings.put("diaChiThuongTru", diaChiThuongTru);
		mappings.put("diaChiThuongTru", diaChiThuongTru);

		mappings.put("soThuaDat", soThuaDat);
		mappings.put("soBanDo", soBanDo);
		mappings.put("diaChiTai", diaChiTai);
		mappings.put("dienTichThuaDat", dienTichThuaDat);
		mappings.put("dienTichSuDungRieng", dienTichSuDungRieng);
		mappings.put("tuThoiDiem", tuThoiDiem);
		mappings.put("thoiHanDeNghiSuDungDat", thoiHanDeNghiSuDungDat);
		mappings.put("nguonGocSuDung", nguonGocSuDung);
		mappings.put("mucDichSuDung", mucDichSuDung);
		
		if ("".equals(hoVaTen)) {
			mappings.put("hoVaTen", "......................");
		}
		if ("".equals(namSinh)) {
			mappings.put("namSinh", "............");
		}
		if ("".equals(soCMND)) {
			mappings.put("soCMND", "............");
		}
		if (ngayCap != null) {
			mappings.put("ngayCap", Utils.formatDate(ngayCap, "dd/MM/yyyy"));
		}
		if ("".equals(noiCap)) {
			mappings.put("noiCap", "......................");
		}
		if ("".equals(ngheNghiep)) {
			mappings.put("ngheNghiep", "......................");
		}
		if ("".equals(diaChiThuongTru)) {
			mappings.put("diaChiThuongTru", ".............................................");
		}
		if ("".equals(soThuaDat)) {
			mappings.put("soThuaDat", "...............");
		}
		if ("".equals(soBanDo)) {
			mappings.put("soBanDo", "...............");
		}
		if ("".equals(diaChiTai)) {
			mappings.put("diaChiTai", ".............................................");
		}
		if ("".equals(dienTichThuaDat)) {
			mappings.put("dienTichThuaDat", ".......");
		}
		if ("".equals(dienTichSuDungRieng)) {
			mappings.put("dienTichSuDungRieng", ".......");
		}
		if ("".equals(mucDichSuDung)) {
			mappings.put("mucDichSuDung", ".............................................");
		}
		if ("".equals(tuThoiDiem)) {
			mappings.put("tuThoiDiem", ".......");
		}
		if ("".equals(thoiHanDeNghiSuDungDat)) {
			mappings.put("thoiHanDeNghiSuDungDat", ".............................................");
		}
		if ("".equals(nguonGocSuDung)) {
			mappings.put("nguonGocSuDung", ".............................................");
		}
		return mappings;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void handlePrint(ActionEvent event) {
		try {
			HashMap<String, String> mappings = initDonCapGiayChungNhanSDD();
			Stage primaryStage = new Stage();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Lưu tệp tin");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DOC files (*.doc)", "*.doc");
			fileChooser.getExtensionFilters().add(extFilter);
			File fileOutput = fileChooser.showSaveDialog(primaryStage);
			
			if (fileOutput != null) {
				final String fileName = "DON-CAP-GIAY-CN.docx"; //4m²
				Task<Void> task = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						// TODO Auto-generated method stub
						try {
							SaveFile saver = WordUtil.exportWord(fileOutput, fileName, mappings);
							if (saver.save()) {
								logger.info("saved");
								saver.getOutput().close();
								updateProgress(10, 10);
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.info("Exception " + e.toString());
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
