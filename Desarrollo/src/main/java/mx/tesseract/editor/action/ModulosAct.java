package mx.tesseract.editor.action;

import java.util.Collection;
import java.util.List;

import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
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
				"actionName", Constantes.ACTION_NAME_MODULOS }),
		@Result(name = "proyectos", type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_PROYECTOS }),
		@Result(name = "caso-uso", type = "redirectAction", params = { "actionName",
				Constantes.ACTION_NAME_CASO_USO }),
		@Result(name = "referencias", type = "json", params = { "root",
				"elementosReferencias" }),
		@Result(name = "pantallas", type = "redirectAction", params = { "actionName",Constantes.ACTION_NAME_PANTALLAS })

})
@AllowedMethods({"entrarIU", "entrarCU"})
public class ModulosAct extends ActionSupportTESSERACT implements ModelDriven<Modulo> {

	private static final long serialVersionUID = 1L;
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	private static final String PROYECTOS = "proyectos";
	private static final String PANTALLAS = "pantallas";
	private static final String CASOS_USO = "caso-uso";
	private Proyecto proyecto;
	private Modulo model;
	private Colaborador colaborador;
	private List<Modulo> listModulos;
	private Integer idProyecto;
	private List<String> elementosReferencias;
	private int idSel;
	
	@Autowired
	private ModuloBs moduloBs;
	
	@Autowired
	private ProyectoBs proyectoBs;

	@SuppressWarnings("unchecked")
	public String index(){
		String resultado = PROYECTOS;
		try {
			SessionManager.delete("idModulo");
			idProyecto = (Integer) SessionManager.get("idProyecto");
			proyecto = proyectoBs.consultarProyecto(idProyecto);
			model.setProyecto(proyecto);
			listModulos = moduloBs.consultarModulosProyecto(proyecto.getId());
			resultado = INDEX;
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "index", e);
		}
		return resultado;
	}

	public String editNew() {
		idProyecto = (Integer) SessionManager.get("idProyecto");
		proyecto = proyectoBs.consultarProyecto(idProyecto);
		model.setProyecto(proyecto);
		return EDITNEW;
	}

	public void validateCreate() {
		if (!hasErrors()) {
			try {
				moduloBs.registrarModulo(model, (Integer) SessionManager.get("idProyecto"));
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

	public String create(){
		addActionMessage(getText("MSG1", new String[] { "El", "Módulo", "registrado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public String edit() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = proyectoBs.consultarProyecto(idProyecto);
				model.setProyecto(proyecto);
				resultado = EDIT;
			}
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

	public void validateUpdate() {
		if (!hasErrors()) {
			try {
				moduloBs.modificarModulo(model, (Integer) SessionManager.get("idProyecto"));
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
		addActionMessage(getText("MSG1", new String[] { "El", "Módulo", "modificado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String entrarCU() {
		String resultado = INDEX;
		try {
			SessionManager.set(idSel, "idModulo");
			resultado = CASOS_USO;
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "entrarCU", e);
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public String entrarIU() {
		String resultado = INDEX;
		try {
			SessionManager.set(idSel, "idModulo");
			resultado = PANTALLAS;
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "entrarIU", e);
		}
		return resultado;
	}

	public void validateDestroy() {
		if (!hasActionErrors()) {
			try {
				moduloBs.eliminarModulo(model);
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
		addActionMessage(getText("MSG1", new String[] { "El", "Módulo", "eliminado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
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
		model = moduloBs.consultarModuloById(idSel);
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

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}
	
}
