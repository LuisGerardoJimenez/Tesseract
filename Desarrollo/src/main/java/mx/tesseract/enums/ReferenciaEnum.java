package mx.tesseract.enums;

import mx.tesseract.admin.entidad.EstadoProyecto;
import mx.tesseract.admin.entidad.Rol;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.editor.entidad.Trayectoria;
import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.Actor;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.EstadoElemento;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.editor.entidad.Paso;
import mx.tesseract.editor.entidad.ReglaNegocio;

//import mx.tesseract.editor.entidad.Accion;
//import mx.tesseract.editor.entidad.Actor;
//import mx.tesseract.editor.entidad.Atributo;
//import mx.tesseract.editor.entidad.Cardinalidad;
//import mx.tesseract.editor.entidad.CasoUso;
//import mx.tesseract.editor.entidad.Entidad;
//import mx.tesseract.editor.entidad.Mensaje;
//import mx.tesseract.editor.entidad.Operador;
//import mx.tesseract.editor.entidad.Pantalla;
//import mx.tesseract.editor.entidad.Parametro;
//import mx.tesseract.editor.entidad.Paso;
//import mx.tesseract.editor.entidad.ReferenciaParametro;
//import mx.tesseract.editor.entidad.ReglaNegocio;
//import mx.tesseract.editor.entidad.TipoAccion;
//import mx.tesseract.editor.entidad.TipoComparacion;
//import mx.tesseract.editor.entidad.TipoDato;
//import mx.tesseract.editor.entidad.TipoParametro;
//import mx.tesseract.editor.entidad.TipoReglaNegocio;
//import mx.tesseract.editor.entidad.Trayectoria;
//import mx.tesseract.editor.entidad.UnidadTamanio;
//import mx.tesseract.editor.entidad.Verbo;

public class ReferenciaEnum {
	
	private static String ACCION = "Accion";
	private static String ACTOR = "Actor";
	private static String ATRIBUTO = "Atributo";
	private static String CASOUSO = "Caso de uso";
	private static String ENTIDAD = "Entidad";
	private static String MENSAJE = "Mensaje";
	private static String PANTALLA = "Pantalla";
	private static String PASO = "Paso";
	private static String REGLANEGOCIO = "Regla de negocio";
	private static String TERMINO = "Termino del glosario";
	private static String TRAYECTORIA = "Trayectoria";
	
	public enum TipoReferencia {
	    ACTOR, ENTIDAD, CASOUSO, PANTALLA, PASO, ATRIBUTO,
	    MENSAJE, REGLANEGOCIO, TERMINOGLS, ACCION, TRAYECTORIA, PARAMETRO
	}
	
	public enum TipoSeccion {
		ACTORES, ENTRADAS, SALIDAS, REGLAS, POSTPRECONDICIONES, REGLASNEGOCIOS, PASOS, EXTENSIONES, PARAMETROS
	}
	
	public enum Clave {
		ACT, ENT, GLS, RN, MSG, IU, CU
	}
	
	public enum TipoRelacion {
		/* Actores */
		ACTOR_ACTORES, ACTOR_POSTPRECONDICIONES, ACTOR_PASOS,
			
		/* Entidades */
		ENTIDAD_POSTPRECONDICIONES, ENTIDAD_PASOS, ENTIDAD_SALIDAS, ENTIDAD_ENTRADAS,
		
		/* Casos de uso */
		CASOUSO_POSTPRECONDICIONES, CASOUSO_PASOS,
		
		/* Pantalla */
		PANTALLA_PASOS, PANTALLA_POSTPRECONDICIONES, PANTALLA_SALIDAS,
		
		/* Mensajes */
		MENSAJE_SALIDAS, MENSAJE_POSTPRECONDICIONES, MENSAJE_PASOS,
		
		/* Reglas de negocio */
		REGLANEGOCIO_REGLASNEGOCIOS, REGLANEGOCIO_POSTPRECONDICIONES, REGLANEGOCIO_PASOS,
		
		/* Término del glosario */		
		TERMINOGLS_ENTRADAS, TERMINOGLS_SALIDAS, TERMINOGLS_POSTPRECONDICIONES, TERMINOGLS_PASOS,

		/* Atributos */		
		ATRIBUTO_ENTRADAS, ATRIBUTO_SALIDAS, ATRIBUTO_POSTPRECONDICIONES, ATRIBUTO_PASOS,
		
		/* Acciones */
		ACCION_POSTPRECONDICIONES, ACCION_PASOS,
		
		/* Trayectorias */
		TRAYECTORIA_POSTPRECONDICIONES, TRAYECTORIA_PASOS,
		
		/* Pasos */
		PASO_PASOS, PASO_POSTPRECONDICIONES, PASO_EXTENSIONES
	}
	
	public enum TipoCatalogo {
		VERBO, TIPOPARAMETRO, TIPODATO, TIPOACCION, CARDINALIDAD,
		
		UNIDADTAMANIO, OPERADOR, TIPOCOMPARACION, TIPOREGLANEGOCIO,
		
		PARAMETRO, ESTADOELEMENTO, ESTADOPROYECTO, ROL
	}
	
	public static TipoReferencia getTipoReferencia(String tokenReferencia){
		if (tokenReferencia.equals("ACT")){
			return TipoReferencia.ACTOR;
		}
		if (tokenReferencia.equals("ATR")){
			return TipoReferencia.ATRIBUTO;
		}
		if (tokenReferencia.equals("ENT")){
			return TipoReferencia.ENTIDAD;
		}
		if (tokenReferencia.equals("CU")){
			return TipoReferencia.CASOUSO;
		}
		if (tokenReferencia.equals("IU")){
			return TipoReferencia.PANTALLA;
		}
		if (tokenReferencia.equals("MSG")){
			return TipoReferencia.MENSAJE;
		}
		if (tokenReferencia.equals("RN")){
			return TipoReferencia.REGLANEGOCIO;
		}
		if (tokenReferencia.equals("GLS")){
			return TipoReferencia.TERMINOGLS;
		}
		if (tokenReferencia.equals("TRAY")){
			return TipoReferencia.TRAYECTORIA;
		}
		if (tokenReferencia.equals("P")){
			return TipoReferencia.PASO;
		}
		if (tokenReferencia.equals("PARAM")){
			return TipoReferencia.PARAMETRO;
		}
		return null;
	}
	
	public static TipoReferencia getTipoReferencia(Object objeto){
		System.out.println(objeto);
		
		if (objeto instanceof TerminoGlosario){
			return TipoReferencia.TERMINOGLS;
		}
		
		if (objeto instanceof Actor) {
			return TipoReferencia.ACTOR;
		}
		if (objeto instanceof Atributo) {
			return TipoReferencia.ATRIBUTO;
		}
		if (objeto instanceof Entidad) {
			return TipoReferencia.ENTIDAD;
		}
		if (objeto instanceof CasoUso) {
			return TipoReferencia.CASOUSO;
		}
		if (objeto instanceof Pantalla) {
			return TipoReferencia.PANTALLA;
		}
		if (objeto instanceof Mensaje) {
			return TipoReferencia.MENSAJE;
		}
		if (objeto instanceof ReglaNegocio) {
			return TipoReferencia.REGLANEGOCIO;
		}
		if (objeto instanceof Accion) {
			return TipoReferencia.ACCION;
		}
		if (objeto instanceof Trayectoria) {
			return TipoReferencia.TRAYECTORIA;
		}
		if (objeto instanceof Paso) {
			return TipoReferencia.PASO;
		}
		System.out.println("No es instancia de ninguna clase");

		return null;
	}

	public static TipoRelacion getTipoRelacion(TipoReferencia tipoReferencia, TipoSeccion tipoSeccion) {
		if (tipoReferencia == TipoReferencia.ACTOR) {
			if (tipoSeccion == TipoSeccion.ACTORES) {
				return TipoRelacion.ACTOR_ACTORES;
			}
			if (tipoSeccion == TipoSeccion.POSTPRECONDICIONES) {
				return TipoRelacion.ACTOR_POSTPRECONDICIONES;

			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.ACTOR_PASOS;
			}
		}

		if (tipoReferencia == TipoReferencia.ENTIDAD) {
			if (tipoSeccion == TipoSeccion.POSTPRECONDICIONES) {
				return TipoRelacion.ENTIDAD_POSTPRECONDICIONES;

			}
			if (tipoSeccion == TipoSeccion.ENTRADAS) {
				return TipoRelacion.ENTIDAD_ENTRADAS;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.ENTIDAD_PASOS;
			}
			if (tipoSeccion == TipoSeccion.SALIDAS) {
				return TipoRelacion.ENTIDAD_SALIDAS;
			}
		}

		if (tipoReferencia == TipoReferencia.CASOUSO) {
			if (tipoSeccion == TipoSeccion.POSTPRECONDICIONES) {
				return TipoRelacion.CASOUSO_POSTPRECONDICIONES;

			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.CASOUSO_PASOS;
			}
		}

		if (tipoReferencia == TipoReferencia.PANTALLA) {
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.PANTALLA_PASOS;
			}
			if (tipoSeccion == TipoSeccion.POSTPRECONDICIONES) {
				return TipoRelacion.PANTALLA_POSTPRECONDICIONES;

			}
			if (tipoSeccion == TipoSeccion.SALIDAS) {
				return TipoRelacion.PANTALLA_SALIDAS;
			}
		}

		if (tipoReferencia == TipoReferencia.MENSAJE) {
			if (tipoSeccion == TipoSeccion.SALIDAS) {
				return TipoRelacion.MENSAJE_SALIDAS;
			}
			if (tipoSeccion == TipoSeccion.POSTPRECONDICIONES) {
				return TipoRelacion.MENSAJE_POSTPRECONDICIONES;

			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.MENSAJE_PASOS;
			}
		}

		if (tipoReferencia == TipoReferencia.REGLANEGOCIO) {
			if (tipoSeccion == TipoSeccion.REGLASNEGOCIOS) {
				return TipoRelacion.REGLANEGOCIO_REGLASNEGOCIOS;
			}
			if (tipoSeccion == TipoSeccion.POSTPRECONDICIONES) {
				return TipoRelacion.REGLANEGOCIO_POSTPRECONDICIONES;

			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.REGLANEGOCIO_PASOS;
			}
		}
		if (tipoReferencia == TipoReferencia.TERMINOGLS) {
			if (tipoSeccion == TipoSeccion.ENTRADAS) {
				return TipoRelacion.TERMINOGLS_ENTRADAS;
			}
			if (tipoSeccion == TipoSeccion.SALIDAS) {
				return TipoRelacion.TERMINOGLS_SALIDAS;
			}
			if (tipoSeccion == TipoSeccion.POSTPRECONDICIONES) {
				return TipoRelacion.TERMINOGLS_POSTPRECONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.TERMINOGLS_PASOS;
			}
		}

		if (tipoReferencia == TipoReferencia.ATRIBUTO) {
			if (tipoSeccion == TipoSeccion.ENTRADAS) {
				return TipoRelacion.ATRIBUTO_ENTRADAS;
			}
			if (tipoSeccion == TipoSeccion.SALIDAS) {
				return TipoRelacion.ATRIBUTO_SALIDAS;
			}
			if (tipoSeccion == TipoSeccion.POSTPRECONDICIONES) {
				return TipoRelacion.ATRIBUTO_POSTPRECONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.ATRIBUTO_PASOS;
			}
		}

		if (tipoReferencia == TipoReferencia.ACCION) {
			if (tipoSeccion == TipoSeccion.POSTPRECONDICIONES) {
				return TipoRelacion.ACCION_POSTPRECONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.ACCION_PASOS;
			}
		}

		if (tipoReferencia == TipoReferencia.TRAYECTORIA) {
			if (tipoSeccion == TipoSeccion.POSTPRECONDICIONES) {
				return TipoRelacion.TRAYECTORIA_POSTPRECONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.TRAYECTORIA_PASOS;
			}
		}

		if (tipoReferencia == TipoReferencia.PASO) {
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.PASO_PASOS;
			}
			if (tipoSeccion == TipoSeccion.POSTPRECONDICIONES) {
				return TipoRelacion.PASO_POSTPRECONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.EXTENSIONES) {
				return TipoRelacion.PASO_EXTENSIONES;
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static Class getClase(TipoReferencia referencia) {
		switch (referencia) {
		case TERMINOGLS:
			return TerminoGlosario.class;
//		case ACCION:
//			return Accion.class;
//		case ACTOR:
//			return Actor.class;
//		case ATRIBUTO:
//			return Atributo.class;
//		case CASOUSO:
//			return CasoUso.class;
//		case ENTIDAD:
//			return Entidad.class;
//		case MENSAJE:
//			return Mensaje.class;
//		case PANTALLA:
//			return Pantalla.class;
//		case PASO:
//			return Paso.class;
//		case REGLANEGOCIO:
//			return ReglaNegocio.class;
//		case TRAYECTORIA:
//			return Trayectoria.class;
		default:
			break;

		}
		return null;
	}

//	@SuppressWarnings("rawtypes")
//	public static Class getClaseCatalogo (TipoCatalogo tipoCatalogo) {
//		switch(tipoCatalogo){
//		case CARDINALIDAD:
//			return Cardinalidad.class;
//		case ESTADOELEMENTO:
//			return EstadoElemento.class;
//		case ESTADOPROYECTO:
//			return EstadoProyecto.class;
//		case OPERADOR:
//			return Operador.class;
//		case PARAMETRO:
//			return Parametro.class;
//		case ROL:
//			return Rol.class;
//		case TIPOACCION:
//			return TipoAccion.class;
//		case TIPOCOMPARACION:
//			return TipoComparacion.class;
//		case TIPODATO:
//			return TipoDato.class;
//		case TIPOPARAMETRO:
//			return TipoParametro.class;
//		case TIPOREGLANEGOCIO:
//			return TipoReglaNegocio.class;
//		case UNIDADTAMANIO:
//			return UnidadTamanio.class;
//		case VERBO:
//			return Verbo.class;
//		default:
//			break;
//			
//		}
//		return null;
//
//	}
//
//	public static TipoReferencia getTipoReferenciaParametro(
//			ReferenciaParametro referenciaParametro) {
//		if (referenciaParametro.getTipoParametro().getNombre().equals(ACCION)){
//			return TipoReferencia.ACCION;
//		}
//		if (referenciaParametro.getTipoParametro().getNombre().equals(ACTOR)){
//			return TipoReferencia.ACTOR;
//		}
//		if (referenciaParametro.getTipoParametro().getNombre().equals(ATRIBUTO)){
//			return TipoReferencia.ATRIBUTO;
//		}
//		if (referenciaParametro.getTipoParametro().getNombre().equals(CASOUSO)){
//			return TipoReferencia.CASOUSO;
//		}
//		if (referenciaParametro.getTipoParametro().getNombre().equals(ENTIDAD)){
//			return TipoReferencia.ENTIDAD;
//		}
//		if (referenciaParametro.getTipoParametro().getNombre().equals(MENSAJE)){
//			return TipoReferencia.MENSAJE;
//		}
//		if (referenciaParametro.getTipoParametro().getNombre().equals(PANTALLA)){
//			return TipoReferencia.PANTALLA;
//		}
//		if (referenciaParametro.getTipoParametro().getNombre().equals(PASO)){
//			return TipoReferencia.PASO;
//		}
//		if (referenciaParametro.getTipoParametro().getNombre().equals(REGLANEGOCIO)){
//			return TipoReferencia.REGLANEGOCIO;
//		}
//		if (referenciaParametro.getTipoParametro().getNombre().equals(TERMINO)){
//			return TipoReferencia.TERMINOGLS;
//		}
//		if (referenciaParametro.getTipoParametro().getNombre().equals(TRAYECTORIA)){
//			return TipoReferencia.TRAYECTORIA;
//		}
//		
//		System.out.println("No es instancia de ninguna clase ID: " + referenciaParametro.getId());
//		
//		return null;
//	}
}
