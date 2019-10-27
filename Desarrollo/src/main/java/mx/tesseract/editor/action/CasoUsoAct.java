package mx.tesseract.editor.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dto.CasoUsoDTO;
import mx.tesseract.editor.bs.ActorBs;
import mx.tesseract.editor.bs.CasoUsoBs;
//import mx.tesseract.bs.ReferenciaEnum;
//import mx.tesseract.bs.TipoSeccionEnum;
//import mx.tesseract.bs.AnalisisEnum.CU_CasosUso;
//import mx.tesseract.bs.TipoSeccionEnum.TipoSeccionENUM;
//import mx.tesseract.editor.bs.CasoUsoBs;
import mx.tesseract.editor.bs.ElementoBs;
import mx.tesseract.editor.bs.EntidadBs;
import mx.tesseract.editor.bs.MensajeBs;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.bs.PantallaBs;
import mx.tesseract.editor.bs.ReglaNegocioBs;
import mx.tesseract.editor.bs.TerminoGlosarioBs;
import mx.tesseract.editor.bs.TokenBs;
//import mx.tesseract.editor.bs.TrayectoriaBs;
//import mx.tesseract.editor.bs.ElementoBs.Estado;
import mx.tesseract.editor.entidad.Actor;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.editor.entidad.Paso;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.editor.entidad.Revision;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.enums.TipoSeccionEnum;
import mx.tesseract.enums.TipoSeccionEnum.TipoSeccionENUM;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.JsonUtil;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/pages/editor/")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_CASO_USO }),
		@Result(name = "referencias", type = "json", params = { "root",
				"elementosReferencias" }),
		@Result(name = "modulos", type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_MODULOS }),
		@Result(name = "postprecondicion", type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_POSTPRECONDICION }),
		@Result(name = "trayectorias", type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_TRAYECTORIAS }),
		@Result(name = "restricciones", type = "json", params = { "root",
				"restriccionesTermino" }),
		@Result(name = "revision", type = "dispatcher", location = "caso-uso/revision.jsp"),
		@Result(name = "liberacion", type = "dispatcher", location = "caso-uso/liberacion.jsp")})
@AllowedMethods({"entrarPostprecondiciones", "entrarTrayectorias"})
public class CasoUsoAct extends ActionSupportTESSERACT implements ModelDriven<CasoUsoDTO> {

	private static final long serialVersionUID = 1L;
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	private static final String PROYECTOS = "proyectos";
	private static final String MODULOS = "modulos";
	private static final String REVISION = "revision";
	private static final String LIBERACION = "liberacion";
	private static final String POSTPRECONDICION = "postprecondicion";
	private static final String TRAYECTORIAS = "trayectorias";

	// Proyecto y módulo
	private Proyecto proyecto;
	private Modulo modulo;
	private Colaborador colaborador;
	
	private Integer idProyecto;
	private Integer idModulo;

	// Modelo
	private CasoUsoDTO model;

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
	
	@Autowired
	private ReglaNegocioBs reglaNegocioBs;
	
	@Autowired
	private EntidadBs entidadBs;
	
	@Autowired
	private PantallaBs pantallaBs;
	
	@Autowired
	private MensajeBs mensajeBs;
	
	@Autowired
	private ActorBs actorBs;
	
	@Autowired
	private TerminoGlosarioBs terminoGlosarioBs;
	
	@Autowired
	private ElementoBs elementoBs;
	
	@Autowired
	private TokenBs tokenBs;
	
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
					SessionManager.delete("idCU");
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
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "index", e);
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
					buscaElementos();
					resultado = EDITNEW;
				} else {
					resultado = MODULOS;
				}
			}
		} catch (TESSERACTValidacionException tve) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
			ErrorManager.agregaMensajeError(this, tve);
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "editNew", e);
			ErrorManager.agregaMensajeError(this, e);
		}finally{
			model.setClave(Clave.CU.toString());
		}
		return resultado;
	}
	
	public String edit() {
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
					buscaElementos();
					prepararVista();
					resultado = EDIT;
				} else {
					resultado = MODULOS;
				}
			}
		} catch (TESSERACTValidacionException tve) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
			ErrorManager.agregaMensajeError(this, tve);
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "edit", e);
			ErrorManager.agregaMensajeError(this, e);
		}finally{
			model.setClave(Clave.CU.toString());
		}
		return resultado;
	}
	
	private void buscaElementos() {
		idProyecto = (Integer) SessionManager.get("idProyecto");
		List<ReglaNegocio> listReglasNegocio = reglaNegocioBs.consultarReglaNegocioProyecto(idProyecto);
		List<Entidad> listEntidades = entidadBs.consultarEntidadesProyecto(idProyecto);
		List<Pantalla> listPantallas = pantallaBs.consultarPantallas(idProyecto);
		List<Mensaje> listMensajes = mensajeBs.consultarMensajeProyecto(idProyecto);
		List<Actor> listActores = actorBs.consultarActoresProyecto(idProyecto);
		List<TerminoGlosario> listTerminosGls = terminoGlosarioBs.consultarGlosarioProyecto(idProyecto);
		List<Atributo> listAtributos = new ArrayList<Atributo>();
		
		for (Entidad entidad : listEntidades) {
			for (Atributo atributo : entidad.getAtributos()) {
				listAtributos.add(atributo);
			}
		}
		
		if (listReglasNegocio != null) {
			this.jsonReglasNegocio = JsonUtil.mapListToJSON(listReglasNegocio);
		}
		if (listEntidades != null) {
			this.jsonEntidades = JsonUtil.mapListToJSON(listEntidades);
		}
		if (listPantallas != null) {
			this.jsonPantallas = JsonUtil.mapListToJSON(listPantallas);
		}
		if (listMensajes != null) {
			this.jsonMensajes = JsonUtil.mapListToJSON(listMensajes);
		}
		if (listActores != null) {
			this.jsonActores = JsonUtil.mapListToJSON(listActores);
		}
		if (listTerminosGls != null) {
			this.jsonTerminosGls = JsonUtil.mapListToJSON(listTerminosGls);
		}
		if (listAtributos != null) {
			this.jsonAtributos = JsonUtil.mapListToJSON(listAtributos);
		}
		if (listPasos != null) {
			this.jsonPasos = JsonUtil.mapListToJSON(listPasos);
		}

	}
 
	private void prepararVista() {
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

		model.setRedaccionActores((tokenBs.decodificarCadenasToken(model
				.getRedaccionActores())));
		
		model.setRedaccionEntradas((tokenBs.decodificarCadenasToken(model
				.getRedaccionEntradas())));
		
		model.setRedaccionSalidas((tokenBs.decodificarCadenasToken(model
				.getRedaccionSalidas())));
		
		model.setRedaccionReglasNegocio((tokenBs.decodificarCadenasToken(model
				.getRedaccionReglasNegocio())));

		for (Revision rev : model.getRevisiones()) {
			if (!rev.isRevisado()
					&& rev.getSeccion()
							.getNombre()
							.equals(TipoSeccionEnum.getNombre(TipoSeccionENUM.RESUMEN))) {
				this.observaciones = rev.getObservaciones();
			}
		}
	}
	
	public void validateCreate() {
		if (!hasErrors()) {
			try {
				
				idProyecto = (Integer) SessionManager.get("idProyecto");
				idModulo = (Integer) SessionManager.get("idModulo");
				proyecto = loginBs.consultarProyectoActivo();
				modulo = moduloBs.consultarModuloById(idModulo);
				model.setProyecto(proyecto);
				model.setModulo(modulo);
				model.setEstadoElemento(elementoBs.consultarEstadoElemento(Estado.EDICION));
				CasoUso casoUso = new CasoUso();
				casoUso.setClave(model.getClave());
				casoUso.setId(model.getId());
				casoUso.setNumero(model.getNumero());
				casoUso.setNombre(model.getNombre());
				casoUso.setDescripcion(model.getDescripcion());
				casoUso.setEstadoElemento(model.getEstadoElemento());
				casoUso.setRedaccionActores(model.getRedaccionActores());
				casoUso.setRedaccionEntradas(model.getRedaccionEntradas());
				casoUso.setRedaccionSalidas(model.getRedaccionSalidas());
				casoUso.setRedaccionReglasNegocio(model.getRedaccionReglasNegocio());
				casoUso.setProyecto(model.getProyecto());
				casoUso.setModulo(model.getModulo());
				// Se agregan las postcondiciones y precondiciones
				//agregarPostPrecondiciones(model);
				casoUsoBs.preAlmacenarObjetosToken(casoUso);
				casoUsoBs.registrarCasoUso(casoUso);
			} catch (TESSERACTValidacionException tve) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
				ErrorManager.agregaMensajeError(this, tve);
			} catch (TESSERACTException te) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
				ErrorManager.agregaMensajeError(this, te);
			} catch (Exception e) {
				TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "validateCreate", e);
				ErrorManager.agregaMensajeError(this, e);
			}finally {
				model.setClave(Clave.CU.toString());
			}
		}else {
			buscaElementos();
		}
	}
	
	public String create() {
		addActionMessage(getText("MSG1", new String[] { "El", "Caso de Uso", "registrado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public void validateDestroy() {
		if (!hasErrors()) {
			try {
				casoUsoBs.eliminarCasoUso(model);
			} catch (TESSERACTValidacionException tve) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
				ErrorManager.agregaMensajeError(this, tve);
				edit();
			} catch (TESSERACTException te) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
				ErrorManager.agregaMensajeError(this, te);
				edit();
			} catch (Exception e) {
				TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "validateDestroy", e);
				ErrorManager.agregaMensajeError(this, e);
				edit();
			}
		}
	}
	
	
	
	public String destroy() {
		addActionMessage(getText("MSG1", new String[] { "El", "Caso de Uso", "eliminado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public void validateUpdate() {
		if (!hasErrors()) {
			try {
				
				idProyecto = (Integer) SessionManager.get("idProyecto");
				idModulo = (Integer) SessionManager.get("idModulo");
				proyecto = loginBs.consultarProyectoActivo();
				modulo = moduloBs.consultarModuloById(idModulo);
				model.setProyecto(proyecto);
				model.setModulo(modulo);
				model.setEstadoElemento(elementoBs.consultarEstadoElemento(Estado.EDICION));
				CasoUso casoUso = new CasoUso();
				casoUso.setClave(model.getClave());
				casoUso.setId(model.getId());
				casoUso.setNumero(model.getNumero());
				casoUso.setNombre(model.getNombre());
				casoUso.setDescripcion(model.getDescripcion());
				casoUso.setEstadoElemento(model.getEstadoElemento());
				casoUso.setRedaccionActores(model.getRedaccionActores());
				casoUso.setRedaccionEntradas(model.getRedaccionEntradas());
				casoUso.setRedaccionSalidas(model.getRedaccionSalidas());
				casoUso.setRedaccionReglasNegocio(model.getRedaccionReglasNegocio());
				casoUso.setProyecto(model.getProyecto());
				casoUso.setModulo(model.getModulo());
				// Se agregan las postcondiciones y precondiciones
				//agregarPostPrecondiciones(model);
				casoUsoBs.preAlmacenarObjetosToken(casoUso);
				casoUsoBs.modificarCasoUso(casoUso);
				
			} catch (TESSERACTValidacionException tve) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
				ErrorManager.agregaMensajeError(this, tve);
				edit();
			} catch (TESSERACTException te) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
				ErrorManager.agregaMensajeError(this, te);
				edit();
			} catch (Exception e) {
				TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "validateUpdate", e);
				ErrorManager.agregaMensajeError(this, e);
				edit();
			}
		}else {
			buscaElementos();
		}
	}
	
	public String update() {
		addActionMessage(getText("MSG1", new String[] { "El", "Caso de Uso", "modificado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String entrarPostprecondiciones() {
		String resultado = INDEX;
		try {
			resultado = POSTPRECONDICION;
			SessionManager.set(idSel, "idCU");
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "entrarPostprecondiciones", e);
		}
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public String entrarTrayectorias() {
		String resultado = INDEX;
		try {
			resultado = TRAYECTORIAS;
			SessionManager.set(idSel, "idCU");
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "entrarTrayectorias", e);
		}
		return resultado;
	}

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
		
	public String getObservacionesPuntosExt() {
		return observacionesPuntosExt;
	}

	public void setObservacionesPuntosExt(String observacionesPuntosExt) {
		this.observacionesPuntosExt = observacionesPuntosExt;
	}
	
	@VisitorFieldValidator
	public CasoUsoDTO getModel() {
		return (model == null) ? model = new CasoUsoDTO() : model;
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

	public void setModel(CasoUsoDTO model) {
		this.model = model;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		model = casoUsoBs.consultarCasoUsoDTO(idSel);
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
