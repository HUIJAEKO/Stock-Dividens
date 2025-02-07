package stock.dividends.scraper;

import stock.dividends.model.Company;
import stock.dividends.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
