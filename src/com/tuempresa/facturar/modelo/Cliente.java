package com.tuempresa.facturar.modelo;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity @Setter @Getter
@View(name= "Simple", members="numero, nombre")                                                      // name, en donde indica que esa vista solo se va a usar cuando se espec�fique la vista simple la cual tambi�n se indicar� en la clase factura con la nota @ReferenceView
public class Cliente {

@Id
@Column(length=6)
int numero;

@Required
@Column(length=50)
String nombre;	

@Embedded @NoFrame
Direccion direccion;
}
