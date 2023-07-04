package org.example;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiCollection {
    public static Response createCourier(String login, String password, String firstName) {
        Courier courier  = new Courier(login, password, firstName);
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }
    public static Response createCourierWithoutPass(String firstName) {
        Courier courier  = new Courier(firstName);
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }
    public static Response loginCourier(LoginCourier login) {
        return given()
                .header("Content-type", "application/json")
                .body(login)
                .when()
                .post("/api/v1/courier/login");
    }
    public static int getCourierId(String login, String password) {
        LoginCourier auth = new LoginCourier(login, password);
        Response response = loginCourier(auth);

        if (response != null && response.statusCode() == 200) {
            return response.then().extract().path("id");
        } else {
            return -1; // Обработка ошибки
        }
    }
    public static void deleteCourier(int id) {
        given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + id);
    }

}
