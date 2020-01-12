package com.vandalar.server.note.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteWithAuthorDto {
	
	private long id;
	
	private String content;
	
	private String userId;
	
	private String userName;
	
	private double lat;
	
	private double lon;
	
	private double height;
	
	private String created;

}
