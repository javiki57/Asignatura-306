package es.uma.informatica.sii.ejb.practica.ejb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.informatica.sii.ejb.practica.ejb.exceptions.IngredientesIncorrectosException;
import es.uma.informatica.sii.ejb.practica.ejb.exceptions.LoteExistenteException;
import es.uma.informatica.sii.ejb.practica.ejb.exceptions.LoteNoEncontradoException;
import es.uma.informatica.sii.ejb.practica.ejb.exceptions.ProductoNoEncontradoException;
import es.uma.informatica.sii.ejb.practica.entidades.Lote;
import es.uma.informatica.sii.ejb.practica.entidades.Producto;

/**
 * Session Bean implementation class Sample
 */
@Stateless
public class LotesEJB implements GestionLotes {
	
	private static final Logger LOG = Logger.getLogger(LotesEJB.class.getCanonicalName());
	
	@PersistenceContext(name="Trazabilidad")
	private EntityManager em;

	@Override
	public void insertarLote(String producto, Lote lote) throws ProductoNoEncontradoException, LoteExistenteException, IngredientesIncorrectosException {
		Producto productoEntity = em.find(Producto.class, producto);
		if (productoEntity == null) {
			throw new ProductoNoEncontradoException();
		}
		
		Lote loteExistente = em.find(Lote.class, new Lote.LoteId(lote.getCodigo(), producto));
		if (loteExistente != null) {
			throw new LoteExistenteException();
		}
		
		if (!productoEntity.getIngredientes().equals(lote.getLoteIngredientes().keySet())) {
			throw new IngredientesIncorrectosException();
		}
		
		lote.setProducto(productoEntity);
		em.persist(lote);
	}

	@Override
	public List<Lote> obtenerLotesDeProducto(String nombre) throws ProductoNoEncontradoException {
		Producto producto = em.find(Producto.class, nombre);
		if (producto == null) {
			throw new ProductoNoEncontradoException();
		}
		producto.getLotes().size();
		return new ArrayList<>(producto.getLotes());
	}

	@Override
	public void actualizarLote(String producto, Lote lote)
			throws ProductoNoEncontradoException, LoteNoEncontradoException, IngredientesIncorrectosException {
		// TODO
		
		Producto entproduct = em.find(Producto.class, producto);
		
		//Si el producto no se encuentra, se lanza una excepción
		if(entproduct == null) {
			throw new ProductoNoEncontradoException();
		}
		
		/* Debe asegurarse de que hay una entrada para cada ingrediente 
		 * de un producto en el mapa de lotes de ingredientes (aunque el lote esté a null).
		 * Si no es así debe lanzar la excepción pertinente.
		 */
		if (!entproduct.getIngredientes().equals(lote.getLoteIngredientes().keySet())) {
			throw new IngredientesIncorrectosException();
		}
		
		Lote entlote = em.find(Lote.class, new Lote.LoteId(lote.getCodigo(), producto));
		
		//Si el lote no existe en la base de datos lanza una excepción
		if(entlote == null) {
			throw new LoteNoEncontradoException();
		}
		
		entlote.setLoteIngredientes(lote.getLoteIngredientes());
		entlote.setCantidad(lote.getCantidad());
		entlote.setFechaFabricacion(lote.getFechaFabricacion());
		em.persist(entproduct);
	}

	@Override
	public void eliminarLote(String producto, Lote lote)
			throws ProductoNoEncontradoException, LoteNoEncontradoException {
		// TODO
		Producto entproduct = em.find(Producto.class, producto);
		
		//Si el producto no se encuentra, se lanza una excepción
		if(entproduct == null) {
			throw new ProductoNoEncontradoException();
		}
		
		Lote entlote = em.find(Lote.class, new Lote.LoteId(lote.getCodigo(), producto));
		
		//Si el lote no existe en la base de datos lanza una excepción
		if(entlote == null) {
			throw new LoteNoEncontradoException();
		}
		
		Set<Lote> lista = entproduct.getLotes();
		lista.remove(lote);
		entproduct.setLotes(lista);
		em.persist(entproduct);
		
	}

	@Override
	public void eliminarTodosLotes(String producto) throws ProductoNoEncontradoException {
		// TODO
		Producto entproduct = em.find(Producto.class, producto);
		
		//Si el producto no se encuentra, se lanza una excepción
		if(entproduct == null) {
			throw new ProductoNoEncontradoException();
		}
		
		Set<Lote> lista = null;
		entproduct.setLotes(lista);
		em.persist(entproduct);
		
	}

}
