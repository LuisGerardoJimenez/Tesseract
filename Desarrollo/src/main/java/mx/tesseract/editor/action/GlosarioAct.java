package mx.tesseract.editor.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.bs.AccessBs;
//import mx.tesseract.bs.AnalisisEnum.CU_Glosario;
import mx.tesseract.enums.ReferenciaEnum.TipoReferencia;
import mx.tesseract.dto.TerminoGlosarioDTO;
import mx.tesseract.editor.bs.TerminoGlosarioBs;
//import mx.tesseract.editor.bs.ElementoBs;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.editor.entidad.Modulo;
//import mx.tesseract.editor.bs.ActorBs;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
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
				"actionName", Constantes.ACTION_NAME_GLOSARIO }),
		@Result(name = "proyectos", type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_PROYECTOS }),
		@Result(name = "modulos", type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_MODULOS }),
		@Result(name = "referencias", type = "json", params = { "root",
				"elementosReferencias" }),
})
public class GlosarioAct extends ActionSupportTESSERACT implements ModelDriven<TerminoGlosarioDTO> {

	private static final long serialVersionUID = 1L;
	private static final String PROYECTOS = "proyectos";
	private static final String MODULOS = "modulos";
	private Proyecto proyecto;
	private TerminoGlosarioDTO model;
	private Colaborador colaborador;
	private List<TerminoGlosario> listGlosario;
	private Integer idSel;
	private Integer idProyecto;
	private List<String> elementosReferencias;
	
	@Autowired
	private LoginBs loginBs;
	
	@Autowired
	private TerminoGlosarioBs terminoGlosarioBs;
	
	@Autowired
	private ProyectoBs proyectoBs;

	@SuppressWarnings("unchecked")
	public String index(){
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = proyectoBs.consultarProyecto(idProyecto);
				listGlosario = terminoGlosarioBs.consultarGlosarioProyecto(proyecto.getId());
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
				terminoGlosarioBs.registrarTerminoGlosario(model);
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
		addActionMessage(getText("MSG1", new String[] { "El", "Término", "registrado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}

	public String show() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = proyectoBs.consultarProyecto(idProyecto);
				resultado = INDEX;
				Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
				this.setActionMessages(msjs);
				SessionManager.delete("mensajesAccion");
			}
//			model.setProyecto(proyecto);
			resultado = SHOW;
		} catch (TESSERACTException pe) {
			pe.setIdMensaje("MSG26");
			ErrorManager.agregaMensajeError(this, pe);
			return index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			return index();
		}
		return resultado;
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
//	public String edit() throws Exception {
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
//			ElementoBs.verificarEstado(model, CU_Glosario.MODIFICARTERMINO10_2);
//
//			resultado = EDIT;
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
//			if (proyecto == null) {
//				resultado = "proyectos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(model.getProyecto(), colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//			model.setProyecto(proyecto);
////			Actualizacion actualizacion = new Actualizacion(new Date(),
////					comentario, model,
////					SessionManager.consultarColaboradorActivo());
////			TerminoGlosarioBs.modificarTerminoGlosario(model, actualizacion);
//			TerminoGlosarioBs.modificarTerminoGlosario(model);
//			resultado = SUCCESS;
//			addActionMessage(getText("MSG1", new String[] { "El", "Término",
//					"modificado" }));
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
	public TerminoGlosarioDTO getModel() {
		return (model == null) ? model = new TerminoGlosarioDTO() : model;
	}

	public void setModel(TerminoGlosarioDTO model) {
		this.model = model;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public List<TerminoGlosario> getListGlosario() {
		return listGlosario;
	}

	public void setListGlosario(List<TerminoGlosario> listGlosario) {
		this.listGlosario = listGlosario;
	}
	
}
