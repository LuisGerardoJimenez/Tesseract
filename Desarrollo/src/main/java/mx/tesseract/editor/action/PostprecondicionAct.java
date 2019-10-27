package mx.tesseract.editor.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dto.SelectDTO;
import mx.tesseract.editor.bs.ActorBs;
import mx.tesseract.editor.bs.CasoUsoBs;
import mx.tesseract.editor.bs.EntidadBs;
import mx.tesseract.editor.bs.MensajeBs;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.bs.PantallaBs;
import mx.tesseract.editor.bs.PostprecondicionBs;
import mx.tesseract.editor.bs.ReglaNegocioBs;
import mx.tesseract.editor.bs.TerminoGlosarioBs;
import mx.tesseract.editor.bs.TokenBs;
import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.Actor;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.editor.entidad.Paso;
import mx.tesseract.editor.entidad.PostPrecondicion;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.editor.entidad.Trayectoria;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.JsonUtil;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

@ResultPath("/pages/editor/")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_POSTPRECONDICION }),
		@Result(name = "proyectos", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_PROYECTOS }),
		@Result(name = "modulos", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_MODULOS }),
		@Result(name = "caso-uso", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_CASO_USO })})
public class PostprecondicionAct extends ActionSupportTESSERACT implements ModelDriven<PostPrecondicion> {
	private static final long serialVersionUID = 1L;
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	private static final String PROYECTOS = "proyectos";
	private static final String MODULOS = "modulos";
	private static final String CASO_USO = "caso-uso";
	private PostPrecondicion model;
	private Proyecto proyecto;
	private CasoUso casoUsoBase;
	private Modulo modulo;
	private List<PostPrecondicion> listPostprecondiciones;
	private Integer idSel;
	private Integer idProyecto;
	private Integer idModulo;
	private Integer idCasoUso;
	private List<SelectDTO> listAlternativa;
	private String alternativaPrincipal;

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
	
	@Autowired
	private LoginBs loginBs;
	
	@Autowired
	private CasoUsoBs casoUsoBs;
	
	@Autowired
	private ModuloBs moduloBs;
	
	@Autowired
	private PostprecondicionBs postprecondicionBs;
	
	@Autowired
	private TokenBs tokenBs;
	
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
	
	public String editNew() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					buscaCatalogos();
					buscaElementos();
					prepararVista();
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
					buscaCatalogos();
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
		}
		return resultado;
	}
	
	private void buscaElementos() {
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
	
	private void prepararVista() {
		model.setRedaccion(tokenBs.decodificarCadenasToken(model.getRedaccion()));
	}
	
	public void validateCreate() {
		clearErrors();
		if (!hasErrors()) {
			try {
				idProyecto = (Integer) SessionManager.get("idProyecto");
				if (idProyecto != null) {
					idModulo = (Integer) SessionManager.get("idModulo");
					if (idModulo != null) {
						registrarPostprecondicion();
					}
				}
			} catch (TESSERACTValidacionException tve) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
				ErrorManager.agregaMensajeError(this, tve);
			} catch (TESSERACTException te) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
				ErrorManager.agregaMensajeError(this, te);
			} catch (Exception e) {
				TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "validateCreate", e);
				ErrorManager.agregaMensajeError(this, e);
			}
		}
	}

	private void registrarPostprecondicion() {
		idCasoUso = (Integer) SessionManager.get("idCU");
		if (idCasoUso != null) {
			proyecto = loginBs.consultarProyectoActivo();
			modulo = moduloBs.consultarModuloById(idModulo);
			casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
			model.setCasoUso(casoUsoBase);
			postprecondicionBs.preAlmacenarObjetosToken(model, idModulo);
			postprecondicionBs.registrarPostprecondicion(model);
		}
	}

	public String create() {
		addActionMessage((model.isPrecondicion()) ? getText("MSG1", new String[] { "La", "PreCondición", "registrada" }) : getText("MSG1", new String[] { "La", "PostCondición", "registrada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public void validateUpdate() {
		if (!hasErrors()) {
			try {
				idProyecto = (Integer) SessionManager.get("idProyecto");
				if (idProyecto != null) {
					idModulo = (Integer) SessionManager.get("idModulo");
					if (idModulo != null) {
						modificarPostprecondicion();
					}
				}
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
		}
	}
	
	public void validateDestroy() {
		if (!hasErrors()) {
			try {
				postprecondicionBs.eliminarPostprecondicion(model);
			} catch (TESSERACTValidacionException tve) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
				ErrorManager.agregaMensajeError(this, tve);
				edit();
			} catch (TESSERACTException te) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
				ErrorManager.agregaMensajeError(this, te);
				edit();
			} catch (Exception e) {
				ErrorManager.agregaMensajeError(this, e);
				TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "validateDestroy", e);
				edit();
			}
		}
	}
	
	public String destroy() {
		addActionMessage(getText("MSG1", new String[] { "La", "Postprecondicion", "eliminada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	private void modificarPostprecondicion() {
		idCasoUso = (Integer) SessionManager.get("idCU");
		if (idCasoUso != null) {
			proyecto = loginBs.consultarProyectoActivo();
			modulo = moduloBs.consultarModuloById(idModulo);
			casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
			model.setCasoUso(casoUsoBase);
			postprecondicionBs.preAlmacenarObjetosToken(model, idModulo);
			postprecondicionBs.modificarPostprecondicion(model);
		}
	}

	public String update() {
		addActionMessage((model.isPrecondicion()) ? getText("MSG1", new String[] { "La", "PreCondición", "modificada" }) : getText("MSG1", new String[] { "La", "PostCondición", "modificada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	private void buscaCatalogos() {
		// Se llena la lista par indicar si es post o pre condición
		listAlternativa = new ArrayList<>();
		listAlternativa.add(new SelectDTO(Boolean.FALSE, Constantes.SELECT_POSTCONDICION));
		listAlternativa.add(new SelectDTO(Boolean.TRUE, Constantes.SELECT_PRECONDICION));
	}
	
	@SuppressWarnings("unchecked")
	private String buscarModelos() {
		String resultado = null;
		idCasoUso = (Integer) SessionManager.get("idCU");
		if (idCasoUso != null) {
			proyecto = loginBs.consultarProyectoActivo();
			modulo = moduloBs.consultarModuloById(idModulo);
			casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
			listPostprecondiciones = postprecondicionBs.consultarPostPrecondicionesByCasoUso(casoUsoBase.getId());
			for(PostPrecondicion postprecondicion : listPostprecondiciones) {
				postprecondicion.setRedaccion(tokenBs.decodificarCadenasToken(postprecondicion.getRedaccion()));
			}
			resultado = INDEX;
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} else {
			resultado = CASO_USO;
		}
		return resultado;
	}

	public PostPrecondicion getModel() {
		return (model == null) ? model = new PostPrecondicion() : model;
	}

	public void setModel(PostPrecondicion model) {
		this.model = model;
	}
	
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		model = postprecondicionBs.consultarPostPrecondicionById(idSel);
	}

	public List<PostPrecondicion> getListPostprecondiciones() {
		return listPostprecondiciones;
	}

	public void setListPostprecondiciones(List<PostPrecondicion> listPostprecondiciones) {
		this.listPostprecondiciones = listPostprecondiciones;
	}

	public CasoUso getCasoUsoBase() {
		return casoUsoBase;
	}

	public void setCasoUsoBase(CasoUso casoUso) {
		this.casoUsoBase = casoUso;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public List<SelectDTO> getListAlternativa() {
		return listAlternativa;
	}

	public void setListAlternativa(List<SelectDTO> listAlternativa) {
		this.listAlternativa = listAlternativa;
	}

	public String getAlternativaPrincipal() {
		return alternativaPrincipal;
	}

	public void setAlternativaPrincipal(String alternativaPrincipal) {
		this.alternativaPrincipal = alternativaPrincipal;
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

	public String getJsonPasos() {
		return jsonPasos;
	}

	public void setJsonPasos(String jsonPasos) {
		this.jsonPasos = jsonPasos;
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
}
