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

	 private boolean buscar; //Para almacenar la opci�n de buscar
	 
 public void initialize(ISBN isbn) {
 this.buscar=isbn.buscar();
 }
 
 // Contiene la l�gica de validaci�n
 public boolean isValid(Object valor, ConstraintValidatorContext contexto) { 
     if (Is.empty(valor)) return true;
     if(!validador.isValid(valor.toString())) return false; // Usa 'Commons Validator'
     return buscar ? existeISBN(valor) : true; //Aqu� hacemos la llamada REST y usa buscar
     //REST consiste b�sicamente en usar la ya existente �forma de trabajar� de internet para comunicaci�n entre programas. Llamar a un servicio 
     //REST consiste en usar una URL web convencional para obtener un recurso de un servidor web
     
 }
 
 private boolean existeISBN(Object isbn) {
	 try {
		    // Aqu� usamos JAX-RS para llamar al servicio REST
         String respuesta = ClientBuilder.newClient()
             .target("http://openlibrary.org/") // El sitio
             .path("/api/books") // La ruta del servicio
             .queryParam("jscmd", "data") // Los par�metros
             .queryParam("format", "json")
             .queryParam("bibkeys", "ISBN:" + isbn) // El ISBN es un par�metro
             .request()
             .get(String.class); // Una cadena con el JSON
         return !respuesta.equals("{}"); // �Est� el JSON vac�o? Suficiente para nuestro caso
         
	//https://openlibrary.org/api/books?jscmd=data&format=json&bibkeys=ISBN:9780932633439 que es lo el servicio web
	//Conoce m�s de las URL aqu�: https://kinsta.com/es/base-de-conocimiento/que-es-una-url/
	// https://doc.arcgis.com/es/dashboards/create-and-share/url-parameters.htm
	 
	 } catch (Exception ex) {
		log.warn("Imposible conectar openlibrary.org "
				+ "para validar el ISBN. Validaci�n fallida", ex);
		return false;  //Si hay errores la validaci�n falla y el servicio no se llama
	}
	 
 }
 
 
}
