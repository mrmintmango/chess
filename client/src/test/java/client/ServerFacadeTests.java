package client;

import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import java.io.IOException;


public class ServerFacadeTests {

    private static Server server;
    ServerFacade serverFacade = new ServerFacade("http://localhost:8080");

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerPassTest() throws IOException {
        String answer = serverFacade.register("user1", "pass", "email");

        Assertions.assertEquals(answer, "GOOD");
    }

    @Test
    public void registerFailTest() throws IOException {
        String answer = serverFacade.register("user1", null, "email");

        Assertions.assertNotEquals(answer, "GOOD");
    }



}
