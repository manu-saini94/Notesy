package com.notesy.entities;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "collaborator")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Collaborator {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, name = "id")
	private Integer id;

	@Column(name = "collaborator_name")
	private String collaboratorName;

	@Column(name = "collaborator_email")
	private String email;

	@ManyToMany
	@JoinTable(name = "collaborator_note", joinColumns = { @JoinColumn(name = "note_id") }, inverseJoinColumns = {
			@JoinColumn(name = "collaborator_id") })
	private List<Note> noteList;

	@Override
	public String toString() {
		return "Collaborator [id=" + id + ", collaboratorName=" + collaboratorName + ", email=" + email + ", noteList="
				+ noteList + "]";
	}

}
