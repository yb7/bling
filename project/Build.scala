import sbt._
import Keys._
import play.Project._
import Dependency._
import org.apache.commons.io.FileUtils
import collection.JavaConverters._

object ApplicationBuild extends Build {

  val appName         = "bling"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    HibernateCore, MySqlConnector,
    JodaTime, JodaMoney, JodaConvert, HibernateUserType,
    CommonsLang3, CommonsIo, Poi,
    SpringContext, SpringContextSupport, SpringBean, SpringTx, SpringORM,
    CGLib
  ) ++ Group.JavaMail ++ Group.Jackson


  val formatRoutes = TaskKey[Unit]("format-routes")

  val formatSettings = formatRoutes := {
    val routesFile: File = file("conf/routes") // / "routes"
    val RouteRegex = """(GET|POST|PUT|DELETE)\s+(\S+)\s+(\S+.*)""".r
    val lines = for (line <- FileUtils.readLines(routesFile).asScala) yield {
      try {
        val RouteRegex(method, path, action) = line
        (method, path, action.trim)
      } catch {
        case _: MatchError => (line.trim, "", "")
      }

    }
    val MaxLengthOfPath = lines.map(_._2.length).max + 8
    val result = for ((method, path, action) <- lines) yield {
      if(method.length > 0) {
        (method + " " * (8 - method.length)) + (path + " " * (MaxLengthOfPath - path.length)) + action
      } else {
        ""
      }
    }
    println("format " + routesFile.getAbsolutePath)
    FileUtils.writeLines(routesFile, result.asJava)

  }




  val main = play.Project(appName, appVersion, appDependencies).settings(
    formatSettings,
    resolvers ++= Seq(
//      "waffle" at "https://github.com/wangyibin/mvn-repo/raw/master",
      "JBoss Repo" at "https://repository.jboss.org/nexus/content/groups/public/",
      "Sonatype Release" at "http://oss.sonatype.org/content/repositories/releases",
      "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Akka Repo" at "http://akka.io/repository/",
      "repo.novus rels" at "http://repo.novus.com/releases/"//,
//      "twitter-repo" at "http://maven.twttr.com"
    )
  )

}
