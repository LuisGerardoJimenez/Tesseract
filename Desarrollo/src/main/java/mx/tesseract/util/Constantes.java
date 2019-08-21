package mx.tesseract.util;

import java.util.ArrayList;
import java.util.List;

public final class Constantes {
	
	//NUMEROS CONSTANTES ENTEROS
	public static final Integer NUMERO_CERO = 0;
	public static final Integer NUMERO_UNO_NEGATIVO = -1;
	public static final Integer NUMERO_UNO = 1;
	public static final Integer NUMERO_DOS = 2;
	public static final Integer NUMERO_TRES = 3;
	public static final Integer NUMERO_CUATRO = 4;
	public static final Integer NUMERO_CINCO = 5;
	public static final Integer NUMERO_SEIS = 6;
	public static final Integer NUMERO_SIETE = 7;
	public static final Integer NUMERO_OCHO = 8;
	public static final Integer NUMERO_NUEVE = 9;
	public static final Integer NUMERO_DIEZ = 10;
	public static final Integer NUMERO_ONCE = 11;
	public static final Integer NUMERO_DOCE = 12;
	public static final Integer NUMERO_DIECISIETE = 17;
	public static final Integer NUMERO_DIECIOCHO = 18;
	public static final Integer NUMERO_VEINTE = 20;
	public static final Integer NUMERO_TREINTA = 30;
	public static final Integer NUMERO_CUARENTA_Y_CINCO = 45;
	public static final Integer NUMERO_CINCUENTA = 50;
	public static final Integer NUMERO_CIEN = 100;
	public static final Integer NUMERO_DOSCIENTOS = 100;
	public static final Integer NUMERO_NOVECIENTOS_NOVENTA_Y_NUEVE = 999;
	public static final Integer NUMERO_MIL = 1000;
	
	//NUMEROS CONSTATNES DOUBLES
	public static final Double NUMERO_CERO_DOUBLE= 0.0;
	public static final Double NUMERO_DIEZ_DOUBLE = 10.0;
	
	//NUMEROS CONSTANTES DOUBLES
	public static final Double NUMERO_MIL_MILLONES = 1000000000.00;
	
	//ROLES
	public static final Integer ROL_LIDER = 1;
	public static final Integer ROL_ANALISTA = 2;
	
	//ESTADOS ELEMENTO
	public final static Integer ESTADO_ELEMENTO_EDICION = Constantes.NUMERO_UNO;
	public final static Integer ESTADO_ELEMENTO_REVISION = Constantes.NUMERO_DOS;
	public final static Integer ESTADO_ELEMENTO_PENDIENTECORRECCION = Constantes.NUMERO_TRES;
	public final static Integer ESTADO_ELEMENTO_PORLIBERAR = Constantes.NUMERO_CUATRO;
	public final static Integer ESTADO_ELEMENTO_LIBERADO = Constantes.NUMERO_CINCO;
	public final static Integer ESTADO_ELEMENTO_PRECONFIGURADO = Constantes.NUMERO_SEIS;
	public final static Integer ESTADO_ELEMENTO_CONFIGURADO = Constantes.NUMERO_SIETE;
	
	//ERRORES
	public static final String NUMERO_UNO_NEGATIVO_STRING = "-1";
	public static final String NUMERO_DIECIOCHO_STRING = "18";
	public static final String NUMERO_CINCUENTA_STRING = "50";
	public static final String NUMERO_CUARENTA_Y_CINCO_STRING = "45";
	
	//ERRORES MYSQL
	public static final Integer MYSQL_ERROR_1451 = 1451;
	public static final Integer MYSQL_ERROR_1062 = 1062;
	
	//URLS GESTIONES
	
	public static final String ACTION_NAME_PROYECTOS_ADMIN = "proyectos-admin";
	public static final String ACTION_NAME_PERSONAL = "personal";
	public static final String ACTION_NAME_PROYECTOS = "proyectos";
	public static final String ACTION_NAME_MODULOS = "modulos";
	public static final String ACTION_NAME_GLOSARIO = "glosario";
	public static final String ACTION_NAME_ENTIDADES = "entidades";
	public static final String ACTION_NAME_ELEMENTOS_REFERENCIAS = "elementosReferencias";
	public static final String ACTION_NAME_ACTORES = "actores";
	
	//DICCIONARIO
	public static final String DICCIONARIO = "0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
	
	//EXPRESIONES REGULARES
	public static final String REGEX_CAMPO_ALFANUMERICO = "^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\\s]+$";
	public static final String REGEX_CAMPO_ALFANUMERICO_MAYUSCULAS_SIN_ESPACIOS = "^[A-Z0-9ÑÁÉÍÓÚ]+$";
	public static final String REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES = "^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\\s\\-\\,\\.\\:]+$";
	public static final String REGEX_CAMPO_ALFABETICO = "^[a-zA-zñÑáéíóúÁÉÍÓÚ\\s]+$";
	public static final String REGEX_CAMPO_ALFABETICO_SIN_ESPACIOS = "^[a-zA-zñÑáéíóúÁÉÍÓÚ]+$";
	public static final String REGEX_CAMPO_ALFABETICO_CARACTERES_ESPECIALES = "^[a-zA-zñÑáéíóúÁÉÍÓÚ\\s\\-\\,\\.]+$";
	public static final String REGEX_CAMPO_NUMERICO_ENTERO = "^[0-9]+$";
	public static final String REGEX_PRESUPUESTO = "^[0-9]{1,9}\\.[0-9]{1,2}$";
	public static final String REGEX_CURP = "^([A-Z][AEIOUX][A-Z]{2}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d])(\\d)$";
	public static final String REGEX_CONTRASENIA = "^[a-zA-Z0-9\\(\\)\\-\\_\\!\\?\\&\\@\\%\\#]{8,20}$";
	public static final String REGEX_COMBO_BOX = "^[0-9]+$";
	public static final String REGEX_COMBO_BOX_STRING = "^[0-9A-Z]+$";

}
