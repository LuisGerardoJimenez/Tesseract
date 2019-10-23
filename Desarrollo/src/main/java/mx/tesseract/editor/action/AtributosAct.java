package mx.tesseract.editor.action;

import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dto.AtributoDTO;
import mx.tesseract.editor.bs.AtributoBs;
import mx.tesseract.editor.bs.EntidadBs;
import mx.tesseract.editor.bs.TipoDatoBs;
import mx.tesseract.editor.bs.UnidadTamanioBs;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.TipoDato;
import mx.tesseract.editor.entidad.UnidadTamanio;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.SessionManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

@ResultPath("/pages/editor/")
@Results({
	@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = {
			"actionName", Constantes.ACTION_NAME_ATRIBUTOS }),
	@Result(name = "entidades", type = "redirectAction", params = {
			"actionName", Constantes.ACTION_NAME_ENTIDADES })		
})
public class AtributosAct extends ActionSupportTESSERACT implements ModelDriven<AtributoDTO> {
	
	private static final long serialVersionUID = 1L;
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	private static final String ENTIDADES = "entidades";
	private static final String PROYECTOS = "proyectos";
	private Proyecto proyecto;
	private AtributoDTO model;
	private Entidad entidad;
	
	private List<Atributo> listAtributos;
	private List<TipoDato> listTipoDato;
	private List<UnidadTamanio> listUnidadTamanio;
	private Integer idSel;
	private Integer idProyecto;
	private Integer idEntidad;
	
	@Autowired
	private LoginBs loginBs;
	
	@Autowired
	private AtributoBs atributoBs;
	
	@Autowired
	private UnidadTamanioBs unidadTamanioBs;
	
	@Autowired
	private TipoDatoBs tipoDatoBs;
	
	@Autowired
	private EntidadBs entidadBs;
	
	@SuppressWarnings("unchecked")
	public String index() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idEntidad = (Integer) SessionManager.get("idEntidad");
				if (idEntidad != null) {
					proyecto = loginBs.consultarProyectoActivo();
					entidad = entidadBs.consultarEntidadById(idEntidad);
					listAtributos = atributoBs.consultarAtributosByEntidad(idEntidad);
					resultado = INDEX;
					Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
					this.setActionMessages(msjs);
					SessionManager.delete("mensajesAccion");
				} else {
					resultado = ENTIDADES;
				}
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
		String resultado = INDEX;
		try {
			idEntidad = (Integer) SessionManager.get("idEntidad");
			if (idEntidad != null) {
				entidad = entidadBs.consultarEntidadById(idEntidad);
			}
			proyecto = loginBs.consultarProyectoActivo();
			listUnidadTamanio = unidadTamanioBs.consultarUnidadesTamanio();
			listTipoDato = tipoDatoBs.consultarTiposDato();
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
				Integer idEntidad = (Integer) SessionManager.get("idEntidad");
				model.setIdEntidad(idEntidad);
				atributoBs.registrarAtributo(model);
			} catch (TESSERACTValidacionException tve) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
				ErrorManager.agregaMensajeError(this, tve);
				editNew();
			} catch (TESSERACTException te) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
				ErrorManager.agregaMensajeError(this, te);
				editNew();
			} catch (Exception e) {
				TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "validateCreate", e);
				ErrorManager.agregaMensajeError(this, e);
				editNew();
			}
		} else {
			editNew();
		}
	}
	
	public String create() {
		addActionMessage(getText("MSG1", new String[] { "El", "Atributo", "registrado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public String edit() {
		String resultado = INDEX;
		try {
			idEntidad = (Integer) SessionManager.get("idEntidad");
			if (idEntidad != null) {
				entidad = entidadBs.consultarEntidadById(idEntidad);
			}
			proyecto = loginBs.consultarProyectoActivo();
			listUnidadTamanio = unidadTamanioBs.consultarUnidadesTamanio();
			listTipoDato = tipoDatoBs.consultarTiposDato();
			resultado = EDIT;
		} catch (TESSERACTException te) {
			TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "edit", e);
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}
	
	public void validateUpdate() {
		if (!hasErrors()) {
			try {
				atributoBs.modificarAtributo(model);
			} catch (TESSERACTValidacionException tve) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
				ErrorManager.agregaMensajeError(this, tve);
				editNew();
			} catch (TESSERACTException te) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
				ErrorManager.agregaMensajeError(this, te);
				editNew();
			} catch (Exception e) {
				TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "validateUpdate", e);
				ErrorManager.agregaMensajeError(this, e);
				editNew();
			}
		} else {
			editNew();
		}
	}
	
	public String update() {
		addActionMessage(getText("MSG1", new String[] { "El", "Atributo", "modificado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public void validateDestroy() {
		if (!hasErrors()) {
			try {
				model.setProyectoId((Integer) SessionManager.get("idProyecto"));
				atributoBs.eliminarAtributo(model);
			} catch (TESSERACTValidacionException tve) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + tve.getMessage());
				ErrorManager.agregaMensajeError(this, tve);
				editNew();
			} catch (TESSERACTException te) {
				TESSERACT_LOGGER.debug(this.getClass().getName() + ": " + te.getMessage());
				ErrorManager.agregaMensajeError(this, te);
				editNew();
			} catch (Exception e) {
				TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "validateDestroy", e);
				ErrorManager.agregaMensajeError(this, e);
				editNew();
			}
		} else {
			editNew();
		}
	}
	
	public String destroy() {
		addActionMessage(getText("MSG1", new String[] { "El", "Atributo", "eliminado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	@VisitorFieldValidator
	public AtributoDTO getModel() {
		return (model == null) ? model = new AtributoDTO() : this.model;
	}
	
	public void setModel(AtributoDTO model) {
		this.model = model;
	}

	public List<Atributo> getListAtributos() {
		return listAtributos;
	}

	public void setListAtributos(List<Atributo> listAtributos) {
		this.listAtributos = listAtributos;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		model = atributoBs.consultarAtributoById(idSel);
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public List<TipoDato> getListTipoDato() {
		return listTipoDato;
	}

	public void setListTipoDato(List<TipoDato> listTipoDato) {
		this.listTipoDato = listTipoDato;
	}

	public List<UnidadTamanio> getListUnidadTamanio() {
		return listUnidadTamanio;
	}

	public void setListUnidadTamanio(List<UnidadTamanio> listUnidadTamanio) {
		this.listUnidadTamanio = listUnidadTamanio;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}
	
}
