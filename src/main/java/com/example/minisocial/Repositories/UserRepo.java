package com.example.minisocial.Repositories;

import com.example.minisocial.Entities.FriendRequest;
import com.example.minisocial.Entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class UserRepo {

    @PersistenceContext(unitName = "MiniSocialPU")
    private EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findUserByUsername(String username) {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class);
        return query.setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }


    public boolean emailExists(String email) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
        return query.setParameter("email", email)
                .getSingleResult() > 0;
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public List<FriendRequest> getFriendRequests(String username) {
        User user = findUserByUsername(username);
        user = em.merge(user); // Ensure it's managed
        return user.getFriendRequests();
    }

    public void sendFriendRequest(FriendRequest friendRequest) {
        em.persist(friendRequest);
        User receiver = findUserByUsername(friendRequest.getReceiver());
        receiver.addFriendRequest(friendRequest);
        em.merge(receiver);
    }

    public Boolean isFriendRequestSent(String username1, String username2) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(fr) FROM FriendRequest fr WHERE fr.Sender = :sender AND fr.receiver = :receiver",
                Long.class);
        return query.setParameter("sender", username1)
                .setParameter("receiver", username2)
                .getSingleResult() > 0;
    }

    public Boolean isFriend(String username1, String username2) {
        User user1 = findUserByUsername(username1);
        return user1.getFriends().stream()
                .anyMatch(friend -> friend.getUsername().equals(username2));
    }

    public void addFriend(String senderUsername, String receiverUsername) {
        User sender = findUserByUsername(senderUsername);
        User receiver = findUserByUsername(receiverUsername);

        sender.addFriend(receiver);
        receiver.addFriend(sender);
        em.merge(sender);
        em.merge(receiver);
    }
    public void updateFriendRequestStatus(String sender, String receiver, FriendRequest.RequestStatus status) {
        TypedQuery<FriendRequest> query = em.createQuery(
                "SELECT fr FROM FriendRequest fr WHERE fr.Sender = :sender AND fr.receiver = :receiver", FriendRequest.class);
        query.setParameter("sender", sender);
        query.setParameter("receiver", receiver);

        query.getResultStream().findFirst().ifPresent(fr -> {
            fr.setStatus(status);
            em.merge(fr);
        });
    }

}
