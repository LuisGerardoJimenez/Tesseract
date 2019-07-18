package mx.tesseract.admin.action;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.tesseract.admin.bs.ColaboradorBs;
import mx.tesseract.admin.bs.EstadoProyectoBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.EstadoProyecto;
import mx.tesseract.admin.entidad.Proyecto;
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
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
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
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}

	private void buscarCatalogos() {
		listPersonas = colaboradorBs.consultarPersonal();
		listEstadosProyecto = estadoProyectoBs.consultarEstadosNoTerminado();
	}

//	private void buscarCatalogosModificacion() {
//		listEstadosProyecto = ProyectoBs.consultarEstadosProyecto();
//		listPersonas = ColaboradorBs.consultarPersonal();
//	}

	public void validateCreate() {
		if (!hasErrors()) {
			try {
				System.out.println("Vamos a agregar proyecto");
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
				editNew();
				e.printStackTrace();
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

	public String edit() throws Exception {

		String resultado;
		try {
			// buscarCatalogosModificacion();
			// prepararVista();
			resultado = EDIT;
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

//	private void prepararVista() {
//		idEstadoProyecto = model.getEstadoProyecto().getId();
//		curpLider = ProyectoBs.consultarColaboradorProyectoLider(model).getColaborador().getCurp();
//		if (!Validador.esNulo(model.getPresupuesto())) {
//			DecimalFormat df2 = new DecimalFormat(".##");
//			presupuestoString = df2.format(model.getPresupuesto()).toString();
//		}
//	}

	public String update() throws Exception {
		String resultado;
		try {
			// ProyectoBs.modificarProyecto(model, curpLider, idEstadoProyecto,
			// presupuestoString);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El", "Proyecto", "modificado" }));
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
		String resultado;
		try {
			// ProyectoBs.eliminarProyecto(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El", "Proyecto", "eliminado" }));
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
