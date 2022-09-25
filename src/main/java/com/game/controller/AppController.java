package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AppController {
    public final PlayerRepository repository;

    public AppController(PlayerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/rest/players")
    List<Player> getPlayers(
            @RequestParam(value="name", required=false) String name,
            @RequestParam(value="title", required=false) String title,
            @RequestParam(value="race", required=false) Race race,
            @RequestParam(value="profession", required=false) Profession profession,
            @RequestParam(value="after", required=false) Long after,
            @RequestParam(value="before", required=false) Long before,
            @RequestParam(value="banned", required=false) Boolean banned,
            @RequestParam(value="minExperience", required=false) Integer minExperience,
            @RequestParam(value="maxExperience", required=false) Integer maxExperience,
            @RequestParam(value="minLevel", required=false) Integer minLevel,
            @RequestParam(value="maxLevel", required=false) Integer maxLevel,
            PlayerOrder order,
            @RequestParam(value="pageNumber", required=false, defaultValue="0") Integer pageNumber,
            @RequestParam(value="pageSize", required=false, defaultValue="3") Integer pageSize
    ) {
        Pageable pageNumberAndSize = PageRequest.of(pageNumber, pageSize,
                Sort.by(order == null ? PlayerOrder.ID.getFieldName() : order.getFieldName()));
        return repository.findAllByParameters(name,
                title,
                race == null ? null : race.name(),
                profession == null ? null : profession.name(),
                after == null ? null : new Date(after),
                before == null ? null : new Date(before),
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel,
                pageNumberAndSize);
    }

    @GetMapping("/rest/players/count")
    int getPlayersCount(
            @RequestParam(value="name", required=false) String name,
            @RequestParam(value="title", required=false) String title,
            @RequestParam(value="race", required=false) Race race,
            @RequestParam(value="profession", required=false) Profession profession,
            @RequestParam(value="after", required=false) Long after,
            @RequestParam(value="before", required=false) Long before,
            @RequestParam(value="banned", required=false) Boolean banned,
            @RequestParam(value="minExperience", required=false) Integer minExperience,
            @RequestParam(value="maxExperience", required=false) Integer maxExperience,
            @RequestParam(value="minLevel", required=false) Integer minLevel,
            @RequestParam(value="maxLevel", required=false) Integer maxLevel) {
        return repository.countAllByParameters(
                name,
                title,
                race == null ? null : race.name(),
                profession == null ? null : profession.name(),
                after == null ? null : new Date(after),
                before == null ? null : new Date(before),
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel);
    }

    @PostMapping("/rest/players")
    @ResponseBody
    ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        if (player.isValid()){
            Long id = repository.findMaxId()+1;
            player.setId(id);
            player.countAndSetLevel();
            player.countAndSetUntilNextLevel();
            return new ResponseEntity<>(repository.save(player), HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/rest/players/{id}")
    ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        if(id>0) {
           Optional<Player> player = repository.findById(id);
           if (player.isPresent()) {
               return new ResponseEntity<>(player.get(),HttpStatus.OK);
           }
           else return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/rest/players/{id}")
    ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player requestPlayer) {
        if(id>0) {
            Optional<Player> player = repository.findById(id);
            if (player.isPresent()) {
                Player playerToUpdate = player.get();
                if (requestPlayer.getName() != null) {
                    playerToUpdate.setName(requestPlayer.getName());
                }
                if (requestPlayer.getTitle() != null) {
                    playerToUpdate.setTitle(requestPlayer.getTitle());
                }
                if (requestPlayer.getRace() != null) {
                    playerToUpdate.setRace(requestPlayer.getRace());
                }
                if (requestPlayer.getProfession() != null) {
                    playerToUpdate.setProfession(requestPlayer.getProfession());
                }
                if (requestPlayer.getBanned() != null) {
                    playerToUpdate.setBanned(requestPlayer.getBanned());
                }
                if (requestPlayer.getExperience() != null) {
                    playerToUpdate.setExperience(requestPlayer.getExperience());
                }
                if (requestPlayer.getBirthday() != null) {
                    playerToUpdate.setBirthday(requestPlayer.getBirthday());
                }
                if (playerToUpdate.isValid()){
                    playerToUpdate.countAndSetLevel();
                    playerToUpdate.countAndSetUntilNextLevel();
                    return new ResponseEntity<>(repository.save(playerToUpdate),HttpStatus.OK);
                }
                else return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            else return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/rest/players/{id}")
    ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        if(id>0) {
            Optional<Player> player = repository.findById(id);
            if (player.isPresent()) {
                repository.delete(player.get());
                return new ResponseEntity(HttpStatus.OK);
            }
            else return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}

