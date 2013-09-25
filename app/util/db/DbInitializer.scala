package util.db

import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import collection.JavaConverters._
import play.api.Play.current
/**
 * User: abin
 * Date: 13-9-17
 * Time: 上午9:35
 */
class DbInitializer {
    private val logger = LoggerFactory.getLogger(this.getClass)
    private val jdbcTempate = new JdbcTemplate(play.api.db.DB.getDataSource())


    private def getTables(): Set[String] = {
        val tables = jdbcTempate.queryForList("show tables", classOf[String]).asScala.toSet
        logger.info("existed tables " + tables.mkString(","))
        tables
    }

    def init() {
        val tables = getTables()
        val quartzInit = new QuartzInitializer(jdbcTempate)

        if (!tables.exists(_.equalsIgnoreCase("QRTZ_JOB_DETAILS"))) {
            quartzInit.createTables()
        }
    }
}
