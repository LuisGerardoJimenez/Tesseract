package mx.tesseract.editor.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dto.ExtensionDTO;
import mx.tesseract.editor.bs.CasoUsoBs;
import mx.tesseract.editor.bs.ElementoBs;
import mx.tesseract.editor.bs.ExtensionBs;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.bs.TokenBs;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Paso;
import mx.tesseract.editor.entidad.Revision;
import mx.tesseract.editor.entidad.Trayectoria;
import mx.tesseract.enums.TipoSeccionEnum;
import mx.tesseract.enums.TipoSeccionEnum.TipoSeccionENUM;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.JsonUtil;
import mx.tesseract.util.SessionManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

@ResultPath("/pages/editor")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = {
				"actionName", "extensiones"}),
		@Result(name = "proyectos", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_PROYECTOS }),
		@Result(name = "modulos", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_MODULOS }),
		@Result(name = "caso-uso", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_CASO_USO }),
		@Result(name = "pasos", type = "redirectAction", params = {"actionName", Constantes.ACTION_NAME_PASOS }),
		@Result(name = "cu", type = "redirectAction", params = { "actionName",
				"cu" }) })
public class ExtensionesAct extends ActionSupportTESSERACT implements ModelDriven<ExtensionDTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	private static final String PROYECTOS = "proyectos";
	private static final String MODULOS = "modulos";
	private static final String CASO_USO = "caso-uso";
	
	private Integer idProyecto;
	private Integer idModulo;
	private Integer idColaborador;
	private Integer idCasoUso;

	private Proyecto proyecto;
	private Modulo modulo;
	private CasoUso casoUsoBase;

	private ExtensionDTO model;
	private List<ExtensionDTO> listPtosExtension;
	private Integer idCU;
	private List<CasoUso> catalogoCasoUso;

	private List<CasoUso> listCasoUso;
	private String jsonPasos;
	
	private Integer idSel;
	private Integer idCasoUsoDestino;
	
	private String observaciones;

	@Autowired
	private ExtensionBs extensionBs;
	
	@Autowired
	private LoginBs loginBs;
	
	@Autowired
	private ModuloBs moduloBs;
	
	@Autowired
	private ProyectoBs proyectoBs;
	
	@Autowired
	private CasoUsoBs casoUsoBs;
	
	@Autowired
	private TokenBs tokenBs;
	
	@Autowired
	private ElementoBs elementoBs;
	
	public String index(){
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
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	private String buscarModelos() {
		String resultado = null;
		idCasoUso = (Integer) SessionManager.get("idCU");
		if (idCasoUso != null) {
			proyecto = loginBs.consultarProyectoActivo();
			modulo = moduloBs.consultarModuloById(idModulo);
			casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
			listPtosExtension = extensionBs.consultarExtensionesByIdCasoUso(idCasoUso);
			for (Revision rev : casoUsoBase.getRevisiones()) {
				if (!rev.isRevisado()
						&& rev.getSeccion()
								.getNombre()
								.equals(TipoSeccionEnum
										.getNombre(TipoSeccionENUM.PUNTOSEXTENSION))) {
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
		String resultado = null;
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
			resultado = EDITNEW;
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
	
	public void validateCreate(){
		try {
			buscaCatalogos();
			if (!hasErrors()) {
				idCasoUso = (Integer) SessionManager.get("idCU");
				casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
				if (idCasoUsoDestino == -1) {
					throw new TESSERACTValidacionException(
							"El usuario no seleccionó el caso de uso destino.",
							"MSG4", null, "claveCasoUsoDestino");
				} else {
					model.setCasoUsoDestino(casoUsoBs.consultarCasoUso(idCasoUsoDestino));
				}
				model.setCasoUsoOrigen(casoUsoBase);
				extensionBs.preAlmacenarObjetosToken(model);
				extensionBs.registrarExtension(model);
			}else {
				editNew();
			}
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
			editNew();
		}
	}

	public String create() {
		addActionMessage(getText("MSG1", new String[] { "El", "Punto de Extensión", "registrado" }));
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
						proyecto = proyectoBs.consultarProyecto(idProyecto);
						modulo = moduloBs.consultarModuloById(idModulo);
						casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
						buscaElementos();
						buscaCatalogos();
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
	
	private void prepararVista() {
		idCasoUsoDestino = model.getCasoUsoDestino().getId();
		model.setRegion(tokenBs.decodificarCadenasToken(model.getRegion()));
		for (Revision rev : model.getCasoUsoOrigen().getRevisiones()) {
			if (!rev.isRevisado()
					&& rev.getSeccion()
							.getNombre()
							.equals(TipoSeccionEnum
									.getNombre(TipoSeccionENUM.PUNTOSEXTENSION))) {
				this.observaciones = rev.getObservaciones();
			}
		}
	}
	
	public void validateUpdate() {
		try {
			buscaCatalogos();
			if (!hasErrors()) {
				idCasoUso = (Integer) SessionManager.get("idCU");
				casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
				if (idCasoUsoDestino == -1) {
					throw new TESSERACTValidacionException(
							"El usuario no seleccionó el caso de uso destino.",
							"MSG4", null, "claveCasoUsoDestino");
				} else {
					model.setCasoUsoDestino(casoUsoBs
							.consultarCasoUso(idCasoUsoDestino));
				}

				model.setCasoUsoOrigen(casoUsoBase);
				
				extensionBs.preAlmacenarObjetosToken(model);
				extensionBs.modificarExtension(model);
			}else {
				edit();
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
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "validateCreate", e);
			ErrorManager.agregaMensajeError(this, e);
			edit();
		}
	}

	public String update() {
		addActionMessage(getText("MSG1", new String[] { "El", "Punto de Extensión", "modificado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public void validateDestroy() {
		if (!hasErrors()) {
			try {
				extensionBs.eliminarExtension(model);
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
		addActionMessage(getText("MSG1", new String[] { "El", "Punto de Extensión", "eliminado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	private void buscaElementos() {
		List<Paso> listPasos = new ArrayList<>();
		listCasoUso = casoUsoBs.consultarCasosDeUsoByProyecto(idProyecto);
		Modulo moduloAux = new Modulo();
		moduloAux.setId(modulo.getId());
		moduloAux.setNombre(modulo.getNombre());

		if (listCasoUso != null && !listCasoUso.isEmpty()) {
			for (CasoUso casoUsoi : listCasoUso) {
				if (casoUsoi.getId() == casoUsoBase.getId()) {
					CasoUso casoUsoAux = new CasoUso();
					casoUsoAux.setClave(casoUsoi.getClave());
					casoUsoAux.setNumero(casoUsoi.getNumero());
					casoUsoAux.setNombre(casoUsoi.getNombre());
					casoUsoAux.setModulo(moduloAux);
					List<Trayectoria> trayectorias = casoUsoi.getTrayectorias();
					for (Trayectoria trayectoria : trayectorias) {
						Trayectoria trayectoriaAux = new Trayectoria();
						trayectoriaAux.setClave(trayectoria.getClave());
						trayectoriaAux.setCasoUso(casoUsoAux);
						List<Paso> pasos = trayectoria.getPasos();
						for (Paso paso : pasos) {
							Paso pasoAuxiliar = new Paso();
							pasoAuxiliar.setTrayectoria(trayectoriaAux);
							pasoAuxiliar.setNumero(paso.getNumero());
							pasoAuxiliar.setRealizaActor(paso.isRealizaActor());
							pasoAuxiliar.setVerbo(paso.getVerbo());
							pasoAuxiliar.setOtroVerbo(paso.getOtroVerbo());
							pasoAuxiliar.setRedaccion(tokenBs.decodificarCadenaSinToken(paso
											.getRedaccion()));

							listPasos.add(pasoAuxiliar);
						}
					}
				}
			}

			// Se convierte en json los Pasos
			if (listPasos != null) {
				this.jsonPasos = JsonUtil.mapListToJSON(listPasos);
			}
		}

	}

	
	private void buscaCatalogos() {
		idProyecto = (Integer) SessionManager.get("idProyecto");
		listCasoUso = casoUsoBs.consultarCasosDeUsoByProyecto(idProyecto);
		catalogoCasoUso = new ArrayList<>();
		idCasoUso = (Integer) SessionManager.get("idCU");
		casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
		for (CasoUso casoUsoi : listCasoUso) {
			if (casoUsoi.getId() != casoUsoBase.getId()) {
				catalogoCasoUso.add(casoUsoi);
			}
		}

		if (catalogoCasoUso.isEmpty()) {
			throw new TESSERACTException(
					"No hay casos de uso para seleccionar como punto de extensión.",
					"MSG15", new String[] { "Casos de uso para extender."});
		}
	}

	public List<CasoUso> getCatalogoCasoUso() {
		return catalogoCasoUso;
	}

	public Integer getIdCU() {
		return idCU;
	}

	public String getJsonPasos() {
		return jsonPasos;
	}

	public List<ExtensionDTO> getListPtosExtension() {
		return listPtosExtension;
	}

	@VisitorFieldValidator
	public ExtensionDTO getModel() {
		return (this.model == null) ? model = new ExtensionDTO() : this.model;
	}

	public void setCatalogoCasoUso(List<CasoUso> catalogoCasoUso) {
		this.catalogoCasoUso = catalogoCasoUso;
	}

	public void setIdCU(Integer idCU) {
		this.idCU = idCU;
	}

	public void setJsonPasos(String jsonPasos) {
		this.jsonPasos = jsonPasos;
	}

	public void setListPtosExtension(List<ExtensionDTO> listPtosExtension) {
		this.listPtosExtension = listPtosExtension;
	}

	public void setModel(ExtensionDTO model) {
		this.model = model;
	}

	public void setSession(Map<String, Object> arg0) {
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
	
	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		this.model = extensionBs.consultarExtension(idSel);
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Integer getIdCasoUsoDestino() {
		return idCasoUsoDestino;
	}

	public void setIdCasoUsoDestino(Integer idCasoUsoDestino) {
		this.idCasoUsoDestino = idCasoUsoDestino;
	}

	public CasoUso getCasoUsoBase() {
		return casoUsoBase;
	}

	public void setCasoUsoBase(CasoUso casoUsoBase) {
		this.casoUsoBase = casoUsoBase;
	}

}
