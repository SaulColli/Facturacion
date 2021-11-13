package com.tuempresa.facturar.modelo;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity @Getter @Setter
public class Producto {

@Id
@Column(length=9)
int numero;

@Required
@Column(length=50)
String descripcion;

@ManyToOne
(fetch=FetchType.LAZY,
optional=true)
@DescriptionsList
Categoria categoria;

@ManyToOne(fetch=FetchType.LAZY)
@DescriptionsList
Autor autor;

@Stereotype("DINERO")
BigDecimal precio;

@Stereotype("GALERIA_IMAGENES")
String fotos;

@Stereotype("TEXTO_GRANDE")
String observaciones;



}
