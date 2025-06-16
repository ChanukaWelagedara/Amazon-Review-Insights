package main;

import java.io.IOException;
import java.util.Calendar;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;


public class ReviewsPerYearMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text yearProduct = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split("\t");

        if (fields.length >= 4) {
            String productId = fields[0];
            String timeStr = fields[4];  

            try {
                long timestamp = Long.parseLong(timeStr);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(timestamp * 1000L);
                int year = cal.get(Calendar.YEAR);

                yearProduct.set(year + "_" + productId);
                context.write(yearProduct, one);
            } catch (NumberFormatException e) {
                // skip malformed record
            }
        }
    }
}
