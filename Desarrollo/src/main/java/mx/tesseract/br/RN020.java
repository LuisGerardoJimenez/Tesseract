package mx.tesseract.br;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.dao.EstadoProyectoDAO;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.EstadoProyecto;

@Service("rN020")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN020 {

	@Autowired
	private ColaboradorDAO colaboradorDAO;

	@Autowired
	private EstadoProyectoDAO estadoProyectoDAO;

	public Boolean isValidRN020Colaborador() {
		Boolean valido = true;
		List<Colaborador> lista = colaboradorDAO.findAllWithoutAdmin();
		if (lista.isEmpty()) {
			valido = false;
		}
		return valido;
	}

	public Boolean isValidRN020EstadoProyecto() {
		Boolean valido = true;
		List<EstadoProyecto> lista = estadoProyectoDAO.findAllWithoutFinished();
		if (lista.isEmpty()) {
			valido = false;
		}
		return valido;
	}

}
