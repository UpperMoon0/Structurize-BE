package org.nstut.luvit.service;

import org.nstut.luvit.entity.Favourite;
import org.nstut.luvit.entity.FavouriteId;
import org.nstut.luvit.repository.IFavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService {
    private final IFavouriteRepository favouriteRepository;

    @Autowired
    public FavouriteService(IFavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    public Favourite createFavourite(Favourite favourite) {
        return favouriteRepository.save(favourite);
    }

    public Optional<Favourite> getFavourite(FavouriteId id) {
        return favouriteRepository.findById(id);
    }

    public void deleteFavourite(FavouriteId id) {
        favouriteRepository.deleteById(id);
    }

    public List<Favourite> getAllFavourites() {
        return favouriteRepository.findAll();
    }
}
