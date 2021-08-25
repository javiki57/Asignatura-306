package es.uma.informatica.sii.jpa.trazabilidad;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TrazabilidadTest {
	
	private AccesoDatos ad;
	private SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Before
	public void setup() {
		ad = new AccesoDatos();
	}
	
	@After
	public void teardown() {
		ad.close();
	}

	@Test
	public void testListaProductos() {
		try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
			
			Statement stmt = connection.createStatement();
			stmt.execute("INSERT INTO PRODUCTO(CODIGO_BARRAS, NOMBRE, DESCRIPCION)"
					+ " VALUES ('12345678', 'Leche de vaca', 'Leche del Valle de los Pedroches')");
			stmt.execute("INSERT INTO PRODUCTO(CODIGO_BARRAS, NOMBRE, DESCRIPCION)"
					+ " VALUES ('87654321', 'Queso de cabra', 'Queso de leche de cabra ultrapasteurizada')");
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<Producto> lista = ad.getListaProductos();
		assertEquals("El número de productos no coincide", 2, lista.size());
		
		comprobarProducto(lista, "12345678", "Leche de vaca");
		comprobarProducto(lista, "87654321", "Queso de cabra");
	}
	
	@Test
	public void testCrearProducto() {
		Producto producto = new Producto("23456789", "Chorizo", "Picante, procedente de Italia");
		ad.crearProducto(producto);
		
		List<Producto> productos = ad.getListaProductos();
		assertEquals("La lista leída no coincide con la insertada", 1, productos.size());
		
		Producto leido = productos.get(0);
		assertEquals(producto.getNombre(), leido.getNombre());
		assertEquals(producto.getDescripcion(), leido.getDescripcion());
		assertEquals(producto.getCodigoBarras(), leido.getCodigoBarras());
		assertEquals(0, producto.getLotes().size());
	}
	
	@Test
	public void testCrearProductoConMasExistentes() {
		Producto producto = new Producto("23456789", "Chorizo", "Picante, procedente de Italia");
		ad.crearProducto(producto);
		
		List<Producto> productos = ad.getListaProductos();
		assertEquals("La lista leída no coincide con la insertada", 1, productos.size());
		
		Producto leido = productos.get(0);
		assertEquals(producto.getNombre(), leido.getNombre());
		assertEquals(producto.getDescripcion(), leido.getDescripcion());
		assertEquals(producto.getCodigoBarras(), leido.getCodigoBarras());
		assertEquals(0, producto.getLotes().size());
		
		producto = new Producto("45678912", "Arroz", "Arroz Basmati de alta calidad");
		ad.crearProducto(producto);
		
		productos = ad.getListaProductos();
		assertEquals("La lista leída no coincide con la insertada", 2, productos.size());
		
		comprobarProducto(productos, "45678912", "Arroz");
		comprobarProducto(productos, "23456789", "Chorizo");
	}
	
	@Test
	public void testEliminarProductoSinLotes() {
		Producto producto = new Producto("34567891", "Salchichas", "Elaboradas en Frankfurt");
		ad.crearProducto(producto);
		
		producto = new Producto("45678912", "Arroz", "Arroz Basmati de alta calidad");
		ad.crearProducto(producto);
		
		List<Producto> productos = ad.getListaProductos();
		assertEquals("La lista leída no coincide con la insertada", 2, productos.size());
		
		ad.eliminarProducto(producto);
		productos = ad.getListaProductos();
		assertEquals("No se ha eliminado el producto", 1, productos.size());
		
		Producto leido = productos.get(0);
		assertEquals("Salchichas", leido.getNombre());
		assertEquals("34567891", leido.getCodigoBarras());
	}
	
	@Test
	public void testAniadirLoteAProducto() {
		Producto producto = new Producto("34567891", "Salchichas", "Elaboradas en Frankfurt");
		ad.crearProducto(producto);
		
		try {
			Date fabricacion = dateformat.parse("21/03/2021");
			Date consumo = dateformat.parse("21/04/2021");
			Lote lote = new Lote(fabricacion, consumo, "LT345GH3", 34.0);
			ad.aniadirLoteAProducto(producto, lote);
			
			List<Lote> lista = ad.getListaProductos().get(0).getLotes();
			assertEquals(1, lista.size());
			Lote loteLeido = lista.get(0);
			assertEquals(lote.getCodigoLote(), loteLeido.getCodigoLote());
			assertEquals(lote.getCantidad(), loteLeido.getCantidad());
			assertEquals(lote.getFechaConsumoPreferente(), loteLeido.getFechaConsumoPreferente());
			assertEquals(lote.getFechaFabricacion(), loteLeido.getFechaFabricacion());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAniadirVariosLotesAProducto() {
		Producto producto = new Producto("34567891", "Salchichas", "Elaboradas en Frankfurt");
		ad.crearProducto(producto);
		
		try {
			Date fabricacion = dateformat.parse("21/03/2021");
			Date consumo = dateformat.parse("21/04/2021");
			
			for (int i =0; i < 10; i++) {
				Lote lote = new Lote(fabricacion, consumo, "LT345GH3"+i, 34.0+i);
				ad.aniadirLoteAProducto(producto, lote);
			}
			
			List<Lote> lista = ad.getListaProductos().get(0).getLotes();
			assertEquals(10, lista.size());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testEliminarLoteDeProducto() {
		Producto producto = new Producto("34567891", "Salchichas", "Elaboradas en Frankfurt");
		ad.crearProducto(producto);
		
		try {
			Date fabricacion = dateformat.parse("21/03/2021");
			Date consumo = dateformat.parse("21/04/2021");
			
			for (int i =0; i < 10; i++) {
				Lote lote = new Lote(fabricacion, consumo, "LT345GH3"+i, 34.0+i);
				ad.aniadirLoteAProducto(producto, lote);
			}
			
			List<Lote> lista = ad.getListaProductos().get(0).getLotes();
			assertEquals(10, lista.size());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		producto = ad.getListaProductos().get(0);
		Lote eliminar = producto.getLotes().get(0);
		ad.eliminarLoteDeProducto(producto, eliminar);
		
		producto = ad.getListaProductos().get(0);
		assertEquals(9, producto.getLotes().size());
		
		assertFalse("El lote no ha sido eliminado", producto.getLotes().stream()
			.anyMatch(lote->lote.getCodigoLote().equals(eliminar.getCodigoLote())));
		
	}
	
	@Test
	public void testEliminarProductoConLotes() {
		Producto producto = new Producto("34567891", "Salchichas", "Elaboradas en Frankfurt");
		ad.crearProducto(producto);
		
		try {
			Date fabricacion = dateformat.parse("21/03/2021");
			Date consumo = dateformat.parse("21/04/2021");
			
			for (int i =0; i < 10; i++) {
				Lote lote = new Lote(fabricacion, consumo, "LT345GH3"+i, 34.0+i);
				ad.aniadirLoteAProducto(producto, lote);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<Producto> productos = ad.getListaProductos();
		assertEquals(1, productos.size());
		
		producto = productos.get(0);
		ad.eliminarProducto(producto);
		
		productos = ad.getListaProductos();
		assertEquals(0, productos.size());
		
		
		try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
			
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Lote");
			
			int count = 0;
			while (rs.next()) {
				count++;
			}
			
			rs.close();
			stmt.close();
						
			assertEquals(0,count);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

	private void comprobarProducto(List<Producto> lista, String key, String nombre) {
		Optional<Producto> producto = lista.stream()
			.filter(p->p.getCodigoBarras().equals(key))
			.findAny();

		assertTrue("No lee el producto insertado", producto.isPresent());
		assertEquals(nombre, producto.get().getNombre());
	}

}
