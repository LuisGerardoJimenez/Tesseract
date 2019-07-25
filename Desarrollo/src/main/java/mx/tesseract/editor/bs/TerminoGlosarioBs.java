package mx.tesseract.editor.bs;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN023;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.TerminoGlosarioDTO;
import mx.tesseract.editor.dao.TerminoGlosarioDAO;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.util.TESSERACTValidacionException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("terminoGlosarioBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class TerminoGlosarioBs {
	
	private static final String CLAVE = "GLS";

	private Proyecto proyecto;

	@Autowired
	private RN006 rn006;

	@Autowired
	private RN023 rn023;

	@Autowired
	private TerminoGlosarioDAO terminoGlosarioDAO;

	@Autowired
	private GenericoDAO genericoDAO;

	@Autowired
	private ProyectoBs proyectoBs;

	public List<TerminoGlosario> consultarGlosarioProyecto(Integer idProyecto) {
		System.out.println("ya estamos en el bsGlosario");
		List<TerminoGlosario> listGlosario = terminoGlosarioDAO.consultarTerminosGlosario(idProyecto);
		return listGlosario;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void registrarTerminoGlosario(TerminoGlosarioDTO terminoGlosarioDTO, Integer idProyecto) {
		TerminoGlosario terminoGlosario = new TerminoGlosario();
		terminoGlosario.setNombre(terminoGlosarioDTO.getNombre());
		terminoGlosario.setDescripcion(terminoGlosarioDTO.getDescripcion());
		Proyecto proyecto = genericoDAO.findById(Proyecto.class, idProyecto);
		terminoGlosario.setProyecto(proyecto);
		if (rn006.isValidRN006(terminoGlosario)) {
			terminoGlosario.setClave(CLAVE);
			terminoGlosario.setNumero(terminoGlosarioDAO.siguienteNumeroTerminoGlosario(proyecto.getId()));
			//model.setEstadoElemento(ElementoBs.consultarEstadoElemento(Estado.EDICION));
		} else { 
			throw new TESSERACTValidacionException("EL nombre del término ya existe.", "MSG7",
					new String[] { "El", "Módulo", terminoGlosarioDTO.getNombre() }, "model.nombre");
		}
	}

}
