package mx.tesseract.editor.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dto.AtributoDTO;
import mx.tesseract.dto.ReglaNegocioDTO;
import mx.tesseract.editor.bs.AtributoBs;
import mx.tesseract.editor.bs.EntidadBs;
import mx.tesseract.editor.bs.OperadorBs;
import mx.tesseract.editor.bs.ReglaNegocioBs;
import mx.tesseract.editor.bs.TipoReglaNegocioBs;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.Operador;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.editor.entidad.TipoReglaNegocio;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.SessionManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

@ResultPath("/pages/editor/")
@Results({
	@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = {
			"actionName", Constantes.ACTION_NAME_REGLAS_NEGOCIO}),
	@Result(name = "atributos", type = "json", params = { "root",
			"listAtributos", "excludeNullProperties","true" }),
	@Result(name = "entidades", type = "json", params = { "root",
			"listEntidades"}),
	@Result(name = "operadores", type = "json", params = { "root",
			"listOperadores" }),
	@Result(name = "referencias", type = "json", params = { "root",
			"elementosReferencias" }),
	@Result(name = "proyectos", type = "redirectAction", params = {
			"actionName", Constantes.ACTION_NAME_PROYECTOS }),

})
@AllowedMethods(value={"cargarAtributos"})

public class ReglasNegocioAct extends ActionSupportTESSERACT implements ModelDriven<ReglaNegocioDTO> {
	
	private static final long serialVersionUID = 1L;
	private static final String PROYECTOS = "proyectos";
	private static final String REGLASDENEGOCIO = "reglas-negocio";
	private Proyecto proyecto;
	private Integer idProyecto;
	private ReglaNegocioDTO model;
	
	private int entidadUC;
	
	private List<ReglaNegocio> listReglasNegocio;
	private List<TipoReglaNegocio> listTipoRN; 
	private List<Entidad> listEntidades;
	private List<AtributoDTO> listAtributos;
	private List<Operador> listOperadores;
	private List<Entidad> listEntidades2;
	private List<AtributoDTO> listAtributos2;
	
	private int idSel;
	
	@Autowired
	private ReglaNegocioBs reglaNegocioBs;
	
	@Autowired
	private LoginBs loginBs;
	
	@Autowired
	private TipoReglaNegocioBs tipoReglaNegocioBs;
	
	@Autowired
	private EntidadBs entidadBs;
	
	@Autowired
	private AtributoBs atributoBs;
	
	@Autowired
	private OperadorBs operadorBs;
	
	//*GESTIONAR REGLAS DE NEGOCIO*//

	@SuppressWarnings("unchecked")
	public String index() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = loginBs.consultarProyectoActivo();
				listReglasNegocio = reglaNegocioBs.consultarReglaNegocioProyecto(proyecto.getId());
				resultado = INDEX;
				Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
				this.setActionMessages(msjs);
				SessionManager.delete("mensajesAccion");
			}
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	//*REGISTRAR REGLAS DE NEGOCIO*//
	
	public String editNew() {
		String resultado = REGLASDENEGOCIO;
		try {
			proyecto = loginBs.consultarProyectoActivo();
			model.setIdProyecto(proyecto.getId());
			listTipoRN = tipoReglaNegocioBs.consultarTipoReglaNegocio();
			listEntidades = entidadBs.consultarEntidadesProyecto(proyecto.getId());
			listAtributos = new ArrayList<AtributoDTO>();
			listOperadores = operadorBs.consultarOperador();
			listEntidades2 = entidadBs.consultarEntidadesProyecto(proyecto.getId());
			listAtributos2 = new ArrayList<AtributoDTO>();
			resultado = EDITNEW;
		} catch (TESSERACTException pe) {
			System.err.println(pe.getMessage());
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}
	
	public void validateCreate() {
		if (!hasErrors()) {
			try {
				model.setIdProyecto((Integer) SessionManager.get("idProyecto"));
				reglaNegocioBs.registrarRN(model);
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
			}
		} else {
			editNew();
			System.out.println(getFieldErrors());
		}
	}
	
	public String create() {
		addActionMessage(getText("MSG1", new String[] { "La", "Regla de Negocio", "registrado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	
	//*CONSULTAR REGLAS DE NEGOCIO*//
	@SuppressWarnings("unchecked")
	public String show() {
		String resultado = REGLASDENEGOCIO;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = loginBs.consultarProyectoActivo();
				resultado = SHOW;
			}
		} catch (TESSERACTException te) {
			te.setIdMensaje("MSG26");
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}
	
	//*EDITAR REGLAS DE NEGOCIO*//
	
	public String edit() {
		String resultado = REGLASDENEGOCIO;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = loginBs.consultarProyectoActivo();
				model.setIdProyecto(proyecto.getId());
				listTipoRN = tipoReglaNegocioBs.consultarTipoReglaNegocio();
				listEntidades = entidadBs.consultarEntidadesProyecto(proyecto.getId());
				listEntidades2 = entidadBs.consultarEntidadesProyecto(proyecto.getId());
				listOperadores = operadorBs.consultarOperador();
				if (model.getIdTipoRN() == Constantes.TIPO_FORMATO_CORRECTO || model.getIdTipoRN() == Constantes.TIPO_UNICIDAD_PARAMETROS 
						|| model.getIdTipoRN() == Constantes.TIPO_COMPARACION_ATRIBUTOS) {
					listAtributos = atributoBs.consultarAtributosToRN(model.getIdEntidad1());
					
				}
				if(model.getIdTipoRN() == Constantes.TIPO_COMPARACION_ATRIBUTOS) {
					listAtributos2 = atributoBs.consultarAtributosToRN(model.getIdEntidad2());
				}
				else {
					listAtributos = new ArrayList<AtributoDTO>();
					listAtributos2 = new ArrayList<AtributoDTO>();
				}
				resultado = EDIT;
			}
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}
	
	public void validateUpdate() {
		if (!hasErrors()) {
			try {
				reglaNegocioBs.modificarRN(model);
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
		}else {
			edit();
			System.out.println(getFieldErrors());
		}
	}
	
	public String update() {
		addActionMessage(getText("MSG1", new String[] { "La", "Regla de Negocio", "modificado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	public void validateCargarAtributos() {
		System.out.println("Entro a validate"+entidadUC);
		clearErrors();
		clearActionErrors();
		clearFieldErrors();
	}
	
	public String cargarAtributos() {
		System.out.println("Entro a cargarAtributos"+entidadUC);

		listAtributos = new ArrayList<AtributoDTO>();
		try {
			listAtributos= atributoBs.consultarAtributosToRN(entidadUC);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(listAtributos.size()+"TAMAÑO");
		
		return "atributos";
	}
		
	@VisitorFieldValidator
	public ReglaNegocioDTO getModel() {
		return (model == null) ? model = new ReglaNegocioDTO() : model;
	}

	public void setModel(ReglaNegocioDTO model) {
		this.model = model;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public List<ReglaNegocio> getListReglasNegocio() {
		return listReglasNegocio;
	}

	public void setListReglasNegocio(List<ReglaNegocio> listReglasNegocio) {
		this.listReglasNegocio = listReglasNegocio;
	}

	public List<TipoReglaNegocio> getListTipoRN() {
		return listTipoRN;
	}

	public void setListTipoRN(List<TipoReglaNegocio> listTipoRN) {
		this.listTipoRN = listTipoRN;
	}

	public List<Entidad> getListEntidades() {
		return listEntidades;
	}

	public void setListEntidades(List<Entidad> listEntidades) {
		this.listEntidades = listEntidades;
	}

	

	public List<AtributoDTO> getListAtributos() {
		return listAtributos;
	}

	public void setListAtributos(List<AtributoDTO> listAtributos) {
		this.listAtributos = listAtributos;
	}

	public List<Operador> getListOperadores() {
		return listOperadores;
	}

	public void setListOperadores(List<Operador> listOperadores) {
		this.listOperadores = listOperadores;
	}

	public List<Entidad> getListEntidades2() {
		return listEntidades2;
	}

	public void setListEntidades2(List<Entidad> listEntidades2) {
		this.listEntidades2 = listEntidades2;
	}

	public List<AtributoDTO> getListAtributos2() {
		return listAtributos2;
	}

	public void setListAtributos2(List<AtributoDTO> listAtributos2) {
		this.listAtributos2 = listAtributos2;
	}
	
	public int getIdSel() {
		return idSel;
	}

	public void setIdSel(int idSel) {
		this.idSel = idSel;
		model = reglaNegocioBs.consultarRNById(idSel);
	}

	public int getEntidadUC() {
		return entidadUC;
	}

	public void setEntidadUC(int entidadUC) {
		this.entidadUC = entidadUC;
	}
	
}

