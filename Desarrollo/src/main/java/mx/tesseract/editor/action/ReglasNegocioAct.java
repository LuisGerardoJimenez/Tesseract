package mx.tesseract.editor.action;

import java.util.Collection;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.editor.bs.ReglaNegocioBs;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.SessionManager;
import mx.tesseract.util.TESSERACTException;

@ResultPath("/pages/editor/")
@Results({
	@Result(name = ActionSupportTESSERACT.SUCCESS, type = "redirectAction", params = {
			"actionName", Constantes.ACTION_NAME_REGLASNEGOCIO}),
	@Result(name = "atributos", type = "json", params = { "root",
			"listAtributos" }),
	@Result(name = "entidades", type = "json", params = { "root",
			"listEntidades" }),
	@Result(name = "operadores", type = "json", params = { "root",
			"listOperadores" }),
	@Result(name = "referencias", type = "json", params = { "root",
			"elementosReferencias" }),
	@Result(name = "proyectos", type = "redirectAction", params = {
			"actionName", Constantes.ACTION_NAME_PROYECTOS }),

})

public class ReglasNegocioAct extends ActionSupportTESSERACT implements ModelDriven<ReglaNegocio> {
	
	private static final long serialVersionUID = 1L;
	private static final String PROYECTOS = "proyectos";
	private Proyecto proyecto;
	private Integer idProyecto;
	private ReglaNegocio model;
	private List<ReglaNegocio> listReglasNegocio;

	@Autowired
	private ProyectoBs proyectoBs;
	
	@Autowired
	private ReglaNegocioBs reglaNegocioBs;

	@SuppressWarnings("unchecked")
	public String index() {
		String resultado = PROYECTOS;
		try {
			idProyecto = (Integer) SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = proyectoBs.consultarProyecto(idProyecto);
				model.setProyecto(proyecto);
				listReglasNegocio = reglaNegocioBs.consultarGlosarioProyecto(proyecto.getId());
				System.out.println(listReglasNegocio.size());
				for (ReglaNegocio r : listReglasNegocio) {
					System.out.println("Nombre: "+r.getNombre());
					System.out.println("Clave: "+r.getClave());
					System.out.println("DESC: "+r.getDescripcion());
					System.out.println("Numero: "+r.getNumero());
					System.out.println("Nombre: "+r.getNombre());
					System.out.println("Estado: "+r.getEstadoElemento().getNombre());
					System.out.println("Nombre: "+r.getNombre());
					System.out.println("Proyecto: "+r.getProyecto().getNombre());
					System.out.println("TipoRN: "+r.getTiporeglanegocio().getNombre());
					System.out.println("TipoComp: "+r.getTipocomparacion());
					System.out.println("ExpReg: "+r.getExpresionRegular());
					
				}
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

	public ReglaNegocio getModel() {
		return (model == null) ? model = new ReglaNegocio() : this.model;
	}
	
	public void setModel(ReglaNegocio model) {
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

}

