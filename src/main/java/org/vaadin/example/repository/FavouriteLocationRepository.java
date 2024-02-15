package org.vaadin.example.repository;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.vaadin.example.entity.FavouriteLocation;

@Stateless
@Transactional
public class FavouriteLocationRepository {

    @PersistenceContext
    EntityManager em;

    public FavouriteLocation save(FavouriteLocation location) {
        em.persist(location);
        return location;
    }

    public void removeByUserIdAndLocationId(Integer userId, Integer locationId) {
        em.createQuery("DELETE FROM FavouriteLocation f WHERE f.user.id = :userId AND f.locationId = :locationId")
                .setParameter("userId", userId)
                .setParameter("locationId", locationId)
                .executeUpdate();
    }

}