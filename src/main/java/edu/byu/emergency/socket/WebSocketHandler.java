package edu.byu.emergency.socket;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class WebSocketHandler extends AbstractWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("New text message received today.");
//        super.handleTextMessage(session, message);
        session.sendMessage(message); // just echo the received message

    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        System.out.println("New binary message received.");
//        super.handleBinaryMessage(session, message);
        session.sendMessage(message); // just echo the received message
    }
}
