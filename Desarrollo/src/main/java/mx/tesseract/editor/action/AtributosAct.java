package mx.tesseract.editor.action;

import java.util.Collection;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.editor.bs.AtributoBs;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.SessionManager;
import mx.tesseract.util.TESSERACTException;

@ResultPath("/pages/editor/")
@Results({
	@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = {
			"actionName", Constantes.ACTION_NAME_ATRIBUTOS }),
	@Result(name = "entidades", type = "redirectAction", params = {
			"actionName", Constantes.ACTION_NAME_ENTIDADES })		
})
public class AtributosAct extends ActionSupportTESSERACT implements ModelDriven<Atributo> {
	
	private static final long serialVersionUID = 1L;
	private static final String ENTIDADES = "entidades";
	private static final String PROYECTOS = "proyectos";
	private Proyecto proyecto;
	private Atributo model;
	
	private List<Atributo> listAtributos;
	private Integer idSel;
	private Integer idProyecto;
	private Integer idEntidad;
	
	@Autowired
	private LoginBs loginBs;
	
	@Autowired
	private AtributoBs atributoBs;
	
	@SuppressWarnings("unchecked")
	public String index() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				idEntidad = (Integer) SessionManager.get("idEntidad");
				if (idEntidad != null) {
					proyecto = loginBs.consultarProyectoActivo();
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
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	
	public Atributo getModel() {
		return model;
	}
	
	public void setModel(Atributo model) {
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
	}

	public Proyecto getProyecto() {
		return proyecto;
	}


	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
}
