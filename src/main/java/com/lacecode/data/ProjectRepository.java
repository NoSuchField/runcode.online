package com.lacecode.data;

import com.lacecode.model.entity.Project;
import com.lacecode.model.entity.Team;
import com.lacecode.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ProjectRepository extends CrudRepository<Project,Integer> {


    ArrayList<Project> findAllByTeam(Team team);

    ArrayList<Project> findOneByMaintainer(User user);

}
