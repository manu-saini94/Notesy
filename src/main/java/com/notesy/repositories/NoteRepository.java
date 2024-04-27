package com.notesy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.notesy.entities.Note;
import com.notesy.entities.User;

public interface NoteRepository extends JpaRepository<Note, Integer> {

	public List<Note> findAllByUser(User user);

	@Query("select n from Note n where n.id=:x and n.user.id=:y")
	public Note findByIdAndUser(@Param("x") int id, @Param("y") int userId);

	@Query("select n from Note n where n.isTrashed=true and n.user.id=:y")
	public List<Note> findAllByUserAndIsTrashed(@Param("y") int userId);

	@Query(value = "DELETE FROM note WHERE id = :id AND user_id = :userId", nativeQuery = true)
	public int deleteNoteByIdAndUserId(@Param("noteId") int id, @Param("userId") int userId);

}
