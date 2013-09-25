package util.db

import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource
import org.slf4j.LoggerFactory
import play.api.Play
import org.springframework.core.io.ClassPathResource
import org.apache.commons.io.{IOUtils, FileUtils}
import uti.ScriptRunner
import java.io.{BufferedReader, FileReader}

/**
 * User: abin
 * Date: 13-9-17
 * Time: 上午9:18
 */
class QuartzInitializer(jdbcTempate: JdbcTemplate) {
    val logger = LoggerFactory.getLogger(this.getClass)
    def createTables() {
        val cpr = new ClassPathResource("quartz_tables_mysql.sql")

        logger.info(" >>>>>>> create quartz tables <<<<<<<")
        new ScriptRunner(jdbcTempate.getDataSource.getConnection, false, true)
            .runScript(new BufferedReader(new FileReader(cpr.getFile)))
        logger.info(" >>>>>>> create quartz tables finished! <<<<<<<")
    }
}
