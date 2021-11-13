package com.tuempresa.facturar.calculadores;

import static org.openxava.jpa.XPersistence.getManager;

import org.openxava.calculators.*;

import com.tuempresa.facturar.modelo.*;

import lombok.*;

public class CalculadorPrecioPorUnidad
  implements ICalculator{

	@Getter @Setter
	int numeroProducto;
	
	@Override
	public Object calculate() throws Exception{
		
		Producto producto = getManager()
				.find(Producto.class, numeroProducto);   //Busca el producto  y numeroProducto
		return producto.getPrecio();
		
	}
	
	
}
