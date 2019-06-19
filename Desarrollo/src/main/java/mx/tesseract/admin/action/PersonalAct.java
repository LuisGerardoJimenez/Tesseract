package mx.tesseract.admin.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.tesseract.admin.bs.ColaboradorBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/pages/administrador/")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = { "actionName", "personal" }),
		@Result(name = "referencias", type = "json", params = { "root", "proyectosLider" }) })
public class PersonalAct extends ActionSupportTESSERACT implements ModelDriven<Colaborador>, SessionAware {
	private Colaborador model;
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private List<Colaborador> listPersonal;
	private String idSel;
	private String contrasenaAnterior;
	private String correoAnterior;
	private List<String> proyectosLider;

	@Autowired
	private ColaboradorBs colaboradorBs;

	@SuppressWarnings("unchecked")
	public String index() throws Exception {
		try {
			listPersonal = colaboradorBs.consultarPersonal();
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INDEX;
	}

	public String editNew() throws Exception {
		return EDITNEW;
	}

	public void validateCreate() {
		if (!hasErrors()) {
			System.out.println("Pasale prro >:v");
			try {
				colaboradorBs.registrarColaborador(model);
			} catch (TESSERACTValidacionException pve) {
				ErrorManager.agregaMensajeError(this, pve);
			} catch (TESSERACTException pe) {
				ErrorManager.agregaMensajeError(this, pe);
			} catch (Exception e) {
				ErrorManager.agregaMensajeError(this, e);
			}
		} else {
			System.out.println("Hay errores prro >:v");
			Map mapa = getFieldErrors();
			System.out.println("mapa: " + mapa);
		}
	}

	public String create() throws Exception {
		addActionMessage(getText("MSG1", new String[] { "La", "Persona", "registrada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}

	public String edit() throws Exception {

		String resultado = null;
		try {
			contrasenaAnterior = model.getContrasenia();
			correoAnterior = model.getCorreoElectronico();
			resultado = EDIT;
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

	public String update() throws Exception {
		String resultado = null;
		try {
			// ColaboradorBs.modificarColaborador(model);
			// ColaboradorBs.enviarCorreo(model, contrasenaAnterior, correoAnterior);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "La", "Persona", "modificada" }));

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

	
	public String destroy() throws Exception {
	System.out.println("ENTRO");
		String resultado = null;
		try {
			colaboradorBs.eliminarColaborador(model);
			resultado = SUCCESS;
			System.out.println("entro a eliminar");
			addActionMessage(getText("MSG1", new String[] { "La", "Persona", "eliminada" }));
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

	/*
	 * public String verificarProyectosLider() { try { proyectosLider =
	 * ColaboradorBs.verificarProyectosLider(model); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return "referencias"; }
	 */

	@VisitorFieldValidator
	public Colaborador getModel() {
		return (model == null) ? model = new Colaborador() : model;
	}

	public void setModel(Colaborador model) {
		this.model = model;
	}

	public Map<String, Object> getUserSession() {
		return userSession;
	}

	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
	}

	public List<Colaborador> getListPersonal() {
		return listPersonal;
	}

	public void setListPersonal(List<Colaborador> listPersonal) {
		this.listPersonal = listPersonal;
	}

	public String getIdSel() {
		return idSel;
	}

	public void setIdSel(String idSel) {
		this.idSel = idSel;
		// model = ColaboradorBs.consultarPersona(idSel);
	}

	public void setSession(Map<String, Object> session) {

	}

	public String getContrasenaAnterior() {
		return contrasenaAnterior;
	}

	public void setContrasenaAnterior(String contrasenaAnterior) {
		this.contrasenaAnterior = contrasenaAnterior;
	}

	public String getCorreoAnterior() {
		return correoAnterior;
	}

	public void setCorreoAnterior(String correoAnterior) {
		this.correoAnterior = correoAnterior;
	}

	public List<String> getProyectosLider() {
		return proyectosLider;
	}

	public void setProyectosLider(List<String> proyectosLider) {
		this.proyectosLider = proyectosLider;
	}

}
