package com.notesy.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.notesy.dto.NoteDto;
import com.notesy.entities.Note;
import com.notesy.entities.User;
import com.notesy.exceptions.NoteNotFoundException;
import com.notesy.exceptions.NoteNotPersistedException;
import com.notesy.exceptions.NoteNotUpdatedException;
import com.notesy.repositories.NoteRepository;
import com.notesy.repositories.UserRepository;
import com.notesy.utility.NoteUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private UserRepository userRepository;

	public NoteDto createNewNote(NoteDto noteDto, User user) {
		try {
			Note note = new Note();
			Note updatedNote = setNoteFromNoteDto(noteDto, note);
			updatedNote.setUser(user);
			user.getNoteList().add(updatedNote);
			userRepository.save(user);
			noteRepository.save(updatedNote);
			log.info(NoteUtils.NOTE_PERSIST_SUCCESS, updatedNote);
			return setNoteDtoFromNote(updatedNote);
		} catch (Exception ex) {
			log.error(NoteUtils.NOTE_PERSIST_ERROR, ex.getMessage());
			throw new NoteNotPersistedException();
		}
	}

	public NoteDto updateExistingNote(NoteDto noteDto, User user) {
		Note note = noteRepository.findByIdAndUser(noteDto.getId(), user.getId());
		if (note != null) {
			try {
				Note updatedNote = setNoteFromNoteDto(noteDto, note);
				updatedNote.setUser(user);
				user.getNoteList().add(updatedNote);
				userRepository.save(user);
				noteRepository.save(updatedNote);
				log.info(NoteUtils.NOTE_UPDATE_SUCCESS, updatedNote);
				return setNoteDtoFromNote(updatedNote);
			} catch (Exception ex) {
				log.error(NoteUtils.NOTE_UPDATE_ERROR, ex.getMessage());
				throw new NoteNotUpdatedException();
			}
		} else {
			log.error(NoteUtils.NOTE_NOT_FOUND, noteDto.getId());
			throw new NoteNotFoundException();
		}
	}

	private NoteDto setNoteDtoFromNote(Note note) {
		NoteDto noteDto = new NoteDto();
		noteDto.setId(note.getId());
		noteDto.setTitle(note.getTitle());
		noteDto.setColor(note.getColor());
		noteDto.setArchived(note.isArchived());
		noteDto.setUpdatedAt(note.getUpdatedAt());
		noteDto.setContent(note.getContent());
		noteDto.setImages(note.getImages());
		noteDto.setCollaboratorList(note.getCollaboratorList());
		noteDto.setCreatedAt(note.getCreatedAt());
		noteDto.setLabelList(note.getLabelList());
		noteDto.setPinned(note.isPinned());
		noteDto.setReminder(note.getReminder());
		noteDto.setTrashed(note.isTrashed());
		return noteDto;
	}

	private Note setNoteFromNoteDto(NoteDto noteDto, Note note) {
		note.setTitle(noteDto.getTitle());
		note.setColor(noteDto.getColor());
		note.setArchived(noteDto.isArchived());
		note.setUpdatedAt(noteDto.getUpdatedAt());
		note.setContent(noteDto.getContent());
		note.setImages(noteDto.getImages());
		note.setCollaboratorList(noteDto.getCollaboratorList());
		note.setCreatedAt(noteDto.getCreatedAt());
		note.setLabelList(noteDto.getLabelList());
		note.setPinned(noteDto.isPinned());
		note.setReminder(noteDto.getReminder());
		note.setTrashed(noteDto.isTrashed());
		note.setUser(noteDto.getUser());
		return note;
	}

	public List<NoteDto> getAllNotesByUser(User user) {
		try {
			List<Note> noteList = noteRepository.findAllByUser(user);
			List<NoteDto> noteDtoList = noteList.stream().map(note -> setNoteDtoFromNote(note))
					.collect(Collectors.toList());
			return noteDtoList;
		} catch (Exception exp) {
			log.error(NoteUtils.ERROR_FETCHING_NOTES_FOR_USER, user);
			throw new NoteNotFoundException();
		}
	}

	public List<NoteDto> getAllNotesByUserAndLabelId(User user, int labelId) {
		try {
			List<Note> noteList = noteRepository.findAllByUser(user);
			List<NoteDto> noteDtoList = noteList.stream()
					.filter(note -> note.getLabelList().stream().anyMatch(label -> label.getId() == labelId))
					.map(note -> setNoteDtoFromNote(note)).collect(Collectors.toList());
			return noteDtoList;
		} catch (Exception exp) {
			log.error(NoteUtils.ERROR_FETCHING_NOTES_FOR_USER, user);
			throw new NoteNotFoundException();
		}
	}

	public List<NoteDto> getAllNotesByIsTrashed(User user) {
		try {
			List<Note> noteList = noteRepository.findAllByUserAndIsTrashed(user.getId());
			List<NoteDto> noteDtoList = noteList.stream().map(note -> setNoteDtoFromNote(note))
					.collect(Collectors.toList());
			return noteDtoList;
		} catch (Exception exp) {
			log.error(NoteUtils.ERROR_FETCHING_NOTES_FOR_USER, user);
			throw new NoteNotFoundException();
		}
	}

	public boolean trashNoteByUserAndId(User user, int id) {
		try {
			Note note = noteRepository.findByIdAndUser(id, user.getId());
			note.setTrashed(true);
			note.setPinned(false);
			note.setArchived(false);
			return noteRepository.save(note) != null ? true : false;
		} catch (Exception exp) {
			log.error(NoteUtils.ERROR_FETCHING_NOTES_FOR_USER, user);
			throw new NoteNotFoundException();
		}

	}

	public boolean deleteNoteByUserAndId(User user, int id) {
		try {
			return noteRepository.deleteNoteByIdAndUserId(id, user.getId()) == 1 ? true : false;
		} catch (Exception exp) {
			log.error(NoteUtils.ERROR_DELETING_NOTE_FOR_USER, user);
			throw new NoteNotFoundException();
		}
	}

}
