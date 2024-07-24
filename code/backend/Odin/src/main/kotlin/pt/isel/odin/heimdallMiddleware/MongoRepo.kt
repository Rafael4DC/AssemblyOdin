package pt.isel.odin.heimdallMiddleware

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import pt.isel.odin.heimdallMiddleware.models.BaseLog
import pt.isel.odin.heimdallMiddleware.models.BaseLogInputModel
import pt.isel.odin.heimdallMiddleware.models.ProcessedLog

@Repository
class MongoRepo(
    private val mongoTemplate: MongoTemplate,
    @Value("\${spring.data.mongodb.collection-unprocessed}") private val unprocessedCollection: String,
    @Value("\${spring.data.mongodb.collection-bin}") private val binCollection: String,
    @Value("\${spring.data.mongodb.collection-processed}") private val processedCollection: String
) {

    fun findAllUnprocessedLogs(): List<BaseLogInputModel> {
        return mongoTemplate.findAll(BaseLogInputModel::class.java, unprocessedCollection)
    }

    fun findAllPreprocessedLogs(): List<BaseLog> {
        return mongoTemplate.findAll(BaseLog::class.java, binCollection)
    }

    fun findAllProcessedLogs(): List<ProcessedLog> {
        return mongoTemplate.findAll(ProcessedLog::class.java, processedCollection)
    }

    fun saveUnprocessedLog(log: BaseLog) {
        mongoTemplate.save(log, unprocessedCollection)
    }

    fun savePreprocessedLog(log: BaseLog) {
        mongoTemplate.save(log, binCollection)
    }

    fun saveProcessedLog(log: ProcessedLog) {
        mongoTemplate.save(log, processedCollection)
    }

    fun deleteUnprocessed(log: BaseLog) {
        mongoTemplate.remove(log, unprocessedCollection)
    }

}
