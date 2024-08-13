package org.example.blackoffice.repository;

import org.example.blackoffice.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email); // 중복확인을 하기 위한 함수임
}
