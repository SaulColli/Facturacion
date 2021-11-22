package com.tuempresa.facturar.validadores;

import org.openxava.util.*;
import org.openxava.validators.*;

import com.tuempresa.facturar.modelo.*;

public class ValidadorBorrarPedido 
implements IRemoveValidator{  //Tienes que implementarlo para hacer notar que se borrr� algo al borrar otra cosa
	private Pedido pedido;
	
	public void setEntity(Object entity)  //La entidad que se borrr� se inyectar�
	throws Exception // con este m�todo antes de la validaci�n	  
	{
		this.pedido= (Pedido) entity;
}

public void validate(Messages errors) // L�gica de validaci�n
throws Exception
{	
     if(pedido.getFactura() != null)	{              //Si pedido no es nulo, es decir que si cuenta con factura
    	 //A�adiendo mensaje a errors la validaci�n fallar� y el borrador se abortar�
    	 errors.add("no_puede_borrar_pedido_con_factura");
     }
	
}

//La l�gica de validaci�n est� en el m�todo validate(). Antes de llamarlo la entidad a validar es inyectada usando setEntity(). Si se a�aden mensajes al objeto errors la validaci�n fallar� y la entidad no se borrar�
	
}
