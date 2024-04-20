package org.nstut.luvit.favourite;

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
