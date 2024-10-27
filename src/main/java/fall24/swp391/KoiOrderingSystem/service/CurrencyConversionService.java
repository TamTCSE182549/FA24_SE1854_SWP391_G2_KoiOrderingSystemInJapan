package fall24.swp391.KoiOrderingSystem.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyConversionService {
    private final String API_URL = "http://apilayer.net/api/live";
    private final String ACCESS_KEY = "47b1ac4ba273758245ba5544b58a9f52";
    private final String CURRENCIES = "VND";
    private final String SOURCE = "USD";
    private final String FORMAT = "1";

    // Lấy tỷ giá USD -> VND
    public float getExchangeRate() {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + "?access_key=" + ACCESS_KEY + "&currencies=" + CURRENCIES + "&source=" + SOURCE + "&format=" + FORMAT;

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null && Boolean.TRUE.equals(responseBody.get("success")) && responseBody.containsKey("quotes")) {
                Map<String, Float> quotes = (Map<String, Float>) responseBody.get("quotes");
                Float exchangeRate = Float.parseFloat(String.valueOf(quotes.get("USDVND")));
                if (exchangeRate != null) {
                    return exchangeRate;
                }
            }
        }

        throw new RuntimeException("Failed to fetch exchange rate from API");
    }

    // Chuyển đổi số tiền từ USD sang VND
    public float convertUsdToVnd(float amountInUsd) {
        float exchangeRate = getExchangeRate();
        return amountInUsd * exchangeRate;
    }
}
