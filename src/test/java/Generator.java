import com.github.javafaker.Faker;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Data
@NoArgsConstructor
public class Generator {
    private static Faker fakerRu = new Faker(new Locale("ru"));
    private static Faker fakerEng = new Faker(new Locale("eng"));

    public static String getRandomCorrectCity() {
        List<String> list = Arrays.asList("Санкт-Петербург", "Москва", "Новосибирск", "Казань");
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    public static String getEngCity() {
        return fakerEng.address().cityName();
    }

    public static String getRandomCorrectDate() {
        int min = 4;
        int max = 14;
        Random random = new Random();
        int randomNumber = random.nextInt((max - min) + 1) + min;
        return LocalDate.now().plusDays(randomNumber).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String getRandomIncorrectDate() {
        int min = -14;
        int max = 2;
        Random random = new Random();
        int randomNumber = random.nextInt((max - min) + 1) + min;
        if (randomNumber > 0) {
            return LocalDate.now().plusDays(randomNumber).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
        return LocalDate.now().minusDays(Math.abs(randomNumber)).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String getRandomCorrectName() {
        return fakerRu.name().lastName() + " " + fakerRu.name().firstName();
    }

    public static String getRandomIncorrectName() {
        return fakerEng.name().fullName();
    }

    public static String getRandomCorrectPhone() {
        return fakerRu.phoneNumber().phoneNumber();
    }

    public static String getRandomIncorrectPhone() {
        return fakerEng.phoneNumber().phoneNumber();
    }
}
