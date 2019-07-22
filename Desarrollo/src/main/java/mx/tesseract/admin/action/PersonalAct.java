package mx.tesseract.admin.action;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/pages/administrador/")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = { "actionName", "personal" }),
		@Result(name = "referencias", type = "json", params = { "root", "proyectosLider" }) })
public class PersonalAct extends ActionSupportTESSERACT implements ModelDriven<Colaborador> {
	private static final long serialVersionUID = 1L;
	private Colaborador model;
	private List<Colaborador> listPersonal;
	private String idSel;
	private String contrasenaAnterior;
	private String correoAnterior;
	private List<String> proyectosLider;

	@Autowired
	private ColaboradorBs colaboradorBs;

	@SuppressWarnings("unchecked")
	public String index() {
		try {
			listPersonal = colaboradorBs.consultarColaboradores();
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INDEX;
	}

	public String editNew() {
		return EDITNEW;
	}

	public void validateCreate() {
		if (!hasErrors()) {
			try {
				colaboradorBs.registrarColaborador(model);
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
		addActionMessage(getText("MSG1", new String[] { "La", "Persona", "registrada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}

	public String edit() {
		correoAnterior = model.getCorreoElectronico();
		contrasenaAnterior = model.getContrasenia();
		return EDIT;
	}

	public void validateUpdate() {
		if (!hasErrors()) {
			try {
				colaboradorBs.modificarColaborador(model, correoAnterior, contrasenaAnterior);
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

	public String update() {
		addActionMessage(getText("MSG1", new String[] { "La", "Persona", "modificada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}

	public String destroy() throws Exception {
		String resultado = null;
		try {
			model.setCurp(idSel);
			colaboradorBs.eliminarColaborador(model);
			resultado = SUCCESS;
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

//	public String verificarProyectosLider() {
//		try {
//			proyectosLider = ColaboradorBs.verificarProyectosLider(model);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "referencias";
//	}

	@VisitorFieldValidator
	public Colaborador getModel() {
		return (model == null) ? model = new Colaborador() : model;
	}

	public void setModel(Colaborador model) {
		this.model = model;
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
		model = colaboradorBs.consultarPersona(idSel);
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
