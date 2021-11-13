package com.tuempresa.facturar.util;

import java.io.*;
import java.math.*;
import java.util.*;

import org.apache.commons.logging.*;
import org.openxava.util.*;

public class PreferenciasFacturar {

	private final static String ARCHIVO_PROPIEDADES="facturar.properties";
	private static Log log = LogFactory.getLog(PreferenciasFacturar.class);   //Para obtener las anotaciones log traducción = anotar
	
	private static Properties propiedades;         //Aquí se almacenan las propiedades
	
	private static Properties getPropiedades() {
		if(propiedades== null) {
			PropertiesReader reader=                         //PropertiesReader es una clase de OpenXava que significa leer propiedades
			new PropertiesReader(
		    PreferenciasFacturar.class, ARCHIVO_PROPIEDADES);   //Lee el archivo propiedades de la clase Preferencias Facturar
			try {
				propiedades = reader.get();                    // propiedades se le asigna el valor por defecto
				}
				catch(IOException ex) {
					log.error(
					XavaResources.getString("properties_file_error",
							ARCHIVO_PROPIEDADES),
					ex);
					propiedades=new Properties();
				}
				
			}
			return propiedades;
		}
		public static BigDecimal getPorcentajeIVADefecto() {   //Unico método público
			return new BigDecimal(getPropiedades().getProperty("porcentajeIVADefecto"));
			
		}
	}
	


