package pt.isel.odin.utils.heimdallSupport

import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["pt.isel.odin"])
class MongoConfig : AbstractMongoClientConfiguration() {

    override fun getDatabaseName(): String {
        return "logsDatabase"
    }

    @Bean
    override fun mongoClient() = MongoClients.create("mongodb+srv://DragoTeste:DragoTeste@testecluster.51fod3y.mongodb.net/?retryWrites=true&w=majority&appName=TesteCluster")
}
