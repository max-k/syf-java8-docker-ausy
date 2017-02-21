package com.ausy.syf.rps.ws.repository;

import com.ausy.syf.rps.ws.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "user", path = "users")
public interface UserRepository extends JpaRepository<User, String> {
}
