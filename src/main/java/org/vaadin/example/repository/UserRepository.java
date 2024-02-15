package org.vaadin.example.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.vaadin.example.entity.User;

@Stateless
@Transactional
public class UserRepository {
    @PersistenceContext
    EntityManager em;

    public User findByUsername(String username) {
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findById(Integer id) {
        User user = em.find(User.class, id);
        if (user == null) throw new NotFoundException("User not found with ID: " + id);
        return user;
    }

    public User save(User user) {
        em.persist(user);
        return user;
    }
}
