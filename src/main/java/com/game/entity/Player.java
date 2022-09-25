package com.game.entity;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name="player")
public class Player {
    @Column(name="id")
    private @Id Long id;

    @Column(name="name")
    private String name;

    @Column(name="title")
    private String title;

    @Enumerated(STRING)
    @Column(name="race")
    private Race race;

    @Enumerated(STRING)
    @Column(name="profession")

    private Profession profession;
    @Column(name="birthday")

    private Date birthday;
    @Column(name="banned")
    private Boolean banned;

    @Column(name="level")
    private Integer level;

    @Column(name="experience")
    private Integer experience;

    @Column(name="untilNextLevel")
    private int untilNextLevel;

    public Player(Long id, String name, String title, Race race, Profession profession, Long birthday, Boolean banned, int level, int experience,
                  int untilNextLevel) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.birthday = birthday==null ? null : new Date(birthday);
        this.banned = banned==null ? false : banned;
        this.level = level;
        this.experience = experience;
        this.untilNextLevel = untilNextLevel;
    }

    public Player(){

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Race getRace() {
        return race;
    }

    public Profession getProfession() {
        return profession;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getExperience() {
        return experience;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setUntilNextLevel(int untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Boolean isValid(){
        if(name !=null && name.length()<=12
                && title!=null && title.length()<=30
                && experience != null && experience>=0 && experience<=10_000_000
                && birthday.getYear() + 1900 >=2000 && birthday.getYear()<=3000
                && race != null
                && profession != null
        ) {
            return true;
        }
       else return false;
    }

    public void countAndSetLevel() {
        int level=(int)((Math.sqrt(2500+200*experience)-50)/100);
        this.setLevel(level);
    }

    public void countAndSetUntilNextLevel() {
        int untilNextLevel = 50*(level+1)*(level+2)-experience;
        this.setUntilNextLevel(untilNextLevel);
    }
}

