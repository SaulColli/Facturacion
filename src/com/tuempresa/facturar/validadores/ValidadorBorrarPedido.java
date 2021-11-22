package com.tuempresa.facturar.validadores;

import org.openxava.util.*;
import org.openxava.validators.*;

import com.tuempresa.facturar.modelo.*;

public class ValidadorBorrarPedido 
implements IRemoveValidator{  //Tienes que implementarlo para hacer notar que se borrrá algo al borrar otra cosa
	private Pedido pedido;
	
	public void setEntity(Object entity)  //La entidad que se borrrá se inyectará
	throws Exception // con este método antes de la validación	  
	{
		this.pedido= (Pedido) entity;
}

public void validate(Messages errors) // Lógica de validación
throws Exception
{	
     if(pedido.getFactura() != null)	{              //Si pedido no es nulo, es decir que si cuenta con factura
    	 //Añadiendo mensaje a errors la validación fallará y el borrador se abortará
    	 errors.add("no_puede_borrar_pedido_con_factura");
     }
	
}

//La lógica de validación está en el método validate(). Antes de llamarlo la entidad a validar es inyectada usando setEntity(). Si se añaden mensajes al objeto errors la validación fallará y la entidad no se borrará
	
}
