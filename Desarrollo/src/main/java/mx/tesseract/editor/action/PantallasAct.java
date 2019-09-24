package mx.tesseract.editor.action;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dto.AccionDTO;
import mx.tesseract.dto.PantallaDTO;
import mx.tesseract.editor.bs.AccionBs;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.bs.PantallaBs;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.ImageConverterUtil;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/pages/editor/")
@Results({ @Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = {
		"actionName", Constantes.ACTION_NAME_PANTALLAS }),
		@Result(name = "modulos", type = "redirectAction", params = {"actionName", Constantes.ACTION_NAME_MODULOS }),
		@Result(name = "proyectos", type = "redirectAction", params = {"actionName", Constantes.ACTION_NAME_PROYECTOS }),
		@Result(name = "acciones", type = "redirectAction", params = {"actionName", Constantes.ACTION_NAME_ACCIONES }),
		@Result(name = "referencias", type = "json", params = { "root",
		"elementosReferencias" }) })
@AllowedMethods({"gestionarAcciones"})
public class PantallasAct extends ActionSupportTESSERACT implements ModelDriven<PantallaDTO> {
	
	private static final long serialVersionUID = 1L;
	private static final String MODULOS = "modulos";
	private static final String PROYECTOS = "proyectos";
	private static final String ACCIONES = "acciones";
	private Proyecto proyecto;
	private Modulo modulo;
	private PantallaDTO model;
	
	private Integer idProyecto;
	private Integer idModulo; 
	
	private List<Pantalla> listPantallas;
	private List<AccionDTO> listAcciones;
	private Integer idSel;
	private File imagenPantalla;
	private String imagenPantallaContentType;
	private String imagenPantallaFileName;
	private String pantallaB64;
	
	@Autowired
	private LoginBs loginBs;
	
	@Autowired
	private ModuloBs moduloBs;
	
	@Autowired
	private PantallaBs pantallaBs;
	
	@Autowired
	private AccionBs accionBs;

	@SuppressWarnings("unchecked")
	public String index() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					proyecto = loginBs.consultarProyectoActivo();
					modulo = moduloBs.consultarModuloById(idModulo);
					listPantallas = pantallaBs.consultarPantallasByModulo(idProyecto, idModulo);
					resultado = INDEX;
					Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
					this.setActionMessages(msjs);
					SessionManager.delete("mensajesAccion");
				} else {
					resultado = MODULOS;
				}
			}
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public String editNew() {
		String resultado = INDEX;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					proyecto = loginBs.consultarProyectoActivo();
					modulo = moduloBs.consultarModuloById(idModulo);
					model.setClave(Clave.IU.toString());
					resultado = EDITNEW;
					Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
					this.setActionMessages(msjs);
					SessionManager.delete("mensajesAccion");
				} else {
					resultado = MODULOS;
				}
			} else {
				resultado = PROYECTOS;
			}
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}
	
	public void validateCreate() {
		if (!hasErrors()) {
			try {
				model.setIdProyecto((Integer) SessionManager.get("idProyecto"));
				model.setIdModulo((Integer) SessionManager.get("idModulo"));
				pantallaBs.registrarPantalla(model, imagenPantalla);
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
			if (!getFieldErrors().containsKey("imagenPantalla") && imagenPantalla == null) {
				addFieldError("imagenPantalla", this.getText("MSG30"));
			}
		}
	}
	
	public String create() {
		addActionMessage(getText("MSG1", new String[] { "La", "Pantalla", "registrada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String edit() {
		String resultado = INDEX;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					proyecto = loginBs.consultarProyectoActivo();
					modulo = moduloBs.consultarModuloById(idModulo);
					pantallaB64 = ImageConverterUtil.parseBytesToPNGB64String(model.getPantallaB64());
					resultado = EDIT;
					Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
					this.setActionMessages(msjs);
					SessionManager.delete("mensajesAccion");
				} else {
					resultado = MODULOS;
				}
			} else {
				resultado = PROYECTOS;
			}
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}
	
	public void validateUpdate() {
		if (!hasErrors()) {
			try {
				model.setIdProyecto((Integer) SessionManager.get("idProyecto"));
				model.setIdModulo((Integer) SessionManager.get("idModulo"));
				pantallaBs.modificarPantalla(model, imagenPantalla);
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
		addActionMessage(getText("MSG1", new String[] { "La", "Pantalla", "modificada" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String gestionarAcciones() {
		String resultado = INDEX;
		try {
			resultado = ACCIONES;
			SessionManager.set(idSel, "idPantalla");
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public String show() {
		String resultado = INDEX;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idModulo = (Integer) SessionManager.get("idModulo");
				if (idModulo != null) {
					proyecto = loginBs.consultarProyectoActivo();
					modulo = moduloBs.consultarModuloById(idModulo);
					pantallaB64 = ImageConverterUtil.parseBytesToPNGB64String(model.getPantallaB64());
					listAcciones = accionBs.consultarAccionesDTOByPantalla(model.getId());
					for (AccionDTO accionDTO : listAcciones) {
						System.out.println("Nombre: "+accionDTO.getNombre());
					}
					resultado = SHOW;
					Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
					this.setActionMessages(msjs);
					SessionManager.delete("mensajesAccion");
				} else {
					resultado = MODULOS;
				}
			} else {
				resultado = PROYECTOS;
			}
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}

	public void validateDestroy() {
		if (!hasErrors()) {
			try {
				pantallaBs.eliminarMensaje(model);
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
		}
	}
	
	public String destroy() {
		addActionMessage(getText("MSG1", new String[] { "El", "Pantalla", "eliminado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
//	public String verificarElementosReferencias() {
//		try {
//			elementosReferencias = new ArrayList<String>();
//			elementosReferencias = PantallaBs.verificarReferencias(model, null);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "referencias";
//	}
	
//	public String verificarElementosReferenciasAccion() {
//		try {
//			Accion accion = AccionBs.consultarAccion(idAccion);
//			elementosReferencias = new ArrayList<String>();
//			elementosReferencias = AccionBs.verificarReferencias(accion, null);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "referencias";
//	}

	@VisitorFieldValidator
	public PantallaDTO getModel() {
		return (model == null) ? model = new PantallaDTO() : this.model;
	}

	public void setModel(PantallaDTO model) {
		this.model = model;
	}

	public void setSession(Map<String, Object> arg0) {
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
		model = pantallaBs.consultarPantallaDTO(idSel);
	}

	public List<Pantalla> getListPantallas() {
		return listPantallas;
	}

	public void setListPantallas(List<Pantalla> listPantallas) {
		this.listPantallas = listPantallas;
	}

	public File getImagenPantalla() {
		return imagenPantalla;
	}

	public void setImagenPantalla(File imagenPantalla) {
		this.imagenPantalla = imagenPantalla;
	}

	public String getImagenPantallaContentType() {
		return imagenPantallaContentType;
	}

	public void setImagenPantallaContentType(String imagenPantallaContentType) {
		this.imagenPantallaContentType = imagenPantallaContentType;
	}

	public String getImagenPantallaFileName() {
		return imagenPantallaFileName;
	}

	public void setImagenPantallaFileName(String imagenPantallaFileName) {
		this.imagenPantallaFileName = imagenPantallaFileName;
	}

	public String getPantallaB64() {
		return pantallaB64;
	}

	public void setPantallaB64(String pantallaB64) {
		this.pantallaB64 = pantallaB64;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public List<AccionDTO> getListAcciones() {
		return listAcciones;
	}

	public void setListAcciones(List<AccionDTO> listAcciones) {
		this.listAcciones = listAcciones;
	}
	
}
