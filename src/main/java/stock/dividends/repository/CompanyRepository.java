package stock.dividends.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.dividends.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
