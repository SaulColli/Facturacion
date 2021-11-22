package com.tuempresa.facturar.modelo;


import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity @Getter @Setter
@View(extendsView="super.DEFAULT",           //Heredamos la vista de DocumentoComercial
members="pedidos { pedidos }"                //A�adimos una pesta�a para los pedidos
)
@View(name="SinClienteNiPedido",            //Esta vista se llamar� SinClienteNiPedidos
members=                                     //Estos miembros no tendr�n a los clientes y pedidos
"anyo, numero, fecha;" +                     //Es muy excelente para ser usado para los pedidos
"detalles;" + 
"observaciones"
)

@Tab(baseCondition = "eliminado = false")        //@Tab. Esta anotaci�n te permite definir la forma en que los datos tabulares (los datos mostrados en modo lista) 
                                                 //son visualizados y te permite adem�s definir una condici�n
@Tab(name="Eliminado", baseCondition = "eliminado = true") // Tab con nombre
public class Factura extends DocumentoComercial {

	@OneToMany(mappedBy="factura")
	@CollectionView("SinClienteNiFactura")                 //Es usado para poder visualizar pedidos
	private Collection<Pedido> pedidos;
	
            //Con esto a�adimos la colecci�n de pedidos
	
	//@Hidden // No se mostrar� por defecto en las vistas y los tabs
	//@Column(columnDefinition="BOOLEAN DEFAULT FALSE") // Para llenar con falses en lugar de con nulos 
	//USAMOS @Column(columnDefinition=) para llenar la columna con falses en lugar de con nulos.
	//boolean eliminado;
//Se quita porque lo eliminamos par Factura y todas las demas entidades
}
