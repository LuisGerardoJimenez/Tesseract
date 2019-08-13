package mx.tesseract.editor.bs;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN023;
import mx.tesseract.bs.ReferenciaEnum.Clave;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.TerminoGlosarioDTO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.util.TESSERACTValidacionException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service("terminoGlosarioBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class TerminoGlosarioBs {

	private Proyecto proyecto;

	@Autowired
	private RN006 rn006;

	@Autowired
	private RN023 rn023;

	@Autowired
	private ElementoDAO elementoDAO;

	@Autowired
	private GenericoDAO genericoDAO;

	@Autowired
	private ProyectoBs proyectoBs;

	public List<TerminoGlosario> consultarGlosarioProyecto(Integer idProyecto) {
		System.out.println("ya estamos en el bsGlosario");
		List<TerminoGlosario> listGlosario = elementoDAO.findAllByIdProyectoAndClave(idProyecto, Clave.GLS);
		return listGlosario;
	}

//	@Transactional(rollbackFor = Exception.class)
	public void registrarTerminoGlosario(TerminoGlosarioDTO terminoGlosarioDTO) {
		if (rn006.isValidRN006(terminoGlosarioDTO)) {
			TerminoGlosario terminoGlosario = new TerminoGlosario();
			Proyecto proyecto = genericoDAO.findById(Proyecto.class, terminoGlosarioDTO.getIdProyecto());
			String numero = elementoDAO.siguienteNumero(proyecto.getId(), Clave.GLS);
			terminoGlosario.setClave(Clave.GLS.toString());
			terminoGlosario.setNumero(numero);
			terminoGlosario.setNombre(terminoGlosarioDTO.getNombre());
			terminoGlosario.setDescripcion(terminoGlosarioDTO.getDescripcion());
			terminoGlosario.setProyecto(proyecto);
			System.out.println("-------------------------------->");
			System.out.println("Nombre: " + terminoGlosario.getNombre());
			System.out.println("Clave: " + terminoGlosario.getClave());
			System.out.println("Numero: " + terminoGlosario.getNumero());
			System.out.println("Descripcion: " + terminoGlosario.getDescripcion());
			System.out.println("NombreProyecto: " + terminoGlosario.getProyecto().getNombre());
			System.out.println("-------------------------------->");
			// model.setEstadoElemento(ElementoBs.consultarEstadoElemento(Estado.EDICION));
		} else {
			throw new TESSERACTValidacionException("EL nombre del término ya existe.", "MSG7",
					new String[] { "El", "Módulo", terminoGlosarioDTO.getNombre() }, "model.nombre");
		}
	}

}
