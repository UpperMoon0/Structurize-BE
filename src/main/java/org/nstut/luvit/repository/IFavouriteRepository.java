package org.nstut.luvit.repository;

import org.nstut.luvit.entity.Favourite;
import org.nstut.luvit.entity.FavouriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFavouriteRepository extends JpaRepository<Favourite, FavouriteId> {
}
