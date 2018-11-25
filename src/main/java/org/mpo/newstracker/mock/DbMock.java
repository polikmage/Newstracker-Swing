package org.mpo.newstracker.mock;

import org.mpo.newstracker.entity.dao.UserDao;
import org.mpo.newstracker.entity.dao.WatchdogDao;

import java.util.Arrays;

public class DbMock {
    //mock vyhledani vsech uzivatelu v databazi
    public static UserDao [] mockGetAllUsersFromDb(){
        //create users
        WatchdogDao watchdogDao1 = new WatchdogDao("Skoda","polik.mage@seznam.cz","CZ",false);
        WatchdogDao watchdogDao2 = new WatchdogDao("Java","newstracker@email.cz","WORLD",false);
        WatchdogDao watchdogDao3 = new WatchdogDao("BMW","polik.mage@seznam.cz","AT",true);


        UserDao userDao1 = new UserDao();
        UserDao userDao2 = new UserDao();
        userDao1.setUsername("Marek");
        userDao1.setWatchdogs(Arrays.asList(watchdogDao1));
        userDao2.setUsername("Olinka");
        userDao2.setWatchdogs(Arrays.asList(watchdogDao2,watchdogDao3));


        UserDao [] userDaos = {userDao1,userDao2};
        return userDaos;
    }
}
