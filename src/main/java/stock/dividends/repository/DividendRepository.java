package stock.dividends.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.dividends.domain.DividendEntity;

import java.util.List;

public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
    List<DividendEntity> findAllByCompanyId(Long companyId);
}
