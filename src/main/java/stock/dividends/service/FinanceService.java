package stock.dividends.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import stock.dividends.domain.CompanyEntity;
import stock.dividends.domain.DividendEntity;
import stock.dividends.model.Company;
import stock.dividends.model.Dividend;
import stock.dividends.model.ScrapedResult;
import stock.dividends.repository.CompanyRepository;
import stock.dividends.repository.DividendRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Nodes.collect;

@Service
@AllArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository

    public ScrapedResult getDividendByCompanyName(String companyName){
        CompanyEntity company = this.companyRepository.findByName(companyName)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회사입니다."));

        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());

        List<Dividend> dividends = new ArrayList<>();

//        for(var entity : dividendEntities){
//            dividends.add(Dividend.builder()
//                    .date(entity.getDate())
//                    .dividend(entity.getDividend())
//                    .build());
//        }

        dividendEntities.stream()
                .map(e -> Dividend.builder()
                        .date(e.getDate())
                        .dividend(e.getDividend())
                        .build())
                .collect(Collectors.toList());

        return new ScrapedResult(Company.builder()
                .ticker(company.getTicker())
                .name(company.getName())
                .build(),
        null);
    }
}
