package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.dao.PostprecondicionDAO;
import mx.tesseract.editor.entidad.PostPrecondicion;
import mx.tesseract.enums.ReferenciaEnum.TipoSeccion;

@Service("postprecondicionBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class PostprecondicionBs {

	@Autowired
	private PostprecondicionDAO postprecondicionDAO;
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	@Autowired
	private TokenBs tokenBs;
	
	public PostPrecondicion consultarPostPrecondicionById(Integer idPostPrecondicion) {
		return genericoDAO.findById(PostPrecondicion.class, idPostPrecondicion);
	}
	
	public List<PostPrecondicion> consultarPostPrecondicionesByCasoUso(Integer idCasoUso) {
		return postprecondicionDAO.findAllByCasoUso(idCasoUso);
	}

	@Transactional(rollbackFor = Exception.class)
	public void registrarPostprecondicion(PostPrecondicion model) {
		genericoDAO.save(model);
	}

	@Transactional(rollbackFor = Exception.class)
	public void modificarPostprecondicion(PostPrecondicion model) {
		genericoDAO.update(model);
	}
	
	public void preAlmacenarObjetosToken(PostPrecondicion model, Integer idModulo) {
		tokenBs.almacenarObjetosToken(
				tokenBs.convertirToken_Objeto(
							model.getRedaccion(),
							model.getCasoUso().getProyecto(), idModulo), model.getCasoUso(),
					TipoSeccion.POSTPRECONDICIONES, model);
		model.setRedaccion(tokenBs.codificarCadenaToken(
			model.getRedaccion(), model.getCasoUso().getProyecto(), idModulo));
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminarPostprecondicion(PostPrecondicion model) {
		genericoDAO.delete(model);
	}
	
}
