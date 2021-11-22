package com.tuempresa.facturar.validadores;

import javax.validation.*;
import javax.ws.rs.client.*;

import org.apache.commons.logging.*;
import org.openxava.util.*;

import com.tuempresa.facturar.anotaciones.*;


public class ValidadorISBN 
implements ConstraintValidator<ISBN, Object>{
	
    private static Log log = LogFactory.getLog(ValidadorISBN.class); // Instancia 'log'
	
	 private static org.apache.commons.validator.routines.ISBNValidator
     validador = // De 'Commons Validator'
         new org.apache.commons.validator.routines.ISBNValidator();

	 private boolean buscar; //Para almacenar la opción de buscar
	 
 public void initialize(ISBN isbn) {
 this.buscar=isbn.buscar();
 }
 
 // Contiene la lógica de validación
 public boolean isValid(Object valor, ConstraintValidatorContext contexto) { 
     if (Is.empty(valor)) return true;
     if(!validador.isValid(valor.toString())) return false; // Usa 'Commons Validator'
     return buscar ? existeISBN(valor) : true; //Aquí hacemos la llamada REST y usa buscar
     //REST consiste básicamente en usar la ya existente “forma de trabajar” de internet para comunicación entre programas. Llamar a un servicio 
     //REST consiste en usar una URL web convencional para obtener un recurso de un servidor web
     
 }
 
 private boolean existeISBN(Object isbn) {
	 try {
		    // Aquí usamos JAX-RS para llamar al servicio REST
         String respuesta = ClientBuilder.newClient()
             .target("http://openlibrary.org/") // El sitio
             .path("/api/books") // La ruta del servicio
             .queryParam("jscmd", "data") // Los parámetros
             .queryParam("format", "json")
             .queryParam("bibkeys", "ISBN:" + isbn) // El ISBN es un parámetro
             .request()
             .get(String.class); // Una cadena con el JSON
         return !respuesta.equals("{}"); // ¿Está el JSON vacío? Suficiente para nuestro caso
         
	//https://openlibrary.org/api/books?jscmd=data&format=json&bibkeys=ISBN:9780932633439 que es lo el servicio web
	//Conoce más de las URL aquí: https://kinsta.com/es/base-de-conocimiento/que-es-una-url/
	// https://doc.arcgis.com/es/dashboards/create-and-share/url-parameters.htm
	 
	 } catch (Exception ex) {
		log.warn("Imposible conectar openlibrary.org "
				+ "para validar el ISBN. Validación fallida", ex);
		return false;  //Si hay errores la validación falla y el servicio no se llama
	}
	 
 }
 
 
}
