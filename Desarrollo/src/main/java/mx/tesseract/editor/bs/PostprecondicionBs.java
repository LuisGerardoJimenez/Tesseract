package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.PostPrecondicionDTO;
import mx.tesseract.editor.dao.PostprecondicionDAO;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.PostPrecondicion;
import mx.tesseract.enums.ReferenciaEnum.TipoSeccion;
import mx.tesseract.util.Constantes;

@Service("postprecondicionBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class PostprecondicionBs {

	@Autowired
	private PostprecondicionDAO postprecondicionDAO;
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	@Autowired
	private TokenBs tokenBs;
	
	public PostPrecondicionDTO consultarPostPrecondicionById(Integer idPostPrecondicion) {
		PostPrecondicionDTO model = new PostPrecondicionDTO();
		PostPrecondicion entidad = genericoDAO.findById(PostPrecondicion.class, idPostPrecondicion);
		model.setId(entidad.getId());
		model.setIdCasoUso(entidad.getCasoUso().getId());
		model.setPrecondicion(entidad.isPrecondicion() ? Constantes.SELECT_PRECONDICION : Constantes.SELECT_POSTCONDICION);
		model.setRedaccion(entidad.getRedaccion());
		return model;
	}
	
	public List<PostPrecondicion> consultarPostPrecondicionesByCasoUso(Integer idCasoUso) {
		return postprecondicionDAO.findAllByCasoUso(idCasoUso);
	}

	@Transactional(rollbackFor = Exception.class)
	public void registrarPostprecondicion(PostPrecondicionDTO model) {
		PostPrecondicion entidad = new PostPrecondicion();
		CasoUso casoUso = genericoDAO.findById(CasoUso.class, model.getIdCasoUso());
		entidad.setCasoUso(casoUso);
		entidad.setPrecondicion(model.getPrecondicion().equals(Constantes.SELECT_PRECONDICION));
		entidad.setRedaccion(model.getRedaccion());
		genericoDAO.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void modificarPostprecondicion(PostPrecondicionDTO model) {
		PostPrecondicion entidad = genericoDAO.findById(PostPrecondicion.class, model.getId());
		CasoUso casoUso = genericoDAO.findById(CasoUso.class, model.getIdCasoUso());
		entidad.setCasoUso(casoUso);
		entidad.setPrecondicion(model.getPrecondicion().equals(Constantes.SELECT_PRECONDICION));
		entidad.setRedaccion(model.getRedaccion());
		genericoDAO.update(entidad);
	}
	
	public void preAlmacenarObjetosToken(PostPrecondicionDTO model, Integer idModulo) {
		CasoUso casoUso = genericoDAO.findById(CasoUso.class, model.getIdCasoUso());
		model.setRedaccion(tokenBs.codificarCadenaToken(
			model.getRedaccion(), casoUso.getProyecto(), idModulo));
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminarPostprecondicion(PostPrecondicionDTO model) {
		PostPrecondicion entidad = genericoDAO.findById(PostPrecondicion.class, model.getId());
		genericoDAO.delete(entidad);
	}
	
}
