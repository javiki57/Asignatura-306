package es.uma.informatica.sii.practica.websockets.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriBuilder;

@Named
@ViewScoped
public class SalaChat implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String salirChat() {
		nombre=null;
		return null;
	}
	
	public String getWebSocketUrl() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
        		.getExternalContext()
        		.getRequest();
        
        String thisUri = request.getRequestURL().toString();
        
        int aparicionFaces = thisUri.lastIndexOf("/faces");
        if (aparicionFaces < 0) {
        	FacesMessage fm = new FacesMessage("Error interno de URL");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return null;
        }
        
        UriBuilder uriBuilder = UriBuilder.fromUri(thisUri.substring(0, aparicionFaces)).path("chat").scheme("ws");
		return uriBuilder.build().toString();
	}

}
