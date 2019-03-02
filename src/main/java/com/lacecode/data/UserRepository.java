package com.lacecode.data;

import com.lacecode.model.entity.Project;
import com.lacecode.model.entity.Team;
import com.lacecode.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    ArrayList<User> findAllByTeam(Team team);

    ArrayList<User> findByProject(Project project);

}
