package stock.dividends.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.dividends.domain.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}
