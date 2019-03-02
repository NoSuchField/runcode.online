package com.lacecode.data;

import com.lacecode.model.entity.Code;
import com.lacecode.model.entity.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CodeRepository extends CrudRepository<Code,Integer> {

    public ArrayList<Code> findAllByProject(Project project);

}
