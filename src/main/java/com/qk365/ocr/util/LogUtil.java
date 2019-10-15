package com.qk365.ocr.util;

public class LogUtil {
	public static String getPrintExceptionString(Exception ex){
		StringBuilder sb = new StringBuilder();
		sb.append("[error: ").append(ex.getMessage()).append("] \r\n");
		sb.append("[stack trace: ").append(getStackTraceString(ex.getStackTrace())).append("] \r\n");
		return sb.toString();
	}

	public static String getStackTraceString(StackTraceElement[] stackTraceElements) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement traceElement : stackTraceElements) {
			sb.append("\tat ").append(traceElement).append("\r\n");
		}
		return sb.toString();
	}
}
