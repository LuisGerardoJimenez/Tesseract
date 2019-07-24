package mx.tesseract.editor.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.bs.AccessBs;
import mx.tesseract.bs.ReferenciaEnum.TipoReferencia;
import mx.tesseract.editor.bs.TerminoGlosarioBs;
import mx.tesseract.editor.bs.ModuloBs;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.editor.entidad.Modulo;
//import mx.tesseract.editor.bs.ActorBs;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.GenericInterface;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/pages/editor/")
@Results({
		@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = {
				"actionName", "glosario" }),
		@Result(name = "proyectos", type = "redirectAction", params = {
				"actionName", "proyectos" }),
		@Result(name = "referencias", type = "json", params = { "root",
				"elementosReferencias" }),
})
public class GlosarioAct extends ActionSupportTESSERACT implements ModelDriven<TerminoGlosario> {

	private static final long serialVersionUID = 1L;
	private static final String PROYECTOS = "proyectos";
	private Proyecto proyecto;
	private TerminoGlosario model;
	private Colaborador colaborador;
	private List<TerminoGlosario> listGlosario;
	private Integer idSel;
	private Integer idProyecto;
	private List<String> elementosReferencias;
	
	@Autowired
	private TerminoGlosarioBs terminoGlosarioBs;
	
	@Autowired
	private ProyectoBs proyectoBs;

	@SuppressWarnings("unchecked")
	public String index(){
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			proyecto = proyectoBs.consultarProyecto(idProyecto);
			model.setProyecto(proyecto);
			listGlosario = terminoGlosarioBs.consultarGlosarioProyecto(proyecto.getId());
			resultado = INDEX;
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
	
	
	@VisitorFieldValidator
	public TerminoGlosario getModel() {
		return (model == null) ? model = new TerminoGlosario() : model;
	}

	public void setModel(TerminoGlosario model) {
		this.model = model;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public List<TerminoGlosario> getListModulos() {
		return listGlosario;
	}

	public void setListGlosario(List<TerminoGlosario> listGlosario) {
		this.listGlosario = listGlosario;
	}
	
	
}
