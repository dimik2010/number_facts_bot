import java.time.LocalDate;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class NumbersAPI {
    private static final String MATH_URL = "http://numbersapi.com/%d/math";
    private static final String TRIVIA_URL = "http://numbersapi.com/%d";
    private static final String DATE_URL = "http://numbersapi.com/%d/%d/date";

    private static final String ERROR_MESSAGE = "Error: can't access API server";

    private NumbersAPI() {
    }

    public static String getMathFact(int number) {
        String url = String.format(MATH_URL, number);
        try {
            return Unirest.get(url).asString().getBody();
        } catch (UnirestException e) {
            return ERROR_MESSAGE;
        }
    }

    public static String getTriviaFact(int number) {
        String url = String.format(TRIVIA_URL, number);
        try {
            return Unirest.get(url).asString().getBody();
        } catch (UnirestException e) {
            return ERROR_MESSAGE;
        }
    }

    public static String getDateFact(LocalDate date) {
        String url = String.format(DATE_URL, date.getMonth().getValue(), date.getDayOfMonth());
        try {
            return Unirest.get(url).asString().getBody();
        } catch (UnirestException e) {
            return ERROR_MESSAGE;
        }
    }
}
