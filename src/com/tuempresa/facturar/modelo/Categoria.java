package com.tuempresa.facturar.modelo;

import javax.persistence.*;

import lombok.*;

@Entity @Getter @Setter
public class Categoria extends Identificable{
	//Heredamos de la clase Identificable
	
	@Column(length=50)
	String descripcion;
}
