package stock.dividends.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.dividends.domain.DividendEntity;

public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
}
