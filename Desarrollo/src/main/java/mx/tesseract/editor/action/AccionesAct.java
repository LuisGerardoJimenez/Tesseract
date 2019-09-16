package mx.tesseract.editor.action;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dto.AccionDTO;
import mx.tesseract.dto.AtributoDTO;
import mx.tesseract.dto.EntidadDTO;
import mx.tesseract.editor.bs.AccionBs;
import mx.tesseract.editor.bs.AtributoBs;
import mx.tesseract.editor.bs.EntidadBs;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.bs.PantallaBs;
import mx.tesseract.editor.bs.TipoAccionBs;
import mx.tesseract.editor.bs.TipoDatoBs;
import mx.tesseract.editor.bs.UnidadTamanioBs;
import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.editor.entidad.TipoAccion;
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
			"actionName", Constantes.ACTION_NAME_ACCIONES }),
	@Result(name = "proyectos", type = "redirectAction", params = {
			"actionName", Constantes.ACTION_NAME_PROYECTOS }),
	@Result(name = "modulos", type = "redirectAction", params = {
			"actionName", Constantes.ACTION_NAME_MODULOS }),
	@Result(name = "pantallas", type = "redirectAction", params = {
			"actionName", Constantes.ACTION_NAME_PANTALLAS })		
})
public class AccionesAct extends ActionSupportTESSERACT implements ModelDriven<AccionDTO> {
	
	private static final long serialVersionUID = 1L;
	private static final String PANTALLAS = "pantallas";
	private static final String PROYECTOS = "proyectos";
	private static final String MODULOS = "modulos";
	private Proyecto proyecto;
	private Modulo modulo;
	private AccionDTO model;
	private Pantalla pantalla;
	
	private File imagenAccion;
	private String imagenAccionContentType;
	private String imagenAccionFileName;
	
	private List<Accion> listAcciones;
	private List<TipoAccion> listTipoAccion;
	private List<Pantalla> listPantallas;
	private Integer idSel;
	private Integer idProyecto;
	private Integer idModulo;
	private Integer idPantalla;
	
	@Autowired
	private LoginBs loginBs;
	
	@Autowired
	private ModuloBs moduloBs;
	
	@Autowired
	private PantallaBs pantallaBs;
	
	@Autowired
	private AccionBs accionBs;
	
	@Autowired
	private TipoAccionBs tipoAccionBs;
	
	public String index() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					resultado = buscarModelos();
				} else {
					resultado = MODULOS;
				}
			}
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	private String buscarModelos() {
		String resultado = null;
		idPantalla = (Integer) SessionManager.get("idPantalla");
		if (idPantalla != null) {
			proyecto = loginBs.consultarProyectoActivo();
			modulo = moduloBs.consultarModuloById(idModulo);
			pantalla = pantallaBs.consultarPantalla(idPantalla);
			listAcciones = accionBs.consultarAccionesByPantalla(idPantalla);
			resultado = INDEX;
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} else {
			resultado = PANTALLAS;
		}
		return resultado;
	}
	
	public String editNew() {
		String resultado = INDEX;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					resultado = buscarCatalogos();
				} else {
					resultado = MODULOS;
				}
			}
		} catch (TESSERACTException pe) {
			System.err.println(pe.getMessage());
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}
	
	private String buscarCatalogos() {
		String resultado = null;
		idPantalla = (Integer) SessionManager.get("idPantalla");
		if (idPantalla != null) {
			proyecto = loginBs.consultarProyectoActivo();
			modulo = moduloBs.consultarModuloById(idModulo);
			pantalla = pantallaBs.consultarPantalla(idPantalla);
			listTipoAccion = tipoAccionBs.consultarTiposAccion();
			listPantallas = pantallaBs.consultarPantallas(idProyecto);
			resultado = EDITNEW;
		} else {
			resultado = PANTALLAS;
		}
		return resultado;
	}
	
	public void validateCreate() {
		if (!hasErrors()) {
			try {
				Integer idPantala = (Integer) SessionManager.get("idPantalla");
				model.setIdPantalla(idPantala);
				accionBs.registrarAccion(model, imagenAccion);
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
			proyecto = loginBs.consultarProyectoActivo();
			if (!getFieldErrors().containsKey("imagenAccion") && imagenAccion == null) {
				addFieldError("imagenAccion", this.getText("MSG30"));
			}
			editNew();
		}
	}
	
	public String create() {
		addActionMessage(getText("MSG1", new String[] { "La", "Acción", "registrada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
//	
//	public String edit() {
//		String resultado = INDEX;
//		try {
//			idEntidad = (Integer) SessionManager.get("idEntidad");
//			if (idEntidad != null) {
//				entidad = entidadBs.consultarEntidadById(idEntidad);
//			}
//			proyecto = loginBs.consultarProyectoActivo();
//			listUnidadTamanio = unidadTamanioBs.consultarUnidadesTamanio();
//			listTipoDato = tipoDatoBs.consultarTiposDato();
//			resultado = EDIT;
//		} catch (TESSERACTException pe) {
//			System.err.println(pe.getMessage());
//			ErrorManager.agregaMensajeError(this, pe);
//		} catch (Exception e) {
//			e.printStackTrace();
//			ErrorManager.agregaMensajeError(this, e);
//		}
//		return resultado;
//	}
//	
//	public void validateUpdate() {
//		if (!hasErrors()) {
//			try {
//				atributoBs.modificarAtributo(model);
//			} catch (TESSERACTValidacionException tve) {
//				ErrorManager.agregaMensajeError(this, tve);
//				System.err.println(tve.getMessage());
//				editNew();
//			} catch (TESSERACTException te) {
//				ErrorManager.agregaMensajeError(this, te);
//				System.err.println(te.getMessage());
//				editNew();
//			} catch (Exception e) {
//				ErrorManager.agregaMensajeError(this, e);
//				e.printStackTrace();
//				editNew();
//			}
//		} else {
//			editNew();
//		}
//	}
//	
//	public String update() {
//		addActionMessage(getText("MSG1", new String[] { "El", "Atributo", "modificado" }));
//		SessionManager.set(this.getActionMessages(), "mensajesAccion");
//		return SUCCESS;
//	}
	
	@VisitorFieldValidator
	public AccionDTO getModel() {
		return (model == null) ? model = new AccionDTO() : this.model;
	}
	
	public void setModel(AccionDTO model) {
		this.model = model;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public Pantalla getPantalla() {
		return pantalla;
	}

	public void setPantalla(Pantalla pantalla) {
		this.pantalla = pantalla;
	}

	public List<Accion> getListAcciones() {
		return listAcciones;
	}

	public void setListAcciones(List<Accion> listAcciones) {
		this.listAcciones = listAcciones;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
	}

	public List<TipoAccion> getListTipoAccion() {
		return listTipoAccion;
	}

	public void setListTipoAccion(List<TipoAccion> listTipoAccion) {
		this.listTipoAccion = listTipoAccion;
	}

	public List<Pantalla> getListPantallas() {
		return listPantallas;
	}

	public void setListPantallas(List<Pantalla> listPantallas) {
		this.listPantallas = listPantallas;
	}

	public File getImagenAccion() {
		return imagenAccion;
	}

	public void setImagenAccion(File imagenAccion) {
		this.imagenAccion = imagenAccion;
	}

	public String getImagenAccionContentType() {
		return imagenAccionContentType;
	}

	public void setImagenAccionContentType(String imagenAccionContentType) {
		this.imagenAccionContentType = imagenAccionContentType;
	}

	public String getImagenAccionFileName() {
		return imagenAccionFileName;
	}

	public void setImagenAccionFileName(String imagenAccionFileName) {
		this.imagenAccionFileName = imagenAccionFileName;
	}
	
}
