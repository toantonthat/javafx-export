package com.export.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io3.Save;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.core.io.ClassPathResource;

import com.export.model.SaveFile;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

public class WordUtil {
	private final static String MIME_TYPE = "application/octet-stream";
	
	public static String getMimeType(File file) {
		ContentInfo info;
		try {
			info = (new ContentInfoUtil()).findMatch(file);
			if (info != null)
				return info.getMimeType();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static void generateDocument(File template, String outputDocument, HashMap<String, String> replace) {
		if (template != null && replace != null && replace.size() > 0 && outputDocument != null
				&& !outputDocument.isEmpty()) {
			if (template.exists()) {
				// 1. Check file extension with simplemagic
				String mimeType = URLConnection.guessContentTypeFromName(template.getName());
				if (mimeType == null) {
					mimeType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml";
				}
				if (mimeType != null && mimeType.equals(MIME_TYPE)) {
					WordprocessingMLPackage wordMLPackage;
					try {
						// 2. Load template
						wordMLPackage = WordprocessingMLPackage.load(template);
						MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
						// 3. Replace placeholders
						VariablePrepare.prepare(wordMLPackage);
						documentPart.variableReplace(replace);

						// 4. output docx
						OutputStream output = new FileOutputStream(outputDocument);
						String errorMessage = "Sorry. The file you are looking for does not exist";
						OutputStream outputStream = new FileOutputStream(outputDocument);
						outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
						outputStream.close();
						
						Save saver = new Save(wordMLPackage);
						saver.save(output);
					} catch (Docx4JException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Invalid document mime type");
				}
			}
		}
	}
	
	public static SaveFile exportWord(File fileOutput, String fileName, HashMap<String, String> mappings) {
		try {
			File file = new ClassPathResource("files/" + fileName).getFile();
			WordprocessingMLPackage wordMLPackage;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			wordMLPackage = WordprocessingMLPackage.load(file);
			VariablePrepare.prepare(wordMLPackage);
			wordMLPackage.getMainDocumentPart().variableReplace(mappings);
			wordMLPackage = wordMLPackage.getMainDocumentPart().convertAltChunks();
			wordMLPackage.save(out);
			SaveFile saver = new SaveFile(wordMLPackage);
			OutputStream output = new FileOutputStream(fileOutput);
			saver.setOutput(output);
			return saver;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
