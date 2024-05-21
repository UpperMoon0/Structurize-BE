package org.nstut.luvit.repository;

import org.nstut.luvit.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGenderRepository extends JpaRepository<Gender, Byte> {
}
