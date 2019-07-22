package mx.tesseract.admin.bs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.EstadoProyectoDAO;
import mx.tesseract.admin.entidad.EstadoProyecto;
import mx.tesseract.br.RN020;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.TESSERACTException;

@Service("estadoProyectoBs")
public class EstadoProyectoBs {

	@Autowired
	private GenericoDAO genericoDAO;

	@Autowired
	private EstadoProyectoDAO estadoProyectoDAO;

	@Autowired
	private RN020 rn020;

	public List<EstadoProyecto> consultarEstados() {
		List<EstadoProyecto> estadosProyecto = genericoDAO.findAll(EstadoProyecto.class);
		if (estadosProyecto.isEmpty()) {
			throw new TESSERACTException("No se pueden consultar los estados.", "MSG13");
		}
		return estadosProyecto;
	}

	public List<EstadoProyecto> consultarEstadosNoTerminado() {
		List<EstadoProyecto> estadosProyectos = new ArrayList<EstadoProyecto>();
		if (rn020.isValidRN020EstadoProyecto()) {
			estadosProyectos = estadoProyectoDAO.findAllWithoutFinished();
		} else {
			throw new TESSERACTException("No se pueden consultar los estados.", "MSG13");
		}
		return estadosProyectos;
	}

}
