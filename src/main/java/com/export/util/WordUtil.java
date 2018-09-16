package com.export.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io3.Save;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.export.model.SaveFile;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

public class WordUtil {
	private static final Logger logger = LoggerFactory.getLogger(WordUtil.class);
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
	
	/*
	 * ## References : https://stackoverflow.com/questions/42655397/read-files-from-boot-inf-classes
	 */
	public static SaveFile exportWord(File fileOutput, String fileName, HashMap<String, String> mappings) {
		try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources("classpath*:static/*.docx");
			for (Resource resource : resources) {
				if (resource.exists()) {
					if (resource.getFilename().equals(fileName)) {
						InputStream inputStream = resource.getInputStream();
						File file = File.createTempFile(resource.getFilename(), ".docx");
						try {
							FileUtils.copyInputStreamToFile(inputStream, file);
						} finally {
							IOUtils.closeQuietly(inputStream);
						}
						logger.info("file :: " + file.getPath());
						if (file.exists()) {
							logger.info("file exists");
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
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("error " + e.toString());
		}
		return null;
	}
}
