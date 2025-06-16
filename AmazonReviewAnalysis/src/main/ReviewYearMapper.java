package main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class ReviewYearMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text year = new Text();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split("\t");
        if (fields.length > 4) {
            try {
                long timestamp = Long.parseLong(fields[4]) * 1000L;
                String yearStr = new SimpleDateFormat("yyyy").format(new Date(timestamp));
                year.set(yearStr);
                context.write(year, one);
            } catch (Exception e) {
                // Ignore
            }
        }
    }
}