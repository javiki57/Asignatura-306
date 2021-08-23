package es.uma.informatica.sii.agendaee.rest;

import java.net.URI;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import es.uma.informatica.sii.agendaee.entidades.Contacto;
import es.uma.informatica.sii.agendaee.entidades.Usuario;
import es.uma.informatica.sii.agendaee.negocio.AgendaException;
import es.uma.informatica.sii.agendaee.negocio.ContactoInexistenteException;
import es.uma.informatica.sii.agendaee.negocio.Negocio;

@Path("/agenda")
public class ServicioREST {
	@EJB
	private Negocio negocio;
	@Context
	private UriInfo uriInfo;
	
	@HeaderParam("User-auth")
	private String autorizacion;
	
	@Path("/contactos")
	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getContactos() {
		Usuario usuario = getUsuario();
		if (usuario == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		try {	
			usuario = negocio.refrescarUsuario(usuario);
			return Response.ok(usuario).build();
		} catch (AgendaException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	
	@Path("/contactos")
	@POST
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response aniadirContacto(Contacto contacto) {
		// TODO
		return null;
	}
	
	private Usuario getUsuario() {
		if (autorizacion == null) {
			return null;
		}
		
		String [] partesAutorizacion = autorizacion.split(":");
		if (partesAutorizacion.length != 2) {
			return null;
		}
		
		Usuario usuario = new Usuario();
		usuario.setCuenta(partesAutorizacion[0]);
		usuario.setContrasenia(partesAutorizacion[1]);
		
		return usuario;
	}

}
