package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ParentServiceTests {
    private ParentService parentService;

    @BeforeEach
    public void setUp() {
        parentService = new ParentService();
    }

    @Test
    public void testClear() {
        parentService.ClearApplication();

        Assertions.assertEquals(0, parentService.dataAccess.authDataMap.size());
        Assertions.assertEquals(0, parentService.dataAccess.gameDataMap.size());
        Assertions.assertEquals(0, parentService.dataAccess.userDataMap.size());
    }

}
