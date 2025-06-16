package main;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class AvgRatingReducer extends Reducer<Text, IntWritable, Text, DoubleWritable> {
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int total = 0, count = 0;
        for (IntWritable val : values) {
            total += val.get();
            count++;
        }
        if (count != 0) {
            context.write(key, new DoubleWritable((double) total / count));
        }
    }
}