package com.vandalar.server.note.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteCreationRequestDto {

	@Size(max = 1000)
	@NotNull
	private String content;
	
	private double lat;
	
	private double lon;
	
	private double height;
}