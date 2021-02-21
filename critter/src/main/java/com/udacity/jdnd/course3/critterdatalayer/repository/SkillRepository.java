package com.udacity.jdnd.course3.critterdatalayer.repository;

import com.udacity.jdnd.course3.critterdatalayer.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query("SELECT s FROM Skill s WHERE s.skill = :skill")
    public Skill findSkill(String skill);
}
