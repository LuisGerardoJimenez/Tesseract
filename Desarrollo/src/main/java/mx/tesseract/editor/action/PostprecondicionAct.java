package mx.tesseract.editor.action;

import java.util.Collection;
import java.util.List;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.editor.bs.CasoUsoBs;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.bs.PostprecondicionBs;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.PostPrecondicion;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.SessionManager;

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
		@Result(name = "casoUsoBase-uso", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_CASO_USO })})
public class PostprecondicionAct extends ActionSupportTESSERACT implements ModelDriven<PostPrecondicion> {
	private static final long serialVersionUID = 1L;
	private static final String PROYECTOS = "proyectos";
	private static final String MODULOS = "modulos";
	private static final String CASO_USO = "casoUsoBase-uso";
	private PostPrecondicion model;
	private Proyecto proyecto;
	private CasoUso casoUsoBase;
	private Modulo modulo;
	private List<PostPrecondicion> listPostprecondiciones;
	private Integer idSel;
	private Integer idProyecto;
	private Integer idModulo;
	private Integer idCasoUso;

	@Autowired
	private LoginBs loginBs;
	
	@Autowired
	private CasoUsoBs casoUsoBs;
	
	@Autowired
	private ModuloBs moduloBs;
	
	@Autowired
	private PostprecondicionBs postprecondicionBs;

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
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	private String buscarModelos() {
		String resultado = null;
		idCasoUso = (Integer) SessionManager.get("idCU");
		if (idCasoUso != null) {
			proyecto = loginBs.consultarProyectoActivo();
			modulo = moduloBs.consultarModuloById(idModulo);
			casoUsoBase = casoUsoBs.consultarCasoUso(idCasoUso);
			listPostprecondiciones = postprecondicionBs.consultarPostPrecondicionesByCasoUso(casoUsoBase.getId());
			resultado = INDEX;
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} else {
			resultado = CASO_USO;
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
		model = postprecondicionBs.consultarPostPrecondicionById(idSel);
	}

	public List<PostPrecondicion> getListPostprecondiciones() {
		return listPostprecondiciones;
	}

	public void setListPostprecondiciones(List<PostPrecondicion> listPostprecondiciones) {
		this.listPostprecondiciones = listPostprecondiciones;
	}

	public CasoUso getCasoUsoBase() {
		return casoUsoBase;
	}

	public void setCasoUsoBase(CasoUso casoUso) {
		this.casoUsoBase = casoUso;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

}
