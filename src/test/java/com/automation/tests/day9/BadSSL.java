package com.automation.tests.day9;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

public class BadSSL {

    @Test
    public void badSSLCertificate() {
        /**
         * no valid certificate - no handshake, no secure connection
         */
        baseURI = "https://untrusted-root.badssl.com/";
        get().prettyPeek();
    }
}
