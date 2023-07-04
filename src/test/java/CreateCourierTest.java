import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.ApiCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class CreateCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
    @Test
    @DisplayName("Create Courier")
    @Description("Create Courier, 201")
    public void createCourierSuccess() {
        Response response = ApiCollection.createCourier(CreateData.LOGIN_ONE,
                CreateData.PASSWORD_ONE, CreateData.FIRST_NAME_ONE);

        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
        System.out.println(response.body().asString());
    }
    @Test
    @DisplayName("Create Courier Duplicate")
    @Description("Create Courier Duplicate, error 409")
    public void createCourierDuplicate() {
        ApiCollection.createCourier(CreateData.LOGIN_TWO, CreateData.PASSWORD_TWO, CreateData.FIRST_NAME_TWO);

        Response response = ApiCollection.createCourier(CreateData.LOGIN_TWO, CreateData.PASSWORD_TWO,
                CreateData.FIRST_NAME_TWO);

        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);

        System.out.println(response.body().asString());
    }
    @Test
    @DisplayName("Create Courier Incomplete Data")
    @Description("Create Courier Incomplete Data,  error 400")
    public void createCourierIncompleteData() {

        Response response = ApiCollection.createCourierWithoutPass(CreateData.FIRST_NAME_ONE);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);

        System.out.println(response.body().asString());
    }
    @After
    public void cleanUp() {
        int id = ApiCollection.getCourierId(CreateData.LOGIN_ONE, CreateData.PASSWORD_ONE);
        ApiCollection.deleteCourier(id);
    }

}
