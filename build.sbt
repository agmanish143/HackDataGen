import sbtassembly.Plugin.AssemblyKeys._

assemblySettings

name := "HackDataGen"

version := "1.0"

scalaVersion := "2.10.4"



libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.8.2.0"

libraryDependencies += "org.apache.kafka" % "kafka_2.10" % "0.8.2.0"

mergeStrategy in assembly <<= (mergeStrategy in assembly) { mergeStrategy => {
  case entry => {
    val strategy = mergeStrategy(entry)
    if (strategy == MergeStrategy.deduplicate) MergeStrategy.first
    else strategy
  }
}
}