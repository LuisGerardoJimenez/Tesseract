package mx.tesseract.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@Service("reportUtil")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ReportUtil {
	
	@Autowired
    private DataSource dataSource;
	
	public void crearReporte(String formato, String nombre, Integer idProyecto, String rutaJasper, String rutaTarget) throws JRException, SQLException {
		String extension = "";
		
		@SuppressWarnings("deprecation")
		JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(rutaTarget + "prisma.jasper");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("idProyecto", idProyecto);
		param.put("p_contextPath", rutaTarget);
		param.put("SUBREPORT_DIR", rutaTarget + "subreports/");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, param, DataSourceUtils.getConnection(dataSource));
		
		JRExporter exporter = null;
		
		if(formato.equals("pdf")) {
			extension = "pdf";
			exporter = new JRPdfExporter();
		} else if(formato.equals("docx")) {
			extension = "docx";
			exporter = new JRDocxExporter();
		}
		
		
		//Configuración genérica (no importa del formato)
		exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint); 
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(rutaTarget + nombre));
		exporter.exportReport();

	}
	
}
