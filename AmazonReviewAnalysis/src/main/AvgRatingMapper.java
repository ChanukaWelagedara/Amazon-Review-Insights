package main;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class AvgRatingMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private Text productId = new Text();
    private IntWritable score = new IntWritable();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split("\t");
        if (fields.length > 4) {
            try {
                productId.set(fields[0]);
                score.set(Integer.parseInt(fields[1]));
                context.write(productId, score);
            } catch (NumberFormatException e) {
                // Skip malformed score
            }
        }
    }
}