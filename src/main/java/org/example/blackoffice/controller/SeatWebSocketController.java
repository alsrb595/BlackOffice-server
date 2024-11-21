package org.example.blackoffice.controller;

import org.example.blackoffice.dto.SeatDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SeatWebSocketController {
    private final SimpMessagingTemplate messagingTemplate; // Spring에서 제공하는 메시징 템플릿 클래스로 주로 WebSocket을 통해 클라이언트에게 메시지를 전송하는 데 사용됩니다. 이 클래스는 STOMP 기반으로 동작, 서버에서 클라이언트로 메시지를 발행하거나 브로드케스트하는 데에 유용
    // SimpMessagingTemplate는 클라이언트가 구독하고 있는 경로로 메시지를 보내거나 특정 클라이언트에게 직접적으로 메세지를 전달할 수 있는 API를 제공한다.

    public SeatWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendSeatUpdate(SeatDto seatDto){
        messagingTemplate.convertAndSend("/topic/seatStatus", seatDto);
        System.out.println("웹소켓 데이터 전송");
    }

    public void sendSeatChange(SeatDto oldSeat, SeatDto newSeat){
        messagingTemplate.convertAndSend("/topic/seatStatus", oldSeat);
        messagingTemplate.convertAndSend("/topic/seatStatus", newSeat);
    }
}
