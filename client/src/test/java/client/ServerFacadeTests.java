package client;

import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;
import ui.Client;

import java.io.IOException;
import java.util.ArrayList;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        Client client = new Client();
        var port = server.run(0);
        serverFacade = new ServerFacade("http://localhost:" + port, client);
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
        server.clearServer("Ruben is Awesome");
        String answer = serverFacade.register("user1", "pass", "email");

        Assertions.assertTrue(answer.contains("GOOD"));
    }

    @Test
    public void registerFailTest() throws IOException {
        String answer = serverFacade.register("user1", null, "email");

        Assertions.assertNotEquals(answer, "GOOD");
    }

    @Test
    public void loginPassTest() throws IOException {
        serverFacade.register("user1", "pass", "email");
        String answer = serverFacade.login("user1", "pass");

        Assertions.assertTrue(answer.contains("GOOD"));
    }

    @Test
    public void loginFailTest() throws IOException {
        serverFacade.register("user1", "pass", "email");
        String answer = serverFacade.login("user1", "womp");

        Assertions.assertFalse(answer.contains("GOOD"));
    }

    @Test
    public void logoutPassTest() throws IOException {
        serverFacade.register("user1", "pass", "email");
        String login = serverFacade.login("user1", "pass");
        String answer = serverFacade.logout(login.substring(4));

        Assertions.assertTrue(answer.contains("GOOD"));
    }

    @Test
    public void logoutFailTest() throws IOException {
        serverFacade.register("user1", "pass", "email");
        String login = serverFacade.login("user1", "pass");
        String answer = serverFacade.logout(login.substring(2));

        Assertions.assertFalse(answer.contains("GOOD"));
    }

    @Test
    public void listGamesPassTest() throws IOException {
        server.clearServer("Ruben is Awesome");
        serverFacade.register("user1", "pass", "email");
        String login = serverFacade.login("user1", "pass");
        serverFacade.createGame("game", login.substring(4));
        serverFacade.createGame("game2", login.substring(4));
        ArrayList<String> list = serverFacade.listGames(login.substring(4));

        Assertions.assertEquals(8, list.size());
    }

    @Test
    public void listGamesFailTest() throws IOException {
        server.clearServer("Ruben is Awesome");
        serverFacade.register("user1", "pass", "email");
        String login = serverFacade.login("user1", "pass");
        serverFacade.createGame("game", login.substring(4));
        serverFacade.createGame("game2", login.substring(4));
        ArrayList<String> list = serverFacade.listGames(login.substring(4));

        Assertions.assertNotEquals(3, list.size());
    }

    @Test
    public void createGamePassTest() throws IOException {
        server.clearServer("Ruben is Awesome");
        serverFacade.register("user1", "pass", "email");
        String login = serverFacade.login("user1", "pass");
        String make = serverFacade.createGame("game", login.substring(4));

        Assertions.assertEquals("GOOD", make);
    }

    @Test
    public void createGameFailTest() throws IOException {
        server.clearServer("Ruben is Awesome");
        serverFacade.register("user1", "pass", "email");
        String login = serverFacade.login("user1", "pass");
        String make = serverFacade.createGame("game", login.substring(2));

        Assertions.assertNotEquals("GOOD", make);
    }

    @Test
    public void joinGamePassTest() throws IOException {
        server.clearServer("Ruben is Awesome");
        serverFacade.register("user1", "pass", "email");
        String login = serverFacade.login("user1", "pass");
        serverFacade.createGame("game", login.substring(4));
        System.out.print(serverFacade.listGames(login.substring(4)));
        String join = serverFacade.joinGame("WHITE", Integer.parseInt(serverFacade.listGames(login.substring(4)).getFirst()), login.substring(4));

        Assertions.assertEquals("GOOD", join);
    }

    @Test
    public void joinGameFailTest() throws IOException {
        server.clearServer("Ruben is Awesome");
        serverFacade.register("user1", "pass", "email");
        String login = serverFacade.login("user1", "pass");
        serverFacade.createGame("game", login.substring(4));
        String join = serverFacade.joinGame("WHITE", 4, login.substring(4));

        Assertions.assertNotEquals("GOOD", join);
    }

}
