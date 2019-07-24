package mx.tesseract.editor.bs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.dao.ProyectoDAO;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN023;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.dao.TerminoGlosarioDAO;
import mx.tesseract.editor.dao.ModuloDAO;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.editor.entidad.Modulo;
//import mx.tesseract.editor.dao.CasoUsoActorDAO;
//import mx.tesseract.editor.dao.ModuloDAO;
//import mx.tesseract.editor.dao.ReferenciaParametroDAO;
//import mx.tesseract.editor.model.Actor;
//import mx.tesseract.editor.model.CasoUso;
//import mx.tesseract.editor.model.CasoUsoActor;
//import mx.tesseract.editor.model.Pantalla;
//import mx.tesseract.editor.model.Paso;
//import mx.tesseract.editor.model.PostPrecondicion;
//import mx.tesseract.editor.model.ReferenciaParametro;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.SessionManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.Validador;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("glosarioBS")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class TerminoGlosarioBs {

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
	if (listGlosario.isEmpty()) {
		throw new TESSERACTException("No se pueden consultar los términos del glosario.", "MSG13");
	}
	return listGlosario;
}

}
