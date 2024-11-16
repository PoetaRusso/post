package forum.test;

import forum.dao.Forum;
import forum.dao.ForumImpl;
import forum.model.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class ForumTest {
    private final LocalDateTime now = LocalDateTime.now();
    private final int capacity = 6;
    private final Comparator<Message> comparator = (m1, m2) -> {
        int res = Integer.compare(m1.getMessageId(), m2.getMessageId());
        return res != 0 ? res : Integer.compare(m1.getMessageId(), m2.getMessageId());
    };
    private Forum forum;
    private Message[] messages;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        forum = new ForumImpl(capacity);
        messages = new Message[capacity];
        messages[0] = new Message(1, "Title1", "Author1", "Content1", now.minusDays(7));
        messages[1] = new Message(2, "Title2", "Author1", "Content2", LocalDate.now().minusDays(5).atStartOfDay());
        messages[2] = new Message(3, "Title3", "Author1", "Content3", LocalDate.now().minusDays(5).atStartOfDay());
        messages[3] = new Message(4, "Title4", "Author2", "Content4", LocalDate.now().minusDays(5).atStartOfDay());
        messages[4] = new Message(5, "Title5", "Author2", "Content5", LocalDate.now().minusDays(5).atStartOfDay());
        messages[5] = new Message(6, "Title6", "Author1", "Content6", now.minusDays(2));
        for (int i = 0; i < messages.length - 1; i++) {
            forum.addPost(messages[i]);
        }
    }

    @org.junit.jupiter.api.Test
    void testAddPost() {
        assertFalse(forum.addPost(null));
        assertFalse(forum.addPost(messages[2]));
        assertTrue(forum.addPost(messages[5]));
        assertEquals(capacity, forum.size());
        assertFalse(forum.addPost(new Message(10, "Title7", "Author3", "Content7", now.minusDays(2))));
    }

    @org.junit.jupiter.api.Test
    void testRemovePost() {
        assertFalse(forum.removePost(10));
        assertTrue(forum.removePost(1));
        assertEquals(4, forum.size());
    }

    @org.junit.jupiter.api.Test
    void testUpdatePost() {
        assertTrue(forum.updatePost(2, "newContent"));
        assertEquals("newContent", forum.getPostById(2).getContent());
    }

    @org.junit.jupiter.api.Test
    void testGetPostById() {
        assertEquals(messages[0], forum.getPostById(1));
        assertNull(forum.getPostById(10));
    }

    @org.junit.jupiter.api.Test
    void testGetPostsByAuthor() {
        Message[] actual = forum.getPostsByAuthor("Author2");
        Arrays.sort(actual, comparator);
        Message[] expected = {messages[3], messages[4]};
        assertArrayEquals(expected, actual);
    }

//    @org.junit.jupiter.api.Test
//    void testGetPostsByAuthorAndDate() {
//        LocalDate localDate = LocalDate.now();
//        Message[] actual = forum.getPostsByAuthorAndDate("Author1", localDate.minusDays(5), localDate.minusDays(2));
//        Arrays.sort(actual, comparator);
//        Message[] expected = {messages[1], messages[2], messages[5]};
//        assertArrayEquals(expected, actual);
//    }

    @org.junit.jupiter.api.Test
    void testSize() {
        assertEquals(capacity - 1, forum.size());
    }
}