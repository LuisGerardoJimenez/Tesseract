package mx.tesseract.editor.bs;

import java.util.ArrayList;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service("tokenBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class TokenBs {

	private static String tokenSeparator1 = "·";
	private static String tokenSeparator2 = ":";
	/*
	 * Estructura para referenciar elementos:
	 * 
	 * Regla de negocio: RN·Número:Nombre Entidad: ENT·Nombre Caso de uso:
	 * CU·ClaveMódulo·Número:Nombre Pantalla: IU·ClaveMódulo·Número:Nombre
	 * Mensaje: MSG·Número:Nombre Actor: ACT·Nombre Término Glosario: GLS·Nombre
	 * Atributo: ATR·Entidad:Nombre Paso:
	 * P·ClaveCasoUso·NúmeroCasoUso:NombreCasoUso:ClaveTrayectoria·Número
	 * Trayectoria:
	 * TRAY·ClaveCasoUso·NúmeroCasoUso:NombreCasoUso:ClaveTrayectoria Acción:
	 * ACC·ClavePantalla·NúmeroPantalla:NombrePantalla:Nombre Parámetros (Msj.)
	 * PARAM·Nombre
	 */
	public static String tokenRN = "RN" + tokenSeparator1;
	public static String tokenENT = "ENT" + tokenSeparator1;
	public static String tokenCU = "CU" + tokenSeparator1;
	public static String tokenIU = "IU" + tokenSeparator1;
	public static String tokenMSG = "MSG" + tokenSeparator1;
	public static String tokenACT = "ACT" + tokenSeparator1;
	public static String tokenGLS = "GLS" + tokenSeparator1;
	public static String tokenATR = "ATR" + tokenSeparator1;
	public static String tokenP = "P" + tokenSeparator1;
	public static String tokenTray = "TRAY" + tokenSeparator1;
	public static String tokenACC = "ACC" + tokenSeparator1;
	public static String tokenPARAM = "PARAM" + tokenSeparator1;

	public ArrayList<String> procesarTokenIpunt(String cadena) {
		ArrayList<String> tokens = new ArrayList<String>();
		String pila = "";
		String token = "";
		char caracter;
		boolean almacenar = false;
		if (cadena != null) {
			int longitud = cadena.length();
			for (int i = 0; i < longitud; i++) {
				caracter = cadena.charAt(i);
				if (almacenar) {
					if (puntoFinal(longitud, i, caracter)) {
						tokens.add(token);
					} else if (ignorarEscape(cadena, i, caracter)) {
						tokens.add(token);
						pila = "";
						almacenar = false;
					} else if (longitud - 1 == i) {
						token += caracter;
						tokens.add(token);
					} else {
						token += caracter;
					}

				} else {
					if (caracter == ' ') {
						pila = "";
					} else if (!ignorarEscape(cadena, i, caracter)) {
						pila += cadena.charAt(i);
						/*
						 * Si el sistema encuentra un token, el estado de la
						 * pila será almacenar.
						 */
						if (esToken(pila)) {
							almacenar = true;
							token = pila;
						}
					}
				}
			}
		}
		return tokens;
	}
	
	public static boolean puntoFinal(int longitud, int i, char caracter) {
		if (caracter == '.' && longitud - 1 == i) {
			return true;
		}
		return false;
	}
	
	public static boolean ignorarEscape(String cadena, int i, char caracter) {
		if (puntoSeguido(cadena, i, caracter) || espacio(cadena, i, caracter)
				|| coma(cadena, i, caracter) || puntoComa(cadena, i, caracter)
				|| caracter == '\n' || caracter == '\t' || caracter == '\r') {
			return true;
		}

		return false;
	}
	
	public static boolean esToken(String pila) {
		if (pila.equals(tokenRN) || pila.equals(tokenENT)
				|| pila.equals(tokenCU) || pila.equals(tokenIU)
				|| pila.equals(tokenMSG) || pila.equals(tokenACT)
				|| pila.equals(tokenGLS) || pila.equals(tokenATR)
				|| pila.equals(tokenP) || pila.equals(tokenTray)
				|| pila.equals(tokenACC) || pila.equals(tokenPARAM)) {
			return true;
		}
		return false;
	}
	
	public static boolean puntoSeguido(String cadena, int i, char caracter) {
		if (caracter == '.') {
			if (cadena.length() - 1 > i) {
				if (cadena.charAt(i + 1) == ' ') {
					return true;
				} else {
					return false;
				}

			}
		}

		return false;
	}
	
	public static boolean espacio(String cadena, int i, char caracter) {
		if (caracter == ' ') {
			return true;
		}
		return false;
	}
	
	private static boolean coma(String cadena, int i, char caracter) {
		if (caracter == ',') {
			if (cadena.length() - 1 > i) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean puntoComa(String cadena, int i, char caracter) {
		if (caracter == ';') {
			return true;
		}
		return false;
	}
	
	/*
	 * El método ArrayList<String> segmentarToken(String @token) construye un
	 * ArrayList de segmentos de un token, por ejemplo: para el token
	 * "ACT·Profesor", la función devolvería un ArrayList con dos elementos: [0]
	 * = ACT [1] = Profesor
	 * 
	 * Parámetros:
	 * 
	 * @token: Token que se desea segmentar
	 */
	public ArrayList<String> segmentarToken(String token) {
		String segmento = "";
		ArrayList<String> segmentos = new ArrayList<String>();
		String caracterAt;

		for (int i = 0; i < token.length(); i++) {
			caracterAt = token.charAt(i) + "";
			if (caracterAt.equals(tokenSeparator1)
					|| caracterAt.equals(tokenSeparator2)) {
				segmentos.add(segmento);
				segmento = "";
			} else {
				segmento += token.charAt(i);
			}
		}
		if (segmento != "") {
			segmentos.add(segmento);
		}
		return segmentos;
	}
	
	
}
