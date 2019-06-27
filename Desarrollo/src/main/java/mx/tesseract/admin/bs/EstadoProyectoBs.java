package mx.tesseract.admin.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.entidad.EstadoProyecto;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.TESSERACTException;

@Service("estadoProyectoBs")
public class EstadoProyectoBs {
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	public List<EstadoProyecto> consultarEstados() {
		List<EstadoProyecto> estadoProyecto = genericoDAO.findAll(EstadoProyecto.class);
		if(estadoProyecto.size() == Constantes.NUMERO_CERO) {
			throw new TESSERACTException("No se pueden consultar los estados.", "MSG13");
		}
		return estadoProyecto;
	}

}
