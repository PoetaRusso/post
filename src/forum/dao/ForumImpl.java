package forum.dao;

import forum.model.Message;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class ForumImpl implements Forum {
    private Message[] posts;
    private int size;

    /**
     * Comparator to sort messages by date and then by messageId
     */
    private Comparator<Message> comparator = (m1, m2) -> {
        int res = m1.getDate().compareTo(m2.getDate());
        return res != 0 ? res : Integer.compare(m1.getMessageId(), m2.getMessageId());
    };

    public ForumImpl(int capacity) {
        posts = new Message[capacity];
        size = 0;
    }

    @Override
    public boolean addPost(Message post) {
        if (post == null || size == posts.length || getPostById(post.getMessageId()) != null) {
            return false;
        }
        int index = Arrays.binarySearch(posts, 0, size, post, comparator);
        /**
         * Find insertion point*/
        index = index >= 0 ? index : -index - 1;
        System.arraycopy(posts, index, posts, index + 1, size - index);
        posts[index] = post;
        size++;
        return true;
    }

    @Override
    public boolean removePost(int postId) {
        for (int i = 0; i < size; i++) {
            if (posts[i].getMessageId() == postId) {
                System.arraycopy(posts, i + 1, posts, i, size - 1 - i);
                posts[--size] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updatePost(int postId, String content) {
        Message post = getPostById(postId);
        if (post == null) {
            return false;
        }
        post.setContent(content);
        post.setDate(LocalDate.now().atStartOfDay());
        return true;
    }

    @Override
    public Message getPostById(int postId) {
        for (int i = 0; i < size; i++) {
            if (posts[i].getMessageId() == postId) {
                return posts[i];
            }
        }
        return null;
    }

    @Override
    public Message[] getPostsByAuthor(String author) {
        return findPostsByPredicate(p -> p.getAuthor().equals(author));
    }

    @Override
    public Message[] getPostsByAuthorAndDate(String author, LocalDate dateFrom, LocalDate dateTo) {
        return findPostsByPredicate(p -> p.getAuthor().equals(author) &&
                (p.getDate().toLocalDate().isEqual(dateFrom) ||
                        p.getDate().toLocalDate().isEqual(dateTo) ||
                        (p.getDate().toLocalDate().isAfter(dateFrom) &&
                                p.getDate().toLocalDate().isBefore(dateTo))));
    }

    @Override
    public int size() {
        return size;
    }

    private Message[] findPostsByPredicate(Predicate<Message> predicate) {
        Message[] result = new Message[size];
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (predicate.test(posts[i])) {
                result[j++] = posts[i];
            }
        }
        return Arrays.copyOf(result, j);
    }
}
