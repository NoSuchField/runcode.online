package com.lixinyu.cooperativecoding.data;

import com.lixinyu.cooperativecoding.model.entity.Project;
import com.lixinyu.cooperativecoding.model.entity.Team;
import com.lixinyu.cooperativecoding.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    ArrayList<User> findAllByTeam(Team team);

    ArrayList<User> findByProject(Project project);

}
