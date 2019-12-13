package com.vandalar.server.note.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteCreationRequestDto {
	
	private String privateUserId;
	
	private String content;
	
	private double lat;
	
	private double lon;
	
	private double height;
}
