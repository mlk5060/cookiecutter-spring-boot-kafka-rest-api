- Check if Kafka Health Indicators are suitable: I think they may not reflect the state of the Kafka topic the app connects to! For example, the app may say "I'm alive!" when the Kafka topic is actually down. This may be OK, but I can't be sure if its suitable at the moment?

- Check if the `k8s` stuff actually works!

- Validate `"_kafka_topic_name"` according to the Kafka topic naming conventions and try to create it (if it doesn't exist) in the relevant environment via `hooks/pre_gen_project.py`
