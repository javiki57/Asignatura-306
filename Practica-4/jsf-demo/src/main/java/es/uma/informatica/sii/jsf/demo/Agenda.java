package es.uma.informatica.sii.jsf.demo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Agenda{

	private static BBDD bbdd = new BBDD();
	
	private Contacto contacto;
	private String contactoElegido;
	
	public Agenda() {
		contacto = new Contacto();
	}

	public Contacto getContacto() {
		return contacto;
	}
	
	public void aniadirContacto() {
		bbdd.getDatos().add(contacto);
		contacto = new Contacto(); 
	}
	
	public List<Contacto> getTodosContactos() {
		return bbdd.getDatos();
	}
	
	public void eliminar(Contacto c) {
		bbdd.getDatos().remove(c);
	}

	public String getContactoElegido() {
		return contactoElegido;
	}

	public void setContactoElegido(String contactoElegido) {
		this.contactoElegido = contactoElegido;
	}
	

}
