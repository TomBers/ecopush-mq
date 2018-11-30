export ECOPUSH_MQ_URL="pulsar+ssl://mqe.tuyaeu.com:7285/"
export ECOPUSH_MQ_ACCESS_ID="rrfe8rn5n35yu7apsfjs"
export ECOPUSH_MQ_ACCESS_KEY="jrss4qj3skhtj9wnq7mq8dpftdcgecut"
export ECOPUSH_MQ_DECRYPTION_KEY="skhtj9wnq7mq8dpf"
export ECOPUSH_ENDPOINT_URL="http://ptsv2.com/t/kd000-1542236515/post"
#mvn exec:java -Dexec.mainClass="com.tuya.open.sdk.example.ConsumerExample"
./target/bin/worker

