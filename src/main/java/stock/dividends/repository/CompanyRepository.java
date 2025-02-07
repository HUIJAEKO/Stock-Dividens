package stock.dividends.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.dividends.domain.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
}
