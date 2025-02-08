package stock.dividends.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stock.dividends.domain.CompanyEntity;
import stock.dividends.domain.DividendEntity;
import stock.dividends.model.Company;
import stock.dividends.model.ScrapedResult;
import stock.dividends.repository.CompanyRepository;
import stock.dividends.repository.DividendRepository;
import stock.dividends.scraper.Scraper;

import java.util.List;

@Slf4j
@Component
@EnableCaching
@AllArgsConstructor
public class ScraperScheduler {

    private final CompanyRepository companyRepository;
    private final Scraper yahooFinanceScraper;
    private final DividendRepository dividendRepository;

    @CacheEvict(value = "finance", allEntries = true)
    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling(){
        log.info("scraping scheduler is started");

        List<CompanyEntity> companyEntities = this.companyRepository.findAll();

        for(var company : companyEntities){
            ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(new Company(company.getTicker(), company.getName()));

            scrapedResult.getDividendEntities().stream()
                    .map(e -> new DividendEntity(company.getId(), e))
                    .forEach(e -> {
                        boolean exists = this.dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());
                        if(!exists){
                            this.dividendRepository.save(e);
                        }
                    });
        }
    }
}
