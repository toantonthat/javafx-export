package com.export.model;

import java.io.OutputStream;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io3.Save;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveFile extends Save {
	private static final Logger logger = LoggerFactory.getLogger(SaveFile.class);
	private OutputStream output;

	public OutputStream getOutput() {
		return output;
	}

	public void setOutput(OutputStream output) {
		this.output = output;
	}

	public SaveFile(OpcPackage p) {
		super(p);
	}

	public boolean save() {
		if (output != null) {
			logger.info("save : output != null ");
			try {
				return super.save(output);
			} catch (Docx4JException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
}
