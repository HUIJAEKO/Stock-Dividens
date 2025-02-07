package stock.dividends.scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import stock.dividends.model.Company;
import stock.dividends.model.Dividend;
import stock.dividends.model.ScrapedResult;
import stock.dividends.model.constants.Month;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class YahooFinanceScraper implements Scraper{

    private static final String URL = "https://finance.yahoo.com/quote/%s/history?frequency=1mo&period1=%d&period2=%d";
    private static final long START_TIME = 86400;
    private static String SUMMANY_URL = "https://finance.yahoo.com/quote/%s?p=%s";

    @Override
    public ScrapedResult scrap(Company company){
        var scrapedResult = new ScrapedResult();
        scrapedResult.setCompany(company);

        try{
            long end = System.currentTimeMillis() / 1000;
            String url = String.format(URL, company.getTicker(), START_TIME, end);
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();

            Elements parsingDivs = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element tableEls = parsingDivs.get(0);

            Element tbody = tableEls.children().get(1);
            List<Dividend> dividends = new ArrayList<>();
            for(Element e : tbody.children()){
                String txt = e.text();
                if(!txt.endsWith("Dividend")){
                    continue;
                }

                String[] splits = txt.split(" ");
                int month = Month.strToNumber(splits[0]);
                int day = Integer.valueOf(splits[1].replace(",", ""));
                int year = Integer.valueOf(splits[2]);
                String dividend = splits[3];

                if(month < 0){
                    throw new RuntimeException("Unexpected Month enum value");
                }

                dividends.add(Dividend.builder()
                            .date(LocalDateTime.of(year, month, day, 0, 0))
                            .dividend(dividend)
                            .build());

//                System.out.println(year + "/" + month + "/" + day + "->" + dividend);
            }
            scrapedResult.setDividendEntities(dividends);

        }catch(IOException e){
            e.printStackTrace();
        }

        return scrapedResult;
    }


    @Override
    public Company scrapCompanyByTicker(String ticker){
        String url = String.format(SUMMANY_URL, ticker, ticker);

        try{
            Document document = Jsoup.connect(url).get();
            Element titleEle = document.getElementsByTag("h1").get(0);
            String title = titleEle.text().split(" - ")[1].trim();

            return Company.builder()
                    .name(title)
                    .ticker(ticker)
                    .build();
        }catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
