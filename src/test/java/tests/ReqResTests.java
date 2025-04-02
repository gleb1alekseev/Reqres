package tests;

import reqres_objects.*;
import com.google.gson.Gson;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReqResTests {

    public static final String BASE_URL = "https://reqres.in/api";

    SoftAssert softAssert = new SoftAssert();

    User user = User.builder()
            .name("morpheus")
            .job("leader")
            .build();

    User user2 = User.builder()
            .name("morpheus")
            .job("zion resident")
            .build();

    User user3 = User.builder()
            .email("eve.holt@reqres.in")
            .password("pistol")
            .build();

    User user4 = User.builder()
            .email("sydney@fife")
            .build();

    User user5 = User.builder()
            .email("eve.holt@reqres.in")
            .password("cityslicka")
            .build();

    User user6 = User.builder()
            .email("peter@klaven")
            .build();

    @Test (description = "Create user and check status code and data")
    public void postCreateUserTest() {
        given()
                .log().all()
                .body(user)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL + "/users")
                .then()
                .log().all()
                .body("name", equalTo("morpheus"),
                        "job", equalTo("leader"))
                .statusCode(201);
    }

    @Test (description = "Get list of users and check status code and all exist fields of the first user")
    public void getUsersListTest() {

        String body = given()
                .log().all()
                .when()
                .get(BASE_URL + "/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        UsersList usersList = new Gson().fromJson(body, UsersList.class);
        softAssert.assertEquals(usersList.getData().get(0).getId(), 7);
        softAssert.assertEquals(usersList.getData().get(0).getFirstName(), "Michael");
        softAssert.assertEquals(usersList.getData().get(0).getLastName(), "Lawson");
        softAssert.assertEquals(usersList.getData().get(0).getEmail(), "michael.lawson@reqres.in");
        softAssert.assertEquals(usersList.getData().get(0).getAvatar(), "https://reqres.in/img/faces/7-image.jpg");
        softAssert.assertAll();
    }

    @Test (description = "Get Single user not found test")
    public void getSingleUserNotFoundTest() {
        given()
                .log().all()
                .get(BASE_URL + "/users/23")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test (description = "Get single user test")
    public void getSingleUserTest() {
        String body = given()
                .log().all()
                .when()
                .get(BASE_URL + "/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        UserForSingleUser user = new Gson().fromJson(body, UserForSingleUser.class);
        softAssert.assertEquals(user.getId(), 2);
        softAssert.assertEquals(user.getFirstName(), "Janet");
        softAssert.assertEquals(user.getLastName(), "Weaver");
        softAssert.assertEquals(user.getEmail(), "janet.weaver@reqres.in");
        softAssert.assertEquals(user.getAvatar(), "https://reqres.in/img/faces/2-image.jpg");
    }


    @Test (description = "Get List<Resource> test")
    public void getListResourceTest() {
        String body = given()
                .log().all()
                .when()
                .get(BASE_URL + "/unknown")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        ResourceList resourceList = new Gson().fromJson(body, ResourceList.class);
        softAssert.assertEquals(resourceList.getData().get(0).getId(), 1);
        softAssert.assertEquals(resourceList.getData().get(0).getName(), "cerulean");
        softAssert.assertEquals(resourceList.getData().get(0).getYear(), 2000);
        softAssert.assertEquals(resourceList.getData().get(0).getColor(), "#98B2D1");
        softAssert.assertEquals(resourceList.getData().get(0).getPantoneValue(), "15-4020");
        softAssert.assertAll();
    }

    @Test (description = "Get single resource test")
    public void getSingleResourceTest() {
        String body = given()
                .log().all()
                .when()
                .get(BASE_URL + "/unknown/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        ResourceForSingleUser resource = new Gson().fromJson(body, ResourceForSingleUser.class);
        softAssert.assertEquals(resource.getId(), 2);
        softAssert.assertEquals(resource.getName(), "fuchsia rose");
        softAssert.assertEquals(resource.getYear(), 2001);
        softAssert.assertEquals(resource.getColor(), "17-2031");
    }


    @Test (description = "Get Single resource not found test")
    public void getSingleResourceNotFoundTest() {
        given()
                .log().all()
                .get(BASE_URL + "/unknown/23")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test (description = "Put update, check name, job")
    public void putUpdateUserTest() {
        given()
                .log().all()
                .body(user2)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .put(BASE_URL + "/users/2")
                .then()
                .log().all()
                .body("name", equalTo("morpheus"),
                        "job", equalTo("zion resident"))
                .statusCode(200);
    }

    @Test (description = "Patch update, check name, job")
    public void patchUpdateUserTest() {
        given()
                .log().all()
                .body(user2)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .patch(BASE_URL + "/users/2")
                .then()
                .log().all()
                .body("name", equalTo("morpheus"),
                        "job", equalTo("zion resident"))
                .statusCode(200);
    }

    @Test (description = "Delete user")
    public void deleteUserTest() {
        given()
                .log().all()
                .delete(BASE_URL + "/users/2")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test (description = "Post register user")
    public void postRegisterUserTest() {
        given()
                .log().all()
                .body(user3)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL + "/register")
                .then()
                .log().all()
                .body("id", equalTo(4),
                        "token", equalTo("QpwL5tke4Pnpja7X4"))
                .statusCode(200);
    }

    @Test (description = "Post unsuccessful registration")
    public void postUnsuccessfulRegistrationTest() {
        given()
                .log().all()
                .body(user4)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL + "/register")
                .then()
                .log().all()
                .body("error", equalTo("Missing password"))
                .statusCode(400);
    }


    @Test (description = "Post login successful")
    public void postLoginSuccessfulTest() {
        given()
                .log().all()
                .body(user5)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL + "/login")
                .then()
                .log().all()
                .body("token", equalTo("QpwL5tke4Pnpja7X4"))
                .statusCode(200);
    }

    @Test (description = "Post unsuccessful login")
    public void postUnsuccessfulLoginTest() {
        given()
                .log().all()
                .body(user6)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL + "/login")
                .then()
                .log().all()
                .body("error", equalTo("Missing password"))
                .statusCode(400);
    }


    @Test (description = "Get delayed response")
    public void getDelayedResponseTest() {

        String body = given()
                .log().all()
                .when()
                .get(BASE_URL + "/users?delay=3")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        UsersList usersList = new Gson().fromJson(body, UsersList.class);
        softAssert.assertEquals(usersList.getData().get(0).getId(), 1);
        softAssert.assertEquals(usersList.getData().get(0).getEmail(), "george.bluth@reqres.in");
        softAssert.assertEquals(usersList.getData().get(0).getFirstName(), "George");
        softAssert.assertEquals(usersList.getData().get(0).getLastName(), "Bluth");
        softAssert.assertEquals(usersList.getData().get(0).getAvatar(), "https://reqres.in/img/faces/1-image.jpg");
        softAssert.assertAll();
    }
}