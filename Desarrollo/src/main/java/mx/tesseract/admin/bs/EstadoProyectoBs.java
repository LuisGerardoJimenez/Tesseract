package mx.tesseract.admin.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.EstadoProyectoDAO;
import mx.tesseract.admin.entidad.EstadoProyecto;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.util.TESSERACTException;

@Service("estadoProyectoBs")
public class EstadoProyectoBs {

	@Autowired
	private GenericoDAO genericoDAO;

	@Autowired
	private EstadoProyectoDAO estadoProyectoDAO;

	public List<EstadoProyecto> consultarEstados() {
		List<EstadoProyecto> estadosProyecto = genericoDAO.findAll(EstadoProyecto.class);
		if (estadosProyecto.isEmpty()) {
			throw new TESSERACTException("No se pueden consultar los estados.", "MSG29");
		}
		return estadosProyecto;
	}

	public List<EstadoProyecto> consultarEstadosNoTerminado() {
		List<EstadoProyecto> estadosProyectos = estadoProyectoDAO.findAllWithoutFinished();
		if (estadosProyectos.isEmpty()) {
			throw new TESSERACTException("No se pueden consultar los estados.", "MSG29");
		}
		return estadosProyectos;
	}

}
