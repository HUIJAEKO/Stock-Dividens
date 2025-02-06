package stock.dividends.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.dividends.domain.Dividend;

public interface DividendRepository extends JpaRepository<Dividend, Long> {
}
