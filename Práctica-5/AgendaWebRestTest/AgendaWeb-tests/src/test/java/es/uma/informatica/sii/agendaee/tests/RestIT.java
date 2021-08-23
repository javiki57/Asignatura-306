package es.uma.informatica.sii.agendaee.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.uma.informatica.sii.agendaee.entidades.Contacto;
import es.uma.informatica.sii.agendaee.entidades.Usuario;


public class RestIT {

	private static final String UNIDAD_PERSISTENCIA = "AgendaPU";
	private static final String CONTACTOS = "rs/agenda/contactos";
	private static final String CONTACTO = "rs/agenda/contacto";
	private static final String CONTEXT_ROOT = "http://localhost:8080/AgendaWeb-war";

	private static final String CUENTA_PRUEBA = "antonio";
	private static final String CONTRASENIA = "antonio";

	private static WebTarget uri;

	@BeforeClass
	public static void setupClass() {
		uri = ClientBuilder.newClient().target(CONTEXT_ROOT);
	}

	@Before
	public void setup() {
		BaseDeDatos.inicializar(UNIDAD_PERSISTENCIA);
	}

	@Test
	public void testGetNormal() {
		Response r = uri.path(CONTACTOS).request()
				.accept(MediaType.APPLICATION_JSON)
				.header("User-auth", CUENTA_PRUEBA + ":" + CONTRASENIA)
				.buildGet().invoke();

		assertEquals(Response.Status.OK.getStatusCode(), r.getStatusInfo().getStatusCode());
		Usuario u = r.readEntity(Usuario.class);
		assertNotNull(u);
		assertEquals(2, u.getContactos().size());
	}
	
	@Test
	public void testGetUnContacto() {
		Contacto contacto = getContactoExistente(1L);
		assertEquals("Fulanito", contacto.getNombre());
		assertEquals("123456789", contacto.getTelefono());
		assertEquals("fulanito@uma.es", contacto.getEmail());
	}
	
	@Test
	public void testGetUsuarioIncorrecto() {
		Response r = uri.path(CONTACTOS).request()
				.accept(MediaType.APPLICATION_JSON)
				.header("User-auth", "fake" + ":" + "fake")
				.buildGet().invoke();

		assertNotEquals(Response.Status.OK.getStatusCode(), r.getStatusInfo().getStatusCode());
	}
	
	@Test
	public void testAniadirContacto() {
		Contacto contacto = new Contacto();
		contacto.setNombre("Mike");
		contacto.setEmail("mike@uma.es");
		contacto.setTelefono("234567891");
		
		Response r = uri.path(CONTACTOS).request(MediaType.APPLICATION_JSON)
				.header("User-auth", CUENTA_PRUEBA + ":" + CONTRASENIA)
				.buildPost(Entity.json(contacto)).invoke();
		
		assertEquals(Response.Status.CREATED.getStatusCode(), r.getStatusInfo().getStatusCode());
		String[] cad = r.getLocation().getPath().split("/");
		contacto.setId(Long.parseLong(cad[cad.length - 1]));
		
		Contacto nuevo = getContactoExistente(contacto.getId());
		assertEquals(contacto.getNombre(), nuevo.getNombre());
		assertEquals(contacto.getEmail(), nuevo.getEmail());
		assertEquals(contacto.getTelefono(), nuevo.getTelefono());
	}
	
	@Test
	public void testModificarContacto() {
		Contacto contacto = new Contacto();
		contacto.setNombre("Mike");
		contacto.setEmail("mike@uma.es");
		contacto.setTelefono("234567891");
		contacto.setId(1L);
		
		Response r = uri.path(CONTACTO).path(contacto.getId() + "")
				.request(MediaType.APPLICATION_JSON)
				.header("User-auth", CUENTA_PRUEBA + ":" + CONTRASENIA)
				.buildPut(Entity.json(contacto)).invoke();
		
		assertEquals(Response.Status.OK.getStatusCode(), r.getStatusInfo().getStatusCode());
		
		Contacto nuevo = getContactoExistente(contacto.getId());
		assertEquals(contacto.getNombre(), nuevo.getNombre());
		assertEquals(contacto.getEmail(), nuevo.getEmail());
		assertEquals(contacto.getTelefono(), nuevo.getTelefono());
		
	}

	@Test
	public void testEliminarContacto() {
		Response r = uri.path(CONTACTO).path(1L + "")
				.request(MediaType.APPLICATION_JSON)
				.header("User-auth", CUENTA_PRUEBA + ":" + CONTRASENIA)
				.buildDelete().invoke();
		
		r = uri.path(CONTACTOS).request()
				.accept(MediaType.APPLICATION_JSON)
				.header("User-auth", CUENTA_PRUEBA + ":" + CONTRASENIA)
				.buildGet().invoke();

		assertEquals(Response.Status.OK.getStatusCode(), r.getStatusInfo().getStatusCode());
		Usuario u = r.readEntity(Usuario.class);
		assertNotNull(u);
		assertEquals(1, u.getContactos().size());
		assertEquals("Menganito", u.getContactos().get(0).getNombre());
		
	}

	
	private Contacto getContactoExistente(Long id) {
		Response r = uri.path(CONTACTO).path(""+id).request()
				.accept(MediaType.APPLICATION_JSON)
				.header("User-auth", CUENTA_PRUEBA + ":" + CONTRASENIA)
				.buildGet().invoke();

		assertEquals(Response.Status.OK.getStatusCode(), r.getStatusInfo().getStatusCode());
		return r.readEntity(Contacto.class);
	}

}