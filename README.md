# Amazon-Review-Insights

Analyze Amazon product reviews using Hadoop MapReduce (Java) and Python for sentiment, ratings, and trends

```markdown
# 📊 Amazon Product Review Analysis

This project demonstrates **large-scale data analysis** using **Hadoop MapReduce (Java)** and **Python** for data preprocessing and visualization. The dataset contains Amazon product reviews, and this system extracts key insights such as:

- ⭐ **Average rating per product**
- 😊 **Sentiment polarity counts** (Positive, Neutral, Negative)
- 📅 **Number of reviews per year**

---

## 📁 Project Structure
```

Amazon-Review-Insights/
├── AmazonReviewAnalysis/ # Java MapReduce code
│ └── src/ # Java classes (Mappers, Reducers, Drivers)
│ ├── AvgRatingMapper.java
│ ├── AvgRatingReducer.java
│ ├── AvgRatingDriver.java
│ ├── ReviewsPerYearMapper.java
│ ├── ReviewsPerYearReducer.java
│ ├── ReviewsPerYearDriver.java
│ ├── SentimentMapper.java
│ ├── SentimentReducer.java
│ ├── SentimentDriver.java
│ ├── ReviewYearMapper.java
│ ├── ReviewYearReducer.java
│ ├── ReviewYearDriver.java
├── preprocess/ # Python data cleaning
│ ├── preprocess.ipynb
│ └── cleaned_reviews.tsv
├── visualization/ # Python data visualization
│ └── visualization.ipynb
├── data/
│ └── Reviews.csv
├── output/
│ └── AmazonReviewAnalysis2.0.jar
├── README.md
└── requirements.txt

````

---

## 🚀 How to Run the Project

### ✅ 1. Preprocessing (Python)

- Download the dataset from [Kaggle](https://www.kaggle.com/datasets/arhamrumi/amazon-product-reviews) and place it in the `data/` directory.

```bash
cd preprocess
pip install -r requirements.txt
````

- Run `preprocess.ipynb` to clean the raw dataset and generate `cleaned_reviews.tsv`.

---

### ⚙️ 2. Hadoop Setup (Linux Environment)

#### 2.1 Install Java 8

```bash
sudo apt install openjdk-8-jdk
```

#### 2.2 Add Environment Variables (`~/.bashrc`)

```bash
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export PATH=$PATH:$JAVA_HOME/bin
export HADOOP_HOME=~/hadoop-3.4.1
export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin
export HADOOP_MAPRED_HOME=$HADOOP_HOME
export YARN_HOME=$HADOOP_HOME
export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
export HADOOP_OPTS="-Djava.library.path=$HADOOP_HOME/lib/native"
export HADOOP_STREAMING=$HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-3.4.1.jar
export HADOOP_LOG_DIR=$HADOOP_HOME/logs
export PDSH_RCMD_TYPE=ssh
```

#### 2.3 Install SSH

```bash
sudo apt-get install ssh
```

#### 2.4 Download & Extract Hadoop

```bash
wget https://downloads.apache.org/hadoop/common/hadoop-3.4.1/hadoop-3.4.1.tar.gz
tar -zxvf hadoop-3.4.1.tar.gz
```

#### 2.5 Configure Hadoop

**hadoop-env.sh**

```bash
JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
```

**core-site.xml**

```xml
<configuration>
 <property>
   <name>fs.defaultFS</name>
   <value>hdfs://localhost:9000</value>
 </property>
 <!-- Proxy user properties -->
</configuration>
```

**hdfs-site.xml**

```xml
<configuration>
 <property>
   <name>dfs.replication</name>
   <value>1</value>
 </property>
</configuration>
```

**mapred-site.xml**

```xml
<configuration>
 <property>
   <name>mapreduce.framework.name</name>
   <value>yarn</value>
 </property>
 <property>
   <name>mapreduce.application.classpath</name>
   <value>$HADOOP_MAPRED_HOME/share/hadoop/mapreduce/*:$HADOOP_MAPRED_HOME/share/hadoop/mapreduce/lib/*</value>
 </property>
</configuration>
```

**yarn-site.xml**

```xml
<configuration>
 <property>
   <name>yarn.nodemanager.aux-services</name>
   <value>mapreduce_shuffle</value>
 </property>
</configuration>
```

#### 2.6 Format HDFS

```bash
hdfs namenode -format
```

#### 2.7 Start Hadoop Services

```bash
start-dfs.sh
start-yarn.sh
```

> To stop:

```bash
stop-dfs.sh
stop-yarn.sh
```

#### 2.8 Check Services

```bash
jps
```

- Namenode UI: [http://localhost:9870](http://localhost:9870)
- ResourceManager UI: [http://localhost:8088](http://localhost:8088)

---

### 🧩 3. MapReduce Job (Java in Eclipse)

1. Import the `AmazonReviewAnalysis/` directory as a Java project.
2. Compile all Mapper, Reducer, and Driver classes.
3. Export the JAR as `AmazonReviewAnalysis2.0.jar` to the `output/` folder.

#### Upload & Run on Hadoop:

```bash
hdfs dfs -mkdir -p /review_analysis/input
hdfs dfs -put cleaned_reviews.tsv /review_analysis/input

hadoop jar AmazonReviewAnalysis2.0.jar main.AvgRatingDriver /review_analysis/input /review_analysis/output_avg_rating
hadoop jar AmazonReviewAnalysis2.0.jar main.SentimentDriver /review_analysis/input /review_analysis/output_review_sentiment
hadoop jar AmazonReviewAnalysis2.0.jar main.ReviewYearDriver /review_analysis/input /review_analysis/output_reviews_year
hadoop jar AmazonReviewAnalysis2.0.jar main.ReviewsPerYearDriver /review_analysis/input /review_analysis/output_reviews_per_year
```

#### View Output:

```bash
hdfs dfs -cat /review_analysis/output_avg_rating/part-r-00000
hdfs dfs -cat /review_analysis/output_review_sentiment/part-r-00000
hdfs dfs -cat /review_analysis/output_reviews_year/part-r-00000
hdfs dfs -cat /review_analysis/output_reviews_per_year/part-r-00000
```

#### Download from HDFS:

```bash
hdfs dfs -get /review_analysis/output_avg_rating ./output/avg_rating
hdfs dfs -get /review_analysis/output_review_sentiment ./output/review_sentiment
hdfs dfs -get /review_analysis/output_reviews_year ./output/reviews_year
hdfs dfs -get /review_analysis/output_reviews_per_year ./output/reviews_per_year
```

---

### 📈 4. Visualization (Python)

```bash
cd visualization
pip install -r ../requirements.txt
```

- Run `visualization.ipynb` to generate insightful plots from the MapReduce outputs.

---

## 🛠️ Requirements

- Java 8
- Hadoop 3.4.1
- Python 3.8+
- Python Libraries:

  - `pandas`
  - `matplotlib`
  - `seaborn`

---

## 📦 Dataset

- **Name**: Amazon Product Reviews
- **Source**: [Kaggle Dataset](https://www.kaggle.com/datasets/arhamrumi/amazon-product-reviews)

```

Let me know if you want to add images, GIF demos, or GitHub badges to make it even more impressive!
```
