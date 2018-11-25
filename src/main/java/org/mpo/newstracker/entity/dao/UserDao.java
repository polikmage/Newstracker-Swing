package org.mpo.newstracker.entity.dao;


import org.mpo.newstracker.entity.dto.UserDto;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class UserDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    private String username;
    private String email;
    private String password;

    //private String role;


    // Joins tables throught id columns
    @OneToMany(cascade=CascadeType.ALL)
    //@Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private List<WatchdogDao> watchdogs;



    public UserDao() {
    }

    public UserDao(UserDto userDto) {
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
*/
    public List<WatchdogDao> getWatchdogs() {
        return watchdogs;
    }

    public void setWatchdogs(List<WatchdogDao> watchdogs) {
        this.watchdogs = watchdogs;
    }

   /* public void setWatchdogs(WatchdogDao watchdogInfo) {
          if(this.watchdogs==null || this.watchdogs.isEmpty()){
            this.watchdogs = new ArrayList<>();
            this.watchdogs.add(watchdogInfo);
          }
          else{
              this.watchdogs.add(watchdogInfo);
          }
    }*/




}
