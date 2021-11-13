package com.tuempresa.facturar.modelo;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import com.tuempresa.facturar.calculadores.*;

import lombok.*;

@Embeddable @Getter @Setter       //Usamos Embeddable y no Entity porque no se puede definir una ElementCollection de entidades
public class Detalle {
int cantidad;


@ManyToOne(fetch=FetchType.LAZY, optional=true)                             // Para hacer una relaci�n a detalles del producto, en donde se hace una colleccion transferible a la clase factura que recibira la coleccion detalle de los productos que es �ste caso ser� la variable cantidad
Producto producto;

@Stereotype("DINERO")
@Depends("precioPorUnidad, cantidad") //Cuando usuario cambie el producto o cantidad, pero producto.numero se cambia por PrecioPorUnidad
public BigDecimal getImporte() {       // Propiedad calculada que recalcular� y redibujar�
	if(precioPorUnidad == null) return BigDecimal.ZERO;             // Cambiamos a producto y producto.getPrecio por PrecioPorUnidad
	return new BigDecimal(cantidad).multiply(precioPorUnidad);  //Antes estaba producto.getPrecio() pero al cambiar el precio, el importe a�n ten�a el mismo valor, as� que lo cambiamos por PrecioPorUnidad para que no seuceda 
	}
	//Con eso hacemos que ahora getImporte() usa precioPorUnidad como fuente en lugar de producto.precio.
	


	@DefaultValueCalculator(
    value=CalculadorPrecioPorUnidad.class,                           // La clase calcula el valor inicial
	properties=@PropertyValue(
			name="numeroProducto",                                        //La propiedad n�meroProducto del calculador...(abajo est� la continuaci�n del mensaje)
			from="producto.numero"                                        //... Se llena con el valor de producto.numero de la entidad por lo que tendr�n mismos valores
			))
	@Stereotype("DINERO")
	BigDecimal precioPorUnidad;           //Una propiedad persistence que nos conviene
	
	



	
	
	
	
	
	
}
