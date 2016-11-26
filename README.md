Generates Data for RFID

For running it please put below arguments in your jar call :
    val startTime =args(0)
    val broker =args(1)
    val topicName= args(2)
    val timeGenerationPattern= args(3).toInt
    val timeVar =args(4).toInt

example "2012-01-01 00:00:00" localhost:9092 hack 1000 500
