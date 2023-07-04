import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.ApiCollection;
import org.example.LoginCourier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class LoginCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        ApiCollection.createCourier(CreateData.LOGIN_ONE, CreateData.PASSWORD_ONE, CreateData.FIRST_NAME_ONE);
    }

    @Test
    @DisplayName("Success login")
    @Description("Success login, 200")
    public void loginCourierTest() {
        LoginCourier login = new LoginCourier(CreateData.LOGIN_ONE, CreateData.PASSWORD_ONE);
        Response response = ApiCollection.loginCourier(login);
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Login without login")
    @Description("Login without login, 400")
    public void loginCourierWithoutPass() {
        LoginCourier login = new LoginCourier(CreateData.LOGIN_ONE, "");
        Response response = ApiCollection.loginCourier(login);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());
    }


    @Test
    @DisplayName("Incorrect login")
    @Description("Incorrect login, 404")
    public void loginCourierIncorrectLogin() {
        LoginCourier login = new LoginCourier(CreateData.LOGIN_INCORRECT, CreateData.PASSWORD_ONE);
        Response response = ApiCollection.loginCourier(login);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Incorrect password")
    @Description("Incorrect password, 404")
    public void loginCourierIncorrectPass() {
        LoginCourier login = new LoginCourier(CreateData.LOGIN_ONE, CreateData.PASSWORD_INCORRECT);
        Response response = ApiCollection.loginCourier(login);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        System.out.println(response.body().asString());
    }
    @After
    public void cleanUp() {
        int id = ApiCollection.getCourierId(CreateData.LOGIN_ONE, CreateData.PASSWORD_ONE);
        ApiCollection.deleteCourier(id);
    }

}
