package courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import utils.Specification;

import static io.restassured.RestAssured.given;

public class ApiCourier {
    private static final String PATH = "api/v1/courier";
    private static final String LOGIN_PATH = "api/v1/courier/login";
    private static final String DELETE_PATH = "api/v1/courier/";

    @Step("Send POST request to /api/v1/courier")
    public static Response createCourier(String login, String password, String firstName) {
        Courier courier  = new Courier(login, password, firstName);
        return given()
                .spec(Specification.requestSpecification())
                .body(courier)
                .when()
                .post(PATH);
    }
    @Step("Send POST request to /api/v1/courier")
    public static Response createCourierWithoutPass(String firstName) {
        Courier courier  = new Courier(firstName);
        return given()
                .spec(Specification.requestSpecification())
                .body(courier)
                .when()
                .post(PATH);
    }
    @Step("Send POST request to /api/v1/courier/login")
    public static Response loginCourier(LoginCourier login) {
        return given()
                .spec(Specification.requestSpecification())
                .body(login)
                .when()
                .post(LOGIN_PATH);
    }
    @Step("Get courier id by login and password")
    public static int getCourierId(String login, String password) {
        LoginCourier auth = new LoginCourier(login, password);
        Response response = loginCourier(auth);

        if (response != null && response.statusCode() == 200) {
            return response.then().extract().path("id");
        } else {
            return -1; // Обработка ошибки
        }
    }
    @Step("Send DELETE request to /api/v1/courier/:id")
    public static void deleteCourier(int id) {
        given()
                .spec(Specification.requestSpecification())
                .when()
                .delete(DELETE_PATH + id);
    }

}
