package mx.tesseract.admin.action;

import java.util.Collection;
import java.util.List;

import mx.tesseract.admin.bs.ColaboradorBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
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
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/pages/administrador/")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_PERSONAL }),
		@Result(name = "referencias", type = "json", params = { "root", "proyectosLider" }) })
public class PersonalAct extends ActionSupportTESSERACT implements ModelDriven<Colaborador> {
	private static final long serialVersionUID = 1L;
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
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
			Collection<String> msjs = (Collection<String>) SessionManager.get(Constantes.MENSAJES_ACCION);
			this.setActionMessages(msjs);
			SessionManager.delete(Constantes.MENSAJES_ACCION);
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
		addActionMessage(getText("MSG1", new String[] { "El", "Colaborador", "registrado" }));
		SessionManager.set(this.getActionMessages(), Constantes.MENSAJES_ACCION);
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
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
				ErrorManager.agregaMensajeError(this, tve);
			} catch (TESSERACTException te) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
				ErrorManager.agregaMensajeError(this, te);
			} catch (Exception e) {
				TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "validateUpdate", e);
				ErrorManager.agregaMensajeError(this, e);
			}
		}
	}

	public String update() {
		addActionMessage(getText("MSG1", new String[] { "El", "Colaborador", "modificado" }));
		SessionManager.set(this.getActionMessages(), Constantes.MENSAJES_ACCION);
		return SUCCESS;
	}

	public void validateDestroy() {
		if (!hasErrors()) {
			try {
				colaboradorBs.eliminarColaborador(model);
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
		addActionMessage(getText("MSG1", new String[] { "El", "Colaborador", "eliminado" }));
		SessionManager.set(this.getActionMessages(), Constantes.MENSAJES_ACCION);
		return SUCCESS;
	}

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
