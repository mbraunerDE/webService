/**
 *
 */
package de.mbrauner.webservice.test;

import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import de.mbrauner.webservice.Main;

/**
 *
 */
public class ApiTest {

	private static final int TESTPORT=12345;

	private static final Thread jetty = new Thread() {
		@Override
		public synchronized void start() {
			try {
				Main.main(new String[]{""+TESTPORT});
			} catch (NoClassDefFoundError e) {
				LOG.error(e.getMessage(), e);
				System.exit(-1);
			}
		};
	};

	private static final Logger LOG = LoggerFactory.getLogger(ApiTest.class);

	public static final String URL = "http://localhost:" + TESTPORT +"/";

	@AfterClass
	public static void afterClass() {
		jetty.interrupt();
		LOG.trace("Jetty interrupted");
	}

	@BeforeClass
	public static void beforeClass() {
		jetty.start();
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			LOG.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		LOG.trace("Jetty started");
	}

	@Test
	public void testGet() {
		HttpResponse<String> response = null;
		try {
			response = Unirest.get(URL).asString();
		} catch (UnirestException e) {
			LOG.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(!response.getBody().trim().isEmpty());
		Assert.assertEquals(200, response.getStatus());
		LOG.trace(response.getBody());
	}

}
