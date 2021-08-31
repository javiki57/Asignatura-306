var websocket;
function conecta() {
	 websocket = new WebSocket (wsurl);
	 websocket.onmessage = entraMensaje;
	 websocket.onopen = function () {
		 var mensaje = {
				 tipoMensaje : "ENTRADA",
				 usuario : nombre
		 };
		 websocket.send(JSON.stringify(mensaje));
		 console.log("Websocket abierto");
	 }
	 websocket.onclose = function () {
		 console.log("Websocket cerrado");
	 }
	 websocket.onerror = function () {
		 console.log("Error en Websocket")
	 }
	 
}

function entraMensaje(evento) {
	var mensaje = JSON.parse(evento.data);
	console.log(mensaje);
	escribeMensajeAreaChat(mensaje)
	
}

function escribeMensajeAreaChat(mensaje) {
	switch (mensaje.tipoMensaje) {
	case "ENTRADA":
		escribirAreaChat(">>>>>> Entra " + mensaje.usuario);
		break;
	case "SALIDA":
		escribirAreaChat("<<<<<< Sale " + mensaje.usuario);
		break;
	case "TEXTO":
		escribirAreaChat(mensaje.usuario + ": "+ mensaje.contenido);
		break;
	}
}

function escribirAreaChat(texto) {
	document.getElementById("areaChat").innerHTML += (texto +"\n"); 
}

function mandaMensaje() {
	var texto = document.getElementById("entradaTexto").value;
	var  mensaje = {
			tipoMensaje : "TEXTO",
			contenido : texto,
			usuario : nombre
	};
	escribeMensajeAreaChat(mensaje);
	websocket.send(JSON.stringify(mensaje));
	document.getElementById("entradaTexto").value = "";
	console.log("Mando " + texto+ " al servidor");
}

function teclaEnEntrada(event) {
	if (event.keyCode===13) {
		event.preventDefault(); 
		mandaMensaje();
	}
} 

window.addEventListener("load", conecta, false);
