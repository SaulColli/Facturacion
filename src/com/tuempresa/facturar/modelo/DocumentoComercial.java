package com.tuempresa.facturar.modelo;

import java.math.*;
import java.time.*;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.openxava.annotations.*;
import org.openxava.calculators.*;
import org.openxava.jpa.*;

import com.tuempresa.facturar.calculadores.*;

import lombok.*;

@Entity @Getter @Setter
@View(members=                // Para definir la disposicion de los miembros de factura, cuando van seguidos por una coma, eso significa que los campos iran en una sola linea, pero cuando hay un punto y coma, significa nueva linea, igual le podemos poner un nombre con name="", pero no lo usaremos
"anyo, numero, fecha;" +                                                          
"datos {" + 
"cliente;"
+ "detalles;"
+ "observaciones" +
"}"
		)

//abstract public class DocumentoComercial extends Identificable{
abstract public class DocumentoComercial extends Eliminable{  //Eliminable hereda de Identificable, para entender esto; ver el código de la superclase Eliminable
	//Añadimos el modificador abstract
	
	@DefaultValueCalculator(CurrentYearCalculator.class)                      // El DefaultValueCalculator sirve para establecer valores por defecto para que el usuario no tenga que escribir los valores en donde CurrentYearCalculator.class sirve para calcular el año
	@Column(length= 4)
	int anyo;
	
	@Column(length=6)
	//@DefaultValueCalculator(value= CalculadorSiguienteNumeroParaAnyo.class,       // En este caso se le agrega la clase que creamos en el otro paquete la cual sirve para calcular el anyo siguiente, debe llevar .class para definir que es una clase
	//properties= @PropertyValue(name="anyo"))                                      // Esto sirve para inyectar el valor del año actual en el Calculator antes de llamar al Calculate
	@ReadOnly   //El usuario no puede modificar su valor
	int numero;
	
	@Required
	@DefaultValueCalculator(CurrentLocalDateCalculator.class)                  // Este sirve para la fecha y se puede encontrar mas Calculator´s en el video 3 de openxava
	LocalDate fecha;
	
	@ManyToOne
	(fetch=FetchType.LAZY, optional= false)                                      // Creamos para que el cliente se pueda relacionar con las facturas y este se pueda seleccionar, el false es para que cuando el cliente no tenga valor, se evite grabar y saldrá el mensaje "es obligatorio que el cliente tenga valor"
	 @ReferenceView("Simple")                                                                            //Para indicar que en la clase cliente se va a usar una vista simple
	Cliente cliente;
	
	@ElementCollection                                                              //Para definir que haremos una colleccion de elementos el cual el usuario podrá editar y borrar a su gusto
	@ListProperties("producto.numero, producto.descripcion,cantidad, precioPorUnidad, "
			+ "importe+[" +                                                       //Los corechetes son para una colocar algo debajo de una columna de colección, el signo de + con el ] con iporte deben estar pegados o se tendrá un error de subvista
			"documentoComercial.porcentajeIVA," +                                      //El "importe + [", el "+" sirve para hacer que se sumen las columnas
			"documentoComercial.iva," + 
			"documentoComercial.importeTotal" +
			"]")               // Para añadir las propiedades de las referencias del producto
	
	
	Collection<Detalle> detalles;
	
	@Stereotype("MEMO")                             //Memo con valores por defecto
	String observaciones;
	
	
	@Digits(integer=2, fraction=0)     //Para darle un tamaño  @Digits (una anotación de Bean Validation, el estándar de validación de Java) como una alternativa a @Column para especificar su tamaño
	@DefaultValueCalculator(CalculadorPorcentajeIVA.class)
	BigDecimal porcentajeIVA;
	
	@ReadOnly                             //Para que no se modifique
	@Stereotype("DINERO")
	@Calculation("sum(detalles.importe) * porcentajeIVA/ 100")
	BigDecimal iva;
	
	@ReadOnly
	@Stereotype("DINERO")
	@Calculation("sum(detalles.importe) + iva")
	BigDecimal importeTotal;
	
	@PrePersist //Vamos a implementar la generación del número usando métodos de retrollamada JPA. JPA permite marcar cualquier método de tu clase para ser ejecutado en cualquier momento de su ciclo de vida.
	private void calcularNumero() {
				Query query=XPersistence.getManager().createQuery(
						"select max(f.numero) from "
						+ getClass().getSimpleName() + //De está manera es valido para Factura y Pedido
						" f where f.anyo = :anyo");
				query.setParameter("anyo", anyo);
				Integer ultimoNumero = (Integer) query.getSingleResult();
				this.numero = ultimoNumero == null ? 1: ultimoNumero + 1;
	//Este código es el mismo que el de CalculadorSiguienteNumeroParaAnyo pero usando getClass().getSimpleName() 
	//en lugar de "DocumentoComercial". El método getSimpleName() devuelve el nombre de la clase sin paquete, es decir, precisamente el nombre de la entidad.

	
	}
}
