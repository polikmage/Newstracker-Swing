package org.mpo.newstracker.controller;

import org.mpo.newstracker.entity.NewsEntity;
import org.mpo.newstracker.entity.dto.CommonResponseDto;
import org.mpo.newstracker.entity.dto.UserDto;
import org.mpo.newstracker.entity.dto.WatchdogDto;
import org.mpo.newstracker.exception.*;
import org.mpo.newstracker.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1")
public class ApiRestController {

    private static final Logger log = LoggerFactory.getLogger(ApiRestController.class);

    @Value("${news-admin.max-items}")
    private Integer defaultMaxItems;

    @Autowired
    private ApiNewsService apiNewsService;
    @Autowired
    private WebNewsService webNewsService;
    @Autowired
    private WatchdogService watchdogService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/api-news/articles")
    public ResponseEntity<NewsEntity> getApiArticles(@RequestParam(name = "maxItems", required = false) Integer maxItems,
                                     @RequestParam(name = "keyWords", required = true) String keyWords,
                                     @RequestParam(name = "lang", required = true) String language,
                                     @RequestParam(name = "page", required = false) Integer page){
        if (maxItems == null) {
            maxItems = defaultMaxItems;
        }
        if (page == null) {
            page = 0;
        }
        //System.out.println("Accepted headers: " + xSessionId);
        return apiNewsService.getApiArticles(maxItems, keyWords, language,page);
    }



    @GetMapping("/user/web-news/articles")
    public ResponseEntity<NewsEntity> getWebNewsArticles(@RequestParam(name = "pageSize", required = false) Integer pageSize,
                                                         @RequestParam(name = "keyWords", required = true) String keyWords,
                                                         @RequestParam(name = "country", required = true) String country,
                                                         @RequestParam(name = "page", required = false) Integer page,
                                                         @RequestParam(name = "translate", required = false) Boolean translate)
            throws ExecutionException, InterruptedException, BackendException,
            CountryNotFoundException, NoArticlesFoundException, IOException, TranslatorException {
        if (pageSize == null) {
            pageSize = defaultMaxItems;
        }
        if (page == null) {
            page = 0;
        }
        if(translate==null){
            translate=false;
        }
        //System.out.println("Accepted headers " + xSessionId);
        return webNewsService.getWebNewsArticles(pageSize, keyWords, country, page,translate);
    }

    @PostMapping("/user/watchdog")
    public ResponseEntity<WatchdogDto[]> setWatchdog(@RequestBody WatchdogDto watchdogDto, Principal principal){
       //log.info("first method: " + ((UserDetails) principal).getUsername());
        final UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
        log.info("logged in username: "+currentUser.getUsername());
        return watchdogService.setWatchdog(watchdogDto,currentUser.getUsername());
    }

/*    @GetMapping("/user/{username}/watchdog")
    public ResponseEntity<> setWatchdog(@PathVariable(name = "username")  String username,  Principal principal){
        //log.info("first method: " + ((UserDetails) principal).getUsername());
        final UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
        log.info("logged in username: "+currentUser.getUsername());
        return watchdogService.getWatchdog(currentUser.getUsername());
    }*/
    @GetMapping("/user/watchdog")
    public ResponseEntity<WatchdogDto[]> setWatchdog(Principal principal){
        //log.info("first method: " + ((UserDetails) principal).getUsername());
        final UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
        log.info("logged in username: "+currentUser.getUsername());
        return watchdogService.getWatchdogs(currentUser.getUsername());
    }

    @DeleteMapping("/user/watchdog/{id}")
    public ResponseEntity<Integer> deleteWatchdog(@PathVariable(name="id") int id, Principal principal) throws NoWatchdogFoundException {
        //log.info("first method: " + ((UserDetails) principal).getUsername());
        final UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
        log.info("logged in username: "+currentUser.getUsername());
        return watchdogService.deleteWatchdog(id,currentUser.getUsername());
    }

  /*  @GetMapping("/admin/user")
    public ResponseEntity<> getUsers(){
        return userService.getUsers();
    }
*/

    @PostMapping("/sign_up")
    public ResponseEntity<CommonResponseDto> registerUser(@RequestBody UserDto userDto){
        return userService.registerUser(userDto);
    }


}
