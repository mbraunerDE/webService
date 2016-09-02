package de.mbrauner.webservice;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

public class Main {

	public static final int JETTYPORT = 80;

	public static void main(String[] args) {
		port(JETTYPORT);
		staticFileLocation("/public");

		get("/", (request, response) -> { // Status page
			response.redirect("/index.html");
			return null;
		});
	}

	private Main() {
	}

}
