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
import mx.tesseract.editor.entidad.PostPrecondicion;
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
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_POSTPRECONDICION }),
		@Result(name = "proyectos", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_PROYECTOS }),
		@Result(name = "modulos", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_MODULOS }),
		@Result(name = "caso-uso", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_CASO_USO }),
		@Result(name = "documento", type = "stream", params = { "contentType", "${type}", "inputName",
				"fileInputStream", "bufferSize", "1024", "contentDisposition",
				"attachment;filename=\"${filename}\"" }) })
@AllowedMethods({ "entrar", "elegirColaboradores", "guardarColaboradores" })
public class PostprecondicionAct extends ActionSupportTESSERACT implements ModelDriven<PostPrecondicion> {
	private static final long serialVersionUID = 1L;
	private static final String PROYECTOS = "proyectos";
	private static final String MODULOS = "modulos";
	private static final String CASO_USO = "caso-uso";
	private PostPrecondicion model;
	private Proyecto proyecto;
	private List<PostPrecondicion> listPostprecondiciones;
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
//			colaborador = loginBs.consultarColaboradorActivo();
//			listProyectos = proyectoBs.consultarProyectosByColaborador(colaborador.getCurp());
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

	public PostPrecondicion getModel() {
		return (model == null) ? model = new PostPrecondicion() : model;
	}

	public void setModel(PostPrecondicion model) {
		this.model = model;
	}
	
	public Proyecto getProyecto() {
		return (proyecto == null) ? proyecto = loginBs.consultarProyectoActivo() : proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
//		model = proyectoBs.consultarProyecto(idSel);
//		proyecto = model;
	}

}
