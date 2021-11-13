package com.tuempresa.facturar.modelo;

import javax.persistence.*;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

import lombok.*;

@MappedSuperclass @Getter @Setter     // Marcada como una superclase mapeada en vez de una entidad
public class Identificable {

	@Id @Hidden
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@Column(length=32)
	String oid;
}
