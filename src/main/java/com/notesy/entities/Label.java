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
@Table(name = "label")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, name = "id")
	private Integer id;

	@Column(name = "label_name")
	private String labelName;

	@ManyToMany
	@JoinTable(name = "label_note", joinColumns = { @JoinColumn(name = "note_id") }, inverseJoinColumns = {
			@JoinColumn(name = "label_id") })
	private List<Note> noteList;

	@Override
	public String toString() {
		return "Label [id=" + id + ", labelName=" + labelName + ", noteList=" + noteList + "]";
	}

}
