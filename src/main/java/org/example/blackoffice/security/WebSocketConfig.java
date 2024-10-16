package org.example.blackoffice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
// Spring 프레임워크에서 설정 클래스임을 나타내기 위해 사용함. @Coinfigration이 붙은 클래스 내에서 Bean을 정의하고 그 Bean들의 의존성 주입을 설정하는 역할을 한다.
// Configration이 붙은 클래스 안에서는 Bean 등록이 가능하고 spring 프레임워크는 생성된 Bean들을 Spring container에 등록한 다음 필요한 곳에 그 Bean의 의존성 주입을 통해 제공하는 역할
// Spring 컨테이너는 빈들을 보관하고 제공하는 역할을 함, Spring 프레임워크는 어플리케이션 시작 시 @Configuration 클래스를 스캔하여 빈을 등록 관리함
// @Autowired 어노테이션 또는 생성자를 통해 빈을 주입 받을 수가 있다.
@EnableWebSocketMessageBroker
// 이 어노테이션을 인식을 못했었는데 Spring Websocket 플러그인이 없는게 문제였음
// WebsocketMessageBroker는 클라이언트가 보내는 메시지를 특정 경로로 라우팅을 함. 그 경로를 구독하고 있는 클라이언트들에게 메세지를 전달하게 된다.
// 또한 클라이언트가 서버로 보낸 메세지를 서버 내에서 적절히 처리할 수 있도록 경로를 설정해준다.
// 또한 서버가 여러 클라이언트에게 동시에 메세지를 전송헐 수 있도록 한다. 이를 통해 클라이언트 간의 상태 동기화가 가능하다.
// STOMP 프로토콜: WebSocket 위에서 동작하는 간단한 텍스트 기반의 메시징 프로토콜로 WebsocketMessageBroker와 함꼐 사용
// 클라이언트는 STOMP 프로토콜로 메시지를 전송해야 한다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // 메모리 내에서 동작하는 메시지 브로커임, 특정 경로로 들어온 메시지를 해당 경로를 구독 중인 클라이언트들에게 전달함
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트가 서버로 메시지를 보낼 떄 사용되는 경로의 접두어(prefix)를 설정
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS(); // WebSocket 연결을 위한 앤드포인트 정의, 클라이언트가 이 경로로 Websocket 연결을 시도하게 됨. 이 앤트포인트는 연결을 하는 앤드포인트이고 실제 메세지 전송은 위에서 설정한 Prefix로 보냄
        //withSockJs()는 Websocket을 지원하지 않는 환경에서 대체 통신 방식을 사용할 수 있도록 해주는 풀백 기능을 활성화 한다. XHR, long polling 등의 대체 방식을 사용하여 비슷한 통신을 구현
    }
}
