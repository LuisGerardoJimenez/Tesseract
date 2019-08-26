package mx.tesseract.editor.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.bs.AccessBs;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
//import mx.tesseract.bs.AnalisisEnum.CU_Glosario;
import mx.tesseract.enums.ReferenciaEnum.TipoReferencia;
import mx.tesseract.dto.MensajeDTO;
import mx.tesseract.dto.TerminoGlosarioDTO;
import mx.tesseract.editor.bs.TerminoGlosarioBs;
import mx.tesseract.editor.bs.ElementoBs;
import mx.tesseract.editor.bs.MensajeBs;
//import mx.tesseract.editor.bs.ElementoBs;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.MensajeParametro;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Parametro;
//import mx.tesseract.editor.bs.ActorBs;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.GenericInterface;
import mx.tesseract.util.JsonUtil;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
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
@AllowedMethods(value = {"verificarParametros","prueba"})
public class MensajesAct extends ActionSupportTESSERACT implements ModelDriven<MensajeDTO> {

	private static final long serialVersionUID = 1L;
	private static final String PROYECTOS = "proyectos";
	private static final String MODULOS = "modulos";
	private Proyecto proyecto;
	private MensajeDTO model;
	private Colaborador colaborador;
	private List<Mensaje> listMensajes;
	private Integer idSel;
	private Integer idProyecto;
	private List<String> elementosReferencias;
	private List<Parametro> listParametros;
	
	private String jsonParametros;
	private String jsonParametrosGuardados;
	private String cambioRedaccion;
	private String redaccionMensaje;
	
	@Autowired
	private LoginBs loginBs;
	
	@Autowired
	private MensajeBs mensajeBs;
	
	@Autowired
	private ProyectoBs proyectoBs;

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
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	public String editNew() {
		String resultado = MODULOS;
		try {
			proyecto = loginBs.consultarProyectoActivo();
			model.setIdProyecto(proyecto.getId());
			buscarParametrosDisponibles(proyecto.getId());
			model.setClave("MSG");
			resultado = EDITNEW;
		} catch (TESSERACTException pe) {
			System.err.println(pe.getMessage());
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}
	
	public void validateCreate() {
		if (!hasErrors()) {
			try {
				model.setIdProyecto((Integer) SessionManager.get("idProyecto"));
				agregarParametros();
				model.setRedaccion('$'+model.getRedaccion());
				System.out.println(model.getParametrizado());
				
				int contador2=0;
				System.out.println(model.getRedaccion());
				while(model.getRedaccion().indexOf("PARAM")!=-1){
					System.out.println("entra al while");
					contador2++;
					break;
				}
				if(contador2==0){
					System.out.println("entra al if");
					model.setParametrizado(Constantes.NUMERO_CERO);
				}
				mensajeBs.registrarMensaje(model);
			} catch (TESSERACTValidacionException tve) {
				ErrorManager.agregaMensajeError(this, tve);
				System.err.println(tve.getMessage());
			} catch (TESSERACTException te) {
				ErrorManager.agregaMensajeError(this, te);
				System.err.println(te.getMessage());
			} catch (Exception e) {
				ErrorManager.agregaMensajeError(this, e);
				e.printStackTrace();
			}
		}
	}
	
	public String create() {
		addActionMessage(getText("MSG1", new String[] { "El", "Mensaje", "registrado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String show() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = proyectoBs.consultarProyecto(idProyecto);
				resultado = SHOW;
			}
		} catch (TESSERACTException te) {
			te.setIdMensaje("MSG26");
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
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
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}
	
	public void validateUpdate() {
		if (!hasErrors()) {
			try {
				//terminoGlosarioBs.modificarTerminoGlosario(model);
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
		addActionMessage(getText("MSG1", new String[] { "El", "Mensaje", "registrado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public void validateDestroy() {
		if (!hasErrors()) {
			try {
				mensajeBs.eliminarMensaje(model);
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
		addActionMessage(getText("MSG1", new String[] { "El", "Mensaje", "eliminado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	/* FUNCIONES ADICIONALES */
	public String prueba() {
		clearActionErrors();
		clearErrors();
		clearFieldErrors();
		return "parametros";
	}
	
	public String verificarParametros() {
		System.out.println("ENTRO A LA FUNCION");
		listParametros = new ArrayList<Parametro>();
		try {
			if (mensajeBs.esParametrizado(redaccionMensaje)) {
				listParametros = mensajeBs.obtenerParametros(redaccionMensaje,
						model.getIdProyecto());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "parametros";
	}
	
	private void buscarParametrosDisponibles(int idProyecto) {
		List<Parametro> listParametrosAux = mensajeBs.consultarParametros(idProyecto);
		List<Parametro> listParametros = new ArrayList<Parametro>();
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
		model.setParametrizado(Constantes.NUMERO_UNO);
		
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
		System.out.println("Model params size: " + model.getParametros().size());
	}
	
//
//	public String destroy() throws Exception {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			if (proyecto == null) {
//				resultado = "proyectos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(model.getProyecto(), colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//			model.setProyecto(proyecto);
//			TerminoGlosarioBs.eliminarTermino(model);
//			resultado = SUCCESS;
//			addActionMessage(getText("MSG1", new String[] { "El", "Término",
//					"eliminado" }));
//			SessionManager.set(this.getActionMessages(), "mensajesAccion");
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
//	public String verificarElementosReferencias() {
//		try {
//			elementosReferencias = new ArrayList<String>();
//			elementosReferencias = TerminoGlosarioBs
//					.verificarReferencias(model);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "referencias";
//	}
	
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
}
