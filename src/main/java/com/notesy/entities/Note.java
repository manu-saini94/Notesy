package com.notesy.entities;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "note")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, name = "id")
	private Integer id;

	@Column(name = "title")
	private String title;

	@Column(name = "content", length = 10000)
	private String content;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> images;

	@Column(name = "color")
	private String color;

	@Column(name = "is_pinned")
	private boolean isPinned;

	@Column(name = "is_archived")
	private boolean isArchived;

	@Column(name = "is_trashed")
	private boolean isTrashed;

	@Column(name = "reminder")
	private LocalDateTime reminder;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@CreationTimestamp
	@Column(updatable = false, name = "created_at")
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	@ManyToMany(mappedBy = "noteList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Label> labelList;

	@ManyToMany(mappedBy = "noteList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Collaborator> collaboratorList;

	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", content=" + content + ", images=" + images + ", color="
				+ color + ", isPinned=" + isPinned + ", isArchived=" + isArchived + ", isTrashed=" + isTrashed
				+ ", reminder=" + reminder + ", updatedAt=" + updatedAt + ", createdAt=" + createdAt + ", labelList="
				+ labelList + ", collaboratorList=" + collaboratorList + "]";
	}

}
