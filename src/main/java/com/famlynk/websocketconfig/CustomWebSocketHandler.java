package com.famlynk.websocketconfig;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.famlynk.model.Notify;
import com.famlynk.repository.INotifyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Websocket Handler class for push notification
 * 
 * @Date 14-07-2023
 * 
 */
public class CustomWebSocketHandler extends TextWebSocketHandler {

	@Autowired
	private INotifyRepository notifyRepository;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// Handle received message
		String receivedMessage = message.getPayload();
//	      converting json stringify 
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(receivedMessage);
		String fromUserID = jsonNode.get("fromUserID").asText();
		String receiverUniqueId = jsonNode.get("receiverUniqueId").asText();
		String relation = jsonNode.get("relation").asText();
//	      getting notification details
		List<Notify> notification = notifyRepository.findByToUniqueUserID(receiverUniqueId);
		String jsonString = objectMapper.writeValueAsString(notification);
		// Send a response back
		String response = receivedMessage;
		session.sendMessage(new TextMessage(jsonString));
	}
}