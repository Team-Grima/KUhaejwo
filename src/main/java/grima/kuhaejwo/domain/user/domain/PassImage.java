package grima.kuhaejwo.domain.user.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Embeddable
public class PassImage {
    @Column(name = "passImage_fileOriName")
    private String fileOriName;

    @Column(name = "passImage_fileUrl")
    private String fileUrl;
}