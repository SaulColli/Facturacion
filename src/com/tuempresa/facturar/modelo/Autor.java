package com.tuempresa.facturar.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity @Getter @Setter
public class Autor extends Identificable{
    //Heredamos de la clase Identificable el Id son uuid
 
    @Column(length=50) 
    @Required
    String nombre;
    
    @OneToMany(mappedBy="autor")                                                                    //Para que el usuario escoja un autor y vea todos sus productos
    @ListProperties("numero, descripcion, precio")                                                  //Aquí se define la lista de propiedades que podrá visualizar
    Collection <Producto> productos;                                                                //
}
