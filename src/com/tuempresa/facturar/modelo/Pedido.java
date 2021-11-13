package com.tuempresa.facturar.modelo;

import java.math.*;
import java.time.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity @Getter @Setter
@View(extendsView="super.DEFAULT",                      //Heredamos la vista de DocumentoComercial y default de la vista por defecto
members=
"diasEntregaEstimados," + 
"factura { factura }"                           //Añadimos una pestaña para la Factura en los pedidos
)
@View(name="SinClienteNiFactura",                      //Una vista con el nombre SinClienteNiFactura
members=
"anyo, numero, fecha;" +                             //Es notorio que ya no está cliente ni factura
"detalles;" +                                        //Excelente para usarse desde factura
"observaciones"
		)
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
	
	@PrePersist @PreUpdate                                            //  Es suficiente con asignar el valor de diasEntregaEstimados a diasEntrega cada vez que un nuevo Pedido se crea (@PrePersist) o modifica (@PreUpdate).
	private void recalcularDiasEntrega() {
	    setDiasEntrega(getDiasEntregaEstimados());}
	
	@org.hibernate.annotations.Formula("IMPORTETOTAL * 0.10") // El cálculo usando SQL
	@Setter(AccessLevel.NONE) // El setter no se genera, sólo necesitamos el getter puesto que lo cancela así que no aparecerá
	@Stereotype("DINERO")
	BigDecimal beneficioEstimado; // Un campo, como con una propiedad persistente que devuelve dinero
	//DocumentoComercial se lea de la base de datos, el campo beneficioEstimado se rellenerá con el cálculo de @Formula que es ejecutado por la base de datos
}
