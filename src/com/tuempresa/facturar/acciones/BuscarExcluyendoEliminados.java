package com.tuempresa.facturar.acciones;

import java.util.*;

import javax.ejb.*;

import org.openxava.actions.*;

public class BuscarExcluyendoEliminados 
extends SearchByViewKeyAction { // La acción estándar de OpenXava para buscar, la hereda para que la búsqued sea posible
 
    private boolean esEliminable() { // Pregunta si la entidad tiene una propiedad 'eliminado', se lleva adetro de los {}
        return getView().getMetaModel()
            .containsMetaProperty("eliminado");
    }
 
    protected Map getValuesFromView() // Coge los valores visualizados desde la vista
        throws Exception // Estos valores se usan como clave al buscar
    {
        if (!esEliminable()) { // Si no es 'eliminable' usamos la lógica estándar que retorna los valores visualizados de la 
        	//vista cosa que es correcto para los que no tienen eliminable al refrescar que es en la que está este código
            return super.getValuesFromView();
        }
        Map<String, Object> valores = super.getValuesFromView();
        valores.put("eliminado", false) ; // Llenamos la propiedad 'eliminado' con false
        return valores;
    }
 
    protected Map getMemberNames() // Los miembros a leer de la entidad
        throws Exception
    {
        if (!esEliminable()) { // Si no es 'eliminable' ejecutamos la lógica estándar que lee los miembros de la entidad
            return super.getMemberNames();
        }
        Map<String, Object> miembros = super.getMemberNames();
        miembros.put("eliminado", null); // Queremos obtener la propiedad 'eliminado'
        return miembros; // aunque no esté en la vista
    }
 
    protected void setValuesToView(Map valores) // Asigna los valores desde
        throws Exception // la entidad a la vista
    {
        if (esEliminable() && // Si tiene una propiedad 'eliminado' y
            (Boolean) valores.get("eliminado")) { // vale true
            throw new ObjectNotFoundException(); // lanzamos la misma excepción que
                // OpenXava lanza cuando el objeto no se encuentra "Miembro no encontrado"
        }
        else {
            super.setValuesToView(valores); // En caso contrario usamos la lógica estándar de establecer valores para la vista
        }
    }

}
