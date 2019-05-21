package com.augmentum.mediacloud;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//import com.mongodb.Mongo;
//import com.mongodb.MongoClient;
//import com.mongodb.ServerAddress;
//
//@Configuration
////@EnableMongoRepositories(basePackageClasses = MongoDataSourceConfig.class)
//public class MongoDataSourceConfig extends AbstractMongoConfiguration {
//    @Value("${spring.data.mongodb.host}")
//    private String mongoHost;
//
//    @Value("${spring.data.mongodb.port}")
//    private int mongoPort;
//
//    @Value("${spring.data.mongodb.database}")
//    private String mongoDb;
//
//    @Override
//    public String getDatabaseName() {
//        return mongoDb;
//    }
//
//    @Override
//    @Bean
//    public Mongo mongo() throws Exception {
//        return new MongoClient(new ServerAddress(mongoHost, mongoPort));
//    }
//
//    @Override
//    @Bean
//    public MongoTemplate mongoTemplate() throws Exception {
//        return new MongoTemplate(mongo(),mongoDb);
//    }
//}
