package com.tuempresa.facturar.modelo;


import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity @Getter @Setter
@View(extendsView="super.DEFAULT",           //Heredamos la vista de DocumentoComercial
members="pedidos { pedidos }"                //Añadimos una pestaña para los pedidos
)
@View(name="SinClienteNiPedido",            //Esta vista se llamará SinClienteNiPedidos
members=                                     //Estos miembros no tendrán a los clientes y pedidos
"anyo, numero, fecha;" +                     //Es muy excelente para ser usado para los pedidos
"detalles;" + 
"observaciones"
)
public class Factura extends DocumentoComercial {

	@OneToMany(mappedBy="factura")
	@CollectionView("SinClienteNiFactura")                 //Es usado para poder visualizar pedidos
	private Collection<Pedido> pedidos;
	
            //Con esto añadimos la colección de pedidos
}
