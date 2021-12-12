package de.devofvictory.schoolapi.objects.exercises;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExerciseManager {

	private String iservAddress;
	private String[] sessionCookies;
	
	public ExerciseManager(String iservAddress, String[] cookies) {
		this.iservAddress = iservAddress;
		this.sessionCookies = cookies;
	}
	
	public IservResponse requestExercises(final int amount) {

		ExecutorService executor = Executors.newCachedThreadPool();

		Future<IservResponse> future = executor.submit(new Callable<IservResponse>() {

			@Override
			public IservResponse call() throws Exception {
				IservResponse response = new IservResponse();

				try {

					Connection iservConnection = Jsoup.connect("https://" + iservAddress + "/iserv/exercise?filter%5Bstatus%5D=current");
					iservConnection.timeout(5000);
					iservConnection.cookie("REMEMBERME", sessionCookies[0].split("=")[1]);
					iservConnection.cookie("IServSession", sessionCookies[1].split("=")[1]);
					iservConnection.cookie("IServSAT", sessionCookies[2].split("=")[1]);

					Document exercisesDocument = iservConnection.get();

					Elements table = exercisesDocument.select("#crud-table > tbody");
					Elements row = table.select("tr");

					List<Exercise> exercises = new ArrayList<Exercise>();

					int a = 0;
					if (amount > row.size()) {
						a = row.size();
					}else {
						a = amount;
					}
					for (Element element : row.subList(row.size()-a, row.size())) {

						Exercise exercise = new Exercise();

						String name = element.getAllElements().get(1).text();
						String deadline = element.getAllElements().get(4).text();
						String link = element.select("a").attr("href");

						exercise.setName(name);
						exercise.setDeadlineTime(deadline);
						exercise.setLink(link);

						iservConnection.url(link);
						Document singleExercise = iservConnection.get();
						Elements textDiv = singleExercise.select(
								"#content > div:nth-of-type(2) > div > div > div:nth-of-type(2) > div:nth-of-type(1) > div > div:nth-of-type(2) > div:nth-of-type(2)");

						Elements teacherA = singleExercise.select(
								"#content > div:nth-of-type(2) > div > div > div:nth-of-type(2) > div:nth-of-type(1) > div > table > tbody > tr:nth-of-type(1) > td > a");
						Elements startDateTd = singleExercise.select(
								"#content > div:nth-of-type(2) > div > div > div:nth-of-type(2) > div:nth-of-type(1) > div > table > tbody > tr:nth-of-type(2) > td");

						exercise.setStartTime(startDateTd.text());
						exercise.setTeacher(teacherA.text());

						String formattedText = "";
						for (Element line : textDiv.select("p")) {
							formattedText += "\n" + line.text();
						}
						formattedText = formattedText.replaceFirst("\n", "");

						exercise.setFullText(formattedText);

						exercises.add(exercise);

					}

					Collections.sort(exercises, (o1, o2) -> o2.getDeadlineAsDate().compareTo(o1.getDeadlineAsDate()));
					response.setExercises(exercises);

					String time = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss").format(Calendar.getInstance().getTime());
					response.setLastUpdate(time);
					response.setTimestamp(System.currentTimeMillis());
					response.setOutput("SUCCESS");

				} catch (Exception ex) {
					response.setOutput(ex.getClass().getSimpleName());
					ex.printStackTrace();
				}

				return response;
			}
		});

		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
