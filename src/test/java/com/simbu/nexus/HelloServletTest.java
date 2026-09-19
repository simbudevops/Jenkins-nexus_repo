package com.simbu.nexus;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HelloServletTest {

    @Test
    void servletLoads() {
        assertNotNull(new HelloServlet());
    }
}
