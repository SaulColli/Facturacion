package com.tuempresa.facturar.modelo;

import java.math.*;
import java.time.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.openxava.annotations.*;
import org.openxava.util.*;

import lombok.*;

@Entity @Getter @Setter
@View(extendsView="super.DEFAULT",                      //Heredamos la vista de DocumentoComercial y default de la vista por defecto
members=
"diasEntregaEstimados, entregado," + 
"factura { factura }"                           //Añadimos una pestaña para la Factura en los pedidos
)
@View(name="SinClienteNiFactura",                      //Una vista con el nombre SinClienteNiFactura
members=
"anyo, numero, fecha;" +                             //Es notorio que ya no está cliente ni factura
"detalles;" +                                        //Excelente para usarse desde factura
"observaciones"
		)
//@EntityValidator(
	//	value = com.tuempresa.facturar.validadores.ValidadorEntregadoParaEstarEnFactura.class, //clase que tiene la lógica de validación
		//properties = {
	//	@PropertyValue(name="anyo"),	
	//	@PropertyValue(name="numero"),
	//	@PropertyValue(name="factura"),
	//	@PropertyValue(name="entregado")
	//	})

//@RemoveValidator(com.tuempresa.facturar.validadores.ValidadorBorrarPedido.class) Lo quitamos para poner otra validación
@Tab(baseCondition = "eliminado = false")        //@Tab. Esta anotación te permite definir la forma en que los datos tabulares (los datos mostrados en modo lista) 
                                                 //son visualizados y te permite además definir una condición
@Tab(name="Eliminado", baseCondition = "eliminado = true") // Tab con nombre
public class Pedido extends DocumentoComercial{

	@ManyToOne
	@ReferenceView("SinClienteNiPedido")               //Usado que a diferencia del CollectionView, se usa cuando se pasa muchos elementos para uno solo, es decir que no hay ninguna collection y si
	Factura factura;                                   //se nota muy bien, el @ReferenceView es el mismo que se uso para la vista simple en clientes
	
	@Depends("fecha")                              // Va a depender de la fecha
	public int getDiasEntregaEstimados() {
	    if (getFecha().getDayOfYear() < 15) {
	        return 20 - getFecha().getDayOfYear(); 
	    }
	    if (getFecha().getDayOfWeek() == DayOfWeek.SUNDAY) return 2;
	    if (getFecha().getDayOfWeek() == DayOfWeek.SATURDAY) return 3;
	    return 1;
	}
	
	@Column(columnDefinition = "INTEGER DEFAULT 1")
	int diasEntrega;
	
	@PrePersist @PreUpdate //Solo puede haber un @PrePersist @PreUpdate por entidad              //  Es suficiente con asignar el valor de diasEntregaEstimados a diasEntrega cada vez que un nuevo Pedido se crea (@PrePersist) o modifica (@PreUpdate).
	private void recalcularDiasEntrega() {
	    setDiasEntrega(getDiasEntregaEstimados());}
	
	@org.hibernate.annotations.Formula("IMPORTETOTAL * 0.10") // El cálculo usando SQL
	@Setter(AccessLevel.NONE) // El setter no se genera, sólo necesitamos el getter puesto que lo cancela así que no aparecerá
	@Stereotype("DINERO")
	BigDecimal beneficioEstimado; // Un campo, como con una propiedad persistente que devuelve dinero
	//DocumentoComercial se lea de la base de datos, el campo beneficioEstimado se rellenerá con el cálculo de @Formula que es ejecutado por la base de datos

   @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
   boolean entregado;
  
   //Vamos a quitar el metodo validar que era otra forma de validar las entregas
   //Quitamos el metodo validar()
   
 //@PrePersist @PreUpdate   //Antes de crear o modificar 
 //private void validar() throws Exception{
	// if(factura != null && !isEntregado()) { //La lógica de validación
		 //Se añade una nueva excepción de vlidción del entorno Ben Validation
		// throw new javax.validation.ValidationException(
			//	 XavaResources.getString( //Para leer el mensaje del archivo i18n
				//		 "pedido_debe_estar_entregado",
					//	 getAnyo(), getNumero())
				 //);
	 //}
	 //}
   
   //public void setFactura(Factura factura) {   // Hace lo mismo que la validación y el metodo validar pero no depende de un JPA
	 //  if(factura != null && !isEntregado()) { //La lógica de validación, dice: si factura no es nulo y no esta entregado o entregado es false entonces haz la excepción de abajo
		//   throw new javax.validation.ValidationException(
			//	   XavaResources.getString(   // Para leer el String de los archivos de la carpeta i18n
				//   "pedido_debe_estar_entregado",
				  // getAnyo(), getNumero())
				 //  );
	  // }
	  // this.factura = factura; //Lo típico que se pone en los metodos set
   
   @AssertTrue(// Antes de grabar confirma que el método devuelve true, si no lanza una excepción
		   message = "pedido_debe_estar_entregado"//Mensaje de error en caso de retornar false
		   )
   private boolean isEntregadoParaEstarEnFactura() {
	   return factura == null || isEntregado(); //Está es la lógica para hacer la validación que retorna una factura nula o esta entregado
   }
   
   @PreRemove
   private void validarPreBorrar() {
       if (factura != null) { // La lógica de validación
           throw new javax.validation.ValidationException( // Lanza una excepción runtime
               XavaResources.getString( // Para obtener un mensaje de texto
                   "no_puede_borrar_pedido_con_factura")); 
       }
   }
   
   public void setEliminado(boolean eliminado) {
	   if(eliminado) validarPreBorrar();
	   super.setEliminado(eliminado);
   }
   
   
}
