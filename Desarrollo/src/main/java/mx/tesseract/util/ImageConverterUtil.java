package mx.tesseract.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageConverterUtil {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	
	public static byte[] parsePNGB64StringToBytes(String string) {
		if(string != null && !string.equals("")) {
			int index = string.indexOf("base64") + 7;
			return string.substring(index).getBytes();
		} 
		return null;
	}

	/**
	 * Método que te devuelve una cadena con el contenido de un byte Array de un PNG en BASE 64
	 * @param bytes El byte Array a convertir
	 * @return String con el contenido del archivo en BASE 64 y prefijo data:image/png;base64,
	 * @throws UnsupportedEncodingException 
	 */
	public static String parseBytesToPNGB64String(byte[] bytes) throws UnsupportedEncodingException {
		String string = null;
		if(bytes != null) {
			string = new String(bytes, "UTF-8");
			string = "data:image/png;base64,".concat(string);
		} 
		return string;
	}
	
	/**
	 * Método que te devuelve una cadena con el contenido de un archivo en BASE 64
	 * @param file Archivo a convertir
	 * @return String con el contenido del archivo en BASE 64
	 */
	public static String parseFileToBASE64String(File file) {
		String cadenaB64 = null;
		try {
			byte[] fileContent = FileUtils.readFileToByteArray(new File(file.getAbsolutePath()));
			cadenaB64 = Base64.getEncoder().encodeToString(fileContent);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(ImageConverterUtil.class + ": " + "parseFileToBASE64String", e);
		}
		return cadenaB64;
	}
	
	/**
	 * Método que te devuelve una byte[] con el contenido de un archivo en BASE 64
	 * @param file Archivo a convertir
	 * @return byte Array con el contenido del archivo en BASE 64
	 */
	public static byte[] parseFileToBASE64ByteArray(File file) {
		byte[] byteB64 = null;
		try {
			byte[] fileContent = FileUtils.readFileToByteArray(new File(file.getAbsolutePath()));
			String cadenaB64 = Base64.getEncoder().encodeToString(fileContent);
			byteB64 = cadenaB64.getBytes();
		} catch (Exception e) {
			TESSERACT_LOGGER.error(ImageConverterUtil.class + ": " + "parseFileToBASE64ByteArray", e);
		}
		return byteB64;
	}
	
	public static String parseBytesToB64String(Object obj) {
		String string = null;
		try { 
			byte[] bytes = (byte[])obj;
			if(bytes != null) {
				string = new String(bytes);
			} 
		} catch (Exception e) {
			TESSERACT_LOGGER.error(ImageConverterUtil.class + ": " + "parseBytesToB64String", e);
		}
		return string;
	}
}
