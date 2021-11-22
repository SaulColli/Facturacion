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
	throws Exception{        //La l�gica de validaci�n
	if(factura == null) return;
	if(!entregado){
		errors.add( //Al a�adir mensaje a 'errors' la validaci�n fallar�
				"pedido_debe_estar_entregado", //Un Id del archivo i18n
				anyo, numero   //Argumentos para el mensaje
				);
		
	}
	
}
	
}
