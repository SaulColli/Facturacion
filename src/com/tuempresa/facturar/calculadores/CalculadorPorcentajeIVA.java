package com.tuempresa.facturar.calculadores;

import org.openxava.calculators.*;

import com.tuempresa.facturar.util.*;

public class CalculadorPorcentajeIVA 
implements ICalculator{

	@Override
	public Object calculate() throws Exception {
	
		return PreferenciasFacturar.getPorcentajeIVADefecto();
	}

}
