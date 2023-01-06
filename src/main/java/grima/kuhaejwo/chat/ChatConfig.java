//package grima.kuhaejwo.chat;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class ChatConfig implements WebSocketMessageBrokerConfigurer {
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        // stomp 접속 주소 url /ws-stomp
//        registry.addEndpoint("/ws/stomp").setAllowedOriginPatterns("*")
//                .withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        // url을 chat/room/3 >>> chat.room.3 으로 참조하기 위한 설정
//        registry.setPathMatcher(new AntPathMatcher("."));
//
//        // 메시지를 구독하는 요청 url > 메세지 받을 때
//        // registry.enableSimpleBroker("/sub");
//        registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue");
//
//        //메세지륿 발행하는 요청 url > 메세지 보낼 때
//        registry.setApplicationDestinationPrefixes("/pub");
//    }
//}
