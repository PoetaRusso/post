package forum.dao;

import forum.model.Message;
import java.time.LocalDate;

public interface Forum {
    boolean addPost(Message post);

    boolean removePost(int postId);

    boolean updatePost(int postId, String content);

    Message getPostById(int postId);

    Message[] getPostsByAuthor(String author);

    Message[] getPostsByAuthorAndDate(String author, LocalDate dateFrom, LocalDate dateTo);

    int size();
}