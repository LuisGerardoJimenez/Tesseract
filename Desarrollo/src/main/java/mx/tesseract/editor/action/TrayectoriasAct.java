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
import mx.tesseract.dto.SelectDTO;
import mx.tesseract.dto.TrayectoriaDTO;
import mx.tesseract.editor.bs.CasoUsoBs;
import mx.tesseract.editor.bs.ElementoBs;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.bs.TrayectoriaBs;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Revision;
import mx.tesseract.editor.entidad.Trayectoria;
import mx.tesseract.enums.AnalisisEnum.CU_CasosUso;
import mx.tesseract.enums.TipoSeccionEnum;
import mx.tesseract.enums.TipoSeccionEnum.TipoSeccionENUM;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.SessionManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/pages/editor/")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = { "actionName",
				"trayectorias" }),
		@Result(name = "proyectos", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_PROYECTOS }),
		@Result(name = "modulos", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_MODULOS }),
		@Result(name = "caso-uso", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_CASO_USO }),
		@Result(name = "pasos", type = "redirectAction", params = {"actionName", Constantes.ACTION_NAME_PASOS }),
		@Result(name = "referencias", type = "json", params = { "root", "elementosReferencias" }) })
@AllowedMethods({"entrarPasos"})
public class TrayectoriasAct extends ActionSupportTESSERACT implements ModelDriven<TrayectoriaDTO> {
	
	private static final long serialVersionUID = 1L;
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	private static final String PROYECTOS = "proyectos";
	private static final String MODULOS = "modulos";
	private static final String CASO_USO = "caso-uso";
	private static final String PASOS = "pasos";

	private Proyecto proyecto;
	private Modulo modulo;

	private Integer idProyecto;
	private Integer idModulo;
	private Integer idCasoUso;
	private Integer idTrayectoria;

	private CasoUso casoUsoBase;
	private TrayectoriaDTO model;
	private List<Trayectoria> listTrayectorias;
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
	private ElementoBs elementoBs;
	
	@Autowired
	private ProyectoBs proyectoBs;
	
	@Autowired
	private CasoUsoBs casoUsoBs;
	
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
			SessionManager.delete("idTrayectoria");
			proyecto = loginBs.consultarProyectoActivo();
			modulo = moduloBs.consultarModuloById(idModulo);
			casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
			model.setIdCasoUso(casoUsoBase.getId());
			listTrayectorias = new ArrayList<Trayectoria>();
			for (Trayectoria t : casoUsoBase.getTrayectorias()) {
				listTrayectorias.add(t);
			}
			for (Revision rev : casoUsoBase.getRevisiones()) {
				if (!rev.isRevisado() && rev.getSeccion().getNombre()
						.equals(TipoSeccionEnum.getNombre(TipoSeccionENUM.TRAYECTORIA))) {
					this.observaciones = rev.getObservaciones();
				}
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
						proyecto = proyectoBs.consultarProyecto(idProyecto);
						modulo = moduloBs.consultarModuloById(idModulo);
						casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
						existeTPrincipal = trayectoriaBs.existeTrayectoriaPrincipal(idCasoUso);
						buscaElementos();
						buscaCatalogos();
						resultado = EDITNEW;
						Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
						this.setActionMessages(msjs);
						SessionManager.delete("mensajesAccion");
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
				if(model.getAlternativa() == null) {
					model.setAlternativa(Boolean.TRUE);
				}
				idCasoUso = (Integer) SessionManager.get("idCU");
				casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
				model.setIdCasoUso(casoUsoBase.getId());
				trayectoriaBs.registrarTrayectoria(model, idCasoUso);
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

	public String create() {
		addActionMessage(getText("MSG1", new String[] { "La", "Trayectoria", "registrada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}

	public String edit(){
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					idCasoUso = (Integer) SessionManager.get("idCU");
					if (idCasoUso != null) {
						casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
						model.setIdCasoUso(casoUsoBase.getId());
						elementoBs.verificarEstado(casoUsoBase, CU_CasosUso.MODIFICARTRAYECTORIA5_1_1_2);
						buscaElementos();
						buscaCatalogos();
						existeTPrincipal = trayectoriaBs.existeTrayectoriaPrincipal(casoUsoBase.getId(), model.getId());
						prepararVista();
						resultado = EDIT;
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
				if(model.getAlternativa() == null) {
					model.setAlternativa(Boolean.TRUE);
				}
				trayectoriaBs.modificarTrayectoria(model);
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

	public String update() {
		addActionMessage(getText("MSG1", new String[] { "La", "Trayectoria", "modificada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public void validateDestroy() {
		if (!hasErrors()) {
			try {
				trayectoriaBs.eliminarTrayectoria(model);
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
		addActionMessage(getText("MSG1", new String[] { "La", "Trayectoria", "eliminada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	private void buscaCatalogos() {
		// Se llena la lista del catálogo de quien realiza
		listRealiza = new ArrayList<String>();
		listRealiza.add(Constantes.SELECT_ACTOR);
		listRealiza.add(Constantes.SELECT_SISTEMA);

		// Se llena la lista par indicar si es alternativa o no
		listAlternativa = new ArrayList<SelectDTO>();
		listAlternativa.add(new SelectDTO( Boolean.FALSE,Constantes.SELECT_PRINCIPAL) );
		listAlternativa.add(new SelectDTO( Boolean.TRUE,Constantes.SELECT_ALTERNATIVA));

		// Se extraen los verbos de la BD
		listVerbos = trayectoriaBs.consultarVerbos();
	}

	private void buscaElementos() {
		TrayectoriaDTO trayectoriaDTOEle = trayectoriaBs.buscaElementos(idProyecto, idCasoUso);
		// Se convierte en json las Reglas de Negocio
		if (trayectoriaDTOEle.getJsonReglasNegocio() != null) {
			this.jsonReglasNegocio = trayectoriaDTOEle.getJsonReglasNegocio();
		}
		if (trayectoriaDTOEle.getJsonEntidades() != null) {
			this.jsonEntidades = trayectoriaDTOEle.getJsonEntidades();
		}
		if (trayectoriaDTOEle.getJsonCasosUsoProyecto() != null) {
			this.jsonCasosUsoProyecto = trayectoriaDTOEle.getJsonCasosUsoProyecto();
		}
		if (trayectoriaDTOEle.getJsonPantallas() != null) {
			this.jsonPantallas = trayectoriaDTOEle.getJsonPantallas();
		}
		if (trayectoriaDTOEle.getJsonMensajes() != null) {
			this.jsonMensajes = trayectoriaDTOEle.getJsonMensajes();
		}
		if (trayectoriaDTOEle.getJsonActores() != null) {
			this.jsonActores = trayectoriaDTOEle.getJsonActores();
		}
		if (trayectoriaDTOEle.getJsonTerminosGls() != null) {
			this.jsonTerminosGls = trayectoriaDTOEle.getJsonTerminosGls();
		}
		if (trayectoriaDTOEle.getJsonAtributos() != null) {
			this.jsonAtributos = trayectoriaDTOEle.getJsonAtributos();
		}
		if (trayectoriaDTOEle.getJsonPasos() != null) {
			this.jsonPasos = trayectoriaDTOEle.getJsonPasos();
		}
		if (trayectoriaDTOEle.getJsonTrayectorias() != null) {
			this.jsonTrayectorias = trayectoriaDTOEle.getJsonTrayectorias();
		}
		if (trayectoriaDTOEle.getJsonAcciones() != null) {
			this.jsonAcciones = trayectoriaDTOEle.getJsonAcciones();
		}
	}

	private void prepararVista() {
		CasoUso casoUso = trayectoriaBs.buscarCasoUsoByTrayectoria(model);
		for (Revision rev : casoUso.getRevisiones()) {
			if (!rev.isRevisado()
					&& rev.getSeccion().getNombre().equals(TipoSeccionEnum.getNombre(TipoSeccionENUM.TRAYECTORIA))) {
				this.observaciones = rev.getObservaciones();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public String entrarPasos() {
		String resultado = INDEX;
		try {
			resultado = PASOS;
			SessionManager.set(idSel, "idTrayectoria");
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "entrarPasos", e);
			e.printStackTrace();
		}
		return resultado;
	}
	
	@Override
	@VisitorFieldValidator
	public TrayectoriaDTO getModel() {
		return (model == null) ? model = new TrayectoriaDTO() : model;
	}

	public void setModel(TrayectoriaDTO model) {
		this.model = model;
	}

	public List<Trayectoria> getListTrayectorias() {
		return listTrayectorias;
	}

	public void setListTrayectorias(List<Trayectoria> listTrayectorias) {
		this.listTrayectorias = listTrayectorias;
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
		this.model = trayectoriaBs.consultarTrayectoriaById(idSel);
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

}
