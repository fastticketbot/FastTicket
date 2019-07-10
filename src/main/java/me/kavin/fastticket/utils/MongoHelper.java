package me.kavin.fastticket.utils;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class MongoHelper {

	private MongoClient mongoClient;

	private Object2ObjectOpenHashMap<String, MongoDatabase> databases = new Object2ObjectOpenHashMap<>();

	public MongoHelper(String uri) {
		mongoClient = new MongoClient(new MongoClientURI(uri));
	}

	public MongoDatabase getDatabase(String name) {
		if (databases.containsKey(name))
			return databases.get(name);
		else {
			MongoDatabase database = mongoClient.getDatabase(name);
			databases.put(name, database);
			return database;
		}
	}

	public MongoCollection<Document> getCollection(MongoDatabase database, String name) {
		return database.getCollection(name);
	}

	public boolean containsKey(MongoCollection<Document> collection, String key) {
		FindIterable<Document> docs = collection.find();
		for (Document doc : docs)
			if (doc.containsKey(key))
				return true;
		return false;
	}

	public int getValueInt(MongoCollection<Document> collection, String key) {
		int val = -1;
		FindIterable<Document> docs = collection.find();
		for (Document doc : docs)
			if (doc.containsKey(key))
				val = doc.getInteger(key);
		return val;
	}

	public void setValueInt(MongoCollection<Document> collection, String key, int value) {
		FindIterable<Document> docs = collection.find();
		for (Document doc : docs)
			if (doc.containsKey(key)) {
				Document replacement = cloneDocument(doc);
				replacement.put(key, value);
				collection.replaceOne(doc, replacement);
				return;
			}
		collection.insertOne(new Document().append(key, value));
	}

	public long getValueLong(MongoCollection<Document> collection, String key) {
		long val = -1;
		FindIterable<Document> docs = collection.find();
		for (Document doc : docs)
			if (doc.containsKey(key))
				val = doc.getLong(key);
		return val;
	}

	public void setValueLong(MongoCollection<Document> collection, String key, long value) {
		FindIterable<Document> docs = collection.find();
		for (Document doc : docs)
			if (doc.containsKey(key)) {
				Document replacement = cloneDocument(doc);
				replacement.put(key, value);
				collection.replaceOne(doc, replacement);
				return;
			}
		collection.insertOne(new Document().append(key, value));
	}

	public String getValueString(MongoCollection<Document> collection, String key) {
		String val = null;
		FindIterable<Document> docs = collection.find();
		for (Document doc : docs)
			if (doc.containsKey(key))
				val = doc.getString(key);
		return val;
	}

	public void setValueString(MongoCollection<Document> collection, String key, String value) {
		FindIterable<Document> docs = collection.find();
		for (Document doc : docs)
			if (doc.containsKey(key)) {
				Document replacement = cloneDocument(doc);
				replacement.put(key, value);
				collection.replaceOne(doc, replacement);
				return;
			}
		collection.insertOne(new Document().append(key, value));
	}

	public boolean getValueBoolean(MongoCollection<Document> collection, String key) {
		boolean val = false;
		FindIterable<Document> docs = collection.find();
		for (Document doc : docs)
			if (doc.containsKey(key))
				val = doc.getBoolean(key);
		return val;
	}

	public void setValueBoolean(MongoCollection<Document> collection, String key, boolean value) {
		FindIterable<Document> docs = collection.find();
		for (Document doc : docs)
			if (doc.containsKey(key)) {
				Document replacement = cloneDocument(doc);
				replacement.put(key, value);
				collection.replaceOne(doc, replacement);
				return;
			}
		collection.insertOne(new Document().append(key, value));
	}

	private Document cloneDocument(Document toClone) {
		Document clone = new Document();
		toClone.forEach((key, value) -> clone.put(key, value));
		return clone;
	}
}
