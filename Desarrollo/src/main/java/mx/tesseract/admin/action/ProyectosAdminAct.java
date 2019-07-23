package mx.tesseract.admin.action;

import java.util.Collection;
import java.util.List;

import mx.tesseract.admin.bs.ColaboradorBs;
import mx.tesseract.admin.bs.EstadoProyectoBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.ColaboradorProyecto;
import mx.tesseract.admin.entidad.EstadoProyecto;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/pages/administrador/")
@Results({ @Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = { "actionName",
		"proyectos-admin" }) })
@Conversion(conversions = {
		// key must be the name of a property for which converter should be used
		@TypeConversion(key = "model.fechaInicio", converter = "mx.tesseract.util.StrutsDateConverter"),
		@TypeConversion(key = "model.fechaTermino", converter = "mx.tesseract.util.StrutsDateConverter"),
		@TypeConversion(key = "model.fechaInicioProgramada", converter = "mx.tesseract.util.StrutsDateConverter"),
		@TypeConversion(key = "model.fechaTerminoProgramada", converter = "mx.tesseract.util.StrutsDateConverter") })
public class ProyectosAdminAct extends ActionSupportTESSERACT implements ModelDriven<Proyecto> {

	private static final long serialVersionUID = 1L;
	private Proyecto model;
	private List<Proyecto> listProyectos;
	private List<EstadoProyecto> listEstadosProyecto;
	private List<Colaborador> listPersonas;
	private Integer idSel;

	@Autowired
	private ProyectoBs proyectoBs;

	@Autowired
	private ColaboradorBs colaboradorBs;

	@Autowired
	private EstadoProyectoBs estadoProyectoBs;

	@SuppressWarnings("unchecked")
	public String index() {
		try {
			listProyectos = proyectoBs.consultarProyectos();
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return INDEX;
	}

	public String editNew() {
		String resultado = INDEX;
		try {
			buscarCatalogos();
			resultado = EDITNEW;
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}

	private void buscarCatalogos() {
		listPersonas = colaboradorBs.consultarPersonal();
		listEstadosProyecto = estadoProyectoBs.consultarEstadosNoTerminado();
	}

	public void validateCreate() {
		if (!hasErrors()) {
			try {
				proyectoBs.registrarProyecto(model);
			} catch (TESSERACTValidacionException tve) {
				ErrorManager.agregaMensajeError(this, tve);
				System.err.println(tve.getMessage());
				editNew();
			} catch (TESSERACTException te) {
				ErrorManager.agregaMensajeError(this, te);
				System.err.println(te.getMessage());
				editNew();
			} catch (Exception e) {
				ErrorManager.agregaMensajeError(this, e);
				e.printStackTrace();
				editNew();
			}
		} else {
			editNew();
		}
	}

	public String create() {
		addActionMessage(getText("MSG1", new String[] { "El", "Proyecto", "registrado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}

	public String edit() {
		String resultado = INDEX;
		try {
			buscarCatalogos();
			for (ColaboradorProyecto cp : model.getProyecto_colaboradores()) {
				if (cp.getRol().getId() == Constantes.ROL_LIDER) {
					model.setColaboradorCurp(cp.getColaborador().getCurp());
					break;
				}
			}
			resultado = EDIT;
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}

	public void validateUpdate() {
		if (!hasErrors()) {
			try {
				proyectoBs.modificarProyecto(model);
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
		} else {
			edit();
		}
	}

	public String update() {
		addActionMessage(getText("MSG1", new String[] { "El", "Proyecto", "modificado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}

	public void validateDestroy() {
		if (hasActionErrors()) {
			try {
				proyectoBs.eliminarProyecto(model);
			} catch (TESSERACTValidacionException tve) {
				ErrorManager.agregaMensajeError(this, tve);
				System.err.println(tve.getMessage());
				index();
			} catch (TESSERACTException te) {
				ErrorManager.agregaMensajeError(this, te);
				System.err.println(te.getMessage());
				index();
			} catch (Exception e) {
				ErrorManager.agregaMensajeError(this, e);
				index();
				e.printStackTrace();
			}
		}
	}

	public String destroy() {
		addActionMessage(getText("MSG1", new String[] { "El", "Proyecto", "eliminado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}

	@VisitorFieldValidator
	public Proyecto getModel() {
		return (model == null) ? model = new Proyecto() : model;
	}

	public void setModel(Proyecto model) {
		this.model = model;
	}

	public List<Proyecto> getListProyectos() {
		return listProyectos;
	}

	public void setListProyectos(List<Proyecto> listProyectos) {
		this.listProyectos = listProyectos;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		model = proyectoBs.consultarProyecto(idSel);
	}

	public List<EstadoProyecto> getListEstadosProyecto() {
		return listEstadosProyecto;
	}

	public void setListEstadosProyecto(List<EstadoProyecto> listEstadosProyecto) {
		this.listEstadosProyecto = listEstadosProyecto;
	}

	public List<Colaborador> getListPersonas() {
		return listPersonas;
	}

	public void setListPersonas(List<Colaborador> listPersonas) {
		this.listPersonas = listPersonas;
	}

}
