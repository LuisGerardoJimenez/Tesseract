package mx.tesseract.editor.action;

import java.util.Collection;
import java.util.List;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dto.TerminoGlosarioDTO;
import mx.tesseract.editor.bs.TerminoGlosarioBs;
import mx.tesseract.editor.entidad.TerminoGlosario;
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
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	private static final String PROYECTOS = "proyectos";
	private static final String MODULOS = "modulos";
	private Proyecto proyecto;
	private TerminoGlosarioDTO model;
	private List<TerminoGlosario> listGlosario;
	private Integer idSel;
	private Integer idProyecto;
	
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
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "index", e);
		}
		return resultado;
	}
	
	public String editNew() {
		String resultado = MODULOS;
		try {
			proyecto = loginBs.consultarProyectoActivo();
			model.setIdProyecto(proyecto.getId());
			resultado = EDITNEW;
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "editNew", e);
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
		addActionMessage(getText("MSG1", new String[] { "El", "Término", "registrado" }));
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
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "validateCreate", e);
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
				terminoGlosarioBs.modificarTerminoGlosario(model);
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
		addActionMessage(getText("MSG1", new String[] { "El", "Término", "modificado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public void validateDestroy() {
		if (!hasErrors()) {
			try {
				terminoGlosarioBs.eliminarTerminoGlosario(model);
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
		addActionMessage(getText("MSG1", new String[] { "El", "Término", "eliminado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
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
	
	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		model = terminoGlosarioBs.consultarTerminoGlosarioById(idSel);
	}
	
}
