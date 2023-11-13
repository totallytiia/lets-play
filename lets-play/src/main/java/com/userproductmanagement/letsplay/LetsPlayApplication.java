package com.userproductmanagement.letsplay;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

@SpringBootApplication
public class LetsPlayApplication {

	public static void main(String[] args) {

		SpringApplication.run(LetsPlayApplication.class, args);

		String connectionString = "mongodb+srv://tiia:tiia1234@letsplaydb.pyo9xoc.mongodb.net/?retryWrites=true&w=majority";
		ServerApi serverApi = ServerApi.builder()
				.version(ServerApiVersion.V1)
				.build();
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(connectionString))
				.serverApi(serverApi)
				.build();
		// Create a new client and connect to the server
		try (MongoClient mongoClient = MongoClients.create(settings)) {
			try {
				// Send a ping to confirm a successful connection
				MongoDatabase database = mongoClient.getDatabase("admin");
				database.runCommand(new Document("ping", 1));
				System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
			} catch (MongoException e) {
				e.printStackTrace();
			}
		}

	}
}
