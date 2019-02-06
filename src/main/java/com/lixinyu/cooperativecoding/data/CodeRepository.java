package com.lixinyu.cooperativecoding.data;

import com.lixinyu.cooperativecoding.model.entity.Code;
import com.lixinyu.cooperativecoding.model.entity.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CodeRepository extends CrudRepository<Code,Integer> {

    public ArrayList<Code> findAllByProject(Project project);

}
