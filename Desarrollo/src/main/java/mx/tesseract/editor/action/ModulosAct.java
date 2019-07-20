package mx.tesseract.editor.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.bs.AccessBs;
import mx.tesseract.editor.bs.ModuloBs;
//import mx.tesseract.editor.bs.ActorBs;
//import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.GenericInterface;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;

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
				"actionName", "modulos" }),
		@Result(name = "proyectos", type = "redirectAction", params = {
				"actionName", "proyectos" }),
		@Result(name = "cu", type = "redirectAction", params = { "actionName",
				"cu" }),
		@Result(name = "referencias", type = "json", params = { "root",
				"elementosReferencias" }),
		@Result(name = "pantallas", type = "redirectAction", params = { "actionName",
				"pantallas" })

})
public class ModulosAct extends ActionSupportTESSERACT implements ModelDriven<Modulo> {

	private static final long serialVersionUID = 1L;
	private static final String PROYECTOS = "proyectos";
	private Proyecto proyecto;
	private Modulo model;
	private Colaborador colaborador;
	private List<Modulo> listModulos;
	private Integer idSel;
	private List<String> elementosReferencias;
	
	@Autowired
	private ModuloBs moduloBs;
	
	@Autowired
	private ProyectoBs proyectoBs;

	@SuppressWarnings("unchecked")
	public String index(){
		String resultado = PROYECTOS;
		try {
			SessionManager.delete("idModulo");
			Integer idProyecto = (Integer) SessionManager.get("idProyecto");
			proyecto = proyectoBs.consultarProyecto(idProyecto);
			model.setProyecto(proyecto);
			listModulos = moduloBs.consultarModulosProyecto(proyecto.getId());
			resultado = INDEX;
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public String editNew() throws Exception {
		String resultado = null;
		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			if (proyecto == null) {
//				resultado = "proyectos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(proyecto, colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
			model.setProyecto(proyecto);
			resultado = EDITNEW;
		} catch (TESSERACTException pe) {
			System.err.println(pe.getMessage());
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public String create() throws Exception {
		String resultado = null;
		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			if (proyecto == null) {
//				resultado = "proyectos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(proyecto, colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//			model.setProyecto(proyecto);
//			ModuloBs.registrarModulo(model);

			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El", "Módulo",
					"registrado" }));

			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (TESSERACTValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = editNew();
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

	public String destroy() throws Exception {
		String resultado = null;
		try {
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
//			ModuloBs.eliminarModulo(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El", "Módulo",
					"eliminado" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public String edit() throws Exception {
		String resultado = null;
		try {
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
			resultado = EDIT;
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}

		return resultado;

	}

	public String update() throws Exception {
		String resultado = null;
		try {
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
//			ModuloBs.modificarModulo(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El", "Módulo",
					"modificado" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (TESSERACTValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = edit();
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public String entrarCU() throws Exception {
		Map<String, Object> session = null;
		String resultado = null;
		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			if (idSel == null
//					|| colaborador == null
//					|| !AccessBs.verificarPermisos(model.getProyecto(),
//							colaborador)) {
//				resultado = LOGIN;
//				return resultado;
//			}

			resultado = "cu";
			session = ActionContext.getContext().getSession();
			session.put("idModulo", idSel);

			@SuppressWarnings("unchecked")
			Collection<String> msjs = (Collection<String>) SessionManager
					.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public String entrarIU() throws Exception {
		Map<String, Object> session = null;
		String resultado = null;
		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			if (idSel == null
//					|| colaborador == null
//					|| !AccessBs.verificarPermisos(model.getProyecto(),
//							colaborador)) {
//				resultado = LOGIN;
//				return resultado;
//			}

			resultado = "pantallas";
			session = ActionContext.getContext().getSession();
			session.put("idModulo", idSel);

			@SuppressWarnings("unchecked")
			Collection<String> msjs = (Collection<String>) SessionManager
					.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	public String verificarElementosReferencias() {
		System.out.println("Entre a buscar referencias");
		elementosReferencias = new ArrayList<String>();
		try {
			System.out.println("Entre a buscar referencias");
//			elementosReferencias = ModuloBs.verificarReferencias(model);
			System.out.println("elementosReferenciasSize: "+elementosReferencias.size());
			System.out.println("elementosReferencias: "+elementosReferencias);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "referencias";
	}

	@VisitorFieldValidator
	public Modulo getModel() {
		return (model == null) ? model = new Modulo() : model;
	}

	public void setModel(Modulo model) {
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
//		model = ModuloBs.consultarModulo(idSel);
	}

	public List<Modulo> getListModulos() {
		return listModulos;
	}

	public void setListModulos(List<Modulo> listModulos) {
		this.listModulos = listModulos;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public List<String> getElementosReferencias() {
		return elementosReferencias;
	}

	public void setElementosReferencias(List<String> elementosReferencias) {
		this.elementosReferencias = elementosReferencias;
	}
	
	

}
