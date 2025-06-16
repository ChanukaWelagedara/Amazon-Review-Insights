package main;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class SentimentMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text sentiment = new Text();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split("\t");
        if (fields.length > 4) {
            try {
                int score = Integer.parseInt(fields[1]);
                if (score >= 4) sentiment.set("Positive");
                else if (score == 3) sentiment.set("Neutral");
                else sentiment.set("Negative");
                context.write(sentiment, one);
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
    }
}