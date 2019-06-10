package mx.tesseract.admin.action;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.tesseract.admin.bs.ColaboradorBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.EstadoProyecto;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;
import mx.tesseract.util.Validador;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/pages/administrador/")
@Results({ @Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = {
		"actionName", "proyectos-admin" })
})
@Conversion(
	    conversions = {
	         // key must be the name of a property for which converter should be used
	         @TypeConversion(key = "model.fechaInicio", converter = "mx.tesseract.util.StrutsDateConverter"),
	         @TypeConversion(key = "model.fechaTermino", converter = "mx.tesseract.util.StrutsDateConverter"),
	         @TypeConversion(key = "model.fechaInicioProgramada", converter = "mx.tesseract.util.StrutsDateConverter"),
	         @TypeConversion(key = "model.fechaTerminoProgramada", converter = "mx.tesseract.util.StrutsDateConverter")
	    }
	)
public class ProyectosAdminAct extends ActionSupportTESSERACT implements
ModelDriven<Proyecto>, SessionAware{
	private Proyecto model;
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private List<Proyecto> listProyectos;
	private List<EstadoProyecto> listEstadosProyecto;
	private List<Colaborador> listPersonas;
	private Integer idSel;
	private int idEstadoProyecto;
	private String curpLider;
	private String presupuestoString;
	
	@Autowired
	private ProyectoBs proyectoBs;
	
	public String index() throws Exception {
		try {
			listProyectos = proyectoBs.consultarProyectos();
			@SuppressWarnings("unchecked")
			Collection<String> msjs = (Collection<String>) SessionManager
					.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return INDEX;
	}
	
	/*public String editNew() throws Exception {
		String resultado;
		try {
			buscarCatalogos();
			resultado = EDITNEW;
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}
	
	private void buscarCatalogos() {
		listEstadosProyecto = ProyectoBs.consultarEstadosProyectoRegistro();
		listPersonas = ColaboradorBs.consultarPersonal();
	}
	
	private void buscarCatalogosModificacion() {
		listEstadosProyecto = ProyectoBs.consultarEstadosProyecto();
		listPersonas = ColaboradorBs.consultarPersonal();
	}*/

	public String create() throws Exception {
		String resultado = "";
		try {
			//ProyectoBs.registrarProyecto(model, curpLider, idEstadoProyecto, presupuestoString);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Proyecto", "registrado" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (TESSERACTValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			//resultado = editNew();
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

		String resultado;
		try {
			//buscarCatalogosModificacion();
			//prepararVista();
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

	/*private void prepararVista() {
		idEstadoProyecto = model.getEstadoProyecto().getId();
		curpLider = ProyectoBs.consultarColaboradorProyectoLider(model).getColaborador().getCurp();
		if (!Validador.esNulo(model.getPresupuesto())) {
			DecimalFormat df2 = new DecimalFormat(".##");
			presupuestoString = df2.format(model.getPresupuesto()).toString();
		}
	}*/

	public String update() throws Exception {
		String resultado;
		try {
			//ProyectoBs.modificarProyecto(model, curpLider, idEstadoProyecto, presupuestoString);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Proyecto", "modificado" }));
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
			//ProyectoBs.eliminarProyecto(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Proyecto", "eliminado" }));
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
	
	//@VisitorFieldValidator
	public Proyecto getModel() {
		return (model == null) ? model = new Proyecto() : model;
	}
	public void setModel(Proyecto model) {
		this.model = model;
	}
	public Map<String, Object> getUserSession() {
		return userSession;
	}
	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
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
		//model = ProyectoBs.consultarProyecto(idSel);
	}
	public void setSession(Map<String, Object> session) {
		
	}

	public List<EstadoProyecto> getListEstadosProyecto() {
		return listEstadosProyecto;
	}

	public void setListEstadosProyecto(List<EstadoProyecto> listEstadosProyecto) {
		this.listEstadosProyecto = listEstadosProyecto;
	}

	public int getIdEstadoProyecto() {
		return idEstadoProyecto;
	}

	public void setIdEstadoProyecto(int idEstadoProyecto) {
		this.idEstadoProyecto = idEstadoProyecto;
	}

	public String getCurpLider() {
		return curpLider;
	}

	public void setCurpLider(String curpLider) {
		this.curpLider = curpLider;
	}

	public List<Colaborador> getListPersonas() {
		return listPersonas;
	}

	public void setListPersonas(List<Colaborador> listPersonas) {
		this.listPersonas = listPersonas;
	}

	public String getPresupuestoString() {
		return presupuestoString;
	}

	public void setPresupuestoString(String presupuesto) {
		this.presupuestoString = presupuesto;
	}
	
	

}
