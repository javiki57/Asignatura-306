package es.uma.informatica.sii.practica.websockets.websocket;

public class Mensaje {
	public static enum TipoMensaje {ENTRADA, SALIDA, TEXTO};
	
	private TipoMensaje tipoMensaje;
	private String usuario;
	private String contenido;
	
	public TipoMensaje getTipoMensaje() {
		return tipoMensaje;
	}
	public void setTipoMensaje(TipoMensaje tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
