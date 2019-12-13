package com.vandalar.server.note.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notes")
@NoArgsConstructor
public class NoteEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String userId;
	
	private String content;
	
	private double lat;
	
	private double lon;
	
	private double height;
	
	private LocalDateTime creationDateTime;
}
