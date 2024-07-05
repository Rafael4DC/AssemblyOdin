package pt.isel.odin.heimdallMiddleware

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import pt.isel.odin.heimdallMiddleware.models.BaseLog
import pt.isel.odin.heimdallMiddleware.models.BaseLogModel
import pt.isel.odin.heimdallMiddleware.models.ProcessedLog

@Repository
class MongoRepo(
    private val mongoTemplate: MongoTemplate,
    @Value("\${spring.data.mongodb.collection-unprocessed}") private val unprocessedCollection: String,
    @Value("\${spring.data.mongodb.collection-preprocessed}") private val preprocessedCollection: String,
    @Value("\${spring.data.mongodb.collection-processed}") private val processedCollection: String
) {

    fun findAllUnprocessedLogs(): List<BaseLogModel> {
        return mongoTemplate.findAll(BaseLogModel::class.java, unprocessedCollection)
    }

    fun findAllPreprocessedLogs(): List<BaseLog> {
        return mongoTemplate.findAll(BaseLog::class.java, preprocessedCollection)
    }

    fun findAllProcessedLogs(): List<ProcessedLog> {
        return mongoTemplate.findAll(ProcessedLog::class.java, processedCollection)
    }

    fun saveUnprocessedLog(log: BaseLog) {
        mongoTemplate.save(log, unprocessedCollection)
    }

    fun savePreprocessedLog(log: BaseLog) {
        mongoTemplate.save(log, preprocessedCollection)
    }

    fun saveProcessedLog(log: ProcessedLog) {
        mongoTemplate.save(log, processedCollection)
    }
}
