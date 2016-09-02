package de.mbrauner.webservice;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.exception;
import static spark.Spark.staticFileLocation;

public class Main {

	public static final int JETTYPORT = 80;

	public static void main(String[] args) {
		if( args != null && args.length == 1){
			port(Integer.parseInt(args[0]));
		}else{
			port(JETTYPORT);
		}
		staticFileLocation("/public");
get("/throwexception", (request, response) -> {
    throw new Exception();
});

exception(Exception.class, (e, request, response) -> {
    response.status(404);
    response.body("Resource not found");
});
		get("/", (request, response) -> {
			response.redirect("/index.html");
			return null;
		});
	}

	private Main() {
	}

}
