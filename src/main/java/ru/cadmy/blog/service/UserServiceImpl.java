package ru.cadmy.blog.service;

import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cadmy.blog.model.*;
import ru.cadmy.blog.model.User;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.*;

@Service("userServiceImpl")
public class UserServiceImpl extends ModelService implements UserService, UserDetailsService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public UserAdditionResults addUser(User user) {
        if ( doesUsernameExist(user.getUsername()) ) {
            return UserAdditionResults.USERNAME_EXISTS;
        } else if ( doesEmailExist(user.getEmail()) ) {
            return UserAdditionResults.EMAIL_EXISTS;
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            em.persist(user);
            return UserAdditionResults.SUCCESS;
        }
    }

    @Override
    @Transactional
    public List<User> getUserList() {
        CriteriaQuery<User> c = em.getCriteriaBuilder().createQuery(User.class);
        c.from(User.class);
        return em.createQuery(c).getResultList();
    }

    @Override
    @Transactional
    public void removeUser(Long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        return getUserByUsername(getCurrentUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
        Root<User> userRequest = criteriaQuery.from(User.class);

        Expression<String> exp = userRequest.get("id");
        Predicate predicate = exp.in(userId);

        criteriaQuery.where(predicate);
        return em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public User getUserByUsername(String username) {
        CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
        Root<User> userRequest = criteriaQuery.from(User.class);

        Expression<String> exp = userRequest.get("username");
        Predicate predicate = exp.in(username);

        criteriaQuery.where(predicate);
        try {
            return em.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return new User();
        }
    }

    @Override
    public boolean doesUsernameExist(String username) {
        CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
        Root<User> userRequest = criteriaQuery.from(User.class);

        Expression<String> exp = userRequest.get("username");
        Predicate predicate = exp.in(username);

        criteriaQuery.where(predicate);
        try {
            return em.createQuery(criteriaQuery).getSingleResult() != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    private boolean doesEmailExist(String email) {
        CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
        Root<User> userRequest = criteriaQuery.from(User.class);

        Expression<String> exp = userRequest.get("email");
        Predicate predicate = exp.in(email);

        criteriaQuery.where(predicate);
        try {
            return em.createQuery(criteriaQuery).getSingleResult() != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user.getUsername() == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getState().equals(State.ACTIVE), true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }
}

