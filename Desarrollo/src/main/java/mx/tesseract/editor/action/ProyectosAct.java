package mx.tesseract.editor.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.tesseract.admin.bs.ColaboradorBs;
import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.ColaboradorProyecto;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.admin.entidad.Rol;
import mx.tesseract.bs.AccessBs;
/*import mx.tesseract.bs.RolBs;
import mx.tesseract.bs.RolBs.Rol_Enum;*/
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.ErrorManager;
//import mx.tesseract.util.FileUtil;
import mx.tesseract.util.JsonUtil;
import mx.tesseract.util.TESSERACTException;
//import mx.tesseract.util.ReportUtil;
import mx.tesseract.util.SessionManager;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@ResultPath("/pages/editor/")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = {"actionName", "proyectos" }),
		@Result(name = "modulos", type = "redirectAction", params = {"actionName", "modulos" }),
		@Result(name = "colaboradores", type = "dispatcher", location = "proyectos/colaboradores.jsp"), 
		@Result(name = "documento", type = "stream", params = { 
		        "contentType", "${type}", 
		        "inputName", "fileInputStream", 
		        "bufferSize", "1024", 
		        "contentDisposition", "attachment;filename=\"${filename}\""})
		})
@AllowedMethods({ "entrar" })
public class ProyectosAct extends ActionSupportTESSERACT implements ModelDriven<Proyecto> {
	private static final long serialVersionUID = 1L;
	private static final String MODULOS = "modulos";
	private Colaborador colaborador;
	private Proyecto model;
	private Proyecto proyecto;
	private List<Proyecto> listProyectos;
	private List<Colaborador> listColaboradores;
	private String jsonColaboradoresTabla;
	private Integer idSel;
	private InputStream fileInputStream;
	private String type;
    private String filename; 
    private String extension;
    
    @Autowired
    private LoginBs loginBs;
    
    @Autowired
    private ProyectoBs proyectoBs;

    @SuppressWarnings("unchecked")
	public String index() {
		String resultado = INDEX;
		try {
			SessionManager.delete("idProyecto");
			SessionManager.delete("idModulo");
			colaborador = loginBs.consultarColaboradorActivo();
			listProyectos = proyectoBs.consultarProyectosByColaborador(colaborador.getCurp());
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}

    @SuppressWarnings("unchecked")
	public String entrar() {
    	System.out.println("Entrando al proyecto");
		String resultado = LOGIN;
		try {
			/*colaborador = SessionManager.consultarColaboradorActivo();
			if (idSel == null || colaborador == null
					|| !AccessBs.verificarPermisos(model, colaborador)) {
				resultado = LOGIN;
				return resultado;
			}*/
			//resultado = MODULOS;
			SessionManager.set(idSel, "idProyecto");
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	/*public String elegirColaboradores() throws Exception {
		Map<String, Object> session = null;
		String resultado = null;
		try {

			colaborador = SessionManager.consultarColaboradorActivo();
			if (idSel == null || colaborador == null
					|| !AccessBs.verificarPermisos(model, colaborador)) {
				resultado = LOGIN;
				return resultado;
			}
			resultado = "colaboradores";
			listColaboradores = new ArrayList<Colaborador>();
			List<Colaborador> colaboradores = ColaboradorBs.consultarPersonal();
			for (Colaborador colaboradorSel : colaboradores) {
				if (!(colaboradorSel.getCurp().equals(colaborador.getCurp()) || 
						colaboradorSel.isAdministrador()))
					listColaboradores.add(colaboradorSel);
			}

			session = ActionContext.getContext().getSession();
			session.put("idProyecto", idSel);
			proyecto = SessionManager.consultarProyectoActivo();
			prepararVista();
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
		
		return resultado;
	}

	public String guardarColaboradores() throws Exception {
		String resultado = null;
		try {
			colaborador = SessionManager.consultarColaboradorActivo();
			proyecto = SessionManager.consultarProyectoActivo();
			if (proyecto == null || colaborador == null
					|| !AccessBs.verificarPermisos(proyecto, colaborador)) {
				resultado = LOGIN;
				return resultado;
			}
			resultado = SUCCESS;
			agregarColaboradores();

			addActionMessage(getText("MSG1", new String[] { "Los", "Colaboradores",
			"registrados" }));
			ProyectoBs.modificarColaboradoresProyecto(model);
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}

	public void prepararVista() {
		List<Colaborador> colaboradoresSeleccionados = new ArrayList<Colaborador>();
		Colaborador colaboradorJSON = null;
		for (ColaboradorProyecto colaboradorProyecto : model
				.getProyecto_colaboradores()) {
			if (!colaboradorProyecto.getColaborador().getCurp()
					.equals(colaborador.getCurp())) {
				colaboradorJSON = colaboradorProyecto.getColaborador();
				colaboradorJSON.setColaborador_proyectos(null);
				colaboradoresSeleccionados.add(colaboradorProyecto
						.getColaborador());
			}

		}
		jsonColaboradoresTabla = JsonUtil
				.mapListToJSON(colaboradoresSeleccionados);
	}

	private void agregarColaboradores() throws Exception {
		Set<Colaborador> colaboradoresSeleccionados = new HashSet<Colaborador>(
				0);
		Set<ColaboradorProyecto> colaboradoresProyectoAdd = new HashSet<ColaboradorProyecto>(
				0);
		Set<ColaboradorProyecto> colaboradoresProyectoRemove = new HashSet<ColaboradorProyecto>(
				0);
		Rol rol;
		Colaborador colaborador;

		if (jsonColaboradoresTabla != null
				&& !jsonColaboradoresTabla.equals("")) {
			colaboradoresSeleccionados = JsonUtil.mapJSONToSet(
					jsonColaboradoresTabla, Colaborador.class);
		}

		for (ColaboradorProyecto colaboradorProyectoOld : model
				.getProyecto_colaboradores()) {
			if (!isContained(colaboradorProyectoOld, colaboradoresSeleccionados) && colaboradorProyectoOld.getRol().getId() != RolBs.consultarIdRol(Rol_Enum.LIDER)){
				colaboradoresProyectoRemove.add(colaboradorProyectoOld);
			}
		}

		for (Colaborador colaboradorSeleccionado : colaboradoresSeleccionados) {
			if (!isContained(colaboradorSeleccionado,
					model.getProyecto_colaboradores())) {
				rol = RolBs.findById(RolBs.consultarIdRol(Rol_Enum.ANALISTA));
				colaborador = ColaboradorBs
						.consultarPersona(colaboradorSeleccionado.getCurp());
				colaboradoresProyectoAdd.add(new ColaboradorProyecto(
						colaborador, rol, model));
			}
		}

		for (ColaboradorProyecto colaboradorToRemove : colaboradoresProyectoRemove) {
			model.getProyecto_colaboradores().remove(colaboradorToRemove);
		}

		for (ColaboradorProyecto colaboradorToAdd : colaboradoresProyectoAdd) {
			model.getProyecto_colaboradores().add(colaboradorToAdd);
		}

	}

	private boolean isContained(Colaborador colaborador,
			Set<ColaboradorProyecto> colaboradores) {
		for (ColaboradorProyecto colaboradorProyecto : colaboradores) {
			if (colaboradorProyecto.getColaborador().getCurp()
					.equals(colaborador.getCurp())) {
				return true;
			}
		}
		return false;
	}

	private boolean isContained(ColaboradorProyecto colaboradorProyecto,
			Set<Colaborador> colaboradores) {
		for (Colaborador colaborador : colaboradores) {
			if (colaborador.getCurp().equals(
					colaboradorProyecto.getColaborador().getCurp())) {
				return true;
			}
		}
		return false;
	}
	
	public String descargarDocumento() {
		//String extension = "docx";
		@SuppressWarnings("deprecation")
		String rutaSrc = request.getRealPath("/") + "/resources/JasperReport/";
		@SuppressWarnings("deprecation")
		String rutaTarget = request.getRealPath("/") + "/resources/JasperReport/";
		
		if(extension.equals("pdf")) {
			filename = this.model.getNombre().replace(' ', '_') + "." + extension;
			type = "application/pdf";
		} else if(extension.equals("docx")) {
			filename = this.model.getNombre().replace(' ', '_') + "." + extension;
			type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		} else {
			filename = this.model.getNombre().replace(' ', '_') + ".pdf";
			type = "application/pdf";
		}
				
		try {
				ReportUtil.crearReporte(extension, filename, model.getId(), rutaSrc, rutaTarget);
		        File doc = new File(rutaTarget + filename);
		        this.fileInputStream = new FileInputStream(doc);
		        FileUtil.delete(doc);
	        } catch (Exception e) {
	        	ErrorManager.agregaMensajeError(this, e);
	        	return index();
	        }
			
	    return "documento";
	}*/
	
	

	public Proyecto getModel() {
		try {
			return (model == null) ? model = loginBs.consultarProyectoActivo(): model;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
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
		System.out.println("IdProyecto: "+idSel);
		model = proyectoBs.consultarProyecto(idSel);
		System.out.println("modelo: "+model.getNombre());
		this.proyecto = model;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public List<Colaborador> getListColaboradores() {
		return listColaboradores;
	}

	public void setListColaboradores(List<Colaborador> listColaboradores) {
		this.listColaboradores = listColaboradores;
	}

	public String getJsonColaboradoresTabla() {
		return jsonColaboradoresTabla;
	}

	public void setJsonColaboradoresTabla(String jsonColaboradoresTabla) {
		this.jsonColaboradoresTabla = jsonColaboradoresTabla;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	

}
