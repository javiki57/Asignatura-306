package es.uma.informatica.sii.jsf.demo;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

public class BBDD {
	
	private List<Contacto> datos;
	
	public BBDD() {
		datos = new ArrayList<>();
	}

	public List<Contacto> getDatos() {
		return datos;
	}
	
	
}
