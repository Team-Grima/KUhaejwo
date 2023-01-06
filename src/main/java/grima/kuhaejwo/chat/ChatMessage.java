//package grima.kuhaejwo.chat;
//
//import lombok.Getter;
//import org.hibernate.annotations.CreationTimestamp;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//public class ChatMessage {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "chatRoom_id")
//    private ChatRoom chatRoom;
//
//    private String writer;
//
//    private String message;
//
//    @CreationTimestamp
//    @Column(updatable = false)
//    private LocalDateTime sendDate;
//
//
//}
