package es.uma.informatica.sii.agendaee.tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.informatica.sii.agendaee.entidades.Contacto;
import es.uma.informatica.sii.agendaee.entidades.Usuario;

public class BaseDeDatos {
	
	public static void inicializar(String unidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(unidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		/* Comentar la línea de abajo si los datos se incluyen en un script de SQL */
		//datos(em); 
		
		em.close();
		emf.close();
		
	}

	public static void datos(EntityManager em) {
		Usuario usuario = new Usuario();
		usuario.setApellidos("García");
		usuario.setNombre("Antonio");
		usuario.setContrasenia("antonio");
		usuario.setCuenta("antonio");
		
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		
		Contacto contacto = new Contacto();
		contacto.setNombre("Fulanito");
		contacto.setTelefono("123456789");
		contacto.setEmail("fulanito@uma.es");
		contacto.setUsuario(usuario);
		
		em.getTransaction().begin();
		em.persist(contacto);
		em.getTransaction().commit();
		
		contacto = new Contacto();
		contacto.setNombre("Menganito");
		contacto.setTelefono("987654321");
		contacto.setEmail("menganito@uma.es");
		contacto.setUsuario(usuario);
		
		em.getTransaction().begin();
		em.persist(contacto);
		em.getTransaction().commit();
	}

}
