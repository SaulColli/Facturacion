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
public class Factura extends DocumentoComercial {

	@OneToMany(mappedBy="factura")
	@CollectionView("SinClienteNiFactura")                 //Es usado para poder visualizar pedidos
	private Collection<Pedido> pedidos;
	
            //Con esto a�adimos la colecci�n de pedidos
}
