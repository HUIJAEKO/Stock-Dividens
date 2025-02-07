package stock.dividends.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.dividends.domain.CompanyEntity;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByTicker(String ticker);

    Optional<CompanyEntity> findByName(String name);
}
