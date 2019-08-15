package mx.tesseract.editor.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mx.tesseract.admin.bs.ColaboradorBs;
import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.ColaboradorProyecto;
import mx.tesseract.admin.entidad.Proyecto;
/*import mx.tesseract.bs.RolBs;
import mx.tesseract.bs.RolBs.Rol_Enum;*/
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
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
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

@ResultPath("/pages/editor/")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_PROYECTOS }),
		@Result(name = "modulos", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_MODULOS }),
		@Result(name = "colaboradores", type = "dispatcher", location = "proyectos/colaboradores.jsp"),
		@Result(name = "documento", type = "stream", params = { "contentType", "${type}", "inputName",
				"fileInputStream", "bufferSize", "1024", "contentDisposition",
				"attachment;filename=\"${filename}\"" }) })
@AllowedMethods({ "entrar", "elegirColaboradores" })
public class ProyectosAct extends ActionSupportTESSERACT implements ModelDriven<Proyecto> {
	private static final long serialVersionUID = 1L;
	private static final String MODULOS = "modulos";
	private static final String COLABORADORES = "colaboradores";
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

	@Autowired
	private ColaboradorBs colaboradorBs;

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
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public String entrar() {
		String resultado = INDEX;
		try {
			resultado = MODULOS;
			SessionManager.set(idSel, "idProyecto");
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
	public String elegirColaboradores() {
		String resultado = INDEX;
		try {
			listColaboradores = new ArrayList<Colaborador>();
			this.colaborador = loginBs.consultarColaboradorActivo();
			for (Colaborador colaborador : colaboradorBs.consultarColaboradoresCatalogo()) {
				if (!colaborador.getCurp().equals(this.colaborador.getCurp())) {
					listColaboradores.add(colaborador);
				}
			}
			SessionManager.set(idSel, "idProyecto");
			model = loginBs.consultarProyectoActivo();
			prepararVista();
			resultado = COLABORADORES;
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}

	public void prepararVista() {
		List<Colaborador> colaboradoresSeleccionados = new ArrayList<Colaborador>();
		Colaborador colaboradorJSON = null;
		for (ColaboradorProyecto colaboradorProyecto : model.getProyecto_colaboradores()) {
			if (colaboradorProyecto.getRol().getId() != Constantes.ROL_LIDER) {
				colaboradorJSON = colaboradorProyecto.getColaborador();
				colaboradorJSON.setColaborador_proyectos(null);
				colaboradoresSeleccionados.add(colaboradorProyecto.getColaborador());
			}
		}
		jsonColaboradoresTabla = JsonUtil.mapListToJSON(colaboradoresSeleccionados);
	}

	public String guardarColaboradores() {
		String resultado = null;
		try {
			model = loginBs.consultarProyectoActivo();
			proyectoBs.modificarColaboradoresProyecto(model, jsonColaboradoresTabla);
			addActionMessage(getText("MSG1", new String[] { "Los", "Colaboradores", "registrados" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
			resultado = SUCCESS;
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}
//	
//	public String descargarDocumento() {
//		//String extension = "docx";
//		@SuppressWarnings("deprecation")
//		String rutaSrc = request.getRealPath("/") + "/resources/JasperReport/";
//		@SuppressWarnings("deprecation")
//		String rutaTarget = request.getRealPath("/") + "/resources/JasperReport/";
//		
//		if(extension.equals("pdf")) {
//			filename = this.model.getNombre().replace(' ', '_') + "." + extension;
//			type = "application/pdf";
//		} else if(extension.equals("docx")) {
//			filename = this.model.getNombre().replace(' ', '_') + "." + extension;
//			type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
//		} else {
//			filename = this.model.getNombre().replace(' ', '_') + ".pdf";
//			type = "application/pdf";
//		}
//				
//		try {
//				ReportUtil.crearReporte(extension, filename, model.getId(), rutaSrc, rutaTarget);
//		        File doc = new File(rutaTarget + filename);
//		        this.fileInputStream = new FileInputStream(doc);
//		        FileUtil.delete(doc);
//	        } catch (Exception e) {
//	        	ErrorManager.agregaMensajeError(this, e);
//	        	return index();
//	        }
//			
//	    return "documento";
//	}

	public Proyecto getModel() {
		return (model == null) ? model = loginBs.consultarProyectoActivo() : model;
	}

	public void setModel(Proyecto model) {
		this.model = model;
	}
	
	public Proyecto getProyecto() {
		return (proyecto == null) ? proyecto = loginBs.consultarProyectoActivo() : proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
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
		proyecto = model;
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
