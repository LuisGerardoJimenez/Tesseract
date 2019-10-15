package mx.tesseract.editor.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.TrayectoriaDTO;
import mx.tesseract.editor.bs.CasoUsoBs;
import mx.tesseract.editor.bs.ElementoBs;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.bs.TrayectoriaBs;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.Actor;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.editor.entidad.Paso;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.editor.entidad.Revision;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.editor.entidad.Trayectoria;
import mx.tesseract.enums.AnalisisEnum.CU_CasosUso;
import mx.tesseract.enums.ReferenciaEnum;
import mx.tesseract.enums.TipoSeccionEnum;
import mx.tesseract.enums.TipoSeccionEnum.TipoSeccionENUM;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.JsonUtil;
import mx.tesseract.util.SessionManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/pages/editor/")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = { "actionName",
				"trayectorias" }),
		@Result(name = "referencias", type = "json", params = { "root", "elementosReferencias" }),
		@Result(name = "cu", type = "redirectAction", params = { "actionName", "cu" }) })
public class TrayectoriasAct extends ActionSupportTESSERACT implements ModelDriven<TrayectoriaDTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;

	private static final String PROYECTOS = "proyectos";
	private static final String CASOUSO = "caso-uso";

	private Proyecto proyecto;
	private Modulo modulo;
	private Colaborador colaborador;

	private Integer idProyecto;
	private Integer idModulo;

	private CasoUso casoUso;
	private TrayectoriaDTO model;
	private Set<Paso> pasos = new HashSet<Paso>();
	private List<Trayectoria> listTrayectorias;
	private String jsonPasosTabla;
	private Integer idCU;
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
	private Integer idSelPaso;

	private boolean existeTPrincipal;
	private List<String> listAlternativa;
	private String alternativaPrincipal;

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
	private GenericoDAO genericoDAO;

	@Autowired
	private ElementoBs elementoBs;

	public String index(){
		String resultado;
		Map<String, Object> session = null;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					proyecto = loginBs.consultarProyectoActivo();
					modulo = moduloBs.consultarModuloById(idModulo);
					System.out.println(idCU);
					idCU = 416;
					casoUso = genericoDAO.findById(CasoUso.class, idCU);
					model.setCasoUso(casoUso);

					listTrayectorias = new ArrayList<Trayectoria>();
					for (Trayectoria t : casoUso.getTrayectorias()) {
						listTrayectorias.add(t);
					}
					for (Revision rev : model.getCasoUso().getRevisiones()) {
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
					resultado = CASOUSO;
				}
			}
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INDEX;
	}

	public String editNew() throws Exception {

		String resultado = null;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					idCU = 416;
					existeTPrincipal = trayectoriaBs.existeTrayectoriaPrincipal(idCU);
					buscaElementos();
					buscaCatalogos();

					resultado = EDITNEW;
					Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
					this.setActionMessages(msjs);
					SessionManager.delete("mensajesAccion");
				} else {
					resultado = CASOUSO;
				}
			}
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public void validateCreate() {
		clearActionErrors();
		clearErrors();
		clearFieldErrors();
		if (!hasErrors()) {
			try {
				if (alternativaPrincipal == null || alternativaPrincipal.equals("Alternativa")) {
					model.setAlternativa(true);
				} else if (alternativaPrincipal.equals("Principal")) {
					model.setAlternativa(false);
				} else {
					throw new TESSERACTValidacionException("El usuario no seleccionó el tipo de la trayectoria.",
							"MSG4", null, "alternativaPrincipal");
				}
				idCU = 416;
				casoUso = genericoDAO.findById(CasoUso.class, idCU);
				model.setCasoUso(casoUso);
				// agregarPasos();
				// trayectoriaBs.preAlmacenarObjetosToken(model, idModulo);

				trayectoriaBs.registrarTrayectoria(model, idCU);
			} catch (TESSERACTValidacionException tve) {
				ErrorManager.agregaMensajeError(this, tve);
				System.err.println(tve.getMessage());
			} catch (TESSERACTException te) {
				ErrorManager.agregaMensajeError(this, te);
				System.err.println(te.getMessage());
			} catch (Exception e) {
				ErrorManager.agregaMensajeError(this, e);
				e.printStackTrace();
			} finally {
				model.setClave("MSG");
			}
		}
	}

	public String create() {
		addActionMessage(getText("MSG1", new String[] { "La", "Trayectoria", "registrada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}

	public String edit(){
		String resultado = null;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					idCU = 416;
					casoUso = genericoDAO.findById(CasoUso.class, idCU);
					model.setCasoUso(casoUso);
					elementoBs.verificarEstado(casoUso, CU_CasosUso.MODIFICARTRAYECTORIA5_1_1_2);
					buscaElementos();
					buscaCatalogos();
					existeTPrincipal = trayectoriaBs.existeTrayectoriaPrincipal(casoUso.getId(), model.getId());
					prepararVista();

					resultado = EDIT;
				} else {
					resultado = CASOUSO;
				}
			}

		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public void validateUpdate() {
		if (!hasErrors()) {
			try {
				if (alternativaPrincipal == null || alternativaPrincipal.equals("Alternativa")) {
					model.setAlternativa(true);
				} else if (alternativaPrincipal.equals("Principal")) {
					model.setAlternativa(false);
				} else {
					throw new TESSERACTValidacionException("El usuario no seleccionó el tipo de la trayectoria.",
							"MSG4", null, "alternativaPrincipal");
				}
				//List<Paso> pasosAux = trayectoriaBs.obtenerPasos_(model.getId());// HOLI
				//pasos.addAll(pasosAux);
				//pasos.clear();
//				model.setPasos(null);//ANTES ESTABA model.getPasos().clear();

//				agregarPasos();
//				trayectoriaBs.preAlmacenarObjetosToken(model, idModulo);
				trayectoriaBs.modificarTrayectoria(model);
			} catch (TESSERACTValidacionException tve) {
				ErrorManager.agregaMensajeError(this, tve);
				System.err.println(tve.getMessage());
				edit();
			} catch (TESSERACTException te) {
				ErrorManager.agregaMensajeError(this, te);
				System.err.println(te.getMessage());
				edit();
			} catch (Exception e) {
				ErrorManager.agregaMensajeError(this, e);
				e.printStackTrace();
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
				ErrorManager.agregaMensajeError(this, tve);
				System.err.println(tve.getMessage());
				edit();
			} catch (TESSERACTException te) {
				ErrorManager.agregaMensajeError(this, te);
				System.err.println(te.getMessage());
				edit();
			} catch (Exception e) {
				ErrorManager.agregaMensajeError(this, e);
				e.printStackTrace();
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
		listAlternativa = new ArrayList<String>();
		listAlternativa.add(Constantes.SELECT_PRINCIPAL);
		listAlternativa.add(Constantes.SELECT_ALTERNATIVA);

		// Se extraen los verbos de la BD
		listVerbos = trayectoriaBs.consultarVerbos();
	}

	private void agregarPasos() {
		/*Set<Paso> pasosModelo = new HashSet<Paso>(0);
		Set<Paso> pasosVista = new HashSet<Paso>(0);

		Paso pasoBD = null;
		if (jsonPasosTabla != null && !jsonPasosTabla.equals("")) {
			pasosVista = JsonUtil.mapJSONToSet(jsonPasosTabla, Paso.class);
			for (Paso pasoVista : pasosVista) {
				if (pasoVista.getId() != null && pasoVista.getId() != 0) {
					System.out.println("pasoVista if: " + pasoVista.getRedaccion());
					pasoBD = pasosBs.consultarPaso(pasoVista.getId());
					pasoBD.setNumero(pasoVista.getNumero());
					pasoBD.setRealizaActor(pasoVista.isRealizaActor());
					pasoBD.setVerbo(TrayectoriaBs.consultaVerbo(pasoVista.getVerbo().getNombre()));
					if (pasoVista.getOtroVerbo() != null && pasoVista.getOtroVerbo().isEmpty()) {
						pasoVista.setOtroVerbo(null);
					} else {
						pasoBD.setOtroVerbo(pasoVista.getOtroVerbo());
					}
					pasoBD.setRedaccion(pasoVista.getRedaccion());
					pasoBD.getReferencias().clear();
					pasosModelo.add(pasoBD);

				} else {
					System.out.println("pasoVista else: " + pasoVista.getRedaccion());
					pasoVista.setId(null);
					pasoVista.setVerbo(TrayectoriaBs.consultaVerbo(pasoVista.getVerbo().getNombre()));
					if (pasoVista.getOtroVerbo() != null && pasoVista.getOtroVerbo().isEmpty()) {
						pasoVista.setOtroVerbo(null);
					}
					pasoVista.setTrayectoria(model);
					pasosModelo.add(pasoVista);

				}
			}

			pasos.addAll(pasosModelo);
			System.out.println("Pasos: " + pasos);
			model.setPasos(pasos); // antes estaba model.getPasos().addAll(pasosModelo).
		}*/
	}

	private void buscaElementos() {
		TrayectoriaDTO trayectoriaDTOEle = trayectoriaBs.buscaElementos(idProyecto, idCU);
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
		// Set<Paso> pasos = model.getPasos();
//		ArrayList<Paso> pasosTabla = new ArrayList<Paso>();
//		Paso pasoAux;
//		List<Paso>pasos = TrayectoriaBs.obtenerPasos_(model.getId());//HOLI
//		for (Paso paso : pasos) {
//			pasoAux = new Paso();
//			pasoAux.setNumero(paso.getNumero());
//			pasoAux.setRedaccion(TokenBs.decodificarCadenasToken(paso
//					.getRedaccion()));
//			pasoAux.setRealizaActor(paso.isRealizaActor());
//			pasoAux.setVerbo(paso.getVerbo());
//			pasoAux.setOtroVerbo(paso.getOtroVerbo());
//			pasoAux.setId(paso.getId());
//			pasosTabla.add(pasoAux);
//		}
//
//		this.jsonPasosTabla = JsonUtil.mapListToJSON(pasosTabla);

		if (model.isAlternativa()) {
			alternativaPrincipal = "Alternativa";
		} else {
			alternativaPrincipal = "Principal";
		}

		for (Revision rev : model.getCasoUso().getRevisiones()) {
			if (!rev.isRevisado()
					&& rev.getSeccion().getNombre().equals(TipoSeccionEnum.getNombre(TipoSeccionENUM.TRAYECTORIA))) {
				this.observaciones = rev.getObservaciones();
			}
		}

	}

	/*
	 * public String verificarElementosReferencias() { try { model.setId(idSel);
	 * elementosReferencias = new ArrayList<String>(); elementosReferencias =
	 * TrayectoriaBs.verificarReferencias(model, null);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return "referencias"; }
	 * 
	 * public String verificarElementosReferenciasPaso() { try {
	 * elementosReferencias = new ArrayList<String>(); elementosReferencias =
	 * PasoBs.verificarReferencias(PasoBs .consultarPaso(idSelPaso), null);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return "referencias"; }
	 */
	@VisitorFieldValidator
	public TrayectoriaDTO getModel() {
		if (this.model == null) {
			model = new TrayectoriaDTO();
		}
		return model;
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

	public Integer getIdCU() {
		return idCU;
	}

	public void setIdCU(Integer idCU) {
		this.idCU = idCU;
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

	public void setSession(Map<String, Object> session) {
		userSession = session;

	}

	public boolean isExisteTPrincipal() {
		return existeTPrincipal;
	}

	public void setExisteTPrincipal(boolean existeTPrincipal) {
		this.existeTPrincipal = existeTPrincipal;
	}

	public List<String> getListAlternativa() {
		return listAlternativa;
	}

	public void setListAlternativa(List<String> listAlternativa) {
		this.listAlternativa = listAlternativa;
	}

	public String getAlternativaPrincipal() {
		return alternativaPrincipal;
	}

	public void setAlternativaPrincipal(String alternativaPrincipal) {
		this.alternativaPrincipal = alternativaPrincipal;
	}

	public Map<String, Object> getUserSession() {
		return userSession;
	}

	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
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

	public Integer getIdSelPaso() {
		return idSelPaso;
	}

	public void setIdSelPaso(Integer idSelPaso) {
		this.idSelPaso = idSelPaso;
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

	public CasoUso getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
	}

}
