package org.example.blackoffice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column
    private String writer;

    @ManyToOne(fetch = FetchType.LAZY) //FetchType.LAZY가 지정되면, 연관된 엔티티를 실제로 접근할 때 로드합니다. 즉, 필요할 때만 데이터를 가져오기 때문에 성능을 최적화할 수 있습니다.
    @JoinColumn(name = "userId") // userId라는 이름으로 foreign 키를 사용함, Posts 테이블의 userId 컬럼이 Member table의 id 컬럼을 참조함
    @JsonBackReference
    private Member member;

    @Column
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) // 컬럼이 직접적으로 형성되는 것은 아님, mappedBy = "post"는 외래 키가 Comments 테이블에 있다는 것을 의미, 즉, 외래 키는 Comments 테이블에 존재하고, 그 컬럼이 Post 테이블의 기본 키를 참조
    @JsonManagedReference
    private List<Comments> comments = new ArrayList<>();
}
