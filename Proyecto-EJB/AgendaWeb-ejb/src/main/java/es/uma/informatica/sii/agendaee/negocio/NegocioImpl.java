/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.informatica.sii.agendaee.negocio;

import java.net.URI;
import java.util.Random;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.UriBuilder;

import es.uma.informatica.sii.agendaee.entidades.Contacto;
import es.uma.informatica.sii.agendaee.entidades.Usuario;

/**
 *
 * @author francis
 */
@Stateless
public class NegocioImpl implements Negocio {

    private static final int TAM_CADENA_VALIDACION = 20;
    private static final Logger LOGGER = Logger.getLogger(NegocioImpl.class.getCanonicalName());

    @PersistenceContext(unitName = "AgendaEE-EntidadesPU")
    private EntityManager em;

    @Override
    public void registrarUsuario(Usuario u, UriBuilder uriBuilder) throws AgendaException {
        Usuario user = em.find(Usuario.class, u.getCuenta());
        if (user != null) {
            // El usuario ya existe
            throw new CuentaRepetidaException();
        }

        u.setCadenaValidacion(generarCadenaAleatoria());
        em.persist(u);

        URI uriValidacion = uriBuilder.build(u.getCuenta(), u.getCadenaValidacion());

        LOGGER.info(uriValidacion.toString());
    }

    private String generarCadenaAleatoria() {
        Random rnd = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < TAM_CADENA_VALIDACION; i++) {
            int v = rnd.nextInt(62);
            if (v < 26) {
                sb.append((char) ('a' + v));
            } else if (v < 52) {
                sb.append((char) ('A' + v - 26));
            } else {
                sb.append((char) ('0' + v - 52));
            }
        }

        return sb.toString();

    }

    @Override
    public void validarCuenta(String cuenta, String validacion) throws AgendaException {
        Usuario u = em.find(Usuario.class, cuenta);
        if (u == null) {
            throw new CuentaInexistenteException();
        }

        if (u.getCadenaValidacion() == null) {
            // la cuenta ya est?? activa
            return;
        }

        if (!u.getCadenaValidacion().equals(validacion)) {
            throw new ValidacionIncorrectaException();
        }
        // else
        // Eliminamos la cadena de validaci??n, indicando que ya est?? activa la cuenta
        u.setCadenaValidacion(null);
    }

    /**
     * Este m??todo debe comprobar que el nombre de usuario y contrase??a que
     * recibe en el objeto u pertenecen a un usuario que existe en la BBDD y que
     * est?? validado (un usuario est?? validado cuando su cadena de validaci??n es
     * nula).
     * 
     * Puede lanzar las excepciones CuentaInexistenteException, CuentaInactivaException
     * y ContraseniaInvalidaException
     *
     * @param u
     * @return
     */
    @Override
    public void compruebaLogin(Usuario u)  throws AgendaException {
        // TODO

    }

    /**
     * Este m??todo debe comprobar que el usuario que se le pasa como par??metro
     * es un usuario existente y con contrase??a correcta (ya que estamos en la capa
     * de negocio con un Session Bean de tipo @Stateless, debemos comprobar
     * todos los accesos a la capa de nogocio). En caso negativo debe devolver 
     * la excepci??n que corresponda,
     * en caso afirmativo debe devolver una entidad usuario tal con la informaci??n
     * existe ahora mismo en la BBDD.
     * @param u
     * @return 
     */
    @Override
    public Usuario refrescarUsuario(Usuario u)  throws AgendaException {
        // TODO
        return null;

    }

    /**
     * Este m??todo debe actualizar el contacto correspondiente en la BBDD con
     * la informaci??n contenida en el objeto que se le pasa como argumento.
     * Antes de eso, debe comprobar que el usuario a quien pertenece el contacto existe y 
     * tiene una contrase??a correcta (en caso contrario debe devolver la excepci??n que
     * corresponda.
     * @param c
     * @return 
     */
    @Override
    public void modificar(Contacto c)  throws AgendaException {
        // TODO
    }

    /** Este m??todo debe insertar un contacto en la BBDD. Antes debe comprobar que
     * el usuario del contacto existe y tiene contrase??a correcta (en caso contrario
     * debe lanzar la excepci??n correspodiente).
     * @param c
     * @return 
     */
    
    @Override
    public void insertar(Contacto c)  throws AgendaException {
        // TODO
    }

    /**
     * Este m??todo debe eliminar el contacto que se le pasa como argumento.
     * Antes debe comprobar que el usuario a quien pertenece el contacto existe
     * y tiene contrase??a v??lida (en caso contrario debe lanzar la excepci??n
     * correspondiente).
     * @param c
     * @return 
     */
    @Override
    public void eliminarContacto(Contacto c)  throws AgendaException {
        // TODO
    }

}
