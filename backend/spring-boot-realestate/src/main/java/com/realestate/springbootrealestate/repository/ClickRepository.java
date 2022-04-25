package com.realestate.springbootrealestate.repository;

import com.realestate.springbootrealestate.model.Click;
import com.realestate.springbootrealestate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClickRepository extends JpaRepository<Click, Long> {

    List<Click> findByUser(User id);
}
