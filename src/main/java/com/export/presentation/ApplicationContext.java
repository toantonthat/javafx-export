package com.export.presentation;

import java.io.File;
import java.util.Stack;

import com.export.presentation.builder.SIFStage;

public class ApplicationContext {
	public static Stack<SIFStage> stageStack = new Stack<SIFStage>();
	public static File tracedFile;
}
