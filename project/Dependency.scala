import sbt._

object Dependency {
    val CommonsLang3 = "org.apache.commons" % "commons-lang3" % "3.1"
    val CommonsIo = "commons-io" % "commons-io" % "2.3"

    val HibernateCore = "org.hibernate" % "hibernate-core" % "4.2.5.Final"
    val HibernateUserType = "org.jadira.usertype" % "usertype.core" % "3.0.0.CR1"

    val JodaTime = "joda-time" % "joda-time" % "2.1"
    val JodaMoney = "org.joda" % "joda-money" % "0.6"
    val JodaConvert = "org.joda" % "joda-convert" % "1.2"

    val Poi = "org.apache.poi" % "poi" % "3.7" exclude ("log4j", "log4j")

//    val Quartz = "org.quartz-scheduler" % "quartz" % "2.1.5"

//    val CommonsFileupload = "commons-fileupload" % "commons-fileupload" % "1.2.2"

    private val springGroupId = "org.springframework"
    private val springVersion = "3.2.4.RELEASE"

//    spring component is use to scan entity package
    val SpringContext = springGroupId % "spring-context" % springVersion
    val SpringContextSupport = springGroupId % "spring-context-support" % springVersion
    val SpringBean = springGroupId % "spring-beans" % springVersion
    val SpringTx = springGroupId % "spring-tx" % springVersion
    val SpringORM = springGroupId % "spring-orm" % springVersion
//    var SpringAsm = springGroupId % "spring-asm" % springVersion

    val MySqlConnector = "mysql" % "mysql-connector-java" % "5.1.25"

    val CGLib = "cglib" % "cglib" % "2.2.2"

    val Spec2 = "org.specs2" %% "specs2" % "1.13" % "test"


    object Group {
        import Dependency.modulesToExclusionWrapped

        val Jackson = Seq(
            "com.fasterxml.jackson.core" % "jackson-core" % "2.2.2",
            "com.fasterxml.jackson.core" % "jackson-annotations" % "2.2.2",
            "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.2",
            "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.2.2"
        )
        // jasperreports 依赖于eclipse % jdtcore % 3.1.0与drools的依赖 org.eclipse.jdt.core.compiler % ecj % 3.5.1冲突
        // 可以调整drools使用janino以避免冲突
        // 这里把jdtcore 排除了，看是否会发生问题。
        val JasperReports = Seq("net.sf.jasperreports" % "jasperreports" % "5.0.0" exclude("eclipse", "jdtcore"),
                "net.sf.barcode4j" % "barcode4j" % "2.0",
                "net.sf.barcode4j" % "barcode4j-fop-ext-complete" % "2.0",
                "org.codehaus.groovy" % "groovy-all" % "2.0.1")
//                ,"ar.com.fdvs" % "DynamicJasper" % "4.0.1" exclude("eclipse", "jdtcore"))

        val AspectJ = Seq("org.aspectj" % "aspectjrt" % "1.6.10",
            "org.aspectj" % "aspectjweaver" % "1.6.10")

//        val Slf4j = Seq("org.slf4j" % "slf4j-api" % "1.6.3",
//            "org.slf4j" % "jcl-over-slf4j" % "1.6.3",
//            "org.slf4j" % "log4j-over-slf4j" % "1.6.3",
//            "ch.qos.logback" % "logback-classic" % "0.9.30")


		val JavaMail = Seq("javax.mail" % "mail" % "1.4.5", "javax.activation" % "activation" % "1.1.1")
    }

    implicit def modulesToExclusionWrapped(modules: Seq[ModuleID]) = ExclusionWrapped(modules)
}

case class ExclusionWrapped(modules: Seq[ModuleID]) {
    def applyExclusions(): Seq[ModuleID] =
        modules.map(_.exclude("commons-logging", "commons-logging"))
}


