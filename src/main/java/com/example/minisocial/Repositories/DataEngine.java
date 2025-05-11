package com.example.minisocial.Repositories;

import com.example.minisocial.Entities.*;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class DataEngine {
    @PersistenceContext(unitName = "MiniSocialPU")
    private EntityManager em;

    @PostConstruct
    @Transactional
    public void initDummyData() {

        User alice = createUser("alice", "1234", "alice@example.com");
        User bob = createUser("bob", "1234", "bob@example.com");
        User carol = createUser("carol", "1234", "carol@example.com");
        User dave = createUser("dave", "1234", "dave@example.com");

        alice.addFriend(carol);
        carol.addFriend(alice);

        alice.addFriend(bob);
        bob.addFriend(alice);

        bob.addFriend(carol);
        carol.addFriend(bob);

        carol.addFriend(dave);
        dave.addFriend(carol);


        em.persist(alice);
        em.persist(bob);
        em.persist(carol);
        em.persist(dave);

        Group group = new Group();
        group.setName("Nature Lovers");
        group.setDescription("A group for people who love hiking and nature photography");
        group.setPrivate(false);
        group.setCreator(alice);

        List<User> members = new ArrayList<>();
        members.add(bob);
        members.add(carol);
        group.setMembers(members);
        group.setAdmins(List.of(alice));

        em.persist(group);
        Post post1 = new Post();
        post1.setAuthor(alice);
        post1.setContent("Enjoying the sunny weather today!");
        post1.setMediaURL("https://example.com/media/sunny.jpg");
        post1.setLikedBy(List.of(bob, carol));
        post1.setComments(new ArrayList<>());

        Comment comment1 = new Comment();
        comment1.setAuthor(bob);
        comment1.setContent("Looks amazing!");
        comment1.setPost(post1);
        post1.addComment(comment1);

        Comment comment2 = new Comment();
        comment2.setAuthor(carol);
        comment2.setContent("Wish I was there.");
        comment2.setPost(post1);
        post1.addComment(comment2);

        em.persist(post1);
        em.persist(comment1);
        em.persist(comment2);

// --- Create a group post ---
        GroupPost groupPost1 = new GroupPost();
        groupPost1.setAuthor(bob);
        groupPost1.setGroup(group);
        groupPost1.setContent("Check out this trail I found!");
        groupPost1.setMediaURL("https://example.com/media/trail.jpg");
        groupPost1.setLikedBy(List.of(alice));
        groupPost1.setComments(new ArrayList<>());

        Comment groupComment1 = new Comment();
        groupComment1.setAuthor(alice);
        groupComment1.setContent("Nice find!");
        groupComment1.setPost(groupPost1);
        groupPost1.addComment(groupComment1);

        em.persist(groupPost1);
        em.persist(groupComment1);
    }
    public void save(User user) {
        em.persist(user);
    }
    public User createUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
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
    public void createGroup(Group group){
        em.persist(group);
    }

   public Group findGroupByName(String name){
        TypedQuery<Group> query = em.createQuery(
                "SELECT g FROM Group g WHERE g.name = :name", Group.class);
        return query.setParameter("name", name)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
    }

    public void updateGroup(Group group){
        em.merge(group);
    }

    public void saveGroupPost(GroupPost groupPost) {
        em.persist(groupPost);
    }
    public List<Group> findAllGroups() {
        return em.createQuery("SELECT g FROM Group g", Group.class).getResultList();
    }
    public void createPost(Post post) {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", post.getAuthor());
        post.setAuthor(
                query.getResultStream()
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("User not found"))
        );
        em.persist(post);
    }
    public List<Post> getPostsByUser(String username) {
        return em.createQuery("SELECT p FROM Post p WHERE p.author.username = :username", Post.class)
                .setParameter("username", username)
                .getResultList();
    }

    public Post findPostById(Long postId) {
        return em.find(Post.class, postId);
    }

    public void updatePost(Post post) {
        em.merge(post);
    }
}
