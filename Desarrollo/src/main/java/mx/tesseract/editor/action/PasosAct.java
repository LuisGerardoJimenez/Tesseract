package mx.tesseract.editor.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dto.PasoDTO;
import mx.tesseract.dto.SelectDTO;
import mx.tesseract.editor.bs.ActorBs;
import mx.tesseract.editor.bs.CasoUsoBs;
import mx.tesseract.editor.bs.EntidadBs;
import mx.tesseract.editor.bs.MensajeBs;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.bs.PantallaBs;
import mx.tesseract.editor.bs.PasoBs;
import mx.tesseract.editor.bs.ReglaNegocioBs;
import mx.tesseract.editor.bs.TerminoGlosarioBs;
import mx.tesseract.editor.bs.TokenBs;
import mx.tesseract.editor.bs.TrayectoriaBs;
import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.Actor;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.editor.entidad.Paso;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.editor.entidad.Trayectoria;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.JsonUtil;
import mx.tesseract.util.SessionManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/pages/editor/")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = { "actionName",
				Constantes.ACTION_NAME_PASOS }),
		@Result(name = "proyectos", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_PROYECTOS }),
		@Result(name = "modulos", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_MODULOS }),
		@Result(name = "caso-uso", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_CASO_USO }),
		@Result(name = "referencias", type = "json", params = { "root", "elementosReferencias" }) })
		@Result(name = "pasos", type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_PASOS })
@AllowedMethods({"subirPaso", "bajarPaso"})
public class PasosAct extends ActionSupportTESSERACT implements ModelDriven<PasoDTO> {
	
	private static final long serialVersionUID = 1L;
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	private static final String PROYECTOS = "proyectos";
	private static final String TRAYECTORIAS = "trayectorias";
	private static final String MODULOS = "modulos";
	private static final String CASO_USO = "caso-uso";
	private static final String PASOS = "pasos";

	private Proyecto proyecto;
	private Modulo modulo;
	private Trayectoria trayectoria;

	private Integer idProyecto;
	private Integer idModulo;
	private Integer idCasoUso;
	private Integer idTrayectoria;

	private CasoUso casoUsoBase;
	private PasoDTO model;
	private List<PasoDTO> listPasos;
	private String jsonPasosTabla;
	private List<String> listRealiza;
	private List<String> listVerbos;

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

	private Integer idSel;

	private boolean existeTPrincipal;
	private List<SelectDTO> listAlternativa;
	private Boolean alternativa;

	private String observaciones;
	private String comentario;
	private List<String> elementosReferencias;

	@Autowired
	private TrayectoriaBs trayectoriaBs;

	@Autowired
	private LoginBs loginBs;

	@Autowired
	private ModuloBs moduloBs;
	
	@Autowired
	private ProyectoBs proyectoBs;
	
	@Autowired
	private CasoUsoBs casoUsoBs;
	
	@Autowired
	private PasoBs pasoBs;
	
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
	private TokenBs tokenBs;
	
	public String index() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					resultado = buscarModelos();
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
	
	@SuppressWarnings("unchecked")
	private String buscarModelos() {
		String resultado = null;
		idCasoUso = (Integer) SessionManager.get("idCU");
		if (idCasoUso != null) {
			idTrayectoria = (Integer) SessionManager.get("idTrayectoria");
			if(idTrayectoria != null) {
				proyecto = loginBs.consultarProyectoActivo();
				modulo = moduloBs.consultarModuloById(idModulo);
				casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
				trayectoria = trayectoriaBs.consultarTrayectoria(idTrayectoria);
				listPasos = pasoBs.obtenerPasos(trayectoria, idModulo);
				resultado = INDEX;
				Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
				this.setActionMessages(msjs);
				SessionManager.delete("mensajesAccion");
			}else {
				resultado = TRAYECTORIAS;
			}
		} else {
			resultado = CASO_USO;
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public String editNew() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					idCasoUso = (Integer) SessionManager.get("idCU");
					if (idCasoUso != null) {
						idTrayectoria = (Integer) SessionManager.get("idTrayectoria");
						if(idTrayectoria != null) {
							proyecto = proyectoBs.consultarProyecto(idProyecto);
							modulo = moduloBs.consultarModuloById(idModulo);
							casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
							trayectoria = trayectoriaBs.consultarTrayectoria(idTrayectoria);
							buscaElementos();
							buscaCatalogos();
							resultado = EDITNEW;
							Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
							this.setActionMessages(msjs);
							SessionManager.delete("mensajesAccion");
						} else {
							resultado = TRAYECTORIAS;
						}
					} else {
						resultado = CASO_USO;
					}
				} else {
					resultado = MODULOS;
				}
			}
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
			resultado = index();
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "editNew", e);
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public void validateCreate() {
		buscaCatalogos();
		if (!hasErrors()) {
			try {
				idCasoUso = (Integer) SessionManager.get("idCU");
				casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
				idTrayectoria = (Integer) SessionManager.get("idTrayectoria");
				idModulo = (Integer) SessionManager.get("idModulo");
				model.setIdTrayectoria(idTrayectoria);
				pasoBs.preAlmacenarObjetosToken(model , casoUsoBase , idModulo);
				pasoBs.registrarPaso(model);
			} catch (TESSERACTValidacionException tve) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
				ErrorManager.agregaMensajeError(this, tve);
				editNew();
			} catch (TESSERACTException te) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
				ErrorManager.agregaMensajeError(this, te);
				editNew();
			} catch (Exception e) {
				TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "validateCreate", e);
				ErrorManager.agregaMensajeError(this, e);
				e.printStackTrace();
				editNew();
			}
		}else {
			editNew();
		}
	}

	public String create() {
		addActionMessage(getText("MSG1", new String[] { "El", "Paso", "registrado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String edit(){
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					idCasoUso = (Integer) SessionManager.get("idCU");
					if (idCasoUso != null) {
						idTrayectoria = (Integer) SessionManager.get("idTrayectoria");
						if(idTrayectoria != null) {
							proyecto = proyectoBs.consultarProyecto(idProyecto);
							modulo = moduloBs.consultarModuloById(idModulo);
							casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
							trayectoria = trayectoriaBs.consultarTrayectoria(idTrayectoria);
							buscaElementos();
							buscaCatalogos();
							prepararVista();
							resultado = EDIT;
							Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
							this.setActionMessages(msjs);
							SessionManager.delete("mensajesAccion");
						} else {
							resultado = TRAYECTORIAS;
						}
					} else {
						resultado = CASO_USO;
					}
				} else {
					resultado = MODULOS;
				}
			}
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
			resultado = index();
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "edit", e);
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public void validateUpdate() {
		buscaCatalogos();
		if (!hasErrors()) {
			try {
				idCasoUso = (Integer) SessionManager.get("idCU");
				casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
				idTrayectoria = (Integer) SessionManager.get("idTrayectoria");
				idModulo = (Integer) SessionManager.get("idModulo");
				model.setIdTrayectoria(idTrayectoria);
				pasoBs.preAlmacenarObjetosToken(model , casoUsoBase , idModulo);
				pasoBs.modificarPaso(model);
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
			}finally {
				buscaElementos();
			}
		}else {
			edit();
		}
	}

	public String update() {
		addActionMessage(getText("MSG1", new String[] { "El", "Paso", "modificado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public void validateDestroy() {
		if (!hasErrors()) {
			try {
				pasoBs.eliminarPaso(model);
			} catch (TESSERACTValidacionException tve) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
				ErrorManager.agregaMensajeError(this, tve);
				index();
			} catch (TESSERACTException te) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
				ErrorManager.agregaMensajeError(this, te);
				index();
			} catch (Exception e) {
				TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "validateDestroy", e);
				ErrorManager.agregaMensajeError(this, e);
				index();
			}
		}
	}
	
	public String destroy() {
		addActionMessage(getText("MSG1", new String[] { "El", "Paso", "eliminada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	private void buscaCatalogos() {
		// Se llena la lista del catálogo de quien realiza
		listRealiza = new ArrayList<>();
		listRealiza.add(Constantes.SELECT_ACTOR);
		listRealiza.add(Constantes.SELECT_SISTEMA);

		// Se extraen los verbos de la BD
		listVerbos = trayectoriaBs.consultarVerbos();
	}

	private void buscaElementos() {
		establecerEntidades();
		List<ReglaNegocio> listReglasNegocio = reglaNegocioBs.consultarReglaNegocioProyecto(idProyecto);
		List<Entidad> listEntidades = entidadBs.consultarEntidadesProyecto(idProyecto);
		List<Pantalla> listPantallas = pantallaBs.consultarPantallas(idProyecto);
		List<Mensaje> listMensajes = mensajeBs.consultarMensajeProyecto(idProyecto);
		List<Actor> listActores = actorBs.consultarActoresProyecto(idProyecto);
		List<TerminoGlosario> listTerminosGls = terminoGlosarioBs.consultarGlosarioProyecto(idProyecto);
		List<Atributo> listAtributos = new ArrayList<>();
		List<Paso> listPasos = new ArrayList<>();
		List<CasoUso> listCasosUso = casoUsoBs.consultarCasosDeUso(idProyecto, idModulo);
		List<Trayectoria> listTrayectorias = new ArrayList<>();
		List<Accion> listAcciones = new ArrayList<>();
		
		for (Entidad entidad : listEntidades) {
			for (Atributo atributo : entidad.getAtributos()) {
				listAtributos.add(atributo);
			}
		}
		
		for (CasoUso casoUso : listCasosUso) {
			for(Trayectoria  trayectoria :casoUso.getTrayectorias()) {
				listTrayectorias.add(trayectoria);
				for(Paso  paso :trayectoria.getPasos()) {
					listPasos.add(paso);
				}
			}
		}
		
		for (Pantalla pantalla : listPantallas) {
			for(Accion accion : pantalla.getAcciones()) {
				listAcciones.add(accion);
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
		if (listCasosUso != null) {
			this.jsonCasosUsoProyecto = JsonUtil
					.mapListToJSON(listCasosUso);
		}
		if (listTrayectorias != null) {
			this.jsonTrayectorias = JsonUtil.mapListToJSON(listTrayectorias);
		}
		
		if (listAcciones != null) {
			this.jsonAcciones = JsonUtil.mapListToJSON(listAcciones);
		}

	}

	private void establecerEntidades() {
		idProyecto = (Integer) SessionManager.get("idProyecto");
		idCasoUso = (Integer) SessionManager.get("idCU");
		casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
		idTrayectoria = (Integer) SessionManager.get("idTrayectoria");
		idModulo = (Integer) SessionManager.get("idModulo");
		model.setIdTrayectoria(idTrayectoria);
	}

	private void prepararVista() {
		model.setRedaccion(tokenBs.decodificarCadenasToken(model
				.getRedaccion()));
	}
	
	@SuppressWarnings("unchecked")
	public String entrarPasos() {
		String resultado = INDEX;
		try {
			resultado = PASOS;
			SessionManager.set(idSel, "idCU");
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "entrarPasos", e);
		}
		return resultado;
	}
	
	public String subirPaso() {
		try {
			idCasoUso = (Integer) SessionManager.get("idCU");
			casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
			idTrayectoria = (Integer) SessionManager.get("idTrayectoria");
			idModulo = (Integer) SessionManager.get("idModulo");
			pasoBs.subirPaso(model);
		} catch (TESSERACTValidacionException tve) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
			ErrorManager.agregaMensajeError(this, tve);
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "subirPaso", e);
			ErrorManager.agregaMensajeError(this, e);
		}
		return index();
	}
	
	public String bajarPaso() {
		try {
			idCasoUso = (Integer) SessionManager.get("idCU");
			casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
			idTrayectoria = (Integer) SessionManager.get("idTrayectoria");
			idModulo = (Integer) SessionManager.get("idModulo");
			pasoBs.bajarPaso(model);
		} catch (TESSERACTValidacionException tve) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
			ErrorManager.agregaMensajeError(this, tve);
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "bajarPaso", e);
			ErrorManager.agregaMensajeError(this, e);
		}
		return index();
	}
	
	@Override
	@VisitorFieldValidator
	public PasoDTO getModel() {
		return (model == null) ? model = new PasoDTO() : model;
	}

	public void setModel(PasoDTO model) {
		this.model = model;
	}

	public Integer getIdCasoUso() {
		return idCasoUso;
	}

	public void setIdCasoUso(Integer idCasoUso) {
		this.idCasoUso = idCasoUso;
	}

	public List<String> getListRealiza() {
		return listRealiza;
	}

	public void setListRealiza(List<String> listRealiza) {
		this.listRealiza = listRealiza;
	}

	public List<String> getListVerbos() {
		return listVerbos;
	}

	public void setListVerbos(List<String> listVerbos) {
		this.listVerbos = listVerbos;
	}

	public String getJsonPasosTabla() {
		return jsonPasosTabla;
	}

	public void setJsonPasosTabla(String jsonPasosTabla) {
		this.jsonPasosTabla = jsonPasosTabla;
	}

	public String getJsonReglasNegocio() {
		return jsonReglasNegocio;
	}

	public void setJsonReglasNegocio(String jsonReglasNegocio) {
		this.jsonReglasNegocio = jsonReglasNegocio;
	}

	public String getJsonEntidades() {
		return jsonEntidades;
	}

	public void setJsonEntidades(String jsonEntidades) {
		this.jsonEntidades = jsonEntidades;
	}

	public String getJsonCasosUsoProyecto() {
		return jsonCasosUsoProyecto;
	}

	public void setJsonCasosUsoProyecto(String jsonCasosUsoProyecto) {
		this.jsonCasosUsoProyecto = jsonCasosUsoProyecto;
	}

	public String getJsonPantallas() {
		return jsonPantallas;
	}

	public void setJsonPantallas(String jsonPantallas) {
		this.jsonPantallas = jsonPantallas;
	}

	public String getJsonMensajes() {
		return jsonMensajes;
	}

	public void setJsonMensajes(String jsonMensajes) {
		this.jsonMensajes = jsonMensajes;
	}

	public String getJsonActores() {
		return jsonActores;
	}

	public void setJsonActores(String jsonActores) {
		this.jsonActores = jsonActores;
	}

	public String getJsonTerminosGls() {
		return jsonTerminosGls;
	}

	public void setJsonTerminosGls(String jsonTerminosGls) {
		this.jsonTerminosGls = jsonTerminosGls;
	}

	public String getJsonAtributos() {
		return jsonAtributos;
	}

	public void setJsonAtributos(String jsonAtributos) {
		this.jsonAtributos = jsonAtributos;
	}

	public String getJsonTrayectorias() {
		return jsonTrayectorias;
	}

	public void setJsonTrayectorias(String jsonTrayectorias) {
		this.jsonTrayectorias = jsonTrayectorias;
	}

	public String getJsonAcciones() {
		return jsonAcciones;
	}

	public void setJsonAcciones(String jsonAcciones) {
		this.jsonAcciones = jsonAcciones;
	}

	public String getJsonPasos() {
		return jsonPasos;
	}

	public void setJsonPasos(String jsonPasos) {
		this.jsonPasos = jsonPasos;
	}

	public boolean isExisteTPrincipal() {
		return existeTPrincipal;
	}

	public void setExisteTPrincipal(boolean existeTPrincipal) {
		this.existeTPrincipal = existeTPrincipal;
	}

	public List<SelectDTO> getListAlternativa() {
		return listAlternativa;
	}

	public void setListAlternativa(List<SelectDTO> listAlternativa) {
		this.listAlternativa = listAlternativa;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		this.model = pasoBs.consultarPasoById(idSel);
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

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public CasoUso getCasoUsoBase() {
		return casoUsoBase;
	}

	public void setCasoUsoBase(CasoUso casoUso) {
		this.casoUsoBase = casoUso;
	}

	public Boolean getAlternativa() {
		return alternativa;
	}

	public void setAlternativa(Boolean alternativa) {
		this.alternativa = alternativa;
	}

	public Trayectoria getTrayectoria() {
		return trayectoria;
	}

	public void setTrayectoria(Trayectoria trayectoria) {
		this.trayectoria = trayectoria;
	}

	public List<PasoDTO> getListPasos() {
		return listPasos;
	}

	public void setListPasos(List<PasoDTO> listPasos) {
		this.listPasos = listPasos;
	}

}
