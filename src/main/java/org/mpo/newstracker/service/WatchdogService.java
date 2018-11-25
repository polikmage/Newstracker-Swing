package org.mpo.newstracker.service;

import org.mpo.newstracker.entity.dao.UserDao;
import org.mpo.newstracker.entity.dao.WatchdogDao;
import org.mpo.newstracker.entity.dto.WatchdogDto;
import org.mpo.newstracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class WatchdogService {
    private final Logger log = LoggerFactory.getLogger(WatchdogService.class);
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<WatchdogDto[]> setWatchdog(WatchdogDto watchdogDto,String username) {

/*        WatchdogDao watchdogInfo = new WatchdogDao(watchdogRequest);
        //List<WatchdogDao> watchdogInfos =

        //user.setWatchdogs(Arrays.asList(watchdogInfo));
        user.setWatchdogs(watchdogInfo);
        userRepository.save(user);*/

        //TODO, uzivatel si zada vlastne klasicke vyhledavani, a k tomu email a k nemu se do db ulozi watchdog objekt.


        //UserDao[] users = userRepository.findAll().stream().toArray(UserDao[]::new);
        //return users;
        UserDao userDao = userRepository.findByUsername(username);
        List<WatchdogDao> watchdogDaoList;
        if(userDao.getWatchdogs().isEmpty()){
            watchdogDaoList = new ArrayList<>();
        }
        else{
            watchdogDaoList = userDao.getWatchdogs();
        }

        watchdogDaoList.add(new WatchdogDao(watchdogDto));
        userDao.setWatchdogs(watchdogDaoList);
        userRepository.save(userDao);
        log.info("watchdog: " + Arrays.toString(userDao.getWatchdogs().toArray()) + "was added");
        return getWatchdogsForAuthorizedUser(username);

    }

    public ResponseEntity<WatchdogDto[]> getWatchdogsForAuthorizedUser(String username) {
        UserDao userDao = userRepository.findByUsername(username);
        WatchdogDao [] watchdogDaos= userDao.getWatchdogs().stream().toArray(WatchdogDao[]::new);
        WatchdogDto[] watchdogDtos = new WatchdogDto[watchdogDaos.length];
        for (int i = 0; i < watchdogDaos.length; i++) {
            watchdogDtos[i]=new WatchdogDto(watchdogDaos[i]);
        }

        return ResponseEntity.status(200)
                //.header(SESSION_HEADER, "12345")
                .body(watchdogDtos);

    }
}
