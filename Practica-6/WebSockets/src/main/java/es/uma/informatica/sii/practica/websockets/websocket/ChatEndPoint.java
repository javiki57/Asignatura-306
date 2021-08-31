package es.uma.informatica.sii.practica.websockets.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import es.uma.informatica.sii.practica.websockets.websocket.Mensaje.TipoMensaje;

@ServerEndpoint("/chat")
public class ChatEndPoint {
	private static final Logger LOGGER = Logger.getLogger(ChatEndPoint.class.getCanonicalName());
	private static Set<Session> sesiones = Collections.synchronizedSet(new HashSet<>());
	
	private Session miSesion;
	private String nombre;
	
	@OnOpen
	public void open(Session session,
	                 EndpointConfig conf) { 
		miSesion=session;
		sesiones.add(miSesion);
		LOGGER.info("Se abre conexión de websocket");
	}

	@OnClose
	public void close(Session session, CloseReason reason) { 
		LOGGER.info("Se cierra conexión de websocket");
		Mensaje mensaje=new Mensaje();
		mensaje.setUsuario(nombre);
		mensaje.setTipoMensaje(TipoMensaje.SALIDA);
		mensaje.setContenido("La sesion "+session.getId()+" cierra por "+reason.getReasonPhrase());
		notificar(mensaje);
		sesiones.remove(session);
	}
	
	@OnError
	public void error(Session session, Throwable error) {
		LOGGER.severe(error.getMessage());
	}
	
	@OnMessage
	public void onMessage(Session session, String msg) {
		LOGGER.info("Llega mensaje del cliente");
		
		Jsonb jsonb = JsonbBuilder.create();
		Mensaje mensaje = jsonb.fromJson(msg, Mensaje.class);
		
		switch(mensaje.getTipoMensaje()) {
		
		case ENTRADA:
			nombre=mensaje.getUsuario();
			notificar(mensaje);
			break;
		case TEXTO:
			notificar(mensaje);
			break;
		case SALIDA:
			notificar(mensaje);
		}

	}
	
	private void notificar(Mensaje mensaje) {
		try {
			int count=0;
			Jsonb jsonb = JsonbBuilder.create();
			synchronized (sesiones) {
				for (Session sesion: sesiones) {
					if (sesion != miSesion) {
						sesion.getBasicRemote().sendText(jsonb.toJson(mensaje));
						count++;
					}
				}
			}
			LOGGER.info("Enviado mensaje a "+count+" clientes");
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
		}
	}


}
