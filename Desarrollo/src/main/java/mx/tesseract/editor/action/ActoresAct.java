package mx.tesseract.editor.action;

import java.util.Collection;
import java.util.List;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dto.ActorDTO;
//import mx.tesseract.bs.AnalisisEnum.CU_Actores;
import mx.tesseract.editor.bs.ActorBs;
import mx.tesseract.editor.bs.CardinalidadBs;
import mx.tesseract.editor.entidad.Actor;
import mx.tesseract.editor.entidad.Cardinalidad;
import mx.tesseract.editor.entidad.Modulo;
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
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/pages/editor/")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_ACTORES }),
		@Result(name = "referencias", type = "json", params = { "root",
				Constantes.ACTION_NAME_ELEMENTOS_REFERENCIAS }),
		@Result(name = "proyectos", type = "redirectAction", params = {
				"actionName", Constantes.ACTION_NAME_PROYECTOS })		
})

public class ActoresAct extends ActionSupportTESSERACT implements ModelDriven<ActorDTO> {
	
	private static final long serialVersionUID = 1L;
	private static final String PROYECTOS = "proyectos";
	private static final String ACTORES = "actores";
	private Proyecto proyecto;
	private Modulo modulo;
	
	
	private ActorDTO model;
	private List<Actor> listActores;
	private List<Cardinalidad> listCardinalidad;
	private Integer cardinalidadSeleccionada;
	private int idSel;
	private String comentario;
	private List<String> elementosReferencias;
	private Integer idProyecto;
	
	@Autowired
	private LoginBs loginBs;
	
	@Autowired
	private ActorBs actorBs;
	
	@Autowired
	private CardinalidadBs cardinalidadBs;

	@SuppressWarnings("unchecked")
	public String index(){
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = loginBs.consultarProyectoActivo();
				listActores = actorBs.consultarActoresProyecto(proyecto.getId());
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
	
	public String editNew() {
		String resultado = ACTORES;
		try {
			proyecto = loginBs.consultarProyectoActivo();
			model.setIdProyecto(proyecto.getId());
			listCardinalidad = cardinalidadBs.consultarCardinalidad();
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
				actorBs.registrarActor(model);
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
			System.out.println(getFieldErrors());
		}
	}
	
	public String create() {
		addActionMessage(getText("MSG1", new String[] { "El", "Actor", "registrado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String show() {
		String resultado = ACTORES;
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
	
	public String edit() {
		String resultado = ACTORES;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = loginBs.consultarProyectoActivo();
				model.setIdProyecto(proyecto.getId());
				listCardinalidad = cardinalidadBs.consultarCardinalidad();
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
				actorBs.modificarActor(model);
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
		addActionMessage(getText("MSG1", new String[] { "El", "actor", "modificado" }));
		SessionManager.set(this.getActionMessages(), "mensajesAccion");
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	public String editNew() throws Exception {
//
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			if (proyecto == null) {
//				resultado = "proyectos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(proyecto, colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//			model.setProyecto(proyecto);
//			buscaCatalogos();
//			resultado = EDITNEW;
//		} catch (TESSERACTException pe) {
//			System.err.println(pe.getMessage());
//			ErrorManager.agregaMensajeError(this, pe);
//			resultado = index();
//		} catch (Exception e) {
//			e.printStackTrace();
//			ErrorManager.agregaMensajeError(this, e);
//			resultado = index();
//		}
//		return resultado;
//	}
//
//	private void buscaCatalogos() {
//		listCardinalidad = ActorBs.consultarCardinalidades();
//
//		if (listCardinalidad == null || listCardinalidad.isEmpty()) {
//			throw new TESSERACTException(
//					"No hay cardinalidades para registrar el atributo.",
//					"MSG13");
//		}
//	}
//
//	public String create() throws Exception {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			if (proyecto == null) {
//				resultado = "proyectos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(proyecto, colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//			model.setProyecto(proyecto);
//			ActorBs.registrarActor(model);
//			resultado = SUCCESS;
//			addActionMessage(getText("MSG1", new String[] { "El", "Actor",
//					"registrado" }));
//
//			SessionManager.set(this.getActionMessages(), "mensajesAccion");
//		} catch (TESSERACTValidacionException pve) {
//			ErrorManager.agregaMensajeError(this, pve);
//			resultado = editNew();
//		} catch (TESSERACTException pe) {
//			ErrorManager.agregaMensajeError(this, pe);
//			resultado = index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			resultado = index();
//		}
//		return resultado;
//	}
//
//	public String show() throws Exception {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			if (proyecto == null) {
//				resultado = "proyectos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(model.getProyecto(), colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}		
//			model = ActorBs.consultarActor(idSel);
//			model.setProyecto(proyecto);
//			resultado = SHOW;
//		} catch (TESSERACTException pe) {
//			pe.setIdMensaje("MSG26");
//			ErrorManager.agregaMensajeError(this, pe);
//			return index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			return index();
//		}
//		return resultado;
//	}
//
//	public String destroy() throws Exception {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			if (proyecto == null) {
//				resultado = "proyectos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(model.getProyecto(), colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//			model.setProyecto(proyecto);
//			ActorBs.eliminarActor(model);
//			resultado = SUCCESS;
//			addActionMessage(getText("MSG1", new String[] { "El", "Actor",
//					"eliminado" }));
//			SessionManager.set(this.getActionMessages(), "mensajesAccion");
//		} catch (TESSERACTException pe) {
//			ErrorManager.agregaMensajeError(this, pe);
//			resultado = index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			resultado = index();
//		}
//		return resultado;
//	}
//
//	public String edit() throws Exception {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			if (proyecto == null) {
//				resultado = "proyectos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(model.getProyecto(), colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//			model.setProyecto(proyecto);
//			ElementoBs.verificarEstado(model, CU_Actores.MODIFICARACTOR7_2);
//
//			buscaCatalogos();
//
//			resultado = EDIT;
//		} catch (TESSERACTException pe) {
//			ErrorManager.agregaMensajeError(this, pe);
//			resultado = index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			resultado = index();
//		}
//
//		return resultado;
//
//	}
//
//	public String update() throws Exception {
//		String resultado = null;
//		try {
//			colaborador = SessionManager.consultarColaboradorActivo();
//			proyecto = SessionManager.consultarProyectoActivo();
//			if (proyecto == null) {
//				resultado = "proyectos";
//				return resultado;
//			}
//			if (!AccessBs.verificarPermisos(model.getProyecto(), colaborador)) {
//				resultado = Action.LOGIN;
//				return resultado;
//			}
//			model.setProyecto(proyecto);
////			Actualizacion actualizacion = new Actualizacion(new Date(),
////					comentario, model,
////					SessionManager.consultarColaboradorActivo());
//			//ActorBs.modificarActor(model, actualizacion);
//			ActorBs.modificarActor(model);
//			resultado = SUCCESS;
//			addActionMessage(getText("MSG1", new String[] { "El", "Actor",
//					"modificado" }));
//			SessionManager.set(this.getActionMessages(), "mensajesAccion");
//		} catch (TESSERACTValidacionException pve) {
//			ErrorManager.agregaMensajeError(this, pve);
//			resultado = edit();
//		} catch (TESSERACTException pe) {
//			ErrorManager.agregaMensajeError(this, pe);
//			resultado = index();
//		} catch (Exception e) {
//			ErrorManager.agregaMensajeError(this, e);
//			resultado = index();
//		}
//		return resultado;
//	}
//
//	public String verificarElementosReferencias() {
//		try {
//			elementosReferencias = new ArrayList<String>();
//			elementosReferencias = ActorBs.verificarReferencias(model);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "referencias";
//	}

	@VisitorFieldValidator
	public ActorDTO getModel() {
		return (model == null) ? model = new ActorDTO() : model;
	}

	public void setModel(ActorDTO model) {
		this.model = model;
	}
	
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public List<Actor> getListActores() {
		return listActores;
	}

	public void setListActores(List<Actor> listActores) {
		this.listActores = listActores;
	}

	public List<Cardinalidad> getListCardinalidad() {
		return listCardinalidad;
	}

	public void setListCardinalidad(List<Cardinalidad> listCardinalidad) {
		this.listCardinalidad = listCardinalidad;
	}

	public Integer getCardinalidadSeleccionada() {
		return cardinalidadSeleccionada;
	}

	public void setCardinalidadSeleccionada(Integer cardinalidadSeleccionada) {
		this.cardinalidadSeleccionada = cardinalidadSeleccionada;
	}

	public int getId() {
		return model.getId();
	}

	public void setId(int id) {
		model.setId(id);
	}

	public int getIdSel() {
		return idSel;
	}

	public void setIdSel(int idSel) {
		this.idSel = idSel;
		model = actorBs.consultarActorById(idSel);
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public List<String> getElementosReferencias() {
		return elementosReferencias;
	}

	public void setElementosReferencias(List<String> elementosReferencias) {
		this.elementosReferencias = elementosReferencias;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}
}
