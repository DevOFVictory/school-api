package de.devofvictory.schoolapi.objects.exercises;

import java.util.ArrayList;
import java.util.List;

public class IservResponse {
	
	private String output;
	private String lastUpdate;
	private long timestamp;
	private List<Exercise> exercises;

	public IservResponse() {
		this.exercises = new ArrayList<Exercise>();
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setLastUpdate(String age) {
		this.lastUpdate = age;
	}
	
	public String getLastUpdate() {
		return lastUpdate;
	}
	
	public void setOutput(String output) {
		this.output = output;
	}
	
	public String getOutput() {
		return output;
	}
	
	public List<Exercise> getExercises() {
		return exercises;
	}
	
	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}
	
	public void addExercise(Exercise exercise) {
		if (!exercises.contains(exercise))
			exercises.add(exercise);
	}

}
