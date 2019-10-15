package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.dao.PostprecondicionDAO;
import mx.tesseract.editor.entidad.PostPrecondicion;

@Service("postprecondicionBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class PostprecondicionBs {

	@Autowired
	private PostprecondicionDAO postprecondicionDAO;
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	public PostPrecondicion consultarPostPrecondicionById(Integer idPostPrecondicion) {
		return genericoDAO.findById(PostPrecondicion.class, idPostPrecondicion);
	}
	
	public List<PostPrecondicion> consultarPostPrecondicionesByCasoUso(Integer idCasoUso) {
		List<PostPrecondicion> PostPrecondiciones = postprecondicionDAO.findAllByCasoUso(idCasoUso);
		return PostPrecondiciones;
	}
	
}
