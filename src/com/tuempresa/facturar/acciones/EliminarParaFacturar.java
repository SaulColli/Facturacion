package com.tuempresa.facturar.acciones;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;

//import com.tuempresa.facturar.modelo.*;

public class EliminarParaFacturar extends ViewBaseAction{  // ViewBaseAction tiene getView(), addMessage(), etc
	 
	public void execute() throws Exception { // La l�gica de la acci�n
	
		if(!getView().getMetaModel().containsMetaProperty("eliminado")) { //Dice: Si no tiene la propiedad eliminado, entonces:
			executeAction("CRUD.delete");
			return;
			//Para no hacer nuestra propia l�gica de borrado, hacemos que no necesitamos escribir ninguna l�gica de borrado y usamos un c�digo m�s refinado y probado.
			//Para hacer esto OpenXava permite llamar a una acci�n desde dentro de una acci�n, simplemente llama a executeAction() indicando el nombre calificado de la acci�n, es decir, el 
			//nombre del controlador y el nombre de la acci�n.  En nuestro caso para llamar 
			//a la acci�n est�ndar de OpenXava para borrar usar�amos executeAction("CRUD.delete").
		}
		
		
		//Todo ello es nada m�s aplicable para la entidad de Factur
		//	Factura factura=XPersistence.getManager().find(
		//		Factura.class, getView().getValue("oid"));  //Leemos el id de la vista de la clase Factura
		//
		//factura.setEliminado(true);  // Modificamos el estado de la entidad */
		
		//MapFacade (del paquete org.openxava.model) te permite manejar el estado de tus entidades usando mapas, 
		//esto es conveniente ya que View trabaja con mapas
		
		
		//Todo ello es para que el borrado sea aplicable en todas las entidades
		Map<String, Object> valores =
	            new HashMap<>(); // Los valores a modificar en la entidad
	        valores.put("eliminado", true); // Asignamos true a la propiedad 'eliminado'
	        MapFacade.setValues( // Modifica los valores de la entidad indicada
	            getModelName(), // Un m�todo de ViewBaseAction
	            getView().getKeyValues(), // La clave de la entidad a modificar
	            valores); // Los valores a cambiar
	        resetDescriptionsCache(); // Reinicia los caches para los combos
		
	        addMessage( // A�ade un mensaje para mostrar al usuario
	            "object_deleted", getModelName());   //El mensaje que confirma que se borr�
	        getView().clear(); // getView() devuelve el objeto xava_view
	            // clear() borra los datos en la interfaz de usuario
	        getView().setKeyEditable(true); // Crearemos una nueva entidad   
	        getView().setEditable(false); //Dejamos la vista para que no sea editable
	        // Esto inhabilita los controles de la vista, para impedir que el usuario rellene datos en la vista. 
	        //Para crear una nueva entidad el usuario tiene que pulsar el bot�n Nuevo.
	}
	
}
