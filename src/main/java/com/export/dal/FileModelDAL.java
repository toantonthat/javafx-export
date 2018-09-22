package com.export.dal;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.export.model.FileModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileModelDAL {
	private URL loadPath(String url) {
		if (!"".equals(url)) {
			return getClass().getResource("/static/" + url);
		}
		return null;
	}
	
	public ObservableList<FileModel> getFiles() {
		ObservableList<FileModel> list = FXCollections.observableArrayList();
		List<FileModel> files = new ArrayList<>();
		files.add(new FileModel(1, "04a/ĐK", "ĐƠN ĐĂNG KÝ, CẤP GIẤY CHỨNG NHẬN QUYỀN SỬ DỤNG ĐẤT",
				loadPath("DON-CAP-GIAY-CN.docx").getPath(), "DON-CAP-GIAY-CN.docx", 
				"ĐƠN ĐĂNG KÝ, CẤP GIẤY CHỨNG NHẬN QUYỀN SỬ DỤNG ĐẤT, QUYỀN SỞ HỮU NHÀ Ở VÀ TÀI SẢN KHÁC GẮN LIỀN VỚI ĐẤT"));
		files.stream().forEach(list::add);
		return list;
	}
}
