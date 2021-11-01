package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class AuthTest {
	
	@Test
	public void deveAcessarSWAPI() {
		given()
			.log().all()
		.when()
			.get("https://swapi.dev/api/people/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Luke Skywalker"))
		;
	}
	
	
	// https://api.openweathermap.org/data/2.5/weather?q=Fortaleza,ce,BR&appid=ddf35855c3fff98e152766e0ad1ec109&&units=metric
	
	@Test
	public void deveObterClima() {
		given()
			.log().all()
			.queryParam("q", "Fortaleza,ce,BR")
			.queryParam("appid", "ddf35855c3fff98e152766e0ad1ec109")
			.queryParam("units", "metric")
		.when()
			.get("https://api.openweathermap.org/data/2.5/weather")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Fortaleza"))
			.body("coord.lon", is(-38.5247f))
			.body("main.temp", greaterThan(25f))	
		;
	}	
	
	@Test
	public void validandoHttptatusCodeAssert() {
		Response response = RestAssured.request(Method.GET, "https://api.openweathermap.org/data/2.5/weather");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(401);
		
		get("https://api.openweathermap.org/data/2.5/weather").then().statusCode(401);
		
		given()
		.when()
			.get("https://api.openweathermap.org/data/2.5/weather")
		.then()
// 			.assertThat()
			.statusCode(401);
	}
}