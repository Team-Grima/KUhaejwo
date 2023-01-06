//package grima.kuhaejwo.chat;
//
//import lombok.Data;
//import lombok.Getter;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.UUID;
//
//
//// Stomp 를 통해 pub/sub 를 사용하면 구독자 관리가 알아서 된다!!
//// 따라서 따로 세션 관리를 하는 코드를 작성할 필도 없고,
//// 메시지를 다른 세션의 클라이언트에게 발송하는 것도 구현 필요가 없다!
//@Entity
//@Getter
//public class ChatRoom {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String roomId; // 채팅방 아이디
//    private String roomName; // 채팅방 이름
//
//    private long userCount; // 채팅방 인원수
//
//    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<ChatMessage> chatMessageList = new ArrayList<>();
//
//    public ChatRoom create(String roomName) {
//
//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.roomId = UUID.randomUUID().toString();
//        chatRoom.roomName = roomName;
//
//        return chatRoom;
//    }
//
//}