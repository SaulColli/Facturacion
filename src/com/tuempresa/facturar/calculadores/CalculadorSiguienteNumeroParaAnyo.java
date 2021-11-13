package com.tuempresa.facturar.calculadores;

import javax.persistence.*;

import org.openxava.calculators.*;
import org.openxava.jpa.*;

import lombok.*;

public class CalculadorSiguienteNumeroParaAnyo 
 implements ICalculator{                                                  // El calculador necesita implementos, el cual será ICalculator para definir que es un calculador

	@Getter @Setter                                                          
	int anyo;                                                             // Para poner en ella el año del calculo el cual se inyecta antes de calcular
	
	@Override
	public Object calculate() throws Exception {
	Query query= XPersistence.getManager().createQuery("select max(f.numero) from DocumentoComercial f where f.anyo= :anyo");                            //Para hacer el calculo ponemos una nota JPA el cual es Query, una de muchas notas
	query.setParameter("anyo", anyo);	                                  // Este nos devuelve el numero de factura maxima del indicado y luego ponemos el anyo inyectado commo parametro de consulta
	Integer ultimoNumero= (Integer) query.getSingleResult();             //Esto guarda los datos en la variable ultimoNumero
	return ultimoNumero == null ? 1 : ultimoNumero + 1;                                                  //Retorna la variable, en el que si la variable ultimoNumero es nulo esntonces tomará el valor de uno y de lo contario será el ultimo valor obtenido de ultimoNumero mas 1
	}
	
}

