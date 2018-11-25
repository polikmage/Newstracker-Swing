package org.mpo.newstracker.mock;

import org.junit.jupiter.api.Test;
import org.mpo.newstracker.entity.dao.UserDao;
import org.mpo.newstracker.entity.dao.WatchdogDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DbMockTest {

    @Test
    void testUsernameMockGetAllUsersFromDb() {
        UserDao[] userDaos = DbMock.mockGetAllUsersFromDb();
        assertEquals("Marek",userDaos[0].getUsername());
    }


    @Test
    void testWatchdogEmailsMockGetAllUsersFromDb() {
        UserDao[] userDaos = DbMock.mockGetAllUsersFromDb();
        List<WatchdogDao> watchdogDaos = userDaos[0].getWatchdogs();
        assertEquals("",watchdogDaos.get(0).getEmail());
    }

    @Test
    void testWatchdogEmailsNullMockGetAllUsersFromDb() {
        UserDao[] userDaos = DbMock.mockGetAllUsersFromDb();
        List<WatchdogDao> watchdogDaos = userDaos[1].getWatchdogs();
        assertNotNull(watchdogDaos);
    }
}