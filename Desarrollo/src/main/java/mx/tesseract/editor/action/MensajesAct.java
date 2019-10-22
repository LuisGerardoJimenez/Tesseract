package mx.tesseract.editor.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.enums.AnalisisEnum.CU_Mensajes;
import mx.tesseract.dto.MensajeDTO;
import mx.tesseract.editor.bs.ElementoBs;
import mx.tesseract.editor.bs.MensajeBs;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.MensajeParametro;
import mx.tesseract.editor.entidad.Parametro;
//import mx.tesseract.editor.bs.ActorBs;
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
				"actionName", Constantes.ACTION_NAME_MENSAJES }),
		@Result(name = "proyectos", type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_PROYECTOS }),
		@Result(name = "modulos", type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_MODULOS }),
		@Result(name = "parametros", type = "json", params = { "root",
		"listParametros" }),
		@Result(name = "referencias", type = "json", params = { "root",
				"elementosReferencias" }),
})
@AllowedMethods(value = {"verificarParametros"})
public class MensajesAct extends ActionSupportTESSERACT implements ModelDriven<MensajeDTO> {

	private static final long serialVersionUID = 1L;
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	private static final String PROYECTOS = "proyectos";
	private static final String MODULOS = "modulos";
	private Proyecto proyecto;
	private MensajeDTO model;
	private List<Mensaje> listMensajes;
	private Integer idSel;
	private Integer idProyecto;
	private List<String> elementosReferencias;
	private List<Parametro> listParametros;
	
	private String jsonParametros;
	private String jsonParametrosGuardados;
	private String cambioRedaccion;
	private String redaccionMensaje;
	private boolean existenParametros;
	
	private String jsonObject = "prueba";
	
	@Autowired
	private LoginBs loginBs;
	
	@Autowired
	private MensajeBs mensajeBs;
	
	@Autowired
	private ProyectoBs proyectoBs;
	
	@Autowired
	private ElementoBs elementoBs;

	@SuppressWarnings("unchecked")
	public String index(){
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = proyectoBs.consultarProyecto(idProyecto);
				listMensajes = mensajeBs.consultarMensajeProyecto(proyecto.getId());
				resultado = INDEX;
				Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
				this.setActionMessages(msjs);
				SessionManager.delete("mensajesAccion");
			}
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "index", e);
		}
		return resultado;
	}
	
	public String editNew() {
		String resultado = MODULOS;
		try {
			proyecto = loginBs.consultarProyectoActivo();
			model.setIdProyecto(proyecto.getId());
			buscarParametrosDisponibles(proyecto.getId());
			resultado = EDITNEW;
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "editNew", e);
			ErrorManager.agregaMensajeError(this, e);
		}finally{
			model.setClave("MSG");
		}
		return resultado;
	}
	
	public void validateCreate() {
		if (!hasErrors()) {
			try {
				model.setIdProyecto((Integer) SessionManager.get("idProyecto"));
				agregarParametros();
				
				int contador2=0;
				while(model.getRedaccion().indexOf("PARAM")!=-1){
					contador2++;
					break;
				}
				if(contador2==0){
					model.setParametrizado(Boolean.FALSE);
				}
				mensajeBs.registrarMensaje(model);
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
				model.setClave("MSG");
			}
		}
	}
	
	public String create() {
		addActionMessage(getText("MSG1", new String[] { "El", "Mensaje", "registrado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public String show() {
		String resultado = INDEX;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = loginBs.consultarProyectoActivo();
				this.existenParametros = model.getParametros().size() > 0 ? true
						: false;
				resultado = SHOW;
			}
		} catch (TESSERACTException te) {
			te.setIdMensaje("MSG26");
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "show", e);
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}
	
	public String edit() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = proyectoBs.consultarProyecto(idProyecto);
				model.setIdProyecto(proyecto.getId());
				resultado = EDIT;
			}
			Mensaje mensaje = new Mensaje();
			mensaje.setEstadoElemento(model.getEstadoElemento());
			elementoBs.verificarEstado(mensaje, CU_Mensajes.MODIFICARMENSAJE9_2);
			buscarParametrosDisponibles(proyecto.getId());
			prepararVista();
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
		ArrayList<Parametro> parametrosVista = new ArrayList<>();
		Parametro parametroAux = null;
		if (cambioRedaccion == null) {
			cambioRedaccion = "false";
		}

		if (jsonParametros == null || jsonParametros.isEmpty()) {
			for (MensajeParametro parametro : model.getParametros()) {
				parametroAux = new Parametro(parametro.getParametro()
						.getNombre(), parametro.getParametro().getDescripcion());
				parametrosVista.add(parametroAux);
			}
			this.jsonParametros = JsonUtil.mapListToJSON(parametrosVista);
		}

	}
	
	public void validateUpdate() {
		if (!hasErrors()) {
			try {
				model.getParametros().clear();
				agregarParametros();
				mensajeBs.modificarMensaje(model);
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
		addActionMessage(getText("MSG1", new String[] { "El", "Mensaje", "modificado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public void validateDestroy() {
		if (!hasErrors()) {
			try {
				mensajeBs.eliminarMensaje(model);
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
		addActionMessage(getText("MSG1", new String[] { "El", "Mensaje", "eliminado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	/* FUNCIONES ADICIONALES */
	
	public void validateVerificarParametros(){
		clearErrors();
		clearActionErrors();
		clearFieldErrors();
	}
	
	public String verificarParametros() {
		listParametros = new ArrayList<>();
		try {
			if (mensajeBs.esParametrizado(redaccionMensaje)) {
				listParametros = mensajeBs.obtenerParametros(redaccionMensaje,
						(Integer) SessionManager.get("idProyecto"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "parametros";
	}
	
	private void buscarParametrosDisponibles(int idProyecto) {
		List<Parametro> listParametrosAux = mensajeBs.consultarParametros(idProyecto);
		List<Parametro> listParametros = new ArrayList<>();
		for (Parametro par : listParametrosAux) {
			Parametro parametroAux = new Parametro();
			parametroAux.setNombre(par.getNombre());
			parametroAux.setDescripcion(par.getDescripcion());
			listParametros.add(parametroAux);
		}
		
		// Se convierte en json las Reglas de Negocio
		if (listParametros != null) {
			this.jsonParametrosGuardados = JsonUtil.mapListToJSON(listParametros);
		}

	}
	
	private void agregarParametros() throws Exception {
		model.setParametrizado(Boolean.TRUE);
		
		if (jsonParametros != null && !jsonParametros.equals("")) {
			Set<Parametro> parametros = JsonUtil.mapJSONToSet(jsonParametros,Parametro.class);

			for (Parametro p : parametros) {
				Parametro parametroAux = mensajeBs.consultarParametro(p.getNombre(), model.getIdProyecto());
				if (parametroAux != null) {
					// Si existe, debe mantener los cambios hechos en la vista
					parametroAux.setDescripcion(p.getDescripcion());
					model.getParametros().add(new MensajeParametro(parametroAux));
				} else {
					p.setProyecto(proyecto);
					MensajeParametro nuevoParametro = new MensajeParametro(p);
					nuevoParametro.setId(null);
					model.getParametros().add(nuevoParametro);
				}
			}
		}
	}
	
	@VisitorFieldValidator
	public MensajeDTO getModel() {
		return (model == null) ? model = new MensajeDTO() : model;
	}

	public void setModel(MensajeDTO model) {
		this.model = model;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public List<Mensaje> getListMensajes() {
		return listMensajes;
	}

	public void setListMensajes(List<Mensaje> listMensajes) {
		this.listMensajes = listMensajes;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		model = mensajeBs.consultarMensajeById(idSel);
	}

	public String getJsonParametros() {
		return jsonParametros;
	}

	public void setJsonParametros(String jsonParametros) {
		this.jsonParametros = jsonParametros;
	}

	public String getJsonParametrosGuardados() {
		return jsonParametrosGuardados;
	}

	public void setJsonParametrosGuardados(String jsonParametrosGuardados) {
		this.jsonParametrosGuardados = jsonParametrosGuardados;
	}

	public String getCambioRedaccion() {
		return cambioRedaccion;
	}

	public void setCambioRedaccion(String cambioRedaccion) {
		this.cambioRedaccion = cambioRedaccion;
	}

	public String getRedaccionMensaje() {
		return redaccionMensaje;
	}

	public void setRedaccionMensaje(String redaccionMensaje) {
		this.redaccionMensaje = redaccionMensaje;
	}

	public List<Parametro> getListParametros() {
		return listParametros;
	}

	public void setListParametros(List<Parametro> listParametros) {
		this.listParametros = listParametros;
	}

	public List<String> getElementosReferencias() {
		return elementosReferencias;
	}

	public void setElementosReferencias(List<String> elementosReferencias) {
		this.elementosReferencias = elementosReferencias;
	}

	public String getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(String jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	public boolean isExistenParametros() {
		return existenParametros;
	}

	public void setExistenParametros(boolean existenParametros) {
		this.existenParametros = existenParametros;
	}
	
}
