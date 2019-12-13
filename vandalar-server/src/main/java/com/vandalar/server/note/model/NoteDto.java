package com.vandalar.server.note.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {
	
	private long id;
	
	private String content;
	
	private double lat;
	
	private double lon;
	
	private double height;
	
	private String created;
}
