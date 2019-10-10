package mx.tesseract.editor.bs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.dao.AccionDAO;
import mx.tesseract.editor.dao.AtributoDAO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.dao.ModuloDAO;
import mx.tesseract.editor.dao.PantallaDAO;
import mx.tesseract.editor.dao.TipoParametroDAO;
import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.Actor;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.CasoUsoActor;
import mx.tesseract.editor.entidad.CasoUsoReglaNegocio;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.Entrada;
import mx.tesseract.editor.entidad.Extension;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.editor.entidad.Parametro;
import mx.tesseract.editor.entidad.Paso;
import mx.tesseract.editor.entidad.PostPrecondicion;
import mx.tesseract.editor.entidad.ReferenciaParametro;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.editor.entidad.Salida;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.editor.entidad.TipoParametro;
import mx.tesseract.editor.entidad.Trayectoria;
import mx.tesseract.enums.ReferenciaEnum;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.enums.ReferenciaEnum.TipoSeccion;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;


@Service("tokenBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class TokenBs {

	@Autowired
	private PantallaDAO pantallaDAO;
	
	@Autowired
	private AccionDAO accionDAO;
	
	@Autowired
	private ElementoDAO elementoDAO;
	
	@Autowired
	private AtributoDAO atributoDAO;
	
	@Autowired
	private ModuloDAO moduloDAO;
	
	/*@Autowired
	private EntidadDAO entidadDAO;
	
	@Autowired
	private TerminoGlosarioDAO terminoGlosarioDAO;*/
	
	/*@Autowired
	private MensajeDAO mensajeDAO;*/
	
	@Autowired
	private TipoParametroDAO tipoParametroDAO;
	
	@Autowired
	private GenericoDAO genericoDAO;
	
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
	
	/*
	 * El método ArrayList<Object> convertirToken_Objeto(String @redaccion,
	 * Proyecto @proyecto) se encarga de generar objetos con base en los tokens
	 * contenidos en una cadena.
	 * 
	 * Parámetros:
	 * 
	 * @redaccion: Cadena cuyo contenido incluye los tokens en su versión
	 * edición, por ejemplo: ATR.Escuela:Nombre. Se utilizará para procesar los
	 * tokens y convertirlos a objetos.
	 * 
	 * @proyecto: Proyecto en cuestión. Se utilizará para únicamente entregar
	 * como respuesta una lista de objetos, presentes para el proyecto actual.
	 * 
	 * 
	 * Ejemplo:
	 * 
	 * Para la cadena 'El sistema muestra la pantalla IU.SF.1:Gestionar
	 * elementos con el mensaje MSG.1:Operación exitosa', el método entregaría
	 * como resultado un ArrayList con dos objetos; uno de tipo Pantalla y otro
	 * de tipo Mensaje, con sus respectivas variables cargadas.
	 */
	public ArrayList<Object> convertirToken_Objeto(String redaccion,
			Proyecto proyecto, Integer idModulo) {

		ArrayList<String> tokens = procesarTokenIpunt(redaccion);
		ArrayList<Object> objetos = new ArrayList<Object>();
		ArrayList<String> segmentos;

		Atributo atributo;
		Actor actor;
		Pantalla pantalla;
		Accion accion;
		Modulo modulo;
		CasoUso casodeuso;
		Trayectoria trayectoria;
		Paso paso;
		
		for (String token : tokens) {
			segmentos = segmentarToken(token);
			switch (ReferenciaEnum.getTipoReferencia(segmentos.get(0))) {
			case ACCION: // ACC.IUM.NUM:PANTALLA:NOMBRE_ACC =
							// ACC.IUSF.7:Registrar_incendio:Aceptar
				if (segmentos.size() != 5) {
					errorEnToken("la", "acción");
				}
				pantalla = pantallaDAO.findByIdProyectoAndIdModuloAndNombre(proyecto.getId(),Clave.IU,idModulo ,segmentos.get(2) );
				if (pantalla == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "pantalla",
							segmentos.get(1) + segmentos.get(2), "registrada" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: La pantalla "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrada", "MSG15",
							parametros);
				}

				//accion = accionDAO.consultarAccion(segmentos.get(4).replaceAll("_", " "), pantalla);
				accion = accionDAO.findByNombreAndIdPantalla(segmentos.get(4)
						.replaceAll("_", " "),pantalla.getId());
				
				if (accion == null) {
					String[] parametros = {
							"la",
							"accion",
							segmentos.get(4).replaceAll("_", " ")
									+ " de la pantalla " + segmentos.get(1)
									+ segmentos.get(2), "registrada" };
					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: La acción "
									+ segmentos.get(4).replaceAll("_", " ")
									+ " de la pantalla " + segmentos.get(1)
									+ segmentos.get(2) + " no está registrada",
							"MSG15", parametros);
				}
				objetos.add(accion);
				break;
			case ATRIBUTO: // ATR.ENTIDAD_A_B:NOMBRE_ATT
				if (segmentos.size() != 3) {
					errorEnToken("el", "atributo");
				}
				Entidad entidad = elementoDAO.findAllByIdProyectoAndNombreAndClave(Entidad.class, proyecto.getId(),segmentos.get(1)
						.replaceAll("_", " "), Clave.ENT);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: La entidad "
									+ segmentos.get(1).replaceAll("_", " ")
									+ " no está registrada", "MSG15",
							parametros);
				}

				//atributo = atributoDAO.consultarAtributo(segmentos.get(2).replaceAll("_", " "), entidad);
				atributo = atributoDAO.findAtributoByNombreAndEntidad(segmentos.get(2)
						.replaceAll("_", " "), entidad.getId());
				
				if (atributo == null) {
					String[] parametros = {
							"el",
							"atributo",
							segmentos.get(2).replaceAll("_", " ")
									+ " de la entidad " + segmentos.get(1),
							"registrado" };
					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: El atributo "
									+ segmentos.get(2) + " de la entidad "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}
				objetos.add(atributo);
				break;
			case ACTOR: // ACT.NOMBRE_ACT
				if (segmentos.size() != 2) {
					errorEnToken("el", "actor");
				}
				actor = elementoDAO.findAllByIdProyectoAndNombreAndClave(Actor.class, proyecto.getId(),segmentos.get(1)
						.replaceAll("_", " "), Clave.ACT);
				if (actor == null) {
					String[] parametros = {
							// Construcción del mensaje de error;
							"el", "actor",
							segmentos.get(1).replaceAll("_", " "), "registrado" };
					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: El actor "
									+ segmentos.get(1).replaceAll("_", " ")
									+ " no está registrado", "MSG15",
							parametros);
				}
				objetos.add(actor);

				break;
			case CASOUSO: // CU.MODULO.NUMERO:NOMBRE_CU
				if (segmentos.size() != 4) {
					errorEnToken("el", "caso de uso");
				}
				modulo = moduloDAO.findModuloByName(segmentos.get(1));
				if (modulo == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "modulo",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: El módulo "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}

				casodeuso = elementoDAO.findAllByIdProyectoAndNombreAndClave(CasoUso.class, proyecto.getId(),segmentos.get(2)
						.replaceAll("_", " "), Clave.CUAD);
				if (casodeuso == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "caso de uso",
							tokenCU + segmentos.get(1) + segmentos.get(2),
							"registrado" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: El caso de uso "
									+ token + " no está registrado", "MSG15",
							parametros);
				}
				objetos.add(casodeuso);

				break;
			case ENTIDAD: // ENT.NOMBRE_ENT
				if (segmentos.size() != 2) {
					errorEnToken("la", "entidad");
				}
				//entidad = entidadDAO.consultarEntidad(segmentos.get(1).replaceAll("_", " "), proyecto);
				entidad = elementoDAO.findAllByIdProyectoAndNombreAndClave(Entidad.class, proyecto.getId(),segmentos.get(1)
						.replaceAll("_", " "), Clave.ENT);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: La entidad "
									+ segmentos.get(1) + " no está registrada",
							"MSG15", parametros);
				}
				objetos.add(entidad);
				break;
			case TERMINOGLS: // GLS.NOMBRE_GLS
				if (segmentos.size() != 2) {
					errorEnToken("el", "término");
				}
				//TerminoGlosario terminoGlosario = terminoGlosarioDAO.consultarTerminoGlosario(segmentos.get(1).replaceAll("_", " "), proyecto);
				TerminoGlosario terminoGlosario = elementoDAO.findAllByIdProyectoAndNombreAndClave(TerminoGlosario.class, proyecto.getId(),segmentos.get(1)
						.replaceAll("_", " "), Clave.GLS);
				if (terminoGlosario == null) {
					String[] parametros = { "el", "término",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: El término no está registrado",
							"MSG15", parametros);
				}
				objetos.add(terminoGlosario);
				break;
			case PANTALLA: // IU.MODULO.NUMERO:NOMBRE_IU
				if (segmentos.size() != 4) {
					errorEnToken("la", "pantalla");
				}
				modulo = moduloDAO.findModuloByClave(segmentos.get(1));
				if (modulo == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "modulo",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: El módulo "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}

				//pantalla = pantallaDAO.consultarPantalla(modulo,segmentos.get(2));
				pantalla = pantallaDAO.findByIdProyectoAndIdModuloAndNumero(proyecto.getId(), Clave.IU, modulo.getId(),segmentos.get(2));
				if (pantalla == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "pantalla",
							tokenIU + segmentos.get(1) + segmentos.get(2),
							"registrada" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: La pantalla "
									+ token + " no está registrada", "MSG15",
							parametros);
				}
				objetos.add(pantalla);

				break;
			case MENSAJE: // MSG.NUMERO:NOMBRE_MSG
				if (segmentos.size() != 3) {
					errorEnToken("el", "mensaje");
				}
				Mensaje mensaje = elementoDAO.findAllByIdProyectoAndNombreAndClave(Mensaje.class, proyecto.getId(),segmentos.get(2)
						.replaceAll("_", " "), Clave.MSG);
				if (mensaje == null) {
					String[] parametros = { "el", "mensaje",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: El mensaje "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}
				objetos.add(mensaje);
				break;
			case REGLANEGOCIO: // RN.NUMERO:NOMBRE_RN
				if (segmentos.size() != 3) {
					errorEnToken("la", "regla de negocio");
				}
				ReglaNegocio reglaNegocio = elementoDAO.findAllByIdProyectoAndNombreAndClave(ReglaNegocio.class, proyecto.getId(),segmentos.get(2)
						.replaceAll("_", " "), Clave.RN);
				if (reglaNegocio == null) {
					String[] parametros = { "la", "regla de negocio",
							segmentos.get(2).replaceAll("_", " "), "registrada" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: La regla de negocio "
									+ segmentos.get(2) + " no está registrada",
							"MSG15", parametros);
				}
				objetos.add(reglaNegocio);
				break;
			// TRAY·CUSF·001:s:A
			
			case TRAYECTORIA: // TRAY.CUMODULO.NUM:NOMBRECU:CLAVETRAY
				if (segmentos.size() != 5) {
					errorEnToken("la", "trayectoria");
				}
				elementoDAO.findAllByIdProyectoAndNombreAndClave(CasoUso.class, proyecto.getId(),segmentos.get(2)
						.replaceAll("_", " "), Clave.CUAD);
				//casodeuso = new CasoUsoDAO().consultarCasoUso(segmentos.get(1),segmentos.get(2), proyecto);
				casodeuso = elementoDAO.findAllByIdProyectoAndNombreAndClave(CasoUso.class, proyecto.getId(),segmentos.get(2)
						.replaceAll("_", " "), Clave.CUAD);
				if (casodeuso == null) {
					String[] parametros = { "el", "caso de uso",
							segmentos.get(1) + segmentos.get(2), "registrado" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: El caso de uso "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrado", "MSG15",
							parametros);
				}

				trayectoria = null;
				for (Trayectoria t : casodeuso.getTrayectorias()) {
					if (t.getClave().equals(segmentos.get(4))) {
						trayectoria = t;
					}
				}

				if (trayectoria == null) {
					String[] parametros = {
							"la",
							"trayectoria",
							segmentos.get(4) + " del caso de uso "
									+ segmentos.get(1) + segmentos.get(2),
							"registrada" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: La trayectoria "
									+ segmentos.get(4) + " del caso de uso "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrada", "MSG15",
							parametros);
				}
				objetos.add(trayectoria);
				break;

			case PASO: // P.CUMODULO.NUM:NOMBRECU:CLAVETRAY.NUMERO
				if (segmentos.size() != 6) {
					errorEnToken("el", "paso");
				}
				//casodeuso = new CasoUsoDAO().consultarCasoUso(segmentos.get(1),segmentos.get(2), proyecto);
				casodeuso = elementoDAO.findAllByIdProyectoAndNombreAndClave(CasoUso.class, proyecto.getId(),segmentos.get(2)
						.replaceAll("_", " "), Clave.CUAD);
				if (casodeuso == null) {
					String[] parametros = { "el", "caso de uso",
							segmentos.get(1) + segmentos.get(2), "registrado" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: El caso de uso "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrado", "MSG15",
							parametros);
				}

				trayectoria = null;
				for (Trayectoria t : casodeuso.getTrayectorias()) {
					if (t.getClave().equals(segmentos.get(4))) {
						trayectoria = t;
					}
				}

				if (trayectoria == null) {
					String[] parametros = {
							"la",
							"trayectoria",
							segmentos.get(4) + " del caso de uso "
									+ segmentos.get(1) + segmentos.get(2),
							"registrada" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: La trayectoria "
									+ segmentos.get(4) + "del caso de uso"
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrada", "MSG15",
							parametros);
				}
				paso = null;
				for (Paso p : trayectoria.getPasos()) {
					if (p.getNumero() == Integer.parseInt(segmentos.get(5))) {
						paso = p;
					}
				}

				if (paso == null) {
					String[] parametros = {
							"el",
							"paso",
							segmentos.get(5) + " de la trayectoria "
									+ segmentos.get(4) + " del caso de uso "
									+ segmentos.get(1) + segmentos.get(2),
							"registrado" };

					throw new TESSERACTException(
							"TokenBs.convertirToken_Objeto: El paso "
									+ segmentos.get(5) + "de la trayectoria"
									+ segmentos.get(4) + "del caso de uso "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrado", "MSG15",
							parametros);
				}

				objetos.add(paso);
				break;
			default:
				break;

			}
		}

		return objetos;
	}
	
	/*
	 * El método String codificarCadenaToken(String @redaccion, Proyecto
	 * 
	 * @proyecto se encarga de codificar la cadena a su versión base de datos
	 * (cruda).
	 * 
	 * Parámetros:
	 * 
	 * @cadenaCodificada: Cadena cuyo contenido incluye los tokens en su versión
	 * edición, por ejemplo: ATR·Producto:Peso.
	 * 
	 * Ejemplo:
	 * 
	 * El resultado de decodificar la cadena "ATR·Producto:Peso." sería "ATR·1",
	 * siendo "1" el id del atributo "Peso".
	 */
	public String codificarCadenaToken(String redaccion,
			Proyecto proyecto, Integer idModulo) {

		ArrayList<String> tokens = procesarTokenIpunt(redaccion);
		ArrayList<String> segmentos;
		Pantalla pantalla;
		Accion accion;
		Modulo modulo;
		CasoUso casoUso;
		Entidad entidad;
		Atributo atributo;
		Trayectoria trayectoria;
		Paso paso;
		for (String token : tokens) {
			segmentos = segmentarToken(token);
			switch (ReferenciaEnum.getTipoReferencia(segmentos.get(0))) {
			case ACCION:

				if (segmentos.size() != 5) {
					errorEnToken("la", "acción");
				}
				pantalla = pantallaDAO.findByIdProyectoAndIdModuloAndNumero(proyecto.getId(), Clave.IU, idModulo,segmentos.get(2));
				//new PantallaDAO().consultarPantalla(segmentos.get(1).replaceAll("_", " "), segmentos.get(2), proyecto);
				if (pantalla == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "pantalla",
							segmentos.get(1) + segmentos.get(2), "registrada" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: La pantalla "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrada", "MSG15",
							parametros);
				}

				accion = accionDAO.findByNombreAndIdPantalla(segmentos.get(4)
						.replaceAll("_", " "),pantalla.getId());
				if (accion == null) {
					String[] parametros = {
							"la",
							"accion",
							segmentos.get(4).replaceAll("_", " ")
									+ " de la pantalla " + segmentos.get(1)
									+ segmentos.get(2), "registrada" };
					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: La acción"
									+ segmentos.get(4).replaceAll("_", " ")
									+ "de la pantalla" + segmentos.get(1)
									+ segmentos.get(2) + "no está registrada",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, tokenACC + accion.getId());
				break;
			case ATRIBUTO:
				if (segmentos.size() != 3) {
					errorEnToken("el", "atributo");
				}
				entidad = elementoDAO.findAllByIdProyectoAndNombreAndClave(Entidad.class, proyecto.getId(),segmentos.get(1)
						.replaceAll("_", " "), Clave.ENT);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: La entidad "
									+ segmentos.get(1).replaceAll("_", " ")
									+ " no está registrada", "MSG15",
							parametros);
				}

				atributo = atributoDAO.findAtributoByNombreAndEntidad(segmentos.get(2)
						.replaceAll("_", " "), entidad.getId());
				if (atributo == null) {
					String[] parametros = {
							"el",
							"atributo",
							segmentos.get(2).replaceAll("_", " ")
									+ " de la entidad " + segmentos.get(1),
							"registrado" };
					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: El atributo"
									+ segmentos.get(2) + "de la entidad"
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token,
						tokenATR + atributo.getId());
				break;
			case ACTOR:
				if (segmentos.size() != 2) {
					errorEnToken("el", "actor");
				}
				Actor actor = elementoDAO.findAllByIdProyectoAndNombreAndClave(Actor.class, proyecto.getId(),segmentos.get(1)
						.replaceAll("_", " "), Clave.ACT);
				if (actor == null) {
					String[] parametros = {
							// Construcción del mensaje de error;
							"el", "actor",
							segmentos.get(1).replaceAll("_", " "), "registrado" };
					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: El actor no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, tokenACT + actor.getId());

				break;
			case CASOUSO:
				if (segmentos.size() != 4) {
					errorEnToken("el", "caso de uso");
				}
				modulo = moduloDAO.findModuloByName(segmentos.get(1));
				if (modulo == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "modulo",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: El módulo "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}

				casoUso = elementoDAO.findAllByIdProyectoAndNombreAndClave(CasoUso.class, proyecto.getId(),segmentos.get(2)
						.replaceAll("_", " "), Clave.CUAD);
				if (casoUso == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "caso de uso",
							segmentos.get(1) + segmentos.get(2), "registrado" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: El caso de uso "
									+ token + " no está registrado", "MSG15",
							parametros);
				}
				redaccion = redaccion.replace(token, tokenCU + casoUso.getId());
				break;
			case ENTIDAD:
				if (segmentos.size() != 2) {
					errorEnToken("la", "entidad");
				}
				entidad = elementoDAO.findAllByIdProyectoAndNombreAndClave(Entidad.class, proyecto.getId(),segmentos.get(1)
						.replaceAll("_", " "), Clave.ENT);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: La entidad "
									+ segmentos.get(1) + " no está registrada",
							"MSG15", parametros);
				}
				redaccion = redaccion
						.replace(token, tokenENT + entidad.getId());
				break;
			case TERMINOGLS:
				if (segmentos.size() != 2) {
					errorEnToken("el", "término");
				}
				TerminoGlosario terminoGlosario = elementoDAO.findAllByIdProyectoAndNombreAndClave(TerminoGlosario.class, proyecto.getId(),segmentos.get(1)
						.replaceAll("_", " "), Clave.GLS);
				if (terminoGlosario == null) {
					String[] parametros = { "el", "término",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: El término no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, segmentos.get(0)
						+ tokenSeparator1 + terminoGlosario.getId());
				break;
			case PANTALLA:
				if (segmentos.size() != 4) {
					errorEnToken("la", "pantalla");
				}
				modulo = moduloDAO.findModuloByClave(segmentos.get(1));
				if (modulo == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "modulo",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: El módulo "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}

				pantalla = pantallaDAO.findByIdProyectoAndIdModuloAndNumero(proyecto.getId(), Clave.IU, modulo.getId(),segmentos.get(2));
				//new PantallaDAO().consultarPantalla(modulo,segmentos.get(2));
				if (pantalla == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "pantalla", token,
							"registrada" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: La pantalla "
									+ token + " no está registrada", "MSG15",
							parametros);
				}
				redaccion = redaccion
						.replace(token, tokenIU + pantalla.getId());
				break;
			case MENSAJE:
				if (segmentos.size() != 3) {
					errorEnToken("el", "mensaje");
				}
				Mensaje mensaje = elementoDAO.findAllByIdProyectoAndNombreAndClave(Mensaje.class, proyecto.getId(),segmentos.get(2)
						.replaceAll("_", " "), Clave.MSG);
				if (mensaje == null) {
					String[] parametros = { "el", "mensaje",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: El mensaje "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion
						.replace(token, tokenMSG + mensaje.getId());
				break;
			case REGLANEGOCIO:
				if (segmentos.size() != 3) {
					errorEnToken("la", "regla de negocio");
				}
				ReglaNegocio reglaNegocio = elementoDAO.findAllByIdProyectoAndNombreAndClave(ReglaNegocio.class, proyecto.getId(),segmentos.get(2)
						.replaceAll("_", " "), Clave.RN);
				if (reglaNegocio == null) {
					String[] parametros = { "la", "regla de negocio",
							segmentos.get(2).replaceAll("_", " "), "registrada" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: La regla de negocio "
									+ segmentos.get(2) + " no está registrada",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token,
						tokenRN + reglaNegocio.getId());
				break;
			case TRAYECTORIA:
				if (segmentos.size() != 5) {
					errorEnToken("la", "trayectoria");
				}
				trayectoria = null;
				casoUso = elementoDAO.findAllByIdProyectoAndNombreAndClave(CasoUso.class, proyecto.getId(),segmentos.get(2)
						.replaceAll("_", " "), Clave.CUAD);
				if (casoUso == null) {
					String[] parametros = { "el", "caso de uso",
							segmentos.get(1) + segmentos.get(2), "registrado" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: El caso de uso "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrado", "MSG15",
							parametros);
				}
				for (Trayectoria t : casoUso.getTrayectorias()) {
					if (t.getClave().equals(segmentos.get(4))) {
						trayectoria = t;
					}
				}
				if (trayectoria == null) {
					String[] parametros = {
							"la",
							"trayectoria",
							segmentos.get(4) + " del caso de uso "
									+ segmentos.get(1) + segmentos.get(2),
							"registrada" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: La trayectoria "
									+ segmentos.get(4) + "del caso de uso"
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrada", "MSG15",
							parametros);
				}
				redaccion = redaccion.replace(token,
						tokenTray + trayectoria.getId());
				break;
			case PASO:
				if (segmentos.size() != 6) {
					errorEnToken("el", "paso");
				}
				casoUso = elementoDAO.findAllByIdProyectoAndNombreAndClave(CasoUso.class, proyecto.getId(),segmentos.get(2)
						.replaceAll("_", " "), Clave.CUAD);
				if (casoUso == null) {
					String[] parametros = { "el", "caso de uso",
							segmentos.get(1) + segmentos.get(2), "registrado" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: El caso de uso "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrado", "MSG15",
							parametros);
				}

				trayectoria = null;
				paso = null;
				for (Trayectoria t : casoUso.getTrayectorias()) {
					if (t.getClave().equals(segmentos.get(4))) {
						trayectoria = t;
						for (Paso p : trayectoria.getPasos()) {
							if (p.getNumero() == Integer.parseInt(segmentos
									.get(5))) {
								paso = p;
								break;
							}
						}
					}
				}
				if (trayectoria == null) {
					String[] parametros = {
							"la",
							"trayectoria",
							segmentos.get(4) + " del caso de uso "
									+ segmentos.get(3), "registrada" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: La trayectoria "
									+ segmentos.get(4) + "del caso de uso"
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrada", "MSG15",
							parametros);
				}

				if (paso == null) {
					String[] parametros = {
							"el",
							"paso",
							segmentos.get(5) + " de la trayectoria "
									+ segmentos.get(4) + " del caso de uso "
									+ segmentos.get(1) + segmentos.get(2),
							"registrado" };

					throw new TESSERACTValidacionException(
							"TokenBs.convertirToken_Objeto: El paso "
									+ segmentos.get(5) + " de la trayectoria "
									+ segmentos.get(4) + " del caso de uso "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrado", "MSG15",
							parametros);
				}

				redaccion = redaccion.replace(token, tokenP + paso.getId());
				break;
			default:
				break;

			}
		}
		if (!redaccion.isEmpty())
			return "$" + redaccion;
		else
			return "";
	}
	
	public static void errorEnToken(String articulo, String elemento) {
		String[] parametros = { articulo, elemento, };
		throw new TESSERACTException(
				"TokenBs.errorEnToken: El token ingresado para " + articulo
						+ " " + elemento + " es inválido.", "MSG27", parametros);
	}
	
	public void almacenarObjetosToken(ArrayList<Object> objetos,
			CasoUso casouso, TipoSeccion tipoSeccion) {

		// Secciones:
		CasoUsoActor casoUsoActor;
		Entrada entrada;
		Salida salida;
		CasoUsoReglaNegocio casoUsoReglas;

		// Elementos
		Actor actor;
		Atributo atributo;
		TerminoGlosario termino;
		Mensaje mensaje;
		ReglaNegocio reglaNegocio;

		for (Object objeto : objetos) {
			switch (ReferenciaEnum.getTipoRelacion(
					ReferenciaEnum.getTipoReferencia(objeto), tipoSeccion)) {
			case ACTOR_ACTORES:
				actor = (Actor) objeto;
				casoUsoActor = new CasoUsoActor(
						casouso, actor);
				if (!duplicadoActor_Actores(casouso.getActores(),
						casoUsoActor)) {
					casouso.getActores().add(casoUsoActor);
				}
				break;
			case ATRIBUTO_ENTRADAS:
				atributo = (Atributo) objeto;
				entrada = new Entrada(
						tipoParametroDAO.consultarTipoParametroByNombre("Atributo"), casouso);
				entrada.setAtributo(atributo);
				if (!TokenBs.duplicadoAtributo_Entradas(casouso.getEntradas(),
						entrada)) {
					casouso.getEntradas().add(entrada);
				}
				break;
			case TERMINOGLS_ENTRADAS:
				termino = (TerminoGlosario) objeto;
				entrada = new Entrada(
						tipoParametroDAO.consultarTipoParametroByNombre("Término del glosario"),
						casouso);
				entrada.setTerminoGlosario(termino);
				if (!TokenBs.duplicadoTermino_Entradas(casouso.getEntradas(),
						entrada)) {
					casouso.getEntradas().add(entrada);
				}
				break;
			case TERMINOGLS_SALIDAS:
				termino = (TerminoGlosario) objeto;
				salida = new Salida(
						tipoParametroDAO.consultarTipoParametroByNombre("Término del glosario"),
						casouso);
				salida.setTerminoGlosario(termino);
				if (!TokenBs.duplicadoTermino_Salidas(casouso.getSalidas(),
						salida)) {
					casouso.getSalidas().add(salida);
				}
				break;
			case MENSAJE_SALIDAS:
				mensaje = (Mensaje) objeto;
				salida = new Salida(tipoParametroDAO.consultarTipoParametroByNombre("Mensaje"), casouso);
				salida.setMensaje(mensaje);
				if (!TokenBs.duplicadoMensaje_Salidas(casouso.getSalidas(),
						salida)) {
					casouso.getSalidas().add(salida);
				}
				break;
			case ATRIBUTO_SALIDAS:
				atributo = (Atributo) objeto;
				salida = new Salida(tipoParametroDAO.consultarTipoParametroByNombre("Atributo"), casouso);
				salida.setAtributo(atributo);
				if (!TokenBs.duplicadoAtributo_Salidas(casouso.getSalidas(),
						salida)) {
					casouso.getSalidas().add(salida);
				}
				break;
			case REGLANEGOCIO_REGLASNEGOCIOS:
				reglaNegocio = (ReglaNegocio) objeto;
				casoUsoReglas = new CasoUsoReglaNegocio(casouso, reglaNegocio);
				casoUsoReglas.setReglaNegocio(reglaNegocio);
				if (!TokenBs.duplicadoRegla_Reglas(casouso.getReglas(),
						casoUsoReglas)) {
					casouso.getReglas().add(casoUsoReglas);
				}
				break;

			default:
				break;

			}
		}
	}

	public void almacenarObjetosToken(ArrayList<Object> objetos,
			CasoUso casouso, TipoSeccion tipoSeccion,
			PostPrecondicion postPrecondicion) {


		// Elementos
		ReferenciaParametro referenciaParametro = null;
		Accion accion;
		Atributo atributo;
		Actor actor;
		TipoParametro tipoParametro;
		CasoUso casoUso;
		Entidad entidad;
		Mensaje mensaje;
		Pantalla pantalla;
		ReglaNegocio reglaNegocio;
		Paso paso;
		TerminoGlosario terminoGlosario;
		Trayectoria trayectoria;

		for (Object objeto : objetos) {
			switch (ReferenciaEnum.getTipoRelacion(
					ReferenciaEnum.getTipoReferencia(objeto), tipoSeccion)) {

			case ACCION_POSTPRECONDICIONES:
				accion = (Accion) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Acción");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setAccionDestino(accion);

				break;
			case ACTOR_POSTPRECONDICIONES:
				actor = (Actor) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Actor");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(actor);
				break;
			case ATRIBUTO_POSTPRECONDICIONES:
				atributo = (Atributo) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Atributo");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setAtributo(atributo);

				break;
			case CASOUSO_POSTPRECONDICIONES:
				casoUso = (CasoUso) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Caso de uso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(casoUso);
				break;
			case ENTIDAD_POSTPRECONDICIONES:
				entidad = (Entidad) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Entidad");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(entidad);
				break;
			case MENSAJE_POSTPRECONDICIONES:
				mensaje = (Mensaje) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Mensaje");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(mensaje);
				break;
			case PANTALLA_POSTPRECONDICIONES:
				pantalla = (Pantalla) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Pantalla");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(pantalla);

				break;
			case PASO_POSTPRECONDICIONES:
				paso = (Paso) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Paso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setPasoDestino(paso);
				break;
			case REGLANEGOCIO_POSTPRECONDICIONES:
				reglaNegocio = (ReglaNegocio) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Regla de negocio");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(reglaNegocio);
				break;

			case TERMINOGLS_POSTPRECONDICIONES:
				terminoGlosario = (TerminoGlosario) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Término del glosario");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(terminoGlosario);
				break;
			case TRAYECTORIA_POSTPRECONDICIONES:
				trayectoria = (Trayectoria) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Trayectoria");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setTrayectoria(trayectoria);
				break;
			default:
				break;

			}
			if (referenciaParametro != null) {
				postPrecondicion.getReferencias().add(referenciaParametro);
				referenciaParametro.setPostPrecondicion(postPrecondicion);
			}

		}

	}

	public void almacenarObjetosToken(ArrayList<Object> objetos,
			TipoSeccion tipoSeccion, Extension extension) {

		// Elementos
		ReferenciaParametro referenciaParametro = null;
		TipoParametro tipoParametro;
		Paso paso;

		for (Object objeto : objetos) {
			switch (ReferenciaEnum.getTipoRelacion(
					ReferenciaEnum.getTipoReferencia(objeto), tipoSeccion)) {

			case PASO_EXTENSIONES:
				paso = (Paso) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Paso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setPasoDestino(paso);
				break;
			default:
				break;
			}
			if (referenciaParametro != null) {
				extension.getReferencias().add(referenciaParametro);
				referenciaParametro.setExtension(extension);
			}
		}
	}

	public void almacenarObjetosToken(ArrayList<Object> objetos,
			TipoSeccion tipoSeccion, Paso paso) {

		// Elementos
		ReferenciaParametro referenciaParametro = null;
		Accion accion;
		Atributo atributo;
		Actor actor;
		TipoParametro tipoParametro;
		CasoUso casoUso;
		Entidad entidad;
		Mensaje mensaje;
		Pantalla pantalla;
		ReglaNegocio reglaNegocio;
		TerminoGlosario terminoGlosario;
		Trayectoria trayectoria;
		Paso pasoDestino;
		for (Object objeto : objetos) {
			switch (ReferenciaEnum.getTipoRelacion(
					ReferenciaEnum.getTipoReferencia(objeto), tipoSeccion)) {

			case ACCION_PASOS:
				accion = (Accion) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Acción");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setAccionDestino(accion);

				break;
			case ACTOR_PASOS:
				actor = (Actor) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Actor");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(actor);
				break;
			case ATRIBUTO_PASOS:
				atributo = (Atributo) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Atributo");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setAtributo(atributo);

				break;
			case CASOUSO_PASOS:
				casoUso = (CasoUso) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Caso de uso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(casoUso);
				break;
			case ENTIDAD_PASOS:
				entidad = (Entidad) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Entidad");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(entidad);
				break;
			case MENSAJE_PASOS:
				mensaje = (Mensaje) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Mensaje");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(mensaje);
				break;
			case PANTALLA_PASOS:
				pantalla = (Pantalla) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Pantalla");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(pantalla);

				break;
			case PASO_PASOS:
				pasoDestino = (Paso) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Paso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setPasoDestino(pasoDestino);
				break;
			case REGLANEGOCIO_PASOS:
				reglaNegocio = (ReglaNegocio) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Regla de negocio");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(reglaNegocio);
				break;

			case TERMINOGLS_PASOS:
				terminoGlosario = (TerminoGlosario) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Término del glosario");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(terminoGlosario);
				break;
			case TRAYECTORIA_PASOS:
				trayectoria = (Trayectoria) objeto;
				tipoParametro = tipoParametroDAO.consultarTipoParametroByNombre("Trayectoria");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setTrayectoria(trayectoria);
				break;
			default:
				break;

			}
			if (referenciaParametro != null) {
				paso.getReferencias().add(referenciaParametro);
				referenciaParametro.setPaso(paso);
			}
		}

	}
	
	public static boolean duplicadoActor_Actores(List<CasoUsoActor> actores,
			CasoUsoActor casoUsoActor) {

		for (CasoUsoActor casoUsoActori : actores) {
			if (casoUsoActori.getActor().getId() == casoUsoActor.getActor()
					.getId()) {
				if (casoUsoActori.getCasouso().getId() == casoUsoActor
						.getCasouso().getId()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean duplicadoAtributo_Entradas(List<Entrada> entradas,
			Entrada entrada) {
		for (Entrada entradai : entradas) {
			if (entradai.getAtributo() != null && entrada.getAtributo() != null)
				if (entradai.getAtributo().getId() == entrada.getAtributo()
						.getId()) {
					if (entradai.getCasoUso().getId() == entrada.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}

	public static boolean duplicadoAtributo_Salidas(List<Salida> salidas,
			Salida salida) {
		for (Salida salidai : salidas) {
			if (salidai.getAtributo() != null)
				if (salidai.getAtributo().getId() == salida.getAtributo()
						.getId()) {
					if (salidai.getCasoUso().getId() == salida.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}

	public static boolean duplicadoMensaje_Salidas(List<Salida> salidas,
			Salida salida) {
		for (Salida salidai : salidas) {
			if (salidai.getMensaje() != null)
				if (salidai.getMensaje().getId() == salida.getMensaje().getId()) {
					if (salidai.getCasoUso().getId() == salida.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}

	public static boolean duplicadoRegla_Reglas(
			List<CasoUsoReglaNegocio> reglas, CasoUsoReglaNegocio casoUsoReglas) {
		for (CasoUsoReglaNegocio reglai : reglas) {
			if (reglai.getReglaNegocio().getId() == casoUsoReglas
					.getReglaNegocio().getId()) {
				if (reglai.getCasoUso().getId() == casoUsoReglas.getCasoUso()
						.getId()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean duplicadoTermino_Entradas(List<Entrada> entradas,
			Entrada entrada) {
		for (Entrada entradai : entradas) {
			if (entradai.getTerminoGlosario() != null)
				if (entradai.getTerminoGlosario().getId() == entrada
						.getTerminoGlosario().getId()) {
					if (entradai.getCasoUso().getId() == entrada.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}

	public static boolean duplicadoTermino_Salidas(List<Salida> salidas,
			Salida salida) {
		for (Salida salidai : salidas) {
			if (salidai.getTerminoGlosario() != null)
				if (salidai.getTerminoGlosario().getId() == salida
						.getTerminoGlosario().getId()) {
					if (salidai.getCasoUso().getId() == salida.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}
	
	/*
	 * El método String decodificarCadenasToken(String @cadenaCodificada) se
	 * encarga de decodificar la cadena a su versión de edición.
	 * 
	 * Parámetros:
	 * 
	 * @cadenaCodificada: Cadena cuyo contenido incluye los tokens en su versión
	 * base de datos (cruda), por ejemplo: ATR·1.
	 * 
	 * Ejemplo:
	 * 
	 * El resultado de decodificar la cadena "ATR·1" sería "ATR·Producto:Peso",
	 * siendo "ATR·Producto:Peso" el token para referenciar al atributo "Peso"
	 * de la entidad "Producto".
	 */
	public String decodificarCadenasToken(String cadenaCodificada) {
		String cadenaDecodificada = "";
		if (cadenaCodificada != null && !cadenaCodificada.equals("")) {
			String cadenaCodificadaBruta = cadenaCodificada.substring(1);
			ArrayList<String> tokens = procesarTokenIpunt(cadenaCodificadaBruta);
			ArrayList<String> segmentos;
			Modulo modulo;
			cadenaDecodificada = cadenaCodificadaBruta;

			for (String token : tokens) {
				segmentos = segmentarToken(token);
				switch (ReferenciaEnum.getTipoReferencia(segmentos.get(0))) {
				case ACCION:
					Accion accion = genericoDAO.findById(Accion.class, Integer
							.parseInt(segmentos.get(1)));
					if (accion == null) {
						cadenaDecodificada = "";
						break;
					} else {
						Pantalla pantalla = accion.getPantalla();
						cadenaDecodificada = remplazoToken(
								cadenaDecodificada,
								token,
								tokenACC
										+ pantalla.getClave()
										+ tokenSeparator1
										+ pantalla.getNumero()
										+ tokenSeparator2
										+ pantalla.getNombre()
												.replace(" ", "_")
										+ tokenSeparator2
										+ accion.getNombre().replace(" ", "_"));
					}
					break;
				case ATRIBUTO:
					Atributo atributo = genericoDAO.findById(Atributo.class, Integer
							.parseInt(segmentos.get(1)));
					if (atributo == null) {
						cadenaDecodificada = "";
						break;
					} else {
						Entidad entidad = atributo.getEntidad();
						cadenaDecodificada = remplazoToken(cadenaDecodificada,
								token,
								tokenATR
										+ entidad.getNombre().replace(" ", "_")
										+ tokenSeparator2
										+ atributo.getNombre()
												.replace(" ", "_"));
					}
					break;
				case ACTOR:
					Actor actor = elementoDAO.findById(Actor.class, Integer
							.parseInt(segmentos.get(1)), Clave.ACT);
					if (actor == null) {
						cadenaDecodificada = "";
						break;
					}
					cadenaDecodificada = remplazoToken(cadenaDecodificada,
							token,
							tokenACT + actor.getNombre().replace(" ", "_"));

					break;
				case CASOUSO:
					CasoUso casoUso = elementoDAO.findById(CasoUso.class, Integer
							.parseInt(segmentos.get(1)), Clave.CUAD);
					if (casoUso == null) {
						cadenaDecodificada = "";
						break;
					}
					modulo = casoUso.getModulo();
					cadenaDecodificada = remplazoToken(cadenaDecodificada,
							token, tokenCU + modulo.getClave()
									+ tokenSeparator1 + casoUso.getNumero()
									+ tokenSeparator2
									+ casoUso.getNombre().replace(" ", "_"));

					break;
				case ENTIDAD:
					Entidad entidad = elementoDAO.findById(Entidad.class, Integer
							.parseInt(segmentos.get(1)), Clave.ENT);
					if (entidad == null) {
						cadenaDecodificada = "";
						break;
					}
					cadenaDecodificada = remplazoToken(cadenaDecodificada,
							token,
							tokenENT + entidad.getNombre().replace(" ", "_"));

					break;
				case TERMINOGLS:
					TerminoGlosario terminoGlosario = elementoDAO.findById(TerminoGlosario.class, Integer
							.parseInt(segmentos.get(1)), Clave.GLS);
					if (terminoGlosario == null) {
						cadenaDecodificada = "";
					}
					cadenaDecodificada = remplazoToken(
							cadenaDecodificada,
							token,
							tokenGLS
									+ terminoGlosario.getNombre().replace(" ",
											"_"));
					break;
				case PANTALLA:
					Pantalla pantalla = elementoDAO.findById(Pantalla.class, Integer
							.parseInt(segmentos.get(1)), Clave.IU);
					if (pantalla == null) {
						cadenaDecodificada = "";
						break;
					}
					modulo = pantalla.getModulo();
					cadenaDecodificada = remplazoToken(cadenaDecodificada,
							token, tokenIU + modulo.getClave()
									+ tokenSeparator1 + pantalla.getNumero()
									+ tokenSeparator2
									+ pantalla.getNombre().replace(" ", "_"));

					break;

				case MENSAJE:
					Mensaje mensaje = elementoDAO.findById(Mensaje.class, Integer
							.parseInt(segmentos.get(1)), Clave.MSG);
					if (mensaje == null) {
						cadenaDecodificada = "";
					}
					cadenaDecodificada = remplazoToken(cadenaDecodificada,
							token, tokenMSG + mensaje.getNumero()
									+ tokenSeparator2
									+ mensaje.getNombre().replace(" ", "_"));
					break;
				case REGLANEGOCIO:
					
					ReglaNegocio reglaNegocio = elementoDAO.findById(ReglaNegocio.class, Integer
							.parseInt(segmentos.get(1)), Clave.RN);
					if (reglaNegocio == null) {
						cadenaDecodificada = "";
					}
					cadenaDecodificada = remplazoToken(cadenaDecodificada,
							token, tokenRN
									+ reglaNegocio.getNumero()
									+ tokenSeparator2
									+ reglaNegocio.getNombre()
											.replace(" ", "_"));
					break;
				case TRAYECTORIA:
					Trayectoria trayectoria = genericoDAO.findById(Trayectoria.class, Integer
							.parseInt(segmentos.get(1)));
					if (trayectoria == null) {
						cadenaDecodificada = "";
					}

					CasoUso cu = trayectoria.getCasoUso();
					cadenaDecodificada = remplazoToken(cadenaDecodificada,
							token, tokenTray + cu.getClave() + tokenSeparator1
									+ cu.getNumero() + tokenSeparator2
									+ cu.getNombre().replace(" ", "_")
									+ tokenSeparator2 + trayectoria.getClave());
					break;

				case PASO:
					Paso paso = genericoDAO.findById(Paso.class, Integer
							.parseInt(segmentos.get(1)));
					if (paso == null) {
						cadenaDecodificada = "";
					}
					Trayectoria t = paso.getTrayectoria();
					CasoUso cut = t.getCasoUso();
					cadenaDecodificada = remplazoToken(cadenaDecodificada,
							token, tokenP + cut.getClave() + tokenSeparator1
									+ cut.getNumero() + tokenSeparator2
									+ cut.getNombre().replace(" ", "_")
									+ tokenSeparator2 + t.getClave()
									+ tokenSeparator1 + paso.getNumero());
					break;
					
				case PARAMETRO:
					System.out.println("segmento "+segmentos.get(1));
					Parametro parametro = genericoDAO.findById(Parametro.class, segmentos.get(1));
					System.out.println("parametro.getNombre(): "+parametro.getNombre());
					System.out.println("cadenaDecodificada: "+cadenaDecodificada);
					System.out.println("token: "+token);
					System.out.println("tokenPARAM: "+tokenPARAM);
					//System.out.println("parametro.getNombre(): "+parametro.getNombre());
					 
					cadenaDecodificada = remplazoToken(cadenaDecodificada, token, tokenPARAM + parametro.getNombre());
					System.out.println("cadenaDecodificada2: "+cadenaDecodificada);
					break;
				default:
					break;

				}
			}
		}
		return cadenaDecodificada;
	}
	
	/*
	 * El método String remplazoToken(String @cadena, String @cadena_sustituir,
	 * String @cadena_sustituta) remplaza los tokens por el valor
	 * correspondiente según la decodificación realizada. Es útil frente a un
	 * simple replace, porque soluciona el siguiente problema:
	 * 
	 * Si se deseara remplazar el segmente de cadena "ACT·1" por "Profesor" en
	 * la cadena "ACT·1, ACT·11" resultado sería "Profesor, Profesor1" lo cual
	 * es indeseable por que cada token representa una referencia diferente.
	 * 
	 * Este método remplaza únicamente si la subcadena en la que se encuentra el
	 * patrón es una referencia/token completo.
	 * 
	 * Parámetros:
	 * 
	 * @cadena: Cadena en la que se realizarán los remplazos.
	 * 
	 * @cadena_sustituir: Cadena que contiene el token que se desea sustituir
	 * por su valor decodificado.
	 * 
	 * @cadena_sustituta: Cadena que contiene el valor decodificado que
	 * sustituirá a cadena_sustituir.
	 */
	public static String remplazoToken(String cadena, String cadena_sustituir,
			String cadena_sustituta) {
		String cadenaFinal = null;
		int indiceInicial = 0;
		int indiceFinal = 0;
		indiceInicial = cadena.indexOf(cadena_sustituir, indiceInicial);
		//System.out.println("indiceInicial1 :"+indiceInicial);
		while (indiceInicial != -1) {
			indiceFinal = indiceInicial + cadena_sustituir.length() - 1;
			//System.out.println("indiceFinalWhile: "+indiceFinal);
			if (indiceFinal + 1  == cadena.length()
					|| !ignore(cadena.charAt(indiceFinal + 1))) {
				cadenaFinal = cadena.substring(0,
						(indiceInicial != 0) ? indiceInicial : 0)
						+ cadena_sustituta
						+ cadena.substring(indiceFinal + 1, cadena.length());
				//System.out.println("cadena.substring(indiceFinal + 1, cadena.length()): "+cadena.substring(indiceFinal + 1, cadena.length()));
				//System.out.println("CadenaFinal: "+cadenaFinal);
				indiceInicial = cadenaFinal.indexOf(cadena_sustituir, indiceInicial + cadena_sustituta.length());
				cadena = cadenaFinal;
			} else {
				indiceInicial = cadena.indexOf(cadena_sustituir, indiceInicial + 1);
			}
			System.out.println("Valor cadena en while: "+cadena);
		}
		return cadena;
	}
	
	public static boolean ignore(char nextChar) {
		try {
			Integer.parseInt(nextChar + "");
		} catch (NumberFormatException e) {
			if((nextChar + "").equals(tokenSeparator1) || (nextChar + "").equals(tokenSeparator2)) {
				return true;
			}
			return false;
		}
		return false;
	}
}
