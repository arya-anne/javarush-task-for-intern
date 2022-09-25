package com.game.repository;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {

    @Query(value="select * from player p where (UPPER(p.name) like concat('%',upper(:name),'%') or :name is null) "
            + "and (UPPER(p.title) like concat('%',upper(:title),'%') or :title is null) "
            + "and (p.race=:race or :race is null) and (p.profession=:profession or :profession is null) and (p.birthday>=:after or :after is null) "
            + "and (p.birthday<=:before or :before is null) and (p.banned=:banned or :banned is null) and (p.experience>=:minExperience or "
            + ":minExperience is null) and (p.experience<= :maxExperience or :maxExperience is null) and (p.level>=:minLevel or :minLevel is null) "
            + "and (p.level <=:maxLevel or :maxLevel is null)",
    countQuery="select count(*) from player p where (UPPER(p.name) like concat('%',upper(:name),'%') or :name is null) "
            + "and (UPPER(p.title) like concat('%',upper(:title),'%') or :title is null) "
            + "and (p.race=:race or :race is null) and (p.profession=:profession or :profession is null) and (p.birthday>=:after or :after is null) "
            + "and (p.birthday<=:before or :before is null) and (p.banned=:banned or :banned is null) and (p.experience>=:minExperience or "
            + ":minExperience is null) and (p.experience<= :maxExperience or :maxExperience is null) and (p.level>=:minLevel or :minLevel is null) "
            + "and (p.level <=:maxLevel or :maxLevel is null)", nativeQuery = true)
    List<Player> findAllByParameters(
            @Param("name") String name,
            @Param("title") String title,
            @Param("race") String race,
            @Param("profession") String profession,
            @Param("after") Date after,
            @Param("before") Date before,
            @Param("banned") Boolean banned,
            @Param("minExperience") Integer minExperience,
            @Param("maxExperience") Integer maxExperience,
            @Param("minLevel") Integer minLevel,
            @Param("maxLevel") Integer maxLevel,
            Pageable pageNumberAndSize);

    @Query(value="select count(*) from player p where (UPPER(p.name) like concat('%',upper(:name),'%') or :name is null) "
                    + "and (UPPER(p.title) like concat('%',upper(:title),'%') or :title is null) "
                    + "and (p.race=:race or :race is null) and (p.profession=:profession or :profession is null) and (p.birthday>=:after or :after is null) "
                    + "and (p.birthday<=:before or :before is null) and (p.banned=:banned or :banned is null) and (p.experience>=:minExperience or "
                    + ":minExperience is null) and (p.experience<= :maxExperience or :maxExperience is null) and (p.level>=:minLevel or :minLevel is null) "
                    + "and (p.level <=:maxLevel or :maxLevel is null)", nativeQuery = true)
    Integer countAllByParameters(
            @Param("name") String name,
            @Param("title") String title,
            @Param("race") String race,
            @Param("profession") String profession,
            @Param("after") Date after,
            @Param("before") Date before,
            @Param("banned") Boolean banned,
            @Param("minExperience") Integer minExperience,
            @Param("maxExperience") Integer maxExperience,
            @Param("minLevel") Integer minLevel,
            @Param("maxLevel") Integer maxLevel);
}
