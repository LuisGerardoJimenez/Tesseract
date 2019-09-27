package mx.tesseract.editor.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.bs.AccessBs;
import mx.tesseract.editor.bs.CasoUsoBs;
//import mx.tesseract.bs.ReferenciaEnum;
//import mx.tesseract.bs.TipoSeccionEnum;
//import mx.tesseract.bs.AnalisisEnum.CU_CasosUso;
//import mx.tesseract.bs.TipoSeccionEnum.TipoSeccionENUM;
//import mx.tesseract.editor.bs.CasoUsoBs;
import mx.tesseract.editor.bs.ElementoBs;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.bs.TokenBs;
//import mx.tesseract.editor.bs.TrayectoriaBs;
//import mx.tesseract.editor.bs.ElementoBs.Estado;
import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.Actor;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.CasoUsoActor;
import mx.tesseract.editor.entidad.CasoUsoReglaNegocio;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.Entrada;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.editor.entidad.Paso;
import mx.tesseract.editor.entidad.PostPrecondicion;
import mx.tesseract.editor.entidad.ReferenciaParametro;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.editor.entidad.Revision;
import mx.tesseract.editor.entidad.Salida;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.editor.entidad.Trayectoria;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.JsonUtil;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;

@ResultPath("/pages/editor/")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_CASO_USO }),
		@Result(name = "referencias", type = "json", params = { "root",
				"elementosReferencias" }),
		@Result(name = "modulos", type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_MODULOS }),
		@Result(name = "restricciones", type = "json", params = { "root",
				"restriccionesTermino" }),
		@Result(name = "revision", type = "dispatcher", location = "caso-uso/revision.jsp"),
		@Result(name = "liberacion", type = "dispatcher", location = "caso-uso/liberacion.jsp")})
public class CasoUsoAct extends ActionSupportTESSERACT implements ModelDriven<CasoUso> {

	private static final long serialVersionUID = 1L;
	
	private static final String PROYECTOS = "proyectos";
	private static final String MODULOS = "modulos";
	private static final String REVISION = "revision";
	private static final String LIBERACION = "liberacion";

	// Proyecto y módulo
	private Proyecto proyecto;
	private Modulo modulo;
	private Colaborador colaborador;
	
	private Integer idProyecto;
	private Integer idModulo;

	// Modelo
	private CasoUso model;

	// Lista de registros
	private List<CasoUso> listCU;
	private String jsonCasosUsoModulo;
	private String jsonPrecondiciones;
	private String jsonPostcondiciones;
	private String jsonPtosExtension;

	// Elementos disponibles
	private String jsonReglasNegocio;
	private String jsonEntidades;
	private String jsonCasosUsoProyecto;
	private String jsonPantallas;
	private String jsonMensajes;
	private String jsonActores;
	private String jsonTerminosGls;
	private String jsonAtributos;
	private String jsonPasos;
	private String jsonTrayectorias;
	private String jsonAcciones;
	private List<List<Paso>> listPasos; 
	private Integer idSel;

	private boolean existenPrecondiciones;
	private boolean existenPostcondiciones;
	
	private String observaciones;
	private String observacionesResumen;
	private String observacionesTrayectoria;
	private String observacionesPuntosExt;
	private Integer esCorrectoResumen = null;
	private Integer esCorrectoTrayectoria = null;
	private Integer esCorrectoPuntosExt = null;

	private String comentario;
	private List<String> elementosReferencias;
	private List<String> restriccionesTermino;
	
	private boolean pruebaGenerada;
	private Boolean pruebaGenerada2;
	
	@Autowired
	private LoginBs loginBs;
	
	@Autowired
	private ModuloBs moduloBs;
	
	@Autowired
	private CasoUsoBs casoUsoBs;
	
	@SuppressWarnings("unchecked")
	public String index() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					proyecto = loginBs.consultarProyectoActivo();
					modulo = moduloBs.consultarModuloById(idModulo);
					listCU = casoUsoBs.consultarCasosDeUso(idProyecto, idModulo);
					model.setProyecto(proyecto);
					model.setModulo(modulo);
					resultado = INDEX;
					Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
					this.setActionMessages(msjs);
					SessionManager.delete("mensajesAccion");
					Collection<String> msjsError = (Collection<String>) SessionManager.get("mensajesError");
					this.setActionErrors(msjsError);
					SessionManager.delete("mensajesError");
				} else {
					resultado = MODULOS;
				}
			}
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}

	public String editNew() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					proyecto = loginBs.consultarProyectoActivo();
					modulo = moduloBs.consultarModuloById(idModulo);
					model.setProyecto(proyecto);
					model.setModulo(modulo);
//					buscaElementos();
//					model.setClave(CasoUsoBs.calcularClave(modulo.getClave()));
					resultado = EDITNEW;
				} else {
					resultado = MODULOS;
				}
			}
		} catch (TESSERACTValidacionException tve) {
			ErrorManager.agregaMensajeError(this, tve);
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}

//	public String create() throws TESSERACTException, Exception {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			modulo = SessionManager.consultarModuloActivo();
//			if (modulo == null) {
//				resultado = "modulos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(modulo.getProyecto(), colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//			model.setProyecto(proyecto);
//			model.setModulo(modulo);
//			model.setEstadoElemento(ElementoBs
//					.consultarEstadoElemento(Estado.EDICION));
//
//			// Se agregan las postcondiciones y precondiciones
//			agregarPostPrecondiciones(model);
//			CasoUsoBs.preAlmacenarObjetosToken(model);
//
//			CasoUsoBs.registrarCasoUso(model);
//			resultado = SUCCESS;
//			addActionMessage(getText("MSG1", new String[] { "El",
//					"Caso de uso", "registrado" }));
//			SessionManager.set(this.getActionMessages(), "mensajesAccion");
//		} catch (TESSERACTValidacionException pve) {
//			ErrorManager.agregaMensajeError(this, pve);
//			resultado = editNew();
//		} catch (TESSERACTException pe) {
//			ErrorManager.agregaMensajeError(this, pe);
//			resultado = index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			resultado = index();
//		}
//		return resultado;
//	}
//
//	public String edit() {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			
//			modulo = SessionManager.consultarModuloActivo();
//			if (modulo == null) {
//				resultado = "modulos";
//				return resultado;
//			}
//			
//			if (!AccessBs.verificarPermisos(model.getProyecto(), colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//			
//			model.setProyecto(proyecto);
//			
//			model.setModulo(modulo);
//
//			ElementoBs.verificarEstado(model, CU_CasosUso.MODIFICARCASOUSO5_2);
//
//			buscaElementos();
//			
//			prepararVista();
//
//			resultado = EDIT;
//		} catch (TESSERACTValidacionException pve) {
//			ErrorManager.agregaMensajeError(this, pve);
//			resultado = edit();
//		} catch (TESSERACTException pe) {
//			ErrorManager.agregaMensajeError(this, pe);
//			resultado = index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			resultado = index();
//		}
//		return resultado;
//	}
//
//	public String update() throws Exception {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			modulo = SessionManager.consultarModuloActivo();
//			if (modulo == null) {
//				resultado = "modulos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(model.getProyecto(), colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//			model.setProyecto(proyecto);
//			model.setModulo(modulo);
//
//			model.getActores().clear();
//			model.getEntradas().clear();
//			model.getSalidas().clear();
//			model.getReglas().clear();
//			model.getPostprecondiciones().clear();
//
//			agregarPostPrecondiciones(model);
//			CasoUsoBs.preAlmacenarObjetosToken(model);
////			Actualizacion actualizacion = new Actualizacion(new Date(),
////					comentario, model,
////					SessionManager.consultarColaboradorActivo());
//
//			//CasoUsoBs.modificarCasoUso(model, actualizacion);
//			CasoUsoBs.modificarCasoUso(model);
//			resultado = SUCCESS;
//			addActionMessage(getText("MSG1", new String[] { "El",
//					"Caso de uso", "modificado" }));
//			SessionManager.set(this.getActionMessages(), "mensajesAccion");
//		} catch (TESSERACTValidacionException pve) {
//			ErrorManager.agregaMensajeError(this, pve);
//			resultado = edit();
//		} catch (TESSERACTException pe) {
//			ErrorManager.agregaMensajeError(this, pe);
//			resultado = index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			resultado = index();
//		}
//		return resultado;
//	}
//
//	public String show() throws Exception {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			modulo = SessionManager.consultarModuloActivo();
//			if (modulo == null) {
//				resultado = "modulos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(model.getProyecto(), colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//
//			this.existenPrecondiciones = CasoUsoBs.existenPrecondiciones(model
//					.getPostprecondiciones());
//			this.existenPostcondiciones = CasoUsoBs.existenPostcondiciones(model
//					.getPostprecondiciones());
//
//			listPasos = CasoUsoBs.agregarReferencias(request.getContextPath(), this.model, "_self");
//			for(List<Paso> pasitos:listPasos){
//				System.out.println(pasitos.get(0).getTrayectoria().getClave());
//				for(Paso paso:pasitos){
//					System.out.println(paso.getNumero());
//					System.out.println(paso.getRedaccion());
//					System.out.println(paso.getTrayectoria());
//				}
//			}
//			resultado = SHOW;
//		} catch (TESSERACTException pe) {
//			pe.setIdMensaje("MSG26");
//			ErrorManager.agregaMensajeError(this, pe);
//			return index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			return index();
//		}
//		System.out.println("Resultado: "+resultado);
//		return resultado;
//	}
//
//	public String destroy() throws Exception {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			modulo = SessionManager.consultarModuloActivo();
//			if (modulo == null) {
//				resultado = "modulos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(model.getProyecto(), colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//			model.setProyecto(proyecto);
//			model.setModulo(modulo);
//			CasoUsoBs.eliminarCasoUso(model);
//			resultado = SUCCESS;
//			addActionMessage(getText("MSG1", new String[] { "El",
//					"Caso de uso", "eliminado" }));
//			SessionManager.set(this.getActionMessages(), "mensajesAccion");
//		} catch (TESSERACTException pe) {
//			ErrorManager.agregaMensajeError(this, pe);
//			SessionManager.set(this.getActionErrors(), "mensajesError");
//			resultado = index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			SessionManager.set(this.getActionErrors(), "mensajesError");
//			resultado = index();
//		}
//		return resultado;
//	}
//
//	private void agregarPostPrecondiciones(CasoUso casoUso) {
//		// Se agregan precondiciones al caso de uso
//		if (jsonPrecondiciones != null && !jsonPrecondiciones.equals("")) {
//			casoUso.getPostprecondiciones().addAll(
//					(JsonUtil.mapJSONToSet(jsonPrecondiciones,
//							PostPrecondicion.class)));
//		}
//		// Se agregan postcondiciones al caso de uso
//		if (jsonPostcondiciones != null && !jsonPostcondiciones.equals("")) {
//			casoUso.getPostprecondiciones().addAll(
//					JsonUtil.mapJSONToSet(jsonPostcondiciones,
//							PostPrecondicion.class));
//		}
//		// Se agrega a cada elemento el caso de uso
//		for (PostPrecondicion pp : casoUso.getPostprecondiciones()) {
//			pp.setCasoUso(casoUso);
//		}
//	}

//	private void buscaElementos() {
//		// Lists de los elementos disponibles
//		List<Elemento> listElementos;
//		List<ReglaNegocio> listReglasNegocio = new ArrayList<ReglaNegocio>();
//		List<Entidad> listEntidades = new ArrayList<Entidad>();
//		List<Pantalla> listPantallas = new ArrayList<Pantalla>();
//		List<Mensaje> listMensajes = new ArrayList<Mensaje>();
//		List<Actor> listActores = new ArrayList<Actor>();
//		List<TerminoGlosario> listTerminosGls = new ArrayList<TerminoGlosario>();
//		List<Atributo> listAtributos = new ArrayList<Atributo>();
//
//		// Se consultan los elementos de todo el proyecto
//		listElementos = CasoUsoBs.consultarElementos(proyecto);
//		
//		Modulo moduloAux = null;
//		
//
//		if (listElementos != null && !listElementos.isEmpty()) {
//			// Se clasifican los conjuntos
//			for (Elemento el : listElementos) {
//				System.out.println(ReferenciaEnum.getTipoReferencia(el));
//				switch (ReferenciaEnum.getTipoReferencia(el)) {
//
//				case ACTOR:
//					Actor auxActor = new Actor();
//					auxActor.setClave(el.getClave());
//					auxActor.setNombre(el.getNombre());
//					listActores.add(auxActor);
//					break;
//				case CASOUSO:
//					CasoUso cu  = (CasoUso)el;
//					// Módulo auxiliar para la serialización
//					moduloAux = new Modulo();
//					moduloAux.setId(cu.getModulo().getId());
//					moduloAux.setNombre(cu.getModulo().getNombre());
//					moduloAux.setClave(cu.getModulo().getClave());
//					
//					CasoUso auxCasoUso = new CasoUso();
//					auxCasoUso.setClave(cu.getClave());
//					auxCasoUso.setNumero(cu.getNumero());
//					auxCasoUso.setNombre(cu.getNombre());
//					auxCasoUso.setModulo(moduloAux);
//					listCasosUso.add(auxCasoUso);
//
//					// Se obtienen las Trayectorias
//					Set<Trayectoria> trayectorias = ((CasoUso) el)
//							.getTrayectorias();
//					for (Trayectoria tray : trayectorias) {
//						if (tray.getCasoUso().getId() == model.getId()) {
//							Trayectoria auxTrayectoria = new Trayectoria();
//							auxTrayectoria.setClave(tray.getClave());
//							auxTrayectoria.setCasoUso(auxCasoUso);
//							listTrayectorias.add(auxTrayectoria);
//							// Se obtienen los Pasos
//							List <Paso> pasosaux = TrayectoriaBs.obtenerPasos_(tray.getId());//HOLI
//							for (Paso paso : pasosaux) {
//								Paso auxPaso = new Paso();
//								auxPaso.setTrayectoria(auxTrayectoria);
//								auxPaso.setNumero(paso.getNumero());
//								auxPaso.setRealizaActor(paso.isRealizaActor());
//								auxPaso.setVerbo(paso.getVerbo());
//								auxPaso.setOtroVerbo(paso.getOtroVerbo());
//								auxPaso.setRedaccion(TokenBs
//										.decodificarCadenaSinToken(paso
//												.getRedaccion()));
//								listPasos.add(auxPaso);
//							}
//						}
//					}
//					break;
//				case ENTIDAD:
//					Entidad auxEntidad = new Entidad();
//					auxEntidad.setNombre(el.getNombre());
//					listEntidades.add(auxEntidad);
//					// Se obtienen los Atributos
//					Set<Atributo> atributos = ((Entidad) el).getAtributos();
//					for (Atributo atributo : atributos) {
//						Atributo auxAtributo = new Atributo();
//						auxAtributo.setEntidad(auxEntidad);
//						auxAtributo.setNombre(atributo.getNombre());
//						listAtributos.add(auxAtributo);
//					}
//
//					break;
//				case MENSAJE:
//					Mensaje auxMensaje = new Mensaje();
//					auxMensaje.setNumero(el.getNumero());
//					auxMensaje.setNombre(el.getNombre());
//					listMensajes.add(auxMensaje);
//					break;
//				case PANTALLA:
//					Pantalla iu  = (Pantalla) el;
//					// Módulo auxiliar para la serialización
//					moduloAux = new Modulo();
//					moduloAux.setId(iu.getModulo().getId());
//					moduloAux.setNombre(iu.getModulo().getNombre());
//					moduloAux.setClave(iu.getModulo().getClave());
//					
//					Pantalla auxPantalla = new Pantalla();
//					auxPantalla.setClave(el.getClave());
//					auxPantalla.setNumero(el.getNumero());
//					auxPantalla.setNombre(el.getNombre());
//					auxPantalla.setModulo(moduloAux);
//					listPantallas.add(auxPantalla);
//					// Se obtienen las acciones
//					Set<Accion> acciones = ((Pantalla) el).getAcciones();
//					for (Accion accion : acciones) {
//						Accion auxAccion = new Accion();
//						auxAccion.setPantalla(auxPantalla);
//						auxAccion.setNombre(accion.getNombre());
//						listAcciones.add(auxAccion);
//					}
//					break;
//				case REGLANEGOCIO:
//					ReglaNegocio auxReglaNegocio = new ReglaNegocio();
//					auxReglaNegocio.setNumero(el.getNumero());
//					auxReglaNegocio.setNombre(el.getNombre());
//					listReglasNegocio.add(auxReglaNegocio);
//					break;
//				case TERMINOGLS:
//					TerminoGlosario auxTerminoGlosario = new TerminoGlosario();
//					auxTerminoGlosario.setNombre(el.getNombre());
//					listTerminosGls.add(auxTerminoGlosario);
//					break;
//				default:
//					break;
//				}
//			}
//
//			// Se convierte en json las Reglas de Negocio
//			if (listReglasNegocio != null) {
//				this.jsonReglasNegocio = JsonUtil
//						.mapListToJSON(listReglasNegocio);
//			}
//			// Se convierte en json las Entidades
//			if (listEntidades != null) {
//				this.jsonEntidades = JsonUtil.mapListToJSON(listEntidades);
//			}
//			// Se convierte en json los Casos de Uso
//			if (listCasosUso != null) {
//				this.jsonCasosUsoProyecto = JsonUtil
//						.mapListToJSON(listCasosUso);
//			}
//			// Se convierte en json las Pantallas
//			if (listPantallas != null) {
//				this.jsonPantallas = JsonUtil.mapListToJSON(listPantallas);
//			}
//			// Se convierte en json los Mensajes
//			if (listMensajes != null) {
//				this.jsonMensajes = JsonUtil.mapListToJSON(listMensajes);
//			}
//			// Se convierte en json los Actores
//			if (listActores != null) {
//				this.jsonActores = JsonUtil.mapListToJSON(listActores);
//			}
//			// Se convierte en json los Términos del Glosario
//			if (listTerminosGls != null) {
//				this.jsonTerminosGls = JsonUtil.mapListToJSON(listTerminosGls);
//			}
//			// Se convierte en json los Atributos
//			if (listAtributos != null) {
//				this.jsonAtributos = JsonUtil.mapListToJSON(listAtributos);
//			}
//			// Se convierte en json los Pasos
//			if (listPasos != null) {
//				this.jsonPasos = JsonUtil.mapListToJSON(listPasos);
//			}
//			// Se convierte en json las Trayectorias
//			if (listTrayectorias != null) {
//				this.jsonTrayectorias = JsonUtil
//						.mapListToJSON(listTrayectorias);
//			}
//			// Se convierte en json las Acciones
//			if (listAcciones != null) {
//				this.jsonAcciones = JsonUtil.mapListToJSON(listAcciones);
//			}
//		}
//
//	}
//
//	private void prepararVista() {
//		Set<PostPrecondicion> postPrecondiciones = model
//				.getPostprecondiciones();
//		ArrayList<PostPrecondicion> precondiciones = new ArrayList<PostPrecondicion>();
//		ArrayList<PostPrecondicion> postcondiciones = new ArrayList<PostPrecondicion>();
//		PostPrecondicion postPrecondicionAux;
//
//		for (PostPrecondicion postPrecondicion : postPrecondiciones) {
//			postPrecondicionAux = new PostPrecondicion();
//			postPrecondicionAux.setId(postPrecondicion.getId());
//			postPrecondicionAux.setRedaccion(TokenBs
//					.decodificarCadenasToken(postPrecondicion.getRedaccion()));
//			if (postPrecondicion.isPrecondicion()) {
//				precondiciones.add(postPrecondicionAux);
//			} else {
//				postcondiciones.add(postPrecondicionAux);
//			}
//		}
//		this.jsonPrecondiciones = JsonUtil.mapListToJSON(precondiciones);
//		this.jsonPostcondiciones = JsonUtil.mapListToJSON(postcondiciones);
//
//		model.setRedaccionActores((TokenBs.decodificarCadenasToken(model
//				.getRedaccionActores())));
//		
//		model.setRedaccionEntradas((TokenBs.decodificarCadenasToken(model
//				.getRedaccionEntradas())));
//		
//		model.setRedaccionSalidas((TokenBs.decodificarCadenasToken(model
//				.getRedaccionSalidas())));
//		
//		model.setRedaccionReglasNegocio((TokenBs.decodificarCadenasToken(model
//				.getRedaccionReglasNegocio())));
//
//		for (Revision rev : model.getRevisiones()) {
//			if (!rev.isRevisado()
//					&& rev.getSeccion()
//							.getNombre()
//							.equals(TipoSeccionEnum
//									.getNombre(TipoSeccionENUM.RESUMEN))) {
//				this.observaciones = rev.getObservaciones();
//			}
//		}
//	}
//
//	public String verificarElementosReferencias() {
//		try {
//			model.setId(idSel);
//			elementosReferencias = new ArrayList<String>();
//			elementosReferencias = CasoUsoBs.verificarReferencias(model, null);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "referencias";
//	}
//
//	public String verificarTermino() {
//		try {
//			model = CasoUsoBs.consultarCasoUso(idSel);
//			restriccionesTermino = new ArrayList<String>();
//			restriccionesTermino = CasoUsoBs.verificarRestriccionesTermino(model);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "restricciones";
//	}
//
//	public String terminar() throws Exception {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			modulo = SessionManager.consultarModuloActivo();
//			model = CasoUsoBs.consultarCasoUso(idSel);
//			if (modulo == null) {
//				resultado = "modulos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(proyecto, colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//			ElementoBs.verificarEstado(model, CU_CasosUso.TERMINARCASOUSO5_6);
//			resultado = SUCCESS;
//			CasoUsoBs.terminar(model);
//			addActionMessage(getText("MSG1", new String[] { "El",
//					"Caso de uso", "terminado" }));
//			SessionManager.set(this.getActionMessages(), "mensajesAccion");
//		} catch (TESSERACTException pe) {
//			ErrorManager.agregaMensajeError(this, pe);
//			SessionManager.set(this.getActionErrors(), "mensajesError");
//			resultado = index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			SessionManager.set(this.getActionErrors(), "mensajesError");
//			resultado = index();
//		}
//		return resultado;
//	}

//	public String prepararRevision() {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			modulo = SessionManager.consultarModuloActivo();
//			model = CasoUsoBs.consultarCasoUso(idSel);
//			if (modulo == null) {
//				resultado = "modulos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(proyecto, colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//
//			ElementoBs.verificarEstado(model, CU_CasosUso.REVISARCASOUSO5_5);
//
//			this.existenPrecondiciones = CasoUsoBs.existenPrecondiciones(model
//					.getPostprecondiciones());
//			this.existenPostcondiciones = CasoUsoBs.existenPostcondiciones(model
//					.getPostprecondiciones());
//
//			listPasos = CasoUsoBs.agregarReferencias(request.getContextPath(), model, "_blank");
//			resultado = "revision";
//		} catch (TESSERACTValidacionException pve) {
//			ErrorManager.agregaMensajeError(this, pve);
//			SessionManager.set(this.getActionErrors(), "mensajesError");
//			resultado = edit();
//		} catch (TESSERACTException pe) {
//			ErrorManager.agregaMensajeError(this, pe);
//			SessionManager.set(this.getActionErrors(), "mensajesError");
//			resultado = index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			SessionManager.set(this.getActionErrors(), "mensajesError");
//			resultado = index();
//		}
//		return resultado;
//	}
	
//	public String revisar() throws Exception{
//		String resultado = null;
//
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			modulo = SessionManager.consultarModuloActivo();
//			model = CasoUsoBs.consultarCasoUso(idSel);
//			if (modulo == null) {
//				resultado = "modulos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(proyecto, colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//
//			ElementoBs.verificarEstado(model, CU_CasosUso.REVISARCASOUSO5_5);
//
//
//			if(CasoUsoBs.guardarRevisiones(esCorrectoResumen, observacionesResumen,
//					esCorrectoTrayectoria, observacionesTrayectoria,
//					esCorrectoPuntosExt, observacionesPuntosExt, model)) {
//				ElementoBs.modificarEstadoElemento(model, Estado.PENDIENTECORRECCION);
//				addActionMessage(getText("MSG1", new String[] { "El",
//						"Caso de uso", "revisado" }));
//			} else {
//				if(ProyectoBs.consultarColaboradorProyectoLider(proyecto).getColaborador().getCurp().equals(colaborador.getCurp())) {
//					ElementoBs.modificarEstadoElemento(model, Estado.LIBERADO);
//					CasoUsoBs.liberarElementosRelacionados(model);
//					addActionMessage(getText("MSG1", new String[] { "El",
//							"Caso de uso", "liberado" }));
//				} else {
//					ElementoBs.modificarEstadoElemento(model, Estado.PORLIBERAR);
//					addActionMessage(getText("MSG1", new String[] { "El",
//							"Caso de uso", "revisado" }));
//				}
//			}
//			SessionManager.set(this.getActionMessages(), "mensajesAccion");
//			resultado = SUCCESS;
//		} catch (TESSERACTValidacionException pve) {
//			ErrorManager.agregaMensajeError(this, pve);
//			resultado = prepararRevision();
//		} catch (TESSERACTException pe) {
//			ErrorManager.agregaMensajeError(this, pe);
//			resultado = index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			resultado = index();
//		}
//		return resultado;
//	}
	
//	public String prepararLiberacion() {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			modulo = SessionManager.consultarModuloActivo();
//			model = CasoUsoBs.consultarCasoUso(idSel);
//			if (modulo == null) {
//				resultado = "modulos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(proyecto, colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//
//			ElementoBs.verificarEstado(model, CU_CasosUso.LIBERARCASOUSO4_3);
//
//			this.existenPrecondiciones = CasoUsoBs.existenPrecondiciones(model
//					.getPostprecondiciones());
//			this.existenPostcondiciones = CasoUsoBs.existenPostcondiciones(model
//					.getPostprecondiciones());
//
//			listPasos = CasoUsoBs.agregarReferencias(request.getContextPath(), model, "_blank");
//			for(List<Paso> pasitos:listPasos){
//				System.out.println(pasitos.get(0).getTrayectoria().getClave());
//				for(Paso paso:pasitos){
//					System.out.println(paso.getNumero());
//					System.out.println(paso.getRedaccion());
//					System.out.println(paso.getTrayectoria());
//				}
//			}
//			resultado = "liberacion";
//		} catch (TESSERACTValidacionException pve) {
//			ErrorManager.agregaMensajeError(this, pve);
//			SessionManager.set(this.getActionErrors(), "mensajesError");
//			resultado = edit();
//		} catch (TESSERACTException pe) {
//			ErrorManager.agregaMensajeError(this, pe);
//			SessionManager.set(this.getActionErrors(), "mensajesError");
//			resultado = index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			SessionManager.set(this.getActionErrors(), "mensajesError");
//			resultado = index();
//		}
//		return resultado;
//	}
	
//	public String liberar() throws Exception{
//		String resultado = null;
//
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			modulo = SessionManager.consultarModuloActivo();
//			model = CasoUsoBs.consultarCasoUso(idSel);
//			if (modulo == null) {
//				resultado = "modulos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(proyecto, colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//
//			ElementoBs.verificarEstado(model, CU_CasosUso.LIBERARCASOUSO4_3);
//
//			if(CasoUsoBs.guardarRevisiones(esCorrectoResumen, observacionesResumen,
//					esCorrectoTrayectoria, observacionesTrayectoria,
//					esCorrectoPuntosExt, observacionesPuntosExt, model)) {
//				ElementoBs.modificarEstadoElemento(model, Estado.PENDIENTECORRECCION);
//				CasoUsoBs.habilitarElementosRelacionados(model);
//			} else {
//				ElementoBs.modificarEstadoElemento(model, Estado.LIBERADO);
//				CasoUsoBs.liberarElementosRelacionados(model);
//			}
//			
//			addActionMessage(getText("MSG1", new String[] { "El",
//					"Caso de uso", "liberado" }));
//			SessionManager.set(this.getActionMessages(), "mensajesAccion");
//			resultado = SUCCESS;
//		} catch (TESSERACTValidacionException pve) {
//			ErrorManager.agregaMensajeError(this, pve);
//			resultado = prepararLiberacion();
//		} catch (TESSERACTException pe) {
//			ErrorManager.agregaMensajeError(this, pe);
//			resultado = index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			resultado = index();
//		}
//		return resultado;
//	}
		
	public String getObservacionesPuntosExt() {
		return observacionesPuntosExt;
	}

	public void setObservacionesPuntosExt(String observacionesPuntosExt) {
		this.observacionesPuntosExt = observacionesPuntosExt;
	}

	/*public String revisar() throws Exception {
		String resultado = null;
		try {
			colaborador = SessionManager.consultarColaboradorActivo();
			proyecto = SessionManager.consultarProyectoActivo();
			modulo = SessionManager.consultarModuloActivo();
			if (modulo == null) {
				resultado = "modulos";
				return resultado;
			}
			if (!AccessBs.verificarPermisos(model.getProyecto(), colaborador)) {
				resultado = Action.LOGIN;
				return resultado;
			}
			model.setProyecto(proyecto);
			model.setModulo(modulo);
			resultado = SUCCESS;
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}*/
	
	public CasoUso getModel() {
		if (this.model == null) {
			model = new CasoUso();
		}
		return model;
	}

	public String getJsonAcciones() {
		return jsonAcciones;
	}

	public String getJsonActores() {
		return jsonActores;
	}

	public String getJsonAtributos() {
		return jsonAtributos;
	}

	public String getJsonCasosUsoModulo() {
		return jsonCasosUsoModulo;
	}

	public String getJsonCasosUsoProyecto() {
		return jsonCasosUsoProyecto;
	}

	public String getJsonEntidades() {
		return jsonEntidades;
	}

	public String getJsonMensajes() {
		return jsonMensajes;
	}

	public String getJsonPantallas() {
		return jsonPantallas;
	}

	public String getJsonPasos() {
		return jsonPasos;
	}

	public String getJsonPostcondiciones() {
		return jsonPostcondiciones;
	}

	public String getJsonPrecondiciones() {
		return jsonPrecondiciones;
	}

	public String getJsonPtosExtension() {
		return jsonPtosExtension;
	}

	public String getJsonReglasNegocio() {
		return jsonReglasNegocio;
	}

	public String getJsonTerminosGls() {
		return jsonTerminosGls;
	}

	public String getJsonTrayectorias() {
		return jsonTrayectorias;
	}

	public List<CasoUso> getListCU() {
		return listCU;
	}

	public void setJsonAcciones(String jsonAcciones) {
		this.jsonAcciones = jsonAcciones;
	}

	public void setJsonActores(String jsonActores) {
		this.jsonActores = jsonActores;
	}

	public void setJsonAtributos(String jsonAtributos) {
		this.jsonAtributos = jsonAtributos;
	}

	public void setJsonCasosUsoModulo(String jsonCasosUsoModulo) {
		this.jsonCasosUsoModulo = jsonCasosUsoModulo;
	}

	public void setJsonCasosUsoProyecto(String jsonCasosUsoProyecto) {
		this.jsonCasosUsoProyecto = jsonCasosUsoProyecto;
	}

	public void setJsonEntidades(String jsonEntidades) {
		this.jsonEntidades = jsonEntidades;
	}

	public void setJsonMensajes(String jsonMensajes) {
		this.jsonMensajes = jsonMensajes;
	}

	public void setJsonPantallas(String jsonPantallas) {
		this.jsonPantallas = jsonPantallas;
	}

	public void setJsonPasos(String jsonPasos) {
		this.jsonPasos = jsonPasos;
	}

	public void setJsonPostcondiciones(String jsonPostcondiciones) {
		this.jsonPostcondiciones = jsonPostcondiciones;
	}

	public void setJsonPrecondiciones(String jsonPrecondiciones) {
		this.jsonPrecondiciones = jsonPrecondiciones;
	}

	public void setJsonPtosExtension(String jsonPtosExtension) {
		this.jsonPtosExtension = jsonPtosExtension;
	}

	public void setJsonReglasNegocio(String jsonReglasNegocio) {
		this.jsonReglasNegocio = jsonReglasNegocio;
	}

	public void setJsonTerminosGls(String jsonTerminosGls) {
		this.jsonTerminosGls = jsonTerminosGls;
	}

	public void setJsonTrayectorias(String jsonTrayectorias) {
		this.jsonTrayectorias = jsonTrayectorias;
	}

	public void setListCU(List<CasoUso> listCU) {
		this.listCU = listCU;
	}

	public void setModel(CasoUso model) {
		this.model = model;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
//		if (this.model == null) {
//			this.model = CasoUsoBs.consultarCasoUso(idSel);
//		}
	}

	public boolean isExistenPrecondiciones() {
		return existenPrecondiciones;
	}

	public void setExistenPrecondiciones(boolean existenPrecondiciones) {
		this.existenPrecondiciones = existenPrecondiciones;
	}

	public boolean isExistenPostcondiciones() {
		return existenPostcondiciones;
	}

	public void setExistenPostcondiciones(boolean existenPostcondiciones) {
		this.existenPostcondiciones = existenPostcondiciones;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public List<String> getElementosReferencias() {
		return elementosReferencias;
	}

	public void setElementosReferencias(List<String> elementosReferencias) {
		this.elementosReferencias = elementosReferencias;
	}

	public List<String> getRestriccionesTermino() {
		return restriccionesTermino;
	}

	public void setRestriccionesTermino(List<String> restriccionesTermino) {
		this.restriccionesTermino = restriccionesTermino;
	}

	public String getObservacionesResumen() {
		return observacionesResumen;
	}

	public void setObservacionesResumen(String observacionesResumen) {
		this.observacionesResumen = observacionesResumen;
	}
	

	public String getObservacionesTrayectoria() {
		return observacionesTrayectoria;
	}

	public void setObservacionesTrayectoria(String observacionesTrayectoria) {
		this.observacionesTrayectoria = observacionesTrayectoria;
	}

	public String getObservacionesPtosExt() {
		return observacionesPuntosExt;
	}

	public void setObservacionesPtosExt(String observacionesPtosExt) {
		this.observacionesPuntosExt = observacionesPtosExt;
	}

	public Integer getEsCorrectoResumen() {
		return esCorrectoResumen;
	}

	public void setEsCorrectoResumen(Integer esCorrectoResumen) {
		this.esCorrectoResumen = esCorrectoResumen;
	}

	public Integer getEsCorrectoTrayectoria() {
		return esCorrectoTrayectoria;
	}

	public void setEsCorrectoTrayectoria(Integer esCorrectoTrayectoria) {
		this.esCorrectoTrayectoria = esCorrectoTrayectoria;
	}

	public Integer getEsCorrectoPuntosExt() {
		return esCorrectoPuntosExt;
	}

	public void setEsCorrectoPuntosExt(Integer esCorrectoPuntosExt) {
		this.esCorrectoPuntosExt = esCorrectoPuntosExt;
	}

	public boolean isPruebaGenerada() {
		return pruebaGenerada;
	}

	public void setPruebaGenerada(boolean pruebaGenerada) {
		this.pruebaGenerada = pruebaGenerada;
	}
	
	public Boolean isPruebaGenerada2() {
		return pruebaGenerada2;
	}

	public void setPruebaGenerada2(Boolean pruebaGenerada2) {
		this.pruebaGenerada2 = pruebaGenerada2;
	}
	
	
	public void setListPasos(List<List<Paso>> listPasos){
		this.listPasos=listPasos;
	}
	
	public List<List<Paso>> getListPasos(){
		return listPasos;
	}
}
