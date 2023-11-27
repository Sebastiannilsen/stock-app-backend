package ntnu.idata2503.group9.stockappbackend.Models;

import java.util.Date;

public record CandlestickData(double open, double close, double low, double high, Date date, int volume) {
}

