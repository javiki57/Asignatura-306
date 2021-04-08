
package es.uma.informatica.sii.agendaee.negocio;

import javax.ejb.Local;
import javax.ws.rs.core.UriBuilder;

import es.uma.informatica.sii.agendaee.entidades.Contacto;
import es.uma.informatica.sii.agendaee.entidades.Usuario;

/**
 *
 * @author francis
 */
@Local
public interface Negocio {
    public void registrarUsuario(Usuario u, UriBuilder uriBuilder) throws AgendaException;
    public void validarCuenta(String cuenta, String validacion) throws AgendaException;
    public void compruebaLogin(Usuario u) throws AgendaException;
    public Usuario refrescarUsuario(Usuario u) throws AgendaException;
    public void modificar(Contacto c) throws AgendaException;
    public void insertar(Contacto c) throws AgendaException;
    public void eliminarContacto(Contacto c) throws AgendaException;
}

