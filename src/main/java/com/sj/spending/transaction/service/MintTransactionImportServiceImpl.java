package com.sj.spending.transaction.service;

import com.sj.spending.logic.data.ImportedTransaction;
import com.sj.spending.transaction.data.entity.ImportedTransactionEntity;
import com.sj.spending.transaction.data.repo.ImportedTransactionRepository;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MintTransactionImportServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(MintTransactionImportServiceImpl.class);

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    private final ImportedTransactionRepository repository;

    @Autowired
    public MintTransactionImportServiceImpl(ImportedTransactionRepository repository) {
        this.repository = repository;
    }

    @Bean
    public KafkaListenerContainerFactory<?> kafkaJsonListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setMessageConverter(new StringJsonMessageConverter());
        factory.setConcurrency(10);
        return factory;
    }

    private ConsumerFactory<Integer, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @KafkaListener(
            topics = "spending.transactions.import.mint",
            containerFactory = "kafkaJsonListenerContainerFactory")
    public void listen(MintTransaction mintTransaction) {
        if (mintTransaction.isPending()) {
            logger.info("Skipping pending transaction: {}", mintTransaction);
            return;
        } else if (repository.existsBySourceTransactionId(mintTransaction.getId())) {
            logger.info("Skipping existing transaction: {}", mintTransaction);
            return;
        }

        ImportedTransaction transaction = new MintTransactionAdapter(mintTransaction);

        try {
            repository.save(ImportedTransactionEntity.from(transaction));
            logger.debug("Saved transaction: {}", mintTransaction);
        } catch (Exception e) {
            logger.error("Skipping, unable to save transaction: " + mintTransaction, e);
        }
    }

}
