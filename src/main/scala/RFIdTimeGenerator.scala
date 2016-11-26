import java.sql.Timestamp

import kafka.producer.KeyedMessage
import kafka.producer.ProducerConfig
import java.util.Properties

import scala.annotation.tailrec;

/**
  * Created by agarwalm on 11/26/2016.
  */
object RFIdTimeGenerator {



  def main(args: Array[String]): Unit = {
    val startTime =args(0)
    val broker =args(1)
    val topicName= args(2)
    val timeGenerationPattern= args(3).toInt
    val timeVar =args(4).toInt



    val startOffset: Long = Timestamp.valueOf(startTime).getTime()
    val producer: kafka.javaapi.producer.Producer[Integer, String] =new kafka.javaapi.producer.Producer[Integer, String](new ProducerConfig(loadProp(broker)));
    generateTime(startOffset,producer,topicName,timeGenerationPattern,timeVar)
  }
@tailrec
  def generateTime(startTime: Long,producer :kafka.javaapi.producer.Producer[Integer, String],topicName:String,timeGenerationPattern :Int,timeVar:Int): Long = {
    val diff: Long = timeVar
    println("Diff" + diff)
    val generatedTimeInLong = startTime + (Math.random() * diff).toLong

    pushToKafka(producer,topicName, (new Timestamp(generatedTimeInLong)).toString)
    val patternForTimeGeneration = timeGenerationPattern + (Math.random() * 100).toInt
    Thread.sleep(patternForTimeGeneration)

    generateTime(generatedTimeInLong,producer,topicName,timeGenerationPattern,timeVar)
  }

  def pushToKafka(producer :kafka.javaapi.producer.Producer[Integer, String],topic: String, time: String) = {
    producer.send(new KeyedMessage[Integer, String](topic, time));
  }

  def loadProp(broker :String) ={
    val props : Properties = new Properties()
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    props.put("metadata.broker.list", broker)
    props
  }

}
