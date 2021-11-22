package com.tuempresa.facturar.acciones;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;
import org.openxava.model.meta.*;

import lombok.*;


public class EliminarSeleccionadoParaFacturar 
extends TabBaseAction // Para trabajar con datos tabulares (lista) por medio de getTab() as� no hay necesidad de una nueva entidad
implements IChainActionWithArgv { // Encadena con otra acci�n, indicada con getNextAction() 

	
private String nextAction = null; // Para almacenar la siguiente acci�n a ejecutar

public void execute() throws Exception {
	

    if (!getMetaModel().containsMetaProperty("eliminado")) {             //Sin meta modelo que depors� ya se almacena no se encuentra entonces
        nextAction="CRUD.deleteSelected"; // 'CRUD.deleteSelected' se ejecutar�
            // cuando esta acci�n finalice
        return;
    }
    marcarEntidadesSeleccionadasComoEliminadas(); // La l�gica para marcar las
        // filas seleccionadas como objetos borrados
}


private MetaModel getMetaModel() {
    return MetaModel.get(getTab().getModelName());
}

public String getNextAction() // Obligatorio por causa de IChainAction
    throws Exception
{
    return nextAction; // Si es nulo no se encadena con ninguna acci�n
}

public String getNextActionArgv() throws Exception {
    return "row=" + getRow(); // Argumento a enviar a la la acci�n encadenada
}

@Getter @Setter
boolean restaurar;

private void marcarEntidadesSeleccionadasComoEliminadas() throws Exception {
    Map<String, Object> valores = new HashMap<>(); // Valores a asignar a cada entidad para marcarla
    //valores.put("eliminado", true); // Pone 'eliminado' a true, es decir que encuentra la variable y le pone el valor de true
    //En lugar de darle el true fijo, le pasamos el booleano
    valores.put("eliminado", !isRestaurar()); // el valor de la propiedad 'restaurar';
    
    Map<String, Object>[] clavesSeleccionadas = getSelectedKeys(); // Obtenemos las filas seleccionadas
    if (clavesSeleccionadas != null) {     // S� las claves seleccionadas no son nulas entonces
        for (int i = 0; i < clavesSeleccionadas.length; i++) { // Iteramos sobre las filas seleccionadas
            Map<String, Object> clave = clavesSeleccionadas[i]; // Obteniendo la clave de cada entidad
            try {
                MapFacade.setValues( // Modificamos cada entidad
                    getTab().getModelName(),
                    clave,
                    valores);
            }
            catch (javax.validation.ValidationException ex) { // Si se produce una ValidationException..
                addError("no_delete_row", i, clave);
                addError("remove_error", getTab().getModelName(), ex.getMessage()); // ...mostramos el mensaje
            }
            catch (Exception ex) { // Si se lanza cualquier otra excepci�n, se a�ade
                addError("no_delete_row", i, clave); // un mensaje gen�rico
            }
        }
    }
    getTab().deselectAll(); // Despu�s de borrar deseleccionamos la filas
    resetDescriptionsCache(); // Y reiniciamos el cach� de los combos para este usuario
}}
