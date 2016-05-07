package com.mixtri.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/liveUpdate")
public class WebSocket {

	private static final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen 
	public void onOpen(Session peer) {
		peers.add(peer);
	}

	@OnClose
	public void onClose(Session peer) {
		peers.remove(peer);
	}

	@OnError
	public void onError(Throwable error) {
	}

	@OnMessage
    public void message(String message, Session client) throws IOException, EncodeException {
        for (Session peer : client.getOpenSessions()) {
            peer.getBasicRemote().sendText(message);
        }
    }

}
