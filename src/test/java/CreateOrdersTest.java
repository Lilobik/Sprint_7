import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.CreateOrders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.isA;

@RunWith(Parameterized.class)
public class CreateOrdersTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;

    public CreateOrdersTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Тест выбора цвета: {9}")
    public static Object[][] params() {
        return new Object[][]{
                {"Иван", "Иванов", "Ленина, 1", 4, "+7 800 355 35 35", 4, "2023-07-01", "Тестовый заказ", List.of("BLACK")},
                {"Олег", "Тестировщик", "Проспект космонавтов, 12А.", 4, "+7 800 355 35 35", 6, "2023-06-01", "Тестовый заказ", List.of("GREY")},
                {"Тест", "Тестовый", "Тест, 1234.", 4, "+7 800 355 35 35", 8, "2022-01-01", "Тестовый заказ", List.of("BLACK", "GREY")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of()},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }
    @Test
    @DisplayName("Create order")
    @Description("Create order, 201")
    public void CreateOrdersColorTest() {
        CreateOrders orders = new CreateOrders(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(orders)
                .when()
                .post("/api/v1/orders");

        response.then().assertThat().body("track", isA(Integer.class))
                .and()
                .statusCode(201);

        System.out.println(response.body().asString());
    }

}
