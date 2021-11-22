package com.tuempresa.facturar.validadores;

import org.openxava.util.*;
import org.openxava.validators.*;

import com.tuempresa.facturar.modelo.*;

import lombok.*;

@Getter @Setter
public class ValidadorEntregadoParaEstarEnFactura 
implements IValidator{
	
private int anyo;
private int numero;
private boolean entregado;
private Factura factura;

public void validate(Messages errors) 
	throws Exception{        //La lógica de validación
	if(factura == null) return;
	if(!entregado){
		errors.add( //Al añadir mensaje a 'errors' la validación fallará
				"pedido_debe_estar_entregado", //Un Id del archivo i18n
				anyo, numero   //Argumentos para el mensaje
				);
		
	}
	
}
	
}
